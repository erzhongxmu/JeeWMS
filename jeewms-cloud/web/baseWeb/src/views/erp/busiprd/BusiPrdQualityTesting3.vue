<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('子PO')">
              <a-input v-model="queryParam.query14" :placeholder="$t('请输入子PO号')"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户')">
              <j-popup
                v-model="queryParam.query24"
                field="query08"
                org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                dest-fields="query09,query08"
                code="md_cus"
                :multi="false"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('SKU')">
              <a-input v-model="queryParam.query10" :placeholder="$t('请输入SKU')"></a-input>
              <!-- <j-popup
                  v-model="queryParam.query10"
                  field="goodsCode"
                  org-fields="shp_ming_cheng,goods_code"
                  dest-fields="goodsName,goodsCode"
                  code="wv_goods_select"
                  :multi="false"
                /> -->
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('系统单号')">
              <a-input v-model="queryParam.query04" :placeholder="$t('请输入系统单号')"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('状态')">
              <a-input v-model="queryParam.query02" :placeholder="$t('请输入状态')"></a-input>
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('建单日期')">
              <j-date
                :showTime="true"
                dateFormat="YYYY-MM-DD"
                :placeholder="$t('请选择')"
                v-model="queryParam.query03"
              />
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('检验类型')">
              <j-dict-select-tag v-model="queryParam.query15" :placeholder="$t('请选择检验类型')" dictCode="Test_type" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('内部发票号')">
              <a-input v-model="queryParam.query17" :placeholder="$t('请输入内部发票号')"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('销售单号')">
              <a-input v-model="queryParam.link03" :placeholder="$t('请输入销售单号')"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('采购人')">
              <a-input placeholder="请输入" v-model="queryParam.query16"></a-input>
              <!-- <j-search-select-tag
                type="list"
                v-model="queryParam.query16"
                dict="sys_user,realname,realname,del_flag = '0'"
                :placeholder="$t('请选择')"
              /> -->
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('备注')">
              <a-input v-model="queryParam.text01" :placeholder="$t('请输入备注')"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" v-has="'BusiPrdOrdList:search'"
                >{{$t('查询')}}</a-button
              >
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
                v-has="'BusiPrdOrdList:reset'"
                >{{$t('重置')}}</a-button
              >
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'BusiPrdOrdList:add'">{{$t('新增')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('质检订单')" v-has="'BusiPrdOrdList:export'"
        >{{$t('导出')}}</a-button
      >
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'BusiPrdOrdList:import'" >导入</a-button>
      </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel" v-has="'BusiPrdOrdList:batchDelete'"
            ><a-icon type="delete" />删除</a-menu-item
          >
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
      <a-button @click="handleCopy(selectedRowKeys)" type="primary" icon="copy" v-if="selectedRowKeys.length == 1"
        >{{$t('复制')}}</a-button
      >
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>
      <a-tabs @change="tabChange" defaultActiveKey="未完成">
        <a-tab-pane :tab="$t('未完成')" key="未完成">
          </a-tab-pane>
        <a-tab-pane :tab="$t('已完成')" key="已完成">
        </a-tab-pane>
        </a-tabs>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :scroll="{ x: true }"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        @change="handleTableChange"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">{{$t('无图片')}}</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt=""
            style="max-width: 80px; font-size: 12px; font-style: italic"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">{{$t('无文件')}}</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
            {{$t('下载')}}
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit2(record, 'finish')" v-has="'BusiPrdQualityTesting:finish'" v-if="record.query02 != '已完成'">{{$t('完成')}}</a>
          <a-divider type="vertical" v-has="'BusiPrdQualityTesting:finish'" />

          <a @click="handleEdit(record)" v-has="'BusiPrdQualityTesting:edit'">{{$t('编辑')}}</a>

          <a-divider type="vertical" v-has="'BusiPrdQualityTesting:edit'" />
          <a @click="printBox(record)" >{{$t('批量打印箱唛')}}</a>
          <!-- <a @click="handleUpload(record)" v-has="'BusiPrdQualityTesting:Upload'" >质检凭证</a>
          -->
          <a-divider type="vertical" /> 
          <a-dropdown>
            <a class="ant-dropdown-link">{{$t('更多')}} <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="exportList(record)">{{$t('导出检验单')}}</a>
              </a-menu-item>
              <a-menu-item v-has="'BusiPrdOrdList:detail'">
                <a @click="handleDetail(record)">{{$t('详情')}}</a>
              </a-menu-item>
              <a-menu-item v-has="'BusiPrdQualityTesting:delete'">
                <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>

    <BusiPrdQualityTestingModal ref="modalForm" @ok="modalFormOk" />
    <updateTimeLimit ref="modalForm2" @ok="modalFormOk"  :titles="titles"/>
    <uploadFile ref="uploadFile" @ok="modalFormOk" />
    <ShippingMark ref="ShippingMark" />
    <j-modal
      :visible="printSHow"
      :title="$t('批量打印箱唛')"
      @ok="printOK"
      :width="500"
      @cancel="printclose"
    >
      <a-form>
        <a-form-item :label="$t('每箱数量')">
          <a-input v-model="eachBox" />
        </a-form-item>
      </a-form>
    </j-modal>
  </a-card>
