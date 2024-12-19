<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading" style="height: 350px">
      <a-form :form="form">
        <a-form-item label="到货通知单" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['imNoticeId', validatorRules.imNoticeId]"
            :placeholder="title == $t('查看') ? '' : '请输入到货通知单'"
            :disabled="title == $t('查看') || title == $t('编辑')  ? true : false"
          ></a-input>
        </a-form-item>
        <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['goodsId', validatorRules.goodsId]"
            :placeholder="title == $t('查看') ? '' : '请选择货主编码'"
            :disabled="title == $t('查看') || title == $t('编辑')  ? true : false"
          ></a-input>
        </a-form-item>
        <a-form-item label="库位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag
            type="list"
            v-decorator="['binId']"
            :trigger-change="true"
            dict="md_bin,ku_wei_bian_ma,ku_wei_bian_ma"
            :placeholder="title == $t('查看') ? '' : '请输入库位'"
            :disabled="title == $t('查看') || title == $t('编辑')  ? true : false"
          />
        </a-form-item>
        <a-form-item label="收货数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['qmOkQuat', validatorRules.qmOkQuat]"
            :placeholder="title == $t('查看') ? '' : '请选择货主编码'"
            :disabled="title == $t('查看') || title == $t('编辑')  ? true : false"
          ></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['itemText']"
            :placeholder="title == $t('查看') ? '' : '请输入备注'"
            :disabled="title == $t('查看') || title == $t('编辑')  ? true : false"
          ></a-input>
        </a-form-item>
        <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date
            :placeholder="title == $t('查看') ? '' : '请选择生产日期'"
            v-decorator="['proData', validatorRules.proData]"
            :trigger-change="true"
            style="width: 100%"
            :disabled="title == $t('查看') || title == $t('编辑')  ? true : false"
          />
        </a-form-item>
        <a-form-item label="箱码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['tinId',validatorRules.tinId]"
            :placeholder="title == $t('查看') ? '' : '请输入箱码'"
            :disabled="title == $t('查看') ? true : false"
          ></a-input>
        </a-form-item>
        <!--<a-form-item label="批次" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--<a-input v-decorator="['goodsBatch', validatorRules.goodsBatch]" :placeholder="title==$t('查看')?'':'请输入批次'" :disabled="title==$t('查看')?true:false"></a-input>-->
        <!--</a-form-item>-->

        <!--<a-form-item label="温度" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--<a-input v-decorator="['recDeg']" :placeholder="title==$t('查看')?'':'请输入温度'" :disabled="title==$t('查看')?true:false"></a-input>-->
        <!--</a-form-item>-->
      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
import { httpAction, postAction } from '@/api/manage'
import pick from 'lodash.pick'
import { validateDuplicateValue } from '@/utils/util'
import JDate from '@/components/jeecg/JDate'

export default {
  name: 'WmInQmIModal',
  components: {
    JDate,
  },
  props: {
    goodsTypeId: {
      required: false,
      type: String,
    },
  },
  data() {
    return {
      form: this.$form.createForm(this),
      title: '操作',
      width: 800,
      visible: false,
      model: {},
      kwList: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 },
      },
      confirmLoading: false,
      validatorRules: {
        imNoticeId: {
          rules: [{ required: true, message: '请输入到货通知单!' }],
        },
        goodsId: {
          rules: [{ required: true, message: '请输入商品编码!' }],
        },
        qmOkQuat: {
          rules: [{ required: true, message: '请输入收货数量!' }],
        },
        proData: {
          rules: [{ required: true, message: '请输入生产日期!' }],
        },
        tinId: {
          rules: [{ required: true, message: '请输入箱码!' }],
        },
        //          goodsBatch: {
        //            rules: [
        //              { required: true, message: '请输入批次!'},
        //            ]
        //          },
        binId: {
          rules: [{ required: true, message: '请输入仓位!' }],
        },
      },
      url: {
        add: '/jeewms/wmInQmI/add',
        edit: '/jeewms/wmInQmI/edit',
        getKwListByGoodsType: '/jeewms/baKw/getKwListByGoodsType',
      },
    }
  },
  created() {},
  watch: {
    goodsTypeId: {
      immediate: true,
      handler: function (val) {
        if (val != '') {
          this.getKwList(val)
        }
      },
    },
  },
  methods: {
    add() {
      this.edit({})
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(
            this.model,
            'imNoticeId',
            'imNoticeItem',
            'goodsId',
            'imQuat',
            'qmOkQuat',
            'itemText',
            'proData',
            'tinId',
            'goodsUnit',
            'goodsBatch',
            'binId',
            'tinTj',
            'tinZhl',
            'binSta',
            'cusCode',
            'recDeg',
            'baseUnit',
            'baseGoodscount',
            'baseQmcount',
            'cusName',
            'goodsName',
            'imCusCode'
          )
        )
      })
    },
    getKwList(val) {
      return
      postAction(this.url.getKwListByGoodsType, [val]).then((res) => {
        if (res.success) {
          for (let key in res.result) {
            this.kwList.push({ kwCode: key, kwValue: res.result[key] })
          }
          console.log(this.kwList)
          this.showModal = true
        } else {
          this.$message.warning(this.$t('操作失败'))
        }
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.form.setFieldsValue({
        binId: '',
      })
    },
    handleOk() {
      const that = this
      // 触发表单验证
      if (that.title == that.$t('查看')) {
        that.close()
        return
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          let formData = Object.assign(this.model, values)
          console.log('表单提交数据', formData)
          httpAction(httpurl, formData, method)
            .then((res) => {
              if (res.success) {
                that.$message.success(this.$t('操作成功'))
                that.$emit('ok')
              } else {
                that.$message.warning(this.$t('操作失败'))
              }
            })
            .finally(() => {
              that.confirmLoading = false
              that.close()
              this.form.setFieldsValue({
                binId: '',
              })
            })
        }
      })
    },
    handleCancel() {
      this.close()
    },
    popupCallback(row) {
      this.form.setFieldsValue(
        pick(
          row,
          'imNoticeId',
          'imNoticeItem',
          'goodsId',
          'imQuat',
          'qmOkQuat',
          'itemText',
          'proData',
          'tinId',
          'goodsUnit',
          'goodsBatch',
          'binId',
          'tinTj',
          'tinZhl',
          'binSta',
          'cusCode',
          'recDeg',
          'baseUnit',
          'baseGoodscount',
          'baseQmcount',
          'cusName',
          'goodsName',
          'imCusCode'
        )
      )
    },
  },
}
</script>
<style scoped>
>>> .ant-modal-content .ant-modal-body {
  max-height: 460px !important;
  overflow-y: auto;
}
>>> .ant-modal-content .ant-modal-body .ant-form-item {
  display: inline-block !important;
  width: 50%;
}
</style>