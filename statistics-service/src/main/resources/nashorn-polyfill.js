var global = this;
var window = this;
var process = {env: {}};

var console = {};
console.debug = print;
console.log = print;
console.warn = print;
console.error = print;

if (typeof Object.assign != 'function') {
    // Must be writable: true, enumerable: false, configurable: true
    Object.defineProperty(Object, "assign", {
        value: function assign(target, varArgs) { // .length of function is 2
            'use strict';
            if (target == null) { // TypeError if undefined or null
                throw new TypeError('Cannot convert undefined or null to object');
            }

            var to = Object(target);

            for (var index = 1; index < arguments.length; index++) {
                var nextSource = arguments[index];

                if (nextSource != null) { // Skip over if undefined or null
                    for (var nextKey in nextSource) {
                        // Avoid bugs when hasOwnProperty is shadowed
                        if (Object.prototype.hasOwnProperty.call(nextSource, nextKey)) {
                            to[nextKey] = nextSource[nextKey];
                        }
                    }
                }
            }
            return to;
        },
        writable: true,
        configurable: true
    });
}

function readFully(url) {
    var result = "";
    var imports = new JavaImporter(java.net, java.lang, java.io);

    with (imports) {

        var urlObj = null;

        try {
            urlObj = new URL(url);
        } catch (e) {
            // If the URL cannot be built, assume it is a file path.
            urlObj = new URL(new File(url).toURI().toURL());
        }

        var reader = new BufferedReader(new InputStreamReader(urlObj.openStream()));

        var line = reader.readLine();
        while (line != null) {
            result += line + "\n";
            line = reader.readLine();
        }

        reader.close();
    }

    return result;
}