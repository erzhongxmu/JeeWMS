package com.zzjee.wmutil;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
@JsonAutoDetect
public class sdresult {


    /**
     * data : [{"pi_remark":null,"pi_class":"出货单","pi_cardid":67384,"pi_sellercode":"X0104","pi_auditdate":"2018-09-12 14:32:31","pi_freight_user":null,"pi_outamount":null,"pi_updatedate":"2018-09-12 00:00:00","pi_logisticscode":null,"pi_arcode":"GN0084","pi_outcredit":null,"pi_billstatus":null,"pi_tocode":null,"pi_invostatus":"已审核","pi_cardcode":"GN0084","pi_payment":"月结60天","pi_departmentname":"销售四部","pi_monthenddate":null,"pi_whname1_user":null,"pi_cop":null,"pi_title":"齐家兴（个人）","pi_rate":1,"pi_expresscode":null,"pi_updateman":"TEST2","pi_invostatuscode":"AUDITED","pi_chargeamount":null,"pi_statuscode":"UNPOST","detail":[{"pd_remark":null,"pd_purcoutqty":100,"pd_custprodcode":null,"pd_total":0,"pd_id":46947726,"pd_whcode":"csa12321","pd_pocode":"123123123","pd_purcinqty":0,"pd_batchid":0,"pd_piclass":"出货单","pd_bonded":0,"pd_ycheck":0,"pd_ordertotal":0,"pd_prodid":100020115,"pd_customprice":0,"pd_outqty":100,"pd_outerboxgw":0,"pd_netprice":0,"pd_bcid":null,"pd_discount":0,"pd_custproddetail":null,"pd_beipinoutqty":0,"pd_whid":31800,"pd_ordercode":"SS18090004X0104","pd_invoqty":0,"pd_price":0,"pd_outerboxnw":0,"pd_auditstatus":"已审核","pd_location":null,"pd_whname":"存手机","pd_taxrate":16,"pd_vendorrate":0,"pd_orderdetno":1,"pd_inoutno":"SD18090003","pd_batchcode":null,"pd_sendprice":0,"pd_prodmadedate":"2018-09-12 00:00:00","pd_skstatus":null,"pd_orderid":0,"pd_prodcode":"G020001","pd_description":null,"pd_inqty":0,"pd_pdno":1,"pd_snid":0,"pd_nettotal":0,"pd_taxamount":0,"pd_piid":50726895,"pd_custprodspec":null,"pd_purcprice":0}],"pi_ordertype":null,"pi_departmentcode":"XKN-XS4-001","pi_date1":null,"pi_purposename":" ","pi_fax":null,"pi_paymentcode":"1006","pi_currency":"RMB","pi_paydate":null,"pi_totalupper":"零元整","pi_receivecode":"GN0084","pi_cgycode":null,"pi_tduedate":null,"pi_whname":"存手机","pi_inoutman":null,"pi_total":0,"pi_sourcecode":"SS18090004X0104","pi_cgy":null,"pi_recordman":"TEST2","pi_billstatuscode":null,"pi_listcode":null,"pi_emcode":null,"pi_printstatuscode":"UNPRINT","pi_date":"2018-09-12 00:00:00","pi_shr":null,"pi_auditman":"TEST2","pi_recorddate":"2018-09-12 00:00:00","pi_transport":null,"pi_bcid":null,"pi_sellername":"郑琛琛","pi_whcode":"csa12321","pi_status":"未过账","pi_emname":null,"pi_printstatus":"未打印","pi_logisticscompany":null,"pi_address":"阿萨德","pi_receivename":"齐家兴（个人）","pi_packingcode":null,"pi_arname":"齐家兴（个人）","pi_id":50726895,"pi_count":null,"pi_inoutno":"SD18090003","pi_ntbamount":null}]
     * success : true
     */

