<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
     <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="持有货币">
              <a-input placeholder="请输入持有货币" v-model="queryParam.query04"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="兑换货币">
                <a-input placeholder="请输入兑换货币" v-model="queryParam.query05"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" >查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px" >重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
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
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('conf_erp')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
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
    </div>

    <ExchangeRateModal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ExchangeRateModal from './modules/ExchangeRateModal'
  import '@/assets/less/TableExpand.less'

  export default {
    name: "ConfErpList",
    mixins:[JeecgListMixin],
    components: {
      ExchangeRateModal
    },
    data () {
      return {
        description: 'conf_erp管理页面',
        // 表头
        columns: [
          // {
          //   title:'创建人名称',
          //   align:"center",
          //   dataIndex: 'createName'
          // },
          // {
          //   title:'更新人名称',
          //   align:"center",
          //   dataIndex: 'updateName'
          // },
          // {
          //   title:'query01',
          //   align:"center",
          //   dataIndex: 'query01'
          // },
          // {
          //   title:'query02',
          //   align:"center",
          //   dataIndex: 'query02'
          // },
          // {
          //   title:'query03',
          //   align:"center",
          //   dataIndex: 'query03'
          // },
          {
            title:'持有货币',
            align:"center",
            dataIndex: 'query04'
          },
          {
            title:'兑换货币',
            align:"center",
            dataIndex: 'query05'
          },
          {
            title:'汇率',
            align:"center",
            dataIndex: 'query06'
          },
          {
            title:'可用日期',
            align:"center",
            dataIndex: 'query07'
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
          list: "/jeeerp/confErp/list",
          delete: "/jeeerp/confErp/delete",
          deleteBatch: "/jeeerp/confErp/deleteBatch",
          exportXlsUrl: "/jeeerp/confErp/exportXls",
          importExcelUrl: "jeeerp/confErp/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
        queryParam:{
          query01:'HLPZ'
        }
      }
    },
    created() {
      this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      searchReset() {
        this.queryParam = {query01: "HLPZ"}
        this.loadData()
      },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
         fieldList.push({type:'string',value:'createName',text:'创建人名称',dictCode:''})
         fieldList.push({type:'string',value:'updateName',text:'更新人名称',dictCode:''})
         fieldList.push({type:'string',value:'query01',text:'query01',dictCode:''})
         fieldList.push({type:'string',value:'query02',text:'query02',dictCode:''})
         fieldList.push({type:'string',value:'query03',text:'query03',dictCode:''})
         fieldList.push({type:'string',value:'query04',text:'配置编码',dictCode:''})
         fieldList.push({type:'string',value:'query05',text:'query05',dictCode:''})
         fieldList.push({type:'string',value:'query06',text:'query06',dictCode:''})
         fieldList.push({type:'string',value:'query07',text:'query07',dictCode:''})
         fieldList.push({type:'string',value:'query08',text:'query08',dictCode:''})
         fieldList.push({type:'string',value:'query09',text:'query09',dictCode:''})
         fieldList.push({type:'string',value:'query10',text:'query10',dictCode:''})
         fieldList.push({type:'string',value:'query11',text:'query11',dictCode:''})
         fieldList.push({type:'string',value:'query12',text:'query12',dictCode:''})
         fieldList.push({type:'string',value:'query13',text:'query13',dictCode:''})
         fieldList.push({type:'string',value:'query14',text:'query14',dictCode:''})
         fieldList.push({type:'string',value:'query15',text:'query15',dictCode:''})
         fieldList.push({type:'string',value:'query16',text:'query16',dictCode:''})
         fieldList.push({type:'string',value:'query17',text:'query17',dictCode:''})
         fieldList.push({type:'string',value:'query18',text:'query18',dictCode:''})
         fieldList.push({type:'string',value:'query19',text:'query19',dictCode:''})
         fieldList.push({type:'string',value:'query20',text:'query20',dictCode:''})
         fieldList.push({type:'string',value:'query21',text:'query21',dictCode:''})
         fieldList.push({type:'string',value:'query22',text:'query22',dictCode:''})
         fieldList.push({type:'string',value:'query23',text:'query23',dictCode:''})
         fieldList.push({type:'string',value:'query24',text:'query24',dictCode:''})
         fieldList.push({type:'string',value:'query25',text:'query25',dictCode:''})
         fieldList.push({type:'string',value:'query26',text:'query26',dictCode:''})
         fieldList.push({type:'string',value:'query27',text:'query27',dictCode:''})
         fieldList.push({type:'string',value:'query28',text:'query28',dictCode:''})
         fieldList.push({type:'string',value:'query29',text:'query29',dictCode:''})
         fieldList.push({type:'string',value:'query30',text:'query30',dictCode:''})
         fieldList.push({type:'double',value:'num01',text:'num01',dictCode:''})
         fieldList.push({type:'double',value:'num02',text:'num02',dictCode:''})
         fieldList.push({type:'double',value:'num03',text:'num03',dictCode:''})
         fieldList.push({type:'double',value:'num04',text:'num04',dictCode:''})
         fieldList.push({type:'double',value:'num05',text:'num05',dictCode:''})
         fieldList.push({type:'string',value:'link01',text:'link01',dictCode:''})
         fieldList.push({type:'string',value:'link02',text:'link02',dictCode:''})
         fieldList.push({type:'string',value:'link03',text:'link03',dictCode:''})
         fieldList.push({type:'string',value:'link04',text:'link04',dictCode:''})
         fieldList.push({type:'string',value:'link05',text:'link05',dictCode:''})
         fieldList.push({type:'string',value:'text01',text:'text01',dictCode:''})
         fieldList.push({type:'string',value:'text02',text:'text02',dictCode:''})
         fieldList.push({type:'string',value:'text03',text:'text03',dictCode:''})
         fieldList.push({type:'string',value:'text04',text:'text04',dictCode:''})
         fieldList.push({type:'string',value:'text05',text:'text05',dictCode:''})
         fieldList.push({type:'string',value:'attr1',text:'attr1',dictCode:''})
         fieldList.push({type:'string',value:'attr2',text:'attr2',dictCode:''})
         fieldList.push({type:'string',value:'attr3',text:'attr3',dictCode:''})
         fieldList.push({type:'string',value:'attr4',text:'attr4',dictCode:''})
         fieldList.push({type:'string',value:'attr5',text:'attr5',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>