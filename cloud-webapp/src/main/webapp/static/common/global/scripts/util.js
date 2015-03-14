var commonUtil = {
    /**
     * @description 将输入的数字进行处理，若小于10则返回在前面加“0”的字符串，用于时间函数处理时补足位数。
     * @param {int} i  输入的数字
     * @return {String} 处理后的字符串
     * @example commonUtil.getStr(3) 返回：03
     */
    getStr: function (i) {
        return i < 10 ? "0" + i : i + "";
    }
}
String.prototype.leftTrim = function () {
    return this.replace(/^\s\s*/, '');
};
/**
 * @description String方法扩展，去除字符串的右空格
 * @return {String}去除右空格的字符串
 * @example  "  aa  ".rightTrim()  返回："  aa"
 */
String.prototype.rightTrim = function () {
    return this.replace(/\s\s*$/, '');
};
/**
 * @description String方法扩展，去除字符串的两端空格
 * @return {String}去除两端空格的字符串
 * @example  "  aa  ".trim()  返回："aa"
 */
String.prototype.trim = function () {
    return this.leftTrim().rightTrim();
};
/**
 * @description String方法扩展，判断经过trim的字符串是否为非空，若不为空则returnObj.bool为true
 * @return {bool}
 * @example  "  ".isNotEmpty()  返回："false"  "null".isNotEmpty()  返回："false"
 */
String.prototype.isEmpty = function () {
    var tmpStr = this.trim();
    return tmpStr === undefined || tmpStr === null || tmpStr.length === 0 || tmpStr === "" || tmpStr === "null";
};
/**
 * @description String方法扩展，判断经过trim的字符串是否为非空，若不为空则returnObj.bool为true
 * @return {bool}
 * @example  "  ".isNotEmpty()  返回："false"  "null".isNotEmpty()  返回："false"
 */
String.prototype.isNotEmpty = function () {
    return !this.isEmpty();
};
/**
 * @description String方法扩展，用于将人民币小写改完大写，最大值：99999999999.99
 * @return {String} 人民币大写
 * @example  "102".chnMoney() 返回：壹佰零贰元整
 */
String.prototype.chnMoney = function () {
    var MAXIMUM_NUMBER = 99999999999.99;
    // Predefine the radix characters and currency symbols for output:
    var CN_ZERO = "零";
    var CN_ONE = "壹";
    var CN_TWO = "贰";
    var CN_THREE = "叁";
    var CN_FOUR = "肆";
    var CN_FIVE = "伍";
    var CN_SIX = "陆";
    var CN_SEVEN = "柒";
    var CN_EIGHT = "捌";
    var CN_NINE = "玖";
    var CN_TEN = "拾";
    var CN_HUNDRED = "佰";
    var CN_THOUSAND = "仟";
    var CN_TEN_THOUSAND = "万";
    var CN_HUNDRED_MILLION = "亿";
//        var CN_SYMBOL = "人民币";
    var CN_SYMBOL = "";
    var CN_DOLLAR = "元";
    var CN_TEN_CENT = "角";
    var CN_CENT = "分";
    var CN_INTEGER = "整";

// Variables:
    var integral; // Represent integral part of digit number.
    var decimal; // Represent decimal part of digit number.
    var outputCharacters; // The output result.
    var parts;
    var digits, radices, bigRadices, decimals;
    var zeroCount;
    var i, p, d;
    var quotient, modulus;

// Validate input string:
    var currencyDigits = this.toString();
    if (currencyDigits == "") {
//            alert("输入为空！");
        return "";
    }
    if (currencyDigits.match(/[^,.\d]/) != null) {
//            alert("输入了不合法字符！");
        return "";
    }
    if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
//            alert("不合法数字！");
        return "";
    }

// Normalize the format of input digits:
    currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters.
    currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning.
    // Assert the number is not greater than the maximum number.
    if (Number(currencyDigits) > MAXIMUM_NUMBER) {
//            alert("超过最大合法数值！");
        return "";
    }

