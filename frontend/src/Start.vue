<script setup>
import Header from './components/Header.vue';
import AuthForm from './components/AuthForm.vue';
import GraphForm from './components/GraphForm.vue';
import Graph from './components/Graph.vue';
import { onMounted } from 'vue';
import { status, errorMessage, userLogin, clearToken } from "@/authState";
import { allDotsJson } from './graphState';


onMounted(async () => {
  const jwt = localStorage.getItem("jwtToken");

  if (jwt != "" && jwt != null) {
        const response = await fetch("http://localhost:8081/validateToken", {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${jwt}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        "token": jwt,
      }),
    })

    const jsonResponse = await response.json();    
    const validated = jsonResponse.validated;

    if (validated) {
      status.value = true;
      userLogin.value = jsonResponse.username;
    } else {
      console.log("jwt is expired or even wrong!");
    }

    if (!response.ok) {
        clearToken();
      }

  }
  })
</script>

<template>
    <Header>
        <template #blank>
          <div v-if="userLogin">
            <button v-on:click="logout" id="corner_button">Log Out</button>
          </div>
          <div v-else>
            <a id="corner_button" href="http://localhost:5173/reg">Register</a>
          </div>
        </template>
        <template #h_name>melon_egoist</template>
        <template #h_group>P3216</template>
    </Header>

    <div v-if="!status">
      <h1 id="welcome-label">Welcome, log in to use lab4</h1>
      <div class="space"></div>
      <AuthForm></AuthForm> 

      <div v-if="errorMessage">
        <h2 id="errorMessage">{{ errorMessage }}</h2>
      </div>
    </div>

    <div v-else>
      <div v-if="userLogin == 'serzh'">
        <h2 id="welcome-user-label">Welcome, <u style="color: goldenrod;">best practic ever</u></h2>
        <img id="piggy" src="./components/icons/piggy.png" alt="">
      </div>

      <div v-else>
        <h2 id="welcome-user-label">Welcome, {{ userLogin }}</h2>
      </div>

      <div class="space"></div>

      <GraphForm>
        <template #x-input-label>Set X value in range(-3, 3):</template>
        <template #y-input-label>Set Y value in range(-2, 5):</template>
        <template #r-input-label>Set R value in range(1, 5):</template>
      </GraphForm>

      <div class="space"></div>

      <Graph />

      <div id="dashboard" v-if="allDotsJson.inArea != ''">
        <h4>==OWNER=====X=====Y=====R=====inArea?==</h4>
        <p v-for="el in allDotsJson">{{ `${el.owner} |||| ${el.x} |||| ${el.y} |||| ${el.r} |||| ${el.inArea}`}}</p>
      </div>

    </div>


</template>

<script>
export default {
  data() {
    return {
      users: [],
    };
  },

  name: "Start",

  methods: {
    resizeHeader() {
        const h_name = document.getElementById("name");
        const h_date = document.getElementById("date");
  
        let dateWidth = h_date.clientWidth;
        h_name.style.cssText = `margin-right: ${dateWidth-45}px;`;
    },

    logout() {
      localStorage.removeItem("jwtToken");
      window.location.reload();
    }
  },
};
</script>

<style scoped>
    .space {
        margin-top: 100px;
        margin-bottom: 100px;
    }

    #welcome-label {
      color:darkcyan;
      text-align: center;
    }

    #errorMessage {
      text-align: center;
      color: brown
    }

    #welcome-user-label {
      text-align: center;
      color: whitesmoke
    }

    #graph-response {
      color: goldenrod
    }

    #dashboard {
      color: bisque;
    }

    #corner_button {
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

    #corner_button:hover {
      background-color: rgba(255, 228, 196, 0.3);
    }

    h4 {
      color: whitesmoke;
      font-weight: bolder;
    }

    #piggy {
      display: flexbox;
      position: absolute;
      z-index: -1;
      /* transform: scale(500%); */
      width: 100%;
      height: 100%;
      top: 0%;
}
</style>
