<template>
    <a-card :bordered="false">
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="单号">
                <a-input placeholder="请输入单号" v-model="queryParam.query04"></a-input>
              </a-form-item>
            </a-col> -->
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
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="主PO号">
                <a-input placeholder="请输入主PO号" v-model="queryParam.query13"></a-input>
              </a-form-item>
            </a-col>
            <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="装箱单号">
                <a-input placeholder="请输入装箱单号" v-model="queryParam.query17"></a-input>
              </a-form-item>
            </a-col> -->
            <a-col  :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-model-item label="要求交货时间">
                <j-date :placeholder="$t('请选择')"  v-model="queryParam.query21" />
              </a-form-model-item>
            </a-col>
            <a-col  :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-model-item label="创建人">
                <j-search-select-tag
                  type="list"
                  v-model="queryParam.createName"
                  dict="sys_user,realname,realname,del_flag = '0'"
                  :placeholder="$t('请选择')"
                />
              </a-form-model-item>
            </a-col>
           
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                <a-button type="primary" @click="searchQuery" icon="search" v-has="'BusiOmOrder:search'" >查询</a-button>
                <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px" v-has="'BusiOmOrder:reset'" >重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <!-- 查询区域-END -->
      
      <!-- 操作按钮区域 -->
      <div class="table-operator">
        <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
        <!-- <a-button type="primary" icon="download" @click="handleExportXls('出库预约')" v-has="'BusiOmOrder:export'" >导出</a-button> -->
        <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
          <a-button type="primary" icon="import" v-has="'BusiOmOrder:import'" >导入</a-button>
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
  
        <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          class="j-table-force-nowrap"
          :scroll="{x:true}"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :loading="loading"
          :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
          @change="handleTableChange">
  
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
            <a @click="handleEdit(record)" v-has="'BusiOmOrder:edit'" >编辑</a>
  
            <a-divider type="vertical" v-has="'BusiOmOrder:edit'" />
            <a-dropdown>
              <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
              <a-menu slot="overlay">
                <a-menu-item>
                  <a @click="particulars(record)" v-has="'BusiOmOrder:detail'" >详情</a>
                </a-menu-item>
                <!-- <a-menu-item>
                  <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                    <a>删除</a>
                  </a-popconfirm>
                </a-menu-item> -->
              </a-menu>
            </a-dropdown>
          </span>
  
        </a-table>
      </div>
  
      <allotModules ref="modalForm" @ok="modalFormOk"/>
    </a-card>
  </template>
  
  <script>
  
    import { JeecgListMixin } from '@/mixins/JeecgListMixin'
    import allotModules from './modules/allotModules'
    import '@/assets/less/TableExpand.less'
    import { getAction, httpAction, postAction, downFile } from '@/api/manage'
    import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
    import { BusiOmOrder } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
    import store from '@/store/'
    export default {
      name: "BusiOmOrder",
      mixins:[JeecgListMixin],
      components: {
        allotModules
      },
      data () {
        return {
          description: '出库预约页面',
          // 表头
          columns: [
            {
              title:'源仓库',
              align:"center",
              dataIndex: 'query05'
            },
            {
              title:'目的仓库',
              align:"center",
              dataIndex: 'query06'
            },
            {
              title:'供应商',
              align:"center",
              dataIndex: 'query07'
            },
            {
              title:'销售单号',
              align:"center",
              dataIndex: 'query08'
            },
            {
              title:'内部发票号',
              align:"center",
              dataIndex: 'query09'
            },
            {
              title:'采购人',
              align:"center",
              dataIndex: 'query10'
            },
            {
              title:'备注',
              align:"center",
              dataIndex: 'text01'
            },
            {
              title: '操作',
              dataIndex: 'action',
              align:"center",
              fixed:"right",
              width:147,
              scopedSlots: { customRender: 'action' },
            }
          ],
          url: {
            list: "/jeeerp/busiOm/list2",
            delete: "/jeeerp/busiOm/delete",
            deleteBatch: "/jeeerp/busiOm/deleteBatch",
            exportXlsUrl: "/jeeerp/busiOm/exportXls",
            importExcelUrl: "jeeerp/busiOm/importExcel",
            
          },
          dictOptions:{},
          superFieldList:[],
          queryParam:{
            query01:'WLDB',
          },
        }
      },
      created() {
      },
      mounted() {
        if(this.$route.query.orderId) {
          this.$refs.modalForm.add2(this.$route.query.orderId)
        }
      },
      computed: {
        importExcelUrl: function(){
          return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
        }
      },
      methods: {
        particulars(e){
          this.$refs.modalForm.particulars(e)
        },
        searchReset() {
          this.queryParam = {query01: "WLDB"}
          this.loadData()
        },
        initDictConfig(){
        },
      }
    }
  </script>
  <style scoped>
    @import '~@assets/less/common.less';
  </style>