</template>

<script>
import updateTimeLimit from './modules/updateTimeLimit'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BusiPrdQualityTestingModal from './modules/BusiPrdQualityTestingModal'
import uploadFile from '@/components/uploadFile/uploadFile.vue'
import '@/assets/less/TableExpand.less'
import { INSPECTION_SHEET } from '@/utils/PLTN_EXCEL/index' // WMS导出检验单，包装领料单
import { getAction, httpAction, postAction, downFile } from '@/api/manage'
import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
import { BusiPrdQualityTesting } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
import ShippingMark from '@/views/wms/jee/pltn/ShippingMark'
import { OUT_PUT} from '@/utils/util'


export default {
  name: 'BusiPrdOrdList',
  mixins: [JeecgListMixin],
  components: {
    uploadFile,
    updateTimeLimit,
    BusiPrdQualityTestingModal,
    ShippingMark
  },
  data() {
    return {
      printSHow:false,
      eachBox:'',
      printb:{},
      titles:'zj',
      description: '计划订单页面',
      // 表头
      columns: [
        {
          title: this.$t('计划期限'),
          sorter: true,
          align: 'center',
          dataIndex: 'query01',
        },
        {
          title: this.$t('子PO'),
          sorter: true,
          align: 'center',
          dataIndex: 'query02',
        },
        {
          title: this.$t('客户'),
          sorter: true,
          align: 'center',
          dataIndex: 'query03',
        },
        {
          title: this.$t('企业属性'),
          sorter: true,
          align: 'center',
          dataIndex: 'query04',
        },
        {
          title: this.$t('SKU'),
          sorter: true,
          align: 'center',
          dataIndex: 'query05',
        },
        {
          title: this.$t('品名'),
          sorter: true,
          align: 'center',
          dataIndex: 'query06',
        },
        {
          title: this.$t('订单数量'),
          sorter: true,
          align: 'center',
          dataIndex: 'query07',
        },
        {
          title: this.$t('单位'),
          sorter: true,
          align: 'center',
          dataIndex: 'query08',
        },
        {
          title: this.$t('单价(含加工费)'),
          sorter: true,
          align: 'center',
          dataIndex: 'query09',
          NodePermission:'BusiPrdQualityTesting:query33',
          customRender: (text) => {
            return text?OUT_PUT(text):text
          }
        },
        {
          title: this.$t('单价'),
          align: 'center',
          sorter: true,
          dataIndex: 'query10',
          NodePermission:'BusiPrdQualityTesting:query32',
          customRender: (text) => {
            return text?OUT_PUT(text):text
          }
        },
        {
          title: this.$t('税率'),
          align: 'center',
          sorter: true,
          dataIndex: 'query11',
        },
        {
          title: this.$t('验货数量'),
          sorter: true,
          align: 'center',
          dataIndex: 'query12',
        },
        {
          title: this.$t('加工费'),
          sorter: true,
          align: 'center',
          dataIndex: 'query13',
          customRender: (text) => {
            return text?OUT_PUT(text):text
          }
        },
        {
          title: this.$t('工时'),
          sorter: true,
          align: 'center',
          dataIndex: 'query14',
        },
        {
          title: this.$t('检验类型'),
          sorter: true,
          align: 'center',
          dataIndex: 'query15',
        },
        {
          title: this.$t('出货日期'),
          sorter: true,
          align: 'center',
          dataIndex: 'query16',
        },
        {
          title: this.$t('跟单员'),
          sorter: true,
          align: 'center',
          dataIndex: 'query17',
        },
        {
          title: this.$t('状态'),
          sorter: true,
          align: 'center',
          dataIndex: 'query18',
          customRender: (text) => {
            return text?this.$t(text):text
          }
        },
        {
          title: this.$t('销售单号'),
          sorter: true,
          align: 'center',
          dataIndex: 'query19',
        },
        {
          title: this.$t('内部发票号'),
          sorter: true,
          align: 'center',
          dataIndex: 'query20',
        },
        {
          title: this.$t('采购人'),
          sorter: true,
          align: 'center',
          dataIndex: 'query21',
        },
        {
          title: this.$t('创建人'),
          sorter: true,
          align: 'center',
          dataIndex: 'query22',
        }
      ],
      url: {
        list: '/jeeerp/busiPrdOrd/list',
        delete: '/jeeerp/busiPrdOrd/delete',
        deleteBatch: '/jeeerp/busiPrdOrd/deleteBatch',
        exportXlsUrl: '/jeeerp/busiPrdOrd/exportXls',
        importExcelUrl: 'jeeerp/busiPrdOrd/importExcel',
      },
      dictOptions: {},
      superFieldList: [],
      queryParam: {
        query01: 'ZJDD',
         query02: "计划中",
      },
    }
  },
  created() {
    this.getSuperFieldList()
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
    tabChange(active) {
      this.selectedRowKeys = []
      if(active == '未完成'){
        this.queryParam.query02 = "计划中"
      }else{
        this.queryParam.query02 = "已完成"
      }
      this.loadData()
    },
    printBox(e) {
      // 按确定按钮后调用的方法，弹起批量打印箱唛的弹窗
      this.printb = e
      this.printSHow = true
      getAction('/jeewms/mdGoods/listpltn', { shpBianMa: this.printb.query10 }).then(res => {
        if (res.result.records.length != 0) {
          this.eachBox = res.result.records[0].boxQty
        }
      })
    },
    printOK(){
      // 输入数量后，按确定按钮触发的事件
      if (!this.eachBox) {
        this.$message.warning('请输入每箱数量！')
        return
      }
      if (Number(this.eachBox) > Number(this.printb.num01)) {
        this.$message.warning('每箱数量不能大于待入库数量')
        return
      }
      let printContent = []
      let num = Math.floor(this.printb.num01 / this.eachBox)
      getAction('/jeewms/baUnit/list', {unitCode:this.printb.query12}).then(res=>{
        for (let i = 0; i < num; i++) {
        printContent.push({
          ...this.printb,
          cusCode_dictText:this.printb.query24_dictText,
          goodsCode:this.printb.query10,
          goodsName:this.printb.query11,
          unitEnName:res.result.records.length > 0?res.result.records[0].unitEnName:this.printb.query12,
          contractlno:this.printb.query14,
          eachBox: this.eachBox
        })
      }
      let data = {
        list: printContent,
        param: {
          id: this.printb.id,
          type: 'xiangmai',
          pageType: 'DHSH'
        }
      }
      this.$refs.ShippingMark.onShow2(data)
      this.printclose()
      })
    },
    printclose(){
      this.printb = {}
      this.printSHow = false
      this.eachBox = ''
    },
    handleUpload(record) {
      this.$refs.uploadFile.open({...record,query01:'SCWG'})
    },
    exportList(e){
      let params = { orderids: e.query04,type:'ZJDD'}
      getAction('/jeeerp/busiPo/orderExcelOrPdf', params).then((res) => {
        INSPECTION_SHEET(res.result)
        console.log(res.result.records,'-=-=-=');
          // res.result.records
      })
    },
    // handleExportXls(name) {
    //   let myparam = {}
    //   Object.assign(myparam, this.queryParam)
    //   for (var mkey in myparam) {
    //     if (myparam[mkey] != null && myparam[mkey] != '' && myparam[mkey].indexOf('*') == -1) {
    //       myparam[mkey] = '*' + myparam[mkey] + '*'
    //     }
    //   }
    //   let param = { ...myparam }
    //   if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
    //     param['selections'] = this.selectedRowKeys.join(',')
    //   }
    //   getAction('/jeeerp/ExportTableData/ExportBusiPrdQualityTesting', param).then((res) => {
    //     ExportTemplate([],BusiPrdQualityTesting,res.result,name)
    //   })
    // },
    handleEdit2(e, type) {
      this.$refs.modalForm2.open(e, type)
    },
    handleCopy(ids) {
      let orderId = ''
      this.dataSource.map((item, index) => {
        if (item.id == ids[0]) {
          orderId = item.query04
        }
      })
      this.$refs.modalForm.copyAdd(orderId)
    },
    searchReset() {
      this.queryParam = { query01: 'ZJDD' }
      this.loadData()
    },
    initDictConfig() {},
    getSuperFieldList() {
      let fieldList = []
      fieldList.push({ type: 'string', value: 'createName', text: '创建人名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'updateName', text: '更新人名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query01', text: '单据类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query02', text: '单据状态', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query03', text: '建单日期', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query04', text: '单号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query05', text: '公司', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query06', text: '工厂', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query07', text: '库存地点', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query08', text: 'query08', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query09', text: 'query09', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query10', text: '商品编码/成品', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query11', text: '商品名称/成品', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query12', text: '单位', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query13', text: '主PO', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query14', text: '子PO', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query15', text: '检验类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query16', text: 'query16', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query17', text: 'query17', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query18', text: 'query18', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query19', text: 'query19', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query20', text: 'query20', dictCode: '' })
      fieldList.push({ type: 'date', value: 'query21', text: '计划开始时间' })
      fieldList.push({ type: 'date', value: 'query22', text: '计划结束时间' })
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
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>