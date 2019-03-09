package cn.qumiandan.complain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * 投诉类型实体
 * @author lrj
 *
 */
@Data
@TableName("qmd_complain_type")
public class ComplainType implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@TableId
	private Long id;

    /**
     * 类型名称
     */
    private String name;

  

}