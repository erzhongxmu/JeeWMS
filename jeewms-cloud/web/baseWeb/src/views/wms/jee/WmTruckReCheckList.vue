<template>
  <a-card :bordered="false">
    <a-row style="height:650px;">
      <a-col :span="8" style="padding:0 10px ;">
        <!--  -->
        <div v-show="orderNo" class="orderNo">运单号:{{orderNo}}</div>
        <a-form-model ref="form" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-row>
            <a-col :span="24">
              <a-form-model-item
                label="运单号"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                prop="customer"
              >
                <a-input @keyup.enter.native="input1Change" ref="input1" v-model="queryParam.id"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="24">
              <a-form-model-item
                label="商品编码"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                prop="customer"
              >
                <a-input @input="encodingInput()" ref="input2" v-model="queryParam.barCode"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="24">
              <a-form-model-item
                label="子运单数"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                prop="customer"
              >
                <a-input v-model="queryParam.childWaybillCount"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="24">
              <a-form-model-item
                label="包材编码"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                prop="customer"
              >
                <a-input ref="input3" v-model="queryParam.packingNo"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :span="24">
              <div class="orderNo">
                <a-button type="primary" @click="finishReview">完成复核</a-button>
                <a-button type="primary" style="margin-left:8px;" @click="afreshReview">重新复核</a-button>
              </div>
            </a-col>
          </a-row>
        </a-form-model>
      </a-col>
      <a-col :span="16" style="border-left:1px solid #eee;padding:0 20px ;height:100%;">
        <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource"
          :scroll="{ y: 500 }"
          :pagination="false"
          :loading="loading"
          class="j-table-force-nowrap"
          @change="handleTableChange"
        >
        <div slot='goodsQuaok' slot-scope="text,record" :style="{color:record.goodsQua != record.goodsQuaok?'#f00':''}">
          {{text}}
        </div>

        </a-table>
      </a-col>
    </a-row>
    <audio src="" id="eventAudio" v-show="false"></audio>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { debounce } from '@/utils/util.js'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { putAction, getAction } from '@/api/manage'
