package cn.qumiandan.web.frontServer.file.service.impl;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.indexpicture.api.IIndexPictureService;
import cn.qumiandan.indexpicture.vo.IndexPictureVO;
import cn.qumiandan.web.frontServer.file.service.IFileStorageService;

/**
 * @description：文件存储接口实现类
 * @author：zhuyangyong
 * @date: 2018/11/13 11:50
 */

@Service("fileServiceImpl")
public class FileStorageServiceImpl implements IFileStorageService {

    @Reference()
    private IIndexPictureService indexPictureService;

    @Override
    public void addIndexPicture(String name, String imageUrl, String skipUrl, long imageSize,
                                String suffix, String intro, String areaCode,Date effectiveTime,
                                Date loseTime) throws ParseException,QmdException {
        IndexPictureVO indexPictureVO = new IndexPictureVO();
        indexPictureVO.setName(name);
        indexPictureVO.setImageUrl(imageUrl);
        indexPictureVO.setSkipUrl(skipUrl);
        indexPictureVO.setImageSize(imageSize);
        indexPictureVO.setSuffix(suffix);
        indexPictureVO.setEffectiveTime(effectiveTime);
        indexPictureVO.setLoseTime(loseTime);
        indexPictureVO.setIntro(intro);
        indexPictureVO.setAreaCode(areaCode);
        indexPictureVO.setCreateDate(new Date());
        indexPictureService.addIndexPicture(indexPictureVO);
    }

    @Override
    public void deleteByFileName(String fileName) throws QmdException{
        indexPictureService.deleteByFileName(fileName);
    }
}
