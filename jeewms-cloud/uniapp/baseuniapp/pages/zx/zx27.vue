<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<!-- <block slot="content">扫描统计</block> -->
			<block slot="content">{{title1}}</block>
		</cu-custom>
		<view class="Search" style="flex-direction: column;height: 	55px;">
			<input type="text" value="" :focus="isFocus1" v-model="query01" @confirm="getDetails(1)"
				:placeholder="$t('请扫描')" />
		</view>
		<view class="Total">
			<view class="scanTotal" :style="{'color':computedColor(totalNum)}">
				{{$t('总数')}}：{{totalNum}}
			</view>
			<view class="delTotal">
				{{$t('删除数量')}}：{{delArr.length}}
			</view>
		</view>
		<!-- @scrolltolower='scroll' -->
		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;">


			<!-- <view>这里是未滑动之前显示的内容</view> -->
			<view class="body_t">
				<uni-swipe-action style="width:100%">
					<view class="item" v-for="(item,index) in data" :key='index'>
						<uni-swipe-action-item style="width:100%">
							<view class="sonItem">
								<view class="scanContent" style="color: #2D2F2E;">{{item.binId || ''}}</view>
								<view class="scanTime" style="color: #2D2F2E;">{{item.scanningTime || ''}}</view>
							</view>
							<template v-slot:right>
								<view class="slot-button" @click="onClick({content:item,index:index})">{{$t('删除')}}</view>
							</template>
						</uni-swipe-action-item>   
					</view>
				</uni-swipe-action>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import uniSwipeAction from 'components/swiper/uni-swipe-action/uni-swipe-action';
	import uniSwipeActionItem from 'components/swiper/uni-swipe-action-item/uni-swipe-action-item';

	export default {
		data() {
			return {
				title: '',
				title1: '',
				Img: require('@/static/ZX/wsh.png'),
				userName: '',
				pageNo: 1,
				pageSize: 10,
				isFocus1: true,
				data: [],
				query01: '', // 扫描内容
				query02: '', // 扫描时间2022-7-8 10:17:50
				totalNum: 0, // 扫描总数
				delArr: [] // 删除数量

			}
		},
		components: {
			uniSwipeAction,
			uniSwipeActionItem
		},
		onLoad() {
			this.title = this.$Route.query.title
			this.title1 = this.$Route.query.NameKey
			this.userName = uni.getStorageSync('login_user_info').username || '';
		},
		
		computed:{
			computedColor() {
				return (totalNum) => {
					let color = ''
					if(totalNum <= 10) {
						return color = '#48AAF0' 
					} else if (totalNum > 20 && totalNum < 30) {
						return color = '#F1BE0D'
					} else if (totalNum > 30 && totalNum < 50) {
						return color = '#CD2F2F'
					} else if (totalNum > 50){
						return color = '#58c8b9'
					}
					
				}
			},
			
		},
		
		methods: {
			onClick(e) {
				// 点击选项按钮时触发事件	
				//e = {content,index} ，content（点击内容）、index（下标）、position (位置信息)
				this.delArr.push(e)
				let data = {
					id: e.content.id,
					binId: e.content.binId,
					status: 0
				}
				this.$http.put('/jeewms/wmCartonNumber/edit', data).then(res => {
					this.data.map((item,index) =>{
						if(item.id == e.content.id){
							this.data.splice(index,1)
						}
					})
					this.totalNum = this.data.length
					
				})	
			},

			getDetails(e) {
				if (!this.query01) return uni.showToast({title: '请扫描箱号',icon:"none"})
				let data = {
					binId: this.query01,
					status: 1
				}	
				this.$http.post('/jeewms/wmCartonNumber/add', data).then(res => {
					uni.showToast({title: res.data.message,	icon:"none"})
					if(res.data.success){
						let obj = {}
						obj = res.data.result.result
						this.data.push(obj)
						this.totalNum = this.data.length
						this.query01 = ''
						this.isFocus1 = false
						this.$nextTick(function() {
							this.isFocus1 = true
						})
					}else{
						this.query01 = ''
						this.isFocus1 = false
						this.$nextTick(function() {
							this.isFocus1 = true
						})
					}
				})
			},
		}
	}
</script>

<style scoped lang="less">
	.content {
		width: 100%;
		height: 100vh;
		background-color: #F3F4F6;
		overflow: hidden;
		display: flex;
		flex-direction: column;
		padding-bottom: 30rpx;

		.Search {
			width: 100%;
			height: 101rpx;
			background: #FFFFFF;
			display: flex;
			justify-content: center;
			align-items: center;

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

		.Total {
			display: flex;
			justify-content: flex-end;
			width: 100%;
			height: 40px;
			padding: 10rpx;
			text-align: right;
			color: #000;
			// background-color: pink;
			padding-right: 35px;
			padding-bottom: 27rpx;
			color: #808080;

			.delTotal {
				margin-left: 20px;
			}

		}

		.body_t {
			width: 92.53%;
			margin: 0rpx auto;
			border-radius: 10rpx;

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

			.item {
				width: 100%;
				// padding: 20rpx;
				background-color: #fff;
				height: 70rpx;
				display: flex;
				align-items: center;
				font-size: 30rpx;
				color: #666666;
				justify-content: space-between;
				margin-bottom: 20rpx;
				border-radius: 20rpx;

				.scanTime {
					margin-left: 15px;
					font-size: 30rpx;
				}
				
				.sonItem {
					display: flex;
					align-items: center;
					justify-content: space-between;
					width: 100%;
					height: 70rpx;
					padding: 20rpx;
					background-color: #FFFFFF;
					border-radius: 20rpx;
				}
			}

			.inputText {
				// margin-top: 10rpx;
				width: 100%;
				display: flex;
				justify-content: space-between;
				// border-bottom: 1px dashed #DBDBDB;
				padding-bottom: 20rpx;

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
				// border-radius: 10px;
				font-size: 36rpx;
				line-height: 76rpx;
				font-family: PingFang SC;
				font-weight: 400;
				text-align: center;
				color: #FFFEFE;
			}
		
			.slot-button {
				display: flex;
				flex-direction: row;
				justify-content: center;
				align-items: center;
				padding: 0 20px;
				height: 68rpx;
				background-color: #dd524d;
				color: #FFFFFF;
				border-top-right-radius: 20rpx;
				border-bottom-right-radius: 20rpx;
			}	
			
		}
	}

	.search-select {
		display: flex;
		width: 92.66%;
		height: 35px;
		border-radius: 30px;
		background-color: #F3F4F6;

		.search-picker {
			display: flex;
			align-items: center;
		}
	}

	.select-input {
		width: 300rpx !important;
		display: flex;
		align-items: center;
		height: 34px;
		border: 1px solid #3297E2;
		border-radius: 5px;
		padding-right: 20rpx;

		input {
			border: none !important;
		}
	}
</style>
