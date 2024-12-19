<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="到货通知单号">
              <a-input placeholder="请输入到货通知单号" v-model="queryParam.imNoticeId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品编码">
              <a-input placeholder="请输入商品编码" v-model="queryParam.goodsCode"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="商品名称">
                <a-input placeholder="请输入商品名称" v-model="queryParam.goodsName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="订单号">
                <a-input placeholder="请输入订单号" v-model="queryParam.imCusCode"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>-->
<!--      <a-button icon="download" @click="handleExportXls('商品')">{{$t('导出')}}</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
<!--      </a-upload>-->
<!--      <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>-->
      <a-button @click="batchEdit" v-has="'returnAccept:batchUpdate'">批量验收</a-button>
      <a-button @click="batchSave" v-has="'returnAccept:batchUpdate'">批量保存</a-button>
      <a-button @click="batchCancel" v-has="'returnAccept:batchUpdate'">批量取消验收</a-button>
<!--      <a-dropdown>-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
<!--          <a-menu-item key="6" @click="batchEdit"><a-icon type="edit"/>验收</a-menu-item>-->
<!--          <a-menu-item key="7" @click="batchSave"><a-icon type="save"/>保存</a-menu-item>-->
<!--          <a-menu-item key="8" @click="batchCancel"><a-icon type="stop"/>取消验收</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :rowClassName="setRowClsaa">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>
        <template slot="goodsPrdData"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-date-picker
              @change="e => handleBatchChange(e, record.id, 'goodsPrdData')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="goodsBatch"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'goodsBatch')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="goodsWqmCount"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'goodsWqmCount')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="tinId"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'tinId')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="binPlan"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'binPlan')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)" v-has="'returnAccept:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'returnAccept:check'"/>
          <a @click="handleEdit(record)" v-has="'returnAccept:update'">{{$t('编辑')}}</a>

          <a-divider type="vertical"  v-has="'returnAccept:update'"/>
          <a @click="openAccept(record.id)" v-has="'returnAccept:accept'">验收</a>
        </span>

      </a-table>
    </div>

    <j-modal
      title="验收"
      width="800px"
      :visible="showModal"
      :confirmLoading="confirmLoading"
      switchFullscreen
      @ok="accept"
      @cancel="handleCancel"
      >
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">

          <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-date placeholder="请选择生产日期" v-decorator="['goodsPrdData']" :trigger-change="true" style="width: 100%"/>
          </a-form-item>
          <a-form-item label="批次" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsBatch']" placeholder="请输入批次"></a-input>
          </a-form-item>
          <a-form-item label="计划库位" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['binPlan']" placeholder="请输入计划库位"></a-input>
          </a-form-item>
          <a-form-item label="验收数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsWqmCount',validatorRules.goodsWqmCount]" placeholder="请输入验收数量"></a-input>
          </a-form-item>
          <a-form-item label="托盘" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['tinId']" placeholder="请输入托盘"></a-input>
          </a-form-item>

        </a-form>
      </a-spin>
    </j-modal>

    <wmImNoticeI-modal ref="modalForm" @ok="modalFormOk"></wmImNoticeI-modal>
  </a-card>
</template>

