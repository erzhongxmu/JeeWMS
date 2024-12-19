<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.shpBianMa"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.shpMingCheng"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品分类')">
              <j-search-select-tag
                v-model="queryParam.classification"
                dict="ba_part_type,type_name,attr3"
                :placeholder="$t('请选择')"
              />
            </a-form-item>
          </a-col>
          <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="车间">
                <a-input placeholder="请输入车间" v-model="queryParam.workshop"></a-input>
              </a-form-item>
          </a-col>-->
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主编码">
                <j-search-select-tag v-model="queryParam.shpBianMakh" dict="md_cus,ke_hu_bian_ma,ke_hu_bian_ma" placeholder="请选择货主编码"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主名称">
                <j-search-select-tag v-model="queryParam.shpBianMakh" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" placeholder="请选择货主名称"/>
              </a-form-item>
          </a-col>-->
          <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="商品分类">
                <j-search-select-tag placeholder="请选择商品分类" v-model="queryParam.classification" :trigger-change="true" dict="ba_part_type,type_name,id"/>
              </a-form-item>
          </a-col>-->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'part:add'">{{$t('添加')}}</a-button>
      <a-button
        type="primary"
        icon="download"
        @click="handleExportXls($t('商品管理'))"
        v-has="'part:export'"
      >{{$t('导出')}}</a-button>
      <a-button
        type="primary"
        icon="download"
        @click="handleExportXls2($t('商品BOM'))"
        v-has="'part:export'"
      >{{$t('导出BOM')}}</a-button>
      <a-upload
        name="file"
        v-has="'part:import'"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button
        type="primary"
        icon="download"
        @click="handleExportModal($t('商品管理'))"
        v-has="'part:exportModal'"
      >{{$t('模板下载')}}</a-button>
      <a-button key="1" @click="batchDel" v-has="'part:batchDelete'">
        <a-icon type="delete" />
        {{$t('批量删除')}}
      </a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>
        {{$t('项目')}}
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
        :rowClassName="setRowClsaa"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt
            style="max-width:80px;font-size: 12px;font-style: italic;"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)"
          >下载</a-button>
        </template>
        
        <template slot="previewImg" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">{{$t('无文件')}}</span>
          <a-button v-else :ghost="true" type="primary" icon="eye" size="small" @click="previewImg(text)">
            {{$t('预览')}}
          </a-button>
        </template>
        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)" v-has="'part:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'part:check'" />
          <a @click="handleEdit(record)" v-has="'part:update'">{{$t('编辑')}}</a>
          <a-divider type="vertical" v-if="record.factoryType == 'BUNDLE'" />
          <a @click="handleEdit2(record)" v-if="record.factoryType == 'BUNDLE'">BOM</a>
          <a-divider type="vertical" />
          <a-popconfirm
            :title="$t('你确定吗?')"
            @confirm="() => handleDelete(record.id)"
            v-has="'part:delete'"
          >
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
          <!-- <a-divider type="vertical" v-has="'part:delete'"/>
          <a-popconfirm v-if="record.frozen=='N'" title="确定冻结吗?" @confirm="() => handleFrozen(record.id,'Y')" v-has="'part:frozen'">
            <a>冻结</a>
          </a-popconfirm>
          <a-popconfirm v-if="record.frozen=='Y'" title="确定解冻吗?" @confirm="() => handleFrozen(record.id,'N')" v-has="'part:frozen'">
            <a>解冻</a>
          </a-popconfirm>-->
          <!--          <a-dropdown>-->
          <!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
          <!--            <a-menu slot="overlay">-->
          <!--              <a-menu-item>-->
          <!--                -->
          <!--              </a-menu-item>-->
          <!--            </a-menu>-->
          <!--          </a-dropdown>-->
        </span>
      </a-table>
    </div>

    <mdGoods-modal ref="modalForm" @ok="modalFormOk"></mdGoods-modal>
    <mdGoods-modal2 ref="modalForm2"></mdGoods-modal2>
    <export-spin v-if="exportOk"></export-spin>
    <previewFile ref="previewFile" />
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import MdGoodsModal from './modules/MdGoodsModal'
import MdGoodsModal2 from './modules/MdGoodsModal2'
import { postAction, downFile } from '@/api/manage'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import ExportSpin from '@/components/ExportSpin/ExportSpin'
import previewFile from '@/components/previewFile/previewFile.vue'

