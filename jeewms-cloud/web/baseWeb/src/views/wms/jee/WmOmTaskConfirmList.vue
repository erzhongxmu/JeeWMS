<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item  :label="$t('商品名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item  :label="$t('出货单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.omNoticeId"></a-input>
            </a-form-item>
          </a-col>
          <!--            <a-col :xl="4" :lg="7" :md="8" :sm="24">-->
          <!--              <a-form-item label="货主">-->
          <!--                <j-dict-select-tag placeholder="请选择货主" v-model="queryParam.cusCode" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma"/>-->
          <!--              </a-form-item>-->
          <!--            </a-col>-->
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('货主编码')">
              <j-search-select-tag
                v-model="queryParam.cusCode"
                dict="md_cus,ke_hu_bian_ma,ke_hu_bian_ma"
                 :placeholder="$t('请选择')"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('货主名称')">
              <j-search-select-tag
                v-model="queryParam.cusCode"
                dict="md_cus,zhong_wen_qch,ke_hu_bian_ma"
                 :placeholder="$t('请选择')"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('库位')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.binId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item  :label="$t('托盘')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.tinId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item  :label="$t('子PO号')">
              <a-input :placeholder="$t('请输入子PO号')" v-model="queryParam.goodsBatch"></a-input>
            </a-form-item>
          </a-col>
  <a-col :xl="4" :lg="7" :md="8" :sm="24">
  <a-form-item :label="$t('装箱单号')">
  <a-input :placeholder="$t('请输入装箱单号')" v-model="queryParam.imCusCode"></a-input>
  </a-form-item>
  </a-col>

           <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('是否确认')">
              <a-select v-model="queryParam.binSta">
                <a-select-option value="I">{{$t('未确认')}}</a-select-option>
                <a-select-option value="N">{{$t('已确认')}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item label="单量">
              <a-input  v-model="queryParam.pageSize"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item label="规则">
              <j-search-select-tag
                v-model="queryParam.order_type"
                :dictOptions="[{value:'一单一品',text:'一单一品'},{value:'一单多品',text:'一单多品'}]"
              />
            </a-form-item>
          </a-col> -->
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!--      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>-->
      <a-button icon="download" @click="handleExportXls($t('任务确认'))" v-has="'WmOmTaskConfirmList:export'">{{$t('导出')}}</a-button>
      <!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
      <!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
      <!--      </a-upload>-->
      <a-button key="1" @click="batchDel" v-has="'WmOmTaskConfirmList:batchDelete'">
        <a-icon type="delete" />{{$t('批量删除')}}
      </a-button>
      <a-button v-show="Isshow" @click="batchConfirm" v-has="'WmOmTaskConfirmList:batchConfirm'">{{$t('批量确认')}}</a-button>
      <!-- <a-button @click="batchSetReceive" v-has="'WmOmTaskConfirmList:batchSetRecipient'">批量设置任务接收人</a-button> -->
      <!-- <a-dropdown>
         <a-menu slot="overlay">
         <a-menu-item key="1" >批量</a-menu-item>
         <a-menu-item key="2" >散单</a-menu-item>
         <a-menu-item key="3" >尾波单</a-menu-item>
         <a-menu-item key="4" >区域</a-menu-item>
         <a-menu-item key="5" >品类</a-menu-item>
        </a-menu>
        <a-button  v-has="'WmOmTaskConfirmList:waveGeneration'">波次生成<a-icon type="down" /></a-button>
      </a-dropdown>-->
      <!-- <a-button @click="bcProduce" v-has="'WmOmTaskConfirmList:waveGeneration'">波次生成</a-button>
      <a-button @click="batchBcToVessel" v-has="'WmOmTaskConfirmList:designatedContainer'">波次生成到指定容器</a-button> -->
      <a-button v-show="Isshow"  @click="batchEdit" v-has="'WmOmTaskConfirmList:batchUpdate'">{{$t('批量修改')}}</a-button>
      <a-button v-show="Isshow"  @click="batchSave" v-has="'WmOmTaskConfirmList:batchSave'">{{$t('批量保存')}}</a-button>
      <a-button v-show="Isshow"  @click="batchCancel" v-has="'WmOmTaskConfirmList:cancelBatchUpdate'">{{$t('取消批量修改')}}</a-button>
      <!-- <a-button @click="handlePrint('ck')" v-has="'WmOmTaskConfirmList:BatchPrint'">{{$t('批量打印标签')}}</a-button> -->
      <!-- <a-dropdown>
       <a-menu slot="overlay">
         <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>
         <a-menu-item key="2" @click="batchConfirm"><a-icon type="check"/>批量确认</a-menu-item>
         <a-menu-item key="3" @click="batchSetReceive"><a-icon type="edit"/>批量设置任务接收人</a-menu-item>
         <a-menu-item key="4" @click="bcProduce"><a-icon type="snippets"/>波次生成</a-menu-item>
         <a-menu-item key="5" @click="batchBcToVessel"><a-icon type="snippets"/>波次生成到指定容器</a-menu-item>
         <a-menu-item key="6" @click="batchEdit"><a-icon type="edit"/>修改</a-menu-item>
         <a-menu-item key="7" @click="batchSave"><a-icon type="save"/>保存</a-menu-item>
         <a-menu-item key="8" @click="batchCancel"><a-icon type="stop"/>取消修改</a-menu-item>
       </a-menu>
       <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>
      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange"
        :rowClassName="setRowClsaa"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt
            style="max-width:80px;font-size: 12px;font-style: italic;"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)"
          >{{$t('下载')}}</a-button>
        </template>
        <template slot="tinId" slot-scope="text, record, ">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input
              :value="text"
              @blur="e => handleBatchChange(e.target.value, record.id, 'tinId')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="binId" slot-scope="text, record, ">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input
              :value="text"
              @blur="e => handleBatchChange(e.target.value, record.id, 'binId')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="proData" slot-scope="text, record, ">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-date-picker @change="e => handleBatchChange(e, record.id, 'proData')" />
          </div>
          <div v-else>{{text}}</div>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)" v-has="'WmOmTaskConfirmList:check'">{{$t('查看')}}</a>
          <a-divider v-show="record.binSta == 'I'"  type="vertical" v-has="'WmOmTaskConfirmList:update'" />
          <a v-show="record.binSta == 'I'" @click="handleEdit(record)" v-has="'WmOmTaskConfirmList:update'">{{$t('编辑')}}</a>
          <a-divider type="vertical" v-has="'WmOmTaskConfirmList:delete'" />
          <a-popconfirm
            :title="$t('你确定吗?')"
            @confirm="() => handleDelete(record.id)"
            v-has="'WmOmTaskConfirmList:delete'"
          >
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
    <j-modal
      title="批量设置接收人"
      width="800px"
      :visible="showModal"
      :confirmLoading="confirmLoading"
      switchFullscreen
      @ok="handleSetReceive"
      @cancel="handleCancelSetReceive"

    >
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-form-item
            label="任务接收人"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="jobPersonList != null && jobPersonList.length>0"
          >
            <a-select
              v-decorator="['receiveName',validatorRules.receiveName]"
              placeholder="请选择任务接收人"
            >
              <a-select-option
                v-for="(item,key) in jobPersonList"
                :value="item.username"
                :key="key"
              >{{item.realname}}</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="任务接收人" :labelCol="labelCol" :wrapperCol="wrapperCol" v-else>
            <a-input
              placeholder="请输入任务接收人"
              v-decorator="['receiveName',validatorRules.receiveName]"
            />
          </a-form-item>
        </a-form>
      </a-spin>
    </j-modal>
    <j-modal
      title="波次生成到指定容器"
      width="800px"
      :visible="showModal1"
      :confirmLoading="confirmLoading1"
      switchFullscreen
      @ok="handleSetVessel"
      @cancel="handleCancelSetVessel"

    >
      <a-spin :spinning="confirmLoading1">
        <a-form :form="form1">
          <a-form-item label="波次容器" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入波次容器" v-decorator="['vessel',validatorRules.vessel]" />
          </a-form-item>
        </a-form>
      </a-spin>
    </j-modal>
    <!--    <a-tabs defaultActiveKey="1">-->
    <!--      <a-tab-pane tab="任务明细" key="1" >-->
    <!--        <WmOmQmSecondList :mainId="selectedMainId" />-->
    <!--      </a-tab-pane>-->
    <!--    </a-tabs>-->

    <wmOmTaskConfirm-modal ref="modalForm" @ok="modalFormOk"></wmOmTaskConfirm-modal>
    <export-spin v-if="exportOk"></export-spin>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmOmTaskConfirmModal from './modules/WmOmTaskConfirmModal'
