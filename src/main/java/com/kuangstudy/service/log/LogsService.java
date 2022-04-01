package com.kuangstudy.service.log;

import com.kuangstudy.pojo.Logs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogsService {

    public void save(Logs logs) {
        log.info("保存日志入库：{}",logs.toString());
    }
}
