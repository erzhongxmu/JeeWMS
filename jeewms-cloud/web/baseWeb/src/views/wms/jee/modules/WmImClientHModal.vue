<template>
  <j-modal
    :title="title"
    :width="1600"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form-model :model="model" :rules="validatorRules" ref="formName">
        <a-row>
          <!-- <a-col :xs="24" :sm="6" v-if="model.orderTypeCode == '06'">
            <a-form-model-item
              label="货主编码"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="cusCode"
            >
              <j-search-select-tag
                @change="e => cusChange(e)"
                type="list"
                v-model="model.cusCode"
                :trigger-change="true"
                dict="md_cus,zhong_wen_qch,ke_hu_bian_ma"
                :placeholder="title!=$t('新增')?'':'请选择货主编码'"
                :disabled="title==$t('查看')?true:false"
              />
            </a-form-model-item>
          </a-col>-->
          <a-col :xs="24" :sm="6">
            <a-form-model-item
              :label="$t('客户')"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="cusCode"
            >
              <j-popup
                v-model="model.cusCode"
                code="cus_jiancheng"
                org-fields="zhong_wen_qch,ke_hu_bian_ma"
                dest-fields="cusName,cusCode"
                field="cusCode"
                @input="popupCallback"
              />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item
              :label="$t('供应商')"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="supCode"
            >
              <!-- <j-search-select-tag
                type="list"
                v-model="model.supCode"
                dict="md_sup,gys_jian_cheng,gys_bian_ma"
                :placeholder="$t('请输入')"
                :disabled="title == $t('查看') ? true : false"
              />-->
              <j-popup
                v-model="model.supCode"
                code="md_sup"
                org-fields="zhong_wen_qch,gys_bian_ma"
                dest-fields="supName,supCode"
                field="supCode"
              />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item
              :label="$t('销售单号')"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="u8ReturnCode"
            >
              <a-input
                v-model="model.u8ReturnCode"
                :placeholder="$t('请输入')"
                :disabled="title == $t('查看') ? true : false"
              ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item
              :label="$t('内部发票号')"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="astreanum"
            >
              <a-input
                v-model="model.astreanum"
                :placeholder="$t('请输入')"
                :disabled="title == $t('查看') ? true : false"
              ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item
              :label="$t('采购')"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="purchasename"
            >
              <j-search-select-tag
                type="list"
                v-model="model.purchasename"
                dict="sys_user,realname,realname,del_flag = '0'"
                :placeholder="$t('请选择')"
                :disabled="title == $t('查看') ? true : false"
              />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6" v-if="model.orderType != '07'">
            <a-form-model-item
              :label="$t('预计到货时间')"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="imData"
            >
              <j-date
                :placeholder="$t('请选择')"
                v-model="model.imData"
                :trigger-change="true"
                style="width: 100%"
                :disabled="title == $t('查看') ? true : false"
              />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6" v-if="model.orderType == '09'">
            <a-form-model-item
              :label="$t('订单类型')"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="orderTypes"
            >
            <j-dict-select-tag
                v-model="model.orderTypes"
                dictCode="qtrkOrder_state"
                :placeholder="$t('请选择')"
                :disabled="title == $t('查看') ? true : false"
              />
            </a-form-model-item>
          </a-col>
          <!-- <a-col :xs="24" :sm="6">
            <a-form-model-item :label="订单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-model="model.orderType" :trigger-change="true" dictCode="ba_order_type,order_type_name,order_type_code,order_type='2'" :placeholder="title!=$t('新增')?'':'请选择订单类型'" :disabled="true" />
            </a-form-model-item>
          </a-col>-->
          <!-- <a-col :xs="24" :sm="6">
            <a-form-model-item
              :label="订单类型"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="orderTypes"
            >
              <j-search-select-tag
                type="list"
                v-model="model.orderTypes"
                :trigger-change="true"
                :dictOptions="[{text:'采购入库',value:'采购入库'},{text:'门店退货入库',value:'门店退货入库'},{text:'配送差异入库',value:'配送差异入库'}]"
                :placeholder="title!=$t('新增')?'':'请选择订单类型'"
                :disabled="title!=$t('新增')?true:false"
              />
            </a-form-model-item>
          </a-col>-->
          <!--<a-col :xs="24" :sm="6">-->
          <!--<a-form-model-item :label="预计到货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--<j-date :placeholder="title!=$t('新增')?'':'请选择预计到货时间'" v-decorator="['imData', validatorRules.imData]" :trigger-change="true" style="width: 100%" :disabled="title!=$t('新增')?true:false"/>-->
          <!--</a-form-model-item>-->
          <!--</a-col>-->
          <!-- <a-col :xs="24" :sm="6">
            <a-form-model-item :label="$t('采购单号')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-model="model.omNoticeId"
                :placeholder="$t('请输入')"
                :disabled="true"
              ></a-input>
            </a-form-model-item>
          </a-col>-->
          <!-- <a-col :xs="24" :sm="6">
            <a-form-model-item :label="货主单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-model="model.imCusCode" :placeholder="title!=$t('新增')?'':'请输入订单号'"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item :label="司机" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-model="model.imCarDri" :placeholder="title!=$t('新增')?'':'请输入司机'"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item :label="司机电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-model="model.imCarMobile" :placeholder="title!=$t('新增')?'':'请输入司机电话'"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item :label="车号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-model="model.imCarNo" :placeholder="title!=$t('新增')?'':'请输入车号'"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6" v-if="title!=$t('新增')">
            <a-form-model-item :label="订单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['orderTypeCode_dictText']" :disabled="true" />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6" v-if="title!=$t('新增')">
            <a-form-model-item :label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['imSta']" :disabled="true"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6" v-if="title!=$t('新增')">
            <a-form-model-item :label="进货通知单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['noticeId']" :disabled="true"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item :label="月台" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag
                type="list"
                v-decorator="['platformCode']"
                :trigger-change="true"
                dictCode="ba_platform,platform_name,platform_code"
                :placeholder="title!=$t('新增')?'':'请选择月台'"
              />
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6">
            <a-form-model-item :label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag
                placeholder="请选择仓库"
                v-model="model.storeCode"
                dict="ba_store,store_code,store_name"
              />
            </a-form-model-item>
          </a-col>-->

          <!-- <a-col :xs="24" :sm="6">
            <a-form-model-item :label="检验单图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-upload v-model="model.fuJian" :number="1" disabled></j-upload>
            </a-form-model-item>
          </a-col>-->
          <a-col :xs="24" :sm="6">
            <a-form-model-item :label="$t('主PO号')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-model="model.imBeizhu" :placeholder="$t('请输入')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xs="24" :sm="6"  v-if="model.orderType == '07'">
            <a-form-model-item :label="$t('批次')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-model="model.whereCon" :placeholder="$t('请输入')"></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
      <a-button
        @click="addTable"
        class="btn"
        type="primary"
        icon="plus"
        style="margin-bottom: 10px;"
      >
        {{
        $t('添加')
        }}
      </a-button>
      <a-table
        ref="table"
        size="middle"
        :scroll="{ x: true }"
        bordered
        rowKey="num"
        :columns="columns"
        :loading="loading"
        :dataSource="dataSource"
        :pagination="false"
      >
        <!-- 循环展示每一个列表 -->
        <div
          slot-scope="text, record, index"
          :slot="v.dataIndex"
          v-for="(v, i) in columns"
          :key="i"
        >
          <!-- 输入框 -->
          <div class="father">
            <a-input
              v-if="v.type == 'input'"
              :style="{ width: v.width, pointerEvent: v.icon ? 'none' : 'auto' }"
              :disabled="v.isDisabled"
              v-model="record[v.dataIndex]"
              :ref="'ref' + index + '' + v.dataIndex"
            >
              <!-- @click="goodsCodeClick(index)" -->
              <a-icon class v-if="v.icon" slot="prefix" :type="v.icon" />
            </a-input>
            <div v-if="v.icon" class="goodsClickBox" @click="showList(v, index)"></div>
          </div>
          <!-- 时间选择 -->
          <a-date-picker
            v-if="v.type == 'date'"
            :disabled="v.isDisabled"
            :style="{ width: v.width }"
            v-model="record[v.dataIndex]"
            @change="e => datePickerChange(e, v.dataIndex, index)"
            format="YYYY-MM-DD"
          />
          <!-- 下拉框 -->
          <!-- dict="measurement_unit" -->
          <j-search-select-tag
            v-if="v.type == 'select'"
            v-model="record[v.dataIndex]"
            :style="{ width: v.width }"
            type="list"
            :dict="v.code"
            :disabled="v.isDisabled"
            :ref="'ref' + index + '' + v.dataIndex"
          />
          <a-radio-group
            :defaultValue="v.default"
            v-model="record[v.dataIndex]"
            v-if="v.type == 'radio'"
          >
            <a-radio
              :value="item.value"
              v-for="(item,index) in v.options"
              :key="index"
            >{{item.text}}</a-radio>
          </a-radio-group>
        </div>
        <span slot="action" slot-scope="text, record">
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handDelete(record.num)">
            <a>{{ $t('删除') }}</a>
          </a-popconfirm>
        </span>
      </a-table>
      <j-modal
        :title="$t('商品列表')"
        :width="1000"
        :visible="visible2"
        class="j-table-force-nowrap"
        :maskClosable="false"
        @ok="handleOk2"
        @cancel="handleCancel2"
      >
        <a-form-model layout="inline" @keyup.enter.native="getGoods">
          <a-row :gutter="24">
            <a-col :xs="24" :sm="8">
              <a-form-model-item :label="$t('商品编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-model="select.shpBianMa"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :xs="24" :sm="8">
              <a-form-model-item :label="$t('商品名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-model="select.shpMingCheng"></a-input>
              </a-form-model-item>
            </a-col>
            <a-col :xs="24" :sm="8">
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                <a-button type="primary" @click="getGoods" icon="search">{{ $t('查询') }}</a-button>
                <a-button
                  type="primary"
                  @click="searchReset"
                  icon="reload"
                  style="margin-left: 8px"
                >
                  {{
                  $t('重置')
                  }}
                </a-button>
              </span>
            </a-col>
          </a-row>
        </a-form-model>
        <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          class="j-table-force-nowrap"
          :scroll="{ x: true }"
          :columns="columns2"
          :dataSource="dataSource2"
          :pagination="ipagination2"
          :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type: 'radio' }"
          :customRow="clickThenSelect"
          @change="handleTableChange"
        ></a-table>
      </j-modal>
    </a-spin>
    <EnterTheLabelModal ref="EnterTheLabelModal" />
  </j-modal>
