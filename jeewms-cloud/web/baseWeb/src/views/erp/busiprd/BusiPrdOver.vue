<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('子PO')">
              <a-input v-model="queryParam.query14" :placeholder="$t('请输入子PO')"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <!-- <a-input v-model="queryParam.query24" :placeholder="$t('请输入客户')" ></a-input> -->
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
            <a-form-item :label="$t('子PO')">
              <a-input v-model="queryParam.query14" :placeholder="$t('请输入子PO')"></a-input>
            </a-form-item>
          </a-col> -->

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('建单日期')">
              <j-date
                :showTime="true"
                dateFormat="YYYY-MM-DD"
                :placeholder="$t('请选择')"
                v-model="queryParam.query03"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('生产PO')">
              <a-input v-model="queryParam.query13" :placeholder="$t('请输入生产PO')"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品')">
              <j-popup
                v-model="queryParam.query10"
                field="goodsCode"
                org-fields="shp_ming_cheng,goods_code"
                dest-fields="goodsName,goodsCode"
                code="wv_goods_select"
                :multi="false"
              />
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
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
      <a-button type="primary" icon="download" @click="handleExportXls('生产完工')" v-has="'BusiPrdOver:export'"
        >{{$t('导出')}}</a-button
      >
      <a-popconfirm :title="$t('确定推送吗?')" @confirm="() => pushWMSList()" :disabled="selectedRowKeys.length<=0">
        <a-button type="primary" icon="pull-request" v-show="imStatus == '未推送'" :disabled="selectedRowKeys.length<=0">{{$t('批量推送WMS')}}</a-button>
      </a-popconfirm>
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'BusiPrdOver:import'">导入</a-button>
      </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete" />删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>
      <a-tabs @change="tabChange" defaultActiveKey="未推送">
        <a-tab-pane :tab="$t('未推送')" key="未推送">
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
        <template slot="previewImg" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
          <a-button v-else :ghost="true" type="primary" icon="eye" size="small" @click="previewImg(text)">
            {{$t('预览')}}
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a-popconfirm :title="$t('确定推送吗?')" @confirm="() => pushWMS(record)">
            <a v-if="record.query02 !== '已推送' && record.query02 !== '已完成'">{{$t('推送WMS')}}</a>
          </a-popconfirm>

          <a-divider type="vertical" v-if="record.query02 !== '已推送' && record.query02 !== '已完成'" />
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <a @click="handleUpload(record)">{{$t('质检凭证')}}</a>
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
        </a-tab-pane>
        <a-tab-pane :tab="$t('已推送')" key="已推送">
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
        <template slot="previewImg" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">{{$t('无文件')}}</span>
          <a-button v-else :ghost="true" type="primary" icon="eye" size="small" @click="previewImg(text)">
            {{$t('预览')}}
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a-popconfirm :title="$t('确定推送吗?')" @confirm="() => pushWMS(record)">
            <a v-if="record.query02 !== '已推送' && record.query02 !== '已完成'">{{$t('推送WMS')}}</a>
          </a-popconfirm>
          <a-divider type="vertical" v-if="record.query02 !== '已推送' && record.query02 !== '已完成'" />

          <a v-if="record.query02 == '已推送' || record.query02 == '已完成'" @click="pushWMSOm(record)" v-has="'BusiPoList:pushWMS'">{{$t('推送出库')}}</a>
          <a-divider type="vertical" v-if="record.query02 == '已推送' || record.query02 == '已完成'" />

          <a-popconfirm :title="$t('确定完成吗?')" @confirm="() => handleEdit2(record)">
            <a  >{{$t('完成')}}</a>
          </a-popconfirm>
          <a-divider type="vertical" />
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <a @click="handleUpload(record)">{{$t('质检凭证')}}</a>
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
        </a-tab-pane>
        <a-tab-pane :tab="$t('已完成')" key="已完成">
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
        <template slot="previewImg" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">{{$t('无文件')}}</span>
          <a-button v-else :ghost="true" type="primary" icon="eye" size="small" @click="previewImg(text)">
            {{$t('预览')}}
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a-popconfirm :title="$t('确定推送吗?')" @confirm="() => pushWMS(record)">
            <a v-if="record.query02 !== '已推送' && record.query02 !== '已完成'">{{$t('推送WMS')}}</a>
          </a-popconfirm>
          <a-divider type="vertical" v-if="record.query02 !== '已推送' && record.query02 !== '已完成'" />

          <a v-if="record.query02 == '已推送' || record.query02 == '已完成'" @click="pushWMSOm(record)" v-has="'BusiPoList:pushWMS'">{{$t('推送出库')}}</a>
          <a-divider type="vertical" v-if="record.query02 == '已推送' || record.query02 == '已完成'" />

          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <a @click="handleUpload(record)">{{$t('质检凭证')}}</a>
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
        </a-tab-pane>
      </a-tabs>

    </div>

    <busi-prd-over-modal ref="modalForm" @ok="modalFormOk" />
    <uploadFile ref="uploadFile" @ok="modalFormOk2" />
    <previewFile ref="previewFile" />
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BusiPrdOverModal from './modules/BusiPrdOverModal'
import '@/assets/less/TableExpand.less'
import { getAction } from '../../../api/manage'
import uploadFile from '@/components/uploadFile/uploadFile.vue'
import previewFile from '@/components/previewFile/previewFile.vue'

