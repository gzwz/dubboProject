package cn.qumiandan.web.frontServer.file.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @description：文件上传参数对象
 * @author：zhuyangyong
 * @date: 2018/11/20 11:28
 */
@Data
public class FileUploadVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "本地文件路径不能为空")
    private String localFilePath;
}
