package com.zzjee.wmutil;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Auto-generated: 2018-09-01 11:10:48
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@JsonAutoDetect
public class productResult {



    /**
     * result : [{"pr_purcunit":null,"pr_kindcode":null,"pr_costcatename":null,"pr_place":"进口","pr_exbarcode":null,"pr_putouttoint":"0","pr_barcode":"21332dasd","pr_stockcatecode":null,"pr_manutype":"PURCHASE","pr_cggdy":null,"pr_unit":"台","pr_minstock":null,"pr_inspector":null,"pr_recentindate":null,"pr_ltqc":"0","pr_gdtqq":"0","pr_whname":null,"pr_whmanname":null,"pr_kind":"食品","pr_statuscode":"AUDITED","pr_acceptmethod":"0","pr_precheckdate":null,"PR_SPECDESCRIPTION2":null,"pr_location":null,"pr_serialtype":null,"pr_id":"100020035","pr_wiplocation":null,"pr_flowtype":null,"pr_humancost":null,"pr_planner":null,"pr_purccurrency":null,"pr_abc":null,"pr_bgcode":null,"pr_ltwarndays":"0","pr_detail":"p9","pr_material":null,"pr_cost":null,"pr_ifbarcodecheck":null,"pr_speceg":null,"pr_orispeccode":null,"pr_purcmergedays":"0","pr_precision":"0","pr_bzdays":"0","pr_bonded":null,"pr_namerule":null,"pr_level":null,"pr_jitype":null,"pr_recentpurcdate":null,"pr_buyername":null,"PR_RDBG":null,"pr_cop":null,"pr_msdlevel":null,"pr_tracekind":"0","pr_incomecatecode":null,"pr_grade":null,"pr_box_sum_user":"0","pr_recordman":"管理员","pr_dhzc":"MRP","pr_purcrate":"1","pr_purccentercode":null,"pr_pricestatus":null,"pr_wcname":null,"pr_vendprodcode":null,"pr_remark_base":null,"pr_salecurrency":null,"pr_spec":"wqewq","pr_uuid":null,"pr_kczzl":"0","pr_saleunit":null,"pr_speccs":null,"pr_istracbefore":null,"pr_needattach":null,"pr_serial":null,"pr_qualmethod":null,"pr_vendname":null,"pr_whcode":null,"pr_sqr":"管理员","pr_isvendorrate":null,"pr_testlossrate":"0","pr_xikind":null,"pr_purchasedays":"0","pr_sqrq":null,"pr_piccode":null,"pr_refno":null,"pr_machinetime":"0","pr_docdate":"2018-08-29 00:00:00.0","pr_checkstatus":null,"pr_jkgsl":"0","pr_isgrouppurc":"0","pr_admitstatus":null,"pr_purcprice":null,"pr_safetystock":"0","pr_costcatecode":null,"pr_kind3":null,"pr_kind2":"饮料冲调","pr_code":"C0300090","pr_parametereg":null,"pr_salecost":null,"pr_stockcatename":null,"pr_remark":null,"pr_buyercode":null,"pr_oldcode":null,"pr_nameeg":null,"pr_costlevel":null,"pr_bzkczzl":"0","pr_ismsd":null,"pr_zxbzs":"0","pr_smtpoint":"0","pr_cggdycode":null,"pr_sqename":null,"pr_combineqty":"0","pr_status":"已审核","pr_planercode":null,"pr_whmancode":null,"pr_description":null,"pr_avpurcprice":null,"pr_makecost":null,"pr_crman":null,"pr_economicqty":"0","pr_pkid":"64583","pr_exportlossrate":"0","pr_brand":"iPhone","pr_wccode":null,"pr_mainvendcode":null,"pr_standardprice":"0","pr_bplossrate":"0","pr_self":"0","pr_specrule":null,"pr_materialcost":null,"pr_saleprice":null,"pr_sqecode":null,"pr_lossrate":"0","pr_updatedate":null,"pr_standtime":"0","pr_recentchangedate":null,"pr_attach":null,"pr_vendorstatus":null,"pr_ltinstock":"0","pr_parameterrule":null,"pr_bgname":null,"pr_aql":null,"pr_recentoutdate":null,"PR_SPECDESCRIPTION":null,"pr_sourcecode":null,"pr_customprice":null,"pr_purclossrate":"0","pr_zxdhl":"0","pr_plzl":"0","pr_validdays":"0","pr_checkstatuscode":null,"pr_incomecatename":null,"pr_supplytype":"PUSH","pr_leadtime":"0"}]
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
         * pr_purcunit : null
         * pr_kindcode : null
         * pr_costcatename : null
         * pr_place : 进口
         * pr_exbarcode : null
         * pr_putouttoint : 0
         * pr_barcode : 21332dasd
         * pr_stockcatecode : null
         * pr_manutype : PURCHASE
         * pr_cggdy : null
         * pr_unit : 台
         * pr_minstock : null
         * pr_inspector : null
         * pr_recentindate : null
         * pr_ltqc : 0
         * pr_gdtqq : 0
         * pr_whname : null
         * pr_whmanname : null
         * pr_kind : 食品
         * pr_statuscode : AUDITED
         * pr_acceptmethod : 0
         * pr_precheckdate : null
         * PR_SPECDESCRIPTION2 : null
         * pr_location : null
         * pr_serialtype : null
         * pr_id : 100020035
         * pr_wiplocation : null
         * pr_flowtype : null
         * pr_humancost : null
         * pr_planner : null
         * pr_purccurrency : null
         * pr_abc : null
         * pr_bgcode : null
         * pr_ltwarndays : 0
         * pr_detail : p9
         * pr_material : null
         * pr_cost : null
         * pr_ifbarcodecheck : null
         * pr_speceg : null
         * pr_orispeccode : null
         * pr_purcmergedays : 0
         * pr_precision : 0
         * pr_bzdays : 0
         * pr_bonded : null
         * pr_namerule : null
         * pr_level : null
         * pr_jitype : null
         * pr_recentpurcdate : null
         * pr_buyername : null
         * PR_RDBG : null
         * pr_cop : null
         * pr_msdlevel : null
         * pr_tracekind : 0
         * pr_incomecatecode : null
         * pr_grade : null
         * pr_box_sum_user : 0
         * pr_recordman : 管理员
         * pr_dhzc : MRP
         * pr_purcrate : 1
         * pr_purccentercode : null
         * pr_pricestatus : null
         * pr_wcname : null
         * pr_vendprodcode : null
         * pr_remark_base : null
         * pr_salecurrency : null
         * pr_spec : wqewq
         * pr_uuid : null
         * pr_kczzl : 0
         * pr_saleunit : null
         * pr_speccs : null
         * pr_istracbefore : null
         * pr_needattach : null
         * pr_serial : null
         * pr_qualmethod : null
         * pr_vendname : null
         * pr_whcode : null
         * pr_sqr : 管理员
         * pr_isvendorrate : null
         * pr_testlossrate : 0
         * pr_xikind : null
         * pr_purchasedays : 0
         * pr_sqrq : null
         * pr_piccode : null
         * pr_refno : null
         * pr_machinetime : 0
         * pr_docdate : 2018-08-29 00:00:00.0
         * pr_checkstatus : null
         * pr_jkgsl : 0
         * pr_isgrouppurc : 0
         * pr_admitstatus : null
         * pr_purcprice : null
         * pr_safetystock : 0
         * pr_costcatecode : null
         * pr_kind3 : null
         * pr_kind2 : 饮料冲调
         * pr_code : C0300090
         * pr_parametereg : null
         * pr_salecost : null
         * pr_stockcatename : null
         * pr_remark : null
         * pr_buyercode : null
         * pr_oldcode : null
         * pr_nameeg : null
         * pr_costlevel : null
         * pr_bzkczzl : 0
         * pr_ismsd : null
         * pr_zxbzs : 0
         * pr_smtpoint : 0
         * pr_cggdycode : null
         * pr_sqename : null
         * pr_combineqty : 0
         * pr_status : 已审核
         * pr_planercode : null
         * pr_whmancode : null
         * pr_description : null
         * pr_avpurcprice : null
         * pr_makecost : null
         * pr_crman : null
         * pr_economicqty : 0
         * pr_pkid : 64583
         * pr_exportlossrate : 0
         * pr_brand : iPhone
         * pr_wccode : null
         * pr_mainvendcode : null
         * pr_standardprice : 0
         * pr_bplossrate : 0
         * pr_self : 0
         * pr_specrule : null
         * pr_materialcost : null
         * pr_saleprice : null
         * pr_sqecode : null
         * pr_lossrate : 0
         * pr_updatedate : null
         * pr_standtime : 0
         * pr_recentchangedate : null
         * pr_attach : null
         * pr_vendorstatus : null
         * pr_ltinstock : 0
         * pr_parameterrule : null
         * pr_bgname : null
         * pr_aql : null
         * pr_recentoutdate : null
         * PR_SPECDESCRIPTION : null
         * pr_sourcecode : null
         * pr_customprice : null
         * pr_purclossrate : 0
         * pr_zxdhl : 0
         * pr_plzl : 0
         * pr_validdays : 0
         * pr_checkstatuscode : null
         * pr_incomecatename : null
         * pr_supplytype : PUSH
         * pr_leadtime : 0
         */

