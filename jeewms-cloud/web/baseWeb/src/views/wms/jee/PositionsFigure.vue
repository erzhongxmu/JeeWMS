<template>
  <div>
    <a-form layout="inline">
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol"  :label="$t('仓库')">
        <a-input v-model="queryParam.storeCode"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="$t('储位')">
        <a-input v-model="queryParam.kwCode"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="$t('层数')">
        <a-input v-model="queryParam.cengshu"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="$t('行数')">
        <a-input v-model="queryParam.hangshu"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="$t('列数')">
        <a-input v-model="queryParam.lieshu"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol">
        <div style="display: flex;">
          <a-button @click="chaxun('0')" style="margin-left: 10px">{{$t('正向查询')}}</a-button>
          <a-button @click="chaxun('1')" style="margin-left: 10px">{{$t('反向查询')}}</a-button>
        </div>
      </a-form-item>
      </a-form>
        <!-- <div style="padding:0px;border:0px">
          <div style="margin-left:100px"> 桃红色标识此储位有货，淡绿色标识此储位为空</div>
          <div name="searchColums" style="float: left; padding-left: 0px;padding-top: 5px;padding-bottom: 20px">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;" title="仓库">仓库: </span>
            <input type="text" name="cangku" style="width: 100px; height: 30px;">
            <span disabled="true" style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"title="储位">储位: </span>
            <input type="text" name="chuwei" style="width: 100px; height: 30px;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"title="其他">其他: </span>
            <input type="text" name="des" style="width: 100px; height: 30px;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"title="层数">层数: </span>
            <input type="text" name="cengshu" value="1" style="width: 100px; height: 30px;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"title="行数">行数: </span>
            <input type="text" name="hangshu" value="42" style="width: 100px; height: 30px;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"title="列数">列数: </span>
            <input type="text" name="lieshu" value="34" style="width: 100px; height: 30px;">
          </div>
        </div> -->
    <div id="bottom" style="margin-top:100px;border:2px"></div>
  </div>
</template>

<script>
import $ from 'jquery'
import { getAction } from '@api/manage'
export default {
  name: 'PositionsFigure',
  data(){
    return {
      queryParam:{
        cengshu:1,
        hangshu:60,
        lieshu:60
      },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 10 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 14 },
        },
    }
  },
  mounted() {
    let that = this
    $(document).ready(()=> {
      that.chaxun()
    })
    window.addtab = this.addtab
  },
  methods: {
    addtab(name) {
      var str = name.replace('|', '\n')
      alert(str)
    },
    print(id) {
      if (begindate == '' || enddate == '') {
        alert('开始或者结束日期不能为空')
      } else {
        var url = 'mvCusCostController.do?doPrint&id=' + id + '&begindate=' + begindate + '&enddate=' + enddate
        window.open(url)
      }
    },
    chaxun(type) {
      let taht = this
      console.log(111,)
      var cangku
      var chuwei
      var des

      cangku = $('input[name="cangku"]').val()
      chuwei = $('input[name="chuwei"]').val()
      var hangshu = $('input[name="hangshu"]').val() * 1
      var lieshu = $('input[name="lieshu"]').val() * 1
      var cengshu = $('input[name="cengshu"]').val()
      des = $('input[name="des"]').val()
      //加载消息
      var url = '/jeewms/baKw/getKwPicData?storeCode=' + (cangku ? cangku : '')  + '&kwCode=' + (chuwei ? chuwei : '') + '&des=' + (des ? des : '') + '&znode=' + (cengshu ? cengshu : '') + '&rowNum=' + (hangshu ? hangshu : '') + '&type=' + (type ? type : '')
      getAction('/jeewms/baKw/getKwPicData',this.queryParam).then(res=>{
        if (res.success){
          var messageList = res.result.dataList
          var messageContent = ''
          var tincount = 0
          if (messageList.length > 0) {
            let num = hangshu * lieshu + 1  // 行数乘以列数  计算一共有多少格子
            let obj = {
              bin_store: '',
              binid: '',
              colour: 'white',
              des: '',
              tincount: '',
              xnode: '',
              ynode: '',
              znode: ''
            }// 创建一个空数字 用于填充空格子(因为空格子后台不返回 前端循环渲染需要填充数据)
            let list = [] // 创建数组填充所有格子
            //循环填充数组
            for (let s = 0; s < num; s++) {
              list.push(obj)
            }
            for (let o = 0; o < messageList.length; o++) {
              if (messageList[o].ynode * 1 == 1) {
                console.log(messageList[o].ynode* 1, messageList[o].ynode * 1 * messageList[o].xnode * 1,'------')
                list[messageList[o].ynode * 1 * messageList[o].xnode * 1] = messageList[o]
              } else {
                list[(messageList[o].ynode * 1 - 1) * hangshu * 1 + messageList[o].xnode * 1] = messageList[o]
              }
            }
            // console.log(list)

            //计算宽度
            var width = 17.5 * hangshu * 1
            $('#bottom').css('width', width)
            for (let i = 1; i < list.length; i++) {
              if(list[i]){
                  messageContent += " <div class='all' href='javascript:void(0);' style='background:" +(list[i] && list[i].colour || '')+"' onclick='javascript:addtab(\"" + (list[i] && list[i].des || '') + "\")';return false;'>";
                  messageContent += (list[i] && list[i].tincount || '0') + " </div> ";
                }else{
                  messageContent += "  <div class='all' ></div> "
                }
            }
          }

          $('#bottom').html(messageContent)
          // console.dir(messageContent);
        }
      })
    }

  }
}
</script>

<style type="text/css">
#bottom {
  /*width: 1000px;*/
  /*background: red;*/
  /*width: 100%;*/
  
}

a.a01:link, a.a01:visited {
  font-size: 12px;
  font-family: verdana;
  /*width: 90px;*/
  margin: 1px;
  color: #1A1A1A;
  display: inline-table;
  background-color: #FF4040;
}

a.a01:active, a.a01:hover {
  font-size: 12px;
  font-family: verdana;
  /*width: 90px;*/
  margin: 1px;
  color: #1A1A1A;
  display: inline-table;
}

a.a02:link, a.a01:visited {
  font-size: 12px;
  font-family: verdana;
  /*width: 90px;*/
  margin: 1px;
  color: #1A1A1A;
  display: inline-table;
  background-color: #66CD00;
}

a.a02:active, a.a01:hover {
  font-size: 12px;
  font-family: verdana;
  /*width: 90px;*/
  margin: 1px;
  color: #1A1A1A;
  display: inline-table;
}

.all {
  /*display: inline-block;*/
  width: 16.5px;
  height: 16.5px;
  line-height: 16.5px;
  text-align: center;
  margin-right: 1px;
  margin-bottom: 1px;
}

#bottom {
  width: 60%;
  display: flex;
  flex-wrap: wrap;
  margin: auto;
}

</style>
<style scoped>

</style>