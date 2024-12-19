<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('出库单号')" >
              <a-input :placeholder="$t('请输入')" v-model="queryParam.omNoticeId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品名称')" >
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsName"></a-input>
            </a-form-item>
          </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('备注')" >
                <a-input :placeholder="$t('请输入')" v-model="queryParam.omBeiZhu"></a-input>
              </a-form-item>
            </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--      <a-button icon="download" @click="handleExportXls('出货详情')">{{$t('导出')}}</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
<!--      </a-upload>-->
<!--      <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>-->
      <a-button key="6" @click="batchEdit" v-has="'omNotifyProject:batchUpdate'"><a-icon type="edit"/>{{$t('批量修改')}}</a-button>
      <a-button key="7" @click="batchSave" v-has="'omNotifyProject:batchUpdate'"><a-icon type="save"/>{{$t('批量保存')}}</a-button>
      <a-button key="8" @click="batchCancel" v-has="'omNotifyProject:batchUpdate'"><a-icon type="stop"/>{{$t('取消批量修改')}}</a-button>
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

        <template slot="goodsQua"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'goodsQua')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="binId"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'binId')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="baseGoodscount"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'baseGoodscount')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="planSta"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'planSta')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="goodsProData"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-date-picker
              @change="e => handleBatchChange(e, record.id, 'goodsProData')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>

<!--        <span slot="action" slot-scope="text, record">-->
<!--          <a @click="handleCheck(record)">{{$t('查看')}}</a>-->
<!--          <a-divider type="vertical"/>-->
<!--          <a @click="handleEdit(record)">{{$t('编辑')}}</a>-->
<!--          <a-divider type="vertical" />-->
<!--          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">-->
<!--            <a>{{$t('删除')}}</a>-->
<!--          </a-popconfirm>-->

<!--        </span>-->

      </a-table>
    </div>

    <wmOmNotifyProject-modal ref="modalForm" @ok="modalFormOk"></wmOmNotifyProject-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { putAction } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmOmNotifyProjectModal from './modules/WmOmNotifyProjectModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import moment from 'moment'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'

  export default {
    name: "WmOmNoticeIList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
    components: {
      WmOmNotifyProjectModal
    },
    data () {
      return {
        description: '出货详情管理页面',
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
            title:this.$t('创建人'),
            align: 'left',
            dataIndex: 'createBy'
          },
          {
            title:this.$t('创建日期'),
            align: 'left',
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:this.$t('出库单号'),
            align: 'left',
            dataIndex: 'omNoticeId'
          },
          {
            title:this.$t('商品编码'),
            align: 'left',
            dataIndex: 'goodsId'
          },
          {
            title:this.$t('单位'),
            align: 'left',
            dataIndex: 'goodsUnit'
          },
          {
            title:this.$t('数量'),
            align: 'left',
            dataIndex: 'goodsQua',
            // scopedSlots: { customRender: 'goodsQua' },
          },
          {
            title:this.$t('生产日期'),
            align: 'left',
            dataIndex: 'goodsProData',
          },
          // {
          //   title:this.$t('基本单位数量'),
          //   align: 'left',
          //   dataIndex: 'baseGoodscount',
          // },
          {
            title:this.$t('托盘'),
            align: 'left',
            dataIndex: 'binId',
            // scopedSlots: { customRender: 'binId' },
          },
          {
            title:this.$t('待出库数量'),
            align: 'left',
            dataIndex: 'waitQua',
          },
          // {
          //   title:this.$t('下架计划生成'),
          //   align: 'left',
          //   dataIndex: 'planSta',
          //   scopedSlots: { customRender: 'planSta' },
          //   sorter: true
          // },
          {
            title:this.$t('批次'),
            align: 'left',
            dataIndex: 'goodsBatch'
          },
          // {
          //   title:this.$t('出货仓位'),
          //   align: 'left',
          //   dataIndex: 'binOm'
          // },
          {
            title:this.$t('已出货数量'),
            align: 'left',
            dataIndex: 'goodsQuaok'
          },
          // {
          //   title:this.$t('预约出货时间'),
          //   align: 'left',
          //   dataIndex: 'delvData'
          // },
          // {
          //   title:this.$t('货主'),
          //   align: 'left',
          //   dataIndex: 'cusCode_dictText'
          // },
          // {
          //   title:this.$t('商品名称'),
          //   align: 'left',
          //   dataIndex: 'goodsText'
          // },
          // {
          //   title:this.$t('波次号'),
          //   align: 'left',
          //   dataIndex: 'waveId'
          // },
          // {
          //   title:this.$t('状态'),
          //   align: 'left',
          //   dataIndex: 'omSta'
          // },
          // {
          //   title:this.$t('基本单位'),
          //   align: 'left',
          //   dataIndex: 'baseUnit'
          // },
          {
            title:this.$t('商品名称'),
            align: 'left',
            dataIndex: 'goodsName'
          },
          // {
          //   title:this.$t('其他系统ID'),
          //   align: 'left',
          //   dataIndex: 'otherId'
          // },
          // {
          //   title:this.$t('出货通知单'),
          //   align: 'left',
          //   dataIndex: 'omNoticeId',
          // },
          // {
          //   title:this.$t('货主单号'),
          //   align: 'left',
          //   dataIndex: 'imCusCode'
          // },
          // {
          //   title:this.$t('产品属性'),
          //   align: 'left',
          //   dataIndex: 'chpShuXing'
          // },
          // {
          //   title:this.$t('商品条码'),
          //   align: 'left',
          //   dataIndex: 'barcode'
          // }
          {
            title:this.$t('备注'),
            align: 'left',
            dataIndex: 'omBeiZhu'
          },

        ],
        url: {
          list: "/jeewms/wmOmNoticeI/list",
          delete: "/jeewms/wmOmNoticeI/delete",
          deleteBatch: "/jeewms/wmOmNoticeI/deleteBatch",
          exportXlsUrl: "/jeewms/wmOmNoticeI/exportXls",
          importExcelUrl: "jeewms/wmOmNoticeI/importExcel",
          editWmOmNoticeIList: "/jeewms/wmOmNoticeH/editWmOmNoticeIList",
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
          putAction(that.url.editWmOmNoticeIList,that.newColData).then((res) => {
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
          this.$message.warning('无可取消修改内容!');
        }
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>