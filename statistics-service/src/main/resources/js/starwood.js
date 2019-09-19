var util = require('lib/util')

var obj = {};
var env = {
    request: null,
    hotelMapper: null,
    context: null,
    paramPairs: null
};

obj.baseUrl = function (context) {
    var channelCode = context.channelCode;
    var brand = context.brand;

    var url = null;
    switch (channelCode) {
        case 'dh-skyscanner':
            url = "http://www.starwoodhotels.com/preferredguest/rates/room.html";
            break;
        default:
            url = "http://www.starwoodhotels.com/" + brand.toLowerCase() + "/rates/room.html";
            break;
    }

    return url;
}

obj.get_site = function (context) {
    var channelCode = context.channelCode;
    var site = null;
    switch (true) {
        case channelCode.indexOf('trivago') > -1:
            site = 'trivago';
            break;
        case channelCode.indexOf('skyscanner') > -1:
            site = 'skyscanner';
            break;
        case channelCode.indexOf('qunar') > -1:
            site = 'qunar';
            break;
        case channelCode.indexOf('hipmunk') > -1:
            site = 'hipmunk';
            break;
        default:
            break;
    }

    return site;
}

obj.get_SWAQ = function (context) {
    var channelCode = context.channelCode;
    var additionalIdentifier = context.additionalIdentifier;
    var SWAQ = null;
    switch (true) {
        case channelCode.indexOf('trivago') > -1:
            SWAQ = '3TGA';
            break;
        case channelCode.indexOf('skyscanner') > -1:
            switch (additionalIdentifier) {
                case 'AP':
                    SWAQ = '3ZM7';
                    break;
                case 'LAD':
                    SWAQ = '3ZK4';
                    break;
                case 'EAME':
                    SWAQ = '3ZJ1';
                    break;
                case 'NAD':
                    SWAQ = '3ZH8';
                    break;
                default:
                    SWAQ = '3TGA';
                    break;
            }
            break;
        case channelCode.indexOf('qunar') > -1:
            SWAQ = '3TM1';
            break;
        case channelCode.indexOf('hipmunk') > -1:
            SWAQ = '3TN2';
            break;
        default:
            break;
    }

    return SWAQ;
}

obj.get_iataNumber = function (context) {
    var channelCode = context.channelCode;
    var additionalIdentifier = context.additionalIdentifier;
    var iataNumber = null;

    switch (channelCode) {
        case 'dh-skyscanner':
            switch (additionalIdentifier) {
                case 'AP':
                    iataNumber = '9664515';
                    break;
                case 'EAME':
                    iataNumber = '96081333';
                    break;
                case 'LAD':
                    iataNumber = '96081333';
                    break;
                case 'NAD':
                    iataNumber = '96081333';
                    break;
                default:
                    iataNumber = '96081333';
                    break;
            }
            break;
        default:
            break;
    }

    return iataNumber;
}

obj.get_skyScannerClickId = function (context) {
    var channelCode = context.channelCode;
    var skyscannerClickID = null;
    switch (channelCode) {
        case 'dh-skyscanner':
            skyscannerClickID = context.skyscannerClickID;
            break;
        default:
            break;
    }
    return skyscannerClickID;
}

obj.get_src = function (context) {
    var channelCode = context.channelCode;
    var src = null;
    switch (channelCode) {
        case 'dh-skyscanner':
            src = 'skyscanner-dispy-derbysoft-201306.';
            break;
        default:
            break;
    }
    return src;
}

obj.get_currencyCode = function (context) {
    var channelCode = context.channelCode;
    var currencyCode = null;
    switch (channelCode) {
        case 'qunar':
            currencyCode = 'CNY';
            break;
        default:
            if (context.userCurrency == null) {
                currencyCode = context.currency;
            }
            else {
                currencyCode = context.userCurrency;
            }
            break;
    }

    return currencyCode;
}

obj.get_language = function (context) {
    var channelCode = context.channelCode;
    var _language = context.language;
    var languageRtn = null;
    switch (channelCode) {
        case 'qunar':
            languageRtn = 'zh_CN';
            break;
        default:
            if (_language == null) {
                break;
            }
            else {
                var language = _language.toLowerCase();
                switch (true) {
                    case language.indexOf('zh') > -1:
                        languageRtn = 'zh_CN';
                        break;
                    case language.indexOf('en') > -1:
                        languageRtn = 'en_US';
                        break;
                    case language.indexOf('de') > -1:
                        languageRtn = 'de_DE';
                        break;
                    case language.indexOf('fr') > -1:
                        languageRtn = 'fr_FR';
                        break;
                    case language.indexOf('it') > -1:
                        languageRtn = 'it_IT';
                        break;
                    case language.indexOf('jp') > -1:
                        languageRtn = 'ja_JP';
                        break;
                    case language.indexOf('es') > -1:
                        languageRtn = 'es_ES';
                        break;
                    case language.indexOf('pt') > -1:
                        languageRtn = 'pt_BR';
                        break;
                    case language.indexOf('ru') > -1:
                        languageRtn = 'ru_RU';
                        break;
                    default:
                        break;
                }
                break;
            }

    }

    return languageRtn;
}

