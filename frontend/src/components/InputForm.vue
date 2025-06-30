<template>
    <form action="">
        <label for="first"><slot name="first"></slot></label>
        <input id="first" type="text">
        <input type="text">
        <input type="text">
    </form>
</template>

<style scoped>
    form {
        background-color: black;
        color: wheat;
    }
</style>
