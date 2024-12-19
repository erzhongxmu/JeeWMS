<template>
    <j-modal :title="title" :width="width" :visible="visible" switchFullscreen @ok="handleOk" @cancel="close"
        :maskClosable="false">
        <a-spin :spinning="confirmLoading">
            <div class="table-page-search-wrapper">
                <a-form layout="inline" @keyup.enter.native="searchQuery">
                    <a-row :gutter="24">
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item :label="$t('主PO号')">
                                <a-input :placeholder="$t('请输入主PO号')" v-model="queryParam.query13"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item :label="$t('子PO号')">
                                <a-input :placeholder="$t('请输入子PO号')" v-model="queryParam.query14"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item :label="$t('客户')">
                                <j-popup v-model="queryParam.query24" field="query08"
                                    org-fields="ke_hu_jian_cheng,ke_hu_bian_ma" dest-fields="query09,query08"
                                    code="md_cus" :multi="false" />
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item :label="$t('销售单号')">
                                <a-input :placeholder="$t('请输入销售单号')" v-model="queryParam.link03"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item :label="$t('内部发票号')">
                                <a-input :placeholder="$t('请输入内部发票号')" v-model="queryParam.query17"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item :label="$t('PAYMENT LIST')">
                                <a-input :placeholder="$t('请输入PAYMENT LIST')" v-model="queryParam.query19"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item :label="$t('商品编码')">
                                <a-input :placeholder="$t('请输入商品编码')" v-model="queryParam.query10"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item :label="$t('商品名称')">
                                <a-input :placeholder="$t('请输入商品名称')" v-model="queryParam.query11"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item :label="$t('供应商')">
                                <a-input :placeholder="$t('请输入供应商')" v-model="queryParam.query08"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                                <a-button type="primary" @click="searchQuery" icon="search">{{ $t("查询") }}</a-button>
                                <a-button type="primary" @click="searchReset" icon="reload"
                                    style="margin-left: 8px">{{ $t("重置") }}</a-button>
                            </span>
                        </a-col>
                    </a-row>
                </a-form>
            </div>
            <a-table ref="table" size="middle" :scroll="{ x: true }" bordered rowKey="id" :columns="columns"
                :dataSource="dataSource" :pagination="ipagination" :loading="loading"
                :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
                class="j-table-force-nowrap" @change="handleTableChange">
                <template slot="input" slot-scope="text,record">
                    <a-input v-model="record.num18" />
                </template>
            </a-table>
        </a-spin>

        <j-modal :title="$t('确认付款数据')" :width="800" :visible="visible2" switchFullscreen @ok="handleOk2"
            @cancel="close2" cancelText="关闭" :maskClosable="false" :confirm-loading="confirmLoading2">
            <a-spin :spinning="confirmLoading2">
                <div class="table-page-search-wrapper">
                    <a-form layout="inline" @keyup.enter.native="searchQuery">
                        <a-row :gutter="24">
                            <!-- <a-col :xl="12" :lg="7" :md="8" :sm="24">
                            <a-form-item :label="$t('PAYMENT LIST') ">
                                <a-input :placeholder="$t('请输入PAYMENT') LIST" v-model="model.query19"></a-input>
                            </a-form-item>
                        </a-col> -->
                            <a-col :xl="12" :lg="7" :md="8" :sm="24">
                                <a-form-item :label="$t('本次付款比例%')">
                                    <a-input :placeholder="$t('请输入')" v-model="model2.query01"
                                        @input="query02Input"></a-input>
                                </a-form-item>
                            </a-col>
                            <a-col :xl="12" :lg="7" :md="8" :sm="24">
                                <a-form-item :label="$t('本次付款总金额')">
                                    <a-input :placeholder="$t('请输入')" v-model="model2.query02" disabled></a-input>
                                </a-form-item>
                            </a-col>
                            <a-col :xl="12" :lg="7" :md="8" :sm="24">
                                <a-form-item :label="$t('剩余应付总金额')">
                                    <a-input :placeholder="$t('请输入')" v-model="model2.query03" disabled></a-input>
                                </a-form-item>
                            </a-col>
                            <a-col :xl="12" :lg="7" :md="8" :sm="24">
                                <a-form-item :label="$t('付款时间')">
                                    <j-date v-model="datequery05" :placeholder="$t('请选择付款时间')"
                                        @change="query05Change" />
                                </a-form-item>
                            </a-col>
                            <a-col :xl="12" :lg="7" :md="8" :sm="24">
                                <a-form-item :label="$t('付款账号')">
                                    <j-search-select-tag v-model="model2.query32" dict="payment_account_number">
                                    </j-search-select-tag>
                                </a-form-item>
                            </a-col>
                            <a-col :xl="12" :lg="7" :md="8" :sm="24">
                                <a-form-item :label="$t('备注')">
                                    <a-input :placeholder="$t('请输入备注')" v-model="model.text01"></a-input>
                                </a-form-item>
                            </a-col>
                        </a-row>
                    </a-form>
                </div>
                <a-table ref="table" size="middle" :scroll="{ x: true }" bordered rowKey="id" :columns="columns2"
                    :dataSource="dataSource2" :loading="loading2" class="j-table-force-nowrap">
                    <template slot="input" slot-scope="text,record">
                        <a-input v-model="record.num18" @input="query02Input2" />
                    </template>
                </a-table>
            </a-spin>
        </j-modal>
    </j-modal>
