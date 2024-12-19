<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    >
    <a-spin :spinning="confirmLoading" style="height: 300px">
      <a-form :form="form">
        <div class="title-item">基础属性</div>
<!--        <a-form-item label="库存地点" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['kwName',validatorRules.kwName]" :placeholder="title==$t('查看')?'':'请输入库存地点'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['storeCode',validatorRules.storeCode]" :trigger-change="true" dictCode="ba_store,store_name,store_code" :placeholder="title==$t('查看')?'':'请选择仓库'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="所属货主" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['custom']" :trigger-change="true" dictCode="md_cus,zhong_wen_qch,id" :placeholder="title==$t('查看')?'':'请选择所属货主'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
        <a-form-item label="储位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['kwName',validatorRules.kwName]" :placeholder="title==$t('查看')?'':'请输入库位编码'" :disabled="title!=$t('新增')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="库位编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['kwCode',validatorRules.kwCode]" :placeholder="title==$t('查看')?'':'请输入库位编码'" :disabled="title!=$t('新增')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="库位条码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['kwBarCode',validatorRules.kwBarCode]" :placeholder="title==$t('查看')?'':'请输入库位条码'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
<!--        <a-form-item label="库位类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['kwType']" :trigger-change="true" dictCode="kw_type" :placeholder="title==$t('查看')?'':'请选择库位类型'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="库位属性" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['kwAttr']" :trigger-change="true" dictCode="kw_attr" :placeholder="title==$t('查看')?'':'请选择库位属性'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="仓库名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['storeName']" :disabled="true" :placeholder="title==$t('查看')?'':'请输入仓库编码'"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="所属库区" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" @change="e => getStoreByAreaCode(e)" v-decorator="['storeAreaCode',validatorRules.storeAreaCode]" :trigger-change="true" dictCode="ba_store_area,area_name,area_code" :placeholder="title==$t('查看')?'':'请选择所属库区'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="库位用途" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['binUse',validatorRules.binUse]" :trigger-change="true" dictCode="bin_use" :placeholder="title==$t('查看')?'':'请选择库位用途'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="产品属性" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-multi-select-tag type="list_multi" v-decorator="['productAttr']" :trigger-change="true" dictCode="product_attr" :placeholder="title==$t('查看')?'':'请选择产品属性'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="动态库位" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['dynamicLocation',validatorRules.dynamicLocation]" :trigger-change="true" dictCode="is_or_not" :placeholder="title==$t('查看')?'':'请选择是否为动态库位'" :disabled="title==$t('查看')?true:false"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="是否混放" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['mixStore',validatorRules.mixStore]" :trigger-change="true" dictCode="is_or_not" :placeholder="title==$t('查看')?'':'请选择是否混放'" :disabled="title==$t('查看')?true:false" @change="changeType"/>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="存放商品类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-multi-select-tag v-if="mixStore === 'Y'" type="list_multi" v-decorator="['partType',validatorRules.partType]" :trigger-change="true" dictCode="ba_part_type,type_name,id" :placeholder="title==$t('查看')?'':'请选择存放商品类型'" :disabled="title==$t('查看')?true:false"/>-->
<!--          <j-dict-select-tag v-else type="list" v-decorator="['partType',validatorRules.partType]" :trigger-change="true" dictCode="ba_part_type,type_name,id" :placeholder="title==$t('查看')?'':'请选择存放商品类型'" :disabled="title==$t('查看')?true:false"/>-->

<!--          <a-select-->
<!--            v-if="mixStore === 'Y'"-->
<!--            show-search-->
<!--            mode="multiple"-->
<!--            option-filter-prop="children"-->
<!--            :filter-option="filterOption"-->
<!--            @focus="handleFocus"-->
<!--            @blur="handleBlur"-->
<!--            @change="handleChange"-->
<!--            :maxTagCount="1"-->
<!--            :disabled="title==$t('查看')?true:false"-->
<!--            v-decorator="['partType',validatorRules.partType]"-->
<!--            :placeholder="title==$t('查看')?'':'请选择存放商品类型'"-->
<!--          >-->
<!--            <a-select-option v-for="(item,key) in selectList" :value="item.id">-->
<!--              {{item.typeName}}-->
<!--            </a-select-option>-->
<!--          </a-select>-->

<!--          <a-select-->
<!--            v-else-->
<!--            show-search-->
<!--            option-filter-prop="children"-->
<!--            :filter-option="filterOption"-->
<!--            @focus="handleFocus"-->
<!--            @blur="handleBlur"-->
<!--            @change="handleChange"-->
<!--            v-decorator="['partType',validatorRules.partType]"-->
<!--            :placeholder="title==$t('查看')?'':'请选择存放商品类型'"-->
<!--            :disabled="title==$t('查看')?true:false"-->
<!--          >-->
<!--            <a-select-option v-for="(item,key) in selectList" :value="item.id">-->
<!--              {{item.typeName}}-->
<!--            </a-select-option>-->
<!--          </a-select>-->
<!--        </a-form-item>-->

