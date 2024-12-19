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
    name: 'BmsCostRuleISubTable',
    mixins: [JeecgListMixin],
    props: {
      record: {
        type: Object,
        default: null,
      }
    },
    data() {
      return {
        description: '计费规则子内嵌列表',
        disableMixinCreated: true,
        loading: false,
        dataSource: [],
        columns: [
          {
            title: this.$t('合同编号'),
            align: 'center',
            dataIndex: 'contNo',
          },
          {
            title: this.$t('行项目号'),
            align: 'center',
            dataIndex: 'itemNo',
          },
          {
            title: this.$t('计费类型'),
            align: 'center',
            dataIndex: 'costTypeNo',
          },
          {
            title: this.$t('计费类型名称'),
            align: 'center',
            dataIndex: 'costTypeName',
          },
          {
            title: this.$t('开始数量'),
            align: 'center',
            dataIndex: 'beginSum',
          },
          {
            title: this.$t('结束数量'),
            align: 'center',
            dataIndex: 'endSum',
          },
          {
            title: this.$t('单位'),
            align: 'center',
            dataIndex: 'unit',
          },
          {
            title: this.$t('不含税价'),
            align: 'center',
            dataIndex: 'costUnit',
          },
          {
            title: this.$t('税率'),
            align: 'center',
            dataIndex: 'costSl',
          },
          {
            title: this.$t('含税价'),
            align: 'center',
            dataIndex: 'costHsj',
          },
          {
            title: this.$t('原含税价'),
            align: 'center',
            dataIndex: 'costYhsj',
          },
          {
            title: this.$t('货币'),
            align: 'center',
            dataIndex: 'costHb',
          },
        ],
        url: {
          listByMainId: '/bms/bmsCostRuleH/queryBmsCostRuleIByMainId',
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
          id: record.contNo
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
