<template>
  <j-modal
    :maskClosable="false"
    :title="title"
    :width="600"
    :visible="visible"
    :disabled="true"
    :switchFullscreen="true"
    @ok="handleOk"
    okText="确定"
    @cancel="handleCancel">
    <div class="table-page-search-wrapper" >
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :xl="10" :lg="7" :md="8" :sm="24">
            <a-form-item label="收款单号">
              <a-input placeholder="请输入收款单号" v-model="payNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="10" :lg="7" :md="8" :sm="24">
            <a-form-item label="备注">
              <a-input placeholder="请输入备注" v-model="remark"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="10" :lg="7" :md="8" :sm="24">
            <a-form-item label="付款方式">
              <j-dict-select-tag  v-model="payment" dictCode="payment_ways" placeholder="请选择"></j-dict-select-tag>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </div>
  </j-modal>
</template>

<script>

import { validateDuplicateValue } from '@/utils/util'
import JDate from '@/components/jeecg/JDate'
import { getAction} from "@/api/manage"

export default {
  name: "ReconciledRecordModal",
  components: {
    JDate,
  },
  data () {
    return {
      form: this.$form.createForm(this),
      title:"收款",
      width:1080,
      visible: false,
      model: {},
      confirmLoading: false,
      payNo:'',
      remark:'',
      payment:'',
      ids:'',
      url: {
        payBatch: '/bms/bmsInvoice/payBatch',
      },
    }
  },
  created () {
  },
  methods: {
       list(ids1){
         this.visible = true;
        //  console.log(ids1,'0000')
         this.ids = ids1.join(',')
         console.log(this.ids,'00900')
        },
    close () {
      this.$emit('close');
      this.visible = false;
      this.payNo = '',
      this.remark = '',
      this.payment = ''
    },
    handleCancel () {
      this.close()
    },
    handleOk () {
      if(!this.payNo){
             this.$message.warn('请输入收款单号')
             return
       }
      var that = this;
          that.$confirm({
          title:"确认操作",
          content:"确定生成收款记录?",
          onOk: function(){
            getAction(`/bms/bmsInvoice/payBatch`,{
            ids:that.ids,
            payNo:that.payNo,
            remark:that.remark
          }).then(res =>{
            if(res.success){
              that.visible = false
              that.payNo = '',
              that.remark = ''
              this.close()
              return that.$message.success('收款记录已生成')
            }else{
              that.$message.error(res.message)
            }
          })
          }
        });
    },
  }
}
</script>
<style  lang="less" scoped>
.print-button{
  margin-left: 1090px;
  margin-bottom: 20px;
  color: #fff;
}
.ant-card1{
  padding: 0;
}
.ant-alert1{
  color: rgba(0, 0, 0, 0.65);
  border-radius: 4px;
  width:1150px;
  padding: 4px 15px 4px 37px;
}
.ant-alert-info1 {
  border: 1px solid color(~`colorPalette("@{primary-color}", 3)`);
  background-color: color(~`colorPalette("@{primary-color}", 1)`);
}
::v-deep {
  .ant-card-body {
  //  background-color: red;
   padding: 20px 0;
  }
}
.print-ul {
        display: none;
	    width: 100%;
	    list-style: none;
	    border: 1px solid #e8e8e8;
    }
</style>
