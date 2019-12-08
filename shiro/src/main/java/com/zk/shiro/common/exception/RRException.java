package com.zk.shiro.common.exception;

/**
 * 自定义异常
 */
public class RRException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;
    private Object[] args;

    public RRException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RRException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RRException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RRException(int code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public RRException(String msg, Object... args) {
        super(msg);
        this.msg = msg;
        this.args = args;
    }

    public RRException(String msg, Throwable e, Object... args) {
        super(msg, e);
        this.msg = msg;
        this.args = args;
    }

    public RRException(int code, String msg, Object... args) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.args = args;
    }

    public RRException(int code, String msg, Throwable e, Object... args) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
        this.args = args;
    }

    /**
     * 国际化时，此方法返回翻译前的字段
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

}
