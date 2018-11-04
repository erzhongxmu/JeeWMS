package com.zzjee.wmutil;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
@JsonAutoDetect
public class customerResult {

    /**
     * result : [{"cu_lastdate":"2018-04-27 13:47:19.0","cu_city":null,"cu_auditdate":"2018-08-30 09:38:07.0","cu_code":"C18040020","cu_taxid":null,"cu_initdate":null,"cu_shcustname":"京东科技有限公司2","cu_auditstatuscode":"AUDITED","cu_ccoefficient":null,"cu_payments":"现金","cu_currency":"RMB","cu_degree":null,"cu_postalcode":null,"cu_paymentscode":"2018040004","cu_tcoefficient":null,"cu_rate":"1","cu_mobile":"13937682134","cu_agenttype":null,"cu_b2benable":"0","cu_district":null,"cu_enablecredit":"否","cu_country":null,"cu_businesscode":"910000000000MS","cu_id":"67040","cu_pricetype":null,"cu_email":"13937682134@163.com","cu_shcustcode":"C18040020","cu_qualitycode":null,"cu_uu":null,"cu_name":"京东科技有限公司2","cu_contact":"韩雪","cu_shipmentremark":null,"cu_relatedparty":null,"cu_arcode":"C18040020","cu_status":"长期","cu_tel":null,"cu_auditstatus":"已审核","cu_paymentid":"32559","cu_statuscode":null,"cu_add1":null,"cu_add2":null,"cu_webserver":null,"cu_lawman":null,"cu_credit":"0","cu_province":null,"cu_sellerid":"1029591","cu_auditman":"管理员","cu_taxrate":"16","cu_invoicetype":null,"cu_sellername":"韩雪艳","cu_fax":null,"cu_servicename":null,"cu_kind":"线上客户","cu_attach":null,"cu_shortname":"京东2","cu_recorddate":"2018-04-27 00:00:00.0","cu_shipment":null,"cu_bankaccount":null,"cu_source":null,"cu_recordman":"韩雪艳","cu_salegroup":null,"cu_duedays":"0","cu_bank":null,"cu_standarddate":"0","cu_monthsend":"05","cu_remark":null,"cu_arname":"京东科技有限公司2","cu_updatedate":null,"cu_regamount":null,"cu_servicecode":null,"cu_arcustcode":null,"cu_sellercode":"X0008","cu_transdate":null,"cu_businessrange":null}]
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
         * cu_lastdate : 2018-04-27 13:47:19.0
         * cu_city : null
         * cu_auditdate : 2018-08-30 09:38:07.0
         * cu_code : C18040020
         * cu_taxid : null
         * cu_initdate : null
         * cu_shcustname : 京东科技有限公司2
         * cu_auditstatuscode : AUDITED
         * cu_ccoefficient : null
         * cu_payments : 现金
         * cu_currency : RMB
         * cu_degree : null
         * cu_postalcode : null
         * cu_paymentscode : 2018040004
         * cu_tcoefficient : null
         * cu_rate : 1
         * cu_mobile : 13937682134
         * cu_agenttype : null
         * cu_b2benable : 0
         * cu_district : null
         * cu_enablecredit : 否
         * cu_country : null
         * cu_businesscode : 910000000000MS
         * cu_id : 67040
         * cu_pricetype : null
         * cu_email : 13937682134@163.com
         * cu_shcustcode : C18040020
         * cu_qualitycode : null
         * cu_uu : null
         * cu_name : 京东科技有限公司2
         * cu_contact : 韩雪
         * cu_shipmentremark : null
         * cu_relatedparty : null
         * cu_arcode : C18040020
         * cu_status : 长期
         * cu_tel : null
         * cu_auditstatus : 已审核
         * cu_paymentid : 32559
         * cu_statuscode : null
         * cu_add1 : null
         * cu_add2 : null
         * cu_webserver : null
         * cu_lawman : null
         * cu_credit : 0
         * cu_province : null
         * cu_sellerid : 1029591
         * cu_auditman : 管理员
         * cu_taxrate : 16
         * cu_invoicetype : null
         * cu_sellername : 韩雪艳
         * cu_fax : null
         * cu_servicename : null
         * cu_kind : 线上客户
         * cu_attach : null
         * cu_shortname : 京东2
         * cu_recorddate : 2018-04-27 00:00:00.0
         * cu_shipment : null
         * cu_bankaccount : null
         * cu_source : null
         * cu_recordman : 韩雪艳
         * cu_salegroup : null
         * cu_duedays : 0
         * cu_bank : null
         * cu_standarddate : 0
         * cu_monthsend : 05
         * cu_remark : null
         * cu_arname : 京东科技有限公司2
         * cu_updatedate : null
         * cu_regamount : null
         * cu_servicecode : null
         * cu_arcustcode : null
         * cu_sellercode : X0008
         * cu_transdate : null
         * cu_businessrange : null
         */

