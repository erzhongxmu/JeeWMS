let BASE_URL = ''


if (process.env.NODE_ENV == 'development') {
	BASE_URL = 'http://localhost:8081/'
} else {
	BASE_URL = 'http://localhost:8081/'
}
let staticDomainURL = BASE_URL+ '/sys/common/static';

const configService = {
	apiUrl: BASE_URL,
	staticDomainURL: staticDomainURL
};

export default configService
