package cn.qumiandan.web.frontServer.file.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @description：文件存储参数对象
 * @author：zhuyangyong
 * @date: 2018/11/13 14:06
 */
@Data
public class FileDeleteVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "文件名称不能为空")
    private String fileName;

}
