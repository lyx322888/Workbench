package com.zpz.common.bean;

public class UserInfoBean {

    /**
     * msg : 成功
     * state : 1
     * data : {"work_no":"002","account":"15988889999","face":"http://image.yy.com/yywebalbumbs2bucket/144152f8680f421599233c6ffcfcef49_1476265267104.jpeg","nickname":"小方","city_name":"厦门"}
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
         * work_no : 002
         * account : 15988889999
         * face : http://image.yy.com/yywebalbumbs2bucket/144152f8680f421599233c6ffcfcef49_1476265267104.jpeg
         * nickname : 小方
         * city_name : 厦门
         */

        private String work_no;
        private String account;
        private String face;
        private String nickname;
        private String city_name;

        public String getWork_no() {
            return work_no;
        }

        public void setWork_no(String work_no) {
            this.work_no = work_no;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }
    }
}
