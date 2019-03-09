package cn.qumiandan.web.frontServer.ratecode.entity.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 修改费率参数
 * @author lrj
 *
 */
@Data
public class UpdateRateCodeParams {
	/**
     * 编号
     */
	@NotNull(message = "费率id不能为空")
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
     * 更新人id
     */
    private Long updateId;
}
