<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <!-- <a-col :span="24">
            <a-form-model-item :label="$t('whNo')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="whNo">
              <a-input v-model="model.whNo" placeholder="请输入whNo"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="24">
            <a-form-model-item :label="$t('用户')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="userNo">
              <a-input v-model="model.userNo" placeholder="请输入用户"  ></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="24">
            <a-form-model-item :label="$t('userName')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="userName">
              <a-input v-model="model.userName" placeholder="请输入userName"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('userSkill')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="userSkill">
              <a-input v-model="model.userSkill" placeholder="请输入userSkill"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('orgCode')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="orgCode">
              <a-input v-model="model.orgCode" placeholder="请输入orgCode"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('priority')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="priority">
              <a-input v-model="model.priority" placeholder="请输入priority"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('groupType')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="groupType">
              <a-input v-model="model.groupType" placeholder="请输入groupType"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="24">
            <a-form-model-item :label="$t('排班日期')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="planDate">
              <a-input v-model="model.planDate" placeholder="请输入排班日期"  ></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="24">
            <a-form-model-item :label="$t('planDateTime')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="planDateTime">
              <a-input v-model="model.planDateTime" placeholder="请输入planDateTime"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('occupyTime')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="occupyTime">
              <a-input-number v-model="model.occupyTime" placeholder="请输入occupyTime" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('freeTime')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="freeTime">
              <a-input-number v-model="model.freeTime" placeholder="请输入freeTime" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('isRest')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="isRest">
              <a-input v-model="model.isRest" placeholder="请输入isRest"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('linkId')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="linkId">
              <a-input v-model="model.linkId" placeholder="请输入linkId"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('lineItemNo')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="lineItemNo">
              <a-input v-model="model.lineItemNo" placeholder="请输入lineItemNo"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="24">
            <a-form-model-item :label="$t('地点')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
              <a-input v-model="model.attr1" placeholder="请输入地点"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('工作内容')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr2">
              <a-input v-model="model.attr2" placeholder="请输入工作内容"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('安排人')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr3">
              <a-input v-model="model.attr3" placeholder="请输入安排人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('安排时间')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr4">
              <a-input v-model="model.attr4" placeholder="请输入安排时间"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('单号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr5">
              <a-input v-model="model.attr5" placeholder="请输入单号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="24">
            <a-form-model-item :label="$t('workClass')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="workClass">
              <a-input v-model="model.workClass" placeholder="请输入workClass"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('orgCodeName')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="orgCodeName">
              <a-input v-model="model.orgCodeName" placeholder="请输入orgCodeName"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('planDay')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="planDay">
              <a-input-number v-model="model.planDay" placeholder="请输入planDay" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item :label="$t('tenantId')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tenantId">
              <a-input v-model="model.tenantId" placeholder="请输入tenantId"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="24">
            <a-form-model-item :label="$t('pbType')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="pbType">
              <a-input v-model="model.pbType" placeholder="请输入pbType"  ></a-input>
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
    name: 'BaseScheduleInfoForm',
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
           freeTime: [
              { required: true, message: '请输入freeTime!'},
           ],
        },
        url: {
          add: "/sys/baseScheduleInfo/add",
          edit: "/sys/baseScheduleInfo/edit",
          queryById: "/sys/baseScheduleInfo/queryById"
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