import WmTruckReCheckModal from './modules/WmTruckReCheckModal'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import audio from '@/assets/10986.mp3'
export default {
  name: 'WmToDownGoodsList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass, listCheckMixin],
  components: {
    JDictSelectTag,
    WmTruckReCheckModal,
    JSearchSelectTag
  },
  data() {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 - 8 }
      },
      dataSource: [],
      description: '下架明细管理页面',
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'left',
          customRender: function(t, r, index) {
            return parseInt(index) + 1
          }
        },
        {
          title: '商品名称',
          align: 'left',
          dataIndex: 'goodsName'
        },
        {
          title: '商品编码',
          align: 'left',
          dataIndex: 'barcode'
        },
        {
          title: '应收数量',
          align: 'left',
          dataIndex: 'goodsQua'
        },
        {
          title: '实收数量',
          align: 'left',
          dataIndex: 'goodsQuaok',
          scopedSlots: { customRender: 'goodsQuaok' }
        },
        {
          title: '箱码',
          align: 'left',
          dataIndex: 'cusCode_dictText'
        },
        {
          title: '件/箱',
          align: 'left',
          dataIndex: 'goodsQua1'
        }
      ],
      url: {
        list: '/jeewms/wmToDownGoods/datagridzzfh',
        delete: '/jeewms/wmToDownGoods/delete',
        deleteBatch: '/jeewms/wmToDownGoods/deleteBatch',
        exportXlsUrl: '/jeewms/wmToDownGoods/exportXls',
        importExcelUrl: 'jeewms/wmToDownGoods/importExcel',
        dofubatch: '/jeewms/wmToDownGoods/dofubatch'
      },
      dictOptions: {},
      //批量修改标记
      batchEditFlag: false,
      //批量修改时新值数据
      newColData: [],
      //批量修改时数据修改行的id集合
      newColIds: [],
      orderNo: ''
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  mounted() {
    this.$refs.input1.focus()
  },
  methods: {
    playAudio(){
      let buttonAudio = document.getElementById('eventAudio');
      buttonAudio.setAttribute('src',audio)
      buttonAudio.play()
    },
    finishReview(){ // 完成
      if(!this.queryParam.packingNo) return this.$message.warning('包材编码为空')
        getAction('/jeewms/wmToDownGoods/finishReview',{id:this.orderNo,packingNo: this.queryParam.packingNo,childWaybillCount:this.queryParam.childWaybillCount}).then(res=>{
          if(res.success){
            this.$message.success(this.$t('操作成功'))
            this.queryParam = {}
            this.orderNo = ''
            this.dataSource = []
            this.$refs.input1.focus()
          }else{
            this.$message.warning(this.$t('操作失败'))
          }
        })
    },
    afreshReview(){ // 重新
      getAction('/jeewms/wmToDownGoods/afreshReview',{id:this.queryParam.id}).then(res=>{
        if(res.success){
          this.$message.success(this.$t('操作成功'))
          this.dataSource = res.result
          this.$refs.input2.focus()
        }else{
          this.$message.warning(this.$t('操作失败'))
        }
      })
    },
    scan() {
      getAction('/jeewms/wmToDownGoods/scanCodeReview',{id:this.orderNo,barCode: this.queryParam.barCode}).then(res=>{
        if(res.success){
          this.$message.success(this.$t('操作成功'))
          this.dataSource = res.result
          this.queryParam.barCode=''
        }else{
          this.playAudio()
          this.$message.warning(this.$t('操作失败'))
          this.queryParam.barCode=''
        }
        this.next(this.dataSource)
      })
    },
    next(arr){
      let i = -1
      arr.map((item,index)=>{
        if(item.goodsQua != item.goodsQuaok){
          i = index
        }
      })
      if(i == -1){
          this.$refs.input3.focus()
      }
    },
    encodingInput(){
      debounce(this.scan, 1000)
    },
    input1Change() {
      // 运单号变化
      if (!this.queryParam.id) return
      getAction('/jeewms/wmToDownGoods/queryWmOmNoticeIByMainNo',{id:this.queryParam.id}).then(res=>{
        if(res.success){
          this.dataSource = res.result
          this.$refs.input2.focus()
          this.orderNo = this.queryParam.id
        }else{
          this.$message.warning(this.$t('操作失败'))
          this.orderNo = ''
          this.queryParam.id=''
        }
      })
    },
    initDictConfig() {},
    //可编辑行改变时调用
    handleBatchChange(newValue, id, col) {
      var newSingleData = {}
      if (this.newColIds.indexOf(id) != -1) {
        this.newColData.forEach((item, index) => {
          if (item.id == id) {
            this.newColData[index][col] = newValue
          }
        })
      } else {
        this.newColIds.push(id)
        newSingleData[col] = newValue
        newSingleData.id = id
        this.newColData.push(newSingleData)
      }
      console.log(this.newColData, this.newColIds)
    },
    chooseRecheck() {
      if (this.selectedRowKeys.length > 0) {
        this.batchEditFlag = true
      } else {
        this.$message.warning(this.$t('请选择一条记录!'))
      }
    },
    //批量保存
    chooseSave() {
      var that = this
      if (this.batchEditFlag) {
        putAction(that.url.editBatch, that.newColData).then(res => {
          if (res.success) {
            that.$message.success('批量修改成功')
            that.newColData = []
            that.newColIds = []
            that.loadData()
            that.batchEditFlag = false
          } else {
            that.$message.warning(this.$t('操作失败'))
          }
        })
      } else {
        this.$message.warning('无可保存内容!')
      }
    },
    //取消批量修改
    chooseCancel() {
      if (this.batchEditFlag) {
        this.newColData = []
        this.newColIds = []
        this.batchEditFlag = false
        this.selectedRowKeys = []
      } else {
        this.$message.warning('无修改内容可取消!')
      }
    },
    //一键复核
    batchRecheck() {
      if (this.selectedRowKeys.length < 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      var ids = this.selectedRowKeys
      putAction(this.url.dofubatch, ids).then(res => {
        if (res.success) {
          this.$message.success('批量复核成功')
          this.loadData()
        } else {
          this.$message.warning(this.$t('操作失败'))
        }
      })
    },
    loadData() {
      // 获取货品列表
      // if (!this.model.id) return
      // getAction(this.url.list, { id: this.model.id }).then(res => {
      //   if (res.success) {
      //     let data = res.result
      //     data.map((item, index) => {
      //       item.num = index
      //     })
      //     this.dataSource = data
      //   }
      // })
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
.j-table-force-nowrap /deep/ .ant-table td {
  padding: 5px 5px !important;
}
.j-table-force-nowrap /deep/ .ant-table-row {
  height: 35px !important;
  padding: 5px 5px !important;
}
.orderNo {
  color: #f00;
  width: 40%;
  margin: 0 auto;
}
</style>