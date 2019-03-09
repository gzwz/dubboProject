package cn.qumiandan.user.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 更新user主表信息
 * @author yuleidian
 * @version 创建时间：2018年12月25日 上午10:51:41
 */
@Data
public class UpdateUserVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 微信账号unionid
	 */
	private String unionId;

	/**
	 * 账号
	 */
	private String userName;

	/**
	 * 微信openid
	 */
	private String openId;

	/**
	 * 支付宝id
	 */
	private String alipayId;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * md5密码盐
	 */
	private String salt;

	/**
	 * 名字
	 */
	private String name;

	/**
	 * 部门id
	 */
	private Long orgId;

	/**
	 * 父级id
	 */
	private Long pId;

	/**
	 * 状态(1：启用 2：冻结 3：删除）
	 */
	private Integer status;

	/**
	 * 省
	 */
	private String proCode;

	/**
	 * 市
	 */
	private String cityCode;

	/**
	 * 区
	 */
	private String countryCode;

	/**
	 * 保留字段
	 */
	private Integer version;

	/**
	 * 更新人
	 */
	private Long updateId;

	private Date updateDate;
}
