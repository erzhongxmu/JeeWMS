import { getAction } from '@/api/manage'
import {message} from 'ant-design-vue'

/**
 *  同步成品入库
 * @param data 
 */ 
function GS_ProductPut(data,callBack,error) {
    getAction('/gswms/wmImNoticeH/getGscpruku',data).then(res=>{
        if(res.success){
            message.success(res.message);
            callBack()
        }else{
            message.error(res.message);
            error()
        }
    })
}

/**
 *  同步采购入库
 * @param data 
 */
function GS_Purchase(data,callBack,error) {
    getAction('/gswms/wmImNoticeH/getGscgrk',data).then(res=>{
        if(res.success){
            message.success(res.message);
            callBack()
        }else{
            message.error(res.message);
            error()
        }
    })
}

/**
 *  同步生产退料
 * @param data 
 */ 
function GS_productionSendBack(data,callBack,error) {
    getAction('/gswms/wmImNoticeH/getGssctl',data).then(res=>{
        if(res.success){
            message.success(res.message);
            callBack()
        }else{
            message.error(res.message);
            error()
        }
    })
}

/**
 *  同步发货单
 * @param data 
 */
function GS_Shipments(data,callBack,error) {
    getAction('/gswms/wmOmNoticeH/getGsfahuodan',data).then(res=>{
        if(res.success){
            message.success(res.message);
            callBack()
        }else{
            message.error(res.message);
            error()
        }
    })
}

/**
 *  同步生产领料单
 * @param data 
 */
function GS_ProductionPicking(data,callBack,error) {
    getAction('/gswms/wmOmNoticeH/getGslingliaodan',data).then(res=>{
        if(res.success){
            message.success(res.message);
            callBack()
        }else{
            message.error(res.message);
            error()
        }
    })
}

export default {
    GS_ProductPut,
    GS_Purchase,
    GS_productionSendBack,
    GS_Shipments,
    GS_ProductionPicking
}