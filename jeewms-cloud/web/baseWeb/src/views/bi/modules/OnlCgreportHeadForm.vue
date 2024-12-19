<template>
	<a-spin :spinning="confirmLoading">
		<j-form-container :disabled="formDisabled">
			<!-- 主表单区域 -->
			<a-form :form="form" slot="detail">
				<a-row>
					<a-col :span="6">
						<a-form-item label="报表编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
							<a-input v-decorator="['code']" placeholder="请输入报表编码"></a-input>
						</a-form-item>
					</a-col>
					<a-col :span="6">
						<a-form-item label="报表名字" :labelCol="labelCol" :wrapperCol="wrapperCol">
							<a-input v-decorator="['name']" placeholder="请输入报表名字"></a-input>
						</a-form-item>
					</a-col>
					<a-col :span="6">
						<a-form-item label="动态数据源" :labelCol="labelCol" :wrapperCol="wrapperCol">
							<a-input v-decorator="['dbSource']" placeholder="请输入动态数据源"></a-input>
						</a-form-item>
					</a-col>
					<a-col :span="6">
						<a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
							<j-dict-select-tag type="list" v-decorator="['repository']" :trigger-change="true" dictCode="LargeScreenDataType"
							 placeholder="请选择类型" />
						</a-form-item>
					</a-col>
					<a-col :span="21">
						<a-form-item label="报表SQL" :labelCol="labelCol" :wrapperCol="wrapperCol">
							<textarea ref="mycode" class="codesql public_text" v-decorator="['cgrSql']"></textarea>
						</a-form-item>
					</a-col>
				</a-row>
			</a-form>
		</j-form-container>
		<!-- 子表单区域 -->
		<a-tabs v-model="activeKey" @change="handleChangeTabs">
			<a-tab-pane tab="onl_cgreport_item" :key="refKeys[0]" :forceRender="true">
		
				<j-editable-table :ref="refKeys[0]" :loading="onlCgreportItemTable.loading" :columns="onlCgreportItemTable.columns"
				 :dataSource="onlCgreportItemTable.dataSource" :maxHeight="300" :disabled="formDisabled" :rowNumber="true"
				 :rowSelection="true" :actionButton="true" />
		
			</a-tab-pane>
			<a-tab-pane tab="onl_cgreport_param" :key="refKeys[1]" :forceRender="true">
				<j-editable-table :ref="refKeys[1]" :loading="onlCgreportParamTable.loading" :columns="onlCgreportParamTable.columns"
				 :dataSource="onlCgreportParamTable.dataSource" :maxHeight="300" :disabled="formDisabled" :rowNumber="true"
				 :rowSelection="true" :actionButton="true" />
			</a-tab-pane>
		
		</a-tabs>
	</a-spin>
</template>

