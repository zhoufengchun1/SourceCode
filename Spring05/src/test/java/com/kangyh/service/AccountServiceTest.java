package com.kangyh.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sun.net.www.ApplicationLaunchException;

/**
 * @Auther: kangyh
 * @Date: 2019/7/28 11:54
 * @Description:
 */
public class AccountServiceTest
{
    public static void main(String[] args)
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService accountService = (IAccountService) applicationContext.getBean("AccountService");

        accountService.saveAccount();
    }
}
