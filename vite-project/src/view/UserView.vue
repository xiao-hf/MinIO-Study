<template>
  <el-table :data="userList" stripe style="width: 100%">
    <el-table-column prop="id" label="id" />
    <el-table-column label="密码" >
      <template #default="scope">
        ****** {{scope.row.password}}
      </template>
    </el-table-column>
    <el-table-column prop="nick" label="昵称"/>
    <el-table-column prop="sex" label="性别"/>
    <el-table-column prop="phone" label="手机"/>
    <el-table-column prop="email" label="邮箱"/>
    <el-table-column prop="address" label="地址"/>
    <el-table-column prop="createTime" label="创建时间"/>
    <el-table-column prop="updateTime" label="修改时间"/>
    <el-table-column label="操作">
      <template #default="scope">
        <a :href= "'/edit/' + scope.row.id">编辑</a> <a href="">删除</a>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>

<script setup>
import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080";
import { doGet } from "../http/httpRequest.js";
import {onMounted, ref} from "vue";

let userList = ref( [{}] );

onMounted(() => {
  getData();
})

function getData() {
  // alert(123)
  doGet("/api/users", {})
      .then(function (resp) {
        // 处理成功情况
        console.log(resp);
        userList.value = resp.data.data; // resp.data = R对象
      })
      .catch((err) => {
        // 处理出错情况
        console.log(err)
      })
      .finally( () => {
        // 最终都执行
      })
}
</script>