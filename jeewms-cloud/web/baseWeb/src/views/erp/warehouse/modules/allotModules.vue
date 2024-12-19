<template>
    <j-modal :title="title" :width="width" :visible="visible" switchFullscreen @ok="handleOk2"
        :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }" @cancel="handleCancel" cancelText="关闭">
        <a-spin :spinning="confirmLoading">
            <j-form-container :disabled="formDisabled">
                <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
                    <a-row>
                        <a-col :span="8">
                            <a-form-model-item label="源仓库" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05">
                                <j-search-select-tag placeholder="请选择" v-model="model.query05"
                                    dict="ba_store,store_name,store_code" />
                            </a-form-model-item>
                        </a-col>
                        <a-col :span="8">
                            <a-form-model-item label="目的仓库" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
                                <j-search-select-tag placeholder="请选择" v-model="model.query06"
                                    dict="ba_store,store_name,store_code" />
                            </a-form-model-item>
                        </a-col>
                        <a-col :span="8">
                            <a-form-model-item label="供应商" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
                                <j-popup v-model="model.query07" field="query07" org-fields="gys_bian_ma,gys_jian_cheng"
                                    dest-fields="query17,query07" code="md_sup" :multi="false" :disabled="formDisabled" />
                            </a-form-model-item>
                        </a-col>
                        <a-col :span="8">
                            <a-form-model-item label="销售单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
                                <a-input v-model="model.query08" placeholder="请输入销售单号"></a-input>
                            </a-form-model-item>
                        </a-col>
                        <a-col :span="8">
                            <a-form-model-item label="内部发票号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
                                <a-input v-model="model.query09" placeholder="请输入内部发票号"></a-input>
                            </a-form-model-item>
                        </a-col>
                        <a-col :span="8">
                            <a-form-model-item label="采购人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
                                <j-search-select-tag type="list" v-model="model.query10"
                                    dict="sys_user,realname,realname,del_flag = '0'" :placeholder="$t('请选择')" />
                            </a-form-model-item>
                        </a-col>
                        <!-- <a-col :span="8">
                            <a-form-model-item label="调拨人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
                                <a-input v-model="model.query07" placeholder="请输入调拨人"></a-input>
                            </a-form-model-item>
                        </a-col> -->
                        <a-col :span="8">
                            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
                                <a-input v-model="model.text01" placeholder="请输入备注"></a-input>
                            </a-form-model-item>
                        </a-col>
                    </a-row>
                </a-form-model>
            </j-form-container>
            <j-editable-table :ref="refKeys[0]" :loading="allotTable.loading" :columns="allotTable.columns"
                :dataSource="allotTable.dataSource" :maxHeight="300" :disabled="formDisabled" :rowNumber="true"
                :rowSelection="true" :actionButton="true">
            </j-editable-table>
        </a-spin>
    </j-modal>
</template>
  
<script>
import { FormTypes, getRefPromise, VALIDATE_NO_PASSED, validateTables } from '@/utils/JEditableTableUtil'
import { getAction, httpAction } from '@/api/manage'
import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'

export default {
    name: 'allotModules',
    mixins: [JEditableTableModelMixin],
    components: {
    },
    data() {
        return {
            confirmLoading: false,
            formDisabled: false,
            validatorRules: {
                query05: [{ required: true, message: ' ' }],
                query06: [{ required: true, message: ' ' }],
            },
            refKeys: ['busiOmItem',],
            title: '',
            labelCol: {
                xs: { span: 24 },
                sm: { span: 6 },
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 16 },
            },
            width: 1200,
            visible: false,
            disableSubmit: false,
            model: {},
            allotTable: {
                loading: false,
                dataSource: [],
                columns: [
                    {
                        title: '子PO',
                        key: 'query11',
                        type: FormTypes.popup,
                        popupCode: 'wv_stock_ky',
                        destFields: 'query11,query12,query13,query14,query15,query16,num01',
                        orgFields: 'goods_batch,cus_code,zhong_wen_qch,goods_code,shp_ming_cheng,base_unit,base_goodscount',
                        validateRules: [{ required: true, message: '${title}不能为空' }],
                        width: '200px',
                        placeholder: '请输入${title}',
                        defaultValue: '',
                    },
                    {
                        title: '商品编码',
                        key: 'query14',
                        type: FormTypes.input,
                        width: '200px',
                        placeholder: '请输入${title}',
                        disabled: true,
                        defaultValue: '',
                    },
                    {
                        title: '商品名称',
                        key: 'query15',
                        type: FormTypes.input,
                        disabled: true,
                        width: '200px',
                        placeholder: '请输入${title}',
                        defaultValue: '',
                    },
                    {
                        title: '数量',
                        key: 'num01',
                        type: FormTypes.inputNumber,
                        width: '100px',
                        placeholder: '请输入${title}',
                        validateRules: [{ required: true, message: '${title}不能为空' }],
                        defaultValue: '',
                    },
                    {
                        title: '单位',
                        key: 'query16',
                        type: FormTypes.input,
                        width: '100px',
                        placeholder: '请输入${title}',
                        disabled: true,
                        defaultValue: '',
                    },
                ]
            },
            url: {
                add: '/jeeerp/busiOm/batchAllotAdd',
                edit: '/jeeerp/busiOm/batchEdit',
            }
        }
    },
    methods: {
        add() {
            this.visible = true
        },
        edit(record) {
            this.visible = true
            this.model =JSON.parse(JSON.stringify(record)) 
            getAction("/jeeerp/busiOm/list",{query04:record.query04,pageSize:100}).then(res=>{
                this.allotTable.dataSource = res.result.records
            })
        },
        particulars(record){
            this.title = "详情"
            this.formDisabled = true
            this.edit(record)
        },
        validateSubForm(allValues) {
            return new Promise((resolve, reject) => {
                Promise.all([])
                    .then(() => {
                        resolve(allValues)
                    })
                    .catch((e) => {
                        if (e.error === VALIDATE_NO_PASSED) {
                            // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
                            this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
                        } else {
                            console.error(e)
                        }
                    })
            })
        },
        handleOk2(){
          if(this.title == "详情"){
            this.handleCancel()
          }else{
            this.handleOk()
          }
        },
        /** 整理成formData */
        classifyIntoFormData(allValues) {
            let main = Object.assign(this.model, allValues.formValue)
            let list = allValues.tablesValue[0].values
            let dateFormat = []
            list.map((item) => {
                dateFormat.push({
                    ...main,
                    ...item,
                    query01: 'WLDB'
                })
            })
            return dateFormat
        },
        handleCancel() {
            this.allotTable.dataSource = [];
            this.formDisabled = false
            this.model = {};
            this.loading = false;
            this.visible = false;
        }
    }
}
</script>