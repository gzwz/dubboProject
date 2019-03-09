package cn.qumiandan.payaccount.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;

import cn.qumiandan.common.exception.PayErrorCode;
import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.payaccount.api.IPayAccountScheduleService;
import cn.qumiandan.payaccount.mapper.PayAccountMapper;

@Component
@Service(interfaceClass = IPayAccountScheduleService.class)
public class PayAccountScheduleServiceImpl implements IPayAccountScheduleService {

	@Autowired
	private PayAccountMapper payAccountMapper;
	
	
	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public void batchIncreaseSettBalanceScheduled() {
		Integer ret = payAccountMapper.updateBatchIncreaseSettBalance();
		if (ret == 0) {
			throw new QmdException(PayErrorCode.PAY7000);
		}
	}
}
