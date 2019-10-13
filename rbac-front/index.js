const express = require('express');
const bodyParser = require('body-parser');
const env = process.env.NODE_ENV || 'development'
// const history = require('connect-history-api-fallback');

const app = express();
const api = require('./api/user');

app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());

app.use(/(.*htm)[l]?\/$/, function (req, res, next) {
  res.redirect(req.originalUrl.slice(0, -1));
});

app.get("/h", function(req, res, next){
  res.write("Hello");
  next(); //remove this and see what happens
});

app.get("/h", function(req, res, next){
  res.write(" World !!!");
  res.end();
});

// app.use(history({
//   verbose: true
// }))

app.use('/', express.static('public'));

app.use('/', api);

const server = app.listen(4000, (req, res) => {
  console.log(`Express started in ${app.get('env')} mode on http://localhost:4000`)
});
