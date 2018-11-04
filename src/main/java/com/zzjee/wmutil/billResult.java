package com.zzjee.wmutil;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonAutoDetect
public class billResult {

    /**
     * data : [{"pi_date1":"2018-04-25 15:32:07","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"PA001","pi_cardid":95170,"pi_auditdate":"2018-04-25 15:32:01","pi_currency":"RMB","pi_paydate":"2018-04-25 00:00:00","pi_updatedate":"2018-04-25 00:00:00","pi_sendcode":null,"pi_receivecode":"450005","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"450005","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"TEST","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"TEST","pi_date":"2018-04-25 15:26:28","pi_auditman":"TEST","pi_monthenddate":"2018-04-25 00:00:00","pi_recorddate":"2018-04-25 00:00:00","pi_transport":null,"pi_cop":" ","pi_title":"北京得力文具有限公司1","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"TEST","pi_printstatus":"已打印","pi_updateman":"TEST","pi_invostatuscode":"AUDITED","pi_receivename":"北京得力文具有限公司1","pi_statuscode":"POSTED","pi_id":50725940,"detail":[{"pd_remark":"[ SS18040088:1];","pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":0,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946679,"pd_total":8.62,"pd_taxtotal":1.38,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":1,"pd_piclass":"采购验收单","pd_batchid":10961225,"pd_ycheck":0,"pd_ordertotal":10,"pd_prodid":100011354,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180400045","pd_invoqty":0,"pd_price":8.62069,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18040024","pd_batchcode":"18042500011","pd_taxprice":0,"pd_prodmadedate":"2018-04-26 00:00:00","pd_orderid":51001926,"pd_prodcode":"11233","pd_description":null,"pd_inqty":1,"pd_pdno":1,"pd_replydate":"2018-04-26 00:00:00","pd_taxamount":0,"pd_piid":50725940,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":10,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18040024","pi_departmentcode":null},{"pi_date1":"2018-04-27 14:46:49","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"PA001","pi_cardid":95152,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-04-27 00:00:00","pi_updatedate":"2018-04-27 14:45:44","pi_sendcode":null,"pi_receivecode":"100000006","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"100000006","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"管理员","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-04-27 14:45:44","pi_auditman":null,"pi_monthenddate":"2018-04-27 00:00:00","pi_recorddate":"2018-04-27 14:45:44","pi_transport":"陆运","pi_cop":null,"pi_title":"上海雀巢产品服务有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"ENTERING","pi_receivename":"上海雀巢产品服务有限公司","pi_statuscode":"POSTED","pi_id":50725993,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":0,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946747,"pd_total":170.94,"pd_taxtotal":29.06,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":10961262,"pd_ycheck":null,"pd_ordertotal":200,"pd_prodid":100010883,"pd_customprice":null,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180400046","pd_invoqty":0,"pd_price":1.709402,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":17,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":2,"pd_inoutno":"YS18040026","pd_batchcode":"18042700028","pd_taxprice":null,"pd_prodmadedate":"2018-04-27 14:45:44","pd_orderid":51001937,"pd_prodcode":"01021056","pd_description":null,"pd_inqty":100,"pd_pdno":1,"pd_replydate":"2019-10-21 14:45:44","pd_taxamount":null,"pd_piid":50725993,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":2,"pd_seller":null},{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":0,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946748,"pd_total":170.94,"pd_taxtotal":29.06,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":10961263,"pd_ycheck":null,"pd_ordertotal":200,"pd_prodid":100010876,"pd_customprice":null,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180400046","pd_invoqty":0,"pd_price":1.709402,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":17,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18040026","pd_batchcode":"18042700029","pd_taxprice":null,"pd_prodmadedate":"2018-04-27 14:45:44","pd_orderid":51001936,"pd_prodcode":"01021047","pd_description":null,"pd_inqty":100,"pd_pdno":2,"pd_replydate":"2020-04-26 14:45:44","pd_taxamount":null,"pd_piid":50725993,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":2,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18040026","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95283,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-05-08 08:34:04","pi_sendcode":null,"pi_receivecode":"GN0047","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"GN0047","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0038","pi_date":"2018-05-08 08:34:04","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-05-08 08:34:04","pi_transport":null,"pi_cop":null,"pi_title":"金政环球商贸有限公司","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":"康成真","pi_printstatus":"未打印","pi_updateman":"TEST","pi_invostatuscode":"ENTERING","pi_receivename":"金政环球商贸有限公司","pi_statuscode":"UNPOST","pi_id":50726014,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":null,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946786,"pd_total":null,"pd_taxtotal":390.21,"pd_whcode":null,"pd_barcodeinqty":null,"pd_purcinqty":23,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":2829,"pd_prodid":100015493,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500002","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":null,"pd_taxrate":16,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050001","pd_batchcode":null,"pd_taxprice":null,"pd_prodmadedate":null,"pd_orderid":51001957,"pd_prodcode":"C030772","pd_description":null,"pd_inqty":23,"pd_pdno":1,"pd_replydate":null,"pd_taxamount":null,"pd_piid":50726014,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":123,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050001","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95283,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-05-08 08:36:52","pi_sendcode":null,"pi_receivecode":"GN0047","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"GN0047","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0038","pi_date":"2018-05-08 08:36:52","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-05-08 08:36:52","pi_transport":null,"pi_cop":null,"pi_title":"金政环球商贸有限公司","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":"康成真","pi_printstatus":"未打印","pi_updateman":"TEST","pi_invostatuscode":"ENTERING","pi_receivename":"金政环球商贸有限公司","pi_statuscode":"UNPOST","pi_id":50726015,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":null,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946787,"pd_total":null,"pd_taxtotal":1696.55,"pd_whcode":null,"pd_barcodeinqty":null,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":12300,"pd_prodid":100015493,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500002","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":null,"pd_taxrate":16,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050002","pd_batchcode":null,"pd_taxprice":null,"pd_prodmadedate":null,"pd_orderid":51001957,"pd_prodcode":"C030772","pd_description":null,"pd_inqty":100,"pd_pdno":1,"pd_replydate":null,"pd_taxamount":null,"pd_piid":50726015,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":123,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050002","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95343,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-05-08 08:38:56","pi_sendcode":null,"pi_receivecode":"GN0049","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"GN0049","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0038","pi_date":"2018-05-08 08:38:56","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-05-08 08:38:56","pi_transport":null,"pi_cop":null,"pi_title":"广州蓝芒贸易有限公司","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":"康成真","pi_printstatus":"未打印","pi_updateman":"TEST","pi_invostatuscode":"ENTERING","pi_receivename":"广州蓝芒贸易有限公司","pi_statuscode":"UNPOST","pi_id":50726016,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":null,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946788,"pd_total":null,"pd_taxtotal":593.04,"pd_whcode":null,"pd_barcodeinqty":null,"pd_purcinqty":43,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":4299.57,"pd_prodid":100015498,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500001","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":null,"pd_taxrate":16,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050003","pd_batchcode":null,"pd_taxprice":null,"pd_prodmadedate":null,"pd_orderid":51001956,"pd_prodcode":"C030773","pd_description":null,"pd_inqty":43,"pd_pdno":1,"pd_replydate":null,"pd_taxamount":null,"pd_piid":50726016,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":99.99,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050003","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95343,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-05-08 08:39:25","pi_sendcode":null,"pi_receivecode":"GN0049","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"GN0049","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0038","pi_date":"2018-05-08 08:39:25","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-05-08 08:39:25","pi_transport":null,"pi_cop":null,"pi_title":"广州蓝芒贸易有限公司","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":"康成真","pi_printstatus":"未打印","pi_updateman":"TEST","pi_invostatuscode":"ENTERING","pi_receivename":"广州蓝芒贸易有限公司","pi_statuscode":"UNPOST","pi_id":50726017,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":null,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946789,"pd_total":null,"pd_taxtotal":786.13,"pd_whcode":null,"pd_barcodeinqty":null,"pd_purcinqty":57,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":5699.43,"pd_prodid":100015498,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500001","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":null,"pd_taxrate":16,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050004","pd_batchcode":null,"pd_taxprice":null,"pd_prodmadedate":null,"pd_orderid":51001956,"pd_prodcode":"C030773","pd_description":null,"pd_inqty":57,"pd_pdno":1,"pd_replydate":null,"pd_taxamount":null,"pd_piid":50726017,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":99.99,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050004","pi_departmentcode":null},{"pi_date1":"2018-05-09 08:39:46","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"PA001","pi_cardid":95443,"pi_auditdate":"2018-05-09 08:39:35","pi_currency":"RMB","pi_paydate":"2018-05-09 00:00:00","pi_updatedate":"2018-05-09 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0055","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"GN0055","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"TEST5","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST5","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0094","pi_date":"2018-05-09 08:38:46","pi_auditman":"TEST5","pi_monthenddate":"2018-05-09 00:00:00","pi_recorddate":"2018-05-09 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"深圳市香记咖啡有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"赵小英","pi_printstatus":"未打印","pi_updateman":"TEST5","pi_invostatuscode":"AUDITED","pi_receivename":"深圳市香记咖啡有限公司","pi_statuscode":"POSTED","pi_id":50726055,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":33,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946827,"pd_total":99.1,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":33,"pd_piclass":"采购验收单","pd_batchid":10961282,"pd_ycheck":0,"pd_ordertotal":99.1,"pd_prodid":100010876,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500007","pd_invoqty":33,"pd_price":3.003003,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050006","pd_batchcode":"18050900003","pd_taxprice":0,"pd_prodmadedate":"2018-05-09 00:00:00","pd_orderid":51001979,"pd_prodcode":"01021047","pd_description":null,"pd_inqty":33,"pd_pdno":1,"pd_replydate":"2020-05-08 08:38:46","pd_taxamount":0,"pd_piid":50726055,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":3.003003,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050006","pi_departmentcode":null},{"pi_date1":"2018-05-09 08:44:28","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"PA001","pi_cardid":95443,"pi_auditdate":"2018-05-09 08:44:17","pi_currency":"RMB","pi_paydate":"2018-05-09 00:00:00","pi_updatedate":"2018-05-09 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0055","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"GN0055","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"TEST5","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST5","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0094","pi_date":"2018-05-09 08:43:27","pi_auditman":"TEST5","pi_monthenddate":"2018-05-09 00:00:00","pi_recorddate":"2018-05-09 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"深圳市香记咖啡有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"赵小英","pi_printstatus":"未打印","pi_updateman":"TEST5","pi_invostatuscode":"AUDITED","pi_receivename":"深圳市香记咖啡有限公司","pi_statuscode":"POSTED","pi_id":50726056,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":33,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946828,"pd_total":99.1,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":33,"pd_piclass":"采购验收单","pd_batchid":10961283,"pd_ycheck":0,"pd_ordertotal":99.1,"pd_prodid":100010876,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500007","pd_invoqty":33,"pd_price":3.003003,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050007","pd_batchcode":"18050900005","pd_taxprice":0,"pd_prodmadedate":"2018-05-09 00:00:00","pd_orderid":51001979,"pd_prodcode":"01021047","pd_description":null,"pd_inqty":33,"pd_pdno":1,"pd_replydate":"2020-05-08 08:43:27","pd_taxamount":0,"pd_piid":50726056,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":3.003003,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050007","pi_departmentcode":null},{"pi_date1":"2018-05-09 08:45:59","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"PA001","pi_cardid":95443,"pi_auditdate":"2018-05-09 08:45:47","pi_currency":"RMB","pi_paydate":"2018-05-09 00:00:00","pi_updatedate":"2018-05-09 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0055","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"GN0055","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"TEST5","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST5","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0094","pi_date":"2018-05-09 08:44:57","pi_auditman":"TEST5","pi_monthenddate":"2018-05-09 00:00:00","pi_recorddate":"2018-05-09 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"深圳市香记咖啡有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"赵小英","pi_printstatus":"未打印","pi_updateman":"TEST5","pi_invostatuscode":"AUDITED","pi_receivename":"深圳市香记咖啡有限公司","pi_statuscode":"POSTED","pi_id":50726057,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":100,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946829,"pd_total":300.3,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":10961284,"pd_ycheck":0,"pd_ordertotal":300.3,"pd_prodid":100010876,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500007","pd_invoqty":100,"pd_price":3.003003,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050008","pd_batchcode":"18050900007","pd_taxprice":0,"pd_prodmadedate":"2018-05-09 00:00:00","pd_orderid":51001979,"pd_prodcode":"01021047","pd_description":null,"pd_inqty":100,"pd_pdno":1,"pd_replydate":"2020-05-08 08:44:57","pd_taxamount":0,"pd_piid":50726057,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":3.003003,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050008","pi_departmentcode":null},{"pi_date1":"2018-05-09 08:47:24","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"PA001","pi_cardid":95443,"pi_auditdate":"2018-05-09 08:47:08","pi_currency":"RMB","pi_paydate":"2018-05-09 00:00:00","pi_updatedate":"2018-05-09 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0055","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"GN0055","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"TEST5","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST5","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0094","pi_date":"2018-05-09 08:46:21","pi_auditman":"TEST5","pi_monthenddate":"2018-05-09 00:00:00","pi_recorddate":"2018-05-09 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"深圳市香记咖啡有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"赵小英","pi_printstatus":"未打印","pi_updateman":"TEST5","pi_invostatuscode":"AUDITED","pi_receivename":"深圳市香记咖啡有限公司","pi_statuscode":"POSTED","pi_id":50726058,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":33,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946830,"pd_total":99.1,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":33,"pd_piclass":"采购验收单","pd_batchid":10961285,"pd_ycheck":0,"pd_ordertotal":99.1,"pd_prodid":100010876,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500007","pd_invoqty":33,"pd_price":3.003003,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050009","pd_batchcode":"18050900009","pd_taxprice":0,"pd_prodmadedate":"2018-05-09 00:00:00","pd_orderid":51001979,"pd_prodcode":"01021047","pd_description":null,"pd_inqty":33,"pd_pdno":1,"pd_replydate":"2020-05-08 08:46:21","pd_taxamount":0,"pd_piid":50726058,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":3.003003,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050009","pi_departmentcode":null},{"pi_date1":"2018-05-09 08:48:45","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"PA001","pi_cardid":95443,"pi_auditdate":"2018-05-09 08:48:34","pi_currency":"RMB","pi_paydate":"2018-05-09 00:00:00","pi_updatedate":"2018-05-09 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0055","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"GN0055","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"TEST5","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST5","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0094","pi_date":"2018-05-09 08:47:47","pi_auditman":"TEST5","pi_monthenddate":"2018-05-09 00:00:00","pi_recorddate":"2018-05-09 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"深圳市香记咖啡有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"赵小英","pi_printstatus":"未打印","pi_updateman":"TEST5","pi_invostatuscode":"AUDITED","pi_receivename":"深圳市香记咖啡有限公司","pi_statuscode":"POSTED","pi_id":50726059,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":134,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946831,"pd_total":402.4,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":134,"pd_piclass":"采购验收单","pd_batchid":10961286,"pd_ycheck":0,"pd_ordertotal":402.4,"pd_prodid":100010876,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500007","pd_invoqty":134,"pd_price":3.003003,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050010","pd_batchcode":"18050900011","pd_taxprice":0,"pd_prodmadedate":"2018-05-09 00:00:00","pd_orderid":51001979,"pd_prodcode":"01021047","pd_description":null,"pd_inqty":134,"pd_pdno":1,"pd_replydate":"2020-05-08 08:47:47","pd_taxamount":0,"pd_piid":50726059,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":3.003003,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050010","pi_departmentcode":null},{"pi_date1":"2018-05-10 10:28:20","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"PA001","pi_cardid":95463,"pi_auditdate":"2018-05-10 10:26:07","pi_currency":"RMB","pi_paydate":"2018-05-10 00:00:00","pi_updatedate":"2018-05-10 00:00:00","pi_sendcode":null,"pi_receivecode":"LS0016","pi_invocode":null,"pi_billstatus":"未开票","pi_invostatus":"已审核","pi_cardcode":"LS0016","pi_type":null,"pi_cgycode":null,"pi_whname":"仓库名称","pi_inoutman":"TEST","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-05-10 10:23:43","pi_auditman":"TEST","pi_monthenddate":"2018-05-10 00:00:00","pi_recorddate":"2018-05-10 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"上海邕兴贸易有限公司","pi_rate":1,"pi_whcode":"B","pi_status":"已过账","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"TEST","pi_invostatuscode":"AUDITED","pi_receivename":"上海邕兴贸易有限公司","pi_statuscode":"POSTED","pi_id":50726095,"detail":[{"pd_remark":"TT","pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":101,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946867,"pd_total":870.69,"pd_taxtotal":139.31,"pd_whcode":"B","pd_barcodeinqty":0,"pd_purcinqty":101,"pd_piclass":"采购验收单","pd_batchid":10961302,"pd_ycheck":0,"pd_ordertotal":1010,"pd_prodid":100015561,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500008","pd_invoqty":101,"pd_price":8.62069,"pd_prjname":null,"pd_location":null,"pd_whname":"仓库名称","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050011","pd_batchcode":"18051000001","pd_taxprice":0,"pd_prodmadedate":"2018-05-10 00:00:00","pd_orderid":51001996,"pd_prodcode":"F031171","pd_description":null,"pd_inqty":101,"pd_pdno":1,"pd_replydate":"2021-04-24 00:00:00","pd_taxamount":0,"pd_piid":50726095,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":10,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050011","pi_departmentcode":null},{"pi_date1":"2018-06-22 08:39:55","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95443,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-05-21 00:00:00","pi_updatedate":"2018-06-22 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0055","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已提交","pi_cardcode":"GN0055","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"TEST","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0094","pi_date":"2018-05-21 13:36:58","pi_auditman":null,"pi_monthenddate":"2018-05-21 00:00:00","pi_recorddate":"2018-05-21 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"深圳市香记咖啡有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"赵小英","pi_printstatus":"未打印","pi_updateman":"TEST","pi_invostatuscode":"COMMITED","pi_receivename":"深圳市香记咖啡有限公司","pi_statuscode":"POSTED","pi_id":50726294,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":60,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947066,"pd_total":3620.69,"pd_taxtotal":579.31,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":60,"pd_piclass":"采购验收单","pd_batchid":10961502,"pd_ycheck":0,"pd_ordertotal":4200,"pd_prodid":100015513,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":"2018-06-22 00:00:00","pd_ordercode":"MP180500004","pd_invoqty":60,"pd_price":60.344828,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2018-06-22 00:00:00","pd_orderdetno":1,"pd_inoutno":"YS18050013","pd_batchcode":"18062200001","pd_taxprice":0,"pd_prodmadedate":"2018-06-22 00:00:00","pd_orderid":51001976,"pd_prodcode":"F010083","pd_description":null,"pd_inqty":60,"pd_pdno":1,"pd_replydate":"2018-06-22 00:00:00","pd_taxamount":0,"pd_piid":50726294,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":70,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050013","pi_departmentcode":null},{"pi_date1":"2018-05-26 13:20:00","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"PA001","pi_cardid":95723,"pi_auditdate":"2018-05-26 13:19:52","pi_currency":"RMB","pi_paydate":"2018-05-26 00:00:00","pi_updatedate":"2018-05-26 00:00:00","pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"TEST","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"TEST","pi_date":"2018-05-26 13:00:25","pi_auditman":"TEST","pi_monthenddate":"2018-05-26 00:00:00","pi_recorddate":"2018-05-26 00:00:00","pi_transport":null,"pi_cop":" ","pi_title":"咖啡之星","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"TEST","pi_printstatus":"未打印","pi_updateman":"TEST","pi_invostatuscode":"AUDITED","pi_receivename":"咖啡之星","pi_statuscode":"POSTED","pi_id":50726354,"detail":[{"pd_remark":"[ SS18050016:1000];","pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":1000,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947126,"pd_total":6896.55,"pd_taxtotal":1103.45,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":1000,"pd_piclass":"采购验收单","pd_batchid":10961382,"pd_ycheck":null,"pd_ordertotal":8000,"pd_prodid":100015996,"pd_customprice":null,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500011","pd_invoqty":1000,"pd_price":6.896552,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":16,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050014","pd_batchcode":"18052600001","pd_taxprice":null,"pd_prodmadedate":"2018-05-26 00:00:00","pd_orderid":51002076,"pd_prodcode":"F031192","pd_description":null,"pd_inqty":1000,"pd_pdno":1,"pd_replydate":"2021-05-10 00:00:00","pd_taxamount":null,"pd_piid":50726354,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":8,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050014","pi_departmentcode":null},{"pi_date1":"2018-05-26 14:33:32","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"PA001","pi_cardid":95674,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-05-26 00:00:00","pi_updatedate":"2018-05-26 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0138","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已提交","pi_cardcode":"GN0138","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"赵小英","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"赵小英","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0038","pi_date":"2018-05-26 14:29:45","pi_auditman":null,"pi_monthenddate":"2018-05-26 00:00:00","pi_recorddate":"2018-05-26 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"和路雪（中国）有限公司天津分公司（广州）","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"康成真","pi_printstatus":"未打印","pi_updateman":"赵小英","pi_invostatuscode":"COMMITED","pi_receivename":"和路雪（中国）有限公司天津分公司（广州）","pi_statuscode":"POSTED","pi_id":50726360,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":8,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947133,"pd_total":172.41,"pd_taxtotal":27.59,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":8,"pd_piclass":"采购验收单","pd_batchid":10961384,"pd_ycheck":0,"pd_ordertotal":200,"pd_prodid":100011064,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500012","pd_invoqty":8,"pd_price":21.551724,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":3,"pd_inoutno":"YS18050015","pd_batchcode":"18052600008","pd_taxprice":0,"pd_prodmadedate":"2018-05-26 00:00:00","pd_orderid":51002079,"pd_prodcode":"01031065","pd_description":null,"pd_inqty":8,"pd_pdno":1,"pd_replydate":"2020-05-25 00:00:00","pd_taxamount":0,"pd_piid":50726360,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":25,"pd_seller":null},{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":12,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947134,"pd_total":620.69,"pd_taxtotal":99.31,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":12,"pd_piclass":"采购验收单","pd_batchid":10961385,"pd_ycheck":0,"pd_ordertotal":720,"pd_prodid":100010912,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500012","pd_invoqty":12,"pd_price":51.724138,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":2,"pd_inoutno":"YS18050015","pd_batchcode":"18052600009","pd_taxprice":0,"pd_prodmadedate":"2018-05-26 00:00:00","pd_orderid":51002078,"pd_prodcode":"01031014","pd_description":null,"pd_inqty":12,"pd_pdno":2,"pd_replydate":"2021-05-25 00:00:00","pd_taxamount":0,"pd_piid":50726360,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":60,"pd_seller":null},{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":20,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947135,"pd_total":318.97,"pd_taxtotal":51.03,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":20,"pd_piclass":"采购验收单","pd_batchid":10961386,"pd_ycheck":0,"pd_ordertotal":370,"pd_prodid":100011280,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500012","pd_invoqty":20,"pd_price":15.948276,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050015","pd_batchcode":"18052600010","pd_taxprice":0,"pd_prodmadedate":"2018-05-25 00:00:00","pd_orderid":51002077,"pd_prodcode":"01031075","pd_description":null,"pd_inqty":20,"pd_pdno":3,"pd_replydate":"2018-05-25 00:00:00","pd_taxamount":0,"pd_piid":50726360,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":18.5,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050015","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95674,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-05-26 15:20:22","pi_sendcode":null,"pi_receivecode":"GN0138","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"GN0138","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"刘岩","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0038","pi_date":"2018-05-26 15:20:22","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-05-26 15:20:22","pi_transport":null,"pi_cop":null,"pi_title":"和路雪（中国）有限公司天津分公司（广州）","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":"康成真","pi_printstatus":"未打印","pi_updateman":"刘岩","pi_invostatuscode":"ENTERING","pi_receivename":"和路雪（中国）有限公司天津分公司（广州）","pi_statuscode":"UNPOST","pi_id":50726362,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":null,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947142,"pd_total":null,"pd_taxtotal":29.79,"pd_whcode":null,"pd_barcodeinqty":null,"pd_purcinqty":24,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":216,"pd_prodid":100010786,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180500014","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":null,"pd_taxrate":16,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18050016","pd_batchcode":null,"pd_taxprice":null,"pd_prodmadedate":null,"pd_orderid":51002081,"pd_prodcode":"01011007","pd_description":null,"pd_inqty":24,"pd_pdno":1,"pd_replydate":null,"pd_taxamount":null,"pd_piid":50726362,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":9,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18050016","pi_departmentcode":null},{"pi_date1":"2018-06-06 09:21:50","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95723,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-06-06 00:00:00","pi_updatedate":"2018-06-06 00:00:00","pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已提交","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"赵小英","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"赵小英","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-06-06 09:19:39","pi_auditman":null,"pi_monthenddate":"2018-06-06 00:00:00","pi_recorddate":"2018-06-06 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"咖啡之星","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"赵小英","pi_invostatuscode":"COMMITED","pi_receivename":"咖啡之星","pi_statuscode":"POSTED","pi_id":50726394,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":12,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947166,"pd_total":240,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":12,"pd_piclass":"采购验收单","pd_batchid":10961402,"pd_ycheck":0,"pd_ordertotal":240,"pd_prodid":100015994,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600001","pd_invoqty":12,"pd_price":20,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18060001","pd_batchcode":"18060600001","pd_taxprice":0,"pd_prodmadedate":"2018-06-06 00:00:00","pd_orderid":51002136,"pd_prodcode":"C010489","pd_description":null,"pd_inqty":12,"pd_pdno":1,"pd_replydate":"2018-06-06 00:00:00","pd_taxamount":0,"pd_piid":50726394,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":20,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18060001","pi_departmentcode":null},{"pi_date1":"2018-06-09 15:53:06","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95723,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-06-09 00:00:00","pi_updatedate":"2018-06-09 00:00:00","pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已提交","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"赵小英","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"赵小英","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-06-09 15:51:45","pi_auditman":null,"pi_monthenddate":"2018-06-09 00:00:00","pi_recorddate":"2018-06-09 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"咖啡之星","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"赵小英","pi_invostatuscode":"COMMITED","pi_receivename":"咖啡之星","pi_statuscode":"POSTED","pi_id":50726414,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":12,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947186,"pd_total":300,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":12,"pd_piclass":"采购验收单","pd_batchid":10961422,"pd_ycheck":0,"pd_ordertotal":300,"pd_prodid":100016208,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600003","pd_invoqty":12,"pd_price":25,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18060002","pd_batchcode":"18060900001","pd_taxprice":0,"pd_prodmadedate":"2018-06-09 00:00:00","pd_orderid":51002176,"pd_prodcode":"C030799","pd_description":null,"pd_inqty":12,"pd_pdno":1,"pd_replydate":"2018-06-09 00:00:00","pd_taxamount":0,"pd_piid":50726414,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":25,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18060002","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95463,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-05-10 00:00:00","pi_updatedate":null,"pi_sendcode":null,"pi_receivecode":"LS0016","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"LS0016","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-13 00:00:00","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-06-13 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"上海邕兴贸易有限公司","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":null,"pi_invostatuscode":"ENTERING","pi_receivename":"上海邕兴贸易有限公司","pi_statuscode":"UNPOST","pi_id":50726455,"detail":[],"pi_count":0,"pi_inoutno":"YS18060004","pi_departmentcode":null},{"pi_date1":"2018-06-13 14:37:06","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95723,"pi_auditdate":"2018-06-13 14:37:03","pi_currency":"RMB","pi_paydate":"2018-06-13 00:00:00","pi_updatedate":null,"pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":"仓库名称","pi_inoutman":"管理员","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-13 00:00:00","pi_auditman":"管理员","pi_monthenddate":"2018-06-13 00:00:00","pi_recorddate":"2018-06-13 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"咖啡之星","pi_rate":1,"pi_whcode":"B","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":null,"pi_invostatuscode":"AUDITED","pi_receivename":"咖啡之星","pi_statuscode":"POSTED","pi_id":50726456,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":1,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947228,"pd_total":1,"pd_taxtotal":0,"pd_whcode":"B","pd_barcodeinqty":0,"pd_purcinqty":1,"pd_piclass":"采购验收单","pd_batchid":10961442,"pd_ycheck":0,"pd_ordertotal":1,"pd_prodid":100016208,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600005","pd_invoqty":1,"pd_price":1,"pd_prjname":null,"pd_location":null,"pd_whname":"仓库名称","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18060005","pd_batchcode":"18061300004","pd_taxprice":0,"pd_prodmadedate":"2018-06-13 00:00:00","pd_orderid":51002216,"pd_prodcode":"C030799","pd_description":null,"pd_inqty":1,"pd_pdno":1,"pd_replydate":"2018-06-13 00:00:00","pd_taxamount":0,"pd_piid":50726456,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":1,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060005","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95723,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-06-13 00:00:00","pi_updatedate":null,"pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-13 00:00:00","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-06-13 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"咖啡之星","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":null,"pi_invostatuscode":"ENTERING","pi_receivename":"咖啡之星","pi_statuscode":"UNPOST","pi_id":50726457,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":0,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947229,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"B","pd_barcodeinqty":0,"pd_purcinqty":1,"pd_piclass":"采购验收单","pd_batchid":0,"pd_ycheck":0,"pd_ordertotal":1,"pd_prodid":null,"pd_customprice":0,"pd_outqty":0,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600005","pd_invoqty":0,"pd_price":0,"pd_prjname":null,"pd_location":null,"pd_whname":"仓库名称","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18060006","pd_batchcode":"18061300006","pd_taxprice":0,"pd_prodmadedate":"2018-06-13 00:00:00","pd_orderid":51002216,"pd_prodcode":"C030799","pd_description":null,"pd_inqty":1,"pd_pdno":1,"pd_replydate":"2018-06-13 00:00:00","pd_taxamount":0,"pd_piid":50726457,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":1,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060006","pi_departmentcode":null},{"pi_date1":"2018-06-13 14:43:51","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95383,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-06-13 00:00:00","pi_updatedate":null,"pi_sendcode":null,"pi_receivecode":"GN0053","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已提交","pi_cardcode":"GN0053","pi_type":null,"pi_cgycode":null,"pi_whname":"仓库名称","pi_inoutman":"管理员","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-13 00:00:00","pi_auditman":null,"pi_monthenddate":"2018-06-13 00:00:00","pi_recorddate":"2018-06-13 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"北京盛世丰源食品有限责任公司（一帆-辉腾日化-华杰致信）","pi_rate":1,"pi_whcode":"B","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":null,"pi_invostatuscode":"COMMITED","pi_receivename":"北京盛世丰源食品有限责任公司（一帆-辉腾日化-华杰致信）","pi_statuscode":"POSTED","pi_id":50726458,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":11,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947230,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"B","pd_barcodeinqty":0,"pd_purcinqty":11,"pd_piclass":"采购验收单","pd_batchid":10961443,"pd_ycheck":0,"pd_ordertotal":0,"pd_prodid":100015978,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600006","pd_invoqty":11,"pd_price":0,"pd_prjname":null,"pd_location":null,"pd_whname":"仓库名称","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18060007","pd_batchcode":"18061300007","pd_taxprice":0,"pd_prodmadedate":"2018-06-13 00:00:00","pd_orderid":51002217,"pd_prodcode":"B020529","pd_description":null,"pd_inqty":11,"pd_pdno":1,"pd_replydate":"2021-05-28 00:00:00","pd_taxamount":0,"pd_piid":50726458,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060007","pi_departmentcode":null},{"pi_date1":"2018-06-13 14:46:09","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95383,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-06-13 00:00:00","pi_updatedate":null,"pi_sendcode":null,"pi_receivecode":"GN0053","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已提交","pi_cardcode":"GN0053","pi_type":null,"pi_cgycode":null,"pi_whname":"仓库名称","pi_inoutman":"管理员","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-13 00:00:00","pi_auditman":null,"pi_monthenddate":"2018-06-13 00:00:00","pi_recorddate":"2018-06-13 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"北京盛世丰源食品有限责任公司（一帆-辉腾日化-华杰致信）","pi_rate":1,"pi_whcode":"B","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":null,"pi_invostatuscode":"COMMITED","pi_receivename":"北京盛世丰源食品有限责任公司（一帆-辉腾日化-华杰致信）","pi_statuscode":"POSTED","pi_id":50726459,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":11,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947231,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"B","pd_barcodeinqty":0,"pd_purcinqty":11,"pd_piclass":"采购验收单","pd_batchid":10961444,"pd_ycheck":0,"pd_ordertotal":0,"pd_prodid":100015978,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600006","pd_invoqty":11,"pd_price":0,"pd_prjname":null,"pd_location":null,"pd_whname":"仓库名称","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18060008","pd_batchcode":"18061300009","pd_taxprice":0,"pd_prodmadedate":"2018-06-13 00:00:00","pd_orderid":51002217,"pd_prodcode":"B020529","pd_description":null,"pd_inqty":11,"pd_pdno":1,"pd_replydate":"2021-05-28 00:00:00","pd_taxamount":0,"pd_piid":50726459,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060008","pi_departmentcode":null},{"pi_date1":"2018-06-13 15:18:54","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95383,"pi_auditdate":"2018-06-13 15:18:49","pi_currency":"RMB","pi_paydate":"2018-06-13 00:00:00","pi_updatedate":"2018-06-13 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0053","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"GN0053","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"管理员","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-13 00:00:00","pi_auditman":"管理员","pi_monthenddate":"2018-06-13 00:00:00","pi_recorddate":"2018-06-13 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"北京盛世丰源食品有限责任公司（一帆-辉腾日化-华杰致信）","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"AUDITED","pi_receivename":"北京盛世丰源食品有限责任公司（一帆-辉腾日化-华杰致信）","pi_statuscode":"POSTED","pi_id":50726460,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":12,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947232,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":12,"pd_piclass":"采购验收单","pd_batchid":10961445,"pd_ycheck":0,"pd_ordertotal":0,"pd_prodid":100015978,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600006","pd_invoqty":12,"pd_price":0,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18060009","pd_batchcode":"18061300011","pd_taxprice":0,"pd_prodmadedate":"2018-06-13 00:00:00","pd_orderid":51002217,"pd_prodcode":"B020529","pd_description":null,"pd_inqty":12,"pd_pdno":1,"pd_replydate":"2021-05-28 00:00:00","pd_taxamount":0,"pd_piid":50726460,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060009","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":null,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-06-13 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0146","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"GN0146","pi_type":null,"pi_cgycode":null,"pi_whname":"仓库名称","pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-13 00:00:00","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-06-13 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"北京本味商贸有限公司","pi_rate":1,"pi_whcode":"B","pi_status":"未过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"ENTERING","pi_receivename":"北京本味商贸有限公司","pi_statuscode":"UNPOST","pi_id":50726461,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":0,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947233,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"B","pd_barcodeinqty":0,"pd_purcinqty":1,"pd_piclass":"采购验收单","pd_batchid":0,"pd_ycheck":0,"pd_ordertotal":null,"pd_prodid":null,"pd_customprice":0,"pd_outqty":0,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":null,"pd_invoqty":0,"pd_price":0,"pd_prjname":null,"pd_location":null,"pd_whname":"仓库名称","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":null,"pd_inoutno":"YS18060010","pd_batchcode":"18061300013","pd_taxprice":0,"pd_prodmadedate":"2018-06-13 00:00:00","pd_orderid":0,"pd_prodcode":"B020529","pd_description":null,"pd_inqty":1,"pd_pdno":1,"pd_replydate":"2021-05-28 00:00:00","pd_taxamount":0,"pd_piid":50726461,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":null,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060010","pi_departmentcode":null},{"pi_date1":"2018-06-14 15:57:24","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95383,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-06-14 00:00:00","pi_updatedate":"2018-06-14 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0053","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已提交","pi_cardcode":"GN0053","pi_type":null,"pi_cgycode":null,"pi_whname":"仓库名称","pi_inoutman":"管理员","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-14 00:00:00","pi_auditman":null,"pi_monthenddate":"2018-06-14 00:00:00","pi_recorddate":"2018-06-14 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"北京盛世丰源食品有限责任公司（一帆-辉腾日化-华杰致信）","pi_rate":1,"pi_whcode":"B","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"COMMITED","pi_receivename":"北京盛世丰源食品有限责任公司（一帆-辉腾日化-华杰致信）","pi_statuscode":"POSTED","pi_id":50726478,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":77,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947255,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"B","pd_barcodeinqty":0,"pd_purcinqty":77,"pd_piclass":"采购验收单","pd_batchid":10961462,"pd_ycheck":0,"pd_ordertotal":0,"pd_prodid":100015978,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":"2018-06-14 00:00:00","pd_ordercode":"MP180600006","pd_invoqty":77,"pd_price":0,"pd_prjname":null,"pd_location":null,"pd_whname":"仓库名称","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2025-10-14 00:00:00","pd_orderdetno":1,"pd_inoutno":"YS18060011","pd_batchcode":"18061400003","pd_taxprice":0,"pd_prodmadedate":"2018-06-14 00:00:00","pd_orderid":51002217,"pd_prodcode":"B020529","pd_description":null,"pd_inqty":77,"pd_pdno":2,"pd_replydate":"2021-05-29 00:00:00","pd_taxamount":0,"pd_piid":50726478,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060011","pi_departmentcode":null},{"pi_date1":"2018-06-14 16:00:46","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95463,"pi_auditdate":"2018-06-14 16:00:43","pi_currency":"RMB","pi_paydate":"2018-06-14 00:00:00","pi_updatedate":null,"pi_sendcode":null,"pi_receivecode":"LS0016","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"LS0016","pi_type":null,"pi_cgycode":null,"pi_whname":"仓库名称","pi_inoutman":"管理员","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-14 00:00:00","pi_auditman":"管理员","pi_monthenddate":"2018-06-14 00:00:00","pi_recorddate":"2018-06-14 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"上海邕兴贸易有限公司","pi_rate":1,"pi_whcode":"B","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":null,"pi_invostatuscode":"AUDITED","pi_receivename":"上海邕兴贸易有限公司","pi_statuscode":"POSTED","pi_id":50726479,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":11,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947256,"pd_total":104.31,"pd_taxtotal":16.69,"pd_whcode":"B","pd_barcodeinqty":0,"pd_purcinqty":11,"pd_piclass":"采购验收单","pd_batchid":10961463,"pd_ycheck":0,"pd_ordertotal":121,"pd_prodid":100016096,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":"2018-06-27 00:00:00","pd_ordercode":"MP180600007","pd_invoqty":11,"pd_price":9.482759,"pd_prjname":null,"pd_location":null,"pd_whname":"仓库名称","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2018-06-14 00:00:00","pd_orderdetno":1,"pd_inoutno":"YS18060012","pd_batchcode":"18061400005","pd_taxprice":0,"pd_prodmadedate":"2018-06-27 00:00:00","pd_orderid":51002236,"pd_prodcode":"C030786","pd_description":null,"pd_inqty":11,"pd_pdno":1,"pd_replydate":"2019-06-22 00:00:00","pd_taxamount":0,"pd_piid":50726479,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":11,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060012","pi_departmentcode":null},{"pi_date1":"2018-06-14 16:03:15","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95463,"pi_auditdate":"2018-06-14 16:03:13","pi_currency":"RMB","pi_paydate":"2018-06-14 00:00:00","pi_updatedate":null,"pi_sendcode":null,"pi_receivecode":"LS0016","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"LS0016","pi_type":null,"pi_cgycode":null,"pi_whname":"仓库名称","pi_inoutman":"管理员","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-14 00:00:00","pi_auditman":"管理员","pi_monthenddate":"2018-06-14 00:00:00","pi_recorddate":"2018-06-14 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"上海邕兴贸易有限公司","pi_rate":1,"pi_whcode":"B","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":null,"pi_invostatuscode":"AUDITED","pi_receivename":"上海邕兴贸易有限公司","pi_statuscode":"POSTED","pi_id":50726480,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":14,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947257,"pd_total":132.76,"pd_taxtotal":21.24,"pd_whcode":"B","pd_barcodeinqty":0,"pd_purcinqty":14,"pd_piclass":"采购验收单","pd_batchid":10961464,"pd_ycheck":0,"pd_ordertotal":154,"pd_prodid":100016096,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":"2018-06-14 00:00:00","pd_ordercode":"MP180600007","pd_invoqty":14,"pd_price":9.482759,"pd_prjname":null,"pd_location":null,"pd_whname":"仓库名称","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2018-06-14 00:00:00","pd_orderdetno":1,"pd_inoutno":"YS18060013","pd_batchcode":"18061400007","pd_taxprice":0,"pd_prodmadedate":"2018-06-14 00:00:00","pd_orderid":51002236,"pd_prodcode":"C030786","pd_description":null,"pd_inqty":14,"pd_pdno":1,"pd_replydate":"2019-06-09 00:00:00","pd_taxamount":0,"pd_piid":50726480,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":11,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060013","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95463,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-06-14 00:00:00","pi_updatedate":null,"pi_sendcode":null,"pi_receivecode":"LS0016","pi_invocode":null,"pi_billstatus":"未开票","pi_invostatus":"已提交","pi_cardcode":"LS0016","pi_type":null,"pi_cgycode":null,"pi_whname":"仓库名称","pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-14 00:00:00","pi_auditman":null,"pi_monthenddate":"2018-06-14 00:00:00","pi_recorddate":"2018-06-14 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"上海邕兴贸易有限公司","pi_rate":1,"pi_whcode":"B","pi_status":"未过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":null,"pi_invostatuscode":"COMMITED","pi_receivename":"上海邕兴贸易有限公司","pi_statuscode":"UNPOST","pi_id":50726481,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":0,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947258,"pd_total":123.28,"pd_taxtotal":19.72,"pd_whcode":"B","pd_barcodeinqty":0,"pd_purcinqty":13,"pd_piclass":"采购验收单","pd_batchid":0,"pd_ycheck":0,"pd_ordertotal":143,"pd_prodid":100016096,"pd_customprice":0,"pd_outqty":0,"pd_status":0,"pd_prodmadedate2_user":"2018-06-10 00:00:00","pd_ordercode":"MP180600007","pd_invoqty":0,"pd_price":9.482759,"pd_prjname":null,"pd_location":null,"pd_whname":"仓库名称","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2018-06-14 00:00:00","pd_orderdetno":1,"pd_inoutno":"YS18060014","pd_batchcode":"18061400009","pd_taxprice":0,"pd_prodmadedate":"2018-06-10 00:00:00","pd_orderid":51002236,"pd_prodcode":"C030786","pd_description":null,"pd_inqty":13,"pd_pdno":1,"pd_replydate":"2018-06-14 00:00:00","pd_taxamount":0,"pd_piid":50726481,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":11,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060014","pi_departmentcode":null},{"pi_date1":"2018-06-14 16:29:29","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95463,"pi_auditdate":"2018-06-14 16:29:21","pi_currency":"RMB","pi_paydate":"2018-06-14 00:00:00","pi_updatedate":null,"pi_sendcode":null,"pi_receivecode":"LS0016","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"LS0016","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"管理员","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-14 00:00:00","pi_auditman":"管理员","pi_monthenddate":"2018-06-14 00:00:00","pi_recorddate":"2018-06-14 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"上海邕兴贸易有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":null,"pi_invostatuscode":"AUDITED","pi_receivename":"上海邕兴贸易有限公司","pi_statuscode":"POSTED","pi_id":50726482,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":7,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947259,"pd_total":66.38,"pd_taxtotal":10.62,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":7,"pd_piclass":"采购验收单","pd_batchid":10961466,"pd_ycheck":0,"pd_ordertotal":77,"pd_prodid":100016096,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":"2018-06-04 00:00:00","pd_ordercode":"MP180600007","pd_invoqty":7,"pd_price":9.482759,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2018-06-14 00:00:00","pd_orderdetno":1,"pd_inoutno":"YS18060015","pd_batchcode":"18061400013","pd_taxprice":0,"pd_prodmadedate":"2018-06-04 00:00:00","pd_orderid":51002236,"pd_prodcode":"C030786","pd_description":null,"pd_inqty":7,"pd_pdno":1,"pd_replydate":"2018-06-14 00:00:00","pd_taxamount":0,"pd_piid":50726482,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":11,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060015","pi_departmentcode":null},{"pi_date1":"2018-06-22 10:51:15","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95264,"pi_auditdate":"2018-06-22 10:51:09","pi_currency":"RMB","pi_paydate":"2018-06-22 00:00:00","pi_updatedate":"2018-06-22 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0046","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"GN0046","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"管理员","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0038","pi_date":"2018-06-22 10:50:12","pi_auditman":"管理员","pi_monthenddate":"2018-06-22 00:00:00","pi_recorddate":"2018-06-22 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"北京欣康宁工贸有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":"康成真","pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"AUDITED","pi_receivename":"北京欣康宁工贸有限公司","pi_statuscode":"POSTED","pi_id":50726550,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":10,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947324,"pd_total":86.21,"pd_taxtotal":13.79,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":10,"pd_piclass":"采购验收单","pd_batchid":10961505,"pd_ycheck":0,"pd_ordertotal":100,"pd_prodid":100016383,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":"2018-05-01 00:00:00","pd_ordercode":"MP180600009","pd_invoqty":10,"pd_price":8.62069,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2018-06-22 00:00:00","pd_orderdetno":1,"pd_inoutno":"YS18060025","pd_batchcode":"18062200022","pd_taxprice":0,"pd_prodmadedate":"2018-05-01 00:00:00","pd_orderid":51002297,"pd_prodcode":"F031222","pd_description":null,"pd_inqty":10,"pd_pdno":1,"pd_replydate":"2018-06-22 00:00:00","pd_taxamount":0,"pd_piid":50726550,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":10,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18060025","pi_departmentcode":null},{"pi_date1":"2018-06-22 11:14:49","pi_remark":"系统同步产生","pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":67200,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-06-22 00:00:00","pi_updatedate":"2018-06-22 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0046","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"GN0046","pi_type":null,"pi_cgycode":null,"pi_whname":"百佳汇成都仓","pi_inoutman":"TEST","pi_sourcecode":"BJH-SS18060005","pi_cgy":null,"pi_recordman":"TEST","pi_printman":null,"pi_payment":"现金","pi_departmentname":"广州分公司","pi_emcode":null,"pi_date":"2018-06-22 00:00:00","pi_auditman":null,"pi_monthenddate":"2018-06-30 00:00:00","pi_recorddate":"2018-06-22 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"北京欣康宁工贸有限公司","pi_rate":1,"pi_whcode":"BJH-E","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":"TEST","pi_invostatuscode":"ENTERING","pi_receivename":"北京欣康宁工贸有限公司","pi_statuscode":"POSTED","pi_id":50726551,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":1,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":null,"pd_remark2":null,"pd_id":46947325,"pd_total":16.42,"pd_taxtotal":0.14,"pd_whcode":"BJH-E","pd_barcodeinqty":null,"pd_purcinqty":1,"pd_piclass":"采购验收单","pd_batchid":10961506,"pd_ycheck":0,"pd_ordertotal":1,"pd_prodid":100012603,"pd_customprice":1,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"BJH-SS18060005","pd_invoqty":1,"pd_price":16.42,"pd_prjname":null,"pd_location":null,"pd_whname":"百佳汇成都仓","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"BJH-SD18060002","pd_batchcode":"PA18060004-1","pd_taxprice":0,"pd_prodmadedate":"2018-03-01 00:00:00","pd_orderid":0,"pd_prodcode":"F030703","pd_description":null,"pd_inqty":1,"pd_pdno":1,"pd_replydate":"2023-02-03 00:00:00","pd_taxamount":1,"pd_piid":50726551,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":1,"pd_seller":null}],"pi_count":null,"pi_inoutno":"BJH-SD18060002","pi_departmentcode":"XKN-GZ-001"},{"pi_date1":"2018-06-22 11:33:25","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":null,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-06-22 00:00:00","pi_updatedate":null,"pi_sendcode":null,"pi_receivecode":"GN0146","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已提交","pi_cardcode":"GN0146","pi_type":null,"pi_cgycode":null,"pi_whname":"百佳汇成都仓","pi_inoutman":"管理员","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":"管理员","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-22 00:00:00","pi_auditman":null,"pi_monthenddate":"2018-06-22 00:00:00","pi_recorddate":"2018-06-22 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"北京本味商贸有限公司","pi_rate":1,"pi_whcode":"BJH-E","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":null,"pi_invostatuscode":"COMMITED","pi_receivename":"北京本味商贸有限公司","pi_statuscode":"POSTED","pi_id":50726553,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":1,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947327,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"BJH-E","pd_barcodeinqty":0,"pd_purcinqty":1,"pd_piclass":"采购验收单","pd_batchid":10961507,"pd_ycheck":0,"pd_ordertotal":0,"pd_prodid":100016096,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":"2018-06-09 00:00:00","pd_ordercode":null,"pd_invoqty":1,"pd_price":0,"pd_prjname":null,"pd_location":null,"pd_whname":"百佳汇成都仓","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":null,"pd_department":null,"pd_effective_date_user":"2019-06-04 00:00:00","pd_orderdetno":null,"pd_inoutno":"YS18060026","pd_batchcode":"18062200024","pd_taxprice":0,"pd_prodmadedate":"2018-06-09 00:00:00","pd_orderid":0,"pd_prodcode":"C030786","pd_description":null,"pd_inqty":1,"pd_pdno":1,"pd_replydate":"2019-06-04 00:00:00","pd_taxamount":0,"pd_piid":50726553,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060026","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":null,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-06-22 00:00:00","pi_updatedate":"2018-06-22 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0146","pi_invocode":null,"pi_billstatus":"未开票","pi_invostatus":"已提交","pi_cardcode":"GN0146","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST","pi_printman":"TEST","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-06-22 00:00:00","pi_auditman":null,"pi_monthenddate":"2018-06-22 00:00:00","pi_recorddate":"2018-06-22 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"北京本味商贸有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"未过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":"TEST","pi_invostatuscode":"COMMITED","pi_receivename":"北京本味商贸有限公司","pi_statuscode":"UNPOST","pi_id":50726554,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":0,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947328,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":10,"pd_piclass":"采购验收单","pd_batchid":0,"pd_ycheck":0,"pd_ordertotal":0,"pd_prodid":100016096,"pd_customprice":0,"pd_outqty":0,"pd_status":0,"pd_prodmadedate2_user":"2017-08-23 00:00:00","pd_ordercode":null,"pd_invoqty":0,"pd_price":0,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2018-08-18 00:00:00","pd_orderdetno":null,"pd_inoutno":"YS18060027","pd_batchcode":"18062200026","pd_taxprice":0,"pd_prodmadedate":"2017-08-23 00:00:00","pd_orderid":0,"pd_prodcode":"C030786","pd_description":null,"pd_inqty":10,"pd_pdno":1,"pd_replydate":"2018-08-18 00:00:00","pd_taxamount":0,"pd_piid":50726554,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18060027","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95264,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-06-22 16:19:03","pi_sendcode":null,"pi_receivecode":"GN0046","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"GN0046","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0038","pi_date":"2018-06-22 16:19:03","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-06-22 16:19:03","pi_transport":null,"pi_cop":null,"pi_title":"北京欣康宁工贸有限公司","pi_rate":1,"pi_whcode":"BJH-E","pi_status":"未过账","pi_emname":"康成真","pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"ENTERING","pi_receivename":"北京欣康宁工贸有限公司","pi_statuscode":"UNPOST","pi_id":50726574,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947346,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"BJH-E","pd_barcodeinqty":null,"pd_purcinqty":12,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":2.4,"pd_prodid":100016455,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600010","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":null,"pd_taxrate":0,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18060028","pd_batchcode":"18062200045","pd_taxprice":null,"pd_prodmadedate":"2018-06-22 16:19:03","pd_orderid":51002336,"pd_prodcode":"F031569","pd_description":null,"pd_inqty":12,"pd_pdno":1,"pd_replydate":"2023-05-27 16:19:03","pd_taxamount":null,"pd_piid":50726574,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0.2,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18060028","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95463,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-06-25 13:57:33","pi_sendcode":null,"pi_receivecode":"LS0016","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"LS0016","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-06-25 13:57:33","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-06-25 13:57:33","pi_transport":null,"pi_cop":null,"pi_title":"上海邕兴贸易有限公司","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"ENTERING","pi_receivename":"上海邕兴贸易有限公司","pi_statuscode":"UNPOST","pi_id":50726603,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":null,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947375,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"BJH-E","pd_barcodeinqty":null,"pd_purcinqty":10,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":0,"pd_prodid":100016455,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600012","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"百佳汇成都仓","pd_taxrate":0,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18060039","pd_batchcode":null,"pd_taxprice":null,"pd_prodmadedate":null,"pd_orderid":51002376,"pd_prodcode":"F031569","pd_description":null,"pd_inqty":10,"pd_pdno":1,"pd_replydate":null,"pd_taxamount":null,"pd_piid":50726603,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18060039","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95723,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-07-02 00:00:00","pi_updatedate":"2018-07-09 00:00:00","pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-07-02 10:32:01","pi_auditman":null,"pi_monthenddate":"2018-07-02 00:00:00","pi_recorddate":"2018-07-02 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"咖啡之星","pi_rate":1,"pi_whcode":"A","pi_status":"未过账","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"TEST","pi_invostatuscode":"ENTERING","pi_receivename":"咖啡之星","pi_statuscode":"UNPOST","pi_id":50726634,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":0,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947406,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":10,"pd_piclass":"采购验收单","pd_batchid":0,"pd_ycheck":0,"pd_ordertotal":1,"pd_prodid":100016455,"pd_customprice":0,"pd_outqty":0,"pd_status":0,"pd_prodmadedate2_user":"2018-07-03 00:00:00","pd_ordercode":"MP180600011","pd_invoqty":0,"pd_price":0,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2023-06-07 00:00:00","pd_orderdetno":1,"pd_inoutno":"YS18070001","pd_batchcode":"18070300001","pd_taxprice":0,"pd_prodmadedate":"2018-07-03 00:00:00","pd_orderid":51002356,"pd_prodcode":"F031569","pd_description":null,"pd_inqty":10,"pd_pdno":1,"pd_replydate":"2023-06-07 00:00:00","pd_taxamount":0,"pd_piid":50726634,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":0.1,"pd_seller":null},{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":0,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947486,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":10,"pd_piclass":"采购验收单","pd_batchid":0,"pd_ycheck":0,"pd_ordertotal":1,"pd_prodid":null,"pd_customprice":0,"pd_outqty":0,"pd_status":0,"pd_prodmadedate2_user":"2018-07-03 00:00:00","pd_ordercode":"MP180600011","pd_invoqty":0,"pd_price":0,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2023-06-07 00:00:00","pd_orderdetno":1,"pd_inoutno":"YS18070001","pd_batchcode":"18070900001","pd_taxprice":0,"pd_prodmadedate":"2018-07-03 00:00:00","pd_orderid":51002356,"pd_prodcode":"F031569","pd_description":null,"pd_inqty":10,"pd_pdno":2,"pd_replydate":"2023-06-07 00:00:00","pd_taxamount":0,"pd_piid":50726634,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":0.1,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18070001","pi_departmentcode":null},{"pi_date1":"2018-07-06 14:46:53","pi_remark":"系统同步产生","pi_class":"采购验收单","pi_paymentcode":"1008","pi_cardid":67200,"pi_auditdate":"2018-07-06 14:46:40","pi_currency":"RMB","pi_paydate":"2018-07-20 00:00:00","pi_updatedate":"2018-07-06 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0046","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"GN0046","pi_type":null,"pi_cgycode":null,"pi_whname":"百佳汇成都仓","pi_inoutman":"TEST3","pi_sourcecode":"BJH-SS18070002","pi_cgy":null,"pi_recordman":"TEST3","pi_printman":null,"pi_payment":"月结15天","pi_departmentname":"成都分公司","pi_emcode":null,"pi_date":"2018-07-06 00:00:00","pi_auditman":"TEST3","pi_monthenddate":"2018-07-31 00:00:00","pi_recorddate":"2018-07-06 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"北京欣康宁工贸有限公司","pi_rate":1,"pi_whcode":"BJH-E","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":"TEST3","pi_invostatuscode":"AUDITED","pi_receivename":"北京欣康宁工贸有限公司","pi_statuscode":"POSTED","pi_id":50726662,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":1,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":null,"pd_remark2":null,"pd_id":46947454,"pd_total":16.42,"pd_taxtotal":5.93,"pd_whcode":"BJH-E","pd_barcodeinqty":null,"pd_purcinqty":1,"pd_piclass":"采购验收单","pd_batchid":10961522,"pd_ycheck":0,"pd_ordertotal":43,"pd_prodid":100012603,"pd_customprice":43,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":"2018-03-01 00:00:00","pd_ordercode":"BJH-SS18070002","pd_invoqty":1,"pd_price":16.42,"pd_prjname":null,"pd_location":null,"pd_whname":"百佳汇成都仓","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2023-02-03 00:00:00","pd_orderdetno":1,"pd_inoutno":"BJH-SD18070001","pd_batchcode":"18070600001","pd_taxprice":0,"pd_prodmadedate":"2018-03-01 00:00:00","pd_orderid":null,"pd_prodcode":"F030703","pd_description":null,"pd_inqty":1,"pd_pdno":1,"pd_replydate":"2023-02-03 00:00:00","pd_taxamount":43,"pd_piid":50726662,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":43,"pd_seller":null}],"pi_count":null,"pi_inoutno":"BJH-SD18070001","pi_departmentcode":"XKN-CD-001"},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95671,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-07-17 11:30:56","pi_sendcode":null,"pi_receivecode":"GN0135","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"GN0135","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"陈祝","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0038","pi_date":"2018-07-17 11:30:56","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-07-17 11:30:56","pi_transport":null,"pi_cop":null,"pi_title":"和路雪（中国）有限公司天津分公司（立顿零售）","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":"康成真","pi_printstatus":"未打印","pi_updateman":"陈祝","pi_invostatuscode":"ENTERING","pi_receivename":"和路雪（中国）有限公司天津分公司（立顿零售）","pi_statuscode":"UNPOST","pi_id":50726734,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":null,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947546,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"BJH-E","pd_barcodeinqty":null,"pd_purcinqty":12,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":0,"pd_prodid":100010780,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180700007","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"百佳汇成都仓","pd_taxrate":0,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18070002","pd_batchcode":null,"pd_taxprice":null,"pd_prodmadedate":null,"pd_orderid":51002476,"pd_prodcode":"01011001","pd_description":null,"pd_inqty":12,"pd_pdno":1,"pd_replydate":null,"pd_taxamount":null,"pd_piid":50726734,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18070002","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95723,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-07-17 13:24:32","pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"陈祝","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-07-17 13:24:32","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-07-17 13:24:32","pi_transport":null,"pi_cop":null,"pi_title":"咖啡之星","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"陈祝","pi_invostatuscode":"ENTERING","pi_receivename":"咖啡之星","pi_statuscode":"UNPOST","pi_id":50726735,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":null,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947547,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":20,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":80,"pd_prodid":100010781,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180700010","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18070003","pd_batchcode":null,"pd_taxprice":null,"pd_prodmadedate":null,"pd_orderid":51002479,"pd_prodcode":"01011002","pd_description":null,"pd_inqty":20,"pd_pdno":1,"pd_replydate":null,"pd_taxamount":null,"pd_piid":50726735,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":4,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18070003","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95264,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-07-17 15:15:55","pi_sendcode":null,"pi_receivecode":"GN0046","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"GN0046","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"卢宁","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0038","pi_date":"2018-07-17 15:15:55","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-07-17 15:15:55","pi_transport":null,"pi_cop":null,"pi_title":"北京欣康宁工贸有限公司","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":"康成真","pi_printstatus":"未打印","pi_updateman":"卢宁","pi_invostatuscode":"ENTERING","pi_receivename":"北京欣康宁工贸有限公司","pi_statuscode":"UNPOST","pi_id":50726736,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":null,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947548,"pd_total":0,"pd_taxtotal":3.31,"pd_whcode":null,"pd_barcodeinqty":null,"pd_purcinqty":12,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":24,"pd_prodid":100015996,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600008","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":null,"pd_taxrate":16,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18070004","pd_batchcode":null,"pd_taxprice":null,"pd_prodmadedate":null,"pd_orderid":51002278,"pd_prodcode":"F031192","pd_description":null,"pd_inqty":12,"pd_pdno":1,"pd_replydate":null,"pd_taxamount":null,"pd_piid":50726736,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":2,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18070004","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95463,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-07-17 15:16:15","pi_sendcode":null,"pi_receivecode":"LS0016","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"LS0016","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"卢宁","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-07-17 15:16:15","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-07-17 15:16:15","pi_transport":null,"pi_cop":null,"pi_title":"上海邕兴贸易有限公司","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"卢宁","pi_invostatuscode":"ENTERING","pi_receivename":"上海邕兴贸易有限公司","pi_statuscode":"UNPOST","pi_id":50726737,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":null,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947549,"pd_total":0,"pd_taxtotal":100.14,"pd_whcode":null,"pd_barcodeinqty":null,"pd_purcinqty":66,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":726,"pd_prodid":100016096,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600007","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":null,"pd_taxrate":16,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18070005","pd_batchcode":null,"pd_taxprice":null,"pd_prodmadedate":null,"pd_orderid":51002236,"pd_prodcode":"C030786","pd_description":null,"pd_inqty":66,"pd_pdno":1,"pd_replydate":null,"pd_taxamount":null,"pd_piid":50726737,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":11,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18070005","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95723,"pi_auditdate":"2018-08-30 10:23:41","pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-08-30 00:00:00","pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":"存手机","pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"卢宁","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-07-17 15:17:15","pi_auditman":"管理员","pi_monthenddate":null,"pi_recorddate":"2018-07-17 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"咖啡之星","pi_rate":1,"pi_whcode":"csa12321","pi_status":"未过账","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"AUDITED","pi_receivename":"咖啡之星","pi_statuscode":"UNPOST","pi_id":50726738,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":0,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947550,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"csa12321","pd_barcodeinqty":0,"pd_purcinqty":9,"pd_piclass":"采购验收单","pd_batchid":0,"pd_ycheck":0,"pd_ordertotal":9,"pd_prodid":100016208,"pd_customprice":0,"pd_outqty":0,"pd_status":0,"pd_prodmadedate2_user":"2018-08-14 00:00:00","pd_ordercode":"MP180600005","pd_invoqty":0,"pd_price":0,"pd_prjname":null,"pd_location":null,"pd_whname":"存手机","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":"2018-08-30 00:00:00","pd_orderdetno":1,"pd_inoutno":"YS18070006","pd_batchcode":"18083000004","pd_taxprice":0,"pd_prodmadedate":"2018-08-14 00:00:00","pd_orderid":51002216,"pd_prodcode":"C030799","pd_description":null,"pd_inqty":9,"pd_pdno":1,"pd_replydate":"2018-08-30 00:00:00","pd_taxamount":0,"pd_piid":50726738,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":1,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18070006","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95264,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-07-18 09:26:31","pi_sendcode":null,"pi_receivecode":"GN0046","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"在录入","pi_cardcode":"GN0046","pi_type":null,"pi_cgycode":null,"pi_whname":null,"pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"卢宁","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0038","pi_date":"2018-07-18 09:26:31","pi_auditman":null,"pi_monthenddate":null,"pi_recorddate":"2018-07-18 09:26:31","pi_transport":null,"pi_cop":null,"pi_title":"北京欣康宁工贸有限公司","pi_rate":1,"pi_whcode":null,"pi_status":"未过账","pi_emname":"康成真","pi_printstatus":"未打印","pi_updateman":"卢宁","pi_invostatuscode":"ENTERING","pi_receivename":"北京欣康宁工贸有限公司","pi_statuscode":"UNPOST","pi_id":50726754,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":null,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947566,"pd_total":0,"pd_taxtotal":16.69,"pd_whcode":null,"pd_barcodeinqty":null,"pd_purcinqty":11,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":121,"pd_prodid":100015989,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180600002","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":null,"pd_taxrate":16,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18070007","pd_batchcode":null,"pd_taxprice":null,"pd_prodmadedate":null,"pd_orderid":51002156,"pd_prodcode":"B010064","pd_description":null,"pd_inqty":11,"pd_pdno":1,"pd_replydate":null,"pd_taxamount":null,"pd_piid":50726754,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":11,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18070007","pi_departmentcode":null},{"pi_date1":"2018-08-08 15:17:29","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":null,"pi_auditdate":null,"pi_currency":"RMB","pi_paydate":"2018-08-08 00:00:00","pi_updatedate":"2018-08-08 00:00:00","pi_sendcode":null,"pi_receivecode":"GN0146","pi_invocode":null,"pi_billstatus":"未开票","pi_invostatus":"在录入","pi_cardcode":"GN0146","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"TEST","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"TEST","pi_printman":"TEST","pi_payment":"现金","pi_departmentname":null,"pi_emcode":null,"pi_date":"2018-08-08 00:00:00","pi_auditman":null,"pi_monthenddate":"2018-08-08 00:00:00","pi_recorddate":"2018-08-08 00:00:00","pi_transport":null,"pi_cop":null,"pi_title":"北京本味商贸有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"已过账","pi_emname":null,"pi_printstatus":"未打印","pi_updateman":"TEST","pi_invostatuscode":"ENTERING","pi_receivename":"北京本味商贸有限公司","pi_statuscode":"POSTED","pi_id":50726774,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":100,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947586,"pd_total":1000,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":10961582,"pd_ycheck":0,"pd_ordertotal":1000,"pd_prodid":100017533,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":"2018-08-08 00:00:00","pd_ordercode":null,"pd_invoqty":100,"pd_price":10,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":null,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":null,"pd_inoutno":"YS18080001","pd_batchcode":"18080800001","pd_taxprice":0,"pd_prodmadedate":"2018-08-08 00:00:00","pd_orderid":0,"pd_prodcode":"A020603","pd_description":null,"pd_inqty":100,"pd_pdno":1,"pd_replydate":"2021-08-07 00:00:00","pd_taxamount":0,"pd_piid":50726774,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":10,"pd_seller":null},{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":100,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947587,"pd_total":900,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":10961583,"pd_ycheck":0,"pd_ordertotal":900,"pd_prodid":100017454,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":"2018-08-08 00:00:00","pd_ordercode":null,"pd_invoqty":100,"pd_price":9,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":0,"pd_vendorrate":null,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":null,"pd_inoutno":"YS18080001","pd_batchcode":"18080800002","pd_taxprice":0,"pd_prodmadedate":"2018-08-08 00:00:00","pd_orderid":0,"pd_prodcode":"A010395","pd_description":null,"pd_inqty":100,"pd_pdno":2,"pd_replydate":"2021-08-07 00:00:00","pd_taxamount":0,"pd_piid":50726774,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":9,"pd_seller":null}],"pi_count":0,"pi_inoutno":"YS18080001","pi_departmentcode":null},{"pi_date1":"2018-08-31 10:37:48","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95463,"pi_auditdate":"2018-08-31 09:36:13","pi_currency":"RMB","pi_paydate":"2018-08-30 00:00:00","pi_updatedate":"2018-08-30 16:35:22","pi_sendcode":null,"pi_receivecode":"LS0016","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"LS0016","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"wms","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-08-30 16:35:22","pi_auditman":"管理员","pi_monthenddate":"2018-08-30 00:00:00","pi_recorddate":"2018-08-30 16:35:22","pi_transport":null,"pi_cop":null,"pi_title":"上海邕兴贸易有限公司","pi_rate":1,"pi_whcode":"A","pi_status":"已过帐","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"AUDITED","pi_receivename":"上海邕兴贸易有限公司","pi_statuscode":"POSTED","pi_id":50726835,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":100,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947647,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":10961642,"pd_ycheck":null,"pd_ordertotal":0,"pd_prodid":100018513,"pd_customprice":null,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180800002","pd_invoqty":100,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18080003","pd_batchcode":"18083100001","pd_taxprice":null,"pd_prodmadedate":"2018-01-11 00:00:00","pd_orderid":51002576,"pd_prodcode":"A010413","pd_description":null,"pd_inqty":100,"pd_pdno":1,"pd_replydate":"2018-01-11 00:00:00","pd_taxamount":null,"pd_piid":50726835,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18080003","pi_departmentcode":null},{"pi_date1":null,"pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95723,"pi_auditdate":"2018-08-31 10:49:32","pi_currency":"RMB","pi_paydate":null,"pi_updatedate":"2018-08-31 10:49:15","pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":null,"pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-08-31 10:49:15","pi_auditman":"管理员","pi_monthenddate":null,"pi_recorddate":"2018-08-31 10:49:15","pi_transport":null,"pi_cop":null,"pi_title":"咖啡之星","pi_rate":1,"pi_whcode":"A","pi_status":"未过账","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"AUDITED","pi_receivename":"咖啡之星","pi_statuscode":"UNPOST","pi_id":50726855,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":null,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947668,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":200,"pd_piclass":"采购验收单","pd_batchid":null,"pd_ycheck":null,"pd_ordertotal":0,"pd_prodid":100017939,"pd_customprice":null,"pd_outqty":null,"pd_status":0,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180800003","pd_invoqty":null,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":0,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18080004","pd_batchcode":"18083100005","pd_taxprice":null,"pd_prodmadedate":"2018-08-31 10:49:15","pd_orderid":51002596,"pd_prodcode":"A020609","pd_description":null,"pd_inqty":200,"pd_pdno":1,"pd_replydate":"2021-08-30 10:49:15","pd_taxamount":null,"pd_piid":50726855,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18080004","pi_departmentcode":null},{"pi_date1":"2018-08-31 15:02:51","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95723,"pi_auditdate":"2018-08-31 11:00:13","pi_currency":"RMB","pi_paydate":"2018-08-31 00:00:00","pi_updatedate":"2018-08-31 10:57:04","pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"wms","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-08-31 10:57:04","pi_auditman":"管理员","pi_monthenddate":"2018-08-31 00:00:00","pi_recorddate":"2018-08-31 10:57:04","pi_transport":null,"pi_cop":null,"pi_title":"咖啡之星","pi_rate":1,"pi_whcode":"A","pi_status":"已过帐","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"AUDITED","pi_receivename":"咖啡之星","pi_statuscode":"POSTED","pi_id":50726856,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":100,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947680,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":10961643,"pd_ycheck":null,"pd_ordertotal":0,"pd_prodid":100017973,"pd_customprice":null,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180800004","pd_invoqty":100,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":null,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18080005","pd_batchcode":"18083100027","pd_taxprice":null,"pd_prodmadedate":"2018-01-11 00:00:00","pd_orderid":51002597,"pd_prodcode":"F020263","pd_description":null,"pd_inqty":100,"pd_pdno":2,"pd_replydate":"2019-11-11 00:00:00","pd_taxamount":null,"pd_piid":50726856,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null},{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":100,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947681,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":10961644,"pd_ycheck":null,"pd_ordertotal":0,"pd_prodid":100017973,"pd_customprice":null,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180800004","pd_invoqty":100,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":null,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18080005","pd_batchcode":"18083100028","pd_taxprice":null,"pd_prodmadedate":"2018-01-11 00:00:00","pd_orderid":51002597,"pd_prodcode":"F020263","pd_description":null,"pd_inqty":100,"pd_pdno":3,"pd_replydate":"2019-11-11 00:00:00","pd_taxamount":null,"pd_piid":50726856,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18080005","pi_departmentcode":null},{"pi_date1":"2018-08-31 15:08:22","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95723,"pi_auditdate":"2018-08-31 15:06:16","pi_currency":"RMB","pi_paydate":"2018-08-31 00:00:00","pi_updatedate":"2018-08-31 15:06:01","pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"wms","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-08-31 15:06:01","pi_auditman":"管理员","pi_monthenddate":"2018-08-31 00:00:00","pi_recorddate":"2018-08-31 15:06:01","pi_transport":null,"pi_cop":null,"pi_title":"咖啡之星","pi_rate":1,"pi_whcode":"A","pi_status":"已过帐","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"AUDITED","pi_receivename":"咖啡之星","pi_statuscode":"POSTED","pi_id":50726858,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":200,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947683,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":200,"pd_piclass":"采购验收单","pd_batchid":10961645,"pd_ycheck":null,"pd_ordertotal":0,"pd_prodid":100017973,"pd_customprice":null,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180800004","pd_invoqty":200,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":null,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18080006","pd_batchcode":"18083100030","pd_taxprice":null,"pd_prodmadedate":"2018-06-11 00:00:00","pd_orderid":51002597,"pd_prodcode":"F020263","pd_description":null,"pd_inqty":200,"pd_pdno":2,"pd_replydate":"2018-10-10 00:00:00","pd_taxamount":null,"pd_piid":50726858,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null},{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":100,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947684,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":10961646,"pd_ycheck":null,"pd_ordertotal":0,"pd_prodid":100017973,"pd_customprice":null,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180800004","pd_invoqty":100,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":null,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18080006","pd_batchcode":"18083100031","pd_taxprice":null,"pd_prodmadedate":"2018-06-11 00:00:00","pd_orderid":51002597,"pd_prodcode":"F020263","pd_description":null,"pd_inqty":100,"pd_pdno":3,"pd_replydate":"2019-11-11 00:00:00","pd_taxamount":null,"pd_piid":50726858,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null},{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":150,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947685,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":150,"pd_piclass":"采购验收单","pd_batchid":10961647,"pd_ycheck":null,"pd_ordertotal":0,"pd_prodid":100017973,"pd_customprice":null,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180800004","pd_invoqty":150,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":null,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18080006","pd_batchcode":"18083100032","pd_taxprice":null,"pd_prodmadedate":"2018-06-11 00:00:00","pd_orderid":51002597,"pd_prodcode":"F020263","pd_description":null,"pd_inqty":150,"pd_pdno":4,"pd_replydate":"2019-12-12 00:00:00","pd_taxamount":null,"pd_piid":50726858,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18080006","pi_departmentcode":null},{"pi_date1":"2018-08-31 15:41:49","pi_remark":null,"pi_class":"采购验收单","pi_paymentcode":"1001","pi_cardid":95723,"pi_auditdate":"2018-08-31 15:21:35","pi_currency":"RMB","pi_paydate":"2018-08-31 00:00:00","pi_updatedate":"2018-08-31 15:21:28","pi_sendcode":null,"pi_receivecode":"LS0017","pi_invocode":null,"pi_billstatus":null,"pi_invostatus":"已审核","pi_cardcode":"LS0017","pi_type":null,"pi_cgycode":null,"pi_whname":"A","pi_inoutman":"wms","pi_sourcecode":null,"pi_cgy":null,"pi_recordman":"管理员","pi_printman":null,"pi_payment":"现金","pi_departmentname":null,"pi_emcode":"X0040","pi_date":"2018-08-31 15:21:28","pi_auditman":"管理员","pi_monthenddate":"2018-08-31 00:00:00","pi_recorddate":"2018-08-31 15:21:28","pi_transport":null,"pi_cop":null,"pi_title":"咖啡之星","pi_rate":1,"pi_whcode":"A","pi_status":"已过帐","pi_emname":"陈祝","pi_printstatus":"未打印","pi_updateman":"管理员","pi_invostatuscode":"AUDITED","pi_receivename":"咖啡之星","pi_statuscode":"POSTED","pi_id":50726859,"detail":[{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":100,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947687,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":10961648,"pd_ycheck":null,"pd_ordertotal":0,"pd_prodid":100017973,"pd_customprice":null,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180800004","pd_invoqty":100,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":null,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18080007","pd_batchcode":"18083100034","pd_taxprice":null,"pd_prodmadedate":"2018-06-11 00:00:00","pd_orderid":51002597,"pd_prodcode":"F020263","pd_description":null,"pd_inqty":100,"pd_pdno":2,"pd_replydate":"2018-10-10 00:00:00","pd_taxamount":null,"pd_piid":50726859,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null},{"pd_remark":null,"pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":100,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46947688,"pd_total":0,"pd_taxtotal":0,"pd_whcode":"A","pd_barcodeinqty":null,"pd_purcinqty":100,"pd_piclass":"采购验收单","pd_batchid":10961649,"pd_ycheck":null,"pd_ordertotal":0,"pd_prodid":100017973,"pd_customprice":null,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180800004","pd_invoqty":100,"pd_price":null,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":null,"pd_qcid":null,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18080007","pd_batchcode":"18083100035","pd_taxprice":null,"pd_prodmadedate":"2018-06-11 00:00:00","pd_orderid":51002597,"pd_prodcode":"F020263","pd_description":null,"pd_inqty":100,"pd_pdno":3,"pd_replydate":"2019-11-11 00:00:00","pd_taxamount":null,"pd_piid":50726859,"pd_vacode":null,"pd_fee":null,"pd_prjcode":null,"pd_orderprice":0,"pd_seller":null}],"pi_count":null,"pi_inoutno":"YS18080007","pi_departmentcode":null}]
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
         * pi_date1 : 2018-04-25 15:32:07
         * pi_remark : null
         * pi_class : 采购验收单
         * pi_paymentcode : PA001
         * pi_cardid : 95170
         * pi_auditdate : 2018-04-25 15:32:01
         * pi_currency : RMB
         * pi_paydate : 2018-04-25 00:00:00
         * pi_updatedate : 2018-04-25 00:00:00
         * pi_sendcode : null
         * pi_receivecode : 450005
         * pi_invocode : null
         * pi_billstatus : null
         * pi_invostatus : 已审核
         * pi_cardcode : 450005
         * pi_type : null
         * pi_cgycode : null
         * pi_whname : A
         * pi_inoutman : TEST
         * pi_sourcecode : null
         * pi_cgy : null
         * pi_recordman : TEST
         * pi_printman : null
         * pi_payment : 现金
         * pi_departmentname : null
         * pi_emcode : TEST
         * pi_date : 2018-04-25 15:26:28
         * pi_auditman : TEST
         * pi_monthenddate : 2018-04-25 00:00:00
         * pi_recorddate : 2018-04-25 00:00:00
         * pi_transport : null
         * pi_cop :  
         * pi_title : 北京得力文具有限公司1
         * pi_rate : 1
         * pi_whcode : A
         * pi_status : 已过账
         * pi_emname : TEST
         * pi_printstatus : 已打印
         * pi_updateman : TEST
         * pi_invostatuscode : AUDITED
         * pi_receivename : 北京得力文具有限公司1
         * pi_statuscode : POSTED
         * pi_id : 50725940
         * detail : [{"pd_remark":"[ SS18040088:1];","pd_custname":null,"pd_salecode":null,"pd_remark3":null,"pd_showinvoqty":0,"pd_custcode":null,"pd_sellercode":null,"pd_purcoutqty":0,"pd_rebatesprice":0,"pd_remark2":null,"pd_id":46946679,"pd_total":8.62,"pd_taxtotal":1.38,"pd_whcode":"A","pd_barcodeinqty":0,"pd_purcinqty":1,"pd_piclass":"采购验收单","pd_batchid":10961225,"pd_ycheck":0,"pd_ordertotal":10,"pd_prodid":100011354,"pd_customprice":0,"pd_outqty":0,"pd_status":99,"pd_prodmadedate2_user":null,"pd_ordercode":"MP180400045","pd_invoqty":0,"pd_price":8.62069,"pd_prjname":null,"pd_location":null,"pd_whname":"A","pd_taxrate":16,"pd_qcid":0,"pd_vendorrate":0,"pd_department":null,"pd_effective_date_user":null,"pd_orderdetno":1,"pd_inoutno":"YS18040024","pd_batchcode":"18042500011","pd_taxprice":0,"pd_prodmadedate":"2018-04-26 00:00:00","pd_orderid":51001926,"pd_prodcode":"11233","pd_description":null,"pd_inqty":1,"pd_pdno":1,"pd_replydate":"2018-04-26 00:00:00","pd_taxamount":0,"pd_piid":50725940,"pd_vacode":null,"pd_fee":0,"pd_prjcode":null,"pd_orderprice":10,"pd_seller":null}]
         * pi_count : null
         * pi_inoutno : YS18040024
         * pi_departmentcode : null
         */

