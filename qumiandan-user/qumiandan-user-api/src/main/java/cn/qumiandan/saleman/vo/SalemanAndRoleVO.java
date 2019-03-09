package cn.qumiandan.saleman.vo;

import java.io.Serializable;
/**
 * 业务远端查询角色
 * @author lrj
 *
 */
import java.util.List;

import cn.qumiandan.role.vo.RoleVO;
import lombok.Data;
/**
 * 业务员角色信息参数类
 * @author lrj
 *
 */
@Data
public class SalemanAndRoleVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色信息
	 */
	private List<RoleVO> roleList;
	
	/**
	 * 省、市、区代理、业务员信息
	 */
	private SalemanAndUserVO salemanVO;
	
}
