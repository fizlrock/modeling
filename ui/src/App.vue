<script setup lang="ts">
import { RouterView } from "vue-router";

import HeaderPart from "./parts/HeaderPart.vue";
import FooterPart from "./parts/FooterPart.vue";
</script>

<template>
  <HeaderPart />
  <div id="content">
    <RouterView />
  </div>
  <FooterPart />
</template>

<style>
#content {
  background-color: #fff;
  padding: 30px;
}
</style>
