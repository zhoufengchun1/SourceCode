package com.kangyh.service.impl;

import com.kangyh.service.IAccountService;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Auther: kangYh
 * @Date: 2019/7/25 12:53
 * @Description:
 */
public class AccountServiceImpl implements IAccountService
{
    public String name;
    public Integer age;
    public Date birthday;
    public String[] strings;
    public Map map;

    public void setStrings(String[] strings)
    {
        this.strings = strings;
    }

    public void setMap(Map map)
    {
        this.map = map;
    }

    @Override
    public String toString()
    {
        return "AccountServiceImpl{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", strings=" + Arrays.toString(strings) +
                ", map=" + map +
                '}';
    }

    public AccountServiceImpl(String name, Integer age, Date birthday)
    {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public AccountServiceImpl()
    {
        System.out.println("对象创建了");
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    @Override
    public void saveAccount()
    {
        System.out.println("!!!");
    }
}