        @JsonProperty("cu_lastdate")
        private String cuLastdate;
        @JsonProperty("cu_city")
        private Object cuCity;
        @JsonProperty("cu_auditdate")
        private String cuAuditdate;
        @JsonProperty("cu_code")
        private String cuCode;
        @JsonProperty("cu_taxid")
        private Object cuTaxid;
        @JsonProperty("cu_initdate")
        private Object cuInitdate;
        @JsonProperty("cu_shcustname")
        private String cuShcustname;
        @JsonProperty("cu_auditstatuscode")
        private String cuAuditstatuscode;
        @JsonProperty("cu_ccoefficient")
        private Object cuCcoefficient;
        @JsonProperty("cu_payments")
        private String cuPayments;
        @JsonProperty("cu_currency")
        private String cuCurrency;
        @JsonProperty("cu_degree")
        private Object cuDegree;
        @JsonProperty("cu_postalcode")
        private Object cuPostalcode;
        @JsonProperty("cu_paymentscode")
        private String cuPaymentscode;
        @JsonProperty("cu_tcoefficient")
        private Object cuTcoefficient;
        @JsonProperty("cu_rate")
        private String cuRate;
        @JsonProperty("cu_mobile")
        private String cuMobile;
        @JsonProperty("cu_agenttype")
        private Object cuAgenttype;
        @JsonProperty("cu_b2benable")
        private String cuB2benable;
        @JsonProperty("cu_district")
        private Object cuDistrict;
        @JsonProperty("cu_enablecredit")
        private String cuEnablecredit;
        @JsonProperty("cu_country")
        private Object cuCountry;
        @JsonProperty("cu_businesscode")
        private String cuBusinesscode;
        @JsonProperty("cu_id")
        private String cuId;
        @JsonProperty("cu_pricetype")
        private Object cuPricetype;
        @JsonProperty("cu_email")
        private String cuEmail;
        @JsonProperty("cu_shcustcode")
        private String cuShcustcode;
        @JsonProperty("cu_qualitycode")
        private Object cuQualitycode;
        @JsonProperty("cu_uu")
        private Object cuUu;
        @JsonProperty("cu_name")
        private String cuName;
        @JsonProperty("cu_contact")
        private String cuContact;
        @JsonProperty("cu_shipmentremark")
        private Object cuShipmentremark;
        @JsonProperty("cu_relatedparty")
        private Object cuRelatedparty;
        @JsonProperty("cu_arcode")
        private String cuArcode;
        @JsonProperty("cu_status")
        private String cuStatus;
        @JsonProperty("cu_tel")
        private Object cuTel;
        @JsonProperty("cu_auditstatus")
        private String cuAuditstatus;
        @JsonProperty("cu_paymentid")
        private String cuPaymentid;
        @JsonProperty("cu_statuscode")
        private Object cuStatuscode;
        @JsonProperty("cu_add1")
        private Object cuAdd1;
        @JsonProperty("cu_add2")
        private Object cuAdd2;
        @JsonProperty("cu_webserver")
        private Object cuWebserver;
        @JsonProperty("cu_lawman")
        private Object cuLawman;
        @JsonProperty("cu_credit")
        private String cuCredit;
        @JsonProperty("cu_province")
        private Object cuProvince;
        @JsonProperty("cu_sellerid")
        private String cuSellerid;
        @JsonProperty("cu_auditman")
        private String cuAuditman;
        @JsonProperty("cu_taxrate")
        private String cuTaxrate;
        @JsonProperty("cu_invoicetype")
        private Object cuInvoicetype;
        @JsonProperty("cu_sellername")
        private String cuSellername;
        @JsonProperty("cu_fax")
        private Object cuFax;
        @JsonProperty("cu_servicename")
        private Object cuServicename;
        @JsonProperty("cu_kind")
        private String cuKind;
        @JsonProperty("cu_attach")
        private Object cuAttach;
        @JsonProperty("cu_shortname")
        private String cuShortname;
        @JsonProperty("cu_recorddate")
        private String cuRecorddate;
        @JsonProperty("cu_shipment")
        private Object cuShipment;
        @JsonProperty("cu_bankaccount")
        private Object cuBankaccount;
        @JsonProperty("cu_source")
        private Object cuSource;
        @JsonProperty("cu_recordman")
        private String cuRecordman;
        @JsonProperty("cu_salegroup")
        private Object cuSalegroup;
        @JsonProperty("cu_duedays")
        private String cuDuedays;
        @JsonProperty("cu_bank")
        private Object cuBank;
        @JsonProperty("cu_standarddate")
        private String cuStandarddate;
        @JsonProperty("cu_monthsend")
        private String cuMonthsend;
        @JsonProperty("cu_remark")
        private Object cuRemark;
        @JsonProperty("cu_arname")
        private String cuArname;
        @JsonProperty("cu_updatedate")
        private Object cuUpdatedate;
        @JsonProperty("cu_regamount")
        private Object cuRegamount;
        @JsonProperty("cu_servicecode")
        private Object cuServicecode;
        @JsonProperty("cu_arcustcode")
        private Object cuArcustcode;
        @JsonProperty("cu_sellercode")
        private String cuSellercode;
        @JsonProperty("cu_transdate")
        private Object cuTransdate;
        @JsonProperty("cu_businessrange")
        private Object cuBusinessrange;

