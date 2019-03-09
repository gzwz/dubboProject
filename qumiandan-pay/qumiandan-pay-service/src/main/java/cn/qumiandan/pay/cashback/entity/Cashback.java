package cn.qumiandan.pay.cashback.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * 返现实体类
 * @author lrj
 *
 */
@Data
@TableName(value="qmd_cashback")
public class Cashback implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /**
     * 资格券id
     */
    private String ticketId;

    /**
     * 店铺id
     */
    private Long shopId;
    
    /**
     * 提现申请备注
     */
    private String remarkSubmit;

    /**
     * 提现审核备注
     */
    private String remarkAudit;

    /**
     * 返现金额
     */
    private BigDecimal actualAmount;

    /**
     * 实际打款时间
     */
    private Date paymentDate;

    /**
     * 打款异常信息
     */
    private String errorMessage;

    /**
     * 状态（1. 审核中 2.未通过  3.等待打款  4.自动打款异常 5.打款完成  6.手动打款 ）
     */
    private Byte status;

    /**
     * 审核人
     */
    private Long auditorId;

    /**
     * 审核时间
     */
    private Date auditorDate;

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