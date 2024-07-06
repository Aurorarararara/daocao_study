<template>
  <div>
    <el-menu background-color="#304156" text-color="#b1becd" active-text-color="#336cab">
      <!--遍历子元素-->
      <template v-for="menu in menuList" :key="menu.path">
        <!--判断是否有子菜单-->
        <el-sub-menu v-if="hasChildren(menu)" :index="menu.path">
          <template #title>
            <span>{{ menu.menuName }}</span>
          </template>
          <!--渲染子菜单-->
          <el-menu-item-group>
            <el-menu-item
                v-for="children in menu.children"
                :index="children.path"
                @click="handleRouter(children)"
            >
              <span>{{ children.menuName }}</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>
        <!--没有子菜单-->
        <el-menu-item
            v-else
            :index="menu.path"
            @click="handleRouter(menu)"
        >
          <span>{{ menu.menuName }}</span>
        </el-menu-item>
      </template>

    </el-menu>
  </div>
</template>

<script setup>
// 获取所有的菜单，在pinia中
import {useMenuStore} from '@/api/stores/menu.js'
//放到ref里面 pinia提供了storeToRefs()方法将数据取出放到ref中
import {storeToRefs} from 'pinia'
//引入vue-router
import {useRouter} from "vue-router";

const router = useRouter();
const MenuStore = useMenuStore();
const {menuList} = storeToRefs(MenuStore);

//是否有子菜单
function hasChildren(menu) {
  if (menu.children && menu.children.length > 0) {
    return true;
  }
  return false;
}

//切换路由
function handleRouter(menu) {
  // 向tabList中添加数据,已经添加过就不需要添加了
  let hasNode = MenuStore.tabList.filter(item => item.path === menu.path)
  // 修改activeTab的值
  if (hasNode == null || hasNode.length === 0) {
    let data = {title: menu.menuName, path: menu.path};
    MenuStore.setTabList(data)
  }
  //修改active的值
  MenuStore.setActive(menu.path);
  router.push(menu.path);
}

</script>

<style scoped>

</style>