        @JsonProperty("pi_date1")
        private String piDate1;
        @JsonProperty("pi_remark")
        private Object piRemark;
        @JsonProperty("pi_class")
        private String piClass;
        @JsonProperty("pi_paymentcode")
        private String piPaymentcode;
        @JsonProperty("pi_cardid")
        private int piCardid;
        @JsonProperty("pi_auditdate")
        private String piAuditdate;
        @JsonProperty("pi_currency")
        private String piCurrency;
        @JsonProperty("pi_paydate")
        private String piPaydate;
        @JsonProperty("pi_updatedate")
        private String piUpdatedate;
        @JsonProperty("pi_sendcode")
        private Object piSendcode;
        @JsonProperty("pi_receivecode")
        private String piReceivecode;
        @JsonProperty("pi_invocode")
        private Object piInvocode;
        @JsonProperty("pi_billstatus")
        private Object piBillstatus;
        @JsonProperty("pi_invostatus")
        private String piInvostatus;
        @JsonProperty("pi_cardcode")
        private String piCardcode;
        @JsonProperty("pi_type")
        private Object piType;
        @JsonProperty("pi_cgycode")
        private Object piCgycode;
        @JsonProperty("pi_whname")
        private String piWhname;
        @JsonProperty("pi_inoutman")
        private String piInoutman;
        @JsonProperty("pi_sourcecode")
        private Object piSourcecode;
        @JsonProperty("pi_cgy")
        private Object piCgy;
        @JsonProperty("pi_recordman")
        private String piRecordman;
        @JsonProperty("pi_printman")
        private Object piPrintman;
        @JsonProperty("pi_payment")
        private String piPayment;
        @JsonProperty("pi_departmentname")
        private Object piDepartmentname;
        @JsonProperty("pi_emcode")
        private String piEmcode;
        @JsonProperty("pi_date")
        private String piDate;
        @JsonProperty("pi_auditman")
        private String piAuditman;
        @JsonProperty("pi_monthenddate")
        private String piMonthenddate;
        @JsonProperty("pi_recorddate")
        private String piRecorddate;
        @JsonProperty("pi_transport")
        private Object piTransport;
        @JsonProperty("pi_cop")
        private String piCop;
        @JsonProperty("pi_title")
        private String piTitle;
        @JsonProperty("pi_rate")
        private int piRate;
        @JsonProperty("pi_whcode")
        private String piWhcode;
        @JsonProperty("pi_status")
        private String piStatus;
        @JsonProperty("pi_emname")
        private String piEmname;
        @JsonProperty("pi_printstatus")
        private String piPrintstatus;
        @JsonProperty("pi_updateman")
        private String piUpdateman;
        @JsonProperty("pi_invostatuscode")
        private String piInvostatuscode;
        @JsonProperty("pi_receivename")
        private String piReceivename;
        @JsonProperty("pi_statuscode")
        private String piStatuscode;
        @JsonProperty("pi_id")
        private int piId;
        @JsonProperty("pi_count")
        private Object piCount;
        @JsonProperty("pi_inoutno")
        private String piInoutno;
        @JsonProperty("pi_departmentcode")
        private Object piDepartmentcode;
        @JsonProperty("detail")
        private List<DetailBean> detail;

