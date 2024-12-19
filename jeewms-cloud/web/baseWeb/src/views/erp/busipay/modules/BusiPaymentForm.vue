<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <!-- <a-col :span="8">
            <a-form-model-item :label="$t('单号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query04">
              <a-input v-model="model.query04" :placeholder="$t('请输入单号')" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item :label="$t('供应商名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
                <j-popup
                  v-model="model.query09"
                  field="supplyName"
                  org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch"
                  dest-fields="supplyCode,supplyName,supplyNick"
                  code="md_sup"
                  :multi="false"
                  :disabled="true"
                  @input="popupCallback2"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('供应商编码')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" :placeholder="$t('请输入供应商编码')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('主PO')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" :placeholder="$t('请输入主PO')"  disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('是否含税')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query25">
              <j-dict-select-tag type="radio"  v-model="model.query25" :placeholder="$t('请选择是否含税')" dictCode="if_yes_no" disabled/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" v-if="model.query25 == '是'">
            <a-form-model-item :label="$t('税率%')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num10">
              <a-input v-model="model.num10" :placeholder="$t('请输入税率')" @input="num10Input" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('总金额')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num09">
              <a-input v-model="model.num07" :placeholder="$t('请输入总金额')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('币种')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
              <j-dict-select-tag  v-model="model.query22" dictCode="currency"/>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item :label="$t('本次付款金额')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num13">
              <a-input v-model="model.num02" :placeholder="$t('请输入计划付款金额')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('本次付款含税金额')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num13">
              <a-input v-model="model.num03" :placeholder="$t('请输入计划付款金额')" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
              <a-input v-model="model.text01" :placeholder="$t('请输入备注')"  ></a-input>
            </a-form-model-item>
          </a-col>
          
        </a-row>
      </a-form-model>
    </j-form-container>

    <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane :tab="$t('付款商品信息')" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="busiPaymentItem.loading"
          :columns="busiPaymentItem.columns"
          :dataSource="busiPaymentItem.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"
          @valueChange="valueChange"
          />
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'
  import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
  import { FormTypes,getRefPromise,VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
  import { getCurrentTime } from '@/utils/util'


  export default {
    name: 'BusiPaymentForm',
    mixins: [JEditableTableModelMixin],
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        refKeys: ['busiPaymentItem', ],
        tableKeys:['busiPaymentItem', ],
        activeKey: 'busiPaymentItem',
        // busi_po_item
        busiPaymentItem: {
          loading: false,
          dataSource: [],
          columns: [
          {
            title:this.$t('子PO'), 
            key: 'query14',
            type: FormTypes.input,
            width: '200px',
            placeholder: '请输入${title}',
            disabled: true,
            defaultValue: '',
          },
          {
            title:this.$t('商品名称'), 
            key: 'query11',
            type: FormTypes.popup,
            popupCode: 'wv_goods_select',
            destFields: 'query11,query10,query12',
            orgFields: 'shp_ming_cheng,goods_code,baseunit',
            disabled: true,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title:this.$t('商品编码'), 
            key: 'query10',
            type: FormTypes.input,
            width: '200px',
            disabled: true,
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title:this.$t('单位'), 
            key: 'query12',
            type: FormTypes.input,
            width: '200px',
            disabled: true,
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title:this.$t('数量'), 
            key: 'num01',
            type: FormTypes.input,
            disabled: true,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title:this.$t('单价'), 
            key: 'num04',
            type: FormTypes.inputNumber,
            width: '100px',
            placeholder: '请输入${title}',
            validateRules: [{ required: true, message: '${title}不能为空' }],
            defaultValue: '',
          },
          {
            title:this.$t('含税单价'), 
            key: 'num06',
            type: FormTypes.input,
            disabled: true,
            width: '100px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          // {
          //   title:this.$t('总价'), 
          //   key: 'num05',
          //   type: FormTypes.input,
          //   width: '200px',
          //   placeholder: '请输入${title}',
          //   defaultValue: '',
          // },
          {
            title:this.$t('总价'), 
            key: 'num07',
            type: FormTypes.input,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
        ],
        },
        url: {
          add: "/jeeerp/busiPaymentReceived/batchAdd",
          edit: "/jeeerp/busiPaymentReceived/batchEdit",
          queryById: "/jeeerp/busiPaymentReceived/queryById"
        },
        paymentSchedule: [],
        type:'',
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      chargeAgainst(e){
        this.type = "CX"
        this.edit(e)
      },
    popupCallback1(value,row) {
      this.model.query05 = row.company
      this.model.query06 = row.factory
    },
    popupCallback2(value,row) {
      this.model.query08 = row.supplyCode
    },
    num10Input(){
      this.getAllTable().then(tables => {
        tables[0].getAll(false).then((tables)=>{
            let arr = []
            tables.values.map((item,idnex)=>{
              arr.push({row:item})
            })
            arr.map(item=>{
              this.valueChange(item)
            })
        })
      })
    },
    valueChange(e){
      let that = this
      if(e.row.num01 && e.row.num04){
        this.getAllTable().then(tables => {
          // 税率
          let num10 = that.model.num10?Number(this.model.num10)/100:''
          // 含税单价
          let num06 = Number(e.row.num04) * Number(num10) + Number(e.row.num04) 
          // 含税金额
          let num07 = Number(e.row.num01) * Number(num06)
          tables[0].setValues([
            {
              rowKey: e.row.id, // 行的id
              values:{
                num05:Number(e.row.num01) * Number(e.row.num04),
                num06,
                num07
              }
            }
          ])
          tables[0].getAll(false).then((v)=>{
            // let num08 = 0
            let num09 = 0
            // let num11 = 0
            // let num12 = 0
            v.values.map((item,idnex)=>{
              // 总金额
              // num08 += Number(item.num05) 
              // 含税总金额
              num09 += Number(item.num07)
              // 尾款
              // num11 += Number(item.num05) 
              // 尾款(含税)
              // num12 += Number(item.num07)
            })

            that.$nextTick(()=>{
              that.model = {...that.model,num09}
            })
          })
        })
      }
    },
      getPayment(orderId) {
        let params = { query04: orderId,pageNo:1,pageSize:1000}
        console.log(orderId,'orderId')
        getAction('/jeeerp/busiPaymentReceived/list',params).then(res => {
          this.paymentSchedule = res.result.records
          this.paymentSchedule.map((item,index)=>{
            if(item.query01 == 'YPFKJH'){
              item.num14 = item.num05
              item.num15 = item.num07
              item.num02 = item.num08
              item.num03 = item.num09
            }
            // item.query04 = null
            item.id = null
            item.createBy = null
            item.createName = null
            item.createTime = null
            item.query23 = null
            item.updateBy = null
            item.updateName = null
            item.updateTime = null
            item.query03 = getCurrentTime()
          })
          if(this.paymentSchedule.length) {
            this.model =  this.paymentSchedule[0]
            this.busiPaymentItem.dataSource = this.paymentSchedule
          } else {
            this.model =  {}
            this.busiPaymentItem.dataSource = []
          }
        })
      },
      add () {
        this.busiPaymentItem.dataSource = []
        this.edit(this.modelDefault);
      },
      close() {
        this.model = {}
        this.busiPaymentItem.dataSource = []
        this.paymentSchedule = []
      },
      edit(record) {
        this.model = Object.assign({}, record)
        if (this.model.id) {
          let params = { query04: this.model.query04, pageNo: 1, pageSize: 1000 }
          getAction('/jeeerp/busiPaymentReceived/list', params).then((res) => {
              this.busiPaymentItem.dataSource = res.result.records
          })
        }else{
        // 新增
          this.model.query03 = getCurrentTime()
        }
        this.visible = true
      },
      classifyIntoFormData(allValues) {
        let list = []
        allValues.tablesValue[0].values.map((item, index) => {
          list.push({
            ...this.model,
            ...item,
            query01: 'FKD',
          })
        })
        return list
      },
      request(formData) {
        let url = this.url.add, method = 'post'
        if (this.model.id) {
          url = this.url.edit
          method = 'put'
        }
        if(this.type == 'CX'){
          url = this.url.add
          method = 'post'
          formData.map((item,index)=>{
            item.num09 = -item.num09
            item.num07 = -item.num07
          })
        }
        this.confirmLoading = true
        httpAction(url, formData, method).then((res) => {
          if (res.success) {
            this.$message.success(this.$t('操作成功'))
            this.$emit('ok')
            this.close()
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
        }).finally(() => {
          this.confirmLoading = false
        })
    },
    //   submitForm () {
    //     const that = this;
    //     // 触发表单验证
    //     this.$refs.form.validate(valid => {
    //       if (valid) {
    //         that.confirmLoading = true;
    //         let httpurl = '';
    //         let method = '';
    //         if(!this.model.id){
    //           httpurl+=this.url.add;
    //           method = 'post';
    //         }else{
    //           httpurl+=this.url.edit;
    //            method = 'put';
    //         }
    //         httpAction(httpurl,this.model,method).then((res)=>{
    //           if(res.success){
    //             that.$message.success(res.message);
    //             that.$emit('ok');
    //           }else{
    //             that.$message.warning(res.message);
    //           }
    //         }).finally(() => {
    //           that.confirmLoading = false;
    //         })
    //       }
         
    //     })
    //   },
    }
  }
</script>