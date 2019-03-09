package cn.qumiandan.verifycode.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class IdentifyingCodeVO implements Serializable {
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