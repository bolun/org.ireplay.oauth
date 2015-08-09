var Oauth = function () { };

Oauth.prototype.login = function (name, success, error) {
    cordova.exec(success, error, "Oauth", "login", [name]);
};

module.exports = new Oauth;