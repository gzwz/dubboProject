package cn.qumiandan.supportpayment.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 支付方式实体
 * @author lrj
 *
 */
@Data
@TableName(value = "sb_support_payment")
public class SupportPayment implements Serializable {
    private Long id;

    /**
     * 扫呗支付码
     */
    private String code;

    /**
     * 支付方式
     */
    private String name;

    /**
     * 支付方式logo
     */
    private String logoUrl;

    /**
     * 状态：1正常；0删除
     */
    private Byte status;

    private static final long serialVersionUID = 1L;

 
}