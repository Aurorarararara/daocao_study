<template>
  <div class="common-layout">
    <el-container>
      <el-aside class="aside" :style="{height: height + 'px',minHeight:minHeight+'px'}">
        <Aside/>
      </el-aside>
      <el-container>
        <el-header class="header">
          <Header/>
        </el-header>
        <el-main>
          <Main/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
// 导入左侧导航组件
import Aside from '@/api/Layout/Aside/index.vue'
import Header from '@/api/Layout/Header/index.vue'
import Main from '@/api/Layout/Main/index.vue'
import {ref, onMounted} from "vue";

const height = ref(0)
let minHeight = ref(0)

onMounted(() => {
  getHeight();
  // 监听页面缩放
  minHeight = document.documentElement.clientHeight;
  window.addEventListener("resize",getHeight);
})

// 获取文档的高度
function getHeight() {
  height.value = document.documentElement.clientHeight;
  console.log(height.value)
}
</script>

<style scoped>
.aside {
  background-color: #304156;
}
</style>