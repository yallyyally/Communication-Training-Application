require('dotenv').config(); //환경 변수
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

//독화훈련 데이터 get
app.get('/api/lip_reading',(req,res)=>{

    Lip_reading.find(function(err,lip_readings){
        if(err) return res.status(500).json({error:err});
        if(!lip_readings) console.log("aaa");
        console.log(lip_readings);
        res.json(lip_readings);
    })

})

