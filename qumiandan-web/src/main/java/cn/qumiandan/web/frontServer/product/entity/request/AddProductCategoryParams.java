package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 添加商品分类信息参数
 * @author lrj
 *
 */
@Data
public class AddProductCategoryParams  implements Serializable{
	
	private static final long serialVersionUID = 1L;

    /**
     * 类别名称
     */
	@NotBlank(message="类别名称不能为空")
    private String name;
    
    /**
     * 级别（1：一级；2：二级；）
     */
	@NotNull(message="分类级别不能为空")
    private Byte level;

    /**
     * 父级编号
     */
    private Long parentId;

    /**
     * 排列次序
     */
    private Integer sort;

    /**
     * 创建人
     */
    private Long createId;

}
