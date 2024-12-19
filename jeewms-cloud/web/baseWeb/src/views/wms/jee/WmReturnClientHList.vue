<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item label="预计到货时间">
              <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.imData_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.imData_end"></j-date>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="订单号">
              <a-input placeholder="请输入订单号" v-model="queryParam.imCusCode"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="司机">
                <a-input placeholder="请输入司机" v-model="queryParam.imCarDri"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="司机电话">
                <a-input placeholder="请输入司机电话" v-model="queryParam.imCarMobile"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="车号">
                <a-input placeholder="请输入车号" v-model="queryParam.imCarNo"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="月台">
                <j-dict-select-tag placeholder="请选择月台" v-model="queryParam.platformCode" dictCode="ba_platform,platform_name,platform_code"/>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
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
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'returnClient:add'">{{$t('添加')}}</a-button>
<!--      <a-button type="primary" icon="download" @click="handleExportXls('wm_im_notice_h')">{{$t('导出')}}</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>-->
<!--      </a-upload>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
      <a-button key="1" @click="batchDel" v-has="'returnClient:batchDelete'"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>
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
        class="j-table-force-nowrap"
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
          <a @click="handleCheck(record)" v-has="'returnClient:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'returnClient:check'"/>
          <a @click="handleEdit(record)" v-has="'returnClient:update'">{{$t('编辑')}}</a>

          <a-divider type="vertical"  v-has="'returnClient:update'"/>
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)" v-has="'returnClient:delete'">
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
<!--          <a-dropdown>-->
<!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
<!--            <a-menu slot="overlay">-->
<!--              <a-menu-item>-->
<!--                -->
<!--              </a-menu-item>-->
<!--            </a-menu>-->
<!--          </a-dropdown>-->
        </span>

      </a-table>
    </div>

    <WmReturnClientH-modal ref="modalForm" @ok="modalFormOk"></WmReturnClientH-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmReturnClientHModal from './modules/WmReturnClientHModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JDate from '@/components/jeecg/JDate.vue'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import '@/assets/less/TableExpand.less'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'

  export default {
    name: "WmImNoticeHList",
    mixins:[JeecgListMixin,commonTableRowClass,listCheckMixin],
    components: {
      JDictSelectTag,
      JDate,
      WmReturnClientHModal
    },
    data () {
      return {
        description: 'wm_im_notice_h管理页面',
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            width: 147,
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
          // {
          //   title:'创建人名称',
          //   align: 'left',
          //   dataIndex: 'createBy'
          // },
          // {
          //   title:'创建日期',
          //   align: 'left',
          //   dataIndex: 'createTime',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          {
            title:'货主名称',
            align: 'left',
            dataIndex: 'cusCode_dictText'
          },
          {
            title:'预计到货时间',
            align: 'left',
            dataIndex: 'imData',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'订单号',
            align: 'left',
            dataIndex: 'imCusCode'
          },
          {
            title:'司机',
            align: 'left',
            dataIndex: 'imCarDri'
          },
          {
            title:'司机电话',
            align: 'left',
            dataIndex: 'imCarMobile'
          },
          {
            title:'车号',
            align: 'left',
            dataIndex: 'imCarNo'
          },
          {
            title:'订单类型',
            align: 'left',
            dataIndex: 'orderTypeCode_dictText'
          },
          {
            title:'月台',
            align: 'left',
            dataIndex: 'platformCode_dictText'
          },
          {
            title:'备注',
            align: 'left',
            dataIndex: 'imBeizhu'
          },
          {
            title:'单据状态',
            align: 'left',
            dataIndex: 'imSta'
          },
          {
            title:'进货通知单号',
            align: 'left',
            dataIndex: 'noticeId'
          },
          {
            title:'附件',
            align: 'left',
            dataIndex: 'fuJian',
            scopedSlots: {customRender: 'fileSlot'}
          },
          {
            title:'货主编码',
            align: 'left',
            dataIndex: 'supCode_dictText'
          },
          {
            title:'货主名称',
            align: 'left',
            dataIndex: 'supName'
          }
        ],
        url: {
          list: "/jeewms/wmImNoticeH/thList",
          delete: "/jeewms/wmImNoticeH/delete",
          deleteBatch: "/jeewms/wmImNoticeH/deleteBatch",
          exportXlsUrl: "/jeewms/wmImNoticeH/exportXls",
          importExcelUrl: "jeewms/wmImNoticeH/importExcel",
        },
        dictOptions:{},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>