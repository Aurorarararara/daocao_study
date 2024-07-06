<template>
  <el-tabs
      v-model="activeTab"
      type="card"
      closable
      class="demo-tabs"
      @tab-change="goToTab"
      @tab-remove="closeTab"
  >
    <el-tab-pane
        v-for="item in tabList"
        :key="item.path"
        :label="item.title"
        :name="item.path"
        :closable="!item.isClose"
    >
      <RouterView/>
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>
import {RouterView} from "vue-router";
//Tab数据从Store中获取
import {useMenuStore} from '@/api/stores/menu.js'
import {storeToRefs} from 'pinia'
import {useRouter} from "vue-router";

const router = useRouter();
const MenuStore = useMenuStore();
const {tabList, activeTab} = storeToRefs(MenuStore);


// 选中
function goToTab(path) {
  MenuStore.setActive(path);
  router.push(path);
}

//移除
function closeTab(path) {
  // 移除的是否是当前激活的tab，激活首页
  if (path === activeTab) {
    MenuStore.setActive("/index");
    router.push("/index");
  }
  MenuStore.delTabList(path);
}
</script>


<style scoped>

</style>