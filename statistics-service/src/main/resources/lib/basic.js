(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD
        // define(['jquery', 'underscore'], factory);
        console.log('..............loading amd ..............................');

        define('util', [], function () {
            return (root.util = factory())
        });

    } else if (typeof exports === 'object') {
        // Node, CommonJS-like
        // module.exports = factory(require('jquery'), require('lodash'));
        console.log('-------------------loading commonjs-------------------------------');
        module.exports = factory();
    } else {
        // Browser globals (root is window)

        console.log('-------------------default loading-------------------------------');

        root.util = factory();
    }
}(this, function () {

    var templates = {};

    /**
     * extract the query to form a query string
     * @param obj
     * @param prefix
     * @returns {string}
     */
    function serialize(obj, prefix) {
        var str = [],
            p;
        for (p in obj) {
            if (obj.hasOwnProperty(p)) {
                var k = prefix ? prefix + "[" + p + "]" : p,
                    v = obj[p];
                str.push((v !== null && typeof v === "object") ?
                    serialize(v, k) :
                    encodeURIComponent(k) + "=" + encodeURIComponent(v));
            }
        }
        return str.join("&");
    }

    function render(template, model, url) {
        var compiledTemplate;
        if (templates[url] === undefined) {
            compiledTemplate = Handlebars.compile(template);
        } else {
            compiledTemplate = templates[url];
        }

        return compiledTemplate(toJsonObject(model))
    }

    // create a real JSON object from the model Map
    function toJsonObject(model) {
        var o = {};
        for (var k in model) {
            // Convert Iterable like List to real JSON array
            if (model[k] instanceof Java.type("java.lang.Iterable")) {
                o[k] = Java.from(model[k]);
            } else {
                o[k] = model[k];
            }
        }

        return o;
    }

    function isValid(source) {
        return (source !== undefined && source !== null);
    }

    function cleanNull(obj) {
        for (var p in obj) {
            if (obj.hasOwnProperty(p) && (obj[p] === null || obj[p] === undefined)) {
                delete obj[p]
            }
        }
    }

    function strip(str, prefix) {
        if (str == null || str === undefined) {
            return '';
        }

        var param = str.split(prefix)[1];

        // return param && param.replace(/./, param[0].toLowerCase());
        return param;
    }


    function echo(data) {
        console.log('echo ...')
        console.log(JSON.stringify(obj))
        console.log(data)
    }


    return {
        serialize: serialize,
        toJsonObject: toJsonObject,
        isValid: isValid,
        cleanNull: cleanNull,
        strip: strip,
        echo: echo
    }
}));

console.log('loading test.js ...')

// var module = load("statistics-service/src/main/resources/lib/test.js");
var module = load("test.js");
console.log(JSON.stringify(module));

var module2 = load("test.js");
console.log(JSON.stringify(module2));