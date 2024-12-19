<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }"
    @cancel="close"
    cancelText="关闭"
  >
    <div ref="content" class="content" :id="'pdfDom' + index"  v-for="(item,index) in data" :key="index" >
      <!-- 1 -->
      <div class="left">
        <div class="table1">
          <div class="table1_item">
            <p>CUSTOMER</p>
            <p>{{item.query05}}</p>
          </div>
          <div class="table1_item">
            <p>PO#</p>
            <p style="background: #fff">{{item.query06}}</p>
          </div>
          <div class="table1_item">
            <p>PRODUCTS</p>
            <p style="flex-grow:2.6">{{item.query07}}</p>
            <p style="font-size:12px">{{item.query22}}</p>
          </div>
          <div class="table1_item">
            <p>ASTREA INV#</p>
            <p>{{item.query08}}</p>
          </div>
          <div class="table1_item">
            <p>PO date</p>
            <p>{{item.query09}}</p>
          </div>
          <div class="table1_item">
            <p>SALES#</p>
            <p>{{item.query10}}</p>
          </div>
          <div class="table1_item">
            <p>SUPPLIER</p>
            <p>{{item.query11}}</p>
          </div>
          <div class="table1_item">
            <p>PURCHASER</p>
            <p>{{item.query12}}</p>
          </div>
          <div class="table1_item table1_item_h">
            <p>GOOGLE DRIVE</p>
            <p>{{item.query13}}</p>
          </div>
          <div class="table1_item table1_item_h">
            <p class="borderbottom">COMPANY</p>
            <p class="borderbottom">{{item.query14}}</p>
          </div>
        </div>
        <!-- 2 -->
        <div class="table1 table2" style="page-break-after:always;">
          <div class="table1_item">
            <p>QTY</p>
            <p class="background4">{{item.query15}}</p>
          </div>
          <div class="table1_item">
            <p style="width: 100%">BANKING INFORMATION</p>
          </div>
          <div class="table1_item">
            <p>UNIT PRICE</p>
            <p class="background4">{{item.query16}}</p>
          </div>
          <div class="table1_item">
            <p>CURRENCY</p>
            <p class="background4">{{item.query17}}</p>
          </div>
          <div class="table2left">
            <div class="table1_item">
              <p style="font-size: 12px">SPL/FILM/MOULD NON REFUNDABLE</p>
              <p class="background4">{{item.query18 && item.query18.indexOf("否") != -1?item.query18.split(',')[1]:''}}</p>
            </div>
            <div class="table1_item">
              <p style="font-size: 12px">SPL/FILM/MOULD REFUNDABLE</p>
              <p class="background4">{{item.num16}}</p>
              <!-- <p class="background4">{{item.query18 && item.query18.indexOf("是") != -1?item.query18.split(',')[1]:''}}</p> -->
            </div>
            <div class="table1_item">
              <p>TOTAL (1)</p>
              <p class="background2">{{item.query01 == 'CGD'?item.num09 - item.num18:item.num09}}</p>
            </div>
          </div>
          <div class="table1_item table1_item3">
            <p style="width: 100%" class="background3">
              {{item.query19}}
            </p>
          </div>
        </div>
        <!-- 3 -->
        <div class="table3">
          <div class="noneBorder">
            <p>Others costs</p>
            <p></p>
            <p>Note</p>
          </div>
          <div>
            <p></p>
            <p>{{item.num18}}</p>
            <p></p>
          </div>
          <div>
            <p></p>
            <p></p>
            <p></p>
          </div>
          <div>
            <p></p>
            <p></p>
            <p class="borderbottom"></p>
          </div>
          <div>
            <p class="background1 borderbottom">TOTAL (2)</p>
            <p class="background2 borderbottom borderright" style="flex: none; width: 33%">{{item.num18}}</p>
          </div>
        </div>
        <!-- 4 -->
        <div class="table3">
          <div>
            <p class="background1">TOTAL IN PO#</p>
            <p class="background3">{{item.query20}}</p>
            <p class="borderbottom background5">TOTAL (1+2) MATCH WITH PO</p>
          </div>
          <div>
            <p class="background1 borderbottom">VAT%</p>
            <p class="background2 borderbottom borderright" style="flex: none; width: 33%">{{item.num10?item.num10:0}}%</p>
          </div>
        </div>
        <!-- 5 -->
        <div class="Payments">
          <h2>Payments</h2>
          <div class="background3 PaymentsTi" style="border-left: 1px solid #000">
            <p>PR01 - MERCHANDISE (DEPOSIT)</p>
            <p>PR07 - FILM/MOULD NON REFUNDABLE</p>
            <p>PR01 - MERCHANDISE (BALANCE)</p>
            <p>PR08 - CERTIFICATION</p>
            <p>PR02 - SAMPLE REFUNDABLE</p>
            <p>PR09 - OTHER SERVICES (PRINTING…)</p>
            <p>PR03 - SAMPLE NON REFUNDABLE</p>
          </div>
          <div class="PaymentsTableTh">
            <p class="background1">VAT#</p>
            <p class="background1">CODE</p>
            <p class="background1">TOTAL</p>
            <p class="background1">PAYMENTLIST</p>
            <p class="background1">PAYMENT DATE</p>
            <p class="background1">REFERENCE</p>
            <p class="background1">VALIDATION</p>
          </div>
          <!-- 循环 -->
          <div v-for="(v, index) in 5" :key="index" class="PaymentsTableTd">
            <p></p>
            <p>{{item.busiPoItemList[index]?item.busiPoItemList[index].query23:''}}</p>
            <p>{{item.busiPoItemList[index]?item.busiPoItemList[index].query24:''}}</p>
            <!-- <p>{{item.busiPoItemList[index]?item.busiPoItemList[index].query23:''}}{{index}}</p>
            <p>{{item.busiPoItemList[index]?item.busiPoItemList[index].query24:''}}{{index}}</p> -->
            <p></p>
            <p></p>
            <p></p>
            <p></p>
          </div>
        </div>

        <!-- 6 -->
        <div class="Payments">
          <h2>Deliveries & Stock</h2>
          <div class="PaymentsTableTh">
            <p class="background1">DATE</p>
            <p class="background1">SHIP#</p>
            <p class="background1">QTY SHIPPED</p>
            <p class="background1" style="flex: 1">SALES FAPIAO/INV#</p>
            <p class="background1">PAYMENT DATE</p>
            <p class="background1" style="flex: none">VALIDATION DATE</p>
          </div>
          <!-- 循环 -->
          <div v-for="(item, index) in 5" :key="index" class="PaymentsTableTd">
            <p></p>
            <p></p>
            <p></p>
            <p style="flex: 1"></p>
            <p></p>
            <p style="flex: none"></p>
          </div>
        </div>
      </div>
      <div class="right">
        <p>{{item.query23}}</p>
      </div>
    </div>
    <template slot="footer">
      <a-button key="back" @click="handleCancel"> 取消 </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="toPdf"> 导出 </a-button>
    </template>
  </j-modal>