    @JsonProperty("success")
    private boolean success;
    @JsonProperty("data")
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pi_remark : null
         * pi_class : 出货单
         * pi_cardid : 67384
         * pi_sellercode : X0104
         * pi_auditdate : 2018-09-12 14:32:31
         * pi_freight_user : null
         * pi_outamount : null
         * pi_updatedate : 2018-09-12 00:00:00
         * pi_logisticscode : null
         * pi_arcode : GN0084
         * pi_outcredit : null
         * pi_billstatus : null
         * pi_tocode : null
         * pi_invostatus : 已审核
         * pi_cardcode : GN0084
         * pi_payment : 月结60天
         * pi_departmentname : 销售四部
         * pi_monthenddate : null
         * pi_whname1_user : null
         * pi_cop : null
         * pi_title : 齐家兴（个人）
         * pi_rate : 1
         * pi_expresscode : null
         * pi_updateman : TEST2
         * pi_invostatuscode : AUDITED
         * pi_chargeamount : null
         * pi_statuscode : UNPOST
         * detail : [{"pd_remark":null,"pd_purcoutqty":100,"pd_custprodcode":null,"pd_total":0,"pd_id":46947726,"pd_whcode":"csa12321","pd_pocode":"123123123","pd_purcinqty":0,"pd_batchid":0,"pd_piclass":"出货单","pd_bonded":0,"pd_ycheck":0,"pd_ordertotal":0,"pd_prodid":100020115,"pd_customprice":0,"pd_outqty":100,"pd_outerboxgw":0,"pd_netprice":0,"pd_bcid":null,"pd_discount":0,"pd_custproddetail":null,"pd_beipinoutqty":0,"pd_whid":31800,"pd_ordercode":"SS18090004X0104","pd_invoqty":0,"pd_price":0,"pd_outerboxnw":0,"pd_auditstatus":"已审核","pd_location":null,"pd_whname":"存手机","pd_taxrate":16,"pd_vendorrate":0,"pd_orderdetno":1,"pd_inoutno":"SD18090003","pd_batchcode":null,"pd_sendprice":0,"pd_prodmadedate":"2018-09-12 00:00:00","pd_skstatus":null,"pd_orderid":0,"pd_prodcode":"G020001","pd_description":null,"pd_inqty":0,"pd_pdno":1,"pd_snid":0,"pd_nettotal":0,"pd_taxamount":0,"pd_piid":50726895,"pd_custprodspec":null,"pd_purcprice":0}]
         * pi_ordertype : null
         * pi_departmentcode : XKN-XS4-001
         * pi_date1 : null
         * pi_purposename :
         * pi_fax : null
         * pi_paymentcode : 1006
         * pi_currency : RMB
         * pi_paydate : null
         * pi_totalupper : 零元整
         * pi_receivecode : GN0084
         * pi_cgycode : null
         * pi_tduedate : null
         * pi_whname : 存手机
         * pi_inoutman : null
         * pi_total : 0
         * pi_sourcecode : SS18090004X0104
         * pi_cgy : null
         * pi_recordman : TEST2
         * pi_billstatuscode : null
         * pi_listcode : null
         * pi_emcode : null
         * pi_printstatuscode : UNPRINT
         * pi_date : 2018-09-12 00:00:00
         * pi_shr : null
         * pi_auditman : TEST2
         * pi_recorddate : 2018-09-12 00:00:00
         * pi_transport : null
         * pi_bcid : null
         * pi_sellername : 郑琛琛
         * pi_whcode : csa12321
         * pi_status : 未过账
         * pi_emname : null
         * pi_printstatus : 未打印
         * pi_logisticscompany : null
         * pi_address : 阿萨德
         * pi_receivename : 齐家兴（个人）
         * pi_packingcode : null
         * pi_arname : 齐家兴（个人）
         * pi_id : 50726895
         * pi_count : null
         * pi_inoutno : SD18090003
         * pi_ntbamount : null
         */