export default {
  name: 'MdGoodsList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass, listCheckMixin],
  components: {
    JDictSelectTag,
    MdGoodsModal,
    MdGoodsModal2,
    JSearchSelectTag,
    ExportSpin,
    previewFile
  },
  data() {
    return {
      description: '商品管理页面',
      // 表头
      columns: [
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

        // {
        //   title:'货主编号',
        //   align: 'left',
        //   dataIndex: 'shpBianMakh'
        // },
        // {
        //   title:'货主',
        //   align: 'left',
        //   dataIndex: 'suoShuKeHu'
        // },
        {
          title: this.$t('商品编码'),
          align: 'left',
          dataIndex: 'shpBianMa',
          sorter: true
        },
        {
          title: this.$t('商品名称'),
          align: 'left',
          dataIndex: 'shpMingCheng',
          sorter: true
        },
        {
          title: this.$t('中文名称'),
          align: 'left',
          dataIndex: 'ywMingCheng',
          sorter: true
        },
        {
          title: this.$t('商品规格'),
          align: 'left',
          dataIndex: 'shpGuiGe',
          sorter: true
        },
        {
          title: this.$t('产品属性'),
          align: 'left',
          dataIndex: 'chpShuXing',
          sorter: true
        },
        {
          title: this.$t('产品属性英文'),
          align: 'left',
          dataIndex: 'goodsTypeEnname',
          sorter: true,
          width: 100,
          checked:true,
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
              {text?(text.length > 20?text.substring(0,20)+'...':text):''}
            </a-tooltip>
          )
        },
        {
          title: this.$t('颜色'),
          align: 'left',
          dataIndex: 'shpYanSe',
          sorter: true
        },
        {
          title: this.$t('颜色英文'),
          align: 'left',
          dataIndex: 'shpYanEnse',
          sorter: true
        },
        {
          title: this.$t('单位'),
          align: 'left',
          dataIndex: 'shlDanWei',
          sorter: true
        },
        {
          title: this.$t('商品类型'),
          align: 'left',
          dataIndex: 'factoryType'
        },
        {
          title: this.$t('商品logo'),
          align: 'left',
          dataIndex: 'shplogo',
          sorter: true
        },
        {
          title: this.$t('贴纸类型'),
          align: 'left',
          dataIndex: 'stickertype',
          sorter: true
        },
        {
          title: this.$t('商品简称'),
          align: 'left',
          dataIndex: 'shpJianCheng',
          sorter: true
        },
        {
          title: this.$t('商品分类'),
          align: 'left',
          dataIndex: 'classification',
          sorter: true
        },
        {
          title: this.$t('图片'),
          align: 'center',
          dataIndex: 'ppTuPian',
          scopedSlots: { customRender: 'previewImg' },
        },
        // {
        //   title:'日文名称',
        //   align: 'left',
        //   dataIndex: 'rwMingCheng'
        // },
        // {
        //   title:this.$t('海关编码'),
        //   align: 'left',
        //   dataIndex: 'cusNo'
        // },
        {
          title: this.$t('创建人'),
          align: 'left',
          dataIndex: 'createName',
          sorter: true
        },
        {
          title: this.$t('创建日期'),
          align: 'left',
          dataIndex: 'createTime',
          sorter: true
        },
        {
          title: this.$t('创建日期'),
          align: 'left',
          dataIndex: 'createTime',
          sorter: true
        },
        {
          title: this.$t('备注'),
          align: 'left',
          dataIndex: 'assembleSingle',
          sorter: true
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          // fixed:"right",
          width: 200,
          scopedSlots: { customRender: 'action' }
        }
        // {
        //   title:'存放温层',
        //   align: 'left',
        //   dataIndex: 'cfWenCeng'
        // },
        // {
        //   title:'拆零控制',
        //   align: 'left',
        //   dataIndex: 'chlKongZhi'
        // },
        // {
        //   title:'码盘单层数量',
        //   align: 'left',
        //   dataIndex: 'mpDanCeng'
        // },
        // {
        //   title:'码盘层高',
        //   align: 'left',
        //   dataIndex: 'mpCengGao'
        // },
        // {
        //   title:'计费商品类',
        //   align: 'left',
        //   dataIndex: 'jfShpLei'
        // },
        // {
        //   title:'商品条码',
        //   align: 'left',
        //   dataIndex: 'shpTiaoMa'
        // },
        // {
        //   title:'品牌图片',
        //   align: 'left',
        //   dataIndex: 'ppTuPian'
        // },
        // {
        //   title:'保质期',
        //   align: 'left',
        //   dataIndex: 'bzhiQi'
        // },
        // {
        //   title:'允许天数',
        //   align: 'left',
        //   dataIndex: 'zhlKgm'
        // },
        // {
        //   title:'拆零单位',
        //   align: 'left',
        //   dataIndex: 'jshDanWei'
        // },
        // {
        //   title:'体积',
        //   align: 'left',
        //   dataIndex: 'tiJiCm'
        // },
        // {
        //   title:'重量',
        //   align: 'left',
        //   dataIndex: 'zhlKg'
        // },
        // {
        //   title:'拆零数量',
        //   align: 'left',
        //   dataIndex: 'chlShl'
        // },
        // {
        //   title:'价格',
        //   align: 'left',
        //   dataIndex: 'gaoDanPin'
        // },
        // {
        //   title:'长',
        //   align: 'left',
        //   dataIndex: 'chZhXiang'
        // },
        // {
        //   title:'宽',
        //   align: 'left',
        //   dataIndex: 'kuZhXiang'
        // },
        // {
        //   title:'高',
        //   align: 'left',
        //   dataIndex: 'gaoZhXiang'
        // },
        // {
        //   title:'停用',
        //   align: 'left',
        //   dataIndex: 'zhuangTai',
        //   customRender:function (t,r,index) {
        //     return t!='Y'?'正常':'冻结';
        //   }
        // }
        // {
        //   title: '状态',
        //   align: 'left',
        //   dataIndex: 'frozen',
        //   customRender:function (t,r,index) {
        //     return t!='Y'?'正常':'冻结';
        //   }
        // }
        // {
        //   title:'件数与体积比',
        //   align: 'left',
        //   dataIndex: 'jtiJiBi'
        // },
        // {
        //   title:'件数与毛重比',
        //   align: 'left',
        //   dataIndex: 'jmZhongBi'
        // },
        // {
        //   title:'件数与净重比',
        //   align: 'left',
        //   dataIndex: 'jjZhongBi'
        // },
        // {
        //   title:'尺寸单位',
        //   align: 'left',
        //   dataIndex: 'chcDanWei'
        // },
        // {
        //   title:'上线包装-长',
        //   align: 'left',
        //   dataIndex: 'chDanPin'
        // },
        // {
        //   title:'上线包装-宽',
        //   align: 'left',
        //   dataIndex: 'kuDanPin'
        // },
        // {
        //   title:'上线包装-高',
        //   align: 'left',
        //   dataIndex: 'gaoDanPin'
        // },
        // {
        //   title:'出厂包装-长',
        //   align: 'left',
        //   dataIndex: 'chZhXiang'
        // },
        // {
        //   title:'出厂包装-宽',
        //   align: 'left',
        //   dataIndex: 'kuZhXiang'
        // },
        // {
        //   title:'出厂包装-高',
        //   align: 'left',
        //   dataIndex: 'gaoZhXiang'
        // },
        // {
        //   title:'停用',
        //   align: 'left',
        //   dataIndex: 'zhuangTai'
        // },
        // {
        //   title:'货主编码',
        //   align: 'left',
        //   dataIndex: 'shpBianMakh'
        // },
        // {
        //   title:'货主名称',
        //   align: 'left',
        //   dataIndex: 'cusName'
        // },
        // {
        //   title:'上架推荐开关',
        //   align: 'left',
        //   dataIndex: 'recommend'
        // },
        // {
        //   title:'上线包装-是否翻包',
        //   align: 'left',
        //   dataIndex: 'ifBackpacking_dictText'
        // },
        // {
        //   title:'出厂包装-SNP',
        //   align: 'left',
        //   dataIndex: 'beforeSnp'
        // },
        // {
        //   title:'上线包装-SNP',
        //   align: 'left',
        //   dataIndex: 'afterSnp'
        // },
        // {
        //   title:'工艺路线',
        //   align: 'left',
        //   dataIndex: 'routing'
        // },
        // {
        //   title:'Dock管理',
        //   align: 'left',
        //   dataIndex: 'dock'
        // },
        // {
        //   title:'库位属性',
        //   align: 'left',
        //   dataIndex: 'kuWeiShuXing'
        // },
        // {
        //   title:'包装页签',
        //   align: 'left',
        //   dataIndex: 'packingTag'
        // },
        // {
        //   title:'货主ID',
        //   align: 'left',
        //   dataIndex: 'cusId'
        // },
        // {
        //   title:'商品名称',
        //   align: 'left',
        //   dataIndex: 'partName'
        // },
        // {
        //   title:'单位ID',
        //   align: 'left',
        //   dataIndex: 'orgId'
        // },
        // {
        //   title:'商品的重量',
        //   align: 'left',
        //   dataIndex: 'weight'
        // },
        // {
        //   title:'体积',
        //   align: 'left',
        //   dataIndex: 'volume'
        // },
        // {
        //   title:'商品分类',
        //   align: 'left',
        //   dataIndex: 'model_dictText'
        // },
        // {
        //   title:'车间',
        //   align: 'left',
        //   dataIndex: 'workshop'
        // },
        // {
        //   title:'BOM中文名称',
        //   align: 'left',
        //   dataIndex: 'bomZw'
        // },
        // {
        //   title:'商品分类',
        //   align: 'left',
        //   dataIndex: 'classification_dictText'
        // },
        // {
        //   title:'出厂包装-类型',
        //   align: 'left',
        //   dataIndex: 'factoryType_dictText'
        // },
        // {
        //   title:'上架包装-类型',
        //   align: 'left',
        //   dataIndex: 'onlineType_dictText'
        // },
        // {
        //   title:'存放库区',
        //   align: 'left',
        //   dataIndex: 'storageArea'
        // },
        // {
        //   title:'上线包装-是否翻包上线方式',
        //   align: 'left',
        //   dataIndex: 'onlineMode_dictText'
        // },
        // {
        //   title:'min值',
        //   align: 'left',
        //   dataIndex: 'minStock'
        // },
        // {
        //   title:'max值',
        //   align: 'left',
        //   dataIndex: 'maxStock'
        // },
        // {
        //   title:'出厂包装-堆码极限',
        //   align: 'left',
        //   dataIndex: 'factoryStackingLimit'
        // }
      ],
      url: {
        list: '/jeewms/mdGoods/listpltn',
        delete: '/jeewms/mdGoods/delete',
        deleteBatch: '/jeewms/mdGoods/deleteBatch',
        exportXlsUrl: '/jeewms/mdGoods/exportXls',
        exportXlsUrl2: '/jeewms/mdGoods/downloaditemXls',
        importExcelUrl: 'jeewms/mdGoods/importExcel',
        frozen: '/jeewms/mdGoods/frozen',
        downloadXls: '/jeewms/mdGoods/downloadXls'
      },
      dictOptions: {}
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    previewImg(text) {
      // props = 'https://t7.baidu.com/it/u=1819248061,230866778&fm=193&f=GIF,https://t7.baidu.com/it/u=1297102096,3476971300&fm=193&f=GIF'
      // this.$refs.previewFile.open(props)
      this.$refs.previewFile.open(text)
    },
    handleExportXls2(fileName) {
      this.exportOk = true
      console.log(this.exportOk)
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
      console.log('导出参数11', param)
      try {
        downFile(this.url.exportXlsUrl2, param)
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
    initDictConfig() {},
    handleFrozen(id, type) {
      var params = {
        id: id,
        frozen: type
      }
      postAction(this.url.frozen, params).then(res => {
        if (res.success) {
          this.$message.success(this.$t('操作成功'))
          this.loadData()
        }
        if (res.code === 510) {
          this.$message.warning(this.$t('操作失败'))
        }
      })
    },
    handleCheck: function(record) {
      if (record.storageArea == undefined || record.storageArea == null || record.storageArea == '') {
        record.storageArea = []
      }
      this.$refs.modalForm.edit(record)
      this.$refs.modalForm.title = '查看'
      this.$refs.modalForm.disableSubmit = false
      if (record.ifBackpacking == 'Y') {
        this.$refs.modalForm.fbFlag = true
      }
    },
    handleEdit: function(record) {
      if (record.storageArea == undefined || record.storageArea == null || record.storageArea == '') {
        record.storageArea = []
      }
      this.$refs.modalForm.edit(record)
      this.$refs.modalForm.title = '编辑'
      this.$refs.modalForm.disableSubmit = false
      if (record.ifBackpacking == 'Y') {
        this.$refs.modalForm.fbFlag = true
      }
    },
    handleEdit2(record) {
      this.$refs.modalForm2.add(record)
    },
    handleExportModal(fileName) {
      this.exportOk = true
      console.log(this.exportOk)
      if (!fileName || typeof fileName != 'string') {
        fileName = '导出文件'
      }
      let myparam = {}
      // Object.assign(myparam,this.queryParam);
      // for(var mkey in myparam){
      //   if(myparam[mkey]!=null && myparam[mkey]!='' && myparam[mkey].indexOf("*") == -1){
      //     myparam[mkey] = '*'+myparam[mkey]+'*';
      //   }
      // }
      //
      let param = { ...myparam }
      console.log('导出参数', param)
      try {
        downFile(this.url.downloadXls, param)
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
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>