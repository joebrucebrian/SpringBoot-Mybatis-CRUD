package com.lty.springbootmybatiscrud.service;


import com.lty.springbootmybatiscrud.bean.Admin;
import com.lty.springbootmybatiscrud.dao.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;

    public Admin getAdmin(String adminName) {
        return adminMapper.selectByName(adminName);
    }

    public Admin getAdmin(int adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }
}
