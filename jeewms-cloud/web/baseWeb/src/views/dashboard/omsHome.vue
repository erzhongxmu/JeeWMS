<template>
  <div class="OmsHome">
    <!-- <img src="@assets/background.png" alt=""> -->
    <!-- <div class="OmsHome_Itme">
      <a-row :gutter="16">
        <a-col :span="6">
          <a-card :bordered="false" :hoverable="true">
            <div class="user">
              <div>
                <h2>{{$t('welcome')}} , {{nickname()}}</h2>
                <p>{{$t('当前日期')}}：{{curDate}}</p>
              </div>
              <img :src="getAvatar(avatar())" alt />
            </div>
          </a-card>
        </a-col>
        <a-col :span="18">
          <a-card :bordered="false" :hoverable="true">
            <div class="orderData" v-if="dataObj[1]">
              <div>
                <h2>{{dataObj[1].omsTitile}}</h2>
              </div>
              <div class="orderData_list">
                <div
                  class="orderData_list_item"
                  v-for="(item) in dataObj[1]?dataObj[1].omsFrontPageDetailList:[]"
                  :key="item.id"
                >
                  <img :src="getAvatar(item.omsPic)" alt />
                  <div class="orderData_list_item_text">
                    <span>{{item.omsTitile}}</span>
                    <p>{{item.query01}}</p>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-col>
      </a-row>
    </div>
    <div class="OmsHome_Itme">
      <a-row :gutter="16">
        <a-col :span="6">
          <a-card :bordered="false" :hoverable="true">
            <div class="platformData">
              <div class="title">
                <h2>{{dataObj[2] && dataObj[2].omsTitile}}</h2>
                <p>{{dataObj[2] && dataObj[2].query01}}</p>
              </div>
              <div class="platformData_list">
                <div
                  class="platformData_list_item"
                  v-for="item in dataObj[2]?dataObj[2].omsFrontPageDetailList:[]"
                  :key="item.id"
                >
                  <img :src="getAvatar(item.omsPic)" alt />
                  <span>{{item.omsTitile}}</span>
                  <div class="barType">
                    <span>{{item.query01}}</span>
                    <div>
                      <p :style="{width: (item.query01/dataObj[2].query01*100) + '%'}"></p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="15">
          <a-card :bordered="false" :hoverable="true">
            <div class="chart">
              <div>
                <h2>{{dataObj[3] && dataObj[3].omsTitile}}</h2>
              </div>
              <div class="chart-box" id="machineChart"></div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="3">
          <div
            class="OmsHome_Itme"
            v-for="(item) in dataObj[4]?dataObj[4].omsFrontPageDetailList:[]"
            :key="item.id"
          >
            <a-row>
              <a-col :span="24">
                <a-card :bordered="false" :hoverable="true">
                  <div class="volumeOfBusiness">
                    <div>
                      <img :src="getAvatar(item.omsPic)" alt />
                      <p>{{item.omsTitile}}</p>
                    </div>
                    <span>{{item.query01}}</span>
                  </div>
                </a-card>
              </a-col>
            </a-row>
          </div>
        </a-col>
      </a-row>
    </div>

    <div class="OmsHome_Itme">
      <a-row :gutter="16">
        <a-col :span="6">
          <a-card :bordered="false" :hoverable="true">
            <div class="area">
              <div class="firstTitle">
                <h2>{{dataObj[5] && dataObj[5].omsTitile}}</h2>
              </div>
              <div class="area_list">
                <div class="area_list_item" v-for="(item,index) in dataObj[5]?dataObj[5].omsFrontPageDetailList:[]" :key="index">
                  <div class="title">{{item.query02}}</div>
                  <div class="barType">
                  </div>
                  <div class="quantity">
                    <span>{{item.query01}}</span>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="18">
          <a-card :bordered="false" :hoverable="true">
            <div class="area">
              <div>
                <h2>{{dataObj[6] && dataObj[6].omsTitile}}</h2>
              </div>
              <div class="chart-box" id="ColumnarEchart"></div>
            </div>
          </a-card>
        </a-col>
      </a-row>
    </div> -->
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex'
import { getFileAccessHttpUrl, getAction } from '@/api/manage'
// import { parseTime } from '@/utils/index'
import moment from 'dayjs'
export default {
  name: 'OmsHome',
  components: {},
  data() {
    return {
      myLineChart: null,
      myColumnarChart: null,
      dataObj: {},
      userInfo: {},
      // curDate: parseTime(new Date().getTime()) // 当前日期 yyyy-MM-dd 格式
    }
  },
  mounted() {
    // this.initLineEchart()
    // this.initColumnarEchart()
    // this.userInfo = this.$store.getters.userInfo
    // this.getData()
  },
  methods: {
    ...mapGetters(["nickname", "avatar","userInfo"]),
    // ...mapGetters(['avatar']),
    getAvatar(e) {
      return getFileAccessHttpUrl(e)
    },
    getData() {
      let obj = {}
      getAction('/jeeoms/omsFrontPage/queryOmsConfigNum').then(res => {
        res.result.map((item, index) => {
          obj[item.omsType] = item
        })
        this.dataObj = obj
        this.dataObj3()
        this.dataObj6()
      })
    },
    dataObj3() {
      let arr1 = []
      let arr2 = []
      let List = this.dataObj[3].omsFrontPageDetailList
      List.map(item => {
        arr1.push(item.query01)
        arr2.push(item.query02)
      })
      this.dataObj[3].arr1 = arr1
      this.dataObj[3].arr2 = arr2
      this.setLineOption()
    },
    dataObj6() {
      let arr1 = []
      let arr2 = []
      let List = this.dataObj[6].omsFrontPageDetailList
      List.map(item => {
        arr1.push(item.query01)
        arr2.push(item.query02)
      })
      this.dataObj[6].arr1 = arr1
      this.dataObj[6].arr2 = arr2
      this.setColumnarOption()
    },
    initLineEchart() {
      this.myLineChart = this.$echarts.init(document.getElementById('machineChart'))
    },
    initColumnarEchart() {
      this.myColumnarChart = this.$echarts.init(document.getElementById('ColumnarEchart'))
    },
    setLineOption() {
      let that = this
      let option = {
        xAxis: {
          type: 'category',
          data: this.dataObj[3].arr2
          // data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        grid: {
          left: '3%',
          right: '3%',
          bottom: '5%',
          containLabel: true
        },
        series: [
          {
            // data: [820, 932, 901, 934, 1290, 1330, 1320],
            data: this.dataObj[3].arr1,
            type: 'line',
            smooth: true,
            lineStyle: {
              normal: {
                color: '#9155FD'
              }
            },
            itemStyle: {
              normal: {
                color: '#9155FD'
              }
            },
            areaStyle: {
              color: 'rgba(230,222,251)'
            }
          }
        ]
      }
      this.myLineChart.setOption(option)
    },
    setColumnarOption() {
      let that = this
      let option = {
        xAxis: {
          type: 'category',
          // data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun York'],
          data: this.dataObj[6].arr2,
          axisLabel: { 
            show: true,
            interval: 0,//使x轴上的文字显示完全 
          }
        },
        yAxis: {
          type: 'value'
        },
        grid: {
          left: '3%',
          right: '3%',
          bottom: '5%',
          containLabel: true
        },
        series: [
          {
            data: this.dataObj[6].arr1,
            // data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar',
            lineStyle: {
              normal: {
                color: '#9155FD'
              }
            },
            itemStyle: {
              normal: {
                color: '#9155FD'
              }
            },
            areaStyle: {
              color: 'rgba(230,222,251)'
            }
          }
        ]
      }
      this.myColumnarChart.setOption(option)
    }
  }
}
</script>
<style lang="less" scoped>
.OmsHome{
  padding-bottom: 20px;
  box-sizing: border-box;
  img{
    width: 100%;
    height: 100%;
  }
}
.OmsHome_Itme {
  padding-bottom: 20px;
  h2 {
    font-size: 20px;
    font-family: Microsoft YaHei-Bold, Microsoft YaHei;
    font-weight: bold;
    color: #5e5669;
    margin: 0;
  }
  p {
    margin: 0;
  }
  .user {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px 0;
    p {
      font-size: 12px;
      font-family: Microsoft YaHei-Regular, Microsoft YaHei;
      font-weight: 400;
      color: rgba(94, 86, 105, 0.4);
      margin-top: 10px;
    }
    img {
      width: 108px;
      height: 108px;
      border-radius: 50%;
    }
  }
  .orderData {
    .orderData_list {
      display: flex;
      width: 100%;
      margin-top: 48px;
      .orderData_list_item {
        display: flex;
        width: 25%;
        align-items: center;
        .orderData_list_item_text {
          margin-left: 10px;
          font-size: 14px;
          font-family: Microsoft YaHei-Regular, Microsoft YaHei;
          font-weight: 400;
          color: rgba(94, 86, 105, 0.87);
          line-height: 14px;
          p {
            font-size: 18px;
            font-family: Microsoft YaHei-Bold, Microsoft YaHei;
            font-weight: bold;
            color: #403a47;
            line-height: 21px;
            margin-top: 10px;
          }
        }
      }
      img {
        width: 48px;
        height: 48px;
        border-radius: 8px 8px 8px 8px;
      }
    }
  }
  .platformData {
    .title {
      p {
        font-size: 32px;
        font-family: Microsoft YaHei-Bold, Microsoft YaHei;
        font-weight: bold;
        color: #403a47;
        line-height: 38px;
      }
    }
    .platformData_list {
      .platformData_list_item {
        width: 100%;
        height: 60px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 14px;
        font-family: Microsoft YaHei-Regular, Microsoft YaHei;
        font-weight: 400;
        color: rgba(94, 86, 105, 0.87);
        img {
          width: 56px;
          height: 56px;
        }
        .barType {
          width: 70px;
          height: 100%;
          display: flex;
          align-items: center;
          flex-direction: column;
          justify-content: center;
          div {
            width: 70px;
            height: 8px;
            background: #c4c4c4;
            border-radius: 9px 9px 9px 9px;
            opacity: 0.8;
            overflow: hidden;
            p {
              width: 0;
              height: 8px;
              background: #9155fd;
              border-radius: 9px 9px 9px 9px;
              opacity: 1;
            }
          }
        }
      }
      width: 100%;
      max-height: 300px;
      min-height: 300px;
      overflow: auto;
    }
  }
  .chart {
    width: 100%;
    height: 368px;
    .chart-box {
      width: 100%;
      height: 100%;
    }
  }
  .volumeOfBusiness {
    width: 100%;
    height: 77px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 18px;
    font-family: Microsoft YaHei-Regular, Microsoft YaHei;
    font-weight: 400;
    color: #000000;
    line-height: 16px;
    div {
      display: flex;
      flex-direction: column;
      justify-content: center;
      p {
        font-size: 12px;
        color: rgba(94, 86, 105, 0.4);
        margin: 5px 0;
      }
      span {
        font-size: 18px;
        font-family: Microsoft YaHei-Bold, Microsoft YaHei;
        font-weight: bold;
        color: #403a47;
        line-height: 21px;
      }
    }
    img {
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
  }

  .area {
    width: 100%;
    height: 397px;
    .chart-box {
      width: 100%;
      height: 100%;
    }
    .firstTitle {
      width: 100%;
      height: 40px;
    }
    .area_list {
      width: 100%;
      height: 357px;
      margin-top: 10px;
      overflow: auto;
      &::-webkit-scrollbar {
        width: 10px;
        background-color: pink;
      }
      .area_list_item {
        width: 100%;
        height: 30px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-top: 15px;
        .title {
          flex: 1;
          // width: 200px;
          height: 30px;
          font-size: 18px;
          text-align: left;
          line-height: 30px;
          font-family: Microsoft YaHei-Regular, Microsoft YaHei;
          color: #5e5669;
          font-weight: 600;
        }
        // .barType {
        //   width: 70px;
        //   height: 100%;
        //   display: flex;
        //   align-items: center;
        //   flex-direction: column;
        //   justify-content: center;
        //   // background-color: pink;
        //   div {
        //     width: 70px;
        //     height: 8px;
        //     background: #c4c4c4;
        //     border-radius: 9px 9px 9px 9px;
        //     opacity: 0.8;
        //     overflow: hidden;
        //     p {
        //       width: 0;
        //       height: 8px;
        //       background: #9155fd;
        //       border-radius: 9px 9px 9px 9px;
        //       opacity: 1;
        //     }
        //   }
        // }
        .quantity {
          width: 70px;
          height: 100%;
          font-size: 16px;
          font-family: Microsoft YaHei-Bold, Microsoft YaHei;
          font-weight: bold;
          color: #5e5669;
          display: flex;
          align-items: center;
          justify-content: center;
          flex-direction: column;
          // background-color: pink;
          p {
            font-size: 14px;
            font-family: Microsoft YaHei-Regular, Microsoft YaHei;
            font-weight: 400;
            color: rgba(94, 86, 105, 0.4);
          }
        }
        .down {
          display: flex;
          align-items: center;
          justify-content: space-between;
          color: #fd4e56;
          p {
            margin-right: 5px;
            width: 0;
            height: 0;
            border-left: 4px solid transparent;
            border-right: 4px solid transparent;
            border-top: 7px solid red;
          }
        }
        .up {
          display: flex;
          align-items: center;
          justify-content: space-between;
          color: #5bc824;
          p {
            margin-right: 5px;
            width: 0;
            height: 0;
            border-left: 4px solid transparent;
            border-right: 4px solid transparent;
            border-bottom: 7px solid #5bc824;
          }
        }
      }
    }
  }
}
</style>