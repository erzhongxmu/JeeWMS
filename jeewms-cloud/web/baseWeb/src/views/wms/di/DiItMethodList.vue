<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('集成编号')">
              <a-input :placeholder="$t('请输入集成编号')" v-model="queryParam.diItNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('集成名称')">
              <a-input :placeholder="$t('请输入集成名称')" v-model="queryParam.diItName"></a-input>
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
              <!-- <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? this.$t('收起') : this.$t('展开' )}}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('新增')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('数据集成')">{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query
        :fieldList="superFieldList"
        ref="superQueryModal"
        @handleSuperQuery="handleSuperQuery"
      ></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete" />{{$t('删除')}}
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">
          {{$t('批量操作')}}
          <a-icon type="down" />
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项')}}
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
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <!-- <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">{{$t('详情')}}</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>-->
          <a @click="handleDetail(record)">{{$t('详情')}}</a>
          <a-divider type="vertical" />
          <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>

    <di-it-method-modal ref="modalForm" @ok="modalFormOk"></di-it-method-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import DiItMethodModal from './modules/DiItMethodModal'

export default {
  name: 'DiItMethodList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    DiItMethodModal
  },
  data() {
    return {
      description: '数据集成管理页面',
      // 表头
      columns: [
        // {
        //   title: '#',
        //   dataIndex: '',
        //   key:'rowIndex',
        //   width:60,
        //   align:"center",
        //   customRender:function (t,r,index) {
        //     return parseInt(index)+1;
        //   }
        // },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'center',
          fixed: 'left',
          width: 147,
          scopedSlots: { customRender: 'action' }
        },
        {
          title: this.$t('集成编号'),
          align: 'center',
          dataIndex: 'diItNo'
        },
        {
          title: this.$t('集成名称'),
          align: 'center',
          dataIndex: 'diItName'
        },
        {
          title: this.$t('采集规则编号'),
          align: 'center',
          dataIndex: 'diItRuleNo'
        },
        {
          title: this.$t('采集规则名称'),
          align: 'center',
          dataIndex: 'diItRuleName'
        },
        {
          title: this.$t('源类型'),
          align: 'center',
          dataIndex: 'diDsType'
        },
        {
          title: this.$t('源数据源'),
          align: 'center',
          dataIndex: 'diDatasource'
        },
        {
          title: this.$t('源状态'),
          align: 'center',
          dataIndex: 'diSstatus'
        },
        {
          title: this.$t('源数据'),
          align: 'center',
          dataIndex: 'diSdata'
        },
        {
          title: this.$t('源API'),
          align: 'center',
          dataIndex: 'diSapi'
        },
        {
          title: this.$t('源SQL'),
          align: 'center',
          dataIndex: 'diSsql'
        },
        {
          title: this.$t('目的类型'),
          align: 'center',
          dataIndex: 'diDdsType'
        },
        {
          title: this.$t('目的数据源'),
          align: 'center',
          dataIndex: 'diDdatasource'
        },
        {
          title: this.$t('目的状态'),
          align: 'center',
          dataIndex: 'diDstatus'
        },
        {
          title: this.$t('目的数据'),
          align: 'center',
          dataIndex: 'diDdata'
        },
        {
          title: this.$t('目的API'),
          align: 'center',
          dataIndex: 'diDapi'
        },
        {
          title: this.$t('目的SQL'),
          align: 'center',
          dataIndex: 'diDsql'
        },
        {
          title: this.$t('备注'),
          align: 'center',
          dataIndex: 'remark'
        }
      ],
      url: {
        list: '/di/diItMethod/list',
        delete: '/di/diItMethod/delete',
        deleteBatch: '/di/diItMethod/deleteBatch',
        exportXlsUrl: '/di/diItMethod/exportXls',
        importExcelUrl: 'di/diItMethod/importExcel'
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
      fieldList.push({ type: 'BigDecimal', value: 'diItNo', text: '集成编号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'diItName', text: '集成名称', dictCode: '' })
      fieldList.push({
        type: 'popup',
        value: 'diItRuleNo',
        text: '采集规则编号',
        popup: { code: 'di_it_rule', field: 'di_it_rule_no', orgFields: 'di_it_rule_no', destFields: 'diItRuleNo' }
      })
      fieldList.push({ type: 'Text', value: 'diItRuleName', text: '采集规则名称', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'diDsType', text: '源类型', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'diDatasource', text: '源数据源', dictCode: '' })
      fieldList.push({ type: 'string', value: 'diSstatus', text: '源状态', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'diSdata', text: '源数据', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'diSapi', text: '源API', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'diSsql', text: '源SQL', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'diDdsType', text: '目的类型', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'diDdatasource', text: '目的数据源', dictCode: '' })
      fieldList.push({ type: 'string', value: 'diDstatus', text: '目的状态', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'diDdata', text: '目的数据', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'diDapi', text: '目的API', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'diDsql', text: '目的SQL', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'remark', text: '备注', dictCode: '' })
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>