<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createName">
              <a-input v-model="model.createName" placeholder="请输入创建人名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="创建人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createBy">
              <a-input v-model="model.createBy" placeholder="请输入创建人登录名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createTime">
              <j-date placeholder="请选择创建日期" v-model="model.createTime"  style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="updateName">
              <a-input v-model="model.updateName" placeholder="请输入更新人名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="更新人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="updateBy">
              <a-input v-model="model.updateBy" placeholder="请输入更新人登录名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="updateTime">
              <j-date placeholder="请选择更新日期" v-model="model.updateTime"  style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sysOrgCode">
              <a-input v-model="model.sysOrgCode" placeholder="请输入所属部门"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sysCompanyCode">
              <a-input v-model="model.sysCompanyCode" placeholder="请输入所属公司"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query01" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query01">
              <a-input v-model="model.query01" placeholder="请输入query01"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query02" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query02">
              <a-input v-model="model.query02" placeholder="请输入query02"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query03" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="model.query03" placeholder="请输入query03"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query04" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query04">
              <a-input v-model="model.query04" placeholder="请输入query04"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query05" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05">
              <a-input v-model="model.query05" placeholder="请输入query05"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query06" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
              <a-input v-model="model.query06" placeholder="请输入query06"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query07" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
              <a-input v-model="model.query07" placeholder="请输入query07"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query08" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" placeholder="请输入query08"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query09" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
              <a-input v-model="model.query09" placeholder="请输入query09"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query10" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query10">
              <a-input v-model="model.query10" placeholder="请输入query10"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query11" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query11">
              <a-input v-model="model.query11" placeholder="请输入query11"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query12" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query12">
              <a-input v-model="model.query12" placeholder="请输入query12"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query13" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" placeholder="请输入query13"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query14" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query14">
              <a-input v-model="model.query14" placeholder="请输入query14"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query15" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query15">
              <a-input v-model="model.query15" placeholder="请输入query15"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query16" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query16">
              <a-input v-model="model.query16" placeholder="请输入query16"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query17" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
              <a-input v-model="model.query17" placeholder="请输入query17"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query18" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query18">
              <a-input v-model="model.query18" placeholder="请输入query18"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query19" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
              <a-input v-model="model.query19" placeholder="请输入query19"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="query20" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query20">
              <a-input v-model="model.query20" placeholder="请输入query20"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="租户ID" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tenantId">
              <a-input-number v-model="model.tenantId" placeholder="请输入租户ID" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-input v-model="model.remark" placeholder="请输入备注"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remarks">
              <a-input v-model="model.remarks" placeholder="请输入备注"  ></a-input>
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
    name: 'WmImportDataForm',
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
        url: {
          add: "/jeewms/wmImportData/add",
          edit: "/jeewms/wmImportData/edit",
          queryById: "/jeewms/wmImportData/queryById"
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
    }
  }
</script>