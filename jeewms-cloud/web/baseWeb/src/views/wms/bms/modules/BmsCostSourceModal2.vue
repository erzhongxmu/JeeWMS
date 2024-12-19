<template>
  <j-modal
    :maskClosable="false"
    :title="title"
    :width="1080"
    :visible="visible"
    :disabled="true"
    :switchFullscreen="true"
    :footer="null"
    @ok="handleOk"
    @cancel="handleCancel">
    <div class="table-page-search-wrapper" >
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="合同编号">
              <a-input placeholder="请输入合同编号" v-model="queryParam.contNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="规则编号">
              <a-input placeholder="请输入规则编号" v-model="queryParam.costRuleNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" icon="search" @click="searchQuery">{{$t('查询')}}</a-button>
              <a-button type="primary" icon="reload" @click="searchReset" style="margin-left: 8px">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <a-button type="primary" class="print-button" @click="calculate">计算</a-button>
    <!-- <print-multi-modal ref="printForm" @ok="modalFormOk" /> -->

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="index"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :expandedRowKeys="expandedRowKeys"
        @expand="handleExpand"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type:'radio'}"
        class="j-table-force-nowrap"
        @change="handleTableChange">
    </a-table>
  </j-modal>
</template>

<script>

import JDate from '@/components/jeecg/JDate'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { getAction,postAction} from "@/api/manage"

export default {
  name: "ReconciledRecordModal",
  components: {
    JDate,
  },
  mixins: [JeecgListMixin],
  props:{
    mainId:{
      required:false,
      default:''
    }
  },
  data () {
    return {
      form: this.$form.createForm(this),
      title:"计费规则",
      width:1080,
      visible: false,
      model: {},
      isAddPoint:false,
      selectObj:{},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
       columns: [
         {
          title: '合同编号',
          align: 'center',
          dataIndex: 'contNo'
        },
        {
          title: '合同名称',
          align: 'center',
          dataIndex: 'contName'
        },
        {
          title: '规则编号',
          align: 'center',
          dataIndex: 'costRuleNo'
        },
        {
          title: '规则名称',
          align: 'center',
          dataIndex: 'costRuleName'
        },
         {
          title: '费用编号',
          align: 'center',
          dataIndex: 'costNo'
        },
        {
          title: '费用名称',
          align: 'center',
          dataIndex: 'costName'
        },
        {
          title: '来源类型编号',
          align: 'center',
          dataIndex: 'costSNo'
        },
        {
          title: '来源类型名称',
          align: 'center',
          dataIndex: 'costSName'
        },
         {
          title: '计费对象类型',
          align: 'center',
          dataIndex: 'costObjType'
        },
        {
          title: '计费对象编号',
          align: 'center',
          dataIndex: 'costObjNo'
        },
        {
          title: '计费对象名称',
          align: 'center',
          dataIndex: 'costObjName'
        },
         {
          title: '最低收费',
          align: 'center',
          dataIndex: 'costMin'
        },
        {
          title: '最高收费',
          align: 'center',
          dataIndex: 'costMax'
        },
         {
          title: '计费周期',
          align: 'center',
          dataIndex: 'costPeriod'
        },
         {
          title: '有效开始时间',
          align: 'center',
          dataIndex: 'costValidBegin'
        },
        {
          title: '有效结束时间',
          align: 'center',
          dataIndex: 'costValidEnd'
        },
      ],
      expandedRowKeys: [],
      dictOptions:{},
      url: {
        list: '/bms/bmsCostRuleH/list',
      },
      /* 排序参数 */
      isorter:{
        column: 'createTime,id',
        order: 'desc,desc',
      },
    }
  },
  created () {
  },

  methods: {
    handleExpand(expanded, record) {
        this.expandedRowKeys = []
        if (expanded === true) {
          this.expandedRowKeys.push(record.id)
        }
      },
    list(){
      this.loadData();
      this.visible = true;
        },
        onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
        console.log(this.selectedRowKeys,this.selectionRows,'我是数据')
        },
        calculate(){
            if(!this.selectionRows || this.selectionRows.length == 0){
            this.$message.warn('请至少选择一条数据')
            return
          }
          var that = this;
          that.$confirm({
          title:"确认操作",
          content:"是否计算?",
          onOk: function(){
            getAction(`/bms/api/run`,{
            ruleno:that.selectionRows[0].costRuleNo
          }).then(res =>{
            if(res.success){
              that.selectedRowKeys = []
              that.selectionRows = [];
              that.visible = false
              return that.$message.success('计算完成')
            }else{
              that.$message.error(res.message)
            }
          })
          }
        });

        },
      handleTableChange(pagination, filters, sorter) {
      //分页、排序、筛选变化时触发
      //TODO 筛选
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field;
        this.isorter.order = "ascend" == sorter.order ? "asc" : "desc"
      }
      this.ipagination = pagination;
      this.loadData();
      this.selectedRowKeys = []
      this.selectionRows = [];
    },
    close () {
      this.$emit('close');
      this.selectedRowKeys = []
      this.loadData(1)
      this.visible = false;
      this.queryParam = {}
    },
    handleCancel () {
      this.close()
    },
    handleOk () {
      this.close()
    },
  }
}
</script>
<style  lang="less" scoped>
.print-button{
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