        @JsonProperty("pi_remark")
        private Object piRemark;
        @JsonProperty("pi_class")
        private String piClass;
        @JsonProperty("pi_cardid")
        private int piCardid;
        @JsonProperty("pi_sellercode")
        private String piSellercode;
        @JsonProperty("pi_auditdate")
        private String piAuditdate;
        @JsonProperty("pi_freight_user")
        private Object piFreightUser;
        @JsonProperty("pi_outamount")
        private Object piOutamount;
        @JsonProperty("pi_updatedate")
        private String piUpdatedate;
        @JsonProperty("pi_logisticscode")
        private Object piLogisticscode;
        @JsonProperty("pi_arcode")
        private String piArcode;
        @JsonProperty("pi_outcredit")
        private Object piOutcredit;
        @JsonProperty("pi_billstatus")
        private Object piBillstatus;
        @JsonProperty("pi_tocode")
        private Object piTocode;
        @JsonProperty("pi_invostatus")
        private String piInvostatus;
        @JsonProperty("pi_cardcode")
        private String piCardcode;
        @JsonProperty("pi_payment")
        private String piPayment;
        @JsonProperty("pi_departmentname")
        private String piDepartmentname;
        @JsonProperty("pi_monthenddate")
        private Object piMonthenddate;
        @JsonProperty("pi_whname1_user")
        private Object piWhname1User;
        @JsonProperty("pi_cop")
        private Object piCop;
        @JsonProperty("pi_title")
        private String piTitle;
        @JsonProperty("pi_rate")
        private int piRate;
        @JsonProperty("pi_expresscode")
        private Object piExpresscode;
        @JsonProperty("pi_updateman")
        private String piUpdateman;
        @JsonProperty("pi_invostatuscode")
        private String piInvostatuscode;
        @JsonProperty("pi_chargeamount")
        private Object piChargeamount;
        @JsonProperty("pi_statuscode")
        private String piStatuscode;
        @JsonProperty("pi_ordertype")
        private Object piOrdertype;
        @JsonProperty("pi_departmentcode")
        private String piDepartmentcode;
        @JsonProperty("pi_date1")
        private Object piDate1;
        @JsonProperty("pi_purposename")
        private String piPurposename;
        @JsonProperty("pi_fax")
        private Object piFax;
        @JsonProperty("pi_paymentcode")
        private String piPaymentcode;
        @JsonProperty("pi_currency")
        private String piCurrency;
        @JsonProperty("pi_paydate")
        private Object piPaydate;
        @JsonProperty("pi_totalupper")
        private String piTotalupper;
        @JsonProperty("pi_receivecode")
        private String piReceivecode;
        @JsonProperty("pi_cgycode")
        private Object piCgycode;
        @JsonProperty("pi_tduedate")
        private Object piTduedate;
        @JsonProperty("pi_whname")
        private String piWhname;
        @JsonProperty("pi_inoutman")
        private Object piInoutman;
        @JsonProperty("pi_total")
        private int piTotal;
        @JsonProperty("pi_sourcecode")
        private String piSourcecode;
        @JsonProperty("pi_cgy")
        private Object piCgy;
        @JsonProperty("pi_recordman")
        private String piRecordman;
        @JsonProperty("pi_billstatuscode")
        private Object piBillstatuscode;
        @JsonProperty("pi_listcode")
        private Object piListcode;
        @JsonProperty("pi_emcode")
        private Object piEmcode;
        @JsonProperty("pi_printstatuscode")
        private String piPrintstatuscode;
        @JsonProperty("pi_date")
        private String piDate;
        @JsonProperty("pi_shr")
        private Object piShr;
        @JsonProperty("pi_auditman")
        private String piAuditman;
        @JsonProperty("pi_recorddate")
        private String piRecorddate;
        @JsonProperty("pi_transport")
        private Object piTransport;
        @JsonProperty("pi_bcid")
        private Object piBcid;
        @JsonProperty("pi_sellername")
        private String piSellername;
        @JsonProperty("pi_whcode")
        private String piWhcode;
        @JsonProperty("pi_status")
        private String piStatus;
        @JsonProperty("pi_emname")
        private Object piEmname;
        @JsonProperty("pi_printstatus")
        private String piPrintstatus;
        @JsonProperty("pi_logisticscompany")
        private Object piLogisticscompany;
        @JsonProperty("pi_address")
        private String piAddress;
        @JsonProperty("pi_receivename")
        private String piReceivename;
        @JsonProperty("pi_packingcode")
        private Object piPackingcode;
        @JsonProperty("pi_arname")
        private String piArname;
        @JsonProperty("pi_id")
        private int piId;
        @JsonProperty("pi_count")
        private Object piCount;
        @JsonProperty("pi_inoutno")
        private String piInoutno;
        @JsonProperty("pi_ntbamount")
        private Object piNtbamount;
        @JsonProperty("detail")
        private List<DetailBean> detail;

        public Object getPiRemark() {
            return piRemark;
        }

        public void setPiRemark(Object piRemark) {
            this.piRemark = piRemark;
        }

        public String getPiClass() {
            return piClass;
        }

        public void setPiClass(String piClass) {
            this.piClass = piClass;
        }

        public int getPiCardid() {
            return piCardid;
        }

        public void setPiCardid(int piCardid) {
            this.piCardid = piCardid;
        }

        public String getPiSellercode() {
            return piSellercode;
        }

        public void setPiSellercode(String piSellercode) {
            this.piSellercode = piSellercode;
        }

        public String getPiAuditdate() {
            return piAuditdate;
        }

        public void setPiAuditdate(String piAuditdate) {
            this.piAuditdate = piAuditdate;
        }

        public Object getPiFreightUser() {
            return piFreightUser;
        }

        public void setPiFreightUser(Object piFreightUser) {
            this.piFreightUser = piFreightUser;
        }

        public Object getPiOutamount() {
            return piOutamount;
        }

        public void setPiOutamount(Object piOutamount) {
            this.piOutamount = piOutamount;
        }

        public String getPiUpdatedate() {
            return piUpdatedate;
        }

        public void setPiUpdatedate(String piUpdatedate) {
            this.piUpdatedate = piUpdatedate;
        }

        public Object getPiLogisticscode() {
            return piLogisticscode;
        }

        public void setPiLogisticscode(Object piLogisticscode) {
            this.piLogisticscode = piLogisticscode;
        }

        public String getPiArcode() {
            return piArcode;
        }

        public void setPiArcode(String piArcode) {
            this.piArcode = piArcode;
        }

