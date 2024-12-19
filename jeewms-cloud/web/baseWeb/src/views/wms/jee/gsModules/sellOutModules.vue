<template>
  <j-modal :title="$t('出库单')" width="1000px" :visible="visible" :footer="null" @cancel="conceal">
    <div class="But">
      <a-button class="printPutInBut" v-print="'#printPutIn2'" type="primary">{{$t('打印')}}</a-button>
    </div>
    <div class="printBody" id="printPutIn2">
      <h1>{{$t('出库单')}}</h1>
      <h2 class="subtitle" v-if="lang == 'zh'">Stock-out List</h2>
      <div class="print_p" style="margin: 10px 0">
        <p>{{$t('公司地址')}}：{{data.area || ''}}</p>
        <p>{{$t('客户')}}：{{data.cusCode_dictText || ''}}</p>
        <p>{{$t('出库日期')}}：{{data.printingTime || ''}}</p>
        <p>{{$t('出库单号')}}：{{data.omNoticeId || ''}}</p>
        <p>{{$t('收货人')}}：{{data.ocusName || ''}}</p>
        <p>{{$t('联系方式')}}：{{data.delvMobile || ''}}</p>
        <p>{{$t('备注')}}：{{data.omBeizhu || ''}}</p>
      </div>
      <table border="1" id="printPutIn" style="margin: 0 auto;width:100%">
        <tr height="33">
          <th>{{$t('序号')}}</th>
          <th>{{$t('商品编码')}}</th>
          <th>{{$t('商品名称')}}</th>
          <th>{{$t('单位')}}</th>
          <th>{{$t('数量')}}</th>
          <th>{{$t('备注')}}</th>
        </tr>
        <tr style="height:60px" v-for="(item,key) in data.listitem || []" :key="key">
          <td>{{key+1}}</td>
          <td>{{item.goodsId || ''}}</td>
          <td>{{item.shpMingCheng || ''}}</td>
          <td>{{item.baseUnit || ''}}</td>
          <td>{{item.number || ''}}</td>
          <td>{{item.itemText || ''}}</td>
        </tr>
      </table>
      <div class="printBody_p" style="margin: 10px 0">
        <p style="font-size:12px">{{$t('打印日期')}}： {{date}}</p>
        <p></p>
        <p>{{$t('合计数量')}}:{{data.totalQuantity}}</p>
        <p>{{$t('签字（盖章）')}}</p>
      </div>
    </div>
  </j-modal>
</template>

<script>
import { getAction } from '@/api/manage'
import { getCurrentTime } from '@/utils/util'
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
      visible: false,
      date:'',
      data: {},
      lang: localStorage.getItem('Language') || ''
    }
  },
  methods: {
    onShow(e) {
      this.visible = true
      this.date = getCurrentTime()
      this.data = e
      this.getData(e.id)
    },
    conceal() {
      this.visible = false
      this.data = {}
    },
    getData(id) {
      let totalWeight = 0,
        totalNum = 0
      getAction('/jeewms/wmOmNoticeH/doPrintpageckd', { id }).then(res => {
        this.data = {
          ...this.data,
          ...res.result
        }
      })
    }
  }
}
</script>
<style scoped lang="less">
@import '~@assets/less/common.less';
.But {
  height: 70px;
  .printPutInBut {
    float: right;
  }
}
.printBody {
  width: 800px;
  margin: 0 auto;
  color: #000;
  position: relative;
  // padding-top: 20px;
  h1,
  h2 {
    text-align: center;
  }

  .subtitle {
    position: relative;
    top: -10px;
  }
  tr {
    height: 25.05pt;
    th {
      text-align: center;
    }
    td {
      max-width: 100px;
      text-align: center;
    }
  }
  .print_p {
    width: 100%;
    height: auto;
    p {
      display: inline-block;
      width: 50%;
      margin: 3px 0;
    }
  }
  .printBody_p {
    width: 100%;
    height: auto;
    p {
      display: inline-block;
      width: 25%;
      margin: 3px 0;
    }
  }
  .pist {
    position: absolute;
    top: 0px;
    right: 0;
  }
}
</style>