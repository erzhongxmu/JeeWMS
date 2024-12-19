<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="span">
            <a-form-model-item :label="$t('费用编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costNo">
              <j-popup
                v-model="model.costNo"
                field="costNo"
                org-fields="cost_no,cost_name,cost_desc"
                dest-fields="costNo,costName,costDesc"
                code="bms_cost"
                :multi="true"
                @input="popupCallback"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('费用名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costName">
              <a-input v-model="model.costName" :placeholder="$t('请输入费用名称')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('费用描述')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costDesc">
              <a-input v-model="model.costDesc" :placeholder="$t('请输入费用描述')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('来源编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSoNo">
              <j-popup
                v-model="model.costSoNo"
                field="costSoNo"
                org-fields="cost_so_no,cost_so_name"
                dest-fields="costSoNo,costSoName"
                code="bms_cost_source"
                :multi="true"
                @input="popupCallback"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('来源名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSoName">
              <a-input v-model="model.costSoName" :placeholder="$t('请输入来源名称')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费对象类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjType">
              <j-popup
                v-model="model.costObjType"
                field="costObjType"
                org-fields="cost_obj_type,cost_obj_no,cost_obj_name"
                dest-fields="costObjType,costObjNo,costObjName"
                code="bms_cost_source"
                :multi="true"
                @input="popupCallback"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费对象编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjNo">
              <a-input v-model="model.costObjNo" :placeholder="$t('请输入计费对象编号')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费对象名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjName">
              <a-input v-model="model.costObjName" :placeholder="$t('请输入计费对象名称')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('来源数量')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSoSum">
              <a-input-number v-model="model.costSoSum" :placeholder="$t('请输入来源数量')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('来源单位')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSoUnit">
              <!-- <a-input v-model="model.costSoUnit" :placeholder="$t('请输入来源单位')"  ></a-input> -->
              <j-search-select-tag v-model="model.costSoUnit" 
               dict="bms_cost_type,cost_type_unit,cost_type_no" 
               :placeholder="$t('请选择来源单位')"></j-search-select-tag>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('来源日期')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSoDate">
              <j-date :placeholder="$t('请选择来源日期')"  v-model="model.costSoDate" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('规则编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costRuleNo">
              <j-popup
                v-model="model.costRuleNo"
                field="costRuleNo"
                org-fields="cost_rule_no,cost_rule_name"
                dest-fields="costRuleNo,costRuleName"
                code="bms_cost_rule_h"
                :multi="true"
                @input="popupCallback"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('规则名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costRuleName">
              <a-input v-model="model.costRuleName" :placeholder="$t('请输入规则名称')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费规则行项目号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costRuleItemNo">
              <a-input v-model="model.costRuleItemNo" :placeholder="$t('请输入计费规则行项目号')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costTypeNo">
              <j-popup
                v-model="model.costTypeNo"
                field="costTypeNo"
                org-fields="cost_type_no"
                dest-fields="costTypeNo"
                code="bms_cost_type"
                :multi="true"
                @input="popupCallback"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('开始数量')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="beginSum">
              <a-input-number v-model="model.beginSum" :placeholder="$t('请输入开始数量')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('结束数量')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="endSum">
              <a-input-number v-model="model.endSum" :placeholder="$t('请输入结束数量')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费单位')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costUnit">
              <!-- <a-input v-model="model.costUnit" :placeholder="$t('请输入计费单位')"  ></a-input> -->
              <j-search-select-tag v-model="model.costUnit" 
               dict="bms_cost_type,cost_type_unit,cost_type_no" 
               :placeholder="$t('请输入计费单位')"></j-search-select-tag>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费规则不含税价')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costJfgz">
              <a-input-number v-model="model.costJfgz" :placeholder="$t('请输入计费规则不含税价')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费税率')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSl">
              <a-input-number v-model="model.costSl" :placeholder="$t('请输入计费税率')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费规则原含税价')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costHsj">
              <a-input-number v-model="model.costHsj" :placeholder="$t('请输入计费规则原含税价')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('货币')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costHb">
              <!-- <a-input v-model="model.costHb" :placeholder="$t('请输入货币')"  ></a-input> -->
             <j-dict-select-tag type="list" v-model="model.costHb" dictCode="currency_hb" :placeholder="$t('请选择货币')" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费不含税价')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costCoBhsj">
              <a-input-number v-model="model.costCoBhsj" :placeholder="$t('请输入计费不含税价')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费原含税价')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costCoYhsj">
              <a-input-number v-model="model.costCoYhsj" :placeholder="$t('请输入计费原含税价')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('状态')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
              <!-- <a-input v-model="model.status" :placeholder="$t('请输入状态')"  ></a-input> -->
              <j-dict-select-tag type="list" v-model="model.status" dictCode="detail_status" :placeholder="$t('请选择状态')" />
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

  export default {
    name: 'BmsCostDetailForm',
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
        span:'8',
        labelCol: {
          xs: { span: 24 },
          sm: { span: 9 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 13 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/bms/bmsCostDetail/add",
          edit: "/bms/bmsCostDetail/edit",
          queryById: "/bms/bmsCostDetail/queryById"
        }
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
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(value,row){
         this.model = Object.assign(this.model, row);
      },
    }
  }
</script>