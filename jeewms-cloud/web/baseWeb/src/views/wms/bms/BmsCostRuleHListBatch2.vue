<template>
  <a-card class="j-inner-table-wrapper" :bordered="false">
    <!-- 查询区域 begin -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('合同编号')">
              <a-input v-model="queryParam.contNo" :placeholder="$t('请输入合同编号')" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('合同名称')">
              <a-input v-model="queryParam.contName" :placeholder="$t('请输入合同名称')" />
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('规则编号')">
              <a-input v-model="queryParam.costRuleNo" :placeholder="$t('请输入规则编号')" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('规则名称')">
              <a-input v-model="queryParam.costRuleName" :placeholder="$t('请输入规则名称')" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('计费对象编号')">
              <a-input v-model="queryParam.costObjNo" :placeholder="$t('请输入规则名称')" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('计费对象名称')">
              <a-input v-model="queryParam.costObjName" :placeholder="$t('请输入规则名称')" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span class="table-page-search-submitButtons table-operator">
              <a-button type="primary" icon="search" @click="searchQuery">{{$t('查询')}}</a-button>
              <a-button type="primary" icon="reload" @click="searchReset">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域 end -->

    <!-- 操作按钮区域 begin -->
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="handleAdd">{{$t('新增')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('计费规则主')">{{$t('导出')}}</a-button>
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
      <a-button type="primary" @click="openCopyModal">{{$t('复制')}}</a-button>
      <!-- 高级查询区域 -->
      <j-super-query
        :fieldList="superFieldList"
        ref="superQueryModal"
        @handleSuperQuery="handleSuperQuery"
      ></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete" />
            <span>{{$t('删除')}}</span>
          </a-menu-item>
        </a-menu>
        <a-button>
          <span>批量操作</span>
          <a-icon type="down" />
        </a-button>
      </a-dropdown>
    </div>
    <!-- 操作按钮区域 end -->

    <!-- table区域 begin -->
    <div>
      <a-alert type="info" showIcon style="margin-bottom: 16px;">
        <template slot="message">
          <span>已选择</span>
          <a style="font-weight: 600;padding: 0 4px;">{{ selectedRowKeys.length }}</a>
          <span>项</span>
          <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
        </template>
      </a-alert>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :loading="loading"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :expandedRowKeys="expandedRowKeys"
        :rowSelection="{selectedRowKeys, onChange: onSelectChange}"
        @expand="handleExpand"
        @change="handleTableChange"
      >
        <!-- 内嵌table区域 begin -->
        <template slot="expandedRowRender" slot-scope="record">
          <a-tabs tabPosition="top">
            <a-tab-pane :tab="$t('计费规则子')" key="bmsCostRuleI" forceRender>
              <bms-cost-rule-i-sub-table :record="record" />
            </a-tab-pane>
          </a-tabs>
        </template>
        <!-- 内嵌table区域 end -->

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>

        <template slot="imgSlot" slot-scope="text">
          <div style="font-size: 12px;font-style: italic;">
            <span v-if="!text">{{$t('无图片')}}</span>
            <img v-else :src="getImgView(text)" alt style="max-width:80px;height:25px;" />
          </div>
        </template>

        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            ghost
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)"
          >
            <span>{{$t('下载')}}</span>
          </a-button>
        </template>

        <template slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>
          <a-divider type="vertical" />
          <!-- <a-dropdown>
            <a class="ant-dropdown-link">
              <span>更多 <a-icon type="down"/></span>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm :title="$t('确定删除吗?')" @confirm="handleDelete(record.id)">
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
        </template>
      </a-table>
    </div>
    <!-- table区域 end -->

    <!-- 表单区域 -->
    <BmsCostRuleHListBatchModal ref="modalForm" @ok="modalFormOk" />
    <CopyModal ref="CopyModal" @ok="modalFormOk" />
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BmsCostRuleHListBatchModal from './modules/BmsCostRuleHListBatchModal'
import BmsCostRuleISubTable from './subTables/BmsCostRuleISubTable'
import CopyModal from './modules/CopyModal'
import '@/assets/less/TableExpand.less'
import { getAction} from '@/api/manage'

export default {
  name: 'BmsCostRuleHList',
  mixins: [JeecgListMixin],
  components: {
    BmsCostRuleHListBatchModal,
    BmsCostRuleISubTable,
    CopyModal
  },
  data() {
    return {
      description: '计费规则主列表管理页面',
      // 表头
      columns: [
        // {
        //   title: '#',
        //   key: 'rowIndex',
        //   width: 60,
        //   align: 'center',
        //   customRender: (t, r, index) => parseInt(index) + 1
        // },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          width: 147,
          scopedSlots: { customRender: 'action' }
        },
        // {
        //   title: this.$t('合同编号'),
        //   align: 'center',
        //   dataIndex: 'contNo'
        // },
        // {
        //   title: this.$t('合同名称'),
        //   align: 'center',
        //   dataIndex: 'contName'
        // },
        {
          title: this.$t('规则编号'),
          align: 'center',
          dataIndex: 'costRuleNo'
        },
        {
          title: this.$t('规则名称'),
          align: 'center',
          dataIndex: 'costRuleName'
        },
        {
          title: this.$t('费用编号'),
          align: 'center',
          dataIndex: 'costNo'
        },
        {
          title: this.$t('费用名称'),
          align: 'center',
          dataIndex: 'costName'
        },
        {
          title: this.$t('来源类型编号'),
          align: 'center',
          dataIndex: 'costSNo'
        },
        {
          title: this.$t('来源类型名称'),
          align: 'center',
          dataIndex: 'costSName'
        },
        {
          title: this.$t('计费对象类型'),
          align: 'center',
          dataIndex: 'costObjType'
        },
        {
          title: this.$t('计费对象编号'),
          align: 'center',
          dataIndex: 'costObjNo'
        },
        {
          title: this.$t('计费对象名称'),
          align: 'center',
          dataIndex: 'costObjName'
        },
        {
          title: this.$t('最低收费'),
          align: 'center',
          dataIndex: 'costMin'
        },
        {
          title: this.$t('最高收费'),
          align: 'center',
          dataIndex: 'costMax'
        },
        // {
        //   title: this.$t('计费周期'),
        //   align: 'center',
        //   dataIndex: 'costPeriod'
        // },
        {
          title: this.$t('有效开始时间'),
          align: 'center',
          dataIndex: 'costValidBegin'
        },
        {
          title: this.$t('有效结束时间'),
          align: 'center',
          dataIndex: 'costValidEnd'
        }
      ],
      // 字典选项
      dictOptions: {},
      // 展开的行test
      expandedRowKeys: [],
      url: {
        list: '/bms/bmsCostRuleH/list',
        delete: '/bms/bmsCostRuleH/delete',
        deleteBatch: '/bms/bmsCostRuleH/deleteBatch',
        exportXlsUrl: '/bms/bmsCostRuleH/exportXls',
        importExcelUrl: '/bms/bmsCostRuleH/importExcel'
      },
      superFieldList: [],
      type:'sup',
      disableMixinCreated:true
    }
  },
  created() {
    this.getSuperFieldList()
    this.loadData()
  },
  computed: {
    importExcelUrl() {
      return window._CONFIG['domianURL'] + this.url.importExcelUrl
    }
  },
  methods: {
    openCopyModal(){
      this.$refs.CopyModal.open(this.type,'复制客户规则');
    },
    handleEdit: function (record) {
      this.$refs.modalForm.edit(record,this.type);
      this.$refs.modalForm.title = this.$t('编辑');
      this.$refs.modalForm.disableSubmit = false;
    },
    handleAdd: function () {
      this.$refs.modalForm.add(null,this.type);
      this.$refs.modalForm.title = this.$t('新增');
      this.$refs.modalForm.disableSubmit = false;
    },
    handleExpand(expanded, record) {
      this.expandedRowKeys = []
      if (expanded === true) {
        this.expandedRowKeys.push(record.id)
      }
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
      var params = this.getQueryParams();//查询条件
      this.loading = true;
      getAction(this.url.list, {...params,costObjType:this.type}).then((res) => {
        if (res.success) {
          //update-begin---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
          this.dataSource = res.result.records || res.result;
          if(res.result.total)
          {
            this.ipagination.total = res.result.total;
          }
          //update-end---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
        }
        if(res.code===510){
          this.$message.warning(res.message)
        }
        this.loading = false;
      })
    },
    getSuperFieldList() {
      let fieldList = []
      fieldList.push({ type: 'string', value: 'contNo', text: '合同编号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'contName', text: '合同名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costRuleNo', text: '规则编号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costRuleName', text: '规则名称', dictCode: '' })
      fieldList.push({
        type: 'popup',
        value: 'costNo',
        text: '费用编号',
        popup: { code: 'bms_cost', field: 'cost_no', orgFields: 'cost_no', destFields: 'costNo' }
      })
      fieldList.push({ type: 'string', value: 'costName', text: '费用名称', dictCode: '' })
      fieldList.push({
        type: 'popup',
        value: 'costSNo',
        text: '来源类型编号',
        popup: { code: 'bms_cs_type', field: 'cost_s_no', orgFields: 'cost_s_no', destFields: 'costSNo' }
      })
      fieldList.push({ type: 'string', value: 'costSName', text: '来源类型名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costObjType', text: '计费对象类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costObjNo', text: '计费对象编号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costObjName', text: '计费对象名称', dictCode: '' })
      fieldList.push({ type: 'BigDecimal', value: 'costMin', text: '最低收费', dictCode: '' })
      fieldList.push({ type: 'BigDecimal', value: 'costMax', text: '最高收费', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costPeriod', text: '计费周期', dictCode: '' })
      fieldList.push({ type: 'datetime', value: 'costValidBegin', text: '有效开始时间' })
      fieldList.push({ type: 'datetime', value: 'costValidEnd', text: '有效结束时间' })
      this.superFieldList = fieldList
    }
  }
}
</script>
<style lang="less" scoped>
@import '~@assets/less/common.less';
</style>