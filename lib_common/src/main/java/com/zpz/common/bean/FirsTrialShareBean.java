package com.zpz.common.bean;

public class FirsTrialShareBean {

    /**
     * msg : 成功
     * state : 1
     * data : {"share_url":"http://qygs.org.cn/wap/firstassess/first_assess_one/worker_user_id/1"}
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
         * share_url : http://qygs.org.cn/wap/firstassess/first_assess_one/worker_user_id/1
         */

        private String share_url;

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }
    }
}
