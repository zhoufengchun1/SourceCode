package com.kangyh.dao;


import com.kangyh.bean.Department;

public interface DepartmentMapper
{
	
	public Department getDeptById(Integer id);
	
	public Department getDeptByIdPlus(Integer id);

	public Department getDeptByIdStep(Integer id);
}
