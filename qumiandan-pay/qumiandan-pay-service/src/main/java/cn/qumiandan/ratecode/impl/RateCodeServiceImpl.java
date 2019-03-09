package cn.qumiandan.ratecode.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.ratecode.api.IRateCodeService;
import cn.qumiandan.ratecode.entity.RateCode;
import cn.qumiandan.ratecode.mapper.RateCodeMapper;
import cn.qumiandan.ratecode.vo.RateCodeVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;

@Component
@Service(interfaceClass = IRateCodeService.class)
public class RateCodeServiceImpl implements IRateCodeService {

	@Autowired
	private RateCodeMapper rateCodeMapper;

	/**
	 * 根据code查询费率信息
	 */
	@Override
	public RateCodeVO getRateCode(String code) {
		RateCode rateCode = rateCodeMapper.selectOne(new QueryWrapper<RateCode>().eq("code",code));
		if (rateCode == null) {
			return null;
		}
		RateCodeVO rateCodeVO = CopyBeanUtil.copyBean(rateCode, RateCodeVO.class);
		return rateCodeVO;
	}

	/**
	 * 获取所有费率信息
	 */
	@Override
	public List<RateCodeVO> getAllRateCode(List<Byte> typeList) {
		QueryWrapper<RateCode> queryWrapper = new QueryWrapper<>();
		if(ObjectUtils.isEmpty(typeList)) {
			queryWrapper.in("type", typeList);
		}
		queryWrapper.eq("status", StatusEnum.normal.getCode());
		List<RateCode> list = rateCodeMapper.selectList(queryWrapper);
		if (ObjectUtils.isEmpty(list)) {
			return null;
		}
		List<RateCodeVO> rateCodeVOList = CopyBeanUtil.copyList(list, RateCodeVO.class);
		return rateCodeVOList;
	}

	/**
	 * 添加费率信息
	 */
	@Override
	public int addRateCode(RateCodeVO rateCodeVO) {
		RateCode rateCodeByCode = rateCodeMapper.selectOne(new QueryWrapper<RateCode>().eq("code",rateCodeVO.getCode()).eq("status", StatusEnum.normal.getCode()));
		RateCode rateCodeByRate = rateCodeMapper
				.selectOne(new QueryWrapper<RateCode>().eq("type",rateCodeVO.getType()).eq("rate", rateCodeVO.getRate()).eq("status", StatusEnum.normal.getCode()));
		if (rateCodeByCode != null) {
			throw new QmdException("费率码不能重复");
		}
		if (rateCodeByRate != null) {
			throw new QmdException("同类型费率信息的费率不能重复");
		}
		RateCode record = CopyBeanUtil.copyBean(rateCodeVO, RateCode.class);
		record.setCreateDate(new Date());
		return rateCodeMapper.insert(record);
	}

	/**
	 * 修改费率信息
	 */
	@Override
	public int updateRateCode(RateCodeVO rateCodeVO) {
		RateCode rateCodeByCode = rateCodeMapper.selectOne(new QueryWrapper<RateCode>().eq("code",rateCodeVO.getCode()).eq("status", StatusEnum.normal.getCode()));
		RateCode rateCodeByRate = rateCodeMapper
				.selectOne(new QueryWrapper<RateCode>().eq("type",rateCodeVO.getType()).eq("rate", rateCodeVO.getRate()).eq("status", StatusEnum.normal.getCode()));
		if (rateCodeByCode != null && !rateCodeByCode.getId().equals(rateCodeVO.getId())) {
			throw new QmdException("费率码不能重复");
		}
		if (rateCodeByRate != null && !rateCodeByRate.getId().equals(rateCodeVO.getId())) {
			throw new QmdException("同类型费率信息的费率不能重复");
		}
		RateCode record = CopyBeanUtil.copyBean(rateCodeVO, RateCode.class);
		record.setUpdateDate(new Date());
		return rateCodeMapper.updateById(record);
	}

	/**
	 * 删除费率信息
	 */
	@Override
	public int deleteRateCode(Long id) {
		RateCode rateCode = new RateCode();
		rateCode.setId(id);
		rateCode.setStatus(StatusEnum.deleted.getCode());
		return rateCodeMapper.updateById(rateCode);
	}

}
