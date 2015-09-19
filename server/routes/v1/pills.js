var express = require('express');
// load the modern build
var _ = require('lodash-node');
var router = express.Router();

var handleNewPills = function(req, res) {
  var pills = req.body.pills;
  _.forEach(pills, function(n, key) {
    pill = trackedPills[n];
  });
};

router.post('/', function(req, res, next) {
  var longitude = parseFloat(req.query.longitude);
  var latitude = parseFloat(req.query.latitude);
  var listOfFiles = [];
  
  if ((!latitude && latitude!==0) || (!longitude && longitude !== 0 )) {
    return res.status(400).send({ message: 'Need latitude or longitude query' });
  }

  slingObjects.find({
    point: {
      $near: {
        $geometry: { type: "Point", coordinates: [longitude, latitude] },
        $maxDistance: 1000
      }
    }
  },
  {
    sort: {
      _id: -1
    }
  }, function(err, doc) {
    if (err) {
      console.log(err);
      return res.status(400).send({ message: 'Improper latitude and longitude query' });
    } else {
      if (doc === null) {
        return res.status(200).send({ message: 'Nothing here' });
    }
    // store keys with browseRequests
    var fileKeys = [];
    doc.forEach( function(uploadedFile) {
      var file;
      var inCircle = geolib.isPointInCircle(
        { latitude: uploadedFile.point.coordinates[1], longitude: uploadedFile.point.coordinates[0] },
        { latitude: latitude, longitude: longitude },
        uploadedFile.radius);
      if (inCircle) {
        file = {
          uniqueID: uploadedFile.uniqueID,
          timer: uploadedFile.timer,
          radius: uploadedFile.radius,
          longitude: uploadedFile.point.coordinates[0],
          latitude: uploadedFile.point.coordinates[1],
          name: uploadedFile.name,
          type: uploadedFile.type,
        };
        fileKeys.push(uploadedFile.uniqueID);
        if (uploadedFile.password !== "" && uploadedFile.password !== null) {
            file.password = true;
        } else {
          file.password = false;
        }
        listOfFiles.push(file); 
        }
      });
      // keep track of all sent records
      browseRequests.insert({longitude: longitude, latitude: latitude, results: fileKeys});
      res.send({data: listOfFiles});
    }
  });

});

module.exports = router;
