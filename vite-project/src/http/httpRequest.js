import axios from "axios";

/**
 * axios发送get请求
 * @param url  '/api/user'
 * @param params
 */
export function doGet(url, params) {
    return axios.get(url, {
        params: params
    });
}

/**
 * axios发送post请求
 * @param url '/api/user'
 * @param data
 */
export function doPost(url, data) {
    return  axios.post(url, data);
}