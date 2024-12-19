<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :maskClosable="false"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }"
    @cancel="close"
  > 
  <a-spin :spinning="confirmLoading">
    <j-form-container>
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="mineObj" slot="detail" :rules="validatorRules">
        <a-row>
          <!-- <a-col :span="24">
            <a-form-model-item label="是否合格" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query02">
              <a-radio-group v-model="value" placeholder="请选择是否合格">
                <a-radio :value="'合格'">
                    合格
                </a-radio>
                <a-radio :value="'不合格'">
                    不合格
                </a-radio>
            </a-radio-group>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="24">
            <a-form-model-item label="上传凭证" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
                <j-upload v-model="mineObj.attr1" :trigger-change="true" fileType='image' :number='8'></j-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="Size" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text1">
              <!-- <a-input v-model="mineObj.text01" :placeholder="$t('请输入')" ></a-input> -->
               <a-textarea
                v-model="mineObj.text1"
                :placeholder="$t('请输入')"
                :auto-size="{ minRows: 1, maxRows: 5 }"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="Material" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text2">
              <!-- <a-input v-model="mineObj.text01" :placeholder="$t('请输入')" ></a-input> -->
               <a-textarea
                v-model="mineObj.text2"
                :placeholder="$t('请输入')"
                :auto-size="{ minRows: 1, maxRows: 5 }"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="Color" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text3">
              <!-- <a-input v-model="mineObj.text01" :placeholder="$t('请输入')" ></a-input> -->
               <a-textarea
                v-model="mineObj.text3"
                :placeholder="$t('请输入')"
                :auto-size="{ minRows: 1, maxRows: 5 }"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="LOGO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text4">
              <!-- <a-input v-model="mineObj.text01" :placeholder="$t('请输入')" ></a-input> -->
               <a-textarea
                v-model="mineObj.text4"
                :placeholder="$t('请输入')"
                :auto-size="{ minRows: 1, maxRows: 5 }"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="Others" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text5">
              <!-- <a-input v-model="mineObj.text01" :placeholder="$t('请输入')" ></a-input> -->
               <a-textarea
                v-model="mineObj.text5"
                :placeholder="$t('请输入')"
                :auto-size="{ minRows: 1, maxRows: 5 }"
              />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
  <template #footer>
    <a-button key="back" @click="handleOk(1)">任务继续</a-button>
    <a-button key="submit" @click="handleOk(2)">任务完成</a-button>
  </template>
  </j-modal>
</template>

<script>
import { FormTypes, getRefPromise, VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
import { getAction, httpAction, postAction } from '@/api/manage'

export default {
  name: 'BusiPoItemModal',
  components: {},
  data() {
    return {
      visible2:false,
      confirmLoading:false,
      id: null,
      value:'合格',
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
      title: '上传凭证',
      width: 600,
      formDisabled: false,
      visible: false,
      disableSubmit: false,
      id: '',
      mineObj: {},
      refKeys: ['busiPoItem2'],
      tableKeys: ['busiPoItem2'],
      activeKey: 'busiPoItem2',
      validatorRules: {
           query02:  [{required: true, message: ' '}],
           attr1:  [{required: true, message: ' '}],
           text1:  [{required: true, message: ' '}],
           text2:  [{required: true, message: ' '}],
           text3:  [{required: true, message: ' '}],
           text4:  [{required: true, message: ' '}],
           text5:  [{required: true, message: ' '}],
           text01:  [{required: true, message: ' '}]
      },
    }
  },
  methods: {
    getAllTable() {
      let values = this.tableKeys.map((key) => getRefPromise(this, key))
      return Promise.all(values)
    },
    open(e) {
      this.visible = true
      this.id = e
    },
    close() {
      this.visible = false
      this.mineObj = {}
      this.value = "合格"
    },
    handleOk(e) {
      console.log(this.mineObj);
      if(!this.mineObj.attr1) {
        return this.$message.error('请上传图片');
      }
      
      if(e == 1){
        this.value = "不合格"
      }else{
        this.value = "合格"
      }
      let formData = {
          id: this.id,
          ...this.mineObj,
          text01: this.mineObj.text1 + '_' + this.mineObj.text2 + '_' + this.mineObj.text3 + '_' + this.mineObj.text4 + '_' + this.mineObj.text5,
          query02:this.value,
          link01:'打样通知单',
          query30:e
      }
      this.confirmLoading = true
      postAction('/jeeerp/busiPo/scheduleSave', formData).then(res => {
          if(res.success) {
              this.$message.success(res.message)
              this.$emit("ok")
              this.close()
          } else {
              this.$message.warning(res.message)
          }
        this.confirmLoading = false
      })
      this.$emit('ok')
    },
  },
}
</script>

<style scoped>
</style>