        @JsonProperty("pr_purcunit")
        private Object prPurcunit;
        @JsonProperty("pr_kindcode")
        private Object prKindcode;
        @JsonProperty("pr_costcatename")
        private Object prCostcatename;
        @JsonProperty("pr_place")
        private String prPlace;
        @JsonProperty("pr_exbarcode")
        private Object prExbarcode;
        @JsonProperty("pr_putouttoint")
        private String prPutouttoint;
        @JsonProperty("pr_barcode")
        private String prBarcode;
        @JsonProperty("pr_stockcatecode")
        private Object prStockcatecode;
        @JsonProperty("pr_manutype")
        private String prManutype;
        @JsonProperty("pr_cggdy")
        private Object prCggdy;
        @JsonProperty("pr_unit")
        private String prUnit;
        @JsonProperty("pr_minstock")
        private Object prMinstock;
        @JsonProperty("pr_inspector")
        private Object prInspector;
        @JsonProperty("pr_recentindate")
        private Object prRecentindate;
        @JsonProperty("pr_ltqc")
        private String prLtqc;
        @JsonProperty("pr_gdtqq")
        private String prGdtqq;
        @JsonProperty("pr_whname")
        private Object prWhname;
        @JsonProperty("pr_whmanname")
        private Object prWhmanname;
        @JsonProperty("pr_kind")
        private String prKind;
        @JsonProperty("pr_statuscode")
        private String prStatuscode;
        @JsonProperty("pr_acceptmethod")
        private String prAcceptmethod;
        @JsonProperty("pr_precheckdate")
        private Object prPrecheckdate;
        @JsonProperty("PR_SPECDESCRIPTION2")
        private Object PRSPECDESCRIPTION2;
        @JsonProperty("pr_location")
        private Object prLocation;
        @JsonProperty("pr_serialtype")
        private Object prSerialtype;
        @JsonProperty("pr_id")
        private String prId;
        @JsonProperty("pr_wiplocation")
        private Object prWiplocation;
        @JsonProperty("pr_flowtype")
        private Object prFlowtype;
        @JsonProperty("pr_humancost")
        private Object prHumancost;
        @JsonProperty("pr_planner")
        private Object prPlanner;
        @JsonProperty("pr_purccurrency")
        private Object prPurccurrency;
        @JsonProperty("pr_abc")
        private Object prAbc;
        @JsonProperty("pr_bgcode")
        private Object prBgcode;
        @JsonProperty("pr_ltwarndays")
        private String prLtwarndays;
        @JsonProperty("pr_detail")
        private String prDetail;
        @JsonProperty("pr_material")
        private Object prMaterial;
        @JsonProperty("pr_cost")
        private Object prCost;
        @JsonProperty("pr_ifbarcodecheck")
        private Object prIfbarcodecheck;
        @JsonProperty("pr_speceg")
        private Object prSpeceg;
        @JsonProperty("pr_orispeccode")
        private Object prOrispeccode;
        @JsonProperty("pr_purcmergedays")
        private String prPurcmergedays;
        @JsonProperty("pr_precision")
        private String prPrecision;
        @JsonProperty("pr_bzdays")
        private String prBzdays;
        @JsonProperty("pr_bonded")
        private Object prBonded;
        @JsonProperty("pr_namerule")
        private Object prNamerule;
        @JsonProperty("pr_level")
        private Object prLevel;
        @JsonProperty("pr_jitype")
        private Object prJitype;
        @JsonProperty("pr_recentpurcdate")
        private Object prRecentpurcdate;
        @JsonProperty("pr_buyername")
        private Object prBuyername;
        @JsonProperty("PR_RDBG")
        private Object PRRDBG;
        @JsonProperty("pr_cop")
        private Object prCop;
        @JsonProperty("pr_msdlevel")
        private Object prMsdlevel;
        @JsonProperty("pr_tracekind")
        private String prTracekind;
        @JsonProperty("pr_incomecatecode")
        private Object prIncomecatecode;
        @JsonProperty("pr_grade")
        private Object prGrade;
        @JsonProperty("pr_box_sum_user")
        private String prBoxSumUser;
        @JsonProperty("pr_recordman")
        private String prRecordman;
        @JsonProperty("pr_dhzc")
        private String prDhzc;
        @JsonProperty("pr_purcrate")
        private String prPurcrate;
        @JsonProperty("pr_purccentercode")
        private Object prPurccentercode;
        @JsonProperty("pr_pricestatus")
        private Object prPricestatus;
        @JsonProperty("pr_wcname")
        private Object prWcname;
        @JsonProperty("pr_vendprodcode")
        private Object prVendprodcode;
        @JsonProperty("pr_remark_base")
        private Object prRemarkBase;
        @JsonProperty("pr_salecurrency")
        private Object prSalecurrency;
        @JsonProperty("pr_spec")
        private String prSpec;
        @JsonProperty("pr_uuid")
        private Object prUuid;
        @JsonProperty("pr_kczzl")
        private String prKczzl;
        @JsonProperty("pr_saleunit")
        private Object prSaleunit;
        @JsonProperty("pr_speccs")
        private Object prSpeccs;
        @JsonProperty("pr_istracbefore")
        private Object prIstracbefore;
        @JsonProperty("pr_needattach")
        private Object prNeedattach;
        @JsonProperty("pr_serial")
        private Object prSerial;
        @JsonProperty("pr_qualmethod")
        private Object prQualmethod;
        @JsonProperty("pr_vendname")
        private Object prVendname;
        @JsonProperty("pr_whcode")
        private Object prWhcode;
        @JsonProperty("pr_sqr")
        private String prSqr;
        @JsonProperty("pr_isvendorrate")
        private Object prIsvendorrate;
        @JsonProperty("pr_testlossrate")
        private String prTestlossrate;
        @JsonProperty("pr_xikind")
        private Object prXikind;
        @JsonProperty("pr_purchasedays")
        private String prPurchasedays;
        @JsonProperty("pr_sqrq")
        private Object prSqrq;
        @JsonProperty("pr_piccode")
        private Object prPiccode;
        @JsonProperty("pr_refno")
        private Object prRefno;
        @JsonProperty("pr_machinetime")
        private String prMachinetime;
        @JsonProperty("pr_docdate")
        private String prDocdate;
        @JsonProperty("pr_checkstatus")
        private Object prCheckstatus;
        @JsonProperty("pr_jkgsl")
        private String prJkgsl;
        @JsonProperty("pr_isgrouppurc")
        private String prIsgrouppurc;
        @JsonProperty("pr_admitstatus")
        private Object prAdmitstatus;
        @JsonProperty("pr_purcprice")
        private Object prPurcprice;
        @JsonProperty("pr_safetystock")
        private String prSafetystock;
        @JsonProperty("pr_costcatecode")
        private Object prCostcatecode;
        @JsonProperty("pr_kind3")
        private Object prKind3;
        @JsonProperty("pr_kind2")
        private String prKind2;
        @JsonProperty("pr_code")
        private String prCode;
        @JsonProperty("pr_parametereg")
        private Object prParametereg;
        @JsonProperty("pr_salecost")
        private Object prSalecost;
        @JsonProperty("pr_stockcatename")
        private Object prStockcatename;
        @JsonProperty("pr_remark")
        private Object prRemark;
        @JsonProperty("pr_buyercode")
        private Object prBuyercode;
        @JsonProperty("pr_oldcode")
        private Object prOldcode;
        @JsonProperty("pr_nameeg")
        private Object prNameeg;
        @JsonProperty("pr_costlevel")
        private Object prCostlevel;
        @JsonProperty("pr_bzkczzl")
        private String prBzkczzl;
        @JsonProperty("pr_ismsd")
        private Object prIsmsd;
        @JsonProperty("pr_zxbzs")
        private String prZxbzs;
        @JsonProperty("pr_smtpoint")
        private String prSmtpoint;
        @JsonProperty("pr_cggdycode")
        private Object prCggdycode;
        @JsonProperty("pr_sqename")
        private Object prSqename;
        @JsonProperty("pr_combineqty")
        private String prCombineqty;
        @JsonProperty("pr_status")
        private String prStatus;
        @JsonProperty("pr_planercode")
        private Object prPlanercode;
        @JsonProperty("pr_whmancode")
        private Object prWhmancode;
        @JsonProperty("pr_description")
        private Object prDescription;
        @JsonProperty("pr_avpurcprice")
        private Object prAvpurcprice;
        @JsonProperty("pr_makecost")
        private Object prMakecost;
        @JsonProperty("pr_crman")
        private Object prCrman;
        @JsonProperty("pr_economicqty")
        private String prEconomicqty;
        @JsonProperty("pr_pkid")
        private String prPkid;
        @JsonProperty("pr_exportlossrate")
        private String prExportlossrate;
        @JsonProperty("pr_brand")
        private String prBrand;
        @JsonProperty("pr_wccode")
        private Object prWccode;
        @JsonProperty("pr_mainvendcode")
        private Object prMainvendcode;
        @JsonProperty("pr_standardprice")
        private String prStandardprice;
        @JsonProperty("pr_bplossrate")
        private String prBplossrate;
        @JsonProperty("pr_self")
        private String prSelf;
        @JsonProperty("pr_specrule")
        private Object prSpecrule;
        @JsonProperty("pr_materialcost")
        private Object prMaterialcost;
        @JsonProperty("pr_saleprice")
        private Object prSaleprice;
        @JsonProperty("pr_sqecode")
        private Object prSqecode;
        @JsonProperty("pr_lossrate")
        private String prLossrate;
        @JsonProperty("pr_updatedate")
        private Object prUpdatedate;
        @JsonProperty("pr_standtime")
        private String prStandtime;
        @JsonProperty("pr_recentchangedate")
        private Object prRecentchangedate;
        @JsonProperty("pr_attach")
        private Object prAttach;
        @JsonProperty("pr_vendorstatus")
        private Object prVendorstatus;
        @JsonProperty("pr_ltinstock")
        private String prLtinstock;
        @JsonProperty("pr_parameterrule")
        private Object prParameterrule;
        @JsonProperty("pr_bgname")
        private Object prBgname;
        @JsonProperty("pr_aql")
        private Object prAql;
        @JsonProperty("pr_recentoutdate")
        private Object prRecentoutdate;
        @JsonProperty("PR_SPECDESCRIPTION")
        private Object PRSPECDESCRIPTION;
        @JsonProperty("pr_sourcecode")
        private Object prSourcecode;
        @JsonProperty("pr_customprice")
        private Object prCustomprice;
        @JsonProperty("pr_purclossrate")
        private String prPurclossrate;
        @JsonProperty("pr_zxdhl")
        private String prZxdhl;
        @JsonProperty("pr_plzl")
        private String prPlzl;
        @JsonProperty("pr_validdays")
        private String prValiddays;
        @JsonProperty("pr_checkstatuscode")
        private Object prCheckstatuscode;
        @JsonProperty("pr_incomecatename")
        private Object prIncomecatename;
        @JsonProperty("pr_supplytype")
        private String prSupplytype;
        @JsonProperty("pr_leadtime")
        private String prLeadtime;

