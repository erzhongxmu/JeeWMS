<template>
	<view class="contents">
		<view class="head">
			<view class="img" style="margin-top: 80rpx;">
				<image src="../../static/touxian.png" mode=""></image>
				<view class=""
					style="margin: 41rpx 0 26rpx 0;font-size: 30rpx;font-family: PingFang SC;font-weight: bold;color: #FFFFFF;">
					{{personalList.username}}</view>
				<!-- <view class="" style="ont-size: 26rpx;font-family: PingFang SC;font-weight: bold;color: #FFFFFF;">
					职务：{{personalList.post?personalList.post:'员工'}}</view> -->
			</view>
		</view>

		<view class="cu-list menu card-menu margin-top-xl margin-bottom-xl shadow-lg radius"
			style="flex-direction: column; margin: 0;border-radius: 0;">
			<!-- <view class="cu-item arrow animation-slide-bottom" :style="[{animationDelay: '0.2s'}]">
				<navigator class="content" url="/pages/user/userdetail" hover-class="none">
					<text class="cuIcon-settingsfill text-cyan"></text>
					<text class="text-grey">设置</text>
				</navigator>
			</view> -->
			<view class="cu-item arrow animation-slide-bottom" :style="[{animationDelay: '0.2s'}]">
				<view class="content" hover-class="none" @click='selectLanguageClick'>
					<text class="cuIcon-favorfill text-cyan"></text>
					<text class="text-grey">{{$t('user.language')}}</text>
					<text style="float: right;color:#8799a3">{{lang}}</text>
				</view>
			</view>
			<view class="cu-item arrow animation-slide-bottom" :style="[{animationDelay: '0.3s'}]">
				<div class="content" hover-class="none" @click='logOut'>
					<text class="cuIcon-exit text-cyan"></text>
					<text class="text-grey">{{$t('user.logOut')}}</text>
				</div>
			</view>
		</view>
		<!-- 语言选择弹窗 -->
		<uni-popup ref="selectLanguage">
			<view class="box_Language">
				<radio-group @change="radioChange" style="display: flex;flex-direction: column;line-height: 70rpx; ">
					<label v-for="(item, index) in language" :key="item.value"
						:style="{borderBottom:index == (language.length -1)?'':'1px solid #eee'}">
						<text>{{item.text}}</text>
						<radio :value="item.value" :checked="index === current"
							style="transform:scale(0.8);float: right;" />
					</label>
				</radio-group>
			</view>
		</uni-popup>
		<model ref='uniModel'></model>
	</view>
</template>

