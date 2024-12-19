<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="单号">
              <a-input placeholder="请输入单号" v-model="queryParam.query04"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品名称">
              <a-input placeholder="请输入商品名称" v-model="queryParam.query11"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品编码">
              <a-input placeholder="请输入商品编码" v-model="queryParam.query10"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="公司">
                <a-input placeholder="请输入公司" v-model="queryParam.query05"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="工厂">
                <a-input placeholder="请输入工厂" v-model="queryParam.query06"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" v-has="'BusiOmList:search'" >查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px" v-has="'BusiOmList:reset'" >重置</a-button>
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
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'BusiOmList:add'" >新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('busi_om')" v-has="'BusiOmList:export'" >导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'BusiOmList:import'" >导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel" v-has="'BusiOmList:batchDelete'" ><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
      <a-button @click="handleCopy(selectedRowKeys)" type="primary" icon="copy" v-if="selectedRowKeys.length == 1">复制</a-button>
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
          <a @click="collectionSchedule(record)" v-has="'BusiOmList:collectionSchedule'" v-if="record.query02 != '收款中'" >收款计划</a>
          <a-divider type="vertical" v-has="'BusiOmList:collectionSchedule'" v-if="record.query02 != '收款中'" />
          <a @click="pushWMS(record)" v-has="'BusiOmList:pushWMS'" v-if="record.query02 != '出库中'" >分批出库</a>
          <a-divider type="vertical" v-has="'BusiOmList:pushWMS'" v-if="record.query02 != '出库中'" />
          <a @click="handleEdit(record)" v-has="'BusiOmList:edit'">编辑</a>
          <a-divider type="vertical" v-has="'BusiOmList:edit'" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-has="'BusiOmList:detail'" >
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item v-has="'BusiOmList:delete'" >
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <busi-om-modal ref="modalForm" @ok="modalFormOk"/>
    <busi-push-om-modal ref="modalForm2" @ok="modalFormOk"/>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BusiOmModal from './modules/BusiOmModal'
  import BusiPushOmModal from './modules/BusiPushOmModal'
  import '@/assets/less/TableExpand.less'
  import { getAction,httpAction } from '@/api/manage'

  export default {
    name: "BusiOmList",
    mixins:[JeecgListMixin],
    components: {
      BusiOmModal,
      BusiPushOmModal
    },
    data () {
      return {
        description: '销售订单页面',
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
            title:'单号',
            align:"center",
            dataIndex: 'query04'
          },
          // {
          //   title:'单据类型',
          //   align:"center",
          //   dataIndex: 'query01'
          // },
          {
            title:'单据状态',
            align:"center",
            dataIndex: 'query02'
          },
          {
            title:'建单日期',
            align:"center",
            dataIndex: 'query03'
          },
          {
            title:'公司',
            align:"center",
            dataIndex: 'query05'
          },
          {
            title:'工厂',
            align:"center",
            dataIndex: 'query06'
          },
          {
            title:'库存地点',
            align:"center",
            dataIndex: 'query07'
          },
          {
            title:'客户编码',
            align:"center",
            dataIndex: 'query08'
          },
          {
            title:'客户名称',
            align:"center",
            dataIndex: 'query09'
          },
          // {
          //   title:'商品编码',
          //   align:"center",
          //   dataIndex: 'query10'
          // },
          // {
          //   title:'商品名称',
          //   align:"center",
          //   dataIndex: 'query11'
          // },
          // {
          //   title:'单位',
          //   align:"center",
          //   dataIndex: 'query12'
          // },
          {
            title:'主PO',
            align:"center",
            dataIndex: 'query13'
          },
          // {
          //   title:'子PO',
          //   align:"center",
          //   dataIndex: 'query14'
          // },
          // {
          //   title:'检验类型',
          //   align:"center",
          //   dataIndex: 'query15'
          // },
          // {
          //   title:'是否报关',
          //   align:"center",
          //   dataIndex: 'query16'
          // },
          {
            title:'装箱单号',
            align:"center",
            dataIndex: 'query17'
          },
          {
            title:'出货方式',
            align:"center",
            dataIndex: 'query18'
          },
          {
            title:'出货地址',
            align:"center",
            dataIndex: 'query19'
          },
          // {
          //   title:'query20',
          //   align:"center",
          //   dataIndex: 'query20'
          // },
          {
            title:'要求交货时间',
            align:"center",
            dataIndex: 'query21'
          },
          // {
          //   title:'总价',
          //   align:"center",
          //   dataIndex: 'num05'
          // },
          // {
          //   title:'query22',
          //   align:"center",
          //   dataIndex: 'query22'
          // },
          // {
          //   title:'单号-行项目号',
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
          //   title:'数量',
          //   align:"center",
          //   dataIndex: 'num01'
          // },
          // {
          //   title:'未完成数量',
          //   align:"center",
          //   dataIndex: 'num02'
          // },
          // {
          //   title:'已完成数量',
          //   align:"center",
          //   dataIndex: 'num03'
          // },
          // {
          //   title:'num04',
          //   align:"center",
          //   dataIndex: 'num04'
          // },
          // {
          //   title:'关联单据类型',
          //   align:"center",
          //   dataIndex: 'link01'
          // },
          // {
          //   title:'关联单号',
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
          {
            title:'备注',
            align:"center",
            dataIndex: 'text01'
          },
          // {
          //   title:'备注',
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
          //   title:'单据附件',
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
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/jeeerp/busiOm/list2",
          delete: "/jeeerp/busiOm/delete",
          deleteBatch: "/jeeerp/busiOm/deleteBatch",
          exportXlsUrl: "/jeeerp/busiOm/exportXls",
          importExcelUrl: "jeeerp/busiOm/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
        queryParam:{
          query01:'XSD'
        },
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
        this.queryParam = {query01: "XSD"}
        this.loadData()
      },
      handleCopy(ids) {
        let orderId = ''
        this.dataSource.map((item,index) => {
            if(item.id == ids[0]) {
              orderId = item.query04
            }
        })
        this.$refs.modalForm.copyAdd(orderId)
      },
      initDictConfig(){
      },
      collectionSchedule(record) {
        this.$router.push({path: '/erp/busiom/BusiOmPlan', query: { orderId: record.query04 }})
      },
      pushWMS(record) {
      let pushData = []
      let params = { query04: record.query04,pageNo:1,pageSize:1000}
      getAction('/jeeerp/busiOm/list',params).then(res => {
        if(res.success) {
          pushData = res.result.records
          // pushData.map((item,index) => {
          //   if(item.num01) {
          //     item.num20 = item.num01
          //     item.num01 = ''
          //   }
          // })
          this.$refs.modalForm2.open(pushData)
        } else {
          this.$message.warning(res.message)
        }   
      })
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