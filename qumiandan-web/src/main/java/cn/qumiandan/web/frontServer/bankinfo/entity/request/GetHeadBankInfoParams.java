package cn.qumiandan.web.frontServer.bankinfo.entity.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 查询总行参数
 * @author lrj
 *
 */
@Data
public class GetHeadBankInfoParams implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 总行名称
     */
    private String headBankName;
}
