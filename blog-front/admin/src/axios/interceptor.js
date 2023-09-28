/**
 * 生成基础axios对象，并对请求和响应做处理
 */
 import axios from 'axios'
 import { getToken } from '@/assets/js/common';

 // 创建一个独立的axios实例
 const instance = axios.create({
     baseURL: '/',
     // 定义统一的请求头部
     headers: {
        'Content-Type': 'application/json' //默认方式提交数据
     },
     // 配置请求超时时间
     timeout: 500000,
 });
 
 // 请求拦截
 instance.interceptors.request.use(config => {
     // 在header中自定义token参数名：tokenHeader，可添加项目token
     config.headers[Authorization] =  getToken()
     return config;
 });

 // 返回拦截
instance.interceptors.response.use(config=> {
    return config;
});
 export default instance;
 
 