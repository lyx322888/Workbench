package com.zpz.home.baen;

public class FirstAssessCountBean {

    /**
     * msg : 成功
     * state : 1
     * data : {"count_status_zero":1,"count_status_one":0,"count_status_two":0}
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
         * count_status_zero : 1
         * count_status_one : 0
         * count_status_two : 0
         */

        private int count_status_zero;
        private int count_status_one;
        private int count_status_two;

        public int getCount_status_zero() {
            return count_status_zero;
        }

        public void setCount_status_zero(int count_status_zero) {
            this.count_status_zero = count_status_zero;
        }

        public int getCount_status_one() {
            return count_status_one;
        }

        public void setCount_status_one(int count_status_one) {
            this.count_status_one = count_status_one;
        }

        public int getCount_status_two() {
            return count_status_two;
        }

        public void setCount_status_two(int count_status_two) {
            this.count_status_two = count_status_two;
        }
    }
}
