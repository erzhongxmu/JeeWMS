<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('主PO号')">
              <a-input :placeholder="$t('请输入主PO号')" v-model="queryParam.query13"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('采购人')">
              <a-input placeholder="请输入采购人" v-model="queryParam.query16"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户')">
              <j-popup
                v-model="queryParam.query24"
                field="query08"
                org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                dest-fields="query09,query08"
                code="md_cus"
                :multi="false"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('供应商')">
              <j-popup
                v-model="queryParam.query08"
                field="supplyCode"
                org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch"
                dest-fields="supplyCode,supplyName,supplyNick"
                code="md_sup"
                :multi="false"
              />
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('销售单号')">
              <a-input :placeholder="$t('请输入销售单号')" v-model="queryParam.link03"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('内部发票号')">
              <a-input :placeholder="$t('请输入内部发票号')" v-model="queryParam.query17"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('企业属性')">
              <a-input placeholder="请输入企业属性" v-model="queryParam.xingYeFenLei"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('业务员')">
              <a-input placeholder="请输入业务员" v-model="queryParam.query30"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('状态')">
              <a-input :placeholder="$t('请输入状态')" v-model="queryParam.query02"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('备注')">
              <a-input :placeholder="$t('请输入备注')" v-model="queryParam.text01"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl='9' :lg='14' :md='12' :sm='24'>
            <a-form-item :label="$t('创建日期')">
              <j-date
                :placeholder="$t('请选择开始日期')"
                class='query-group-cust'
                dateFormat='YYYY-MM-DD HH:mm:ss'
                v-model='queryParam.createTime_begin'
                 style="width: 40%"
              ></j-date>
              <span class='query-group-split-cust'></span>
              <j-date
                :placeholder="$t('请选择结束日期')"
                class='query-group-cust'
                dateFormat='YYYY-MM-DD HH:mm:ss'
                v-model='queryParam.createTime_end'
                 style="width: 40%"
              ></j-date>
            </a-form-item>
          </a-col>
          
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" v-has="'SampleMakingOrder:search'">{{
                $t('查询')
              }}</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
                v-has="'SampleMakingOrder:reset'"
                >{{ $t('重置') }}</a-button
              >
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'BusiPoList:add'">{{ $t('新增') }}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('采购订单')" v-has="'BusiPoList:export'">{{
        $t('导出')
      }}</a-button>
      <a-button type="primary" icon="download" @click="handleExportDetailXls('导出详情')" v-has="'BusiPoList:export'">{{
        $t('导出详情')
      }}</a-button>
      <a-button
        type="primary"
        icon="download"
        @click="handleExportXls2('采购订单模板')"
        v-has="'SampleMakingOrder:export'"
        >{{ $t('下载导入模板') }}</a-button
      >
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import" v-has="'BusiPoList:import'">{{ $t('导入') }}</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fsieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->

      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel" v-has="'BusiPoList:batchDelete'"><a-icon type="delete" />删除</a-menu-item>
          <a-menu-item key="2" @click="handleCopy(selectedRowKeys)"><a-icon type="copy" />复制</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
      <a-button @click="handleCopy(selectedRowKeys)" type="primary" icon="copy" v-if="selectedRowKeys.length == 1">{{
        $t('复制')
      }}</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{ $t('已选择') }}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >{{ $t('项目') }}
        <a style="margin-left: 24px" @click="onClearSelected">{{ $t('清空') }}</a>
      </div>

      <a-tabs @change="tabChange" defaultActiveKey="未确认">
        <a-tab-pane :tab="$t('未确认')" key="未确认">
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            class="j-table-force-nowrap"
            :scroll="{ x: true }"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :loading="loading"
            :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
            @change="handleTableChange"
          >
            <template slot="htmlSlot" slot-scope="text">
              <div v-html="text"></div>
            </template>
            <template slot="imgSlot" slot-scope="text">
              <span v-if="!text" style="font-size: 12px; font-style: italic">{{ $t('无图片') }}</span>
              <img
                v-else
                :src="getImgView(text)"
                height="25px"
                alt=""
                style="max-width: 80px; font-size: 12px; font-style: italic"
              />
            </template>
            <template slot="fileSlot" slot-scope="text">
              <span v-if="!text" style="font-size: 12px; font-style: italic">{{ $t('无文件') }}</span>
              <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
                {{ $t('下载') }}
              </a-button>
            </template>
            <template slot="previewImg" slot-scope="text">
              <span v-if="!text || text == ''" style="font-size: 12px; font-style: italic">无文件</span>
              <a-button v-else :ghost="true" type="primary" icon="eye" size="small" @click="previewImg(text)">
                {{ $t('预览') }}
              </a-button>
            </template>

            <span slot="action" slot-scope="text, record">
              <!-- 单据未完成显示 -->
              <!-- <span v-if="record.query02 != '已完成'">
            <a @click="PoFinish(record)">完成</a>
            <a-divider type="vertical" />
          </span> -->
              <a @click="handleEdit(record)" v-has="'BusiPoList:edit'">{{ $t('编辑') }}</a>
              <a-divider type="vertical" v-has="'BusiPoList:edit'" />
              <a-divider type="vertical" />

              <a-popconfirm :title="$t('是否确认?')" @confirm="() => handleEditquery37(record)">
                <a v-has="'BusiPoList:Editquery37'">{{ $t('确认') }}</a>
              </a-popconfirm>
              <a-divider type="vertical" v-has="'BusiPoList:Editquery37'" />
              <a-dropdown>
                <a class="ant-dropdown-link">{{ $t('更多') }} <a-icon type="down"/></a>
                <a-menu slot="overlay">
                  <a-menu-item v-has="'BusiPoList:detail'">
                    <a @click="handleDetail(record)">{{ $t('详情') }}</a>
                  </a-menu-item>
                  <a-menu-item v-has="'BusiPoList:delete'">
                    <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
                      <a>{{ $t('删除') }}</a>
                    </a-popconfirm>
                  </a-menu-item>
                  <a-menu-item v-has="'BusiPoList:delete'">
                    <a-popconfirm :title="$t('确定取消订单吗?')" @confirm="() => handleDelete2(record)">
                      <a>{{ $t('取消订单') }}</a>
                    </a-popconfirm>
                  </a-menu-item>
                  <a-menu-item>
                    <a @click="Downloadproof(record)">{{ $t('下载水单') }}</a>
                  </a-menu-item>
                </a-menu>
              </a-dropdown>
            </span>
          </a-table>
        </a-tab-pane>
        <a-tab-pane :tab="$t('已确认')" key="已确认">
          <a-table
            ref="table"
            size="middle"
            bordered
            rowKey="id"
            class="j-table-force-nowrap"
            :scroll="{ x: true }"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :loading="loading"
            :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
            @change="handleTableChange"
          >
            <template slot="htmlSlot" slot-scope="text">
              <div v-html="text"></div>
            </template>
            <template slot="imgSlot" slot-scope="text">
              <span v-if="!text" style="font-size: 12px; font-style: italic">{{ $t('无图片') }}</span>
              <img
                v-else
                :src="getImgView(text)"
                height="25px"
                alt=""
                style="max-width: 80px; font-size: 12px; font-style: italic"
              />
            </template>
            <template slot="fileSlot" slot-scope="text">
              <span v-if="!text" style="font-size: 12px; font-style: italic">{{ $t('无文件') }}</span>
              <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
                {{ $t('下载') }}
              </a-button>
            </template>
            <template slot="previewImg" slot-scope="text">
              <span v-if="!text || text == ''" style="font-size: 12px; font-style: italic">{{ $t('无文件') }}</span>
              <a-button v-else :ghost="true" type="primary" icon="eye" size="small" @click="previewImg(text)">
                {{ $t('预览') }}
              </a-button>
            </template>

            <span slot="action" slot-scope="text, record">
              <!-- 单据未完成显示 -->
              <span v-if="record.query02 != '已完成'" v-has="'BusiPoList:Finish'">
                <a @click="PoFinish(record)">{{ $t('完成') }}</a>
                <a-divider type="vertical" />
              </span>
              <a @click="handleEdit(record)" v-has="'BusiPoList:edit2'">{{ $t('编辑') }}</a>
              <a-divider type="vertical" v-has="'BusiPoList:edit2'" />
              <a-dropdown>
                <a class="ant-dropdown-link">{{ $t('导出') }}<a-icon type="down"/></a>
                <a-menu slot="overlay">
                  <a-menu-item v-show="record.query25 == '是'" v-has="'BusiPoList:CGHS'">
                    <a @click="purchaseContract1(record, 'CGHS')">{{ $t('大货（含税）') }}</a>
                  </a-menu-item>
                  <a-menu-item v-show="record.query25 == '是'" v-has="'BusiPoList:CGHSPDF'">
                    <a @click="PoContractPDF(record, 'CGHS')">{{ $t('大货（含税）PDF') }}</a>
                  </a-menu-item>
                  <a-menu-item v-show="record.query25 == '否'" v-has="'BusiPoList:CGBHS'">
                    <a @click="purchaseContract2(record, 'CGBHS')">{{ $t('大货（不含税）') }}</a>
                  </a-menu-item>
                  <a-menu-item v-show="record.query25 == '否'" v-has="'BusiPoList:CGBHSPDF'">
                    <a @click="PoContractPDF(record, 'CGBHS')">{{ $t('大货（不含税）PDF') }}</a>
                  </a-menu-item>
                  <a-menu-item v-has="'BusiPoList:CGFKD'">
                    <a @click="salesContract(record, 'FKD')">{{ $t('付款单') }}</a>
                  </a-menu-item>
                  <a-menu-item v-has="'BusiPoList:CGFKDPDF'">
                    <a @click="SalesContractPdf(record, 'FKD')">{{ $t('付款单PDF') }}</a>
                  </a-menu-item>
                </a-menu>
              </a-dropdown>
              <!-- <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">收付款 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="paymentSchedule(record)" v-has="'BusiPoList:paymentSchedule'">付款计划</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="collectionSchedule(record)">收款计划</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="paymentSchedule2(record)">形式发票</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown> -->
              <a-divider type="vertical" />
              <a-dropdown>
                <a class="ant-dropdown-link">{{ $t('出入库') }} <a-icon type="down"/></a>
                <a-menu slot="overlay">
                  <a-menu-item>
                    <a @click="pushWMS(record)" v-has="'BusiPoList:pushWMS'">{{ $t('推送入库') }}</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a @click="pushWMSOm(record)" v-has="'BusiPoList:pushWMS'">{{ $t('推送出库') }}</a>
                  </a-menu-item>
                </a-menu>
              </a-dropdown>
              <a-divider type="vertical" />
              <a-dropdown>
                <a class="ant-dropdown-link">{{ $t('更多') }} <a-icon type="down"/></a>
                <a-menu slot="overlay">
                  <a-menu-item v-has="'BusiPoList:detail'">
                    <a @click="handleDetail(record)">{{ $t('详情') }}</a>
                  </a-menu-item>
                  <a-menu-item v-has="'BusiPoList:delete'">
                    <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
                      <a>{{ $t('删除') }}</a>
                    </a-popconfirm>
                  </a-menu-item>
                  <a-menu-item v-has="'BusiPoList:delete'">
                    <a-popconfirm :title="$t('确定取消订单吗?')" @confirm="() => handleDelete2(record)">
                      <a>{{ $t('取消订单') }}</a>
                    </a-popconfirm>
                  </a-menu-item>
                  <a-menu-item v-show="record.query25 == '是'">
                    <a @click="handleUpload2(record)">{{ $t('上传发票') }}</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a @click="QualityInspectionSchedule(record)">{{ $t('排班') }}</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a @click="exportList(record)">{{ $t('导出检验单') }}</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a @click="handleUpload(record)">{{ $t('上传质检凭证') }}</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a @click="Downloadproof(record)">{{ $t('下载水单') }}</a>
                  </a-menu-item>
                </a-menu>
              </a-dropdown>
            </span>
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </div>

    <busi-po-modal ref="modalForm" @ok="modalFormOk" />
    <busi-po-push-modal ref="modalForm2" @ok="modalFormOk" />
    <uploadFile ref="uploadFile" @ok="modalFormOk" />
    <uploadFile2 ref="uploadFile2" @ok="modalFormOk" />
    <previewFile ref="previewFile" />
    <PoFinish ref="PoFinish" @ok="modalFormOk" />
    <!-- <BusiPosheetModal ref="BusiPosheetModal" @ok="modalFormOk" /> -->
    <SalesContractPdf ref="SalesContractPdf" />
    <updateTimeLimit2 ref="updateTimeLimit2" @ok="modalFormOk" />
    <PoContract ref="PoContract" @ok="modalFormOk" />
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BusiPoModal from './modules/BusiPoModal'
// import BusiPosheetModal from './modules/BusiPosheet'
import BusiPoPushModal from './modules/BusiPoPushModal'
import updateTimeLimit2 from './modules/updateTimeLimit2'
import PoFinish from './modules/PoFinish'
import '@/assets/less/TableExpand.less'
import { getAction, httpAction, postAction, downFile } from '@/api/manage'
import uploadFile from '@/components/uploadFile/uploadFile.vue'
import uploadFile2 from '@/components/uploadFile/uploadFile2.vue'
import previewFile from '@/components/previewFile/previewFile.vue'
import { exportFile } from '@/utils/PLTN_EXCEL_OMS/purchaseContract1.js'
import { exportFile2 } from '@/utils/PLTN_EXCEL_OMS/purchaseContract2.js'
import { exportSalesContract } from '@/utils/PLTN_EXCEL_OMS/salesContract.js'
import SalesContractPdf from '@/views/erp/modules/SalesContractPdf.vue'
import PoContract from '@/views/erp/modules/PoContract'
import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
import { BusiPoList, BusiPoDetailList } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
import { INSPECTION_SHEET } from '@/utils/PLTN_EXCEL/index' // WMS导出检验单，包装领料单
import store from '@/store/'
import { OUT_PUT} from '@/utils/util'
export default {
  name: 'BusiPoList',
  mixins: [JeecgListMixin],
  components: {
    // BusiPosheetModal,
    BusiPoModal,
    BusiPoPushModal,
    PoContract,
    uploadFile,
    uploadFile2,
    PoFinish,
    previewFile,
    updateTimeLimit2,
    SalesContractPdf
  },
  data() {
    return {
      query100:"",
      description: 'busi_po管理页面',
      imStatus: '未确认',
      // 表头
      columns: [
        {
          title: this.$t('主PO'),
          sorter: true,
          align: 'center',
          dataIndex: 'query13'
        },
        {
          title: this.$t('客户'),
          align: 'center',
          sorter: true,
          dataIndex: 'query24_dictText'
        },
        {
          title: this.$t('企业属性'),
          align: 'center',
          sorter: true,
          dataIndex: 'xingYeFenLei'
        },
        // {
        //   title: this.$t('供应商编码'),
        //   align: 'center',
        //   dataIndex: 'query08',
        // },
        {
          title: this.$t('制造商'),
          align: 'center',
          sorter: true,
          dataIndex: 'query08'
        },
        {
          title: this.$t('供应商'),
          align: 'center',
          sorter: true,
          dataIndex: 'query41'
        },
        {
          title: this.$t('状态'),
          align: 'center',
          sorter: true,
          dataIndex: 'query02',
          customRender: text => {
            return text ? this.$t(text) : text
          }
        },
        // {
        //   title: this.$t('建单日期'),
        //   align: 'center',
        //   dataIndex: 'query03',
        // },
        {
          title: this.$t('预计到货时间'),
          align: 'center',
          sorter: true,
          dataIndex: 'query21'
        },
        {
          title: this.$t('业务类型'),
          align: 'center',
          sorter: true,
          dataIndex: 'query31'
        },
        // {
        //   title: this.$t('采购单号'),
        //   align: 'center',
        //   dataIndex: 'query04',
        // },
        {
          title: this.$t('销售单号'),
          align: 'center',
          sorter: true,
          dataIndex: 'link03',
          width: 100,
          checked: true,
          customCell: () => {
            return {
              style: {
                width: '100px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer'
              }
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}
            </a-tooltip>
          )
        },
        {
          title: this.$t('内部发票号'),
          align: 'center',
          sorter: true,
          dataIndex: 'query17',
          width: 100,
          checked: true,
          customCell: () => {
            return {
              style: {
                width: '100px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer'
              }
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}
            </a-tooltip>
          )
        },
        {
          title: this.$t('采购人'),
          align: 'center',
          sorter: true,
          dataIndex: 'query16'
        },
        {
          title: this.$t('含税总金额'),
          align: 'center',
          sorter: true,
          dataIndex: 'num09',
          NodePermission: 'BusiPoList:num09',
          customRender: (text) => {
            return text?OUT_PUT(text):text
          }
        },
        {
          title: this.$t('未付总金额'),
          align: 'center',
          dataIndex: 'remainingSum',
          sorter: true,
          NodePermission: 'SampleMakingOrder:num09',
          customRender: (text) => {
            return text?OUT_PUT(text):text
          }
        },
        {
          title: this.$t('币种'),
          align: 'center',
          sorter: true,
          dataIndex: 'query22'
        },
        {
          title: this.$t('税点'),
          align: 'center',
          sorter: true,
          dataIndex: 'num10',
          customRender: text => {
            if (text) {
              return text + '%'
            } else {
              return 0
            }
          }
        },
        {
          title: this.$t('业务员'),
          align: 'center',
          sorter: true,
          dataIndex: 'query30'
        },
        {
          title: this.$t('WMS下架操作人'),
          align: 'center',
          sorter: true,
          dataIndex: 'query39'
        },
        {
          title: this.$t('INV'),
          align: 'center',
          dataIndex: 'query36',
          sorter: true,
          width: 100,
          checked: true,
          customCell: () => {
            return {
              style: {
                width: '100px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer'
              }
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}
            </a-tooltip>
          )
        },
        {
          title: this.$t('创建日期'),
          align: 'center',
          sorter: true,
          dataIndex: 'createTime'
        },
        {
          title: this.$t('仓库'),
          align: 'center',
          dataIndex: 'query07'
        },
        {
          title: this.$t('单备注'),
          align: 'center',
          sorter: true,
          dataIndex: 'text01'
        },
        {
          title: this.$t('质检凭证'),
          align: 'center',
          sorter: true,
          dataIndex: 'attr2',
          scopedSlots: { customRender: 'previewImg' }
        },
        {
          title: this.$t('发票'),
          align: 'center',
          sorter: true,
          dataIndex: 'attr3',
          scopedSlots: { customRender: 'fileSlot' }
        },
        {
          title: this.$t('质检备注'),
          align: 'center',
          sorter: true,
          dataIndex: 'text03'
        },
        {
          title: this.$t('确认时间'),
          align: 'center',
          sorter: true,
          dataIndex: 'query43'
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/jeeerp/busiPo/list2',
        delete: '/jeeerp/busiPo/delete',
        deleteBatch: '/jeeerp/busiPo/deleteBatch',
        exportXlsUrl: '/jeeerp/busiPo/exportXls',
        importExcelUrl: 'jeeerp/Import/SampleMakingOrderImportExcel2'
      },
      dictOptions: {},
      superFieldList: [],
      queryParam: {
        query01: 'CGD'
        // createBy: store.getters.userInfo.username
      }
    }
  },
  created() {
    this.getSuperFieldList()
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },

  methods: {
    pickerChange(e, mode, key) {
      this.queryParam['sT'] = mode[0]?mode[0]:null
      this.queryParam['nT'] = mode[1]?mode[1]:null
    },
    handleDelete2(e) {
      getAction('/jeeerp/busiPo/editquery02', { ...e, query02: '已取消' }).then(res => {
        if (res.success) {
          this.$message.success(res.message)
          this.loadData()
        } else {
          this.$message.warning(res.message)
        }
        this.confirmLoading = false
      })
    },
    handleEditquery37(e) {
      getAction('/jeeerp/busiPo/editquery37', { ...e, query37: '已确认' }).then(res => {
        if (res.success) {
          this.$message.success(res.message)
          this.loadData()
        } else {
          this.$message.warning(res.message)
        }
        this.confirmLoading = false
      })
    },
    tabChange(active) {
      this.selectedRowKeys = []
      this.imStatus = active
      this.loadData()
    },
    loadData(arg) {
      if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      var params = this.getQueryParams() //查询条件
      params.query37 = this.imStatus
      this.loading = true
      getAction(this.url.list, params).then(res => {
        if (res.success) {
          //update-begin---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
          this.dataSource = res.result.records || res.result
          if (this.imStatus == '未确认') {
            this.total1 = res.result.total
          } else if (this.imStatus == '未确认') {
            this.total2 = res.result.total
          }
          if (res.result.total) {
            this.ipagination.total = res.result.total
          }
          //update-end---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    Downloadproof(e) {
      let param = {
        pageNo: 1,
        pageSize: 20,
        query01: 'FKD',
        query13: e.query13
      }
      getAction('/jeeerp/busiPaymentReceived/list2', param).then(res => {
        console.log(res.result.records)
        if (res.result.records.length > 0) {
          let jarr = []
          res.result.records.map(v => {
            let arr = v.attr4 ? v.attr4.split(',') : []
            jarr = [...jarr, ...arr]
          })
          this.previewImg(jarr.join(','))
        } else {
          this.$message.warning('暂未付款')
        }
      })
    },

    exportList(e) {
      let params = { orderids: e.query04, type: 'CGZJ' }
      getAction('/jeeerp/busiPo/orderExcelOrPdf', params).then(res => {
        INSPECTION_SHEET(res.result)
        console.log(res.result.records, '-=-=-=')
        // res.result.records
      })
    },
    QualityInspectionSchedule(e) {
      this.$refs.updateTimeLimit2.open(e)
    },
    handleExportXls2(fileName) {
      this.exportOk = true
      if (!fileName || typeof fileName != 'string') {
        fileName = '导出文件'
      }
      let myparam = {}
      Object.assign(myparam, this.queryParam)
      for (var mkey in myparam) {
        if (myparam[mkey] != null && myparam[mkey] != '' && myparam[mkey].indexOf('*') == -1) {
          myparam[mkey] = '*' + myparam[mkey] + '*'
        }
      }

      let param = { ...myparam }
      if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
        param['selections'] = this.selectedRowKeys.join(',')
      }
      console.log('导出参数', param)
      try {
        downFile('/jeeerp/Import/SampleMakingOrderExportXls')
          .then(data => {
            if (!data) {
              this.$message.warning('文件下载失败')
              this.exportOk = false
              return
            }
            if (typeof window.navigator.msSaveBlob !== 'undefined') {
              window.navigator.msSaveBlob(new Blob([data], { type: 'application/vnd.ms-excel' }), fileName + '.xls')
            } else {
              let url = window.URL.createObjectURL(new Blob([data], { type: 'application/vnd.ms-excel' }))
              let link = document.createElement('a')
              link.style.display = 'none'
              link.href = url
              link.setAttribute('download', fileName + '.xls')
              document.body.appendChild(link)
              link.click()
              document.body.removeChild(link) //下载完成移除元素
              window.URL.revokeObjectURL(url) //释放掉blob对象
            }
            this.exportOk = false
            console.log(this.exportOk)
          })
          .finally(() => {
            this.exportOk = false
          })
      } catch (e) {
        this.exportOk = false
      }
    },
    handleExportDetailXls(name) {
      let myparam = {}
      Object.assign(myparam, this.queryParam)
      for (var mkey in myparam) {
        if (myparam[mkey] != null && myparam[mkey] != '' && myparam[mkey].indexOf('*') == -1) {
          myparam[mkey] = '*' + myparam[mkey] + '*'
        }
      }
      let param = { ...myparam }
      param.column = 'query14'
      param.order = 'asc'
      if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
        var dat = []
        this.selectionRows.forEach(v => {
          dat.push(v.query04)
        })
        param['query04'] = dat.join(',')
      }
      getAction('/jeeerp/busiPo/list', {
        ...param,
        pageNo: 1,
        pageSize: 100000
      }).then(res => {
        res.result.records.map(v => {
          if (!v.num10) {
            v.num10 = '0%'
          } else {
            v.num10 = v.num10 + '%'
          }
        })
        ExportTemplate([], BusiPoDetailList, res.result.records, name)
      })
    },
    handleExportXls(name) {
      let myparam = {}
      Object.assign(myparam, this.queryParam)
      for (var mkey in myparam) {
        if (myparam[mkey] != null && myparam[mkey] != '' && myparam[mkey].indexOf('*') == -1) {
          myparam[mkey] = '' + myparam[mkey] + ''
        }
      }
      let param = { ...myparam }
      if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
        param['selections'] = this.selectedRowKeys.join(',')
      }
      getAction('/jeeerp/ExportTableData/ExportBusiPo', param).then(res => {
        res.result.map(v => {
          if (!v.num10) {
            v.num10 = '0%'
          } else {
            v.num10 = v.num10 + '%'
          }
        })
        ExportTemplate([], BusiPoList, res.result, name)
      })
    },
    collectionSchedule(record) {
      // 收款计划
      this.$router.push({ path: '/erp/busiom/BusiOmPlan', query: { orderId: record.query04 } })
    },
    // SalesContractPdf() {
    //   this.$refs.SalesContractPdf.open()
    // },
    PoContractPDF(e, type) {
      this.$refs.PoContract.open(e, type)
    },
    SalesContractPdf(e, type) {
      this.$refs.SalesContractPdf.open(e, type)
    },
    PoFinish(e) {
      this.$refs.PoFinish.open(e)
    },
    purchaseContract1(e, type) {
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: e.query04, type: type }).then(res => {
        if (res.success) {
          exportFile([], [], res.result, 'WMS PO 大货含税')
        }
      })
    },
    purchaseContract2(e, type) {
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: e.query04, type: type }).then(res => {
        if (res.success) {
          exportFile2([], [], res.result, 'WMS PO 大货不含税')
        }
      })
    },
    salesContract(e, type) {
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: e.query04, type: type }).then(res => {
        if (res.success) {
          exportSalesContract([], [], res.result, 'WMS 付款单')
        }
      })
    },
    previewImg(text) {
      this.$refs.previewFile.open(text)
    },
    handleUpload(record) {
      this.$refs.uploadFile.open(record)
    },
    handleUpload2(record) {
      this.$refs.uploadFile2.open(record)
    },
    handleCopy(ids) {
      let orderId = ''
      this.dataSource.map((item, index) => {
        if (item.id == ids[0]) {
          orderId = item.query04
        }
      })
      this.$refs.modalForm.copyAdd(orderId)
    },
    searchReset() {
      // this.queryParam = { query01: 'CGD',createBy: this.store.getters.userInfo.username }
      this.queryParam = { query01: 'CGD' }
      this.loadData()
    },
    pushWMS(record) {
      let pushData = []
      let params = { query04: record.query04, pageNo: 1, pageSize: 1000, query01: 'CGD' }
      getAction('/jeeerp/busiPo/list', params).then(res => {
        if (res.success) {
          pushData = res.result.records
          this.$refs.modalForm2.open(pushData)
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    paymentSchedule(record) {
      // 付款计划
      this.$router.push({ path: '/erp/busipo/BusiPoPlan', query: { orderId: record.query04 } })
    },
    paymentSchedule2(record) {
      // 形式发票
      this.$router.push({ path: '/erp/busiom/BusiOmProInvoice', query: { orderId: record.query04 } })
    },
    pushWMSOm(record) {
      // 推送WMS
      this.$router.push({ path: '/erp/busiom/BusiOmOrder', query: { orderId: record.query04 } })
    },
    popupCallback3(value, row) {},
    initDictConfig() {},
    getSuperFieldList() {
      let fieldList = []
      fieldList.push({ type: 'string', value: 'createName', text: '创建人名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'updateName', text: '更新人名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query01', text: '单据类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query02', text: '单据状态', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query03', text: '建单日期', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query04', text: '单号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query05', text: '公司', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query06', text: '工厂', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query07', text: '库存地点', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query08', text: '供应商编码', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query09', text: '供应商名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query10', text: '商品编码', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query11', text: '商品名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query12', text: '单位', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query13', text: '主PO', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query14', text: '子PO', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query15', text: '检验类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query16', text: 'query16', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query17', text: 'query17', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query18', text: 'query18', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query19', text: 'query19', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query20', text: 'query20', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query21', text: '预计到货时间', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query22', text: 'query22', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query23', text: '单号-行项目号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query24', text: 'query24', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query25', text: 'query25', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query26', text: 'query26', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query27', text: 'query27', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query28', text: 'query28', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query29', text: 'query29', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query30', text: 'query30', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num01', text: '数量', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num02', text: '未完成数量', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num03', text: '已完成数量', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num04', text: 'num04', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num05', text: 'num05', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link01', text: '关联单据类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link02', text: '关联单号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link03', text: 'link03', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link04', text: 'link04', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link05', text: 'link05', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text01', text: '备注', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text02', text: '备注', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text03', text: 'text03', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text04', text: 'text04', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text05', text: 'text05', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr1', text: '单据附件', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr2', text: 'attr2', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr3', text: 'attr3', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr4', text: 'attr4', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr5', text: 'attr5', dictCode: '' })
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>
