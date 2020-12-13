var mongoose=require('mongoose');
var Schema= mongoose.Schema;

var lip_readingSchema = new Schema({
start: 'string',

} );

module.exports = mongoose.model('lip_reading', lip_readingSchema);