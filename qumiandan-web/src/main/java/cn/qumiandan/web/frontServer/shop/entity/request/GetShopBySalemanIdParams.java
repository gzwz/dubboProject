package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.qumiandan.common.params.PageInfoParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class GetShopBySalemanIdParams extends PageInfoParams implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 店铺名
	 */
	private String shopName;
	
	/**
	 * 状态
	 */
	private List<Byte> statusList;
	
	/**
	 * 创建时间查询条件：开始时间
	 */
	private Date startCreateDate;
	
	/**
	 * 创建时间查询条件：结束时间
	 */
	private Date endCreateDate;
}
