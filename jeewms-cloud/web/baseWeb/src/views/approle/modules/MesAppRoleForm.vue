<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item :label="$t('角色编号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="approleCode">
              <a-input v-model="model.approleCode" placeholder="请输入角色编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('角色名称')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="approleName">
              <a-input v-model="model.approleName" placeholder="请输入角色名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('APP功能')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="appmodelId">
              <j-popup
                v-model="model.appmodelId"
                field="appmodelId"
                org-fields="id,appmodel_code,appmodel_name"
                dest-fields="appmodelId,appmodelCode,appmodelName"
                code="mes_app_function"
                multi="true"
                @input="popupCallback"
                />
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="24">
            <a-form-model-item label="app模块编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="appmodelCode">
              <a-textarea v-model="model.appmodelCode" rows="4" placeholder="请输入app模块编号" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="app模块名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="appmodelName">
              <a-textarea v-model="model.appmodelName" rows="4" placeholder="请输入app模块名称" />
            </a-form-model-item>
          </a-col> -->
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'MesAppRoleForm',
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
          add: "/jeewms/mesAppRole/add",
          edit: "/jeewms/mesAppRole/edit",
          queryById: "/jeewms/mesAppRole/queryById"
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
                that.$message.success(this.$t('操作成功'));
                that.$emit('ok');
              }else{
                that.$message.warning(this.$t('操作失败'));
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