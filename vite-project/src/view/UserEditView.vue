<template>

  <el-form
      label-width="auto"
      :model="userInfo"
      style="max-width: 600px">

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
    <el-upload
        ref="uploadImageRef"
        class="avatar-uploader"
        action="http://localhost:8080/api/user/image"
        :show-file-list="true"
        :auto-upload="false">
      <img v-if="imageUrl" :src="imageUrl" class="avatar" />
      <el-icon v-else class="avatar-uploader-icon"><plus/></el-icon>
    </el-upload>
    <el-upload
        ref="uploadContractRef"
        action="http://localhost:8080/api/user/contract"
        :auto-upload="false"
        :show-file-list="true">
      <el-button type="primary">Click to upload</el-button>
      <template #tip>
        <div class="el-upload__tip">
          jpg/png files with a size less than 500KB.
        </div>
      </template>
    </el-upload>
    <el-form-item>
      <el-button type="primary" @click="submitUpload">提交</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {Minus, Plus} from "@element-plus/icons-vue";
import {useRoute} from "vue-router";
import {doGet} from "../http/httpRequest.js";

let userInfo = ref({})

const uploadImageRef = ref( {} )

const uploadContractRef = ref ( {} )

let route = useRoute()
let id = route.params.id

onMounted(() => {
  // 根据id查询用户信息
  doGet("http://localhost:8080/api/user/" + id, {}).then(resp => {
    if (resp.data.code === 200) {
      userInfo.value = resp.data.data;
    }
  })
})

function submitUpload() {
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