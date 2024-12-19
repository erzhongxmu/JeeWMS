<template>
  <div>
    <div>
      <a-range-picker @change="onChange" style="margin-bottom: 10px"/>
      <a-tabs type="card" v-model="activeKey" @change="activeKeyChange">
        <a-tab-pane key="1" tab="今日">
          <a-table  class="j-table-force-nowrap"  :pagination='false' :columns="columns" :data-source="StatisticalData">
            <a slot="name" slot-scope="text">{{ text }}</a>
          </a-table>
        </a-tab-pane>
        <a-tab-pane key="2" tab="本周">
          <a-table  class="j-table-force-nowrap"  :pagination='false' :columns="columns" :data-source="StatisticalData">
            <a slot="name" slot-scope="text">{{ text }}</a>
          </a-table>
        </a-tab-pane>
        <a-tab-pane key="3" tab="本月">
          <a-table  class="j-table-force-nowrap"  :pagination='false' :columns="columns" :data-source="StatisticalData">
            <a slot="name" slot-scope="text">{{ text }}</a>
          </a-table>
        </a-tab-pane>
        <a-tab-pane key="4" tab="本年">
          <a-table  class="j-table-force-nowrap"  :pagination='false' :columns="columns" :data-source="StatisticalData">
            <a slot="name" slot-scope="text">{{ text }}</a>
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </div>

  </div>
</template>

<script>
const columns = [
  {
    title: '生产排行榜',
    dataIndex: 'name',
    key: 'name',
    scopedSlots: { customRender: 'name' },
  },
  {
    title: 'SMT',
    dataIndex: 'SMT',
    key: 'SMT',
    width: 80,
  },
  {
    title: 'DIP',
    dataIndex: 'DIP',
    key: 'DIP',
    ellipsis: true,
  },
  {
    title: 'Assembly',
    dataIndex: 'Assembly',
    key: 'Assembly',
    ellipsis: true,
  },
];

export default {
  props:{
    StatisticalData:{
      type:Array,
      default:[]
    }
  },
  name: 'StatisticalTable',
  data() {
    return {
      columns,
      activeKey:'1',

    };
  },
  methods:{
    onChange(){
      this.activeKey = '0'
    },
    activeKeyChange(){
      console.log(this.activeKey)
    }
  }
};
</script>

<style scoped>

</style>