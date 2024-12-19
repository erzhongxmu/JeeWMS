<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="当日出库量" :total="dayReceiptNum">
<!--          <a-tooltip title="指标说明" slot="action">-->
<!--            <a-icon type="info-circle-o" />-->
<!--          </a-tooltip>-->
<!--          <div>-->
<!--            <trend flag="up" style="margin-right: 16px;">-->
<!--              <span slot="term">周同比</span>-->
<!--              12%-->
<!--            </trend>-->
<!--            <trend flag="down">-->
<!--              <span slot="term">日同比</span>-->
<!--              11%-->
<!--            </trend>-->
<!--          </div>-->
<!--          <template slot="footer">日均生产额<span>￥ 234.56</span></template>-->
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="当日入库量" :total="dayOutNum">
<!--          <a-tooltip title="指标说明" slot="action">-->
<!--            <a-icon type="info-circle-o" />-->
<!--          </a-tooltip>-->
<!--          <div>-->
<!--            <mini-area />-->
<!--          </div>-->
<!--          <template slot="footer">日订单量<span> {{ '1234' | NumberFormat }}</span></template>-->
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="剩余库位" :total="usableKw">
<!--          <a-tooltip title="指标说明" slot="action">-->
<!--            <a-icon type="info-circle-o" />-->
<!--          </a-tooltip>-->
<!--          <div>-->
<!--            <mini-bar :height="40" />-->
<!--          </div>-->
<!--          <template slot="footer">转化率 <span>60%</span></template>-->
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="库位使用率" :total="useRate+'%'">
<!--          <a-tooltip title="指标说明" slot="action">-->
<!--            <a-icon type="info-circle-o" />-->
<!--          </a-tooltip>-->
<!--          <div>-->
<!--            <mini-progress color="rgb(19, 194, 194)" :target="80" :percentage="78" :height="8" />-->
<!--          </div>-->
<!--          <template slot="footer">-->
<!--            <trend flag="down" style="margin-right: 16px;">-->
<!--              <span slot="term">同周比</span>-->
<!--              12%-->
<!--            </trend>-->
<!--            <trend flag="up">-->
<!--              <span slot="term">日环比</span>-->
<!--              80%-->
<!--            </trend>-->
<!--          </template>-->
        </chart-card>
      </a-col>
    </a-row>

