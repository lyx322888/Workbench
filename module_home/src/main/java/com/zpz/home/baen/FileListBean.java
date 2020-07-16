package com.zpz.home.baen;

import java.util.List;

public class FileListBean {

    /**
     * msg : 成功
     * state : 1
     * data : [{"company_id":204,"company_name":"震荡城有有fdfds","logo":"","status":0,"record_number":"","ratings":""}]
     */

    private String msg;
    private int state;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * company_id : 204
         * company_name : 震荡城有有fdfds
         * logo :
         * status : 0
         * record_number :
         * ratings :
         */

        private long company_id;
        private String company_name;
        private String logo;
        private int status;
        private String record_number;
        private String ratings;

        public String getReason_refusal() {
            return reason_refusal;
        }

        public void setReason_refusal(String reason_refusal) {
            this.reason_refusal = reason_refusal;
        }

        private String reason_refusal;

        public long getCompany_id() {
            return company_id;
        }

        public void setCompany_id(long company_id) {
            this.company_id = company_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRecord_number() {
            return record_number;
        }

        public void setRecord_number(String record_number) {
            this.record_number = record_number;
        }

        public String getRatings() {
            return ratings;
        }

        public void setRatings(String ratings) {
            this.ratings = ratings;
        }
    }
}