<script>

  import WmImNoticeIModal from './modules/WmImNoticeIModal'
  import { mixinDevice } from '@/utils/mixin'
  import JDate from '@/components/jeecg/JDate'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { postAction } from '@/api/manage'
  import '@/assets/less/TableExpand.less'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import moment from 'moment'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'
  import pick from 'lodash.pick'

  export default {
    name: "WmImNoticeIList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
    components: {
      WmImNoticeIModal,
      JDictSelectTag,
      JDate
    },
    data () {
      return {
        description: '商品管理页面',
        form: this.$form.createForm(this),
        showModal: false,
        confirmLoading: false,
        aloneAcceptId: '',
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        validatorRules: {
          goodsWqmCount: {
            rules: [
              { required: true, message: '请输入验收数量!'},
            ]
          },
        },
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            scopedSlots: { customRender: 'action' },
          },
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align: 'left',
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          {
            title:'到货通知单号',
            align: 'left',
            dataIndex: 'imNoticeId'
          },
          {
            title:'商品编码',
            align: 'left',
            dataIndex: 'goodsCode'
          },
          {
            title:'商品名称',
            align: 'left',
            dataIndex: 'goodsName'
          },
          {
            title:'数量',
            align: 'left',
            dataIndex: 'goodsCount'
          },
          {
            title:'生产日期',
            align: 'left',
            dataIndex: 'goodsPrdData',
            scopedSlots: { customRender: 'goodsPrdData' },
          },
          {
            title:'批次',
            align: 'left',
            dataIndex: 'goodsBatch',
            scopedSlots: { customRender: 'goodsBatch' },
          },
          {
            title:'收货登记数量',
            align: 'left',
            dataIndex: 'goodsQmCount'
          },
          // {
          //   title:'计划库位',
          //   align: 'left',
          //   dataIndex: 'binPlan',
          //   scopedSlots: { customRender: 'binPlan' },
          // },
          // {
          //   title:'单位',
          //   align: 'left',
          //   dataIndex: 'goodsUnit'
          // },
          // {
          //   title:'未清数量',
          //   align: 'left',
          //   dataIndex: 'goodsWqmCount',
          //   scopedSlots: { customRender: 'goodsWqmCount' },
          // },
          // {
          //   title:'订单号',
          //   align: 'left',
          //   dataIndex: 'imCusCode'
          // },
          // {
          //   title:'托盘',
          //   align: 'left',
          //   dataIndex: 'tinId',
          //   scopedSlots: { customRender: 'tinId' },
          // }

        ],
        url: {
          list: "/jeewms/wmImNoticeI/ysList",
          delete: "/jeewms/wmImNoticeI/delete",
          deleteBatch: "/jeewms/wmImNoticeI/deleteBatch",
          exportXlsUrl: "/jeewms/wmImNoticeI/exportXls",
          importExcelUrl: "jeewms/wmImNoticeI/importExcel",
          batchAdd: "/jeewms/wmInQmI/batchAdd",
        },
        dictOptions:{},
        //批量修改标记
        batchEditFlag: false,
        //批量修改时新值数据
        newColData: [],
        //批量修改时数据修改行的id集合
        newColIds: [],
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      },
      batchEdit() {
        if(this.selectedRowKeys.length > 0) {
          this.batchEditFlag = true
        } else {
          this.$message.warning(this.$t('请选择一条记录!'));
        }
      },
      //可编辑行改变时调用
      handleBatchChange(newValue, id, col) {
        if(newValue && newValue._isAMomentObject) {
          newValue = moment(newValue).format('YYYY-MM-DD');
        }
        var newSingleData = {};
        if(this.newColIds.indexOf(id) != -1) {
          this.newColData.forEach((item,index) => {
            if(item.id == id) {
              this.newColData[index][col] = newValue
            }
          })
        } else {
          this.newColIds.push(id)
          newSingleData[col] = newValue;
          newSingleData.id = id;
          this.newColData.push(newSingleData)
        }
        console.log(this.newColData,this.newColIds)
      },
      //批量保存
      batchSave() {
        var that = this
        if(this.batchEditFlag) {
          postAction(that.url.batchAdd,that.newColData).then((res) => {
            if (res.success) {
              that.$message.success('批量修改成功')
              that.newColData = [];
              that.newColIds = [];
              that.loadData();
              that.batchEditFlag = false;
            } else {
              that.$message.warning(this.$t('操作失败'))
            }
          })
        } else {
          this.$message.warning('无可保存内容!');
        }
      },
      //取消批量修改
      batchCancel() {
        if(this.batchEditFlag) {
          this.newColData = [];
          this.newColIds = [];
          this.batchEditFlag = false;
          this.selectedRowKeys = [];
        } else {
          this.$message.warning('无修改内容可取消!');
        }
      },
      openAccept(id) {
        this.showModal = true;
        this.aloneAcceptId = id;
      },
      accept() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            formData.id = that.aloneAcceptId;
            let param = [];
            param.push(formData);
            console.log("表单提交数据",formData)
            postAction(this.url.batchAdd,param).then((res)=>{
              if(res.success){
                that.$message.success(this.$t('操作成功'));
                that.showModal = false;
                that.aloneAcceptId = '';
                that.model = {};
                this.$nextTick(() => {
                  this.form.setFieldsValue({
                    "goodsPrdData" : '',
                    "goodsBatch" : '',
                    "binPlan" : '',
                    "goodsWqmCount" : '',
                    "tinId" : '',
                  })
                })
              }else{
                that.$message.warning(this.$t('操作失败'));
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.showModal = false;
              that.aloneAcceptId = '';
              that.model = {};
              that.loadData();
              this.$nextTick(() => {
                this.form.setFieldsValue({
                  "goodsPrdData" : '',
                  "goodsBatch" : '',
                  "binPlan" : '',
                  "goodsWqmCount" : '',
                  "tinId" : '',
                })
              })
            })
          }

        })
      },
      handleCancel() {
        this.showModal = false;
        this.aloneAcceptId = '';
        this.model = {};
        this.$nextTick(() => {
          this.form.setFieldsValue({
            "goodsPrdData" : '',
            "goodsBatch" : '',
            "binPlan" : '',
            "goodsWqmCount" : '',
            "tinId" : '',
          })
        })
      }


    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>