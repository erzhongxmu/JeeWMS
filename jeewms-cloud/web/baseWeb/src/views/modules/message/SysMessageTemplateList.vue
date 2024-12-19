<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item :label="$t('模板CODE')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.templateCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item :label="$t('模板内容')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.templateContent"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item :label="$t('模板标题')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.templateName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item :label="$t('模板类型')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.templateType"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('消息模板')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
                @change="handleImportExcel">
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
             {{$t('删除')}}
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
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{
        selectedRowKeys.length }}</a>{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
          class="j-table-force-nowrap" 
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <!-- 字符串超长截取省略号显示-->
        <span slot="templateContent" slot-scope="text">
          <j-ellipsis :value="text" :length="25" />
        </span>


        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">{{$t('更多')}} <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item>
                  <a @click="handleTest(record)">{{$t('发送消息')}}</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <sysMessageTemplate-modal ref="modalForm" @ok="modalFormOk"></sysMessageTemplate-modal>

    <sysMessageTest-modal ref="testModal"></sysMessageTest-modal>
  </a-card>
</template>

<script>
  import SysMessageTemplateModal from './modules/SysMessageTemplateModal'
  import SysMessageTestModal from './modules/SysMessageTestModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import JEllipsis from "@/components/jeecg/JEllipsis";

  export default {
    name: "SysMessageTemplateList",
    mixins: [JeecgListMixin],
    components: {
      JEllipsis,
      SysMessageTemplateModal,
      SysMessageTestModal
    },
    data() {
      return {
        description: '消息模板管理页面',
        // 表头
        columns: [
          {
            title:'#',
            dataIndex: '',
            key: 'rowIndex',
            width: 60,
            align: 'left',
            customRender: function (t, r, index) {
              return parseInt(index) + 1;
            }
          },
          {
            title:this.$t('模板CODE') ,
            align: 'left',
            dataIndex: 'templateCode'
          },
          {
            title:this.$t('模板标题') ,
            align: 'left',
            dataIndex: 'templateName'
          },
          {
            title:this.$t('模板内容') ,
            align: 'left',
            dataIndex: 'templateContent',
            scopedSlots: {customRender: 'templateContent'},
          },
          {
            title:this.$t('模板类型') ,
            align: 'left',
            dataIndex: 'templateType',
            customRender: function (text) {
              if(text=='1') {
                return "短信";
              }
              if(text=='2') {
                return "邮件";
              }
              if(text=='3') {
                return "微信";
              }
              if(text=='4') {
                return "系统";
              }
            }
          },
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            scopedSlots: {customRender: 'action'},
          }
        ],
        url: {
          list: "/sys/message/sysMessageTemplate/list",
          delete: "/sys/message/sysMessageTemplate/delete",
          deleteBatch: "/sys/message/sysMessageTemplate/deleteBatch",
          exportXlsUrl: "sys/message/sysMessageTemplate/exportXls",
          importExcelUrl: "sys/message/sysMessageTemplate/importExcel",
        },
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      handleTest(record){
        this.$refs.testModal.open(record);
        this.$refs.testModal.title = "发送测试";
      }

    }
  }
</script>
<style lang="less" scoped>
  /** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }

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