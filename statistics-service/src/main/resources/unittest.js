load("jvm-npm.js");
var QUnit = require("qunit-2.4.0.js");

QUnit.done(function (details) {
    print(JSON.stringify(details, null, 4));
});

QUnit.test("Running at least Java 8", function (assert) {
    assert.ok(java.lang.System.getProperty("java.version") >= "1.8");
});

QUnit.test("Always failing", function (assert) {
    assert.fail();
});

QUnit.load();