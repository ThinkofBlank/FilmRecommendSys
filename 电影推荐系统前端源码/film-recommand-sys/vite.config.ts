import { defineConfig, loadEnv, UserConfig } from "vite";
import viteCompression from "vite-plugin-compression";
import legacy from "@vitejs/plugin-legacy";
import vue from "@vitejs/plugin-vue";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { VantResolver } from "unplugin-vue-components/resolvers";
import path from "path";
import { createHtmlPlugin } from "vite-plugin-html";
import { createSvgIconsPlugin } from "vite-plugin-svg-icons";
import DefineOptions from "unplugin-vue-define-options/vite";

function resolve(str: string): string {
  return path.resolve(__dirname, str);
}

// https://vitejs.dev/config/
export default ({ mode }: UserConfig) => {
  const env = loadEnv(mode as string, process.cwd());

  return defineConfig({
    base: mode === "production" ? `/${env.VITE_PACKAGE_NAME}/` : "/",
    server: {
      // Listening on all local IPs
      host: true,
      port: 8090,
      // Load proxy configuration from .env
      proxy: {
        "/api": {
          // target: "http://wyk5pr.natappfree.cc",
          target: "http://localhost:10010",
          changeOrigin: true,
          ws: true,
          rewrite: (path) => path.replace(new RegExp(`^/api`), ""),
        },
      },
    },
    build: {
      outDir: `../dist/${env.VITE_PACKAGE_NAME}`,
      terserOptions: {
        compress: {
          drop_console: true,
          drop_debugger: true,
        },
      },
      minify: "terser",
      chunkSizeWarningLimit: 500,
    },
    resolve: {
      alias: {
        "@": resolve("src"),
      },
    },
    css: {
      preprocessorOptions: {
        scss: {
          charset: false,
          additionalData: '@import "@/assets/style/main.scss";',
        },
      },
    },
    plugins: [
      vue(),
      DefineOptions(),
      AutoImport({
        dts: "types/auto-imports.d.ts",
        eslintrc: {
          enabled: true,
        },
        imports: ["vue", "vue-router", "pinia"],
      }),
      Components({
        // dirs 指定组件所在位置，默认为 src/components
        // 可以让我们使用自己定义组件的时候免去 import 的麻烦
        dirs: ["src/components/"],
        // 配置需要将哪些后缀类型的文件进行自动按需引入，'vue'为默认值
        extensions: ["vue"],
        // 解析组件，这里以 Element Plus 为例
        resolvers: [VantResolver()],
        dts: "types/components.d.ts",
      }),
      // gzip压缩 生产环境生成 .gz 文件
      viteCompression({
        verbose: true,
        disable: false,
        threshold: 10 * 1024,
        algorithm: "gzip",
        ext: ".gz",
      }),
      // 浏览器兼容
      legacy({
        targets: ["> 1%, last 1 version"],
        // additionalLegacyPolyfills: ["regenerator-runtime/runtime"], // 面向IE11时需要此插件
      }),

      // 自动注入html变量
      createHtmlPlugin({
        minify: true,
        inject: loadEnv(mode as string, process.cwd()),
      }),

      createSvgIconsPlugin({
        iconDirs: [path.resolve(process.cwd(), "src/assets/svg")], // icon存放的目录
        symbolId: "icon-[name]", // symbol的id
        inject: "body-last", // 插入的位置
        customDomId: "__svg__icons__dom__", // svg的id
      }),
    ],
  });
};
