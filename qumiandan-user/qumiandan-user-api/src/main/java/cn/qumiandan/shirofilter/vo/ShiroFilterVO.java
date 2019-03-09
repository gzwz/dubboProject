package cn.qumiandan.shirofilter.vo;

import java.io.Serializable;
/**
 * @description：ShiroFilter传输对象
 * @author：lrj
 * @date: 2018/11/9 15:50
 */
public class ShiroFilterVO implements Serializable{
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "ShiroFilterVO [id=" + id + ", name=" + name + ", perms=" + perms + ", sort=" + sort + "]";
	}
    
    
}
