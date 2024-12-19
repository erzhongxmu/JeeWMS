<template>
    <view v-if="isShow">
        <view class="show-box-bg wx-login-box">
            <view class="conten">
                <view class="titl">
                    {{ textmsg.title }}
                </view>
                <view class="text">
                    {{ textmsg.content }}
                </view>
				<view class="text" style="margin-bottom: 50rpx;">
				    {{ textmsg.contentTwo || '' }}
				</view>
                <view class="btn-box">
                    <view class="cancel" @tap="cancel()">
                        {{ textmsg.cancelText }}
                    </view>
                    <view class="confirm" @tap="success()">
                        {{ textmsg.confirmText }}
                    </view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
    export default {
        data() {
            return {
				isShow:false,
                textmsg:{
					title:'提示',
					content:'提示文本',
					cancelText:'取消',
					confirmText:'确定',
					success:(res)=>{},
					cancel:(res)=>{},
				}
            }
        },
		onShow() {
			
		},
        methods:{
			open(res){ // 开启
				this.isShow = true
				this.textmsg.title =  res.title?res.title:this.$t('message.hint')
				this.textmsg.content =  res.content?res.content:'' 
				this.textmsg.cancelText =  res.cancelText?res.cancelText:this.$t('message.cancel')
				this.textmsg.confirmText =  res.confirmText?res.confirmText: this.$t('message.confirm')
				this.textmsg.success =  res.success?res.success:()=>{}
				this.textmsg.cancel =  res.cancel?res.cancel:()=>{}
			},
			cancel(){ // 取消
				this.isShow = false
				this.textmsg.cancel()
			},
			success(){ // 确定
				this.isShow = false
				this.textmsg.success({confirm:true})
			}
        }
    }
</script>

<style scoped lang="scss">
    .wx-login-box {
        width: 100%;
        position: fixed;
        top: 0;
        left: 0;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.6);
        z-index: 1000;
        .conten {
            width: 78%;
            // height: 400upx;
            background-color:#fff;
            z-index: 1;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%,-50%);
            border-radius: 14upx;
            overflow: hidden;
            .titl {
                width: 100%;
                height: 110rpx;
                font-size: 35rpx;
                padding: 0 5%;
                color: black;
                text-align: center;
                line-height: 140rpx;
            }
            .text {
                width: 100%;
                font-size: 32rpx;
                color: black ;
                padding: 0 5%;
                text-align: center;
            }
            .memberY {
                width: 90%;
                font-size: 28rpx;
                color: rgb(48, 190, 215);
                padding: 0 5%;
                text-align: center;
            }
            .memberB {
                width: 90%;
                font-size: 28rpx;
                color: #007AFF;
                padding: 0 5%;
                text-align: center;
            }
            .btn-box {
                width: 100%;
                height: 90rpx;
                display: flex;
                border-top: 2rpx solid #d5d5d6;
                .cancel {
                    width: 50%;
                    height: 100%;
                    font-size: 40rpx;
                    color: #999;
                    text-align: center;
                    line-height: 89rpx;
                    border-right: 2rpx solid #d5d5d6;
                }
                .confirm {
                    width: 50%;
                    height: 100%;
                    font-size: 40rpx;
                    color: rgb(48, 190, 215);
                    text-align: center;
                    line-height: 99rpx;
                }
            }
        }
        
    }
    
</style>
