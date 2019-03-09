package cn.qumiandan.user.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 修改用户vo对象
 */
@Data
public class UserUpdateVO implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户角色列表
     */
    private List<Long> roleIds;
    
}
