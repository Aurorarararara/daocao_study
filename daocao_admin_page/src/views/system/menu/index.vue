<template>
  <!--头部搜索框-->
  <el-form :model="queryForm" label-width="120px">
    <el-form-item label="菜单名称">
      <el-input v-model="queryForm.menuName"/>
    </el-form-item>
    <el-form-item label="权限名称">
      <el-input v-model="queryForm.perms"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleQuery">搜索</el-button>
      <el-button>重置</el-button>
    </el-form-item>
  </el-form>
  <!--功能按钮-->
  <el-row :gutter="20">
    <el-col :span="6">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="success" @click="handleEdit">修改</el-button>
      <el-button type="danger" @click="handleRemove">删除</el-button>
    </el-col>
  </el-row>
  <!--列表-->
  <el-table :data="menuList" style="width: 100%">
    <el-table-column type="selection" width="55"/>
    <el-table-column type="index" label="序号" width="55"/>
    <el-table-column prop="manuName" label="菜单名称" width="150"/>
    <el-table-column prop="perms" label="权限名称" width="150"/>
    <el-table-column prop="path" label="组件路径" width="150"/>
    <el-table-column prop="componentPath" label="组件名称" width="150"/>
    <el-table-column prop="createTime" label="创建时间" width="150"/>
    <el-table-column prop="updateTime" label="修改时间" width="150"/>
    <el-table-column prop="remark" label="备注" width="150"/>
    <el-table-column label="操作" width="150">
      <template #default>
        <el-button link type="primary" size="small">新增</el-button>
        <el-button link type="success" size="small">修改</el-button>
        <el-button link type="danger" size="small">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <!--分页-->
  <div class="pagination-container">
    <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        :page-sizes="[10, 20, 30, 40,50]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
  <!--新增和修改的弹窗-->
  <el-dialog
      v-model="menuFormShow"
      :title="menuTitle"
      width="500"
      :before-close="handleClose"
  >
    <el-form :model="form" label-width="120px">
      <!--表单-->
      <el-form-item label="上级菜单">
        <el-input v-model="form.parentId"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="menuFormShow = false">取消</el-button>
        <el-button type="primary" @click="menuFormShow = false">提交</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref} from "vue";

let total = ref(0)
let menuFormShow = ref(false);
let menuTitle = ref("");
let queryForm = ref({
  menuName: undefined,
  perms: undefined,
  pageNum: 1,
  pageSize: 10,
})
let form = ref({
  id: undefined,
  parentId: undefined,
  menuName: undefined,
  path: undefined,
  componentPath: undefined,
  perms: undefined,
  icon: undefined,
  menuType: 0,
  sort: 0,
  status: 0,
  remark: undefined,
})
let menuList = ref([
  {
    id: 1,
    manuName: '菜单管理',
    perms: 'system:menu:list',
    path: '/system/menu/list',
    componentPath: '/system/menu/list',
    createTime: '2023-12-25 10:22:22',
    updateTime: '2023-12-25 10:22:22',
    remark: '备注',
    children: []
  },
  {
    id: 2,
    manuName: '菜单管理',
    perms: 'system:menu:list',
    path: '/system/menu/list',
    componentPath: '/system/menu/list',
    createTime: '2023-12-25 10:22:22',
    updateTime: '2023-12-25 10:22:22',
    remark: '备注'
  }
])

function handleQuery() {

}

function handleSizeChange() {

}

function handleCurrentChange() {

}

function handleClose(){
  menuFormShow = false;
}

function handleAdd(){
  menuFormShow.value = true;
  menuTitle.value = "新增菜单";
}
function handleEdit(){
  // 先查询数据，再弹窗
  menuFormShow.value = true;
  menuTitle.value = "修改菜单";
}
function handleRemove(){

}

</script>

<style scoped>
.pagination-container {
  position: relative;
  height: 40px;
  margin-top: 15px;

}

.el-pagination {
  position: absolute;
  right: 100px;
}
</style>