        public Object getPrPurcunit() {
            return prPurcunit;
        }

        public void setPrPurcunit(Object prPurcunit) {
            this.prPurcunit = prPurcunit;
        }

        public Object getPrKindcode() {
            return prKindcode;
        }

        public void setPrKindcode(Object prKindcode) {
            this.prKindcode = prKindcode;
        }

        public Object getPrCostcatename() {
            return prCostcatename;
        }

        public void setPrCostcatename(Object prCostcatename) {
            this.prCostcatename = prCostcatename;
        }

        public String getPrPlace() {
            return prPlace;
        }

        public void setPrPlace(String prPlace) {
            this.prPlace = prPlace;
        }

        public Object getPrExbarcode() {
            return prExbarcode;
        }

        public void setPrExbarcode(Object prExbarcode) {
            this.prExbarcode = prExbarcode;
        }

        public String getPrPutouttoint() {
            return prPutouttoint;
        }

        public void setPrPutouttoint(String prPutouttoint) {
            this.prPutouttoint = prPutouttoint;
        }

        public String getPrBarcode() {
            return prBarcode;
        }

        public void setPrBarcode(String prBarcode) {
            this.prBarcode = prBarcode;
        }

        public Object getPrStockcatecode() {
            return prStockcatecode;
        }

        public void setPrStockcatecode(Object prStockcatecode) {
            this.prStockcatecode = prStockcatecode;
        }

