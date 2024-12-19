<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-model-item :label="$t('选择PO')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="pocode">
              <j-popup
                v-model="model.pocode"
                code="busi_po_code"
                org-fields="query14"
                dest-fields="query14"
                field="query14"
                @input="popuppocodeCallback"
              />
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item :label="$t('建单日期')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <j-date
                :showTime="true"
                dateFormat="YYYY-MM-DD"
                :placeholder="$t('请选择')"
                v-model="model.query03"
                style="width: 100%"
                :disabled="title == $t('查看') ? true : false"
              />
            </a-form-model-item>
          </a-col> -->

          <a-col :span="8">
            <a-form-model-item :label="$t('期限')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
              <a-range-picker
                @change="onDateChange"
                v-model="query21"
                dateFormat="YYYY/MM/DD'"
                style="width: 100%"
                :disabled="title == $t('查看') ? true : false"
              />
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('客户')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <j-popup
              v-model="model.query08"
              code="cus_jiancheng"
              org-fields="zhong_wen_qch,ke_hu_bian_ma"  
              dest-fields="cusName,cusCode"
              field="cusCode"/>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('公司')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05">
              <a-input v-model="model.query05" :placeholder="$t('请输入公司')" disabled></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('工厂')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
              <a-input v-model="model.query06" :placeholder="$t('请输入工厂')" disabled></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('库存地点')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
              <j-popup
                  v-model="model.query07"
                  field="storage"
                  org-fields="query01,query02,query03"
                  dest-fields="company,factory,storage"
                  code="organization"
                  :multi="false"
                  :disabled="formDisabled"
                  @input="popupCallback"
                />
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('商品编码')/成品" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query10">
              <j-popup
                  v-model="model.query10"
                  field="query10"
                  org-fields="shp_ming_cheng,shp_bian_ma,shl_dan_wei"
                  dest-fields="query11,query10,query12"
                  code="bundle_md_goods"
                  :multi="false"
                  @input="popupCallback1"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('商品名称')/成品" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query11">
              <a-input v-model="model.query11" :placeholder="$t('请输入商品名称')/成品" disabled></a-input>
            </a-form-model-item>
          </a-col> -->
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('数量')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num01">
              <a-input v-model="model.num01" :placeholder="$t('请输入数量')" @change="getSubNum" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8" >
            <a-form-model-item :label="$t('单位')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query12">
              <a-input v-model="model.query12" :placeholder="$t('请输入单位')" disabled ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item :label="$t('检验类型')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query15">
              <j-dict-select-tag v-model="model.query15" :placeholder="$t('请选择检验类型')" dictCode="Test_type" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('内部发票号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
              <a-input v-model="model.query17" :placeholder="$t('请输入内部发票号')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('销售单号')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link03">
              <a-input v-model="model.link03" :placeholder="$t('请输入销售单号')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('采购人')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query16">
              <j-search-select-tag
                type="list"
                v-model="model.query16"
                dict="sys_user,realname,realname,del_flag = '0'"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('质检人')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query25">
              <j-search-select-tag
                type="list"
                v-model="model.query25"
                dict="sys_user,realname,username,del_flag = '0'"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('出货日期')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date
                :showTime="true"
                dateFormat="YYYY-MM-DD"
                :placeholder="$t('请选择')"
                v-model="model.query18"
                style="width: 100%"
                :disabled="title == $t('查看') ? true : false"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('验货指示')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text04">
              <a-textarea v-model="model.text04" :placeholder="$t('请输入验货指示')" rows="4" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('单价')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query32">
              <a-input v-model="model.query32" :placeholder="$t('请输入')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('税率')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query35">
              <j-search-select-tag :placeholder="$t('请选择')" v-model="model.query35" dict="taxRate"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('验货数量')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query32">
              <a-input v-model="model.query34" :placeholder="$t('请输入')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('加工费')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
              <a-input v-model="model.query19" :placeholder="$t('请输入')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('工时')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query31">
              <a-input v-model="model.query31" :placeholder="$t('请输入')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('单价(含加工费)')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query33">
              <a-input v-model="model.query33" :placeholder="$t('请输入')"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('完成时间')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
              <j-date
                :showTime="true"
                dateFormat="YYYY-MM-DD"
                :placeholder="$t('请选择')"
                v-model="model.query22"
                style="width: 100%"
                :disabled="title == $t('查看') ? true : false"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item :label="$t('备注')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
              <a-input v-model="model.text01" :placeholder="$t('请输入备注')"></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8" >
            <a-form-model-item :label="$t('单据附件')" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
              <j-upload v-model="model.attr1" :trigger-change="true" :disabled="title==$t('查看')?true:false"></j-upload>
            </a-form-model-item>
          </a-col> -->
        </a-row>
      </a-form-model>
    </j-form-container>
    <!-- 子表单区域 -->
    <a-spin :spinning="confirmLoading2">
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane :tab="$t('加工商品信息')" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="busiPrdOrdItemTable.loading"
            :columns="busiPrdOrdItemTable.columns"
            :dataSource="busiPrdOrdItemTable.dataSource"
            :maxHeight="300"
            :disabled="formDisabled"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"
          />
        </a-tab-pane>
        <!-- <a-tab-pane tab="加工工艺信息" :key="refKeys[1]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[1]"
            :loading="busiOrdCraftTable.loading"
            :columns="busiOrdCraftTable.columns"
            :dataSource="busiOrdCraftTable.dataSource"
            :maxHeight="300"
            :disabled="formDisabled"
            :rowNumber="true"
          />
        </a-tab-pane> -->
      </a-tabs>
    </a-spin>
  </a-spin>
</template>

<script>
import { getAction ,httpAction} from '@/api/manage'
import { FormTypes, getRefPromise, VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
import { validateDuplicateValue } from '@/utils/util'
import { simpleDebounce } from '@/utils/util'
import JDictSelectTag from '../../../../components/dict/JDictSelectTag.vue'
import { getCurrentTime } from '@/utils/util'

export default {
  name: 'BusiPrdOrdForm',
  mixins: [JEditableTableModelMixin],
  components: { JDictSelectTag },
  data() {
    return {
      query21: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      model: {
        query25:'Bowie',
      },
      confirmLoading2: false,
      // 新增时子表默认添加几行空数据
      addDefaultRowNum: 1,
      validatorRules: {
        num01: [{ required: true, message: ' ' }],
        query03: [{ required: true, message: ' ' }],
        query21: [{ required: true, message: ' ' }],
        query08: [{ required: true, message: ' ' }],
        query05: [{ required: true, message: ' ' }],
        query06: [{ required: true, message: ' ' }],
        query07: [{ required: true, message: ' ' }],
        query10: [{ required: true, message: ' ' }],
        query11: [{ required: true, message: ' ' }],
        num01: [{ required: true, message: ' ' }],
        query12: [{ required: true, message: ' ' }],
        query13: [{ required: true, message: ' ' }],
        pocode: [{ required: true, message: ' ' }],
        query15: [{ required: true, message: ' ' }],
        text04: [{ required: true, message: ' ' }],
        query32: [{ required: true, message: ' ' }],
      },
      refKeys: ['busiPrdOrdItem'],
      tableKeys: ['busiPrdOrdItem'],
      activeKey: 'busiPrdOrdItem',
      // busi_prd_ord_item
      busiPrdOrdItemTable: {
        loading: false,
        dataSource: [],
        columns: [
          {
            title: this.$t('商品编码'),
            key: 'query10',
            type: FormTypes.popup,
            popupCode: 'wv_goods_select',
            destFields: 'query11,query10,query12',
            orgFields: 'shp_ming_cheng,goods_code,baseunit',
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title: this.$t('商品名称'),
            key: 'query11',
            type: FormTypes.input,
            disabled: true,
            width: '200px',
            validateRules: [{ required: true, message: '${title}不能为空' }],
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title: this.$t('单位'),
            key: 'query12',
            type: FormTypes.input,
            disabled: true,
            width: '200px',
            validateRules: [{ required: true, message: '${title}不能为空' }],
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title: this.$t('数量'),
            key: 'num01',
            type: FormTypes.inputNumber,
            width: '200px',
            validateRules: [{ required: true, message: '${title}不能为空' }],
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          // {
          //   title: this.$t('单价'),
          //   key: 'num04',
          //   type: FormTypes.inputNumber,
          //   width: '200px',
          //   placeholder: '请输入${title}',
          //   defaultValue: '',
          // },
          {
            title: this.$t('出库PO'),
            key: 'query14',
            type: FormTypes.input,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title: this.$t('备注'),
            key: 'text01',
            type: FormTypes.input,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
        ],
      },
      // busi_ord_craft
      // busiOrdCraftTable: {
      //   loading: false,
      //   dataSource: [],
      //   columns: [
      //     {
      //       title: this.$t('工艺编码'),
      //       key: 'query10',
      //       type: FormTypes.input,
      //       width:"200px",
      //       placeholder: '请输入${title}',
      //       defaultValue:'',
      //     },
      //     {
      //       title: this.$t('工艺名称'),
      //       key: 'query11',
      //       type: FormTypes.input,
      //       width:"200px",
      //       placeholder: '请输入${title}',
      //       defaultValue:'',
      //     },
      //     {
      //       title: this.$t('备注'),
      //       key: 'text01',
      //       type: FormTypes.input,
      //       width:"200px",
      //       placeholder: '请输入${title}',
      //       defaultValue:'',
      //     },
      //   ]
      // },
      url: {
        add: '/jeeerp/busiPrdOrd/add',
        edit: '/jeeerp/busiPrdOrd/edit',
        queryById: '/jeeerp/busiPrdOrd/queryById',
        busiPrdOrdItem: {
          list: '/jeeerp/busiPrdOrd/queryBusiPrdOrdItemByMainId',
        },
        busiOrdCraft: {
          list: '/jeeerp/busiPrdOrd/queryBusiOrdCraftByMainId',
        },
      },
      materialsList: [],
      copyData: [],
    }
  },
  watch: {
    'model.query10'(newVal, oldVal) {
      if (!newVal) {
        this.busiPrdOrdItemTable.dataSource = []
      }
      // this.days = moment(newVal).daysInMonth()
    },
  },
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false,
    },
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
  },
  created() {},
  methods: {
    popuppocodeCallback(e) {
      if(e.indexOf('PO') != -1){
        let params = { query14: e, query01: 'CGD' }
        getAction('/jeeerp/busiPo/list', params).then((res) => {
          this.copyData = res.result.records
          this.copyData.map((item, index) => {
            item.query04 = null
            item.id = null
            item.createBy = null
            item.createName = null
            item.createTime = null
            item.query23 = null
            item.query22 = null
            item.query21 = null
            item.updateBy = null
            item.query32 = item.num04
            item.num04 = null
            item.updateName = null
            item.updateTime = null
            item.query31 = null
            item.query33 = null
            item.query25 = this.model.query25
            item.query35 = item.num10
            item.query19 = null
          })
          if (this.copyData.length) {
            this.model = this.copyData[0]
            this.model.pocode = e
            this.query21 = null
            let data = []
            this.copyData.map((item) => {
              let obj = {
                query10: item.query10,
                query11: item.query11,
                query12: item.query12,
                num01: item.num01,
                query14: item.query14,
                text01: item.text01,
              }
              data.push(obj)
            })
            this.busiPrdOrdItemTable.dataSource = data
          } else {
            this.model = {}
            this.busiPrdOrdItemTable.dataSource = []
          }
        })  
      }else{
        let params = { query14: e, query01: 'SCWG' }
        getAction('/jeeerp/busiPrdOrd/list', params).then((res) => {
          this.copyData = res.result.records
          this.copyData.map((item, index) => {
            item.query04 = null
            item.id = null
            item.createBy = null
            item.createName = null
            item.createTime = null
            item.query23 = null
            item.query22 = null
            item.query21 = null
            item.updateBy = null
            item.num04 = null
            item.query31 = null
            item.updateName = null
            item.updateTime = null
            item.query33 = null
            item.query25 = this.model.query25
            item.query19 = null
          })
          if (this.copyData.length) {
            this.model = this.copyData[0]
            this.model.pocode = e
            this.query21 = null
            let data = []
            this.copyData.map((item) => {
              let obj = {
                query10: item.query10,
                query11: item.query11,
                query12: item.query12,
                num01: item.num01,
                query14: item.query14,
                text01: item.text01,
              }
              data.push(obj)
            })
            this.busiPrdOrdItemTable.dataSource = data
          } else {
            this.model = {}
            this.busiPrdOrdItemTable.dataSource = []
          }
        })    
      }
    },
    getCopyData(orderId) {
      let params = { query04: orderId, pageNo: 1, pageSize: 1000 }
      getAction('/jeeerp/busiPrdOrd/list', params).then((res) => {
        this.copyData = res.result.records
        this.copyData.map((item, index) => {
          item.query04 = null
          item.id = null
          item.createBy = null
          item.createName = null
          item.createTime = null
          item.query23 = null
          item.updateBy = null
          item.updateName = null
          item.updateTime = null
          item.query19 = null
          item.query03 = getCurrentTime()
        })
        if (this.copyData.length) {
          this.model = this.copyData[0]
          this.query21 = this.model.query21.split('~')
          let goodsData = {
            query11: this.model.query11,
            query10: this.model.query10,
            query12: this.model.query12,
          }
          this.popupCallback1(this.model.query10, goodsData)
        } else {
          this.model = {}
          this.busiPrdOrdItemTable.dataSource = []
        }
      })
    },
    onDateChange: function (value, dateString) {
      console.log(dateString)
      this.model.query21 = dateString[0] + '~' + dateString[1]
    },
    popupCallback1(value, row) {
      getAction('/jeewms/mdGoods/ItemgoodsDetail', { sttr1: value }).then((res) => {
        this.materialsList = res.result
        if (row) {
          this.getMaterialsSubTable()
          this.model.query11 = row.query11
          this.model.query12 = row.query12
        }
      })
    },
    popupCallback(value, row) {
      this.model.query05 = row.company
      this.model.query06 = row.factory
    },
    getMaterialsSubTable() {
      if (this.model.query10) {
        this.confirmLoading2 = true
      }
      let data = []
      this.materialsList.map((item) => {
        let obj = {
          query10: item.shpBianMa,
          query11: item.shpMingCheng,
          query12: item.shlDanWei,
          chlShl: item.chlShl,
          text01: item.text01,
        }
        data.push(obj)
      })
      this.model.pocode = data[0].query14
      this.busiPrdOrdItemTable.dataSource = data
      this.confirmLoading2 = false
      if (this.model.num01) {
        this.getSubNum()
      }
    },
    getSubNum: simpleDebounce(function () {
      this.getAllTable().then((allValues) => {
        allValues[0].getAll().then((Val) => {
          let arr = JSON.parse(JSON.stringify(Val.values))
          if (arr.length > 0) {
            arr.map((item, index) => {
              item.num01 = Number(this.model.num01) * (item.chlShl ? Number(item.chlShl) : 1)
            })
          }
          this.busiPrdOrdItemTable.dataSource = arr
        })
      })
    }, 300),
    addBefore() {
      this.model.id = null // 重置id,否则旧数据残留
      this.busiPrdOrdItemTable.dataSource = []
      // this.busiOrdCraftTable.dataSource=[]
    },
    getAllTable() {
      console.log('1')
      let values = this.tableKeys.map((key) => getRefPromise(this, key))
      return Promise.all(values)
    },
    /** 调用完edit()方法之后会自动调用此方法 */
    editAfter() {
      this.$nextTick(() => {})
      // 加载子表数据
      if (this.model.id) {
        this.query21 = this.model.query21.split('~')
        let params = { id: this.model.query04 }
        this.popupCallback1(this.model.query10)
        this.getSubNum()
        this.requestSubTableData(this.url.busiPrdOrdItem.list, params, this.busiPrdOrdItemTable,this.success)
        this.requestSubTableData(this.url.busiOrdCraft.list, params, this.busiOrdCraftTable)
      } else {
        // 新增
        this.model.query03 = getCurrentTime()
      }
    },
    success(res){
      this.model.pocode = res.result[0].query14
    },
    //校验所有一对一子表表单
    validateSubForm(allValues) {
      if(allValues.tablesValue[0].values.length == 0){
        return this.$message.warning(this.$t('子表为空'))
      }
      return new Promise((resolve, reject) => {
        Promise.all([])
          .then(() => {
            resolve(allValues)
          })
          .catch((e) => {
            if (e.error === VALIDATE_NO_PASSED) {
              // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
              this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
            } else {
              console.error(e)
            }
          })
      })
    },
    /** 整理成formData */
    classifyIntoFormData(allValues) {
      let main = Object.assign(this.model, allValues.formValue)
      return {
        ...main, // 展开
        query10: allValues.tablesValue[0].values[0].query10,
        query11: allValues.tablesValue[0].values[0].query11,
        query12: allValues.tablesValue[0].values[0].query12,
        num01: allValues.tablesValue[0].values[0].num01,
        // num04: "",
        query14: allValues.tablesValue[0].values[0].query14,
        busiPrdOrdItemList: allValues.tablesValue[0].values,
        query01: 'ZJDD',
        // busiOrdCraftList: allValues.tablesValue[1].values,
      }
    },
    validateError(msg) {
      this.$message.error(msg)
    },
    closedata() {
      this.model = {}
      this.busiPrdOrdItemTable.dataSource = []
      this.query21 = []
      this.materialsList = []
    },
    request(formData) {
      let url = this.url.add, method = 'post'
      if (this.model.id) {
        url = this.url.edit
        method = 'put'
      }
      this.confirmLoading = true
      httpAction(url, formData, method).then((res) => {
        if (res.success) {
          this.$message.success(this.$t('操作成功'))
          this.$emit('ok')
          this.close()
        } else {
          this.$message.warning(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
  },
}
</script>

<style scoped>
</style>