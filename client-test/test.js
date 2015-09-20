var net = require('net');

var client = new net.Socket();
client.connect(1337, '52.89.88.229', function() {
  console.log('Connected');
  client.write('Hello, server! Love, Client.');
});

client.on('data', function(data) {
  console.log('Received: ' + data);
  console.log(data);
});

client.on('close', function() {
  console.log('Connection closed');
});
