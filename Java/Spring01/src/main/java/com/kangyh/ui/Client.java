package com.kangyh.ui;

import com.kangyh.service.IAccountService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Auther: kangYh
 * @Date: 2019/7/25 13:00
 * @Description:
 */
public class Client
{
    public static void main(String[] args)
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");

        IAccountService accountService1 = (IAccountService) applicationContext.getBean("accountService2");

        System.out.println(accountService1.toString());
    }
}
