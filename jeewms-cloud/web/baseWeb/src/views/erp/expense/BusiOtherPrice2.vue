<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="Payment">
              <a-input placeholder v-model="queryParam.query05"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="Expense type">
              <a-input placeholder v-model="queryParam.query06"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="Client">
              <j-popup
                v-model="queryParam.query10"
                field="query08"
                org-fields="ke_hu_jian_cheng,ke_hu_bian_ma,xing_ye_fen_lei"
                dest-fields="query09,query08,query11"
                code="md_cus"
                :multi="false"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="Note">
              <a-input placeholder v-model="queryParam.query12"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="PO">
              <a-input placeholder v-model="queryParam.query15"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="SHIP">
              <a-input placeholder v-model="queryParam.query16"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="INV">
              <a-input placeholder v-model="queryParam.query17"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="Vendor">
              <j-popup
                v-model="queryParam.query19"
                field="supplyName"
                org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch"
                dest-fields="supplyCode,supplyName,supplyNick"
                code="md_sup"
                :multi="false"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="Staff">
              <a-input placeholder v-model="queryParam.query07"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="YYMM">
              <j-date v-model="queryParam.query09" :placeholder="$t('请选择')" dateFormat="YYMM" />
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
      <a-button @click="handleAdd" type="primary" icon="plus"  v-has="'BusiOtherPrice2:ADD'">{{$t('新增')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls($t('QC其他费用'))"  v-has="'BusiOtherPrice2:DC'">{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import"  v-has="'BusiOtherPrice2:DR'">{{$t('导入')}}</a-button>
      </a-upload>
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('busi_ord_price')">导出</a-button>
      -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel" ><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px" v-has="'BusiOtherPrice2:PLSC'"> {{$t('批量操作')}} <a-icon type="down" /></a-button>
      </a-dropdown>
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
          <a @click="handleEdit(record)" v-if="record.query23 != '已确认'" v-has="'BusiOtherPrice2:BJ'">{{$t("编辑")}}</a>
          <a-divider type="vertical" />
          <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
            <a v-has="'BusiOtherPrice2:SC'">{{$t('删除')}}</a>
          </a-popconfirm>
          <a-divider type="vertical" />
          <a-popconfirm :title="$t('确定已付款吗?')" @confirm="() => ConfirmedPayment(record.id)">
            <a v-if="record.query23 != '已确认'" v-has="'BusiOtherPrice2:FK'">{{$t("已付款")}}</a>
          </a-popconfirm>
</span>
      </a-table>
    </div>

    <BusiOtherPriceModal2 ref="modalForm" @ok="modalFormOk" />
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BusiOtherPriceModal2 from './modules/BusiOtherPriceModal2'
import { getAction } from '../../../api/manage'
import { OUT_PUT } from '@/utils/util'

export default {
  name: 'BusiOtherPrice',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    BusiOtherPriceModal2
  },
  data() {
    return {
      description: '其他费用页面',
      // 表头
      columns: [
        {
          title: 'Payment',
          align: 'center',
          sorter: true,
          dataIndex: 'query05'
        },
        {
          title: 'Expense type',
          align: 'center',
          sorter: true,
          dataIndex: 'query06'
        },
        {
          title: 'Staff',
          align: 'center',
          sorter: true,
          dataIndex: 'query07'
        },
        {
          title: 'Unit price',
          align: 'center',
          dataIndex: 'query21',
          customRender: text => {
            return text ? OUT_PUT(text) : text
          }
        },
        {
          title: 'Total CNY',
          align: 'center',
          dataIndex: 'query22',
          customRender: text => {
            return text ? OUT_PUT(text) : text
          }
        },
        {
          title: 'Client',
          align: 'center',
          sorter: true,
          dataIndex: 'query10'
        },
        {
          title: 'Company',
          align: 'center',
          sorter: true,
          dataIndex: 'query11'
        },
        {
          title: 'Note',
          align: 'center',
          dataIndex: 'query12',
          width: 100,
          checked: true,
          customCell: () => {
            return {
              style: {
                width: '100px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer'
              }
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}
            </a-tooltip>
          )
        },
        {
          title: 'Charge to client',
          align: 'center',
          dataIndex: 'query13'
        },
        {
          title: 'Departement',
          align: 'center',
          sorter: true,
          dataIndex: 'query14'
        },
        {
          title: 'PO',
          align: 'center',
          sorter: true,
          dataIndex: 'query15'
        },
        {
          title: 'SHIP#',
          align: 'center',
          sorter: true,
          dataIndex: 'query16'
        },
        {
          title: 'INV#',
          align: 'center',
          dataIndex: 'query17'
        },
        {
          title: 'Order Qty',
          align: 'center',
          dataIndex: 'query18'
        },
        {
          title: 'Vendor',
          align: 'center',
          sorter: true,
          dataIndex: 'query19'
        },
        {
          title: 'Currency',
          align: 'center',
          dataIndex: 'query20'
        },

        {
          title: 'Status',
          align: 'center',
          dataIndex: 'query23',
          customRender: text => {
            return text ? this.$t(text) : text
          }
        },
        {
          title: 'BANK ACCOUNT',
          align: 'center',
          dataIndex: 'query30'
        },{
          title: 'Date',
          align: 'center',
          dataIndex: 'query08'
        },
        {
          title: 'YYMM',
          align: 'center',
          dataIndex: 'query09'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/jeeerp/busiOrdPrice/list',
        delete: '/jeeerp/busiOrdPrice/delete',
        deleteBatch: '/jeeerp/busiOrdPrice/deleteBatch',
        exportXlsUrl: '/jeeerp/busiOrdPrice/exportXlssal',
        importExcelUrl: 'jeeerp/busiOrdPrice/importQCFY'
      },
      dictOptions: {},
      superFieldList: [],
      queryParam: {
        query01: 'QCFY'
      }
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
    ConfirmedPayment(e) {
      getAction('/jeeerp/busiOrdPrice/ConfirmedPayment', { id: e }).then(res => {
        if (res.success) {
          this.$message.success(this.$t('操作成功'))
          this.loadData()
        } else {
          this.$message.warning(this.$t('操作失败'))
        }
      })
    },
    searchReset() {
      this.queryParam = { query01: 'QCFY' }
      this.loadData()
    },
    initDictConfig() {},
    getSuperFieldList() {
      let fieldList = []
      fieldList.push({ type: 'string', value: 'createName', text: '创建人名称' })
      fieldList.push({ type: 'string', value: 'updateName', text: '更新人名称' })
      fieldList.push({ type: 'string', value: 'query01', text: '单据类型' })
      fieldList.push({ type: 'string', value: 'query02', text: '单据状态' })
      fieldList.push({ type: 'string', value: 'query03', text: '建单日期' })
      fieldList.push({ type: 'string', value: 'query04', text: '单号' })
      fieldList.push({ type: 'string', value: 'query05', text: '公司' })
      fieldList.push({ type: 'string', value: 'query06', text: '工厂' })
      fieldList.push({ type: 'string', value: 'query07', text: '库存地点' })
      fieldList.push({ type: 'string', value: 'query08', text: '对象编码' })
      fieldList.push({ type: 'string', value: 'query09', text: '对象名称' })
      fieldList.push({ type: 'string', value: 'query10', text: '商品编码' })
      fieldList.push({ type: 'string', value: 'query11', text: '商品名称' })
      fieldList.push({ type: 'string', value: 'query12', text: '单位' })
      fieldList.push({ type: 'string', value: 'query13', text: 'query13' })
      fieldList.push({ type: 'string', value: 'query14', text: 'query14' })
      fieldList.push({ type: 'string', value: 'query15', text: '类型（采购/销售/税率/成本价）' })
      fieldList.push({ type: 'string', value: 'query16', text: '币种' })
      fieldList.push({ type: 'string', value: 'query17', text: 'query17' })
      fieldList.push({ type: 'string', value: 'query18', text: 'query18' })
      fieldList.push({ type: 'string', value: 'query19', text: 'query19' })
      fieldList.push({ type: 'string', value: 'query20', text: 'query20' })
      fieldList.push({ type: 'string', value: 'query21', text: 'query21' })
      fieldList.push({ type: 'string', value: 'query22', text: 'query22' })
      fieldList.push({ type: 'string', value: 'query23', text: 'query23' })
      fieldList.push({ type: 'string', value: 'query24', text: 'query24' })
      fieldList.push({ type: 'string', value: 'query25', text: 'query25' })
      fieldList.push({ type: 'string', value: 'query26', text: 'query26' })
      fieldList.push({ type: 'string', value: 'query27', text: 'query27' })
      fieldList.push({ type: 'string', value: 'query28', text: 'query28' })
      fieldList.push({ type: 'string', value: 'query29', text: 'query29' })
      fieldList.push({ type: 'string', value: 'query30', text: 'query30' })
      fieldList.push({ type: 'number', value: 'num01', text: '单位数量' })
      fieldList.push({ type: 'number', value: 'num02', text: '未完成数量' })
      fieldList.push({ type: 'number', value: 'num03', text: '已完成数量' })
      fieldList.push({ type: 'number', value: 'num04', text: '总价格' })
      fieldList.push({ type: 'number', value: 'num05', text: 'num05' })
      fieldList.push({ type: 'string', value: 'link01', text: '关联单据类型' })
      fieldList.push({ type: 'string', value: 'link02', text: '关联单号' })
      fieldList.push({ type: 'string', value: 'link03', text: 'link03' })
      fieldList.push({ type: 'string', value: 'link04', text: 'link04' })
      fieldList.push({ type: 'string', value: 'link05', text: 'link05' })
      fieldList.push({ type: 'string', value: 'text01', text: '备注' })
      fieldList.push({ type: 'string', value: 'text02', text: '备注' })
      fieldList.push({ type: 'string', value: 'text03', text: 'text03' })
      fieldList.push({ type: 'string', value: 'text04', text: 'text04' })
      fieldList.push({ type: 'string', value: 'text05', text: 'text05' })
      fieldList.push({ type: 'string', value: 'attr1', text: '单据附件' })
      fieldList.push({ type: 'string', value: 'attr2', text: 'attr2' })
      fieldList.push({ type: 'string', value: 'attr3', text: 'attr3' })
      fieldList.push({ type: 'string', value: 'attr4', text: 'attr4' })
      fieldList.push({ type: 'string', value: 'attr5', text: 'attr5' })
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>