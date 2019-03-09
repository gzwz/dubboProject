package cn.qumiandan.pay.saobei.model.request.merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class QueryTerminalReq extends BaseMerchantReqDefinition {

	private static final long serialVersionUID = 1L;

	/** 终端号*/
	@Expose
	@SerializedName("terminal_id")
	private String terminalId;

	public QueryTerminalReq() {
		super();
	}
	
	public QueryTerminalReq(String terminalId) {
		super();
		this.terminalId = terminalId;
	}
	
	public static QueryTerminalReq create(String terminalId) {
		return new QueryTerminalReq(terminalId);
	}
	
	public QueryTerminalReq setTerminalId(String terminalId) {
		this.terminalId = terminalId;
		return this;
	}	
	
}
