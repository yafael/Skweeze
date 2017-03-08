var urlStr = window.location.href;
const url = require('url');
var urlObj = url.parse(urlStr, true);
var searchParams = urlObj.searchParams;
var file = searchParams.get('resume');
var p = document.createElement('p');
p.textContent = file;
document.body.appendChild(p);

//var child = require('child_process').spawn('java bin/Skweeze', file);
//child.stdout.on('data', function(data) {
//    console.log(data.toString());
//});
//child.stderr.on("data", function (data) {
//    console.log(data.toString());
//});