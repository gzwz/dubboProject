package cn.qumiandan.maintain.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.maintain.api.IMaintainRecordService;
import cn.qumiandan.maintain.api.IMaintainTypeService;
import cn.qumiandan.maintain.entity.MaintainRecord;
import cn.qumiandan.maintain.mapper.MaintainRecordMapper;
import cn.qumiandan.maintain.vo.MaintainRecordVO;
import cn.qumiandan.maintain.vo.MaintainTypeVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 功能维护管理实现类
 * 
 * @author lrj
 *
 */
@Slf4j
@Component
@Service(interfaceClass = IMaintainRecordService.class)
public class MaintainRecordServiceImpl implements IMaintainRecordService {

	@Autowired
	private MaintainRecordMapper maintainRecordMapper;
	@Autowired
	private IMaintainTypeService maintainTypeService;

	/**
	 * 添加维护记录
	 * 
	 * @param MaintainRecordVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = {QmdException.class, Exception.class})
	public MaintainRecordVO addMaintainRecord(MaintainRecordVO maintainRecordVO) {
		AssertUtil.isNull(maintainRecordVO, "维护记录参数为空");
		//当当前维护类型存在正常维护的维护记录，则将其状态改为失效
		maintainRecordMapper.update(new MaintainRecord(), new UpdateWrapper<MaintainRecord>()
				.eq("maintain_type_id", maintainRecordVO.getMaintainTypeId()).set("status", StatusEnum.Invalid.getCode()));
		MaintainRecord maintainRecord = CopyBeanUtil.copyBean(maintainRecordVO, MaintainRecord.class);
		maintainRecord.setCreateDate(new Date());
		maintainRecord.setStatus(StatusEnum.normal.getCode());
		int i = maintainRecordMapper.insert(maintainRecord);
		if( i != 1) {
			log.error("addMaintainRecord|添加维护记录失败；受影响的行数不为1；受影响的行数:"+i);
			throw new QmdException("添加维护记录失败");
		}
		maintainRecordVO.setId(maintainRecord.getId());
		return maintainRecordVO;
	}

	/**
	 * 修改维护记录
	 */
	@Override
	public void updateMaintainRecord(MaintainRecordVO maintainRecordVO) {
		AssertUtil.isNull(maintainRecordVO, "维护记录为空");
		MaintainRecord maintainRecord = CopyBeanUtil.copyBean(maintainRecordVO, MaintainRecord.class);
		maintainRecord.setUpdateDate(new Date());
		int i = maintainRecordMapper.updateById(maintainRecord);
		if( i != 1) {
			log.error("updateMaintainRecord|修改维护记录失败；受影响的行数不为1；受影响的行数:"+i);
			throw new QmdException("修改维护记录失败");
		}
	}

	@Override
	public void deleteMaintainRecord(Long id) {
		AssertUtil.isNull(id, "维护记录id为空");
		MaintainRecord maintainRecord = new MaintainRecord();
		maintainRecord.setId(id);
		maintainRecord.setStatus(StatusEnum.deleted.getCode());
		int i = maintainRecordMapper.updateById(maintainRecord);
		if( i != 1) {
			log.error("updateMaintainRecord|删除维护记录失败；受影响的行数不为1；受影响的行数:"+i+";id:"+id);
			throw new QmdException("删除维护记录失败");
		}
	}

	/**
	 * 根据主键查询维护记录
	 * @param id
	 * @return
	 */
	@Override
	public MaintainRecordVO getMaintainRecordById(Long id) {
		MaintainRecord maintainRecord = maintainRecordMapper.selectById(id);
		if(maintainRecord == null) {
			return null;
		}
		return CopyBeanUtil.copyBean(maintainRecord, MaintainRecordVO.class);
	}

	/**
	 * 查询所有维护记录
	 */
	@Override
	public PageInfo<MaintainRecordVO> queryMaintainRecord(Long maintainTypeId, int pageNum,int pageSize){
		QueryWrapper<MaintainRecord> queryWrapper = new QueryWrapper<>();
		if(maintainTypeId != null) {
			queryWrapper.eq("maintain_type_id", maintainTypeId);
		}
		queryWrapper.in("status", Lists.newArrayList(StatusEnum.normal.getCode(),StatusEnum.Invalid.getCode()));
		queryWrapper.orderByDesc("create_date");
		Page<MaintainRecord> page = PageHelper.startPage(pageNum, pageSize);
		List<MaintainRecord> maintainRecords = maintainRecordMapper.selectList(queryWrapper);
		if(ObjectUtils.isEmpty(maintainRecords)) {
			return null;
		}
		List<MaintainRecordVO> copyList = CopyBeanUtil.copyList(maintainRecords, MaintainRecordVO.class);
		@SuppressWarnings("unchecked")
		PageInfo<MaintainRecordVO> info = CopyBeanUtil.copyBean(page, PageInfo.class);
		info.setList(copyList);
		return info;
	}

	/**
	 * 根据类型查询维护记录
	 */
	@Override
	public MaintainRecordVO getMaintainRecordByType(String type) {
		MaintainTypeVO MaintainTypeByType = maintainTypeService.getMaintainTypeByType(type);
		if(MaintainTypeByType == null) {
			return null;
		}
		Date now = new Date();
		MaintainRecord maintainRecord = maintainRecordMapper.selectOne(new QueryWrapper<MaintainRecord>()
				.eq("maintain_type_id", MaintainTypeByType.getId())
				.eq("status", StatusEnum.normal.getCode())
				.gt("end_date", now)
				.lt("start_date", now));
		if(maintainRecord == null) {
			return null;
		}
		return CopyBeanUtil.copyBean(maintainRecord, MaintainRecordVO.class);
	}

}
