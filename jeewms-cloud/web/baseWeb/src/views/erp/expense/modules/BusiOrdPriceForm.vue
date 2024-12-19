<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <!-- <a-col :span="8">
            <a-form-model-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createName">
              <a-input v-model="model.createName" placeholder="请输入创建人名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="updateName">
              <a-input v-model="model.updateName" placeholder="请输入更新人名称"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item label="单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query01">
              <a-input v-model="model.query01" placeholder="请输入单据类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query02">
              <a-input v-model="model.query02" placeholder="请输入单据状态"  ></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item label="建单日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query03">
              <a-input v-model="model.query03" placeholder="请输入建单日期"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item label="单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query04">
              <a-input v-model="model.query04" placeholder="请输入单号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            <a-form-model-item label="公司" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query05">
              <a-input v-model="model.query05" placeholder="请输入公司"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="工厂" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query06">
              <a-input v-model="model.query06" placeholder="请输入工厂"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="库存地点" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query07">
              <a-input v-model="model.query07" placeholder="请输入库存地点"  ></a-input>
            </a-form-model-item>
          </a-col> -->
          <a-col :span="8">
            <a-form-model-item label="对象编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query08">
              <a-input v-model="model.query08" placeholder="请输入对象编码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="对象名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query09">
              <a-input v-model="model.query09" placeholder="请输入对象名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query10">
              <a-input v-model="model.query10" placeholder="请输入商品编码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query11">
              <a-input v-model="model.query11" placeholder="请输入商品名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query12">
              <a-input v-model="model.query12" placeholder="请输入单位"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="主PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query13">
              <a-input v-model="model.query13" placeholder="请输入主PO"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="子PO" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query14">
              <a-input v-model="model.query14" placeholder="请输入子PO"  ></a-input>
            </a-form-model-item>
          </a-col>
          <!-- <a-col :span="8">
            （采购/销售/税率/成本价）
            <a-form-model-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query15">
              <a-input v-model="model.query15" placeholder="请输入类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="币种" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query16">
              <a-input v-model="model.query16" placeholder="请输入币种"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query17" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query17">
              <a-input v-model="model.query17" placeholder="请输入query17"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query18" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query18">
              <a-input v-model="model.query18" placeholder="请输入query18"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query19" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query19">
              <a-input v-model="model.query19" placeholder="请输入query19"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query20" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query20">
              <a-input v-model="model.query20" placeholder="请输入query20"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query21" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query21">
              <a-input v-model="model.query21" placeholder="请输入query21"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query22" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query22">
              <a-input v-model="model.query22" placeholder="请输入query22"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query23" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query23">
              <a-input v-model="model.query23" placeholder="请输入query23"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query24" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query24">
              <a-input v-model="model.query24" placeholder="请输入query24"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query25" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query25">
              <a-input v-model="model.query25" placeholder="请输入query25"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query26" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query26">
              <a-input v-model="model.query26" placeholder="请输入query26"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query27" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query27">
              <a-input v-model="model.query27" placeholder="请输入query27"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query28" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query28">
              <a-input v-model="model.query28" placeholder="请输入query28"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query29" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query29">
              <a-input v-model="model.query29" placeholder="请输入query29"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="query30" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="query30">
              <a-input v-model="model.query30" placeholder="请输入query30"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="单位数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num01">
              <a-input-number v-model="model.num01" placeholder="请输入单位数量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="未完成数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num02">
              <a-input-number v-model="model.num02" placeholder="请输入未完成数量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="已完成数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num03">
              <a-input-number v-model="model.num03" placeholder="请输入已完成数量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="总价格" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num04">
              <a-input-number v-model="model.num04" placeholder="请输入总价格" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="num05" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="num05">
              <a-input-number v-model="model.num05" placeholder="请输入num05" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="关联单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link01">
              <a-input v-model="model.link01" placeholder="请输入关联单据类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="关联单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link02">
              <a-input v-model="model.link02" placeholder="请输入关联单号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="link03" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link03">
              <a-input v-model="model.link03" placeholder="请输入link03"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="link04" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link04">
              <a-input v-model="model.link04" placeholder="请输入link04"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="link05" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link05">
              <a-input v-model="model.link05" placeholder="请输入link05"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text01">
              <a-input v-model="model.text01" placeholder="请输入备注"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text02">
              <a-input v-model="model.text02" placeholder="请输入备注"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="text03" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text03">
              <a-input v-model="model.text03" placeholder="请输入text03"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="text04" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text04">
              <a-input v-model="model.text04" placeholder="请输入text04"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="text05" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="text05">
              <a-input v-model="model.text05" placeholder="请输入text05"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="单据附件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr1">
              <a-input v-model="model.attr1" placeholder="请输入单据附件"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="attr2" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr2">
              <a-input v-model="model.attr2" placeholder="请输入attr2"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="attr3" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr3">
              <a-input v-model="model.attr3" placeholder="请输入attr3"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="attr4" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr4">
              <a-input v-model="model.attr4" placeholder="请输入attr4"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="8">
            <a-form-model-item label="attr5" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attr5">
              <a-input v-model="model.attr5" placeholder="请输入attr5"  ></a-input>
            </a-form-model-item>
          </a-col> -->
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'BusiOrdPriceForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/jeeerp/busiOrdPrice/add",
          edit: "/jeeerp/busiOrdPrice/edit",
          queryById: "/jeeerp/busiOrdPrice/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>