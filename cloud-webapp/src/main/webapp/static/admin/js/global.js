/**
 * Created by Zhaopeng-Rabook on 14-12-23.
 */
/**
 * 首先是全局的地址
 */
var basePath = function () {
    var url = window.location + "";
    var h = url.split("//");
    var x = h[1].split("/");
    return h[0] + "//" + window.location.host + "/" + x[1] + "/";
}();

var globalConstant = {
    AESKEY: "Hope6537JiChuang",
    AESIV: "1234567812345678",
    YES: "是",
    NO: "否",
    FOUNDER: "创建者",
    READER: "只读",
    WRITER: "读写"
};

var globalFunction = {


    autoCompleteDriver: function (targetUrl, method, formData, autoCompleteInput, putSelectionFunction, afterDataFunction) {
        $.ajax({
            url: targetUrl,
            type: method,
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (data) {
                if (globalFunction.returnResult(data, undefined, false)) {
                    var dataList = putSelectionFunction(data);
                    $(autoCompleteInput).autocomplete({
                        lookup: dataList,
                        onSelect: function (suggestion) {
                            afterDataFunction(suggestion);
                        }
                    });
                }
            }
        });
    },
    autoCompleteBySelect2: function (targetUrl, method, formData, autoCompleteInput, afterDataFunction) {
        var targetIndex = $(autoCompleteInput);
        $.ajax({
            url: targetUrl,
            contentType: 'application/json',
            type: method,
            async: false,
            success: function (data) {
                if (globalFunction.returnResult(data, undefined, false)) {
                    afterDataFunction(data, targetIndex);
                    /*var list = eval(data.returnData.roleList);
                     roleList = list;
                     for (var i = 0; i < list.length; i++) {
                     var roleType = list[i].type;
                     var roleId = list[i].roleId;
                     var des = list[i].des;
                     if (roleType == "管理员权限") {
                     adminRoles.append("<option value = " + roleId + ">" + des + "</option>");
                     }
                     if (roleType == "读写权限") {
                     writeRoles.append("<option value = " + roleId + ">" + des + "</option>");
                     }
                     if (roleType == "只读权限") {
                     readRoles.append("<option value = " + roleId + ">" + des + "</option>");
                     }
                     }*/
                }
            }
        })
    },
    returnResult: function (data, message, binding) {
        var messageNull = message == "" || message == undefined;
        if (data.returnState == "OK") {
            if (binding != false)
                toast.success(messageNull ? data.returnMsg : message);
            return true;
        } else {
            if (binding != false && messageNull)
                toast.error(messageNull ? data.returnMsg : message);
            return false;
        }
    },
    replaceHtml: function (S) {
        var str = S;
        str = str.replace(/<[^>].*?>/g, '');
        str = str.replace("&nbsp;", "");
        return str;
    },
    delHtmlTag: function (str) {
        var str = str.replace(/<\/?[^>]*>/gim, "");//去掉所有的html标记
        var result = str.replace(/(^\s+)|(\s+$)/g, "");//去掉前后空格
        return result.replace(/\s/g, "");//去除文章中间空格
    },
    Encrypt: function (word) {
        var key = CryptoJS.enc.Utf8.parse(globalConstant.AESKEY);
        var iv = CryptoJS.enc.Utf8.parse(globalConstant.AESIV);
        var srcs = CryptoJS.enc.Utf8.parse(word);
        var encrypted = CryptoJS.AES.encrypt(srcs, key, {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
        return encrypted.toString();
    }
    ,
    Decrypt: function (word) {
        var key = CryptoJS.enc.Utf8.parse(globalConstant.AESKEY);
        var iv = CryptoJS.enc.Utf8.parse(globalConstant.AESIV);
        var decrypted = CryptoJS.AES.decrypt(word, key, {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
        return decrypted.toString(CryptoJS.enc.Utf8);
    }

}

