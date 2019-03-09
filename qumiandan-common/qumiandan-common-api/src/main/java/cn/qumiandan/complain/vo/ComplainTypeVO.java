package cn.qumiandan.complain.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * 投诉类型传输类
 * @author lrj
 *
 */
@Data
public class ComplainTypeVO implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 类型名称
     */
    private String name;


}