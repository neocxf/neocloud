(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD
        // define(['jquery', 'underscore'], factory);
        define(['handlebars'], factory);
    } else if (typeof exports === 'object') {
        // Node, CommonJS-like
        // module.exports = factory(require('jquery'), require('lodash'));
        module.exports = factory(require('handlebars'));
    } else {
        // Browser globals (root is window)
        root.util = factory(Handlebars);
    }
}(this, function (Handlebars) {

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

    function echo(data) {
        console.log('echo ...')
        console.log(data)
    }


    return {
        serialize: serialize,
        toJsonObject: toJsonObject,
        render: render,
        echo: echo
    }
}));
