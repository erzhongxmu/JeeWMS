<template>
  <j-modal :title="$t('打印')" width="500px" :visible="visible" :footer="null" @cancel="conceal">
    <div class="But">
      <span>份额：</span>
      <a-input v-model="bqNum" class="input" />
      <a-button class="printPutInBut" v-print="'#printPutIn'" type="primary">{{ $t('打印') }}</a-button>
    </div>
    <!-- 箱唛   -->
    <!--  -->
    <div v-show="false">
      <div class="printBody" id="printPutIn" v-if="type == 'xiangmai'">
        <div class="printItem1" v-for="(item, index) in printData" :key="index" style="page-break-after: always">
          <div class="printItem1">
            <p class="printItem1_p">{{ item.cusCode_dictText || '' }}</p>
            <p>SKU：{{ item.goodsCode || '' }}</p>
            <p class="printItem1_p">{{ item.goodsName || '' }}</p>
            <p>QTY:{{ item.eachBox || '' }} {{ item.unitEnName || '' }}</p>
            <p>{{ item.contractlno || '' }}</p>
            <p>WMS HOSPITALITY</p>
          </div>
        </div>
      </div>
      <!-- 入库 -->
      <div class="printBody" id="printPutIn" v-else-if="type == 'rk'">
        <div class="printItem2" v-for="(item, index) in printData" :key="index" style="page-break-after: always">
          <div class="printItem2">
            <div class="top">
              <p>- SKU:{{ item.goodsCode || '' }}</p>
              <p>- {{ item.cusCode_dictText || '' }}</p>
              <!-- :style="{fontSize:setSize(item.goodsName,'fontSize'),lineHeight: setSize(item.goodsName,'lineHeight')}" -->
              <p>- {{ item.goodsName || '' }}</p>
            </div>

            <div class="foot">
              <p>- QTY:{{ item.eachBox || '' }} {{ item.unitEnName || '' }}</p>
              <p>- {{ item.contractlno || '' }}</p>
              <p>- {{ item.createTime || '' }}</p>
            </div>
            <!-- <p>- QTY:{{ item.eachBox || '' }} {{ item.unitEnName || '' }}</p>
            <p>- {{ item.contractlno || '' }}</p>
            <p>- {{ item.createTime || '' }}</p> -->
            <img :id="`barcode${index}`" />
          </div>
        </div>
      </div>
      <!-- 出库 -->
      <div class="printBody" id="printPutIn" v-else>
        <div class="printItem3" v-for="(item, index) in printData" :key="index" style="page-break-after: always">
          <div class="printItem3">
            <p>{{ item.data01 || '' }}</p>
            <p>{{ item.data02 || '' }}</p>
            <p>{{ item.goodsCode || '' }}-{{ item.data04 || '' }}</p>
            <p><img :id="`barcode${index}`" />{{ item.data03 || '' }}</p>
          </div>
        </div>
      </div>
    </div>
  </j-modal>
</template>

