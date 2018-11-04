package com.zzjee.wmutil;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
@JsonAutoDetect
public class vendorResult {

    /**
     * result : [{"ve_custname":null,"ve_id":"97006","ve_duedays":"0","ve_b2benable":"0","ve_auditstatuscode":"AUDITED","ve_buyercode":"X0110","ve_monthsend":"无","ve_remark":null,"ve_buyeruu":"2000001138","ve_billtype":"普通发票","ve_priceterm":null,"ve_tel":"无","ve_fax":null,"ve_uu":null,"ve_initdate":"2018-08-22 00:00:00.0","ve_code":"GN0184","ve_add2":null,"ve_add1":"广东","ve_attach":null,"ve_shipment":null,"ve_level":null,"ve_nsrzh":null,"ve_bank":null,"ve_mobile":"无","ve_webserver":"123","ve_contact":"周诗妙","ve_buyername":"卢宁","ve_industry":"设计","ve_currency":"RMB","ve_auditstatus":"已审核","ve_custcode":null,"ve_onecurr":"0","ve_bankcode":null,"ve_updatedate":null,"ve_credit":null,"ve_ifdeliveryonb2b":"0","ve_businessrange":null,"ve_kind":"国内","ve_abc":null,"ve_apvendname":"广东川木品牌策划有限公司","ve_source":"手工新增","ve_b2bcheck":"0","ve_bankman":null,"ve_transdate":null,"ve_recordname":"卢宁","ve_isperformance":null,"ve_cop":null,"ve_style":"GENERAL","ve_sourceid":null,"ve_taxrate":"16","ve_degree":null,"ve_paymentcode":"1001","ve_email":"无","ve_apvendcode":"GN0184","ve_buyerid":"1029693","ve_payment":"现金","ve_bankaccount":null,"ve_regcapital":null,"ve_legalman":null,"ve_name":"广东川木品牌策划有限公司","ve_bankaddress":null,"ve_shortname":"川木"}]
     * success : true
     * resCode : 00
     */

    @JsonProperty("success")
    private boolean success;
    @JsonProperty("resCode")
    private String resCode;
    @JsonProperty("result")
    private List<ResultBean> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * ve_custname : null
         * ve_id : 97006
         * ve_duedays : 0
         * ve_b2benable : 0
         * ve_auditstatuscode : AUDITED
         * ve_buyercode : X0110
         * ve_monthsend : 无
         * ve_remark : null
         * ve_buyeruu : 2000001138
         * ve_billtype : 普通发票
         * ve_priceterm : null
         * ve_tel : 无
         * ve_fax : null
         * ve_uu : null
         * ve_initdate : 2018-08-22 00:00:00.0
         * ve_code : GN0184
         * ve_add2 : null
         * ve_add1 : 广东
         * ve_attach : null
         * ve_shipment : null
         * ve_level : null
         * ve_nsrzh : null
         * ve_bank : null
         * ve_mobile : 无
         * ve_webserver : 123
         * ve_contact : 周诗妙
         * ve_buyername : 卢宁
         * ve_industry : 设计
         * ve_currency : RMB
         * ve_auditstatus : 已审核
         * ve_custcode : null
         * ve_onecurr : 0
         * ve_bankcode : null
         * ve_updatedate : null
         * ve_credit : null
         * ve_ifdeliveryonb2b : 0
         * ve_businessrange : null
         * ve_kind : 国内
         * ve_abc : null
         * ve_apvendname : 广东川木品牌策划有限公司
         * ve_source : 手工新增
         * ve_b2bcheck : 0
         * ve_bankman : null
         * ve_transdate : null
         * ve_recordname : 卢宁
         * ve_isperformance : null
         * ve_cop : null
         * ve_style : GENERAL
         * ve_sourceid : null
         * ve_taxrate : 16
         * ve_degree : null
         * ve_paymentcode : 1001
         * ve_email : 无
         * ve_apvendcode : GN0184
         * ve_buyerid : 1029693
         * ve_payment : 现金
         * ve_bankaccount : null
         * ve_regcapital : null
         * ve_legalman : null
         * ve_name : 广东川木品牌策划有限公司
         * ve_bankaddress : null
         * ve_shortname : 川木
         */

