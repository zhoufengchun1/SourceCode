package com.kangyh.dao;

import com.kangyh.bean.Employee;

/**
 * @Auther: kangyh
 * @Date: 2019/7/30 09:46
 * @Description:
 */
public interface EmployeeMapper
{
    public Employee getEmyById(Integer id);

    public void insertEmy(Employee employee);

    public void deleteEmy(Integer id);

    public void updateEmy(Employee employee);



}
