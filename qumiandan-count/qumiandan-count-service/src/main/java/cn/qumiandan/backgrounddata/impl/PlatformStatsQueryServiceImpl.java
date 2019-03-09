package cn.qumiandan.backgrounddata.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;

import cn.qumiandan.backgrounddata.api.IPlatformStatsQueryService;
import cn.qumiandan.backgrounddata.mapper.PlatformStatsMapper;
import cn.qumiandan.backgrounddata.vo.StatVO;
import cn.qumiandan.shop.entity.Shop;
import cn.qumiandan.shop.service.IShopService;
import cn.qumiandan.utils.DateUtil;

@Component
@Service(interfaceClass = IPlatformStatsQueryService.class)
public class PlatformStatsQueryServiceImpl implements IPlatformStatsQueryService {

	@Autowired
	private PlatformStatsMapper mapper;
	
	@Autowired
	private IShopService shopService;
	
	private Date getYesterdayStartTime() {
		Date date = DateUtil.getDate(-1);
		Date dayBeginTime = DateUtil.getDayBeginTime(date);
		return dayBeginTime;
	}
	private Date getYesterdayEndTime() {
		Date date = DateUtil.getDate(0);
		Date dayBeginTime = DateUtil.getDayBeginTime(date);
		return dayBeginTime;
	}
	
	@Override
	public List<StatVO> queryReceivedAmount() {
		List<StatVO> queryReceivedAmount = mapper.queryReceivedAmount(getYesterdayStartTime(), getYesterdayEndTime());
 		return queryReceivedAmount;
	}

	@Override
	public List<StatVO> queryMerchantCost() {
		return mapper.queryMerchantCost(getYesterdayStartTime(), getYesterdayEndTime());
	}
	
	@Override
	public List<StatVO> queryMerchantProfit() {
		List<StatVO> amountList = mapper.queryReceivedAmount(getYesterdayStartTime(), getYesterdayEndTime());
		List<StatVO> costList = mapper.queryMerchantCost(getYesterdayStartTime(), getYesterdayEndTime());
		List<StatVO> resultList = Lists.newArrayListWithExpectedSize(amountList.size());
		if (!CollectionUtils.isEmpty(amountList)) {
			StatVO vo = null;
			for (StatVO amount : amountList) {
				if (Objects.nonNull(amount)) {
					for (StatVO cost : costList) {
						if (Objects.nonNull(cost) && amount.getShopId().equals(cost.getShopId())) {
							if (Objects.nonNull(amount.getReceivedAmount()) && Objects.nonNull(cost.getCostAmount())) {
								vo = new StatVO();
								vo.setShopId(cost.getShopId());
								vo.setMerchantProfit(amount.getReceivedAmount().subtract(cost.getCostAmount()));
								resultList.add(vo);
							} else if (Objects.isNull(amount.getReceivedAmount()) && Objects.nonNull(cost.getCostAmount())) {
								vo = new StatVO();
								vo.setShopId(cost.getShopId());
								vo.setMerchantProfit(cost.getCostAmount().negate());
								resultList.add(vo);
							} else if (Objects.nonNull(amount.getReceivedAmount()) && Objects.isNull(cost.getCostAmount())) {
								vo = new StatVO();
								vo.setShopId(cost.getShopId());
								vo.setMerchantProfit(amount.getReceivedAmount());
								resultList.add(vo);
							}
						}
					}
				}
			}
		}
		return resultList;
	}

	@Override
	public List<StatVO> queryPlatformProfit() {
		return mapper.queryPlatformProfit(getYesterdayStartTime(), getYesterdayEndTime());
	}

	@Override
	public List<StatVO> queryServiceFee() {
		return mapper.queryServiceFee(getYesterdayStartTime(), getYesterdayEndTime());
	}

	@Override
	public List<StatVO> queryGameAmount() {
		return mapper.queryGameAmount(getYesterdayStartTime(), getYesterdayEndTime());
	}

	@Override
	public List<StatVO> queryGameWinAmount() {
		return mapper.queryGameWinAmount(getYesterdayStartTime(), getYesterdayEndTime());
	}

	@Override
	public List<StatVO> queryWithdrawAmount() {
		return mapper.queryGameWithdrawAmount(getYesterdayStartTime(), getYesterdayEndTime());
	}
	
