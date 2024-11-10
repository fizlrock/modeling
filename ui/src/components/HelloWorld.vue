<script setup lang="ts">
defineProps<{
  message: string;
}>();
</script>

<template>
  <h3>Hello World</h3>
  <p class="msg">{{ message }}</p>
</template>

<style scoped>
p.msg {
  margin-left: 10px;
  color: #35495e;
  font-size: 110%;
}
</style>
