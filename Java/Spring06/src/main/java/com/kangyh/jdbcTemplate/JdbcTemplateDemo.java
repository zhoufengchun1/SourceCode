package com.kangyh.jdbcTemplate;

import com.kangyh.dao.IAccountDao;
import com.kangyh.domain.Account;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: kangyh
 * @Date: 2019/8/5 18:26
 * @Description:
 */
public class JdbcTemplateDemo
{
    public static void main(String[] args)
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        IAccountDao accountDao = applicationContext.getBean("accountDao", IAccountDao.class);
        Account account = accountDao.findAccountById(1);
        System.out.println(account);
    }
}
