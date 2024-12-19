<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="24">
            <a-form-item label="订单号">
              <a-input placeholder="请输入订单号" v-model="queryParam.orderCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="订单类型">
              <a-select placeholder="请输入订单类型"  v-model="queryParam.ctype">
                <a-select-option value="1">国内订单</a-select-option>
                <a-select-option value="2">国际订单</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="24" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('一对多示例')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>

      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
          class="j-table-force-nowrap" 
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <jeecgOrderMain-modal ref="modalForm" @ok="modalFormOk"></jeecgOrderMain-modal>
  </a-card>
</template>

<script>
  import JeecgOrderMainModal from './modules/JeecgOrderMainModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "JeecgOrderMainList",
    mixins: [JeecgListMixin],
    components: {
      JeecgOrderMainModal
    },
    data () {
      return {
        description: '订单管理页面',
        importExcelUrl:`${window._CONFIG['domianURL']}/test/baseOrderMain/importExcel`,
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align: 'left',
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '订单号',
            align: 'left',
            dataIndex: 'orderCode'
          },
          {
            title: '订单类型',
            align: 'left',
            dataIndex: 'ctype',
            customRender: (text, record, index) => {
              let re = "";
              if (text === '1') {
                re = "国内订单";
              } else if (text === '2') {
                re = "国际订单";
              }
              return re;
            }
          },
          {
            title: '订单日期',
            align: 'left',
            dataIndex: 'orderDate'
          },
          {
            title: '订单金额',
            align: 'left',
            dataIndex: 'orderMoney'
          },
          {
            title: '订单备注',
            align: 'left',
            dataIndex: 'content'
          },
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            scopedSlots: { customRender: 'action' },
          }
        ],

		url: {
          list: "/test/baseOrderMain/list",
          delete: "/test/baseOrderMain/delete",
          deleteBatch: "/test/baseOrderMain/deleteBatch",
          exportXlsUrl: "/test/baseOrderMain/exportXls",
        }
      }
    },
    methods: {
    }
  }
</script>
<style scoped>
  /** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }
  .ant-card-body .table-operator{
    margin-bottom: 18px;
  }
  .ant-table-tbody .ant-table-row td{
    padding-top:15px;
    padding-bottom:15px;
  }
  .anty-row-operator button{margin: 0 5px}
  .ant-btn-danger{background-color: #ffffff}

  .ant-modal-cust-warp{height: 100%}
  .ant-modal-cust-warp .ant-modal-body{height:calc(100% - 110px) !important;overflow-y: auto}
  .ant-modal-cust-warp .ant-modal-content{height:90% !important;overflow-y: hidden}
</style>