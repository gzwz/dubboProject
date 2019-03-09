package cn.qumiandan.web.frontServer.shop.entity.request.updateshop;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/**
 * 设置是否开启打烊及打烊时间参数 
 * @author lrj
 *
 */
@Data
public class SetIsOpenShutParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 店铺id
	 */
	@NotNull(message = "店铺id不能为空")
	private Long shopId;
	
	/**
	 * 是否开启打烊(1:是；0：否)
	 */
	
	@NotNull(message = "是否开启打烊标识不能为空")
	private Byte status;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date openTime;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date restTime;

}
