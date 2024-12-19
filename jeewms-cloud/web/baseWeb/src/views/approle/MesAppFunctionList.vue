<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="app模块编号">
              <a-input placeholder="请输入app模块编号" v-model="queryParam.appmodelCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="app模块名称">
              <a-input placeholder="请输入app模块名称" v-model="queryParam.appmodelName"></a-input>
            </a-form-item>
          </a-col>
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
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('APP功能')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>
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
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail2(record)">配置字段</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <mes-app-function-modal ref="modalForm" @ok="modalFormOk"></mes-app-function-modal>
    <WmPdaConfigList ref="WmPdaConfigList"  @ok="modalFormOk"/>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MesAppFunctionModal from './modules/MesAppFunctionModal'
	import WmPdaConfigList from './modules/WmPdaConfigList'

  export default {
    name: 'MesAppFunctionList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      MesAppFunctionModal,
      WmPdaConfigList
    },
    data () {
      return {
        description: 'APP功能管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align: 'left',
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'app模块编号',
            align: 'left',
            dataIndex: 'appmodelCode'
          },
          {
            title:'app模块名称',
            align: 'left',
            dataIndex: 'appmodelName'
          },
          {
            title:'app多语言key',
            align: 'left',
            dataIndex: 'query1'
          },
          {
            title:'app模块排序',
            align: 'left',
            dataIndex: 'appmodelSort'
          },
          {
            title:'类型',
            align: 'left',
            dataIndex: 'type'
          },
          {
            title:'路径',
            align: 'left',
            dataIndex: 'route'
          },
          {
            title:'图片',
            align: 'left',
            dataIndex: 'picture',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'是否禁用',
            align: 'left',
            dataIndex: 'ifBind_dictText'
          },
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/jeewms/mesAppFunction/list",
          delete: "/jeewms/mesAppFunction/delete",
          deleteBatch: "/jeewms/mesAppFunction/deleteBatch",
          exportXlsUrl: "/jeewms/mesAppFunction/exportXls",
          importExcelUrl: "jeewms/mesAppFunction/importExcel",
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
      handleDetail2(e){
        this.$refs.WmPdaConfigList.onShow(e)
      },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'appmodelCode',text:'app模块编号',dictCode:''})
        fieldList.push({type:'string',value:'appmodelName',text:'app模块名称',dictCode:''})
        fieldList.push({type:'string',value:'appmodelSort',text:'app模块排序',dictCode:''})
        fieldList.push({type:'string',value:'type',text:'类型',dictCode:''})
        fieldList.push({type:'string',value:'route',text:'路径',dictCode:''})
        fieldList.push({type:'string',value:'picture',text:'图片',dictCode:''})
        fieldList.push({type:'string',value:'ifBind',text:'是否禁用',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>