        public String getPiDate1() {
            return piDate1;
        }

        public void setPiDate1(String piDate1) {
            this.piDate1 = piDate1;
        }

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

        public String getPiPaymentcode() {
            return piPaymentcode;
        }

        public void setPiPaymentcode(String piPaymentcode) {
            this.piPaymentcode = piPaymentcode;
        }

        public int getPiCardid() {
            return piCardid;
        }

        public void setPiCardid(int piCardid) {
            this.piCardid = piCardid;
        }

        public String getPiAuditdate() {
            return piAuditdate;
        }

        public void setPiAuditdate(String piAuditdate) {
            this.piAuditdate = piAuditdate;
        }

        public String getPiCurrency() {
            return piCurrency;
        }

        public void setPiCurrency(String piCurrency) {
            this.piCurrency = piCurrency;
        }

        public String getPiPaydate() {
            return piPaydate;
        }

        public void setPiPaydate(String piPaydate) {
            this.piPaydate = piPaydate;
        }

        public String getPiUpdatedate() {
            return piUpdatedate;
        }

        public void setPiUpdatedate(String piUpdatedate) {
            this.piUpdatedate = piUpdatedate;
        }

        public Object getPiSendcode() {
            return piSendcode;
        }

        public void setPiSendcode(Object piSendcode) {
            this.piSendcode = piSendcode;
        }

