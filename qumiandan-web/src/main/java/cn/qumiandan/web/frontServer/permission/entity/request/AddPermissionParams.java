package cn.qumiandan.web.frontServer.permission.entity.request;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * 添加权限参数类
 * @author lrj
 *
 */
public class AddPermissionParams implements Serializable{
	
	private static final long serialVersionUID = 1L;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父级菜单
     */
    @Min(value = 0, message = "父级菜单不能为空")
    private Long parentId;

    /**
     * 菜单
     */
    @NotBlank(message="菜单不能为空")
    private String permission;

    /**
     * 菜单名
     */
    @NotBlank(message="菜单名不能为空")
    private String name;

    /**
     * 是否是菜单（1：菜单，2：按钮）
     */
    @NotNull(message="类型不能为空")
    private Byte type;

    /**
     * 创建者id
     */
    private Long createId;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Override
	public String toString() {
		return "AddPermissionParams [sort=" + sort + ", parentId=" + parentId + ", permission=" + permission + ", name="
				+ name + ", type=" + type + ", createId=" + createId + "]";
	}


    
}
