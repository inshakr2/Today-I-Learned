const express = require('express');

const PORT = 3201;

// APP
const app = express();
app.get('/', (req, res) => {
    res.send('hello Node App, Review');
});

app.listen(PORT);
console.log(`Server Running on http://localhost:${PORT}`);