<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    >
    <a-spin :spinning="confirmLoading">
      <a-form-model ref="form" :model="model" :rules="validatorRules">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="费用名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costCode">
              <j-search-select-tag v-model="model.costCode"dict="ba_cost,cost_name,cost_code" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="价格RMB" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costJg">
              <a-input v-model="model.costJg"placeholder="请输入价格RMB" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="税率" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costSl">
              <a-input v-model="model.costSl"placeholder="请输入税率" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="折扣" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costZk">
              <a-input v-model="model.costZk"placeholder="请输入折扣" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="不含税价RMB" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costBhs">
              <a-input v-model="model.costBhs"placeholder="请输入不含税价RMB" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="含税价RMB" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="costHs">
              <a-input v-model="model.costHs"placeholder="请输入含税价RMB" ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: "WmCusCostIModal",
    components: {
    },
    props:{
      mainId:{
        type:String,
        required:false,
        default:''
      }
    },
    data () {
      return {
        title:"操作",
        width:800,
        visible: false,
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
          add: "/jeebms/wmCusCostH/addWmCusCostI",
          edit: "/jeebms/wmCusCostH/editWmCusCostI",
        }

      }
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
      close () {
        this.$emit('close');
        this.visible = false;
        this.$refs.form.clearValidate();
      },
      handleOk () {
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
            this.model['cusCostId'] = this.mainId
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(this.$t('操作成功'));
                that.$emit('ok');
              }else{
                that.$message.warning(this.$t('操作失败'));
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }else{
             return false
          }
        })
      },
      handleCancel () {
        this.close()
      },


    }
  }
</script>
