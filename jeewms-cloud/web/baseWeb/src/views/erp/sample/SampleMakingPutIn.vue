<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入商品编码')" v-model="queryParam.query10"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品名称')">
              <a-input :placeholder="$t('请输入商品名称')" v-model="queryParam.query11"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('子PO号')">
              <a-input :placeholder="$t('请输入子PO号')" v-model="queryParam.query14"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('采购人')">
              <a-input placeholder="请输入" v-model="queryParam.query16"></a-input>
              <!-- <j-search-select-tag type="list" v-model="queryParam.query16"
                dict="sys_user,realname,realname,del_flag = '0'" :placeholder="$t('请选择')" /> -->
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户')">
              <j-popup v-model="queryParam.query24" field="query08" org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                dest-fields="query09,query08" code="md_cus" :multi="false" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('供应商')">
              <j-popup v-model="queryParam.query08" field="supplyCode"
                org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch" dest-fields="supplyCode,supplyName,supplyNick"
                code="md_sup" :multi="false" />
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('打样单号')">
              <a-input :placeholder="$t('请输入打样单号')" v-model="queryParam.link02"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('入库单号')">
              <a-input :placeholder="$t('请输入打样单号')" v-model="queryParam.query04"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('销售单号')">
              <a-input :placeholder="$t('请输入销售单号')" v-model="queryParam.link03"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('内部发票号')">
              <a-input :placeholder="$t('请输入内部发票号')" v-model="queryParam.query17"></a-input>
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" v-has="'SampleMakingOrder:search'">{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px"
                v-has="'SampleMakingOrder:reset'">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('新增')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('样品管理')" v-has="'BusiPoOrder:export'">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
        @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'BusiPoOrder:import'">{{$t('导入')}}</a-button>
      </a-upload>
      <a-popconfirm :title="$t('确定推送吗?')" @confirm="() => pushWMSList()" :disabled="selectedRowKeys.length<=0">
        <a-button type="primary" icon="pull-request" v-show="imStatus == '未推送'" :disabled="selectedRowKeys.length<=0">{{$t('批量推送WMS')}}</a-button>
      </a-popconfirm>
      <!-- <a-button @click="pushWMSList" type="primary" icon="pull-request" v-show="imStatus == '未推送'" :disabled="selectedRowKeys.length<=0">批量推送WMS</a-button> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"
              v-has="'BusiPoOrder:batchDelete'" />删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{
          selectedRowKeys.length }}</a>{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-tabs @change="tabChange" defaultActiveKey="未推送">
        <a-tab-pane :tab="$t('未推送')" key="未推送">
          <a-table ref="table" size="middle" bordered rowKey="id" class="j-table-force-nowrap" :scroll="{ x: true }"
        :columns="columns" :dataSource="dataSource" :pagination="ipagination" :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }" @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img v-else :src="getImgView(text)" height="25px" alt=""
            style="max-width:80px;font-size: 12px;font-style: italic;" />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
            {{$t('下载')}}
          </a-button>
        </template>
        <template slot="attr1" slot-scope="text">
          <a-button :ghost="true" type="primary" icon="download" size="small" @click="previewImg(text)">
           {{$t('预览')}}
          </a-button>
        </template>
        <template slot="text01" slot-scope="text">
          <a-popover placement="topLeft">
            <template slot="content">
              <p v-html="comment1(comment(text,'_', 0))"></p>
              <p>Material: {{comment(text,'_',1)}}</p>
              <p>Color: {{comment(text,'_',2)}}</p>
              <p>LOGO: {{comment(text,'_',3)}}</p>
              <p>Others: {{comment(text,'_',4)}}</p>
            </template>
            <span> {{text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}}</span>
          </a-popover>
        </template>
        <span slot="action" slot-scope="text, record">
          <a-popconfirm :title="$t('确定推送吗?')" @confirm="() => pushWMS(record)">
            <a v-if="record.query02 != '已推送' && record.query02 != '已完成'">{{$t('推送WMS')}}</a>
          </a-popconfirm>
          <a-divider type="vertical" v-if="record.query02 != '已推送' && record.query02 != '已完成'" />
          <a @click="handleEdit(record)" v-has="'BusiPoOrder:edit'">{{$t('编辑')}}</a>
          <a-divider type="vertical" v-has="'BusiPoOrder:edit'" />
          <a-dropdown>
            <a class="ant-dropdown-link">{{$t('更多')}} <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-has="'BusiPoOrder:detail'">
                <a @click="handleDetail(record)">{{$t('详情')}}</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

          </a-table>
        </a-tab-pane>
        <a-tab-pane :tab="$t('已推送')" key="已推送">
          <a-table ref="table" size="middle" bordered rowKey="id" class="j-table-force-nowrap" :scroll="{ x: true }"
        :columns="columns" :dataSource="dataSource" :pagination="ipagination" :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }" @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img v-else :src="getImgView(text)" height="25px" alt=""
            style="max-width:80px;font-size: 12px;font-style: italic;" />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
            {{$t('下载')}}
          </a-button>
        </template>
        <template slot="attr1" slot-scope="text">
          <a-button :ghost="true" type="primary" icon="download" size="small" @click="previewImg(text)">
            {{$t('预览')}}
          </a-button>
        </template>
        <template slot="text01" slot-scope="text">
          <a-popover placement="topLeft">
            <template slot="content">
              <p v-html="comment1(comment(text,'_', 0))"></p>
              <p>Material: {{comment(text,'_',1)}}</p>
              <p>Color: {{comment(text,'_',2)}}</p>
              <p>LOGO: {{comment(text,'_',3)}}</p>
              <p>Others: {{comment(text,'_',4)}}</p>
            </template>
            <span> {{text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}}</span>
          </a-popover>
        </template>
        <span slot="action" slot-scope="text, record">
          <a-popconfirm :title="$t('确定推送吗?')" @confirm="() => pushWMS(record)">
            <a v-if="record.query02 != '已推送' && record.query02 != '已完成'">{{$t('推送WMS')}}</a>
          </a-popconfirm>
          <a-divider type="vertical" v-if="record.query02 != '已推送' && record.query02 != '已完成'" />
          <a @click="handleEdit(record)" v-has="'BusiPoOrder:edit'">{{$t('编辑')}}</a>
          <a-divider type="vertical" v-has="'BusiPoOrder:edit'" />
          <a-dropdown>
            <a class="ant-dropdown-link">{{$t('更多')}} <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-has="'BusiPoOrder:detail'">
                <a @click="handleDetail(record)">{{$t('详情')}}</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

          </a-table>
        </a-tab-pane>
      </a-tabs>
    </div>

    <SampleMakingPutInModal ref="modalForm" @ok="modalFormOk" />
    <previewFile ref="previewFile" />
  </a-card>
