package cn.qumiandan.web.countServer.platformStats.entity.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/**
 * 店铺图表统计
 * @author yuleidian
 * @date 2019年1月26日
 */
@Data
public class ShopStatParams implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "查询时间不能为空")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd")
	private Date time;
	
	@NotEmpty(message = "查询店铺不能为空")
	private List<Long> shopIds;
	
}
