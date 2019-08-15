package com.kangyh.test;

import com.kangyh.config.SpringConfiguration;
import com.kangyh.domain.Account;
import com.kangyh.service.IAccountService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Auther: kangyh
 * @Date: 2019/7/26 02:36
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountServiceTest
{

    @Autowired
    private IAccountService iAccountService;

    @Test
    public void testFindAll()
    {
        List<Account> accounts = iAccountService.findAllAccount();
        for (Account account : accounts)
        {
            System.out.println(account);
        }
    }

    @Test
    public void testFindOne()
    {
        Account account = iAccountService.findAccountById(1);

        System.out.println(account);
    }

    @Test
    public void testSave()
    {
        Account account = new Account();
        account.setId(132);
        account.setMoney(615155.4644346f);
        account.setName("zhangsan");
        iAccountService.saveAccount(account);
    }

    @Test
    public void testUpdate()
    {
        Account account = iAccountService.findAccountById(2);
        account.setMoney(49.55f);
        iAccountService.updateAccount(account);
    }

    @Test
    public void testDelete()
    {
        iAccountService.deleteAccount(4);
    }
}
