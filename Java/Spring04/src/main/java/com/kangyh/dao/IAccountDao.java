package com.kangyh.dao;

import com.kangyh.domain.Account;

import java.util.List;

/**
 * @Auther: kangyh
 * @Date: 2019/7/26 02:12
 * @Description:
 */
public interface IAccountDao
{
    List<Account> findAllAccount();

    Account findAccountById(Integer accountId);

    void saveAccount(Account account);

    void updateAccount(Account account);

    void deleteAccount(Integer accountId);
}
