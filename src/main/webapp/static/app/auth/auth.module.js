(function () {
    'use strict';
    define(['angular', 'static/app/auth/auth-resource.factory', 'static/app/auth/current-user.service', 'static/app/auth/user-resource.factory',
            'static/app/auth/state-post-login-redirect.service', 'ui-router'],
        function (angular, AuthResource, CurrentUserService, UserResource, PostLoginRedirect) {
            var module = angular.module("auth.module", ['ui.router']);
            module.factory("AuthResource", AuthResource);
            module.factory("CurrentUserResource", UserResource);
            module.service("CurrentUser", CurrentUserService);
            //module.provider("PostLoginRedirect", PostLoginRedirect);
            return module;
        });
})();