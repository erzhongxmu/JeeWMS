<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="span">
            <a-form-model-item :label="$t('集成编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diItNo">
              <a-input-number v-model="model.diItNo" :placeholder="$t('请输入集成编号')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('集成名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diItName">
              <a-input v-model="model.diItName" :placeholder="$t('请输入集成名称')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('采集规则编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diItRuleNo">
              <a-input-number v-model="model.diItRuleNo" :placeholder="$t('请输入采集规则编号')" style="width: 100%" />
            </a-form-model-item>
          </a-col>
<!--          <a-col :span="span">-->
<!--            <a-form-model-item :label="$t('采集规则编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diItRuleNo">-->
<!--              <j-popup-->
<!--                v-model="model.diItRuleNo"-->
<!--                field="diItRuleNo"-->
<!--                org-fields="di_it_rule_no,di_it_rule_name"-->
<!--                dest-fields="diItRuleNo,diItRuleName"-->
<!--                code="di_it_rule"-->
<!--                :multi="true"-->
<!--                @input="popupCallback"-->
<!--                />-->
<!--            </a-form-model-item>-->
<!--          </a-col>-->
          <a-col :span="span">
            <a-form-model-item :label="$t('采集规则名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diItRuleName">
              <a-input v-model="model.diItRuleName" :placeholder="$t('请输入采集规则名称')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('源类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diDsType">
              <a-input v-model="model.diDsType" :placeholder="$t('请输入源类型')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('源数据源')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diDatasource">
              <a-input v-model="model.diDatasource" :placeholder="$t('请输入源数据源')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('源状态')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diSstatus">
              <a-input v-model="model.diSstatus" :placeholder="$t('请输入源状态')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('源数据')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diSdata">
              <!-- <a-input v-model="model.diSdata" :placeholder="$t('请输入源数据')"  ></a-input> -->
              <j-search-select-tag v-model="model.diSdata" dict="sys_data_source,code,code" :placeholder="$t('请选择源数据')"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('源API')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diSapi">
              <a-input v-model="model.diSapi" :placeholder="$t('请输入源API')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('源SQL')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diSsql">
              <a-textarea v-model="model.diSsql" rows="1" :placeholder="$t('请输入')" />

<!--              <a-input v-model="model.diSsql" :placeholder="$t('请输入源SQL')"  ></a-input>-->
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('目的类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diDdsType">
              <a-input v-model="model.diDdsType" :placeholder="$t('请输入目的类型')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('目的数据源')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diDdatasource">
              <!-- <a-input v-model="model.diDdatasource" :placeholder="$t('请输入目的数据源')"  ></a-input> -->
              <j-search-select-tag v-model="model.diDdatasource" dict="sys_data_source,code,code" :placeholder="$t('请选择目的数据源')"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('目的状态')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diDstatus">
              <a-input v-model="model.diDstatus" :placeholder="$t('请输入目的状态')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('目的数据')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diDdata">
              <a-input v-model="model.diDdata" :placeholder="$t('请输入目的数据')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('目的API')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diDapi">
              <a-input v-model="model.diDapi" :placeholder="$t('请输入目的API')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('目的SQL')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="diDsql">
              <a-textarea v-model="model.diDsql" rows="1" :placeholder="$t('请输入')" />

<!--              <a-input v-model="model.diDsql" :placeholder="$t('请输入目的SQL')"  ></a-input>-->
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-textarea v-model="model.remark" rows="1" :placeholder="$t('请输入')" />
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
    name: 'DiItMethodForm',
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
        span:12,
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
        },
        url: {
          add: "/di/diItMethod/add",
          edit: "/di/diItMethod/edit",
          queryById: "/di/diItMethod/queryById"
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