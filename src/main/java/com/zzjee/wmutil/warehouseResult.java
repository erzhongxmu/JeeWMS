package com.zzjee.wmutil;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonAutoDetect
public class warehouseResult {

    /**
     * result : [{"wh_nocost":"0","wh_contact":null,"wh_ifoutmake":"0","wh_type":"良品仓","wh_ifmrp":"-1","wh_recorder":"管理员","wh_statuscode":"AUDITED","wh_isnotin":"0","wh_bonded":"0","wh_salecatecode":null,"wh_tel":null,"wh_costcateid":null,"wh_iflack":"-1","wh_ifclash":"-1","wh_cop":null,"wh_catecode":null,"wh_costcatecode":null,"wh_iflocation":"0","wh_departmentcode":null,"wh_level":null,"wh_status":"已审核","wh_ifvendbad":"0","wh_isnotout":"0","wh_ifmove":"0","wh_cateid":null,"wh_ifb2c":null,"wh_salecateid":null,"wh_ifbarcode":"-1","wh_subof":null,"wh_id":"31800","wh_subofname":null,"wh_factory":null,"wh_ifdefect":"0","wh_ifwip":"0","wh_description":"存手机","wh_date":"2018-08-30 00:00:00.0","wh_code":"csa12321"}]
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
         * wh_nocost : 0
         * wh_contact : null
         * wh_ifoutmake : 0
         * wh_type : 良品仓
         * wh_ifmrp : -1
         * wh_recorder : 管理员
         * wh_statuscode : AUDITED
         * wh_isnotin : 0
         * wh_bonded : 0
         * wh_salecatecode : null
         * wh_tel : null
         * wh_costcateid : null
         * wh_iflack : -1
         * wh_ifclash : -1
         * wh_cop : null
         * wh_catecode : null
         * wh_costcatecode : null
         * wh_iflocation : 0
         * wh_departmentcode : null
         * wh_level : null
         * wh_status : 已审核
         * wh_ifvendbad : 0
         * wh_isnotout : 0
         * wh_ifmove : 0
         * wh_cateid : null
         * wh_ifb2c : null
         * wh_salecateid : null
         * wh_ifbarcode : -1
         * wh_subof : null
         * wh_id : 31800
         * wh_subofname : null
         * wh_factory : null
         * wh_ifdefect : 0
         * wh_ifwip : 0
         * wh_description : 存手机
         * wh_date : 2018-08-30 00:00:00.0
         * wh_code : csa12321
         */

        @JsonProperty("wh_nocost")
        private String whNocost;
        @JsonProperty("wh_contact")
        private Object whContact;
        @JsonProperty("wh_ifoutmake")
        private String whIfoutmake;
        @JsonProperty("wh_type")
        private String whType;
        @JsonProperty("wh_ifmrp")
        private String whIfmrp;
        @JsonProperty("wh_recorder")
        private String whRecorder;
        @JsonProperty("wh_statuscode")
        private String whStatuscode;
        @JsonProperty("wh_isnotin")
        private String whIsnotin;
        @JsonProperty("wh_bonded")
        private String whBonded;
        @JsonProperty("wh_salecatecode")
        private Object whSalecatecode;
        @JsonProperty("wh_tel")
        private Object whTel;
        @JsonProperty("wh_costcateid")
        private Object whCostcateid;
        @JsonProperty("wh_iflack")
        private String whIflack;
        @JsonProperty("wh_ifclash")
        private String whIfclash;
        @JsonProperty("wh_cop")
        private Object whCop;
        @JsonProperty("wh_catecode")
        private Object whCatecode;
        @JsonProperty("wh_costcatecode")
        private Object whCostcatecode;
        @JsonProperty("wh_iflocation")
        private String whIflocation;
        @JsonProperty("wh_departmentcode")
        private Object whDepartmentcode;
        @JsonProperty("wh_level")
        private Object whLevel;
        @JsonProperty("wh_status")
        private String whStatus;
        @JsonProperty("wh_ifvendbad")
        private String whIfvendbad;
        @JsonProperty("wh_isnotout")
        private String whIsnotout;
        @JsonProperty("wh_ifmove")
        private String whIfmove;
        @JsonProperty("wh_cateid")
        private Object whCateid;
        @JsonProperty("wh_ifb2c")
        private Object whIfb2c;
        @JsonProperty("wh_salecateid")
        private Object whSalecateid;
        @JsonProperty("wh_ifbarcode")
        private String whIfbarcode;
        @JsonProperty("wh_subof")
        private Object whSubof;
        @JsonProperty("wh_id")
        private String whId;
        @JsonProperty("wh_subofname")
        private Object whSubofname;
        @JsonProperty("wh_factory")
        private Object whFactory;
        @JsonProperty("wh_ifdefect")
        private String whIfdefect;
        @JsonProperty("wh_ifwip")
        private String whIfwip;
        @JsonProperty("wh_description")
        private String whDescription;
        @JsonProperty("wh_date")
        private String whDate;
        @JsonProperty("wh_code")
        private String whCode;

        public String getWhNocost() {
            return whNocost;
        }

        public void setWhNocost(String whNocost) {
            this.whNocost = whNocost;
        }

        public Object getWhContact() {
            return whContact;
        }

        public void setWhContact(Object whContact) {
            this.whContact = whContact;
        }

        public String getWhIfoutmake() {
            return whIfoutmake;
        }

        public void setWhIfoutmake(String whIfoutmake) {
            this.whIfoutmake = whIfoutmake;
        }

