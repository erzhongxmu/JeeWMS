<template>
  <j-modal title="入库单打印" width="1000px" :visible="visible" :footer="null" @cancel="conceal">
    <div class="But">
      <a-button class="printPutInBut" v-print="'#printPutIn'" type="primary">{{$t('打印')}}</a-button>
    </div>
    <div class="printBody" id="printPutIn">
      <h1>{{$t('仓储管理系统入库通知单')}}</h1>
      <h2 class="subtitle" v-if="lang == 'zh'">Order Notice</h2>
      <div class="pist">
        <vue-qr v-if="data.noticeId" :text="data.noticeId" :size="100" :margin="0"></vue-qr>
      </div>
      <div class="print_p" style="margin: 10px 0">
        <p>{{$t('入库单号')}}：{{data.noticeId || '暂无'}}</p>
        <p>{{$t('创建日期')}}：{{data.createTime || '暂无'}}</p>
        <!-- <p>客户：{{data.cusCode_dictText || '暂无'}}</p> -->
        <p>{{$t('客户')}}：{{data.cusName || '暂无'}}</p>
        <p>{{$t('供应商')}}：{{data.supCode_dictText || '暂无'}}</p>
      <!-- <p>类型：{{data.orderTypes || '暂无'}}</p> -->
      </div>
      <table id="printPutIn" style="margin: 0 auto;width:100%">
        <tbody>
          <tr height="33">
            <td>{{$t('序号')}}</td>
            <td>{{$t('PO号')}}</td>
            <td>{{$t('商品编码')}}</td>
            <td>{{$t('商品名称')}}</td>
            <!-- <td>{{$t('商品规格')}}</td> -->
            <td>{{$t('单位')}}</td>
            <td>{{$t('数量')}}</td>
            <!-- <td>{{$t('批次')}}</td> -->
            <td>{{$t('备注')}}</td>
            <td>{{$t('库位')}}</td>
            <td>{{$t('二维码')}}</td>
          </tr>
          <tr style="height:60px" v-for="(item,key) in data.printDataList || []" :key="key">
            <td>{{key+1}}</td>
            <td>{{item.contractlno || ''}}</td>
            <td>{{item.goodsCode || ''}}</td>
            <td>{{item.goodsName || ''}}</td>
            <!-- <td>{{item.shpGuiGe || ''}}</td> -->
            <td>{{item.goodsUnit || ''}}</td>
            <td>{{item.goodsCount || ''}}</td>
            <!-- <td>{{item.goodsBatch || ''}}</td> -->
            <td>{{item.imBeizhu || ''}}</td>
            <td>{{item.binPlan || ''}}</td>
            <td>
                <vue-qr v-if="item.goodsCode" :text="item.goodsCode" :size="50" :margin="0"></vue-qr>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="printBody_p" style="margin: 10px 0">
        <p style="font-size:12px">{{$t('打印日期')}}： {{date}}</p>
        <p>{{$t('数量')}}：{{data.totalNum || 0}}</p>
        <p>{{$t('创建人')}}：{{data.createBy  || 0}}</p>
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
      this.data = e
      this.date = getCurrentTime()
      this.getData(e.noticeId)
    },
    conceal() {
      this.visible = false
      this.data = {}
    },
    getData(id) {
      let totalWeight = 0,
        totalNum = 0
      getAction('/jeewms/wmImNoticeH/queryWmImNoticeIByMainId', { id }).then(res => {
        res.result.map(item => {
          if (item.goodsWeight) {
            totalWeight += parseInt(item.goodsWeight)
          }
          if (item.goodsCount) {
            totalNum += parseInt(item.goodsCount)
          }
        })
        this.data = {
          ...this.data,
          printDataList: res.result,
          totalWeight,
          totalNum
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
  width: 700px;
  margin: 0 auto;
  color: #000;
  position: relative;
  padding-top: 50px;
  h1,
  h2,
  h3 {
    text-align: center;
  }
  .subtitle {
    position: relative;
    top: -10px;
  }
}
tr {
  height: 25.05pt;
  td {
    border: 1pt solid black;
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
    width: 33%;
    margin: 3px 0;
  }
}
.pist {
  position: absolute;
  top: 0px;
  right: 0;
}
</style>