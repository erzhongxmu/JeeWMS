<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('入库单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.imNoticeId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('子PO号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsBatch"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户')">
              <j-popup
                v-model="queryParam.cusCode"
                code="cus_jiancheng"
                org-fields="zhong_wen_qch,ke_hu_bian_ma"
                dest-fields="cusName,cusCode"
                field="cusCode"
              />
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('货主编码')">
                <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,ke_hu_bian_ma,ke_hu_bian_ma" :placeholder="$t('请选择')"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('货主名称')">
                <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="$t('请选择')"/>
              </a-form-item>
          </a-col>-->
          <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
          <!--              <a-form-item label="订单号">-->
          <!--                <a-input placeholder="请输入订单号" v-model="queryParam.imCusCode"></a-input>-->
          <!--              </a-form-item>-->
          <!--            </a-col>-->
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button
                type="primary"
                @click="searchQuery"
                icon="search"
                v-has="'WmImAcceptIList:search'"
              >{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button
        @click="printBqCode"
        type="primary"
        icon="plus"
        v-has="'WmImAcceptIList:printLabel'"
      >{{$t('样品标签打印')}}</a-button>
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button> -->
      <!--      <a-button icon="download" @click="handleExportXls('商品')">{{$t('导出')}}</a-button>-->
      <!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
      <!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
      <!--      </a-upload>-->
      <a-button @click="batchDel">
        <a-icon type="delete" v-has="'WmImAcceptIList:batchDelete'" />
        {{$t('批量删除')}}
      </a-button>
      <!--      <a-button key="6" @click="batchEdit"><a-icon type="edit" v-has="'imAccept:batchAccept'"/>批量验收</a-button>-->
      <!--      <a-button key="7" @click="batchSave"><a-icon type="save" v-has="'imAccept:batchAccept'"/>批量保存</a-button>-->
      <!--      <a-button key="8" @click="batchCancel"><a-icon type="stop" v-has="'imAccept:batchAccept'"/>取消批量验收</a-button>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>
        {{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
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
          >下载</a-button>
        </template>
        <template slot="goodsPrdData" slot-scope="text, record, index">
          <a-input
            :value="text"
            @blur="e => handleBatchChange(e.target.value, record.id, 'goodsPrdData')"
          />
        </template>
        <template slot="goodsBatch" slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input
              :value="text"
              @blur="e => handleBatchChange(e.target.value, record.id, 'goodsBatch')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="goodsWqmCount" slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input
              :value="text"
              @blur="e => handleBatchChange(e.target.value, record.id, 'goodsWqmCount')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="tinId" slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input
              :value="text"
              @blur="e => handleBatchChange(e.target.value, record.id, 'tinId')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="binPlan" slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input
              :value="text"
              @blur="e => handleBatchChange(e.target.value, record.id, 'binPlan')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <span slot="action" slot-scope="text, record">
          <!-- <a @click="handleEdit(record)">{{$t('编辑')}}</a>
          <a-divider type="vertical" /> -->
          <a @click="openAccept(record)" v-has="'imAccept:accept'">{{$t('验收')}}</a>
          <a-divider type="vertical" v-has="'WmImAcceptIList:printbox'" />
          <a @click="printBox(record)" v-has="'WmImAcceptIList:printbox'">{{$t('批量打印箱唛')}}</a>
          <!--          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                  <a @click="openAccept(record.id)">验收</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>-->
        </span>
      </a-table>
    </div>

    <j-modal
      :visible="printSHow"
      :title="$t('批量打印箱唛')"
      @ok="printOK"
      :width="700"
      @cancel="printclose"
    >
      <a-form :form="form">
        <a-form-item :label="$t('每箱数量')" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-model="eachBox" />
        </a-form-item>
      </a-form>
    </j-modal>
    <j-modal :visible="visibledy1" :title="$t('打印标签')" :width="700" @cancel="visibledy1=false">
      <!-- 自定义弹窗确定按钮，实现打印功能 -->
      <template slot="footer">
        <a-button key="back" @click="visibledy1=false">{{$t('取消')}}</a-button>
        <a-button key="submit" type="primary" :loading="loading" v-print="'#printbq'">{{$t('打印')}}</a-button>
      </template>

      <!-- 打印模板 -->
      <div style="margin-bottom: 20px">
        <div class="printTitle">
          <p>样品类型：</p>
          <j-search-select-tag v-model="bqText" dict="sample_type" :placeholder="$t('请选择')" />
        </div>
        <!-- <div class="printTitle" style="margin-top: 10px">
          <p>份额：</p>
          <a-input v-model="bqNum" />
        </div>-->
        <!-- <a-button  v-print="'#printbq'" style="margin-bottom: 10px">{{$t('打印')}}</a-button> -->
        <div class="printBady" id="printbq" ref="print">
          <div
            class="printItem"
            v-for="(item,index) in selectionRows"
            :key="index"
            style="page-break-after:always"
          >
            <div class="printItem3">
              <div class="printItem_1">
                <p class="printItem_title">{{bqText}}</p>
                <p>- {{item.cusCode_dictText}}</p>
                <p>- {{item.goodsName}}</p>
                <p>Date：</p>
              </div>
              <div class="printItem_2">
                <p class="printItem_title">{{bqText}}</p>
                <p>- {{item.cusCode_dictText}}</p>
                <p>- {{item.goodsName}}</p>
                <p>Date：</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </j-modal>
    <j-modal
      :title="$t('验收')"
      width="800px"
      :visible="showModal"
      class="acceptModal"
      :confirmLoading="confirmLoading"
      switchFullscreen
      @ok="accept"
      @cancel="handleCancel"
    >
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-form-item :label="$t('入库单号')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['imNoticeId']" :disabled="true" />
          </a-form-item>
          <a-form-item :label="$t('商品编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsCode']" :disabled="true"></a-input>
          </a-form-item>
          <a-form-item :label="$t('商品名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsName']" :disabled="true"></a-input>
          </a-form-item>
          <a-form-item :label="$t('货主编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['cusCode']" :disabled="true"></a-input>
          </a-form-item>
          <a-form-item :label="$t('货主名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['cusCode_dictText']" :disabled="true"></a-input>
          </a-form-item>
          <a-form-item :label="$t('通知单数量')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsCount']" :disabled="true"></a-input>
          </a-form-item>
          <a-form-item :label="$t('已验收数量')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsQmCount']" :disabled="true"></a-input>
          </a-form-item>
          <a-form-item :label="$t('未验收数量')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsWqmCount']" :disabled="true"></a-input>
          </a-form-item>

          <a-form-item :label="$t('生产日期')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-date
              placeholder="请选择生产日期"
              v-decorator="['goodsPrdData']"
              @change="e => changeAcceptDate(e)"
              :trigger-change="true"
              style="width: 100%"
               date-format="YYYY-MM-DD HH:mm:ss" 
            />
          </a-form-item>
          <a-form-item :label="$t('子PO号')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsBatch']" placeholder="请输入批次"></a-input>
          </a-form-item>

          <a-form-item :label="$t('验收数量')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['tuoNum',validatorRules.tuoNum]" :placeholder="$t('请输入')"></a-input>
          </a-form-item>
          <!-- <a-form-item :label="储位" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input  v-decorator="['binPlan']" ></a-input>
          </a-form-item>-->
          <a-form-item :label="$t('托盘')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['tinId',validatorRules.tinId]" :placeholder="$t('请输入')"></a-input>
          </a-form-item>
          <a-form-item :label="$t('长')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['tinLength',validatorRules.tinLength]" :placeholder="$t('请输入')"></a-input>
          </a-form-item>
          <a-form-item :label="$t('宽')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['tinWidth',validatorRules.tinWidth]" :placeholder="$t('请输入')"></a-input>
          </a-form-item>
          <a-form-item :label="$t('高')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['tinHigh',validatorRules.tinHigh]" :placeholder="$t('请输入')"></a-input>
          </a-form-item>
          <a-form-item :label="$t('重量')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['tinWeight',validatorRules.tinWeight]" :placeholder="$t('请输入')"></a-input>
          </a-form-item>

          <!-- <a-form-item :label="$t('品质')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-radio-group v-model="imBeizhu">
              <a-radio value="良品"  >
                良品
              </a-radio>
              <a-radio value="不良品">
                不良品
              </a-radio>
            </a-radio-group>
          </a-form-item>-->
        </a-form>
      </a-spin>
    </j-modal>

    <wmImNoticeI-modal ref="modalForm" @ok="modalFormOk"></wmImNoticeI-modal>
    <ShippingMark ref="ShippingMark" />
  </a-card>
</template>

<script>
import WmImNoticeIModal from './modules/WmImNoticeIModal'
import { mixinDevice } from '@/utils/mixin'
import JDate from '@/components/jeecg/JDate'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { postAction, getAction } from '@/api/manage'
import '@/assets/less/TableExpand.less'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import moment from 'moment'
import pick from 'lodash.pick'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import ShippingMark from '@/views/wms/jee/pltn/ShippingMark'

export default {
  name: 'WmImNoticeIList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass],
  components: {
    WmImNoticeIModal,
    JDictSelectTag,
    JDate,
    JSearchSelectTag,
    ShippingMark
  },
  data() {
    return {
      visibledy1: false,
      bqText: 'GROLET LONDON',
      bqNum: 2,
      selectionList: [],
      description: '商品管理页面',
      form: this.$form.createForm(this),
      showModal: false,
      confirmLoading: false,
      aloneAcceptId: '',
      imBeizhu: '良品',
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      validatorRules: {
        tuoNum: {
          rules: [{ required: true, message: this.$t('请输入') }]
        },
        tinId: {
          rules: [{ required: true, message: this.$t('请输入') }]
        },
        tinHigh:{
          rules: [{ required: true, message: this.$t('请输入') }]
        },
        tinLength:{
          rules: [{ required: true, message: this.$t('请输入') }]
        },
        tinWidth:{
          rules: [{ required: true, message: this.$t('请输入') }]
        },
        tinWeight:{
          rules: [{ required: true, message: this.$t('请输入') }]
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
          title:this.$t('子PO号'),
          align: 'left',
          dataIndex: 'contractlno',
          sorter: true
        },
        {
          title: this.$t('商品编码'),
          align: 'left',
          dataIndex: 'goodsCode',
          sorter: true
        },
        {
          title: this.$t('商品名称'),
          align: 'left',
          dataIndex: 'goodsName',
          sorter: true
        },
        {
          title: this.$t('商品中文名称'),
          align: 'left',
          dataIndex: 'ywMingCheng',
          sorter: true
        },
        // {
        //   title:this.$t('货主编码'),
        //   align: 'left',
        //   dataIndex: 'cusCode'
        // },
        {
          title: this.$t('货主名称'),
          align: 'left',
          dataIndex: 'cusCode_dictText',
          sorter: true
        },
        {
          title: this.$t('数量'),
          align: 'left',
          dataIndex: 'goodsCount',
          sorter: true
        },
        {
          title: this.$t('单位'),
          align: 'left',
          dataIndex: 'goodsUnit',
          customRender: (t, r, index) => {
            return this.$t(t)
          },
          sorter: true
        },
        // {
        //   title: this.$t('单价'),
        //   align: 'left',
        //   dataIndex: 'unitPrice',
        //   sorter: true
        // },
        // {
        //   title:this.$t('总价'),
        //   align: 'left',
        //   dataIndex: 'procode'
        // },
        // {
        //   title:'入库日期',
        //   align: 'left',
        //   dataIndex: 'goodsPrdData',
        //   scopedSlots: { customRender: 'goodsPrdData' },
        //   customRender:function (t,r,index) {
        //     var now = new Date();
        //     var year = now.getFullYear();
        //     var month = now.getMonth()+1;
        //     var date = now.getDate();
        //     return year+"-"+month+"-"+date
        //   }
        // },
        // {
        //   title:'批次',
        //   align: 'left',
        //   dataIndex: 'goodsBatch',
        //   scopedSlots: { customRender: 'goodsBatch' },
        // },
        // {
        //   title:'商品分类',
        //   align: 'left',
        //   dataIndex: 'goodsTypeName',
        //   //scopedSlots: { customRender: 'binPlan' },
        // },
        // {
        // title:'生产日期',
        // align: 'left',
        // dataIndex: 'goodsPrdData',
        // scopedSlots: { customRender: 'goodsPrdData' },
        // customRender:function (t,r,index) {
        //   var now = new Date();
        //   var year = now.getFullYear();
        //   var month = now.getMonth()+1;
        //   var date = now.getDate();
        //   return year+"-"+month+"-"+date
        // }
        // // },
        // {
        //   title: this.$t('批次'),
        //   align: 'left',
        //   dataIndex: 'goodsBatch',
        //   scopedSlots: { customRender: 'goodsBatch' },
        //   sorter: true
        // },
        {
          title: this.$t('待入库数'),
          align: 'left',
          dataIndex: 'goodsWqmCount',
          scopedSlots: { customRender: 'goodsWqmCount' },
          sorter: true
        },
        // {
        //   title:'储位',
        //   align: 'left',
        //   dataIndex: 'binPlan',
        //   scopedSlots: { customRender: 'binPlan' },
        // },
        {
          title: this.$t('已入库数'),
          align: 'left',
          dataIndex: 'goodsQmCount',
          sorter: true
        },
        {
          title: this.$t('入库单号'),
          align: 'left',
          dataIndex: 'imNoticeId',
          sorter: true
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          width: 50,
          scopedSlots: { customRender: 'action' }
        }
        // {
        //   title:'品质',
        //   align: 'left',
        //   dataIndex: 'imBeizhu'
        // }
      ],
      url: {
        list: '/jeewms/wmImNoticeH/batchList',
        delete: '/jeewms/wmImNoticeI/delete',
        deleteBatch: '/jeewms/wmImNoticeI/deleteBatch',
        exportXlsUrl: '/jeewms/wmImNoticeI/exportXls',
        importExcelUrl: 'jeewms/wmImNoticeI/importExcel',
        batchAdd: '/jeewms/wmInQmI/batchAdd'
      },
      dictOptions: {},
      //批量修改标记
      batchEditFlag: false,
      //批量修改时新值数据
      newColData: [],
      //批量修改时数据修改行的id集合
      newColIds: [],
      printb: [], // 打印的内容
      printSHow: false, // 是否显示打印箱唛弹窗
      eachBox: '' // 输入的每箱数量值
    }
  },
  watch: {
    bqNum(n, t) {
      if (n) {
        this.countArr(n)
      }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    goPrint(record) {
      // 调用打印组件
    },
    printclose() {
      this.printb = {}
      this.printSHow = false
      this.eachBox = ''
    },
    printOK() {
      // 输入数量后，按确定按钮触发的事件
      if (!this.eachBox) {
        this.$message.warning('请输入每箱数量！')
        return
      }
      if (Number(this.eachBox) > Number(this.printb.goodsWqmCount)) {
        this.$message.warning('每箱数量不能大于待入库数量')
        return
      }
      let printContent = []
      let num = Math.floor(this.printb.goodsWqmCount / this.eachBox)
      for (let i = 0; i < num; i++) {
        printContent.push({
          ...this.printb,
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
      this.$refs.ShippingMark.onShow(data)
      this.printclose()
      // getAction('/jeewms/wmImNoticeH/labelPrints1',{id:this.printb.id,query15:this.eachBox}).then(res => {
      //   if (res.success) {
      //       this.printclose()
      //       this.$message.success(this.$t('操作成功'))
      //     } else {
      //       this.$message.warning(this.$t('操作失败'))
      //     }
      // })
    },
    printBox(e) {
      // 按确定按钮后调用的方法，弹起批量打印箱唛的弹窗
      this.printb = e
      this.printSHow = true
      getAction('/jeewms/mdGoods/listpltn', { shpBianMa: this.printb.goodsCode }).then(res => {
        if (res.result.records.length != 0) {
          this.eachBox = res.result.records[0].boxQty
        }
      })
    },
    countArr(n) {
      // let list = [];
      // this.selectionRows.map((item,index)=>{
      //   for(let i = 0;i<n;i++){
      //     list.push(item)
      //   }
      // })
      // this.selectionList = list
    },
    printBqCode() {
      if (this.selectedRowKeys.length > 0) {
        this.visibledy1 = true
        this.countArr(this.bqNum)
      } else {
        this.$message.warning(this.$t('请选择一条记录!'))
      }
    },
    // handlegetBqCode(){
    //   this.visibledy1 = false

    // },
    initDictConfig() {},
    // getDate(strDate){
    //   var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
    //     function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
    //   return date;
    // },
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
    changeAcceptDate(val) {
      console.log(moment(val).format('YYYYMMDD'))
      // if(val && val._isAMomentObject) {
      var now = new Date()
      var year = now.getFullYear()
      var month = now.getMonth() + 1
      var date = now.getDate()
      let a = year + '' + month + '' + date
      val = 'P' + a + '-' + moment(val).format('YYYYMMDD')
      // val = moment(val).format('YYYY-MM-DD');
      // }
      this.$nextTick(() => {
        this.form.setFieldsValue({
          goodsBatch: val
        })
      })
    },
    //批量保存
    batchSave() {
      var that = this
      if (this.batchEditFlag) {
        postAction(that.url.batchAdd, that.newColData).then(res => {
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
    change() {
      console.log(this.itemText)
    },
    getNowDate(){
      var date = new Date();
      var sign2 = ":";
      var year = date.getFullYear() // 年
      var month = date.getMonth() + 1; // 月
      var day = date.getDate(); // 日
      var hour = date.getHours(); // 时
      var minutes = date.getMinutes(); // 分
      var seconds = date.getSeconds() //秒
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (day >= 0 && day <= 9) {
        day = "0" + day;
      }
      if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
      }
      if (minutes >= 0 && minutes <= 9) {
        minutes = "0" + minutes;
      }
      if (seconds >= 0 && seconds <= 9) {
        seconds = "0" + seconds;
      }
      return year + "-" + month + "-" + day + " " + hour + sign2 + minutes + sign2 + seconds;
    },
    openAccept(record) {
      record.tinId = ''
      this.showModal = true
      // var now = new Date()
      // var year = now.getFullYear()
      // var month = now.getMonth() + 1
      // var date = now.getDate()
      record.goodsPrdData = this.getNowDate()
      this.aloneAcceptId = record.id
      this.$nextTick(() => {
        this.imBeizhu = '良品'
        this.form.setFieldsValue(
          pick(
            record,
            'imNoticeId',
            'goodsCode',
            'goodsPrdData',
            'goodsBatch',
            'goodsWqmCount',
            'tuoNum',
            'tinId',
            'cusCode',
            'cusCode_dictText',
            'goodsName',
            'goodsCount',
            'goodsQmCount'
          )
        )
      })
    },
    accept() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let formData = Object.assign(this.model, values)
          formData.id = that.aloneAcceptId
          formData.imBeizhu = that.imBeizhu
          let param = []
          param.push(formData)
          console.log('表单提交数据', formData)
          postAction(this.url.batchAdd, param)
            .then(res => {
              if (res.success) {
                that.$message.success(this.$t('操作成功'))
                that.loadData()
                that.showModal = false
                that.aloneAcceptId = ''
                that.model = {}
                this.$nextTick(() => {
                  this.form.setFieldsValue({
                    imNoticeId: '',
                    goodsCode: '',
                    goodsPrdData: '',
                    goodsBatch: '',
                    binPlan: '',
                    goodsWqmCount: '',
                    tinId: '',
                    cusCode: '',
                    cusCode_dictText: '',
                    goodsName: '',
                    goodsCount: '',
                    goodsQmCount: '',
                    tuoNum: '',
                    tinLength: '',
                    tinWidth: '',
                    tinHigh: '',
                    tin_weight: ''
                  })
                  that.imBeizhu = ''
                })
              } else {
                that.$message.warning(this.$t('操作失败'))
              }
            })
            .finally(() => {
              that.confirmLoading = false
              that.showModal = false
              that.aloneAcceptId = ''
              that.model = {}
              that.loadData()
              this.$nextTick(() => {
                this.form.setFieldsValue({
                  imNoticeId: '',
                  goodsCode: '',
                  goodsPrdData: '',
                  goodsBatch: '',
                  binPlan: '',
                  goodsWqmCount: '',
                  tinId: '',
                  cusCode: '',
                  cusCode_dictText: '',
                  goodsName: '',
                  goodsCount: '',
                  goodsQmCount: '',
                  tuoNum: '',
                  tinLength: '',
                  tinWidth: '',
                  tinHigh: '',
                  tin_weight: ''
                })
                that.imBeizhu = ''
              })
            })
        }
      })
    },
    handleCancel() {
      this.showModal = false
      this.aloneAcceptId = ''
      this.model = {}
      this.$nextTick(() => {
        this.form.setFieldsValue({
          imNoticeId: '',
          goodsCode: '',
          goodsPrdData: '',
          goodsBatch: '',
          binPlan: '',
          goodsWqmCount: '',
          tinId: ''
        })
        this.imBeizhu = ''
      })
    }
  }
}
</script>
<style scoped lang="less">
@import '~@assets/less/common.less';
/deep/ .ant-modal-content .ant-modal-body {
  max-height: 460px !important;
  overflow-y: auto;
}
/deep/ .ant-modal-content .ant-modal-body .ant-form-item {
  display: inline-block !important;
  width: 50%;
}
.printTitle {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  p {
    width: 30%;
    margin: 0;
  }
}
.printBady {
  background: #eee;
  width: 330px;
  .printItem {
    font-size: 10px;
    width: 330px;
    .printItem3{
      width: 330px;
      height: 106px;
      display: flex;
    }
    .printItem_2 {
      width: 50%;
      height: 100%;
      margin-left: 20px;
      display: inline-block;
    }
    .printItem_1 {
      width: 50%;
      height: 100%;
      display: inline-block;
    }
    .printItem_title {
      font-size: 17px;
    }
    p {
      font-weight: 600;
      color: #000;
      margin: 0;
      line-height: 16px;
    }
  }
}
</style>