<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品编码">
              <a-input placeholder="请输入商品编码" v-model="queryParam.goodsId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品名称">
              <a-input placeholder="请输入商品名称" v-model="queryParam.goodsName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="源托盘">
                <a-input placeholder="请输入源托盘" v-model="queryParam.binIdFrom"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主编码">
                <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,ke_hu_bian_ma,ke_hu_bian_ma" placeholder="请选择货主编码"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主名称">
                <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" placeholder="请选择货主名称"/>
              </a-form-item>
            </a-col>
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="货主名称">-->
<!--                <a-input placeholder="请输入货主名称" v-model="queryParam.cusCode_dictText"></a-input>-->
<!--              </a-form-item>-->
<!--            </a-col>-->

            <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
            <!--              <a-form-item label="创建日期">-->
            <!--                <j-date placeholder="请选择创建日期" v-model="queryParam.createTime"/>-->
            <!--              </a-form-item>-->
            <!--            </a-col>-->
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button  @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
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
      <a-button icon="download" @click="handleExportXls('下架调整')" v-has="'toDownGoods:export'">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" v-has="'toDownGoods:import'" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel" v-has="'toDownGoods:batchDelete'"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>
      <a-button @click="batchEdit" v-has="'toDownGoods:batchUpdate'">批量修改</a-button>
      <a-button @click="batchSave" v-has="'toDownGoods:batchUpdate'">批量保存</a-button>
      <a-button @click="batchCancel" v-has="'toDownGoods:batchUpdate'">批量取消修改</a-button>
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
<!--          <a-menu-item key="6" @click="batchEdit"><a-icon type="edit"/>修改</a-menu-item>-->
<!--          <a-menu-item key="7" @click="batchSave"><a-icon type="save"/>保存</a-menu-item>-->
<!--          <a-menu-item key="8" @click="batchCancel"><a-icon type="stop"/>取消修改</a-menu-item>-->
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
        <template slot="goodsProData"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-date-picker
              @change="e => handleBatchChange(e, record.id, 'goodsProData')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="goodsQua"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'goodsQua')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="goodsQuaok"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'goodsQuaok')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="baseUnit"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'baseUnit')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="kuWeiBianMa"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'kuWeiBianMa')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="binIdFrom"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'binIdFrom')"/>
          </div>
          <div v-else>{{text}}</div>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)" v-has="'toDownGoods:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'toDownGoods:check'"/>
          <a @click="handleEdit(record)" v-has="'toDownGoods:update'">{{$t('编辑')}}</a>

          <a-divider type="vertical"  v-has="'toDownGoods:update'"/>
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)" v-has="'toDownGoods:delete'">
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>

    <wmToDownGoods-modal ref="modalForm" @ok="modalFormOk"></wmToDownGoods-modal>
    <export-spin v-if="exportOk"></export-spin>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { putAction } from '@/api/manage'
  import { postAction } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmToDownGoodsModal from './modules/WmToDownGoodsModal'
  import moment from 'moment'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'
  import ExportSpin from '@/components/ExportSpin/ExportSpin'
  import JSearchSelectTag from '@comp/dict/JSearchSelectTag'

  export default {
    name: "WmToDownGoodsList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
    components: {
      WmToDownGoodsModal,
      ExportSpin,
      JSearchSelectTag
    },
    data () {
      return {
        description: 'wm_to_down_goods管理页面',
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            // fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
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
            title:'商品编码',
            align: 'left',
            dataIndex: 'goodsId'
          },
          {
            title:'商品名称',
            align: 'left',
            dataIndex: 'goodsName'
          },
          {
            title:'数量',
            align: 'left',
            dataIndex: 'goodsQua',
            scopedSlots: { customRender: 'goodsQua' },
          },
          {
            title:'完成数量',
            align: 'left',
            dataIndex: 'goodsQuaok',
            scopedSlots: { customRender: 'goodsQuaok' },
          },
          {
            title:'单位',
            align: 'left',
            dataIndex: 'goodsUnit'
          },
          {
            title:'原始单据编码',
            align: 'left',
            dataIndex: 'orderId'
          },
          {
            title:'生产日期',
            align: 'left',
            dataIndex: 'goodsProData',
            scopedSlots: { customRender: 'goodsProData' },
          },
          {
            title:'批号',
            align: 'left',
            dataIndex: 'goodsBatch'
          },
          {
            title:'库位编码',
            align: 'left',
            dataIndex: 'kuWeiBianMa',
            scopedSlots: { customRender: 'kuWeiBianMa' },
          },
          {
            title:'源托盘',
            align: 'left',
            dataIndex: 'binIdFrom',
            scopedSlots: { customRender: 'binIdFrom' },
          },
          {
            title:'货主名称',
            align: 'left',
            dataIndex: 'cusCode_dictText'
          },
          {
            title:'状态',
            align: 'left',
            dataIndex: 'downSta'
          },
          {
            title:'基本单位',
            align: 'left',
            dataIndex: 'baseUnit',
            scopedSlots: { customRender: 'baseUnit' },
          },
          {
            title:'基本单位数量',
            align: 'left',
            dataIndex: 'baseGoodscount'
          }

        ],
        url: {
          list: "/jeewms/wmToDownGoods/list",
          delete: "/jeewms/wmToDownGoods/delete",
          deleteBatch: "/jeewms/wmToDownGoods/deleteBatch",
          exportXlsUrl: "/jeewms/wmToDownGoods/exportXls",
          importExcelUrl: "jeewms/wmToDownGoods/importExcel",
          updateBatch: "/jeewms/wmToDownGoods/updateBatch",
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
          postAction(that.url.updateBatch, that.newColData).then((res) => {
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
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>