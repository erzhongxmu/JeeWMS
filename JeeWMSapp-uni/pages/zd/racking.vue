<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">{{$t('上架')}}</block>
		</cu-custom>

		<view class="Search" style="height: 90px;">
			<!-- <input type="text" :focus="isFocus1" v-model="query01" @confirm="getDetails" :placeholder="$t('pages.请扫描单号')" />
			<input type="text" :focus="isFocus2" v-model="query02" @confirm="getDetails" :placeholder="$t('pages.请扫描产品编码')"
				style="margin-top:10rpx;" /> -->
				<view class="saoma_father1" >
					<input class="input_first" type="text" :focus="isFocus1" v-model="query01"  @confirm="getDetails(1)" :placeholder="$t('pages.请扫描单号')" />
					<image @click="scanClick('query01')" src="../../static/ZX/sm.png" class="saoma" ></image>
				</view>
				
				<view class="saoma_father2" >
					<input class="input_second" type="text" :focus="isFocus2" v-model="query02"  @confirm="getDetails(1)" :placeholder="$t('pages.请扫描产品编码')" style="margin-top:10rpx;"/>
					<image @click="scanClick('query02')" src="../../static/ZX/sm.png" class="saoma" ></image>
				</view>	
		</view>

		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;" @scrolltolower='scroll'>
			<view class="body_t" v-for="(item,index) in data" :key='index'>
				<view class="item">
					<view>{{$t('pages.入库单号')}}：</view>
					<view style="color: #2D2F2E;max-width: 70%;">{{item.imNoticeId}}</view>
				</view>
				
				<view class="item">
					<view>{{$t('pages.产品名称')}}：</view>
					<view style="color: #2D2F2E;max-width: 70%;">{{item.goodsName}}</view>
				</view>
				
				<view class="item">
					<view>{{$t('pages.产品编码')}}：</view>
					<view style="color: #2D2F2E;max-width: 70%;">{{item.goodsId}}</view>
				</view>

				<view class="item">
					<view>{{$t('pages.生产日期')}}：</view>
					<view style="color: #2D2F2E;max-width: 70%;">{{item.proData}}</view>
				</view>

				<view class="item">
					<view>{{$t('pages.单位')}}：</view>
					<view style="color: #2D2F2E;max-width: 70%;">{{item.goodsUnit}}</view>
				</view>

				<view class="item">
					<view>{{$t('pages.数量')}}：</view>
					<view style="color: #2D2F2E;max-width: 70%;">{{item.baseGoodscount}}</view>
				</view>

				<view class="inputText">
					<view>{{$t('pages.储位')}}：</view>
					<input v-model="conditionObj[`query03${index}`]" class="inputText_itm" :focus="Focus3"/>
				</view>

				<!-- <view class="inputText">
					<view>托盘：</view>
					<input v-model="query04" class="inputText_itm" :focus="Focus4"/>
				</view> -->

				<view class="btn" @click="preserve(item,index)">{{$t('上架')}}</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				data: [],
				isFocus1: '',
				isFocus2: '',
				Focus3:'',
				query01: '',
				query02: '',
				conditionObj:{},
				userName:''

			}
		},
		onLoad() {
			this.userName = uni.getStorageSync('login_user_info').userName || '';
			let that  = this
			uni.$off('scan-listener')
			uni.$on('scan-listener', (screenData)=>{
				that[screenData.typescan] = screenData.result 
				that.getDetails()
				that.data = []
			})
		},
		methods: {	
			scroll(){
				
			},
			scanClick(e){
				this.typescan = e
				this.$Router.push({path: '/components/barcode/barcode',params:{typescan:e}})
			},
			getDetails() { // 获取上架列表
			    let params = {
					username: this.userName,
					searchstr: this.query01,
					Searchstr2: this.query02
				}
				console.log(params,'------------');
				this.$http.get('/jeewms/rest/pdaapi/wmInQmIController/list', {
					params
				}).then(res => {
					this.data = res.data.obj
					if(!this.data.length) {
						this.query01 = ''
						this.query02 = ''
					}
				})
			},
			
			preserve(item, index) {
				let data = {
					createBy: this.userName,
					wmToUpId: item.id,
					kuWeiBianMa: this.conditionObj[`query03${index}`]
				}
				if(!data.kuWeiBianMa) {
					uni.showToast({
						title: '储位不能为空',
						icon: 'none'
					})	
				}
				let url = `/jeewms/rest/pdaapi/wmToUpGoodsController/save?wmToUpGoodsstr=${JSON.stringify(data)}`
				let that = this
				that.goOn(data, url)
			},
			
			goOn(data, url) {
				let that = this
				for (var key in data) {
					data[key] = String(data[key]).trim()
				}
				this.$http.post(url).then(res => {
					if (res.data.ok) {
						uni.showToast({
							title: '操作成功',
							icon: 'none'
						})
						that.conditionObj = {}
						that.getDetails()
					} else {
						uni.showToast({
							title: res.data.errorMsg,
							icon: 'none'
						})
					}
				})
			},

		},
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

		.Search {
			display: flex;
			flex-direction: column;
			width: 100%;
			height: 177rpx;
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
				height: 80rpx;
				display: flex;
				align-items: center;
				justify-content: space-between;
				padding-bottom: 15rpx;

				text {
					font-size: 28rpx;
					font-weight: 400;
					color: #666666;
				}

				.inputText_it {
					width: 30%;
					height: 100%;
					border-radius: 10rpx;
					border: 1px solid #3297E2;
				}

				.inputText_itm {
					text-align: right;
					width: 47.5%;
					height: 60rpx;
					border-radius: 10rpx;
					border: 1px solid #3297E2;
					color: #3297E2;
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
