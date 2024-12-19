<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="公司">
              <a-input placeholder="请输入公司" v-model="queryParam.query01"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="工厂">
              <a-input placeholder="请输入工厂" v-model="queryParam.query02"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="库存地点">
                <a-input placeholder="请输入库存地点" v-model="queryParam.query03"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="商品编码">
                <a-input placeholder="请输入商品编码" v-model="queryParam.query04"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="商品名称">
                <a-input placeholder="请输入商品名称" v-model="queryParam.query05"></a-input>
              </a-form-item>
            </a-col>
            <!-- 类型（采购/销售/税率/成本价） -->
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="类型">
                <a-input placeholder="请输入类型（采购/销售/税率/成本价）" v-model="queryParam.query06"></a-input>
              </a-form-item>
            </a-col>
            <!-- 来源编码（客户/供应商） -->
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="来源编码">
                <a-input placeholder="请输入来源编码（客户/供应商）" v-model="queryParam.query07"></a-input>
              </a-form-item>
            </a-col>
            <!-- 来源名称（客户/供应商） -->
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="来源名称">
                <a-input placeholder="请输入来源名称（客户/供应商）" v-model="queryParam.query08"></a-input>
              </a-form-item>
            </a-col>
            <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="币种">
                <a-input placeholder="请输入币种" v-model="queryParam.query09"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="单位数量">
                <a-input placeholder="请输入单位数量" v-model="queryParam.query10"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="价格">
                <a-input placeholder="请输入价格" v-model="queryParam.query11"></a-input>
              </a-form-item>
            </a-col> -->
            <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query12">
                <a-input placeholder="请输入query12" v-model="queryParam.query12"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query13">
                <a-input placeholder="请输入query13" v-model="queryParam.query13"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query14">
                <a-input placeholder="请输入query14" v-model="queryParam.query14"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query15">
                <a-input placeholder="请输入query15" v-model="queryParam.query15"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query16">
                <a-input placeholder="请输入query16" v-model="queryParam.query16"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query17">
                <a-input placeholder="请输入query17" v-model="queryParam.query17"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query18">
                <a-input placeholder="请输入query18" v-model="queryParam.query18"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query19">
                <a-input placeholder="请输入query19" v-model="queryParam.query19"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query20">
                <a-input placeholder="请输入query20" v-model="queryParam.query20"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query21">
                <a-input placeholder="请输入query21" v-model="queryParam.query21"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query22">
                <a-input placeholder="请输入query22" v-model="queryParam.query22"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query23">
                <a-input placeholder="请输入query23" v-model="queryParam.query23"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query24">
                <a-input placeholder="请输入query24" v-model="queryParam.query24"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query25">
                <a-input placeholder="请输入query25" v-model="queryParam.query25"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query26">
                <a-input placeholder="请输入query26" v-model="queryParam.query26"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query27">
                <a-input placeholder="请输入query27" v-model="queryParam.query27"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query28">
                <a-input placeholder="请输入query28" v-model="queryParam.query28"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query29">
                <a-input placeholder="请输入query29" v-model="queryParam.query29"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="query30">
                <a-input placeholder="请输入query30" v-model="queryParam.query30"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="num01">
                <a-input placeholder="请输入num01" v-model="queryParam.num01"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="num02">
                <a-input placeholder="请输入num02" v-model="queryParam.num02"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="num03">
                <a-input placeholder="请输入num03" v-model="queryParam.num03"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="num04">
                <a-input placeholder="请输入num04" v-model="queryParam.num04"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="num05">
                <a-input placeholder="请输入num05" v-model="queryParam.num05"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="link01">
                <a-input placeholder="请输入link01" v-model="queryParam.link01"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="link02">
                <a-input placeholder="请输入link02" v-model="queryParam.link02"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="link03">
                <a-input placeholder="请输入link03" v-model="queryParam.link03"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="link04">
                <a-input placeholder="请输入link04" v-model="queryParam.link04"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="link05">
                <a-input placeholder="请输入link05" v-model="queryParam.link05"></a-input>
              </a-form-item>
            </a-col> -->
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
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
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('base_price')">导出</a-button>
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

    <base-price-modal ref="modalForm" @ok="modalFormOk"></base-price-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BasePriceModal from './modules/BasePriceModal'

  export default {
    name: 'BasePriceList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      BasePriceModal
    },
    data () {
      return {
        description: 'base_price管理页面',
        // 表头
        columns: [
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          // {
          //   title:'创建人名称',
          //   align:"center",
          //   dataIndex: 'createName'
          // },
          // {
          //   title:'更新人名称',
          //   align:"center",
          //   dataIndex: 'updateName'
          // },
          {
            title:'公司',
            align:"center",
            dataIndex: 'query01'
          },
          {
            title:'工厂',
            align:"center",
            dataIndex: 'query02'
          },
          {
            title:'库存地点',
            align:"center",
            dataIndex: 'query03'
          },
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
            title:'类型',// （采购/销售/税率/成本价）
            align:"center",
            dataIndex: 'query06'
          },
          {
            title:'来源编码', // （客户/供应商）
            align:"center",
            dataIndex: 'query07'
          },
          {
            title:'来源名称', // （客户/供应商）
            align:"center",
            dataIndex: 'query08'
          },
          {
            title:'币种',
            align:"center",
            dataIndex: 'query09'
          },
          {
            title:'单位数量',
            align:"center",
            dataIndex: 'query10'
          },
          {
            title:'价格',
            align:"center",
            dataIndex: 'query11'
          },
          // {
          //   title:'query12',
          //   align:"center",
          //   dataIndex: 'query12'
          // },
          // {
          //   title:'query13',
          //   align:"center",
          //   dataIndex: 'query13'
          // },
          // {
          //   title:'query14',
          //   align:"center",
          //   dataIndex: 'query14'
          // },
          // {
          //   title:'query15',
          //   align:"center",
          //   dataIndex: 'query15'
          // },
          // {
          //   title:'query16',
          //   align:"center",
          //   dataIndex: 'query16'
          // },
          // {
          //   title:'query17',
          //   align:"center",
          //   dataIndex: 'query17'
          // },
          // {
          //   title:'query18',
          //   align:"center",
          //   dataIndex: 'query18'
          // },
          // {
          //   title:'query19',
          //   align:"center",
          //   dataIndex: 'query19'
          // },
          // {
          //   title:'query20',
          //   align:"center",
          //   dataIndex: 'query20'
          // },
          // {
          //   title:'query21',
          //   align:"center",
          //   dataIndex: 'query21'
          // },
          // {
          //   title:'query22',
          //   align:"center",
          //   dataIndex: 'query22'
          // },
          // {
          //   title:'query23',
          //   align:"center",
          //   dataIndex: 'query23'
          // },
          // {
          //   title:'query24',
          //   align:"center",
          //   dataIndex: 'query24'
          // },
          // {
          //   title:'query25',
          //   align:"center",
          //   dataIndex: 'query25'
          // },
          // {
          //   title:'query26',
          //   align:"center",
          //   dataIndex: 'query26'
          // },
          // {
          //   title:'query27',
          //   align:"center",
          //   dataIndex: 'query27'
          // },
          // {
          //   title:'query28',
          //   align:"center",
          //   dataIndex: 'query28'
          // },
          // {
          //   title:'query29',
          //   align:"center",
          //   dataIndex: 'query29'
          // },
          // {
          //   title:'query30',
          //   align:"center",
          //   dataIndex: 'query30'
          // },
          // {
          //   title:'num01',
          //   align:"center",
          //   dataIndex: 'num01'
          // },
          // {
          //   title:'num02',
          //   align:"center",
          //   dataIndex: 'num02'
          // },
          // {
          //   title:'num03',
          //   align:"center",
          //   dataIndex: 'num03'
          // },
          // {
          //   title:'num04',
          //   align:"center",
          //   dataIndex: 'num04'
          // },
          // {
          //   title:'num05',
          //   align:"center",
          //   dataIndex: 'num05'
          // },
          // {
          //   title:'link01',
          //   align:"center",
          //   dataIndex: 'link01'
          // },
          // {
          //   title:'link02',
          //   align:"center",
          //   dataIndex: 'link02'
          // },
          // {
          //   title:'link03',
          //   align:"center",
          //   dataIndex: 'link03'
          // },
          // {
          //   title:'link04',
          //   align:"center",
          //   dataIndex: 'link04'
          // },
          // {
          //   title:'link05',
          //   align:"center",
          //   dataIndex: 'link05'
          // },
          // {
          //   title:'text01',
          //   align:"center",
          //   dataIndex: 'text01'
          // },
          // {
          //   title:'text02',
          //   align:"center",
          //   dataIndex: 'text02'
          // },
          // {
          //   title:'text03',
          //   align:"center",
          //   dataIndex: 'text03'
          // },
          // {
          //   title:'text04',
          //   align:"center",
          //   dataIndex: 'text04'
          // },
          // {
          //   title:'text05',
          //   align:"center",
          //   dataIndex: 'text05'
          // },
          // {
          //   title:'attr1',
          //   align:"center",
          //   dataIndex: 'attr1'
          // },
          // {
          //   title:'attr2',
          //   align:"center",
          //   dataIndex: 'attr2'
          // },
          // {
          //   title:'attr3',
          //   align:"center",
          //   dataIndex: 'attr3'
          // },
          // {
          //   title:'attr4',
          //   align:"center",
          //   dataIndex: 'attr4'
          // },
          // {
          //   title:'attr5',
          //   align:"center",
          //   dataIndex: 'attr5'
          // },
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
          list: "/jeeerp/basePrice/list",
          delete: "/jeeerp/basePrice/delete",
          deleteBatch: "/jeeerp/basePrice/deleteBatch",
          exportXlsUrl: "/jeeerp/basePrice/exportXls",
          importExcelUrl: "jeeerp/basePrice/importExcel",
          
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
        fieldList.push({type:'string',value:'createName',text:'创建人名称',dictCode:''})
        fieldList.push({type:'string',value:'updateName',text:'更新人名称',dictCode:''})
        fieldList.push({type:'string',value:'query01',text:'公司',dictCode:''})
        fieldList.push({type:'string',value:'query02',text:'工厂',dictCode:''})
        fieldList.push({type:'string',value:'query03',text:'库存地点',dictCode:''})
        fieldList.push({type:'string',value:'query04',text:'商品编码',dictCode:''})
        fieldList.push({type:'string',value:'query05',text:'商品名称',dictCode:''})
        fieldList.push({type:'string',value:'query06',text:'类型（采购/销售/税率/成本价）',dictCode:''})
        fieldList.push({type:'string',value:'query07',text:'来源编码（客户/供应商）',dictCode:''})
        fieldList.push({type:'string',value:'query08',text:'来源名称（客户/供应商）',dictCode:''})
        fieldList.push({type:'string',value:'query09',text:'币种',dictCode:''})
        fieldList.push({type:'string',value:'query10',text:'单位数量',dictCode:''})
        fieldList.push({type:'string',value:'query11',text:'价格',dictCode:''})
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