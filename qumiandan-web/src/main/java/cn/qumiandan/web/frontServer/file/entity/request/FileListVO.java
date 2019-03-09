package cn.qumiandan.web.frontServer.file.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @description：文件列表参数对象
 * @author：zhuyangyong
 * @date: 2018/11/20 10:41
 */
@Data
public class FileListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "文件名前缀不能为空")
    private String prefix;
    @NotNull(message = "迭代的长度不能为空")
    private Integer limit;  //每次迭代的长度限制，最大1000，推荐值 1000
    private String delimiter;   //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
}