        public String getPrManutype() {
            return prManutype;
        }

        public void setPrManutype(String prManutype) {
            this.prManutype = prManutype;
        }

        public Object getPrCggdy() {
            return prCggdy;
        }

        public void setPrCggdy(Object prCggdy) {
            this.prCggdy = prCggdy;
        }

        public String getPrUnit() {
            return prUnit;
        }

        public void setPrUnit(String prUnit) {
            this.prUnit = prUnit;
        }

        public Object getPrMinstock() {
            return prMinstock;
        }

        public void setPrMinstock(Object prMinstock) {
            this.prMinstock = prMinstock;
        }

        public Object getPrInspector() {
            return prInspector;
        }

        public void setPrInspector(Object prInspector) {
            this.prInspector = prInspector;
        }

        public Object getPrRecentindate() {
            return prRecentindate;
        }

        public void setPrRecentindate(Object prRecentindate) {
            this.prRecentindate = prRecentindate;
        }

        public String getPrLtqc() {
            return prLtqc;
        }

        public void setPrLtqc(String prLtqc) {
            this.prLtqc = prLtqc;
        }

        public String getPrGdtqq() {
            return prGdtqq;
        }

        public void setPrGdtqq(String prGdtqq) {
            this.prGdtqq = prGdtqq;
        }

        public Object getPrWhname() {
            return prWhname;
        }

        public void setPrWhname(Object prWhname) {
            this.prWhname = prWhname;
        }

        public Object getPrWhmanname() {
            return prWhmanname;
        }

        public void setPrWhmanname(Object prWhmanname) {
            this.prWhmanname = prWhmanname;
        }

        public String getPrKind() {
            return prKind;
        }

