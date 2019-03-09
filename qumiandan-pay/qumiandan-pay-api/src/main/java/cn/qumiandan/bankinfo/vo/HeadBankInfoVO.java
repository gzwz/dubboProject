package cn.qumiandan.bankinfo.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 扫呗总行传输类
 * @author lrj
 *
 */
@Data
public class HeadBankInfoVO  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 总行编号
     */
    private String headBankNo;

    /**
     * 总行名称
     */
    private String headBankName;
}
