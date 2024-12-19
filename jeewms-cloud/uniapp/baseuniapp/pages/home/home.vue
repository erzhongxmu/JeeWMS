<template name="home">
	<view class="content">
		<view class="top">
			<view class="backgrounds">
				<!-- title -->
				<view class="title">
					<view class="" style="font-size: 30rpx;display: flex;align-items: center;">
						{{userName}} <!-- <image src="../../static/home/jiantou.png" mode=""></image> -->
					</view>
					<view class="">WMS</view>
					<view class="">
						<!-- <image class="saoma-img" src="../../static/home/saoma.png" mode=""></image> -->
					</view>
				</view>
				<!-- 轮播 -->
				<view class="screen-swiper">
					<swiper class="square-dot" style="width: 100% ;height: 100%;" indicator-color='#999999' indicator-active-color='#58C8B9' :indicator-dots="true" :circular="true"  :style="[{animation: 'show 0.2s 1'}]">
						<swiper-item v-for="(item,index) in swiperList" :key="index">
							<view class="screen-swiper_view">
								<view class="item" v-for="(v,i) in item" @click="goPage(v)">
									<image :src="v.picture" mode=""></image>
									<!-- <text>{{v.appmodelName}}</text> -->
									<text>{{$t('home.'+ v.query1)}}</text>
								</view>
							</view>
						</swiper-item>
					</swiper>
				</view>
			</view>
			<view class="" style="width: 100%;height: 481rpx;"></view>
			<view class="message">
				<image src="../../static/home/message.png" mode=""></image>
				{{$t('message.news')}}: 
				<!-- <swiper class="swiper" circular="true" vertical="true" display-multiple-items="3" :autoplay="autoplay" :interval="interval" :duration="duration">
					<swiper-item v-for="(item,index) in list" :key="index">
						<view class="" style="width: 70%; margin-left: 10px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">{{item}}</view>
					</swiper-item>
				</swiper> -->
				 <view class="scroll_box" style="width: 70%; margin-left: 10px;height: 100%;"> 
					<swiper  style="width: 100% ;height: 100%;" class="swiper" circular="true" vertical="true" display-multiple-items="1" :autoplay="autoplay" :interval="interval" :duration="duration">
						<swiper-item v-for="(item,index) in MsgList" :key="index">
							<view class="swiper-item uni-bg-green" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;line-height: 75rpx;">{{item.msgAbstract}}</view>
						</swiper-item>
					</swiper>
				</view>
				
				
				
			</view>
			
		</view>
		<view class="bottom">
			<view class="title">{{$t('home.todayTask')}}</view>	
			<view class="percentage">
				<view class="percentage-t"> {{data.query01 || 0}} <text style="font-size: 40rpx;">%</text>  </view>
				<view class="percentage-to"> <view class=""></view> {{$t('home.percentageComplete')}} </view>
			</view>
			<view class="schedule" style="position: relative;overflow: hidden;">
				<image style="position: absolute;top: 0;z-index: 10;height: 100%;" src="../../static/home/touming.png" mode=""></image>
				<image :style="{position: 'absolute',top: 0,left:right, zIndex: 9,height: '100%'}" src="../../static/home/lans.png" mode=""></image>
			</view>
			<view class="div">
				<view class="item">
					<view class="">{{$t('home.total')}}</view>
					<view class="" style="color: #63ADE8;margin-top: 10rpx;font-size: 30rpx;">{{data.query02 || 0}}</view>
				</view>
				<view class="item">
					<view class="">{{$t('home.offTheStocks')}}</view>
					<view class="" style="color: #58C8B9;margin-top: 10rpx;font-size: 30rpx;">{{data.query02 || 0}}</view>
				</view>
				<view class="item">
					<view class="">{{$t('home.abnormal')}}</view>
					<view class="" style="color: #FF0000;margin-top: 10rpx;font-size: 30rpx;">{{data.query03 || 0}}</view>
				</view>
			</view>
		</view>
		<!-- 撑起页面 -->
		<view class="" style="width: 100%;height: 100rpx;"></view>
	</view>
</template>

