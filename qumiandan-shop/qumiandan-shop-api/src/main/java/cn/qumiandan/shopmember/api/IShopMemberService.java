package cn.qumiandan.shopmember.api;

import java.math.BigDecimal;

import cn.qumiandan.shopmember.vo.PurchaseShopMemberVO;
import cn.qumiandan.shopmember.vo.ShopMemberVO;

/**
 * 会员管理接口
 * @author lrj
 *
 */
public interface IShopMemberService {


	/**
	 * 购买会员
	 * @param purchaseShopMemberVO
	 * @return
	 */
	int purchaseShopMember(PurchaseShopMemberVO purchaseShopMemberVO) ;
	
	/**
	 * 查询用户是否是会员
	 * @param shopId
	 * @param userId
	 * @return
	 */
	ShopMemberVO isShopMember(Long shopId,Long userId);
	
	/**
	 * 查询用户是否是会员内部调用，是会员且店铺开启会员优惠返回店铺折扣信息，不是会员返回null
	 * @param shopId
	 * @param userId
	 * @return
	 */
	BigDecimal getShopMemberDiscount(Long shopId,Long userId);
}
