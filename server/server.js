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

var Uiseong=require('./models/uiseong');

//var fs= require('fs');


//데이터 읽기(lip_reading)

/*
var XLSX= require('xlsx');
var workbook = XLSX.readFile('uiseong1.xlsx');
let worksheet = workbook.Sheets['Sheet1'];

var cell_idx=['A','B','C','D','E','F']

//의성 의태

    for(i=2; i<22; i++){
 
        var newData = new Uiseong(
            {
                id: worksheet[cell_idx[0]+i].v,
                link: worksheet[cell_idx[1]+i].v,
                answer: worksheet[cell_idx[2]+i].v,
                desc: worksheet[cell_idx[3]+i].v,
            }
        )
    
         newData.save(function(error, data){
            if(error){
                console.log(error);
            }else{
                console.log('Saved!')
            }
        });   
    
    
    }
*/


/*
var workbook = XLSX.readFile('lip_reading.xlsx');
let worksheet = workbook.Sheets['Sheet1'];

var cell_idx=['A','B','C','D','E','F']

console.log(worksheet[cell_idx[0]+1].v);


for(i=2; i<22; i++){
 
    var newData = new Lip_reading(
        {
            id: worksheet[cell_idx[0]+i].v,
            link: worksheet[cell_idx[1]+i].v,
            start: worksheet[cell_idx[2]+i].v,
            end: worksheet[cell_idx[3]+i].v,
            answer: worksheet[cell_idx[4]+i].v,
            type: worksheet[cell_idx[5]+i].v
        }
    )

     newData.save(function(error, data){
        if(error){
            console.log(error);
        }else{
            console.log('Saved!')
        }
    });   

}
*/


//독화훈련 데이터 get
app.get('/api/lip_reading',(req,res)=>{

    Lip_reading.find().sort({'id': 1});

    Lip_reading.find(function(err,lip_readings){
        if(err) return res.status(500).json({error:err});
        if(!lip_readings) console.log("aaa");
        console.log(lip_readings);
        res.json(lip_readings);
    })

})

app.get('/api/uiseong',(req,res)=>{

    Uiseong.find({},['id','answer']).sort({id:1}).exec(function(err,uiseongs){
        if(err) return res.status(500).json({error:err});
        if(!uiseongs) console.log("aaa");
        console.log(uiseongs);
        res.json(uiseongs);
    })


})

app.get('/api/uiseong/:uiseong_id',(req,res)=>{

    Uiseong.findOne({id:req.params.uiseong_id}).exec(function(err,uiseong){
        if(err) return res.status(500).json({error:err});
        
        if(!uiseong) console.log("aaa");
        console.log(uiseong);

        res.json(uiseong);
    })

})