// Process the coversion from currency digits to characters:
    // Separate integral and decimal parts before processing coversion:
    parts = currencyDigits.split(".");
    if (parts.length > 1) {
        integral = parts[0];
        decimal = parts[1];
        // Cut down redundant decimal digits that are after the second.
        decimal = decimal.substr(0, 2);
    }
    else {
        integral = parts[0];
        decimal = "";
    }
    // Prepare the characters corresponding to the digits:
    digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
    radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
    bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
    decimals = new Array(CN_TEN_CENT, CN_CENT);
    // Start processing:
    outputCharacters = "";
    // Process integral part if it is larger than 0:
    if (Number(integral) > 0) {
        zeroCount = 0;
        for (i = 0; i < integral.length; i++) {
            p = integral.length - i - 1;
            d = integral.substr(i, 1);
            quotient = p / 4;
            modulus = p % 4;
            if (d == "0") {
                zeroCount++;
            }
            else {
                if (zeroCount > 0) {
                    outputCharacters += digits[0];
                }
                zeroCount = 0;
                outputCharacters += digits[Number(d)] + radices[modulus];
            }
            if (modulus == 0 && zeroCount < 4) {
                outputCharacters += bigRadices[quotient];
            }
        }
        outputCharacters += CN_DOLLAR;
    }
    // Process decimal part if there is:
    if (decimal != "") {
        for (i = 0; i < decimal.length; i++) {
            d = decimal.substr(i, 1);
            if (d != "0") {
                outputCharacters += digits[Number(d)] + decimals[i];
            }
        }
    }
    // Confirm and return the final output string:
    if (outputCharacters == "") {
        outputCharacters = CN_ZERO + CN_DOLLAR;
    }
    if (decimal == "") {
        outputCharacters += CN_INTEGER;
    }
    outputCharacters = CN_SYMBOL + outputCharacters;
    return outputCharacters;
};
/**
 * @description String方法扩展，用于将字符串进行md5加密
 * @return {String} 进行md5加密后的字符串
 * @example  "102".md5()
 */
