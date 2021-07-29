package com.fujias.qwmini.results;

/**
 * 临时登录凭证校验接口的返回参数接收对象
 * @author YU
 */
public class Code2SessionResult {

    private String corpid;
    private String userid;
    private String session_key;
    private int errcode;
    private String errmsg;

    public Code2SessionResult(String corpid) {
        this.corpid = corpid;
    }

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
