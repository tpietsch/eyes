(function () {
    'use strict';
    define([], function () {
        RootStates.prototype.$inject = ['$stateProvider','$urlRouterProvider'];
        function RootStates($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise("/login");
            $stateProvider
                .state('user', {
                    url: "/user/:id",
                    templateUrl: 'static/app/root/main.html',
                    controller: "RootController",
                    controllerAs: "RootController",
                    resolve: {
                        user: [
                            'UserResource','$stateParams', function (UserResource,$stateParams) {
                                return UserResource.get({id:$stateParams.id}).$promise
                            }
                        ],
                        currentUser: [
                            'AuthResource','$stateParams', function (AuthResource,$stateParams) {
                                return AuthResource.get().$promise
                            }
                        ],
                        followedBy: [
                            'FollowResource','$stateParams', function (FollowResource,$stateParams) {
                                return FollowResource.query({id:$stateParams.id}).$promise
                            }
                        ],
                        userFollowing: [
                            'FollowingResource','$stateParams', function (FollowingResource,$stateParams) {
                                return FollowingResource.query({id:$stateParams.id}).$promise
                            }
                        ],
                        tweets: [
                            'TweetResource','$stateParams', function (TweetResource,$stateParams) {
                                return TweetResource.query({id:$stateParams.id}).$promise
                            }
                        ],

                        allUsers: [
                            'UserResource','$stateParams', function (UserResource) {
                                return UserResource.query({}).$promise
                            }
                        ]

                    }
                })
        }

        return RootStates;

    });
})();