<!--    <a-card :loading="loading" :bordered="false" :body-style="{padding: '0'}">-->
<!--      <div class="salesCard">-->
<!--        <a-tabs default-active-key="1" size="large" :tab-bar-style="{marginBottom: '24px', paddingLeft: '16px'}">-->
<!--          <div class="extra-wrapper" slot="tabBarExtraContent">-->
<!--            <div class="extra-item">-->
<!--              <a>今日</a>-->
<!--              <a>本周</a>-->
<!--              <a>本月</a>-->
<!--              <a>本年</a>-->
<!--            </div>-->
<!--            <a-range-picker :style="{width: '256px'}" />-->
<!--          </div>-->
<!--          <a-tab-pane loading="true" tab="任务完成量" key="1">-->
<!--            <a-row>-->
<!--              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">-->
<!--                <bar title="任务完成量" :dataSource="barData"/>-->
<!--              </a-col>-->
<!--              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">-->
<!--                <rank-list title="员工任务完成量" :list="rankList"/>-->
<!--              </a-col>-->
<!--            </a-row>-->
<!--          </a-tab-pane>-->
<!--          <a-tab-pane tab="生产趋势" key="2">-->
<!--            <a-row>-->
<!--              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">-->
<!--                <bar title="生产额趋势" :dataSource="barData"/>-->
<!--              </a-col>-->
<!--              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">-->
<!--                <rank-list title="门店生产排行榜" :list="rankList"/>-->
<!--              </a-col>-->
<!--            </a-row>-->
<!--          </a-tab-pane>-->
<!--        </a-tabs>-->
<!--      </div>-->
<!--    </a-card>-->

    <a-row>
      <a-col :span="24">
        <a-card :loading="loading" :bordered="false" title="最近一周访问量统计" :style="{ marginTop: '24px' }">
          <a-row>
            <a-col :span="6">
              <head-info title="今日IP" :content="loginfo.todayIp"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="environment" style="font-size: 24px"  />
              </a-spin>
            </a-col>
            <a-col :span="6">
              <head-info title="今日访问" :content="loginfo.todayVisitCount"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="team" style="font-size: 24px"  />
              </a-spin>
            </a-col>
            <a-col :span="6">
              <head-info title="总访问量" :content="loginfo.totalVisitCount"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="rise" style="font-size: 24px"  />
              </a-spin>
            </a-col>
          </a-row>
          <line-chart-multid :fields="visitFields" :dataSource="visitInfo"></line-chart-multid>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import ChartCard from '@/components/ChartCard'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from '@/components/chart/MiniArea'
  import MiniBar from '@/components/chart/MiniBar'
  import MiniProgress from '@/components/chart/MiniProgress'
  import RankList from '@/components/chart/RankList'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'
  import { getAction } from '@/api/manage'

  import Trend from '@/components/Trend'
  import { getLoginfo,getVisitInfo } from '@/api/api'

  const rankList = []
  const barData = []
  for (let i = 0; i < 12; i += 1) {
    barData.push({
      x: `${i + 1}月`,
      y: Math.floor(Math.random() * 1000) + 200
    })
  }
  export default {
    name: "IndexChart",
    components: {
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      MiniBar,
      MiniProgress,
      RankList,
      Bar,
      Trend,
      LineChartMultid,
      HeadInfo
    },
    data() {
      return {
        loading: true,
        center: null,
        rankList,
        barData,
        loginfo:{},
        visitFields:['ip','visit'],
        visitInfo:[],
        dayReceiptNum: '0',
        dayOutNum: '0',
        usableKw: '10',
        useRate: '0',
        url:{
          getDayReceiptNum: "/onl_cgreport_head/onlCgreportHead/queryOne?Id=1402826660193087489",
          getDayOutNum:"/onl_cgreport_head/onlCgreportHead/queryOne?Id=1402826071849676801",
          getUseRate:"onl_cgreport_head/onlCgreportHead/queryOne?Id=1402823197484683265",
          getList:"/onl_cgreport_head/onlCgreportHead/queryList?Id=1402864988896473089",
        },
        indicator: <a-icon type="loading" style="font-size: 24px" spin />
      }
    },
    created() {
      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
      this.initLogInfo();
    },
    mounted() {
      this.getDayReceiptNum()
      this.getDayOutNum()
      this.getUseRate()
      this.getList()
    },
    methods: {
      getDayReceiptNum() {
        var that = this
        getAction(that.url.getDayOutNum).then((res) => {
            that.dayReceiptNum = res.value.toString()
        })
      },
      getUseRate() {
        var that = this
        getAction(that.url.getUseRate).then((res) => {
          that.useRate = res.value.toString()
        })
      },
      getList() {
        var that = this
        getAction(that.url.getList).then((res) => {
          for (let i = 0; i < res.length; i++) {
            rankList.push({
              name: res.type1,
              total: res.type2
            })
          }
        })
      },
      getDayOutNum() {
        var that = this
        getAction(that.url.getDayReceiptNum).then((res) => {
          that.dayOutNum = res.value.toString()
        })
      },
      initLogInfo () {
        getLoginfo(null).then((res)=>{
          if(res.success){
            Object.keys(res.result).forEach(key=>{
              res.result[key] =res.result[key]+""
            })
            this.loginfo = res.result;
          }
        })
        getVisitInfo().then(res=>{
          if(res.success){
             this.visitInfo = res.result;
           }
         })
      },
    }
  }
</script>

<style lang="less" scoped>
  .circle-cust{
    position: relative;
    top: 28px;
    left: -100%;
  }
  .extra-wrapper {
    line-height: 55px;
    padding-right: 24px;

    .extra-item {
      display: inline-block;
      margin-right: 24px;

      a {
        margin-left: 24px;
      }
    }
  }

  /* 首页访问量统计 */
  .head-info {
    position: relative;
    text-align: left;
    padding: 0 32px 0 0;
    min-width: 125px;

    &.center {
      text-align: center;
      padding: 0 32px;
    }

    span {
      color: rgba(0, 0, 0, .45);
      display: inline-block;
      font-size: .95rem;
      line-height: 42px;
      margin-bottom: 4px;
    }
    p {
      line-height: 42px;
      margin: 0;
      a {
        font-weight: 600;
        font-size: 1rem;
      }
    }
  }
  /deep/.chart-card-content{
    height: 0px !important;
  }
</style>