<!--        <div class="title-item">单位</div>-->
<!--        <a-form-item label="最小批量" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['volumeUnit']" :trigger-change="true" dictCode="bulk_unit" :placeholder="title==$t('查看')?'':'请选择最小批量'" :disabled="title==$t('查看')?true:false"/>-->
<!--&lt;!&ndash;          <a-input v-decorator="['volumeUnit']" :placeholder="title==$t('查看')?'':'请输入体积单位'" :disabled="title==$t('查看')?true:false"></a-input>&ndash;&gt;-->
<!--        </a-form-item>-->
<!--        <a-form-item label="最大批量" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['weightUnit']" :trigger-change="true" dictCode="weight_unit" :placeholder="title==$t('查看')?'':'请选择最大批量'" :disabled="title==$t('查看')?true:false"/>-->
<!--&lt;!&ndash;          <a-input v-decorator="['weightUnit']" :placeholder="title==$t('查看')?'':'请输入重量单位'" :disabled="title==$t('查看')?true:false"></a-input>&ndash;&gt;-->
<!--        </a-form-item>-->
<!--        <a-form-item label="出库口" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['areaUnit']" :trigger-change="true" dictCode="acreage_unit" :placeholder="title==$t('查看')?'':'请选择出库口'" :disabled="title==$t('查看')?true:false"/>-->
<!--&lt;!&ndash;          <a-input v-decorator="['areaUnit']" :placeholder="title==$t('查看')?'':'请输入面积单位'" :disabled="title==$t('查看')?true:false"></a-input>&ndash;&gt;-->
<!--        </a-form-item>-->


<!--        <div class="title-item">库位限制</div>-->
<!--        <a-form-item label="最大体积" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['maxVolume']" :placeholder="title==$t('查看')?'':'请输入最大体积'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="最大重量" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['maxWeight']" :placeholder="title==$t('查看')?'':'请输入最大重量'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="储位组别" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['maxArea']" :placeholder="title==$t('查看')?'':'请输入储位组别'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="最大托盘" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['maxTray',validatorRules.maxTray]" :placeholder="title==$t('查看')?'':'请输入最大托盘'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->

<!--        <div class="title-item">体积/位置信息</div>-->
<!--        <a-form-item label="长度" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['length']" :placeholder="title==$t('查看')?'':'请输入长度'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="宽度" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['width']" :placeholder="title==$t('查看')?'':'请输入宽度'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="高度" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['height']" :placeholder="title==$t('查看')?'':'请输入高度'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="X坐标" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['xnode']" :placeholder="title==$t('查看')?'':'请输入X坐标'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="Y坐标" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['ynode']" :placeholder="title==$t('查看')?'':'请输入Y坐标'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="Z坐标" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['znode']" :placeholder="title==$t('查看')?'':'请输入Z坐标'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->

<!--        <div class="title-item">上架/下架次序</div>-->
<!--        <a-form-item label="上架次序" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['orderOn']" :placeholder="title==$t('查看')?'':'请输入上架次序'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="取货次序" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['orderDowm']" :placeholder="title==$t('查看')?'':'请输入取货次序'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="动线" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['mingXi3']" :placeholder="title==$t('查看')?'':'请输入动线'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->

