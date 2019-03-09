package cn.qumiandan.coupon.service.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

/**
 * 满减对象
 * @author WLZ
 * 2018年12月14日
 */
@Data
@Builder
public class UMoneyOffDTO {
	
	private Sub UMoneyOff;
	
	@Data
	public class Sub{
		private BigDecimal max;
		
		private BigDecimal cut;
		
	}
}
