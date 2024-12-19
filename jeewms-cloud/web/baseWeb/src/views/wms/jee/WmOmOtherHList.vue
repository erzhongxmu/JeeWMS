<template>
  <a-card class="j-inner-table-wrapper" :bordered="false">

    <!-- 查询区域 begin -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="出货单号">
              <a-input v-model="queryParam.omNoticeId" placeholder="请输入出货单号"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="货主编码">
              <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,ke_hu_bian_ma,ke_hu_bian_ma" placeholder="请选择货主编码"/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主名称">
                <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" placeholder="请选择货主名称"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="要求交货时间">
                <j-date class="query-group-cust" v-model="queryParam.delvData_begin" placeholder="请选择开始日期"/>
                <span class="query-group-split-cust"></span>
                <j-date class="query-group-cust" v-model="queryParam.delvData_end" placeholder="请选择结束日期"/>
              </a-form-item>
            </a-col>
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="收货人">-->
<!--            <a-input v-model="queryParam.delvMember" placeholder="请输入收货人"/>-->
<!--          </a-form-item>-->
<!--        </a-col>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="收货人电话">-->
<!--          <a-input v-model="queryParam.delvMobile" placeholder="请输入收货人电话"/>-->
<!--        </a-form-item>-->
<!--      </a-col>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="承运人">-->
<!--          <j-dict-select-tag v-model="queryParam.reMember" placeholder="请选择承运人" dictCode="tms_kd"/>-->
<!--        </a-form-item>-->
<!--      </a-col>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="承运人车号">-->
<!--          <a-input v-model="queryParam.reCarno" placeholder="请输入承运人车号"/>-->
<!--        </a-form-item>-->
<!--      </a-col>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="状态">-->
<!--          <a-input v-model="queryParam.omSta" placeholder="请输入状态"/>-->
<!--        </a-form-item>-->
<!--      </a-col>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="出货单号">-->
<!--          <a-input v-model="queryParam.omNoticeId" placeholder="请输入出货单号"/>-->
<!--        </a-form-item>-->
<!--      </a-col>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="三方货主">-->
<!--          <a-input v-model="queryParam.ocusCode" placeholder="请输入三方货主"/>-->
<!--        </a-form-item>-->
<!--      </a-col>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="订单号">-->
<!--          <a-input v-model="queryParam.imCusCode" placeholder="请输入订单号"/>-->
<!--        </a-form-item>-->
<!--      </a-col>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="打印状态">-->
<!--          <a-input v-model="queryParam.printStatus" placeholder="请输入打印状态"/>-->
<!--        </a-form-item>-->
<!--      </a-col>-->
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span class="table-page-search-submitButtons table-operator">
              <a-button type="primary" icon="search" @click="searchQuery">{{$t('查询')}}</a-button>
              <a-button type="primary" icon="reload" @click="searchReset">{{$t('重置')}}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                <span>{{ toggleSearchStatus ? $t('收起') : $t('展开')  }}</span>
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域 end -->

    <!-- 操作按钮区域 begin -->
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="handleAdd">{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('出库管理')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>
    </div>
    <!-- 操作按钮区域 end -->

    <!-- table区域 begin -->
    <div>

      <a-alert type="info" showIcon style="margin-bottom: 16px;">
        <template slot="message">
          <span>{{$t('已选择')}}</span>
          <a style="font-weight: 600;padding: 0 4px;">{{ selectedRowKeys.length }}</a>
          <span>{{$t('项目')}}</span>
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
        :rowClassName="setRowClsaa">

        <!-- 内嵌table区域 begin -->
<!--        <template slot="expandedRowRender" slot-scope="record">-->
<!--          <a-tabs tabPosition="top">-->
<!--            <a-tab-pane tab="出货详情" key="wmOmNoticeI" forceRender>-->
<!--              <wm-om-other-h-sub-table :record="record"/>-->
<!--            </a-tab-pane>-->
<!--          </a-tabs>-->
<!--        </template>-->
        <!-- 内嵌table区域 end -->

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>

        <template slot="imgSlot" slot-scope="text">
          <div style="font-size: 12px;font-style: italic;">
            <span v-if="!text">{{$t('无图片')}}</span>
            <img v-else :src="getImgView(text)" alt="" style="max-width:80px;height:25px;"/>
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
            @click="uploadFile(text)"
          >
            <span>{{$t('下载')}}</span>
          </a-button>
        </template>

        <template slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)">{{$t('查看')}}</a>
          <a-divider type="vertical"/>
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>
          <a-divider type="vertical" />
          <a @click="exportBill('tz',record.id)">导出通知单</a>
          <a-divider type="vertical" />
          <a @click="exportBill('ck',record.id)">导出出库单</a>
          <a-divider type="vertical" />
          <a @click="exportBill('zx',record.id)">导出装箱单</a>
