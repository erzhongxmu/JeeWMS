<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="创建人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createBy">
              <a-input v-model="model.createBy" placeholder="请输入创建人登录名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createName">
              <a-input v-model="model.createName" placeholder="请输入创建人名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="货主" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cusCode">
              <a-input v-model="model.cusCode" placeholder="请输入货主"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cusName">
              <a-input v-model="model.cusName" placeholder="请输入货主名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="波次号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="waveId">
              <a-input v-model="model.waveId" placeholder="请输入波次号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="goodsId">
              <a-input v-model="model.goodsId" placeholder="请输入商品编码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="goodsName">
              <a-input v-model="model.goodsName" placeholder="请输入商品名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="imCusCode">
              <a-input v-model="model.imCusCode" placeholder="请输入单号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="仓位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="binId">
              <a-input v-model="model.binId" placeholder="请输入仓位"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="托盘" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tinId">
              <a-input v-model="model.tinId" placeholder="请输入托盘"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="proData">
              <a-input v-model="model.proData" placeholder="请输入生产日期"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="baseGoodscount">
              <a-input-number v-model="model.baseGoodscount" placeholder="请输入数量" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="出库备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="omBeiZhu">
              <a-input-number v-model="model.omBeiZhu" placeholder="请输入出库备注" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="基本单位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="baseUnit">
              <a-input v-model="model.baseUnit" placeholder="请输入基本单位"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="一次下架容器" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="firstRq">
              <a-input v-model="model.firstRq" placeholder="请输入一次下架容器"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="分拣容器" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="secondRq">
              <a-input v-model="model.secondRq" placeholder="请输入分拣容器"  ></a-input>
            </a-form-model-item>
          </a-col>
<!--          <a-col :span="24">-->
<!--            <a-form-model-item label="by1" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="by1">-->
<!--              <a-input v-model="model.by1" placeholder="请输入by1"  ></a-input>-->
<!--            </a-form-model-item>-->
<!--          </a-col>-->
<!--          <a-col :span="24">-->
<!--            <a-form-model-item label="by2" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="by2">-->
<!--              <a-input v-model="model.by2" placeholder="请输入by2"  ></a-input>-->
<!--            </a-form-model-item>-->
<!--          </a-col>-->
<!--          <a-col :span="24">-->
<!--            <a-form-model-item label="by3" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="by3">-->
<!--              <a-input v-model="model.by3" placeholder="请输入by3"  ></a-input>-->
<!--            </a-form-model-item>-->
<!--          </a-col>-->
<!--          <a-col :span="24">-->
<!--            <a-form-model-item label="by4" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="by4">-->
<!--              <a-input v-model="model.by4" placeholder="请输入by4"  ></a-input>-->
<!--            </a-form-model-item>-->
<!--          </a-col>-->
<!--          <a-col :span="24">-->
<!--            <a-form-model-item label="by5" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="by5">-->
<!--              <a-input v-model="model.by5" placeholder="请输入by5"  ></a-input>-->
<!--            </a-form-model-item>-->
<!--          </a-col>-->
          <a-col :span="24">
            <a-form-model-item label="商品条码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shpTiaoMa">
              <a-input v-model="model.shpTiaoMa" placeholder="请输入商品条码"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'WaveToDownForm',
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
           by1: [
              { required: true, message: '请输入by1!'},
           ],
           by2: [
              { required: true, message: '请输入by2!'},
           ],
           by3: [
              { required: true, message: '请输入by3!'},
           ],
           by4: [
              { required: true, message: '请输入by4!'},
           ],
           by5: [
              { required: true, message: '请输入by5!'},
           ],
        },
        url: {
          add: "/jeewms/waveToDown/add",
          edit: "/jeewms/waveToDown/edit",
          queryById: "/jeewms/waveToDown/queryById"
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
                that.$message.success(this.$t('操作成功'));
                that.$emit('ok');
              }else{
                that.$message.warning(this.$t('操作失败'));
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