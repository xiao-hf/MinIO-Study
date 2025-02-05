<template>
  <el-form
      label-width="auto"
      :model="userInfo"
      style="max-width: 600px; margin: auto">
    <div style="margin: auto; text-align: center; font-size: 18px; font-weight: 700; padding: 15px;">
      编辑用户
    </div>
    <br>
    <el-form-item label="昵称" :label-position="itemLabelPosition">
      <el-input v-model="userInfo.nick" />
    </el-form-item>
    <el-form-item label="密码" :label-position="itemLabelPosition">
      <el-input v-model="userInfo.password" />
    </el-form-item>
    <el-form-item label="性别" :label-position="itemLabelPosition">
      <el-input v-model="userInfo.sex" />
    </el-form-item>
    <el-form-item label="手机" :label-position="itemLabelPosition">
      <el-input v-model="userInfo.phone" />
    </el-form-item>
    <el-form-item label="邮箱" :label-position="itemLabelPosition">
      <el-input v-model="userInfo.email" />
    </el-form-item>
    <el-form-item label="住址" :label-position="itemLabelPosition">
      <el-input v-model="userInfo.address" />
    </el-form-item>
    <el-form-item label="上传头像 ">
      <el-upload
          ref="uploadImageRef"
          class="avatar-uploader"
          accept="image/jpg,image/png,image/jpeg"
          limit="1"
          :on-success="imageSuccess"
          :on-error="imageError"
          :file-list="imageFileList"
          :action="'http://localhost:8080/api/user/image?id=' + id"
          :show-file-list="true"
          :auto-upload="false">
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><plus/></el-icon>
      </el-upload>
      <span style="color: forestgreen;">&nbsp;&nbsp;{{imageTip}}</span>
    </el-form-item>
    <el-form-item label="上传合同 ">
      <el-upload
          ref="uploadContractRef"
          class="avatar-uploader"
          :action="'http://localhost:8080/api/user/contract?id=' + id"
          accept="application/pdf"
          limit="1"
          :file-list="contractFileList"
          :on-success="contractSuccess"
          :on-error="contractError"
          :auto-upload="false"
          :show-file-list="true">
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><plus/></el-icon>
      </el-upload>
      <span style="color: forestgreen;">&nbsp;&nbsp;{{contractTip}}</span>
    </el-form-item>
    <el-form-item label=" ">
      <el-button type="primary" @click="submitUpload">提交</el-button>
      <el-button type="cancel" @click="goBack" plain>取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {Plus} from "@element-plus/icons-vue";
import {useRoute, useRouter} from "vue-router";
import {doGet, doPut} from "../http/httpRequest.js";
import axios from "axios";
import router from "../router/router.js";

function goBack() {
  router.go(-1)
}
function imageSuccess() {
  imageFileList.value = []
  imageTip = "头像上传成功"
}
function imageError() {
  imageFileList.value = []
  imageTip = "头像上传失败"
}
function contractSuccess() {
  console.log("头像上传成功")
  contractFileList.value = []
  contractTip = "合同上传成功"
}
function contractError() {
  contractFileList.value = []
  contractTip = "合同上传失败"
}
let imageTip = ref("")
let contractTip = ref("")
let imageFileList = ref([])
let contractFileList = ref([])
axios.defaults.baseURL = "http://localhost:8080";
let userInfo = ref({})

const uploadImageRef = ref( {} )

const uploadContractRef = ref ( {} )

let route = useRoute()
let id = route.params.id

onMounted(() => {
  // 根据id查询用户信息
  doGet("/api/user/" + id, {}).then(resp => {
    if (resp.data.code === 200) {
      userInfo.value = resp.data.data;
    }
  })
})

function submitUpload() {
  // 提交用户信息
  let formData = new FormData()
  for (let field in userInfo.value) {
    if (field !== 'userImageDO' && field !== 'userContractDO') {
      formData.append(field, userInfo.value[field])
    }
  }
  doPut("/api/user", formData).then(resp => {
    if (resp.data.code === 200) {
      console.log("更新成功......")
    }
  })
  uploadImageRef.value.submit()
  uploadContractRef.value.submit()
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>