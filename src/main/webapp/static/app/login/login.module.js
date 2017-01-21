(function () {
    'use strict';
    define(['angular', 'static/app/login/login.controller', 'static/app/login/login.states', 'ui-router', 'static/app/auth/auth.module'],
        function (angular, loginController, loginStates) {
            var module = angular.module("login.module", ['ui.router', 'auth.module']);
            module.controller("LoginController", loginController);
            module.config(loginStates);
            return module;
        });
})();