        @JsonProperty("ve_custname")
        private Object veCustname;
        @JsonProperty("ve_id")
        private String veId;
        @JsonProperty("ve_duedays")
        private String veDuedays;
        @JsonProperty("ve_b2benable")
        private String veB2benable;
        @JsonProperty("ve_auditstatuscode")
        private String veAuditstatuscode;
        @JsonProperty("ve_buyercode")
        private String veBuyercode;
        @JsonProperty("ve_monthsend")
        private String veMonthsend;
        @JsonProperty("ve_remark")
        private Object veRemark;
        @JsonProperty("ve_buyeruu")
        private String veBuyeruu;
        @JsonProperty("ve_billtype")
        private String veBilltype;
        @JsonProperty("ve_priceterm")
        private Object vePriceterm;
        @JsonProperty("ve_tel")
        private String veTel;
        @JsonProperty("ve_fax")
        private Object veFax;
        @JsonProperty("ve_uu")
        private Object veUu;
        @JsonProperty("ve_initdate")
        private String veInitdate;
        @JsonProperty("ve_code")
        private String veCode;
        @JsonProperty("ve_add2")
        private Object veAdd2;
        @JsonProperty("ve_add1")
        private String veAdd1;
        @JsonProperty("ve_attach")
        private Object veAttach;
        @JsonProperty("ve_shipment")
        private Object veShipment;
        @JsonProperty("ve_level")
        private Object veLevel;
        @JsonProperty("ve_nsrzh")
        private Object veNsrzh;
        @JsonProperty("ve_bank")
        private Object veBank;
        @JsonProperty("ve_mobile")
        private String veMobile;
        @JsonProperty("ve_webserver")
        private String veWebserver;
        @JsonProperty("ve_contact")
        private String veContact;
        @JsonProperty("ve_buyername")
        private String veBuyername;
        @JsonProperty("ve_industry")
        private String veIndustry;
        @JsonProperty("ve_currency")
        private String veCurrency;
        @JsonProperty("ve_auditstatus")
        private String veAuditstatus;
        @JsonProperty("ve_custcode")
        private Object veCustcode;
        @JsonProperty("ve_onecurr")
        private String veOnecurr;
        @JsonProperty("ve_bankcode")
        private Object veBankcode;
        @JsonProperty("ve_updatedate")
        private Object veUpdatedate;
        @JsonProperty("ve_credit")
        private Object veCredit;
        @JsonProperty("ve_ifdeliveryonb2b")
        private String veIfdeliveryonb2b;
        @JsonProperty("ve_businessrange")
        private Object veBusinessrange;
        @JsonProperty("ve_kind")
        private String veKind;
        @JsonProperty("ve_abc")
        private Object veAbc;
        @JsonProperty("ve_apvendname")
        private String veApvendname;
        @JsonProperty("ve_source")
        private String veSource;
        @JsonProperty("ve_b2bcheck")
        private String veB2bcheck;
        @JsonProperty("ve_bankman")
        private Object veBankman;
        @JsonProperty("ve_transdate")
        private Object veTransdate;
        @JsonProperty("ve_recordname")
        private String veRecordname;
        @JsonProperty("ve_isperformance")
        private Object veIsperformance;
        @JsonProperty("ve_cop")
        private Object veCop;
        @JsonProperty("ve_style")
        private String veStyle;
        @JsonProperty("ve_sourceid")
        private Object veSourceid;
        @JsonProperty("ve_taxrate")
        private String veTaxrate;
        @JsonProperty("ve_degree")
        private Object veDegree;
        @JsonProperty("ve_paymentcode")
        private String vePaymentcode;
        @JsonProperty("ve_email")
        private String veEmail;
        @JsonProperty("ve_apvendcode")
        private String veApvendcode;
        @JsonProperty("ve_buyerid")
        private String veBuyerid;
        @JsonProperty("ve_payment")
        private String vePayment;
        @JsonProperty("ve_bankaccount")
        private Object veBankaccount;
        @JsonProperty("ve_regcapital")
        private Object veRegcapital;
        @JsonProperty("ve_legalman")
        private Object veLegalman;
        @JsonProperty("ve_name")
        private String veName;
        @JsonProperty("ve_bankaddress")
        private Object veBankaddress;
        @JsonProperty("ve_shortname")
        private String veShortname;

        public Object getVeCustname() {
            return veCustname;
        }

        public void setVeCustname(Object veCustname) {
            this.veCustname = veCustname;
        }

        public String getVeId() {
            return veId;
        }

        public void setVeId(String veId) {
            this.veId = veId;
        }

        public String getVeDuedays() {
            return veDuedays;
        }

        public void setVeDuedays(String veDuedays) {
            this.veDuedays = veDuedays;
        }

        public String getVeB2benable() {
            return veB2benable;
        }

        public void setVeB2benable(String veB2benable) {
            this.veB2benable = veB2benable;
        }

        public String getVeAuditstatuscode() {
            return veAuditstatuscode;
        }

        public void setVeAuditstatuscode(String veAuditstatuscode) {
            this.veAuditstatuscode = veAuditstatuscode;
        }

