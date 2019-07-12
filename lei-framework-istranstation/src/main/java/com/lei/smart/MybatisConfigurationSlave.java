package com.lei.smart;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@Import({ DataBaseConfiguration.class})
@MapperScan(basePackages="com.lei.smart.slavemapper",sqlSessionFactoryRef = "sqlSessionFactory2")
public class MybatisConfigurationSlave {
 
 
	    
	 @Resource(name = DataSources.SLAVE)
	 private DataSource slaveDataSource;



    @Bean
    public SqlSessionFactory sqlSessionFactory2() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(slaveDataSource);
//        factoryBean.setTypeAliasesPackage("com.zyzs.microservice.validate.domain");
        //指定mapper xml目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        factoryBean.setMapperLocations(resolver.getResources("classpath:com/dingjianlei/webchatservice/mapper/*.xml"));
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().
                        getResources("classpath*:slavemapper/*.xml"));
        factoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return factoryBean.getObject();

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate2() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory2()); // 使用上面配置的Factory
        return template;
    }
}