</template>

<script>
import { getAction,httpAction, postAction,downFile } from '@/api/manage'

export default {
  name: 'SalesContractPdf',
  components: {},
  data() {
    return {
      title: '付款单',
      width: 1100,
      visible: false,
      disableSubmit: false,
      loading: false,
      data: []
    }
  },
  methods: {
    open(e,type){
        this.visible = true
        this.type = type
        getAction('/jeeerp/busiPo/orderExcelOrPdf',{orderids:e.query04,type:this.type}).then(res => {
          this.data = res.result
          console.log(res,'============')
        })
      },
    close() {
      this.visible = false
    },
    handleOk() {},
    handleCancel(e) {
      this.visible = false
    },
    toPdf() {
      let arr = this.$refs['content']
      arr.map((item,index)=>{
        this.getPdf('#pdfDom' + index, "付款单" +  this.data[index].query06 )
      })
    },
  },
}
</script>

<style scoped lang="less">
@import '~@assets/less/common.less';
.content {
  width: 100%;
  height: auto;
  padding: 30px;
  display: flex;
  p {
    display: flex;
    margin: 0;
    padding-left: 5px;
    align-items: center;
  }
  .left {
    width: 93%;
    .table1 {
      display: flex;
      flex-wrap: wrap;
      .table1_item {
        width: 50%;
        height: 40px;
        display: flex;
        p {
          border-top: 1px solid #000;
          border-left: 1px solid #000;
          background: #b6d7a8;
          color: #000;
          font-weight: 600;
          flex: 1;

          &:first-child {
            font-weight: 400;
            color: #fff;
            flex: none;
            width: 110px;
            background: #38761d;
          }
        }
      }
      .table1_item_h {
        width: 100%;
      }
    }
    .table2 {
      margin-top: 50px;
      .table2left {
        width: 50%;
        div {
          width: 100%;
        }
      }
      .table1_item3 {
        width: 50%;
        height: 120px;
      }
    }
    .table3 {
      width: 100%;
      height: auto;
      margin-top: 20px;
      div {
        width: 100%;
        display: flex;
        height: 30px;
        align-items: center;
        p {
          width: 33%;
          height: 100%;
          border-top: 1px solid #000;
          border-left: 1px solid #000;
          &:last-child {
            flex: 1;
          }
        }
      }
      .noneBorder {
        border: 0;
        p {
          border: 0;
          display: flex;
          align-items: end;
        }
      }
    }
    .Payments {
      width: 100%;
      margin-top: 30px;
      h2 {
        margin: 0;
      }
      .PaymentsTi {
        display: flex;
        flex-wrap: wrap;
        border-top: 1px solid #000;
        border-left: 1px solid #000;
        p {
          width: 50%;
          font-size: 12px;
        }
      }
      .PaymentsTableTh {
        width: 100%;
        display: flex;
        p {
          height: 34px;
          justify-content: center;
          width: 14.28%;
          border-top: 1px solid #000;
          border-left: 1px solid #000;
          color: #fff;
          &:last-child {
            flex: 1;
          }
        }
      }
      .PaymentsTableTd {
        width: 100%;
        display: flex;
        p {
          justify-content: center;
          height: 34px;
          width: 14.28%;
          border-top: 1px solid #000;
          border-left: 1px solid #000;
          color: #000;
          &:last-child {
            flex: 1;
          }
        }
        &:last-child {
          p {
            border-bottom: 1px solid #000;
          }
        }
      }
    }
  }
  .right {
    color: #000;
    flex: 1;
    border: 1px solid #000;
    font-size: 40px;
    position: relative;
    p {
      top: 50%;
      width: 500px;
      left: -320%;
      transform: rotate(90deg);
      position: absolute;
    }
  }
  .borderbottom {
    border-bottom: 1px solid #000;
  }
  .borderright {
    border-right: 1px solid #000;
  }
  .background1 {
    background: #38761d !important;
  }
  .background2 {
    background: #6aa84f !important;
  }
  .background3 {
    background: #b6d7a8 !important;
  }
  .background4 {
    background: #fff !important;
  }
  .background5 {
    background: #4a86e8 !important;
  }
}
</style>