        public void setPrKind(String prKind) {
            this.prKind = prKind;
        }

        public String getPrStatuscode() {
            return prStatuscode;
        }

        public void setPrStatuscode(String prStatuscode) {
            this.prStatuscode = prStatuscode;
        }

        public String getPrAcceptmethod() {
            return prAcceptmethod;
        }

        public void setPrAcceptmethod(String prAcceptmethod) {
            this.prAcceptmethod = prAcceptmethod;
        }

        public Object getPrPrecheckdate() {
            return prPrecheckdate;
        }

        public void setPrPrecheckdate(Object prPrecheckdate) {
            this.prPrecheckdate = prPrecheckdate;
        }

        public Object getPRSPECDESCRIPTION2() {
            return PRSPECDESCRIPTION2;
        }

        public void setPRSPECDESCRIPTION2(Object PRSPECDESCRIPTION2) {
            this.PRSPECDESCRIPTION2 = PRSPECDESCRIPTION2;
        }

        public Object getPrLocation() {
            return prLocation;
        }

        public void setPrLocation(Object prLocation) {
            this.prLocation = prLocation;
        }

        public Object getPrSerialtype() {
            return prSerialtype;
        }

        public void setPrSerialtype(Object prSerialtype) {
            this.prSerialtype = prSerialtype;
        }

        public String getPrId() {
            return prId;
        }

        public void setPrId(String prId) {
            this.prId = prId;
        }

        public Object getPrWiplocation() {
            return prWiplocation;
        }

        public void setPrWiplocation(Object prWiplocation) {
            this.prWiplocation = prWiplocation;
        }

        public Object getPrFlowtype() {
            return prFlowtype;
        }

        public void setPrFlowtype(Object prFlowtype) {
            this.prFlowtype = prFlowtype;
        }

        public Object getPrHumancost() {
            return prHumancost;
        }

        public void setPrHumancost(Object prHumancost) {
            this.prHumancost = prHumancost;
        }

        public Object getPrPlanner() {
            return prPlanner;
        }

        public void setPrPlanner(Object prPlanner) {
            this.prPlanner = prPlanner;
        }

        public Object getPrPurccurrency() {
            return prPurccurrency;
        }

        public void setPrPurccurrency(Object prPurccurrency) {
            this.prPurccurrency = prPurccurrency;
        }

        public Object getPrAbc() {
            return prAbc;
        }

        public void setPrAbc(Object prAbc) {
            this.prAbc = prAbc;
        }

        public Object getPrBgcode() {
            return prBgcode;
        }

        public void setPrBgcode(Object prBgcode) {
            this.prBgcode = prBgcode;
        }

        public String getPrLtwarndays() {
            return prLtwarndays;
        }

        public void setPrLtwarndays(String prLtwarndays) {
            this.prLtwarndays = prLtwarndays;
        }

        public String getPrDetail() {
            return prDetail;
        }

        public void setPrDetail(String prDetail) {
            this.prDetail = prDetail;
        }

        public Object getPrMaterial() {
            return prMaterial;
        }

        public void setPrMaterial(Object prMaterial) {
            this.prMaterial = prMaterial;
        }

        public Object getPrCost() {
            return prCost;
        }

        public void setPrCost(Object prCost) {
            this.prCost = prCost;
        }

        public Object getPrIfbarcodecheck() {
            return prIfbarcodecheck;
        }

        public void setPrIfbarcodecheck(Object prIfbarcodecheck) {
            this.prIfbarcodecheck = prIfbarcodecheck;
        }

        public Object getPrSpeceg() {
            return prSpeceg;
        }

        public void setPrSpeceg(Object prSpeceg) {
            this.prSpeceg = prSpeceg;
        }

        public Object getPrOrispeccode() {
            return prOrispeccode;
        }

        public void setPrOrispeccode(Object prOrispeccode) {
            this.prOrispeccode = prOrispeccode;
        }

        public String getPrPurcmergedays() {
            return prPurcmergedays;
        }

        public void setPrPurcmergedays(String prPurcmergedays) {
            this.prPurcmergedays = prPurcmergedays;
        }

        public String getPrPrecision() {
            return prPrecision;
        }

        public void setPrPrecision(String prPrecision) {
            this.prPrecision = prPrecision;
        }

        public String getPrBzdays() {
            return prBzdays;
        }

        public void setPrBzdays(String prBzdays) {
            this.prBzdays = prBzdays;
        }

        public Object getPrBonded() {
            return prBonded;
        }

        public void setPrBonded(Object prBonded) {
            this.prBonded = prBonded;
        }

        public Object getPrNamerule() {
            return prNamerule;
        }

        public void setPrNamerule(Object prNamerule) {
            this.prNamerule = prNamerule;
        }

        public Object getPrLevel() {
            return prLevel;
        }

        public void setPrLevel(Object prLevel) {
            this.prLevel = prLevel;
        }

        public Object getPrJitype() {
            return prJitype;
        }

        public void setPrJitype(Object prJitype) {
            this.prJitype = prJitype;
        }

        public Object getPrRecentpurcdate() {
            return prRecentpurcdate;
        }

        public void setPrRecentpurcdate(Object prRecentpurcdate) {
            this.prRecentpurcdate = prRecentpurcdate;
        }

        public Object getPrBuyername() {
            return prBuyername;
        }

        public void setPrBuyername(Object prBuyername) {
            this.prBuyername = prBuyername;
        }

        public Object getPRRDBG() {
            return PRRDBG;
        }

        public void setPRRDBG(Object PRRDBG) {
            this.PRRDBG = PRRDBG;
        }

        public Object getPrCop() {
            return prCop;
        }

        public void setPrCop(Object prCop) {
            this.prCop = prCop;
        }

        public Object getPrMsdlevel() {
            return prMsdlevel;
        }

        public void setPrMsdlevel(Object prMsdlevel) {
            this.prMsdlevel = prMsdlevel;
        }

