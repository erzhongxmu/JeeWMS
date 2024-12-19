<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="span">
            <a-form-model-item :label="$t('附件单号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="accessoryNo">
              <a-input v-model="model.accessoryNo" :placeholder="$t('请输入附件单号')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('来源编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSoNo">
              <a-input v-model="model.costSoNo" :placeholder="$t('请输入来源编号')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('来源名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSoName">
              <a-input v-model="model.costSoName" :placeholder="$t('请输入来源名称')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('来源类型编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSNo">
              <j-popup
                v-model="model.costSNo"
                field="costSNo"
                org-fields="cost_s_no,cost_s_name,cost_s_desc"
                dest-fields="costSNo,costSName,costSDesc"
                code="bms_cs_type"
                :multi="true"
                @input="popupCallback"
                :placeholder="$t('请选择')"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('来源类型名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSName">
              <a-input v-model="model.costSName" :placeholder="$t('请输入来源类型名称')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('来源类型描述')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSDesc">
              <a-input v-model="model.costSDesc" :placeholder="$t('请输入来源类型描述')"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('计费对象类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costObjType">
              <a-input v-model="model.costObjType" :placeholder="$t('请输入计费对象类型')"  ></a-input>
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
              <j-date :placeholder="$t('请选择来源日期')" v-model="model.costSoDate"  style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="span">
            <a-form-model-item :label="$t('状态')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
              <!-- <a-input v-model="model.status" :placeholder="$t('请输入状态')"  ></a-input> -->
              <j-dict-select-tag type="list" v-model="model.status"  dictCode="cost_status" :placeholder="$t('请选择状态')" />
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
    name: 'BmsCostSourceForm',
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
            sm: { span: 7 },
          },
          wrapperCol: {
            xs: { span: 24 },
            sm: { span: 16 },
          },
        span:'12',

        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/bms/bmsCostSource/add",
          edit: "/bms/bmsCostSource/edit",
          queryById: "/bms/bmsCostSource/queryById"
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