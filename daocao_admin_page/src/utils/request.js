//封装axios，做请求处理
//导入axios
import axios from "axios";
//引入router
import router from "@/router/index.js";
import {ElMessage} from "element-plus";
// 获取token
import {getToken} from "@/utils/token/index.js";
//创建axios
const request = axios.create({
    //根请求地址
    baseURL: 'http://localhost:8066',
    withCredentials: false,//用于配置请求接口跨域是否需要凭证
    timeout: 3000,//超时时间
})

//配置请求头的参数类型,和编码格式
axios.defaults.headers['Content-Type'] = 'application/json;charset=UTF-8';
//配置请求的拦截器
request.interceptors.request.use((config) => {
    //在请求头添加token,是否需要发送token
    if (getToken("daocaoToken")) {
        config.headers["Daocao-Authorization"] = getToken("daocaoToken");
    }
    return config;
}, (error) => {
    //发生异常
    console.log("请求异常--->", error);
    return Promise.reject(error);
})
//配置响应拦截器
request.interceptors.response.use((response) => {
    //判断响应码，后端返回的数据 code,data,msg
    let {msg, code} = response.data;
    console.log("response--->", code, "msg--->", msg);
    if (code == null) {
        return response;
    } else if (code === 200) {
        return response;
    } else if (code === 500) {
        ElMessage.error("服务端异常")
    } else if (code === 401) {
        ElMessage.error("没有操作权限")
    } else if (code === 403) {
        ElMessage.error("登录过期！")
        // 需要重新登录，跳转到登录页面，清楚pinia中的数据，sessionStorage中
        window.sessionStorage.clear();
        router.push('/login');
    }
    return Promise.reject(msg);
}, (error) => {
    //出现异常
    ElMessage.error("error--->",error);
    window.sessionStorage.clear();
    router.push('/login');
    return Promise.reject(error);
})

//导出
export default request;