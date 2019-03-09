package cn.qumiandan.apply.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("qmd_apply")
public class Apply {
	@TableId
	private Long id;
	/** 联系人 */
	private String name;
	/** 手机 */
	private String mobile;
	/** 店铺名 */
	private String shop;
	/** 省code */
	private String proCode;
	/** 市code */
	private String cityCode;
	/** 区code */
	private String countryCode;
	/**行业id*/
	private Long industryId;
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