</template>

<script>

import { getAction, postAction } from '@/api/manage'
import { getCurrentTime } from '@/utils/util'
export default {
    name: 'addProformaInvoice',
    components: {
    },
    data() {
        return {
            datequery05: "",
            title: '',
            width: 1300,
            model: {},
            visible: false,
            visible2: false,
            loading2: false,
            loading: false,
            confirmLoading2: false,
            confirmLoading: false,
            selectedRowKeys: [],
            selectionRows: [],
            dataSource: [],
            dataSource2: [],
            model2: {
                query01: 100,
                query02: 0,
                query03: 0
            },
            columns2: [
                {
                    title: this.$t('本次付款金额'),
                    align: 'center',
                    dataIndex: 'num18',
                    scopedSlots: { customRender: 'input' },
                },
                {
                    title: this.$t('剩余应付金额'),
                    align: 'center',
                    dataIndex: 'num17',
                },
                {
                    title: this.$t('主PO'),
                    align: 'center',
                    dataIndex: 'query13',
                },
                {
                    title: this.$t('子PO'),
                    align: 'center',
                    dataIndex: 'query14',
                },
                {
                    title: this.$t('客户'),
                    align: 'center',
                    dataIndex: 'query24_dictText',
                },
                {
                    title: this.$t('供应商'),
                    align: 'center',
                    dataIndex: 'query08',
                },
                {
                    title: this.$t('销售单号'),
                    align: 'center',
                    dataIndex: 'link03',
                },
                {
                    title: this.$t('内部发票号'),
                    align: 'center',
                    dataIndex: 'query17',
                },
                {
                    title: this.$t('PAYMENT LIST'),
                    align: 'center',
                    dataIndex: 'query19',
                },
                {
                    title: this.$t('商品编码'),
                    align: 'center',
                    dataIndex: 'query10',
                },
                {
                    title: this.$t('商品名称'),
                    align: 'center',
                    dataIndex: 'query11',
                },
                {
                    title: this.$t('数量'),
                    align: 'center',
                    dataIndex: 'num01',
                },
                {
                    title: this.$t('单位'),
                    align: 'center',
                    dataIndex: 'query12',
                },
                {
                    title: this.$t('单价'),
                    align: 'center',
                    dataIndex: 'num04',
                },
                {
                    title: this.$t('金额'),
                    align: 'center',
                    dataIndex: 'num07',
                },
                {
                    title: this.$t('币种'),
                    align: 'center',
                    dataIndex: 'query22',
                },
                // {
                //     title: this.$t('关联单号'),
                //     align: 'center',
                //     dataIndex: 'query04',
                // },
            ],
            columns: [
                {
                    title: this.$t('主PO'),
                    align: 'center',
                    dataIndex: 'query13',
                },
                {
                    title: this.$t('子PO'),
                    align: 'center',
                    dataIndex: 'query14',
                },
                {
                    title: this.$t('客户'),
                    align: 'center',
                    dataIndex: 'query24_dictText',
                },
                {
                    title: this.$t('供应商'),
                    align: 'center',
                    dataIndex: 'query08',
                },
                {
                    title: this.$t('金额'),
                    align: 'center',
                    dataIndex: 'num07',
                },
                {
                    title: this.$t('剩余应付金额'),
                    align: 'center',
                    dataIndex: 'num17',
                },
                {
                    title: this.$t('内部发票号'),
                    align: 'center',
                    dataIndex: 'query17',
                },
                {
                    title: this.$t('PAYMENT LIST'),
                    align: 'center',
                    dataIndex: 'query19',
                },
                {
                    title: this.$t('商品编码'),
                    align: 'center',
                    dataIndex: 'query10',
                },
                {
                    title: this.$t('商品名称'),
                    align: 'center',
                    dataIndex: 'query11',
                },
                {
                    title: this.$t('数量'),
                    align: 'center',
                    dataIndex: 'num01',
                },
                {
                    title: this.$t('单位'),
                    align: 'center',
                    dataIndex: 'query12',
                },
                {
                    title: this.$t('单价'),
                    align: 'center',
                    dataIndex: 'num04',
                },
                {
                    title: this.$t('币种'),
                    align: 'center',
                    dataIndex: 'query22',
                },
                {
                    title: this.$t('销售单号'),
                    align: 'center',
                    dataIndex: 'link03',
                },
                // {
                //     title: this.$t('关联单号'),
                //     align: 'center',
                //     dataIndex: 'query04',
                // },
            ],
            ipagination: {
                current: 1,
                pageSize: 20,
                pageSizeOptions: ['10', '20', '30', '50', '100'],
                showTotal: (total, range) => {
                    return range[0] + '-' + range[1] + '  ' + ' ' + this.$t('共') + ' ' + total + ' ' + this.$t('项目')
                },
                showQuickJumper: true,
                showSizeChanger: true,
                total: 0
            },
            queryParam: {},
        }
    },
    computed: {
        formDisabled() {
            return this.disabled
        },
    },

    methods: {
        query02Input() {
            if (this.model2.query01) {
                let n = this.model2.query01 / 100
                this.dataSource2.map((item, index) => {
                    item.num18 = item.num17 * n
                })
                this.query02Input2()
            }
        },
        query02Input2() {
            let query02 = 0
            this.dataSource2.map((item, index) => {
                query02 = Number(query02) + Number(item.num18)
            })
            this.model2.query02 = query02
        },
        query05Change(e) {
            this.$set(this.model, 'query05', e)
        },
        searchReset() {
            this.queryParam = {}
            this.ipagination.current = 1
            this.selectedRowKeys = []
            this.selectionRows = []
            this.loadData()
        },
        onSelectChange(selectedRowKeys, selectionRows) {
            this.selectedRowKeys = selectedRowKeys
            this.selectionRows = selectionRows
        },
        searchQuery() {
            this.ipagination.current = 1
            this.loadData()
        },
        loadData() {
            let obj = { pageNo: this.ipagination.current, pageSize: this.ipagination.pageSize, ...this.queryParam }
            getAction("/jeeerp/vOmsPayWq/list", obj).then(res => {
                if (res.success) {
                    let query02 = 0
                    let query03 = 0
                    res.result.records.map((item, index) => {
                        item.num18 = item.num17
                    })
                    this.dataSource = res.result.records
                    this.ipagination.total = res.result.total
                }
            })
        },
        handleTableChange(pagination, filters, sorter) {
            //分页、排序、筛选变化时触发
            //TODO 筛选
            if (Object.keys(sorter).length > 0) {
                this.isorter.column = sorter.field
                this.isorter.order = 'ascend' == sorter.order ? 'asc' : 'desc'
            }
            this.ipagination = pagination
            this.loadData()
        },
        valueChange(e) {
            if (e.row.num01 && e.row.num04) {
                this.getAllTable().then(tables => {
                    tables[0].setValues([])
                })
            }
        },
        add() {
            this.title = '付款'
            this.visible = true
            this.loadData()
        },
        edit(record) {
            this.visible = true
        },
        close() {
            this.ipagination.current = 1
            this.ipagination.pageSize = 20
            this.visible = false
            this.visible2 = false
            this.loading2 = false
            this.loading = false
            this.confirmLoading = false
            this.selectedRowKeys = []
            this.selectionRows = []
            this.dataSource = []
            this.dataSource2 = []
        },
        handleOk() {
            this.datequery05 = getCurrentTime()
            let obj = { pageNo: 1, pageSize: 20000, ids: this.selectedRowKeys.join(',') }
            getAction("/jeeerp/vOmsPayWq/list2", obj).then(res => {
                if (res.success) {
                    let query02 = 0
                    let query03 = 0
                    let inr = -1
                    res.result.map((item, index) => {
                        item.num18 = item.num17
                        query02 = query02 + item.num18
                        query03 = query03 + item.num17
                        if(!item.query19){
                            inr = item.query14
                        }
                    })
                    this.model2.query02 = query02
                    this.model2.query03 = query03
                    var query09 = res.result.some(v => {
                        return v.query09 != res.result[0].query09
                    })
                    var query22 = res.result.some(v => {
                        return v.query22 != res.result[0].query22
                    })
                    if(inr != -1){
                       return this.$message.info(this.$t(inr + '：PAYMENT LIST 为空请确认'))
                    }
                    if (!query09 && !query22) {
                        this.dataSource2 = res.result;
                        this.model.query19 = this.dataSource2[0].query19
                        this.model.text01 = this.dataSource2[0].text01
                        this.visible2 = true
                    } else {
                        this.$message.info(this.$t('请选择相同供应商和币种'))
                    }
                }
            })

        },

        isAllEqual(array, attr) {
            if (array.length > 0) {
                return !array.some(function (value, index) {
                    return value.attr !== array[0].attr;
                });
            } else {
                return true;
            }
        },
        handleOk2() {
            let data = JSON.parse(JSON.stringify(this.dataSource2));
            // if(!this.model.query19) return this.$message.warning(this.$t("PAYMENT LIST不能为空"))
            let str = ""
            let str2 = ""
            console.log(this.model);
            let num = 0
            let num2 = 0
            // data.map((item,index)=>{
            //     num += Number(item.num18)
            // })
            data.map((item, index) => {
                num2 += Number(item.num17)
            })
            data.map((item, index) => {
                item.num07 = item.num18
                item.num08 = Number(item.num17) - Number(item.num18)
                item.num09 = item.num18
                item.query05 = this.model.query05
                item.query19 = this.model.query19
                item.text01 = this.model.text01
                item.query32 = this.model2.query32
                item = { ...item, ...this.model }
                if (isNaN(item.num18)) {
                    str2 = item.query14
                }
                // let regPos = /^[0-9]+(.[0-9]{2})?$/ //判断是否是数字。
                // if(!regPos.test(item.num18)){
                //     str2 = item.query14
                // }
                if (!item.num18) {
                    str2 = item.query14
                }
                if (Number(item.num18) > Number(item.num17)) {
                    str = item.query14
                }
            })
            if (str2) return this.$message.warning('请检查子PO' + str + "金额输入格式错误")
            if (str) return this.$message.warning('请检查子PO' + str + "金额是否是否大于总金额")
            let data2 = {
                postType: "FKD",
                list01: data
            }
            this.confirmLoading2 = true
            postAction("/jeeerp/busiPaymentReceived/postdata", data2).then(res => {
                if (res.success) {
                    this.$emit("ok")
                    this.close()
                    this.$message.success(this.$t('操作成功'))
                } else {
                    this.$message.warning(this.$t('操作失败'))
                }
                this.confirmLoading2 = false
            })
            console.log(this.dataSource2);
        },
        close2() {
            this.visible2 = false
            this.loading2 = false
            this.dataSource2 = []
        },
    }
}
</script>