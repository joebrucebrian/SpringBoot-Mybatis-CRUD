package com.lty.springbootmybatiscrud.service;


import com.lty.springbootmybatiscrud.bean.Department;
import com.lty.springbootmybatiscrud.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;
    public List<Department> getAll(){
        return departmentMapper.selectByExample(null);
    }
}