</template>

<script>

import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import SampleMakingPutInModal from './modules/SampleMakingPutInModal'
import '@/assets/less/TableExpand.less'
import previewFile from '@/components/previewFile/previewFile.vue'
import { getAction, postFormAction, postAction } from '@/api/manage'
import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
import { SampleMakingPutIn } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
export default {
  name: "SampleMakingPutIn",
  mixins: [JeecgListMixin],

  components: {
    SampleMakingPutInModal,
    previewFile,
  },
  data() {
    return {
      description: '来样管理页面',
      imStatus:'未推送',
      total1:'0',
      total2:'0',
      // 表头
      columns: [
        {
          title: this.$t('打样次数'),
            sorter: true,
          align: "center",
          dataIndex: 'query29'
        },
        {
          title: this.$t('评语'),
          align: "center",
            sorter: true,
          width: 180,
          dataIndex: 'text01',
          checked: true,
          scopedSlots: { customRender: 'text01' },
          // customCell: () => {
          //   return {
          //     style: {
          //       width: '100px',
          //       overflow: 'hidden',
          //       whiteSpace: 'nowrap',
          //       textOverflow: 'ellipsis',
          //       cursor: 'pointer',
          //     },
          //   }
          // },
          // customRender: (text, record) => (
          //   <a-tooltip placement="topLeft" title={text}>
          //     {text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}
          //     <p>123</p>
          //   </a-tooltip>
          // ),
        },
        {
          title: this.$t('凭证'),
          align: "center",
            sorter: true,
          dataIndex: 'attr1',
          scopedSlots: { customRender: 'attr1' },
        },

        {
          title: this.$t('子PO'),
          align: "center",
            sorter: true,
          dataIndex: 'query14'
        },
        {
          title: this.$t('客户'),
          align: "center",
            sorter: true,
          dataIndex: 'query24_dictText'
        },
        {
          title: this.$t('商品编码'),
            sorter: true,
          align: "center",
          dataIndex: 'query10'
        },
        {
          title: this.$t('商品名称'),
          align: "center",
            sorter: true,
          dataIndex: 'query11'
        },
        {
          title: this.$t('数量'),
          align: "center",
          dataIndex: 'num01'
        },
        {
          title: this.$t('单位'),
          align: "center",
            sorter: true,
          dataIndex: 'query12'
        },
        {
          title: this.$t('状态'),
          align: "center",
            sorter: true,
          dataIndex: 'query02',
          customRender: (text) => {
            return text?this.$t(text):text
          }
        },
        // {
        //   title: this.$t('到货时间'),
        //   align: "center",
        //     sorter: true,
        //   dataIndex: 'query21'
        // },
        {
          title: this.$t('采购人'),
          align: "center",
            sorter: true,
          dataIndex: 'query16'
        },
        {
          title:this.$t('供应商'),
          align:"center",
            sorter: true,
          dataIndex: 'query08'
        },
        {
          title: this.$t('销售单号'),
          align: "center",
            sorter: true,
          dataIndex: 'link03',
          width: 100,
          checked: true,
          customCell: () => {
            return {
              style: {
                width: '100px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer',
              },
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}
            </a-tooltip>
          ),
        },
        {
          title: this.$t('内部发票号'),
          align: "center",
            sorter: true,
          dataIndex: 'query17',
          width: 100,
          checked: true,
          customCell: () => {
            return {
              style: {
                width: '100px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer',
              },
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}
            </a-tooltip>
          ),
        },
        // {
        //   title: this.$t('入库单号'),
        //   align: "center",
        //   dataIndex: 'query04',
        // },
        // {
        //   title: this.$t('打样单号'),
        //   align: "center",
        //   dataIndex: 'link02',
        //   customRender: (text) => {
        //     console.log(text.split('-')[0])
        //     return text ? text.split('-')[0] : '123'
        //   },
        // },
        {
          title: this.$t('检验类型'),
            sorter: true,
          align: "center",
          dataIndex: 'query15'
        },
        {
          title: this.$t('创建日期'),
          align: "center",
            sorter: true,
          dataIndex: 'createTime'
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: "center",
          fixed: "right",
          width: 147,
          scopedSlots: { customRender: 'action' },
        }
      ],
      url: {
        list: "/jeeerp/busiPo/BusiPoItemList2",
        delete: "/jeeerp/busiPo/delete",
        deleteBatch: "/jeeerp/busiPo/deleteItem",
        exportXlsUrl: "/jeeerp/busiPo/exportXls",
        importExcelUrl: "jeeerp/busiPo/importExcel",

      },
      dictOptions: {},
      superFieldList: [],
      queryParam: {
        query01: 'LYGL'
      }
    }
  },
  created() {
    this.getSuperFieldList();
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
  methods: {
    tabChange(active){
      this.selectedRowKeys = []
      this.imStatus = active
      this.loadData()
    },
      loadData(arg) {
      if(!this.url.list){
        this.$message.error("请设置url.list属性!")
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1;
      }
      var params = this.getQueryParams();//查询条件
      params.query02 = this.imStatus
      this.loading = true;
      getAction(this.url.list, params).then((res) => {
        if (res.success) {
          //update-begin---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
          this.dataSource = res.result.records||res.result;
          if(this.imStatus == '未推送'){
            this.total1 = res.result.total;
          }else if(this.imStatus == '已推送'){
            this.total2 = res.result.total;
          }
          if(res.result.total)
          {
            this.ipagination.total = res.result.total;
          }
          //update-end---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
        }
        if(res.code===510){
          this.$message.warning(res.message)
        }
        this.loading = false;
      })
    },
    comment(text,slicerType,index){
      return text.split(slicerType)[index]
    },
    comment1(text,slicerType,index){
      text =  text.replaceAll('/n', '')
      return 'Size:' + text.replace(/(\r\n)|(\n)/g,'<br>')
    },
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
      getAction('/jeeerp/ExportTableData/ExportBusiPoItem', param).then((res) => {
        ExportTemplate([], SampleMakingPutIn, res.result, name)
      })
    },
    previewImg(text) {
      this.$refs.previewFile.open(text)
    },
    searchReset() {
      this.queryParam = { query01: "LYGL" }
      this.loadData()
    },
    pushWMS(e) {
      getAction('/jeeerp/busiPo/samplePoPushWms', { ids: e.id }).then(res => {
        if (res.success) {
          this.$message.success(res.message)
          this.loadData()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    pushWMSList(){
      let param = {}
      if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
        param['ids'] = this.selectedRowKeys.join(',')
      }
      getAction('/jeeerp/busiPo/samplePoPushWms', param).then(res => {
        if (res.success) {
          this.$message.success(res.message)
          this.selectedRowKeys = []
          this.loadData()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    popupCallback1() { },
    popupCallback2() { },
    popupCallback3() { },
    popupCallback4() { },
    initDictConfig() {
    },
    getSuperFieldList() {
      let fieldList = [];
      fieldList.push({ type: 'string', value: 'createName', text: '创建人名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'updateName', text: '更新人名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query01', text: '单据类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query02', text: '单据状态', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query03', text: '建单日期', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query04', text: '单号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query05', text: '公司', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query06', text: '工厂', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query07', text: '库存地点', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query08', text: '供应商编码', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query09', text: '供应商名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query10', text: '商品编码', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query11', text: '商品名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query12', text: '单位', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query13', text: '主PO', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query14', text: '子PO', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query15', text: '检验类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query16', text: 'query16', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query17', text: 'query17', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query18', text: 'query18', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query19', text: 'query19', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query20', text: 'query20', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query21', text: '预计到货时间', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query22', text: 'query22', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query23', text: '单号-行项目号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query24', text: 'query24', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query25', text: 'query25', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query26', text: 'query26', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query27', text: 'query27', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query28', text: 'query28', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query29', text: 'query29', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query30', text: 'query30', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num01', text: '数量', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num02', text: '未完成数量', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num03', text: '已完成数量', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num04', text: 'num04', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num05', text: 'num05', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link01', text: '关联单据类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link02', text: '关联单号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link03', text: 'link03', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link04', text: 'link04', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link05', text: 'link05', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text01', text: '备注', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text02', text: '备注', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text03', text: 'text03', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text04', text: 'text04', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text05', text: 'text05', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr1', text: '单据附件', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr2', text: 'attr2', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr3', text: 'attr3', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr4', text: 'attr4', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr5', text: 'attr5', dictCode: '' })
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>
