let BASE_URL = ''


if (process.env.NODE_ENV == 'development') {
	// BASE_URL = 'http://58.17.66.24:9999/' // 开发环境后台请求地址
	// BASE_URL =  'http://47.116.3.108:9999/' // 开发环境后台请求地址
	BASE_URL = 'http://192.168.0.104:9999/'
	// BASE_URL = 'http://47.113.229.131:9999/'
	// BASE_URL = 'http://192.168.2.116:9999/'
	// BASE_URL = 'http://47.113.229.131:9999/'sss
	// BASE_URL = 'http://192.168.10.25:9999/'
} else {
	//BASE_URL = 'http://192.168.0.118:9999/'
		// BASE_URL = 'http://192.168.1.107:9999/'
	// BASE_URL = 'http://47.113.229.131:9999/'
	BASE_URL = 'http://47.116.3.108:9999/'
	// BASE_URL = 'http://58.17.66.24:9999/' // 生产环境
}
let staticDomainURL = BASE_URL+ '/sys/common/static';

const configService = {
	apiUrl: BASE_URL,
	staticDomainURL: staticDomainURL
};

export default configService
