package cn.qumiandan.indexpicture.api;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.indexpicture.vo.IndexPictureVO;
import cn.qumiandan.indexpicture.vo.QueryIndexPictureParamsVO;

import java.util.List;

import com.github.pagehelper.PageInfo;

public interface IIndexPictureService {

    /**
     * 获取首页轮播图片
     * @return
     */
    List<IndexPictureVO> getIndexPictureList(String areaCode);

    /**
     * 添加首页轮播图片
     * @param indexPictureVO
     * @return
     */
    int addIndexPicture(IndexPictureVO indexPictureVO) throws QmdException;

    /**
     * 根据文件名删除文件
     * @param fileName
     */
    void deleteByFileName(String fileName) throws QmdException;
    
    /**
     * 总后台查询轮播图
     * @param paramsVO
     * @return
     */
    PageInfo<IndexPictureVO> queryIndexPicture(QueryIndexPictureParamsVO paramsVO);

    /**
     * 修改首页轮播图
     * @param indexPictureVO
     * @return
     */
    Integer updateIndexPictureById(IndexPictureVO indexPictureVO);
    
    /**
     * 删除首页轮播图
     * @param id
     */
    void deleteIndexPictureById(Long id);
}
