<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('装箱单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query01"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('出库日期')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query02"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('公司属性')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query03"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('客户名')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query04"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('目的地')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query05"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('类型')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query06"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('出货人')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query08"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('出货方式')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query09"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('贸易条款')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query10"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('状态')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query11"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item :label="$t('离开日期')">
                <a-input :placeholder="$t('请输入')" class="query-group-cust" v-model="queryParam.query12_begin"></a-input>
                <span class="query-group-split-cust"></span>
                <a-input :placeholder="$t('请输入')" class="query-group-cust" v-model="queryParam.query12_end"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item :label="$t('到达日期')">
                <a-input :placeholder="$t('请输入')" class="query-group-cust" v-model="queryParam.query13_begin"></a-input>
                <span class="query-group-split-cust"></span>
                <a-input :placeholder="$t('请输入')" class="query-group-cust" v-model="queryParam.query13_end"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('货代')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query14"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('追踪单号')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query15"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('追踪链接')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query16"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('Google Folder') ">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query22"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('是否含税')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query23"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('备注')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.query26"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开') }}
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
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('新增')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('busi_om_trace')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
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
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项')}}
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
            {{$t('下载')}}
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">{{$t('更多')}} <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">{{$t('详情')}}</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <busi-om-trace-modal ref="modalForm" @ok="modalFormOk"></busi-om-trace-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BusiOmTraceModal from './modules/BusiOmTraceModal'
  import { OUT_PUT2 } from '@/utils/util'

  export default {
    name: 'BusiOmTraceList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      BusiOmTraceModal
    },
    data () {
      return {
        description: 'busi_om_trace管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title: this.$t('装箱单号'),
            align:"center",
            dataIndex: 'query01'
          },
          {
            title: this.$t('出库日期'),
            align:"center",
            dataIndex: 'query02'
          },
          {
            title: this.$t('公司属性'),
            align:"center",
            dataIndex: 'query03'
          },
          {
            title: this.$t('客户名'),
            align:"center",
            dataIndex: 'query04'
          },
          {
            title: this.$t('目的地'),
            align:"center",
            dataIndex: 'query05'
          },
          {
            title: this.$t('类型'),
            align:"center",
            dataIndex: 'query06'
          },
          {
            title: this.$t('出货人'),
            align:"center",
            dataIndex: 'query08'
          },
          {
            title: this.$t('出货方式'),
            align:"center",
            dataIndex: 'query09'
          },
          {
            title: this.$t('贸易条款'),
            align:"center",
            dataIndex: 'query10'
          },
          {
            title: this.$t('状态'),
            align:"center",
            dataIndex: 'query11'
          },
          {
            title: this.$t('离开日期'),
            align:"center",
            dataIndex: 'query12'
          },
          {
            title: this.$t('到达日期'),
            align:"center",
            dataIndex: 'query13'
          },
          {
            title: this.$t('货代'),
            align:"center",
            dataIndex: 'query14'
          },
          {
            title: this.$t('追踪单号'),
            align:"center",
            dataIndex: 'query15'
          },
          {
            title: this.$t('追踪链接'),
            align:"center",
            dataIndex: 'query16'
          },
          {
            title: this.$t('总体积'),
            align:"center",
            dataIndex: 'query18',
          customRender: text => {
            return text ? Number(text).toFixed(2)  : text
          }
          },
          {
            title: this.$t('总重量'),
            align:"center",
            dataIndex: 'query19',
          customRender: text => {
            return text ? Number(text).toFixed(2) : text
          }
          },
          {
            title: this.$t('总箱数'),
            align:"center",
            dataIndex: 'query20'
          },
          {
            title: this.$t('Google Folder'),
            align:"center",
            dataIndex: 'query22'
          },
          {
            title: this.$t('是否含税'),
            align:"center",
            dataIndex: 'query23'
          },
          {
            title: this.$t('备注'),
            align:"center",
            dataIndex: 'query26'
          },
          {
            title: this.$t('创建日期'),
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title: this.$t('创建人名称'),
            align:"center",
            dataIndex: 'createName'
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
          list: "/jeeerp/busiOmTrace/list",
          delete: "/jeeerp/busiOmTrace/delete",
          deleteBatch: "/jeeerp/busiOmTrace/deleteBatch",
          exportXlsUrl: "/jeeerp/busiOmTrace/exportXls",
          importExcelUrl: "jeeerp/busiOmTrace/importExcel",
          
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
        fieldList.push({type:'string',value:'createName',text:this.$t('创建人名称'),dictCode:''})
        fieldList.push({type:'string',value:'updateName',text:this.$t('更新人名称'),dictCode:''})
        fieldList.push({type:'string',value:'query01',text:this.$t('装箱单号'),dictCode:''})
        fieldList.push({type:'string',value:'query02',text:this.$t('出库日期'),dictCode:''})
        fieldList.push({type:'string',value:'query03',text:this.$t('公司属性'),dictCode:''})
        fieldList.push({type:'string',value:'query04',text:this.$t('客户名'),dictCode:''})
        fieldList.push({type:'string',value:'query05',text:this.$t('目的地'),dictCode:''})
        fieldList.push({type:'string',value:'query06',text:this.$t('类型'),dictCode:''})
        fieldList.push({type:'Text',value:'query07',text:this.$t('产品清单'),dictCode:''})
        fieldList.push({type:'string',value:'query08',text:this.$t('出货人'),dictCode:''})
        fieldList.push({type:'string',value:'query09',text:this.$t('出货方式'),dictCode:''})
        fieldList.push({type:'string',value:'query10',text:this.$t('贸易条款'),dictCode:''})
        fieldList.push({type:'string',value:'query11',text:this.$t('状态'),dictCode:''})
        fieldList.push({type:'string',value:'query12',text:this.$t('离开日期'),dictCode:''})
        fieldList.push({type:'string',value:'query13',text:this.$t('到达日期'),dictCode:''})
        fieldList.push({type:'string',value:'query14',text:this.$t('货代'),dictCode:''})
        fieldList.push({type:'Text',value:'query15',text:this.$t('追踪单号'),dictCode:''})
        fieldList.push({type:'Text',value:'query16',text:this.$t('追踪链接'),dictCode:''})
        fieldList.push({type:'Text',value:'query17',text:this.$t('目的地地址'),dictCode:''})
        fieldList.push({type:'string',value:'query18',text:this.$t('总体积'),dictCode:''})
        fieldList.push({type:'string',value:'query19',text:this.$t('总重量'),dictCode:''})
        fieldList.push({type:'string',value:'query20',text:this.$t('总箱数'),dictCode:''})
        fieldList.push({type:'string',value:'query21',text:this.$t('总费用'),dictCode:''})
        fieldList.push({type:'Text',value:'query22',text:this.$t('Google Folder'),dictCode:''})
        fieldList.push({type:'string',value:'query23',text:this.$t('是否含税'),dictCode:''})
        fieldList.push({type:'string',value:'query24',text:this.$t('报价金额'),dictCode:''})
        fieldList.push({type:'string',value:'query25',text:this.$t('账单金额'),dictCode:''})
        fieldList.push({type:'string',value:'query26',text:this.$t('备注'),dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
