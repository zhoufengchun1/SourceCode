package com.kangyh.dao.impl;

import com.kangyh.dao.IAccountDao;
import com.kangyh.domain.Account;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: kangyh
 * @Date: 2019/7/26 02:15
 * @Description:
 */
@Component("accountDao")
public class AccountDaoImpl implements IAccountDao
{
    @Autowired
    private QueryRunner queryRunner;


    @Override
    public List<Account> findAllAccount()
    {
        try
        {
            return queryRunner.query("select * from account", new BeanListHandler<Account>(Account.class));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account findAccountById(Integer accountId)
    {
        try
        {
            return queryRunner.query("select * from account where id=?", new BeanHandler<Account>(Account.class), accountId);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveAccount(Account account)
    {
        try
        {
            queryRunner.update("insert into account(name,money)values(?,?)", account.getName(), account.getMoney());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(Account account)
    {
        try
        {
            queryRunner.update("update account set name=?,money=? where id=?", account.getName(), account.getMoney(), account.getId());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(Integer accountId)
    {
        try
        {
            queryRunner.update("delete from account where id=?", accountId);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
