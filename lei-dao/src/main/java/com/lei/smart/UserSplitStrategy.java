package com.lei.smart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lei.smart.pojo.User;
import com.lei.smart.util.JsonUtil;
import com.lei.smart.vo.UserVO;
import org.apache.ibatis.mapping.BoundSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * order 分表策略  阉割版本  TODO:后期利用语义分析进行替换
 */
@Component
public class UserSplitStrategy implements Strategy {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String convert(String tableName, Object object, BoundSql boundSql) {
        String realTab = "";
        //进行order 分表
        if (object instanceof User) {
            User order = (User) object;
            if (order.getId() != null) {
                String boundSqlJson = "";
                try {
                    boundSqlJson = JsonUtil.convertObject2String(boundSql);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                logger.info("boundsql:{}  传入参数user的Id为空", boundSqlJson);
                String tableSuffix = String.valueOf(order.getId() % 5 + 1);

                if (Integer.parseInt(tableSuffix) < 10) {
                    tableSuffix = "0" + tableSuffix;
                }
                StringBuffer sb = new StringBuffer();
                StringBuffer tableReal = sb.append(tableName).append("_").append(tableSuffix);
                logger.info("分表后的表名{}", tableReal);
                return tableReal.toString();
            }
        } else if (object instanceof UserVO) {
            UserVO order = (UserVO) object;
            if (order.getId() != null) {
                String boundSqlJson = "";
                try {
                    boundSqlJson = JsonUtil.convertObject2String(boundSql);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                logger.info("boundsql:{}  传入参数user的Id为空", boundSqlJson);
                String tableSuffix = String.valueOf(order.getId() % 5 + 1);

                if (Integer.parseInt(tableSuffix) < 10) {
                    tableSuffix = "0" + tableSuffix;
                }
                StringBuffer sb = new StringBuffer();
                StringBuffer tableReal = sb.append(tableName).append("_").append(tableSuffix);
                logger.info("分表后的表名{}", tableReal);
                return tableReal.toString();
            }
        } else {
            return realTab;
        }
        return realTab;
    }
}