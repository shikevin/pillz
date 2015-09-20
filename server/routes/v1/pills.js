var express = require('express');
// load the modern build
var _ = require('lodash-node');
var router = express.Router();

var COLORS = ['red', 'green', 'blue'];

var handleNewPills = function(req, res) {
  // give frequency in seconds
  var pills = [];
  pills = req.body.pills;
  pills = req.body.pills;
  console.log("PILLS: " + pills);
  trackedPills = [];
  removeCurrentScheduled();
  for (var i = 0; i < pills.length; i++) {
    pill = pills[i];
    pill.color = COLORS[i];
    addSchedule(pill);
    trackedPills.push(pill);
  }
  res.send({message: trackedPills});
};

var addSchedule = function(pill) {
  var cycle = parseFloat(pill.frequency);
  if (!cycle && cycle !==0) {
    setInterval(function(){
      console.log("scheduling");
      _.forEach(sockets, function(socket) {
        socket.write("do something...");
      });
    }, cycle*1000);
  }
};

var removeCurrentScheduled = function() {
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
