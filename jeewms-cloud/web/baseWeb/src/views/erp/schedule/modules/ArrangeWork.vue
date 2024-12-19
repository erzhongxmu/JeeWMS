<template>
  <j-modal
    title="排班信息"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel"
    cancelText="关闭">
     <j-form-container>
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query01">
              <j-dict-select-tag
                type="radio"
                v-model="model.query01"
                dictCode="WorkType"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="24" v-if="model.query01 == 1">
            <a-form-model-item label="地点" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query02">
              <a-input v-model="model.query02" placeholder="请输入地点"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24" v-if="model.query01 == 1">
            <a-form-model-item label="工作内容" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="model.query03" placeholder="请输入工作内容"></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </j-modal>
</template>

<script>
  import { getAction, postAction } from '@/api/manage'
  export default {
    name: 'ArrangeWork',
    components: { },
    data () {
      return {
        title:'',
        width:500,
        visible: false,
        disableSubmit: false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        validatorRules:{
          query01: [{ required: true, message: ' ' }],
          query02: [{ required: true, message: ' ' }],
          query03: [{ required: true, message: ' ' }],
        },
        selectedArr:[],
        model:{
          query01:"1",
        },
        url: {
          editSchedInfoList: '/sys/baseScheduleInfo/editSchedInfoList', // 编辑排班
        },
      }
    },
    methods: {
      open(selectedArr){
        this.selectedArr = selectedArr
        this.visible = true
      },
      handleOk () {
        let that = this
        this.$refs.form.validate(valid => {
          if (valid) {
            if(!this.model.query01) return  that.$message.error("请选择排班类型")
            if(this.model.query01 == 1){
              if(!this.model.query02) return  that.$message.error("请输入地点")
              if(!this.model.query03) return  that.$message.error("请输入工作内容")
            }
            this.selectedArr.map((item)=>{
              item.pbType = this.model.query01
              item.attr1 = this.model.query02
              item.attr2 = this.model.query03
            })
             postAction(that.url.editSchedInfoList, this.selectedArr).then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
                that.handleCancel()
              } else {
                that.$message.error(res.message)
              }
          })
          }
        })
      },
      handleCancel () {
        this.visible = false
        this.selectedArr = []
        this.model = {
          query01:"1",
        }
      }
    }
  }
</script>