package com.kangyh.service.impl;

import com.kangyh.dao.IAccountDao;
import com.kangyh.domain.Account;
import com.kangyh.service.IAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: kangyh
 * @Date: 2019/7/26 02:10
 * @Description:
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService
{

    @Autowired
    private IAccountDao iAccountDao;


    @Override
    public List<Account> findAllAccount()
    {
        return iAccountDao.findAllAccount();
    }

    @Override
    public Account findAccountById(Integer accountId)
    {
        return iAccountDao.findAccountById(accountId);
    }

    @Override
    public void saveAccount(Account account)
    {
        iAccountDao.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account)
    {
        iAccountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Integer accountId)
    {
        iAccountDao.deleteAccount(accountId);
    }
}
