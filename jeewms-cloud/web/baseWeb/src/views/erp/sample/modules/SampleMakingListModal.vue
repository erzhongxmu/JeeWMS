<template>
  <a-spin :spinning="confirmLoading">
    <j-modal
      :title="$t('打样进度')"
      :width="1400"
      :visible="visible"
      :maskClosable="false"
      switchFullscreen
      @ok="handleOk"
      @cancel="handleCancel"
    >
      <j-editable-table
        :ref="refKeys[0]"
        :loading="sampleMakingSchedule.loading"
        :columns="sampleMakingSchedule.columns"
        :dataSource="sampleMakingSchedule.dataSource"
        :maxHeight="300"
        :disabled="formDisabled"
        :rowNumber="true"
      >
        <template v-slot:action="props">
          <a @click="handleFinish(props)" v-if="!sampleMakingSchedule.dataSource[props.index].query02">{{
            $t('去处理')
          }}</a>
          <span v-else>{{$t('已更新完成')}}</span>
        </template>
        <template v-slot:attr1="props">
          <a-button :ghost="true" type="primary" icon="download" size="small" @click="previewImg(props)">
            {{$t('预览')}}
          </a-button>
        </template>
      </j-editable-table>
    </j-modal>
    <SampleMakingListForm ref="SampleMakingListForm" @ok="editAfter" />
    <previewFile ref="previewFile" />
  </a-spin>
</template>

<script>
import { getAction, httpAction } from '@/api/manage'
import { FormTypes, getRefPromise, VALIDATE_NO_PASSED, validateTables } from '@/utils/JEditableTableUtil'
import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
import { validateDuplicateValue } from '@/utils/util'
import JEditableTable from '../../../../components/jeecg/JEditableTable.vue'
import SampleMakingListForm from './SampleMakingListForm'
import previewFile from '@/components/previewFile/previewFile.vue'

export default {
  name: 'SampleMakingOrderForm',
  mixins: [JEditableTableModelMixin],
  components: {
    JEditableTable,
    SampleMakingListForm,
    previewFile
  },
  data() {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      labelCol2: {
        xs: { span: 24 },
        sm: { span: 3 },
      },
      wrapperCol2: {
        xs: { span: 24 },
        sm: { span: 20 },
      },
      confirmLoading: false,
      visible2: false,
      visible: false,
      model: {},
      // 新增时子表默认添加几行空数据
      addDefaultRowNum: 1,
      validatorRules: {},
      inBatchesList: [],
      refKeys: ['busiPoItem'],
      tableKeys: ['busiPoItem'],
      activeKey: 'busiPoItem',
      // busi_po_item
      sampleMakingSchedule: {
        loading: false,
        dataSource: [],
        columns: [
          {
            title: this.$t('操作'),
            key: 'action',
            align: 'center',
            fixed: 'right',
            width: '150px',
            type: FormTypes.slot,
          },
          {
            title: this.$t('子PO'),
            key: 'query14',
            type: FormTypes.input,
            width: '150px',
            placeholder: '请输入${title}',
            defaultValue: '',
            disabled: true,
          },
          {
            title: this.$t('商品名称'),
            key: 'query11',
            type: FormTypes.input,
            disabled: true,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
          },
          {
            title: this.$t('商品编码'),
            key: 'query10',
            type: FormTypes.input,
            width: '200px',
            placeholder: '请输入${title}',
            defaultValue: '',
            disabled: true,
            disabled: true,
          },
          {
            title: this.$t('数量'),
            key: 'num01',
            type: FormTypes.input,
            width: '150px',
            placeholder: '请输入${title}',
            defaultValue: '',
            disabled: true,
          },
          {
            title: this.$t('单位'),
            key: 'query12',
            type: FormTypes.input,
            width: '150px',
            placeholder: '请输入${title}',
            disabled: true,
            defaultValue: '',
          },
          {
            title: this.$t('打样结果'),
            key: 'query02',
            type: FormTypes.input,
            width: '150px',
            placeholder: '请输入${title}',
            defaultValue: '',
            disabled: true,
          },
          {
            title: this.$t('处理日期'),
            key: 'updateTime',
            type: FormTypes.input,
            width: '150px',
            placeholder: '请输入${title}',
            defaultValue: '',
            disabled: true,
          },
          {
            title: this.$t('样品文件'),
            key: 'attr1',
            type: FormTypes.slot,
            width: '150px',
            placeholder: '请输入${title}',
            disabled: true,
            defaultValue: '',
          },
          {
            title: this.$t('评语'),
            key: 'text01',
            type: FormTypes.input,
            width: '150px',
            placeholder: '请输入${title}',
            disabled: true,
            defaultValue: '',
          }
        ],
      }, 
      url: {
        add: '/jeeerp/busiPo/batchAdd',
        edit: '/jeeerp/busiPo/batchEdit',
        queryById: '/jeeerp/busiPo/queryById',
        busiPoItem: {
          list: '/jeeerp/busiPo/queryBusiPoItemByMainId',
        },
      },
    }
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
    handleCancel2() {
      this.visible2 = false
    },
    previewImg(props) {
      this.$refs.previewFile.open(props.text)
    },
    open(record) {
      this.model = record
      this.visible = true
      this.editAfter()
    },
    handleOk() {
      this.handleCancel()
    },
    handleCancel() {
      this.visible = false
    },
    handleFinish(e) {
      let id = e.rowId
      this.$refs.SampleMakingListForm.open(id)
    },
    editAfter() {
      if (this.model.id) {
        let params = { link02: this.model.query23, query01:'DYJD', pageNo: 1, pageSize: 1000 }
        getAction('/jeeerp/busiPo/BatchQueryBusiPoItemByMainId', params).then((res) => {
          this.sampleMakingSchedule.dataSource = res.result
        })
      }
    },
  },
}
</script>

<style scoped lang="less">
.ant-carousel {
  .slick-slide {
    text-align: center;
    height: 300px;
    line-height: 300px;
    overflow: hidden;
    img {
      width: 100%;
      height: 100%;
    }
  }
  .custom-slick-arrow:hover {
    opacity: 0.5;
  }
  .custom-slick-arrow {
    width: 25px;
    height: 25px;
    font-size: 25px;
    color: #fff;
    opacity: 0.3;
  }
  .custom-slick-arrow:before {
    display: none;
  }
}
</style>