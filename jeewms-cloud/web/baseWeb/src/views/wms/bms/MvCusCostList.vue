<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="客户编码">
              <a-input placeholder="请输入客户编码" v-model="queryParam.cusCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="中文全称">
              <a-input placeholder="请输入中文全称" v-model="queryParam.cusName"></a-input>
            </a-form-item>
          </a-col>
             <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item label="结算周期">
                <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.costData_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.costData_end"></j-date>
              </a-form-item>
            </a-col>
           <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>

            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

<!--    &lt;!&ndash; 操作按钮区域 &ndash;&gt;-->
<!--    <div class="table-operator">-->
<!--      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>-->
<!--      <a-button type="primary" icon="download" @click="handleExportXls('客户账单')">{{$t('导出')}}</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>-->
<!--      </a-upload>-->
<!--      &lt;!&ndash; 高级查询区域 &ndash;&gt;-->
<!--      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
<!--    </div>-->

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项目')}}
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
          <a-popconfirm title="确定导出期间账单吗?" @confirm="() => handleDelete(record.id,'Y')">
                  <a>导出期间账单</a>
                </a-popconfirm>
          <a-divider type="vertical" />
           <a-popconfirm title="确定导出未清账单吗?" @confirm="() => handleDelete(record.id,'N')">
                  <a>导出未清账单</a>
                </a-popconfirm>
        </span>
      </a-table>
    </div>
    <mv-cus-cost-modal ref="modalForm" @ok="modalFormOk"></mv-cus-cost-modal>
  </a-card>
</template>

<script>
  import {getAction} from '@/api/manage'
  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MvCusCostModal from './modules/MvCusCostModal'

  export default {
    name: 'MvCusCostList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      MvCusCostModal
    },
    data () {
      return {
        description: '客户账单管理页面',
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
            title:'客户编码',
            align: 'left',
            dataIndex: 'cusCode'
          },
          {
            title:'中文全称',
            align: 'left',
            dataIndex: 'cusName'
          },
          {
            title:'最后结算日期',
            align: 'left',
            dataIndex: 'costData',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/jeebms/mvCusCost/list",
          delete: "/jeebms/mvCusCost/delete",
          deleteBatch: "/jeebms/mvCusCost/deleteBatch",
          exportXlsUrl: "/jeebms/mvCusCost/exportXls",
          importExcelUrl: "jeebms/mvCusCost/importExcel",
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
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      handleDelete(id,type){
        // let data = {
        //   begindate:this.queryParam.costData_begin,
        //   id:id,
        //   sfqj:type,
        //   enddate:this.queryParam.costData_end,
        // }
        let url = window._CONFIG['domianURL'] + `/jeebms/mvCusCost/doPrint?id=${id}&begindate=${this.queryParam.costData_begin}&enddate=${this.queryParam.costData_end}&sfqj=${type}&`
          window.open(url);

      },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'cusCode',text:'客户编码',dictCode:''})
        fieldList.push({type:'string',value:'cusName',text:'中文全称',dictCode:''})
        fieldList.push({type:'date',value:'costData',text:'costData'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>