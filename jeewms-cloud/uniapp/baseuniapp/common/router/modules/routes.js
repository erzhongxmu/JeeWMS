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
		path: '/pages/zx/zx01',
		name: 'zx01',
		meta: {
			title: '成品入库',
		},
	},
	{
		path: '/pages/zx/zx01-2',
		name: 'zx01-2',
		meta: {
			title: '半成品入库',
		},
	},
	{
		path: '/pages/zx/zx02',
		name: 'zx02',
		meta: {
			title: '到货审核',
		},
	},
	{
		path: '/pages/zx/zx03',
		name: 'zx03',
		meta: {
			title: '采购入库',
		},
	},
	{
		path: '/pages/zx/zx04',
		name: 'zx04',
		meta: {
			title: '生产领料(倒冲)',
		},
	},
	{
		path: '/pages/zx/zx05',
		name: 'zx05',
		meta: {
			title: '销售组托',
		},
	},
	{
		path: '/pages/zx/zx06',
		name: 'zx06',
		meta: {
			title: '销售出库',
		},
	},
	{
		path: '/pages/zx/zx07',
		name: 'zx07',
		meta: {
			title: '拆托',
		},
	},
	{
		path: '/pages/zx/zx08',
		name: 'zx08',
		meta: {
			title: '部门领料(出库)',
		},
	},
	{
		path: '/pages/zx/zx09',
		name: 'zx09',
		meta: {
			title: '调拨管理',
		},
	},
	{
		path: '/pages/zx/zx10',
		name: 'zx10',
		meta: {
			title: '货位转储',
		},
	},
	{
		path: '/pages/zx/zx11',
		name: 'zx11',
		meta: {
			title: '暗盘',
		},
	},
	{
		path: '/pages/zx/zx12',
		name: 'zx12',
		meta: {
			title: '明盘',
		},
	},
	{
		path: '/pages/zx/zx13',
		name: 'zx13',
		meta: {
			title: '托盘清单',
		},
	},
	{
		path: '/pages/zx/zx14',
		name: 'zx14',
		meta: {
			title: '直拨组托',
		},
	},
	{
		path: '/pages/zx/zx15',
		name: 'zx15',
		meta: {
			title: '下架',
		},
	},
	{
		path: '/pages/zx/zx16',
		name: 'zx16',
		meta: {
			title: '上架',
		},
	},
	{
		path: '/pages/zx/zx17',
		name: 'zx17',
		meta: {
			title: '波次下架',
		},
	},
	{
		path: '/pages/zx/zx18',
		name: 'zx18',
		meta: {
			title: '波次分拣',
		},
	},
	{
		path: '/pages/zx/zx19',
		name: 'zx19',
		meta: {
			title: '复核',
		},
	},
	{
		path: '/pages/zx/zx24',
		name: 'zx24',
		meta: {
			title: '商品资料',
		},
	},
	{
		path: '/pages/zx/zx25',
		name: 'zx25',
		meta: {
			title: '库存查询',
		},
	},
	{
		path: '/pages/zx/zx27',
		name: 'zx27',
		meta: {
			title: '扫描统计',
		},
	},
	{
		path: '/pages/zx/zx28',
		name: 'zx28',
		meta: {
			title: '蓝牙打印',
		},
	},
	{
		path: '/pages/zx/pages/zx05Child',
		name: 'zx05Child',
		meta: {
			title: '组托',
		},
	},
	{
		path: '/pages/zx/pages/zx14Child',
		name: 'zx14Child',
		meta: {
			title: '直拨组托',
		},
	},
	{
		path: '/pages/zx/collect',
		name: 'collect',
		meta: {
			title: 'PDA页面配置总表',
		},
	},
	{
		path: '/pages/zx/zx16-1',
		name: 'zx16-1',
		meta: {
			title: '上架任务详情',
		},
	},
	{
		path: '/pages/zx/zx29',
		name: 'zx29',
		meta: {
			title: '货位转储2',
		},
	},
	{
		path: '/pages/zx/zx29-1',
		name: 'zx29-1',
		meta: {
			title: '货位转储任务详情',
		},
	},
	{
		path: '/pages/zx/pltn01',
		name: 'pltn01',
		meta: {
			title: 'QC检验',
		},
	},
	{
		path: '/pages/zx/pltn02',
		name: 'pltn02',
		meta: {
			title: '箱码扫码查询',
		},
	},
	{
		path: '/pages/zx/pltn03',
		name: 'pltn03',
		meta: {
			title: 'QC费用',
		},
	},
]

export default routes
