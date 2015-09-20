var express = require('express');
var router = express.Router();
var fs = require('fs');

var Busboy = require('busboy');

var handleUpload = function(fieldname, file, filename, transferEncoding, mimeType) {
  console.log('handling file upload');
  upload = fs.createWriteStream('./test2.JPG');
  upload.on('finish', function() {
    this.res.status(200).send({message: "done"});
  }.bind({res: this.res}));
  file.pipe(upload);
};
var savePictureUpload = function(req, res) {
  console.log('got here');
  var busboy = new Busboy({
    headers: req.headers,
    limits: {
      files: 1,
    }
  });

  // stop accepting fields beyond what we need
  busboy.on('fieldsLimit', function() {
    console.log('reached limit');
  });

  busboy.on('file', handleUpload.bind({
    res: res
  }));

  busboy.on('finish', function() {
  });

  req.pipe(busboy);
};
  
router.post('/', savePictureUpload);

module.exports = router;