<script>
import { getAction, postAction } from '@/api/manage'
import { getCurrentTime } from '@/utils/util'
import '@/assets/less/TableExpand.less'
import vueQr from 'vue-qr'
// 检验单，包装领料单
export default {
  name: 'ShippingMark',
  components: {
    vueQr,
  },
  data() {
    return {
      visible: false,
      originData: [], // 原始打印数据
      printData: [], // 打印要用到的数据
      type: 'xiangmai',
      bqNum: 2, // 打印的份数
    }
  },
  watch: {
    bqNum(n, t) { // 监测打印份数的变化
      if (n) {
        this.countArr(n)
      }
    }
  },
  computed: {
    setSize() {
      return (text, type) => {
        let strLen = text.length
        return '44px'
      }
    },
  },
  methods: {
    countArr(n) { // 处理打印份数函数
      let arr1 = JSON.parse(JSON.stringify(this.originData)) // 将原始数据复制一份
      let list = [] 
      arr1.map((item, index) => {
        for (let i = 0; i < n; i++) {
          list.push(item)
        }
      })
      this.printData = list // 将根据打印份数处理好的数据，重新赋值给 printData
      if (this.type == 'rk') {
        this.Label()
      }
      if (this.type == 'ck') {
        this.Label2()
      }
    },
    onShow2(e){
      this.visible = true
      this.type = e.param.type
      this.printData = e.list
      this.originData = e.list
      this.countArr(this.bqNum)
    },
    onShow(e) { // 弹窗显示，获取数据
      this.visible = true
      this.type = e.param.type
      console.log(e,'00000000000000');
      this.getdata(e)
    },
    conceal() {
      this.visible = false
      this.originData = []
      this.bqNum = 2
    },
    getdata(data) { // 专门处理不同类型打印数据的函数
      let url = '/jeewms/wmImNoticeH/labelPrints3'
      if (this.type == 'ck') {
        url = '/jeewms/wmOmQmI/labelPrinting3'
      }
      let formData = new FormData()
      for(let key in data.param){
        formData.append(key,data.param[key])
      }
      postAction(url, formData).then((res) => {
        switch (this.type) { // 形参一 data 为主表的基础数据， 形参二为请求回来的数据
          case 'xiangmai':
            this.Mark(data, res.result)
            break
          case 'rk':
            this.Mark(data, res.result)
            this.Label()
            break
          default:
            this.outLabel(data, res.result)
            this.Label2()
        }
      })
    },
    // 箱唛/入库
    Mark(data, arr) { // 整理data的数据为符合箱唛打印的数据
      let arr1 = []
      data.list.map((item, index) => {
        arr.map((v, i) => {
          if (v.id == item.id) {
            arr1.push({
              ...item,
              contractlno: v.contractlno,
              unitEnName: v.unitenname,
              createTime: v.createTime || '',
            })
          }
        })
      })
      this.originData = arr1 // 保留一份原始数据，避免后面改变打印份数的时候出现重复数据的问题
      this.printData = arr1 // printData 作为处理打印份数的数据
      this.countArr(this.bqNum) // 处理打印份数
    },
    Label() {
      this.printData.map((item, index) => {
        this.$nextTick(() => {
          JsBarcode(`#barcode${index}`, item.xianghao, {
            width: '3px',
            height: 60,
            displayValue: true,
            fontSize: 28,
            padding: 0,
            fontOptions: 'bold',
            margin: 0,
          })
        })
      })
    },
    Label2(){
      this.printData.map((item, index) => {
        this.$nextTick(() => {
          JsBarcode(`#barcode${index}`, item.goodsCode, {
            width: '1px',
            height: 30,
            displayValue: false,
            fontSize: 16,
            padding: 0,
            margin: 0,
          })
        })
      })
    },
    // 出库
    outLabel(data, arr) {
      let arr4 = []
      data.list.map((item, index) => {
        arr.map((v, i) => {
          if (v.id == item.id) {
            arr4.push({
              ...item,
              data01: v.data01,
              data02: v.data02,
              data03: v.data03,
              data04: v.data04,
            })
          }
        })
      })
      this.originData = this.dateData(arr4)
      this.printData = this.dateData(arr4)
      this.countArr(this.bqNum)
    },
    dateData(arr) {
      arr.sort(function (s, t) {
        var a = 'A00001'
        var b = 'A00001'
        if (s['data02']) {
          a = s['data02'].toLowerCase()
        }
        if (t['data02']) {
          b = t['data02'].toLowerCase()
        }
        if (a.length === 2) {
          a = a.slice(0, a.length - 1) + '0' + a.slice(-1)
        }
        if (b.length === 2) {
          b = b.slice(0, b.length - 1) + '0' + b.slice(-1)
        }
        if (a < b) return -1
        if (a > b) return 1
        return 0
      })
      return arr
    },
  },
}
</script>
<style scoped lang="less">
@import '~@assets/less/common.less';
.But {
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: center;
  .input {
    width: 60%;
  }
}
.printBody {
  margin: 0 auto;
  color: #000;
  position: relative;
  .printItem1 {
    width: 1100px;
    font-size: 70px;
    height: 750px;
    margin-bottom: 5px;
    padding: 10px;
    font-weight: 800;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    font-family: 'Microsoft YaHei';
    p {
      width: 100%;
      line-height: 95px;
      margin: 0;
    }
    .printItem1_p {
      line-height: 75px;
    }
  }
  .printItem2 {
    height: 670px;
    width: 450px;
    font-size: 40px; // 44px
    overflow: hidden;
    padding-left: 5px;
    font-weight: 550; // 700
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    padding-left: 15px;
    font-family: 'Microsoft YaHei';
    padding-bottom: 5px;

    img {
      width: 93%;
      margin: 0 auto;
      margin-right: 30px;
    }

    .top {
      // background-color: yellow;
      height: 387px; // 387
      width: 100%;
      p {
        width: 100%;
        // line-height: 44px;
        margin: 0;
        &:first-child {
          height: 55px;
          // background-color: pink;
        }
        &:nth-child(2) {
          height: 175px; // 155
          padding-top: 20px;
          // background-color: skyblue;
        }
        &:nth-child(3) {
          height: 142px; // 122
          // background-color: tomato;
        }
      }
    }

    .foot {
      // background-color: pink;
      width: 100%;
      height: 144px;
      margin-bottom: 10px;
      p {
        width: 100%;
        // line-height: 44px;
        margin: 0;
        &:first-child {
          height: 48px;
          // background-color: yellow;
        }
        &:nth-child(2) {
          height: 48px;
          // background-color: purple;
        }
        &:nth-child(3) {
          height: 48px;
          // background-color: orange;
        }
      }
    }
  }
  .printItem3 {
    width: 450px;
    font-size: 45px;
    height: 320px;
    // margin-bottom: 5px;
    padding:0 10px;
    overflow: hidden;
    font-weight: 700;
    font-family: 'Microsoft YaHei';
    text-align: center;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    p {
      width: 100%;
      line-height: 65px;
      margin: 0;
      &:first-child {
        font-size: 95px;
        line-height: 110px;
      }
      &:nth-child(2) {
        font-size: 75px;
      }
      &:nth-child(3) {
        font-size: 32px;
        font-weight: 600;
      }
      &:nth-child(4) {
        font-size: 37px;
        font-weight: 600;
        line-height: 40px;
        // position: relative;
        display: flex;
        align-items: center;
        justify-content: space-around;
      }
      img{
        width: 60%;
        height: 40px;
        // width: 30%;
        // height: 40px;
        // position: absolute;
        // left:30px;
        // bottom: 0px;
      }
    }
  }
}
</style>