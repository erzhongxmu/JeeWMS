<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('base_goods_item')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
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
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
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
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <base-goods-item-modal ref="modalForm" @ok="modalFormOk"></base-goods-item-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BaseGoodsItemModal from './modules/BaseGoodsItemModal'

  export default {
    name: 'BaseGoodsItemList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      BaseGoodsItemModal
    },
    data () {
      return {
        description: 'base_goods_item管理页面',
        // 表头
        columns: [
          {
            title:'创建人名称',
            align:"center",
            dataIndex: 'createName'
          },
          {
            title:'更新人名称',
            align:"center",
            dataIndex: 'updateName'
          },
          // {
          //   title:'公司',
          //   align:"center",
          //   dataIndex: 'query01'
          // },
          // {
          //   title:'工厂',
          //   align:"center",
          //   dataIndex: 'query02'
          // },
          // {
          //   title:'库存地点',
          //   align:"center",
          //   dataIndex: 'query03'
          // },
          {
            title:'商品编码',
            align:"center",
            dataIndex: 'query04'
          },
          {
            title:'商品名称',
            align:"center",
            dataIndex: 'query05'
          },
          {
            title:'销售运输方式',
            align:"center",
            dataIndex: 'query06'
          },
          {
            title:'采购提前期',
            align:"center",
            dataIndex: 'query07'
          },
          {
            title:'生产周期',
            align:"center",
            dataIndex: 'query08'
          },
          {
            title:'质检方式',
            align:"center",
            dataIndex: 'query09'
          },
          {
            title:'质检比例',
            align:"center",
            dataIndex: 'query10'
          },
          {
            title:'query11',
            align:"center",
            dataIndex: 'query11'
          },
          {
            title:'query12',
            align:"center",
            dataIndex: 'query12'
          },
          {
            title:'query13',
            align:"center",
            dataIndex: 'query13'
          },
          {
            title:'query14',
            align:"center",
            dataIndex: 'query14'
          },
          {
            title:'query15',
            align:"center",
            dataIndex: 'query15'
          },
          {
            title:'query16',
            align:"center",
            dataIndex: 'query16'
          },
          {
            title:'query17',
            align:"center",
            dataIndex: 'query17'
          },
          {
            title:'query18',
            align:"center",
            dataIndex: 'query18'
          },
          {
            title:'query19',
            align:"center",
            dataIndex: 'query19'
          },
          {
            title:'query20',
            align:"center",
            dataIndex: 'query20'
          },
          {
            title:'query21',
            align:"center",
            dataIndex: 'query21'
          },
          {
            title:'query22',
            align:"center",
            dataIndex: 'query22'
          },
          {
            title:'query23',
            align:"center",
            dataIndex: 'query23'
          },
          {
            title:'query24',
            align:"center",
            dataIndex: 'query24'
          },
          {
            title:'query25',
            align:"center",
            dataIndex: 'query25'
          },
          {
            title:'query26',
            align:"center",
            dataIndex: 'query26'
          },
          {
            title:'query27',
            align:"center",
            dataIndex: 'query27'
          },
          {
            title:'query28',
            align:"center",
            dataIndex: 'query28'
          },
          {
            title:'query29',
            align:"center",
            dataIndex: 'query29'
          },
          {
            title:'query30',
            align:"center",
            dataIndex: 'query30'
          },
          {
            title:'num01',
            align:"center",
            dataIndex: 'num01'
          },
          {
            title:'num02',
            align:"center",
            dataIndex: 'num02'
          },
          {
            title:'num03',
            align:"center",
            dataIndex: 'num03'
          },
          {
            title:'num04',
            align:"center",
            dataIndex: 'num04'
          },
          {
            title:'num05',
            align:"center",
            dataIndex: 'num05'
          },
          {
            title:'link01',
            align:"center",
            dataIndex: 'link01'
          },
          {
            title:'link02',
            align:"center",
            dataIndex: 'link02'
          },
          {
            title:'link03',
            align:"center",
            dataIndex: 'link03'
          },
          {
            title:'link04',
            align:"center",
            dataIndex: 'link04'
          },
          {
            title:'link05',
            align:"center",
            dataIndex: 'link05'
          },
          {
            title:'text01',
            align:"center",
            dataIndex: 'text01'
          },
          {
            title:'text02',
            align:"center",
            dataIndex: 'text02'
          },
          {
            title:'text03',
            align:"center",
            dataIndex: 'text03'
          },
          {
            title:'text04',
            align:"center",
            dataIndex: 'text04'
          },
          {
            title:'text05',
            align:"center",
            dataIndex: 'text05'
          },
          {
            title:'attr1',
            align:"center",
            dataIndex: 'attr1'
          },
          {
            title:'attr2',
            align:"center",
            dataIndex: 'attr2'
          },
          {
            title:'attr3',
            align:"center",
            dataIndex: 'attr3'
          },
          {
            title:'attr4',
            align:"center",
            dataIndex: 'attr4'
          },
          {
            title:'attr5',
            align:"center",
            dataIndex: 'attr5'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/jeeerp/baseGoodsItem/list",
          delete: "/jeeerp/baseGoodsItem/delete",
          deleteBatch: "/jeeerp/baseGoodsItem/deleteBatch",
          exportXlsUrl: "/jeeerp/baseGoodsItem/exportXls",
          importExcelUrl: "jeeerp/baseGoodsItem/importExcel",
          
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
        fieldList.push({type:'string',value:'createName',text:'创建人名称'})
        fieldList.push({type:'string',value:'updateName',text:'更新人名称'})
        fieldList.push({type:'string',value:'query01',text:'公司'})
        fieldList.push({type:'string',value:'query02',text:'工厂'})
        fieldList.push({type:'string',value:'query03',text:'库存地点'})
        fieldList.push({type:'string',value:'query04',text:'商品编码'})
        fieldList.push({type:'string',value:'query05',text:'商品名称'})
        fieldList.push({type:'string',value:'query06',text:'销售运输方式'})
        fieldList.push({type:'string',value:'query07',text:'采购提前期'})
        fieldList.push({type:'string',value:'query08',text:'生产周期'})
        fieldList.push({type:'string',value:'query09',text:'质检方式'})
        fieldList.push({type:'string',value:'query10',text:'质检比例'})
        fieldList.push({type:'string',value:'query11',text:'query11'})
        fieldList.push({type:'string',value:'query12',text:'query12'})
        fieldList.push({type:'string',value:'query13',text:'query13'})
        fieldList.push({type:'string',value:'query14',text:'query14'})
        fieldList.push({type:'string',value:'query15',text:'query15'})
        fieldList.push({type:'string',value:'query16',text:'query16'})
        fieldList.push({type:'string',value:'query17',text:'query17'})
        fieldList.push({type:'string',value:'query18',text:'query18'})
        fieldList.push({type:'string',value:'query19',text:'query19'})
        fieldList.push({type:'string',value:'query20',text:'query20'})
        fieldList.push({type:'string',value:'query21',text:'query21'})
        fieldList.push({type:'string',value:'query22',text:'query22'})
        fieldList.push({type:'string',value:'query23',text:'query23'})
        fieldList.push({type:'string',value:'query24',text:'query24'})
        fieldList.push({type:'string',value:'query25',text:'query25'})
        fieldList.push({type:'string',value:'query26',text:'query26'})
        fieldList.push({type:'string',value:'query27',text:'query27'})
        fieldList.push({type:'string',value:'query28',text:'query28'})
        fieldList.push({type:'string',value:'query29',text:'query29'})
        fieldList.push({type:'string',value:'query30',text:'query30'})
        fieldList.push({type:'number',value:'num01',text:'num01'})
        fieldList.push({type:'number',value:'num02',text:'num02'})
        fieldList.push({type:'number',value:'num03',text:'num03'})
        fieldList.push({type:'number',value:'num04',text:'num04'})
        fieldList.push({type:'number',value:'num05',text:'num05'})
        fieldList.push({type:'string',value:'link01',text:'link01'})
        fieldList.push({type:'string',value:'link02',text:'link02'})
        fieldList.push({type:'string',value:'link03',text:'link03'})
        fieldList.push({type:'string',value:'link04',text:'link04'})
        fieldList.push({type:'string',value:'link05',text:'link05'})
        fieldList.push({type:'string',value:'text01',text:'text01'})
        fieldList.push({type:'string',value:'text02',text:'text02'})
        fieldList.push({type:'string',value:'text03',text:'text03'})
        fieldList.push({type:'string',value:'text04',text:'text04'})
        fieldList.push({type:'string',value:'text05',text:'text05'})
        fieldList.push({type:'string',value:'attr1',text:'attr1'})
        fieldList.push({type:'string',value:'attr2',text:'attr2'})
        fieldList.push({type:'string',value:'attr3',text:'attr3'})
        fieldList.push({type:'string',value:'attr4',text:'attr4'})
        fieldList.push({type:'string',value:'attr5',text:'attr5'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>