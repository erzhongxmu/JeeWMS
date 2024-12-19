<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @cancel="close"
    :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }"
  >
    <template slot="footer">
      <a-button key="back" @click="close">{{ $t('取消') }}</a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleOk">{{ $t('导出') }}</a-button>
    </template>
    <a-form-model>
      <a-form-model-item label="上边距" :labelCol="labelCol" :wrapperCol="wrapperCol" >
        <a-input v-model="higthpx" placeholder="请输入上边距"  ></a-input>
      </a-form-model-item>
  </a-form-model>
    <div :id="'pdfDom' + index" class="content" :ref="'content' + index" v-for="(item, index) in topData" :key="index">
      <div :style="{height:higthpx+'px',width:'100%'}"></div>
      <!-- 标题 -->
      <div class="title">
        <div v-if="type == 'CGBHS' || type == 'CGHS'">采购合同</div>
        <div v-else>样品采购合同</div>
        <div style="font-size:16px">Subject:{{ item.query12 }}</div>
      </div>
      <!-- 主数据详情item -->
      <div class="title_item">
        <p style="width: 100%;">供应商Vendor：{{ item.query42 }}</p>
        <p>地址Address：{{ item.query07 }}</p>
        <p>采购合同号Contract No：{{ item.query06 }}</p>
        <p>制造商Manufacturer：{{ item.query05 }}</p>
        <p>日期DATE：{{ item.query08 }}</p>
        <p>联系人Contact：{{ item.query09 }}</p>
        <p>采购名Purchaser：{{ item.query10 }}</p>
        <p>联系电话Tel：{{ item.query11 }}</p>
        <p>跟单员Merchandiser：{{ item.query28 }}</p>
      </div>
      <!-- 表格 -->
      <table :border="0" class="POtable">
        <tr>
          <th style="width: 10%;font-size: 13px;" class="borderLeft">订单ID</th>
          <th style="width: 10%;font-size: 13px;">ITEM品名</th>
          <th style="width: 50%;font-size: 13px;">DESCRIPTION产品描述</th>
          <th style="width: 10%;font-size: 13px;">QUANTITY数量</th>
          <th style="width: 10%;font-size: 13px;">
            {{
              type == 'CGBHS' || type == 'YPBHS'
                ? `UNIT PRICE(${item.query25})单价`
                : `UNIT PRICE WITH
            VAT (${item.query25}含税单价)`
            }}
          </th>
          <th style="width: 10%;font-size: 13px;">
            {{
              type == 'CGBHS' || type == 'YPBHS'
                ? `TOTAL AMOUNT(${item.query25})总金额`
                : `TOTAL AMOUNT
            WITH VAT(${item.query25})含税后总金额`
            }}
          </th>
        </tr>
        <tr v-for="(v, i) in item.busiPoItemList" :key="i" class="trpdf">
          <td style="font-size: 10px;" class="borderLeft">{{ getA(i) + (v.query11 ? '+' + v.query11 : '') }}</td>
          <td style="font-size: 10px;">
            <div v-html="v.query12" style=" display: inline-block;word-wrap: break-word;word-break: break-all;"></div>
          </td>
          <td style="text-align: left;width: 200px;font-size: 10px;">
            <div v-html="v.query13" style=" display: inline-block;word-wrap: break-word;word-break: break-all;"></div>
          </td>
          <td style="font-size: 10px;">{{ v.query14 }}</td>
          <td style="width:70px;font-size: 10px;">{{ type == 'CGBHS' || type == 'YPBHS' ? v.query15 : v.query17 }}</td>
          <td style="width:70px;font-size: 10px;">{{ type == 'CGBHS' || type == 'YPBHS' ? v.query16 : v.query18 }}</td>
        </tr>
        <tr v-if="type == 'CGBHS' || type == 'CGHS'">
          <td style="font-size: 10px;" colspan="4" rowspan="3">
            <div v-html="comment1(item.query19)" v-if="item.query19 != null" style="text-align: left;"></div>
          </td>
          <td style="font-size: 10px;">样品可退金额</td>
          <td style="font-size: 10px;">{{ item.query27 }}</td>
        </tr>
        <tr v-else>
          <td style="font-size: 10px;" colspan="4" rowspan="3">
            <div v-html="comment1(item.query19)" v-if="item.query19 != null" style="text-align: left;"></div>
          </td>
        </tr>
        <!--  v-if="type == 'CGBHS' || type == 'CGHS'" -->
        <tr v-if="type == 'CGBHS' || type == 'CGHS'">
          <td style="font-size: 10px;">其他费用</td>
          <td style="font-size: 10px;">{{ item.query29 }}</td>
        </tr>
        <tr>
          <td style="font-size: 10px;" class="borderbottom">TOTAL</td>
          <td style="font-size: 10px;" class="borderbottom">{{ item.totalPrice }}</td>
        </tr>
      </table>
      <div class="hanshui" v-if="type == 'CGHS' || type == 'YPHS'">
        <div class="billRequirement">
          开票要求：
          <pre />
          1.请在发票备注栏写上我司合同号，发票原件和签字盖章订单原件一起寄回
          <pre />
          2.开票人跟复核人不可以是同一个人
        </div>

        <table border="1" class="taxTable">
          <tr>
            <th>税收分类编码</th>
            <th>品名</th>
            <th>规格型号</th>
            <th>单位</th>
            <th>数量</th>
            <th>单价</th>
            <th>金额</th>
            <th>税率</th>
            <th>税额</th>
          </tr>
          <tr v-for="(v, i) in item.busiPoItemList" :key="i">
            <td>{{ v.query23 }}</td>
            <td>{{ v.query24 }}</td>
            <td>{{ v.query25 }}</td>
            <td>{{ v.query26 }}</td>
            <td>{{ v.query14 }}</td>
            <td>{{ v.query15 }}</td>
            <td>{{ v.query16 }}</td>
            <td>{{ v.query21 }}</td>
            <td>{{ v.query22 }}</td>
          </tr>
        </table>
      </div>
      <div>以下无正文</div>
      <div>
        <div class="signature">
          <div>
            <p class="underline" style="border-bottom: 1px solid #000">（采购）Purchaser</p>
            <p class="Authorized">
              <i>（签字）Authorized by </i>
              <i class="date">（日期）Date </i>
            </p>
          </div>
          <div>
            <p class="underline" style="border-bottom: 1px solid #000">（供应商）Vendor</p>
            <p class="Authorized">
              <i>（签字）Authorized by </i>
              <i class="date">（日期）Date </i>
            </p>
          </div>
        </div>
      </div>
    </div>
  </j-modal>
