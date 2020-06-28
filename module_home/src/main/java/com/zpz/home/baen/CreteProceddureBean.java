package com.zpz.home.baen;

import java.util.List;

public class CreteProceddureBean {

    /**
     * msg : 成功
     * state : 1
     * data : {"company_id":0,"company_name":"测试公司22222","logo":"http://imgs.wbx365.com/18046040853_1546066627.jpg","legal_person":"厄尔","registered_capital":0,"establish_date":0,"address":0,"lat":0,"lng":0,"introduce_video":[],"introduce_text":0,"introduce_img":[],"work_environment_img":[],"enterprise_honor_img":[],"keep_faith_contract_img":[]}
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
         * company_id : 0
         * company_name : 测试公司22222
         * logo : http://imgs.wbx365.com/18046040853_1546066627.jpg
         * legal_person : 厄尔
         * registered_capital : 0
         * establish_date : 0
         * address : 0
         * lat : 0
         * lng : 0
         * introduce_video : []
         * introduce_text : 0
         * introduce_img : []
         * work_environment_img : []
         * enterprise_honor_img : []
         * keep_faith_contract_img : []
         */

        private int company_id;
        private String company_name;
        private String logo;
        private String legal_person;
        private String registered_capital;
        private int establish_date;
        private String address;
        private int lat;
        private int lng;
        private String introduce_text;
        private List<String> introduce_video;
        private List<String> introduce_img;
        private List<String> work_environment_img;
        private List<String> enterprise_honor_img;
        private List<String> keep_faith_contract_img;

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
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

        public String getLegal_person() {
            return legal_person;
        }

        public void setLegal_person(String legal_person) {
            this.legal_person = legal_person;
        }

        public String getRegistered_capital() {
            return registered_capital;
        }

        public void setRegistered_capital(String registered_capital) {
            this.registered_capital = registered_capital;
        }

        public int getEstablish_date() {
            return establish_date;
        }

        public void setEstablish_date(int establish_date) {
            this.establish_date = establish_date;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public int getLng() {
            return lng;
        }

        public void setLng(int lng) {
            this.lng = lng;
        }

        public String getIntroduce_text() {
            return introduce_text;
        }

        public void setIntroduce_text(String introduce_text) {
            this.introduce_text = introduce_text;
        }

        public List<String> getIntroduce_video() {
            return introduce_video;
        }

        public void setIntroduce_video(List<String> introduce_video) {
            this.introduce_video = introduce_video;
        }

        public List<String> getIntroduce_img() {
            return introduce_img;
        }

        public void setIntroduce_img(List<String> introduce_img) {
            this.introduce_img = introduce_img;
        }

        public List<?> getWork_environment_img() {
            return work_environment_img;
        }

        public void setWork_environment_img(List<String> work_environment_img) {
            this.work_environment_img = work_environment_img;
        }

        public List<?> getEnterprise_honor_img() {
            return enterprise_honor_img;
        }

        public void setEnterprise_honor_img(List<String> enterprise_honor_img) {
            this.enterprise_honor_img = enterprise_honor_img;
        }

        public List<?> getKeep_faith_contract_img() {
            return keep_faith_contract_img;
        }

        public void setKeep_faith_contract_img(List<String> keep_faith_contract_img) {
            this.keep_faith_contract_img = keep_faith_contract_img;
        }
    }
}
