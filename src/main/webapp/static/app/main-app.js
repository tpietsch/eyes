(function () {
    'use strict';
    define(['angular', 'static/app/config/state-change-listeners.run', 'static/app/config/http-provider.config',
            'static/app/config/error-interceptor.factory', 'static/app/root/root.controller', 'static/app/root/root.states',
            'static/app/root/user-resource.factory','static/app/root/tweet-resource.factory','static/app/root/follow-resource.factory',
            'static/app/root/following-resource.factory',
            //MODULES
            'static/app/auth/auth.module', 'static/app/login/login.module',
            'ui-router', 'angular-file-upload', 'angular-animate', 'xeditable',
            'angular-resource', 'angular-ui-bootstrap', 'angular-ui-bootstrap-tpls'],
        function (angular, StateChangeListeners, HttpProvider,
                  HttpErrorInterceptor, RootController, RootStates,
                  UserResource,TweetResource,FollowResource,FollowingResource) {
            var module = angular.module('app', ['auth.module', 'login.module',
                'ui.router', 'ngFileUpload', 'ngAnimate', 'xeditable',
                'ngResource', 'ui.bootstrap', 'ui.bootstrap.tpls']);
            module.factory('errorInterceptor', HttpErrorInterceptor);
            module.factory('UserResource', UserResource);
            module.factory('TweetResource', TweetResource);
            module.factory('FollowResource', FollowResource);
            module.factory('FollowingResource', FollowingResource);
            module.controller('RootController', RootController);
            module.config(HttpProvider);
            module.config(RootStates);
            module.run(StateChangeListeners);
            module.run(['editableOptions', function (editableOptions) {
                editableOptions.theme = 'bs3';
            }]);
            return module;
        });
})();


