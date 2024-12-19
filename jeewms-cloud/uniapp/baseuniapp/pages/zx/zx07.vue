<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">{{title1}}</block>
		</cu-custom>
		<view class="Search" style="flex-direction: column;height: 	177rpx;">
			<input type="text" v-model="query01" :focus="isFocus1" @confirm="getDetails(1)" :placeholder="$t('pages.PleaseScanTheOrderNumber')" />
			<input type="text" v-model="query02" :focus="isFocus2" @confirm="getDetails(1)" :placeholder="$t('pages.PleaseScanTheTrayNumber')" style="margin-top: 16rpx;" />
		</view>
		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;" @scrolltolower='scroll'>
			<view class="body_t" v-for="(item,index) in data" :key=	'index'>
			<view class="item">
				<view>{{$t('pages.productCode')}}：</view>
				<view style="color: #2D2F2E;">{{item.query01}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.productName')}}：</view>
				<view style="color: #2D2F2E;">{{item.query02}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.productStandard')}}：</view>
				<view style="color: #2D2F2E;">{{item.query03}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.ThePalletNo')}}：</view>
				<view style="color: #2D2F2E;">{{item.query04}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.unit')}}：</view>
				<view style="color: #2D2F2E;">{{item.query05}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.amount')}}：</view>
				<view style="color: #3297E2">{{item.query06}}</view>
			</view>
			<!-- <view class="item">
				<view>托盘号：</view>
				<view style="color: #3297E2">{{item.query07}}</view>
			</view> -->
			<!--< view class="inputText" style="border-bottom: 1px dashed #DBDBDB;">
				<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;border">
					<text>托盘位：</text>
					<input style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;" type="number" value=""  placeholder-style="color:#E0E2E4"/>
				</view>
				<view class="select-input">
					<input v-model="item.query08" style=" color: #3297E2;text-align: right;padding-right: 20rpx;margin-top: 0;"   placeholder-style="color:#E0E2E4"/>
					<picker class="search-picker" @change="(e)=>{bindPickerChange(e,index,'query08',item.list01)}"  :range="item.list01" range-key="query01">
						<image style="width: 13px; height: 7px;" src="@/static/icon/jiantou.png" mode=""></image>
					</picker>
				</view>
			</view> -->
			<view class="btn" @click="preserve(item)">{{$t('pages.takeApartThePallet')}}</view>
		</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title:'',
				title1:'',
				userName:'',
				query01:'',
				query02:'',
				data:[],
				pageNo:1,
				pageSize:10,
				isFocus1: true,
				isFocus2: false,
			}
		},
		onLoad() {
			this.title = this.$Route.query.title
			this.title1 = this.$Route.query.NameKey
			this.userName = uni.getStorageSync('login_user_info').username || '';
			this.getDetails();
		},
		methods: {
			scroll(){
				this.pageNo++
				this.getDetails()
			},
			preserve(item){
				let data = {
					...item,
					username:this.userName,
					listtype:this.title
				}
				uni.showLoading({title: '加载中',mask:true})
				this.$http.post('/jeewms/pdaPapi/postdata',data).then(res => {
					uni.showToast({ title:res.data.message,icon:'none' })
					uni.hideLoading()
					if(res.data.code==200){
						this.data = []
						this.pageNo = 1
						this.getDetails()
					}
				})	
			},
			getDetails(e){
				if(e == 1){
					this.data = []
					this.isFocus1 =  false
					this.isFocus2 =  true
					this.pageNo = 1
				}
				let data = {
					pageNo:this.pageNo,
					pageSize:this.pageSize,
					username:this.userName,
					listtype:this.title,
					query01:this.query01,
					query02:this.query02
				}
				this.$http.get('/jeewms/pdaGapi/getlist',{params:data}).then(res => {
					this.data = this.data.concat(res.data.result.records)
				})	
			},
			bindPickerChange(e,i,key,list){
				let index = e.detail.value;
				this.$nextTick(()=>{
					this.$set(this.data[i], key, list[index].query01)	
				})
			}
		}
	}
</script>

<style scoped lang="less">
	.content{
		width: 100%;
		height: 100vh;
		background-color: #F3F4F6;
		overflow: hidden;
		display: flex;
		flex-direction: column;
		.Search{
			width: 100%;
			height: 101rpx;
			background: #FFFFFF;
			display: flex;
			justify-content: center;
			align-items: center;
			input{
				padding-left: 36rpx;
				width: 92.66%;
				height: 70rpx;
				background: #F3F4F6;
				border-radius: 30px;
				font-size: 28rpx;
				font-weight: 400;
			}
		}
		.body_t{
			width: 	92.53%;
			min-height: 500rpx;
			margin: 27rpx auto;
			background: #FFFFFF;
			border-radius: 10rpx;
			padding: 20rpx 33rpx;
			.title {
				width: 100%;
				height: 70rpx;
				position: relative;
				line-height: 60rpx;
				color: #3297E2;
				font-size: 34rpx;
			
				.posi_left {
					height: 30rpx;
					width: 6rpx;
					background: #3297E2;
					position: absolute;
					left: -32rpx;
					top: 15rpx;
				}
			
				.posi_right {
					height: 56rpx;
					width: 104rpx;
					background: #3297E2;
					position: absolute;
					right: -24px;
					top: 10rpx;
					font-size: 28rpx;
					line-height: 40rpx;
					color: #fff;
					text-align: center;
					background-size: 100% 100%;
					background-repeat: no-repeat;
				}
			}
			.item{
				width: 100%;
				height: 70rpx;
				display: flex;
				align-items: center;
				font-size: 28rpx;
				color: #666666;
				justify-content: space-between;
			}
			.inputText{
				margin-top: 10rpx;
				width: 100%;
				display: flex;
				justify-content: space-between;
				// border-bottom: 1px dashed #DBDBDB;
				padding-bottom: 31rpx;
				text{
					font-size: 28rpx;
					font-weight: 400;
					color: #666666;
				}
				view{
					width: 35%;
				}
				input{
					margin-top: 25rpx;
					height: 68rpx;
					border: 1px solid #3297E2;
					text-align: center;
					font-size: 28rpx;
					font-weight: 400;
					border-radius: 5px;
				}
			}
			.btn{
				margin: 16rpx auto 16rpx;
				width: 90%;
				height: 76rpx;
				background: #3396E2;
				border-radius: 10px;
				font-size: 36rpx;
				line-height: 76rpx;
				font-family: PingFang SC;
				font-weight: 400;
				text-align: center;
				color: #FFFEFE;
			}
		}
	}
	
</style>
