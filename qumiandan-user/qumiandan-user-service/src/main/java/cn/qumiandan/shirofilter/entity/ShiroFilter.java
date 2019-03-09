package cn.qumiandan.shirofilter.entity;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName(value = "sys_shiro_filter")
public class ShiroFilter implements Serializable {
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
}