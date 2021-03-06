var express = require('express');
var bodyParser = require('body-parser');
var path = require('path');
var ws = require("nodejs-websocket");
var spawn = require('child_process').spawn;
var concat = require('concat-stream');

var apiRoutes = require('./routes/v1_router');
app = exports.app = express();

trackedPills = {};
setIntervals = [];
sockets = [];

app.set('port', 5000);
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


var WebSocketServer = require('websocket').server;
var http = require('http');

var server = http.createServer(function(request, response) {
    console.log((new Date()) + ' Received request for ' + request.url);
    response.writeHead(404);
    response.end();
});
server.listen(1337, function() {
    console.log((new Date()) + ' Server is listening on port 1337');
});

wsServer = new WebSocketServer({
    httpServer: server,
    // You should not use autoAcceptConnections for production
    // applications, as it defeats all standard cross-origin protection
    // facilities built into the protocol and the browser.  You should
    // *always* verify the connection's origin and decide whether or not
    // to accept it.
    autoAcceptConnections: false
});

function originIsAllowed(origin) {
  // put logic here to detect whether the specified origin is allowed.
  return true;
}

wsServer.on('request', function(request) {
    if (!originIsAllowed(request.origin)) {
      // Make sure we only accept requests from an allowed origin
      request.reject();
      console.log((new Date()) + ' Connection from origin ' + request.origin + ' rejected.');
      return;
    }

    var connection = request.accept('echo-protocol', request.origin);
    // add socket to list
    sockets.push(connection);
    console.log((new Date()) + ' Connection accepted.');
    connection.on('message', function(message) {
        if (message.type === 'utf8') {
            console.log('Received Message: ' + message.utf8Data);
            connection.sendUTF(message.utf8Data);
        }
        else if (message.type === 'binary') {
            console.log('Received Binary Message of ' + message.binaryData.length + ' bytes');
            connection.sendBytes(message.binaryData);
        }
    });
    connection.on('close', function(reasonCode, description) {
        console.log((new Date()) + ' Peer ' + connection.remoteAddress + ' disconnected.');
        // remove sockets from list
        for (var i = 0; i < sockets.length; i++) {
          if (sockets[i].remoteAddress == connection.remoteAddress) {
            sockets.splice(i, 1);
          }
        }
    });
});

var process = spawn('python',["../image-recognition/counting_objects.py"]);
console.log("error?");

process.stdout.pipe(concat(function(data) {
      // all your data ready to be used.
  console.log(data.toString('utf-8'));
}));