<script>
	import api from '@/api/api'
	export default {
		name: "people",
		data() {
			return {
				personalList: {
					avatar: '',
					realname: '',
					username: '',
					post: '',
					depart: ''
				},
				lang: '',
				current: 0,
				positionUrl: '/sys/position/list',
				departUrl: '/sys/user/userDepartList',
				userUrl: '/sys/user/queryById',
				userId: '',
				id: '',
				language: [{
						text: this.$t('user.chinese'),
						value: 'zh',
						key: 'user.chinese',
					},
					{
						text: this.$t('user.English'),
						value: 'en',
						key: 'user.English',
					},
				]
			};
		},
		watch: {
			cur: {
				immediate: true,
				handler() {
					this.load()
				},
			},
		},
		mounted() {
			console.log(uni.getStorageSync('lang') , '===========')
			let lang = {detail:{value: uni.getStorageSync('lang') || 'zh'}}
			this.radioChange(lang)
		},
		methods: {
			radioChange: function(evt) {
				this._i18n.locale = evt.detail.value;
				uni.setStorageSync('lang', evt.detail.value);
				this.language.map((item, index) => {
					item.text = this.$t(item.key)
					if (item.value === evt.detail.value) {
						this.current = index;
						this.lang = item.text;
						this.$refs['selectLanguage'].close();
					}
				})
			},
			selectLanguageClick() {
				this.$refs['selectLanguage'].open();
			},
			remove() {
				uni.removeStorageSync('Access-Token')
			},
			logOut() {
				let that = this
				this.$refs.uniModel.open({
					content: this.$t('message.Log_Out'),
					success(res) {
						if (res.confirm) {
							// api.logout().then(res => {
							// 	let base_url = uni.getStorageSync('base_url')
							// 	uni.clearStorageSync()
							// 	uni.setStorageSync('base_url', base_url)
							// 	uni.navigateTo({ url: '/pages/login/login' })
							// })
							let base_url = uni.getStorageSync('base_url')
							uni.clearStorageSync()
							uni.setStorageSync('base_url', base_url)
							uni.navigateTo({ url: '/pages/login/login' })
							
						}
					}
				})
			},
			load() {
				console.log(1)
				// this.$http.get(this.userUrl, {
				// 	params: {
				// 		id: uni.getStorageSync('login_user_info').id
				// 	}
				// }).then(res => {
				// 	console.log("res", res)
				// 	if (res.data.success) {
				// 		let perArr = res.data.result
				// 		let avatar = (perArr.avatar && perArr.avatar.length > 0) ? api.getFileAccessHttpUrl(perArr
				// 			.avatar) : '/static/avatar_boy.png'
				// 		this.personalList.avatar = avatar
				// 		this.personalList.realname = perArr.realname
				// 		this.personalList.username = perArr.username
				// 		this.personalList.post = perArr.post
				// 		// this.personalList.depart = perArr.departIds
				// 	}
				// }).catch(err => {
				// 	console.log(err);
				// });

				// this.$http.get(this.departUrl, {
				// 	params: {
				// 		userId: this.$store.getters.userid
				// 	}
				// }).then(res => {
				// 	if (res.data.success) {
				// 		console.log(res.data.result[0].title)
				// 		this.personalList.depart = res.data.result[0].title
				// 		console.log(this.personalList)
				// 	}
				// }).catch(e => {
				// 	console.log("请求错误", e)
				// })
			}
		}
	}
</script>

<style lang="less">
	.contents {
		width: 100%;
		height: 100vh;
		background-color: #ECF0F1;
		overflow: hidden;
		overflow-y: auto;

		.head {
			width: 100%;
			height: 501rpx;
			background-image: url(../../static/backj.png);
			background-size: cover;
			display: flex;
			align-items: center;
			justify-content: center;

			.img {
				width: 27.6%;
				display: flex;
				flex-direction: column;
				align-items: center;

				image {
					width: 165rpx;
					height: 165rpx;
				}
			}
		}

		.cu-item {
			margin: 20rpx 0;
		}
	}

	.UCenter-bg {
		background-image: url(https://image.weilanwl.com/color2.0/index.jpg);
		background-size: cover;
		height: 400rpx;
		display: flex;
		justify-content: center;
		padding-top: 40rpx;
		overflow: hidden;
		position: relative;
		flex-direction: column;
		align-items: center;
		color: #fff;
		font-weight: 300;
		text-shadow: 0 0 3px rgba(0, 0, 0, 0.3);
	}

	.UCenter-bg text {
		opacity: 0.8;
	}

	.UCenter-bg image {
		width: 200rpx;
		height: 200rpx;
	}

	.UCenter-bg .gif-wave {
		position: absolute;
		width: 100%;
		bottom: 0;
		left: 0;
		z-index: 99;
		mix-blend-mode: screen;
		height: 100rpx;
	}

	map,
	.mapBox {
		left: 0;
		z-index: 99;
		mix-blend-mode: screen;
		height: 100rpx;
	}

	map,
	.mapBox {
		width: 750rpx;
		height: 300rpx;
	}

	.box_Language {
		width: 80%;
		max-height: 600rpx;
		background: #fff;
		border-radius: 20rpx;
		overflow: auto;
		padding: 50rpx;
		.languageItem {
			width: 100%;
			line-height: 70rpx;
		}

		.radio_Language {
			float: right;
		}
	}
</style>
