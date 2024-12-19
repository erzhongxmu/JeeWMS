<template>
  <a-table
    rowKey="id"
    size="middle"
    bordered
    :loading="loading"
    :columns="columns"
    :dataSource="dataSource"
    :pagination="false"
  >

    <template slot="htmlSlot" slot-scope="text">
      <div v-html="text"></div>
    </template>

    <template slot="imgSlot" slot-scope="text">
      <div style="font-size: 12px;font-style: italic;">
        <span v-if="!text">{{$t('无图片')}}</span>
        <img v-else :src="getImgView(text)" alt="" style="max-width:80px;height:25px;"/>
      </div>
    </template>

    <template slot="fileSlot" slot-scope="text">
      <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
      <a-button
              v-else
              ghost
              type="primary"
              icon="download"
              size="small"
              @click="downloadFile(text)"
      >
        <span>{{$t('下载')}}</span>
      </a-button>
    </template>

  </a-table>
</template>

<script>
  import { getAction } from '@api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: 'BmsBillISubTable',
    mixins: [JeecgListMixin],
    props: {
      record: {
        type: Object,
        default: null,
      }
    },
    data() {
      return {
        description: '账单详情子内嵌列表',
        disableMixinCreated: true,
        loading: false,
        dataSource: [],
        columns: [
          {
            title: '账单编号',
            align: 'center',
            dataIndex: 'billNo',
          },
          {
            title: '来源编号',
            align: 'center',
            dataIndex: 'costSoNo',
          },
          {
            title: '来源名称',
            align: 'center',
            dataIndex: 'costSoName',
          },
          {
            title: '来源日期',
            align: 'center',
            dataIndex: 'costSoDate',
          },
          {
            title: '计费税率',
            align: 'center',
            dataIndex: 'costSl',
          },
          {
            title: '货币',
            align: 'center',
            dataIndex: 'costHb',
          },
          {
            title: '计费不含税价',
            align: 'center',
            dataIndex: 'costCoBhsj',
          },
          {
            title: '计费原含税价',
            align: 'center',
            dataIndex: 'costCoYhsj',
          },
        ],
        url: {
          listByMainId: '/bms/bmsBillH/queryBmsBillIByMainId',
        },
      }
    },
    watch: {
      record: {
        immediate: true,
        handler() {
          if (this.record != null) {
            this.loadData(this.record)
          }
        }
      }
    },
    methods: {

      loadData(record) {
        this.loading = true
        this.dataSource = []
        getAction(this.url.listByMainId, {
          id: record.id
        }).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records
          }
        }).finally(() => {
          this.loading = false
        })
      },

    },
  }
</script>

<style scoped>

</style>
