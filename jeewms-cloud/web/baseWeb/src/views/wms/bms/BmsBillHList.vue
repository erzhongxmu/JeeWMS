<template>
  <a-card class="j-inner-table-wrapper" :bordered="false">
    <!-- 查询区域 begin -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('账单编号')">
              <a-input v-model="queryParam.billNo" :placeholder="$t('请输入')" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('计费对象编号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.costObjNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('计费对象名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.costObjName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span class="table-page-search-submitButtons table-operator">
              <a-button type="primary" icon="search" @click="searchQuery">{{$t('查询')}}</a-button>
              <a-button type="primary" icon="reload" @click="searchReset">{{$t('重置')}}</a-button>
              <!-- <a @click="handleToggleSearch" style="margin-left: 8px">
                <span>{{ toggleSearchStatus ? $t('收起') : $t('展开') }}</span>
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域 end -->

    <!-- 操作按钮区域 begin -->
    <div class="table-operator">
      <!-- <a-button type="primary" icon="plus" @click="handleAdd">{{$t('新增')}}</a-button> -->
      <a-button type="primary" icon="download" @click="handleExportXls('账单详情主')">{{$t('导出')}}</a-button>
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>-->
      <!-- 高级查询区域 -->
      <j-super-query
        :fieldList="superFieldList"
        ref="superQueryModal"
        @handleSuperQuery="handleSuperQuery"
      ></j-super-query>
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            <span>{{$t('删除')}}</span>
          </a-menu-item>
      </a-menu>-->
      <!-- <a-button>
          <span>批量操作</span>
          <a-icon type="down"/>
      </a-button>-->
      <!-- </a-dropdown> -->
      <!-- @click="makeInvoice" -->
      <a-button type="primary" icon="swap" v-if="system == 'WMS'">{{$t('开票')}}</a-button>
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

      <a-tabs @change="tabChange" defaultActiveKey="未开票">
        <a-tab-pane :tab="$t('未开票')+'('+total1+')'" key="未开票">
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
            :expandedRowKeys="expandedRowKeys"
            :rowSelection="{selectedRowKeys, onChange: onSelectChange}"
            @expand="handleExpand"
            @change="handleTableChange"
          >
            <!-- 内嵌table区域 begin -->
            <template slot="expandedRowRender" slot-scope="record">
              <a-tabs tabPosition="top">
                <a-tab-pane tab="账单详情子" key="bmsBillI" forceRender>
                  <bms-bill-i-sub-table :record="record" />
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
              <a @click="billAudit(record)">审核</a>
              <a-divider type="vertical" />
              <a @click="billSend(record)">发送</a>
              <a-divider type="vertical" />
              <a @click="billConfirm(record)">确认</a>
              <a-divider type="vertical" />
              <a @click="billComplete(record)">完成</a>
              <a-divider type="vertical" />
              <a @click="handleEdit(record)">账单调整</a>
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
              <!-- <a-divider type="vertical" />
            <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
              <a>{{$t('删除')}}</a>-->
              <!-- </a-popconfirm> -->
            </template>
          </a-table>
        </a-tab-pane>

        <a-tab-pane :tab="$t('已开票')+'('+total2+')'" key="已开票">
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
            :expandedRowKeys="expandedRowKeys"
            :rowSelection="{selectedRowKeys, onChange: onSelectChange}"
            @expand="handleExpand"
            @change="handleTableChange"
          >
            <!-- 内嵌table区域 begin -->
            <template slot="expandedRowRender" slot-scope="record">
              <a-tabs tabPosition="top">
                <a-tab-pane tab="账单详情子" key="bmsBillI" forceRender>
                  <bms-bill-i-sub-table :record="record" />
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
              <a @click="billAudit(record)">审核</a>
              <a-divider type="vertical" />
              <a @click="billSend(record)">发送</a>
              <a-divider type="vertical" />
              <a @click="billConfirm(record)">确认</a>
              <a-divider type="vertical" />
              <a @click="billComplete(record)">完成</a>
              <a-divider type="vertical" />
              <a @click="handleEdit(record)">账单调整</a>
              <a-divider type="vertical" />
              <a @click="handleDetail(record)">{{$t('详情')}}</a>
            </template>
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </div>
    <!-- table区域 end -->

    <!-- 表单区域 -->
    <bms-bill-h-modal ref="modalForm" @ok="modalFormOk" />
    <bms-bill-h-modal1 ref="modalForm1" @ok="modalFormOk" />
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BmsBillHModal from './modules/BmsBillHModal'
import BmsBillHModal1 from './modules/BmsBillHModal1'
import BmsBillISubTable from './subTables/BmsBillISubTable'
import { getAction } from '@/api/manage'
import '@/assets/less/TableExpand.less'

  export default {
    name: 'BmsBillHList',
    mixins: [JeecgListMixin],
    components: {
      BmsBillHModal,
      BmsBillISubTable,
      BmsBillHModal1
    },
    data() {
      return {
        system: window.localStorage.getItem('SystemPattern'),
        description: '账单详情主列表管理页面',
        total1:'0',
        total2:'0',
        disableMixinCreated:true,
        imStatus:'未开票',
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
            align: 'center',
            width:147,
            scopedSlots: { customRender: 'action' },
          },
          {
            title: this.$t('账单编号'),
            align: 'center',
            dataIndex: 'billNo',
          },
          {
            title: this.$t('账单类型'),
            align: 'center',
            dataIndex: 'billType',
          },
          {
            title: this.$t('期间'),
            align: 'center',
            dataIndex: 'billPeriod',
          },
          {
            title: this.$t('计费对象类型'),
            align: 'center',
            dataIndex: 'costObjType',
          },
          {
            title: this.$t('计费对象编号'),
            align: 'center',
            dataIndex: 'costObjNo',
          },
          {
            title: this.$t('计费对象名称'),
            align: 'center',
            dataIndex: 'costObjName',
          },
          {
            title: this.$t('账单金额'),
            align: 'center',
            dataIndex: 'billSum',
          },
          {
            title: this.$t('货币'),
            align: 'center',
            dataIndex: 'costHb',
          },
          {
            title: this.$t('备注'),
            align: 'center',
            dataIndex: 'remark',
          },
          {
            title: this.$t('状态'),
            align: 'center',
            dataIndex: 'status',
          },
          {
            title: this.$t('文件'),
            align: 'center',
            dataIndex: 'attr1',
            scopedSlots: { customRender: 'fileSlot' },
          },
        ],
        // 字典选项
        dictOptions: {},
        // 展开的行test
        expandedRowKeys: [],
        url: {
          list: '/bms/bmsBillH/list',
          delete: '/bms/bmsBillH/delete',
          deleteBatch: '/bms/bmsBillH/deleteBatch',
          exportXlsUrl: '/bms/bmsBillH/exportXls',
          importExcelUrl: '/bms/bmsBillH/importExcel',
        },
        superFieldList:[],
      }
    },
    created() {
      // this.getSuperFieldList();
      this.loadData()
    },
    computed: {
      importExcelUrl() {
        return window._CONFIG['domianURL'] + this.url.importExcelUrl
      }
    },
    methods: {
      tabChange(active){
        this.imStatus = active
        this.loadData()
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
      params.status = this.imStatus
      this.loading = true;
      getAction(this.url.list, params).then((res) => {
        if (res.success) {
          //update-begin---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
          this.dataSource = res.result.records||res.result;
          if(this.imStatus == '未开票'){
            this.total1 = res.result.total;
          }else if(this.imStatus == '已开票'){
            this.total2 = res.result.total;
          }
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
      initDictConfig() {
      },     
      handleExpand(expanded, record) {
        this.expandedRowKeys = []
        if (expanded === true) {
          this.expandedRowKeys.push(record.id)
        }
      },
    }
  }
  

  // methods: {
  //   initDictConfig() {},
  //   handleExpand(expanded, record) {
  //     this.expandedRowKeys = []
  //     if (expanded === true) {
  //       this.expandedRowKeys.push(record.id)
  //     }
  //   },
  //   getSuperFieldList() {
  //     let fieldList = []
  //     fieldList.push({ type: 'string', value: 'billNo', text: '账单编号', dictCode: '' })
  //     fieldList.push({ type: 'string', value: 'billType', text: '账单类型', dictCode: '' })
  //     fieldList.push({ type: 'string', value: 'billPeriod', text: '期间', dictCode: '' })
  //     fieldList.push({ type: 'string', value: 'costObjType', text: '计费对象类型', dictCode: '' })
  //     fieldList.push({ type: 'string', value: 'costObjNo', text: '计费对象编号', dictCode: '' })
  //     fieldList.push({ type: 'string', value: 'costObjName', text: '计费对象名称', dictCode: '' })
  //     fieldList.push({ type: 'BigDecimal', value: 'billSum', text: '账单金额', dictCode: '' })
  //     fieldList.push({ type: 'string', value: 'costHb', text: '货币', dictCode: '' })
  //     fieldList.push({ type: 'Text', value: 'remark', text: '备注', dictCode: '' })
  //     fieldList.push({ type: 'string', value: 'status', text: '状态', dictCode: '' })
  //     fieldList.push({ type: 'string', value: 'attr1', text: '文件', dictCode: '' })
  //     this.superFieldList = fieldList
  //   },
  //   onSelectChange(selectedRowKeys, selectionRows) {
  //     this.selectedRowKeys = selectedRowKeys
  //     this.selectionRows = selectionRows
  //     // console.log(this.selectedRowKeys,this.selectionRows,'我是数据')
  //   },
  //   makeInvoice() {
  //     if (!this.selectionRows || this.selectionRows.length == 0) {
  //       this.$message.warn('请至少选择一条数据')
  //       return
  //     }
  //     this.$refs.modalForm1.list(this.selectedRowKeys)
  //     this.$refs.modalForm1.title = '开票'
  //     this.$refs.modalForm1.disableSubmit = false
  //   },
  //   billAudit(record) {
  //     console.log(record, '999999')
  //     getAction(`/bms/bmsBillH/shenhebillById`, {
  //       id: record.id
  //     }).then(res => {
  //       if (res.success) {
  //         this.$message.success('账单已审核')
  //         return
  //       } else {
  //         this.$message.error(res.message)
  //       }
  //     })
  //   },
  //   billSend(record) {
  //     console.log(record, '888888')
  //     getAction(`/bms/bmsBillH/sendbillById`, {
  //       id: record.id
  //     }).then(res => {
  //       if (res.success) {
  //         this.$message.success('账单已发送')
  //         return
  //       } else {
  //         this.$message.error(res.message)
  //       }
  //     })
  //   },
  //   billConfirm(record) {
  //     getAction(`/bms/bmsBillH/querenbillById`, {
  //       id: record.id
  //     }).then(res => {
  //       if (res.success) {
  //         this.$message.success('账单已确认')
  //         return
  //       } else {
  //         this.$message.error(res.message)
  //       }
  //     })
  //   },
  //   billComplete(record) {
  //     getAction(`/bms/bmsBillH/wanchengbillById`, {
  //       id: record.id
  //     }).then(res => {
  //       if (res.success) {
  //         this.$message.success('账单已完成')
  //         return
  //       } else {
  //         this.$message.error(res.message)
  //       }
  //     })
  //   },
  //   handleEdit: function(record) {
  //     this.$refs.modalForm.edit(record)
  //     this.$refs.modalForm.title = '账单调整'
  //     this.$refs.modalForm.disableSubmit = false
  //   }
  // }
// }
</script>
<style lang="less" scoped>
@import '~@assets/less/common.less';
</style>