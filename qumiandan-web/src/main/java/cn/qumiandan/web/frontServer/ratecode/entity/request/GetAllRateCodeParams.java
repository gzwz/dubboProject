package cn.qumiandan.web.frontServer.ratecode.entity.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 查询费率参数
 * @author lrj
 *
 */
@Data
public class GetAllRateCodeParams implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 类型集合
	 */
	private List<Byte> typeList;
}
