package com.lty.springbootmybatiscrud.service;


import com.lty.springbootmybatiscrud.bean.Employee;
import com.lty.springbootmybatiscrud.bean.EmployeeExample;
import com.lty.springbootmybatiscrud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    //返回用户列表
    public List<Employee> getAll(){
        EmployeeExample example = new EmployeeExample();
        example.setOrderByClause("emp_id");
        return employeeMapper.selectByExampleWithDept(example);
    }
    //保存用户信息
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);
    }
    //检查数据库中是否已有重复名字
    //返回true代表可用
    public boolean checkUser(String empName) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count == 0;
    }
    //  按照员工id查出员工信息
    public Employee getEmp(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    //员工更新
    public void updateEmp(Employee e) {
        employeeMapper.updateByPrimaryKeySelective(e);
    }

    //根据员工id删除员工
    public void deleteEmp(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<Integer> ids) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //delete from xxx where id in( , , )
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);

    }

}
