package cn.qumiandan.payaccount.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.payaccount.api.IBankCardService;
import cn.qumiandan.payaccount.api.IPayAccountService;
import cn.qumiandan.payaccount.entity.BankCard;
import cn.qumiandan.payaccount.mapper.BankCardMapper;
import cn.qumiandan.payaccount.vo.BankCardVO;
import cn.qumiandan.payaccount.vo.PayAccountVO;
import cn.qumiandan.utils.AssertUtil;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service(interfaceClass = IBankCardService.class)
public class BankCardServiceImpl implements IBankCardService {

	@Autowired
	private BankCardMapper bankCardMapper;
	@Autowired
	private IPayAccountService payAccountService ;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BankCardVO addBankCard(BankCardVO vo) {
		AssertUtil.isNull(vo, "BankCardServiceImpl|addBankCard|传入参数vo为空");
		List<BankCard> list = bankCardMapper.selectList(new QueryWrapper<BankCard>().eq("account_id", vo.getAccountId()));
		// 将以前的卡信息全部置为删除
		if (!ObjectUtils.isEmpty(list)) {
			list.forEach(bc -> {
				bc.setStatus(StatusEnum.deleted.getCode());
				bankCardMapper.update(bc, new UpdateWrapper<BankCard>());
			});
		}
		BankCard entity = CopyBeanUtil.copyBean(vo, BankCard.class);
		if (!checkCUD(bankCardMapper.insert(entity))) {
			log.info("创建银行卡信息失败|bankCardNo:" + vo.getBankCardNo());
			throw new QmdException("创建银行卡信息失败");
		}
		vo.setId(entity.getId());
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateBankCard(BankCardVO vo) {
		AssertUtil.isNull(vo, "BankCardServiceImpl|updateBankCard|传入参数vo为空");
		BankCard entity = CopyBeanUtil.copyBean(vo, BankCard.class);
		if (!checkCUD(bankCardMapper.updateById(entity))) {
			log.info("更新银行卡信息失败|id" + vo.getId() + "|bankCardNo:" + vo.getBankCardNo());
			throw new QmdException("更新银行卡信息失败");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBankCardByAccountId(Long accountId, Long operateId) {
		AssertUtil.isNull(accountId, "BankCardServiceImpl|deleteBankCardByAccountId|传入参数accountId为空");
		AssertUtil.isNull(operateId, "BankCardServiceImpl|deleteBankCardByAccountId|传入参数operateId为空");
		int ret = bankCardMapper.delete(new QueryWrapper<BankCard>().eq("account_id", accountId));
		if (ret == 0) {
			log.info("删除绑定银行卡信息失败|operateId" + operateId + "|accountId:" + accountId);
			throw new QmdException("更新银行卡信息失败");
		}
	}
	
	@Override
	public BankCardVO getBankCardByAccountId(Long accountId) {
		AssertUtil.isNull(accountId, "BankCardServiceImpl|getBankCardByAccountId|传入参数accountId为空");
		 BankCard bankCard = bankCardMapper.selectOne(new QueryWrapper<BankCard>().eq("account_id", accountId)
				 .eq("status", StatusEnum.normal.getCode()));
		if (Objects.nonNull(bankCard)) {
			return CopyBeanUtil.copyBean(bankCard, BankCardVO.class);
		}
		return null;
	}

	/**
	 * 根据店铺id查询银行卡信息
	 */
	@Override
	public BankCardVO getBankCardByShopId(Long shopId) {
		PayAccountVO payAccountByShopId = payAccountService.getPayAccountByShopId(shopId);
		if(payAccountByShopId == null) {
			throw new QmdException("该店铺的账户不存在");
		}
		BankCard bankCard = bankCardMapper.selectOne(new QueryWrapper<BankCard>().eq("account_id", payAccountByShopId.getId())
				.eq("status", StatusEnum.normal.getCode()));
		if(bankCard == null) {
			return null;
		}
		BankCardVO bankCardVO = CopyBeanUtil.copyBean(bankCard, BankCardVO.class);
		return bankCardVO;
	}
}
