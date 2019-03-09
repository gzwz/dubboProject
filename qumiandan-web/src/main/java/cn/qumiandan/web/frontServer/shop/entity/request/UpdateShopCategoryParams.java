package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description：更新店铺分类信息参数
 * @author：lrj
 * @date: 2018/11/13 15:47
 */
@Data
public class UpdateShopCategoryParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
     * 编号
     */
	@NotNull(message="店铺分类id不能为空")
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级id
     */
    private Long parentId;

    private Integer sort;  //排序
}
