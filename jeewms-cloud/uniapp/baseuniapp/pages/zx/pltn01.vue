<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">{{title1}}</block>
		</cu-custom>
		<u-form :model="form" ref="uForm" class="uForm">
			<u-form-item label="子PO" label-width='140' prop="query05">
				<u-input v-model="form.query05" :placeholder="$t('请输入子PO')" />
			</u-form-item>
			<u-form-item label="vendor" label-width='140' prop="query19">
				<view class="formItemBody2"  :style="{color:form.query19?'#000':'#bbb'}" @click="getDictItems">
					{{form.query19?form.query19:$t('请选择')}}
				</view>
			</u-form-item>
			<u-form-item label="费用描述" label-width='140' prop="query06">
				<u-input v-model="form.query06" :placeholder="$t('请输入费用描述')" />
			</u-form-item>
			<u-form-item label="费用金额" label-width='130' prop="query10">
				<u-input v-model="form.query10" :placeholder="$t('请输入费用')" />
			</u-form-item>
			<u-form-item label="日期" label-width='130' prop="query07">
				<picker class="pickerdate" mode="date"  start="1999-01-01" end="2099-12-30" fields="day" @change="bindDateChange">
					<view class="uni-input"  :style="{color:form.query07?'#000':'#bbb'}">{{form.query07?form.query07:'选择日期'}}</view>
				</picker>
			</u-form-item>
			<u-form-item label="年月" label-width='130' prop="query08">
				<u-input v-model="form.query08" :placeholder="$t('请输入年月')" disabled/>
			</u-form-item>
			<u-button type="primary" @click="preserve" >提交</u-button>
		</u-form>
		<u-select v-model="uSelectShow" mode="single-column" :list="uSelectList"  @selectConfirm="selectConfirm"></u-select>
	</view>
</template>

<script>
export default {
	data() {
		return {
			title:"",
			title1:'',
			userName:"",
			form: {
			},
			TOKEN:uni.getStorageSync('Access-Token'),
			action: uni.getStorageSync('base_url') + '/sys/common/upload',
			fileList: [],
			urlImg:[],
			uSelectShow:false,
			uSelectList:[],
			rules: {
				query05: [{required: true, message: '请输入子PO',trigger: 'change'}],
				query06: [{required: true, message: '费用描述不能为空',trigger: 'change'}],
				query07: [{required: true, message: '日期不能为空',trigger: 'change'}],
				query08: [{required: true, message: '年月不能为空',trigger: 'change'}],
				query19: [{required: true, message: 'vendor',trigger: 'change'}],
				query10: [{required: true, message: '费用金额不能为空或格式错误',trigger: 'change',type:"number"}],
			},
			switchVal: false
		};
	},
	onLoad() {
		this.title = this.$Route.query.title
		this.title1 = this.$Route.query.NameKey
		this.userName = uni.getStorageSync('login_user_info').username || '';
	},
	onReady() {
		this.$refs.uForm.setRules(this.rules);
	},
	methods: {
		getDictItems(e){
			this.$http.get('/sys/dict/getDictItems/vendor').then(res => {
				if (res.data.result.length > 0) {
					let arr = []
					res.data.result.map(item=>{
						arr.push({value: item.value, label: item.value })
					})
					this.uSelectList = arr
					this.uSelectShow = true
				}
			})
		},
		uInputClick2(){
			this.uSelectShow = true
		},
		selectConfirm(e){
			this.form.query19 = e[0].value
		},
		bindDateChange(e) {
			if(e.detail.value){
				this.$set(this.form,'query07',e.detail.value)
				let arr = e.detail.value.split("-");
				this.$set(this.form,'query08',arr[0].substring(2,4)+arr[1])
			}
		},
		uploadSuccess(data, index, lists, name){
			if(data.success){
				this.urlImg.push(data.message)
			}
		},
		preserve(){
			this.$refs.uForm.validate(valid => {
				if (valid) {
					let data = {
						...this.form,
						attr1:this.urlImg.join(","),
						username: this.userName,
						listtype: 'QCFY'
					};
					uni.showLoading({ title: '加载中', mask: true })
					this.$http.post('/jeewms/pdaPapi/postdata', data).then(res => {
						uni.showToast({ title: res.data.message, icon: 'none' });
						if (res.data.code == 200) {
							this.form = {}
							this.urlImg = [];
						}
					})
				} else {
					console.log('验证失败');
				}
			});
		},
	}
};
</script>
<style scoped lang="less">
	.content {
		width: 100%;
		height: 100vh;
		background-color: #F3F4F6;
		overflow: hidden;
		display: flex;
		flex-direction: column;
		background-color: #fff;
		.uForm{
			width: 95%;
			margin: 0 auto;
			/deep/ .u-form-item{
				padding: 2rpx 0;
			}
			/deep/ .u-list-item{
				width: 40rpx;
				height: 40rpx;
			}
			.formItemBody{
				width: 100%;
				height: 100%;
			}
			.formItemBody2{
				flex: 1;
				margin-left: 10rpx;
			}
		}
	}
</style>