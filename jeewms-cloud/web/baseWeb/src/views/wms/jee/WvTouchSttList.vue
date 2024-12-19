<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item :label="$t('移动日期')">
              <j-date :placeholder="$t('开始日期')" class="query-group-cust" v-model="queryParam.lastMove_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date :placeholder="$t('结束日期')"  class="query-group-cust" v-model="queryParam.lastMove_end"></j-date>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('库位编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.kuWeiBianMa"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsId"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('商品名称')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.shpMingCheng"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('货主编码')">
            <j-popup
              v-model="queryParam.cusCode"
              code="cus_jiancheng"
              org-fields="zhong_wen_qch,ke_hu_bian_ma"  
              dest-fields="cusName,cusCode"
              field="cusCode"/>
              </a-form-item>
            </a-col>
            <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('货主编码')">
                <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,ke_hu_bian_ma,ke_hu_bian_ma" :placeholder="$t('请选择')" />
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('货主名称')">
                <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="$t('请选择')" />
              </a-form-item>
            </a-col> -->
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('子PO号')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsBatch"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('托盘')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.binId"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button  @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}
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
<!--      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>-->
<!--      <a-button icon="download" @click="handleExportXls('wv_stock_stt')">{{$t('导出')}}</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
<!--      </a-upload>-->
<!--      <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>-->
      <a-button @click="batchCreat" v-has="'touchStt:batchCreate'">{{$t('明盘')}}</a-button>
      <a-button @click="batchCreat1" v-has="'touchStt:batchCreate'">{{$t('暗盘')}}</a-button>
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
<!--          <a-menu-item key="2" @click="batchCreat">批量生成</a-menu-item>-->
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
        class="j-table-force-nowrap"
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
          <a @click="generate(record.id)" v-if="record.sttSta != '计划中'" v-has="'touchStt:create'">{{$t('生成')}}</a>
        </span>

      </a-table>
    </div>

    <wvTouchStt-modal ref="modalForm" @ok="modalFormOk"></wvTouchStt-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { postAction } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WvTouchSttModal from './modules/WvTouchSttModal'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import JDate from '@/components/jeecg/JDate.vue'
  import JSearchSelectTag from "@/components/dict/JSearchSelectTag"

  export default {
    name: "WvStockSttList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass],
    components: {
      WvTouchSttModal,
      JDate,
      JSearchSelectTag
    },
    data () {
      return {
        description: 'wv_stock_stt管理页面',
        // 表头
        columns: [
          // {
          //   title: this.$t('操作'),
          //   dataIndex: 'action',
          //   align: 'left',
          //   // fixed:"right",
          //   width:147,
          //   scopedSlots: { customRender: 'action' }
          // },
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
          // {
          //   title:'库存类型',
          //   align: 'left',
          //   dataIndex: 'kuctype'
          // },
          {
            title:this.$t('库位编码'),
            align: 'left',
            dataIndex: 'kuWeiBianMa',
          sorter: true
          },
          {
            title:this.$t('客户'),
            align: 'left',
            dataIndex: 'zhongWenQch',
          sorter: true
          },
          {
            title:this.$t('商品编码'),
            align: 'left',
            dataIndex: 'goodsId',
          sorter: true
          },
          {
            title:this.$t('商品名称'),
            align: 'left',
            dataIndex: 'shpMingCheng',
          sorter: true
          },
          {
            title:this.$t('数量'),
            align: 'left',
            dataIndex: 'goodsQua',
          sorter: true
          },
           {
            title:this.$t('子PO号'),
            align: 'left',
            dataIndex: 'goodsBatch',
          sorter: true
          },
           {
            title:this.$t('托盘'),
            align: 'left',
            dataIndex: 'binId',
          sorter: true
          },
          {
            title:this.$t('生产日期'),
            align: 'left',
            dataIndex: 'goodsProData',
          sorter: true
          },
          // {
          //   title:this.$t('保质期'),
          //   align: 'left',
          //   dataIndex: 'bzhiQi',
          // sorter: true
          // },
          {
            title:this.$t('单位'),
            align: 'left',
            dataIndex: 'goodsUnit',
          sorter: true
          },
          {
            title:this.$t('状态'),
            align: 'left',
            dataIndex: 'sttSta',
          sorter: true
          },
          {
            title:this.$t('移动日期'),
            align: 'left',
            dataIndex: 'lastMove',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            },
          sorter: true
          }

        ],
        url: {
          list: "/jeewms/wvStockStt/list",
          delete: "/jeewms/wvStockStt/delete",
          deleteBatch: "/jeewms/wvStockStt/deleteBatch",
          exportXlsUrl: "/jeewms/wvStockStt/exportXls",
          importExcelUrl: "jeewms/wvStockStt/importExcel",
          generate: "/jeewms/wvStockStt/generate",
          generate1: "/jeewms/wvStockStt/generate1",
        },
        dictOptions:{},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      generate(id) {
        postAction(this.url.generate, [id]).then((res) => {
          if (res.success) {
            this.$message.success('生成成功');
            this.loadData()
          }
          if(res.code===510){
            this.$message.warning(this.$t('操作失败'))
          }
        })
      },
      batchCreat() {
        if(this.selectedRowKeys.length < 1) {
          this.$message.warning(this.$t('请选择一条记录!'));
          return
        }
        var ids = this.selectedRowKeys;
        postAction(this.url.generate, ids).then((res) => {
          if (res.success) {
            this.$message.success('生成成功');
            this.loadData()
          }
          if(res.code===510){
            this.$message.warning(this.$t('操作失败'))
          }
        })
      },
      batchCreat1() {
        if(this.selectedRowKeys.length < 1) {
          this.$message.warning(this.$t('请选择一条记录!'));
          return
        }
        var ids = this.selectedRowKeys;
        postAction(this.url.generate1, ids).then((res) => {
          if (res.success) {
            this.$message.success('生成成功');
            this.loadData()
          }
          if(res.code===510){
            this.$message.warning(this.$t('操作失败'))
          }
        })
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
  .query-group-cust{
    width: 44% !important;
  }
</style>