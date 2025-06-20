module.exports = {
  configureWebpack: {
    entry: './src/main.js'
  },
  devServer: {
    port: 8081,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
}