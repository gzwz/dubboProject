package cn.qumiandan.ratecode.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 费率传输类
 * @author lrj
 *
 */
@Data
public class RateCodeVO implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 编号
     */
    private Long id;

    /**
     * 费率代码
     */
    private String code;

    /**
     * 费率
     */
    private BigDecimal rate;

    /**
     * 费率规则（type为1时没有规则，type为2时规则保存的是每笔加收的金额，type为3是json字符串分别记录高低阶梯及相应费率）
     */
    private String rateRule;

    /**
     * 费率类型（1：支付费率代码；2：D0费率代码；3：阶梯费率代码）
     */
    private Byte type;
    
    
    /**
     * 状态：1正常；0删除
     */
    private Byte status;
    
    /**
     * 创建时间
     */
    private Date createDate;
    
    /**
     * 更新时间
     */
    private Date updateDate;
    
    /**
     * 创建人id
     */
    private Long createId;
    
    /**
     * 更新人id
     */
    private Long updateId;
}
