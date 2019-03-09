package cn.qumiandan.web.frontServer.shop.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description：更新店铺自定义分类信息参数
 * @author：lrj
 * @date: 2018/11/13 14:11
 */
@Data
public class UpdateShopCusCateParams implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 编号
     */
	@NotNull(message="id不能为空")
    private Long id;

    /**
     * 店铺编号
     */
    private Long shopId;
    
    /**
     * 排序数
     */
    private int sort;
    
    /**
     * 名称
     */
    private String name;

    /**
     * 更新人Id
     */
    private Long updateId;
}
