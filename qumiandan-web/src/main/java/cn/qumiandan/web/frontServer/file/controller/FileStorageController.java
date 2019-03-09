package cn.qumiandan.web.frontServer.file.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;

import cn.qumiandan.common.pojo.Result;
import cn.qumiandan.utils.QiNiuFileUtil;
import cn.qumiandan.utils.ResultUtils;
import cn.qumiandan.web.frontServer.file.entity.request.FileDeleteVO;
import cn.qumiandan.web.frontServer.file.entity.request.FileInfoVO;
import cn.qumiandan.web.frontServer.file.entity.request.FileListVO;
import cn.qumiandan.web.frontServer.file.entity.request.FileUploadVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @description：文件存储控制器
 * @author：zhuyangyong
 * @date: 2018/11/19 16:59
 */
@RestController
@RequestMapping("/file/")
@Slf4j
public class FileStorageController {

    /**
     * 获取文件列表
     * @param fileListVO
     * @return
     */
    @PostMapping("getFileList")
    public static Result<BucketManager.FileListIterator> getFileList(@Valid @RequestBody(required = false) FileListVO fileListVO, BindingResult bindingResult) {
        //参数校验
        if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }

        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuFileUtil.getZone());
        BucketManager bucketManager = new BucketManager(QiNiuFileUtil.getAuth(), cfg);
        // 列举空间文件列表
        BucketManager.FileListIterator fileListIterator =
                bucketManager.createFileListIterator(QiNiuFileUtil.getBucket(), fileListVO.getPrefix(), fileListVO.getLimit(),fileListVO.getDelimiter());
        Result<BucketManager.FileListIterator> result = ResultUtils.success();
        result.setData(fileListIterator);
        return result;
    }

    /**
     * 根据图片存储名称获取文件信息
     * @param fileInfoVO
     * @return
     */
    @PostMapping("getFileInfo")
    public static Result<FileInfo> getFileInfo(@Valid @RequestBody(required = false) FileInfoVO fileInfoVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }
        FileInfo fileInfo = QiNiuFileUtil.getFileInfo(fileInfoVO.getKey());
        Result<FileInfo> result = ResultUtils.success();
        result.setData(fileInfo);
        log.info("获取文件信息成功：" + fileInfo);
        return result;
    }

    /**
     *  根据图片名称刪除图片
     * @param fileDeleteVO
     */
    @PostMapping("deleteFileByName")
    public static Result<FileDeleteVO> deleteFileByName(@Valid @RequestBody(required = false) FileDeleteVO fileDeleteVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuFileUtil.getZone());
        BucketManager bucketManager = new BucketManager(QiNiuFileUtil.getAuth(), cfg);
        try {
            bucketManager.delete(QiNiuFileUtil.getBucket(), fileDeleteVO.getFileName());
            log.info("根据图片名称刪除图片成功");
            return ResultUtils.success();
        } catch (QiniuException ex) {
            // 如果遇到异常，说明删除失败
            return ResultUtils.error(ex.getMessage() != null ? ex.getMessage() : "删除失败，文件信息不存在");
        }
    }

    /**
     * 文件上传
     * @param fileUploadVO
     * @return
     */
    @PostMapping("fileUpload")
    public static Result<DefaultPutRet> fileUpload(@Valid @RequestBody(required = false) FileUploadVO fileUploadVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtils.paramsError(bindingResult);
        }
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuFileUtil.getZone());
        UploadManager uploadManager = new UploadManager(cfg);
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            Response response = uploadManager.put(fileUploadVO.getLocalFilePath(), key, QiNiuFileUtil.getUploadCredential());
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            String fileUrl = QiNiuFileUtil.getDomainOfBucket() + putRet.key;
            putRet.key = fileUrl;
            //返回
            Result<DefaultPutRet> result = ResultUtils.success();
            result.setData(putRet);
            log.info("file-upload-success：" + putRet.key);
            return result;
        } catch (QiniuException ex) {
            ex.printStackTrace();
            return ResultUtils.error(ex.getMessage() != null ? ex.getMessage() : "上传失败");
        }
    }

    /**
     * 获取文件上传凭证
     * @return
     */
    @RequestMapping("getUploadToken")
    public Result<String> getUploadToken(){
        String upToken = QiNiuFileUtil.getUploadCredential();
        Result<String> result = ResultUtils.success();
        result.setData(upToken);
        return result;
    }

}
