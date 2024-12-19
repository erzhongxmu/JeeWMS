<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">{{$t('pages.SetOfSupporting')}}</block>
		</cu-custom>
		<view class="body_t body_s">
			<view class="item">
				<view>{{$t('pages.ConsignNum')}}：</view>
				<view style="color: #2D2F2E;">{{obj.query01 || ''}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.productCode')}}：</view>
				<view style="color: #2D2F2E;">{{obj.query02 || ''}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.productName')}}：</view>
				<view style="color: #2D2F2E;">{{obj.query03 || ''}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.productStandard')}}：</view>
				<view style="color: #2D2F2E;">{{obj.query04 || ''}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.amount')}}：</view>
				<view style="color: #2D2F2E;">{{obj.query05 || ''}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.NumberOfEntrustedGroups')}}：</view>
				<view style="color: #2D2F2E;">{{obj.query06 || ''}}</view>
			</view>
		</view>
		
			
		
		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;" @scrolltolower='scroll'>
			<view class="body_t" v-for="(item,index) in data" :key=	'index'>
				<view class="item">
					<view>U8生产单号：</view>
					<view style="color: #2D2F2E;">{{item.query01 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.productCode')}}：</view>
					<view style="color: #2D2F2E;">{{item.query02 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.productName')}}：</view>
					<view style="color: #2D2F2E;">{{item.query03 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.productStandard')}}：</view>
					<view style="color: #2D2F2E;">{{item.query04 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.unit')}}：</view>
					<view style="color: #2D2F2E;">{{item.query05 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.batchNumber')}}：</view>
					<view style="color: #2D2F2E;">{{item.query06 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.amount')}}：</view>
					<view style="color: #2D2F2E;">{{item.query07 || ''}}</view>
				</view>
				<view class="inputText" >
					<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;border">
						<text>{{$t('pages.GroupTowerTray')}}：</text>
						<input v-model="item.query08" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;" type="text"   placeholder-style="color:#E0E2E4"/>
					</view>
				</view>
				<view class="inputText" >
					<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;border">
						<text>{{$t('pages.NumberOfGroupsSupporting')}}：</text>
						<input v-model="item.query09" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;" type="number"   placeholder-style="color:#E0E2E4"/>
					</view>
				</view>
				<view class="btn" @click="preserve(item)">{{$t('pages.GroupHoldsSave')}}</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title:'',
				data:[],
				obj:{},
				query01:'',
				query02:'',
				userName:'',
				pageNo:1,
				pageSize:10,
			}
		},
		onLoad() {
			this.obj = this.$Route.query.data || {}
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
					listtype:'直拨组托',
					query20:this.obj.id,
				}
				uni.showLoading({title: '加载中',mask:true})
				this.$http.post('/jeewms/pdaPapi/postdata',data).then(res => {
					uni.showToast({ title:res.data.message,icon:'none' })
					uni.hideLoading()
					if(res.data.code==200){
						this.$router.back(-1)
						this.data = []
						this.pageNo = 1
						this.getDetails()
					}
				})	
			},
			getDetails(e){
				if(e == 1){
					this.data = []
					this.pageNo = 1
				}
				let data = {
					pageNo:this.pageNo,
					pageSize:this.pageSize,
					username:this.userName,
					listtype:'直拨组托库存',
					query01:this.obj.query02
					// query01:this.query01,
					// query02:this.query02
				}
				this.$http.get('/jeewms/pdaGapi/getlist',{params:data}).then(res => {
					 this.data = this.data.concat(res.data.result.records)
				})
			},
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
