package com.kangyh.config;

import com.kangyh.dao.IAccountDao;
import com.kangyh.dao.impl.AccountDaoImpl;
import com.kangyh.service.impl.AccountServiceImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

/**
 * @Auther: kangyh
 * @Date: 2019/7/26 11:50
 * @Description:
 */
@Configuration
@ComponentScan("com.kangyh")
public class SpringConfiguration
{
    @Bean(name = "runner")
    public QueryRunner createQueryRunner(DataSource dataSource)
    {
        return new QueryRunner(dataSource);
    }

    @Bean(name = "dataSource")
    public DataSource createDataSource()
    {
        try
        {
            ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test?characterEncoding=utf-8" +
                    "&useSSL=true&serverTimezone=UTC & rewriteBatchedStatements=true");
            comboPooledDataSource.setUser("root");
            comboPooledDataSource.setPassword("admin");
            return comboPooledDataSource;

        } catch (PropertyVetoException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
