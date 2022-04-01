package com.kuangstudy.service.order;

import com.kuangstudy.aop.log.KsdLog;
import com.kuangstudy.pojo.Order;
import com.kuangstudy.service.log.LogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private LogsService logsService;

    /**
     *
     * @param order
     */
    @KsdLog
    public void saveOrder(Order order) {
        log.info("保存订单信息：{}",order.toString());
        // 这里应该做的是日志方法的增强使用aop方式实现
        // 通过的做法是在每个暂存方法后添加这块代码
        /*Logs logs = new Logs();
        logs.setId(UUID.randomUUID().toString());
        logs.setClassName(OrderService.class.getName());
        logs.setMethodName("saveOrder");
        logs.setParams(order.toString());
        logs.setTime("1ms");
        logsService.save(logs);*/
    }

}
