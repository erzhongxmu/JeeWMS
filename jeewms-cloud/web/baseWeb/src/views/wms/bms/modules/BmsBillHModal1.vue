<template>
  <j-modal
    :title="title"
    :width="780"
    :visible="visible"
    :maskClosable="false"
    :disabled="true"
    :switchFullscreen="true"
    @ok="handleOk"
    okText="确定"
    @cancel="handleCancel">
    <div class="table-page-search-wrapper" >
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :xl="10" :lg="7" :md="15" :sm="24">
            <a-form-item label="发票号" >
              <a-input placeholder="请输入发票号" v-model="invoiceNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col  :xl="10" :lg="7" :md="15" :sm="24">
            <a-form-item label="备注">
              <a-input placeholder="请输入备注" v-model="remark"></a-input>
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
import { getAction,postAction} from "@/api/manage"

export default {
  name: "ReconciledRecordModal",
  components: {
    JDate,
  },
  data () {
    return {
      form: this.$form.createForm(this),
      title:"开票",
      width:1080,
      visible: false,
      model: {},
      confirmLoading: false,
      invoiceNo:'',
      remark:'',
      ids:'',
      url: {
        kaipiaoBatch: '/bms/bmsBillH/kaipiaoBatch',
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
      this.invoiceNo = '',
      this.remark = ''
    },
    handleCancel () {
      this.close()
    },
    handleOk () {
      if(!this.invoiceNo){
             this.$message.warn('请输入发票号')
             return
       }
      console.log(this.invoiceNo,'发票号')
      var that = this;
          that.$confirm({
          title:"确认操作",
          content:"确定生成开票记录?",
          onOk: function(){
            getAction(`/bms/bmsBillH/kaipiaoBatch`,{
            ids:that.ids,
            invoiceNo:that.invoiceNo,
            remark:that.remark
          }).then(res =>{
            if(res.success){
              that.visible = false
              that.invoiceNo = '',
              that.remark = ''
              this.close()
              return that.$message.success('开票记录已生成')
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
