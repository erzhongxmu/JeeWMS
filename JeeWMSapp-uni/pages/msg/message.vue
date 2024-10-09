<template>
	<view class="bigbox">
		<scroll-view scroll-y class="page">
			<view class="fixed bg-white nav title-box" :style="[{height:CustomBar + 'px'}]">
				<!-- <view class="cu-item tab_qh" :class="index==TabCur?'active cur':''" v-for="(item,index) in arr" :key="index" @tap="tabSelect"
				 :data-id="index">{{item.name}}{{item.num}}</view> -->
				 <view>{{$t('message.news')}}</view>
			</view>
			<!-- 公告 -->
			<view :style="[{marginTop:CustomBar + 'px'}]" v-if="TabCur == 0">
				<view v-if="gg_list.length!=0" class="box" v-for="item in gg_list" @click="shanchu(item.id)">
					<view class="title">
						<view>{{item.titile}}</view>
						<view style="font-size: 26rpx;">{{item.startTime}}</view>
					</view>
					<view class="fu_title">{{item.msgAbstract}}</view>
					<view v-html="item.msgContent"></view>
				</view>
				<view v-if="gg_list.length==0" class="msg">
					<image src="../../static/87.png" mode=""></image>
					<view>{{$t('message.noMessage')}}</view>
				</view>
			</view>
			<!-- 消息 -->
			<view :style="[{marginTop:CustomBar + 'px'}]" v-if="TabCur == 1">
				<view v-if="xx_list.length!=0" class="box" v-for="item in xx_list" @click="shanchu(item.id)">
					<view class="title">
						<view>{{item.titile}}</view>
						<view style="font-size: 26rpx;">{{item.startTime}}</view>
					</view>
					<view class="fu_title">{{item.msgAbstract}}</view>
					<view v-html="item.msgContent"></view>
				</view>
				<view v-if="xx_list.length==0" class="msg">{{$t('message.noMessage')}}</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		name: 'peoplelis',
		data() {
			return {
				CustomBar: this.CustomBar,
				xx_list: [],
				gg_list: [],
				sta: '',
				arr: [
					{
						name: '公告',
						num: '(0)'
					},
					{
						name: '消息',
						num: '(0)'
					},
				],
				TabCur: 1,
				scrollLeft: 0,
				interval:null,
			}
		},
		watch: {
			TabCur(val){
				this.initial()
            },
		},
		mounted() {
			this.interval = setInterval(()=>{
				this.initial()
			},60000)
			this.initial()
		},
		methods: {
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
				console.log(e)
			},
			shanchu: function(id) {
				this.$http.put('sys/sysAnnouncementSend/editByAnntIdAndUserId?anntId=' + id, {
					anntId: id
				}).then((res) => {
					if (res.data.success == true) {
						this.initial()
					}
				})
			},
			initial: function() {
				let data = {
					pageNo: 1,
					pageSize: 10000
				}
				// this.$http.get('sys/annountCement/listByUser', {params: data}).then((res) => {
				// 	this.gg_list = res.data.result.anntMsgList //公告
				// 	this.arr[0].num = '(' + this.gg_list.length + ')'
				// 	this.xx_list = res.data.result.sysMsgList //消息
				// 	this.arr[1].num = '(' + this.xx_list.length + ')'
				// 	let nums = (this.gg_list.length + this.xx_list.length)
				// 	console.log(nums)
				// 	uni.setStorageSync('SET_MSGCOUNTS',nums);
				// 	this.$store.commit('SET_MSGCOUNTS',nums)
				// 	console.log(this.$store.state.msgCounts)
				// })
			}
		},

	}
</script>

<style>
	.page {
		padding-bottom: calc(100upx + env(safe-area-inset-bottom) / 2);;
	}
	.title-box {
		display: flex;
		justify-content: space-around;
		align-items: center;
		font-size: 38rpx;
		font-weight: bold;
	}
	.cur {
		border-bottom: 4rpx solid #4185F5 !important;
	}

	.active {
		color: #4185F5 !important;
	}

	.tab_qh {
		width: 50%;
		text-align: center;
	}

	.msg>image {
		width: 300rpx;
		height: 300rpx;
	}

	.msg {
		text-align: center;
		margin-top: 200rpx;
		font-size: 34rpx;
		font-family: PingFang SC;
		color: rgba(51, 51, 51, 1);
	}

	.fu_title {
		margin: 20rpx 0;
		color: #3A62FF;

	}

	.title {
		height: 140rpx;
		line-height: 60rpx;
		border-bottom: 1px solid #F5F5F5;
		font-size: 35rpx;
		font-family: PingFang SC;
		font-weight: bold;
		color: rgba(51, 51, 51, 1);
	}

	.box {
		padding: 30rpx;
		box-sizing: border-box;
		background: rgba(255, 255, 255, 1);
		box-shadow: 0rpx 4rpx 20rpx 0rpx rgba(0, 0, 0, 0.1);
		border-radius: 10rpx;
		margin-bottom: 30rpx;
	}

	.bigbox {
		/* padding: 30rpx; */
		box-sizing: border-box;
	}
</style>
