package cn.qumiandan.orderinvoice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@TableName("qmd_order_invoice")
@Data
public class OrderInvoice implements Serializable {
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 是否增值税发票(1,普通发票  2.增值发票)
     */
    private Byte isVat;

    /**
     * 发票抬头名称
     */
    private String invoiceTitle;

    /**
     * 发票抬头内容 
     */
    private String invoiceContent;

    /**
     * 发票金额
     */
    private BigDecimal invoiceAmount;

    /**
     * 发票税号
     */
    private String invoiceTaxNo;

    /**
     * 开票税金
     */
    private BigDecimal invoiceTax;

    /**
     * 公司名称[增值税] 
     */
    private String vatCompanyName;

    /**
     * 公司地址[增值税]
     */
    private String vatCompanyAddress;

    /**
     * 联系电话[增值税]
     */
    private String vatTelphone;

    /**
     * 开户银行[增值税] 
     */
    private String vatBankName;

    /**
     * 银行帐号[增值税]
     */
    private String vatBankAccount;

    /**
     * 开票时间
     */
    private Date createdDate;
    
    /**
     * 状态；1正常；0删除
     */
    private Byte status;
    
    private static final long serialVersionUID = 1L;


}