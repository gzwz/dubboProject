package cn.qumiandan.web.frontServer.ticket.entity.request;

import java.io.Serializable;

import cn.qumiandan.common.request.CommonParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 根据用户id查询资格券参数
 * @author lrj
 *
 */
@Getter
@Setter
@ToString
public class GetTicketByUserIdParams extends CommonParams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 状态（1.未消费，2.消费中；3已消费）
	 */
	private Byte status;
	
	private Integer pageNum;
	
	private Integer pageSize;
	
    public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 200 : pageSize;
    }
}