<!--          <a-divider type="vertical"/>-->
<!--          <a-popconfirm :title="$t('你确定吗?')" @confirm="handleDelete(record.id)">-->
<!--            <a>{{$t('删除')}}</a>-->
<!--          </a-popconfirm>-->
        </template>

      </a-table>
    </div>
    <!-- table区域 end -->

    <!-- 表单区域 -->
    <wm-om-other-h-modal ref="modalForm" @ok="modalFormOk"/>

  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmOmOtherHModal from './modules/WmOmOtherHModal'
  import WmOmOtherHSubTable from './subTables/WmOmOtherHSubTable'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JDate from '@/components/jeecg/JDate.vue'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import '@/assets/less/TableExpand.less'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'
  import JSearchSelectTag from "@/components/dict/JSearchSelectTag"

  export default {
    name: 'WmOmNoticeHList',
    mixins: [JeecgListMixin,commonTableRowClass,listCheckMixin],
    components: {
      WmOmOtherHModal,
      WmOmOtherHSubTable,
      JDate,
      JDictSelectTag,
      JSearchSelectTag
    },
    data() {
      return {
        description: '出库管理列表管理页面',
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            width: 420,
            scopedSlots: { customRender: 'action' },
          },
          {
            title: '出库单号',
            align: 'left',
            dataIndex: 'omNoticeId',
          },
          {
            title: '货主编码',
            align: 'left',
            dataIndex: 'cusCode'
          },
          {
            title: '货主名称',
            align: 'left',
            dataIndex: 'cusCode_dictText'
          },
          {
            title: '要求交货时间',
            align: 'left',
            dataIndex: 'delvData',
          },
          {
            title: '订单类型',
            align: 'left',
            dataIndex: 'orderTypeCode',
            customRender: (text) => {
              return text=='19'?this.$t('其他出库'):this.$t('货主出库')
            }
          },
          // {
          //   title: '#',
          //   key: 'rowIndex',
          //   width: 60,
          //   align: 'left',
          //   customRender: (t, r, index) => parseInt(index) + 1
          // },
          // {
          //   title: '创建人名称',
          //   align: 'left',
          //   dataIndex: 'createName',
          // },
          // {
          //   title: '创建日期',
          //   align: 'left',
          //   dataIndex: 'createTime',
          // },
          // {
          //   title: '所属部门',
          //   align: 'left',
          //   dataIndex: 'sysOrgCode',
          // },
          // {
          //   title: '所属公司',
          //   align: 'left',
          //   dataIndex: 'sysCompanyCode_dictText'
          // },
          // {
          //   title: '货主名称',
          //   align: 'left',
          //   dataIndex: 'cusCode_dictText'
          // },
          // {
          //   title: '要求交货时间',
          //   align: 'left',
          //   dataIndex: 'delvData',
          // },
          // {
          //   title: '收货人',
          //   align: 'left',
          //   dataIndex: 'delvMember',
          // },
          // {
          //   title: '收货人电话',
          //   align: 'left',
          //   dataIndex: 'delvMobile',
          // },
          // {
          //   title: '收货人地址',
          //   align: 'left',
          //   dataIndex: 'delvAddr',
          // },
          // {
          //   title: '承运人',
          //   align: 'left',
          //   dataIndex: 'reMember_dictText'
          // },
          // {
          //   title: '承运人电话',
          //   align: 'left',
          //   dataIndex: 'reMobile',
          // },
          // {
          //   title: '承运人车号',
          //   align: 'left',
          //   dataIndex: 'reCarno',
          // },
          // {
          //   title: '发货月台',
          //   align: 'left',
          //   dataIndex: 'omPlatNo_dictText'
          // },
          // {
          //   title: '状态',
          //   align: 'left',
          //   dataIndex: 'omSta',
          // },
          // {
          //   title: '出货单号',
          //   align: 'left',
          //   dataIndex: 'omNoticeId',
          // },
          // {
          //   title: '附件',
          //   align: 'left',
          //   dataIndex: 'fuJian',
          //   scopedSlots: {customRender: 'fileSlot'}
          // },
          // {
          //   title: '订单类型',
          //   align: 'left',
          //   dataIndex: 'orderTypeCode',
          // },
          // {
          //   title: '三方货主',
          //   align: 'left',
          //   dataIndex: 'ocusCode_dictText'
          // },
          // {
          //   title: '三方货主名称',
          //   align: 'left',
          //   dataIndex: 'ocusName',
          // },
          // {
          //   title: '订单号',
          //   align: 'left',
          //   dataIndex: 'imCusCode',
          // },
          // {
          //   title: '打印状态',
          //   align: 'left',
          //   dataIndex: 'printStatus',
          // },
          // {
          //   title: '对接单据类型',
          //   align: 'left',
          //   dataIndex: 'piClass',
          // },
          // {
          //   title: '账套',
          //   align: 'left',
          //   dataIndex: 'piMaster',
          // },
          // {
          //   title: '备注',
          //   align: 'left',
          //   dataIndex: 'omBeizhu',
          // },
        ],
        // 字典选项
        dictOptions: {},
        // 展开的行
        expandedRowKeys: [],
        url: {
          list: "/jeewms/wmOmNoticeH/otherlist",
          delete: "/jeewms/wmOmNoticeH/delete",
          deleteBatch: "/jeewms/wmOmNoticeH/deleteBatch",
          exportXlsUrl: "/jeewms/wmOmNoticeH/exportXls",
          importExcelUrl: "jeewms/wmOmNoticeH/importExcel",
          downReceiveExcel: "/jeewms/wmOmNoticeH/downReceiveExcel",
          doPrintckd: "/jeewms/wmOmNoticeH/doPrintckd",
        },
      }
    },
    computed: {
      importExcelUrl() {
        return window._CONFIG['domianURL'] + this.url.importExcelUrl
      }
    },
    methods: {

      initDictConfig() {
      },

      handleExpand(expanded, record) {
        console.log(expanded,record)
        this.expandedRowKeys = []
        if (expanded === true) {
          this.expandedRowKeys.push(record.id)
        }
      },
      //导出
      exportBill(type,id) {
        if(type == 'tz') {
          //return `${window._CONFIG['domianURL']}/${this.url.downReceiveExcel+'?id='+id}`;
          window.open(window._CONFIG['domianURL']+this.url.downReceiveExcel+'?id='+id);
        } else {
          window.open(window._CONFIG['domianURL']+this.url.doPrintckd+'?id='+id);
        }
      }
    }
  }
</script>
<style lang="less" scoped>
  @import '~@assets/less/common.less';
</style>