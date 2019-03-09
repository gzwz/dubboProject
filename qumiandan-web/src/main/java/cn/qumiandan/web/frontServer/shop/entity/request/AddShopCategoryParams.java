package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @description：添加店铺分类信息参数
 * @author：lrj
 * @date: 2018/11/13 15:47
 */
@Data
public class AddShopCategoryParams implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "店铺分类名称不能为空")
    private String name;

    private Long parentId;  //上级分类id

    private Integer sort;  //排序
}
