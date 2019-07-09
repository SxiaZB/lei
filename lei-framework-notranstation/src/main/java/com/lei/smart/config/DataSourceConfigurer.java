package com.lei.smart.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置类,在tomcat启动时触发，在该类中生成多个数据源实例并将其注入到 ApplicationContext 中
 *
 * @author dingjianlei@xdf.cn
 */

@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
@MapperScan(basePackages="com.lei.smart.mapper",sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfigurer {
    //日志logger句柄

    private final Logger logger = LoggerFactory.getLogger(getClass());

    //自动注入环境类，用于获取配置文件的属性值
    @Autowired
    private Environment evn;
    @Autowired
    private MybatisProperties mybatisProperties;
    // 数据源
    private DataSource defaultDataSource;
    private Map<String, DataSource> customDataSources = new HashMap<>();
    public DataSourceConfigurer(MybatisProperties properties) {
        this.mybatisProperties = properties;
    }


    /**
     * 创建数据源对象
     *
     * @param dbType 数据库类型
     * @return data source
     */
    private DruidDataSource createDataSource(String dbType) {
        //如果不指定数据库类型，则使用默认数据库连接
        String dbName = dbType.trim().isEmpty() ? "default" : dbType.trim();
        DruidDataSource dataSource = new DruidDataSource();
        String prefix = "spring.datasource." + dbName + ".";
        StringBuffer dbUrl = new StringBuffer();
        dbUrl.append(evn.getProperty(prefix + "url"));
        logger.info("+++default默认数据库连接url = " + dbUrl);
        dataSource.setUrl(dbUrl.toString());
        dataSource.setUsername(evn.getProperty(prefix + "username"));
        dataSource.setPassword(evn.getProperty(prefix + "password"));
        dataSource.setDriverClassName(evn.getProperty(prefix + "driver-class-name"));
        return dataSource;
    }

    /**
     * spring boot 启动后将自定义创建好的数据源对象放到TargetDataSources中用于后续的切换数据源用
     * (比如：DynamicDataSourceContextHolder.setDataSourceKey("")，
     * 同时指定默认数据源连接
     *
     * @return 动态数据源对象
     */
    @Bean
    @Primary
    public ThreadLocalRountingDataSource dynamicDataSource() {
        //获取动态数据库的实例（单例方式）
        ThreadLocalRountingDataSource dynamicDataSource = ThreadLocalRountingDataSource.getInstance();
        //创建默认数据库连接对象
        DruidDataSource defaultDataSource = createDataSource(DataSources.MASTER);
        //创建SLAVE数据库连接对象
        DruidDataSource slaveDataSource = createDataSource(DataSources.SLAVE);

        Map<Object, Object> map = new HashMap<>();
        //自定义数据源key值，将创建好的数据源对象，赋值到targetDataSources中,用于切换数据源时指定对应key即可切换
        map.put(DataSources.MASTER, defaultDataSource);
        this.defaultDataSource=defaultDataSource;
        map.put(DataSources.SLAVE, slaveDataSource);
        customDataSources.put(DataSources.MASTER,defaultDataSource);

        customDataSources.put(DataSources.SLAVE,slaveDataSource);
        dynamicDataSource.setTargetDataSources(map);
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);

        return dynamicDataSource;
    }

    /**
     * 　配置mybatis的sqlSession连接动态数据源
     *
     * @param dynamicDataSource
     * @return
     * @throws Exception
     */
   @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource());
        bean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
//mybatis配置文件自动注入失败了 不知道为啥 先凑合着用吧
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().
                        getResources("classpath*:mapper/*.xml"));
//        bean.setTransactionFactory();
        bean.setTransactionFactory(new MultiDataSourceTransactionFactory());
        bean.setConfiguration(mybatisProperties.getConfiguration());
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 将动态数据源添加到事务管理器中，并生成新的bean
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }


}