String.prototype.md5 = function () {
    var str = this.trim();
    var hex_chr = '0123456789ABCDEF';

    function rhex(num) {
        str = "";
        for (var j = 0; j <= 3; j++)
            str += hex_chr.charAt((num >> (j * 8 + 4)) & 0x0F) +
                hex_chr.charAt((num >> (j * 8)) & 0x0F);
        return str;
    }

    /*
     * Convert a string to a sequence of 16-word blocks, stored as an array.
     * Append padding bits and the length, as described in the MD5 standard.
     */
    function str2blks_MD5(str) {
        var nblk = ((str.length + 8) >> 6) + 1;
        var blks = new Array(nblk * 16);
        for (var i = 0; i < nblk * 16; i++) blks[i] = 0;
        for (i = 0; i < str.length; i++)
            blks[i >> 2] |= str.charCodeAt(i) << ((i % 4) * 8);
        blks[i >> 2] |= 0x80 << ((i % 4) * 8);
        blks[nblk * 16 - 2] = str.length * 8;
        return blks;
    }

    /*
     * Add integers, wrapping at 2^32. This uses 16-bit operations internally
     * to work around bugs in some JS interpreters.
     */
    function add(x, y) {
        var lsw = (x & 0xFFFF) + (y & 0xFFFF);
        var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
        return (msw << 16) | (lsw & 0xFFFF);
    }

    /*
     * Bitwise rotate a 32-bit number to the left
     */
    function rol(num, cnt) {
        return (num << cnt) | (num >>> (32 - cnt));
    }

    /*
     * These functions implement the basic operation for each round of the
     * algorithm.
     */
    function cmn(q, a, b, x, s, t) {
        return add(rol(add(add(a, q), add(x, t)), s), b);
    }

    function ff(a, b, c, d, x, s, t) {
        return cmn((b & c) | ((~b) & d), a, b, x, s, t);
    }

    function gg(a, b, c, d, x, s, t) {
        return cmn((b & d) | (c & (~d)), a, b, x, s, t);
    }

    function hh(a, b, c, d, x, s, t) {
        return cmn(b ^ c ^ d, a, b, x, s, t);
    }

    function ii(a, b, c, d, x, s, t) {
        return cmn(c ^ (b | (~d)), a, b, x, s, t);
    }

    var x = str2blks_MD5(str);
    var a = 1732584193;
    var b = -271733879;
    var c = -1732584194;
    var d = 271733878;

    for (var i = 0; i < x.length; i += 16) {
        var olda = a;
        var oldb = b;
        var oldc = c;
        var oldd = d;

        a = ff(a, b, c, d, x[i + 0], 7, -680876936);
        d = ff(d, a, b, c, x[i + 1], 12, -389564586);
        c = ff(c, d, a, b, x[i + 2], 17, 606105819);
        b = ff(b, c, d, a, x[i + 3], 22, -1044525330);
        a = ff(a, b, c, d, x[i + 4], 7, -176418897);
        d = ff(d, a, b, c, x[i + 5], 12, 1200080426);
        c = ff(c, d, a, b, x[i + 6], 17, -1473231341);
        b = ff(b, c, d, a, x[i + 7], 22, -45705983);
        a = ff(a, b, c, d, x[i + 8], 7, 1770035416);
        d = ff(d, a, b, c, x[i + 9], 12, -1958414417);
        c = ff(c, d, a, b, x[i + 10], 17, -42063);
        b = ff(b, c, d, a, x[i + 11], 22, -1990404162);
        a = ff(a, b, c, d, x[i + 12], 7, 1804603682);
        d = ff(d, a, b, c, x[i + 13], 12, -40341101);
        c = ff(c, d, a, b, x[i + 14], 17, -1502002290);
        b = ff(b, c, d, a, x[i + 15], 22, 1236535329);

        a = gg(a, b, c, d, x[i + 1], 5, -165796510);
        d = gg(d, a, b, c, x[i + 6], 9, -1069501632);
        c = gg(c, d, a, b, x[i + 11], 14, 643717713);
        b = gg(b, c, d, a, x[i + 0], 20, -373897302);
        a = gg(a, b, c, d, x[i + 5], 5, -701558691);
        d = gg(d, a, b, c, x[i + 10], 9, 38016083);
        c = gg(c, d, a, b, x[i + 15], 14, -660478335);
        b = gg(b, c, d, a, x[i + 4], 20, -405537848);
        a = gg(a, b, c, d, x[i + 9], 5, 568446438);
        d = gg(d, a, b, c, x[i + 14], 9, -1019803690);
        c = gg(c, d, a, b, x[i + 3], 14, -187363961);
        b = gg(b, c, d, a, x[i + 8], 20, 1163531501);
        a = gg(a, b, c, d, x[i + 13], 5, -1444681467);
        d = gg(d, a, b, c, x[i + 2], 9, -51403784);
        c = gg(c, d, a, b, x[i + 7], 14, 1735328473);
        b = gg(b, c, d, a, x[i + 12], 20, -1926607734);

        a = hh(a, b, c, d, x[i + 5], 4, -378558);
        d = hh(d, a, b, c, x[i + 8], 11, -2022574463);
        c = hh(c, d, a, b, x[i + 11], 16, 1839030562);
        b = hh(b, c, d, a, x[i + 14], 23, -35309556);
        a = hh(a, b, c, d, x[i + 1], 4, -1530992060);
        d = hh(d, a, b, c, x[i + 4], 11, 1272893353);
        c = hh(c, d, a, b, x[i + 7], 16, -155497632);
        b = hh(b, c, d, a, x[i + 10], 23, -1094730640);
        a = hh(a, b, c, d, x[i + 13], 4, 681279174);
        d = hh(d, a, b, c, x[i + 0], 11, -358537222);
        c = hh(c, d, a, b, x[i + 3], 16, -722521979);
        b = hh(b, c, d, a, x[i + 6], 23, 76029189);
        a = hh(a, b, c, d, x[i + 9], 4, -640364487);
        d = hh(d, a, b, c, x[i + 12], 11, -421815835);
        c = hh(c, d, a, b, x[i + 15], 16, 530742520);
        b = hh(b, c, d, a, x[i + 2], 23, -995338651);

        a = ii(a, b, c, d, x[i + 0], 6, -198630844);
        d = ii(d, a, b, c, x[i + 7], 10, 1126891415);
        c = ii(c, d, a, b, x[i + 14], 15, -1416354905);
        b = ii(b, c, d, a, x[i + 5], 21, -57434055);
        a = ii(a, b, c, d, x[i + 12], 6, 1700485571);
        d = ii(d, a, b, c, x[i + 3], 10, -1894986606);
        c = ii(c, d, a, b, x[i + 10], 15, -1051523);
        b = ii(b, c, d, a, x[i + 1], 21, -2054922799);
        a = ii(a, b, c, d, x[i + 8], 6, 1873313359);
        d = ii(d, a, b, c, x[i + 15], 10, -30611744);
        c = ii(c, d, a, b, x[i + 6], 15, -1560198380);
        b = ii(b, c, d, a, x[i + 13], 21, 1309151649);
        a = ii(a, b, c, d, x[i + 4], 6, -145523070);
        d = ii(d, a, b, c, x[i + 11], 10, -1120210379);
        c = ii(c, d, a, b, x[i + 2], 15, 718787259);
        b = ii(b, c, d, a, x[i + 9], 21, -343485551);

        a = add(a, olda);
        b = add(b, oldb);
        c = add(c, oldc);
        d = add(d, oldd);
    }
    return rhex(a) + rhex(b) + rhex(c) + rhex(d);
};
/**
 * @description String方法扩展，用于将trim后的时间字符串转换为date对象可接受格式为：2010-15-21，2010-15-21 12:45, 2010-15-21 12:45:40
 * @return {Date} 转换后的date对象
 * @example  "2010-15-21".dateFormat()
 */
