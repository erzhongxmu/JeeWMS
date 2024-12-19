<template>
  <div>
    <index-chart v-if="indexStyle==1"></index-chart>
    <index-bdc v-if="indexStyle==2"></index-bdc>
    <index-task v-if="indexStyle==3"></index-task>
    <!-- wms -->
    <Home v-if="indexStyle==4"/>
    <!-- oms -->
    <omsHome v-if="indexStyle==5"/>
  </div>
</template>

<script>
  import IndexChart from './IndexChart'
  import IndexTask from "./IndexTask"
  import IndexBdc from './IndexBdc'
  import Home from './Home'
  import omsHome from './omsHome'
  import { getAction } from '@api/manage'

  export default {
    name: "Analysis",
    components: {
      IndexChart,
      IndexTask,
      IndexBdc,
      Home,
      omsHome
    },
    data() {
      return {
        indexStyle:4
      }
    },
    created() {

    },
  mounted() {
    if (this.$store.state.app.systemPattern == 'OMS') {
      this.indexStyle = 5
    }
  },
    methods: {

    }
  }
</script>