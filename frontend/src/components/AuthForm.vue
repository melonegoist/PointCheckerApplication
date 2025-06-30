<template>
    <form class="auth-form" @submit.prevent="submitForm">
        <label for="">Login:</label>
        <input id="login-field" type="text" v-model="formData.username" required />

        <div class="space"></div>

        <label for="password-field">Password:</label>
        <br>
        <div class="password-with-icon">
            <input id="password-field" type="password" v-model="formData.password" required />
            <img @click="changeIcon" id="lock-icon" src="./icons/lock_closed.png" alt="">
        </div>
        <br>

        <div class="space"></div>
        <button id="login-button" type="submit">Log in</button>
    </form>
</template>

<style scoped>
    .auth-form {
        color: whitesmoke;
        text-align: center;
    }

    .auth-form label {
        font-size: 25px;
    }

    .auth-form input {
        display: block;
        background-color: #121212;
        border: 1px solid wheat;
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

    #login-button {
        background-color: wheat;
        color: #121212;
        border: 1px solid wheat;
        border-radius: 10px;
        width: 10%;
        height: 50px;
        font-size: 25px;
        font-weight:bolder;
    }
</style>

<script>
    import { status, errorMessage, userLogin, setToken } from '@/authState';

    export default {
        data() {
            return {
                formData: {
                    username: "",
                    password: "",
                },
            };
        },

        methods: {
            changeIcon() {
                const icon = document.getElementById("lock-icon");
                const pass = document.getElementById("password-field");

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
                    const response = await fetch("http://localhost:8081/authenticate", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify(this.formData),
                    });

                    const statusCode = response.status;
                    const token = await response.json();

                    if (statusCode == 200) {
                        console.log("Success!");
                        status.value = true;
                        errorMessage.value = "";

                        setToken(token.jwt);

                        userLogin.value = this.formData.username;
                    } else if (statusCode == 403) {
                        console.log("Auth error!");
                        errorMessage.value = "Wrong password or username";
                        userLogin.value = "";
                    } else {
                        console.log("Wrong username!");
                        errorMessage.value = "Wrong username";
                    }
                
                } catch(error) {
                    console.log("Error", error);
                }
            }
        },
    };
</script>
