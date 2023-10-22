module.exports = {
    devServer: {
        proxy: {
            '/card': {
                target: 'http://localhost:8070',
                changeOrigin: true,
                pathRewrite: {
                    '^/': ''
                }
            }
        }
    }
}
// import { fileURLToPath, URL } from 'node:url'
//
// import { defineConfig } from 'vite'
// import vue from '@vitejs/plugin-vue'
//
// // https://vitejs.dev/config/
// export default defineConfig({
//   devServer:{
//     "proxy": "http://localhost:8070"
//   },
//   plugins: [vue()],
//   resolve: {
//     alias: {
//       '@': fileURLToPath(new URL('./src', import.meta.url))
//     }
//   }
// })
// // module.exports={
// //   devServer:{
// //     "proxy": "http://localhost:8070"
// //   }
// // }
