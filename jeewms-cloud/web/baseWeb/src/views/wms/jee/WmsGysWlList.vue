<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="货主编码">
              <j-search-select-tag placeholder="请选择货主编码" v-model="queryParam.suplyCode" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="货主名称">
              <a-input placeholder="请输入货主名称" v-model="queryParam.suplyName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="物料编码">
                <j-search-select-tag placeholder="请选择物料编码" v-model="queryParam.materialCode" dict="md_goods,shp_ming_cheng,shp_bian_ma"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="物料名称">
                <a-input placeholder="请输入物料名称" v-model="queryParam.materialName"></a-input>
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
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('物料供应关系')">{{$t('导出')}}</a-button>
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
      <div>
        <j-modal title="查看二维码" :visible="visible" :footer="null" @cancel="handleCancel" >
          <div style="width: 100%;margin: 0 auto;text-align: center"> <img :src="imgPath" /></div>
        </j-modal>
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
    <wms-gys-wl-modal ref="modalForm" @ok="modalFormOk"></wms-gys-wl-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmsGysWlModal from './modules/WmsGysWlModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'WmsGysWlList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      WmsGysWlModal
    },
    data () {
      return {
        visible: false,
        description: '物料供应关系管理页面',
        // 表头
        columns: [
          {
            title:'货主编码',
            align: 'left',
            dataIndex: 'suplyCode'
          },
          {
            title:'货主名称',
            align: 'left',
            dataIndex: 'suplyName'
          },
          {
            title:'物料编码',
            align: 'left',
            dataIndex: 'materialCode'
          },
          {
            title:'物料名称',
            align: 'left',
            dataIndex: 'materialName'
          },
          {
            title:'物料规格',
            align: 'left',
            dataIndex: 'materialSpe'
          },
          {
            title:'单位',
            align: 'left',
            dataIndex: 'unit'
          },
          {
            title:'条码图片',
            align: 'left',
            dataIndex: 'picture',
            scopedSlots: {customRender: 'imgSlot'}
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
          list: "/jeewms/wmsGysWl/list",
          delete: "/jeewms/wmsGysWl/delete",
          deleteBatch: "/jeewms/wmsGysWl/deleteBatch",
          exportXlsUrl: "/jeewms/wmsGysWl/exportXls",
          importExcelUrl: "jeewms/wmsGysWl/importExcel",

        },
        dictOptions:{},
        superFieldList:[],
        imgPath:"",
      }
    },
    created() {
    this.getSuperFieldList();
      this.loadData();
      document.addEventListener('click', (e) => {
        if(e.target.localName=='img'&&e.target.currentSrc){
          console.log(e.target.currentSrc)
          this.visible=true;
          this.imgPath=e.target.currentSrc
        }
      }, false)
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      handleCancel () {
        this.visible = false
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'sel_search',value:'suplyCode',text:'货主编码',dictTable:'md_cus', dictText:'zhong_wen_qch', dictCode:'ke_hu_bian_ma'})
        fieldList.push({type:'string',value:'suplyName',text:'货主名称',dictCode:''})
        fieldList.push({type:'sel_search',value:'materialCode',text:'物料编码',dictTable:'md_goods', dictText:'shp_ming_cheng', dictCode:'shp_bian_ma'})
        fieldList.push({type:'string',value:'materialName',text:'物料名称',dictCode:''})
        fieldList.push({type:'string',value:'materialSpe',text:'物料规格',dictCode:''})
        fieldList.push({type:'string',value:'unit',text:'单位',dictCode:''})
        fieldList.push({type:'string',value:'picture',text:'条码图片',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>