        public String getPiReceivecode() {
            return piReceivecode;
        }

        public void setPiReceivecode(String piReceivecode) {
            this.piReceivecode = piReceivecode;
        }

        public Object getPiInvocode() {
            return piInvocode;
        }

        public void setPiInvocode(Object piInvocode) {
            this.piInvocode = piInvocode;
        }

        public Object getPiBillstatus() {
            return piBillstatus;
        }

        public void setPiBillstatus(Object piBillstatus) {
            this.piBillstatus = piBillstatus;
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

        public Object getPiType() {
            return piType;
        }

        public void setPiType(Object piType) {
            this.piType = piType;
        }

        public Object getPiCgycode() {
            return piCgycode;
        }

        public void setPiCgycode(Object piCgycode) {
            this.piCgycode = piCgycode;
        }

        public String getPiWhname() {
            return piWhname;
        }

        public void setPiWhname(String piWhname) {
            this.piWhname = piWhname;
        }

        public String getPiInoutman() {
            return piInoutman;
        }

        public void setPiInoutman(String piInoutman) {
            this.piInoutman = piInoutman;
        }

        public Object getPiSourcecode() {
            return piSourcecode;
        }

        public void setPiSourcecode(Object piSourcecode) {
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

        public Object getPiPrintman() {
            return piPrintman;
        }

        public void setPiPrintman(Object piPrintman) {
            this.piPrintman = piPrintman;
        }

        public String getPiPayment() {
            return piPayment;
        }

        public void setPiPayment(String piPayment) {
            this.piPayment = piPayment;
        }

        public Object getPiDepartmentname() {
            return piDepartmentname;
        }

        public void setPiDepartmentname(Object piDepartmentname) {
            this.piDepartmentname = piDepartmentname;
        }

        public String getPiEmcode() {
            return piEmcode;
        }

        public void setPiEmcode(String piEmcode) {
            this.piEmcode = piEmcode;
        }

        public String getPiDate() {
            return piDate;
        }

        public void setPiDate(String piDate) {
            this.piDate = piDate;
        }

        public String getPiAuditman() {
            return piAuditman;
        }

        public void setPiAuditman(String piAuditman) {
            this.piAuditman = piAuditman;
        }

        public String getPiMonthenddate() {
            return piMonthenddate;
        }

        public void setPiMonthenddate(String piMonthenddate) {
            this.piMonthenddate = piMonthenddate;
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

        public String getPiCop() {
            return piCop;
        }

        public void setPiCop(String piCop) {
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

        public String getPiEmname() {
            return piEmname;
        }

        public void setPiEmname(String piEmname) {
            this.piEmname = piEmname;
        }

        public String getPiPrintstatus() {
            return piPrintstatus;
        }

        public void setPiPrintstatus(String piPrintstatus) {
            this.piPrintstatus = piPrintstatus;
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

        public String getPiReceivename() {
            return piReceivename;
        }

        public void setPiReceivename(String piReceivename) {
            this.piReceivename = piReceivename;
        }

        public String getPiStatuscode() {
            return piStatuscode;
        }

        public void setPiStatuscode(String piStatuscode) {
            this.piStatuscode = piStatuscode;
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

        public Object getPiDepartmentcode() {
            return piDepartmentcode;
        }

        public void setPiDepartmentcode(Object piDepartmentcode) {
            this.piDepartmentcode = piDepartmentcode;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class DetailBean {
            /**
             * pd_remark : [ SS18040088:1];
             * pd_custname : null
             * pd_salecode : null
             * pd_remark3 : null
             * pd_showinvoqty : 0
             * pd_custcode : null
             * pd_sellercode : null
             * pd_purcoutqty : 0
             * pd_rebatesprice : 0
             * pd_remark2 : null
             * pd_id : 46946679
             * pd_total : 8.62
             * pd_taxtotal : 1.38
             * pd_whcode : A
             * pd_barcodeinqty : 0
             * pd_purcinqty : 1
             * pd_piclass : 采购验收单
             * pd_batchid : 10961225
             * pd_ycheck : 0
             * pd_ordertotal : 10
             * pd_prodid : 100011354
             * pd_customprice : 0
             * pd_outqty : 0
             * pd_status : 99
             * pd_prodmadedate2_user : null
             * pd_ordercode : MP180400045
             * pd_invoqty : 0
             * pd_price : 8.62069
             * pd_prjname : null
             * pd_location : null
             * pd_whname : A
             * pd_taxrate : 16
             * pd_qcid : 0
             * pd_vendorrate : 0
             * pd_department : null
             * pd_effective_date_user : null
             * pd_orderdetno : 1
             * pd_inoutno : YS18040024
             * pd_batchcode : 18042500011
             * pd_taxprice : 0
             * pd_prodmadedate : 2018-04-26 00:00:00
             * pd_orderid : 51001926
             * pd_prodcode : 11233
             * pd_description : null
             * pd_inqty : 1
             * pd_pdno : 1
             * pd_replydate : 2018-04-26 00:00:00
             * pd_taxamount : 0
             * pd_piid : 50725940
             * pd_vacode : null
             * pd_fee : 0
             * pd_prjcode : null
             * pd_orderprice : 10
             * pd_seller : null
             */

            @JsonProperty("pd_remark")
            private String pdRemark;
            @JsonProperty("pd_custname")
            private Object pdCustname;
            @JsonProperty("pd_salecode")
            private Object pdSalecode;
            @JsonProperty("pd_remark3")
            private Object pdRemark3;
            @JsonProperty("pd_showinvoqty")
            private int pdShowinvoqty;
            @JsonProperty("pd_custcode")
            private Object pdCustcode;
            @JsonProperty("pd_sellercode")
            private Object pdSellercode;
            @JsonProperty("pd_purcoutqty")
            private int pdPurcoutqty;
            @JsonProperty("pd_rebatesprice")
            private int pdRebatesprice;
            @JsonProperty("pd_remark2")
            private Object pdRemark2;
            @JsonProperty("pd_id")
            private int pdId;
            @JsonProperty("pd_total")
            private double pdTotal;
            @JsonProperty("pd_taxtotal")
            private double pdTaxtotal;
            @JsonProperty("pd_whcode")
            private String pdWhcode;
            @JsonProperty("pd_barcodeinqty")
            private int pdBarcodeinqty;
            @JsonProperty("pd_purcinqty")
            private int pdPurcinqty;
            @JsonProperty("pd_piclass")
            private String pdPiclass;
            @JsonProperty("pd_batchid")
            private int pdBatchid;
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
            @JsonProperty("pd_status")
            private int pdStatus;
            @JsonProperty("pd_prodmadedate2_user")
            private Object pdProdmadedate2User;
            @JsonProperty("pd_ordercode")
            private String pdOrdercode;
            @JsonProperty("pd_invoqty")
            private int pdInvoqty;
            @JsonProperty("pd_price")
            private double pdPrice;
            @JsonProperty("pd_prjname")
            private Object pdPrjname;
            @JsonProperty("pd_location")
            private Object pdLocation;
            @JsonProperty("pd_whname")
            private String pdWhname;
            @JsonProperty("pd_taxrate")
            private int pdTaxrate;
            @JsonProperty("pd_qcid")
            private int pdQcid;
            @JsonProperty("pd_vendorrate")
            private int pdVendorrate;
            @JsonProperty("pd_department")
            private Object pdDepartment;
            @JsonProperty("pd_effective_date_user")
            private Object pdEffectiveDateUser;
            @JsonProperty("pd_orderdetno")
            private int pdOrderdetno;
            @JsonProperty("pd_inoutno")
            private String pdInoutno;
            @JsonProperty("pd_batchcode")
            private String pdBatchcode;
            @JsonProperty("pd_taxprice")
            private int pdTaxprice;
            @JsonProperty("pd_prodmadedate")
            private String pdProdmadedate;
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
            @JsonProperty("pd_replydate")
            private String pdReplydate;
            @JsonProperty("pd_taxamount")
            private int pdTaxamount;
            @JsonProperty("pd_piid")
            private int pdPiid;
            @JsonProperty("pd_vacode")
            private Object pdVacode;
            @JsonProperty("pd_fee")
            private int pdFee;
            @JsonProperty("pd_prjcode")
            private Object pdPrjcode;
            @JsonProperty("pd_orderprice")
            private int pdOrderprice;
            @JsonProperty("pd_seller")
            private Object pdSeller;

            public String getPdRemark() {
                return pdRemark;
            }

            public void setPdRemark(String pdRemark) {
                this.pdRemark = pdRemark;
            }

            public Object getPdCustname() {
                return pdCustname;
            }

            public void setPdCustname(Object pdCustname) {
                this.pdCustname = pdCustname;
            }

            public Object getPdSalecode() {
                return pdSalecode;
            }

            public void setPdSalecode(Object pdSalecode) {
                this.pdSalecode = pdSalecode;
            }

            public Object getPdRemark3() {
                return pdRemark3;
            }

            public void setPdRemark3(Object pdRemark3) {
                this.pdRemark3 = pdRemark3;
            }

            public int getPdShowinvoqty() {
                return pdShowinvoqty;
            }

            public void setPdShowinvoqty(int pdShowinvoqty) {
                this.pdShowinvoqty = pdShowinvoqty;
            }

            public Object getPdCustcode() {
                return pdCustcode;
            }

            public void setPdCustcode(Object pdCustcode) {
                this.pdCustcode = pdCustcode;
            }

            public Object getPdSellercode() {
                return pdSellercode;
            }

            public void setPdSellercode(Object pdSellercode) {
                this.pdSellercode = pdSellercode;
            }

            public int getPdPurcoutqty() {
                return pdPurcoutqty;
            }

            public void setPdPurcoutqty(int pdPurcoutqty) {
                this.pdPurcoutqty = pdPurcoutqty;
            }

            public int getPdRebatesprice() {
                return pdRebatesprice;
            }

            public void setPdRebatesprice(int pdRebatesprice) {
                this.pdRebatesprice = pdRebatesprice;
            }

            public Object getPdRemark2() {
                return pdRemark2;
            }

            public void setPdRemark2(Object pdRemark2) {
                this.pdRemark2 = pdRemark2;
            }

            public int getPdId() {
                return pdId;
            }

            public void setPdId(int pdId) {
                this.pdId = pdId;
            }

            public double getPdTotal() {
                return pdTotal;
            }

            public void setPdTotal(double pdTotal) {
                this.pdTotal = pdTotal;
            }

            public double getPdTaxtotal() {
                return pdTaxtotal;
            }

            public void setPdTaxtotal(double pdTaxtotal) {
                this.pdTaxtotal = pdTaxtotal;
            }

            public String getPdWhcode() {
                return pdWhcode;
            }

            public void setPdWhcode(String pdWhcode) {
                this.pdWhcode = pdWhcode;
            }

            public int getPdBarcodeinqty() {
                return pdBarcodeinqty;
            }

            public void setPdBarcodeinqty(int pdBarcodeinqty) {
                this.pdBarcodeinqty = pdBarcodeinqty;
            }

            public int getPdPurcinqty() {
                return pdPurcinqty;
            }

            public void setPdPurcinqty(int pdPurcinqty) {
                this.pdPurcinqty = pdPurcinqty;
            }

            public String getPdPiclass() {
                return pdPiclass;
            }

            public void setPdPiclass(String pdPiclass) {
                this.pdPiclass = pdPiclass;
            }

            public int getPdBatchid() {
                return pdBatchid;
            }

            public void setPdBatchid(int pdBatchid) {
                this.pdBatchid = pdBatchid;
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

            public int getPdStatus() {
                return pdStatus;
            }

            public void setPdStatus(int pdStatus) {
                this.pdStatus = pdStatus;
            }

            public Object getPdProdmadedate2User() {
                return pdProdmadedate2User;
            }

            public void setPdProdmadedate2User(Object pdProdmadedate2User) {
                this.pdProdmadedate2User = pdProdmadedate2User;
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

            public double getPdPrice() {
                return pdPrice;
            }

            public void setPdPrice(double pdPrice) {
                this.pdPrice = pdPrice;
            }

            public Object getPdPrjname() {
                return pdPrjname;
            }

            public void setPdPrjname(Object pdPrjname) {
                this.pdPrjname = pdPrjname;
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

            public int getPdQcid() {
                return pdQcid;
            }

            public void setPdQcid(int pdQcid) {
                this.pdQcid = pdQcid;
            }

            public int getPdVendorrate() {
                return pdVendorrate;
            }

            public void setPdVendorrate(int pdVendorrate) {
                this.pdVendorrate = pdVendorrate;
            }

            public Object getPdDepartment() {
                return pdDepartment;
            }

            public void setPdDepartment(Object pdDepartment) {
                this.pdDepartment = pdDepartment;
            }

            public Object getPdEffectiveDateUser() {
                return pdEffectiveDateUser;
            }

            public void setPdEffectiveDateUser(Object pdEffectiveDateUser) {
                this.pdEffectiveDateUser = pdEffectiveDateUser;
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

            public String getPdBatchcode() {
                return pdBatchcode;
            }

            public void setPdBatchcode(String pdBatchcode) {
                this.pdBatchcode = pdBatchcode;
            }

            public int getPdTaxprice() {
                return pdTaxprice;
            }

            public void setPdTaxprice(int pdTaxprice) {
                this.pdTaxprice = pdTaxprice;
            }

            public String getPdProdmadedate() {
                return pdProdmadedate;
            }

            public void setPdProdmadedate(String pdProdmadedate) {
                this.pdProdmadedate = pdProdmadedate;
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

            public String getPdReplydate() {
                return pdReplydate;
            }

            public void setPdReplydate(String pdReplydate) {
                this.pdReplydate = pdReplydate;
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

            public Object getPdVacode() {
                return pdVacode;
            }

            public void setPdVacode(Object pdVacode) {
                this.pdVacode = pdVacode;
            }

            public int getPdFee() {
                return pdFee;
            }

            public void setPdFee(int pdFee) {
                this.pdFee = pdFee;
            }

            public Object getPdPrjcode() {
                return pdPrjcode;
            }

            public void setPdPrjcode(Object pdPrjcode) {
                this.pdPrjcode = pdPrjcode;
            }

            public int getPdOrderprice() {
                return pdOrderprice;
            }

            public void setPdOrderprice(int pdOrderprice) {
                this.pdOrderprice = pdOrderprice;
            }

            public Object getPdSeller() {
                return pdSeller;
            }

            public void setPdSeller(Object pdSeller) {
                this.pdSeller = pdSeller;
            }
        }
    }
}