<script>
	import pick from 'lodash.pick'
	import {
		getAction
	} from '@/api/manage'
	import {
		FormTypes,
		getRefPromise
	} from '@/utils/JEditableTableUtil'
	import {
		JEditableTableMixin
	} from '@/mixins/JEditableTableMixin'
	import {
		validateDuplicateValue
	} from '@/utils/util'
	import JFormContainer from '@/components/jeecg/JFormContainer'
	import JDictSelectTag from "@/components/dict/JDictSelectTag"

	import CodeMirror from 'codemirror/lib/codemirror'
	import "codemirror/theme/ambiance.css";
	require("codemirror/mode/javascript/javascript");
	export default {
		name: 'OnlCgreportHeadForm',
		mixins: [JEditableTableMixin],
		components: {
			JFormContainer,
			JDictSelectTag,
			CodeMirror
		},
		data() {
			return {
				mess: '',
				labelCol: {
					xs: {
						span: 24
					},
					sm: {
						span: 6
					},
				},
				wrapperCol: {
					xs: {
						span: 24
					},
					sm: {
						span: 16
					},
				},
				labelCol2: {
					xs: {
						span: 24
					},
					sm: {
						span: 3
					},
				},
				wrapperCol2: {
					xs: {
						span: 24
					},
					sm: {
						span: 20
					},
				},
				// 新增时子表默认添加几行空数据
				addDefaultRowNum: 1,
				validatorRules: {
					code: {
						rules: [{
							required: true,
							message: '请输入报表编码!'
						}, ]
					},
					name: {
						rules: [{
							required: true,
							message: '请输入报表名字!'
						}, ]
					},
					cgrSql: {
						rules: [{
							required: true,
							message: '请输入报表SQL!'
						}, ]
					},
				},
				refKeys: ['onlCgreportItem', 'onlCgreportParam', ],
				tableKeys: ['onlCgreportItem', 'onlCgreportParam', ],
				activeKey: 'onlCgreportItem',
				onlCgreportParamTable: {
					loading: false,
					dataSource: [],
					columns: [{
							title: '动态报表ID',
							key: 'cgrheadId',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
							validateRules: [{
								required: true,
								message: '${title}不能为空'
							}],
						},
						{
							title: '参数字段',
							key: 'paramName',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
							validateRules: [{
								required: true,
								message: '${title}不能为空'
							}],
						},
						{
							title: '参数文本',
							key: 'paramTxt',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '参数默认值',
							key: 'paramValue',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '排序',
							key: 'orderNum',
							type: FormTypes.inputNumber,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '创建人登录名称',
							key: 'createBy',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '创建日期',
							key: 'createTime',
							type: FormTypes.date,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '更新人登录名称',
							key: 'updateBy',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '更新日期',
							key: 'updateTime',
							type: FormTypes.date,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
					]
				},
				// onl_cgreport_item
				onlCgreportItemTable: {
					loading: false,
					dataSource: [],
					columns: [
						// {
						// 	title: '报表ID',
						// 	key: 'cgrheadId',
						// 	type: FormTypes.input,
						// 	width: "200px",
						// 	placeholder: '请输入${title}',
						// 	defaultValue: '',
						// 	validateRules: [{
						// 		required: true,
						// 		message: '${title}不能为空'
						// 	}],
						// },
						{
							title: '字段名字',
							key: 'fieldName',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
							validateRules: [{
								required: true,
								message: '${title}不能为空'
							}],
						},
						{
							title: '字段文本',
							key: 'fieldTxt',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '字段类型',
							key: 'fieldType',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '是否显示  0否,1显示',
							key: 'isShow',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '字段跳转URL',
							key: 'fieldHref',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '查询模式',
							key: 'searchMode',
							type: FormTypes.select,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
							options: [{
									title: '单条件查询',
									value: '单条件查询'
								},
								{
									title: '范围查询',
									value: '范围查询'
								},
							],
						},
						{
							title: '取值表达式',
							key: 'replaceVal',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '字典CODE',
							key: 'dictCode',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '分组标题',
							key: 'groupTitle',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '是否查询  0否,1是',
							key: 'isSearch',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						},
						{
							title: '是否合计 0否,1是（仅对数值有效）',
							key: 'isTotal',
							type: FormTypes.input,
							width: "200px",
							placeholder: '请输入${title}',
							defaultValue: '',
						}
					]
				},
				url: {
					add: "/onl_cgreport_head/onlCgreportHead/add",
					edit: "/onl_cgreport_head/onlCgreportHead/edit",
					queryById: "/onl_cgreport_head/onlCgreportHead/queryById",
					querySQL: '/online/cgreport/head/parseSql',
					onlCgreportParam: {
						list: '/onl_cgreport_head/onlCgreportHead/queryOnlCgreportParamByMainId'
					},
					onlCgreportItem: {
						list: '/onl_cgreport_head/onlCgreportHead/queryOnlCgreportItemByMainId'
					},
				}
			}
		},
		props: {
			//流程表单data
			formData: {
				type: Object,
				default: () => {},
				required: false
			},
			//表单模式：false流程表单 true普通表单
			formBpm: {
				type: Boolean,
				default: false,
				required: false
			},
			//表单禁用
			disabled: {
				type: Boolean,
				default: false,
				required: false
			}
		},
		computed: {
			formDisabled() {
				if (this.formBpm === true) {
					if (this.formData.disabled === false) {
						return false
					}
					return true
				}
				return this.disabled
			},
			showFlowSubmitButton() {
				if (this.formBpm === true) {
					if (this.formData.disabled === false) {
						return true
					}
				}
				return false
			}
		},
		created() {
			//如果是流程中表单，则需要加载流程表单data
			this.showFlowData();
		},
		mounted() {
			this.editor = CodeMirror.fromTextArea(this.$refs.mycode, {
				mode: "text/x-mysql",
				theme: "ambiance",
				readOnly: false,
			})
		},
		methods: {
			addBefore() {
				this.form.resetFields()
				this.onlCgreportParamTable.dataSource = []
				this.onlCgreportItemTable.dataSource = []
			},
			getAllTable() {
				let values = this.tableKeys.map(key => getRefPromise(this, key))
				return Promise.all(values)
			},
			/** 调用完edit()方法之后会自动调用此方法 */
			editAfter() {
				this.editor.setValue(this.model.cgrSql)
				let fieldval = pick(this.model, 'code', 'name', 'dbSource','repository', 'cgrSql')
				this.$nextTick(() => {
					this.form.setFieldsValue(fieldval)
				})
				// 加载子表数据
				if (this.model.id) {
					let params = {
						id: this.model.id
					}
					this.requestSubTableData(this.url.onlCgreportParam.list, params, this.onlCgreportParamTable)
					this.requestSubTableData(this.url.onlCgreportItem.list, params, this.onlCgreportItemTable)
				}
			},
			/** 整理成formData */
			classifyIntoFormData(allValues) {
				let main = Object.assign(this.model, allValues.formValue)
				return {
					...main, // 展开
					onlCgreportParamList: allValues.tablesValue[0].values,
					onlCgreportItemList: allValues.tablesValue[1].values,
				}
			},
			//渲染流程表单数据
			showFlowData() {
				if (this.formBpm === true) {
					let params = {
						id: this.formData.dataId
					};
					getAction(this.url.queryById, params).then((res) => {
						if (res.success) {
							this.edit(res.result);
						}
					})
				}
			},
			validateError(msg) {
				this.$message.error(msg)
			},
			popupCallback(row) {
				this.form.setFieldsValue(pick(row, 'code', 'name', 'dbSource','repository', 'cgrSql'))
			},
			// SQL解析按钮
			SQLAnalysis() {
				console.log(typeof this.editor.getValue())
				// this.form.setFieldsValue({
				// 	cgrSql:this.editor.getValue()
				// })
				let data = {
					code: "",
					dbSource: "",
					name: "",
					sql: this.editor.getValue()
				}
				this.form.validateFields((err, value) => {
					data.code = value.code
					data.dbSource = value.dbSource
					data.name = value.name
				})
				getAction(this.url.querySQL, data).then((res) => {
					console.log(res)
					if (res.success) {
						this.onlCgreportItemTable.dataSource = res.result.fields
					}
				})
			},	
		}
	}
</script>

<style scoped>
</style>
