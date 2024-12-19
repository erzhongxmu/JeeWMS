<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="货主">
              <j-dict-select-tag placeholder="请选择货主" v-model="queryParam.cusCode" dictCode="md_cus,zhong_wen_qch,id"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="费用名称">
              <a-input placeholder="请输入费用名称" v-model="queryParam.costName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item label="费用日期">
                <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.costData_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.costData_end"></j-date>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="费用来源">
                <a-input placeholder="请输入费用来源" v-model="queryParam.costOri"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="状态">
                <a-input placeholder="请输入状态" v-model="queryParam.costSta"></a-input>
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
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls('收款')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>
<!--          <a-menu-item key="2" @click="batchGathering"><a-icon type="bank"/>收款</a-menu-item>-->
          <a-menu-item key="3" @click="stopGathering"><a-icon type="stop"/>收款取消</a-menu-item>
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

    <wmDayCost-modal ref="modalForm" @ok="modalFormOk"></wmDayCost-modal>
  </a-card>
</template>

<script>

import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { putAction } from '@/api/manage'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmDayCostModal from './modules/WmDayCostModal'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import JDate from '@/components/jeecg/JDate.vue'
import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

export default {
  name: "WmDayCostskList",
  mixins:[JeecgListMixin, mixinDevice],
  components: {
    JDictSelectTag,
    JDate,
    WmDayCostModal
  },
  data () {
    return {
      description: '收款管理页面',
      // 表头
      columns: [
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          // fixed:"right",
          width:147,
          scopedSlots: { customRender: 'action' }
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
        //   dataIndex: 'createName'
        // },
        // {
        //   title:'创建人登录名称',
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
        // {
        //   title:'更新人名称',
        //   align: 'left',
        //   dataIndex: 'updateName'
        // },
        // {
        //   title:'更新人登录名称',
        //   align: 'left',
        //   dataIndex: 'updateBy'
        // },
        // {
        //   title:'更新日期',
        //   align: 'left',
        //   dataIndex: 'updateTime',
        //   customRender:function (text) {
        //     return !text?"":(text.length>10?text.substr(0,10):text)
        //   }
        // },
        {
          title:'货主',
          align: 'left',
          dataIndex: 'cusCode_dictText'
        },
        {
          title:'费用',
          align: 'left',
          dataIndex: 'costCode'
        },
        {
          title:'费用名称',
          align: 'left',
          dataIndex: 'costName_dictText'
        },
        {
          title:'费用日期',
          align: 'left',
          dataIndex: 'costData',
          customRender:function (text) {
            return !text?"":(text.length>10?text.substr(0,10):text)
          }
        },
        // {
        //   title:'每日费用',
        //   align: 'left',
        //   dataIndex: 'dayCostYj'
        // },
        {
          title:'不含税价',
          align: 'left',
          dataIndex: 'dayCostBhs'
        },
        {
          title:'税额',
          align: 'left',
          dataIndex: 'dayCostSe'
        },
        {
          title:'含税价',
          align: 'left',
          dataIndex: 'dayCostHsj'
        },
        {
          title:'费用来源',
          align: 'left',
          dataIndex: 'costOri'
        },
        // {
        //   title:'备注',
        //   align: 'left',
        //   dataIndex: 'beizhu'
        // },
        // {
        //   title:'状态',
        //   align: 'left',
        //   dataIndex: 'costSta'
        // },
        // {
        //   title:'计费数量',
        //   align: 'left',
        //   dataIndex: 'costSl'
        // },
        // {
        //   title:'数量单位',
        //   align: 'left',
        //   dataIndex: 'costUnit'
        // },
        // {
        //   title:'是否已结算',
        //   align: 'left',
        //   dataIndex: 'costJs_dictText'
        // }

      ],
      url: {
        list: "/jeewms/wmDayCost/datagridsk",
        delete: "/jeewms/wmDayCost/delete",
        deleteBatch: "/jeewms/wmDayCost/deleteBatch",
        exportXlsUrl: "/jeewms/wmDayCost/exportXls",
        importExcelUrl: "jeewms/wmDayCost/importExcel",
        dopljq: "/jeewms/wmDayCost/dopljq",
        doplfjq: "/jeewms/wmDayCost/doplfjq",
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
    batchGathering() {
      var that = this
      putAction(that.url.dopljq,that.selectedRowKeys).then((res) => {
        if (res.success) {
          that.$message.success('费用结清成功')
          that.loadData();
        } else {
          that.$message.warning(this.$t('操作失败'))
        }
      })
    },
    stopGathering() {
      var that = this
      putAction(that.url.doplfjq,that.selectedRowKeys).then((res) => {
        if (res.success) {
          that.$message.success('费用反结清成功')
          that.loadData();
        } else {
          that.$message.warning(this.$t('操作失败'))
        }
      })
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>