        public String getVeBuyercode() {
            return veBuyercode;
        }

        public void setVeBuyercode(String veBuyercode) {
            this.veBuyercode = veBuyercode;
        }

        public String getVeMonthsend() {
            return veMonthsend;
        }

        public void setVeMonthsend(String veMonthsend) {
            this.veMonthsend = veMonthsend;
        }

        public Object getVeRemark() {
            return veRemark;
        }

        public void setVeRemark(Object veRemark) {
            this.veRemark = veRemark;
        }

        public String getVeBuyeruu() {
            return veBuyeruu;
        }

        public void setVeBuyeruu(String veBuyeruu) {
            this.veBuyeruu = veBuyeruu;
        }

        public String getVeBilltype() {
            return veBilltype;
        }

        public void setVeBilltype(String veBilltype) {
            this.veBilltype = veBilltype;
        }

        public Object getVePriceterm() {
            return vePriceterm;
        }

        public void setVePriceterm(Object vePriceterm) {
            this.vePriceterm = vePriceterm;
        }

        public String getVeTel() {
            return veTel;
        }

        public void setVeTel(String veTel) {
            this.veTel = veTel;
        }

        public Object getVeFax() {
            return veFax;
        }

        public void setVeFax(Object veFax) {
            this.veFax = veFax;
        }

        public Object getVeUu() {
            return veUu;
        }

        public void setVeUu(Object veUu) {
            this.veUu = veUu;
        }

        public String getVeInitdate() {
            return veInitdate;
        }

        public void setVeInitdate(String veInitdate) {
            this.veInitdate = veInitdate;
        }

        public String getVeCode() {
            return veCode;
        }

        public void setVeCode(String veCode) {
            this.veCode = veCode;
        }

        public Object getVeAdd2() {
            return veAdd2;
        }

        public void setVeAdd2(Object veAdd2) {
            this.veAdd2 = veAdd2;
        }

        public String getVeAdd1() {
            return veAdd1;
        }

        public void setVeAdd1(String veAdd1) {
            this.veAdd1 = veAdd1;
        }

        public Object getVeAttach() {
            return veAttach;
        }

        public void setVeAttach(Object veAttach) {
            this.veAttach = veAttach;
        }

        public Object getVeShipment() {
            return veShipment;
        }

        public void setVeShipment(Object veShipment) {
            this.veShipment = veShipment;
        }

        public Object getVeLevel() {
            return veLevel;
        }

        public void setVeLevel(Object veLevel) {
            this.veLevel = veLevel;
        }

        public Object getVeNsrzh() {
            return veNsrzh;
        }

        public void setVeNsrzh(Object veNsrzh) {
            this.veNsrzh = veNsrzh;
        }

        public Object getVeBank() {
            return veBank;
        }

        public void setVeBank(Object veBank) {
            this.veBank = veBank;
        }

        public String getVeMobile() {
            return veMobile;
        }

        public void setVeMobile(String veMobile) {
            this.veMobile = veMobile;
        }

        public String getVeWebserver() {
            return veWebserver;
        }

        public void setVeWebserver(String veWebserver) {
            this.veWebserver = veWebserver;
        }

        public String getVeContact() {
            return veContact;
        }

        public void setVeContact(String veContact) {
            this.veContact = veContact;
        }

        public String getVeBuyername() {
            return veBuyername;
        }

        public void setVeBuyername(String veBuyername) {
            this.veBuyername = veBuyername;
        }

        public String getVeIndustry() {
            return veIndustry;
        }

        public void setVeIndustry(String veIndustry) {
            this.veIndustry = veIndustry;
        }

        public String getVeCurrency() {
            return veCurrency;
        }

        public void setVeCurrency(String veCurrency) {
            this.veCurrency = veCurrency;
        }

        public String getVeAuditstatus() {
            return veAuditstatus;
        }

        public void setVeAuditstatus(String veAuditstatus) {
            this.veAuditstatus = veAuditstatus;
        }

        public Object getVeCustcode() {
            return veCustcode;
        }

        public void setVeCustcode(Object veCustcode) {
            this.veCustcode = veCustcode;
        }

        public String getVeOnecurr() {
            return veOnecurr;
        }

        public void setVeOnecurr(String veOnecurr) {
            this.veOnecurr = veOnecurr;
        }

        public Object getVeBankcode() {
            return veBankcode;
        }

        public void setVeBankcode(Object veBankcode) {
            this.veBankcode = veBankcode;
        }

        public Object getVeUpdatedate() {
            return veUpdatedate;
        }

        public void setVeUpdatedate(Object veUpdatedate) {
            this.veUpdatedate = veUpdatedate;
        }

