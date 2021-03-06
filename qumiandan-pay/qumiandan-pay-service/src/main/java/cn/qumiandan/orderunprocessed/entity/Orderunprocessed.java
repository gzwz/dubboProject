package cn.qumiandan.orderunprocessed.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * 未处理成功的订单实体
 * @author lrj
 *
 */
@Data
@TableName(value = "qmd_pay_order_unprocessed")
public class Orderunprocessed implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @TableId
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 游戏订单id
     */
    private String gameOrderId;

    /**
     * 交易流水id
     */
    private Long tradeId;

    /**
     * 失败类型（1.回调支付金额不等于订单应付金额，2.并发导致未处理）
     */
    private Byte type;

    /**
     * 该条信息状态是否处理：（1.未处理(默认值)，2.已处理）
     */
    private Byte status;

    /**
     * 创建人
     */
    private Long createId;

    /**
     * 更新人
     */
    private Long updateId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;


}