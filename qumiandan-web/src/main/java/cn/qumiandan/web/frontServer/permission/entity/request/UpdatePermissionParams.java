package cn.qumiandan.web.frontServer.permission.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * 更新菜单参数
 * @author lrj
 *
 */
@Data
public class UpdatePermissionParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 编号
     */
	@NotNull(message="id不能为空")
    private Long id;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父级菜单
     */
    private Long parentId;

    /**
     * 菜单
     */
    private String permission;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 是否是菜单（1：菜单，2：按钮）
     */
    private Byte type;

  
    /**
     * 修改者id
     */
    private Long updateId;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


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


	public Long getUpdateId() {
		return updateId;
	}


	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
}
