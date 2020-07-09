package com.zpz.common.bean;

import java.io.Serializable;
import java.util.List;

public class VersionBean {

    /**
     * msg : 成功
     * state : 1
     * data : {"is_update":1,"title":"80%的用户正在使用最新版本","message":["优化用户体验"],"is_force_update":1}
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

    public static class DataBean implements Serializable {
        /**
         * is_update : 1
         * title : 80%的用户正在使用最新版本
         * message : ["优化用户体验"]
         * is_force_update : 1
         */

        private String is_update;
        private String title;
        private int is_force_update;
        private List<String> message;

        public String getIs_update() {
            return is_update;
        }

        public void setIs_update(String is_update) {
            this.is_update = is_update;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIs_force_update() {
            return is_force_update;
        }

        public void setIs_force_update(int is_force_update) {
            this.is_force_update = is_force_update;
        }

        public List<String> getMessage() {
            return message;
        }

        public void setMessage(List<String> message) {
            this.message = message;
        }
    }
}
