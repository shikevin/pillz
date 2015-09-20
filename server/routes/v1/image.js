var express = require('express');
var router = express.Router();
var fs = require('fs');
var spawn = require('child_process').spawn;
var concat = require('concat-stream');

var Busboy = require('busboy');

var handleUpload = function(fieldname, file, filename, transferEncoding, mimeType) {
  console.log('handling file upload');
  upload = fs.createWriteStream('./test2.JPG');
  upload.on('finish', function() {
    var process = spawn('python',["../image-recognition/counting_objects.py"]);
    console.log("error?");

    process.stdout.pipe(concat(function(data) {
          // all your data ready to be used.
      this.res.status(200).send({message: data.toString('utf-8').trim()});
      console.log(data.toString('utf-8'));
    }.bind({res:this.res})));
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
