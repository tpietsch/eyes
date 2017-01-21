(function () {
    'use strict';
    define([],
        function () {
            CurrentUserService.prototype.$inject = [];

            function CurrentUserService() {

            }

            CurrentUserService.prototype.set = function (user) {
                this.user = user;
                return this.user;
            };

            CurrentUserService.prototype.get = function () {
                if (typeof this.user === 'undefined') {
                    throw 'user must be set to use the CurrentUserService';
                } else {
                    return this.user;
                }
            };

            return CurrentUserService
        });
})();