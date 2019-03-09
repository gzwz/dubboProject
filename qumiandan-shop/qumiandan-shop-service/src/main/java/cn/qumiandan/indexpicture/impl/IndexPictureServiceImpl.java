package cn.qumiandan.indexpicture.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.indexpicture.api.IIndexPictureService;
import cn.qumiandan.indexpicture.entity.IndexPicture;
import cn.qumiandan.indexpicture.enums.IndexPictureEnums;
import cn.qumiandan.indexpicture.mapper.IndexPictureMapper;
import cn.qumiandan.indexpicture.vo.IndexPictureVO;
import cn.qumiandan.indexpicture.vo.QueryIndexPictureParamsVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @description：首页轮播图片接口实现类
 * @author：zhuyangyong
 * @date: 2018/11/10 16:34
 */
@Slf4j
@Component
@Service(interfaceClass = IIndexPictureService.class)
public class IndexPictureServiceImpl implements IIndexPictureService {

    @Autowired
    private IndexPictureMapper indexPictureMapper;

    @Override
    public List<IndexPictureVO> getIndexPictureList(String areaCode) {
        //首页总部轮播图集合
        List<IndexPictureVO> centerIndexPictureList = indexPictureMapper.getCenterIndexPictureListByCode("0");

        //首页区轮播图集合
        List<IndexPictureVO> districtIndexPictureList = indexPictureMapper.getCenterIndexPictureListByCode(areaCode);
        List<IndexPictureVO> retList = new ArrayList<>();
        List<Long> idList = new ArrayList<>();
        if (districtIndexPictureList.size() >= 3) {
            return districtIndexPictureList;
        } else if (districtIndexPictureList.size() < 1) {
            return centerIndexPictureList;
        } else {
            if (districtIndexPictureList.size() > 0) {
                for (IndexPictureVO districtVO : districtIndexPictureList) {
                    retList.add(districtVO);
                    idList.add(districtVO.getId());
                }
            }
            if (retList.size() < 3 && centerIndexPictureList.size() > 0) {
                for (IndexPictureVO centerVO : centerIndexPictureList) {
                    if (retList.size() < 3 && !idList.contains(centerVO.getId())) {
                        retList.add(centerVO);
                        idList.add(centerVO.getId());
                    }
                }
            }
        }
        return retList;
    }

    @Override
    @Transactional(rollbackFor = { QmdException.class, Exception.class })
    public int addIndexPicture(IndexPictureVO indexPictureVO) throws QmdException {
        IndexPicture indexPicture = new IndexPicture();
        if(null != indexPictureVO){
            //排序自增
            Integer maxSort = indexPictureMapper.getMaxSort();
            indexPictureVO.setSort((maxSort!=null?maxSort:0) + 1);
            BeanUtils.copyProperties(indexPictureVO,indexPicture);
        }
        indexPicture.setCreateDate(new Date());
        int ret = indexPictureMapper.insert(indexPicture);
        if(ret < 1){
            throw new QmdException("添加图片失败");
        }
        return ret;
    }

    @Override
    @Transactional(rollbackFor = { QmdException.class, Exception.class })
    public void deleteByFileName(String fileName) throws QmdException{
        int ret = indexPictureMapper.deleteByFileName(fileName);
        if(ret < 1){
            throw new QmdException("删除图片失败");
        }
    }

    /**
     * 总后台查询轮播图
     * @param paramsVO
     * @return
     */
	@Override
	public PageInfo<IndexPictureVO> queryIndexPicture(QueryIndexPictureParamsVO paramsVO) {
		PageHelper.startPage(paramsVO.getPageNum(), paramsVO.getPageSize());
		List<IndexPictureVO> indexPictureVOs = indexPictureMapper.queryIndexPicture(paramsVO.getAreaCode(),paramsVO.getIsValid(),paramsVO.getStatus());
		if(ObjectUtils.isEmpty(indexPictureVOs)) {
			return null;
		}
		PageInfo<IndexPictureVO> pageInfo = new PageInfo<>(indexPictureVOs);
		return pageInfo;
	}

	
    /**
     * 修改首页轮播图
     * @param indexPictureVO
     * @return
     */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public Integer updateIndexPictureById(IndexPictureVO indexPictureVO) {
		IndexPicture indexPicture = indexPictureMapper.selectById(indexPictureVO.getId());
		if(indexPicture == null) {
			throw new QmdException("该图片不存在");
		}
		indexPicture = CopyBeanUtil.copyBean(indexPictureVO, IndexPicture.class);
		int i = indexPictureMapper.updateById(indexPicture);
		if(i != 1) {
			throw new QmdException("修改失败");
		}
		return i;
	}

    /**
     * 删除首页轮播图
     * @param id
     */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public void deleteIndexPictureById(Long id) {
		IndexPicture indexPicture = indexPictureMapper.selectById(id);
		if(indexPicture == null) {
			log.error("deleteIndexPictureById|该图片不存在,首页轮播图id："+id);
			throw new QmdException("该图片不存在");
		}
		indexPicture.setStatus(IndexPictureEnums.Deleted.getCode());
		int i = indexPictureMapper.updateById(indexPicture);
		if(i != 1) {
			log.error("deleteIndexPictureById|删除首页轮播图失败："+id);
			throw new QmdException("删除首页轮播图失败");
		}
	}
}
