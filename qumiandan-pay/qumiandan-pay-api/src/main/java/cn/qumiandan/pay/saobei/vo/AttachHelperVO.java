package cn.qumiandan.pay.saobei.vo;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import cn.qumiandan.common.interfaces.Jsonable;
import lombok.Getter;
import lombok.Setter;

/**
 * 传入扫呗返回结果
 * @author yuleidian
 * @version 创建时间：2018年12月19日 下午2:43:19
 */
@Getter
@Setter
public class AttachHelperVO implements Serializable, Jsonable {
	
	private static final long serialVersionUID = 1L;
	
	/** 订单id*/
	@Expose
	private String orderId;
	
	/** 游戏订单id*/
	@Expose
	private String gameOrderId;

	/** 流水记录id*/
	@Expose
	private Long tradeId;
	
	public AttachHelperVO() {
		super();
	}
	
	public AttachHelperVO(String orderId, String gameOrderId, Long tradeId) {
		super();
		this.orderId = orderId;
		this.gameOrderId = gameOrderId;
		this.tradeId = tradeId;
	}
	
	
	
}
