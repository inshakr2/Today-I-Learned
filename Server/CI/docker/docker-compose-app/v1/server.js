const express = require("express");
const redis = require("redis");

// Create redis client
const client = redis.createClient(
    {
        host: "redis-server",
        port: 6379
    }
)

// cnt 0부터
client.set("number", 0);

const app = express();
app.listen("3202");
console.log("Server is running!");

app.get('/', (req, res) => {
    console.log("Listening!")
    client.get("number", (err, number) => {
        // 현재 카운트 값을 가져온 후 1씩 더하여 재저장
        client.set("number", parseInt(number) + 1)
        res.send("카운트가 1씩 증가. 카운트 : " + number)
    })
})