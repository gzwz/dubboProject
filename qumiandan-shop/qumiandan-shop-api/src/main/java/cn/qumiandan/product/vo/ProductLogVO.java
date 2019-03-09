package cn.qumiandan.product.vo;

import java.io.Serializable;

import lombok.Data;
@Data
public class ProductLogVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private Long id;

    /**
     * 商品编号
     */
    private Long productId;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 操作描述
     */
    private String operationDescription;

    /**
     * 用户IP
     */
    private String userIp;

    /**
     * 操作状态 (status,成功,失败)
     */
    private Byte status;
}
