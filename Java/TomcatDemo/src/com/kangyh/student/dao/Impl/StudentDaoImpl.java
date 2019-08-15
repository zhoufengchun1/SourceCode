package com.kangyh.student.dao.Impl;

import com.kangyh.student.dao.IStudentDao;
import com.kangyh.student.entity.Student;

import java.util.List;

public class StudentDaoImpl implements IStudentDao
{


    @Override
    public boolean addStudent(Student student)
    {
        return false;
    }

    @Override
    public boolean updateStudentBySno(int sno, Student student)
    {
        return false;
    }

    @Override
    public int getTotalCount()
    {
        return 0;
    }

    @Override
    public boolean deleteStudentBySno(int sno)
    {
        return false;
    }

    @Override
    public List<Student> queryAllStudents()
    {
        return null;
    }

    @Override
    public List<Student> queryStudentsByPage(int currentPage, int pageSize)
    {
        return null;
    }

    @Override
    public boolean isExist(int sno)
    {
        return false;
    }

    @Override
    public Student queryStudentBySno(int sno)
    {
        return null;
    }
}
