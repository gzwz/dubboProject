package test.coupon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

import cn.qumiandan.coupon.api.IConponRulesService;
import cn.qumiandan.coupon.api.ICouponService;
import cn.qumiandan.coupon.api.ICouponTempleteService;
import cn.qumiandan.coupon.api.ICouponUseRecordService;
import cn.qumiandan.coupon.constant.CouponEnum;
import cn.qumiandan.coupon.constant.CouponRulesTypeEnum;
import cn.qumiandan.coupon.constant.CouponUseRulesEnum;
import cn.qumiandan.coupon.mapper.CouponTakeRecordMapper;
import cn.qumiandan.coupon.vo.CouponRulesVO;
import cn.qumiandan.coupon.vo.CouponTakeRecordVO;
import cn.qumiandan.coupon.vo.CouponTempleteVO;
import cn.qumiandan.coupon.vo.CouponUseRecordVO;
import cn.qumiandan.coupon.vo.CouponVO;
import cn.qumiandan.coupon.vo.GetCouponResponseParamsVO;
import cn.qumiandan.coupon.vo.QueryCouponParamsVO;
import cn.qumiandan.coupon.vo.QueryCouponTakeRecordParamsVO;
import cn.qumiandan.coupon.vo.QueryUsefulCouponParamsVO;
import test.BaseTest;

public class CouponTest extends BaseTest {
	
	@Autowired
	public ICouponService service;
	
	@Autowired
	public ICouponTempleteService service1;
	
	@Autowired
	public IConponRulesService service2;
	
	@Autowired
	private CouponTakeRecordMapper mapper;
	
	@Autowired
	private ICouponUseRecordService couponUseRecordService;
	
	@Test
	public void getCouponUseRecordByOrderId() {
		CouponUseRecordVO couponUseRecordByOrderId = couponUseRecordService.getCouponUseRecordByOrderId("2018123113652125326245888");
		System.out.println(couponUseRecordByOrderId);
	}
	
	@Test
	public void queryUsefulCoupon() {
		System.out.println("=======queryUsefulCoupon======");
		QueryUsefulCouponParamsVO couponParamsVO = new QueryUsefulCouponParamsVO();
		couponParamsVO.setPageNum(1);
		couponParamsVO.setPageSize(10);
		couponParamsVO.setUserId(11L);
		couponParamsVO.setDate(new Date());
		couponParamsVO.setPublisherList(Lists.newArrayList(10L,0L));
		PageInfo<CouponVO> queryUsefulCoupon = service.queryUsefulCoupon(couponParamsVO);
		System.out.println(queryUsefulCoupon);
	}
	
	@Test
	public void test() {
		QueryCouponTakeRecordParamsVO paramsVO = new QueryCouponTakeRecordParamsVO();
		paramsVO.setStatusList(Lists.newArrayList(new Byte("1")));
		List<CouponTakeRecordVO> test = mapper.test(paramsVO);
		System.out.println(test);
	}
	
