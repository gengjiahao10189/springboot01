package com.kuangstudy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "订单管理")
@RestController
@RequestMapping("order")
public class OrderController {

    @GetMapping("queryOrder")
    @ApiOperation("订单查询")
    public String queryOrder() {
        return "success";
    }

    @GetMapping("saveOrder")
    @ApiOperation("订单保存")
    public String saveOrder() {
        return "success";
    }
}
