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
      <div class="content" :ref="'content' + index" v-for="(item,index) in data" :key="index">
        <!-- 第一行 -->
        <div class="one">{{item.query05}}</div>
        <!-- 第二行 -->
        <div class="two">
            <p></p>
            <p>
                Estimate {{item.query05}} <pre/> 
                Date : {{item.query06}} <pre/> 
                No. client : {{item.query07}} <pre/> 
            </p>
        </div>
        <!-- 第三行 -->
        <div class="three">
            <p> </p>
            <p></p>
            <p>
                {{item.query10}}
            </p>
        </div>
        <!-- 第四行 -->
        <div class="four">Object :  {{item.query11}}</div>
        <!-- 表格 -->
        <table border="1" class="ProformaTable">
            <tr>
                <th>Name / Code</th>
                <th>Description</th>
                <th>Qty/Unit</th>
                <th>price</th>
                <th>Sales tax</th>
                <th>Line total</th>
            </tr>
            <tr v-for="(v,i) in item.busiPoItemList" :key="i">
                <td>{{v.query12}}</td>
                <td>{{v.query13}}</td>
                <td>{{v.query14}}</td>
                <td>{{v.query15}}</td>
                <td>{{v.query16}}</td>
                <td>{{v.query17}}</td>
            </tr>
            <tr>
                <td colspan="4"></td>
                <td>
                    Total <pre/>
                    Total due
                </td>
                <td>{{item.itemTotalPrice}}</td>
            </tr>
        </table>
        <!-- 签名 -->
        <div class="signature">
            <p></p>
            <p>Client signature:</p>
        </div>
        <!-- 备注 -->
        <div class="remark">
            <p>
                Valid until : <pre/>
                Payment means : <pre/>
                Terms of payment : <pre/>
            </p>
            <p>
                {{item.query18}}<pre/>
                virement bancaire <pre/>
                à la commande <pre/>
            </p>
        </div>
        <!-- 公司备注 -->
        <div class="companyRemark"></div>
        <!-- 页脚 -->
        <div class="foot">
            <p>
                <pre/>
                <pre/>
            </p>
            <p>
                Page 1/1
            </p>
        </div>

       
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
      title:'形式发票',
      width:1100,
      visible: false,
      disableSubmit: false,
      loading: false,
      data:[1]
    }
  },
  methods: {
    open(e,type){
        this.visible = true
        getAction('/jeeerp/busiPo/orderExcelOrPdf',{orderids:e.query04,type:type}).then(res => {
        this.data = res.result
        this.data.map((item,index) => { // 计算总价
          let busiPoItemList = item.busiPoItemList
          let total = 0
          busiPoItemList.map((v,i) => {
            total += Number(v.query17)
            item['itemTotalPrice'] = total
          })    
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
      this.data.map((item,index)=>{
        console.log(this.$refs["content"+ index][0])
        this.$PDFSave(this.$refs["content"+ index][0], "我的文件"+(index+1));
    })
    },
  },
};
</script>
 
<style scoped lang="less">
@import '~@assets/less/common.less';
.content {
    width: 100%;
    padding: 30px;
    .one {
        width: 100%;
        height: 50px;
        padding: 5px;
        border: 1px solid #c3cbdd;
    }

    .two {
        width: 100%;
        height: 100px;
        display: flex;
        border: 1px solid #c3cbdd;
        border-top: 0;
        p {
            width: 50%;
            height: 100%;
            &:first-child {
                // position: relative;
                border-right: 1px solid #c3cbdd;
                // &::after {
                //     content: "";
                //     background: #c3cbdd;
                //     width: 1px;
                //     height: 100%;
                //     position: absolute;
                //     top: 0;
                //     left: 100%;
                // }
            }
            &:last-child {
                padding: 5px;
                pre{
                    margin: 0;
                }
            }
            

        }
    }

    .three {
        width: 100%;
        height: 150px;
        display: flex;
        border: 1px solid #c3cbdd;
        border-top: 0;
        p {
            height: 100%;
            &:first-child {
                flex: 1;
                border-right: 1px solid #c3cbdd;
            }
            &:nth-child(2) {
                width: 5%;
                border-right: 1px solid #c3cbdd;
            }
            &:last-child {
                width: 50%;
                padding: 5px;
                pre{
                    margin: 0;
                }
            }
            

        }
    }

    .four {
        width: 100%;
        height: 50px;
        padding: 5px;
        border: 1px solid #c3cbdd;
        border-top: 0;
        line-height: 40px;
    }

    .ProformaTable{
        width: 100%;
        border-top: 0;
        th,td{
        text-align: center;
        }
        tr{
        height: 50px;
        }
    }

    .signature {
        width: 100%;
        height: 30px;
        display: flex;
        border: 1px solid #c3cbdd;
        border-top: 0;
        p {
            width: 50%;
            height: 100%;
            padding: 3px 10px;
            &:first-child {
                border-right: 1px solid #c3cbdd;
            }
        }
    }

    .remark {
        width: 100%;
        height: 100px;
        display: flex;
        border: 1px solid #c3cbdd;
        border-top: 0;
        p {
            width: 50%;
            height: 100%;
            padding: 3px 10px;
            &:first-child {
                width: 30%;
                border-right: 1px solid #c3cbdd;
            }
            &:last-child {
                flex: 1;
            }
            pre{
                margin: 0;
            }
        }
    }

    .companyRemark {
        width: 100%;
        height: 50px;
        padding: 5px;
        border: 1px solid #c3cbdd;
        border-top: 0;
        line-height: 40px;
    }

    .foot {
        width: 100%;
        height: 60px;
        display: flex;
        border: 1px solid #c3cbdd;
        border-top: 0;
        p {
            height: 100%;
            padding: 3px 10px;
            &:first-child {
                width: 80%;
                border-right: 1px solid #c3cbdd;
                text-align: center;
            }
            &:last-child {
                flex: 1;
                text-align: right;
                line-height: 50px;
            }
            pre{
                margin: 0;
            }
        }
    }
}

</style>
