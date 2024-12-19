<template>
  <a-card :bordered="false" @click.prevent="visible2False">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :span="15">
            <div style="display: flex;">
               <a-form-item :label="$t('月份')" style="margin-right: 20px">
                  <a-month-picker
                    v-model="queryParam.planDate"
                    format="YYYY-MM"
                    dateFormat="YYYY-MM"
                    @change="openChange"
                  />
                </a-form-item>

                <a-form-item :label="$t('姓名')">
                  <div style="display: flex; justify-content: space-between;">
                    <a-input :placeholder="$t('请输入姓名/部门')" v-model="queryParam.userName" style="width: 150px; margin-right: 10px"></a-input>
                    <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                      <a-button type="primary" @click="getUserList" icon="search">{{ $t('查询') }}</a-button>
                      <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
                      <a-button type="primary" @click="copy" style="margin-left: 8px" >{{ $t('复制排班') }}</a-button >
                      <a-button type="primary" @click="arrange" style="margin-left: 8px"  v-show="selectedArr.length != 0">{{ $t('排班') }}</a-button >
                      <a-button type="primary" @click="Reset" style="margin-left: 8px" v-show="selectedArr.length != 0"  >{{ $t('取消选择') }}</a-button>
                    </span>
                  </div> 
                </a-form-item>
            </div>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <div>
      <a-spin :spinning="spinning">
      <table :border="1" class="atable">
        <tr class="trhead">
          <th style="width: 50px; background-color: #1890ff">
            <a-checkbox v-model="allcheck" @click="allcheckClick('all')"></a-checkbox>
          </th>
          <th style="width: 100px; background-color: #1890ff">{{ $t('姓名') }}</th>
          <th
            v-for="(item, index) in days"
            :key="index"
            class="itemTAME"
            :style="changeWeekensColor(getWeeks(queryParam.planDate + '-' + (item >10?item:'0'+ item)))"
          >
            {{ item }}
            <p >{{ getWeeks(queryParam.planDate + '-' + (item >10?item:'0'+ item)) }}</p>
          </th>
        </tr>
        <tr v-for="(item, index) in SchedulingList" :key="index" class="trBody">
          <td style="background-color: #5babdc" class="realname">
            <a-checkbox v-model="item.check" @click="allcheckClick('item',index)"></a-checkbox>
          </td>
          <td style="background-color: #5babdc" class="realname">{{ item.realname }}{{item.id}}</td>
          <td
            v-for="(v, i) in item.scheduleInfo"
            :key="i"
            class="trBodyTd"
            :style="changeColor(v.pbType)"
            @click.stop="selectTd(v, index,i)"
            :class="selectedState.indexOf(v.id) >= 0 ? 'checkedTd' : ''"
          >
          <a-popover trigger="click" :visible="visible2['popover'+index+i]">
            <template #content>
              <p>{{$t('安排人')}}：{{v.attr3}}</p>
              <p>{{$t('地点')}}：{{v.attr1}}</p>
              <p>{{$t('工作内容')}}：{{v.attr2}}</p>
              <p>{{$t('安排时间')}}：{{v.attr4}}</p>
              <p>{{v.attr5}}</p>
            </template>
            <div class="trBodyTdDiv" style="font-size:14px">{{ v.pbType == 0?'休':"班" }}</div>
          </a-popover>
          </td>
        </tr>
      </table>
      </a-spin>
      <div class="Apagination">
        <a-pagination
          :current="pagination.current"
          :total="pagination.total"
          hideOnSinglePage
          @change="paginationChange"
        />
      </div>
    </div>

    <div>
      <a-modal v-model="visible" :title="$t('排班复制')" @ok="copyPb" :confirmLoading="confirmLoading">
        <a-form-item :label="$t('源月份')">
          <a-month-picker v-model="source" format="YYYY-MM" dateFormat="YYYY-MM" />
        </a-form-item>
        <a-form-item :label="$t('目标月')">
          <a-month-picker v-model="target" format="YYYY-MM" dateFormat="YYYY-MM" />
        </a-form-item>
      </a-modal>
    </div>
    
    <ArrangeWork ref="ArrangeWork" @ok="getUserList"/>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { getAction, postAction } from '@/api/manage'
