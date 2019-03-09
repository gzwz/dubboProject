package cn.qumiandan.web.frontServer.file.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @description：获取文件信息参数对象
 * @author：zhuyangyong
 * @date: 2018/11/20 10:51
 */
@Data
public class FileInfoVO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "文件名不能为空")
    private String key;
}
