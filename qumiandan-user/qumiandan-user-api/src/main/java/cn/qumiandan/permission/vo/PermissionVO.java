package cn.qumiandan.permission.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class PermissionVO implements Serializable {
 
	private static final long serialVersionUID = 1L;
	/**编号*/
	private Long id;
	
	/**排序*/
	private Integer sort;
	
	/**菜单名*/
	private String name;
	
	/**父级菜单*/
	private Long pId;
	
	/**菜单*/
	private String permission;
	
	/**是否是菜单（1：菜单，2：按钮）*/
	private Byte type;
	
	/**菜单状态 :  1:启用   0:不启用*/
	private Byte status;
	
	/**创建者id*/
	private Long createId;
	
	/**修改者id*/
	private Long updateId;
	
	/**创建时间*/
	private Date createDate;
	
	/**修改时间*/
	private Date updateDate;

	
}
