package cn.qumiandan.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "sys_user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 微信账号unionid
     */
    @TableField(value = "union_id")
    private String unionId;

    /**
     * 账号
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 微信openid
     */
    @TableField(value = "open_id")
    private String openId;

    /**
     * 支付宝id
     */
    @TableField(value = "alipay_id")
    private String alipayId;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * md5密码盐
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 名字
     */
    @TableField(value = "name")
    private String name;

    /**
     * 部门id
     */
    @TableField(value = "org_id")
    private Long orgId;

    /**
     * 父级id
     */
    @TableField(value = "p_id")
    private Long pId;

    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 省
     */
    @TableField(value = "pro_code")
    private String proCode;

    /**
     * 市
     */
    @TableField(value = "city_code")
    private String cityCode;

    /**
     * 区
     */
    @TableField(value = "country_code")
    private String countryCode;

    /**
     * 保留字段
     */
    @TableField(value = "version")
    private Integer version;

    /**
     * 创建人
     */
    @TableField(value = "create_id")
    private Long createId;

    /**
     * 更新人
     */
    @TableField(value = "update_id")
    private Long updateId;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    private Date createDate;

    @TableField(value = "update_date")
    private Date updateDate;

}