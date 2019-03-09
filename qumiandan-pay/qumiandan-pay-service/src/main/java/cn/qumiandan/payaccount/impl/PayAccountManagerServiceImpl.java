package cn.qumiandan.payaccount.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.payaccount.api.IPayAccountManagerService;
import cn.qumiandan.payaccount.entity.PayAccountManager;
import cn.qumiandan.payaccount.mapper.PayAccountManagerMapper;
import cn.qumiandan.payaccount.vo.PayAccountManagerVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = IPayAccountManagerService.class)
public class PayAccountManagerServiceImpl implements IPayAccountManagerService {

	@Autowired
	private PayAccountManagerMapper payAccountManagerMapper;
	
	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public PayAccountManagerVO addPayAccountManager(PayAccountManagerVO vo) {
		AssertUtil.isNull(vo, "PayAccountManagerServiceImpl|addBankCard|传入参数vo为空");
		PayAccountManager entity = CopyBeanUtil.copyBean(vo, PayAccountManager.class);
		if (!checkCUD(payAccountManagerMapper.insert(entity))) {
			log.info("创建支付账户管理关联信息失败|userId:" + vo.getUserId() + "|accountId:" + vo.getAccountId());
			throw new QmdException("创建支付账户管理关联信息失败");
		}
		vo.setId(entity.getId());
		return vo;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public int updatePayAccountManager(PayAccountManagerVO vo) {
		AssertUtil.isNull(vo, "PayAccountManagerServiceImpl|updatePayAccountManager|传入参数vo为空");
		PayAccountManager entity = CopyBeanUtil.copyBean(vo, PayAccountManager.class);
		if (!checkCUD(payAccountManagerMapper.updateById(entity))) {
			log.info("更新支付账户管理关联信息失败|userId:" + vo.getUserId() + "|accountId:" + vo.getAccountId());
			throw new QmdException("更新支付账户管理关联信息失败");
		}
		return 0;
	}
	
	
	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public void deletePayAccountManager(Long id, Long operateId) {
		AssertUtil.isNull(id, "PayAccountManagerServiceImpl|deletePayAccountManager|传入参数id为空");
		AssertUtil.isNull(operateId, "PayAccountManagerServiceImpl|deletePayAccountManager|传入参数operateId为空");
		if (!checkCUD(payAccountManagerMapper.deleteById(id))) {
			log.info("删除支付账户管理关联信息失败|operateId:" + operateId + "|id:" + id);
			throw new QmdException("删除支付账户管理关联信息失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public void deletePayAccountManager(Long userId, Long accountId, Long operateId) {
		AssertUtil.isNull(userId, "PayAccountManagerServiceImpl|deletePayAccountManager|传入参数userId为空");
		AssertUtil.isNull(accountId, "PayAccountManagerServiceImpl|deletePayAccountManager|传入参数accountId为空");
		AssertUtil.isNull(operateId, "PayAccountManagerServiceImpl|deletePayAccountManager|传入参数operateId为空");
		int ret = payAccountManagerMapper.delete(new QueryWrapper<PayAccountManager>().eq("user_id", userId)
				.eq("account_id", accountId));
		if (!checkCUD(ret)) {
			log.info("删除支付账户管理关联信息失败|operateId:" + operateId + "|userId:" + userId + "|accountId:" + accountId);
			throw new QmdException("删除支付账户管理关联信息失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class, QmdException.class})
	public void deletePayAccountManagerByAccountId(Long accountId, Long operateId) {
		AssertUtil.isNull(accountId, "PayAccountManagerServiceImpl|deletePayAccountManager|传入参数accountId为空");
		AssertUtil.isNull(operateId, "PayAccountManagerServiceImpl|deletePayAccountManager|传入参数operateId为空");
		int ret = payAccountManagerMapper.delete(new QueryWrapper<PayAccountManager>().eq("account_id", accountId));
		if (!checkCUD(ret)) {
			log.info("删除支付账户管理关联信息失败|operateId:" + operateId + "|accountId:" + accountId);
			throw new QmdException("删除支付账户管理关联信息失败");
		}
	}
}
