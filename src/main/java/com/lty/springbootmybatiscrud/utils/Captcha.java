package com.lty.springbootmybatiscrud.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 生成验证码
 */
public class Captcha {
    //验证码的宽
    private int width;
    //验证码的高
    private int height;
    //验证码的字符个数
    private int num;
    //验证码的字典
    private String code;
    //取随机数的对象
    private static final Random ran = new Random();
    //单例模式
    private static Captcha captcha;
    private Captcha(){
        code = "0123456789abcdefghijklmnopqrstuvwxyz";
        num = 4;
    }
    public synchronized static Captcha getInstance(){
        if(captcha==null){
            captcha = new Captcha();
        }
        return captcha;
    }
    //设置验证码大小位数字典
    public void set(int width,int height,int num,String code){
        this.width = width;
        this.height = height;
        this.setNum(num);
        this.setCode(code);
    }
    public void set(int width,int height){
        this.width = width;
        this.height = height;
    }
    //生成随机验证码
    public String generateCheckcode(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            sb.append(code.charAt(ran.nextInt(code.length())));
        }
        return sb.toString();
    }
    //生成图像
    public BufferedImage generateCheckImg(String checkcode){
        //创建一张图片
        BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics2D graphic = img.createGraphics();
        //画笔选择颜色
        graphic.setColor(Color.WHITE);
        //填充背景
        graphic.fillRect(0,0,width,height);
        //换成黑色
        graphic.setColor(Color.BLACK);
        //画边框
        graphic.drawRect(0,0,width-1,height-1);
        //设计一个字体
        Font font = new Font("宋体", Font.BOLD + Font.ITALIC, (int)(height*0.8));
        //把字体放进去
        graphic.setFont(font);
        //循环每个字
        for (int i = 0; i < num; i++) {
            //随机颜色
            graphic.setColor(new Color(ran.nextInt(155),ran.nextInt(255),ran.nextInt(255)));
            //字写在画布上
            graphic.drawString(String.valueOf(checkcode.charAt(i)),i*(width/num)+4,(int)(height*0.8));
        }
        //随机加一些点
        for (int i = 0; i < (width + height); i++) {
            graphic.setColor(new Color(ran.nextInt(155),ran.nextInt(255),ran.nextInt(255)));
            graphic.drawOval(ran.nextInt(width),ran.nextInt(height),1,1);
        }
        //随机加一些线
        for (int i = 0; i < 2; i++) {
            graphic.setColor(new Color(ran.nextInt(155),ran.nextInt(255),ran.nextInt(255)));
            graphic.drawLine(0,ran.nextInt(height),width,ran.nextInt(height));
        }
        return img;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
