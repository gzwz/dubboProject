package cn.qumiandan.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_user_info")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    private String nickName;

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
    
    /**
     * 最后登录时间
     */
    private Date lastLoginDate;
}