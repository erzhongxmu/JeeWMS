<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-model-item label="Applicant" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
              <j-search-select-tag
                type="list"
                v-model="model.query09"
                @change="sysUserChange"
                dict="sys_user,realname,username"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item label="PO/EX" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" placeholder="请输入" ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item label="Client" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query10">
              <j-search-select-tag
                type="list"
                v-model="model.query10"
                dict="Client"
                :placeholder="$t('请选择')"
              />
              <!-- <j-popup
                  v-model="model.query10"
                  field="query10"
                  org-fields="ke_hu_jian_cheng"
                  dest-fields="query10"
                  code="md_cus"
                  :multi="false"
                /> -->
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Item" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query11">
              <a-input v-model="model.query11" placeholder="请输入"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Vendor" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query12">
              <a-input v-model="model.query12" placeholder="请输入" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Curr" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" placeholder="请输入"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="Total" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query14">
              <a-input v-model="model.query14" placeholder="请输入"  ></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item label="个税" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query15">
              <a-input v-model="model.query15" placeholder="请输入个税"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="税前工资" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query16">
              <a-input v-model="model.query16" placeholder="请输入税前工资"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8">
            <a-form-model-item label="实发工资" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num04">
              <a-input-number v-model="model.num05" placeholder="请输入实发工资" style="width: 100%" />
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8">
            <a-form-model-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createTime">
              <a-input v-model="model.createTime" placeholder="请输入创建时间" disabled></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
              <a-input v-model="model.text01" placeholder="请输入备注"  ></a-input>
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
  import { getCurrentTime } from '@/utils/util'

  export default {
    name: 'BusiOrdPriceForm',
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
          add: "/jeeerp/busiOrdPrice/add",
          edit: "/jeeerp/busiOrdPrice/edit",
          queryById: "/jeeerp/busiOrdPrice/queryById"
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
    sysUserChange(e,v){
      // this.model.query08 = e
      this.model.query09 = v
    },  
    popupCallback1(value,row) {
      this.model.query05 = row.company
      this.model.query06 = row.factory
    },
      add () {
        // this.modelDefault.query12 = 'BACKOFFICE'
        this.edit(this.modelDefault);
      },
      edit (record) {
        if(!record.id){
          // 新增
          record.query03 = getCurrentTime()
        }
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
            this.model.query01 = 'YGGZ'
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