        public String getPrTracekind() {
            return prTracekind;
        }

        public void setPrTracekind(String prTracekind) {
            this.prTracekind = prTracekind;
        }

        public Object getPrIncomecatecode() {
            return prIncomecatecode;
        }

        public void setPrIncomecatecode(Object prIncomecatecode) {
            this.prIncomecatecode = prIncomecatecode;
        }

        public Object getPrGrade() {
            return prGrade;
        }

        public void setPrGrade(Object prGrade) {
            this.prGrade = prGrade;
        }

        public String getPrBoxSumUser() {
            return prBoxSumUser;
        }

        public void setPrBoxSumUser(String prBoxSumUser) {
            this.prBoxSumUser = prBoxSumUser;
        }

        public String getPrRecordman() {
            return prRecordman;
        }

        public void setPrRecordman(String prRecordman) {
            this.prRecordman = prRecordman;
        }

        public String getPrDhzc() {
            return prDhzc;
        }

        public void setPrDhzc(String prDhzc) {
            this.prDhzc = prDhzc;
        }

        public String getPrPurcrate() {
            return prPurcrate;
        }

        public void setPrPurcrate(String prPurcrate) {
            this.prPurcrate = prPurcrate;
        }

        public Object getPrPurccentercode() {
            return prPurccentercode;
        }

        public void setPrPurccentercode(Object prPurccentercode) {
            this.prPurccentercode = prPurccentercode;
        }

        public Object getPrPricestatus() {
            return prPricestatus;
        }

        public void setPrPricestatus(Object prPricestatus) {
            this.prPricestatus = prPricestatus;
        }

        public Object getPrWcname() {
            return prWcname;
        }

        public void setPrWcname(Object prWcname) {
            this.prWcname = prWcname;
        }

        public Object getPrVendprodcode() {
            return prVendprodcode;
        }

        public void setPrVendprodcode(Object prVendprodcode) {
            this.prVendprodcode = prVendprodcode;
        }

        public Object getPrRemarkBase() {
            return prRemarkBase;
        }

        public void setPrRemarkBase(Object prRemarkBase) {
            this.prRemarkBase = prRemarkBase;
        }

        public Object getPrSalecurrency() {
            return prSalecurrency;
        }

        public void setPrSalecurrency(Object prSalecurrency) {
            this.prSalecurrency = prSalecurrency;
        }

        public String getPrSpec() {
            return prSpec;
        }

        public void setPrSpec(String prSpec) {
            this.prSpec = prSpec;
        }

        public Object getPrUuid() {
            return prUuid;
        }

        public void setPrUuid(Object prUuid) {
            this.prUuid = prUuid;
        }

        public String getPrKczzl() {
            return prKczzl;
        }

        public void setPrKczzl(String prKczzl) {
            this.prKczzl = prKczzl;
        }

        public Object getPrSaleunit() {
            return prSaleunit;
        }

        public void setPrSaleunit(Object prSaleunit) {
            this.prSaleunit = prSaleunit;
        }

        public Object getPrSpeccs() {
            return prSpeccs;
        }

        public void setPrSpeccs(Object prSpeccs) {
            this.prSpeccs = prSpeccs;
        }

        public Object getPrIstracbefore() {
            return prIstracbefore;
        }

        public void setPrIstracbefore(Object prIstracbefore) {
            this.prIstracbefore = prIstracbefore;
        }

        public Object getPrNeedattach() {
            return prNeedattach;
        }

        public void setPrNeedattach(Object prNeedattach) {
            this.prNeedattach = prNeedattach;
        }

        public Object getPrSerial() {
            return prSerial;
        }

        public void setPrSerial(Object prSerial) {
            this.prSerial = prSerial;
        }

        public Object getPrQualmethod() {
            return prQualmethod;
        }

        public void setPrQualmethod(Object prQualmethod) {
            this.prQualmethod = prQualmethod;
        }

        public Object getPrVendname() {
            return prVendname;
        }

        public void setPrVendname(Object prVendname) {
            this.prVendname = prVendname;
        }

        public Object getPrWhcode() {
            return prWhcode;
        }

        public void setPrWhcode(Object prWhcode) {
            this.prWhcode = prWhcode;
        }

        public String getPrSqr() {
            return prSqr;
        }

        public void setPrSqr(String prSqr) {
            this.prSqr = prSqr;
        }

        public Object getPrIsvendorrate() {
            return prIsvendorrate;
        }

        public void setPrIsvendorrate(Object prIsvendorrate) {
            this.prIsvendorrate = prIsvendorrate;
        }

        public String getPrTestlossrate() {
            return prTestlossrate;
        }

        public void setPrTestlossrate(String prTestlossrate) {
            this.prTestlossrate = prTestlossrate;
        }

        public Object getPrXikind() {
            return prXikind;
        }

        public void setPrXikind(Object prXikind) {
            this.prXikind = prXikind;
        }

        public String getPrPurchasedays() {
            return prPurchasedays;
        }

        public void setPrPurchasedays(String prPurchasedays) {
            this.prPurchasedays = prPurchasedays;
        }

        public Object getPrSqrq() {
            return prSqrq;
        }

        public void setPrSqrq(Object prSqrq) {
            this.prSqrq = prSqrq;
        }

        public Object getPrPiccode() {
            return prPiccode;
        }

        public void setPrPiccode(Object prPiccode) {
            this.prPiccode = prPiccode;
        }

        public Object getPrRefno() {
            return prRefno;
        }

        public void setPrRefno(Object prRefno) {
            this.prRefno = prRefno;
        }

        public String getPrMachinetime() {
            return prMachinetime;
        }

        public void setPrMachinetime(String prMachinetime) {
            this.prMachinetime = prMachinetime;
        }

        public String getPrDocdate() {
            return prDocdate;
        }

        public void setPrDocdate(String prDocdate) {
            this.prDocdate = prDocdate;
        }

