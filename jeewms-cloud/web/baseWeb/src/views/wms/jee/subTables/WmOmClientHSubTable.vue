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
    name: 'WmOmNoticeISubTable',
    mixins: [JeecgListMixin],
    props: {
      record: {
        type: Object,
        default: null,
      }
    },
    data() {
      return {
        description: '出货详情内嵌列表',
        disableMixinCreated: true,
        loading: false,
        dataSource: [],
        columns: [
          {
            title: '创建人名称',
            align: 'left',
            dataIndex: 'createName',
          },
          {
            title: '创建日期',
            align: 'left',
            dataIndex: 'createTime',
          },
          // {
          //   title: '所属公司',
          //   align: 'left',
          //   dataIndex: 'sysCompanyCode_dictText'
          // },
          {
            title: '出货通知',
            align: 'left',
            dataIndex: 'omNoticeId',
          },
          {
            title: '出货商品',
            align: 'left',
            dataIndex: 'goodsId',
          },
          {
            title: '出货数量',
            align: 'left',
            dataIndex: 'goodsQua',
          },
          // {
          //   title: '出货单位',
          //   align: 'left',
          //   dataIndex: 'goodsUnit',
          // },
          // {
          //   title: '生产日期',
          //   align: 'left',
          //   dataIndex: 'goodsProData',
          // },
          // {
          //   title: '批次',
          //   align: 'left',
          //   dataIndex: 'goodsBatch',
          // },
          // {
          //   title: '出货仓位',
          //   align: 'left',
          //   dataIndex: 'binOm',
          // },
          {
            title: '已出货数量',
            align: 'left',
            dataIndex: 'goodsQuaok',
          },
          // {
          //   title: '预约出货时间',
          //   align: 'left',
          //   dataIndex: 'delvData',
          // },
          {
            title: '货主',
            align: 'left',
            dataIndex: 'cusCode_dictText'
          },
          // {
          //   title: '商品名称',
          //   align: 'left',
          //   dataIndex: 'goodsText',
          // },
          // {
          //   title: '波次号',
          //   align: 'left',
          //   dataIndex: 'waveId',
          // },
          // {
          //   title: '状态',
          //   align: 'left',
          //   dataIndex: 'omSta',
          // },
          // {
          //   title: '基本单位',
          //   align: 'left',
          //   dataIndex: 'baseUnit',
          // },
          // {
          //   title: '基本单位数量',
          //   align: 'left',
          //   dataIndex: 'baseGoodscount',
          // },
          // {
          //   title: '下架计划生成',
          //   align: 'left',
          //   dataIndex: 'planSta',
          // },
          {
            title: '商品名称',
            align: 'left',
            dataIndex: 'goodsName',
          },
          // {
          //   title: '其他系统ID',
          //   align: 'left',
          //   dataIndex: 'otherId',
          // },
          // {
          //   title: '订单号',
          //   align: 'left',
          //   dataIndex: 'imCusCode',
          // },
          // {
          //   title: '备注',
          //   align: 'left',
          //   dataIndex: 'omBeiZhu',
          // },
          // {
          //   title: '产品属性',
          //   align: 'left',
          //   dataIndex: 'chpShuXing',
          // },
          // {
          //   title: '商品条码',
          //   align: 'left',
          //   dataIndex: 'barcode',
          // },
        ],
        url: {
          listByMainId: '/jeewms/wmOmNoticeH/queryWmOmNoticeIByMainId',
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
        console.log(record)
        this.loading = true
        this.dataSource = []
        getAction(this.url.listByMainId, {
          id: record.omNoticeId
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
