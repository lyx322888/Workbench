package com.zpz.home.baen;

import java.util.List;

public class ListBonusBean {

    /**
     * msg : 成功
     * state : 1
     * data : {"all_bonus":13,"worker_user_money_log":[{"create_time":"2020-07-13 17:24:44","money":"10.00","status":1,"company_name":"百度公司","logo":""},{"create_time":"2020-07-13 17:25:09","money":"3.00","status":1,"company_name":"百度公司","logo":""}]}
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
         * all_bonus : 13
         * worker_user_money_log : [{"create_time":"2020-07-13 17:24:44","money":"10.00","status":1,"company_name":"百度公司","logo":""},{"create_time":"2020-07-13 17:25:09","money":"3.00","status":1,"company_name":"百度公司","logo":""}]
         */

        private String all_bonus;
        private List<WorkerUserMoneyLogBean> worker_user_money_log;

        public String getAll_bonus() {
            return all_bonus;
        }

        public void setAll_bonus(String all_bonus) {
            this.all_bonus = all_bonus;
        }

        public List<WorkerUserMoneyLogBean> getWorker_user_money_log() {
            return worker_user_money_log;
        }

        public void setWorker_user_money_log(List<WorkerUserMoneyLogBean> worker_user_money_log) {
            this.worker_user_money_log = worker_user_money_log;
        }

        public static class WorkerUserMoneyLogBean {
            /**
             * create_time : 2020-07-13 17:24:44
             * money : 10.00
             * status : 1
             * company_name : 百度公司
             * logo :
             */

            private String create_time;
            private String money;
            private int status;
            private String company_name;
            private String logo;

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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
        }
    }
}
