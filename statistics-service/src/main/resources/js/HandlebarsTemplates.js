function generateTemplate(dataObject) {
    var source = "Hello {{user}}, \n \nYour Order Number is {{orderId}}. You have " +
        "{{items.length}} items :\n" +
        "{{#items}}{{name}}, Quantity : {{quantity}} \n {{/items}}";
    var template = Handlebars.compile(source);
    var data = JSON.parse(dataObject);
    var result = template(data);
    console.log(result);

    console.log(util.serialize(dataObject));
}
