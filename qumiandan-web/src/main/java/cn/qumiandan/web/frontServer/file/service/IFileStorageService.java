package cn.qumiandan.web.frontServer.file.service;


import java.text.ParseException;
import java.util.Date;

import cn.qumiandan.common.exception.QmdException;

public interface IFileStorageService {
    /**
     * 添加首页轮播图片
     * @param name 图片名称
     * @param imageUrl 图片url
     * @param suffix 图片后缀名
     * @param suffix 图片描述
     * @param imageSize 图片大小
     * @param areaCode 中心编号
     * @return
     */
    void addIndexPicture(String name, String imageUrl, String skipUrl, long imageSize, String suffix,
                         String intro, String areaCode,Date effectiveTime,Date loseTime) throws ParseException,QmdException;

    /**
     * 根据文件名删除文件
     * @param fileName
     */
    void deleteByFileName(String fileName) throws QmdException;
}
