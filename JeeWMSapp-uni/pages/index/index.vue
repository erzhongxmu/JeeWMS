<template>
	<view >
		<home :cur="PageCur" v-if="PageCur=='home'" ref="homePage" :key="commponent1Key"></home>
		<message :cur="PageCur" v-if="PageCur=='message'" :key="commponent2Key"></message>
		<people v-if="PageCur=='people'" :key="commponent4Key"></people>
		<view class="cu-bar tabbar bg-white shadow foot">
			<view :class="PageCur=='home'?'action text-green':'action text-gray'" @click="NavChange" data-cur="home">
				<view class='cuIcon-homefill'></view>{{$t('home.home')}}
			</view>
			<view :class="PageCur=='message'?'action text-green':'action text-gray'" @click="NavChange" data-cur="message">
				   <view class="cu-tag badge" v-if="msgCount > 0">{{msgCount}}</view>
				<view class='cuIcon-message'></view>{{$t('message.news')}}
			</view>
			<view :class="PageCur=='people'?'action text-green':'action text-gray'" @click="NavChange" data-cur="people">
				<view class='cuIcon-people'></view>{{$t('user.mine')}}
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props:{
			cur:String,
		},
		data() {
			return {
				PageCur: 'home',
				commponent1Key: 0,
				commponent2Key: 0,
				commponent3Key: 0,
				commponent4Key: 0,
				websock:'',
				lockReconnect:false,
				msgCount:0,
				dot:{
				  mailHome:false
				},
				innerAudioContext: null
			}
		},
		watch: {
			cur: {
				immediate: true,
				handler() {
					console.log('watch',this.cur)
				    this.initMenu()
				},
			},
			$route(to, from) {
			  if (from.name === 'instructionReceipt') {
				  uni.setStorageSync('instructionReceiptRise',null);
			  }
			},
			msgCount: function(newCount, oldCount){
				let innerAudioContext = uni.createInnerAudioContext()
				innerAudioContext.obeyMuteSwitch = false
				innerAudioContext.src = require('static/mp3/tips.mp3')
				this.innerAudioContext = innerAudioContext
				this.innerAudioContext.pause()
				newCount > oldCount && this.innerAudioContext.play()
			}
		},
		onShow: function() {
			// if(this.$refs.homePage) {
			// 	this.$refs.homePage.getMenuNum();
			// }
			// console.log('showIndex')
		},
		onLoad:function(){
			this.PageCur='home'
			++this.commponent1Key
			++this.commponent2Key
			++this.commponent3Key
			++this.commponent4Key
			console.log('2222',this.$store.state.msgCounts)
			console.log('route:',this.$Router)
		},
		methods: {
			NavChange: function(e) {
				console.log(e)
				console.log(e.currentTarget.dataset.cur)
				this.PageCur = e.currentTarget.dataset.cur
			},
			initMenu(){
				console.log("-----------home------------")
			    // this.initWebSocket();
			    this.loadCount(0);
			},	
			goPage(page){
				if(!page){
					return false;
				}
				if(page==='annotationList'){
				  this.msgCount = 0
				}
				this.dot[page]=false
				this.$router.push({name: page})
			},
			initWebSocket: function () {
				// WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
				var userId = this.$store.getters.userid;
				let base_url = uni.getStorageSync('base_url') + '/'
				var url = this.$config.apiUrl.replace("https://","wss://").replace("http://","ws://")+"websocket/"+userId;
				// var url = base_url.replace("https://","wss://").replace("http://","ws://")+"websocket/"+userId;
				this.websock = new WebSocket(url);
				this.websock.onopen = this.websocketOnopen;
				this.websock.onerror = this.websocketOnerror;
				this.websock.onmessage = this.websocketOnmessage;
				this.websock.onclose = this.websocketOnclose;
			},
			websocketOnopen: function () {
				console.log("WebSocket连接成功");
				
				//心跳检测重置
				//this.heartCheck.reset().start();
			},
			websocketOnerror: function () {
				console.log("WebSocket连接发生错误");
				this.reconnect();
			},
			websocketOnmessage: function (e) {
				console.log("-----接收消息-------",e.data);
				var data = eval("(" + e.data + ")"); //解析对象
				if(data.cmd == "topic"){
				  //系统通知
				  this.loadCount('1')
				}else if(data.cmd == "user"){
				  //用户消息
				  this.loadCount('2')
				} else if(data.cmd == 'email'){
				  this.loadEmailCount()
				}
					
				//心跳检测重置
				//this.heartCheck.reset().start();
			},
			websocketOnclose: function (e) {
				console.log("connection closed (" + e.code + ")");
				this.reconnect();
			},
			websocketSend(text) { // 数据发送
				try {
				  this.websock.send(text);
				} catch (err) {
				  console.log("send failed (" + err.code + ")");
				}
			},
			reconnect() {
				var that = this;
				if(that.lockReconnect) return;
				that.lockReconnect = true;
				//没连接上会一直重连，设置延迟避免请求过多
				setTimeout(function () {
				  console.info("尝试重连...");
				  that.initWebSocket();
				  that.lockReconnect = false;
				}, 5000);
			},
			loadCount(flag){
				console.log("loadCount::flag",flag)
				let url = '/sys/annountCement/listByUser';
				this.$http.get(url).then(res=>{
					console.log("res::",res)
				  if(res.data.success){
					let msg1Count = res.data.result.anntMsgTotal;
					let msg2Count = res.data.result.sysMsgTotal;
					//this.msgCount = msg1Count + msg2Count
					this.msgCount = msg2Count
					// this.$store.dispatch('msgCounts',this.msgCount)
					 this.$store.commit('SET_MSGCOUNTS', this.msgCount)
					 uni.setStorageSync('SET_MSGCOUNTS', this.msgCount);
					console.log("this.msgCount",this.msgCount)
				  }
				})
			},
			loadEmailCount(){
				this.dot.mailHome = true
			},
			getTtemDotInfo(){
				if(this.msgCount>0){
				  return this.msgCount
				}
				return '';
			}
			
		}
	}
</script>

<style>
  .cu-tag.badge{
	  right: 70rpx !important;
  }
  .text-green{
	  color: #58c8b9;
  }
  .cu-bar.foot{
	  z-index:1024 !important;
  }
</style>
