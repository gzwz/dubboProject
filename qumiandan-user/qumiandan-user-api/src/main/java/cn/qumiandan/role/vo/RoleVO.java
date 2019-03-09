package cn.qumiandan.role.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色vo
 */
public class RoleVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	private Long id;

	/**
	 * 序号
	 */
	private Integer sort;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 部门名称
	 */
	private Long deptId;

	/**
	 * 英文别名
	 */
	private String ealias;

	/**
	 * 1:正常； 0：已删除；（默认1）
	 */
	private Byte status;

	private Long createId;

	private Long updateId;

	private Date createDate;

	private Date updateDate;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getEalias() {
		return ealias;
	}

	public void setEalias(String ealias) {
		this.ealias = ealias;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "RoleVO [id=" + id + ", sort=" + sort + ", name=" + name + ", deptId=" + deptId + ", ealias=" + ealias
				+ ", status=" + status + ", createId=" + createId + ", updateId=" + updateId + ", createDate="
				+ createDate + ", updateDate=" + updateDate + "]";
	}

}