        public Object getPiOutcredit() {
            return piOutcredit;
        }

        public void setPiOutcredit(Object piOutcredit) {
            this.piOutcredit = piOutcredit;
        }

        public Object getPiBillstatus() {
            return piBillstatus;
        }

        public void setPiBillstatus(Object piBillstatus) {
            this.piBillstatus = piBillstatus;
        }

        public Object getPiTocode() {
            return piTocode;
        }

        public void setPiTocode(Object piTocode) {
            this.piTocode = piTocode;
        }

        public String getPiInvostatus() {
            return piInvostatus;
        }

        public void setPiInvostatus(String piInvostatus) {
            this.piInvostatus = piInvostatus;
        }

        public String getPiCardcode() {
            return piCardcode;
        }

        public void setPiCardcode(String piCardcode) {
            this.piCardcode = piCardcode;
        }

        public String getPiPayment() {
            return piPayment;
        }

        public void setPiPayment(String piPayment) {
            this.piPayment = piPayment;
        }

        public String getPiDepartmentname() {
            return piDepartmentname;
        }

        public void setPiDepartmentname(String piDepartmentname) {
            this.piDepartmentname = piDepartmentname;
        }

        public Object getPiMonthenddate() {
            return piMonthenddate;
        }

        public void setPiMonthenddate(Object piMonthenddate) {
            this.piMonthenddate = piMonthenddate;
        }

        public Object getPiWhname1User() {
            return piWhname1User;
        }

        public void setPiWhname1User(Object piWhname1User) {
            this.piWhname1User = piWhname1User;
        }

        public Object getPiCop() {
            return piCop;
        }

        public void setPiCop(Object piCop) {
            this.piCop = piCop;
        }

        public String getPiTitle() {
            return piTitle;
        }

        public void setPiTitle(String piTitle) {
            this.piTitle = piTitle;
        }

        public int getPiRate() {
            return piRate;
        }

        public void setPiRate(int piRate) {
            this.piRate = piRate;
        }

        public Object getPiExpresscode() {
            return piExpresscode;
        }

        public void setPiExpresscode(Object piExpresscode) {
            this.piExpresscode = piExpresscode;
        }

        public String getPiUpdateman() {
            return piUpdateman;
        }

        public void setPiUpdateman(String piUpdateman) {
            this.piUpdateman = piUpdateman;
        }

        public String getPiInvostatuscode() {
            return piInvostatuscode;
        }

        public void setPiInvostatuscode(String piInvostatuscode) {
            this.piInvostatuscode = piInvostatuscode;
        }

        public Object getPiChargeamount() {
            return piChargeamount;
        }

        public void setPiChargeamount(Object piChargeamount) {
            this.piChargeamount = piChargeamount;
        }

        public String getPiStatuscode() {
            return piStatuscode;
        }

        public void setPiStatuscode(String piStatuscode) {
            this.piStatuscode = piStatuscode;
        }

        public Object getPiOrdertype() {
            return piOrdertype;
        }

        public void setPiOrdertype(Object piOrdertype) {
            this.piOrdertype = piOrdertype;
        }

        public String getPiDepartmentcode() {
            return piDepartmentcode;
        }

        public void setPiDepartmentcode(String piDepartmentcode) {
            this.piDepartmentcode = piDepartmentcode;
        }

        public Object getPiDate1() {
            return piDate1;
        }

        public void setPiDate1(Object piDate1) {
            this.piDate1 = piDate1;
        }

        public String getPiPurposename() {
            return piPurposename;
        }

        public void setPiPurposename(String piPurposename) {
            this.piPurposename = piPurposename;
        }

        public Object getPiFax() {
            return piFax;
        }

        public void setPiFax(Object piFax) {
            this.piFax = piFax;
        }

        public String getPiPaymentcode() {
            return piPaymentcode;
        }

        public void setPiPaymentcode(String piPaymentcode) {
            this.piPaymentcode = piPaymentcode;
        }

        public String getPiCurrency() {
            return piCurrency;
        }

        public void setPiCurrency(String piCurrency) {
            this.piCurrency = piCurrency;
        }

        public Object getPiPaydate() {
            return piPaydate;
        }

        public void setPiPaydate(Object piPaydate) {
            this.piPaydate = piPaydate;
        }

        public String getPiTotalupper() {
            return piTotalupper;
        }

        public void setPiTotalupper(String piTotalupper) {
            this.piTotalupper = piTotalupper;
        }

        public String getPiReceivecode() {
            return piReceivecode;
        }

        public void setPiReceivecode(String piReceivecode) {
            this.piReceivecode = piReceivecode;
        }

        public Object getPiCgycode() {
            return piCgycode;
        }

        public void setPiCgycode(Object piCgycode) {
            this.piCgycode = piCgycode;
        }

