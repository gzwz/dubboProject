package cn.qumiandan.pricecalculate.vo;

import java.io.Serializable;

import cn.qumiandan.order.vo.OrderAddVO;
import cn.qumiandan.order.vo.OrderDetailVO;
import lombok.Data;

@Data
public class OrderCalculateVO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private OrderAddVO orderAddVO;
	
	private OrderDetailVO detailVO;

}
