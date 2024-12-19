<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk2"
    @cancel="close"
    
  >
    <a-button style="margin:0 10px 10px 10px;" type="primary" @click="addH">{{$t('添加')}}</a-button>
    <a-table
      ref="table"
      size="middle"
      bordered
      rowKey="num"
      :columns="columns"
      :dataSource="dataSource"
      class="j-table-force-nowrap"
      @change="handleTableChange2"
      :pagination="false"
    >
      <div slot-scope="text,record,index" :slot="v.dataIndex" v-for="(v,i) in columns" :key="i">
        <a-input
          style="width:100px"
          v-if="handleIndex == record.num"
          v-model="record[v.dataIndex]"
          :disabled="v.isDisabled"
        ></a-input>
        <span v-else>{{record[v.dataIndex]}}</span>
      </div>

      <span slot="action" slot-scope="text, record">
        <a v-if="handleIndex == record.num" @click="save(record)">{{$t('保存')}}</a>
        <a v-else @click="Edit2(record)">{{$t('编辑')}}</a>
        <a-divider type="vertical" />
        <a-popconfirm
          :title="$t('你确定吗?')"
          @confirm="() => handleDisabled(record.id,record.num)"
          v-has="'part:delete'"
        >
          <a>{{$t('删除')}}</a>
        </a-popconfirm>
      </span>
    </a-table>
    <j-modal
      :title="$t('商品列表')"
      :width="1100"
      :visible="visible2"
      :maskClosable="false"
      @ok="handleOk3"
      @cancel="handleCancel2"
    >
      <a-form-model layout="inline" @keyup.enter.native="getGoods">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-model-item
              :label="$t('商品编码')"
              :labelCol="{xs: { span: 10 }, sm: { span: 7 },}"
              :wrapperCol="{xs: { span: 10 },  sm: { span: 14 }}"
            >
              <a-input v-model="select.shpBianMa"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-model-item
              :label="$t('商品名称')"
              :labelCol="{xs: { span: 10 }, sm: { span: 7 },}"
              :wrapperCol="{xs: { span: 10 },  sm: { span: 14 },}"
            >
              <a-input v-model="select.shpMingCheng"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="getGoods" icon="search">{{$t('查询')}}</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >{{$t('重置')}}</a-button>
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
        :scroll="{x:true}"
        :columns="columns2"
        :dataSource="dataSource2"
        :pagination="ipagination2"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type:'radio'}"
        :customRow="clickThenSelect"
        @change="handleTableChange"
      ></a-table>
    </j-modal>
  </j-modal>
</template>

<script>
import { getAction, httpAction, putAction, deleteAction, postAction } from '@/api/manage'
import pick from 'lodash.pick'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import JMultiSelectTag from '@/components/dict/JMultiSelectTag'

