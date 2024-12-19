<template>
  <a-card :bordered="false">
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="showModal(null)" type="primary" icon="plus">{{$t('添加')}}</a-button>
    </div>
    <div>
      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="false"
        :loading="loading"
        class="j-table-force-nowrap"
        @change="handleTableChange">
         <span slot="status" slot-scope="text, record, index">
            <a-tag color="pink" v-if="text==0">禁用</a-tag>
            <a-tag color="#87d068" v-if="text==1" >正常</a-tag>
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="showModal(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <gate-way-route-modal ref="modalForm" @ok="modalFormOk"></gate-way-route-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import GateWayRouteModal from './modules/GateWayRouteModal'

  export default {
    name: 'TenantList',
    mixins: [JeecgListMixin, mixinDevice],
    components: {
      GateWayRouteModal
    },
    data() {
      return {
        description: 'adad管理页面',
        // 表头
        columns: [
          {
            title: '路由ID',
            align: 'left',
            dataIndex: 'routerId'
          }, {
            title: '路由名称',
            align: 'left',
            dataIndex: 'name'
          },
          {
            title: '路由URI',
            align: 'left',
            dataIndex: 'uri'
          },
          {
            title: '状态',
            align: 'left',
            dataIndex: 'status',
            scopedSlots: { customRender: 'status' }
          },
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            fixed: 'right',
            width: 147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: '/sys/gatewayRoute/list',
          delete: '/sys/gatewayRoute/delete'
        },
        dictOptions: {}
      }
    },
    created() {
    },
    methods: {
      showModal(record) {
        this.$refs['modalForm'].show(record)
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>