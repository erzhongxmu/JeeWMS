<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item label="出货单号">
              <a-input placeholder="请输入最小值" class="query-group-cust" v-model="queryParam.omNoticeId_begin"></a-input>
              <span class="query-group-split-cust"></span>
              <a-input placeholder="请输入最大值" class="query-group-cust" v-model="queryParam.omNoticeId_end"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button  @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls('出货通知抬头')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type:'radio'}"
        :customRow="clickThenSelect"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

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

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="出货通知项目" key="1" >
        <WmOmNoticeIList :mainId="selectedMainId" />
      </a-tab-pane>
    </a-tabs>

    <wmOmNoticeH-modal ref="modalForm" @ok="modalFormOk"></wmOmNoticeH-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmOmNoticeHModal from './modules/WmOmNoticeHModal'
  import { getAction } from '@/api/manage'
  import WmOmNoticeIList from './WmOmNoticeIList'
  import '@/assets/less/TableExpand.less'

  export default {
    name: "WmOmNoticeHList",
    mixins:[JeecgListMixin],
    components: {
      WmOmNoticeIList,
      WmOmNoticeHModal
    },
    data () {
      return {
        description: '出货通知抬头管理页面',
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            scopedSlots: { customRender: 'action' },
          },
          {
            title:'创建人名称',
            align: 'left',
            dataIndex: 'createName'
          },
          {
            title:'创建人登录名称',
            align: 'left',
            dataIndex: 'createBy'
          },
          {
            title:'创建日期',
            align: 'left',
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'更新人名称',
            align: 'left',
            dataIndex: 'updateName'
          },
          {
            title:'更新人登录名称',
            align: 'left',
            dataIndex: 'updateBy'
          },
          {
            title:'更新日期',
            align: 'left',
            dataIndex: 'updateTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'所属部门',
            align: 'left',
            dataIndex: 'sysOrgCode'
          },
          {
            title:'所属公司',
            align: 'left',
            dataIndex: 'sysCompanyCode'
          },
          {
            title:'货主',
            align: 'left',
            dataIndex: 'cusCode'
          },
          {
            title:'要求交货时间',
            align: 'left',
            dataIndex: 'delvData',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'收货人',
            align: 'left',
            dataIndex: 'delvMember'
          },
          {
            title:'收货人电话',
            align: 'left',
            dataIndex: 'delvMobile'
          },
          {
            title:'收货人地址',
            align: 'left',
            dataIndex: 'delvAddr'
          },
          {
            title:'承运人',
            align: 'left',
            dataIndex: 'reMember'
          },
          {
            title:'承运人电话',
            align: 'left',
            dataIndex: 'reMobile'
          },
          {
            title:'承运人车号',
            align: 'left',
            dataIndex: 'reCarno'
          },
          {
            title:'发货月台',
            align: 'left',
            dataIndex: 'omPlatNo'
          },
          {
            title:'备注',
            align: 'left',
            dataIndex: 'omBeizhu'
          },
          {
            title:'状态',
            align: 'left',
            dataIndex: 'omSta'
          },
          {
            title:'出货单号',
            align: 'left',
            dataIndex: 'omNoticeId'
          },
          {
            title:'附件',
            align: 'left',
            dataIndex: 'fuJian'
          },
          {
            title:'readOnly',
            align: 'left',
            dataIndex: 'readOnly'
          },
          {
            title:'whereCon',
            align: 'left',
            dataIndex: 'whereCon'
          },
          {
            title:'订单类型',
            align: 'left',
            dataIndex: 'orderTypeCode'
          },
          {
            title:'三方货主',
            align: 'left',
            dataIndex: 'ocusCode'
          },
          {
            title:'三方货主名称',
            align: 'left',
            dataIndex: 'ocusName'
          },
          {
            title:'imCusCode',
            align: 'left',
            dataIndex: 'imCusCode'
          },
          {
            title:'printStatus',
            align: 'left',
            dataIndex: 'printStatus'
          },
          {
            title:'对接单据类型',
            align: 'left',
            dataIndex: 'piClass'
          },
          {
            title:'账套',
            align: 'left',
            dataIndex: 'piMaster'
          }

        ],
        url: {
          list: "/jeewms/wmOmNoticeH/list",
          delete: "/jeewms/wmOmNoticeH/delete",
          deleteBatch: "/jeewms/wmOmNoticeH/deleteBatch",
          exportXlsUrl: "/jeewms/wmOmNoticeH/exportXls",
          importExcelUrl: "jeewms/wmOmNoticeH/importExcel",
        },
        dictOptions:{
        },
        /* 分页参数 */
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '50'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + ' ' + this.$t('共') + ' '  + total + ' ' + this.$t('项目')
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        selectedMainId:''

      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      },
      clickThenSelect(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange(record.id.split(","), [record]);
            }
          }
        }
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.selectedMainId=''
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedMainId=selectionRows[0].omNoticeId;
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      loadData(arg) {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        this.onClearSelected()
        var params = this.getQueryParams();//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(this.$t('操作失败'))
          }
          this.loading = false;
        })
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>