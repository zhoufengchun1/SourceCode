package homework;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

class Bank  //3-37
{
    private Account account;
    private ArrayList userList;

    public Bank(Account account)
    {
        this.account = account;
    }

    public boolean createAccount()
    {
        if (userList.contains(account.getUserName()))
        {
            return false;
        }
        else
        {
            account.setDate(new Date());
            userList.add(account.getUserName());
            return true;
        }
    }

    public boolean saveMoney(double money)
    {
        if (userList.contains(account))
        {
            account.setBalance(money,'+');
            return true;
        }
        else
            return false;
    }

    public boolean withDrawals(double money)
    {
        if (userList.contains(account))
        {
            if (account.getBalance() - money < 0)
            {
                return false;
            }
            else
            {
                account.setBalance(money, '-');
                return true;
            }
        }
        return true;
    }

    public boolean search(Account account)
    {
        if (!userList.contains(account))
        {
            return false;
        }
        else
        {
            ListIterator listIterator = userList.listIterator();
            while (listIterator.hasNext())
            {
                if (listIterator.equals(account))
                {
                    System.out.println("User exists\n" + account.getBalance());
                    System.out.println(account.getDate());
                    System.out.println(account.getName());
                    System.out.println(account.getUserName());
                    System.out.println(account.getId());
                    break;
                }
            }
        }
        return true;
    }

    public boolean deleteUser(Account account)
    {
        if(!userList.contains(account))
            return false;
        else
        {
            userList.remove(account);
            return true;
        }
    }
}
class Account
{
    private String name;
    private double balance=0;
    private String id;
    private Date date;
    private String userName;

    public String getName()
    {
        return name;
    }

    public double getBalance()
    {
        return balance;
    }

    public String getId()
    {
        return id;
    }

    public Date getDate()
    {
        return date;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setBalance(double money, char ch)
    {
        if (ch == '+')
        {
            balance+=money;
        }
        else if (ch == '-')
        {
            balance-=money;
        }

    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}