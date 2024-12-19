<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('仓库代码')">
              <a-input v-model="queryParam.storeCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('仓库名称')">
              <a-input v-model="queryParam.storeName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
<!--              <a @click="handleToggleSearch" style="margin-left: 8px">-->
<!--                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}-->
<!--                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
<!--              </a>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'store:add'">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls($t('仓库管理'))" v-has="'store:export'">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" v-has="'store:import'" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel" v-has="'store:batchDelete'"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>
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
        class="j-table-force-nowrap"
        @change="handleTableChange"
        :rowClassName="setRowClsaa">

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
          <a @click="handleCheck(record)" v-has="'store:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'store:check'"/>
          <a @click="handleEdit(record)" v-has="'store:update'">{{$t('编辑')}}</a>

          <a-divider type="vertical" v-has="'store:update'"/>
          <a-popconfirm :title="$t('你确定吗?')" v-has="'store:delete'" @confirm="() => handleDelete(record.id)">
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
<!--          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>-->
        </span>

      </a-table>
    </div>

    <baStore-modal ref="modalForm" @ok="modalFormOk"></baStore-modal>
    <export-spin v-if="exportOk"></export-spin>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BaStoreModal from './modules/BaStoreModal'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'
  import ExportSpin from '@/components/ExportSpin/ExportSpin'

  export default {
    name: "BaStoreList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
    components: {
      BaStoreModal,
      ExportSpin
    },
    data () {
      return {
        description: 'ba_store管理页面',
        // 表头
        columns: [
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align: 'left',
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          {
            title:this.$t('仓库代码'),
            align: 'left',
            dataIndex: 'storeCode',
            sorter: true
          },
          {
            title:this.$t('仓库名称'),
            align: 'left',
            dataIndex: 'storeName',
            sorter: true
          },
          {
            title:this.$t('备注'),
            align: 'left',
            dataIndex: 'storeText',
            sorter: true
          },
          {
            title:this.$t('负责人'),
            align: 'left',
            dataIndex: 'directId',
            sorter: true
          },
          {
            title:this.$t('管理员'),
            align: 'left',
            dataIndex: 'responseId',
            sorter: true
          },
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            // fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }

        ],
        url: {
          list: "/jeewms/baStore/list",
          delete: "/jeewms/baStore/delete",
          deleteBatch: "/jeewms/baStore/deleteBatch",
          exportXlsUrl: "/jeewms/baStore/exportXls",
          importExcelUrl: "jeewms/baStore/importExcel",
        },
        dictOptions:{},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>