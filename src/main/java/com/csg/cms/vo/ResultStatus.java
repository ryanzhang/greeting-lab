package com.csg.cms.vo;

public enum ResultStatus {

    SUCCESS(200, "操作成功"),

    FAILED(500, "操作失败"),

    BIZ_FAILED(501, "业务处理发生异常"),

    FALLBACK(444, "依赖服务不可用"),

    VALIDATE_FAILED(404, "参数检验失败"),

    LOGIN_FAIL(401, "登录失败，用户名或密码不正确"),

    UN_AUTHENTICATION(402, "尚未登录或token已过期"),

    FORBIDDEN(403, "没有相关权限");

    private int code;

    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}