import ArrangeWork from './modules/ArrangeWork'
import { formatDate } from '@/utils/util'
import moment from 'moment'
export default {
  name: 'ComconfCalList',
  components: {
    ArrangeWork
  },
  data() {
    return {
      allcheck:false,
      visible2:{},
      spinning:false,
      pagination: {
        current: 1,
        total: 0,
        pageSize: 10,
      },
      description: '工厂日历图表页面',
      queryParam: {
        planDate: moment().format('YYYY-MM'),
      },
      days: moment().daysInMonth(),
      SchedulingList: [], // 单元行
      SchedulingList2: [], // 单元行
      selectedArr: [],
      url: {
        echoList: '/sys/baseScheduleInfo/echoList', // 获取排班详情
        copyPb: '/sys/baseScheduleInfo/copyPb', // 复制上月排班到下个月
        departUserList: '/sys/user/departUserList', // 查询人员详情
        editSchedInfoList: '/sys/baseScheduleInfo/editSchedInfoList', // 编辑排班
      },
      selectedState: [], // 选中的单元格对象
      isctrl: false, // 是否按ctrl
      selectValue: null,
      visible: false, // 复制弹窗显示
      confirmLoading: false, // 复制排班弹窗按钮loading
      source: moment().format('YYYY-MM'), // 源月份
      target: moment().format('YYYY-MM'), // 目标月
      idArr:[],
    }
  },
  watch: {
    'queryParam.planDate'(newVal, oldVal) {
      this.days = moment(newVal).daysInMonth()
    },
  },
  created() {
    this.initConfig()
    
  },
  computed: {
    changeColor() {
      return (pbType) => {
        if (pbType == '1') {
          return { color: '#e2a8a7' }
        }
      }
    },
    changeWeekensColor() {
      return (e) => {
        if (e == this.$t('周六') || e == this.$t('周日')) {
          return { color: '#0195a9' }
        }
      }
    },
  },
  methods: {
    allcheckClick(type,index){
      if(type == 'all'){
        this.allcheck = !this.allcheck
        this.SchedulingList.map((item,index)=>{
          item.check = this.allcheck
        })
      }else if(type == 'item'){
        this.$nextTick(()=>{
          this.$set(this.SchedulingList[index],'check',!this.SchedulingList[index].check)
        })
        let i = -1
        this.SchedulingList.map((item,index)=>{
          if(!item.check){
              i = index
          }
        })
        if(i == -1){
          this.allcheck = false
        }else{
          this.allcheck = true
        }
      }
    },
    arrange(){
      if(this.selectedArr.length == 0) return this.$message.warning('请先选择要排班的对象！')
      this.$refs.ArrangeWork.open(this.selectedArr,)
    },
    Reset(){
      this.selectedState=[];
      this.selectedArr=[]
      this.allcheck = false
    },
    copy(){
      let idArr = []
      this.SchedulingList.map((item,index)=>{
        if(item.check){
          idArr.push(item.userId)
        }
      })
      console.log(idArr,'-----')
      if(idArr.length > 0){
        this.idArr = idArr
        this.visible = true
      }else{
        this.$message.warning('请先选择要复制的对象！')
      }
    },
    paginationChange(page, pageSize) {
      if (page && this.pagination.current != page) {
        this.pagination.current = page
      }
      if (pageSize) {
        this.pagination.pageSize = pageSize
      }
      this.setDate()
    },
    setDate() {
      let endIndex = this.pagination.current * this.pagination.pageSize
      let startIndex = endIndex - this.pagination.pageSize
      this.SchedulingList = this.SchedulingList2.slice(startIndex, endIndex)
    },
    selectTd(item, index,i) {
      if (this.isctrl) {
        // 如果按下的是ctrl
        let str = item.id // 存储被点击的单元格id
        let i = this.selectedState.indexOf(str) // 判断选中列表中是否包含这个点击的td
        if (i < 0) {
          this.selectedState.push(str) // 如果不包含就加进去
          this.selectedArr.push(item)
        } else {
          this.selectedState.splice(i, 1) // 如果包含就删，表示按下ctrl键点一下选中，在点一下取消选中
          this.selectedArr = this.selectedArr.filter((v, i) => {
            return v.id != str
          })
        }
      }else{
          this.visible2False()
          this.$set(this.visible2,'popover'+index+i,true)
      }
    },
    visible2False(){
        for(let key in this.visible2){
          this.$set(this.visible2,key,false)
        }
    },
    // 根据日期获取周几
    getWeeks(e) {
      let week = moment(e).day()
      switch (week) {
        case 1:
          return this.$t('周一')
        case 2:
          return this.$t('周二')
        case 3:
          return this.$t('周三')
        case 4:
          return this.$t('周四')
        case 5:
          return this.$t('周五')
        case 6:
          return this.$t('周六')
        case 0:
          return this.$t('周日')
      }
    },
    searchReset() {
      this.queryParam = {
        planDate: moment().format('YYYY-MM'),
      }
      this.days = moment().daysInMonth()
      this.SchedulingList = []
      this.allcheck = false
      this.idArr = []
      this.source = moment().format('YYYY-MM'), // 源月份
      this.target = moment().format('YYYY-MM'), // 目标月
      this.initConfig()
    },
    // 选择时间更改返回值为YYYY-MM
    openChange(e) {
      this.queryParam.planDate = moment(e).format('YYYY-MM')
    },
    getUserList() {
      // 获取人员详情
      this.spinning = true
      this.pagination.current = 1
      this.queryParam.orgCode = 'QC'
      getAction(this.url.echoList, this.queryParam).then((res) => {
        this.spinning = false
        this.SchedulingList2 = res.result
        this.pagination.total = this.SchedulingList2.length
        this.setDate()
        this.Reset()
      })
    },
    copyPb() {
      // 复制指定月份排班
      let data = {
        source: moment(this.source).format('YYYY-MM'),
        target: moment(this.target).format('YYYY-MM'),
        userIds:this.idArr.join(',')
      }
      this.confirmLoading = true
      getAction(this.url.copyPb, data).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.visible = false
          this.confirmLoading = false
        } else {
          this.$message.error(res.message)
          this.visible = false
          this.confirmLoading = false
        }
        // console.log(res,'13123')
      })
    },
    initConfig() {
      // 初始化数据
      // this.copyPb()
      this.getUserList()
      this.getWeeks()
      this.keyDown()
    },
    // 监听键盘
    keyDown() {
      // 键盘按下事件
      document.onkeydown = (e) => {
        //事件对象兼容
        let e1 = e || window.event || arguments.callee.caller.arguments[0]
        //键盘按键判断:左箭头-37;上箭头-38；右箭头-39;下箭头-40  回车：13   ctrl：17   shift：16
        switch (e1.keyCode) {
          // case 16:
          //   this.isshift = true;  // 如果shift按下就让他按下的标识符变为true
          //   break;
          case 17:
            this.isctrl = true // 如果ctrl按下就让他按下的标识符变为true
            break
        }
      }
      // 键盘抬起事件
      document.onkeyup = (e) => {
        //事件对象兼容
        let e2 = e || window.event || arguments.callee.caller.arguments[0]
        switch (e2.keyCode) {
          // case 16:
          //   this.isshift = false; // 如果shift抬起下就让他按下的标识符变为false
          //   break;
          case 17:
            this.isctrl = false // 如果ctrl抬起下就让他按下的标识符变为false
            break
        }
      }
    },  
  },
}
</script>
<style lang="less" >
@import '~@assets/less/common.less';
.rightContents {
  display: flex;
}
.Apagination{
 display: flex; 
 justify-content: space-around;
 margin-top: 10px;
}
.atable {
  width: 100%;
  td,
  th {
    text-align: center;
    font-size: 18px;
    height: 50px;
  }
  .trhead {
    background-color: #97cdfd;
    border: 1px solid #f0f1f5;
    color: #fff;

    .itemTAME {
      width: 45px;
      border-right-color: transparent;
      p {
        font-size: 12px;
        margin: 0;
      }
    }
  }
  .trBody {
    border: 1px solid #f0f1f5;
    color: #fff;
    background-color: #f5f9fc;
    .departName {
      font-size: 16px;
    }
    .realname {
      font-size: 16px;
    }
    .trBodyTd {
      cursor: pointer;
      // color: #a5a5a3;
      color: #8cc9e6;
      &:hover {
        background: #c7e8fd;
      }
      // &:hover {
      //   background: #0092da;
      //   color: #fff !important;
      // }
      .trBodyTdDiv{
        width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }

    .checkedTd {
      background-color: #0092da;
      color: #fff !important;
    }
  }
}
</style>