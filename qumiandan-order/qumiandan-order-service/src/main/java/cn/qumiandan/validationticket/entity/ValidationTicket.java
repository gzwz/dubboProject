package cn.qumiandan.validationticket.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("qmd_validation_ticket")
public class ValidationTicket implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @TableId
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