package com.lei.smart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;


public class ThreadLocalRountingDataSource extends AbstractRoutingDataSource {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    //单例句柄
    private static ThreadLocalRountingDataSource instance;
    private static byte[] lock=new byte[0];
    //用于存储已实例的数据源map
    private static Map<Object,Object> dataSourceMap=new HashMap<Object, Object>();

    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("目前设置数据源"+DataSourceTypeManager.getDataSource());
        return DataSourceTypeManager.getDataSource();
    }
    /**
     * 设置数据源
     * @param targetDataSources
     */
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        dataSourceMap.putAll(targetDataSources);
        super.afterPropertiesSet();// 必须添加该句，否则新添加数据源无法识别到
    }

    /**
     * 获取存储已实例的数据源map
     * @return
     */
    public Map<Object, Object> getDataSourceMap() {
        return dataSourceMap;
    }

    /**
     * 单例方法
     * @return
     */
    public static synchronized ThreadLocalRountingDataSource getInstance(){
        if(instance==null){
            synchronized (lock){
                if(instance==null){
                    instance=new ThreadLocalRountingDataSource();
                }
            }
        }
        return instance;
    }

    /**
     * 是否存在当前key的 DataSource
     * @param key
     * @return 存在返回 true, 不存在返回 false
     */
    public static boolean isExistDataSource(String key) {
        return dataSourceMap.containsKey(key);
    }
}
