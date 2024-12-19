<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="close"
    >
    <a-spin :spinning="confirmLoading">
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
            @change="handleTableChange"
          >
          </a-table>
    </a-spin>
  </j-modal>
</template>

<script>

  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import { getAction } from '@api/manage'

  export default {
    name: "BaActTypeModal",
    components: { 
      JDictSelectTag,
    },
    data () {
      return {
        dataSource:[],
        columns:[
          {
            title: this.$t('装箱单号'),
            sorter: true,
            align: 'center',
            dataIndex: 'name'
          },
          {
            title: this.$t('箱数'),
            sorter: true,
            align: 'center',
            dataIndex: 'y'
          },
          {
            title: this.$t('日期'),
            sorter: true,
            align: 'center',
            dataIndex: 'date'
          },
        ],
        ipagination:{
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30', '50', '100'],
          showTotal: (total, range) => {
            return range[0] + '-' + range[1] + '  ' + ' ' + this.$t('共') + ' ' + total + ' ' + this.$t('项目')
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        title:this.$t('近7日出货箱数'),
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/jeewms/baActType/add",
          edit: "/jeewms/baActType/edit",
        }
      }
    },
    created () {
    },
    methods: {
    handleTableChange(pagination, filters, sorter) {
      //分页、排序、筛选变化时触发
      //TODO 筛选
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field
        this.isorter.order = 'ascend' == sorter.order ? 'asc' : 'desc'
      }
      this.ipagination = pagination
    },
      getList() {
        this.confirmLoading = true
        getAction('/jeewms/bic/dayCount').then(res => {
        this.confirmLoading = false
          if (res.success) {
            this.dataSource = res.result[0].data
          } else {
            this.$message.error(this.title + ':' + res.message)
          }
        })
      },
      onShow () {
        this.visible = true
        this.getList()
      },
      close () {
        this.dataSource = []
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
       this.close()
      },
    }
  }
</script>