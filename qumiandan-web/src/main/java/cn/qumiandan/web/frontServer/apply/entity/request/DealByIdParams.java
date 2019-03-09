package cn.qumiandan.web.frontServer.apply.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 处理开店申请参数类
 * @author lrj
 *
 */
@Data
public class DealByIdParams implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 申请id
	 */
	@NotNull(message = "申请id不能为空")
	private Long id;
	
	/**
	 * 状态：处理完成；2拒绝处理
	 */
	@NotNull(message = "处理结果状态不能为空")
	private Byte status;
}