        public String getWhType() {
            return whType;
        }

        public void setWhType(String whType) {
            this.whType = whType;
        }

        public String getWhIfmrp() {
            return whIfmrp;
        }

        public void setWhIfmrp(String whIfmrp) {
            this.whIfmrp = whIfmrp;
        }

        public String getWhRecorder() {
            return whRecorder;
        }

        public void setWhRecorder(String whRecorder) {
            this.whRecorder = whRecorder;
        }

        public String getWhStatuscode() {
            return whStatuscode;
        }

        public void setWhStatuscode(String whStatuscode) {
            this.whStatuscode = whStatuscode;
        }

        public String getWhIsnotin() {
            return whIsnotin;
        }

        public void setWhIsnotin(String whIsnotin) {
            this.whIsnotin = whIsnotin;
        }

        public String getWhBonded() {
            return whBonded;
        }

        public void setWhBonded(String whBonded) {
            this.whBonded = whBonded;
        }

        public Object getWhSalecatecode() {
            return whSalecatecode;
        }

        public void setWhSalecatecode(Object whSalecatecode) {
            this.whSalecatecode = whSalecatecode;
        }

        public Object getWhTel() {
            return whTel;
        }

        public void setWhTel(Object whTel) {
            this.whTel = whTel;
        }

        public Object getWhCostcateid() {
            return whCostcateid;
        }

        public void setWhCostcateid(Object whCostcateid) {
            this.whCostcateid = whCostcateid;
        }

        public String getWhIflack() {
            return whIflack;
        }

        public void setWhIflack(String whIflack) {
            this.whIflack = whIflack;
        }

        public String getWhIfclash() {
            return whIfclash;
        }

        public void setWhIfclash(String whIfclash) {
            this.whIfclash = whIfclash;
        }

        public Object getWhCop() {
            return whCop;
        }

        public void setWhCop(Object whCop) {
            this.whCop = whCop;
        }

        public Object getWhCatecode() {
            return whCatecode;
        }

        public void setWhCatecode(Object whCatecode) {
            this.whCatecode = whCatecode;
        }

        public Object getWhCostcatecode() {
            return whCostcatecode;
        }

        public void setWhCostcatecode(Object whCostcatecode) {
            this.whCostcatecode = whCostcatecode;
        }

        public String getWhIflocation() {
            return whIflocation;
        }

        public void setWhIflocation(String whIflocation) {
            this.whIflocation = whIflocation;
        }

        public Object getWhDepartmentcode() {
            return whDepartmentcode;
        }

        public void setWhDepartmentcode(Object whDepartmentcode) {
            this.whDepartmentcode = whDepartmentcode;
        }

        public Object getWhLevel() {
            return whLevel;
        }

        public void setWhLevel(Object whLevel) {
            this.whLevel = whLevel;
        }

        public String getWhStatus() {
            return whStatus;
        }

        public void setWhStatus(String whStatus) {
            this.whStatus = whStatus;
        }

        public String getWhIfvendbad() {
            return whIfvendbad;
        }

        public void setWhIfvendbad(String whIfvendbad) {
            this.whIfvendbad = whIfvendbad;
        }

        public String getWhIsnotout() {
            return whIsnotout;
        }

        public void setWhIsnotout(String whIsnotout) {
            this.whIsnotout = whIsnotout;
        }

        public String getWhIfmove() {
            return whIfmove;
        }

        public void setWhIfmove(String whIfmove) {
            this.whIfmove = whIfmove;
        }

        public Object getWhCateid() {
            return whCateid;
        }

        public void setWhCateid(Object whCateid) {
            this.whCateid = whCateid;
        }

        public Object getWhIfb2c() {
            return whIfb2c;
        }

        public void setWhIfb2c(Object whIfb2c) {
            this.whIfb2c = whIfb2c;
        }

        public Object getWhSalecateid() {
            return whSalecateid;
        }

        public void setWhSalecateid(Object whSalecateid) {
            this.whSalecateid = whSalecateid;
        }

        public String getWhIfbarcode() {
            return whIfbarcode;
        }

        public void setWhIfbarcode(String whIfbarcode) {
            this.whIfbarcode = whIfbarcode;
        }

        public Object getWhSubof() {
            return whSubof;
        }

        public void setWhSubof(Object whSubof) {
            this.whSubof = whSubof;
        }

        public String getWhId() {
            return whId;
        }

        public void setWhId(String whId) {
            this.whId = whId;
        }

        public Object getWhSubofname() {
            return whSubofname;
        }

        public void setWhSubofname(Object whSubofname) {
            this.whSubofname = whSubofname;
        }

        public Object getWhFactory() {
            return whFactory;
        }

        public void setWhFactory(Object whFactory) {
            this.whFactory = whFactory;
        }

        public String getWhIfdefect() {
            return whIfdefect;
        }

        public void setWhIfdefect(String whIfdefect) {
            this.whIfdefect = whIfdefect;
        }

        public String getWhIfwip() {
            return whIfwip;
        }

        public void setWhIfwip(String whIfwip) {
            this.whIfwip = whIfwip;
        }

        public String getWhDescription() {
            return whDescription;
        }

        public void setWhDescription(String whDescription) {
            this.whDescription = whDescription;
        }

        public String getWhDate() {
            return whDate;
        }

        public void setWhDate(String whDate) {
            this.whDate = whDate;
        }

        public String getWhCode() {
            return whCode;
        }

        public void setWhCode(String whCode) {
            this.whCode = whCode;
        }
    }
}