String.prototype.dateFormat = function () {
    if (this.isEmpty()) {
        return "";
    }
    var trimValue = this.trim();
    var dateArr;
    var timeArr;
    if (trimValue.length === 7) { //month 2010-12
        dateArr = trimValue.split("-");
        return new Date(parseInt(dateArr[0], 10), parseInt(dateArr[1], 10) - 1)
    } else if (trimValue.length === 10) { //date 2010-15-21
        dateArr = trimValue.split("-");
        return new Date(parseInt(dateArr[0], 10), parseInt(dateArr[1], 10) - 1, parseInt(dateArr[2], 10))
    } else if (trimValue.length === 16) {//date minute 2010-15-21 12:45
        dateArr = trimValue.split(" ")[0].split("-");
        timeArr = trimValue.split(" ")[1].split(":");
        return new Date(parseInt(dateArr[0], 10), parseInt(dateArr[1], 10) - 1, parseInt(dateArr[2], 10), parseInt(timeArr[0], 10), parseInt(timeArr[1], 10));
    } else if (trimValue.length === 19) {//datetime  2010-15-21 12:45:40
        dateArr = trimValue.split(" ")[0].split("-");
        timeArr = trimValue.split(" ")[1].split(":");
        return new Date(parseInt(dateArr[0], 10), parseInt(dateArr[1], 10) - 1, parseInt(dateArr[2], 10), parseInt(timeArr[0], 10), parseInt(timeArr[1], 10), parseInt(timeArr[2], 10));
    }
    return "";
};
/**
 * @description String方法扩展，判断身份证号码格式函数(公民身份号码是特征组合码，排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码)
 * @return {bool}
 * @example  "123456789012345678".isChinaIDCard()  返回为false
 */
String.prototype.isChinaIDCard = function () {
    var StrNo = this.trim();

    function isNumber(oNum) {
        if (!oNum) return false;
        var strP = /^\d+(\.\d+)?$/;
        if (!strP.test(oNum)) return false;
        try {
            if (parseFloat(oNum) != oNum) return false;
        }
        catch (ex) {
            return false;
        }
        return true;
    }

    function isValidDate(iY, iM, iD) {
        return !(iY > 2200 || iY < 1900 || !isNumber(iY) || iM > 12 || iM <= 0 || !isNumber(iM) || iD > 31 || iD <= 0 || !isNumber(iD))
    }

    if (StrNo.length == 15) {
        if (!isValidDate("19" + StrNo.substr(6, 2), StrNo.substr(8, 2), StrNo.substr(10, 2))) {
            return false;
        }
    } else if (StrNo.length == 18) {
        if (!isValidDate(StrNo.substr(6, 4), StrNo.substr(10, 2), StrNo.substr(12, 2))) {
            return false;
        }
    } else {
        return false;
    }
    if (StrNo.length == 18) {
        var a, b, c;
        if (!isNumber(StrNo.substr(0, 17))) {
            return false;
        }
        a = parseInt(StrNo.substr(0, 1)) * 7 + parseInt(StrNo.substr(1, 1)) * 9 + parseInt(StrNo.substr(2, 1)) * 10;
        a = a + parseInt(StrNo.substr(3, 1)) * 5 + parseInt(StrNo.substr(4, 1)) * 8 + parseInt(StrNo.substr(5, 1)) * 4;
        a = a + parseInt(StrNo.substr(6, 1)) * 2 + parseInt(StrNo.substr(7, 1)) + parseInt(StrNo.substr(8, 1)) * 6;
        a = a + parseInt(StrNo.substr(9, 1)) * 3 + parseInt(StrNo.substr(10, 1)) * 7 + parseInt(StrNo.substr(11, 1)) * 9;
        a = a + parseInt(StrNo.substr(12, 1)) * 10 + parseInt(StrNo.substr(13, 1)) * 5 + parseInt(StrNo.substr(14, 1)) * 8;
        a = a + parseInt(StrNo.substr(15, 1)) * 4 + parseInt(StrNo.substr(16, 1)) * 2;
        b = a % 11;
        if (b == 2) {   //最后一位为校验位
            c = StrNo.substr(17, 1).toUpperCase();   //转为大写X
        } else {
            c = parseInt(StrNo.substr(17, 1));
        }
        switch (b) {
            case 0:
                if (c != 1) {
                    return false;
                }
                break;
            case 1:
                if (c != 0) {
                    return false;
                }
                break;
            case 2:
                if (c != "X") {
                    return false;
                }
                break;
            case 3:
                if (c != 9) {
                    return false;
                }
                break;
            case 4:
                if (c != 8) {
                    return false;
                }
                break;
            case 5:
                if (c != 7) {
                    return false;
                }
                break;
            case 6:
                if (c != 6) {
                    return false;
                }
                break;
            case 7:
                if (c != 5) {
                    return false;
                }
                break;
            case 8:
                if (c != 4) {
                    return false;
                }
                break;
            case 9:
                if (c != 3) {
                    return false;
                }
                break;
            case 10:
                if (c != 2) {
                    return false;
                }
        }
    } else {//15位身份证号
        if (!isNumber(StrNo)) {
            return false;
        }
    }
    return true;
};
/**
 * @description String方法扩展，转换为带有%的16进制字符
 * @return {String} result 带有%的16进制字符
 * @example  "1".toHex()  返回 "%31"
 */
