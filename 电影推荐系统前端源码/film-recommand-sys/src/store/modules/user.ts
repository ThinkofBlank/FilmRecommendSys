export const userStore = defineStore({
  id: "user",
  state: (): {
    userInfo: UserInfo;
    token: string;
  } => ({
    userInfo: null,
    token: null,
  }),
  getters: {
    userId: (state) => state.userInfo?.id || null,
    userName: (state) => state.userInfo?.username || null,
    userRole: (state) => state.userInfo?.adminStatus || "-1",
  },
  actions: {
    setUserInfo(userInfo: UserInfo) {
      this.userInfo = Object.assign({}, this.userInfo, userInfo);
    },

    setToken(token: string) {
      this.token = token;
    },

    clear() {
      this.$reset();
    },
  },
  persist: {
    storage: window.sessionStorage,
    paths: ["userInfo", "token"],
  },
});
