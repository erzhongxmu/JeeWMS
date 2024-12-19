<template>
  <div class="part-container">
    <div v-if="activePart === 1">
      <ProductionDepartment />
    </div>
    <div v-if="activePart === 2">
      <TechnicalDepartment />
    </div>
    <div v-if="activePart === 3">
      <QualityDepartment />
    </div>
    <div v-if="activePart === 4">
      <MEEngineeringDepartment />
    </div>
    <div v-if="activePart === 5">
      <EEEngineeringDepartment />
    </div>
  </div>
</template>

<script>
import ProductionDepartment from '@views/dashboard/components/part/ProductionDepartment' // 生产部组件
import TechnicalDepartment from '@views/dashboard/components/part/TechnicalDepartment' // 技术部组件
import QualityDepartment from '@views/dashboard/components/part/QualityDepartment' // 品质部组件
import MEEngineeringDepartment from '@views/dashboard/components/part/MEEngineeringDepartment' // ME工程部组件
import EEEngineeringDepartment from '@views/dashboard/components/part/EEEngineeringDepartment' // EE工程部组件

export default {
  name: 'PartChart',
  props:{
    activePart:{
      type:Number,
      default:0
    }
  },
  components:{
    ProductionDepartment,
    TechnicalDepartment,
    QualityDepartment,
    MEEngineeringDepartment,
    EEEngineeringDepartment,
  }
}
</script>

<style scoped lang="less">
  .part-container {
    width: 100%;
    height: 100%;
  }
</style>