package cn.qumiandan.user.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 添加用户vo对象
 */
@Data
public class UserAddVO implements Serializable{
    private static final long serialVersionUID = 1L;

    public UserAddVO() {
    	setStatus(1);
    }

    public UserAddVO(String userName, String password, String nickName) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        setStatus(1);
        setCreateDate(new Date());
    }

    /**
     * 用户Id
     */
    private Long id;

    /**
     * 账号
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信账号unionid
     */
    private String unionId;

    /**
     * 微信openid
     */
    private String openId;

    /**
     * 支付宝id
     */
    private String alipayId;

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
     * 状态(1：启用  2：冻结  3：删除）
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
     * 创建人
     */
    private Long createId;

    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新人
     */
    private Long updateId;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 座机
     */
    private String phone;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 银行卡
     */
    private String bankAccount;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 头像
     */
    private String portrait;
 
    /**
     * 登录token
     */
    private String token;
    
}