<!--        <a-form-item label="停用" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['status']" :placeholder="title==$t('查看')?'':'请输入停用'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--&lt;!&ndash;        <a-form-item label="明细" :labelCol="labelCol" :wrapperCol="wrapperCol">&ndash;&gt;-->
<!--&lt;!&ndash;          <a-input v-decorator="['detail']" :placeholder="title==$t('查看')?'':'请输入明细'" :disabled="title==$t('查看')?true:false"></a-input>&ndash;&gt;-->
<!--&lt;!&ndash;        </a-form-item>&ndash;&gt;-->
<!--        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['attr1']" :placeholder="title==$t('查看')?'':'请输入备注'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="备注1" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['attr2']" :placeholder="title==$t('查看')?'':'请输入备注1'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="备注2" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['attr3']" :placeholder="title==$t('查看')?'':'请输入备注2'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
<!--        <a-form-item label="电子标签id" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-input v-decorator="['rfId']" :placeholder="title==$t('查看')?'':'请输入电子标签id'" :disabled="title==$t('查看')?true:false"></a-input>-->
<!--        </a-form-item>-->
      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import { getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"


  export default {
    name: "BaKwModal",
    components: {
      JDictSelectTag,
      JMultiSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:1200,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 7 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        selectList: [],
        confirmLoading: false,
        validatorRules: {
          kwName: {
            rules: [{
              required: true,
              message: '请输入储位!'
            }, ]
          },
          kwCode: {
            rules: [{
              required: true,
              message: '请输入库位编码!'
            }, ]
          },
          storeCode: {
            rules: [{
              required: true,
              message: '请选择仓库!'
            }, ]
          },
          storeAreaCode: {
            rules: [{
              required: true,
              message: '请选择所属库区!'
            }, ]
          },
          kwBarCode: {
            rules: [{
              required: true,
              message: '请输入库位条码!'
            }, ]
          },
          binUse: {
            rules: [{
              required: true,
              message: '请选择库位用途!'
            }, ]
          },
          dynamicLocation: {
            rules: [{
              required: true,
              message: '请选择是否为动态库位!'
            }, ]
          },
          mixStore: {
            rules: [{
              required: true,
              message: '请选择是否混放!'
            }, ]
          },
          partType: {
            rules: [{
              required: true,
              message: '请选择存放商品类型!'
            }, ]
          },
          volumeUnit: {
            rules: [{
              required: true,
              message: '请输入体积单位!'
            }, ]
          },
          weightUnit: {
            rules: [{
              required: true,
              message: '请输入重量单位!'
            }, ]
          },
          areaUnit: {
            rules: [{
              required: true,
              message: '请输入面积单位!'
            }, ]
          },
          maxVolume: {
            rules: [{
              required: true,
              message: '请输入最大体积!'
            }, ]
          },
          maxWeight: {
            rules: [{
              required: true,
              message: '请输入最大重量!'
            }, ]
          },
          maxArea: {
            rules: [{
              required: true,
              message: '请输入最大面积!'
            }, ]
          },
          maxTray: {
            rules: [{
              required: true,
              message: '请输入最大托盘!'
            }, ]
          },

        },
        url: {
          add: "/jeewms/baKw/add",
          edit: "/jeewms/baKw/edit",
          getStoreByAreaCode: "/jeewms/baStoreArea/getStoreByAreaCode",
          getSelectList: "/jeewms/baPartType/getSelectList",
        },
        mixStore:'N',
      }
    },
    created () {
    },
    mounted() {
      this.getSelectList()
    },
    methods: {
      handleChange(value) {
        console.log(`selected ${value}`);
      },
      handleBlur() {
        console.log('blur');
      },
      handleFocus() {
        console.log('focus');
      },
      filterOption(input, option) {
        return (
          option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
        );
      },
      getStoreByAreaCode(val) {
        if(val) {
          getAction(this.url.getStoreByAreaCode+'?areaCode='+val).then((res) => {
            if (res.success) {
              this.form.setFieldsValue({
                'storeName': res.result.storeName
              })
            }
          })
        }
      },
      changeType(val){
        this.mixStore = val
        if(val=='Y') {
          this.form.setFieldsValue({partType: []})
        } else {
          this.form.setFieldsValue({partType: ''})
        }
      },
      initDictConfig() {
      },
      add () {
        this.edit({});
      },
      edit (record) {
        this.mixStore = record.mixStore
        this.form.resetFields();
        this.model = Object.assign({}, record);
        if(this.model.partType && this.model.partType.length > 0 && this.mixStore == 'Y') {
          this.model.partType = this.model.partType.split(',')
        }
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'kwName','kwCode','kwBarCode','kwType','kwAttr','orderOn','orderDowm','custom','volumeUnit','weightUnit','areaUnit','maxVolume','maxWeight','maxArea','maxTray','length','width','height','status','detail','storeCode','productAttr','attr1','attr2','attr3','rfId','xnode','ynode','znode','binUse','dynamicLocation','mixStore','storeAreaCode','partType'))
          this.form.setFieldsValue({dynamicLocation: 'N'})
          if (!record.mixStore){
            this.form.setFieldsValue({mixStore:'N'})
          }
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        if(that.title == that.$t('查看')) {
          that.close();
          return
        }
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
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
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            if(Array.isArray(formData.partType) && formData.partType.length > 0) {
              formData.partType = formData.partType.join(",")
            }
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(this.$t('操作成功'));
                that.$emit('ok');
                that.close()
              }else{
                that.$message.warning(this.$t('操作失败'));
              }
            }).finally(()=>{
              that.confirmLoading = false;
            })
          }

        })
      },
      getSelectList() {
        getAction(this.url.getSelectList).then((res) => {
          if (res.success) {
            this.selectList = res.result
          }
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'kwName','kwCode','kwBarCode','kwType','kwAttr','orderOn','orderDowm','custom','volumeUnit','weightUnit','areaUnit','maxVolume','maxWeight','maxArea','maxTray','length','width','height','status','detail','storeCode','productAttr','attr1','attr2','attr3','rfId','xnode','ynode','znode','binUse','dynamicLocation','mixStore','storeAreaCode','partType'))
      },


    }
  }
</script>
<style scoped>
  >>>.ant-modal-content .ant-modal-body .ant-form-item{
    display: inline-block !important;
    width: 33%;
  }
  >>>.ant-modal-content .ant-modal-body{
    max-height: 700px !important;
    overflow-y: auto;
  }
</style>