import WmOmQmSecondList from './WmOmQmSecondList'
import { putAction } from '@/api/manage'
import { httpAction } from '@/api/manage'
import { getAction } from '@/api/manage'
import { postAction } from '@/api/manage'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import moment from 'moment'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import ExportSpin from '@/components/ExportSpin/ExportSpin'

export default {
  name: 'WmOmQmIList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass, listCheckMixin],
  components: {
    JDictSelectTag,
    WmOmTaskConfirmModal,
    WmOmQmSecondList,
    JSearchSelectTag,
    ExportSpin,
  },
  mounted() {
    this.loadData()
  },
  data() {
    return {
      Isshow:false,
      queryParam: {binSta:'I'},
      description: '下架任务管理页面',
      selectedMainId: '',
      form: this.$form.createForm(this),
      form1: this.$form.createForm(this),
      validatorRules: {
        receiveName: {
          rules: [{ required: true, message: '请输入任务接收人!' }]
        },
        vessel: {
          rules: [{ required: true, message: '请输入波次容器!' }]
        }
      },
      // 表头
      columns: [
        // {
        //   title: '#',
        //   dataIndex: '',
        //   key:'rowIndex',
        //   width:60,
        //   align: 'left',
        //   customRender:function (t,r,index) {
        //     return parseInt(index)+1;
        //   }
        // },
        {
          title:this.$t('装箱单号'),
          align: 'left',
          dataIndex: 'imCusCode',
          sorter: true
        },
         {
          title:this.$t('子PO号'),
          align: 'left',
          dataIndex: 'goodsBatch',
          sorter: true
        },
         {
          title:this.$t('货主名称'),
          align: 'left',
          dataIndex: 'cusCode_dictText',
          scopedSlots: { customRender: 'cusCode_dictText' },
          sorter: true
        },
        {
          title:this.$t('商品编码'),
          align: 'left',
          dataIndex: 'goodsId',
          scopedSlots: { customRender: 'goodsId' },
          sorter: true
        },
        {
          title:this.$t('商品名称'),
          align: 'left',
          dataIndex: 'goodsName',
          scopedSlots: { customRender: 'goodsName' },
          sorter: true
        },

        // {
        //   title:'出货数量',
        //   align: 'left',
        //   dataIndex: 'omQuat',
        //   scopedSlots: { customRender: 'omQuat' },
        // },
        // {
        //   title: '任务接收人'this.$t('操作'),
        //   align: 'left',
        //   dataIndex: 'assignTo',
        //   scopedSlots: { customRender: 'assignTo' }
        // },
        {
          title:this.$t('数量'),
          align: 'left',
          dataIndex: 'baseGoodscount',
          scopedSlots: { customRender: 'baseGoodscount' },
          sorter: true
        },
        {
          title:this.$t('单位'),
          align: 'left',
          dataIndex: 'goodsUnit',
          sorter: true
        },
        // {
        //   title:this.$t('生产日期'),
        //   align: 'left',
        //   dataIndex: 'proData',
        //   scopedSlots: { customRender: 'proData' }
        // },
        // {
        //   title:this.$t('批次'),
        //   align: 'left',
        //   dataIndex: 'goodsBatch',
        //   scopedSlots: { customRender: 'goodsBatch' }
        // },
        {
          title:this.$t('库位'),
          align: 'left',
          dataIndex: 'binId',
          scopedSlots: { customRender: 'binId' },
          sorter: true
        },
        {
          title:this.$t('托盘'),
          align: 'left',
          dataIndex: 'tinId',
          scopedSlots: { customRender: 'tinId' },
          sorter: true
        },
        // {
        //   title: '是否已下架'this.$t('操作'),
        //   align: 'left',
        //   dataIndex: 'binSta_dictText',
        //   scopedSlots: { customRender: 'binSta_dictText' }
        // },
        {
          title:this.$t('创建人'),
          align: 'left',
          dataIndex: 'createBy',
          sorter: true
        },
        {
          title:this.$t('创建日期'),
          align: 'left',
          dataIndex: 'createTime',
          customRender: (text) => {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
          sorter: true
        },
        // {
        //   title: '货主单号'this.$t('操作'),
        //   align: 'left',
        //   dataIndex: 'imCusCode'
        // },
        // {
        //   title:this.$t('货主编码'),
        //   align: 'left',
        //   dataIndex: 'cusCode',
        //   scopedSlots: { customRender: 'cusCode' }
        // },

        // {
        //   title:'体积',
        //   align: 'left',
        //   dataIndex: 'tinTj',
        //   scopedSlots: { customRender: 'tinTj' },
        // },
        // {
        //   title:'重量',
        //   align: 'left',
        //   dataIndex: 'tinZhl',
        //   scopedSlots: { customRender: 'tinZhl' },
        // },
        // {
        //   title:'温度',
        //   align: 'left',
        //   dataIndex: 'recDeg',
        //   scopedSlots: { customRender: 'recDeg' },
        // },
        // {
        //   title:'基本单位',
        //   align: 'left',
        //   dataIndex: 'baseUnit',
        //   scopedSlots: { customRender: 'baseUnit' },
        // },
        {
          title:this.$t('出货数量'),
          align: 'left',
          dataIndex: 'qmOkQuat',
          scopedSlots: { customRender: 'qmOkQuat' },
          sorter: true
        },
        {
          title:this.$t('体积CM3'),
          align: 'left',
          dataIndex: 'tinVolume',
          scopedSlots: { customRender: 'tinVolume' },
          sorter: true
        },

        {
          title:this.$t('重量KG'),
          align: 'left',
          dataIndex: 'tinWeight',
          scopedSlots: { customRender: 'tinWeight' },
          sorter: true
        },
        {
          title:this.$t('出货单号'),
          align: 'left',
          dataIndex: 'omNoticeId',
          sorter: true
        },
        {
          title: this.$t('是否确认'),
          align: 'left',
          dataIndex: 'binSta',
          customRender: (t, r, index) => {
            return t == 'N' ? this.$t('已确认') : this.$t('未确认')
          }
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
        // {
        //   title:'货主名称',
        //   align: 'left',
        //   dataIndex: 'cusName',
        //   scopedSlots: { customRender: 'cusName' },
        // },
        // {
        //   title:'波次编号',
        //   align: 'left',
        //   dataIndex: 'waveId',
        //   scopedSlots: { customRender: 'waveId' },
        // },
        // {
        //   title:'订单号',
        //   align: 'left',
        //   dataIndex: 'imCusCode',
        //   scopedSlots: { customRender: 'imCusCode' },
        // },
        // {
        //   title:'备注',
        //   align: 'left',
        //   dataIndex: 'itemText',
        //   scopedSlots: { customRender: 'itemText' },
        // }
      ],
      url: {
        list: '/jeewms/wmOmQmI/datagridassign',
        delete: '/jeewms/wmOmQmI/delete',
        deleteBatch: '/jeewms/wmOmQmI/deleteBatch',
        exportXlsUrl: '/jeewms/wmOmQmI/exportXls',
        importExcelUrl: 'jeewms/wmOmQmI/importExcel',
        dotowavedown: '/jeewms/wmOmQmI/dotowavedown',
        editBatch: '/jeewms/wmOmQmI/editBatch',
        doassignbatch: '/jeewms/wmOmQmI/doassignbatch', //批量确认
        insertAssignToBatch: '/jeewms/wmOmQmI/insertAssignToBatch', //批量设置任务接受人
        wavebatch: '/jeewms/wmOmQmI/wavebatch',
        getJobPersonList: '/jeewms/wmOmNoticeH/getJobPersonList'
      },
      dictOptions: {},
      //批量修改标记
      batchEditFlag: false,
      //批量修改时新值数据
      newColData: [],
      //批量修改时数据修改行的id集合
      newColIds: [],
      showModal: false,
      showModal1: false,
      confirmLoading: false,
      confirmLoading1: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      isSameGoodType: true,
      jobPersonList: []
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  mounted(){
    console.log(this.selectionRows)

  },
  methods: {
    searchReset() {
      this.queryParam =  {binSta:'I'}
      this.loadData(1)
    },
    handlePrint(e){
      if (this.selectedRowKeys.length < 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      let ids = this.selectedRowKeys.join(',')
      getAction('/jeewms/wmOmQmI/labelPrinting',{ids}).then(res=>{})
    },
    loadData(arg) {
       if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      this.onClearSelected()
      var params = this.getQueryParams() //查询条件
      if(params.binSta == 'I'){
        this.Isshow = true
      }else{
        this.Isshow = false
      }
      this.loading = true
      getAction(this.url.list, params).then(res => {
        if (res.success) {
          this.dataSource = res.result.records
          this.ipagination.total = res.result.total
          this.ipagination.pageSize = params.pageSize
        }
        if (res.code === 510) {
          this.$message.warning(this.$t('操作失败'))
        }
        this.loading = false
      })
    },
    initDictConfig() {},
    //查看
    searchDetail(id) {
      console.log(id)
      this.selectedMainId = id
    },
    batchEdit() {
      if (this.selectedRowKeys.length > 0) {
        this.batchEditFlag = true
      } else {
        this.$message.warning(this.$t('请选择一条记录!'))
      }
    },
    //可编辑行改变时调用
    handleBatchChange(newValue, id, col) {
      if (newValue && newValue._isAMomentObject) {
        newValue = moment(newValue).format('YYYY-MM-DD')
      }
      var newSingleData = {}
      if (this.newColIds.indexOf(id) != -1) {
        this.newColData.forEach((item, index) => {
          if (item.id == id) {
            this.newColData[index][col] = newValue
          }
        })
      } else {
        this.newColIds.push(id)
        newSingleData[col] = newValue
        newSingleData.id = id
        this.newColData.push(newSingleData)
      }
      console.log(this.newColData, this.newColIds)
    },
    //批量保存
    batchSave() {
      var that = this
      if (this.batchEditFlag) {
        putAction(that.url.editBatch, that.newColData).then(res => {
          if (res.success) {
            that.$message.success('批量修改成功')
            that.newColData = []
            that.newColIds = []
            that.loadData()
            that.batchEditFlag = false
          } else {
            that.$message.warning(this.$t('操作失败'))
          }
        })
      } else {
        this.$message.warning('无可保存内容!')
      }
    },
    //取消批量修改
    batchCancel() {
      if (this.batchEditFlag) {
        this.newColData = []
        this.newColIds = []
        this.batchEditFlag = false
        this.selectedRowKeys = []
      } else {
        this.$message.warning('无修改内容可取消!')
      }
    },
    //批量确认
    batchConfirm() {
      if (this.selectedRowKeys.length < 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      let arr = this.unique(this.selectionRows)
      if(arr.length > 1){
        this.$message.warning('请选择同一单同一商品')
        return
      }
      var that = this
      var param = that.selectedRowKeys
      putAction(that.url.doassignbatch, param).then(res => {
        if (res.success) {
          that.$message.success('批量确认成功')
          that.loadData()
        } else {
          that.$message.warning(this.$t('操作失败'))
        }
      })
    },
    unique(arr1) {
      const res = new Map()
      return arr1.filter((item) => !res.has(item.omNoticeId + item.goodsId) && res.set(item.omNoticeId + item.goodsId, 1))
    },
    //打开批量设置接收人窗口
    batchSetReceive() {
      if (this.selectedRowKeys.length > 0) {
        this.isSameGoodType = true
        var baseType = this.selectionRows[0].goodsTypeId
        for (let i = 0; i < this.selectionRows.length; i++) {
          if (this.selectionRows[i].goodsTypeId != baseType) {
            this.isSameGoodType = false
            break
          }
        }
        if (!this.isSameGoodType) {
          this.$message.warning('请选择商品分类一致的数据!')
        } else {
          postAction(this.url.getJobPersonList, [baseType]).then(res => {
            if (res.success) {
              this.jobPersonList = res.result
              this.showModal = true
            } else {
              this.$message.warning(this.$t('操作失败'))
            }
          })
        }
      } else {
        this.$message.warning(this.$t('请选择一条记录!'))
      }
    },
    //批量设置接收人
    handleSetReceive() {
      this.form.validateFields((err, value) => {
        if (!err) {
          var receiveName = this.form.getFieldValue('receiveName')
          var param = {
            assignTo: receiveName,
            ids: this.selectedRowKeys
          }
          putAction(this.url.insertAssignToBatch, param).then(res => {
            if (res.success) {
              this.showModal = false
              this.selectedRowKeys = []
              this.loadData()
              this.$nextTick(() => {
                this.form.setFieldsValue({
                  //《=关键 清空tag选项
                  receiveName: ''
                })
              })
              this.$message.success('设置成功')
            } else {
              this.$message.warning(this.$t('操作失败'))
            }
          })
        }
      })
    },
    //取消批量设置接收人
    handleCancelSetReceive() {
      this.showModal = false
      this.form.setFieldsValue({
        //《=关键 清空tag选项
        receiveName: ''
      })
    },
    //波次生成
    bcProduce() {
      if (this.selectedRowKeys.length < 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      var param = {
        ids: this.selectedRowKeys
      }
      putAction(this.url.wavebatch, param).then(res => {
        if (res.success) {
          this.loadData()
          this.$message.success('波次生成成功')
        } else {
          this.$message.warning(this.$t('操作失败'))
        }
      })
    },
    //打开波次生成到容器接口
    batchBcToVessel() {
      if (this.selectedRowKeys.length > 0) {
        this.showModal1 = true
      } else {
        this.$message.warning(this.$t('请选择一条记录!'))
      }
    },
    //设置波次容器
    handleSetVessel() {
      this.form1.validateFields((err, value) => {
        if (!err) {
          var vessel = this.form1.getFieldValue('vessel')
          var param = {
            assignTo: vessel,
            ids: this.selectedRowKeys
          }
          putAction(this.url.wavebatch, param).then(res => {
            if (res.success) {
              this.showModal1 = false
              this.selectedRowKeys = []
              this.loadData()
              this.$nextTick(() => {
                this.form1.setFieldsValue({
                  //《=关键 清空tag选项
                  vessel: ''
                })
              })
              this.$message.success('设置成功')
            } else {
              this.$message.warning(this.$t('操作失败'))
            }
          })
        }
      })
    },
    //取消设置波次容器
    handleCancelSetVessel() {
      this.showModal1 = false
      this.form1.setFieldsValue({
        //《=关键 清空tag选项
        vessel: ''
      })
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>