module.exports = {
  plugins: {
    autoprefixer: {
      overrideBrowserslist: [
        "Android 4.1",
        "iOS 7.1",
        "Chrome > 31",
        "ff > 31",
        "ie >= 8",
        "last 10 versions", // 所有主流浏览器最近10版本
      ],
      grid: true, // 开启grid布局的兼容(浏览器IE除外其他都能兼容grid，可以关闭开启)
    },
    "postcss-pxtorem": {
      rootValue: 37.5,
      propList: ["*"],
      unitPrecision: 5,
      replace: true, // 转换成 rem 以后，不保留原来的 px 单位属性
      mediaQuery: true, // 允许在媒体查询中转换px。
      minPixelValue: 2, // 设置要替换的最小像素值。
    },
  },
};
