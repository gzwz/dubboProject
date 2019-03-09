package cn.qumiandan.complain.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.complain.api.IComplainService;
import cn.qumiandan.complain.entity.Complain;
import cn.qumiandan.complain.mapper.ComplainMapper;
import cn.qumiandan.complain.vo.ComplainVO;
import cn.qumiandan.complain.vo.QueryComplainVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@Service(interfaceClass = IComplainService.class)
public class ComplainServiceImpl implements IComplainService{
	
	@Autowired
	private ComplainMapper complainMapper;
	@Override
	public void addComplain(ComplainVO complainVO) {
		AssertUtil.isNull(complainVO, "添加投诉信息参数为空");
		Complain complain = CopyBeanUtil.copyBean(complainVO, Complain.class);
		int i = complainMapper.insert(complain);
		if(i != 1 ) {
			log.error("addComplain|添加投诉信息失败；受影响的行数不为1；受影响的行数:"+i);
			throw new QmdException("添加投诉信息失败");
		}
	}

	@Override
	public void updateComplain(ComplainVO complainVO) {
		AssertUtil.isNull(complainVO, "更新投诉信息参数为空");
		AssertUtil.isNull(complainVO.getId(), "更新投诉信息id为空");
		Complain complain = complainMapper.selectById(complainVO.getId());
		if(complain == null) {
			log.error("updateComplain|该条投诉信息不存在;i:"+complainVO.getId());
			throw new QmdException("该条投诉信息不存在");
		}
		complain = CopyBeanUtil.copyBean(complainVO, Complain.class);
		int i = complainMapper.updateById(complain);
		if(i != 1 ) {
			log.error("updateComplain|修改投诉信息失败；受影响的行数不为1；受影响的行数:"+i);
			throw new QmdException("修改投诉信息失败");
		}
	}

	@Override
	public ComplainVO getComplainById(Long id) {
		AssertUtil.isNull(id, "投诉信息id为空");
		Complain complain = complainMapper.selectById(id);
		if(complain == null) {
			return null;
		}
		return CopyBeanUtil.copyBean(complain, ComplainVO.class);
	}

	/**
	 * 总后台查询投诉信息
	 */
	@Override
	public PageInfo<ComplainVO> queryComplain(QueryComplainVO paramsVO) {
		Page<ComplainVO> page = PageHelper.startPage(paramsVO.getPageNum(), paramsVO.getPageSize());
		List<ComplainVO> complains = complainMapper.queryComplain(paramsVO);
		@SuppressWarnings("unchecked")
		PageInfo<ComplainVO> info = CopyBeanUtil.copyBean(page, PageInfo.class);
		if(! ObjectUtils.isEmpty(complains)) {
			List<ComplainVO> complainVOs = CopyBeanUtil.copyList(complains, ComplainVO.class);
			info.setList(complainVOs);
		}
		return info;
	}

}
