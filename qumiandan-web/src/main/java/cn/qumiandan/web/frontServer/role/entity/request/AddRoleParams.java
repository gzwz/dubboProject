package cn.qumiandan.web.frontServer.role.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 添加角色信息参数类
 * @author lrj
 *
 */
@Data
public class AddRoleParams implements Serializable{
    
	private static final long serialVersionUID = 1L;

	
    /**
     * 序号
     */
//	 @NotNull(message="序号不能为空")
    private Integer sort;

    /**
     * 角色名称
     */
    @NotBlank(message="角色名称不能为空")
    private String name;

    /**
     * 部门名称
     */
    @NotNull(message="部门id不能为空")
    private Integer deptId;

    /**
     * 英文别名
     */
    @NotNull(message="部门id不能为空")
    private String ealias;

    /**
     * 创建人ID
     */
    //@NotNull(message="创建人id不能为空")
    private Long createId;

}
