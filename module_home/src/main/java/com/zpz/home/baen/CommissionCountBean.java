package com.zpz.home.baen;

public class CommissionCountBean {

    /**
     * msg : 成功
     * state : 1
     * data : {"count_type_one":3,"count_type_two":1,"count_type_three":220}
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
         * count_type_one : 3
         * count_type_two : 1
         * count_type_three : 220
         */

        private int count_type_one;
        private int count_type_two;
        private int count_type_three;

        public int getCount_type_one() {
            return count_type_one;
        }

        public void setCount_type_one(int count_type_one) {
            this.count_type_one = count_type_one;
        }

        public int getCount_type_two() {
            return count_type_two;
        }

        public void setCount_type_two(int count_type_two) {
            this.count_type_two = count_type_two;
        }

        public int getCount_type_three() {
            return count_type_three;
        }

        public void setCount_type_three(int count_type_three) {
            this.count_type_three = count_type_three;
        }
    }
}