        public Object getPrCheckstatus() {
            return prCheckstatus;
        }

        public void setPrCheckstatus(Object prCheckstatus) {
            this.prCheckstatus = prCheckstatus;
        }

        public String getPrJkgsl() {
            return prJkgsl;
        }

        public void setPrJkgsl(String prJkgsl) {
            this.prJkgsl = prJkgsl;
        }

        public String getPrIsgrouppurc() {
            return prIsgrouppurc;
        }

        public void setPrIsgrouppurc(String prIsgrouppurc) {
            this.prIsgrouppurc = prIsgrouppurc;
        }

        public Object getPrAdmitstatus() {
            return prAdmitstatus;
        }

        public void setPrAdmitstatus(Object prAdmitstatus) {
            this.prAdmitstatus = prAdmitstatus;
        }

        public Object getPrPurcprice() {
            return prPurcprice;
        }

        public void setPrPurcprice(Object prPurcprice) {
            this.prPurcprice = prPurcprice;
        }

        public String getPrSafetystock() {
            return prSafetystock;
        }

        public void setPrSafetystock(String prSafetystock) {
            this.prSafetystock = prSafetystock;
        }

        public Object getPrCostcatecode() {
            return prCostcatecode;
        }

        public void setPrCostcatecode(Object prCostcatecode) {
            this.prCostcatecode = prCostcatecode;
        }

        public Object getPrKind3() {
            return prKind3;
        }

        public void setPrKind3(Object prKind3) {
            this.prKind3 = prKind3;
        }

        public String getPrKind2() {
            return prKind2;
        }

        public void setPrKind2(String prKind2) {
            this.prKind2 = prKind2;
        }

        public String getPrCode() {
            return prCode;
        }

        public void setPrCode(String prCode) {
            this.prCode = prCode;
        }

        public Object getPrParametereg() {
            return prParametereg;
        }

        public void setPrParametereg(Object prParametereg) {
            this.prParametereg = prParametereg;
        }

        public Object getPrSalecost() {
            return prSalecost;
        }

        public void setPrSalecost(Object prSalecost) {
            this.prSalecost = prSalecost;
        }

        public Object getPrStockcatename() {
            return prStockcatename;
        }

        public void setPrStockcatename(Object prStockcatename) {
            this.prStockcatename = prStockcatename;
        }

        public Object getPrRemark() {
            return prRemark;
        }

        public void setPrRemark(Object prRemark) {
            this.prRemark = prRemark;
        }

        public Object getPrBuyercode() {
            return prBuyercode;
        }

        public void setPrBuyercode(Object prBuyercode) {
            this.prBuyercode = prBuyercode;
        }

        public Object getPrOldcode() {
            return prOldcode;
        }

        public void setPrOldcode(Object prOldcode) {
            this.prOldcode = prOldcode;
        }

        public Object getPrNameeg() {
            return prNameeg;
        }

        public void setPrNameeg(Object prNameeg) {
            this.prNameeg = prNameeg;
        }

        public Object getPrCostlevel() {
            return prCostlevel;
        }

        public void setPrCostlevel(Object prCostlevel) {
            this.prCostlevel = prCostlevel;
        }

        public String getPrBzkczzl() {
            return prBzkczzl;
        }

        public void setPrBzkczzl(String prBzkczzl) {
            this.prBzkczzl = prBzkczzl;
        }

        public Object getPrIsmsd() {
            return prIsmsd;
        }

        public void setPrIsmsd(Object prIsmsd) {
            this.prIsmsd = prIsmsd;
        }

        public String getPrZxbzs() {
            return prZxbzs;
        }

        public void setPrZxbzs(String prZxbzs) {
            this.prZxbzs = prZxbzs;
        }

        public String getPrSmtpoint() {
            return prSmtpoint;
        }

        public void setPrSmtpoint(String prSmtpoint) {
            this.prSmtpoint = prSmtpoint;
        }

        public Object getPrCggdycode() {
            return prCggdycode;
        }

        public void setPrCggdycode(Object prCggdycode) {
            this.prCggdycode = prCggdycode;
        }

        public Object getPrSqename() {
            return prSqename;
        }

        public void setPrSqename(Object prSqename) {
            this.prSqename = prSqename;
        }

        public String getPrCombineqty() {
            return prCombineqty;
        }

        public void setPrCombineqty(String prCombineqty) {
            this.prCombineqty = prCombineqty;
        }

        public String getPrStatus() {
            return prStatus;
        }

        public void setPrStatus(String prStatus) {
            this.prStatus = prStatus;
        }

        public Object getPrPlanercode() {
            return prPlanercode;
        }

        public void setPrPlanercode(Object prPlanercode) {
            this.prPlanercode = prPlanercode;
        }

        public Object getPrWhmancode() {
            return prWhmancode;
        }

        public void setPrWhmancode(Object prWhmancode) {
            this.prWhmancode = prWhmancode;
        }

        public Object getPrDescription() {
            return prDescription;
        }

        public void setPrDescription(Object prDescription) {
            this.prDescription = prDescription;
        }

        public Object getPrAvpurcprice() {
            return prAvpurcprice;
        }

        public void setPrAvpurcprice(Object prAvpurcprice) {
            this.prAvpurcprice = prAvpurcprice;
        }

        public Object getPrMakecost() {
            return prMakecost;
        }

        public void setPrMakecost(Object prMakecost) {
            this.prMakecost = prMakecost;
        }

        public Object getPrCrman() {
            return prCrman;
        }

        public void setPrCrman(Object prCrman) {
            this.prCrman = prCrman;
        }

        public String getPrEconomicqty() {
            return prEconomicqty;
        }

        public void setPrEconomicqty(String prEconomicqty) {
            this.prEconomicqty = prEconomicqty;
        }

        public String getPrPkid() {
            return prPkid;
        }

        public void setPrPkid(String prPkid) {
            this.prPkid = prPkid;
        }

