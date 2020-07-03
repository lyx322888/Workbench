package com.zpz.home.baen;

import java.util.List;

public class CommissionItemBean {

    /**
     * msg : 成功
     * state : 1
     * data : [{"first_assess_id":1,"company_name":"测试公司","logo":"http://imgs.wbx365.com/18046040853_1546066627.jpg","create_time":"2020-07-02 10:07:03","mobile":"211451423545","company_id":208},{"first_assess_id":2,"company_name":"测试公司22222","logo":"http://imgs.wbx365.com/18046040853_1546066627.jpg","create_time":"2020-07-01 17:32:53","mobile":"211451423545","company_id":0},{"first_assess_id":3,"company_name":"测试公司3333333","logo":"http://imgs.wbx365.com/18046040853_1546066627.jpg","create_time":"2020-07-01 15:44:50","mobile":"211451423545","company_id":0}]
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
         * first_assess_id : 1
         * company_name : 测试公司
         * logo : http://imgs.wbx365.com/18046040853_1546066627.jpg
         * create_time : 2020-07-02 10:07:03
         * mobile : 211451423545
         * company_id : 208
         */

        private int first_assess_id;
        private String company_name;
        private String logo;
        private String create_time;
        private String mobile;
        private int company_id;

        public int getFirst_assess_id() {
            return first_assess_id;
        }

        public void setFirst_assess_id(int first_assess_id) {
            this.first_assess_id = first_assess_id;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }
    }
}
