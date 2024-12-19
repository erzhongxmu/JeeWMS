<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="货主">
              <j-dict-select-tag placeholder="请选择货主" v-model="queryParam.cusCode" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma"/>
            </a-form-item>
          </a-col>
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item label="要求交货时间">
              <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.delvData_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.delvData_end"></j-date>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="收货人">
                <a-input placeholder="请输入收货人" v-model="queryParam.delvMember"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="收货人电话">
                <a-input placeholder="请输入收货人电话" v-model="queryParam.delvMobile"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="承运人">
                <a-input placeholder="请输入承运人" v-model="queryParam.reMember"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="承运人电话">
                <a-input placeholder="请输入承运人电话" v-model="queryParam.reMobile"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="承运人车号">
                <a-input placeholder="请输入承运人车号" v-model="queryParam.reCarno"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="发货月台">
                <j-dict-select-tag placeholder="请选择发货月台" v-model="queryParam.omPlatNo" dictCode="ba_platform,platform_name,platform_code"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="状态">
                <a-input placeholder="请输入状态" v-model="queryParam.omSta"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="10" :lg="11" :md="12" :sm="24">
              <a-form-item label="出货单号">
                <a-input placeholder="请输入最小值" class="query-group-cust" v-model="queryParam.omNoticeId_begin"></a-input>
                <span class="query-group-split-cust"></span>
                <a-input placeholder="请输入最大值" class="query-group-cust" v-model="queryParam.omNoticeId_end"></a-input>
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
<!--      <a-button icon="download" @click="handleExportXls('批量回单')">{{$t('导出')}}</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
<!--      </a-upload>-->
<!--        <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>-->
        <a-button key="3" @click="batchEdit"><a-icon type="edit"/>批量回单</a-button>
        <a-button key="4" @click="batchSave"><a-icon type="save"/>批量保存</a-button>
        <a-button key="5" @click="batchCancel"><a-icon type="stop"/>取消批量回单</a-button>
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
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
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
        <template slot="omBeizhu"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'omBeizhu',record)"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="delvData"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-date-picker
              @change="e => handleBatchChange(e, record.id, 'delvData')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>

<!--        <span slot="action" slot-scope="text, record">-->
<!--          <a @click="handleCheck(record)">{{$t('查看')}}</a>-->
<!--          <a-divider type="vertical"/>-->
<!--          <a @click="handleEdit(record)">{{$t('编辑')}}</a>-->

<!--          <a-divider type="vertical" />-->
<!--           <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">-->
<!--                  <a>{{$t('删除')}}</a>-->
<!--                </a-popconfirm>-->
<!--        </span>-->

      </a-table>
    </div>

    <wmOmNoticeHReceipt-modal ref="modalForm" @ok="modalFormOk"></wmOmNoticeHReceipt-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmOmNoticeHReceiptModal from './modules/WmOmNoticeHReceiptModal'
  import { putAction } from '@/api/manage'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JDate from '@/components/jeecg/JDate.vue'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'
  //设置中文
  import moment from 'moment';


  export default {
    name: "WmOmNoticeHReceiptList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
    components: {
      WmOmNoticeHReceiptModal,
      JDictSelectTag,
      JDate
    },
    data () {
      return {
        description: '批量回单管理页面',
        // 表头
        columns: [
          // {
          //   title: this.$t('操作'),
          //   dataIndex: 'action',
          //   align: 'left',
          //   // fixed:"right",
          //   width:147,
          //   scopedSlots: { customRender: 'action' }
          // },
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
            title:'创建人名称',
            align: 'left',
            dataIndex: 'createName'
          },
          {
            title:'创建日期',
            align: 'left',
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          // {
          //   title:'所属部门',
          //   align: 'left',
          //   dataIndex: 'sysOrgCode'
          // },
          // {
          //   title:'所属公司',
          //   align: 'left',
          //   dataIndex: 'sysCompanyCode'
          // },
          {
            title:'货主',
            align: 'left',
            dataIndex: 'cusCode_dictText'
          },
          {
            title:'要求交货时间',
            align: 'left',
            dataIndex: 'delvData',
            scopedSlots: { customRender: 'delvData'},
            // customRender:function (text) {
            //   return !text?"":(text.length>10?text.substr(0,10):text)
            // }
          },
          {
            title:'备注',
            align: 'left',
            dataIndex: 'omBeizhu',
            scopedSlots: { customRender: 'omBeizhu' },
          },
          {
            title:'收货人',
            align: 'left',
            dataIndex: 'delvMember'
          },
          {
            title:'收货人电话',
            align: 'left',
            dataIndex: 'delvMobile'
          },
          {
            title:'收货人地址',
            align: 'left',
            dataIndex: 'delvAddr'
          },
          // {
          //   title:'承运人',
          //   align: 'left',
          //   dataIndex: 'reMember'
          // },
          // {
          //   title:'承运人电话',
          //   align: 'left',
          //   dataIndex: 'reMobile'
          // },
          // {
          //   title:'承运人车号',
          //   align: 'left',
          //   dataIndex: 'reCarno'
          // },
          // {
          //   title:'发货月台',
          //   align: 'left',
          //   dataIndex: 'omPlatNo_dictText'
          // },
          {
            title:'状态',
            align: 'left',
            dataIndex: 'omSta'
          },
          {
            title:'出货单号',
            align: 'left',
            dataIndex: 'omNoticeId'
          },
          // {
          //   title:'附件',
          //   align: 'left',
          //   dataIndex: 'fuJian'
          // },
          // {
          //   title:'所属部门',
          //   align: 'left',
          //   dataIndex: 'readOnly'
          // },
          // {
          //   title:'订单类型',
          //   align: 'left',
          //   dataIndex: 'orderTypeCode'
          // },
          // {
          //   title:'三方货主',
          //   align: 'left',
          //   dataIndex: 'ocusCode'
          // },
          // {
          //   title:'三方货主名称',
          //   align: 'left',
          //   dataIndex: 'ocusName'
          // },
          {
            title:'订单号',
            align: 'left',
            dataIndex: 'imCusCode'
          },
          {
            title:'打印状态',
            align: 'left',
            dataIndex: 'printStatus'
          },
          // {
          //   title:'对接单据类型',
          //   align: 'left',
          //   dataIndex: 'piClass'
          // },
          // {
          //   title:'账套',
          //   align: 'left',
          //   dataIndex: 'piMaster'
          // }

        ],
        url: {
          list: "/jeewms/wmOmNoticeH/datagridbatchconf",
          delete: "/jeewms/wmOmNoticeH/delete",
          deleteBatch: "/jeewms/wmOmNoticeH/deleteBatch",
          exportXlsUrl: "/jeewms/wmOmNoticeH/exportXls",
          importExcelUrl: "jeewms/wmOmNoticeH/importExcel",
          editBatchReceipt: "/jeewms/wmOmNoticeH/editBatchReceipt",
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
      },
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
          putAction(that.url.editBatchReceipt,that.newColData).then((res) => {
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
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>