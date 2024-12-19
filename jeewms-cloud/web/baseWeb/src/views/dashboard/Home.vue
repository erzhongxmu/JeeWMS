<template>
  <div class="page-content">
    <div class="page-header">
      {{$t('home.title')}}：<span style="font-weight: 600;">{{ yearMonthDay + ' ' + hourMinutesSeconds }}</span>
      <br />
      {{$t('home.hint1')}}<span style="color:red;font-weight: 600;">{{ homeObj.num5 }}</span>{{$t('home.hint2')}}
      <span class="page-header-but" @click="JQRZXS">{{$t('近7日出货箱数')}}</span>
    </div>
    <div class="page-total">
      <a-row :gutter="20">
        <a-col :span="6" style="border-right: 2px solid #f1f4f5">
          <div>
            <div class="circle" style="background:#F2A654">
              <a-icon type="bank" />
            </div>{{$t('home.waitForReceiving')}}
          </div>
          <div class="num_t">
            <div class="num">
              <p class="triangle" style="border-bottom: 5px solid #F96868;"></p>
              {{ homeObj.num1 }}
            </div>
            <div>{{$t('home.waitForReceiving')}}</div>
          </div>
        </a-col>
        <a-col :span="6" style="border-right: 2px solid #f1f4f5">
          <div>
            <div class="circle" style="background:#F96868">
              <a-icon type="rise" />
            </div>{{$t('home.ToStayOn')}}
          </div>
          <div class="num_t">
            <div class="num">
              <p class="triangle"  style="border-bottom: 5px solid #57BE8A;"></p>
              {{ homeObj.num2 }}
            </div>
            <div>{{$t('home.NumberOfPiecesToBePutOnShelves')}}</div>
          </div>
        </a-col>
        <a-col :span="6" style="border-right: 2px solid #f1f4f5">
          <div>
            <div class="circle" style="background:#46BE8A">
              <a-icon type="layout" />
            </div>{{$t('home.ForPicking')}}
          </div>
          <div class="num_t">
            <div class="num">
              <p class="triangle"  style="border-bottom: 5px solid #F96868;"></p>
              {{ homeObj.num3 }}
            </div>
            <div>{{$t('home.NumberOfPiecesToBePicked')}}</div>
          </div>
        </a-col>
        <a-col :span="6">
          <div>
            <div class="circle" style="background:#72B0EA">
              <a-icon type="wallet" />
            </div>{{$t('home.PickingThe')}}
          </div>
          <div class="num_t">
            <div class="num">
              <p class="triangle"  style="border-bottom: 5px solid #57BE8A;"></p>
              {{ homeObj.num4 }}
            </div>
            <div>{{$t('home.NumberOfPiecesPicked')}}</div>
          </div>
        </a-col>
      </a-row>
    </div>
    <div class="chart-box">
      <div class="card">
        <div class="title">{{$t('home.Top6InNumberOfShelves')}}</div>
        <Chart1 />
      </div>
      <div class="card2">
        <!-- <div class="title">{{$t('home.NumberOfShelvesTakenDown')}}</div> -->
        <!-- <Chart2 /> -->
      <!-- </div> -->
      <!-- <div class="card"> -->
        <div class="title">{{$t('home.numberOfTakedowns')}}</div>
        <Chart3 />
      </div>
    </div>
    <JQRZXSModal ref="JQRZXSModal"  />
  </div>
</template>

<script>
import { dateFormat, getWeekDate } from '@/utils/publicTools'

import Chart1 from '@/views/dashboard/components/homeChart/Chart1'
import Chart2 from '@/views/dashboard/components/homeChart/Chart2'
import Chart3 from '@/views/dashboard/components/homeChart/Chart3'
import JQRZXSModal from '@/views/dashboard/components/JQRZXSModal'
import { getAction } from '@api/manage'
export default {
  name: 'Home',
  components: {
    Chart1,
    Chart2,
    Chart3,
    JQRZXSModal
  },
  data() {
    return {
      timer: null,
      hourMinutesSeconds: '',
      week: '',
      yearMonthDay: '',
      homeObj: {}
    }
  },
  mounted() {
    this.getInfo()
    this.timer = setInterval(() => {
      this.nowtime()
    }, 1000)
  },
  methods: {
    JQRZXS(){
      this.$refs.JQRZXSModal.onShow()
    },
    nowtime() {
      let time = new Date()
      this.hourMinutesSeconds = dateFormat(time, 'HH:mm:ss')
      this.week = getWeekDate(time)
      this.yearMonthDay = dateFormat(time, 'YYYY/MM/DD')
    },
    getInfo() {
      getAction('/jeewms/bic/homebi').then(res => {
        if (res.success) {
          this.homeObj = res.result
        }
      })
    }
  }
}
</script>

<style scoped lang="less">
.page-content {
  width: 100%;
  height: 100%;
  .circle {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: #00ff00;
    text-align: center;
    display: inline-block;
    margin-right: 20px;
    margin-left: 10px;
    i {
      color: #fff;
    }
    line-height: 40px;
  }
  .triangle {
    width: 0;
    height: 0;
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;
    border-bottom: 5px solid red;
    margin: 0;
    display: inline-block;
    margin-bottom: 14px;
    margin-right: 7px;
  }
  .num_t {
    width: 100%;
    height: 100px;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: flex-end;
  }
  .page-header {
    padding: 40px 20px;
    font-size: 18px;
    color: #37474f;
    background-color: #fff;
    margin-bottom: 20px;

  }
  .page-total {
    width: 100%;
    padding: 20px 20px;
    background-color: #fff;
    margin-bottom: 20px;

    .num {
      color: #76838f;
      font-size: 30px;
    }
  }
  .chart-box {
    width: 100%;
    display: flex;
    align-items: center;
    background: #fff;
    .card {
      width: 33%;
      height: 396px;
      background: #ffffff;
      padding: 15px 25px;
      display: flex;
      flex-direction: column;
      &:nth-of-type(2) {
        border-right: 1px #f1f4f5 solid;
        border-left: 1px #f1f4f5 solid;
      }
      .title {
        font-size: 18px;
        font-weight: bold;
        color: #333333;
        display: flex;
        align-items: center;
        justify-content: space-between;
      }

      .chart-box {
        flex: 1;
      }
    }
    .card2{
      width: 66%;
      height: 396px;
      background: #ffffff;
      padding: 15px 25px;
      display: flex;
      flex-direction: column;
      &:nth-of-type(2) {
        border-right: 1px #f1f4f5 solid;
        border-left: 1px #f1f4f5 solid;
      }
      .title {
        font-size: 18px;
        font-weight: bold;
        color: #333333;
        display: flex;
        align-items: center;
        justify-content: space-between;
      }

      .chart-box {
        flex: 1;
      }
    }
  }
  .page-header-but{
    float: right; 
    color: rgb(70, 190, 138);
    cursor: pointer;
  }
}
</style>