</template>

<script>
import pick from 'lodash.pick'
import { FormTypes } from '@/utils/JEditableTableUtil'
import { SpecificJEditableTableMixin } from '@/mixins/SpecificJEditableTableMixin'
import { validateDuplicateValue } from '@/utils/util'
import { VALIDATE_NO_PASSED, getRefPromise, validateFormAndTables } from '@/utils/JEditableTableUtil'
import JDate from '@/components/jeecg/JDate'
import JUpload from '@/components/jeecg/JUpload'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import { getAction, httpAction } from '@/api/manage'
import EnterTheLabelModal from '@/views/wms/jee/pltn/EnterTheLabelModal'
import moment from 'dayjs'

export default {
  name: 'WmImNoticeHModal',
  mixins: [SpecificJEditableTableMixin],
  components: {
    JDate,
    JUpload,
    JDictSelectTag,
    EnterTheLabelModal, // WMS标签打印
    JSearchSelectTag
  },
  data() {
    return {
      model: {},
      select: {},
      handleIndex: 0,
      columns2: [
        {
          title: this.$t('商品编码'),
          align: 'left',
          dataIndex: 'shpBianMa'
        },
        {
          title: this.$t('商品名称'),
          align: 'left',
          dataIndex: 'shpMingCheng'
        },
        {
          title: this.$t('英文名称'),
          align: 'left',
          dataIndex: 'ywMingCheng'
        },
        {
          title: this.$t('产品属性'),
          align: 'left',
          dataIndex: 'chpShuXing'
        },
        {
          title: this.$t('单位'),
          align: 'left',
          dataIndex: 'shlDanWei'
        }
      ],
      dataSource2: [],
      selectedRowKeys: [],
      selectionRows: [],
      selectedMainId: '',
      visible2: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      labelCol2: {
        xs: { span: 24 },
        sm: { span: 3 }
      },
      wrapperCol2: {
        xs: { span: 24 },
        sm: { span: 20 }
      },
      paramData: {
        type: '',
        param: ''
      },
      // 新增时子表默认添加几行空数据
      addDefaultRowNum: 1,
      validatorRules: {
        cusCode: [{ required: true, message: this.$t('请选择') }],
        // imData: [{ required: true, message: this.$t('请选择') }],
        orderTypes: [{ required: true, message: this.$t('请选择') }],
        imclientcode: [{ required: true, message: this.$t('请选择') }],
        supCode: [{ required: true, message: this.$t('请选择') }],
        u8ReturnCode: [{ required: true, message: this.$t('请输入') }]
      },
      validatorRules2: ['goodsCode', 'goodsUnit', 'goodsCount'],
      refKeys: ['wmImNoticeI'],
      tableKeys: ['wmImNoticeI'],
      activeKey: 'wmImNoticeI',
      loading: false,
      dataSource: [],
      columns: [
        {
          title: this.$t('子PO号'),
          align: 'left',
          dataIndex: 'contractlno',
          scopedSlots: { customRender: 'contractlno' },
          width: '100px',
          type: 'input'
        },
        // {
        //   title: this.$t('子PO号'),
        //   align: 'left',
        //   dataIndex: 'goodsBatch',
        //   scopedSlots: { customRender: 'goodsBatch' },
        //   width: '100px',
        //   type: 'input'
        // },
        {
          title: this.$t('商品编码'),
          align: 'left',
          dataIndex: 'goodsCode',
          scopedSlots: { customRender: 'goodsCode' },
          width: '200px',
          type: 'input',
          icon: 'funnel-plot',
          isDisabled: true
        },
        {
          title: this.$t('商品名称'),
          align: 'left',
          dataIndex: 'goodsName',
          scopedSlots: { customRender: 'goodsName' },
          width: '200px',
          type: 'input',
          isDisabled: true
        },
        {
          title: this.$t('中文名称'),
          align: 'left',
          dataIndex: 'ywMingCheng',
          scopedSlots: { customRender: 'ywMingCheng' },
          type: 'input',
          isDisabled: true
        },
        {
          title: this.$t('单位'),
          align: 'left',
          dataIndex: 'goodsUnit',
          scopedSlots: { customRender: 'goodsUnit' },
          width: '100px',
          type: 'input',
          isDisabled: true
        },
        // {
        //   title: this.$t('批次'),
        //   align: 'left',
        //   dataIndex: 'goodsBatch',
        //   scopedSlots: { customRender: 'goodsBatch' },
        //   width: '100px',
        //   type: 'input'
        // },
        {
          title: this.$t('计划数量'),
          align: 'left',
          dataIndex: 'goodsCount',
          scopedSlots: { customRender: 'goodsCount' },
          width: '100px',
          type: 'input'
        },
        // {
        //   title: '入库单价',
        //   align: 'left',
        //   dataIndex: 'unitPrice',
        //   scopedSlots: { customRender: 'unitPrice' },
        //   width:"200px",
        //   type:'input'
        // },
        // {
        //   title: this.$t('单价'),
        //   align: 'left',
        //   dataIndex: 'unitPrice',
        //   scopedSlots: { customRender: 'unitPrice' },
        //   width: '100px',
        //   type: 'input'
        // },
        {
          title: this.$t('收货数量'),
          align: 'left',
          dataIndex: 'goodsQmCount',
          scopedSlots: { customRender: 'goodsQmCount' },
          width: '100px',
          type: 'input',
          isDisabled: true
        },
        {
          title: this.$t('验货类型'),
          align: 'left',
          dataIndex: 'totalamountvat',
          scopedSlots: { customRender: 'totalamountvat' },
          width: '100px',
          type: 'select',
          code: 'Test_type',
          shows: ['07']
        },
        // {
        //   title: this.$t('发货日期'),
        //   align: 'left',
        //   dataIndex: 'chukudate',
        //   scopedSlots: { customRender: 'chukudate' },
        //   width: '150px',
        //   type: 'date'
        // },
        // {
        //   title: this.$t('总价'),
        //   align: 'left',
        //   dataIndex: 'procode',
        //   scopedSlots: { customRender: 'procode' },
        //   width: '100px',
        //   type: 'input'
        // },
        // {
        //   title: '不含税总价',
        //   align: 'left',
        //   dataIndex: 'totalamout',
        //   scopedSlots: { customRender: 'totalamout' },
        //   width: '100px',
        //   type: 'input',
        // },
        {
          title: this.$t('是否已完成'),
          align: 'left',
          dataIndex: 'binPre',
          scopedSlots: { customRender: 'binPre' },
          width: '200px',
          type: 'radio',
          default: 'N',
          isDisabled: true,
          options: [
            {
              value: 'Y',
              text:  this.$t('是')
            },
            {
              value: 'N',
              text: this.$t('否')
            }
          ]
        },
        {
          title: this.$t('备注'),
          align: 'left',
          dataIndex: 'imBeizhu',
          scopedSlots: { customRender: 'imBeizhu' },
          type: 'input'
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        add: '/jeewms/wmImNoticeH/add',
        edit: '/jeewms/wmImNoticeH/edit',
        wmImNoticeI: {
          list: '/jeewms/wmImNoticeH/queryWmImNoticeIByMainId'
        }
      },
      ipagination2: {
        current: 1,
        pageSize: 5,
        pageSizeOptions: ['5', '10', '50'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' ' + this.$t('共') + ' ' + total + ' ' + this.$t('项目')
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      }
    }
  },
  methods: {
    popupCallback(e,t,key){
      this.model.cusName = t.cusName
    },
    showList(v, index) {
      // this.v.isDisabled = false
      if (v.icon) {
        this.goodsCodeClick(index)
      }
    },
    datePickerChange(e, key, index) {
      // console.log(this.dataSource[index][key])
      this.dataSource[index][key] = moment(e).format('YYYY-MM-DD')
    },
    PLTN_print(record) {
      this.$refs.EnterTheLabelModal.onShow(record)
    },
    searchReset() {
      this.select = {}
      this.getGoods()
    },
    handleTableChange(res) {
      this.ipagination2 = res
      this.getGoods()
    },
    // 选择商品后确定
    handleOk2() {
      let obj = {
        goodsCode: this.selectionRows[0].shpBianMa,
        goodsName: this.selectionRows[0].shpMingCheng,
        goodsUnit: this.selectionRows[0].shlDanWei,
        ywMingCheng: this.selectionRows[0].ywMingCheng
      }
      this.dataSource.splice(this.handleIndex, 1, { ...this.dataSource[this.handleIndex], ...obj })
      this.handleCancel2()
      this.getSelectList(this.handleIndex)
    },
    getSelectList(index) {
      getAction('/jeewms/mdGoods/listpageDetail', { shpBianMa: this.dataSource[index].goodsCode }).then(res => {
        let arr = []
        res.result.map((item, index) => {
          arr.push({
            text: item.unit2,
            value: item.unit2
          })
        })
        // this.$set(this.dataSource2[index], 'dictOptions', arr)
        this.dataSource.splice(this.handleIndex, 1, { ...this.dataSource[index], dictOptions: arr })
      })
    },
    // 关闭商品弹窗
    handleCancel2() {
      this.visible2 = false
    },
    clickThenSelect(record) {
      return {
        on: {
          click: () => {
            this.onSelectChange(record.id.split(','), [record])
          }
        }
      }
    },
    onClearSelected() {
      this.selectedRowKeys = []
      this.selectionRows = []
      this.selectedMainId = ''
    },
    onSelectChange(selectedRowKeys, selectionRows) {
      this.selectedMainId = selectedRowKeys[0]
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows
    },
    goodsCodeClick(index) {
      this.handleIndex = index
      console.log(index)
      this.visible2 = true
      this.getGoods()
    },
    // 获取商品列表
    getGoods() {
      let obj = {
        ...this.select,
        pageNo: this.ipagination2.current,
        pageSize: this.ipagination2.pageSize
        // shpBianMakh: this.model.cusCode
      }
      getAction('/jeewms/mdGoods/list', obj).then(res => {
        this.dataSource2 = res.result.records
        this.ipagination2.total = res.result.total
      })
    },
    // 添加
    addTable() {
      this.dataSource.push({ dictOptions: [{ text: '' }] })
      console.log(this.dataSource,739)
      this.getTableNum()
    },
    getTableNum() {
      this.dataSource.map((item, index) => {
        item.num = index
      })
    },
    // 删除
    handDelete(num) {
      this.dataSource.map((item, index) => {
        if (item.num == num) {
          this.dataSource.splice(index, 1)
        }
      })
      this.getTableNum()
    },
    cusChange(val) {
      this.paramData.param = val
    },
    getAllTable() {
      let values = this.tableKeys.map(key => getRefPromise(this, key))
      return Promise.all(values)
    },
    /** 当点击新增按钮时调用此方法 */
    add(e) {
      console.log(123123)
      this.closeData()
      this.visible = true
      this.title = this.$t('新增')
      this.model = {
        orderType: e
      }
      if (e == '07') {
        this.validatorRules = {
          cusCode: [{ required: true, message: this.$t('请选择') }],
          // imData: [{ required: true, message: this.$t('请选择') }],
          orderTypes: [{ required: true, message: this.$t('请选择') }],
          imclientcode: [{ required: true, message: this.$t('请选择') }]
        }
      }else if (e == '09') {
        this.validatorRules = {
          cusCode: [{ required: true, message: this.$t('请选择') }],
        }
      }
      let data = JSON.parse(JSON.stringify(this.columns))
      data.map((item, index) => {
        if (item.shows && item.shows.indexOf(this.model.orderType) != -1) {
          data.splice(index, 1)
        }
      })
      this.columns = data
      console.log(data)
    },
    /** 调用完edit()方法之后会自动调用此方法 */
    editAfter() {
      // 加载子表数据
      if (this.model.noticeId) {
        let params = { id: this.model.noticeId }
        this.loadData(params)
        if (this.model.orderTypeCode == '07') {
          this.validatorRules = {
            cusCode: [{ required: true, message: this.$t('请选择') }],
            orderTypes: [{ required: true, message: this.$t('请选择') }],
            imclientcode: [{ required: true, message: this.$t('请选择') }]
          }
        }else if (this.model.orderTypeCode == '09') {
          this.validatorRules = {
            cusCode: [{ required: true, message: this.$t('请选择') }],
          }
        }
        // this.requestSubTableData(this.url.wmImNoticeI.list, params, this.wmImNoticeITable)
      }
    },
    /** 整理成formData */
    classifyIntoFormData(allValues) {
      let main = Object.assign(this.model, allValues.formValue)
      return {
        ...main, // 展开
        wmImNoticeIList: allValues.tablesValue[0].values
      }
    },
    validateError(msg) {
      this.$message.error(msg)
    },
    // 验证子表是否有空值
    verification() {
      let index = 0
      if (this.dataSource.length == 0) {
        this.$message.error('商品列表为空')
        return false
      }
      this.dataSource.map((item, y) => {
        this.validatorRules2.map((v, i) => {
          if (!item[v]) {
            index = 1
            if (v == 'goodsCode') {
              this.$refs['ref' + y + '' + v][0].$refs.input.style.borderColor = '#f00'
            } else {
              this.$refs['ref' + y + '' + v][0].$el.style.borderColor = '#f00'
            }
          } else {
            if (v == 'goodsCode') {
              this.$refs['ref' + y + '' + v][0].$refs.input.style.borderColor = '#D9D9D9'
            } else {
              this.$refs['ref' + y + '' + v][0].$el.style.borderColor = '#D9D9D9'
            }
          }
        })
      })
      if (index == 1) {
        return false
      } else {
        return true
      }
    },
    handleOk(e) {
      if (this.title == this.$t('查看')) {
        this.close()
        return
      }
      console.log(this.dataSource)
      this.$refs.formName.validate(valid => {
        if (valid) {
          if (!this.verification()) return
          this.confirmLoading = true
          this.dataSource.map((item,index)=>{
            item.goodsBatch = item.contractlno
          })
          let formData = Object.assign({ ...this.model, wmImNoticeIList: this.dataSource })
          let url = this.url.add
          let get = 'post'
          if (this.title == this.$t('编辑')) {
            url = this.url.edit
            get = 'put'
          }
          
          httpAction(url, formData, get).then(res => {
            this.confirmLoading = false
            if (res.success) {
              this.close()
              this.closeData()
              this.$emit('ok')
              this.$message.success(this.$t(res.message))
            } else {
              this.$message.error(this.$t(res.message))
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    closeData() {
      this.dataSource = []
      this.dataSource2 = []
      this.selectedRowKeys = []
      this.selectionRows = []
      this.selectedMainId = ''
    },
    loadData(params) {
      this.loading = true
      getAction(this.url.wmImNoticeI.list, params).then(res => {
        if (res.success) {
          this.dataSource = res.result
          this.getTableNum()
        }
        if (res.code === 510) {
          this.$message.warning(this.$t('操作失败'))
        }
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>
>>> .father {
  position: relative;
}

>>> .goodsClickBox {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  cursor: pointer;
  background-color: transparent;
  z-index: 100;
}
>>> .ant-table-wrapper .ant-table-row-cell-break-word {
  text-align: center !important;
}
>>> .ant-modal-content .ant-modal-body {
  max-height: 700px !important;
  overflow-y: auto;
}
>>> .ant-input-affix-wrapper .ant-input-suffix {
  display: none !important;
}
>>> .ant-select .ant-select-selection {
  background-color: transparent;
  border: none;
  border-radius: 0px;
  height: 31px;
}
>>> .ant-select {
  background-color: #fff;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  height: 32px;
}
/deep/ .ant-table-tbody > tr > td {
  white-space: nowrap;
}
</style>
