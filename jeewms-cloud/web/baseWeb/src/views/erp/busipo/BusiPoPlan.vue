<template>
  <a-card :bordered="false"  class="unselectable">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('主PO号')">
              <a-input :placeholder="$t('请输入主PO号')" v-model="queryParam.query13"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('采购人')">
              <a-input placeholder="请输入" v-model="queryParam.query16"></a-input>
              <!-- <j-search-select-tag
                type="list"
                v-model="queryParam.query16"
                dict="sys_user,realname,realname,del_flag = '0'"
                :placeholder="$t('请选择')"
              /> -->
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户')">
              <j-popup
                v-model="queryParam.query24"
                field="query08"
                org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                dest-fields="query09,query08"
                code="md_cus"
                :multi="false"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('供应商')">
              <j-popup
                v-model="queryParam.query08"
                field="supplyCode"
                org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch"
                dest-fields="supplyCode,supplyName,supplyNick"
                code="md_sup"
                :multi="false"
              />
            </a-form-item>
          </a-col>
          
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('创建人')">
              <j-search-select-tag
                type="list"
                v-model="queryParam.createBy"
                dict="sys_user,realname,username,del_flag = '0'"
                :placeholder="$t('请选择')"
              />
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('计划付款单号')">
              <a-input :placeholder="$t('请输入计划付款单号')" v-model="queryParam.query04"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('采购单号')">
              <a-input :placeholder="$t('请输入打样单号')" v-model="queryParam.link02"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('销售单号')">
              <a-input :placeholder="$t('请输入销售单号')" v-model="queryParam.link03"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('内部发票号')">
              <a-input :placeholder="$t('请输入内部发票号')" v-model="queryParam.query17"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('状态')">
              <a-input :placeholder="$t('请输入状态')" v-model="queryParam.query02"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('企业属性')">
              <j-dict-select-tag
                :placeholder="$t('请选择')"
                v-model="queryParam.query31"
                dictCode="wms_com_type,com_type_code,com_type_name"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('备注')">
              <a-input :placeholder="$t('请输入备注')" v-model="queryParam.text01"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" v-has="'SampleMakingOrder:search'"
                >{{$t('查询')}}</a-button
              >
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
                v-has="'SampleMakingOrder:reset'"
                >{{$t('重置')}}</a-button
              >
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button @click="handleAdd" type="primary" icon="plus" v-has="'BusiPoPlan:add'">新增</a-button> -->
      <a-button @click="handleAdd2" type="primary" icon="plus"  >{{$t('创建付款计划')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('采购付款计划')" v-has="'BusiPoPlan:export'"
        >{{$t('导出')}}</a-button>
        <a-button type="primary" v-if="selectedRowKeys.length > 0" @click="SET_PAYMENT_LIST"
        >{{$t('修改PAYMENT LIST')}} </a-button>
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'BusiPoPlan:import'">导入</a-button>
      </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"
            ><a-icon type="delete" v-has="'BusiPoPlan:batchDelete'" />删除</a-menu-item
          >
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{ x: true }"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        class="j-table-force-nowrap"
        @change="handleTableChange"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">{{$t('无图片')}}</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt=""
            style="max-width: 80px; font-size: 12px; font-style: italic"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">{{$t('无文件')}}</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
            {{$t('下载')}}
          </a-button>
        </template>
        <span slot="remark" slot-scope="text,record">
          <j-ellipsis :value="text" :length="20" v-if="text.length>20"/>
          <span v-else>{{text}}</span>
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-has="'BusiPoPlan:edit'">{{$t('编辑')}}</a>

          <a-divider type="vertical" v-has="'BusiPoPlan:edit'" />
          <a-dropdown>
            <a class="ant-dropdown-link">{{$t('更多')}} <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-has="'BusiPoPlan:detail'">
                <a @click="handleDetail(record)">{{$t('详情')}}</a>
              </a-menu-item>
              <a-menu-item v-has="'BusiPoPlan:delete'">
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
    
    <busi-po-plan-modal ref="modalForm" @ok="modalFormOk" />
    <addBusiPoPlanselect ref="addBusiPoPlanselect" @ok="modalFormOk" />

    <j-modal :title="$t('修改PAYMENT LIST')" :width="500" :visible="visible2" switchFullscreen @ok="handleOk2" @cancel="close2" :maskClosable="false">
        <a-form layout="inline" @keyup.enter.native="searchQuery">
                    <a-row >
                        <a-col >
                            <a-form-item :label="$t('PAYMENT LIST') ">
                                <a-input :placeholder="$t('请输入PAYMENT LIST') " v-model="model2.query19"></a-input>
                            </a-form-item>
                        </a-col>
                    </a-row>
                </a-form>
        </j-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BusiPoPlanModal from './modules/BusiPoPlanModal'
import { getAction, postFormAction, postAction,putAction } from '@/api/manage'
import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
import { SamplePlanList } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
import addBusiPoPlanselect from './modules/addBusiPoPlanselect'
import store from '@/store/'
import { OUT_PUT} from '@/utils/util'
export default {
  name: 'BusiPoPlan',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    BusiPoPlanModal,
    addBusiPoPlanselect
  },
  data() {
    return {
      model2:{},
      visible2: false,
      description: '付款计划管理页面',
      // 表头
      columns: [
        {
          title:this.$t( '主PO'),
          align: 'center',
            sorter: true,
          dataIndex: 'query13',
        },
        {
          title:this.$t( '子PO'),
          align: "center",
          dataIndex: 'query14'
          , sorter: true,
        },
        {
          title:this.$t( '客户'),
          align: 'center',
            sorter: true,
          dataIndex: 'query24_dictText',
        },
        {
          title:this.$t( '企业属性'),
          align: 'center',
            sorter: true,
          dataIndex: 'query31',
        },
        {
          title:this.$t( '状态'),
          align: 'center',
            sorter: true,
          dataIndex: 'query02',
          customRender: (text) => {
            return text?this.$t(text):text
          }
        },

        {
          title:this.$t( '预计到货时间'),
          align: 'center',
            sorter: true,
          dataIndex: 'query21',
        },
        {
          title:this.$t( '销售单号'),
          align: 'center',
            sorter: true,
          dataIndex: 'link03',
          width: 100,
          checked: true,
          customCell: () => {
            return {
              style: {
                width: '100px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer',
              },
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}
            </a-tooltip>
          ),
        },
        {
          title:this.$t( '内部发票号'),
          align: 'center',
            sorter: true,
          dataIndex: 'query17',
          width: 100,
          checked: true,
          customCell: () => {
            return {
              style: {
                width: '100px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer',
              },
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}
            </a-tooltip>
          ),
        },
        {
          title:this.$t( '供应商编码'),
          align: 'center',
            sorter: true,
          dataIndex: 'query08',
        },
        {
          title:this.$t( '应付金额'),
          align: 'center',
            sorter: true,
          dataIndex: 'num09',
          customRender: (text) => {
            return text?OUT_PUT(text):text
          }
        },
        {
          title:this.$t( '已付金额'),
          align: 'center',
            sorter: true,
          dataIndex: 'num11',
          customRender: (text) => {
            return text?OUT_PUT(text):text
          }
        },
        {
          title:this.$t( '余额'),
          align: 'center',
            sorter: true,
          dataIndex: 'num08',
          customRender: (text) => {
            return text?OUT_PUT(text):text
          }
        },
        {
          title:this.$t( '币种'),
          align: 'center',
          dataIndex: 'query22',
          sorter: true,
        },
        {
          title:this.$t( '税率%'),
          align: 'center',
          dataIndex: 'num10',
          sorter: true,
        },
        {
          title:this.$t( 'PAYMENT LIST'),
          align: "center",
          dataIndex: 'query19'
          , sorter: true,
          width: 100,
          checked: true,
          customCell: () => {
            return {
              style: {
                width: '100px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer',
              },
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}
            </a-tooltip>
          ),
        },
        {
          title:this.$t( '采购人'),
          align: 'center',
            sorter: true,
          dataIndex: 'query16',
        },
        {
          title:this.$t( '创建日期'),
          align: 'center',
            sorter: true,
          dataIndex: 'createTime',
        },
        {
          title:this.$t( '备注'),
          dataIndex: 'text01',
          align: 'center',
            sorter: true,
          scopedSlots: { customRender: 'remark' },
        },
        {
          title:this.$t( '操作'),
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/jeeerp/busiPaymentReceived/list2',
        delete: '/jeeerp/busiPaymentReceived/delete',
        deleteBatch: '/jeeerp/busiPaymentReceived/deleteBatch',
        exportXlsUrl: '/jeeerp/busiPaymentReceived/exportXls',
        importExcelUrl: 'jeeerp/busiPaymentReceived/importExcel',
      },
      dictOptions: {},
      superFieldList: [],
      queryParam: {
        query01: 'CGFKJH',
        createBy: store.getters.userInfo.username
      },
    }
  },
  created() {
    this.getSuperFieldList()
  },
  mounted() {
    if (this.$route.query.orderId) {
      this.$refs.modalForm.add2(this.$route.query.orderId)
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
    close2(){
      this.visible2 = false
      this.model2 = {}
    },
    handleOk2(){
      if(!this.model2.query19) return this.$message.info("请输入PAYMENT LIST")
      let arr = []
      this.selectionRows.map((item)=>{
        arr.push(item.query04)
      })
      putAction('/jeeerp/busiPaymentReceived/EditQuery19', {query04:arr.join(','),query19:this.model2.query19}).then((res) => {
        if (res.success) {
          this.loadData()
          this.close2()
          this.$message.success(this.$t(res.message))
        } else {
          this.$message.warning(this.$t(res.message))
        }
      })
    },
    SET_PAYMENT_LIST(){
      this.visible2 = true
    },
    handleAdd2(){
      this.$refs.addBusiPoPlanselect.add()
    },
    handleExportXls(name) {
      let myparam = {}
      Object.assign(myparam, this.queryParam)
      for (var mkey in myparam) {
        if (myparam[mkey] != null && myparam[mkey] != '' && myparam[mkey].indexOf('*') == -1) {
          myparam[mkey] = '*' + myparam[mkey] + '*'
        }
      }
      let param = { ...myparam }
      if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
        param['selections'] = this.selectedRowKeys.join(',')
      }
      getAction('/jeeerp/ExportTableData/ExportBusiPaymentReceived', param).then((res) => {
        ExportTemplate([], SamplePlanList, res.result, name)
      })
    },
    searchReset() {
      this.queryParam = { query01: 'CGFKJH',createBy: this.$store.getters.userInfo.username }
      this.loadData()
    },
    popupCallback1() {},
    popupCallback2() {},
    popupCallback3() {},
    popupCallback4() {},
    initDictConfig() {},
    getSuperFieldList() {
      let fieldList = []
      fieldList.push({ type: 'string', value: 'createName', text: '创建人名称' })
      fieldList.push({ type: 'string', value: 'updateName', text: '更新人名称' })
      fieldList.push({ type: 'string', value: 'query01', text: '单据类型' })
      fieldList.push({ type: 'string', value: 'query02', text: '单据状态' })
      fieldList.push({ type: 'date', value: 'query03', text: '建单日期' })
      fieldList.push({ type: 'string', value: 'query04', text: '单号' })
      fieldList.push({ type: 'string', value: 'query05', text: '公司' })
      fieldList.push({ type: 'string', value: 'query06', text: '工厂' })
      fieldList.push({ type: 'string', value: 'query07', text: '库存地点' })
      fieldList.push({ type: 'string', value: 'query08', text: '对象编码' })
      fieldList.push({ type: 'string', value: 'query09', text: '对象名称' })
      fieldList.push({ type: 'string', value: 'query10', text: '商品编码' })
      fieldList.push({ type: 'string', value: 'query11', text: '商品名称' })
      fieldList.push({ type: 'string', value: 'query12', text: '单位' })
      fieldList.push({ type: 'string', value: 'query13', text: '主PO' })
      fieldList.push({ type: 'string', value: 'query14', text: '子PO' })
      fieldList.push({ type: 'string', value: 'query15', text: 'query15' })
      fieldList.push({ type: 'string', value: 'query16', text: 'query16' })
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
      fieldList.push({ type: 'number', value: 'num01', text: '数量' })
      fieldList.push({ type: 'number', value: 'num02', text: '未完成数量' })
      fieldList.push({ type: 'number', value: 'num03', text: '已完成数量' })
      fieldList.push({ type: 'number', value: 'num04', text: 'num04' })
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
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>