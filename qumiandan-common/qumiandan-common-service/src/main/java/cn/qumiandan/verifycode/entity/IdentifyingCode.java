package cn.qumiandan.verifycode.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
/**
 * 
 * 短信验证码记录表实体类
 * @author lrj
 *
 */
@Data
@TableName("sys_identifying_code")
public class IdentifyingCode implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 验证码
     */
    private String code;

    /**
     * 发送时间
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;

  
}