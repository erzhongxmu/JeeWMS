<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="主PO号">
              <a-input placeholder="请输入主PO号" v-model="queryParam.query13"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="客户">
               <j-popup
                  v-model="queryParam.query08"
                  field="query08"
                  org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                  dest-fields="query09,query08"
                  code="md_cus"
                  :multi="false"
                />
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="计划收款单号">
              <a-input placeholder="请输入单号" v-model="queryParam.query04"></a-input>
            </a-form-item>
          </a-col> -->
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="采购单号">
              <a-input placeholder="请输入采购单号" v-model="queryParam.link02"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="销售单号">
              <a-input placeholder="请输入销售单号" v-model="queryParam.link03"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="内部发票号">
              <a-input placeholder="请输入内部发票号" v-model="queryParam.query17"></a-input>
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="状态">
              <a-input placeholder="请输入状态" v-model="queryParam.query02"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="PAYMENT LIST">
              <a-input placeholder="请输入PAYMENT LIST" v-model="queryParam.query19"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="币种">
              <j-dict-select-tag  v-model="queryParam.query22" dictCode="currency"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="付款日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-range-picker
                v-model="createTimeRange"
                format="YYYY-MM-DD"
                :placeholder="['开始时间', '结束时间']"
                @change="onDateChange"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="金额">
              <a-input placeholder="请输入金额" v-model="queryParam.num09"></a-input>
            </a-form-item>
          </a-col>
          
          
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" >查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px" >重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('busi_payment_received')">导出</a-button> -->
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-tabs @change="tabChange" defaultActiveKey="计划中">
        <a-tab-pane tab="计划中" key="计划中">
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
            >

            <template slot="htmlSlot" slot-scope="text">
              <div v-html="text"></div>
            </template>
            <template slot="imgSlot" slot-scope="text">
              <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
              <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
            </template>
            <template slot="fileSlot" slot-scope="text">
              <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
              <a-button
                v-else
                :ghost="true"
                type="primary"
                icon="download"
                size="small"
                @click="downloadFile(text)">
                下载
              </a-button>
            </template>

            <span slot="action" slot-scope="text, record">
              <a @click="handleEdit(record)">编辑</a>
              <a-divider type="vertical" />
              <a @click="goReceive(record)">去收款</a>
              <a-divider type="vertical" />
              <a-dropdown>
                <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
                <a-menu slot="overlay">
                  <a-menu-item>
                    <a @click="handleDetail(record)">详情</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                      <a>删除</a>
                    </a-popconfirm>
                  </a-menu-item>
                </a-menu>
              </a-dropdown>
            </span>
          </a-table>
        </a-tab-pane>

        <a-tab-pane tab="已收款" key="已收款">
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
            >

            <template slot="htmlSlot" slot-scope="text">
              <div v-html="text"></div>
            </template>
            <template slot="imgSlot" slot-scope="text">
              <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
              <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
            </template>
            <template slot="fileSlot" slot-scope="text">
              <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
              <a-button
                v-else
                :ghost="true"
                type="primary"
                icon="download"
                size="small"
                @click="downloadFile(text)">
                下载
              </a-button>
            </template>

            <span slot="action" slot-scope="text, record">
              <a @click="handleEdit(record)">编辑</a>
              <a-divider type="vertical" />
              <a-dropdown>
                <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
                <a-menu slot="overlay">
                  <a-menu-item>
                    <a @click="handleDetail(record)">详情</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                      <a>删除</a>
                    </a-popconfirm>
                  </a-menu-item>
                </a-menu>
              </a-dropdown>
            </span>
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </div>

    <BusiOmPlanModal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  // import BusiReceivableModal from './modules/BusiReceivableModal'
  import BusiOmPlanModal from '../busiom/modules/BusiOmPlanModal'
  
  export default {
    name: 'BusiReceivable',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      BusiOmPlanModal
    },
    data () {
      return {
        createTimeRange:[],
        description: '应收管理页面',
        // 表头
        columns: [
        {
            title:'PAYMENT LIST',
            align:"center",
            dataIndex: 'query19'
          },
          {
            title:'主PO',
            align:"center",
            dataIndex: 'query13'
          },
          {
            title:'客户',
            align:"center",
            dataIndex: 'query24_dictText'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'query02'
          },
          {
            title:'销售单号',
            align:"center",
            dataIndex: 'link03'
          },
          {
            title:'内部发票号',
            align:"center",
            dataIndex: 'query17'
          },
          {
            title:'采购人',
            align:"center",
            dataIndex: 'query16',
          },
          {
            title:'创建日期',
            align:"center",
            dataIndex: 'createTime',
          },
          // {
          //   title:'采购单号',
          //   align:"center",
          //   dataIndex: 'link02'
          // },
          {
            title:'应收总金额',
            align:"center",
            dataIndex: 'num08'
          },
          {
            title:'应收含税总金额',
            align:"center",
            dataIndex: 'num09'
          },
          {
            title:'应收总金额(RMB)',
            align:"center",
            dataIndex: 'num14'
          },
          {
            title:'应收含税总金额(RMB)',
            align:"center",
            dataIndex: 'num15'
          },
          {
            title:'本次收款金额',
            align:"center",
            dataIndex: 'num02'
          },
          {
            title:'本次收款含税金额',
            align:"center",
            dataIndex: 'num03'
          },
          {
            title:'币种',
            align:"center",
            dataIndex: 'query22',
          },
          {
            title:'创建人',
            align:"center",
            dataIndex: 'createName',
          },
          // {
          //   title:'单号',
          //   align:"center",
          //   dataIndex: 'query04'
          // },
          // {
          //   title:'供应商编码',
          //   align:"center",
          //   dataIndex: 'query08'
          // },
          // {
          //   title:'供应商名称',
          //   align:"center",
          //   dataIndex: 'query09'
          // },
          // {
          //   title:'采购单号',
          //   align:"center",
          //   dataIndex: 'link02'
          // },
          {
            title:'备注',
            align:"center",
            dataIndex: 'text01',
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
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/jeeerp/busiPaymentReceived/list2",
          delete: "/jeeerp/busiPaymentReceived/delete",
          deleteBatch: "/jeeerp/busiPaymentReceived/deleteBatch",
          exportXlsUrl: "/jeeerp/busiPaymentReceived/exportXls",
          importExcelUrl: "jeeerp/busiPaymentReceived/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
        receivableStatus: '计划中',
        queryParam:{
          query01:'XSSKJH,YPSKJH',
          query02:'计划中'
        }
      }
    },
    created() {
      this.queryParam.receivableStatus = '计划中'
      this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      onDateChange: function (value, dateString) {
        this.queryParam.createTime_begin=dateString[0];
        this.queryParam.createTime_end=dateString[1];
      },
      searchReset() {
        this.queryParam = {query01:'XSSKJH,YPSKJH', query02:this.receivableStatus}
        this.createTimeRange = []
        this.loadData()
      },
      goReceive(record) {
        this.$router.push({path: '/erp/busipay/BusiReceived', query: { orderId: record.query04 }})
      },
      tabChange(active) {
        this.receivableStatus = active
        this.queryParam.query02 = active
        this.loadData()
      },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'createName',text:'创建人名称'})
        fieldList.push({type:'string',value:'updateName',text:'更新人名称'})
        fieldList.push({type:'string',value:'query01',text:'单据类型'})
        fieldList.push({type:'string',value:'query02',text:'单据状态'})
        fieldList.push({type:'date',value:'query03',text:'建单日期'})
        fieldList.push({type:'string',value:'query04',text:'单号'})
        fieldList.push({type:'string',value:'query05',text:'公司'})
        fieldList.push({type:'string',value:'query06',text:'工厂'})
        fieldList.push({type:'string',value:'query07',text:'库存地点'})
        fieldList.push({type:'string',value:'query08',text:'对象编码'})
        fieldList.push({type:'string',value:'query09',text:'对象名称'})
        fieldList.push({type:'string',value:'query10',text:'商品编码'})
        fieldList.push({type:'string',value:'query11',text:'商品名称'})
        fieldList.push({type:'string',value:'query12',text:'单位'})
        fieldList.push({type:'string',value:'query13',text:'主PO'})
        fieldList.push({type:'string',value:'query14',text:'子PO'})
        fieldList.push({type:'string',value:'query15',text:'query15'})
        fieldList.push({type:'string',value:'query16',text:'query16'})
        fieldList.push({type:'string',value:'query17',text:'query17'})
        fieldList.push({type:'string',value:'query18',text:'query18'})
        fieldList.push({type:'string',value:'query19',text:'query19'})
        fieldList.push({type:'string',value:'query20',text:'query20'})
        fieldList.push({type:'string',value:'query21',text:'query21'})
        fieldList.push({type:'string',value:'query22',text:'query22'})
        fieldList.push({type:'string',value:'query23',text:'query23'})
        fieldList.push({type:'string',value:'query24',text:'query24'})
        fieldList.push({type:'string',value:'query25',text:'query25'})
        fieldList.push({type:'string',value:'query26',text:'query26'})
        fieldList.push({type:'string',value:'query27',text:'query27'})
        fieldList.push({type:'string',value:'query28',text:'query28'})
        fieldList.push({type:'string',value:'query29',text:'query29'})
        fieldList.push({type:'string',value:'query30',text:'query30'})
        fieldList.push({type:'number',value:'num01',text:'数量'})
        fieldList.push({type:'number',value:'num02',text:'未完成数量'})
        fieldList.push({type:'number',value:'num03',text:'已完成数量'})
        fieldList.push({type:'number',value:'num04',text:'num04'})
        fieldList.push({type:'number',value:'num05',text:'num05'})
        fieldList.push({type:'string',value:'link01',text:'关联单据类型'})
        fieldList.push({type:'string',value:'link02',text:'关联单号'})
        fieldList.push({type:'string',value:'link03',text:'link03'})
        fieldList.push({type:'string',value:'link04',text:'link04'})
        fieldList.push({type:'string',value:'link05',text:'link05'})
        fieldList.push({type:'string',value:'text01',text:'备注'})
        fieldList.push({type:'string',value:'text02',text:'备注'})
        fieldList.push({type:'string',value:'text03',text:'text03'})
        fieldList.push({type:'string',value:'text04',text:'text04'})
        fieldList.push({type:'string',value:'text05',text:'text05'})
        fieldList.push({type:'string',value:'attr1',text:'单据附件'})
        fieldList.push({type:'string',value:'attr2',text:'attr2'})
        fieldList.push({type:'string',value:'attr3',text:'attr3'})
        fieldList.push({type:'string',value:'attr4',text:'attr4'})
        fieldList.push({type:'string',value:'attr5',text:'attr5'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>