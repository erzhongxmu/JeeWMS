<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="close"
    cancelText="关闭">
      <div class="content" ref="content" >
        <table :border="1" class="ProformaTable">
            <tr style="background-color: #4c1130; color: #ffffff;">
                <td colspan="17">{{totalPrice}}</td>
            </tr>
            <tr style="background-color: #a64d79; color: #ffffff;">
                <th>RECEIVED DATE</th>
                <th>PAYMENT LIST</th>
                <th>SELECT</th>
                <th>INV REF</th>
                <th style="width:60px"> </th>
                <th>MEMO</th>
                <th>CLIENT</th>
                <th>REGION</th>
                <th>CUR</th>
                <th>INV AMOUNT</th>
                <th style="background-color: #4c1130; color: #ffffff; border-color: #4c1130" >PAYMENT AMOUNT</th>
                <th>STATUS</th>
                <th>SELLSY INV</th>
                <th>BALANCE AFTER THIS PAYMENT</th>
                <th style="width:60px">PO NO.</th>
                <th style="width:60px">PO AMOUNT</th>
            </tr>
            
             <tr style="color: #741b47;" v-for="(item,index) in data" :key="index">
                <td style="background-color: #ffff00; color: #741b47;">{{item.query16}}</td>
                <td style="background-color: #ffff00; color: #741b47;">{{item.query17}}</td>
                <td style="background-color: #ead1dc; color: #741b47;">{{item.query18}}</td>
                <td style="background-color: #ead1dc; color: #741b47;">{{item.query19}}</td>
                <td></td>
                <td>{{item.query05}}</td>
                <td>{{item.query06}}</td>
                <td>{{item.query07}}</td>
                <td>{{item.query08}}</td>
                <td>{{item.query09}}</td>
                <td style="background-color: #741b47; color: #ffffff; border-color: #741b47">{{item.query10}}</td>
                <td>{{item.query11}}</td>
                <td>{{item.query12}}</td>
                <td style="background-color: #f3f3f3; color: #b7a8c6;">{{item.query13}}</td>
                <td style="background-color: #ead1dc; color: #741b47;">{{item.query14}}</td>
                <td style="background-color: #ead1dc; color: #741b47;">{{item.query15}}</td>
            </tr>

            <!-- <tr style="color: #741b47;" v-for="(item,index) in data" :key="index">
                <td style="background-color: #ffff00; color: #741b47;">{{item.query05}}</td>
                <td style="background-color: #ffff00; color: #741b47;">{{item.query06}}</td>
                <td style="background-color: #ead1dc; color: #741b47;">{{item.query07}}</td>
                <td style="background-color: #ead1dc; color: #741b47;">{{item.query08}}</td>
                <td>{{item.query09}}</td>
                <td style="background-color: #741b47; color: #ffffff; border-color: #741b47">{{item.query10}}</td>
                <td>{{item.query11}}</td>
                <td>{{item.query12}}</td>
                <td style="background-color: #f3f3f3; color: #b7a8c6;">{{item.query13}}</td>
                <td style="background-color: #ead1dc; color: #741b47;">{{item.query14}}</td>
                <td style="background-color: #ead1dc; color: #741b47;">{{item.query15}}</td>
            </tr> -->
        </table>
      </div>

    <template slot="footer">
      <a-button key="back" @click="handleCancel">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="toPdf">
        导出
      </a-button>
    </template>
  </j-modal>
</template>
 
<script>
import { getAction } from '@/api/manage'

export default {
  name: 'SalesContractPdf',
  components: {
  },
  data () {
    return {
      title:'形式发票(汇总)',
      width:1100,
      visible: false,
      disableSubmit: false,
      loading: false,
      data:[],
      totalPrice: 0
    }
  },
  methods: {
    open(e,type){
      this.totalPrice = 0
      this.visible = true
      this.type = type
      getAction('/jeeerp/busiPo/orderExcelOrPdf',{orderids:e,type:type}).then(res => {
        this.data = res.result
        this.data.map((item,index) => {
          this.totalPrice += Number(item.query10)
        })
      })
    },
    close () {
      this.$emit('close');
      this.visible = false;
    },
    handleOk () {

    },
    handleCancel(e) {
      this.visible = false;
    },
    toPdf() {
      // this.data.map((item,index)=>{
        console.log(this.$refs["content"])
        this.$PDFSave(this.$refs["content"], "形式发票-汇总");
    // })
    },
  },
};
</script>
 
<style scoped lang="less">
@import '~@assets/less/common.less';
.content {
    width: 100%;
    padding: 30px;
    .ProformaTable{
        width: 100%;
        th,td{
        text-align: center;
        }
        tr{
        border-color: #d5a6bd;
        height: 50px;
        &:nth-child(1) {
            border-color: #4c1130;
        }
        &:nth-child(2) {
            border-color: #a64d79;
        }
        }
    }
    // .total {
    //     width: 100%;
    //     height: 30px;
    //     background-color: #4c1130;
    //     color: #ffffff;
    //     text-align: center;
    //     line-height: 30px;
    // }
    
}

</style>
