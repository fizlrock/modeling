import { createRouter, createWebHistory } from "vue-router";

import Lab1View from "./views/Lab1View.vue";
import Lab2View from "./views/Lab2View.vue";
import HomeView from "./views/HomeView.vue";

export default createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/lab/1",
      name: "lab1",
      component: Lab1View,
    },
    {
      path: "/lab/2",
      name: "lab2",
      component: Lab2View,
    },
    {
      path: "/:path(.*)*",
      name: "NotFound",
      redirect: { name: "home" },
    },
  ],
  sensitive: true,
});
