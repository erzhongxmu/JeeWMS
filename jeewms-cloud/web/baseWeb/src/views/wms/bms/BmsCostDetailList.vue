<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('费用编号')">
              <!-- <j-popup
                :placeholder="$t('请选择费用编号')"
                v-model="queryParam.costNo"
                code="bms_cost"
                org-fields="cost_no,cost_name,cost_desc"
                dest-fields="costNo,costName,costDesc"
                :field="getPopupField('costNo,costName,costDesc')"
                :multi="true"
              /> -->
              <a-input :placeholder="$t('请输入费用编号')" v-model="queryParam.costNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('费用名称')">
              <a-input :placeholder="$t('请输入费用名称')" v-model="queryParam.costName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('计费对象编号')">
              <a-input :placeholder="$t('请输入计费对象编号')" v-model="queryParam.costObjNo"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('计费对象名称')">
              <a-input :placeholder="$t('请输入计费对象名称')" v-model="queryParam.costObjName"></a-input>
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('费用描述')">
                <a-input :placeholder="$t('请输入费用描述')" v-model="queryParam.costDesc"></a-input>
              </a-form-item>
          </a-col>-->
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >{{$t('重置')}}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开') }}
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
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">{{$t('新增')}}</a-button> -->
      <a-button type="primary" icon="download" @click="handleExportXls('费用详情')">{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <!-- <a-button type="primary" icon="import">{{$t('导入')}}</a-button> -->
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query
        :fieldList="superFieldList"
        ref="superQueryModal"
        @handleSuperQuery="handleSuperQuery"
      ></j-super-query>
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>-->
      <a-button type="primary" icon="mail" @click="genarateBill('生成账单')">{{$t('生成账单')}}</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-tabs @change="tabChange" defaultActiveKey="未生成">
        <a-tab-pane :tab="$t('未生成')+'('+total1+')'" key="未生成" >
          <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          class="j-table-force-nowrap"
          :loading="loading"
          :columns="columns"
          :scroll="{x:true}"
          :dataSource="dataSource"
          :pagination="ipagination"
          :rowSelection="{selectedRowKeys, onChange: onSelectChange}"
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
              <a @click="handleEdit(record)">费用调整</a>

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
              <!-- <a-divider type="vertical" />
            <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
             <a>{{$t('删除')}}</a>
              </a-popconfirm>-->
            </span>
          </a-table>
        </a-tab-pane>
        <a-tab-pane :tab="$t('已生成')+'('+total2+')'" key="已生成" >
          <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          class="j-table-force-nowrap"
          :loading="loading"
          :columns="columns"
          :scroll="{x:true}"
          :dataSource="dataSource"
          :pagination="ipagination"
          :rowSelection="{selectedRowKeys, onChange: onSelectChange}"
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
              <a @click="handleEdit(record)">费用调整</a>
              <a-divider type="vertical" />
              <a @click="handleDetail(record)">{{$t('详情')}}</a>
            </span>
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </div>

    <bms-cost-detail-modal ref="modalForm" @ok="modalFormOk"></bms-cost-detail-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { getAction } from '@/api/manage'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BmsCostDetailModal from './modules/BmsCostDetailModal'

export default {
  name: 'BmsCostDetailList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    BmsCostDetailModal
  },
  data() {
    return {
      description: '费用详情管理页面',
      total1: '0',
      total2: '0',
      disableMixinCreated: true,
      imStatus: '未生成',
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
          title: this.$t('费用描述'),
          align: 'center',
          dataIndex: 'costDesc'
        },
        {
          title: this.$t('来源编号'),
          align: 'center',
          dataIndex: 'costSoNo'
        },
        {
          title: this.$t('来源名称'),
          align: 'center',
          dataIndex: 'costSoName'
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
          title: this.$t('来源数量'),
          align: 'center',
          dataIndex: 'costSoSum'
        },
        {
          title: this.$t('来源单位'),
          align: 'center',
          dataIndex: 'costSoUnit'
        },
        {
          title: this.$t('来源日期'),
          align: 'center',
          dataIndex: 'costSoDate'
        },
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
          title: this.$t('计费规则行项目号'),
          align: 'center',
          dataIndex: 'costRuleItemNo'
        },
        {
          title: this.$t('计费类型'),
          align: 'center',
          dataIndex: 'costTypeNo'
        },
        {
          title: this.$t('开始数量'),
          align: 'center',
          dataIndex: 'beginSum'
        },
        {
          title: this.$t('结束数量'),
          align: 'center',
          dataIndex: 'endSum'
        },
        {
          title: this.$t('计费单位'),
          align: 'center',
          dataIndex: 'costUnit'
        },
        {
          title: this.$t('计费规则不含税价'),
          align: 'center',
          dataIndex: 'costJfgz'
        },
        {
          title: this.$t('计费税率'),
          align: 'center',
          dataIndex: 'costSl'
        },
        {
          title: this.$t('计费规则原含税价'),
          align: 'center',
          dataIndex: 'costHsj'
        },
        {
          title: this.$t('货币'),
          align: 'center',
          dataIndex: 'costHb'
        },
        {
          title: this.$t('计费不含税价'),
          align: 'center',
          dataIndex: 'costCoBhsj'
        },
        {
          title: this.$t('计费原含税价'),
          align: 'center',
          dataIndex: 'costCoYhsj'
        },
        {
          title: this.$t('状态'),
          align: 'center',
          dataIndex: 'status'
        }
      ],
      url: {
        list: '/bms/bmsCostDetail/list',
        delete: '/bms/bmsCostDetail/delete',
        deleteBatch: '/bms/bmsCostDetail/deleteBatch',
        exportXlsUrl: '/bms/bmsCostDetail/exportXls',
        importExcelUrl: 'bms/bmsCostDetail/importExcel',
        generateUrl: '/bms/bmsCostDetail/genbill'
      },
      dictOptions: {},
      superFieldList: []
    }
  },
  created() {
    this.getSuperFieldList()
    this.loadData()
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },

  methods: {
    genarateBill() {
      let param
      param = this.getQueryParams()
      if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
        param.selections = this.selectedRowKeys.join(',')
      }
      // console.log(param,'000900')
      getAction(`bms/bmsCostDetail/genbill`, param).then(res => {
        if (res.success) {
          this.$message.success('账单已生成')
          return
        }
      })
    },
    tabChange(active) {
      this.imStatus = active
      this.loadData()
    },
    loadData(arg) {
      if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      var params = this.getQueryParams() //查询条件
      params.status = this.imStatus
      this.loading = true
      getAction(this.url.list, params).then(res => {
        if (res.success) {
          //update-begin---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
          this.dataSource = res.result.records || res.result
          if (this.imStatus == '未生成') {
            this.total1 = res.result.total
          } else if (this.imStatus == '已生成') {
            this.total2 = res.result.total
          }
          if (res.result.total) {
            this.ipagination.total = res.result.total
          }
          //update-end---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    initDictConfig() {},
    getSuperFieldList() {
      let fieldList = []
      fieldList.push({
        type: 'popup',
        value: 'costNo',
        text: '费用编号',
        popup: { code: 'bms_cost', field: 'cost_no', orgFields: 'cost_no', destFields: 'costNo' }
      })
      fieldList.push({ type: 'string', value: 'costName', text: '费用名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costDesc', text: '费用描述', dictCode: '' })
      fieldList.push({
        type: 'popup',
        value: 'costSoNo',
        text: '来源编号',
        popup: { code: 'bms_cost_source', field: 'cost_so_no', orgFields: 'cost_so_no', destFields: 'costSoNo' }
      })
      fieldList.push({ type: 'string', value: 'costSoName', text: '来源名称', dictCode: '' })
      fieldList.push({
        type: 'popup',
        value: 'costObjType',
        text: '计费对象类型',
        popup: {
          code: 'bms_cost_source',
          field: 'cost_obj_type',
          orgFields: 'cost_obj_type',
          destFields: 'costObjType'
        }
      })
      fieldList.push({ type: 'string', value: 'costObjNo', text: '计费对象编号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costObjName', text: '计费对象名称', dictCode: '' })
      fieldList.push({ type: 'BigDecimal', value: 'costSoSum', text: '来源数量', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costSoUnit', text: '来源单位', dictCode: '' })
      fieldList.push({ type: 'datetime', value: 'costSoDate', text: '来源日期' })
      fieldList.push({
        type: 'popup',
        value: 'costRuleNo',
        text: '规则编号',
        popup: { code: 'bms_cost_rule_h', field: 'cost_rule_no', orgFields: 'cost_rule_no', destFields: 'costRuleNo' }
      })
      fieldList.push({ type: 'string', value: 'costRuleName', text: '规则名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costRuleItemNo', text: '计费规则行项目号', dictCode: '' })
      fieldList.push({
        type: 'popup',
        value: 'costTypeNo',
        text: '计费类型',
        popup: { code: 'bms_cost_type', field: 'cost_type_no', orgFields: 'cost_type_no', destFields: 'costTypeNo' }
      })
      fieldList.push({ type: 'BigDecimal', value: 'beginSum', text: '开始数量', dictCode: '' })
      fieldList.push({ type: 'BigDecimal', value: 'endSum', text: '结束数量', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costUnit', text: '计费单位', dictCode: '' })
      fieldList.push({ type: 'BigDecimal', value: 'costJfgz', text: '计费规则不含税价', dictCode: '' })
      fieldList.push({ type: 'BigDecimal', value: 'costSl', text: '计费税率', dictCode: '' })
      fieldList.push({ type: 'BigDecimal', value: 'costHsj', text: '计费规则原含税价', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costHb', text: '货币', dictCode: '' })
      fieldList.push({ type: 'BigDecimal', value: 'costCoBhsj', text: '计费不含税价', dictCode: '' })
      fieldList.push({ type: 'BigDecimal', value: 'costCoYhsj', text: '计费原含税价', dictCode: '' })
      fieldList.push({ type: 'string', value: 'status', text: '状态', dictCode: '' })
      this.superFieldList = fieldList
    },
    handleEdit: function(record) {
      this.$refs.modalForm.edit(record)
      this.$refs.modalForm.title = '费用调整'
      this.$refs.modalForm.disableSubmit = false
    },
    handleEdit: function(record) {
      this.$refs.modalForm.edit(record)
      this.$refs.modalForm.title = '费用调整'
      this.$refs.modalForm.disableSubmit = false
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>