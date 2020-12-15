var mongoose=require('mongoose');
var Schema= mongoose.Schema;

var uiseongSchema = new Schema({ //의성,의태어
    id: 'number',
    link: 'string',
    answer: 'string',
    desc: 'string',
    type: 'string',
    example: 'string',
    extension: 'string'
} );

module.exports = mongoose.model('uiseong', uiseongSchema);