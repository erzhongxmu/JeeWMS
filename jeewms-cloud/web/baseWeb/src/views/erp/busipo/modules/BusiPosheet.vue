<template>
  <j-modal
    :title="title"
    :width="600"
    :visible="visible"
    :maskClosable="false"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
  >
    <a-table
      ref="table"
      size="middle"
      bordered
      rowKey="id"
      class="j-table-force-nowrap"
      :columns="columns"
      :dataSource="dataSource"
      :pagination="false"
      :loading="loading"
      :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type: 'radio' }"
    >
    </a-table>
  </j-modal>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import BusiPoForm from './BusiPoForm'
import BusiPoFormEdit from './BusiPoFormEdit'

export default {
  name: 'BusiPoModal',
  components: {
    BusiPoForm,
    BusiPoFormEdit,
  },
  data() {
    return {
      isType: '',
      title: '请选择子po',
      width: 800,
      loading: false,
      visible: false,
      columns: [
        {
          title: '子PO',
          align: 'center',
          dataIndex: 'query14',
        },
      ],
      dataSource: [],
      ipagination: {
        current: 1,
        pageSize: 20,
        pageSizeOptions: ['10', '20', '30', '50', '100'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + '  ' + ' ' + this.$t('共') + ' ' + total + ' ' + this.$t('项目')
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0,
      },

      /* table选中keys*/
      selectedRowKeys: [],
      /* table选中records*/
      selectionRows: [],
      quert13:'',
    }
  },
  methods: {
    onSelectChange(selectedRowKeys, selectionRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows
    },
    handleTableChange(pagination, filters, sorter) {
      //分页、排序、筛选变化时触发
      //TODO 筛选
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field
        this.isorter.order = 'ascend' == sorter.order ? 'asc' : 'desc'
      }
      this.ipagination = pagination
      this.loadData()
    },
    loadData() {
      let params = { query13: this.query13}
      getAction('/jeeerp/busiPo/list', params).then((res) => {
          this.dataSource = res.result.records
      })
    },
    open(record) {
      this.selectedRowKeys = []
      this.selectedRows = []
      this.visible = true
      this.query13 = record.query13
      this.loadData()
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleOk() {
      this.visible = false
      let selectionRows = this.selectionRows[0]
      let params = { orderids: selectionRows.query04,type:'CGZJ',PO:selectionRows.query14}
      console.log(params,selectionRows,'-=-=-');
      // this.$refs.realForm.handleOk();
    },
    //   submitCallback(){
    //     this.$emit('ok');
    //     this.visible = false;
    //   },
    handleCancel() {
      this.close()
    },
  },
}
</script>

<style scoped>
</style>