export default {
  name: 'MdGoodsModal',
  components: {
    JDictSelectTag,
    JSearchSelectTag,
    JMultiSelectTag
  },
  data() {
    return {
      select: {},
      handleIndex: -1,
      columns2: [
        {
          title:this.$t('商品编码'),
          align: 'left',
          dataIndex: 'shpBianMa'
        },
        {
          title:this.$t('商品名称'),
          align: 'left',
          dataIndex: 'shpMingCheng'
        },
        {
          title:this.$t('产品属性'),
          align: 'left',
          dataIndex: 'chpShuXing'
        },
        {
          title:this.$t('商品规格'),
          align: 'left',
          dataIndex: 'shpGuiGe'
        },
        // {
        //   title:this.$t('单位'),
        //   align: 'left',
        //   dataIndex: 'shlDanWei'
        // }
      ],
      dataSource2: [],
      selectedRowKeys: [],
      selectionRows: [],
      selectedMainId: '',
      visible2: false,
      ipagination2: {
        current: 1,
        pageSize: 5,
        pageSizeOptions: ['5', '10', '50'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' ' + this.$t('共') + ' '  + total + ' ' + this.$t('项目')
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      title: 'BOM',
      dataSource: [],
      ipagination: {
        current: 1,
        pageSize: 5,
        pageSizeOptions: ['5'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' ' + this.$t('共') + ' '  + total + ' ' + this.$t('项目')
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      columns: [
        {
          title:this.$t('商品编码'),
          align: 'left',
          dataIndex: 'sttr2'
        },
        {
          title: this.$t('商品名称'),
          align: 'left',
          dataIndex: 'shpMingCheng'
        },
        {
          title:this.$t('数量'),
          align: 'left',
          dataIndex: 'num2',
          scopedSlots: { customRender: 'num2' }
        },
        {
          title: this.$t('主商品编码'),
          align: 'left',
          dataIndex: 'sttr1'
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          width: 200,
          scopedSlots: { customRender: 'action' }
        }
      ],
      toggleSearchStatus: false,
      form: this.$form.createForm(this),
      width: 1000,
      visible: false,
      model: {},
      fbFlag: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 - 8 }
      },
      confirmLoading: false,
      validatorRules: {},
      url: {
        list: '/jeewms/mdGoods/listpageDetail',
        add: '/jeewms/mdGoods/addDetail',
        edit: '/jeewms/mdGoods/editDetail',
        delete: '/jeewms/mdGoods/deleteDetail',
        getKwList: '/jeewms/baKw/getKwList',
        findByKeHuBianMa: '/jeewms/mdCus/findByKeHuBianMa',
        getKwListByGoodsType: '/jeewms/baKw/getKwListByGoodsType'
      },
      kwList: []
    }
  },
  mounted() {
    //this.getKwList()
  },
  created() {},
  methods: {
    save(e) {
      if(!e.num2) return this.$message.error('请输入数量')
      if (e.id) {
        putAction(this.url.edit, e).then(res => {
          if (res.success) {
            this.$message.success(this.$t('操作成功'))
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
          this.gitList()
        })
      } else {
        postAction(this.url.add, e).then(res => {
          if (res.success) {
            this.$message.success(this.$t('操作成功'))
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
          this.gitList()
        })
      }
      this.handleIndex = -1
    },
    Edit2(e) {
      this.handleIndex = e.num
    },
    handleOk3() {
      let obj = {
        sttr2: this.selectionRows[0].shpBianMa,
        shpMingCheng: this.selectionRows[0].shpMingCheng,
        unit1: this.selectionRows[0].shlDanWei,
        sttr1: this.model.shpBianMa
      }
      this.dataSource.push(obj)
      this.setNum()
      this.handleCancel2()
    },
    handleCancel2() {
      this.visible2 = false
    },
    searchReset() {
      this.select = {}
      this.getGoods()
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
    handleTableChange(res) {
      this.ipagination2 = res
      this.getGoods()
    },
    handleTableChange2(res){
      this.ipagination = res
    },
    addH() {
      this.visible2 = true
      this.getGoods()
    },
    getGoods() {
      let obj = {
        ...this.select,
        pageNo: this.ipagination2.current,
        pageSize: this.ipagination2.pageSize
      }
      getAction('/jeewms/mdGoods/listMatrial', obj).then(res => {
        this.dataSource2 = res.result.records
        this.ipagination2.total = res.result.total
      })
    },
    setNum() {
      this.dataSource.map((item, index) => {
        item.num = index
      })
    },
    handleDisabled(e, num) {
      if (e) {
        deleteAction(this.url.delete, { id: e }).then(res => {
          if (res.success) {
            this.$message.success(this.$t('操作成功'))
            this.gitList()
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
        })
      } else {
        this.dataSource.splice(num, 1)
      }
    },
    inputBlur(e) {
      putAction(this.url.edit, e).then(res => {})
    },
    handleCancel() {},
    add(res) {
      this.title2 = '编辑'
      this.visible = true
      this.model = res
      this.gitList()
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.fbFlag = false
    },
    handleOk() {
      const that = this
      // 触发表单验证
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
          if (formData.storageArea) {
            formData.storageArea = formData.storageArea.join(',')
          }
          httpAction(httpurl, formData, method)
            .then(res => {
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
            })
        }
      })
    },
    gitList() {
      // 获取货品列表
      getAction(this.url.list, { sttr1: this.model.shpBianMa }).then(res => {
        if (res.success) {
          this.dataSource = res.result
          this.setNum()
        }
      })
    },
    handleOk2() {
      this.visible = false
    }
  }
}
</script>
<style scoped>
>>> .ant-modal-content .ant-modal-body .ant-form-item {
  display: inline-block !important;
  width: 100%;
}
>>> .ant-collapse-content-active .ant-col-6 {
  display: inline-block;
  float: none !important;
}
>>> .ant-modal-content .ant-modal-body {
  max-height: 700px !important;
  overflow-y: auto;
}
>>> .ant-collapse-header {
  color: #50bb79 !important;
  font-weight: 600;
}
>>> .ant-collapse-content {
  overflow: initial;
}
</style>