String.prototype.toHex = function () {
    var a, d;
    var hexStr = '';
    for (var i = 0; i < this.length; i++) {
        d = this.charCodeAt(i);
        a = d % 16;
        hexStr += '%' + "0123456789ABCDEF".charAt((d - a) / 16) + "0123456789ABCDEF".charAt(a);
    }
    return hexStr;
};
/**
 * @description String方法扩展，将带有%的16进制字符转换为十进制字符串
 * @return {String} result 十进制字符串
 * @example  "%31".hex2Str()  返回 "1"
 */
String.prototype.hex2Str = function () {
    var arr = this.split("%");
    for (var index = 0; index < arr.length; index++) {
        arr[index] = arr[index].substring(1);
    }
    return arr.join("");
};
/**
 * @description Number方法扩展，用于将人民币小写改完大写，最大值：99999999999.99
 * @return {String} 人民币大写
 * @example  var a = 102; a.chnMoney() 返回：壹佰零贰元整
 */
Number.prototype.chnMoney = String.prototype.chnMoney;
/**
 * @description Date方法扩展，date对象转化为字符串yyyy-mm-dd，
 * @return {String} 转换后的日期字符串
 * @example  new Date().dateFormat() 返回：2011-07-21
 */
Date.prototype.dateFormat = function () {
    return this.getFullYear() + "-" + commonUtil.getStr(this.getMonth() + 1) + "-" + commonUtil.getStr(this.getDate());
};
/**
 * @description Date方法扩展，date对象转化为字符串yyyy-mm-dd hh:mm，
 * @return {String} 转换后的日期字符串
 * @example  new Date().dateMinuteFormat() 返回：2011-07-21 21:15
 */
Date.prototype.dateMinuteFormat = function () {
    return this.getFullYear() + "-" + commonUtil.getStr(this.getMonth() + 1) + "-" + commonUtil.getStr(this.getDate()) + " " + commonUtil.getStr(this.getHours()) + ":" + commonUtil.getStr(this.getMinutes());
};
/**
 * @description Date方法扩展，date对象转化为字符串yyyy-mm-dd hh:mm:ss，
 * @return {String} 转换后的日期字符串
 * @example  new Date().datetimeFormat() 返回：2011-07-21 21:16:05
 */
Date.prototype.datetimeFormat = function () {
    return this.getFullYear() + "-" + commonUtil.getStr(this.getMonth() + 1) + "-" + commonUtil.getStr(this.getDate()) + " " + commonUtil.getStr(this.getHours()) + ":" + commonUtil.getStr(this.getMinutes()) + ":" + commonUtil.getStr(this.getSeconds());
};
/**
 * @description Date方法扩展，date对象转化为字符串hh:mm，
 * @return {String} 转换后的日期字符串
 * @example  new Date().minuteFormat() 返回：21:16
 */
Date.prototype.minuteFormat = function () {
    return commonUtil.getStr(this.getHours()) + ":" + commonUtil.getStr(this.getMinutes());
};
/**
 * @description Date方法扩展，date对象转化为字符串yyyy-mm-dd hh:mm:ss，
 * @return {String} 转换后的日期字符串
 * @example  new Date().timeFormat() 返回：21:16:43
 */
Date.prototype.timeFormat = function () {
    return commonUtil.getStr(this.getHours()) + ":" + commonUtil.getStr(this.getMinutes()) + ":" + commonUtil.getStr(this.getSeconds());
};