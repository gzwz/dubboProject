package cn.qumiandan.validationticket.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @description: 核销券传输对象
 * @author: zhuayngyong
 * @date: 2018/12/5 14:22
 */
@Data
public class ValidationTicketVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 核销码
     */
    private String ticketCode;

    /**
     * 到期时间
     */
    private Date endDate;

    /**
     * 使用状态（1.未消费，2.已消费，3.未消费，已过期）
     */
    private Byte status;

    /**
     * 使用时间
     */
    private Date updateDate;

    /**
     * 更新人id
     */
    private Long updateId;

    /**
     * 创建时间
     */
    private Date createDate;
}
