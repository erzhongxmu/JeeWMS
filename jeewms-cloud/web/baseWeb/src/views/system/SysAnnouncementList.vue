<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :span="6">
            <a-form-item label="标题">
              <a-input placeholder="请输入标题" v-model="queryParam.titile"></a-input>
            </a-form-item>
          </a-col>
          <!--<a-col :span="6">
            <a-form-item label="内容">
              <a-input placeholder="请输入内容" v-model="queryParam.msgContent"></a-input>
            </a-form-item>
          </a-col>-->

          <a-col :span="8">
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
      <a-button type="primary" icon="download" @click="handleExportXls('系统通告')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
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
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a  v-if="record.sendStatus == 0" @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" v-if="record.sendStatus == 0"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item v-if="record.sendStatus != 1">
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item v-if="record.sendStatus == 0">
                <a-popconfirm title="确定发布吗?" @confirm="() => releaseData(record.id)">
                  <a>发布</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item v-if="record.sendStatus == 1">
                <a-popconfirm title="确定撤销吗?" @confirm="() => reovkeData(record.id)">
                  <a>撤销</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item>
                  <a @click="handleDetail(record)">{{$t('查看')}}</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <sysAnnouncement-modal ref="modalForm" @ok="modalFormOk"></sysAnnouncement-modal>
  </a-card>
</template>

<script>
  import SysAnnouncementModal from './modules/SysAnnouncementModal'
  import {doReleaseData, doReovkeData} from '@/api/api'
  import {getAction} from '@/api/manage'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'

  export default {
    name: "SysAnnouncementList",
    mixins: [JeecgListMixin],
    components: {
      SysAnnouncementModal
    },
    data() {
      return {
        description: '系统通告表管理页面',
        // 查询条件
        queryParam: {},
        // 表头
        columns: [
          {
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
            title: '标题',
            align: 'left',
            dataIndex: 'titile'
          },
          {
            title: '消息类型',
            align: 'left',
            dataIndex: 'msgCategory',
            customRender: function (text) {
              if (text == '1') {
                return "通知公告";
              } else if (text == "2") {
                return "系统消息";
              } else {
                return text;
              }
            }
          },
          /*{
            title: '开始时间',
            align: 'left',
            dataIndex: 'startTime'
          },
          {
            title: '结束时间',
            align: 'left',
            dataIndex: 'endTime'
          },*/
          {
            title: '发布人',
            align: 'left',
            dataIndex: 'sender'
          },
          {
            title: '优先级',
            align: 'left',
            dataIndex: 'priority',
            customRender: function (text) {
              if (text == 'L') {
                return "低";
              } else if (text == "M") {
                return "中";
              } else if (text == "H") {
                return "高";
              } else {
                return text;
              }
            }
          },
          {
            title: '通告对象',
            align: 'left',
            dataIndex: 'msgType',
            customRender: function (text) {
              if (text == 'USER') {
                return "指定用户";
              } else if (text == "ALL") {
                return "全体用户";
              } else {
                return text;
              }
            }
          },
          {
            title: '发布状态',
            align: 'left',
            dataIndex: 'sendStatus',
            customRender: function (text) {
              if (text == 0) {
                return "未发布";
              } else if (text == 1) {
                return "已发布";
              } else if (text == 2) {
                return "已撤销";
              } else {
                return text;
              }
            }
          },
          {
            title: '发布时间',
            align: 'left',
            dataIndex: 'sendTime'
          },
          {
            title: '撤销时间',
            align: 'left',
            dataIndex: 'cancelTime'
          },
          /*{
                title: '删除状态（0，正常，1已删除）',
                align: 'left',
                dataIndex: 'delFlag'
              },*/
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            scopedSlots: {customRender: 'action'},
          }
        ],
        url: {
          list: "/sys/annountCement/list",
          delete: "/sys/annountCement/delete",
          deleteBatch: "/sys/annountCement/deleteBatch",
          releaseDataUrl: "/sys/annountCement/doReleaseData",
          reovkeDataUrl: "sys/annountCement/doReovkeData",
          exportXlsUrl: "sys/annountCement/exportXls",
          importExcelUrl: "sys/annountCement/importExcel",
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      //执行发布操作
      releaseData: function (id) {
        console.log(id);
        var that = this;
        doReleaseData({id: id}).then((res) => {
          if (res.success) {
            that.$message.success(this.$t('操作成功'));
            that.loadData(1);
          } else {
            that.$message.warning(this.$t('操作失败'));
          }
        });
      },
      //执行撤销操作
      reovkeData: function (id) {
        var that = this;
        doReovkeData({id: id}).then((res) => {
          if (res.success) {
            that.$message.success(this.$t('操作成功'));
            that.loadData(1);
            this.syncHeadNotic(id)
          } else {
            that.$message.warning(this.$t('操作失败'));
          }
        });
      },
      syncHeadNotic(anntId){
        getAction("sys/annountCement/syncNotic",{anntId:anntId})
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>