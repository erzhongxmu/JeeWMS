<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    :destroyOnClose="true"
    >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="地区代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
<!--          <a-input v-decorator="['cityCode']" placeholder="请输入地区代码"></a-input>-->
          <a-tree-select
            ref="treeSelect"
            v-decorator="['pid']"
            style="width: 100%"
            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
            :tree-data="treeData"
            :replace-fields="{children:'baCityChild', key:'id', value: 'id', title: 'cityName'}"
            placeholder="请选择地区代码"
            tree-default-expand-all
            @select="chooseCity"
          >
          </a-tree-select>
        </a-form-item>
        <a-form-item label="地区名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['cityName']" placeholder="请输入地区名称"></a-input>
        </a-form-item>
        <a-form-item label="地区助记码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['citySerc']" placeholder="请输入地区助记码"></a-input>
        </a-form-item>
        <a-form-item label="城市类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cityTypeCode']" :trigger-change="true" dictCode="ba_city_type,city_type_name,city_type_code" placeholder="请选择城市类型"/>
        </a-form-item>
        <a-form-item label="片区信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['bareaCode']" :trigger-change="true" dictCode="ba_barea,barea_name,barea_code" placeholder="请选择片区信息"/>
        </a-form-item>
        <a-form-item label="大区信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['dareaCode']" :trigger-change="true" dictCode="ba_area,area_name,area_code" placeholder="请选择大区信息"/>
        </a-form-item>
        <a-form-item label="停用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['cityDel']" :trigger-change="true" dictCode="is_del" placeholder="请选择停用"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "BaCityModal",
    components: {
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        treeData: [],
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
          add: "/jeewms/baCity/add",
          edit: "/jeewms/baCity/edit",
          getChild: "/jeewms/baCity/getChild",
        },
        expandedRowKeys:[],
        pidField:""

      }
    },
    created () {
      this.getCityTree()
    },
    methods: {
      add (obj) {
        this.edit(obj);
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'pid','cityName','citySerc','cityTypeCode','bareaCode','dareaCode','cityDel'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
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
            let old_pid = this.model[this.pidField]
            let formData = Object.assign(this.model, values);
            let new_pid = this.model[this.pidField]
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(this.$t('操作成功'));
                that.submitSuccess(formData,old_pid==new_pid)
              }else{
                that.$message.warning(this.$t('操作失败'));
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }

        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'pid','cityName','citySerc','cityTypeCode','bareaCode','dareaCode','cityDel'))
      },
      submitSuccess(formData,flag){
        if(!formData.id){
          let treeData = this.$refs.treeSelect.getCurrTreeData()
          this.expandedRowKeys=[]
          this.getExpandKeysByPid(formData[this.pidField],treeData,treeData)
          this.$emit('ok',formData,this.expandedRowKeys.reverse());
        }else{
          this.$emit('ok',formData,flag);
        }
      },
      getExpandKeysByPid(pid,arr,all){
        if(pid && arr && arr.length>0){
          for(let i=0;i<arr.length;i++){
            if(arr[i].key==pid){
              this.expandedRowKeys.push(arr[i].key)
              this.getExpandKeysByPid(arr[i]['parentId'],all,all)
            }else{
              this.getExpandKeysByPid(pid,arr[i].children,all)
            }
          }
        }
      },
      //获取地区树形列表
      getCityTree() {
        getAction(this.url.getChild).then((res) => {
          if (res.success) {
            this.treeData = res.result;
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
        })
      },
      chooseCity(val) {
        console.log(val)
      }

    }
  }
</script>