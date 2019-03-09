package cn.qumiandan.web.frontServer.file.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.indexpicture.api.IIndexPictureService;
import cn.qumiandan.indexpicture.vo.IndexPictureVO;
import cn.qumiandan.indexpicture.vo.QueryIndexPictureParamsVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.QiNiuFileUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.file.entity.request.FileDeleteVO;
import cn.qumiandan.web.frontServer.file.entity.request.GetIdParams;
import cn.qumiandan.web.frontServer.file.entity.request.IndexFileVO;
import cn.qumiandan.web.frontServer.file.entity.request.IndexPictureVOParams;
import cn.qumiandan.web.frontServer.file.entity.request.QueryIndexPictureParams;
import cn.qumiandan.web.frontServer.file.entity.request.UpdateIndexPictureParams;
import cn.qumiandan.web.frontServer.file.service.IFileStorageService;
import lombok.extern.slf4j.Slf4j;

/**
 * @description：首页轮播图文件存储控制器
 * @author：zhuyangyong
 * @date: 2018/11/12 14:25
 */
@RestController
@RequestMapping("/index/")
@Slf4j
public class IndexPictureController {

    @Autowired
    private IFileStorageService fileStorageService;
    @Reference()
    private IIndexPictureService indexPictureService;

    /**
     * 上传首页轮播图
     * @param indexPictureVO
     * @return
     */
    @PostMapping("uploadIndexPicture")
    public Result<Void> uploadIndexPicture(@Valid @RequestBody(required = false) IndexPictureVOParams indexPictureVO, BindingResult bindingResult) {
        /*if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }

        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuFileUtil.getZone());
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
//        String key = indexPictureVO.getName();
        try {
            //上传
            Response response = uploadManager.put(indexPictureVO.getLocalFilePath(), key, QiNiuFileUtil.getUploadCredential());
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            String fileUrl = QiNiuFileUtil.getDomainOfBucket() + putRet.key;
            putRet.key = fileUrl;
            //获取文件信息
            FileInfo fileInfo = QiNiuFileUtil.getFileInfo(putRet.hash);
            String mimeType = fileInfo != null ? fileInfo.mimeType : null;
            String suffix = mimeType != null ? mimeType.substring(mimeType.indexOf("/") + 1,mimeType.length()) : null;
            long imageSize = fileInfo != null ? fileInfo.fsize : 0;
//            String storageName = putRet.hash;
            try {
                try{
                    //保存文件信息到数据库
                    fileStorageService.addIndexPicture(indexPictureVO.getName(), fileUrl, indexPictureVO.getSkipUrl(),
                            imageSize, suffix, indexPictureVO.getIntro(), indexPictureVO.getAreaCode(),
                            indexPictureVO.getEffectiveTime(), indexPictureVO.getLoseTime());
                }catch (SHException shException){
                    ResultUtils.error(shException.getMessage());
                }
            }catch (ParseException e){
                ResultUtils.error(e.getMessage());
            }
            log.info("上传首页轮播图片成功：" + putRet.key);
            return ResultUtils.success(putRet);
        } catch (QiniuException ex) {
            return ResultUtils.error(ex.getMessage());
        }*/
    	
    	if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }
    	IndexPictureVO pictureVO = CopyBeanUtil.copyBean(indexPictureVO, IndexPictureVO.class);
    	indexPictureService.addIndexPicture(pictureVO);
    	return ResultUtils.success("图片上传成功");
    }

    /**
     *  根据图片名称刪除图片
     * @param fileDeleteVO
     */
    @PostMapping("/deleteIndexPictureByFileName")
    public Result<FileDeleteVO> deleteIndexPictureByFileName(@Valid @RequestBody(required = false) FileDeleteVO fileDeleteVO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuFileUtil.getZone());
        BucketManager bucketManager = new BucketManager(QiNiuFileUtil.getAuth(), cfg);
        try {
            bucketManager.delete(QiNiuFileUtil.getBucket(), fileDeleteVO.getFileName());
            try {
                fileStorageService.deleteByFileName(fileDeleteVO.getFileName());
            }catch (QmdException shException){
                return ResultUtils.error(shException.getMessage());
            }
            log.info("根据图片名称刪除图片成功");
            return ResultUtils.success();
        } catch (QiniuException ex) {
            // 如果遇到异常，说明删除失败
            return ResultUtils.error(ex.getMessage() != null ? ex.getMessage() : "删除失败，文件信息不存在");
        }
    }

    /**
     * 获取首页轮播图片
     * @return
     */
    @PostMapping("/getIndexPictureList")
    public Result<List<cn.qumiandan.indexpicture.vo.IndexPictureVO>> getIndexPictureList(@Valid @RequestBody(required = false) IndexFileVO indexFileVO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }
        List<cn.qumiandan.indexpicture.vo.IndexPictureVO> pictureList = indexPictureService.getIndexPictureList(indexFileVO.getAreaCode());
        Result<List<cn.qumiandan.indexpicture.vo.IndexPictureVO>> result = ResultUtils.success();
        result.setData(pictureList);
        return result;
    }
    
    /**
     * 总后台查询首页轮播图
     * @param params
     * @param bindingResult
     * @return
     */
    @RequestMapping("queryIndexPicture")
    public Result<PageInfo<IndexPictureVO>> queryIndexPicture(@Valid @RequestBody(required = false) QueryIndexPictureParams params, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }
        QueryIndexPictureParamsVO paramsVO = CopyBeanUtil.copyBean(params, QueryIndexPictureParamsVO.class);
        PageInfo<IndexPictureVO> pageInfo = indexPictureService.queryIndexPicture(paramsVO);
        Result<PageInfo<IndexPictureVO>> result = ResultUtils.success();
        result.setData(pageInfo);
        return result;
    }
    
    /**
     * 修改首页轮播图
     * @param params
     * @param bindingResult
     * @return
     */
    @RequestMapping("updateIndexPictureById")
    public Result<Void> updateIndexPictureById(@Valid @RequestBody(required = false) UpdateIndexPictureParams params, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }
        IndexPictureVO paramsVO = CopyBeanUtil.copyBean(params, IndexPictureVO.class);
        indexPictureService.updateIndexPictureById(paramsVO);
        return  ResultUtils.success("修改首页轮播图信息成功");
    }
 
    /**
     * 删除首页轮播图
     * @param params
     * @param bindingResult
     * @return
     */
    @RequestMapping("deleteIndexPictureById")
    public Result<Void> deleteIndexPictureById(@Valid @RequestBody(required = false)GetIdParams params, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }
        indexPictureService.deleteIndexPictureById(params.getId());
        return  ResultUtils.success("删除首页轮播图信息成功");
    }
    

}
