<template>
  <j-modal
    :maskClosable="false"
    :width="980"
    :visible="visible"
    switchFullscreen
    :footer="null"
    @cancel="close"
  >
  
    <a-breadcrumb>
      <a-breadcrumb-item v-for="(item, index) in breadcrumb2" :key="index"
        ><a class="breadcrumb" @click="breadcrumbClick(index)">{{ item }}</a></a-breadcrumb-item
      >
    </a-breadcrumb>
    <div v-if="breadcrumb2.length == 1">
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="loadData(1)">
          <a-row :gutter="24">
            <a-col :xl="8" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('供应商编码')">
                <a-input :placeholder="$t('请输入供应商编码')" v-model="queryParam1.supplyNo"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="8" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('中文名称')">
                <a-input :placeholder="$t('请输入中文名称')" v-model="queryParam1.supplyZhName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="8" :lg="7" :md="8" :sm="24">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <a-button type="primary" @click="loadData(1)" icon="search">{{ $t('查询') }}</a-button>
                <a-button type="primary" @click="searchReset1" icon="reload" style="margin-left: 8px">{{
                  $t('重置')
                }}</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :loading="loading"
        :columns="sourceCus.columns"
        :dataSource="sourceCus.dataSource"
        :pagination="sourceCus.ipagination"
        :rowSelection="{ selectedRowKeys: sourceCus.selectedRowKeys, onChange: onSelectChange1, type: 'radio' }"
        @change="TableChange1"
      />
      <a-button type="primary" @click="next(breadcrumb2.length)" :disabled="sourceCus.selectedRowKeys.length == 0">{{
        $t('下一步')
      }}</a-button>
    </div>
    <div v-if="breadcrumb2.length == 2">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="24">
            <a-col :xl="8" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('规则编号')">
                <a-input v-model="queryParam2.costRuleNo" :placeholder="$t('请输入规则编号')" />
              </a-form-item>
            </a-col>
            <a-col :xl="8" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('规则名称')">
                <a-input v-model="queryParam2.costRuleName" :placeholder="$t('请输入规则名称')" />
              </a-form-item>
            </a-col>
            <a-col :xl="8" :lg="7" :md="8" :sm="24">
              <span class="table-page-search-submitButtons table-operator">
                <a-button type="primary" @click="loadData(2)">{{ $t('查询') }}</a-button>
                <a-button type="primary" @click="searchReset1" style="margin-left: 8px">{{ $t('重置') }}</a-button>
                <a-button type="primary" @click="allPitchOn">{{ $t('全部勾选') }}</a-button>
                <a-button type="primary" @click="onSelectChange2([],[])">{{ $t('全部取消勾选') }}</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :loading="loading"
        :columns="billing.columns"
        :dataSource="billing.dataSource"
        :pagination="billing.ipagination"
        :rowSelection="{ selectedRowKeys: billing.selectedRowKeys, onChange: onSelectChange2 }"
        @change="TableChange2"
      />
      <a-button type="primary" @click="next(breadcrumb2.length)" :disabled="billing.selectedRowKeys.length == 0">{{
        $t('下一步')
      }}</a-button>
    </div>
    <div v-if="breadcrumb2.length == 3">
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="loadData(1)">
          <a-row :gutter="24">
            <a-col :xl="8" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('供应商编码')">
                <a-input :placeholder="$t('请输入供应商编码')" v-model="queryParam3.supplyNo"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="8" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('中文名称')">
                <a-input :placeholder="$t('请输入中文名称')" v-model="queryParam3.supplyZhName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="8" :lg="7" :md="8" :sm="24">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <a-button type="primary" @click="loadData(3)" icon="search">{{ $t('查询') }}</a-button>
                <a-button type="primary" @click="searchReset3" icon="reload" style="margin-left: 8px">{{
                  $t('重置')
                }}</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :loading="loading"
        :columns="target.columns"
        :dataSource="target.dataSource"
        :pagination="target.ipagination"
        :rowSelection="{ selectedRowKeys: target.selectedRowKeys, onChange: onSelectChange3, type: 'radio' }"
        @change="TableChange3"
      />
      <a-button type="primary" :loading="loading" @click="next(breadcrumb2.length)" :disabled="target.selectedRowKeys.length == 0">
        {{ $t('确认') }}
      </a-button>
    </div>
  </j-modal>
</template> 

