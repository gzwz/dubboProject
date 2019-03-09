package cn.qumiandan.web.frontServer.bankinfo.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
/**
 * 查询支行参数
 * @author lrj
 *
 */
@Data
public class GetSubBankInfoParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 总行编号
     */
	@NotBlank(message="总行编号不能为空")
    private String headBankNo;

    /**
     * 总行名称
     */
    private String subBankName;
}
