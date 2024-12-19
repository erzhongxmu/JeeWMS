<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('创建人名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createName">
              <a-input v-model="model.createName" :placeholder="$t('请输入创建人名称')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('更新人名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="updateName">
              <a-input v-model="model.updateName" :placeholder="$t('请输入更新人名称')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('单据类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query01">
              <a-input v-model="model.query01" :placeholder="$t('请输入单据类型')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('单据状态')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query02">
              <a-input v-model="model.query02" :placeholder="$t('请输入单据状态')" ></a-input>
            </a-form-model-item>
          </a-col> -->
           <a-col :span="8" >
            <a-form-model-item :label="$t('加工日期')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <j-date
              :showTime="true"
              dateFormat="YYYY-MM-DD"
              :placeholder="$t('请选择')"
              v-model="model.query03"
              style="width: 100%"
            />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('期限')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
              <a-range-picker
              @change="onDateChange"
              v-model="query21"
              dateFormat="YYYY/MM/DD'"
              style="width: 100%"
            />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('客户')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query24">
              <j-popup
              v-model="model.query24"
              code="cus_jiancheng"
              org-fields="zhong_wen_qch,ke_hu_bian_ma"  
              dest-fields="cusName,cusCode"
              field="cusCode"
              disabled/>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('单号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query04">
              <a-input v-model="model.query04" :placeholder="$t('请输入单号')" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('公司')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05">
              <a-input v-model="model.query05" :placeholder="$t('请输入公司')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('工厂')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
              <a-input v-model="model.query06" :placeholder="$t('请输入工厂')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('库存地点')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
              <a-input v-model="model.query07" :placeholder="$t('请输入库存地点')" disabled></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('商品编码')/成品" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query10">
              <a-input v-model="model.query10" :placeholder="$t('请输入商品编码')/成品" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('商品名称')/成品" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query11">
              <a-input v-model="model.query11" :placeholder="$t('请输入商品名称')/成品" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('生产PO')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" :placeholder="$t('请输入主PO')" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('子PO')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query14">
              <a-input v-model="model.query14" :placeholder="$t('请输入子PO')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('检验类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query15">
              <a-input v-model="model.query15" :placeholder="$t('请输入检验类型')" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- 
          <a-col :span="8" >
            <a-form-model-item :label="$t('单号')-行项目号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query23">
              <a-input v-model="model.query23" :placeholder="$t('请输入单号')-行项目号" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- 
          <a-col :span="8" >
            <a-form-model-item :label="$t('关联单据类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link01">
              <a-input v-model="model.link01" :placeholder="$t('请输入关联单据类型')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('关联单号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link02">
              <a-input v-model="model.link02" :placeholder="$t('请输入关联单号')" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
              <a-input v-model="model.text01" :placeholder="$t('请输入备注')" ></a-input>
            </a-form-model-item>
          </a-col>
           -->
          <a-col :span="8" >
            <a-form-model-item :label="$t('单据附件')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
              <j-upload v-model="model.attr1" :trigger-change="true" :disabled="title==$t('查看')?true:false"></j-upload>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="成品商品信息" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="busiPrdOrdItemTable.loading"
          :columns="busiPrdOrdItemTable.columns"
          :dataSource="busiPrdOrdItemTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="false"/>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import { getAction } from '@/api/manage'
  import { FormTypes,getRefPromise,VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
  import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'BusiPrdOverForm',
    mixins: [JEditableTableModelMixin],
    components: {
    },
    data() {
      return {
        query21: [],
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        model:{
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          query03: [{required: true, message: ' '}],
          query21: [{required: true, message: ' '}],
          query24: [{required: true, message: ' '}],
          query05: [{required: true, message: ' '}],
          query06: [{required: true, message: ' '}],
          query07: [{required: true, message: ' '}],
        },
        refKeys: ['busiPrdOrdItem'],
        tableKeys:['busiPrdOrdItem'],
        activeKey: 'busiPrdOrdItem',
        // busi_prd_ord_item
        busiPrdOrdItemTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title:this.$t('商品编码/成品'),
              key: 'query10',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
              disabled:true
            },
            {
              title:this.$t('商品名称/成品'), 
              key: 'query11',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
              disabled:true
            },
            {
              title:this.$t('单位'), 
              key: 'query12',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
              disabled:true
            },
            {
              title:this.$t('数量'), 
              key: 'num01',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title:this.$t('生产PO'), 
              key: 'query14',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title:this.$t('备注'), 
              key: 'text01',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            
          ]
        },
        url: {
          add: "/jeeerp/busiPrdOrd/add",
          edit: "/jeeerp/busiPrdOrd/edit",
          queryById: "/jeeerp/busiPrdOrd/queryById",
          busiPrdOrdItem: {
            list: '/jeeerp/busiPrdOrd/queryBusiPrdOrdItemByMainId'
          },
          busiOrdCraft: {
            list: '/jeeerp/busiPrdOrd/queryBusiOrdCraftByMainId'
          },
        }
      }
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
    },
    methods: {
      onDateChange:function(value, dateString) {
        this.model.query21 = dateString[0] + '~' + dateString[1]
      },
      addBefore(){
        this.model.id = null // 重置id,否则旧数据残留
        this.busiPrdOrdItemTable.dataSource=[]
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        this.$nextTick(() => {
        })
        // 加载子表数据
        if (this.model.id) {
          this.query21 = this.model.query21.split('~')
          this.$set(this.busiPrdOrdItemTable.dataSource,0,this.model)
          // this.busiPrdOrdItemTable.dataSource[0] = this.model
          console.log(this.busiPrdOrdItemTable.dataSource[0],'this.busiPrdOrdItemTable.dataSource[0]');
        }
      },
      //校验所有一对一子表表单
      validateSubForm(allValues){
          return new Promise((resolve,reject)=>{
            Promise.all([
            ]).then(() => {
              resolve(allValues)
            }).catch(e => {
              if (e.error === VALIDATE_NO_PASSED) {
                // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
                this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
              } else {
                console.error(e)
              }
            })
          })
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          ...allValues.tablesValue[0].values[0],
          query01: 'SCWG'
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },

    }
  }
</script>

<style scoped>
</style>