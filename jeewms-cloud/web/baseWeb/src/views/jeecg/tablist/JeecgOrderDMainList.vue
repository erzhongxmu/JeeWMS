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
              <a-select placeholder="请输入订单类型" v-model="queryParam.ctype">
                <a-select-option value="1">国内订单</a-select-option>
                <a-select-option value="2">国际订单</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="24">
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

      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> {{$t('批量操作')}}
          <a-icon type="down"/>
        </a-button>
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
        filterMultiple="filterMultiple"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type:type}"
        @change="handleTableChange"
        :customRow="clickThenCheck"
      >

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>
          <a-divider type="vertical"/>
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="货主信息" key="1">
        <Jeecg-Order-Customer-List ref="JeecgOrderCustomerList"></Jeecg-Order-Customer-List>
      </a-tab-pane>
      <a-tab-pane tab="机票信息" key="2" forceRender>
        <Jeecg-Order-Ticket-List ref="JeecgOrderTicketList"></Jeecg-Order-Ticket-List>
      </a-tab-pane>
    </a-tabs>

    <!-- 表单区域 -->
    <jeecgOrderDMain-modal ref="modalForm" @ok="modalFormOk"></jeecgOrderDMain-modal>

  </a-card>
</template>

<script>
  import JeecgOrderDMainModal from './form/JeecgOrderDMainModal'
  import JeecgOrderCustomerList from './JeecgOrderCustomerList'
  import JeecgOrderTicketList from './JeecgOrderTicketList'
  import {deleteAction} from '@/api/manage'
  import JeecgOrderCustomerModal from './form/JeecgOrderCustomerModal'
  import JeecgOrderTicketModal from './form/JeecgOrderTicketModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'

  export default {
    name: "JeecgOrderDMainList",
    mixins: [JeecgListMixin],
    components: {
      JeecgOrderTicketModal,
      JeecgOrderCustomerModal,
      JeecgOrderDMainModal,
      JeecgOrderCustomerList,
      JeecgOrderTicketList,
    },
    data() {
      return {
        description: '订单管理页面',
        /* 分页参数 */
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '20'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + ' ' + this.$t('共') + ' '  + total + ' ' + this.$t('项目')
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        // 表头
        columns: [{
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'left',
          customRender: function (t, r, index) {
            return parseInt(index) + 1;
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
            customRender: (text) => {
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
            scopedSlots: {customRender: 'action'},
          }],
        // 分页参数
        type: "radio",
        url: {
          list: "/test/order/orderList",
          delete: "/test/order/delete",
          deleteBatch: "/test/order/deleteBatch",
        },
      }
    },
    methods: {
      clickThenCheck(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange(record.id.split(","), [record]);
            }
          }
        };
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
        this.$refs.JeecgOrderCustomerList.getOrderMain(this.selectedRowKeys[0]);
        this.$refs.JeecgOrderTicketList.getOrderMain(this.selectedRowKeys[0]);
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.JeecgOrderCustomerList.queryParam.mainId = null;
        this.$refs.JeecgOrderTicketList.queryParam.mainId = null;
        this.$refs.JeecgOrderCustomerList.loadData();
        this.$refs.JeecgOrderTicketList.loadData();
        this.$refs.JeecgOrderCustomerList.selectedRowKeys = [];
        this.$refs.JeecgOrderCustomerList.selectionRows = [];
        this.$refs.JeecgOrderTicketList.selectedRowKeys = [];
        this.$refs.JeecgOrderTicketList.selectionRows = [];
      },

      handleDelete: function (id) {
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(this.$t('操作成功'));
            that.loadData();
            this.$refs.JeecgOrderCustomerList.loadData();
            this.$refs.JeecgOrderTicketList.loadData();
          } else {
            that.$message.warning(this.$t('操作失败'));
          }
        });
      },
      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.JeecgOrderCustomerList.queryParam.mainId = null;
        this.$refs.JeecgOrderTicketList.queryParam.mainId = null;
        this.$refs.JeecgOrderCustomerList.loadData();
        this.$refs.JeecgOrderTicketList.loadData();
        this.$refs.JeecgOrderCustomerList.selectedRowKeys = [];
        this.$refs.JeecgOrderCustomerList.selectionRows = [];
        this.$refs.JeecgOrderTicketList.selectedRowKeys = [];
        this.$refs.JeecgOrderTicketList.selectionRows = [];
        this.loadData();
      }
    }
  }
</script>
<style scoped>
  .ant-card-body .table-operator {
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }
</style>