        public Object getPiTduedate() {
            return piTduedate;
        }

        public void setPiTduedate(Object piTduedate) {
            this.piTduedate = piTduedate;
        }

        public String getPiWhname() {
            return piWhname;
        }

        public void setPiWhname(String piWhname) {
            this.piWhname = piWhname;
        }

        public Object getPiInoutman() {
            return piInoutman;
        }

        public void setPiInoutman(Object piInoutman) {
            this.piInoutman = piInoutman;
        }

        public int getPiTotal() {
            return piTotal;
        }

        public void setPiTotal(int piTotal) {
            this.piTotal = piTotal;
        }

        public String getPiSourcecode() {
            return piSourcecode;
        }

        public void setPiSourcecode(String piSourcecode) {
            this.piSourcecode = piSourcecode;
        }

        public Object getPiCgy() {
            return piCgy;
        }

        public void setPiCgy(Object piCgy) {
            this.piCgy = piCgy;
        }

        public String getPiRecordman() {
            return piRecordman;
        }

        public void setPiRecordman(String piRecordman) {
            this.piRecordman = piRecordman;
        }

        public Object getPiBillstatuscode() {
            return piBillstatuscode;
        }

        public void setPiBillstatuscode(Object piBillstatuscode) {
            this.piBillstatuscode = piBillstatuscode;
        }

        public Object getPiListcode() {
            return piListcode;
        }

        public void setPiListcode(Object piListcode) {
            this.piListcode = piListcode;
        }

        public Object getPiEmcode() {
            return piEmcode;
        }

        public void setPiEmcode(Object piEmcode) {
            this.piEmcode = piEmcode;
        }

        public String getPiPrintstatuscode() {
            return piPrintstatuscode;
        }

        public void setPiPrintstatuscode(String piPrintstatuscode) {
            this.piPrintstatuscode = piPrintstatuscode;
        }

        public String getPiDate() {
            return piDate;
        }

        public void setPiDate(String piDate) {
            this.piDate = piDate;
        }

        public Object getPiShr() {
            return piShr;
        }

        public void setPiShr(Object piShr) {
            this.piShr = piShr;
        }

        public String getPiAuditman() {
            return piAuditman;
        }

        public void setPiAuditman(String piAuditman) {
            this.piAuditman = piAuditman;
        }

        public String getPiRecorddate() {
            return piRecorddate;
        }

        public void setPiRecorddate(String piRecorddate) {
            this.piRecorddate = piRecorddate;
        }

        public Object getPiTransport() {
            return piTransport;
        }

        public void setPiTransport(Object piTransport) {
            this.piTransport = piTransport;
        }

        public Object getPiBcid() {
            return piBcid;
        }

        public void setPiBcid(Object piBcid) {
            this.piBcid = piBcid;
        }

        public String getPiSellername() {
            return piSellername;
        }

        public void setPiSellername(String piSellername) {
            this.piSellername = piSellername;
        }

        public String getPiWhcode() {
            return piWhcode;
        }

        public void setPiWhcode(String piWhcode) {
            this.piWhcode = piWhcode;
        }

        public String getPiStatus() {
            return piStatus;
        }

        public void setPiStatus(String piStatus) {
            this.piStatus = piStatus;
        }

        public Object getPiEmname() {
            return piEmname;
        }

        public void setPiEmname(Object piEmname) {
            this.piEmname = piEmname;
        }

        public String getPiPrintstatus() {
            return piPrintstatus;
        }

        public void setPiPrintstatus(String piPrintstatus) {
            this.piPrintstatus = piPrintstatus;
        }

        public Object getPiLogisticscompany() {
            return piLogisticscompany;
        }

        public void setPiLogisticscompany(Object piLogisticscompany) {
            this.piLogisticscompany = piLogisticscompany;
        }

        public String getPiAddress() {
            return piAddress;
        }

        public void setPiAddress(String piAddress) {
            this.piAddress = piAddress;
        }

        public String getPiReceivename() {
            return piReceivename;
        }

        public void setPiReceivename(String piReceivename) {
            this.piReceivename = piReceivename;
        }

        public Object getPiPackingcode() {
            return piPackingcode;
        }

        public void setPiPackingcode(Object piPackingcode) {
            this.piPackingcode = piPackingcode;
        }

        public String getPiArname() {
            return piArname;
        }

        public void setPiArname(String piArname) {
            this.piArname = piArname;
        }

        public int getPiId() {
            return piId;
        }

        public void setPiId(int piId) {
            this.piId = piId;
        }

        public Object getPiCount() {
            return piCount;
        }

        public void setPiCount(Object piCount) {
            this.piCount = piCount;
        }

        public String getPiInoutno() {
            return piInoutno;
        }

