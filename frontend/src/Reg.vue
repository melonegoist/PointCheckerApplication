<script setup>
import Header from './components/Header.vue';
import RegForm from './components/RegForm.vue';
</script>

<template>
    <Header>
        <template #blank><a id="login" href="http://localhost:5173/">Log In</a></template>
        <template #h_name>melon_egoist reg</template>
        <template #h_group>P3216</template>
    </Header>

    <div class="space"></div>

    <h1>Registration</h1>

    <div class="space"></div>

    <RegForm></RegForm>
</template>

<style scoped>
    h1 {
        text-align: center;
        color: cadetblue;
    }

    .space {
        margin: 0;
        margin-top: 50px;
        margin-bottom: 50px;
    }

    #login {
        color: bisque;
        background-color: rgba(255, 228, 196, 0.1);
        border: 1px solid bisque;
        font-size: 20px;
        font-weight: bolder;
        margin-left: 20px;
        padding: 15px;
        border-radius: 20px;
        transition: 0.2s;
    }

    #login:hover {
        background-color: rgba(255, 228, 196, 0.3);
    }
</style>
