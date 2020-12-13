const express= require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const cors= require('cors');

var app= express();
app.listen(3000, function(){
    console.log("connected");
});



app.use(bodyParser.json());
app.use(cors());
app.use(bodyParser.json());


//db 연결
var db = mongoose.connection;
db.on('error', console.error);
db.once('open', function(){
    // CONNECTED TO MONGODB SERVER
    console.log("Connected to mongod server");
});

mongoose.connect(process.env.MONGO_URI)
    .then(() => console.log('Successfully connected to mongodb'))
    .catch(e => console.error(e));

//define model
var Lip_reading= require('./models/lip_reading');

app.get('/api/lip_reading',(req,res)=>{

    db.lip_reading.find(function(err,lip_readings){
        if(err) return res.status(500).json({error:err});
        res.json(lip_readings);
    })

})