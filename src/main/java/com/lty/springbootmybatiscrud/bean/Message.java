package com.lty.springbootmybatiscrud.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int code;
    private String msg;
    private Map<String,Object> extend = new HashMap<>();
    public static Message success(){
        Message result = new Message();
        result.setCode(100);
        result.setMsg("处理成功！");
        return result;
    }

    public static Message fail(){
        Message result = new Message();
        result.setCode(200);
        result.setMsg("处理失败！");
        return result;
    }

    public Message add(String key, Object value){
        this.getExtend().put(key,value);
        return this;
    }
}