        public void setPiInoutno(String piInoutno) {
            this.piInoutno = piInoutno;
        }

        public Object getPiNtbamount() {
            return piNtbamount;
        }

        public void setPiNtbamount(Object piNtbamount) {
            this.piNtbamount = piNtbamount;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class DetailBean {
            /**
             * pd_remark : null
             * pd_purcoutqty : 100
             * pd_custprodcode : null
             * pd_total : 0
             * pd_id : 46947726
             * pd_whcode : csa12321
             * pd_pocode : 123123123
             * pd_purcinqty : 0
             * pd_batchid : 0
             * pd_piclass : 出货单
             * pd_bonded : 0
             * pd_ycheck : 0
             * pd_ordertotal : 0
             * pd_prodid : 100020115
             * pd_customprice : 0
             * pd_outqty : 100
             * pd_outerboxgw : 0
             * pd_netprice : 0
             * pd_bcid : null
             * pd_discount : 0
             * pd_custproddetail : null
             * pd_beipinoutqty : 0
             * pd_whid : 31800
             * pd_ordercode : SS18090004X0104
             * pd_invoqty : 0
             * pd_price : 0
             * pd_outerboxnw : 0
             * pd_auditstatus : 已审核
             * pd_location : null
             * pd_whname : 存手机
             * pd_taxrate : 16
             * pd_vendorrate : 0
             * pd_orderdetno : 1
             * pd_inoutno : SD18090003
             * pd_batchcode : null
             * pd_sendprice : 0
             * pd_prodmadedate : 2018-09-12 00:00:00
             * pd_skstatus : null
             * pd_orderid : 0
             * pd_prodcode : G020001
             * pd_description : null
             * pd_inqty : 0
             * pd_pdno : 1
             * pd_snid : 0
             * pd_nettotal : 0
             * pd_taxamount : 0
             * pd_piid : 50726895
             * pd_custprodspec : null
             * pd_purcprice : 0
             */

            @JsonProperty("pd_remark")
            private Object pdRemark;
            @JsonProperty("pd_purcoutqty")
            private int pdPurcoutqty;
            @JsonProperty("pd_custprodcode")
            private Object pdCustprodcode;
            @JsonProperty("pd_total")
            private int pdTotal;
            @JsonProperty("pd_id")
            private int pdId;
            @JsonProperty("pd_whcode")
            private String pdWhcode;
            @JsonProperty("pd_pocode")
            private String pdPocode;
            @JsonProperty("pd_purcinqty")
            private int pdPurcinqty;
            @JsonProperty("pd_batchid")
            private int pdBatchid;
            @JsonProperty("pd_piclass")
            private String pdPiclass;
            @JsonProperty("pd_bonded")
            private int pdBonded;
            @JsonProperty("pd_ycheck")
            private int pdYcheck;
            @JsonProperty("pd_ordertotal")
            private int pdOrdertotal;
            @JsonProperty("pd_prodid")
            private int pdProdid;
            @JsonProperty("pd_customprice")
            private int pdCustomprice;
            @JsonProperty("pd_outqty")
            private int pdOutqty;
            @JsonProperty("pd_outerboxgw")
            private int pdOuterboxgw;
            @JsonProperty("pd_netprice")
            private int pdNetprice;
            @JsonProperty("pd_bcid")
            private Object pdBcid;
            @JsonProperty("pd_discount")
            private int pdDiscount;
            @JsonProperty("pd_custproddetail")
            private Object pdCustproddetail;
            @JsonProperty("pd_beipinoutqty")
            private int pdBeipinoutqty;
            @JsonProperty("pd_whid")
            private int pdWhid;
            @JsonProperty("pd_ordercode")
            private String pdOrdercode;
            @JsonProperty("pd_invoqty")
            private int pdInvoqty;
            @JsonProperty("pd_price")
            private int pdPrice;
            @JsonProperty("pd_outerboxnw")
            private int pdOuterboxnw;
            @JsonProperty("pd_auditstatus")
            private String pdAuditstatus;
            @JsonProperty("pd_location")
            private Object pdLocation;
            @JsonProperty("pd_whname")
            private String pdWhname;
            @JsonProperty("pd_taxrate")
            private int pdTaxrate;
            @JsonProperty("pd_vendorrate")
            private int pdVendorrate;
            @JsonProperty("pd_orderdetno")
            private int pdOrderdetno;
            @JsonProperty("pd_inoutno")
            private String pdInoutno;
            @JsonProperty("pd_batchcode")
            private Object pdBatchcode;
            @JsonProperty("pd_sendprice")
            private int pdSendprice;
            @JsonProperty("pd_prodmadedate")
            private String pdProdmadedate;
            @JsonProperty("pd_skstatus")
            private Object pdSkstatus;
            @JsonProperty("pd_orderid")
            private int pdOrderid;
            @JsonProperty("pd_prodcode")
            private String pdProdcode;
            @JsonProperty("pd_description")
            private Object pdDescription;
            @JsonProperty("pd_inqty")
            private int pdInqty;
            @JsonProperty("pd_pdno")
            private int pdPdno;
            @JsonProperty("pd_snid")
            private int pdSnid;
            @JsonProperty("pd_nettotal")
            private int pdNettotal;
            @JsonProperty("pd_taxamount")
            private int pdTaxamount;
            @JsonProperty("pd_piid")
            private int pdPiid;
            @JsonProperty("pd_custprodspec")
            private Object pdCustprodspec;
            @JsonProperty("pd_purcprice")
            private int pdPurcprice;

            public Object getPdRemark() {
                return pdRemark;
            }

            public void setPdRemark(Object pdRemark) {
                this.pdRemark = pdRemark;
            }

            public int getPdPurcoutqty() {
                return pdPurcoutqty;
            }

            public void setPdPurcoutqty(int pdPurcoutqty) {
                this.pdPurcoutqty = pdPurcoutqty;
            }

            public Object getPdCustprodcode() {
                return pdCustprodcode;
            }

            public void setPdCustprodcode(Object pdCustprodcode) {
                this.pdCustprodcode = pdCustprodcode;
            }

            public int getPdTotal() {
                return pdTotal;
            }

            public void setPdTotal(int pdTotal) {
                this.pdTotal = pdTotal;
            }

            public int getPdId() {
                return pdId;
            }

            public void setPdId(int pdId) {
                this.pdId = pdId;
            }

            public String getPdWhcode() {
                return pdWhcode;
            }

            public void setPdWhcode(String pdWhcode) {
                this.pdWhcode = pdWhcode;
            }

            public String getPdPocode() {
                return pdPocode;
            }

            public void setPdPocode(String pdPocode) {
                this.pdPocode = pdPocode;
            }

            public int getPdPurcinqty() {
                return pdPurcinqty;
            }

            public void setPdPurcinqty(int pdPurcinqty) {
                this.pdPurcinqty = pdPurcinqty;
            }

            public int getPdBatchid() {
                return pdBatchid;
            }

            public void setPdBatchid(int pdBatchid) {
                this.pdBatchid = pdBatchid;
            }

            public String getPdPiclass() {
                return pdPiclass;
            }

            public void setPdPiclass(String pdPiclass) {
                this.pdPiclass = pdPiclass;
            }

            public int getPdBonded() {
                return pdBonded;
            }

            public void setPdBonded(int pdBonded) {
                this.pdBonded = pdBonded;
            }

            public int getPdYcheck() {
                return pdYcheck;
            }

            public void setPdYcheck(int pdYcheck) {
                this.pdYcheck = pdYcheck;
            }

            public int getPdOrdertotal() {
                return pdOrdertotal;
            }

            public void setPdOrdertotal(int pdOrdertotal) {
                this.pdOrdertotal = pdOrdertotal;
            }

            public int getPdProdid() {
                return pdProdid;
            }

            public void setPdProdid(int pdProdid) {
                this.pdProdid = pdProdid;
            }

            public int getPdCustomprice() {
                return pdCustomprice;
            }

            public void setPdCustomprice(int pdCustomprice) {
                this.pdCustomprice = pdCustomprice;
            }

            public int getPdOutqty() {
                return pdOutqty;
            }

            public void setPdOutqty(int pdOutqty) {
                this.pdOutqty = pdOutqty;
            }

            public int getPdOuterboxgw() {
                return pdOuterboxgw;
            }

            public void setPdOuterboxgw(int pdOuterboxgw) {
                this.pdOuterboxgw = pdOuterboxgw;
            }

            public int getPdNetprice() {
                return pdNetprice;
            }

            public void setPdNetprice(int pdNetprice) {
                this.pdNetprice = pdNetprice;
            }

            public Object getPdBcid() {
                return pdBcid;
            }

            public void setPdBcid(Object pdBcid) {
                this.pdBcid = pdBcid;
            }

            public int getPdDiscount() {
                return pdDiscount;
            }

            public void setPdDiscount(int pdDiscount) {
                this.pdDiscount = pdDiscount;
            }

            public Object getPdCustproddetail() {
                return pdCustproddetail;
            }

            public void setPdCustproddetail(Object pdCustproddetail) {
                this.pdCustproddetail = pdCustproddetail;
            }

            public int getPdBeipinoutqty() {
                return pdBeipinoutqty;
            }

            public void setPdBeipinoutqty(int pdBeipinoutqty) {
                this.pdBeipinoutqty = pdBeipinoutqty;
            }

            public int getPdWhid() {
                return pdWhid;
            }

            public void setPdWhid(int pdWhid) {
                this.pdWhid = pdWhid;
            }

            public String getPdOrdercode() {
                return pdOrdercode;
            }

            public void setPdOrdercode(String pdOrdercode) {
                this.pdOrdercode = pdOrdercode;
            }

            public int getPdInvoqty() {
                return pdInvoqty;
            }

            public void setPdInvoqty(int pdInvoqty) {
                this.pdInvoqty = pdInvoqty;
            }

            public int getPdPrice() {
                return pdPrice;
            }

            public void setPdPrice(int pdPrice) {
                this.pdPrice = pdPrice;
            }

            public int getPdOuterboxnw() {
                return pdOuterboxnw;
            }

            public void setPdOuterboxnw(int pdOuterboxnw) {
                this.pdOuterboxnw = pdOuterboxnw;
            }

            public String getPdAuditstatus() {
                return pdAuditstatus;
            }

            public void setPdAuditstatus(String pdAuditstatus) {
                this.pdAuditstatus = pdAuditstatus;
            }

            public Object getPdLocation() {
                return pdLocation;
            }

            public void setPdLocation(Object pdLocation) {
                this.pdLocation = pdLocation;
            }

            public String getPdWhname() {
                return pdWhname;
            }

            public void setPdWhname(String pdWhname) {
                this.pdWhname = pdWhname;
            }

            public int getPdTaxrate() {
                return pdTaxrate;
            }

            public void setPdTaxrate(int pdTaxrate) {
                this.pdTaxrate = pdTaxrate;
            }

            public int getPdVendorrate() {
                return pdVendorrate;
            }

            public void setPdVendorrate(int pdVendorrate) {
                this.pdVendorrate = pdVendorrate;
            }

            public int getPdOrderdetno() {
                return pdOrderdetno;
            }

            public void setPdOrderdetno(int pdOrderdetno) {
                this.pdOrderdetno = pdOrderdetno;
            }

            public String getPdInoutno() {
                return pdInoutno;
            }

            public void setPdInoutno(String pdInoutno) {
                this.pdInoutno = pdInoutno;
            }

            public Object getPdBatchcode() {
                return pdBatchcode;
            }

            public void setPdBatchcode(Object pdBatchcode) {
                this.pdBatchcode = pdBatchcode;
            }

            public int getPdSendprice() {
                return pdSendprice;
            }

            public void setPdSendprice(int pdSendprice) {
                this.pdSendprice = pdSendprice;
            }

            public String getPdProdmadedate() {
                return pdProdmadedate;
            }

            public void setPdProdmadedate(String pdProdmadedate) {
                this.pdProdmadedate = pdProdmadedate;
            }

            public Object getPdSkstatus() {
                return pdSkstatus;
            }

            public void setPdSkstatus(Object pdSkstatus) {
                this.pdSkstatus = pdSkstatus;
            }

            public int getPdOrderid() {
                return pdOrderid;
            }

            public void setPdOrderid(int pdOrderid) {
                this.pdOrderid = pdOrderid;
            }

            public String getPdProdcode() {
                return pdProdcode;
            }

            public void setPdProdcode(String pdProdcode) {
                this.pdProdcode = pdProdcode;
            }

            public Object getPdDescription() {
                return pdDescription;
            }

            public void setPdDescription(Object pdDescription) {
                this.pdDescription = pdDescription;
            }

            public int getPdInqty() {
                return pdInqty;
            }

            public void setPdInqty(int pdInqty) {
                this.pdInqty = pdInqty;
            }

            public int getPdPdno() {
                return pdPdno;
            }

            public void setPdPdno(int pdPdno) {
                this.pdPdno = pdPdno;
            }

            public int getPdSnid() {
                return pdSnid;
            }

            public void setPdSnid(int pdSnid) {
                this.pdSnid = pdSnid;
            }

            public int getPdNettotal() {
                return pdNettotal;
            }

            public void setPdNettotal(int pdNettotal) {
                this.pdNettotal = pdNettotal;
            }

            public int getPdTaxamount() {
                return pdTaxamount;
            }

            public void setPdTaxamount(int pdTaxamount) {
                this.pdTaxamount = pdTaxamount;
            }

            public int getPdPiid() {
                return pdPiid;
            }

            public void setPdPiid(int pdPiid) {
                this.pdPiid = pdPiid;
            }

            public Object getPdCustprodspec() {
                return pdCustprodspec;
            }

            public void setPdCustprodspec(Object pdCustprodspec) {
                this.pdCustprodspec = pdCustprodspec;
            }

            public int getPdPurcprice() {
                return pdPurcprice;
            }

            public void setPdPurcprice(int pdPurcprice) {
                this.pdPurcprice = pdPurcprice;
            }
        }
    }
}
