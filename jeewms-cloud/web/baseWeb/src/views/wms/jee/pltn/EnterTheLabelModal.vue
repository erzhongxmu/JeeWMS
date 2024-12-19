<template>
  <j-modal title="打印标签" :width="800" :visible="visibledy" :footer="null" @cancel="conceal">
    <a-form-model >
      <a-row>
        <a-col :xs="24" :sm="10">
          <a-form-model-item label="张数" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-model="count"></a-input>
          </a-form-model-item>
        </a-col>
        <a-col :xs="24" :sm="8">
          <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-button type="primary" @click="add">确定</a-button>
            <a-button type="primary" style="margin-left: 10px;" v-print="'#printMe'" icon="printer">{{$t('打印')}}</a-button>
          </a-form-model-item>
        </a-col>
      </a-row>
    </a-form-model>

    <div class="printBody" id="printMe">
      <div class="item" v-for="(item,index) in data" :key="index">{{item.count}}
        <!-- <div class="title">
            批次号:
            <p>{{item.query01}}</p>
            <p>{{item.query02}}</p>
          </div>
          <div class="title">
            存货编码:
            <p>{{item.query03}}</p>
          </div>
          <div class="title">
            存货名称:
            <p>{{item.query04}}</p>
          </div>
          <div class="title">
            产品规格:
            <p>{{item.query05}}</p>
          </div>
          <div class="qrcode">
            <div class="qrcode1">
              <vue-qr
                :text="item.query03"
                :margin="0"
                colorDark="#000"
                colorLight="#fff"
                :size="80"
              ></vue-qr>
              <p>{{item.query03}}</p>
            </div>
            <div class="qrcode1">
              <vue-qr :text="item.query06" :margin="0" colorDark="#000" colorLight="#fff" :size="80"></vue-qr>
              <p>{{item.query06}}</p>
            </div>
        </div>-->
      </div>
    </div>
  </j-modal>
</template>

<script>
import { getAction } from '@/api/manage'
import '@/assets/less/TableExpand.less'
import vueQr from 'vue-qr'
// 检验单，包装领料单
export default {
  name: 'EnterTheLabelModal',
  components: {
    vueQr
  },
  data() {
    return {
      visibledy: false,
      data: [],
      count:1,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      labelCol2: {
        xs: { span: 24 },
        sm: { span: 3 }
      }
    }
  },
  methods: {
      add(){
        let obj = this.data[0]
        let arr = []
        for(let i = 0;i<this.count;i++){
            let obj_t = {
                ...obj,
                count: (i+ 1)+'/'+ this.count
            }
            arr.push(obj_t)
        };
        this.data = arr
      },
    onShow(e) {
      this.visibledy = true
      this.data.push({...e,count:'1/1' })
    },
    conceal() {
      this.visibledy = false
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';

</style>