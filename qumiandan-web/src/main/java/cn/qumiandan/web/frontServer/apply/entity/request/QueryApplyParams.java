package cn.qumiandan.web.frontServer.apply.entity.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.qumiandan.common.params.PageInfoParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 总后台查询开店申请记录
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class QueryApplyParams extends PageInfoParams implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	/**
	 * 申请类型（5：申请开通店铺，4.申请省代理，3.申请市代理，2.申请区代，1.申请成为业务员）
	 */
	@NotNull(message = "类型不能为空")
	private Byte type;
	/**
	 * 模糊查询条件
	 */
	private String inputInfo;
	/** 省code */
	private String proCode;
	/** 市code */
	private String cityCode;
	/** 区code */
	private String countryCode;
	/** 状态（0：未处理 1：已处理 ） */
	private Byte status;

	/**
	 * 创建时间查询条件：开始时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startCreateDate;
	/**
	 * 创建时间查询条件：结束时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endCreateDate;


}
