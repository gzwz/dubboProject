package cn.qumiandan.shopmember.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.qumiandan.common.exception.QmdException;
import cn.qumiandan.constant.StatusEnum;
import cn.qumiandan.shopmember.api.IShopMemberService;
import cn.qumiandan.shopmember.api.IVipDiscountInfoService;
import cn.qumiandan.shopmember.entity.ShopMember;
import cn.qumiandan.shopmember.mapper.ShopMemberMapper;
import cn.qumiandan.shopmember.vo.PurchaseShopMemberVO;
import cn.qumiandan.shopmember.vo.ShopMemberVO;
import cn.qumiandan.shopmember.vo.VipDiscountInfoVO;
import cn.qumiandan.utils.CopyBeanUtil;
import cn.qumiandan.utils.DateUtil;

@Component
@Service(interfaceClass = IShopMemberService.class)
public class ShopMemberServiceImpl implements IShopMemberService{

	@Autowired
	private ShopMemberMapper shopMemberMapper;
	@Autowired
	private IVipDiscountInfoService vipDiscountInfoService;
	
	/**
	 * 购买会员
	 * @param purchaseShopMemberVO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int purchaseShopMember(PurchaseShopMemberVO purchaseShopMemberVO) {
		QueryWrapper<ShopMember> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", purchaseShopMemberVO.getUserId()); 
		queryWrapper.eq("shop_id", purchaseShopMemberVO.getShopId());
		ShopMember shopMember = shopMemberMapper.selectOne(queryWrapper);
		int i = 0;
		if(shopMember == null ) {
			//该用户在该店铺未购买过会员
			shopMember = new ShopMember();
			shopMember.setUserId(purchaseShopMemberVO.getUserId());
			shopMember.setShopId(purchaseShopMemberVO.getShopId());
			shopMember.setCreateDate(new Date());
			shopMember.setMemberDeadline(DateUtil.addDoubleDay(shopMember.getCreateDate(),purchaseShopMemberVO.getDays()));
			i = shopMemberMapper.insert(shopMember);
		}else {
			//该用户在该店铺未购买过会员
			if(shopMember.getMemberDeadline().getTime()>new Date().getTime())
				//会员过期时间大于当前时间
				shopMember.setMemberDeadline(DateUtil.addDoubleDay(shopMember.getMemberDeadline(),purchaseShopMemberVO.getDays()));
			else {
				//会员过期时间小于当前时间
				shopMember.setMemberDeadline(DateUtil.addDoubleDay(new Date(),purchaseShopMemberVO.getDays()));
			}
			i = shopMemberMapper.updateById(shopMember);
		}
		if (i != 1) {
			throw new QmdException("插入会员天数执行错误！");
		}
		return i;
	}
	
	/**
	 * 查询用户是否是
	 * @param shopId
	 * @param userId
	 * @return
	 */
	@Override
	public ShopMemberVO isShopMember(Long shopId, Long userId) {
		QueryWrapper<ShopMember> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId); 
		queryWrapper.eq("shop_id", shopId);
		ShopMember shopMember = shopMemberMapper.selectOne(queryWrapper);
		if(shopMember == null||(shopMember.getMemberDeadline().getTime()<new Date().getTime())) {
			return null;
		}
		ShopMemberVO shopMemberVO = CopyBeanUtil.copyBean(shopMember, ShopMemberVO.class);
		return shopMemberVO;
	}
	
	/**
	 * 设置过期时间
	 * @param date
	 * @param days
	 * @return
	 */
/*	private Date setMemberDeadline (Date date, Double days) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		int dayNum = (int)Math.floor(days);
		rightNow.add(Calendar.DAY_OF_YEAR,dayNum);//日期加10天
		int hours = (int)((days - dayNum)*24);
		rightNow.add(Calendar.HOUR, hours);
		Date retDate=rightNow.getTime();
		return retDate;
	}*/

	/**
	 * 判断用户是否是店铺会员
	 */
	@Override
	public BigDecimal getShopMemberDiscount(Long shopId, Long userId) {
		ShopMember shopMember = shopMemberMapper.selectOne(new QueryWrapper<ShopMember>().eq("shop_id", shopId).eq("user_id", userId));
		if(shopMember == null) {
			return null;
		}
		VipDiscountInfoVO discountInfoVO = vipDiscountInfoService.getVipDiscountInfoByShopId(shopId);
		if(discountInfoVO == null || !discountInfoVO.getStatus().equals(StatusEnum.normal.getCode())) {
			return null;
		}
		return discountInfoVO.getDiscountMoney();
	}


}
