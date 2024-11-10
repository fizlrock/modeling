import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
  plugins: [vue()],
  build: {
    rollupOptions: {
      external: ['./src/views/Lab1View.vue'] // укажите путь к вашему файлу
    }
  }

});