        public Object getVeCredit() {
            return veCredit;
        }

        public void setVeCredit(Object veCredit) {
            this.veCredit = veCredit;
        }

        public String getVeIfdeliveryonb2b() {
            return veIfdeliveryonb2b;
        }

        public void setVeIfdeliveryonb2b(String veIfdeliveryonb2b) {
            this.veIfdeliveryonb2b = veIfdeliveryonb2b;
        }

        public Object getVeBusinessrange() {
            return veBusinessrange;
        }

        public void setVeBusinessrange(Object veBusinessrange) {
            this.veBusinessrange = veBusinessrange;
        }

        public String getVeKind() {
            return veKind;
        }

        public void setVeKind(String veKind) {
            this.veKind = veKind;
        }

        public Object getVeAbc() {
            return veAbc;
        }

        public void setVeAbc(Object veAbc) {
            this.veAbc = veAbc;
        }

        public String getVeApvendname() {
            return veApvendname;
        }

        public void setVeApvendname(String veApvendname) {
            this.veApvendname = veApvendname;
        }

        public String getVeSource() {
            return veSource;
        }

        public void setVeSource(String veSource) {
            this.veSource = veSource;
        }

        public String getVeB2bcheck() {
            return veB2bcheck;
        }

        public void setVeB2bcheck(String veB2bcheck) {
            this.veB2bcheck = veB2bcheck;
        }

        public Object getVeBankman() {
            return veBankman;
        }

        public void setVeBankman(Object veBankman) {
            this.veBankman = veBankman;
        }

        public Object getVeTransdate() {
            return veTransdate;
        }

        public void setVeTransdate(Object veTransdate) {
            this.veTransdate = veTransdate;
        }

        public String getVeRecordname() {
            return veRecordname;
        }

        public void setVeRecordname(String veRecordname) {
            this.veRecordname = veRecordname;
        }

        public Object getVeIsperformance() {
            return veIsperformance;
        }

        public void setVeIsperformance(Object veIsperformance) {
            this.veIsperformance = veIsperformance;
        }

        public Object getVeCop() {
            return veCop;
        }

        public void setVeCop(Object veCop) {
            this.veCop = veCop;
        }

        public String getVeStyle() {
            return veStyle;
        }

        public void setVeStyle(String veStyle) {
            this.veStyle = veStyle;
        }

        public Object getVeSourceid() {
            return veSourceid;
        }

        public void setVeSourceid(Object veSourceid) {
            this.veSourceid = veSourceid;
        }

        public String getVeTaxrate() {
            return veTaxrate;
        }

        public void setVeTaxrate(String veTaxrate) {
            this.veTaxrate = veTaxrate;
        }

        public Object getVeDegree() {
            return veDegree;
        }

        public void setVeDegree(Object veDegree) {
            this.veDegree = veDegree;
        }

        public String getVePaymentcode() {
            return vePaymentcode;
        }

        public void setVePaymentcode(String vePaymentcode) {
            this.vePaymentcode = vePaymentcode;
        }

        public String getVeEmail() {
            return veEmail;
        }

        public void setVeEmail(String veEmail) {
            this.veEmail = veEmail;
        }

        public String getVeApvendcode() {
            return veApvendcode;
        }

        public void setVeApvendcode(String veApvendcode) {
            this.veApvendcode = veApvendcode;
        }

        public String getVeBuyerid() {
            return veBuyerid;
        }

        public void setVeBuyerid(String veBuyerid) {
            this.veBuyerid = veBuyerid;
        }

        public String getVePayment() {
            return vePayment;
        }

        public void setVePayment(String vePayment) {
            this.vePayment = vePayment;
        }

        public Object getVeBankaccount() {
            return veBankaccount;
        }

        public void setVeBankaccount(Object veBankaccount) {
            this.veBankaccount = veBankaccount;
        }

        public Object getVeRegcapital() {
            return veRegcapital;
        }

        public void setVeRegcapital(Object veRegcapital) {
            this.veRegcapital = veRegcapital;
        }

        public Object getVeLegalman() {
            return veLegalman;
        }

        public void setVeLegalman(Object veLegalman) {
            this.veLegalman = veLegalman;
        }

        public String getVeName() {
            return veName;
        }

        public void setVeName(String veName) {
            this.veName = veName;
        }

        public Object getVeBankaddress() {
            return veBankaddress;
        }

        public void setVeBankaddress(Object veBankaddress) {
            this.veBankaddress = veBankaddress;
        }

        public String getVeShortname() {
            return veShortname;
        }

        public void setVeShortname(String veShortname) {
            this.veShortname = veShortname;
        }
    }
}
