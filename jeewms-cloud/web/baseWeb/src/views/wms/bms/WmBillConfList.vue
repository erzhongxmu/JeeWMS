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
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>

            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('账单基础信息配置')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
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
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <wm-bill-conf-modal ref="modalForm" @ok="modalFormOk"></wm-bill-conf-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmBillConfModal from './modules/WmBillConfModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'WmBillConfList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      WmBillConfModal
    },
    data () {
      return {
        description: '账单基础信息配置管理页面',
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
            title:'货主',
            align: 'left',
            dataIndex: 'cusCode_dictText'
          },
          {
            title:'logo文件',
            align: 'left',
            dataIndex: 'logoFile',
            scopedSlots: {customRender: 'fileSlot'}
          },
          {
            title:'盖章图片',
            align: 'left',
            dataIndex: 'comfidzyzFile',
            scopedSlots: {customRender: 'fileSlot'}
          },
          {
            title:'名称',
            align: 'left',
            sorter: true,
            dataIndex: 'comfiname'
          },
          {
            title:'地址',
            align: 'left',
            sorter: true,
            dataIndex: 'comfiaddr'
          },
          {
            title:'电话',
            align: 'left',
            sorter: true,
            dataIndex: 'comfitel'
          },
          {
            title:'银行卡号',
            align: 'left',
            dataIndex: 'comfibankid'
          },
          {
            title:'开户行',
            align: 'left',
            dataIndex: 'comfibankname'
          },
          {
            title:'营业执照',
            align: 'left',
            dataIndex: 'comfizhucehao'
          },
          {
            title:'备注1',
            align: 'left',
            dataIndex: 'comfibeizhu1'
          },
          {
            title:'备注2',
            align: 'left',
            dataIndex: 'comfibeizhu2'
          },
          {
            title:'备注3',
            align: 'left',
            dataIndex: 'comfibeizhu3'
          },
          {
            title:'备注4',
            align: 'left',
            dataIndex: 'comfibeizhu4'
          },
          {
            title:'备注5',
            align: 'left',
            dataIndex: 'comfibeizhu5'
          },
          {
            title:'备注6',
            align: 'left',
            dataIndex: 'comfibeizhu6'
          },
          {
            title:'备注7',
            align: 'left',
            dataIndex: 'comfibeizhu7'
          },
          {
            title:'备注8',
            align: 'left',
            dataIndex: 'comfibeizhu8'
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
          list: "/jeebms/wmBillConf/list",
          delete: "/jeebms/wmBillConf/delete",
          deleteBatch: "/jeebms/wmBillConf/deleteBatch",
          exportXlsUrl: "/jeebms/wmBillConf/exportXls",
          importExcelUrl: "jeebms/wmBillConf/importExcel",

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
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'cusCode',text:'货主',dictCode:'md_cus,zhong_wen_qch,ke_hu_bian_ma'})
        fieldList.push({type:'Text',value:'logoFile',text:'logo文件',dictCode:''})
        fieldList.push({type:'Text',value:'comfidzyzFile',text:'盖章图片',dictCode:''})
        fieldList.push({type:'Text',value:'comfiname',text:'名称',dictCode:''})
        fieldList.push({type:'Text',value:'comfiaddr',text:'地址',dictCode:''})
        fieldList.push({type:'Text',value:'comfitel',text:'电话',dictCode:''})
        fieldList.push({type:'Text',value:'comfibankid',text:'银行卡号',dictCode:''})
        fieldList.push({type:'Text',value:'comfibankname',text:'开户行',dictCode:''})
        fieldList.push({type:'Text',value:'comfizhucehao',text:'营业执照',dictCode:''})
        fieldList.push({type:'Text',value:'comfibeizhu1',text:'备注1',dictCode:''})
        fieldList.push({type:'Text',value:'comfibeizhu2',text:'备注2',dictCode:''})
        fieldList.push({type:'Text',value:'comfibeizhu3',text:'备注3',dictCode:''})
        fieldList.push({type:'Text',value:'comfibeizhu4',text:'备注4',dictCode:''})
        fieldList.push({type:'Text',value:'comfibeizhu5',text:'备注5',dictCode:''})
        fieldList.push({type:'Text',value:'comfibeizhu6',text:'备注6',dictCode:''})
        fieldList.push({type:'Text',value:'comfibeizhu7',text:'备注7',dictCode:''})
        fieldList.push({type:'Text',value:'comfibeizhu8',text:'备注8',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>