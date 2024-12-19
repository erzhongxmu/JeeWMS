<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query04"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('品名')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query05"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('供应商编码')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query06"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('开票品名')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query08"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" >{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px" >{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'TaxClassification:add'">{{$t('新增')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('conf_erp')" v-has="'TaxClassification:download'">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import"  v-has="'TaxClassification:import'">{{$t('导入')}}</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :scroll="{x:true}"
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
            @click="downloadFile(text)">
            {{$t('下载')}}
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-has="'TaxClassification:Edit'">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">{{$t('更多')}} <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">{{$t('详情')}}</a>
              </a-menu-item>
              <a-menu-item v-has="'TaxClassification:Delete'">
                <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <TaxClassificationModal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import TaxClassificationModal from './modules/TaxClassificationModal'
  import '@/assets/less/TableExpand.less'

  export default {
    name: "ConfErpList",
    mixins:[JeecgListMixin],
    components: {
      TaxClassificationModal
    },
    data () {
      return {
        description: 'conf_erp管理页面',
        // 表头
        columns: [
          {
            title: this.$t('商品编码'),
            align:"center",
            dataIndex: 'query04'
          },
          {
            title: this.$t('品名'),
            align:"center",
            dataIndex: 'query05'
          },
          {
            title: this.$t('供应商编码'),
            align:"center",
            dataIndex: 'query06'
          },
          {
            title: this.$t('税收分类编码'),
            align:"center",
            dataIndex: 'query07'
          },
          {
            title: this.$t('开票品名'),
            align:"center",
            dataIndex: 'query08'
          },
          {
            title: this.$t('规格型号'),
            align:"center",
            dataIndex: 'query09'
          },
          {
            title: this.$t('单位'),
            align:"center",
            dataIndex: 'query10'
          },
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/jeeerp/confErp/list",
          delete: "/jeeerp/confErp/delete",
          deleteBatch: "/jeeerp/confErp/deleteBatch",
          exportXlsUrl: "/jeeerp/confErp/exportXlsTaxClassification",
          importExcelUrl: "jeeerp/confErp/importExcelTaxClassification",
        },
        dictOptions:{},
        superFieldList:[],
        queryParam:{
          query01:'SHFL'
        }
      }
    },
    created() {
      this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      searchReset() {
        this.queryParam = {query01: "SHFL"}
        this.loadData()
      },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
         fieldList.push({type:'string',value:'createName',text:'创建人名称',dictCode:''})
         fieldList.push({type:'string',value:'updateName',text:'更新人名称',dictCode:''})
         fieldList.push({type:'string',value:'query01',text:'query01',dictCode:''})
         fieldList.push({type:'string',value:'query02',text:'query02',dictCode:''})
         fieldList.push({type:'string',value:'query03',text:'query03',dictCode:''})
         fieldList.push({type:'string',value:'query04',text:'配置编码',dictCode:''})
         fieldList.push({type:'string',value:'query05',text:'query05',dictCode:''})
         fieldList.push({type:'string',value:'query06',text:'query06',dictCode:''})
         fieldList.push({type:'string',value:'query07',text:'query07',dictCode:''})
         fieldList.push({type:'string',value:'query08',text:'query08',dictCode:''})
         fieldList.push({type:'string',value:'query09',text:'query09',dictCode:''})
         fieldList.push({type:'string',value:'query10',text:'query10',dictCode:''})
         fieldList.push({type:'string',value:'query11',text:'query11',dictCode:''})
         fieldList.push({type:'string',value:'query12',text:'query12',dictCode:''})
         fieldList.push({type:'string',value:'query13',text:'query13',dictCode:''})
         fieldList.push({type:'string',value:'query14',text:'query14',dictCode:''})
         fieldList.push({type:'string',value:'query15',text:'query15',dictCode:''})
         fieldList.push({type:'string',value:'query16',text:'query16',dictCode:''})
         fieldList.push({type:'string',value:'query17',text:'query17',dictCode:''})
         fieldList.push({type:'string',value:'query18',text:'query18',dictCode:''})
         fieldList.push({type:'string',value:'query19',text:'query19',dictCode:''})
         fieldList.push({type:'string',value:'query20',text:'query20',dictCode:''})
         fieldList.push({type:'string',value:'query21',text:'query21',dictCode:''})
         fieldList.push({type:'string',value:'query22',text:'query22',dictCode:''})
         fieldList.push({type:'string',value:'query23',text:'query23',dictCode:''})
         fieldList.push({type:'string',value:'query24',text:'query24',dictCode:''})
         fieldList.push({type:'string',value:'query25',text:'query25',dictCode:''})
         fieldList.push({type:'string',value:'query26',text:'query26',dictCode:''})
         fieldList.push({type:'string',value:'query27',text:'query27',dictCode:''})
         fieldList.push({type:'string',value:'query28',text:'query28',dictCode:''})
         fieldList.push({type:'string',value:'query29',text:'query29',dictCode:''})
         fieldList.push({type:'string',value:'query30',text:'query30',dictCode:''})
         fieldList.push({type:'double',value:'num01',text:'num01',dictCode:''})
         fieldList.push({type:'double',value:'num02',text:'num02',dictCode:''})
         fieldList.push({type:'double',value:'num03',text:'num03',dictCode:''})
         fieldList.push({type:'double',value:'num04',text:'num04',dictCode:''})
         fieldList.push({type:'double',value:'num05',text:'num05',dictCode:''})
         fieldList.push({type:'string',value:'link01',text:'link01',dictCode:''})
         fieldList.push({type:'string',value:'link02',text:'link02',dictCode:''})
         fieldList.push({type:'string',value:'link03',text:'link03',dictCode:''})
         fieldList.push({type:'string',value:'link04',text:'link04',dictCode:''})
         fieldList.push({type:'string',value:'link05',text:'link05',dictCode:''})
         fieldList.push({type:'string',value:'text01',text:'text01',dictCode:''})
         fieldList.push({type:'string',value:'text02',text:'text02',dictCode:''})
         fieldList.push({type:'string',value:'text03',text:'text03',dictCode:''})
         fieldList.push({type:'string',value:'text04',text:'text04',dictCode:''})
         fieldList.push({type:'string',value:'text05',text:'text05',dictCode:''})
         fieldList.push({type:'string',value:'attr1',text:'attr1',dictCode:''})
         fieldList.push({type:'string',value:'attr2',text:'attr2',dictCode:''})
         fieldList.push({type:'string',value:'attr3',text:'attr3',dictCode:''})
         fieldList.push({type:'string',value:'attr4',text:'attr4',dictCode:''})
         fieldList.push({type:'string',value:'attr5',text:'attr5',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>