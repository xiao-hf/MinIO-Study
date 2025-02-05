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
        <a href= "javascript:void(0)" @click="download(scope.row.id)" v-if="scope.row.userContractDO !== null">下载</a>&nbsp;
        <span v-else>下载</span>&nbsp;
        <a :href= "'/edit/' + scope.row.id">编辑</a>&nbsp;
        <a href= "javascript:void(0)" @click="delUser(scope.row.id)">删除</a>
      </template>
    </el-table-column>
  </el-table>

<!--  <el-form-item>-->
<!--    <el-button style="margin: auto" type="primary" @click="alertFail('失败')">测试按钮</el-button>-->
<!--  </el-form-item>-->
</template>

<script setup>
import axios from "axios";
import {doDelete, doGet} from "../http/httpRequest.js";
import {onMounted, ref} from "vue";
import {ElMessage} from "element-plus";

axios.defaults.baseURL = "http://localhost:8080";

function delUser(id) {
  doDelete("/api/delUser/" + id, {}).then(resp => {
    if (resp.data.code === 200) {
      // 删除成功, 提示一下
      alertSuccess("删除成功!")
    } else {
      // 删除失败, 提示一下
      alertFail("删除失败!")
    }
    window.location.reload()
  });
}

const alertSuccess = (msg) => {
  ElMessage({
    message: msg,
    type: 'success',
    plain: true,
  })
}

const alertFail = (msg) => {
  ElMessage({
    message: msg,
    type: 'error',
    plain: true,
  })
}

function download(id) {
  let iframe = document.createElement("iframe");
  iframe.style.display = "none";
  iframe.src = axios.defaults.baseURL + "/api/download/" + id;
  document.body.appendChild(iframe);
}

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