        public String getCuLastdate() {
            return cuLastdate;
        }

        public void setCuLastdate(String cuLastdate) {
            this.cuLastdate = cuLastdate;
        }

        public Object getCuCity() {
            return cuCity;
        }

        public void setCuCity(Object cuCity) {
            this.cuCity = cuCity;
        }

        public String getCuAuditdate() {
            return cuAuditdate;
        }

        public void setCuAuditdate(String cuAuditdate) {
            this.cuAuditdate = cuAuditdate;
        }

        public String getCuCode() {
            return cuCode;
        }

        public void setCuCode(String cuCode) {
            this.cuCode = cuCode;
        }

        public Object getCuTaxid() {
            return cuTaxid;
        }

        public void setCuTaxid(Object cuTaxid) {
            this.cuTaxid = cuTaxid;
        }

        public Object getCuInitdate() {
            return cuInitdate;
        }

        public void setCuInitdate(Object cuInitdate) {
            this.cuInitdate = cuInitdate;
        }

        public String getCuShcustname() {
            return cuShcustname;
        }

        public void setCuShcustname(String cuShcustname) {
            this.cuShcustname = cuShcustname;
        }

        public String getCuAuditstatuscode() {
            return cuAuditstatuscode;
        }

        public void setCuAuditstatuscode(String cuAuditstatuscode) {
            this.cuAuditstatuscode = cuAuditstatuscode;
        }

        public Object getCuCcoefficient() {
            return cuCcoefficient;
        }

        public void setCuCcoefficient(Object cuCcoefficient) {
            this.cuCcoefficient = cuCcoefficient;
        }

        public String getCuPayments() {
            return cuPayments;
        }

        public void setCuPayments(String cuPayments) {
            this.cuPayments = cuPayments;
        }

        public String getCuCurrency() {
            return cuCurrency;
        }

        public void setCuCurrency(String cuCurrency) {
            this.cuCurrency = cuCurrency;
        }

        public Object getCuDegree() {
            return cuDegree;
        }

        public void setCuDegree(Object cuDegree) {
            this.cuDegree = cuDegree;
        }

        public Object getCuPostalcode() {
            return cuPostalcode;
        }

        public void setCuPostalcode(Object cuPostalcode) {
            this.cuPostalcode = cuPostalcode;
        }

        public String getCuPaymentscode() {
            return cuPaymentscode;
        }

        public void setCuPaymentscode(String cuPaymentscode) {
            this.cuPaymentscode = cuPaymentscode;
        }

        public Object getCuTcoefficient() {
            return cuTcoefficient;
        }

        public void setCuTcoefficient(Object cuTcoefficient) {
            this.cuTcoefficient = cuTcoefficient;
        }

        public String getCuRate() {
            return cuRate;
        }

        public void setCuRate(String cuRate) {
            this.cuRate = cuRate;
        }

        public String getCuMobile() {
            return cuMobile;
        }

        public void setCuMobile(String cuMobile) {
            this.cuMobile = cuMobile;
        }

        public Object getCuAgenttype() {
            return cuAgenttype;
        }

        public void setCuAgenttype(Object cuAgenttype) {
            this.cuAgenttype = cuAgenttype;
        }

        public String getCuB2benable() {
            return cuB2benable;
        }

        public void setCuB2benable(String cuB2benable) {
            this.cuB2benable = cuB2benable;
        }

        public Object getCuDistrict() {
            return cuDistrict;
        }

        public void setCuDistrict(Object cuDistrict) {
            this.cuDistrict = cuDistrict;
        }

        public String getCuEnablecredit() {
            return cuEnablecredit;
        }

