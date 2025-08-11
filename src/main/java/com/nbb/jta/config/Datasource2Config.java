package com.nbb.jta.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.kingbase8.xa.KBXADataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author 胡鹏
 */
@Configuration
@MapperScan(
        basePackages = "com.nbb.jta.mapper.business2",
        sqlSessionFactoryRef = "business2SqlSessionFactory",
        sqlSessionTemplateRef = "business2SqlSessionTemplate")
public class Datasource2Config {

    @Value("${spring.datasource.business2.unique-resource-name}")
    private String uniqueResourceName;

    @Value("${spring.datasource.business2.url}")
    private String url;

    @Value("${spring.datasource.business2.username}")
    private String username;

    @Value("${spring.datasource.business2.password}")
    private String password;

    @Value("${spring.datasource.business2.driver-class-name}")
    private String driverClassName;



    // 配置主数据源（JTA兼容）
    @Profile("kingbase")
    @Bean(name = "business2DataSource")
    public DataSource kingbaseDataSource() throws SQLException {
        KBXADataSource xaDatasource = new KBXADataSource();
        xaDatasource.setUrl(url);
        xaDatasource.setUser(username);
        xaDatasource.setPassword(password);

        // 包装为Atomikos数据源（JTA事务管理）
        AtomikosDataSourceBean atomikosDataSource = new AtomikosDataSourceBean();
        atomikosDataSource.setXaDataSource(xaDatasource);
        atomikosDataSource.setUniqueResourceName(uniqueResourceName);
        return atomikosDataSource;
    }

    // 配置主数据源（JTA兼容）
    @Profile("mysql")
    @Bean(name = "business2DataSource")
    public DataSource mysqlDataSource() throws SQLException {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setUrl(url);
        mysqlXADataSource.setUser(username);
        mysqlXADataSource.setPassword(password);

        // 包装为Atomikos数据源（JTA事务管理）
        AtomikosDataSourceBean atomikosDataSource = new AtomikosDataSourceBean();
        atomikosDataSource.setXaDataSource(mysqlXADataSource);
        atomikosDataSource.setUniqueResourceName(uniqueResourceName);
        return atomikosDataSource;
    }

    // MyBatis-Plus插件配置（分页、乐观锁等）
    @Bean(name = "business2MybatisPlusInterceptor")
    public MybatisPlusInterceptor business2MybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件（核心功能）
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 可添加其他插件（如乐观锁：new OptimisticLockerInnerInterceptor()）
        return interceptor;
    }

    // 主库SqlSessionFactory
    @Bean(name = "business2SqlSessionFactory")
    public SqlSessionFactory business2SqlSessionFactory(@Qualifier("business2DataSource") DataSource dataSource,
                                                        @Qualifier("business2MybatisPlusInterceptor") MybatisPlusInterceptor interceptor) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // 配置MyBatis-Plus
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);  // 下划线转驼峰
        configuration.addInterceptor(interceptor);  // 添加插件
        factoryBean.setConfiguration(configuration);

        // 配置MyBatis mapper位置（根据实际路径调整）
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/business2/*.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "business2SqlSessionTemplate")
    public SqlSessionTemplate business2SqlSessionTemplate(
            @Qualifier("business2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
