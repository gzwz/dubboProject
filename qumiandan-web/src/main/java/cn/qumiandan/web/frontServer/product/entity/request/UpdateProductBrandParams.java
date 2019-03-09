package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 更新商品品牌参数
 * @author lrj
 *
 */
@Data
public class UpdateProductBrandParams  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
	@NotNull(message="id不能为空")
    private Long id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 排列次序
     */
    private Integer sort;
    
    /**
     * 更新者id
     */
    private Long updateId;

}
