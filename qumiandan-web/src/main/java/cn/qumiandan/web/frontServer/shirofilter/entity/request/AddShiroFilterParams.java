package cn.qumiandan.web.frontServer.shirofilter.entity.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 请求参数
 * @author yuleidian
 * @version 创建时间：2019年1月7日 下午6:18:48
 */
@Data
public class AddShiroFilterParams implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	 /**
     * 编号id
     */
    private Long id;

    /**
     * 接口地址名
     */
    private String name;

    /**
     * 权限
     */
    private String perms;

    /**
     * 排序
     */
    private Integer sort;
    
    private Long createId;
    
    private Date createDate = new Date();
    

}
