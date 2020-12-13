var mongoose=require('mongoose');
var Schema= mongoose.Schema;

var lip_readingSchema = new Schema({
    id: 'number',
    link: 'string',
    start: 'string',
    end: 'string',
    answer: 'string',
    type: 'string' // sentence or word
} );

module.exports = mongoose.model('lip_reading', lip_readingSchema);