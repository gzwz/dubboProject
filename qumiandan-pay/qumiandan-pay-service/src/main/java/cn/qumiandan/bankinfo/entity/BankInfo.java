package cn.qumiandan.bankinfo.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 扫呗银行信息实体
 * @author lrj
 *
 */
@Data
@TableName(value = "sb_bank_info")
public class BankInfo implements Serializable {
    private Long id;

    /**
     * 总行编号
     */
    private String headBankNo;

    /**
     * 总行名称
     */
    private String headBankName;

    /**
     * 支行编号
     */
    private String subBankNo;

    /**
     * 支行名称
     */
    private String subBankName;

    /**
     * 城市代码
     */
    private String cityCode;

    /**	
     * 支行地址
     */
    private String subBankAddr;

    /**
     * 邮政编码
     */
    private String zipCode;

    private static final long serialVersionUID = 1L;

    
}