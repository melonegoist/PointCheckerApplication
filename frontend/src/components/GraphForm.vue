<template>
    <form @submit.prevent="postForm">
        <label for="x-input">
            <slot name="x-input-label"></slot>
        </label> <br>
        <input type="text" id="x-input" v-model="coordinates.x" required/>

        <div class="space"></div>

        <label for="y-input">
            <slot name="y-input-label"></slot>
        </label> <br>
        <input type="text" id="y-input" v-model="coordinates.y" required/>

        <div class="space"></div>

        <label for="r-input">
            <slot name="r-input-label"></slot>
        </label> <br>
        <input type="text" id="r-input" v-model="coordinates.r" required/>

        <br>
        <br>

        <button type="submit" id="submit-button" >Draw!</button>
        </form>
</template>

<script>
    import { eventBus } from '@/eventBus';
    import { graphResponse } from '@/graphState';
    import { userLogin } from '@/authState';

    export default {
        data() {
            return {
                coordinates: {
                    x: "",
                    y: "",
                    r: "",
                    owner: ""
                }
            };
        },

        methods: {
            async postForm() {
                try {
                    this.coordinates.owner = userLogin.value;

                    const response = await fetch("http://localhost:8081/graph", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`,
                        },
                        body: JSON.stringify(this.coordinates)
                    }).then(response => response.json())
                    .then(response => graphResponse.value = response)


                    if (eventBus.value.triggerFunction) {
                    eventBus.value.triggerFunction();
                    }   
                    
                }
                catch (Error) {
                    console.log("Error!", Error);
                }
            },
        }
    }
</script>

<style scoped>
    form {
        display: flexbox;
        width: 20%;
        background-color: #171717;
        padding: 20px;
        height: fit-content;
        border: 1px solid black;
        border-radius: 20px;
        box-shadow: 0px 0px 5px beige;
        font-size: large;
        justify-items: center;
        text-align: center;
    }

    label {
        color:darksalmon;
        font-weight: bold;
    }

    input {
        font-size: x-large;
        height: fit-content;
        text-align: center;
        background-color: bisque;
        color: #171717;
        font-weight: bolder;
        border: 1px solid black;
        box-shadow: 0px 0px 10px black;
        border-radius: 10px;
    }

    .space {
        margin: 0;
        margin-top: 100px;
        margin-bottom: 100px;
    }

    #submit-button {
        display: inline-block;
        background-color: beige;
        border: 1px solid beige;
        font-weight: bolder;
        color: #171717;
        border-radius: 3px;
        width: 30%;
        height: 50%;
}
</style>

