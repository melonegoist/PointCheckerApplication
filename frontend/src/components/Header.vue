<template>
    <div class="header">
        <!-- <h2 id="date">
            <slot name="h_date"></slot>
        </h2> -->
        <slot name="blank">
            
        </slot>

        <h1 id="name">
            <slot name="h_name"></slot>
        </h1>

        <h2 id="group_name">
            <slot name="h_group"></slot>
        </h2>
    </div>

    <div class="auth-form">
        
    </div>
</template>

<style scoped>
    .header {
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: space-between;
        border: 1px solid wheat;
        color:bisque;
        border-radius: 25px;
        margin: 0 auto;
        height: fit-content;
        width: 100%;
        align-items: center;
    }

    #name {
        text-align: center;
        transition: 1s;
    }

    #group_name {
        margin-right: 20px;
    }

    #date {
        margin-left: 20px;
    }
</style>