<script>
	
	export default {
		name: 'home',
		data() {
			return {
				autoplay:true,
				interval:4000,	
				duration:2000,
				list:[],
				data:{},
				swiperList: [
				],
				usList: [],
				timer: null,
				menuNum: {
					in_loc: 0,
					to_up_shelf: 0,
					putaway: 0,
					down_shelf: 0,
					order_pick: 0,
					storage_transfer: 0,
					check: 0
				},
				right:'100%',
				userName:'',
				MsgList:[],
			}
		},
		beforeDestroy(){
			// clearInterval(this.timer);
		},
		onShow() {
			
			
		},
		mounted() {
			if(uni.getStorageSync('login_user_info')){
				this.userName = uni.getStorageSync('login_user_info').username || '';	
			}
			this.getDetails()
		},
		methods: {
			getDetails(){
				let data = { username:this.userName, listtype:'今日任务', }
				// 获取今日任务
				this.$http.get('/jeewms/pdaGapi/getlist',{params:data}).then(res => {
					this.getQuery(0)
				})
				// 获取消息
				this.$http.get('/sys/annountCement/listByUser',{params:{pageNo: 1,pageSize: 10000}}).then(res => {
					this.MsgList = res.data.result.sysMsgList
				})
				// 获取操作列表
				this.$http.get('/jeewms/mesapp/appfunctionList',{params:{pageNo: 1,pageSize: 100}}).then(res => {
					// res.data.result.push({
					// 	appmodelCode: "100",
					// 	appmodelName: "打印",
					// 	appmodelSort: "01",
					// 	createBy: "admin",
					// 	createTime: "2021-12-10 13:57:31",
					// 	id: "1469184479689781251",
					// 	ifBind: "0",
					// 	picture: "https://zhaodui.oss-cn-shenzhen.aliyuncs.com/logo/wms/2.png",
					// 	query1: "DY",
					// 	query2: null,
					// 	query3: null,
					// 	query4: null,
					// 	query5: null,
					// 	query6: null,
					// 	route: "zx28",
					// 	sysOrgCode: "A01",
					// 	type: "SY",
					// 	updateBy: "admin",
					// 	updateTime: "2022-07-08 09:10:57",
					// })
					this.swiperList = this.group(res.data.result,9)
				})
			},
			group(array, subGroupLength) {
			    let index = 0;
			    let newArray = [];
			    while(index < array.length) {
			      newArray.push(array.slice(index, index += subGroupLength));
			    }
			    return newArray;
			},
			getQuery(e){
				this.right = -(100 - e ) +'%'
			},
			goPage(page) {
				// let pages = page.route
				let pages = page.route
				console.log(page)
				if (!pages) {
					return false;
				}
				uni.setStorageSync('affairType', page.appmodelName)
				this.$Router.push({
					path: '/pages/zx/' + pages,
					// path: '/pages/zx/collect',
					params:{
						title:page.appmodelName,
						NameKey:this.$t('home.'+ page.query1) ,
						id:page.id,
						// NameKey:page.appmodelName,
					}
				})
			},
			// 主数据—APP功能模块查询
			getAPPmodular() {
				var compare = function compare(property) {
					return function(a, b) {
						var value1 = a[property];
						var value2 = b[property];
						return value1 - value2;
					}
				}
				this.$http.get('wmsapi/wmsapp/appfunctionList').then(res => {
					if (res.data.success) {
						this.usList = res.data.result;
						console.log(this.usList)
						this.usList = this.usList.sort(compare('appmodelCode'))
					} else {
						// this.$tip.error(res.data.message)
            this.modal.show({ type:'error', content:res.data.message })
					}
				})
			},
			getMenuNum() {
				let token = uni.getStorageSync('Access-Token')
				if(null == token || token == undefined || token == '' || !token) {
					clearInterval(this.timer);
					return
				}
				var that = this
				this.$http.get('jeewms/wmImNoticeH/batchList').then((res) => {
					if(res.data.success) {
						that.menuNum.in_loc = res.data.result.records.length
					}
				})
				let data = {
					binSta:'N',
					column: 'createTime',
					order: 'desc'
				}
				this.$http.get('/jeewms/wmInQmI/list', {
					params: data
				}).then((res) => {
					if(res.data.success) {
						that.menuNum.to_up_shelf = res.data.result.records.length
					}
				})
				let data1 = {
					binSta: 'N',
					column: 'createTime',
					order: 'desc'
				}
				this.$http.get('jeewms/wmInQmI/list', {
					params: data1
				}).then((res) => {
					if(res.data.success) {
						that.menuNum.putaway = res.data.result.records.length
					}
				})
				this.$http.get('jeewms/wmOmQmI/list').then((res) => {
					if(res.data.success) {
						that.menuNum.down_shelf = res.data.result.records.length
					}
				})
				this.$http.get('jeewms/wmToDownGoods/orderPickingList').then((res) => {
					if(res.data.success) {
						that.menuNum.order_pick = res.data.result.records.length
					}
				})
				this.$http.get('jeewms/wmToMoveGoods/list').then((res) => {
					if(res.data.success) {
						this.menuNum.storage_transfer = res.data.result.records.length
					}
				})
				this.$http.get('jeewms/wmSttInGoods/pdaList').then((res) => {
					if(res.data.success) {
						that.menuNum.check = res.data.result.records.length
					}
				})
			}
		}
	}
