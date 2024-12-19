<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="库位编码">
              <a-input placeholder="请输入库位编码" v-model="queryParam.kwCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="库位名称">
              <a-input placeholder="请输入库位名称" v-model="queryParam.kwName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="库位条码">-->
<!--                <a-input placeholder="请输入库位条码" v-model="queryParam.kwBarCode"></a-input>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="库位用途">-->
<!--                <j-dict-select-tag placeholder="请选择库位用途" v-model="queryParam.binUse" dictCode="bin_use"/>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="仓库">-->
<!--                <j-dict-select-tag placeholder="请选择仓库" v-model="queryParam.storeCode" dictCode="ba_store,store_name,store_code"/>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
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
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'kw:add'">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls('库位')" v-has="'kw:export'">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" v-has="'kw:import'" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button icon="download" @click="doExportBqs('1')">批量导出标签</a-button>
      <a-button icon="download" @click="doExportBqs('2')">批量标签导出2</a-button>
      <a-button icon="download" @click="doExportBqs('3')">批量标签导出3</a-button>
      <a-button key="1" @click="batchDel" v-has="'kw:batchDelete'"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>

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
          <a @click="handleCheck(record)" v-has="'kw:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'kw:check'"/>
          <a @click="handleEdit(record)" v-has="'kw:update'">{{$t('编辑')}}</a>
          <a-divider type="vertical"  v-has="'kw:update'"/>
          <a @click="doExportBq(record)">标签</a>
          <a-divider type="vertical"/>
          <a-popconfirm v-has="'kw:delete'" :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
