package com.lei.smart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 分表策略管理类
 */
@Component
@Slf4j
public class StrategyManager {
    private Map<String, Strategy> strategies = new ConcurrentHashMap<String, Strategy>(10);

    public Strategy getStrategy(String key) {
        return strategies.get(key);
    }

    public Map<String, Strategy> getStrategies() {
        return strategies;
    }


        @PostConstruct
    public void setStrategies() {
        log.info("开始初始化分表策略>>>>>>");
        TableNameEnum[] values = TableNameEnum.values();
        for (TableNameEnum tableNameEnum : values) {
            String tableName = tableNameEnum.getValue();
            String tableSplitRule = tableNameEnum.getDesc();
            try {
                this.strategies.put(tableName, (Strategy) Class.forName(tableSplitRule).newInstance());
            } catch (Exception e) {
                log.info("实例化分表策略fail>>>>>>");
                e.printStackTrace();
            }
        }
        printDebugInfo();
    }

    private void printDebugInfo() {
        StringBuffer msg = new StringBuffer("初始化了" + strategies.size() + "策略");
        for (String key : strategies.keySet()) {
            msg.append("\n");
            msg.append(key);
            msg.append("  --->  ");
            msg.append(strategies.get(key));
        }
        log.debug(msg.toString());
    }
}
