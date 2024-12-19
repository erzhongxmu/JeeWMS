<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item label="费用日期">
              <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.costData_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.costData_end"></j-date>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="货主名称">
              <a-input placeholder="请输入货主名称" v-model="queryParam.cusName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="费用">
                <a-input placeholder="请输入费用" v-model="queryParam.costCode"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="费用名称">
                <a-input placeholder="请输入费用名称" v-model="queryParam.costName"></a-input>
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
      <!--      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>-->
      <a-button type="primary" icon="download" @click="handleExportXls('费用合计')">{{$t('导出')}}</a-button>
      <!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
              <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
            </a-upload>
            &lt;!&ndash; 高级查询区域 &ndash;&gt;
            <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
            <a-dropdown v-if="selectedRowKeys.length > 0">
              <a-menu slot="overlay">
                <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>
              </a-menu>
              <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>
            </a-dropdown>-->
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
<!--          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">-->
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
          <!--              <a-menu-item>
                          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                            <a>{{$t('删除')}}</a>
                          </a-popconfirm>
                        </a-menu-item>
                      </a-menu>
                    </a-dropdown>-->
        </span>

      </a-table>
    </div>

    <wv-day-cost-sum-modal ref="modalForm" @ok="modalFormOk"></wv-day-cost-sum-modal>
  </a-card>
</template>

<script>

import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WvDayCostSumModal from './modules/WvDayCostSumModal'

export default {
  name: 'WvDayCostSumList',
  mixins:[JeecgListMixin, mixinDevice],
  components: {
    WvDayCostSumModal
  },
  data () {
    return {
      description: 'wv_day_cost_sum管理页面',
      // 表头
      columns: [
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
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          // fixed:"right",
          width:147,
          scopedSlots: { customRender: 'action' }
        },
        {
          title:'费用日期',
          align: 'left',
          dataIndex: 'costData',
          customRender:function (text) {
            return !text?"":(text.length>10?text.substr(0,10):text)
          }
        },
        {
          title:'货主',
          align: 'left',
          dataIndex: 'cusCode'
        },
        {
          title:'货主名称',
          align: 'left',
          dataIndex: 'cusName'
        },
        {
          title:'费用',
          align: 'left',
          dataIndex: 'costCode'
        },
        {
          title:'费用名称',
          align: 'left',
          dataIndex: 'costName'
        },
        {
          title:'是否已结算',
          align: 'left',
          dataIndex: 'costJs'
        },
        {
          title:'原价',
          align: 'left',
          dataIndex: 'yuanj'
        },
        {
          title:'不含税',
          align: 'left',
          dataIndex: 'bhsj'
        },
        {
          title:'税',
          align: 'left',
          dataIndex: 'shuie'
        },
        {
          title:'是否结算',
          align: 'left',
          dataIndex: 'costSl'
        },
        {
          title:'含税价',
          align: 'left',
          dataIndex: 'hansj'
        },
        {
          title:'数量单位',
          align: 'left',
          dataIndex: 'costUnit'
        },

      ],
      url: {
        list: "/jeewms/wvDayCostSum/listy",
        delete: "/jeewms/wvDayCostSum/delete",
        deleteBatch: "/jeewms/wvDayCostSum/deleteBatch",
        exportXlsUrl: "/jeewms/wvDayCostSum/exportXls",
        importExcelUrl: "jeewms/wvDayCostSum/importExcel",

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
      fieldList.push({type:'date',value:'costData',text:'费用日期'})
      fieldList.push({type:'string',value:'cusCode',text:'货主',dictCode:''})
      fieldList.push({type:'string',value:'cusName',text:'货主名称',dictCode:''})
      fieldList.push({type:'string',value:'costCode',text:'费用',dictCode:''})
      fieldList.push({type:'string',value:'costName',text:'费用名称',dictCode:''})
      fieldList.push({type:'string',value:'costJs',text:'是否已结算',dictCode:''})
      fieldList.push({type:'double',value:'yuanj',text:'原价',dictCode:''})
      fieldList.push({type:'double',value:'bhsj',text:'不含税',dictCode:''})
      fieldList.push({type:'double',value:'shuie',text:'税',dictCode:''})
      fieldList.push({type:'double',value:'costSl',text:'是否结算',dictCode:''})
      fieldList.push({type:'double',value:'hansj',text:'含税价',dictCode:''})
      fieldList.push({type:'string',value:'costUnit',text:'数量单位',dictCode:''})
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>