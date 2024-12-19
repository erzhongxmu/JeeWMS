<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('库区编码')">
              <a-input v-model="queryParam.areaCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('库区名称')">
              <a-input v-model="queryParam.areaName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('仓库')">
              <j-dict-select-tag
                v-model="queryParam.wareCode"
                dictCode="ba_store,store_name,store_code"
              />
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
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'storeArea:add'">{{$t('添加')}}</a-button>
      <a-button
        type="primary"
        icon="download"
        @click="handleExportXls('库区管理')"
        v-has="'storeArea:export'"
      >{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        v-has="'storeArea:import'"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel" v-has="'storeArea:batchDelete'">
        <a-icon type="delete" />
        {{$t('批量删除')}}
      </a-button>
      <!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
      <!--        <a-menu slot="overlay">-->
      <!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
      <!--        </a-menu>-->
      <!--        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>-->
      <!--      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>
        {{$t('项目')}}
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
        :rowClassName="setRowClsaa"
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
            @click="uploadFile(text)"
          >下载</a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <!--          <a @click="handleCheck(record)">{{$t('查看')}}</a>-->
          <!--          <a-divider type="vertical"/>-->
          <a @click="handleEdit(record)" v-has="'storeArea:update'">{{$t('编辑')}}</a>

          <a-divider type="vertical" v-has="'storeArea:update'" />
          <a-popconfirm
            :title="$t('你确定吗?')"
            v-has="'storeArea:delete'"
            @confirm="() => handleDelete(record.id)"
          >
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
          <!--          <a-dropdown>-->
          <!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
          <!--            <a-menu slot="overlay">-->
          <!--              <a-menu-item>-->
          <!--                -->
          <!--              </a-menu-item>-->
          <!--            </a-menu>-->
          <!--          </a-dropdown>-->
        </span>
      </a-table>
    </div>

    <baStoreArea-modal ref="modalForm" @ok="modalFormOk"></baStoreArea-modal>
    <export-spin v-if="exportOk"></export-spin>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BaStoreAreaModal from './modules/BaStoreAreaModal'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import ExportSpin from '@/components/ExportSpin/ExportSpin'

export default {
  name: 'BaStoreAreaList',
  mixins: [JeecgListMixin, mixinDevice, listCheckMixin, commonTableRowClass],
  components: {
    JDictSelectTag,
    BaStoreAreaModal,
    ExportSpin
  },
  data() {
    return {
      description: '库区管理页面',
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
          title: this.$t('库区编码'),
          align: 'left',
          dataIndex: 'areaCode',
            sorter: true
        },
        {
          title: this.$t('库区名称'),
          align: 'left',
          dataIndex: 'areaName',
            sorter: true
        },
        {
          title: this.$t('库区名称英文'),
          align: 'left',
          dataIndex: 'areaCodeEn',
            sorter: true
        },
        // {
        //   title: this.$t('良次品'),
        //   align: 'left',
        //   dataIndex: 'areaStatus'
        // },
        {
          title: this.$t('仓库'),
          align: 'left',
          dataIndex: 'wareCode_dictText',
          sorter: true
        },
        {
          title: this.$t('备注'),
          align: 'left',
          dataIndex: 'remark',
          sorter: true
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          // fixed:"right",
          width: 100,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/jeewms/baStoreArea/list',
        delete: '/jeewms/baStoreArea/delete',
        deleteBatch: '/jeewms/baStoreArea/deleteBatch',
        exportXlsUrl: '/jeewms/baStoreArea/exportXls',
        importExcelUrl: 'jeewms/baStoreArea/importExcel'
      },
      dictOptions: {}
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    initDictConfig() {}
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>