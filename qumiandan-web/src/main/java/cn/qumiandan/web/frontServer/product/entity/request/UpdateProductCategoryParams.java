package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 更新商品分类信息参数
 * @author lrj
 *
 */
@Data
public class UpdateProductCategoryParams implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
	@NotNull(message="级别id不能为空")
    private Long id;

    /**
     * 类别名称
     */
    private String name;
    
//    /**
//     * 级别（1：一级；2：二级；）
//     */
//    private Byte level;

    
    /**
     * 父级编号
     */
    private Long parentId;

    /**
     * 排列次序
     */
    private Integer sort;

    /**
     * 更新人
     */
    private Long updateId;
}
