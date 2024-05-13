import { defineStore } from "pinia";
import { userStore } from "./modules";
import { logout as loginOut } from "@/api";

export const mainStore = defineStore({
  id: "main",
  state: (): {
    login: {
      account?: string;
    };
  } => ({
    login: {},
  }),
  getters: {
    saveAccount: (state) => state.login.account,
  },
  actions: {
    setLoginAccount(account: string) {
      this.login.account = account;
    },

    clearLoginAccount() {
      this.login.account = null;
    },

    clear() {
      this.$reset();
    },
  },
  persist: {
    storage: window.localStorage,
    paths: ["login.account"],
  },
});

export const logout = () => {
  const userStoreIns = userStore();

  Promise.resolve().then(() => window.location.reload());

  loginOut(userStoreIns.userId);

  userStoreIns.clear();
};

export * from "./modules";
