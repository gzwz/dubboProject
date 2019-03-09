package cn.qumiandan.web.frontServer.bankinfo.entity.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;


/**
 * 根据id获取银行信息
 * @author lrj
 *
 */

@Data
public class GetBankInfoByIdParams implements Serializable{


	private static final long serialVersionUID = 1L;

	@NotNull(message = "银行id不能为空")
	private Long id;
}
