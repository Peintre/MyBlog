import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
const { resolve } = require('path')

// https://vitejs.dev/config/
export default defineConfig({
  base:'./',
  plugins: [vue()],
  server: {
    host: "localhost",
    port: "9001",
    proxy: {
      "/api": {
        target: "http://47.92.51.50:9000",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    }  
  },
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src'),
    },
  },
  build:{
    chunkSizeWarningLimit:1500,
    rollupOptions: {
      output:{
          manualChunks(id) {
            if (id.includes('node_modules')) {
                return id.toString().split('node_modules/')[1].split('/')[0].toString();
            }
        }
      }
    }
  }
})
