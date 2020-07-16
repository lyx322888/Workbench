package com.zpz.home.baen;

public class CertificateBean {

    /**
     * msg : 成功
     * state : 1
     * data : {"company_name":"微百姓公司","certificate_number":"aaaaaa","record_number":"bbbbbbb","create_time":"2020年06月18","end_time":"2020年07月02","certificate_time":"2020年07月10","certificate_text":"fdsafdksajfdklsajfdk","view_url":"http://qygs.org.cn/"}
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
         * company_name : 微百姓公司
         * certificate_number : aaaaaa
         * record_number : bbbbbbb
         * create_time : 2020年06月18
         * end_time : 2020年07月02
         * certificate_time : 2020年07月10
         * certificate_text : fdsafdksajfdklsajfdk
         * view_url : http://qygs.org.cn/
         */

        private String company_name;
        private String certificate_number;
        private String record_number;
        private String create_time;
        private String end_time;
        private String certificate_time;
        private String certificate_text;
        private String view_url;

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCertificate_number() {
            return certificate_number;
        }

        public void setCertificate_number(String certificate_number) {
            this.certificate_number = certificate_number;
        }

        public String getRecord_number() {
            return record_number;
        }

        public void setRecord_number(String record_number) {
            this.record_number = record_number;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getCertificate_time() {
            return certificate_time;
        }

        public void setCertificate_time(String certificate_time) {
            this.certificate_time = certificate_time;
        }

        public String getCertificate_text() {
            return certificate_text;
        }

        public void setCertificate_text(String certificate_text) {
            this.certificate_text = certificate_text;
        }

        public String getView_url() {
            return view_url;
        }

        public void setView_url(String view_url) {
            this.view_url = view_url;
        }
    }
}
