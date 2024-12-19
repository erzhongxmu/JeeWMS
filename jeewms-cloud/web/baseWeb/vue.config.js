const path = require('path')
// 开启gzip压缩
const CompressionWebpackPlugin = require('compression-webpack-plugin')
// 判断开发环境
const isProduction = process.env.NODE_ENV === 'production'

function resolve(dir) {
  return path.join(__dirname, dir)
}
const timeStamp = new Date().getTime()

// vue.config.js
module.exports = {
  chainWebpack: config => {
    // 移除 prefetch 插件
    config.plugins.delete('prefetch')
  },
  /*
    Vue-cli3:
    Crashed when using Webpack `import()` #2463
    https://github.com/vuejs/vue-cli/issues/2463
   */
  // 如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建。
  productionSourceMap: false,
  // 打包app时放开该配置
  // publicPath:'./',
  configureWebpack: config => {
        if (isProduction) {
          config.optimization.minimizer[0].options.terserOptions.compress.drop_console = true

          // config.output.filename = `js/[name].${timeStamp}.js`
          // config.output.chunkFilename = `js/[name].${timeStamp}.js`

          config.plugins.push(new CompressionWebpackPlugin({
            algorithm: 'gzip',
            test: /\.js$|\.html$|\.json$|\.css/,
            threshold: 10240,
            minRatio: 0.8
          }))
          // 开启分离js
          config.optimization = {
            runtimeChunk: 'single',
            splitChunks: {
              chunks: 'all',
              maxInitialRequests: Infinity,
              minSize: 10000,
              cacheGroups: {
                vendor: {
                  test: /[\\/]node_modules[\\/]/,
                  name (module) {
                    // get the name. E.g. node_modules/packageName/not/this/part.js
                    // or node_modules/packageName
                    const packageName = module.context.match(/[\\/]node_modules[\\/](.*?)([\\/]|$)/)[1]
                    // npm package names are URL-safe, but some servers don't like @ symbols
                    return `npm.${packageName.replace('@', '')}`
                  }
                }
              }
            }
          }
          // 取消webpack警告的性能提示
          config.performance = {
                hints: 'warning',
                // 入口起点的最大体积
                maxEntrypointSize: 50000000,
                // 生成文件的最大体积
                maxAssetSize: 30000000,
                // 只给出 js 文件的性能提示
                assetFilter: function(assetFilename) {
              return assetFilename.endsWith('.js')
            }
          }
        }
  },

  // eslint-disable-next-line no-dupe-keys
  chainWebpack: (config) => {
    config.resolve.alias
      .set('@$', resolve('src'))
      .set('@api', resolve('src/api'))
      .set('@assets', resolve('src/assets'))
      .set('@comp', resolve('src/components'))
      .set('@views', resolve('src/views'))
      // if (isProduction) {
        // config.set('externals', {
        //   vue: 'Vue',
        //   'vue-router': 'VueRouter',
        //   axios: 'axios',
        //   'ant-design-vue': 'antd',
        //   "moment": "moment",
        //   'vuex': 'Vuex'
        // })
      // }
      // // 生产环境，开启js\css压缩
      //   config.plugin('compressionPlugin').use(new CompressionPlugin({
      //     algorithm: 'gzip',
      //     test: /\.(js|css|less)$/, // 匹配文件名
      //     threshold: 10240, // 对超过10k的数据压缩
      //     deleteOriginalAssets: false, // 不删除源文件
      //     minRatio: 0.8
      //   }))

    // 配置 webpack 识别 markdown 为普通的文件
    config.module
      .rule('markdown')
      .test(/\.md$/)
      .use()
      .loader('file-loader')
      .end()

    // 编译vxe-table包里的es6代码，解决IE11兼容问题
    config.module
      .rule('vxe')
      .test(/\.js$/)
      .include
        .add(resolve('node_modules/vxe-table'))
        .add(resolve('node_modules/vxe-table-plugin-antd'))
        .end()
      .use()
      .loader('babel-loader')
      .end()
  },

  css: {
    loaderOptions: {
      less: {
        modifyVars: {
          /* less 变量覆盖，用于自定义 ant design 主题 */
          'primary-color': '#5D5FEF',
          'link-color': '#5D5FEF',
          'border-radius-base': '10px'
        },
        javascriptEnabled: true
      }
    }
    // extract: {
    //   filename: `css/[name].${timeStamp}.css`,
    //   chunkFilename: `css/[name].${timeStamp}.css`
    // }
  },

  devServer: {
    port: 3000,
    proxy: {
     /* '/api': {
        target: 'https://mock.ihx.me/mock/5baf3052f7da7e07e04a5116/antd-pro', //mock API接口系统
        ws: false,
        changeOrigin: true,
        pathRewrite: {
          '/jeecg-boot': ''  //默认所有请求都加了jeecg-boot前缀，需要去掉
        }
      }, */
      '/base-boot': {
        target: 'http://localhost:8080', // 请求本地 需要jeecg-boot后台项目
        ws: false,
        changeOrigin: true
      }
    }
  },

  lintOnSave: undefined
}
