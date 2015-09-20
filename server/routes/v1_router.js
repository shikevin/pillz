var express = require('express');
var router = express.Router();

var pillsRoute = require('./v1/pills');
// var browseRoute = require('./v1/browse'); 
// var uploadRoute = require('./v1/upload');
// var deleteRoute = require('./v1/delete');
// var downloadRoute = require('./v1/download');
// var userRoute = require('./v1/email');
// var existsRoute = require('./v1/exists');
// var notifyRoute = require('./v1/notify');

router.use('/pills', pillsRoute);
// router.use('/detail', browseRoute);
// router.use('/delete', deleteRoute);
// router.use('/upload', uploadRoute);
// router.use('/download', downloadRoute);
// router.use('/email', userRoute);
// router.use('/exists', existsRoute);
// router.use('/notify', notifyRoute);

module.exports = router;
