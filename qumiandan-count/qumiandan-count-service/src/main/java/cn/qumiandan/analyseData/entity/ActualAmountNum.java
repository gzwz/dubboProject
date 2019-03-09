package cn.qumiandan.analyseData.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value="qmd_trade_detail")
public class ActualAmountNum implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableField(value="actualAmount")
	private BigDecimal actualAmount;
	
}
