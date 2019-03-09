package cn.qumiandan.web.frontServer.permission.entity.request;

import java.io.Serializable;

import lombok.Data;
/**
 * 添加权限参数类
 * @author lrj
 *
 */
@Data
public class QueryPermissionParams implements Serializable{
	private static final long serialVersionUID = 1L;
 
    //@NotNull(message="类型不能为空")
    private Byte type;

    
}