</script>
<style scoped lang="less">
	.content{
		width: 100%;
		height: 100%;
		overflow: hidden;
		background-color: #F3F4F6;
		.top{
			width: 100%;
			height: 1049rpx;
			position: relative;
			background-color: #fff;
			.backgrounds{
				width: 100%;
				height: 447rpx;
				background: url(../../static/ZX/bar.png) 100% 100% no-repeat;
				background-size: 100% 100%;
				.title{
					width: 100%;
					height: 88rpx;
					display: flex;
					align-items: center;
					justify-content: space-between;
					color: #fff;
					padding: 86rpx 14rpx 0 38rpx;
					view{
						width: 100rpx;
						display: flex;
						align-items: center;
						justify-content: center;
						font-size: 36rpx;
					}
					image{
						width: 33rpx;
						height: 22rpx;
					}
					.saoma-img{
						width: 44rpx;
						height: 44rpx;
					}
				}
				
				.screen-swiper{
					width: 92%;
					height: 596rpx;
					background-color: #fff;
					position: absolute;
					bottom: 120rpx;
					left: 50%; 
					box-shadow: 0px 7rpx 12rpx 1rpx rgba(0, 0, 0, 0.1);
					border-radius: 20rpx;
					transform: translate(-50%, 0%); 
					.screen-swiper_view{
						width: 100%;
						height: 100%;
						.item::hover{
							background:#58c8b9 ;
						}
						view{
							display: inline-block;
							width: 33%;
							height: 30%;
							// display: flex;
							// flex-direction: column;
							// align-items: center;
							// justify-content: center;
							font-size: 24rpx;
							color: #333333;
							text-align: center;
							image{
								margin: 20rpx auto 0;
								width: 103rpx;
								height: 103rpx;
							}
						}
					}
				}
			}
			.message{
				width: 92%;
				height: 67rpx;
				margin: 22rpx auto;
				background: #F7F7F7;
				border-radius: 33rpx;
				font-size: 24rpx;
				color: #666666;
				font-weight: bold;
				display: flex;
				align-items: center;
				image{
					width: 33rpx;
					height: 33rpx;
					margin: 0 15rpx 0  40rpx;
				}
			}
		}
		.bottom{
			width: 100%;
			height: 369rpx;
			background-color: #fff;
			margin: 19rpx 0; 
			padding: 25rpx 30rpx;
			.title{
				font-size: 30rpx;
				font-weight: bold;
				color: #666666;
				margin-bottom: 15rpx;
			}
			.percentage{
				width: 100%;
				display: flex;
				align-items: center;
				justify-content: space-between;
				.percentage-t{
					font-size: 60rpx;
					font-weight: bold;
					color: #333333;
				}
				.percentage-to{
					display: flex;
					align-items: center;
					font-size: 24rpx;
					font-weight: bold;
					color: #333333;
					view{
						width: 18rpx;
						height: 18rpx;
						background-color: #58C8B9;
						border-radius: 50%;
						margin-right: 20rpx;
					}
				}
			}
			.schedule{
				margin: 15rpx 0;
				width:100%;
				height: 50rpx;
				background: #C8C4C4;
				image{
					width: 100%;
					height: 100%;
				}
			}
			.div{
				width: 100%;
				display: flex;
				align-items: center;
				justify-content: space-around;
				.item{
					display: flex;
					align-items: center;
					justify-content: center;
					flex-direction: column;
					width: 220rpx;
					height: 123rpx;
					background: #F6F8FF;
					border-radius: 1rpx;	
					font-size: 24rpx;
					font-family: PingFang SC;
					font-weight: bold;
					color: #999999;
				}
				
			}
		}
	}
</style>
<style>
	.text-sm{
		font-size: 15px;
	}
	.line2-icon {
		width: 60px;
		height: 60px;
	}

	page {
		background-color: white;
	}

	.ss {
		background-size: 100% !important;
	}

	.page {
		margin-bottom: 100rpx;
	}
	.App{
		margin-bottom: 40rpx;
	}
	.num-tag.badge {
	    border-radius: 100px;
	    position: absolute;
	    top: 10px;
	    right: 10px;
	    font-size: 10px;
	    padding: 0px 5px;
	    height: 14px;
	    color: #ffffff;
	    z-index: 1;
	}
	.num-tag.badge:not([class*="bg-"]) {
	    background-color: #dd514c;
	}
	
</style>
