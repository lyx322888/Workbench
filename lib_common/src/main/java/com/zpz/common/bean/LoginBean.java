package com.zpz.common.bean;

public class LoginBean {

    /**
     * msg : 登录成功
     * state : 1
     * data : {"login_token":"9925d77344b16541636b206fbfb9903a"}
     */

    private String msg;
    private int state;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * login_token : 9925d77344b16541636b206fbfb9903a
         */

        private String login_token;

        public String getLogin_token() {
            return login_token;
        }

        public void setLogin_token(String login_token) {
            this.login_token = login_token;
        }
    }
}
