<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-model-item label="BANK ACCOUNT" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query30">
              <j-search-select-tag
                  v-model="model.query30"
                  dict="payment_account_number">
              </j-search-select-tag>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Expense type" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
              <j-popup
                v-model="model.query06"
                field="query06"
                org-fields="query04"
                dest-fields="query06"
                code="conf_erp_cost_type"
                :multi="false"
                :="formDisabled"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Staff" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
              <j-search-select-tag
                type="list"
                v-model="model.query07"
                dict="sys_user,realname,realname,del_flag = '0'"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Date" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <j-date v-model="model.query08" :placeholder="$t('请选择')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="YYMM" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
              <j-date v-model="model.query09" :placeholder="$t('请选择')" dateFormat="YYMM"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <!-- <a-form-model-item label="Client" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query10">
              <a-input v-model="model.query10" placeholder="请输入Client" ></a-input>
            </a-form-model-item> -->
            <a-form-model-item label="Client" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query10">
              <j-popup
                v-model="model.query10"
                field="query08"
                org-fields="ke_hu_jian_cheng,ke_hu_bian_ma,xing_ye_fen_lei"
                dest-fields="query09,query08,query11"
                code="md_cus"
                :multi="false"
                @input="popupCallback5"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Company" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query11">
              <a-input v-model="model.query11" placeholder="" ></a-input>
              <!-- <a-form-item :label="$t('企业属性')" :labelCol="labelCol" :wrapperCol="wrapperCol"> -->
          <!-- <j-dict-select-tag v-model="model.query11" type="list" dictCode="wms_com_type,com_type_code,com_type_name" :placeholder="$t('请选择')" :disabled="title==$t('查看')?true:false"/> -->
        <!-- </a-form-item> -->
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Note" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query12">
              <a-input v-model="model.query12" placeholder="请输入Note" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Charge to client" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" placeholder="请输入Charge to client" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Departement" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query14">
              <j-search-select-tag
                type="list"
                v-model="model.query14"
                dict="Departement"
                :placeholder="$t('请选择')"
                disabled
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query15">
              <div style="display: flex;">
                <a-input v-model="model.query15" placeholder="请输入PO" ></a-input>
                <a-button style="margin-left: 10px;" @click="getpo">{{$t('查询')}}</a-button>
              </div>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="SHIP#" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-model="model.query16" placeholder="请输入SHIP#" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="INV#" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
              <a-input v-model="model.query17" placeholder="请输入INV#" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Order Qty" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query18">
              <a-input v-model="model.query18" placeholder="请输入Order Qty" type="number"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Vendor" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
                <j-search-select-tag type="list" v-model="model.query19" dict="vendor" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Currency" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query20">
              <!-- <a-input v-model="model.query20" placeholder="请输入Currency" ></a-input> -->
              <j-search-select-tag type="list" v-model="model.query20" dict="Currency" disabled/>

            </a-form-model-item>
            <!-- <a-form-item label="字段类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query04">
              <j-search-select-tag type="list" v-model="model.query04" :dictOptions="Options1" />
            </a-form-item> -->
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Unit price" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
              <a-input v-model="model.query21" placeholder="请输入Unit price" type="number"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Total CNY" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
              <a-input v-model="model.query22" placeholder="请输入Total CNY" ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import { validateDuplicateValue } from '@/utils/util'
import { getCurrentTime, } from '@/utils/util'

export default {
  name: 'BusiOrdPriceForm',
  components: {},
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false,
    },
  },
  data() {
    return {
      Options1: [
        { text: 'RMB', value: 'RMB' },
        { text: 'USD', value: 'USD' },
        { text: 'HKD', value: 'HKD' }
      ],
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      validatorRules: {
        query06: [{required: true, message: ' '}],
        query07: [{required: true, message: ' '}],
        query08: [{required: true, message: ' '}],
        query09: [{required: true, message: ' '}],
        query10: [{required: true, message: ' '}],
        query11: [{required: true, message: ' '}],
        query12: [{required: true, message: ' '}],
        query14: [{required: true, message: ' '}],
        query16: [{required: true, message: ' '}],
        query18: [{required: true, message: ' '}],
        query19: [{required: true, message: ' '}],
        query20: [{required: true, message: ' '}],
        query21: [{required: true, message: ' '}],
        query22: [{required: true, message: ' '}],
      },
      url: {
        add: '/jeeerp/busiOrdPrice/add',
        edit: '/jeeerp/busiOrdPrice/edit',
        queryById: '/jeeerp/busiOrdPrice/queryById',
      },
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
  },
  watch:{
    // model.query2(new,old){
    //   console.log(new,'000');
    // }
    'model.query21': {
      handler(newV, oldV) {
        this.model.query22 = newV*this.model.query18
      },
      immediate: true
    },
    'model.query18': {
      handler(newV, oldV) {
        this.model.query22 = newV*this.model.query21
      },
      immediate: true
    }
  },
  created() {
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model))
  },
  methods: {
    getpo(e){
      if(this.model.query15.length < 10) return
      let str = this.model.query15.substring(0, 10);
      getAction("/jeeerp/busiPo/list2",{query13:str}).then((res)=>{
        if(res.success){
          if(res.result.records.length != 0){
            let item = res.result.records[0]
            this.$nextTick(()=>{
              this.$set(this.model,'query10',item.query24)
              this.$set(this.model,'query11',item.xingYeFenLei)
              // this.model.query10 = item.query24
              // this.model.query11 =item.xingYeFenLei
              console.log(this.model);
            })
          }
        }
      })
    },
    popupCallback5(value,row) {
      this.model.query11 = row.query11
    },
    sysUserChange(e, v) {
      this.model.query08 = e
    },
    // popupCallback1(value, row) {
    //   this.$set(this.model,'query05',row.company)
    //   this.$set(this.model,'query06',row.factory)
    // },
    // (value, row) {
    //   this.$set(this.model,'query08',row.supplyCode)
    // },
    // popupCallback3(value, row) {
    //   this.$set(this.model,'query08',row.query08)
    // },
    // popupCallback4(value, row) {
    //   this.$set(this.model,'query16',row.query16)
    // },
    // query17Change(e){
    //   this.$set(this.model,'query09','')
    //   this.$set(this.model,'query08','')
    // },
    add() {
      this.modelDefault.query08 = getCurrentTime()
      this.modelDefault.query14 = "Purchase"
      this.modelDefault.query20 = "CNY"
      this.edit(this.modelDefault)
    },
    edit(record) {
      var initValue = record.query22
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(v=>{
        this.model.query22 = initValue
      })
    },
    submitForm() {
      const that = this
      // 触发表单验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
            this.model.query23 = '未确认'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          this.model.query01 = 'QCFY'
          httpAction(httpurl, this.model, method)
            .then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                this.model = {}
                that.$emit('ok')
              } else {
                that.$message.warning(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
            })
        }
      })
    },
  },
}
</script>