        public String getPrExportlossrate() {
            return prExportlossrate;
        }

        public void setPrExportlossrate(String prExportlossrate) {
            this.prExportlossrate = prExportlossrate;
        }

        public String getPrBrand() {
            return prBrand;
        }

        public void setPrBrand(String prBrand) {
            this.prBrand = prBrand;
        }

        public Object getPrWccode() {
            return prWccode;
        }

        public void setPrWccode(Object prWccode) {
            this.prWccode = prWccode;
        }

        public Object getPrMainvendcode() {
            return prMainvendcode;
        }

        public void setPrMainvendcode(Object prMainvendcode) {
            this.prMainvendcode = prMainvendcode;
        }

        public String getPrStandardprice() {
            return prStandardprice;
        }

        public void setPrStandardprice(String prStandardprice) {
            this.prStandardprice = prStandardprice;
        }

        public String getPrBplossrate() {
            return prBplossrate;
        }

        public void setPrBplossrate(String prBplossrate) {
            this.prBplossrate = prBplossrate;
        }

        public String getPrSelf() {
            return prSelf;
        }

        public void setPrSelf(String prSelf) {
            this.prSelf = prSelf;
        }

        public Object getPrSpecrule() {
            return prSpecrule;
        }

        public void setPrSpecrule(Object prSpecrule) {
            this.prSpecrule = prSpecrule;
        }

        public Object getPrMaterialcost() {
            return prMaterialcost;
        }

        public void setPrMaterialcost(Object prMaterialcost) {
            this.prMaterialcost = prMaterialcost;
        }

        public Object getPrSaleprice() {
            return prSaleprice;
        }

        public void setPrSaleprice(Object prSaleprice) {
            this.prSaleprice = prSaleprice;
        }

        public Object getPrSqecode() {
            return prSqecode;
        }

        public void setPrSqecode(Object prSqecode) {
            this.prSqecode = prSqecode;
        }

        public String getPrLossrate() {
            return prLossrate;
        }

        public void setPrLossrate(String prLossrate) {
            this.prLossrate = prLossrate;
        }

        public Object getPrUpdatedate() {
            return prUpdatedate;
        }

        public void setPrUpdatedate(Object prUpdatedate) {
            this.prUpdatedate = prUpdatedate;
        }

        public String getPrStandtime() {
            return prStandtime;
        }

        public void setPrStandtime(String prStandtime) {
            this.prStandtime = prStandtime;
        }

        public Object getPrRecentchangedate() {
            return prRecentchangedate;
        }

        public void setPrRecentchangedate(Object prRecentchangedate) {
            this.prRecentchangedate = prRecentchangedate;
        }

        public Object getPrAttach() {
            return prAttach;
        }

        public void setPrAttach(Object prAttach) {
            this.prAttach = prAttach;
        }

        public Object getPrVendorstatus() {
            return prVendorstatus;
        }

        public void setPrVendorstatus(Object prVendorstatus) {
            this.prVendorstatus = prVendorstatus;
        }

        public String getPrLtinstock() {
            return prLtinstock;
        }

        public void setPrLtinstock(String prLtinstock) {
            this.prLtinstock = prLtinstock;
        }

        public Object getPrParameterrule() {
            return prParameterrule;
        }

        public void setPrParameterrule(Object prParameterrule) {
            this.prParameterrule = prParameterrule;
        }

        public Object getPrBgname() {
            return prBgname;
        }

        public void setPrBgname(Object prBgname) {
            this.prBgname = prBgname;
        }

        public Object getPrAql() {
            return prAql;
        }

        public void setPrAql(Object prAql) {
            this.prAql = prAql;
        }

        public Object getPrRecentoutdate() {
            return prRecentoutdate;
        }

        public void setPrRecentoutdate(Object prRecentoutdate) {
            this.prRecentoutdate = prRecentoutdate;
        }

        public Object getPRSPECDESCRIPTION() {
            return PRSPECDESCRIPTION;
        }

        public void setPRSPECDESCRIPTION(Object PRSPECDESCRIPTION) {
            this.PRSPECDESCRIPTION = PRSPECDESCRIPTION;
        }

        public Object getPrSourcecode() {
            return prSourcecode;
        }

        public void setPrSourcecode(Object prSourcecode) {
            this.prSourcecode = prSourcecode;
        }

        public Object getPrCustomprice() {
            return prCustomprice;
        }

        public void setPrCustomprice(Object prCustomprice) {
            this.prCustomprice = prCustomprice;
        }

        public String getPrPurclossrate() {
            return prPurclossrate;
        }

        public void setPrPurclossrate(String prPurclossrate) {
            this.prPurclossrate = prPurclossrate;
        }

        public String getPrZxdhl() {
            return prZxdhl;
        }

        public void setPrZxdhl(String prZxdhl) {
            this.prZxdhl = prZxdhl;
        }

        public String getPrPlzl() {
            return prPlzl;
        }

        public void setPrPlzl(String prPlzl) {
            this.prPlzl = prPlzl;
        }

        public String getPrValiddays() {
            return prValiddays;
        }

        public void setPrValiddays(String prValiddays) {
            this.prValiddays = prValiddays;
        }

        public Object getPrCheckstatuscode() {
            return prCheckstatuscode;
        }

        public void setPrCheckstatuscode(Object prCheckstatuscode) {
            this.prCheckstatuscode = prCheckstatuscode;
        }

        public Object getPrIncomecatename() {
            return prIncomecatename;
        }

        public void setPrIncomecatename(Object prIncomecatename) {
            this.prIncomecatename = prIncomecatename;
        }

        public String getPrSupplytype() {
            return prSupplytype;
        }

        public void setPrSupplytype(String prSupplytype) {
            this.prSupplytype = prSupplytype;
        }

        public String getPrLeadtime() {
            return prLeadtime;
        }

        public void setPrLeadtime(String prLeadtime) {
            this.prLeadtime = prLeadtime;
        }
    }
}