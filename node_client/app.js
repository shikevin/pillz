var WebSocketClient = require('websocket').client;
var SerialPort = require("serialport").SerialPort;

// make this the right serial port
var serialPort = new SerialPort("/dev/tty-usbserial1", {
    baudrate: 57600
});

var client = new WebSocketClient();

client.on('connectFailed', function(error) {
      console.log('Connect Error: ' + error.toString());
});

client.on('connect', function(connection) {
    console.log('WebSocket Client Connected');
    connection.on('error', function(error) {
        console.log("Connection Error: " + error.toString());
    });
    connection.on('close', function() {
        console.log('echo-protocol Connection Closed');
    });
    connection.on('message', function(message) {
        if (message.type === 'utf8') {
            console.log("Received: '" + message.utf8Data + "'");
        }
    });

    function sendNumber() {
        if (connection.connected) {
            var number = Math.round(Math.random() * 0xFFFFFF);
            connection.sendUTF(number.toString());
            console.log('sending number');
            setTimeout(sendNumber, 1000);
        }
    }
    sendNumber();
});

client.connect('ws://52.89.88.229:1337/', 'echo-protocol');
