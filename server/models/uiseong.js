var mongoose=require('mongoose');
var Schema= mongoose.Schema;

var uiseongSchema = new Schema({ //의성,의태어
    id: 'number',
    link: 'string',
    //img: { data: Buffer, contentType: String },
    answer: 'string',
    desc: 'string',
    //type: 'string'
} );

module.exports = mongoose.model('uiseong', uiseongSchema);