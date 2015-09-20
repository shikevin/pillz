var express = require('express');
// load the modern build
var _ = require('lodash-node');
var router = express.Router();

var COLORS = ['red', 'green', 'blue'];

var handleNewPills = function(req, res) {
  // give frequency in seconds
  var pills = [];
  pills = req.body.pills;
  console.log(pills);
  trackedPills = [];
  removeCurrentScheduled();
  for (var i = 0; i < pills.length; i++) {
    pill = pills[i];
    pill.color = COLORS[i];
    addSchedule(pill, i);
    trackedPills.push(pill);
  }
  res.send({message: trackedPills});
};

var addSchedule = function(pill, n) {
  var cycle = parseFloat(pill.frequency);
  if (!cycle) {
    return;
  }
  console.log("N: " + n);
  var interval = setInterval(function(){
    _.forEach(sockets, function(socket) {
      // first light and some other light
      socket.sendUTF((n+1).toString());
    });
  }, cycle*1000);
  setIntervals.push(interval);
};

var removeCurrentScheduled = function() {
  _.forEach(sockets, function(socket) {
    socket.sendUTF("picture taken");
  });
  _.forEach(setIntervals, function(n) {
    clearInterval(n);
  });
};

var getCurrentPills = function(req, res) {
  res.send({message: trackedPills});
};

router.post('/', handleNewPills);

router.get('/', getCurrentPills);

module.exports = router;
