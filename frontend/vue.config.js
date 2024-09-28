const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        pathRewrite: { "^/api": ""}
      },
      '/social': {
        target: "http://localhost:8080",
        changeOrigin: true,
        pathRewrite: { "^/social": ""}
      }
    }
  },
  transpileDependencies: true
})
