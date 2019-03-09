package cn.qumiandan.web.frontServer.product.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
/**
 * 添加商品品牌参数
 * @author lrj
 *
 */
@Data
public class AddProductBrandParams implements Serializable{


	private static final long serialVersionUID = 1L;

	/**
     * 品牌名称
     */
	@NotBlank(message="品牌名称不能为空")
    private String name;

    /**
     * 图片url
     */
	@NotBlank(message="品牌图片url不能为空")
    private String imageUrl;

    /**
     * 排列次序
     */
    private Integer sort;
    
    private Long createId;
}
