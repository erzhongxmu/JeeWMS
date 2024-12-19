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
              @click="uploadFile(text)"
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
    name: 'WmImNoticeISubTable',
    mixins: [JeecgListMixin],
    props: {
      record: {
        type: Object,
        default: null,
      }
    },
    data() {
      return {
        description: '商品内嵌列表',
        disableMixinCreated: true,
        loading: false,
        dataSource: [],
        columns: [
          {
            title: '商品编码',
            align: 'left',
            dataIndex: 'goodsCode',
          },
          {
            title: '商品名称',
            align: 'left',
            dataIndex: 'goodsName',
          },
          {
            title: '数量',
            align: 'left',
            dataIndex: 'goodsCount',
          },
          {
            title: '生产日期',
            align: 'left',
            dataIndex: 'goodsPrdData',
          },
          {
            title: '批次',
            align: 'left',
            dataIndex: 'goodsBatch',
          },
          {
            title: '收货登记数量',
            align: 'left',
            dataIndex: 'goodsQmCount',
          },
          {
            title: '计划库位',
            align: 'left',
            dataIndex: 'binPlan',
          },
          {
            title: '单位',
            align: 'left',
            dataIndex: 'goodsUnit',
          },
          {
            title: '未清数量',
            align: 'left',
            dataIndex: 'goodsWqmCount',
          },
          {
            title: '托盘',
            align: 'left',
            dataIndex: 'tinId',
          },
        ],
        url: {
          listByMainId: '/jeewms/wmImNoticeH/queryWmImNoticeIByMainId',
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
