package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description：添加店铺自定分类信息参数
 * @author：lrj
 * @date: 2018/11/13 15:47
 */
@Data
public class AddShopCusCateParams implements Serializable{

	private static final long serialVersionUID = 1L;

    /**
     * 店铺编号
     */
    @NotNull(message = "店铺编号不能为空")
    private Long shopId;
    
    /**
     * 排序数
     */
    private int sort;
    
    /**
     * 名称
     */
    @NotBlank(message = "自定分类名称不能为空")
    private String name;


    /**
     * 创建人Id
     */
    private Long createId;

}