</template>

<script>
import { getAction } from '../../../api/manage'
import jsPDF from 'jspdf'
import html2Canvas from 'html2canvas'
import BaseGoodsItemForm from './BaseGoodsItemForm'
import {numSub,numMulti,accDiv,numAdd} from '@/utils/util'

export default {
  name: 'BaseGoodsItemModal',
  components: {
    BaseGoodsItemForm
  },
  data() {
    return {
      data: [1],
      topData: [],
      title: '采购合同',
      width: 1200,
      visible: false,
      loading: false,
      disableSubmit: false,
      type: '',
      totalPrice: 0,
      higthpx:'0',
      labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
    }
  },
  methods: {
    comment1(text) {
      return text.replace(/(\r\n)|(\n)/g, '<br>')
    },
    getA(i) {
      const arr3 = Array.from(new Array(26), (ele, index) => {
        return String.fromCharCode(65 + index)
      })
      return arr3[i]
    },
    open(e, type) {
      this.visible = true
      this.type = type
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: e.query04, type: this.type }).then(res => {
        this.topData = res.result
        this.topData.map((item, index) => {
          let totalPrice = 0
          item.busiPoItemList.map((v, i) => {
            let a  = numMulti(numMulti(Number(v.query15),Number(v.query14)),100)
            let b  = numMulti(numMulti(Number(v.query17),Number(v.query14)),100)
            console.log(numMulti(Number(v.query15),Number(v.query14)),a,v.query15,v.query14);
            v.query16 = accDiv(Math.floor(a) ,100)
            v.query18 = accDiv(Math.floor(b) ,100)
            if (type == 'CGBHS' || type == 'YPBHS') {
              totalPrice += Number(v.query16)
            } else {
              totalPrice += Number(v.query18)
            }
            if (v.query13 != null) {
              v.query13 = v.query13.replace(/(\r\n)|(\n)/g, '<p style="margin-bottom:0"/>')
              v.query12 = v.query12.replace(/(\r\n)|(\n)/g, '<br/>')
            }
            //  v.query18 - v.query16
            v.query22 = numSub(v.query18,v.query16)	
          })
          item.totalPrice = numAdd(totalPrice.toFixed(2),item.query29)
        })
        this.data = res.result[0].busiPoItemList
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.type = ''
      this.totalPrice = 0
    },
    isSplit (nodes, index, pageHeight) {
      // 计算当前这块dom是否跨越了a4大小，以此分割
      if (nodes[index].offsetTop + nodes[index].offsetHeight < pageHeight && nodes[index + 1] && nodes[index + 1].offsetTop + nodes[index + 1].offsetHeight > pageHeight) {
        console.log(nodes[index].offsetTop,nodes[index].offsetHeight,pageHeight,nodes[index + 1],nodes[index + 1].offsetTop,nodes[index + 1].offsetHeight,pageHeight);
          return true;
      }
      return false;
    },
    offset(obj,direction){
        //将top,left首字母大写,并拼接成offsetTop,offsetLeft
        var offsetDir = 'offset'+ direction[0].toUpperCase()+direction.substring(1);
            
        var realNum = obj[offsetDir];
        var positionParent = obj.offsetParent;  //获取上一级定位元素对象
            
        while(positionParent != null){
        realNum += positionParent[offsetDir];
        positionParent = positionParent.offsetParent;
        }
        return realNum;
    },
    handleOk() {
      this.topData.map((item, index) => {
        this.getPdf('#pdfDom' + index, '合同' + this.topData[index].query13)
      })
    }
  }
}
</script>
<style scoped lang="less">
.content {
  width: 100%;
  height: auto;
  color: #000;
  padding: 5px 30px;
 
  .title {
    display: flex;
    align-items: center;
    width: 100%;
    height: 100px;
    justify-content: space-between;

    div {
      font-size: 30px;
    }

    img {
      width: 350px;
      height: 100px;
    }
  }

  .title_item {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    font-size: 16px;
    margin-top: 30px;

    p {
      width: 50%;
      padding-left: 20px;
      margin-bottom: 5px;
    }
  }

  .POtable {
    width: 100%;

    th,
    td {
      text-align: center;
      border-top: 1px solid #000;
      border-right: 1px solid #000;
    }
    .borderLeft {
      border-left: 1px solid #000;
    }
    .borderbottom {
      border-bottom: 1px solid #000;
    }

    tr {
      min-height: 50px;
    }
  }

  .opening_bank {
    width: 100%;
    margin-bottom: 10px;
    text-align: left;
    border: 0;
    display: flex;
    .left {
      flex: 1;
    }
    .right {
      width: calc(20% + 4px);
      div {
        display: flex;
        border-right: 1px solid #000;
        p {
          text-align: center;
          line-height: 30px;
          margin: 0;
          width: 50%;
          border-left: 1px solid #000;
          border-bottom: 1px solid #000;
        }
      }
    }
  }

  .hanshui {
    width: 100%;
    margin-top: 20px;

    .billRequirement {
      color: red;

      pre {
        margin: 0;
      }
    }

    .taxTable {
      width: 100%;
      max-width: 100%;
      margin-bottom: 20px;

      th,
      td {
        text-align: center;
      }

      tr {
        height: 30px;
      }
    }
  }

  .key_point {
    width: 100%;
    height: auto;
    border: 1px solid #e3e3e3;
    padding: 10px;
    font-size: 10px;
  }

  .signature {
    width: 100%;
    display: flex;
    align-items: center;
    height: 30px;
    justify-content: space-between;
    margin-top: 50px;
    font-size: 12px;

    div {
      width: 40%;
    }

    p {
      width: 100%;
      margin: 0;
      line-height: 14px;
      font-style: italic;

      &:first-child {
        font-weight: 600;
        font-style: normal;
        // border-bottom: 1px solid #000;
      }

      &:last-child {
        .date {
          margin-left: 100px;
        }
      }
    }
  }

  .footer {
    text-align: center;
    width: 100%;
    margin-top: 30px;

    pre {
      margin: 0;
    }
  }
}
</style>
