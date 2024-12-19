<template>
  <a-card :bordered="false" :class="'cust-erp-sub-tab'">
    <!-- 操作按钮区域 -->
    <div class="table-operator" v-if="mainId">
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls('出货通知项目')">{{$t('导出')}}</a-button>
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
<!--      <a-dropdown>-->
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

    <wmOmOtherI-modal ref="modalForm" @ok="modalFormOk" :mainId="mainId"></wmOmOtherI-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmOmOtherIModal from './modules/WmOmOtherIModal'



  export default {
    name: "WmOmNoticeIList",
    mixins:[JeecgListMixin],
    components: { WmOmOtherIModal },
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
            this.queryParam['omNoticeId'] = val
            this.loadData(1);
          }
        }
      }
    },
    data () {
      return {
        description: '出货通知抬头管理页面',
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
            title:'创建人名称',
            align: 'left',
            dataIndex: 'createName'
          },
          {
            title:'创建人登录名称',
            align: 'left',
            dataIndex: 'createBy'
          },
          {
            title:'创建日期',
            align: 'left',
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'更新人名称',
            align: 'left',
            dataIndex: 'updateName'
          },
          // {
          //   title:'更新人登录名称',
          //   align: 'left',
          //   dataIndex: 'updateBy'
          // },
          {
            title:'更新日期',
            align: 'left',
            dataIndex: 'updateTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          // {
          //   title:'所属部门',
          //   align: 'left',
          //   dataIndex: 'sysOrgCode'
          // },
          // {
          //   title:'所属公司',
          //   align: 'left',
          //   dataIndex: 'sysCompanyCode'
          // },
          {
            title:'出货通知',
            align: 'left',
            dataIndex: 'omNoticeId'
          },
          {
            title:'出货商品',
            align: 'left',
            dataIndex: 'goodsId'
          },
          {
            title:'出货数量',
            align: 'left',
            dataIndex: 'goodsQua'
          },
          // {
          //   title:'出货单位',
          //   align: 'left',
          //   dataIndex: 'goodsUnit'
          // },
          // {
          //   title:'生产日期',
          //   align: 'left',
          //   dataIndex: 'goodsProData',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'批次',
          //   align: 'left',
          //   dataIndex: 'goodsBatch'
          // },
          // {
          //   title:'出货仓位',
          //   align: 'left',
          //   dataIndex: 'binOm'
          // },
          {
            title:'已出货数量',
            align: 'left',
            dataIndex: 'goodsQuaok'
          },
          // {
          //   title:'预约出货时间',
          //   align: 'left',
          //   dataIndex: 'delvData'
          // },
          // {
          //   title:'货主',
          //   align: 'left',
          //   dataIndex: 'cusCode'
          // },
          {
            title:'货主名称',
            align: 'left',
            dataIndex: 'cusName'
          },
          // {
          //   title:'商品名称',
          //   align: 'left',
          //   dataIndex: 'goodsText'
          // },
          // {
          //   title:'波次号',
          //   align: 'left',
          //   dataIndex: 'waveId'
          // },
          // {
          //   title:'状态',
          //   align: 'left',
          //   dataIndex: 'omSta'
          // },
          // {
          //   title:'基本单位',
          //   align: 'left',
          //   dataIndex: 'baseUnit'
          // },
          // {
          //   title:'基本单位数量',
          //   align: 'left',
          //   dataIndex: 'baseGoodscount'
          // },
          // {
          //   title:'下架计划生成',
          //   align: 'left',
          //   dataIndex: 'planSta'
          // },
          {
            title:'商品名称',
            align: 'left',
            dataIndex: 'goodsName'
          },
          // {
          //   title:'otherId',
          //   align: 'left',
          //   dataIndex: 'otherId'
          // },
          // {
          //   title:'binId',
          //   align: 'left',
          //   dataIndex: 'binId'
          // },
          // {
          //   title:'imCusCode',
          //   align: 'left',
          //   dataIndex: 'imCusCode'
          // },
          // {
          //   title:'omBeiZhu',
          //   align: 'left',
          //   dataIndex: 'omBeiZhu'
          // },
          // {
          //   title:'bzhiQi',
          //   align: 'left',
          //   dataIndex: 'bzhiQi'
          // },
          // {
          //   title:'chpShuXing',
          //   align: 'left',
          //   dataIndex: 'chpShuXing'
          // },
          // {
          //   title:'barcode',
          //   align: 'left',
          //   dataIndex: 'barcode'
          // }

        ],
        url: {
          list: "/jeewms/wmOmNoticeH/listWmOmNoticeIByMainId",
          delete: "/jeewms/wmOmNoticeH/deleteWmOmNoticeI",
          deleteBatch: "/jeewms/wmOmNoticeH/deleteBatchWmOmNoticeI",
          exportXlsUrl: "/jeewms/wmOmNoticeH/exportWmOmNoticeI",
          importUrl: "/jeewms/wmOmNoticeH/importWmOmNoticeI",
        },
        dictOptions:{
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
