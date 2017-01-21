(function () {
    'use strict';
    define(['jquery', 'env', 'sweetAlert'],
        function ($, env, sweetAlert) {
            LoginController.prototype.$inject = ['$scope', 'AuthResource', '$state', 'CurrentUserResource','CurrentUser'];

            function LoginController($scope, AuthResource, $state, CurrentUserResource,CurrentUser) {
                this.$scope = $scope;
                this.AuthResource = AuthResource;
                this.$state = $state;
                this.CurrentUserResource = CurrentUserResource;
                this.CurrentUser = CurrentUser;
                this.username = "test";
                this.password = "test";

            }

            LoginController.prototype.login = function () {
                this.AuthResource.login({
                    username: this.username,
                    password: this.password
                }).$promise.then(function (data) {
                    this.AuthResource.get().$promise.then(function (data) {
                        if (data.loggedIn) {
                            //this.CurrentUser.set(data);
                            this.$state.go("user", {id: data.userId});
                        } else {
                            sweetAlert("Login Failed!", "", "error")
                        }
                    }.bind(this));
                }.bind(this));
            };

            LoginController.prototype.register = function () {
                this.CurrentUserResource.save({
                    username: this.username,
                    password: this.password
                }).$promise.then(function (data) {
                    if (data.userId) {
                        this.login();
                        //sweetAlert("Registration Success!", "login in for yah ;)!", "success")
                    }
                }.bind(this));
            };

            return LoginController
        });
})();