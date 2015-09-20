var express = require('express');
// load the modern build
var _ = require('lodash-node');
var router = express.Router();

var COLORS = ['red', 'green', 'blue'];

var handleNewPills = function(req, res) {
  var pills = [];
  pills = req.body.pills;
  pills = req.body.pills;
  console.log("PILLS: " + pills);
  trackedPills = [];
  for (var i = 0; i < pills.length; i++) {
    pill = pills[i];
    pill.color = COLORS[i];
    trackedPills.push(pill);
  }
  res.send({message: trackedPills});
};

var getCurrentPills = function(req, res) {
  res.send({message: trackedPills});
};

router.post('/', handleNewPills);

router.get('/', getCurrentPills);

module.exports = router;
