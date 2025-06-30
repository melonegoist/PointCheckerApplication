<script setup>
import { errorStatus } from '@/graphState';
</script>

<template>
    <canvas id="graph" width="700" height="700" @click="handleClick"></canvas>
    <div v-if="errorStatus">
        <p>{{ errorStatus }}</p>
    </div>
    <button id="show_dots_button" @click="getDots">Show All Dots</button>
    <button id="show_graphs_button" @click="getGraphs">Show Graphs</button>
</template>

<script>
import { eventBus } from '@/eventBus';
import { graphResponse, allDotsJson } from '@/graphState';
import { userLogin } from '@/authState';
    export default {
        mounted() {
            eventBus.value.triggerFunction = this.wrapper;
        },

        beforeUnmount() {
            eventBus.value.triggerFunction = null;
        },

        methods: {
            wrapper() {
                const x = graphResponse.value.x;
                const y = graphResponse.value.y;
                const r = graphResponse.value.r;

                const inArea = graphResponse.value.inArea;

                const notificationState = graphResponse.value.needNotification;

                if (notificationState) {
                    alert('You missed point twice in a row!')
                }

                if (inArea.startsWith("Error")) {
                    console.log(inArea);
                    errorStatus.value = inArea.substring(7) + ", value must be int in correct range!";
                } else {
                    errorStatus.value = "";
                    this.drawGraph(x, y, r, inArea);
                }
            },

            drawGraph(x, y, r, inArea) {
                const display = document.getElementById("graph");
                const canvas = display.getContext("2d");

                display.style.cssText = "background-color: bisque";

                const W = 700
                const H = 700

                const scale = (W-20)/10;

                x*=scale
                y*=scale
                r*=scale

                canvas.clearRect(0, 0, W, H);                


                // rect drawing
                canvas.beginPath()
                canvas.fillStyle = "cadetblue"
                canvas.fillRect(W/2 - r, H/2, r, r/2);
                canvas.fill()

                // triangle drawing
                canvas.beginPath()

                canvas.moveTo(W/2, H/2);
                canvas.lineTo(W/2+r, H/2);
                canvas.lineTo(W/2, H/2+r/2);
                
                canvas.fill();

                // circle drawing
                canvas.beginPath()

                canvas.moveTo(W/2, H/2);
                canvas.arc(W/2, H/2, r, Math.PI/180, (Math.PI/180)*270, true);

                canvas.fill();

                // arrows drawing
                canvas.beginPath()

                canvas.moveTo(0, H/2);
                canvas.lineTo(W, H/2);
                canvas.moveTo(W/2, 0);
                canvas.lineTo(W/2, H);

                canvas.stroke()


                // label drawing
                canvas.beginPath()
                canvas.fillStyle = "black"
                canvas.font = "7px Verdana";  

                let textCounter = -5;

                for (let i = 10; i <= W-10; i += scale) {
                    canvas.fillRect(i, H/2-2.5, 2, 5)
                    canvas.fillText(textCounter, i-5, H/2+15);

                    textCounter++;
                }


                textCounter = 5;

                for (let j = 10; j <= H-10; j += scale) {
                    if (textCounter != 0) {
                        canvas.fillRect(W/2 - 2.5, j, 5, 2);
                        canvas.fillText(10 - textCounter, W/2-10, j+5, 10);
                    }

                    textCounter++;
                }
            

                // dot drawing
                canvas.beginPath()
                if (inArea == "in Area!") {
                    canvas.fillStyle = "green";                    
                } else {
                    canvas.fillStyle = "red"
                    console.log(inArea);
                }

                canvas.arc(W/2 + x, H/2 - y, 10, 0, 360);
                canvas.fill()

            },

            handleClick(event) {
                const canvas = document.getElementById("graph");
                const rect = canvas.getBoundingClientRect();

                let x = event.clientX - rect.left;
                let y = event.clientY - rect.top;

                const scale = (canvas.width - 20) / 10;
                const graphX = (x - canvas.width / 2) / scale;
                const graphY = (canvas.height / 2 - y) / scale;

                this.drawPointOnGraph(x, y);
                this.sendPointToServer(graphX.toFixed(0), graphY.toFixed(0), graphResponse.value.r);


            },

            drawPointOnGraph(x, y) {
                const canvas = document.getElementById("graph").getContext("2d");

                canvas.beginPath();
                canvas.fillStyle = "black";
                canvas.arc(x, y, 5, 0, 360);
                canvas.fill();
            },

            async sendPointToServer(x, y, r) {
                const coordinates = {x, y, r}
                try {
                    const response = await fetch("http://localhost:8081/graph", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`,
                        },
                        body: JSON.stringify(coordinates)
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

            async getDots() {
                const response = await fetch(`http://localhost:8081/graph/dots?username=${userLogin.value}`, {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`,
                    }
                });

                allDotsJson.value = await response.json();
                
            },

            getGraphs() {
                location.href = "http://localhost:3000/d/b01a2fa0-954a-4219-a485-b223d01a24d5/lab4opi?orgId=1&from=now-30m&to=now"
            }
        }
    }
</script>

<style scoped>
    #graph {
        display: flex;
        position: absolute;
        align-self: center;
        margin: 0;

        right: 300px;
        top: 300px;
    }

    p {
        color: brown
    }

    #show_dots_button {
        font-size: 15px;
        padding: 5px;
        border: 1px solid whitesmoke;
        border-radius: 5px;
        font-weight: bolder;
        background-color: darkslategrey;
        color:whitesmoke;
        opacity: 60%;
    }
</style>

