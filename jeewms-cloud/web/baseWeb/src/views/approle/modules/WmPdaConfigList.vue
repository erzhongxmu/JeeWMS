<template>
  <j-modal
    title="配置字段"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    switchFullscreen
    @ok="handleOk"
    @cancel="close"
  >
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('pda配置表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
    </div>
    <!-- table区域-begin -->
    <div>
      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="false"
        :loading="loading"
        class="j-table-force-nowrap"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
    <wm-pda-config-modal ref="WmPdaConfigModal" @ok="getList"></wm-pda-config-modal>
  </j-modal>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmPdaConfigModal from './WmPdaConfigModal'
import { getAction } from '@/api/manage'
export default {
  name: 'WmPdaConfigList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    WmPdaConfigModal
  },
  data() {
    return {
      dataSource: [],
      visible: false,
      description: 'pda配置表管理页面',
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
          title: '字段Key',
          align: 'left',
          dataIndex: 'query01'
        },
        {
          title: '字段Key',
          align: 'left',
          dataIndex: 'query02'
        },
        {
          title: '字段文字',
          align: 'left',
          dataIndex: 'query03'
        },
        {
          title: '字段类型',
          align: 'left',
          dataIndex: 'query04'
        },
        {
          title: 'input类型',
          align: 'left',
          dataIndex: 'query09'
        },
        {
          title: '是否查询',
          align: 'left',
          dataIndex: 'query05'
        },
        {
          title: '排序',
          align: 'left',
          dataIndex: 'query06'
        },{
          title: '是否显示',
          align: 'left',
          dataIndex: 'query07'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'left',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/pda/wmPdaConfig/list',
        delete: '/pda/wmPdaConfig/delete',
        deleteBatch: '/pda/wmPdaConfig/deleteBatch',
        exportXlsUrl: '/pda/wmPdaConfig/exportXls',
        importExcelUrl: 'pda/wmPdaConfig/importExcel'
      },
      dictOptions: {},
      superFieldList: [],
      id: ''
    }
  },
  created() {},
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    handleAdd() {
      this.$refs.WmPdaConfigModal.add(this.id)
    },
    handleEdit(e) {
      this.$refs.WmPdaConfigModal.edit(e)
    },
    close() {
      this.visible = false
    },
    handleOk() {},
    onShow(e) {
      this.visible = true
      this.id = e.id
      this.getList()
    },
    getList() {
      getAction(this.url.list, { query01: this.id, pageNo: 1, pageSize: 1000 }).then(res => {
        res.result.records.sort((x,y) => {
						return x.query06 -y.query06;
					})
        this.dataSource = res.result.records
      })
    }
  }
  }
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>