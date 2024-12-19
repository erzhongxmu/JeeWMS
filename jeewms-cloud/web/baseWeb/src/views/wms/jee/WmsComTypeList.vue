<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('企业属性代码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.comTypeCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('企业属性名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.comTypeName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
              <!--              <a @click="handleToggleSearch" style="margin-left: 8px">-->
              <!--                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}-->
              <!--                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
              <!--              </a>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'WmsComTypeList:add'">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls($t('企业属性'))" v-has="'WmsComTypeList:export'">{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
        v-has="'WmsComTypeList:import'"
      >
        <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel" v-has="'WmsComTypeList:batchDelete'">
        <a-icon type="delete" />
        {{$t('批量删除')}}
      </a-button>
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
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>
        {{$t('项目')}}
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
        :rowClassName="setRowClsaa"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt
            style="max-width:80px;font-size: 12px;font-style: italic;"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)"
          >下载</a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-has="'WmsComTypeList:edit'">{{$t('编辑')}}</a>
          <a-divider type="vertical" v-has="'WmsComTypeList:Delete'"/>
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
            <a v-has="'WmsComTypeList:Delete'">{{$t('删除')}}</a>
          </a-popconfirm>
          <!-- <a-divider type="vertical" />
          <a v-if="record.comTypeDel != '1'" @click="handleAudit(record.id,'1')">{{$t('停用')}}</a>
          <a v-if="record.comTypeDel != '0'" @click=" handleAudit(record.id,'0')">{{$t('启用')}}</a>-->
        </span>
      </a-table>
    </div>

    <wmsComType-modal ref="modalForm" @ok="modalFormOk"></wmsComType-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmsComTypeModal from './modules/WmsComTypeModal'
import { postAction } from '../../../api/manage'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'

export default {
  name: 'WmsComTypeList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass],
  components: {
    WmsComTypeModal
  },
  data() {
    return {
      description: 'wms_com_type管理页面',
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
          title: this.$t('企业属性代码'),
          align: 'left',
          dataIndex: 'comTypeCode',
          sorter: true
        },
        {
          title: this.$t('企业属性名称'),
          align: 'left',
          dataIndex: 'comTypeName',
          sorter: true
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          // fixed:"right",
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
        // {
        //   title:'状态',
        //   align: 'left',
        //   dataIndex: 'comTypeDel_dictText'
        // }
      ],
      url: {
        list: '/jeewms/wmsComType/list',
        delete: '/jeewms/wmsComType/delete',
        deleteBatch: '/jeewms/wmsComType/deleteBatch',
        exportXlsUrl: '/jeewms/wmsComType/exportXls',
        importExcelUrl: 'jeewms/wmsComType/importExcel',
        editStatusUrl: 'jeewms/wmsComType/startOrStop'
      },
      dictOptions: {}
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    initDictConfig() {},
    //启用
    handleAudit(recordId, flag) {
      var params = {
        id: recordId,
        comTypeDel: flag
      }
      postAction(this.url.editStatusUrl, params).then(res => {
        if (res.code == 200) {
          this.$message.success(this.$t('操作成功'))
          this.loadData()
        } else {
          this.$message.warning(this.$t('操作失败'))
        }
      })
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>