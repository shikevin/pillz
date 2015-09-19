var express = require('express');
var bodyParser = require('body-parser');
var path = require('path');
var errorhandler = require('errorhandler');
var ws = require("nodejs-websocket")

var apiRoutes = require('./routes/v1_router');

var net = require('net');

var server = net.createServer(function(socket) {
    socket.write('Echo server\r\n');
      socket.pipe(socket);
});

server.listen(1337, '0.0.0.0');

app = exports.app = express();

trackedPills = {};

app.set('port', 5000);
app.use(errorhandler({
  dumpExceptions: true,
  showStack: true
}));
app.set('views', __dirname + '/views');
app.use(express.static(path.join(__dirname, 'public')));

app.engine('.html', require('ejs').__express);
app.set('view engine', 'html');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.use('/v1', apiRoutes);

app.use(function(req, res, next){
  res.render('404.html', { status: 404, url: req.url });
});

// 
var httpServer = app.listen(app.get('port'), function() {
  console.log("Express server listening on port " + app.get('port'));
});

httpServer.on('error', function (err) {
    console.error(err);
});
// 
// // websocket server
// var server = ws.createServer(function (conn) {
//     console.log("New connection")
//     conn.on("text", function (str) {
//         console.log("Received "+str)
//         conn.sendText(str.toUpperCase()+"!!!")
//     })
//     conn.on("close", function (code, reason) {
//         console.log("Connection closed")
//     })
// }).listen(8001)