        public void setCuEnablecredit(String cuEnablecredit) {
            this.cuEnablecredit = cuEnablecredit;
        }

        public Object getCuCountry() {
            return cuCountry;
        }

        public void setCuCountry(Object cuCountry) {
            this.cuCountry = cuCountry;
        }

        public String getCuBusinesscode() {
            return cuBusinesscode;
        }

        public void setCuBusinesscode(String cuBusinesscode) {
            this.cuBusinesscode = cuBusinesscode;
        }

        public String getCuId() {
            return cuId;
        }

        public void setCuId(String cuId) {
            this.cuId = cuId;
        }

        public Object getCuPricetype() {
            return cuPricetype;
        }

        public void setCuPricetype(Object cuPricetype) {
            this.cuPricetype = cuPricetype;
        }

        public String getCuEmail() {
            return cuEmail;
        }

        public void setCuEmail(String cuEmail) {
            this.cuEmail = cuEmail;
        }

        public String getCuShcustcode() {
            return cuShcustcode;
        }

        public void setCuShcustcode(String cuShcustcode) {
            this.cuShcustcode = cuShcustcode;
        }

        public Object getCuQualitycode() {
            return cuQualitycode;
        }

        public void setCuQualitycode(Object cuQualitycode) {
            this.cuQualitycode = cuQualitycode;
        }

        public Object getCuUu() {
            return cuUu;
        }

        public void setCuUu(Object cuUu) {
            this.cuUu = cuUu;
        }

        public String getCuName() {
            return cuName;
        }

        public void setCuName(String cuName) {
            this.cuName = cuName;
        }

        public String getCuContact() {
            return cuContact;
        }

        public void setCuContact(String cuContact) {
            this.cuContact = cuContact;
        }

        public Object getCuShipmentremark() {
            return cuShipmentremark;
        }

        public void setCuShipmentremark(Object cuShipmentremark) {
            this.cuShipmentremark = cuShipmentremark;
        }

        public Object getCuRelatedparty() {
            return cuRelatedparty;
        }

        public void setCuRelatedparty(Object cuRelatedparty) {
            this.cuRelatedparty = cuRelatedparty;
        }

        public String getCuArcode() {
            return cuArcode;
        }

        public void setCuArcode(String cuArcode) {
            this.cuArcode = cuArcode;
        }

        public String getCuStatus() {
            return cuStatus;
        }

        public void setCuStatus(String cuStatus) {
            this.cuStatus = cuStatus;
        }

        public Object getCuTel() {
            return cuTel;
        }

        public void setCuTel(Object cuTel) {
            this.cuTel = cuTel;
        }

        public String getCuAuditstatus() {
            return cuAuditstatus;
        }

        public void setCuAuditstatus(String cuAuditstatus) {
            this.cuAuditstatus = cuAuditstatus;
        }

        public String getCuPaymentid() {
            return cuPaymentid;
        }

        public void setCuPaymentid(String cuPaymentid) {
            this.cuPaymentid = cuPaymentid;
        }

        public Object getCuStatuscode() {
            return cuStatuscode;
        }

        public void setCuStatuscode(Object cuStatuscode) {
            this.cuStatuscode = cuStatuscode;
        }

        public Object getCuAdd1() {
            return cuAdd1;
        }

        public void setCuAdd1(Object cuAdd1) {
            this.cuAdd1 = cuAdd1;
        }

        public Object getCuAdd2() {
            return cuAdd2;
        }

        public void setCuAdd2(Object cuAdd2) {
            this.cuAdd2 = cuAdd2;
        }

        public Object getCuWebserver() {
            return cuWebserver;
        }

        public void setCuWebserver(Object cuWebserver) {
            this.cuWebserver = cuWebserver;
        }

        public Object getCuLawman() {
            return cuLawman;
        }

        public void setCuLawman(Object cuLawman) {
            this.cuLawman = cuLawman;
        }

        public String getCuCredit() {
            return cuCredit;
        }

        public void setCuCredit(String cuCredit) {
            this.cuCredit = cuCredit;
        }

        public Object getCuProvince() {
            return cuProvince;
        }

        public void setCuProvince(Object cuProvince) {
            this.cuProvince = cuProvince;
        }

        public String getCuSellerid() {
            return cuSellerid;
        }

        public void setCuSellerid(String cuSellerid) {
            this.cuSellerid = cuSellerid;
        }

        public String getCuAuditman() {
            return cuAuditman;
        }

        public void setCuAuditman(String cuAuditman) {
            this.cuAuditman = cuAuditman;
        }

        public String getCuTaxrate() {
            return cuTaxrate;
        }

