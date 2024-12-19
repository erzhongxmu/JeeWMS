<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('logo编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.logoCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('logo名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.logoName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'WmsMdgoodColorList:add'">{{$t('新增')}}</a-button>
      <a-button
        type="primary"
        icon="download"
        @click="handleExportXls('LOGO 维护基础配置')"
        v-has="'WmsMdgoodColorList:export'"
      >{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
        v-has="'WmsMdgoodColorList:import'"
      >
        <a-button type="primary" icon="import" >{{$t('导入')}}</a-button>
      </a-upload>
      <a-button  key="1" @click="batchDel" v-has="'WmsMdgoodColorList:batchDelete'"> <a-icon type="delete"/> {{$t('批量删除')}}</a-button>
     
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt
            style="max-width:80px;font-size: 12px;font-style: italic;"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)"
          >{{$t('下载')}}</a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-has="'WmsLogoConfigList:edit'">{{$t('编辑')}}</a>

          <a-divider type="vertical" v-has="'WmsLogoConfigList:Delete'"/>
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
            <a v-has="'WmsLogoConfigList:Delete'">{{$t('删除')}}</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>

    <wms-logo-config-modal ref="modalForm" @ok="modalFormOk"></wms-logo-config-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmsLogoConfigModal from './modules/WmsLogoConfigModal'

export default {
  name: 'WmsLogoConfigList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    WmsLogoConfigModal
  },
  data() {
    return {
      description: 'LOGO 维护基础配置管理页面',
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
          title: this.$t('logo编码'),
          align: 'left',
          dataIndex: 'logoCode',
          sorter: true
        },
        {
          title: this.$t('logo名称'),
          align: 'left',
          dataIndex: 'logoName',
          sorter: true
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/jeewms/wmsLogoConfig/list',
        delete: '/jeewms/wmsLogoConfig/delete',
        deleteBatch: '/jeewms/wmsLogoConfig/deleteBatch',
        exportXlsUrl: '/jeewms/wmsLogoConfig/exportXls',
        importExcelUrl: 'jeewms/wmsLogoConfig/importExcel'
      },
      dictOptions: {},
      superFieldList: []
    }
  },
  created() {
    this.getSuperFieldList()
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    initDictConfig() {},
    getSuperFieldList() {
      let fieldList = []
      fieldList.push({ type: 'string', value: 'logoCode', text: 'logo编码', dictCode: '' })
      fieldList.push({ type: 'string', value: 'logoName', text: 'logo名称', dictCode: '' })
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>