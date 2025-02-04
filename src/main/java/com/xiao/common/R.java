package com.xiao.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R {
    private int code;
    private String msg;
    private Object data;
    public static R success() {
        return new R(200, "成功!", null);
    }
    public static R success(Object data) {
        return new R(200, "成功!", data);
    }
    public static R fail() {
        return new R(500, "失败!", null);
    }
    public static R fail(Object data) {
        return new R(500, "失败!", data);
    }
}
