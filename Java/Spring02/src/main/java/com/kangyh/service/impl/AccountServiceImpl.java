package com.kangyh.service.impl;

import com.kangyh.dao.IAccountDao;
import com.kangyh.service.IAccountService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Auther: kangYh
 * @Date: 2019/7/25 12:53
 * @Description:
 */
@Component("13")
public class AccountServiceImpl implements IAccountService
{
    @Resource
    private IAccountDao iAccountDao;


    @Override
    public void saveAccount()
    {
        iAccountDao.saveAccount();
    }

    @PostConstruct
    public void init()
    {
        System.out.println("初始化");
    }

    @PostConstruct
    public void destroy()
    {
        System.out.println("销毁了");
    }



}

