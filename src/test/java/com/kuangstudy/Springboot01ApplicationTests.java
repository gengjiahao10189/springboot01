package com.kuangstudy;

import com.kuangstudy.pojo.Order;
import com.kuangstudy.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class Springboot01ApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
    }

    @Test
    void testAop1() {
        orderService.saveOrder(new Order(UUID.randomUUID().toString(), BigDecimal.ONE));
    }

}
