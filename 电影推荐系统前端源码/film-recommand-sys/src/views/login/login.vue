<template>
  <div :class="['full-screen', wrapName]">
    <div :class="b('icon')">
      <svg-icon :class-name="e('film')" icon-class="film" />
    </div>

    <van-form ref="formRef" :class="[b('form'), 'theme-form']" @submit="onSubmit">
      <van-cell-group inset>
        <van-field
          v-model="form.account"
          :rules="rules['account']"
          name="account"
          placeholder="请输入账户名"
          maxlength="20"
        />

        <van-field
          v-model="form.password"
          :rules="rules['password']"
          name="password"
          placeholder="请输入密码"
          type="password"
          maxlength="20"
        />

        <van-field name="saveFlag" class="operate-line">
          <template #input>
            <van-checkbox v-model="form.saveFlag" shape="square">记住账号名</van-checkbox>
          </template>

          <template #button>
            <router-link to="/reset-pwd"> <span :class="m('link')">忘记密码</span> </router-link>
            <router-link to="/register"> <span :class="m('link')">注册新账户</span> </router-link>
          </template>
        </van-field>

        <van-button
          class="operate-line"
          color="linear-gradient(to right, #FCCF31, #F55555)"
          native-type="submit"
          block
          round
          :loading="loading"
        >
          登&nbsp;&nbsp;&nbsp;&nbsp;录
        </van-button>
      </van-cell-group>
    </van-form>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { mainStore, userStore } from "@/store";
import { login, getUserInfo } from "@/api";

import type { FormInstance } from "vant";
import { notifyError } from "@/utils";

const { wrapName, b, e, m } = useWrap("login");
const mainStoreIns = mainStore();
const userStoreIns = userStore();
const router = useRouter();
const formRef = ref<FormInstance>();

const rules = {
  account: [
    {
      required: true,
      message: "请输入账户名",
    },
  ],
  password: [
    {
      required: true,
      message: "请输入密码",
    },
  ],
};

const form = reactive({
  account: mainStoreIns.saveAccount || "",
  password: "",
  saveFlag: !!mainStoreIns.saveAccount,
  adminStatus: "0" as "-1" | "0" | "1",
});
const loading = ref(false);

const onSubmit = ({ saveFlag, ...formData }: { saveFlag: boolean; account: string; password: string }) => {
  loading.value = true;
  login({
    ...formData,
    adminStatus: form.adminStatus,
  })
    .then((token) => {
      userStoreIns.setToken(token);

      return getUserInfo().then((userInfo) => {
        if (userInfo.adminStatus === "1" && userInfo.auditStatus === "1") {
          notifyError("正在审核中...");
          userStoreIns.clear();
          return;
        } else if (userInfo.adminStatus === "0" && userInfo.enabled === "0") {
          notifyError("您的账号已被禁用，无法正常登录！");
          userStoreIns.clear();
          return;
        }

        if (saveFlag) {
          mainStoreIns.setLoginAccount(formData.account);
        } else {
          mainStoreIns.clearLoginAccount();
        }

        userStoreIns.setUserInfo(userInfo);

        // 判断角色进入哪个主页
        router.replace(userInfo.adminStatus === "1" ? "/home-admin" : "/");
      });
    })
    .finally(() => (loading.value = false));
};
</script>

<style lang="scss" scoped>
@include wrap(login) {
  @include b(icon) {
    margin-top: 100px;
  }

  @include e(film) {
    width: 120px;
    height: 120px;
  }

  @include b(form) {
    margin-top: 20px;
  }

  @include m(link) {
    margin-left: 8px;
    color: $main-color;
  }
}
</style>
