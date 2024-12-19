<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="托盘">
              <a-input placeholder="请输入托盘" v-model="queryParam.tinId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="仓位">
              <a-input placeholder="请输入仓位" v-model="queryParam.binId"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="是否已下架">
                <j-dict-select-tag placeholder="请选择是否已下架" v-model="queryParam.binSta" dictCode="sf_yn"/>
              </a-form-item>
            </a-col>
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="货主">-->
<!--                <j-dict-select-tag placeholder="请选择货主" v-model="queryParam.cusCode" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma"/>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主名称">
                <a-input placeholder="请输入货主名称" v-model="queryParam.cusName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="商品名称">
                <a-input placeholder="请输入商品名称" v-model="queryParam.goodsName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="波次编号">
                <a-input placeholder="请输入波次编号" v-model="queryParam.waveId"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
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
      <a-button icon="download" @click="handleExportXls('下架任务')" v-has="'omDelete:export'">{{$t('导出')}}</a-button>
<!--      <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>-->
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

<!--        <span slot="action" slot-scope="text, record">-->
<!--          <a @click="handleCheck(record)">{{$t('查看')}}</a>-->
<!--          <a-divider type="vertical"/>-->
<!--          <a @click="handleEdit(record)">{{$t('编辑')}}</a>-->
<!--          <a-divider type="vertical" />-->
<!--           <a @click="searchDetail(record.id)">{{$t('查看')}}</a>-->
<!--          <a-divider type="vertical" />-->
<!--           <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">-->
<!--                  <a>{{$t('删除')}}</a>-->
<!--           </a-popconfirm>-->
<!--        </span>-->

      </a-table>
    </div>
<!--    <a-tabs defaultActiveKey="1">-->
<!--      <a-tab-pane tab="下架明细" key="1" >-->
<!--        <WmOmQmDeleteSecondList :mainId="selectedMainId" />-->
<!--      </a-tab-pane>-->
<!--    </a-tabs>-->

    <wmOmDelete-modal ref="modalForm" @ok="modalFormOk"></wmOmDelete-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmOmDeleteModal from './modules/WmOmDeleteModal'
  import WmOmQmDeleteSecondList from './WmOmQmDeleteSecondList'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'

  export default {
    name: "WmOmQmIList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
    components: {
      JDictSelectTag,
      WmOmDeleteModal,
      WmOmQmDeleteSecondList
    },
    data () {
      return {
        description: '下架任务管理页面',
        // 表头
        columns: [
          // {
          //   title: this.$t('操作'),
          //   dataIndex: 'action',
          //   align: 'left',
          //   // fixed:"right",
          //   width:200,
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
          {
            title:'创建人名称',
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
            title:'出货通知单',
            align: 'left',
            dataIndex: 'omNoticeId'
          },
          {
            title:'商品编码',
            align: 'left',
            dataIndex: 'goodsId'
          },
          {
            title:'出货数量',
            align: 'left',
            dataIndex: 'omQuat'
          },
          {
            title:'数量',
            align: 'left',
            dataIndex: 'qmOkQuat'
          },
          {
            title:'生产日期',
            align: 'left',
            dataIndex: 'proData'
          },
          {
            title:'托盘',
            align: 'left',
            dataIndex: 'tinId'
          },
          // {
          //   title:'单位',
          //   align: 'left',
          //   dataIndex: 'goodsUnit'
          // },
          // {
          //   title:'批次',
          //   align: 'left',
          //   dataIndex: 'goodsBatch'
          // },
          {
            title:'仓位',
            align: 'left',
            dataIndex: 'binId'
          },
          // {
          //   title:'体积',
          //   align: 'left',
          //   dataIndex: 'tinTj'
          // },
          // {
          //   title:'重量',
          //   align: 'left',
          //   dataIndex: 'tinZhl'
          // },
          {
            title:'是否已下架',
            align: 'left',
            dataIndex: 'binSta_dictText'
          },
          {
            title:'货主名称',
            align: 'left',
            dataIndex: 'cusCode_dictText'
          },
          // {
          //   title:'温度',
          //   align: 'left',
          //   dataIndex: 'recDeg'
          // },
          // {
          //   title:'任务接收人',
          //   align: 'left',
          //   dataIndex: 'assignTo'
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
          //   title:'货主名称',
          //   align: 'left',
          //   dataIndex: 'cusName'
          // },
          {
            title:'商品名称',
            align: 'left',
            dataIndex: 'goodsName'
          },
          // {
          //   title:'波次编号',
          //   align: 'left',
          //   dataIndex: 'waveId'
          // },
          {
            title:'订单号',
            align: 'left',
            dataIndex: 'imCusCode'
          },
          {
            title:'备注',
            align: 'left',
            dataIndex: 'itemText'
          }

        ],
        url: {
          list: "/jeewms/wmOmQmI/list",
          delete: "/jeewms/wmOmQmI/delete",
          deleteBatch: "/jeewms/wmOmQmI/deleteBatch",
          exportXlsUrl: "/jeewms/wmOmQmI/exportXls",
          importExcelUrl: "jeewms/wmOmQmI/importExcel",
        },
        dictOptions:{},
        selectedMainId: '',
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
      //查看
      searchDetail(id) {
        console.log(id)
        this.selectedMainId = id
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>