import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
import { BusiPrdOver } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
export default {
  name: 'BusiPrdOver',
  mixins: [JeecgListMixin],
  components: {
    BusiPrdOverModal,
    uploadFile,
    previewFile,
  },
  data() {
    return {
      description: '生产完工页面',
      imStatus:'未推送',
      // 表头
      columns: [
        // {
        //   title: this.$t('系统单号'),
        //   align: 'center',
        //   dataIndex: 'query04',
        // },
        {
          title: this.$t('完成时间'),
          align: 'center',
          sorter: true,
          dataIndex: 'query22',
        },
        {
          title: this.$t('子PO'),
          align: 'center',
          sorter: true,
          dataIndex: 'query14',
        },
        {
          title: this.$t('客户'),
          align: 'center',
          sorter: true,
          dataIndex: 'query24_dictText',
        },
        {
          title: this.$t('SKU'),
          align: 'center',
          sorter: true,
          dataIndex: 'query10',
        },
        {
          title: this.$t('品名'),
          align: 'center',
          sorter: true,
          dataIndex: 'query11',
        },
        {
          title: this.$t('数量'),
          align: 'center',
          sorter: true,
          dataIndex: 'num01',
        },
        {
          title: this.$t('单位'),
          align: 'center',
          sorter: true,
          dataIndex: 'query12',
        },
        {
          title: this.$t('状态'),
          align: 'center',
          sorter: true,
          dataIndex: 'query02',
          customRender: (text) => {
            return text?this.$t(text):text
          }
        },
        {
          title: this.$t('销售单号'),
          align: 'center',
          sorter: true,
          dataIndex: 'link03',
        },
        {
          title: this.$t('内部发票号'),
          align: 'center',
          sorter: true,
          dataIndex: 'query17',
        },
        {
          title: this.$t('采购人'),
          align: 'center',
          sorter: true,
          dataIndex: 'query16',
        },
        {
          title: this.$t('创建人'),
          align: 'center',
          sorter: true,
          dataIndex: 'createName',
        },
        {
          title: this.$t('创建日期'),
          align: 'center',
          sorter: true,
          dataIndex: 'createTime',
        },
        {
          title: this.$t('质检凭证'),
          align: 'center',
          sorter: true,
          dataIndex: 'attr2',
          scopedSlots: { customRender: 'previewImg' },
        },
        {
          title: this.$t('备注'),
          align: 'center',
          sorter: true,
          dataIndex: 'text01',
        },

        // {
        //   title:'主PO',
        //   align:"center",
        //   dataIndex: 'query13'
        // },

        // {
        //   title:'状态',
        //   align:"center",
        //   dataIndex: 'query02'
        // },
        // {
        //   title:'期限',
        //   align:"center",
        //   dataIndex: 'query21',
        // },
        // {
        //   title:'创建日期',
        //   align:"center",
        //   dataIndex: 'createTime'
        // },
        // {
        //   title:'创建人',
        //   align:"center",
        //   dataIndex: 'createName'
        // },
        // {
        //   title:'采购人',
        //   align:"center",
        //   dataIndex: 'query16'
        // },{
        //   title:'商品编码/成品',
        //   align:"center",
        //   dataIndex: 'query10'
        // },
        // {
        //   title:'商品名称/成品',
        //   align:"center",
        //   dataIndex: 'query11'
        // },{
        //   title:'数量',
        //   align:"center",
        //   dataIndex: 'num01'
        // },
        // {
        //   title:'单位',
        //   align:"center",
        //   dataIndex: 'query12'
        // },
        // {
        //   title: this.$t('单号'),
        //   align: 'center',
        //   sorter: true,
        //   dataIndex: 'query04',
        // },
        // {
        //   title: this.$t('加工单号'),
        //   align: 'center',
        //   sorter: true,
        //   dataIndex: 'link02',
        // },
        // {
        //   title:'备注',
        //   align:"center",
        //   dataIndex: 'text01'
        // },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
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
        query01: 'SCWG',
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
    handleEdit2(e){
      getAction('/jeeerp/busiPrdOrd/updateTimeLimit3',{ordId:e.query04}).then(res=>{
         if (res.success) {
          this.$message.success(res.message)
          this.loadData()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    pushWMSOm(record) {
      // 推送WMS
      this.$router.push({ path: '/erp/busiom/BusiOmOrder', query: { orderId: record.query04,type:"BusiPrdOver" } })
    },
    pushWMSList(){
      let param = {}
      if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
        param['ids'] = this.selectedRowKeys.join(',')
      }
      getAction('/jeeerp/busiPrdOrd/finishedPutin', param).then(res => {
        if (res.success) {
          this.$message.success(res.message)
          this.selectedRowKeys = []
          this.loadData()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
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
      getAction('/jeeerp/ExportTableData/ExportBusiPrdOver', param).then((res) => {
        ExportTemplate([], BusiPrdOver, res.result, name)
      })
    },
    modalFormOk2() {
      // 新增/修改 成功时，重载列表
      this.loadData()
    },
    previewImg(text) {
      // props = 'https://t7.baidu.com/it/u=1819248061,230866778&fm=193&f=GIF,https://t7.baidu.com/it/u=1297102096,3476971300&fm=193&f=GIF'
      // this.$refs.previewFile.open(props)
      this.$refs.previewFile.open(text)
    },
    handleUpload(record) {
      this.$refs.uploadFile.open(record)
    },
    searchReset() {
      this.queryParam = { query01: 'SCWG' }
      this.loadData()
    },
    pushWMS(e) {
      getAction('/jeeerp/busiPrdOrd/finishedPutin', { ids: e.id }).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.loadData()
        } else {
          this.$message.warning(res.message)
        }
      })
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