<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
          <!--            <a-form-item label="货主名称">-->
          <!--              <a-input placeholder="请输入货主名称" v-model="queryParam.zhongWenQch"></a-input>-->
          <!--            </a-form-item>-->
          <!--          </a-col>-->
          <!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
          <!--            <a-form-item label="货主编码">-->
          <!--              <a-input placeholder="请输入货主名称" v-model="queryParam.keHuBianMa"></a-input>-->
          <!--            </a-form-item>-->
          <!--          </a-col>-->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.zhongWenQch"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('简称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.keHuJianCheng"></a-input>
            </a-form-item>
          </a-col>
          
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.keHuBianMa"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('企业属性')">
              <j-dict-select-tag
                :placeholder="$t('请选择')"
                v-model="queryParam.xingYeFenLei"
                dictCode="wms_com_type,com_type_code,com_type_name"
              />
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="所属行业">
              <j-dict-select-tag
                placeholder="请选择所属行业"
                v-model="queryParam.suoShuXingYe"
                dictCode="ba_com_classfy,classfl_code,classfl_name"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="等级">
              <j-dict-select-tag
                placeholder="请选择货主等级"
                v-model="queryParam.keHuDengJi"
                dictCode="ba_com_deg,com_deg_code,com_deg_name"
              />
            </a-form-item>
          </a-col> -->
          <!--          </a-col>-->
          <!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->

          <!--          </a-col>-->
          <!--          <template v-if="toggleSearchStatus">-->
          <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->

          <!--          </template>-->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
              <!--              <a @click="handleToggleSearch" style="margin-left: 8px">-->
              <!--                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}-->
              <!--                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
              <!--              </a>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'cus:add'">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls($t('货主管理'))" v-has="'cus:export'">{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        v-has="'cus:import'"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel" v-has="'cus:batchDelete'">
        <a-icon type="delete"/>
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
          >{{$t("下载")}}</a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)" v-has="'cus:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'cus:update'" />

          <span v-has="'cus:update'">
            <a @click="handleEdit(record)">{{$t('编辑')}}</a>
            <!-- <a-divider type="vertical" /> -->
          </span>

          <!-- <span v-has="'cus:stop'" v-if="record.disableStatus != 'Y'">
            <a-popconfirm :title="$t('你确定吗?')" @confirm="() => switchStatus(record.id,'Y')">
              <a>{{$t('停用')}}</a>
            </a-popconfirm>
          </span>
          <span v-has="'cus:start'" v-if="record.disableStatus == 'Y'">
            <a-popconfirm :title="$t('你确定吗?')" @confirm="() => switchStatus(record.id,'N')">
              <a>{{$t('启用')}}</a>
            </a-popconfirm>
          </span> -->
          <a-divider type="vertical" />
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)" v-has="'cus:delete'">
                            <a>{{$t('删除')}}</a>
                          </a-popconfirm>
          <!--          <a-dropdown>
                      <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
                      <a-menu slot="overlay">
                        <a-menu-item>
                          
                        </a-menu-item>
                      </a-menu>
          </a-dropdown>-->
        </span>
      </a-table>
    </div>

    <mdCus-modal ref="modalForm" @ok="modalFormOk"></mdCus-modal>
    <export-spin v-if="exportOk"></export-spin>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { putAction } from '@/api/manage'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import MdCusModal from './modules/MdCusModal'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import ExportSpin from '@/components/ExportSpin/ExportSpin'

export default {
  name: 'MdCusList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass, listCheckMixin],
  components: {
    JDictSelectTag,
    MdCusModal,
    ExportSpin
  },
  data() {
    return {
      description: '货主管理页面',
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
        {
          title: this.$t('编码'),
          align: 'left',
          dataIndex: 'keHuBianMa',
          sorter: true
        },
        {
          title: this.$t('名称'),
          align: 'left',
          dataIndex: 'zhongWenQch',
          sorter: true
        },
        {
          title: this.$t('简称'),
          align: 'left',
          dataIndex: 'keHuJianCheng',
          sorter: true
        },

        // {
        //   title: '计费属性',
        //   align: 'left',
        //   dataIndex: 'keHuShuXing'
        // },
        {
          title: this.$t('企业属性'),
          align: 'left',
          dataIndex: 'xingYeFenLei',
          sorter: true
        },
        {
          title: this.$t('币种'),
          align: 'left',
          dataIndex: 'biBie',
          sorter: true
        },
        // {
        //   title: '等级',
        //   align: 'left',
        //   dataIndex: 'keHuDengJi'
        // },
        // {
        //   title: '所属行业',
        //   align: 'left',
        //   dataIndex: 'suoShuXingYe'
        // },
        // {
        //   title: this.$t('地址'),
        //   align: 'left',
        //   dataIndex: 'diZhi'
        // },
        // {
        //   title: this.$t('联系人'),
        //   align: 'left',
        //   dataIndex: 'zhuLianXiRen'
        // },
        {
          title: this.$t('状态'),
          align: 'left',
          dataIndex: 'disableStatus',
          customRender: (t, r, index) => {
            return t != 'Y' ? this.$t('启用') : this.$t('停用')
          },
          sorter: true
        },
        // {
        //   title:this.$t('联系方式'),
        //   align: 'left',
        //   dataIndex: 'shouJi'
        // },
        // {
        //   title: 'Email地址',
        //   align: 'left',
        //   dataIndex: 'emaildiZhi'
        // },
        {
          title:  this.$t('备注'),
          align: 'left',
          dataIndex: 'beiZhu',
          sorter: true
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          // fixed:"right",
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/jeewms/mdCus/list',
        edit: '/jeewms/mdCus/edit',
        delete: '/jeewms/mdCus/delete',
        deleteBatch: '/jeewms/mdCus/deleteBatch',
        exportXlsUrl: '/jeewms/mdCus/exportXls',
        importExcelUrl: 'jeewms/mdCus/importExcel'
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
    initDictConfig() {},
    switchStatus(id, status) {
      let that = this
      var param = {
        id: id,
        disableStatus: status
      }
      putAction(this.url.edit, param).then(res => {
        if (res.success) {
          that.$message.success(this.$t('操作成功'))
          that.loadData()
        } else {
          that.$message.warning(this.$t('操作失败'))
        }
      })
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>