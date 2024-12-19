<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="创建人登录名称">-->
<!--              <a-input placeholder="请输入创建人登录名称" v-model="queryParam.createBy"></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="创建日期">-->
<!--              <j-date placeholder="请选择创建日期" v-model="queryParam.createTime"></j-date>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="类型">
              <a-input placeholder="请输入类型" v-model="queryParam.rfidType"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="业务编号">
              <a-input placeholder="请输入业务编号" v-model="queryParam.rfidBuseno"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="业务内容">
                <a-input placeholder="请输入业务内容" v-model="queryParam.rfidBusecont"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="RFID1">
                <a-input placeholder="请输入RFID1" v-model="queryParam.rfidId1"></a-input>
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
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls('RFID表')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
                @change="handleImportExcel">
        <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel">
        <a-icon type="delete"/>
        批量删除
      </a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a
        style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项目')}}
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
          <img v-else :src="getImgView(text)" height="25px" alt=""
               style="max-width:80px;font-size: 12px;font-style: italic;"/>
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

        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)">{{$t('查看')}}</a>
          <a-divider type="vertical"/>
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical"/>

          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
          </a-popconfirm>
<!--          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>-->
        </span>

      </a-table>
    </div>

    <rfidBuse-modal ref="modalForm" @ok="modalFormOk"></rfidBuse-modal>
  </a-card>
</template>

<script>

import '@/assets/less/TableExpand.less'
import {mixinDevice} from '@/utils/mixin'
import {JeecgListMixin} from '@/mixins/JeecgListMixin'
import RfidBuseModal from './modules/RfidBuseModal'
import JDate from '@/components/jeecg/JDate.vue'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'

export default {
  name: "RfidBuseList",
  mixins: [JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
  components: {
    JDate,
    RfidBuseModal
  },
  data() {
    return {
      description: 'RFID表管理页面',
      // 表头
      columns: [
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          width:147,
          // fixed:"right",
          scopedSlots: {customRender: 'action'}
        },
        // {
        //   title: '创建人登录名称',
        //   align: 'left',
        //   dataIndex: 'createBy'
        // },
        // {
        //   title: '创建日期',
        //   align: 'left',
        //   dataIndex: 'createTime',
        //   customRender: function (text) {
        //     return !text ? "" : (text.length > 10 ? text.substr(0, 10) : text)
        //   }
        // },
        {
          title: '类型',
          align: 'left',
          dataIndex: 'rfidType'
        },
        {
          title: '业务编号',
          align: 'left',
          dataIndex: 'rfidBuseno'
        },
        {
          title: '业务内容',
          align: 'left',
          dataIndex: 'rfidBusecont'
        }

      ],
      url: {
        list: "/jeewms/rfidBuse/list",
        delete: "/jeewms/rfidBuse/delete",
        deleteBatch: "/jeewms/rfidBuse/deleteBatch",
        exportXlsUrl: "/jeewms/rfidBuse/exportXls",
        importExcelUrl: "jeewms/rfidBuse/importExcel",
      },
      dictOptions: {},
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    },
  },
  methods: {
    initDictConfig() {
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>