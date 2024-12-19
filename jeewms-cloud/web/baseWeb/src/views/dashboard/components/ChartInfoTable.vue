<template>
  <div class="table">
    <a-table  class="j-table-force-nowrap" :columns="columns" :data-source="tableData" :pagination="false"></a-table>
  </div>
</template>

<script>
export default {
  name: 'ChartInfoTable',
  data(){
    return {
      tableData:[],
      columns:[],
    }
  },
  methods:{
    setData(){
      this.tableData = [
        {
          key: '1',
          type:'SMT_input',
          one:'737',
          two:'123',
          three:'2131',
          four:'987',
          five:'342',
          six:'998',
          seven:'874',
          eight:'937',
          nine:'126',
          ten:'755',
          eleven:'755',
          twelve:'755',
        },
        {
          key: '2',
          type:'SMT_input',
          one:'737',
          two:'123',
          three:'2131',
          four:'987',
          five:'342',
          six:'998',
          seven:'874',
          eight:'937',
          nine:'126',
          ten:'755',
          eleven:'755',
          twelve:'755',
        },
        {
          key: '3',
          type:'SMT_input',
          one:'737',
          two:'123',
          three:'2131',
          four:'987',
          five:'342',
          six:'998',
          seven:'874',
          eight:'937',
          nine:'126',
          ten:'755',
          eleven:'755',
          twelve:'755',
        },
        {
          key: '4',
          type:'SMT_input',
          one:'737',
          two:'123',
          three:'2131',
          four:'987',
          five:'342',
          six:'998',
          seven:'874',
          eight:'937',
          nine:'126',
          ten:'755',
          eleven:'755',
          twelve:'755',
        },
        {
          key: '5',
          type:'SMT_input',
          one:'737',
          two:'123',
          three:'2131',
          four:'987',
          five:'342',
          six:'998',
          seven:'874',
          eight:'937',
          nine:'126',
          ten:'755',
          eleven:'755',
          twelve:'755',
        },
      ];
      this.columns = [
        {
          title: '类型',
          dataIndex: 'type',
          key: 'type',
        },
        {
          title: '1月',
          dataIndex: 'one',
          key: 'one',
        },
        {
          title: '2月',
          dataIndex: 'two',
          key: 'two',
        },
        {
          title: '3月',
          dataIndex: 'three',
          key: 'three',
        },
        {
          title: '4月',
          dataIndex: 'four',
          key: 'four',
        },
        {
          title: '5月',
          dataIndex: 'five',
          key: 'five',
        },
        {
          title: '6月',
          dataIndex: 'six',
          key: 'six',
        },
        {
          title: '7月',
          dataIndex: 'seven',
          key: 'seven',
        },
        {
          title: '8月',
          dataIndex: 'eight',
          key: 'eight',
        },
        {
          title: '9月',
          dataIndex: 'nine',
          key: 'nine',
        },
        {
          title: '10月',
          dataIndex: 'ten',
          key: 'ten',
        },
        {
          title: '11月',
          dataIndex: 'eleven',
          key: 'eleven',
        },
        {
          title: '12月',
          dataIndex: 'twelve',
          key: 'twelve',
        },
      ]
    },
  },
  mounted() {
    this.setData()
  }
}
</script>

<style scoped lang="less">
  .table {
    /deep/ .ant-table-thead > tr > th {
      padding: 10px 10px;
    }
    /deep/ .ant-table-tbody > tr > td {
      padding: 10px 10px;
    }
    /deep/ .ant-table-tbody .ant-table-row td {
      padding-top: 10px;
      padding-bottom: 10px;
    }
  }
</style>