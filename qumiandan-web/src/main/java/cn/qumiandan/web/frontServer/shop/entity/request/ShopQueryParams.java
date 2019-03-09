package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 根据店铺状态、店铺名、业务员手机号查询店铺参数
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class ShopQueryParams extends CommonParams implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺名
	 */
	private String shopName;
	
	/**
	 * 店铺状态
	 */
	private Byte status;
	
	
	private Long salemanId ;//业务员id
	
	private String salemanMobile ;//业务员手机号
}
