<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('简称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.zhongWenQch"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.gysBianMa"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.gysJianCheng"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('省份')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.province"></a-input>
            </a-form-item>
          </a-col>
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
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'MdSupList:add'">{{$t('添加')}}</a-button>
      <a-button
        icon="download"
        @click="handleExportXls($t('供应商'))"
        v-has="'MdSupList:export'"
      >{{$t('导出')}}</a-button>
      <a-upload
        v-has="'MdSupList:import'"
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button v-has="'MdSupList:batchDelete'" key="1" @click="batchDel">
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
          >{{$t('下载')}}</a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'MdSupList:edit'" />
          <a @click="handleEdit(record)" v-has="'MdSupList:edit'">{{$t('编辑')}}</a>

          <a-divider type="vertical" v-has="'MdSupList:Delete'" />
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
            <a v-has="'MdSupList:Delete'">{{$t('删除')}}</a>
          </a-popconfirm>
          <!--          <a-divider type="vertical" />-->
          <!--          <a-popconfirm title="确定通过吗?" @confirm="() => handleAudit(record.id,'1')" @cancel="() => handleAudit(record.id,'2')">-->
          <!--            <a>审核</a>-->
          <!--          </a-popconfirm>-->
          <!--          <a-dropdown>-->
          <!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
          <!--            <a-menu slot="overlay">-->
          <!--              <a-menu-item>-->
          <!--                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">-->
          <!--                  <a>{{$t('删除')}}</a>-->
          <!--                </a-popconfirm>-->
          <!--              </a-menu-item>-->
          <!--              <a-menu-item v-if="record.gysZhuangTai != '1'">-->
          <!--                <a-popconfirm title="确定通过吗?" @confirm="() => handleAudit(record.id,'1')" @cancel="() => handleAudit(record.id,'2')">-->
          <!--                  <a>审核</a>-->
          <!--                </a-popconfirm>-->
          <!--              </a-menu-item>-->
          <!--            </a-menu>-->
          <!--          </a-dropdown>-->
        </span>
      </a-table>
    </div>

    <mdSup-modal ref="modalForm" @ok="modalFormOk"></mdSup-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import MdSupModal from './modules/MdSupModal'
import { putAction } from '@/api/manage'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'

export default {
  name: 'MdSupList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass, listCheckMixin],
  components: {
    MdSupModal
  },
  data() {
    return {
      description: '供应商管理页面',

      // 表头
      columns: [
        {
          title: this.$t('简称'),
          align: 'left',
          dataIndex: 'zhongWenQch',
          sorter: true
        },
        {
          title: this.$t('编码'),
          align: 'left',
          dataIndex: 'gysBianMa',
          sorter: true
        },
        {
          title: this.$t('名称'),
          align: 'left',
          dataIndex: 'gysJianCheng',
          sorter: true
        },
        // {
        //   title:'供应商等级',
        //   align: 'left',
        //   dataIndex: 'gysDengJi_dictText'
        // },
        // {
        //   title:'地址',
        //   align: 'left',
        //   dataIndex: 'diZhi'
        // },
        {
          title: this.$t('主营产品'),
          align: 'left',
          dataIndex: 'zhuYingYeWu',
          sorter: true
        },
        {
          title: this.$t('联系人'),
          align: 'left',
          dataIndex: 'zhuLianXiRen',
          sorter: true
        },
        // {
        //   title:'电话',
        //   align: 'left',
        //   dataIndex: 'dianHua'
        // },
        {
          title: this.$t('开票税点'),
          align: 'left',
          dataIndex: 'shuiWuDeng',
          sorter: true
        },
        {
          title: this.$t('省份'),
          align: 'left',
          dataIndex: 'province',
          sorter: true
        },
        {
          title: this.$t('保密协议与行为准则'),
          align: 'left',
          dataIndex: 'wangYeDiZhi',
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
        //   title:'备注',
        //   align: 'left',
        //   dataIndex: 'beiZhu'
        // }
      ],
      url: {
        list: '/jeewms/mdSup/list',
        delete: '/jeewms/mdSup/delete',
        deleteBatch: '/jeewms/mdSup/deleteBatch',
        exportXlsUrl: '/jeewms/mdSup/exportXls',
        importExcelUrl: 'jeewms/mdSup/importExcel',
        editStatus: '/jeewms/mdSup/editStatus'
      },
      dictOptions: {},
      queryParam:{gysDengJi:'ZZS'},
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    handleAdd: function() {
      this.$refs.modalForm.add(this.queryParam.gysDengJi)
      this.$refs.modalForm.title = this.$t('新增')
      this.$refs.modalForm.disableSubmit = false
    },
    searchReset() {
      this.queryParam = {gysDengJi:'ZZS'}
      this.loadData(1)
    },
    initDictConfig() {},
    //审核
    handleAudit(id, status) {
      var _this = this
      var params = {
        id: id,
        gysZhuangTai: status
      }
      putAction(this.url.editStatus, params).then(res => {
        if (res.code == 200) {
          if (status == '1') {
            _this.$message.success('审核通过')
          }
          this.loadData()
        } else {
          this.$message.warning(this.$t('操作失败'))
        }
      })
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>