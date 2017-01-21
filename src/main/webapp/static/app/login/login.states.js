(function () {
    'use strict';
    define([], function () {
        LoginStates.prototype.$inject = ['$stateProvider'];
        function LoginStates($stateProvider) {
            $stateProvider
                .state('login', {
                    url: "/login",
                    templateUrl: 'static/app/login/login.html',
                    controller: "LoginController",
                    controllerAs: "LoginController"
                });
        }

        return LoginStates;
    });
})();


