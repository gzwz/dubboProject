package cn.qumiandan.web.frontServer.order.entity.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @description: 订单详情参数对象
 * @author: zhuayngyong
 * @date: 2018/12/5 16:53
 */
@Data
public class OrderDetailParams implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "订单编号不能为空")
    private String orderId;
}
