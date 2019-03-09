package cn.qumiandan.industry.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.industry.api.IIndustryService;
import cn.qumiandan.industry.entity.Industry;
import cn.qumiandan.industry.mapper.IndustryMapper;
import cn.qumiandan.industry.vo.GetAllIndustryVO;
import cn.qumiandan.industry.vo.IndustryVO;
import cn.qumiandan.utils.CopyBeanUtil;

/**
 * 行业信息管理实现类
 * 
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = IIndustryService.class)
public class IndustryServiceImpl implements IIndustryService {

	@Autowired
	private IndustryMapper industryMapper;

	/**
	 * 添加行业信息
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int addIndustry(IndustryVO industryVO) throws QmdException {
		
		if(industryVO.getParentId()==null&&industryVO.getLevel().equals(1)) {
			industryVO.setParentId(0L);
		}
		IndustryVO industryVO2 = industryMapper.getIndustryVOByName(industryVO.getName(),industryVO.getParentId());
		if (industryVO2 != null) {
			throw new QmdException("同一分类下不能存在相同的子分类");
		}
		if(industryVO.getLevel().equals(3)&&(industryVO.getFee()==null||industryVO.getSetInterval()==null||industryVO.getRateCode()==null)) {
			throw new QmdException("三级行业分类的费率码、费率、结算周期不能为空");
		}
		Industry industry = CopyBeanUtil.copyBean(industryVO, Industry.class);
		industry.setCreateDate(new Date());
		return industryMapper.insert(industry);
	}

	/**
	 * 更新行业信息
	 * 
	 * @param industryVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int updateIndustry(IndustryVO industryVO) throws QmdException {
		Industry industry = CopyBeanUtil.copyBean(industryVO, Industry.class);
		industry.setUpdateDate(new Date());
		if(industryVO.getLevel()==null) {
			Industry industryById = industryMapper.selectById(industryVO.getId()) ;
			industryVO.setParentId(industryById.getParentId());;
		}
		// 根据行业名称查询
		IndustryVO industryVOByName = industryMapper.getIndustryVOByName(industryVO.getName(),industryVO.getParentId());
		//判断修改后的行业名是否与其他条记录的行业名相同
		if (industryVOByName != null && !industryVOByName.getId().equals(industryVO.getId())) {
			throw new QmdException("同一分类下不能存在相同的子分类");
		}
		return industryMapper.updateById(industry);
//	 

	}

	/**
	 * 查询所有行业信息
	 */
	@Override
	public List<GetAllIndustryVO> getAllIndustry() {
		//以及行业信息
		List<IndustryVO> levelOneList = industryMapper.getIndustryByLevelAndParentId(1, null);
		//getLevelTwoIndustry 参数为null时查询所有二级分类
		List<IndustryVO> levelTwoList = industryMapper.getIndustryByLevelAndParentId(2,null);
		//getLevelThreeIndustry 参数为null时查询所有三级分类
		List<IndustryVO> levelThreeList = industryMapper.getIndustryByLevelAndParentId(3,null);
		List<GetAllIndustryVO> getAllIndustryVOList = new ArrayList<GetAllIndustryVO>();
		if(levelOneList.size()>0) {	
			for(IndustryVO levelOneIndustryVO:levelOneList) {
				
				//组装二级三级组装存放在List<Object> getLevelTwoAndThreeIndustryVOList
				List<Object> getLevelTwoAndThreeIndustryVOList = new ArrayList<Object>();
				if(levelTwoList.size()>0) {	
					for(IndustryVO levelTwoIndustryVO:levelTwoList) {
						if(levelTwoIndustryVO.getParentId().equals(levelOneIndustryVO.getId())) {
							List<Object> industryList = new ArrayList<Object>();
							GetAllIndustryVO getLevelTwoAndThreeIndustryVO  = CopyBeanUtil.copyBean(levelTwoIndustryVO, GetAllIndustryVO.class);
							//遍历三级分类,将三级行业分类组装在list里
							if(levelThreeList.size()>0) {
								for(IndustryVO levelThreeIndustryVO:levelThreeList) {
									if(levelThreeIndustryVO.getParentId().equals(levelTwoIndustryVO.getId())) {
										industryList.add(levelThreeIndustryVO);
									}	
								}
							}
							getLevelTwoAndThreeIndustryVO.setObjectList(industryList);
							getLevelTwoAndThreeIndustryVOList.add(getLevelTwoAndThreeIndustryVO);
						}		
					}
				}	
				GetAllIndustryVO allIndustryVO = CopyBeanUtil.copyBean(levelOneIndustryVO, GetAllIndustryVO.class);
				allIndustryVO.setObjectList(getLevelTwoAndThreeIndustryVOList);
				getAllIndustryVOList.add(allIndustryVO);
			}	
		}else {
			return null;
		}		
		return getAllIndustryVOList;
	}

	/**
	 * 删除行业信息（逻辑删除）
	 * 
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = { QmdException.class, Exception.class })
	public int deleteIndustry(Long id) throws QmdException {
		Industry industry = new Industry();
		industry.setId(id);
		industry.setStatus(StatusEnum.deleted.getCode());
		int i = industryMapper.updateById(industry);
		return i;
	}

	/**
	 * 根据主键查询行业信息
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public IndustryVO getIndustryVOById(Long id) {
		Industry industry = industryMapper.selectById(id);
		if (industry == null) {
			return null;
		}
		IndustryVO industryVO = CopyBeanUtil.copyBean(industry, IndustryVO.class);
		return industryVO;
	}
	
	/**
	 * 根父级Id查询行业信息
	 * @return
	 */
	@Override
	public List<IndustryVO> getIndustryByLevelAndParentId( Long parentId){
		//如果父级id为空默认查一级分类
		if(parentId==null) {
			parentId= new Long(0);
		}
		return industryMapper.getIndustryByLevelAndParentId(null, parentId);
		
	}


	

}