<!--          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>-->
        </span>

      </a-table>
    </div>

    <baKw-modal2 ref="modalForm" @ok="modalFormOk"></baKw-modal2>
    <export-spin v-if="exportOk"></export-spin>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BaKwModal2 from './modules/BaKwModal2'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'
  import ExportSpin from '@/components/ExportSpin/ExportSpin'

  export default {
    name: "BaKwList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
    components: {
      JDictSelectTag,
      BaKwModal2,
      ExportSpin
    },
    data () {
      return {
        description: 'ba_kw管理页面',
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            width:147,
            // fixed:"right",
            scopedSlots: { customRender: 'action' }
          },
          // {
          //   title:'库存地点',
          //   align: 'left',
          //   dataIndex: 'kwName'
          // },
          {
            title:'储位',
            align: 'left',
            dataIndex: 'kwName'
          },
          {
            title:'库位编码',
            align: 'left',
            dataIndex: 'kwCode'
          },
          {
            title:'库位条码',
            align: 'left',
            dataIndex: 'kwBarCode'
          },
          // {
          //   title:'库位类型',
          //   align: 'left',
          //   dataIndex: 'kwType_dictText'
          // },
          // {
          //   title:'库位属性',
          //   align: 'left',
          //   dataIndex: 'kwAttr_dictText'
          // },
          // {
          //   title:'产品属性',
          //   align: 'left',
          //   dataIndex: 'productAttr_dictText'
          // },
          // {
          //   title:'上架次序',
          //   align: 'left',
          //   dataIndex: 'orderOn'
          // },
          // {
          //   title:'取货次序',
          //   align: 'left',
          //   dataIndex: 'orderDowm'
          // },
          // {
          //   title:'所属货主',
          //   align: 'left',
          //   dataIndex: 'custom_dictText'
          // },
          // {
          //   title:'最小批量',
          //   align: 'left',
          //   dataIndex: 'volumeUnit'
          // },
          // {
          //   title:'最大批量',
          //   align: 'left',
          //   dataIndex: 'weightUnit'
          // },
          // {
          //   title:'储位组别',
          //   align: 'left',
          //   dataIndex: 'maxArea'
          // },
          // {
          //   title:'出库口',
          //   align: 'left',
          //   dataIndex: 'areaUnit'
          // },
          // {
          //   title:'最大体积',
          //   align: 'left',
          //   dataIndex: 'maxVolume'
          // },
          // {
          //   title:'最大重量',
          //   align: 'left',
          //   dataIndex: 'maxWeight'
          // },
          // {
          //   title:'最大托盘',
          //   align: 'left',
          //   dataIndex: 'maxTray'
          // },
          // {
          //   title:'长度',
          //   align: 'left',
          //   dataIndex: 'length'
          // },
          // {
          //   title:'宽度',
          //   align: 'left',
          //   dataIndex: 'width'
          // },
          // {
          //   title:'高度',
          //   align: 'left',
          //   dataIndex: 'height'
          // },
          // {
          //   title:'停用',
          //   align: 'left',
          //   dataIndex: 'status'
          // },
          // {
          //   title:'备注',
          //   align: 'left',
          //   dataIndex: 'attr1'
          // },
          // {
          //   title:'备注1',
          //   align: 'left',
          //   dataIndex: 'attr2'
          // },
          // {
          //   title:'备注2',
          //   align: 'left',
          //   dataIndex: 'attr3'
          // },
          // {
          //   title:'电子标签id',
          //   align: 'left',
          //   dataIndex: 'rfId'
          // },
          // {
          //   title:'X坐标',
          //   align: 'left',
          //   dataIndex: 'xnode'
          // },
          // {
          //   title:'Y坐标',
          //   align: 'left',
          //   dataIndex: 'ynode'
          // },
          // {
          //   title:'Z坐标',
          //   align: 'left',
          //   dataIndex: 'znode'
          // },
          //
          // {
          //   title:'仓库名称',
          //   align: 'left',
          //   dataIndex: 'wareName'
          // },
          // {
          //   title:'库区编码',
          //   align: 'left',
          //   dataIndex:'storeAreaCode'
          // },
          //
          // {
          //   title:'库位名称',
          //   align: 'left',
          //   dataIndex: 'kwName'
          // },
          //
          // {
          //   title:'是否混放',
          //   align: 'left',
          //   dataIndex: 'mixStore_dictText'
          // },
          // {
          //   title:'是否为动态库位',
          //   align: 'left',
          //   dataIndex: 'dynamicLocation_dictText'
          // },
          // {
          //   title:'明细',
          //   align: 'left',
          //   dataIndex: 'detail'
          // },

        ],
        url: {
          list: "/jeewms/baKw/list",
          delete: "/jeewms/baKw/delete",
          deleteBatch: "/jeewms/baKw/deleteBatch",
          exportXlsUrl: "/jeewms/baKw/exportXls",
          importExcelUrl: "/jeewms/baKw/importExcel",
          doExportBq: "/jeewms/baKw/doExportBq",
        },
        dictOptions:{},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      handleCheck: function (record) {
        if(record.mixStore == 'Y' && (record.partType == undefined || record.partType == null || record.partType == '')) {
          record.partType = []
        }
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "查看";
        this.$refs.modalForm.disableSubmit = false;
      },
      handleEdit: function (record) {
        if(record.mixStore == 'Y' && (record.partType == undefined || record.partType == null || record.partType == '')) {
          record.partType = []
        }
        this.$refs.modalForm.edit(record);
        this.$refs.modalForm.title = "编辑";
        this.$refs.modalForm.disableSubmit = false;
      },
      doExportBq(record){
        window.open(window._CONFIG['domianURL'] + this.url.doExportBq + '?type=1&ids=' + record.id)
      },
      doExportBqs(type) {
        if(this.selectedRowKeys.length < 1) {
          this.$message.warning(this.$t('请选择一条记录!'));
          return
        }
        let ids = ''
        for (var a = 0; a < this.selectedRowKeys.length; a++) {
          ids += this.selectedRowKeys[a] + ','
        }
        if(type==1){
          window.open(window._CONFIG['domianURL'] + this.url.doExportBq + '?type=1&ids=' + ids)
        }else if(type==2){
          window.open(window._CONFIG['domianURL'] + this.url.doExportBq + '?type=2&ids=' + ids)
        }else{
          window.open(window._CONFIG['domianURL'] + this.url.doExportBq + '?type=3&ids=' + ids)
        }
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>