<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="单号">
              <a-input placeholder="请输入单号" v-model="queryParam.fadh"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="发货人">
              <a-input placeholder="请输入发货人" v-model="queryParam.fahuoren"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="发货人电话">
                <a-input placeholder="请输入发货人电话" v-model="queryParam.fhrdh"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="收货人">
                <a-input placeholder="请输入收货人" v-model="queryParam.shouhuoren"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="收货人电话">
                <a-input placeholder="请输入收货人电话" v-model="queryParam.shrsj"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="收货人地址">
                <a-input placeholder="请输入收货人地址" v-model="queryParam.shrdh"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货物">
                <a-input placeholder="请输入货物" v-model="queryParam.huowu"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主单号">
                <a-input placeholder="请输入货主单号" v-model="queryParam.ywkhdh"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="送货方式">
                <j-dict-select-tag placeholder="请选择送货方式" v-model="queryParam.hwshfs" dictCode="ys_type"/>
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
      <a-button icon="download" @click="handleExportXls('tms_yw_dingdan')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
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

    <tmsYwDingdan-modal ref="modalForm" @ok="modalFormOk"></tmsYwDingdan-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import TmsYwDingdanModal from './modules/TmsYwDingdanModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "TmsYwDingdanList",
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      JDictSelectTag,
      TmsYwDingdanModal
    },
    data () {
      return {
        description: 'tms_yw_dingdan管理页面',
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
          {
            title:'单号',
            align: 'left',
            dataIndex: 'fadh'
          },
          {
            title:'发货人',
            align: 'left',
            dataIndex: 'fahuoren'
          },
          {
            title:'发货人电话',
            align: 'left',
            dataIndex: 'fhrdh'
          },
          {
            title:'发货人地址',
            align: 'left',
            dataIndex: 'fhrdz'
          },
          {
            title:'收货人',
            align: 'left',
            dataIndex: 'shouhuoren'
          },
          {
            title:'收货人电话',
            align: 'left',
            dataIndex: 'shrsj'
          },
          {
            title:'收货人地址',
            align: 'left',
            dataIndex: 'shrdh'
          },
          {
            title:'货物',
            align: 'left',
            dataIndex: 'huowu'
          },
          {
            title:'长米',
            align: 'left',
            dataIndex: 'chang'
          },
          {
            title:'宽米',
            align: 'left',
            dataIndex: 'kuan'
          },
          {
            title:'高米',
            align: 'left',
            dataIndex: 'gao'
          },
          {
            title:'立方米',
            align: 'left',
            dataIndex: 'tiji'
          },
          {
            title:'重量',
            align: 'left',
            dataIndex: 'zhongl'
          },
          {
            title:'代收款金额',
            align: 'left',
            dataIndex: 'daishouk'
          },
          {
            title:'是否等通知',
            align: 'left',
            dataIndex: 'dengtongzhi_dictText'
          },
          {
            title:'状态',
            align: 'left',
            dataIndex: 'zhuangtai_dictText'
          },
          {
            title:'货主单号',
            align: 'left',
            dataIndex: 'ywkhdh'
          },
          {
            title:'送货方式',
            align: 'left',
            dataIndex: 'hwshfs_dictText'
          }

        ],
        url: {
          list: "/jeewms/tmsYwDingdan/list",
          delete: "/jeewms/tmsYwDingdan/delete",
          deleteBatch: "/jeewms/tmsYwDingdan/deleteBatch",
          exportXlsUrl: "/jeewms/tmsYwDingdan/exportXls",
          importExcelUrl: "jeewms/tmsYwDingdan/importExcel",
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
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>