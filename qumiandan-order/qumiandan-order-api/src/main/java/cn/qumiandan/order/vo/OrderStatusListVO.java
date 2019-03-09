package cn.qumiandan.order.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 根据订单状态list查询订单参数
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class OrderStatusListVO extends OrderQueryVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Byte> statusList;
}
