package cn.qumiandan.pay.saobei.model.request.merchant;

import java.io.Serializable;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.qumiandan.pay.saobei.model.request.AbstractSaoBeiReqDefinition;
import lombok.Getter;

/**
 * 商户请求参数定义
 * @author yuleidian
 * @version 创建时间：2018年12月4日 下午3:25:05
 */
@Getter
public class BaseMerchantReqDefinition extends AbstractSaoBeiReqDefinition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 机构编号*/
	@Expose
	@SerializedName("inst_no")
	protected String instNo;
	
	/** 请求流水号*/
	@Expose
	@SerializedName("trace_no")
	protected String traceNo;
	
	/** 令牌*/
	protected String key;
	
	public BaseMerchantReqDefinition() {
		this.traceNo = createTranNo();
	}
	
	@Override
	public String getSign() {
		if (keySign == null || "".equals(keySign)) {
			createResolveMap();
			doGetSign();
		}
		return keySign;
	}

	private void doGetSign() {
		if (!reqCondition.isEmpty()) {
			StringBuilder sign = new StringBuilder();
			reqCondition.entrySet().stream().forEach(condtion -> {
				sign.append(AND).append(condtion.getKey()).append(EQ).append(condtion.getValue());
			});
			// acToken必须加在最后 不参与排序
			sign.append(AND).append("key").append(EQ).append(key).deleteCharAt(0);
			this.keySign = sign.toString();
			reqCondition.put("key_sign", encrypt(keySign));
		}
	}

	@Override
	public void initInherentParameter(Object...params) {
		if (Objects.nonNull(params) && params.length > 0) {
			this.instNo = (String) params[0];
			this.key = (String) params[1];
		}
	}

}
