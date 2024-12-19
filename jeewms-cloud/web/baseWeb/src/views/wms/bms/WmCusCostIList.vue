<template>
  <a-card :bordered="false" :class="'cust-erp-sub-tab'">
    <!-- 操作按钮区域 -->
    <div class="table-operator" v-if="mainId">
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('合同项目')">{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel">
          <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
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
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
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
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>
          <a-divider type="vertical" />
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>

    <wmCusCostI-modal ref="modalForm" @ok="modalFormOk" :mainId="mainId"></wmCusCostI-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmCusCostIModal from './modules/WmCusCostIModal'

  export default {
    name: "WmCusCostIList",
    mixins:[JeecgListMixin],
    components: { WmCusCostIModal },
    props:{
      mainId:{
        type:String,
        default:'',
        required:false
      }
    },
    watch:{
      mainId:{
        immediate: true,
        handler(val) {
          if(!this.mainId){
            this.clearList()
          }else{
            this.queryParam['cusCostId'] = val
            this.loadData(1);
          }
        }
      }
    },
    data () {
      return {
        description: '仓储合同管理页面',
        disableMixinCreated:true,
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align: 'left',
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'费用名称',
            align: 'left',
            dataIndex: 'costCode_dictText',
          },
          {
            title:'价格RMB',
            align: 'left',
            dataIndex: 'costJg'
          },
          {
            title:'税率',
            align: 'left',
            dataIndex: 'costSl'
          },
          {
            title:'折扣',
            align: 'left',
            dataIndex: 'costZk'
          },
          {
            title:'不含税价RMB',
            align: 'left',
            dataIndex: 'costBhs'
          },
          {
            title:'含税价RMB',
            align: 'left',
            dataIndex: 'costHs'
          },
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/jeebms/wmCusCostH/listWmCusCostIByMainId",
          delete: "/jeebms/wmCusCostH/deleteWmCusCostI",
          deleteBatch: "/jeebms/wmCusCostH/deleteBatchWmCusCostI",
          exportXlsUrl: "/jeebms/wmCusCostH/exportWmCusCostI",
          importUrl: "/jeebms/wmCusCostH/importWmCusCostI",
        },
        dictOptions:{
        },
        superFieldList:[],
      }
    },
    created() {
      this.getSuperFieldList();
    },
    computed: {
      importExcelUrl(){
        return `${window._CONFIG['domianURL']}/${this.url.importUrl}/${this.mainId}`;
      }
    },
    methods: {
      clearList(){
        this.dataSource=[]
        this.selectedRowKeys=[]
        this.ipagination.current = 1
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'createName',text:'创建人名称',dictCode:''})
        fieldList.push({type:'string',value:'createBy',text:'创建人登录名称',dictCode:''})
        fieldList.push({type:'date',value:'createTime',text:'创建日期'})
        fieldList.push({type:'date',value:'updateTime',text:'更新日期'})
        fieldList.push({type:'sel_search',value:'cusCode',text:'客户编码',dictTable:'md_cus', dictText:'zhong_wen_qch', dictCode:'ke_hu_bian_ma'})
        fieldList.push({type:'date',value:'beginDate',text:'开始日期'})
        fieldList.push({type:'date',value:'endDate',text:'结束日期'})
        fieldList.push({type:'string',value:'cusBeizhu',text:'备注',dictCode:''})
        fieldList.push({type:'string',value:'cusHetongid',text:'合同编号',dictCode:''})
        fieldList.push({type:'string',value:'fujian',text:'附件',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
