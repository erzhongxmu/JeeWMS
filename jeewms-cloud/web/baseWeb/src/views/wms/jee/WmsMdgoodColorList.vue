<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('颜色编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.colorCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('颜色')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.colorName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('颜色英文')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.colorNameEn"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button
        @click="handleAdd"
        type="primary"
        icon="plus"
        v-has="'WmsLogoConfigList:add'"
      >{{$t('新增')}}</a-button>
      <a-button
        type="primary"
        icon="download"
        @click="handleExportXls($t('颜色配置'))"
        v-has="'WmsLogoConfigList:export'"
      >{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
        v-has="'WmsLogoConfigList:import'"
      >
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel" v-has="'WmsLogoConfigList:batchDelete'">
        <a-icon type="delete" />
        {{$t('批量删除')}}
      </a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
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
          <a-divider type="vertical" v-has="'WmsLogoConfigList:Delete'" />
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
            <a v-has="'WmsLogoConfigList:Delete'">{{$t('删除')}}</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>

    <wms-mdgood-color-modal ref="modalForm" @ok="modalFormOk"></wms-mdgood-color-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmsMdgoodColorModal from './modules/WmsMdgoodColorModal'

export default {
  name: 'WmsLogoConfigList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    WmsMdgoodColorModal
  },
  data() {
    return {
      description: '物料颜色管理页面',
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
          title: this.$t('颜色编码'),
          align: 'left',
          dataIndex: 'colorCode',
          sorter: true
        },
        {
          title: this.$t('颜色'),
          align: 'left',
          dataIndex: 'colorName',
          sorter: true
        },
        {
          title: this.$t('颜色英文'),
          align: 'left',
          dataIndex: 'colorNameEn',
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
        list: '/jeewms/wmsMdgoodColor/list',
        delete: '/jeewms/wmsMdgoodColor/delete',
        deleteBatch: '/jeewms/wmsMdgoodColor/deleteBatch',
        exportXlsUrl: '/jeewms/wmsMdgoodColor/exportXls',
        importExcelUrl: 'jeewms/wmsMdgoodColor/importExcel'
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
      fieldList.push({ type: 'string', value: 'colorCode', text: '颜色编码', dictCode: '' })
      fieldList.push({ type: 'string', value: 'colorName', text: '颜色名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'colorNameEn', text: '颜色英文名称', dictCode: '' })
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>