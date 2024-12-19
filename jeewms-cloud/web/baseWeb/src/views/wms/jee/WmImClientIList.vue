<template>
  <a-card :bordered="false" :class="'cust-erp-sub-tab'">
    <!-- 操作按钮区域 -->
    <div class="table-operator" v-if="mainId">
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls('商品')">{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel">
          <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
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
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :rowClassName="setRowClsaa">

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
            @click="uploadFile(text)">
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

    <wmImNoticeI-modal ref="modalForm" @ok="modalFormOk" :mainId="mainId"></wmImNoticeI-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmImNoticeIModal from './modules/WmImNoticeIModal'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'



  export default {
    name: "WmImNoticeIList",
    mixins:[JeecgListMixin,commonTableRowClass],
    components: { WmImNoticeIModal },
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
            this.queryParam['imNoticeId'] = val
            this.loadData(1);
          }
        }
      }
    },
    data () {
      return {
        description: 'wm_im_notice_h管理页面',
        disableMixinCreated:true,
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            scopedSlots: { customRender: 'action' },
          },
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align: 'left',
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          {
            title:'到货通知单号',
            align: 'left',
            dataIndex: 'imNoticeId'
          },
          {
            title:'到货通知项目',
            align: 'left',
            dataIndex: 'imNoticeItem'
          },
          {
            title:'商品编码',
            align: 'left',
            dataIndex: 'goodsCode'
          },
          {
            title:'数量',
            align: 'left',
            dataIndex: 'goodsCount'
          },
          {
            title:'生产日期',
            align: 'left',
            dataIndex: 'goodsPrdData',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'批次',
            align: 'left',
            dataIndex: 'goodsBatch'
          },
          {
            title:'库位整理',
            align: 'left',
            dataIndex: 'binPre'
          },
          {
            title:'体积',
            align: 'left',
            dataIndex: 'goodsFvol'
          },
          {
            title:'重量',
            align: 'left',
            dataIndex: 'goodsWeight'
          },
          {
            title:'计划库位',
            align: 'left',
            dataIndex: 'binPlan'
          },
          {
            title:'单位',
            align: 'left',
            dataIndex: 'goodsUnit'
          },
          {
            title:'未清数量',
            align: 'left',
            dataIndex: 'goodsWqmCount'
          },
          {
            title:'收货登记数量',
            align: 'left',
            dataIndex: 'goodsQmCount'
          },
          {
            title:'行项目状态',
            align: 'left',
            dataIndex: 'noticeiSta'
          },
          {
            title:'基本单位',
            align: 'left',
            dataIndex: 'baseUnit'
          },
          {
            title:'基本单位数量',
            align: 'left',
            dataIndex: 'baseGoodscount'
          },
          {
            title:'基本单位收货数量',
            align: 'left',
            dataIndex: 'baseQmcount'
          },
          {
            title:'规格',
            align: 'left',
            dataIndex: 'shpGuiGe'
          },
          {
            title:'产品属性',
            align: 'left',
            dataIndex: 'chpShuXing'
          }

        ],
        url: {
          list: "/jeewms/wmImNoticeH/listWmImNoticeIByMainId",
          delete: "/jeewms/wmImNoticeH/deleteWmImNoticeI",
          deleteBatch: "/jeewms/wmImNoticeH/deleteBatchWmImNoticeI",
          exportXlsUrl: "/jeewms/wmImNoticeH/exportWmImNoticeI",
          importUrl: "/jeewms/wmImNoticeH/importWmImNoticeI",
        },
        dictOptions:{
         cusCode:[],
         orderTypeCode:[],
         platformCode:[],
         supCode:[],
        },

      }
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

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>