<script>
import { getAction, postAction } from '@/api/manage'
export default {
  name: 'CopyModal',
  components: {},
  data() {
    return {
      queryParam1: {},
      queryParam2: {},
      queryParam3: {},
      loading: false,
      // 源客户
      sourceCus: {
        selectedRowKeys: [],
        selectionRows: [],
        dataSource: [],
        columns: [
          {
            title: this.$t('供应商属性'),
            align: 'center',
            dataIndex: 'supplyAttr',
          },
          {
            title: this.$t('供应商编码'),
            align: 'center',
            dataIndex: 'supplyNo',
          },
          {
            title: this.$t('中文名称'),
            align: 'center',
            dataIndex: 'supplyZhName',
          },
        ],
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '30', '50', '100'],
          showTotal: (total, range) => {
            return range[0] + '-' + range[1] + this.$t(' 共') + total + this.$t('条')
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0,
        },
      },
      // 计费规则
      billing: {
        selectedRowKeys: [],
        selectionRows: [],
        dataSource: [],
        columns: [
          {
            title: this.$t('规则编号'),
            align: 'center',
            dataIndex: 'costRuleNo',
          },
          {
            title: this.$t('规则名称'),
            align: 'center',
            dataIndex: 'costRuleName',
          },
          {
            title: this.$t('费用编号'),
            align: 'center',
            dataIndex: 'costNo',
          },
          {
            title: this.$t('费用名称'),
            align: 'center',
            dataIndex: 'costName',
          },
        ],
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '30', '50', '100'],
          showTotal: (total, range) => {
            return range[0] + '-' + range[1] + this.$t(' 共') + total + this.$t('条')
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0,
        },
      },
      // 目标客户
      target: {
        selectedRowKeys: [],
        selectionRows: [],
        dataSource: [],
        columns: [
          {
            title: this.$t('供应商属性'),
            align: 'center',
            dataIndex: 'supplyAttr',
          },
          {
            title: this.$t('供应商编码'),
            align: 'center',
            dataIndex: 'supplyNo',
          },
          {
            title: this.$t('中文名称'),
            align: 'center',
            dataIndex: 'supplyZhName',
          },
        ],
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '30', '50', '100'],
          showTotal: (total, range) => {
            return range[0] + '-' + range[1] + this.$t(' 共') + total + this.$t('条')
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0,
        },
      },
      title: '',
      visible: false,
      type: '',
      breadcrumb: ['选择源客户', '选择计费规则', '选择到客户'],
      breadcrumb2: [],
      url: {
        sourceCusList: '/base/baseSupply/list',
        billingList: '/bms/bmsCostRuleH/list',
        targetList: '/base/baseSupply/list',
        copy: '/bms/bmsCostRuleH/jfcopy'
      },
    }
  },
  created() {
    
  },
  methods: {
    // 计费规则全部选中
    allPitchOn() {
      this.billing.selectedRowKeys = []
      this.billing.selectionRows = []
      this.billing.dataSource.map((item, index) => {
        this.billing.selectedRowKeys.push(item.id)
        this.billing.selectionRows.push(item)
      })
    },
    // 源客户table选中
    onSelectChange1(selectedRowKeys, selectionRows) {
      this.sourceCus.selectedRowKeys = selectedRowKeys
      this.sourceCus.selectionRows = selectionRows
    },
    // 源客户分页事件
    TableChange1(pagination, filters, sorter) {
      this.sourceCus.ipagination = pagination
      this.loadData(1)
    },
    // 计费规则table选中
    onSelectChange2(selectedRowKeys, selectionRows) {
      this.billing.selectedRowKeys = selectedRowKeys
      this.billing.selectionRows = selectionRows
    },
    // 计费规则分页事件
    TableChange2(pagination, filters, sorter) {
      this.billing.ipagination = pagination
      this.loadData(2)
    },
    // 目标客户table选中
    onSelectChange3(selectedRowKeys, selectionRows) {
      this.target.selectedRowKeys = selectedRowKeys
      this.target.selectionRows = selectionRows
    },
    // 目标客户分页事件
    TableChange3(pagination, filters, sorter) {
      this.target.ipagination = pagination
      this.loadData(3)
    },
    // 源客户查询重置按钮事件
    searchReset1() {
      this.queryParam1 = {}
      this.sourceCus.selectedRowKeys = []
      this.sourceCus.selectionRows = []
      this.loadData(1)
    },
    // 计费规则查询重置按钮事件
    searchReset2() {
      this.queryParam2 = {}
      this.billing.selectedRowKeys = []
      this.billing.selectionRows = []
      this.loadData(2)
    },
    // 目标客户查询重置按钮事件
    searchReset3() {
      this.queryParam3 = {}
      this.target.selectedRowKeys = []
      this.target.selectionRows = []
      this.loadData(3)
    },
    // 查询数据
    loadData(e) {
      var params = {}
      var url = ''
      if (e == 1) {
        // 查源客户
        url = this.url.sourceCusList
        params = this.queryParam1
        params.pageSize = this.sourceCus.ipagination.pageSize
        params.pageNo = this.sourceCus.ipagination.current
        params.supplyAttr  = this.type
      } else if (e == 2) {
        // 查计费规则
        params = this.queryParam2
        params.pageSize = this.billing.ipagination.pageSize
        params.pageNo = this.billing.ipagination.current
        params.costObjNo = this.sourceCus.selectionRows[0].supplyNo
        url = this.url.billingList
      } else if (e == 3) {
        // 查目标客户
        params = this.queryParam3
        params.pageSize = this.target.ipagination.pageSize
        params.pageNo = this.target.ipagination.current
        params.supplyAttr  = this.type
        url = this.url.targetList
      }
      
      this.loading = true
      getAction(url, params).then((res) => {
        this.loading = false
        if (res.success) {
          if (e == 1) {
            // 源客户
            this.sourceCus.dataSource = res.result.records
            this.sourceCus.ipagination.total = res.result.total
            this.sourceCus.ipagination.current = res.result.current
          } else if (e == 2) {
            // 计费规则
            this.billing.dataSource = res.result.records
            this.billing.ipagination.total = res.result.total
            this.billing.ipagination.current = res.result.current
          } else if (e == 3) {
            // 目标客户
            this.target.dataSource = res.result.records
            this.target.ipagination.total = res.result.total
            this.target.ipagination.current = res.result.current
          }
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    },
    // 点击下一步
    next(size) {
      console.log(123)
      if (size == 1) {
        console.log(this.sourceCus.selectionRows)
        this.breadcrumb2.push(this.breadcrumb[size])
        this.loadData(2)
      } else if (size == 2) {
        this.breadcrumb2.push(this.breadcrumb[size])
        this.loadData(3)
      } else if (size == 3) {
        this.StartCopy()
        console.log(this.sourceCus.selectionRows, this.billing.selectionRows, this.target.selectionRows)
      }
    },
    // 调用复制接口
    StartCopy(){
      let sourceCusSplitId = this.sourceCus.selectedRowKeys.join(',')
      let billingSplitId = this.billing.selectedRowKeys.join(',')
      let targetSplitId = this.target.selectedRowKeys.join(',')
      let Param = {
        query01:sourceCusSplitId,
        query02:billingSplitId,
        query03:targetSplitId,
        query04:this.type,
      }
      this.loading = true
      postAction(this.url.copy,Param).then(res=>{
        this.loading = false
        if(res.success){
          this.$message.success(res.message)
          this.close()
          this.$emit('ok')
        }else{
          this.$message.error(res.message)
        }
      })
    },
    // 点击面包屑按钮
    breadcrumbClick(index) {
      if (index == 0) {
        this.billing.selectionRows = []
        this.billing.selectedRowKeys = []
        this.target.selectionRows = []
        this.target.selectedRowKeys = []
      } else if (index == 1) {
        this.target.selectionRows = []
        this.target.selectedRowKeys = []
      } else if (index == 2) {
        return
      }
      this.breadcrumb2 = this.breadcrumb2.slice(0, index + 1)
    },
    // 打开弹窗
    open(e, title) {
      this.title = title
      this.type = e
      this.visible = true
      this.breadcrumb2 = [this.breadcrumb[0]]
      this.loadData(1)
    },
    // 关闭弹窗
    close() {
      this.visible = false
      this.queryParam1 =  {}
      this.queryParam2 =  {}
      this.queryParam3 =  {}
      this.loading =  false
      this.breadcrumb2 = [this.breadcrumb[0]]
      // 源客户
      this.sourceCus.selectedRowKeys = []
      this.sourceCus.selectionRows = []
      this.sourceCus.dataSource = []
      this.sourceCus.ipagination.current = 1
      // 计费规则
      this.billing.selectedRowKeys = []
      this.billing.selectionRows = []
      this.billing.dataSource = []
      this.billing.ipagination.current = 1
      // 目标客户
      this.target.selectedRowKeys = []
      this.target.selectionRows = []
      this.target.dataSource = []
      this.target.ipagination.current = 1
    },
  },
}
</script>

<style scoped>
.breadcrumb {
  font-size: 20px;
}
.table-page-search-wrapper {
  margin-top: 30px;
}
</style>