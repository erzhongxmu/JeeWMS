<template>
  <j-modal :title="$t('拣货单')" width="1000px" :visible="visible" :footer="null" @cancel="conceal">
    <div class="But">
      <a-button class="printPutInBut" v-print="'#printPutIn'" type="primary">{{$t('打印')}}</a-button>
    </div>
    <div class="printBody" id="printPutIn" v-if="data">
      <h1>{{$t('拣货单')}}</h1>
      <h2 class="subtitle" v-if="lang == 'zh'">Pick List</h2>
      <div class="pist">
        <vue-qr v-if="data.omNoticeId" :text="data.omNoticeId" :size="100" :margin="0"></vue-qr>
      </div>
      <div class="print_p" style="margin: 10px 0">
        <p>{{$t('单号')}}：{{data.omNoticeId || ''}}</p>
        <p>{{$t('客户')}}：{{data.orderTypeCode == 13?data.omBeizhu:data.cusCode_dictText || ''}}</p>
        <!-- <p>ERP单号：{{data.u8ReturnCode || '暂无'}}</p> -->
        <p>{{$t('创建日期')}}：{{data.createTime || ''}}</p>
        <!-- <p>{{$t('拣货提醒')}}：{{data.jianhuoremark || '暂无'}}</p> -->
        <p v-if="data.orderTypeCode != 13" style="width:100%">{{$t('备注')}}：{{data.wmOmNoticeHPage && data.wmOmNoticeHPage.omBeizhu || ''}}</p>
      </div>
      <table border="1" id="printPutIn" style="margin: 0 auto;width:100%">
        <tbody>
          <tr height="33">
            <th>{{$t('序号')}}</th>
            <th>{{$t('商品编码')}}</th>
            <th>{{$t('库位')}}</th>
            <th style="width:180px">{{$t('托盘')}}</th>
            <th  style="width:300px">{{$t('商品')}}</th>
            <th>{{$t('单位')}}</th>
            <th>{{$t('批次')}}</th>
            <th>{{$t('数量')}}</th>
            <!-- <th>{{$t('规格')}}</th> -->
            <th>{{$t('拣货')}}</th>
            <!-- <th>{{$t('备注')}}</th> -->
            <!-- <th>二维码</th> -->
          </tr>
          <tr style="height:20px" v-for="(item,key) in data.wmOmQmIList || []" :key="key">
            <td>{{key+1}}</td>
            <td>{{item.goodsId || ''}}</td>
            <td>{{item.binId || ''}}</td>
            <td style="font-size:20px">{{item.tinId || ''}}</td>
            <td>{{item.goodsName || ''}}</td>
            <td>{{item.goodsUnit || ''}}</td>
            <td>{{item.goodsBatch || ''}}</td>
            <td>{{item.baseGoodscount || ''}}</td>
            <!-- <td>{{item.shpGuiGe || ''}}</td> -->
            <td>{{item.pickNotice || ''}}</td>
            <!-- <td>{{item.omBeiZhu || ''}}</td> -->
            <!-- <td>
              <vue-qr v-if="item.goodsId" :text="item.goodsId" :size="50" :margin="0"></vue-qr>
            </td> -->
          </tr>
        </tbody>
      </table>
      <div class="printBody_p" style="margin: 10px 0">
        <p style="font-size:12px">{{$t('打印日期')}}： {{date}}</p>
        <p></p>
        <p>{{$t('制单')}}：{{data.wmOmQmIList?data.wmOmQmIList[0].createBy:''}}</p>
        <p>{{$t('仓管')}}：</p>
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
      data: {},
      date:'',
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
      getAction('/jeewms/wmOmNoticeH/doPrintpage', { id }).then(res => {
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
  width: 850px;
  margin: 0 auto;
  color: #000;
  position: relative;
  padding-top: 20px;
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