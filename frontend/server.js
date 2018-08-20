//Install express server
const express = require('express');
const path = require('path');

const app = express();

// Serve only the static files form the dist directory
// app.use(express.static('./dist/music-hub-app'));
//
// app.get('/*', function(req,res) {
//
//   res.sendFile(path.join('./dist/music-hub-app/index.html'));
// });

// app.use(express.static(__dirname + '/dist/music-hub-app'));
//
// app.get('/*', function(req,res) {
//
//   res.sendFile(path.join(__dirname+'/dist/music-hub-app/index.html'));
// });

app.use(express.static('./dist/music-hub-app'));
app.get('*', function (req, res) {
  res.sendFile('index.html', { root: __dirname });
});

// Start the app by listening on the default Heroku port
app.listen(process.env.PORT || 4200);
