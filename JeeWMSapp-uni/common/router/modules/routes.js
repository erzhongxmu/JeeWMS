const routes = [{
		path: "/pages/login/login",
		name: 'login',
		meta: {
			title: '登录',
		},
	},
	{
		//注意：path必须跟pages.json中的地址对应，最前面别忘了加'/'哦
		path: '/pages/index/index',
		name: 'index',
		meta: {
			title: '主页',
		},
	},
	{
		path: '/pages/user/people',
		name: 'people',
		meta: {
			title: '个人中心',
		},
	},
	{
		path: '/pages/zd/receiving',
		name: 'receiving',
		meta: {
			title: '收货',
		},
	},
	{
		path: '/pages/zd/racking',
		name: 'racking',
		meta: {
			title: '上架',
		},
	},
	{
		path: '/pages/zd/removing',
		name: 'removing',
		meta: {
			title: '下架',
		},
	},
	{
		path: '/pages/zd/truckLoading',
		name: 'truckLoading',
		meta: {
			title: '装车复核',
		},
	},
	{
		path: '/pages/zd/searchStock',
		name: 'searchStock',
		meta: {
			title: '库存查询',
		},
	},
	{
		path: '/pages/zd/moveStore',
		name: 'moveStore',
		meta: {
			title: '移储',
		},
	},
	{
		path: '/pages/zd/checking',
		name: 'checking',
		meta: {
			title: '盘点',
		},
	},
	{
		path: '/pages/zd/manageGoods',
		name: 'manageGoods',
		meta: {
			title: '商品资料',
		},
	},
	{
		path: '/pages/zd/waveRemoving',
		name: 'waveRemoving',
		meta: {
			title: '波次下架',
		},
	},
	{
		path: '/pages/zd/waveSorting',
		name: 'waveSorting',
		meta: {
			title: '波次分拣',
		},
	},
	{
		path: '/pages/zd/libertyMove',
		name: 'libertyMove',
		meta: {
			title: '自主移库',
		},
	},{
		path: '/components/barcode/barcode',
		name: 'barcode',
		meta: {
			title: '扫码',
		},
	}
]

export default routes
