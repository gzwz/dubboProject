package cn.qumiandan.supportpayment.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.supportpayment.api.ISupportPaymentService;
import cn.qumiandan.supportpayment.entity.SupportPayment;
import cn.qumiandan.supportpayment.mapper.SupportPaymentMapper;
import cn.qumiandan.supportpayment.vo.SupportPaymentVO;
import cn.qumiandan.utils.CopyBeanUtil;
/**
 * 扫呗支付方式管理实现类
 * @author lrj
 *
 */
@Component
@Service(interfaceClass = ISupportPaymentService.class)
public class SupportPaymentServiceImpl implements ISupportPaymentService{

	@Autowired
	private SupportPaymentMapper supportPaymentMapper;
	
	/**
	 * 查询扫呗所有支付方式
	 * @return
	 */
	@Override
	public List<SupportPaymentVO> getAllSupportPayment() {
		QueryWrapper<SupportPayment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("status", StatusEnum.normal.getCode());
		List<SupportPayment> supportPaymentList = supportPaymentMapper.selectList(queryWrapper);
		if(supportPaymentList.size()<=0) {
			return null;
		}	
		List<SupportPaymentVO> supportPaymentVOList = CopyBeanUtil.copyList(supportPaymentList, SupportPaymentVO.class);
		return supportPaymentVOList;
	}

	/**
	 * 根据code查询支付方式详情
	 * @param code
	 * @return
	 */
	@Override
	public List<SupportPaymentVO> getSupportPaymentByCode(String codes) {
		List<String> list = Arrays.asList(codes.split(","));
		QueryWrapper<SupportPayment> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("code", list); //sql中in
		List<SupportPayment> supportPaymentList = supportPaymentMapper.selectList(queryWrapper);
		if(supportPaymentList .size()<=0) {
			return null;
		}
		List<SupportPaymentVO> supportPaymentVOList = CopyBeanUtil.copyList(supportPaymentList, SupportPaymentVO.class);
		return supportPaymentVOList;
	}

}
