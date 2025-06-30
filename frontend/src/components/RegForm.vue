<template>
    <form class="reg-form" @submit.prevent="submitForm">
        <label for="login-field">Login:</label>
        <input id="login-field" type="text" v-model="regData.username" required />

        <div class="space"></div>

        <label for="password-field">Password:</label>
        <br>
        <div class="password-with-icon">
            <input id="password-field" type="password" v-model="regData.password" required />
            <img @click="changeIcon" id="lock-icon" src="./icons/lock_closed.png" alt="">
        </div>
        <br>

        <div class="space"></div>
        <button id="reg-button" type="submit" @click="moveToLoginPage">Sign in</button>
    </form>
</template>

<style scoped>
    .reg-form {
        color: whitesmoke;
        text-align: center;
    }

    .reg-form label {
        font-size: 25px;
    }

    .reg-form input {
        display: block;
        background-color: #121212;
        border: 1px solid cadetblue;
        border-radius: 20px;
        color: whitesmoke;
        margin: 0 auto;
        margin-top: 20px;
        font-size: 40px;
        text-align: center;
        padding: 10px;
    }

    .space {
        margin: 0;
        margin-top: 100px;
        margin-bottom: 100px;
    }

    .password-with-icon {
        position: relative;
        display: inline-block;
    }

    #lock-icon {
        position: absolute;
        right: 15px;
        top: 35%;
        transform: scale(0.5) translateY(-50%);
    }

    #reg-button {
        background-color:cadetblue;
        color: #121212;
        border: 1px solid cadetblue;
        border-radius: 10px;
        width: 10%;
        height: 50px;
        font-size: 25px;
        font-weight:bolder;
    }
</style>

<script>
    export default {
        data() {
            return {
                regData: {
                    username: "",
                    password: ""
                },
            };
        },

        methods: {
            changeIcon() {
                let icon = document.getElementById("lock-icon");
                let pass = document.getElementById("password-field");

                if (icon.src == "http://localhost:5173/src/components/icons/lock_closed.png") {
                    pass.type = "text";
                    icon.src = "http://localhost:5173/src/components/icons/lock_opened.png";
                }
                else {
                    pass.type = "password";
                    icon.src = "http://localhost:5173/src/components/icons/lock_closed.png";
                }
            },

            async submitForm() {
                try {
                    const response = await fetch("http://localhost:8081/register", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify(this.regData),
                    });

                    const statusCode = response.status;

                    if (statusCode == 200) {
                        console.log("Registration success!");
                    } else {
                        console.log("Unexpected error!");
                    }
                
                } catch(error) {
                    console.log("Error", error);
                }
            },

            moveToLoginPage() {
                location.href = "./";
            }
        }
    }
</script>
