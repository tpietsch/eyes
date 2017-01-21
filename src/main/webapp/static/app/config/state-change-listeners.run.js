(function () {
    'use strict';
    define(['jquery', 'env', 'growlMessage'],
        function ($, env, growlMessage) {
            StateChangeListeners.prototype.$inject = ['$rootScope', '$state', 'AuthResource'];
            function StateChangeListeners($rootScope, $state, AuthResource) {
                $rootScope.$on('$viewContentLoaded', function (event) {

                });

                $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
                    //if(!env.loggedIn() && toState.name !== "login"){
                    //    event.preventDefault();
                    //    $state.go('login',{});
                    //}
                    AuthResource.get().$promise.then(function (data) {
                        if (!data.loggedIn) {
                            $state.go("login");
                        }
                    }.bind(this));
                });


                $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {

                });

                $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
                    console.log(error)
                });


                $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {
                });
            }

            return StateChangeListeners;
        });
})();