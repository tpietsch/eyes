(function () {
    'use strict';
    define([],
        function () {
            PostLoginRedirect.prototype.$inject = ['$state'];

            function PostLoginRedirect($state) {
                this.$state = $state;
            }

            PostLoginRedirect.prototype.set = function (redirectState) {
                this.redirectState = redirectState;
            };

            PostLoginRedirect.prototype.isRedirect = function () {
                return this.redirectState;
            };

            PostLoginRedirect.prototype.go = function () {
                if (this.redirectState) {
                    window.location.hash = (this.redirectState);
                }
            };

            return PostLoginRedirect
        });
})();