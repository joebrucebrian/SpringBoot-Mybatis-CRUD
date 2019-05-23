package com.lty.springbootmybatiscrud.controller;

import com.lty.springbootmybatiscrud.bean.Admin;
import com.lty.springbootmybatiscrud.bean.Message;
import com.lty.springbootmybatiscrud.service.AdminService;
import com.lty.springbootmybatiscrud.utils.Captcha;
import com.lty.springbootmybatiscrud.utils.FunctionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.Map;
@SessionAttributes(names = {"concurrentAdmin"})
@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    @ResponseBody
    @RequestMapping("/drawCheckCode")
    public void drawCheckCode(HttpServletResponse resp, HttpServletRequest req)throws Exception{
        //设置输出类型为一张图片
        resp.setContentType("image/jpg");
        //设置宽和高
        int width = 100;
        int height = 30;
        //获取验证码对象
        Captcha captcha = Captcha.getInstance();
        captcha.set(width,height);
        String checkcode = captcha.generateCheckcode();
        HttpSession session = req.getSession();
        session.setAttribute("checkCode",checkcode);
        OutputStream os = resp.getOutputStream();
        //把图片以流的形式输出
        ImageIO.write(captcha.generateCheckImg(checkcode),"jpg",os);
    }

    @ResponseBody
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public Message login(Admin admin, String check, String keep, HttpServletRequest req, HttpServletResponse resp, Map<String,Object> map){
        //1.检查验证码
        String checkCode = (String) req.getSession().getAttribute("checkCode");
        if ( !checkCode.equals(check) ){
            return Message.fail().add("error","验证码输入有误！");
        }
        //2.检查密码
        Admin a = adminService.getAdmin(admin.getAdminName());
        if(a == null){
            return Message.fail().add("error","用户不存在！");
        }

        if(!a.getAdminPasswd().equals(FunctionUtils.md5Encrypt(admin.getAdminPasswd()))){
            return Message.fail().add("error","用户名或密码有误！");
        }
        //3.设置cookie
        if (keep.equals("true")){
            Cookie cookie = new Cookie("adminId",a.getAdminId().toString());
            cookie.setMaxAge(10*365*24*60*60);
            resp.addCookie(cookie);
        }
        //4.跳转
        Cookie cookie1 = new Cookie("login","in");
        resp.addCookie(cookie1);
        map.put("concurrentAdmin",a);
        return Message.success();
    }

    @RequestMapping("/choose")
    public String success(){
        return "Choose";
    }


    @RequestMapping("/adminOut")
    public String loginOut(HttpServletRequest req,HttpServletResponse resp){
        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies) {
            if(c.getName().equals("login") ||c.getName().equals("adminId")){
                c.setMaxAge(0);
                resp.addCookie(c);
            }
        }
        return "redirect:/index.jsp";
    }

    @ResponseBody
    @RequestMapping("/checkCookie")
    public Message checkCookie(HttpServletRequest req,HttpServletResponse resp,Map<String,Object> map){
        Cookie[] cookies = req.getCookies();
        Admin admin = null;
        String adminId = null;
        for (Cookie c : cookies) {
            if(c.getName().equals("adminId")){
                adminId = c.getValue();
            }
        }
        if(adminId != null){
            admin = adminService.getAdmin(Integer.parseInt(adminId));
            map.put("concurrentAdmin",admin);
            Cookie cookie = new Cookie("login","in");
            resp.addCookie(cookie);
            return Message.success();
        }
        return Message.fail();
    }


}