	@Override
	public List<StatVO> getAllStats() {
		List<Shop> shopList = shopService.getAllShop();
		List<StatVO> reusltList = Lists.newArrayListWithExpectedSize(shopList.size());
		if (!CollectionUtils.isEmpty(shopList)) {
			Date now = new Date();
			Date start = getYesterdayStartTime();
			Date end = getYesterdayEndTime();
			List<StatVO> receivedAmountList = mapper.queryReceivedAmount(start, end);
			List<StatVO> costList = mapper.queryMerchantCost(start, end);
			List<StatVO> merchantProfitList = Lists.newArrayListWithExpectedSize(receivedAmountList.size());
			if (!CollectionUtils.isEmpty(receivedAmountList)) {
				StatVO vo = null;
				for (StatVO amount : receivedAmountList) {
					if (Objects.nonNull(amount)) {
						for (StatVO cost : costList) {
							if (Objects.nonNull(cost) && amount.getShopId().equals(cost.getShopId())) {
								if (Objects.nonNull(amount.getReceivedAmount()) && Objects.nonNull(cost.getCostAmount())) {
									vo = new StatVO();
									vo.setShopId(cost.getShopId());
									vo.setMerchantProfit(amount.getReceivedAmount().subtract(cost.getCostAmount()));
									merchantProfitList.add(vo);
								} else if (Objects.isNull(amount.getReceivedAmount()) && Objects.nonNull(cost.getCostAmount())) {
									vo = new StatVO();
									vo.setShopId(cost.getShopId());
									vo.setMerchantProfit(cost.getCostAmount().negate());
									merchantProfitList.add(vo);
								} else if (Objects.nonNull(amount.getReceivedAmount()) && Objects.isNull(cost.getCostAmount())) {
									vo = new StatVO();
									vo.setShopId(cost.getShopId());
									vo.setMerchantProfit(amount.getReceivedAmount());
									merchantProfitList.add(vo);
								}
							}
						}
					}
				}
			}
			List<StatVO> platformProfitList = mapper.queryPlatformProfit(start, end);
			List<StatVO> serviceFeeList = mapper.queryServiceFee(start, end);
			List<StatVO> gameAmountList = mapper.queryGameAmount(start, end);
			List<StatVO> gameWinAmountList = mapper.queryGameWinAmount(start, end);
			List<StatVO> gameWithdrawAmountList = mapper.queryGameWithdrawAmount(start, end);
			
			StatVO reuslt = null;
			for (Shop shop : shopList) {
				if (Objects.nonNull(shop)) {
					reuslt = new StatVO();
					reuslt.setShopId(shop.getId());
					reuslt.setProCode(shop.getProCode());
					reuslt.setCityCode(shop.getCityCode());
					reuslt.setCountyCdoe(shop.getCountyCode());
					reuslt.setSalemanId(shop.getSalemanId());
					
					if (!CollectionUtils.isEmpty(receivedAmountList)) {
						for (StatVO receivedAmount : receivedAmountList) {
							if (Objects.nonNull(receivedAmount) && shop.getId().equals(receivedAmount.getShopId())) {
								reuslt.setReceivedAmount(receivedAmount.getReceivedAmount());
							}
						}
					}
					
					if (!CollectionUtils.isEmpty(merchantProfitList)) {
						for (StatVO merchantProfit : merchantProfitList) {
							if (Objects.nonNull(merchantProfit) && shop.getId().equals(merchantProfit.getShopId())) {
								reuslt.setMerchantProfit(merchantProfit.getMerchantProfit());
							}
						}
					}
					
					if (!CollectionUtils.isEmpty(platformProfitList)) {
						for (StatVO platformProfit : platformProfitList) {
							if (Objects.nonNull(platformProfit) && shop.getId().equals(platformProfit.getShopId())) {
								reuslt.setPlatformProfit(platformProfit.getPlatformProfit());
							}
						}
					}
					
					if (!CollectionUtils.isEmpty(serviceFeeList)) {
						for (StatVO serviceFee : serviceFeeList) {
							if (Objects.nonNull(serviceFee)) {
								reuslt.setServiceFee(serviceFee.getServiceFee());
							}
						}
					}
					
					if (!CollectionUtils.isEmpty(gameAmountList)) {
						for (StatVO gameAmount : gameAmountList) {
							if (Objects.nonNull(gameAmount) && shop.getId().equals(gameAmount.getShopId())) {
								reuslt.setGameAmount(gameAmount.getGameAmount());
							}
						}
					}
					
					if (!CollectionUtils.isEmpty(gameWinAmountList)) {
						for (StatVO gameWinAmount : gameWinAmountList) {
							if (Objects.nonNull(gameWinAmount) && shop.getId().equals(gameWinAmount.getShopId())) {
								reuslt.setGameWinAmount(gameWinAmount.getGameWinAmount());
							}
						}
					}
					
					if (!CollectionUtils.isEmpty(gameWithdrawAmountList)) {
						for (StatVO gameWithdrawAmount : gameWithdrawAmountList) {
							if (Objects.nonNull(gameWithdrawAmount) && shop.getId().equals(gameWithdrawAmount.getShopId())) {
								reuslt.setWithdrawAmount(gameWithdrawAmount.getWithdrawAmount());
							}
						}
					}
					
					if (!CollectionUtils.isEmpty(costList)) {
						for (StatVO cost : costList) {
							if (Objects.nonNull(cost) && shop.getId().equals(cost.getShopId())) {
								reuslt.setCostAmount(cost.getCostAmount());
							}
						}
					}
					reuslt.setCreateDate(now);
					reuslt.setCountDate(start);
					reusltList.add(reuslt);
				}
			}
		}
		return reusltList;
	}
}
