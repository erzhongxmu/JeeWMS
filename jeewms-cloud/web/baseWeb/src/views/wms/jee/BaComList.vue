<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="公司中文全称">
              <a-input placeholder="请输入公司中文全称" v-model="queryParam.comZhAname"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="中文地址">
              <a-input placeholder="请输入中文地址" v-model="queryParam.comZhAddr"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="公司英文简称">
                <a-input placeholder="请输入公司英文简称" v-model="queryParam.comEnName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="公司英文全称">
                <a-input placeholder="请输入公司英文全称" v-model="queryParam.comEnAname"></a-input>
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
      <a-button icon="download" @click="handleExportXls('ba_com')">{{$t('导出')}}</a-button>
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

    <baCom-modal ref="modalForm" @ok="modalFormOk"></baCom-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BaComModal from './modules/BaComModal'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'

  export default {
    name: "BaComList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass],
    components: {
      BaComModal
    },
    data () {
      return {
        description: 'ba_com管理页面',
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
            title:'公司代码',
            align: 'left',
            dataIndex: 'comCode'
          },
          {
            title:'公司中文简称',
            align: 'left',
            dataIndex: 'comZhName'
          },
          {
            title:'公司中文全称',
            align: 'left',
            dataIndex: 'comZhAname'
          },
          {
            title:'中文地址',
            align: 'left',
            dataIndex: 'comZhAddr'
          },
          {
            title:'公司英文简称',
            align: 'left',
            dataIndex: 'comEnName'
          },
          {
            title:'公司英文全称',
            align: 'left',
            dataIndex: 'comEnAname'
          }

        ],
        url: {
          list: "/jeewms/baCom/list",
          delete: "/jeewms/baCom/delete",
          deleteBatch: "/jeewms/baCom/deleteBatch",
          exportXlsUrl: "/jeewms/baCom/exportXls",
          importExcelUrl: "jeewms/baCom/importExcel",
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