package cn.qumiandan.pay.saobei.vo.response.merchant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.pay.saobei.vo.response.AbstractSaoBeiResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * 商户接口返回基本信息
 * @author yuleidian
 * @version 创建时间：2018年12月5日 上午10:10:09
 */
@Getter
@Setter
public class BaseMerchantResVO extends AbstractSaoBeiResponse {

	private static final long serialVersionUID = 1L;
	
	/** 操作流水号*/
	@Expose
	@SerializedName("trace_no")
	private String traceNo;
	
}
