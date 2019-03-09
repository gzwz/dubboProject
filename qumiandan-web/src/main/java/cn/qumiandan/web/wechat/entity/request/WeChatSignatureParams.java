package cn.qumiandan.web.wechat.entity.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class WeChatSignatureParams {
	
	@NotNull(message = "当前页面url不能为空")
	private String url;
	
	private String appId;
	
	private long timestamp;
	
	private String signatue;
	
	private String nonceStr;
	
	List<String> jsApiList;
}
