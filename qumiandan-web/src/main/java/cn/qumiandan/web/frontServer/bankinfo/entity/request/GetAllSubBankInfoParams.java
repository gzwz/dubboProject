package cn.qumiandan.web.frontServer.bankinfo.entity.request;

import java.io.Serializable;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class GetAllSubBankInfoParams extends CommonParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 支行名
	 */
	private String subBankName;
}