        public void setCuTaxrate(String cuTaxrate) {
            this.cuTaxrate = cuTaxrate;
        }

        public Object getCuInvoicetype() {
            return cuInvoicetype;
        }

        public void setCuInvoicetype(Object cuInvoicetype) {
            this.cuInvoicetype = cuInvoicetype;
        }

        public String getCuSellername() {
            return cuSellername;
        }

        public void setCuSellername(String cuSellername) {
            this.cuSellername = cuSellername;
        }

        public Object getCuFax() {
            return cuFax;
        }

        public void setCuFax(Object cuFax) {
            this.cuFax = cuFax;
        }

        public Object getCuServicename() {
            return cuServicename;
        }

        public void setCuServicename(Object cuServicename) {
            this.cuServicename = cuServicename;
        }

        public String getCuKind() {
            return cuKind;
        }

        public void setCuKind(String cuKind) {
            this.cuKind = cuKind;
        }

        public Object getCuAttach() {
            return cuAttach;
        }

        public void setCuAttach(Object cuAttach) {
            this.cuAttach = cuAttach;
        }

        public String getCuShortname() {
            return cuShortname;
        }

        public void setCuShortname(String cuShortname) {
            this.cuShortname = cuShortname;
        }

        public String getCuRecorddate() {
            return cuRecorddate;
        }

        public void setCuRecorddate(String cuRecorddate) {
            this.cuRecorddate = cuRecorddate;
        }

        public Object getCuShipment() {
            return cuShipment;
        }

        public void setCuShipment(Object cuShipment) {
            this.cuShipment = cuShipment;
        }

        public Object getCuBankaccount() {
            return cuBankaccount;
        }

        public void setCuBankaccount(Object cuBankaccount) {
            this.cuBankaccount = cuBankaccount;
        }

        public Object getCuSource() {
            return cuSource;
        }

        public void setCuSource(Object cuSource) {
            this.cuSource = cuSource;
        }

        public String getCuRecordman() {
            return cuRecordman;
        }

        public void setCuRecordman(String cuRecordman) {
            this.cuRecordman = cuRecordman;
        }

        public Object getCuSalegroup() {
            return cuSalegroup;
        }

        public void setCuSalegroup(Object cuSalegroup) {
            this.cuSalegroup = cuSalegroup;
        }

        public String getCuDuedays() {
            return cuDuedays;
        }

        public void setCuDuedays(String cuDuedays) {
            this.cuDuedays = cuDuedays;
        }

        public Object getCuBank() {
            return cuBank;
        }

        public void setCuBank(Object cuBank) {
            this.cuBank = cuBank;
        }

        public String getCuStandarddate() {
            return cuStandarddate;
        }

        public void setCuStandarddate(String cuStandarddate) {
            this.cuStandarddate = cuStandarddate;
        }

        public String getCuMonthsend() {
            return cuMonthsend;
        }

        public void setCuMonthsend(String cuMonthsend) {
            this.cuMonthsend = cuMonthsend;
        }

        public Object getCuRemark() {
            return cuRemark;
        }

        public void setCuRemark(Object cuRemark) {
            this.cuRemark = cuRemark;
        }

        public String getCuArname() {
            return cuArname;
        }

        public void setCuArname(String cuArname) {
            this.cuArname = cuArname;
        }

        public Object getCuUpdatedate() {
            return cuUpdatedate;
        }

        public void setCuUpdatedate(Object cuUpdatedate) {
            this.cuUpdatedate = cuUpdatedate;
        }

        public Object getCuRegamount() {
            return cuRegamount;
        }

        public void setCuRegamount(Object cuRegamount) {
            this.cuRegamount = cuRegamount;
        }

        public Object getCuServicecode() {
            return cuServicecode;
        }

        public void setCuServicecode(Object cuServicecode) {
            this.cuServicecode = cuServicecode;
        }

        public Object getCuArcustcode() {
            return cuArcustcode;
        }

        public void setCuArcustcode(Object cuArcustcode) {
            this.cuArcustcode = cuArcustcode;
        }

        public String getCuSellercode() {
            return cuSellercode;
        }

        public void setCuSellercode(String cuSellercode) {
            this.cuSellercode = cuSellercode;
        }

        public Object getCuTransdate() {
            return cuTransdate;
        }

        public void setCuTransdate(Object cuTransdate) {
            this.cuTransdate = cuTransdate;
        }

        public Object getCuBusinessrange() {
            return cuBusinessrange;
        }

        public void setCuBusinessrange(Object cuBusinessrange) {
            this.cuBusinessrange = cuBusinessrange;
        }
    }
}