	@Test
	public void takeCoupon() {
		System.out.println("------takeCouponForUser-----");
 		CouponTakeRecordVO vo = service.takeCouponForUser(11L, 1L);
		System.out.println(vo);
	}
	@Test
	public void queryCoupon() {
		List<CouponVO> vos = service.queryCouponForShop(2L);
		System.out.println(vos);
	}
	@Test
	public void createCoupon() {
		CouponVO vo = new CouponVO();
		vo.setTempleteId(1L);
		vo.setConditionDesc("平台新用户立减10元");
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date());
		instance.add(Calendar.DAY_OF_YEAR, 7);
		vo.setStartTime(new Date());
		vo.setEndTime(instance.getTime());
		vo.setName("全店商品优惠券直减10元");
		vo.setPublisher(2L);
		vo.setPublisherName("小小店家");
		vo.setTotalNumber(100L);
		vo.setUseRule("{\"UDirectCutCash\":10}");
		List<Long> productId = new ArrayList<Long>();
		productId.add(10L);
		productId.add(20L);
		vo.setExcludeProductIds(productId);
		System.out.println(new Gson().toJson(vo));
		service.createCoupon(vo);
	}
	
	@Test
	public void getTempleteForAll() {
		List<CouponTempleteVO> shop = service1.getTempleteForAll();
		System.out.println(shop);
	}
	
	@Test
	public void addRules() {
		CouponRulesVO vo = new CouponRulesVO();
		vo.setRuleName("直减");
		vo.setRuleType(CouponUseRulesEnum.UDirectCutCash.name());
		vo.setBelongType(CouponRulesTypeEnum.UseRules.getCode());
		vo.setRuleDescription(CouponRulesTypeEnum.UseRules.getDesc());
		service2.addRules(vo );
	}

	@Test
	public void getone() {
		CouponRulesVO oneRule = service2.getOneRule(2L,null);
		System.out.println(oneRule);
	}
	
	@Test
	public void getall() {
		List<CouponRulesVO> allRules = service2.getAllRules(null);
		System.out.println(allRules);
	}
	
	/**
	 * {@link CouponRulesEnum}
	 */
	@Test
	public void addtemplete() {
		/* ----------------平台新用户 立减 -------- */
		/*// 创建规则
		CouponTempleteVO vo = new CouponTempleteVO();
		vo.setName("平台新用户立减");
		vo.setCreateRule(null);
		// 领取规则 新用户 
		String[] takeRule = new String[] {CouponTakeRulesEnum.TakeLimitNewUser.getCode()};
		String take = Arrays.toString(takeRule).replaceAll("\\[", "").replaceAll("\\]", "");
		vo.setTakeRule(take);
		// 使用规则 
		String[] useRule = new String[] {CouponUseRulesEnum.UDirectCutCash.getCode()};
		String use = Arrays.toString(useRule).replaceAll("\\[", "").replaceAll("\\]", "");
		vo.setUseRule(use);
		vo.setUseRange(CouponEnum.All.getCode());
		CouponTempleteVO templete = service1.addTemplete(vo);
		System.out.println(templete);*/
		/* ----------------平台新用户 立减 -------- */
		
		/* ----------------平台新用户 满减 -------- */
		// 创建规则
		/*CouponTempleteVO vo = new CouponTempleteVO();
		vo.setName("平台新用户满减");
		vo.setCreateRule(null);
		// 领取规则 新用户 
		String[] takeRule = new String[] {CouponTakeRulesEnum.TakeLimitNewUser.getCode()};
		String take = Arrays.toString(takeRule).replaceAll("\\[", "").replaceAll("\\]", "");
		vo.setTakeRule(take);
		// 使用规则 
		String[] useRule = new String[] {CouponUseRulesEnum.UMoneyOff.getCode()};
		String use = Arrays.toString(useRule).replaceAll("\\[", "").replaceAll("\\]", "");
		vo.setUseRule(use);
		vo.setUseRange(CouponEnum.All.getCode());
		CouponTempleteVO templete = service1.addTemplete(vo);
		System.out.println(templete);*/
		/* ----------------平台新用户 满减 -------- */
		
		/* ----------------平台满减 -------- */
		// 创建规则
		CouponTempleteVO vo = new CouponTempleteVO();
		vo.setName("突突突突突突拖");
		vo.setCreateRule(null);
		// 领取规则 新用户 
//		String[] takeRule = new String[] {CouponTakeRulesEnum.TakeLimitNewUser.getCode()};
//		String take = Arrays.toString(takeRule).replaceAll("\\[", "").replaceAll("\\]", "");
//		vo.setTakeRule(take);
		// 使用规则 
		String[] useRule = new String[] {CouponUseRulesEnum.UMoneyOff.name(),
				CouponUseRulesEnum.USomeProductCanNotUse.name()};
		String use = Arrays.toString(useRule).replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", "");
		vo.setUseRule(use);
		vo.setUseRange(CouponEnum.Shop.getCode());
		CouponTempleteVO templete = service1.addTemplete(vo);
		System.out.println(templete);
		/* ----------------平台满减 -------- */
		
		
	}
	@Test
	public void increaseCouponNum() {
		System.out.println("------increaseCouponNum------");
		service.increaseCouponNum(1L, 10L);
	}
	
	
	@Test
	public void updateCouponProduct() {
		System.out.println("------updateCouponProduct------");
		service.updateCouponProduct(1L, null);
	}
	
	@Test
	public void getCouponByShopIdAndUserId() {
		System.out.println("-----getCouponByShopIdAndUserId-----");
		List<GetCouponResponseParamsVO> list = service.getCouponByShopIdAndUserId(1L, 1L);
		System.out.println(list);
	}

	@Test
	public void deleteCouponByCouponId() {
		System.out.println("-----deleteCouponByCouponId-----");
		int i = service.deleteCouponByCouponId(1L,1L);
		System.out.println(i);
	}
	

	
	@Test
	public void getCouponTakeRecord() {
		System.out.println("-----getCouponTakeRecord-----");
		QueryCouponTakeRecordParamsVO couponTakeRecordParamsVO = new QueryCouponTakeRecordParamsVO();
//		couponTakeRecordParamsVO.setMobile("18785273024");
		couponTakeRecordParamsVO.setStatusList(Lists.newArrayList(new Byte("1")));
		couponTakeRecordParamsVO.setId(6L);
		couponTakeRecordParamsVO.setCouponId(1L);
		PageInfo<CouponTakeRecordVO> i = service.getCouponTakeRecord(couponTakeRecordParamsVO,1,10);
		System.out.println(i);
	}
	
	@Test
	public void  queryTicket() {
		System.out.println("=====queryTicket======");
		QueryCouponParamsVO paramsVO = new QueryCouponParamsVO(); 
//		paramsVO.setTempleteId(1L);
//		paramsVO.setPublisherName("趣免单");
		paramsVO.setPageNum(1);
		paramsVO.setPageSize(10);
		PageInfo<CouponVO> queryTicket = service.backstageQueryCoupon(paramsVO);
		System.out.println(queryTicket);
	}
	
	
}
