package cn.qumiandan.analyseData.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value="qmd_order")
public class PayNum implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableField(value="pay")
	private Long pay;
	
}
