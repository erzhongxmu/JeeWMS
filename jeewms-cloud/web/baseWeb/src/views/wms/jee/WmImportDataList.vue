<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query02"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query03"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query04"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('托盘')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query07"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('仓库')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query11"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('PO号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query08"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" >{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
      <a-button type="primary" icon="download" @click="handleExportXls('wm_import_data')" v-has="'WmImportDataList:download'">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'WmImportDataList:import'">{{$t('导入入库')}}</a-button>
      </a-upload>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl2" @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'WmImportDataList:import2'">{{$t('导入出库')}}</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项')}}
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
          <!-- <a @click="handleEdit(record)">编辑</a> -->

          <!-- <a-divider type="vertical" /> -->
          <a-dropdown>
            <a class="ant-dropdown-link">{{$t('更多')}} <a-icon type="down" /></a>
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
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <wm-import-data-modal ref="modalForm" @ok="modalFormOk"></wm-import-data-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmImportDataModal from './modules/WmImportDataModal'
import { postAction } from '../../../api/manage'

  export default {
    name: 'WmImportDataList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      WmImportDataModal
    },
    data () {
      return {
        description: 'wm_import_data管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          
          // {
          //   title:'所属部门',
          //   align:"center",
          //   dataIndex: 'sysOrgCode'
          // },
          
          {
            title:this.$t('商品编码'),
            align:"center",
            dataIndex: 'query02'
          },
          {
            title:this.$t('商品名称'),
            align:"center",
            dataIndex: 'query03'
          },
          {
            title:this.$t('客户名称'),
            align:"center",
            dataIndex: 'query04'
          },
          {
            title:this.$t('数量'),
            align:"center",
            dataIndex: 'query05'
          },
          {
            title:this.$t('单位'),
            align:"center",
            dataIndex: 'query06'
          },
          {
            title:this.$t('托盘'),
            align:"center",
            dataIndex: 'query07'
          },
          {
            title:this.$t('PO号'),
            align:"center",
            dataIndex: 'query08'
          },
          {
            title:this.$t('体积'),
            align:"center",
            dataIndex: 'query09'
          },
          {
            title:this.$t('重量'),
            align:"center",
            dataIndex: 'query10'
          },
          {
            title:this.$t('仓库'),
            align:"center",
            dataIndex: 'query11'
          },
          // {
          //   title:this.$t('租户ID'),
          //   align:"center",
          //   dataIndex: 'tenantId'
          // },
          {
            title:this.$t('出入库类型'),
            align:"center",
            dataIndex: 'query01'
          },{
            title:this.$t('创建人名称'),
            align:"center",
            dataIndex: 'createName'
          },
          {
            title:this.$t('创建日期'),
            align:"center",
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:this.$t('备注'),
            align:"center",
            dataIndex: 'remark'
          },
          {
            title:this.$t('操作'),
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
          // {
          //   title:this.$t('query12'),
          //   align:"center",
          //   dataIndex: 'query12'
          // },
          // {
          //   title:'query13',
          //   align:"center",
          //   dataIndex: 'query13'
          // },
          // {
          //   title:'query14',
          //   align:"center",
          //   dataIndex: 'query14'
          // },
          // {
          //   title:'query15',
          //   align:"center",
          //   dataIndex: 'query15'
          // },
          // {
          //   title:'query16',
          //   align:"center",
          //   dataIndex: 'query16'
          // },
          // {
          //   title:'query17',
          //   align:"center",
          //   dataIndex: 'query17'
          // },
          // {
          //   title:'query18',
          //   align:"center",
          //   dataIndex: 'query18'
          // },
          // {
          //   title:'query19',
          //   align:"center",
          //   dataIndex: 'query19'
          // },
          // {
          //   title:'query20',
          //   align:"center",
          //   dataIndex: 'query20'
          // },
          // {
          //   title:'备注',
          //   align:"center",
          //   dataIndex: 'remarks'
          // },
          // {
          //   title:'创建人登录名称',
          //   align:"center",
          //   dataIndex: 'createBy'
          // },
          // {
          //   title:'更新人名称',
          //   align:"center",
          //   dataIndex: 'updateName'
          // },
          // {
          //   title:'更新人登录名称',
          //   align:"center",
          //   dataIndex: 'updateBy'
          // },
          // {
          //   title:'更新日期',
          //   align:"center",
          //   dataIndex: 'updateTime',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'所属公司',
          //   align:"center",
          //   dataIndex: 'sysCompanyCode'
          // }
        ],
        url: {
          list: "/jeewms/wmImportData/list",
          delete: "/jeewms/wmImportData/delete",
          deleteBatch: "/jeewms/wmImportData/deleteBatch",
          exportXlsUrl: "/jeewms/wmImportData/exportXls",
          importExcelUrl: "jeewms/wmImportData/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}?imType=入库`;
      },
      importExcelUrl2: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}?imType=出库`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'createName',text:'创建人名称',dictCode:''})
        fieldList.push({type:'string',value:'createBy',text:'创建人登录名称',dictCode:''})
        fieldList.push({type:'date',value:'createTime',text:'创建日期'})
        fieldList.push({type:'string',value:'updateName',text:'更新人名称',dictCode:''})
        fieldList.push({type:'string',value:'updateBy',text:'更新人登录名称',dictCode:''})
        fieldList.push({type:'date',value:'updateTime',text:'更新日期'})
        fieldList.push({type:'string',value:'sysOrgCode',text:'所属部门',dictCode:''})
        fieldList.push({type:'string',value:'sysCompanyCode',text:'所属公司',dictCode:''})
        fieldList.push({type:'string',value:'query01',text:'query01',dictCode:''})
        fieldList.push({type:'string',value:'query02',text:'query02',dictCode:''})
        fieldList.push({type:'string',value:'query03',text:'query03',dictCode:''})
        fieldList.push({type:'string',value:'query04',text:'query04',dictCode:''})
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
        fieldList.push({type:'int',value:'tenantId',text:'租户ID',dictCode:''})
        fieldList.push({type:'string',value:'remark',text:'备注',dictCode:''})
        fieldList.push({type:'string',value:'remarks',text:'备注',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>