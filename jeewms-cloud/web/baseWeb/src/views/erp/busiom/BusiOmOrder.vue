<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="单号">
              <a-input placeholder="请输入单号" v-model="queryParam.query04"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="客户">
              <j-popup
                  v-model="queryParam.query08"
                  field="query08"
                  org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                  dest-fields="query09,query08"
                  code="md_cus"
                  :multi="false"
                />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="主PO号">
              <a-input placeholder="请输入主PO号" v-model="queryParam.query13"></a-input>
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="装箱单号">
              <a-input placeholder="请输入装箱单号" v-model="queryParam.query17"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col  :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-model-item label="要求交货时间">
              <j-date :placeholder="$t('请选择')"  v-model="queryParam.query21" />
            </a-form-model-item>
          </a-col>
          <a-col  :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-model-item label="创建人">
              <j-search-select-tag
                type="list"
                v-model="queryParam.createName"
                dict="sys_user,realname,realname,del_flag = '0'"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
         
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" v-has="'BusiOmOrder:search'" >查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px" v-has="'BusiOmOrder:reset'" >重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('出库预约')" v-has="'BusiOmOrder:export'" >导出</a-button>
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'BusiOmOrder:import'" >导入</a-button>
      </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <!--  <a-dropdown v-if="selectedRowKeys.length > 0">
       <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
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
          <a @click="handleEdit(record)" v-has="'BusiOmOrder:edit'" >编辑</a>

          <a-divider type="vertical" v-has="'BusiOmOrder:edit'" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)" v-has="'BusiOmOrder:detail'" >详情</a>
              </a-menu-item>
              <!-- <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item> -->
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <busi-om-order-modal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BusiOmOrderModal from './modules/BusiOmOrderModal'
  import '@/assets/less/TableExpand.less'
  import { getAction, httpAction, postAction, downFile } from '@/api/manage'
  import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
  import { BusiOmOrder } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
  import store from '@/store/'
  export default {
    name: "BusiOmOrder",
    mixins:[JeecgListMixin],
    components: {
      BusiOmOrderModal
    },
    data () {
      return {
        description: '出库预约页面',
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
          //   title:'更新人名称',
          //   align:"center",
          //   dataIndex: 'updateName'
          // },
          
          {
            title:'主PO',
            align:"center",
            sorter: true,

            dataIndex: 'query13'
          },

          // {
          //   title:'装箱单号',
          //   align:"center",
          //   dataIndex: 'query17'
          // },
          {
            title:'客户',
            align:"center",
            sorter: true,

            dataIndex: 'query08_dictText'
          },
          {
            title:'要求交货时间',
            align:"center",
            sorter: true,

            dataIndex: 'query21'
          },
          {
            title:'创建人',
            align:"center",
            sorter: true,

            dataIndex: 'createName'
          },
          {
            title:'创建日期',
            align:"center",
            sorter: true,

            dataIndex: 'createTime'
          },
          {
            title:'状态',
            align:"center",
            sorter: true,

            dataIndex: 'query02'
          },

          {
            title:'出货方式',
            align:"center",
            sorter: true,

            dataIndex: 'query18'
          },
          {
            title:'出货地址',
            align:"center",
            sorter: true,

            dataIndex: 'query19'
          },

          {
            title:'备注',
            align:"center",
            sorter: true,

            dataIndex: 'text01'
          },
          // {
          //   title:'出库单号',
          //   align:"center",
          //   dataIndex: 'query04'
          // },
          // {
          //   title:'采购单号',
          //   align:"center",
          //   dataIndex: 'link02'
          // },
          {
            title:'仓库',
            align:"center",
            sorter: true,
            dataIndex: 'query07'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/jeeerp/busiOm/BusiOmItemList",
          delete: "/jeeerp/busiOm/delete",
          deleteBatch: "/jeeerp/busiOm/deleteBatch",
          exportXlsUrl: "/jeeerp/busiOm/exportXls",
          importExcelUrl: "jeeerp/busiOm/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
        queryParam:{
          query01:'CKYY',
          createBy: store.getters.userInfo.username
        },
      }
    },
    created() {
      this.getSuperFieldList();
    },
    mounted() {
      if(this.$route.query.orderId) {
        if(this.$route.query.type){
          this.$refs.modalForm.add2(this.$route.query.orderId,this.$route.query.type)
        }else{
          this.$refs.modalForm.add2(this.$route.query.orderId,null)
        }
      }
      
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      handleExportXls(name) {
        let myparam = {}
        Object.assign(myparam, this.queryParam)
        for (var mkey in myparam) {
          if (myparam[mkey] != null && myparam[mkey] != '' && myparam[mkey].indexOf('*') == -1) {
            myparam[mkey] = '*' + myparam[mkey] + '*'
          }
        }
        let param = { ...myparam }
        if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
          param['selections'] = this.selectedRowKeys.join(',')
        }
        getAction('/jeeerp/ExportTableData/ExportBusiOmItem', param).then((res) => {
          ExportTemplate([],BusiOmOrder,res.result,name)
        })
      },
      searchReset() {
        this.queryParam = {query01: "CKYY",createBy: this.store.getters.userInfo.username }
        this.loadData()
      },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
         fieldList.push({type:'string',value:'createName',text:'创建人名称',dictCode:''})
         fieldList.push({type:'string',value:'updateName',text:'更新人名称',dictCode:''})
         fieldList.push({type:'string',value:'query01',text:'单据类型',dictCode:''})
         fieldList.push({type:'string',value:'query02',text:'单据状态',dictCode:''})
         fieldList.push({type:'string',value:'query03',text:'建单日期',dictCode:''})
         fieldList.push({type:'string',value:'query04',text:'单号',dictCode:''})
         fieldList.push({type:'string',value:'query05',text:'公司',dictCode:''})
         fieldList.push({type:'string',value:'query06',text:'工厂',dictCode:''})
         fieldList.push({type:'string',value:'query07',text:'库存地点',dictCode:''})
         fieldList.push({type:'string',value:'query08',text:'客户编码',dictCode:''})
         fieldList.push({type:'string',value:'query09',text:'客户名称',dictCode:''})
         fieldList.push({type:'string',value:'query10',text:'商品编码',dictCode:''})
         fieldList.push({type:'string',value:'query11',text:'商品名称',dictCode:''})
         fieldList.push({type:'string',value:'query12',text:'单位',dictCode:''})
         fieldList.push({type:'string',value:'query13',text:'主PO',dictCode:''})
         fieldList.push({type:'string',value:'query14',text:'子PO',dictCode:''})
         fieldList.push({type:'string',value:'query15',text:'检验类型',dictCode:''})
         fieldList.push({type:'string',value:'query16',text:'是否报关',dictCode:''})
         fieldList.push({type:'string',value:'query17',text:'装箱单号',dictCode:''})
         fieldList.push({type:'string',value:'query18',text:'出货方式',dictCode:''})
         fieldList.push({type:'string',value:'query19',text:'出货地址',dictCode:''})
         fieldList.push({type:'string',value:'query20',text:'query20',dictCode:''})
         fieldList.push({type:'string',value:'query21',text:'要求交货时间',dictCode:''})
         fieldList.push({type:'string',value:'query22',text:'query22',dictCode:''})
         fieldList.push({type:'string',value:'query23',text:'单号-行项目号',dictCode:''})
         fieldList.push({type:'string',value:'query24',text:'query24',dictCode:''})
         fieldList.push({type:'string',value:'query25',text:'query25',dictCode:''})
         fieldList.push({type:'string',value:'query26',text:'query26',dictCode:''})
         fieldList.push({type:'string',value:'query27',text:'query27',dictCode:''})
         fieldList.push({type:'string',value:'query28',text:'query28',dictCode:''})
         fieldList.push({type:'string',value:'query29',text:'query29',dictCode:''})
         fieldList.push({type:'string',value:'query30',text:'query30',dictCode:''})
         fieldList.push({type:'double',value:'num01',text:'数量',dictCode:''})
         fieldList.push({type:'double',value:'num02',text:'未完成数量',dictCode:''})
         fieldList.push({type:'double',value:'num03',text:'已完成数量',dictCode:''})
         fieldList.push({type:'double',value:'num04',text:'num04',dictCode:''})
         fieldList.push({type:'double',value:'num05',text:'num05',dictCode:''})
         fieldList.push({type:'string',value:'link01',text:'关联单据类型',dictCode:''})
         fieldList.push({type:'string',value:'link02',text:'关联单号',dictCode:''})
         fieldList.push({type:'string',value:'link03',text:'link03',dictCode:''})
         fieldList.push({type:'string',value:'link04',text:'link04',dictCode:''})
         fieldList.push({type:'string',value:'link05',text:'link05',dictCode:''})
         fieldList.push({type:'string',value:'text01',text:'备注',dictCode:''})
         fieldList.push({type:'string',value:'text02',text:'备注',dictCode:''})
         fieldList.push({type:'string',value:'text03',text:'text03',dictCode:''})
         fieldList.push({type:'string',value:'text04',text:'text04',dictCode:''})
         fieldList.push({type:'string',value:'text05',text:'text05',dictCode:''})
         fieldList.push({type:'string',value:'attr1',text:'单据附件',dictCode:''})
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