obj.get_localeCode = function (context) {
    var channelCode = context.channelCode;
    var _language = context.language;
    var localeCode = null;
    switch (channelCode) {
        case 'qunar':
            localeCode = 'zh_CN';
            break;
        default:
            if (_language == null) {
                break;
            }
            else {
                var language = _language.toLowerCase();
                switch (true) {
                    case language.indexOf('zh') > -1:
                        localeCode = 'zh_CN';
                        break;
                    case language.indexOf('en') > -1:
                        localeCode = 'en_US';
                        break;
                    case language.indexOf('de') > -1:
                        localeCode = 'de_DE';
                        break;
                    case language.indexOf('fr') > -1:
                        localeCode = 'fr_FR';
                        break;
                    case language.indexOf('it') > -1:
                        localeCode = 'it_IT';
                        break;
                    case language.indexOf('jp') > -1:
                        localeCode = 'ja_JP';
                        break;
                    case language.indexOf('es') > -1:
                        localeCode = 'es_ES';
                        break;
                    case language.indexOf('pt') > -1:
                        localeCode = 'pt_BR';
                        break;
                    case language.indexOf('ru') > -1:
                        localeCode = 'ru_RU';
                        break;
                    default:
                        break;
                }
                break;
            }

    }

    return localeCode;
}

obj.return = function (context, paramPairs) {
    var baseUrl = this.baseUrl(context);
    util.cleanNull(paramPairs);
    return baseUrl + '?' + util.serialize(paramPairs);
}


env.invokeAliasByConfig = function invokeAliasByConfig(config) {
    var paramPairs = {};

    for (var elem in config) {
        paramPairs[elem] = this.context[config[elem]]
    }

    this.paramPairs = Object.assign({}, this.paramPairs, paramPairs);

    return paramPairs;
}

env.invokeFuncByConfig = function invokeFuncByConfig(config, obj) {
    var paramPairs = {};

    for (var elem in config) {
        var fn = obj[config[elem]];
        if (typeof fn === 'function') {
            var value = fn.call(obj, this.context)
            paramPairs[elem] = value;
        }
    }

    this.paramPairs = Object.assign({}, this.paramPairs, paramPairs);

    return paramPairs;
}


env.extractRequest = function extractRequest(javaHttpRequest) {
    var context = {};
    var requestMap = javaHttpRequest.getParameterMap();
    var StringType = Java.type('java.lang.String');
    for (var elem in requestMap) {
        context[elem] = StringType.join(',', requestMap[elem]);
    }

    // channelCode processing
    if (util.isValid(context['identifier'])) {
        context['channelCode'] = context['identifier'];
        delete context['identifier'];
    }

    this.request = javaHttpRequest;
    this.context = context;
    return context;
}

env.extractHotelInfo = function extractHotelInfo() {
    var hotelCode = this.context['hotelCode'];
    var hotelInfo = hotelCommonMapper.findHotelInfo(hotelCode);
    this.context = Object.assign({}, this.context, {
        brand: hotelInfo.brand,
        userCurrency: hotelInfo.currencyCode,
        propertyId: hotelInfo.providerHotelCode
    });
    return hotelInfo;
}

env.extractMappingHotelUrl = function extractMappingHotelUrl() {
    var hotelCode = this.context['hotelCode'];
    var mappingHotelURL = hotelCommonMapper.findMappingHotelURL(hotelCode);
    this.context['additionalIdentifier'] = mappingHotelURL.additionalIdentifier;
    return mappingHotelURL;
}

function starwood_main() {

    env.extractRequest(request);

    env.extractHotelInfo();

    env.extractMappingHotelUrl();

    // 天气很热，笑话很冷，你很美

    var aliasConfig = {
        arrivalDate: 'checkInDate',
        departureDate: 'checkOutDate',
        lpqRatePlanName: 'lpqRatePlanName',
        taxAmount: 'taxAmount',
        propertyId: 'propertyId',
        numberOfRooms: 'rooms',
        numberOfAdults: 'guests'
    };

    var optionalConfig = {
        "site": "get_site",
        "SWAQ": "get_SWAQ",
        "iataNumber": "get_iataNumber",
        "skyscannerClickID": "get_skyScannerClickId",
        "src": "get_src",
        "currencyCode": "get_currencyCode",
        "language": "get_language",
        "localeCode": "get_localeCode",
    };

    env.invokeAliasByConfig(aliasConfig);
    env.invokeFuncByConfig(optionalConfig, obj);

    console.log(JSON.stringify(env.paramPairs));

    require('js/test.js');

    console.log('after load');

    console.log(JSON.stringify(obj));

    return obj.return(env.context, env.paramPairs);
}