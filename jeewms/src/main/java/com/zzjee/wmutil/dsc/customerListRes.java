package com.zzjee.wmutil.dsc;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class customerListRes {



    @JsonProperty("result")
    private String result;
    @JsonProperty("error")
    private int error;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("info")
    private InfoBean info;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * list : [{"user_id":"68","aite_id":"","email":"1491748198@qq.com","user_name":"8569326","nick_name":"","password":"e10adc3949ba59abbe56e057f20f883e","question":"","answer":"","sex":"2","birthday":"0000-00-00","user_money":"0.00","frozen_money":"0.00","pay_points":"0","rank_points":"0","address_id":"0","reg_time":"1575584970","last_login":"0","last_time":"0000-00-00 00:00:00","last_ip":"","visit_count":"0","user_rank":"0","is_special":"0","ec_salt":null,"salt":"0","parent_id":"0","flag":"0","alias":"","msn":"","qq":"","office_phone":"","home_phone":"","mobile_phone":"","is_validated":"0","credit_line":"0.00","passwd_question":"","passwd_answer":"","user_picture":"","old_user_picture":"","report_time":"0","drp_parent_id":"0"},{"user_id":"69","aite_id":"","email":"","user_name":"123456789","nick_name":"7362385-375417","password":"be6ee1380e61c346b28c45a1df1c7aeb","question":"","answer":"","sex":"0","birthday":"0000-00-00","user_money":"0.00","frozen_money":"0.00","pay_points":"0","rank_points":"0","address_id":"2","reg_time":"1576708778","last_login":"1576708801","last_time":"0000-00-00 00:00:00","last_ip":"111.121.0.220","visit_count":"2","user_rank":"6","is_special":"0","ec_salt":"8461","salt":"0","parent_id":"0","flag":"0","alias":"","msn":"","qq":"","office_phone":"","home_phone":"","mobile_phone":"13623826364","is_validated":"0","credit_line":"0.00","passwd_question":null,"passwd_answer":null,"user_picture":"","old_user_picture":"","report_time":"0","drp_parent_id":"0"}]
         * filter : {"page":1,"page_size":15,"record_count":"2","page_count":1}
         * page_count : 1
         * record_count : 2
         */

        @JsonProperty("filter")
        private FilterBean filter;
        @JsonProperty("page_count")
        private int pageCount;
        @JsonProperty("record_count")
        private String recordCount;
        @JsonProperty("list")
        private List<ListBean> list;

        public FilterBean getFilter() {
            return filter;
        }

        public void setFilter(FilterBean filter) {
            this.filter = filter;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public String getRecordCount() {
            return recordCount;
        }

        public void setRecordCount(String recordCount) {
            this.recordCount = recordCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class FilterBean {
            /**
             * page : 1
             * page_size : 15
             * record_count : 2
             * page_count : 1
             */

            @JsonProperty("page")
            private int page;
            @JsonProperty("page_size")
            private int pageSize;
            @JsonProperty("record_count")
            private String recordCount;
            @JsonProperty("page_count")
            private int pageCount;

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public String getRecordCount() {
                return recordCount;
            }

            public void setRecordCount(String recordCount) {
                this.recordCount = recordCount;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }
        }

        public static class ListBean {
            /**
             * user_id : 68
             * aite_id :
             * email : 1491748198@qq.com
             * user_name : 8569326
             * nick_name :
             * password : e10adc3949ba59abbe56e057f20f883e
             * question :
             * answer :
             * sex : 2
             * birthday : 0000-00-00
             * user_money : 0.00
             * frozen_money : 0.00
             * pay_points : 0
             * rank_points : 0
             * address_id : 0
             * reg_time : 1575584970
             * last_login : 0
             * last_time : 0000-00-00 00:00:00
             * last_ip :
             * visit_count : 0
             * user_rank : 0
             * is_special : 0
             * ec_salt : null
             * salt : 0
             * parent_id : 0
             * flag : 0
             * alias :
             * msn :
             * qq :
             * office_phone :
             * home_phone :
             * mobile_phone :
             * is_validated : 0
             * credit_line : 0.00
             * passwd_question :
             * passwd_answer :
             * user_picture :
             * old_user_picture :
             * report_time : 0
             * drp_parent_id : 0
             */

            @JsonProperty("user_id")
            private String userId;
            @JsonProperty("aite_id")
            private String aiteId;
            @JsonProperty("email")
            private String email;
            @JsonProperty("user_name")
            private String userName;
            @JsonProperty("nick_name")
            private String nickName;
            @JsonProperty("password")
            private String password;
            @JsonProperty("question")
            private String question;
            @JsonProperty("answer")
            private String answer;
            @JsonProperty("sex")
            private String sex;
            @JsonProperty("birthday")
            private String birthday;
            @JsonProperty("user_money")
            private String userMoney;
            @JsonProperty("frozen_money")
            private String frozenMoney;
            @JsonProperty("pay_points")
            private String payPoints;
            @JsonProperty("rank_points")
            private String rankPoints;
            @JsonProperty("address_id")
            private String addressId;
            @JsonProperty("reg_time")
            private String regTime;
            @JsonProperty("last_login")
            private String lastLogin;
            @JsonProperty("last_time")
            private String lastTime;
            @JsonProperty("last_ip")
            private String lastIp;
            @JsonProperty("visit_count")
            private String visitCount;
            @JsonProperty("user_rank")
            private String userRank;
            @JsonProperty("is_special")
            private String isSpecial;
            @JsonProperty("ec_salt")
            private Object ecSalt;
            @JsonProperty("salt")
            private String salt;
            @JsonProperty("parent_id")
            private String parentId;
            @JsonProperty("flag")
            private String flag;
            @JsonProperty("alias")
            private String alias;
            @JsonProperty("msn")
            private String msn;
            @JsonProperty("qq")
            private String qq;
            @JsonProperty("office_phone")
            private String officePhone;
            @JsonProperty("home_phone")
            private String homePhone;
            @JsonProperty("mobile_phone")
            private String mobilePhone;
            @JsonProperty("is_validated")
            private String isValidated;
            @JsonProperty("credit_line")
            private String creditLine;
            @JsonProperty("passwd_question")
            private String passwdQuestion;
            @JsonProperty("passwd_answer")
            private String passwdAnswer;
            @JsonProperty("user_picture")
            private String userPicture;
            @JsonProperty("old_user_picture")
            private String oldUserPicture;
            @JsonProperty("report_time")
            private String reportTime;
            @JsonProperty("drp_parent_id")
            private String drpParentId;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getAiteId() {
                return aiteId;
            }

            public void setAiteId(String aiteId) {
                this.aiteId = aiteId;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getUserMoney() {
                return userMoney;
            }

            public void setUserMoney(String userMoney) {
                this.userMoney = userMoney;
            }

            public String getFrozenMoney() {
                return frozenMoney;
            }

            public void setFrozenMoney(String frozenMoney) {
                this.frozenMoney = frozenMoney;
            }

            public String getPayPoints() {
                return payPoints;
            }

            public void setPayPoints(String payPoints) {
                this.payPoints = payPoints;
            }

            public String getRankPoints() {
                return rankPoints;
            }

            public void setRankPoints(String rankPoints) {
                this.rankPoints = rankPoints;
            }

            public String getAddressId() {
                return addressId;
            }

            public void setAddressId(String addressId) {
                this.addressId = addressId;
            }

            public String getRegTime() {
                return regTime;
            }

            public void setRegTime(String regTime) {
                this.regTime = regTime;
            }

            public String getLastLogin() {
                return lastLogin;
            }

            public void setLastLogin(String lastLogin) {
                this.lastLogin = lastLogin;
            }

            public String getLastTime() {
                return lastTime;
            }

            public void setLastTime(String lastTime) {
                this.lastTime = lastTime;
            }

            public String getLastIp() {
                return lastIp;
            }

            public void setLastIp(String lastIp) {
                this.lastIp = lastIp;
            }

            public String getVisitCount() {
                return visitCount;
            }

            public void setVisitCount(String visitCount) {
                this.visitCount = visitCount;
            }

            public String getUserRank() {
                return userRank;
            }

            public void setUserRank(String userRank) {
                this.userRank = userRank;
            }

            public String getIsSpecial() {
                return isSpecial;
            }

            public void setIsSpecial(String isSpecial) {
                this.isSpecial = isSpecial;
            }

            public Object getEcSalt() {
                return ecSalt;
            }

            public void setEcSalt(Object ecSalt) {
                this.ecSalt = ecSalt;
            }

            public String getSalt() {
                return salt;
            }

            public void setSalt(String salt) {
                this.salt = salt;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getMsn() {
                return msn;
            }

            public void setMsn(String msn) {
                this.msn = msn;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getOfficePhone() {
                return officePhone;
            }

            public void setOfficePhone(String officePhone) {
                this.officePhone = officePhone;
            }

            public String getHomePhone() {
                return homePhone;
            }

            public void setHomePhone(String homePhone) {
                this.homePhone = homePhone;
            }

            public String getMobilePhone() {
                return mobilePhone;
            }

            public void setMobilePhone(String mobilePhone) {
                this.mobilePhone = mobilePhone;
            }

            public String getIsValidated() {
                return isValidated;
            }

            public void setIsValidated(String isValidated) {
                this.isValidated = isValidated;
            }

            public String getCreditLine() {
                return creditLine;
            }

            public void setCreditLine(String creditLine) {
                this.creditLine = creditLine;
            }

            public String getPasswdQuestion() {
                return passwdQuestion;
            }

            public void setPasswdQuestion(String passwdQuestion) {
                this.passwdQuestion = passwdQuestion;
            }

            public String getPasswdAnswer() {
                return passwdAnswer;
            }

            public void setPasswdAnswer(String passwdAnswer) {
                this.passwdAnswer = passwdAnswer;
            }

            public String getUserPicture() {
                return userPicture;
            }

            public void setUserPicture(String userPicture) {
                this.userPicture = userPicture;
            }

            public String getOldUserPicture() {
                return oldUserPicture;
            }

            public void setOldUserPicture(String oldUserPicture) {
                this.oldUserPicture = oldUserPicture;
            }

            public String getReportTime() {
                return reportTime;
            }

            public void setReportTime(String reportTime) {
                this.reportTime = reportTime;
            }

            public String getDrpParentId() {
                return drpParentId;
            }

            public void setDrpParentId(String drpParentId) {
                this.drpParentId = drpParentId;
            }
        }
    }
}
