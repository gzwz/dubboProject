package cn.qumiandan.apply.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 申请开端传输类
 * 
 * @author lrj
 *
 */
@Data
public class ApplyVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	/** 联系人 */
	private String name;
	/** 手机 */
	private String mobile;
	/** 店铺名 */
	private String shop;
	/** 省code */
	private String proCode;
	/** 省 */
	private String proName;
	/** 市code */
	private String cityCode;
	/** 市 */
	private String cityName;
	/** 区code */
	private String countryCode;
	/** 区 */
	private String countryName;
	/** 行业id */
	private Long industryId;
	/** 行业id */
	private String industryName;
	
	/** 申请类型（1：申请开通店铺，2.申请省代理，3.申请市代理，4.申请区代，5.申请成为业务员）*/
	private Byte type;
	
	/** 状态（0：未处理 1：已处理 ） */
	private Byte status;
	/** 创建者 */
	private Long createId;
	/** 更新者 */
	private Long updateId;
	/** 创建时间 */
	private Date createDate;
	/** 更新时间 */
	private Date updateDate;

}
