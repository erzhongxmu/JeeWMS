<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">{{title1}}</block>
		</cu-custom>
		<view class="Search" >
			<view class="saoma_father1" >
				<input class="input_first" type="text" :focus="isFocus1" v-model="query01"  @confirm="getDetails(1)" :placeholder="$t('pages.请扫描箱号')" />
				<image @click="scanClick('query01')" src="../../static/ZX/saoma.png" class="saoma" ></image>
			</view>
		</view>
		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;" @scrolltolower='scroll'>
			<view class="body_t" v-for="(item,index) in data" :key='index'>
				<view class="item">
					<view>{{$t('pages.供应商')}}：</view>
					<view style="color: #2D2F2E;">{{item.query01 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.采购名')}}：</view>
					<view style="color: #2D2F2E;">{{item.query02 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.PO号')}}：</view>
					<view style="color: #2D2F2E;">{{item.query03 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.下单日期')}}：</view>
					<view style="color: #2D2F2E;">{{item.query04 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.出入库情况')}}：</view>
					<view style="color: #2D2F2E;">{{item.query05 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.验货情况')}}：</view>
					<view style="color: #2D2F2E;">{{item.query06 || ''}}</view>
				</view>
				<view class="item">
					<view>{{$t('pages.付款情况')}}：</view>
					<view style="color: #2D2F2E;">{{item.query07 || ''}}</view>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: '',
				title1: '',
				data: [{}],
				query01: '',
				query02: '',
				userName: '',
				pageNo: 1,
				pageSize: 10,
				isFocus1: true,
				isFocus2: false,
			}
		},
		onLoad() {
			this.title = this.$Route.query.title
			this.title1 = this.$Route.query.NameKey
			this.userName = uni.getStorageSync('login_user_info').username || '';
		},
		methods: {
			scanClick(e){
				let that = this
				uni.scanCode({
				    onlyFromCamera: false,
				    scanType: ['qrCode', 'barCode'], // 扫描的类型
				    success: function (res) {
						that.query01 = res.result
						that.getDetails()
				    },
				    fail: function (err) {
				        console.error('扫码失败：', err);
				    },
				    complete: function () {
				        console.log('扫码操作完成');
				    }
				});
			},
			getDetails() {
				if (!this.query01) return uni.showToast({ title: '请扫描箱号', icon: 'none' })
				let data = {
					username: this.userName,
					listtype: 'SAOMIAOXIANGHAOCHAXUN',
					query01: this.query01,
					pageSize:1
				}
				this.$http.get('/jeewms/pdaGapi/getlist', {
					params: data
				}).then(res => {
					this.data = res.data.result.records
				})
			},
		}
	}
</script>

<style scoped lang="less">
	.content {
		width: 100%;
		height: 100vh;
		overflow: hidden;
		display: flex;
		flex-direction: column;
		background-color: #F3F4F6;
		.Search {
			display: flex;
			flex-direction: column;
			width: 100%;
			height: 107rpx;
			background: #FFFFFF;
			display: flex;
			justify-content: center;
			align-items: center;
			.saoma_father1 {
				display: flex;
				width: 92.5%;
				.saoma {
					width: 55rpx;
					height: 55rpx; 
					margin-left: 10rpx;
					margin-top: 10rpx;
				}
				.input_first {
					flex: 1;
				}
			}
			.saoma_father2 {
				display: flex;
				width: 92.5%;
				.saoma {
					width: 55rpx;
					height: 55rpx; 
					margin-left: 10rpx;
					margin-top: 25rpx;
					
				}
				
				.input_second {
					flex: 1;
					margin-top: 16rpx;
					
				}
				
			}
		
			input {
				padding-left: 36rpx;
				width: 92.66%;
				height: 70rpx;
				background: #F3F4F6;
				border-radius: 30px;
				font-size: 28rpx;
				font-weight: 400;
			}
		}

		.body_t {
			width: 92.53%;
			min-height: 500rpx;
			margin: 27rpx auto;
			background: #FFFFFF;
			border-radius: 10rpx;
			padding: 20rpx 33rpx;

			.item {
				width: 100%;
				height: 70rpx;
				display: flex;
				align-items: center;
				font-size: 28rpx;
				color: #666666;
				justify-content: space-between;
			}

			.inputText {
				width: 100%;
				display: flex;
				justify-content: space-between;
				// border-bottom: 1px dashed #DBDBDB;
				padding-bottom: 15rpx;

				text {
					font-size: 28rpx;
					font-weight: 400;
					color: #666666;
				}

				view {
					width: 35%;
				}

				input {
					margin-top: 25rpx;
					height: 68rpx;
					border: 1px solid #3297E2;
					text-align: center;
					font-size: 28rpx;
					font-weight: 400;
					border-radius: 5px;
				}
			}

			.btn {
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
