(function () {
    'use strict';
    define([], function () {
        RootController.prototype.$inject = ['$scope', 'AuthResource', '$state',
            'user','TweetResource','FollowResource','FollowingResource',
        'tweets','CurrentUser','UserResource','allUsers','followedBy','userFollowing','currentUser'];
        function RootController($scope, AuthResource, $state,
                                user,TweetResource,FollowResource,FollowingResource,
                                tweets,CurrentUser,UserResource,allUsers,followedBy,userFollowing,currentUser) {
            this.AuthResource = AuthResource;
            this.TweetResource = TweetResource;
            this.UserResource = UserResource;
            this.FollowResource = FollowResource;
            this.FollowingResource = FollowingResource;
            this.currentUser = currentUser;
            this.$state = $state;
            this.user = user;
            this.$scope = $scope;
            this.AuthResource.get().$promise.then(function (data) {
                if (!data.loggedIn) {
                    this.$state.go("login");
                }
            }.bind(this));
            this.allUsers = allUsers;

            this.followedBy = followedBy;
            this.userFollowing = userFollowing;
            this.tweets = tweets;
        }

        RootController.prototype.logout = function () {
            this.AuthResource.logout(function (data) {
                this.$state.go('login');
            }.bind(this))
        };

        RootController.prototype.tweet = function (msg) {
            var twt = {};
            twt.tweet = msg;
            this.TweetResource.save({id:this.user.userId},twt).$promise.then(function(data){
                this.tweets.unshift(data);
            }.bind(this))
        };

        RootController.prototype.follow = function (userId) {
            var follow = {};
            this.FollowResource.save({id:userId},follow).$promise.then(function(data){
                this.$state.reload();
            }.bind(this))
        };

        RootController.prototype.unfollow = function (followId) {
            this.FollowResource.delete({id:this.user.userId,followId:followId}).$promise.then(function(data){
                this.$state.reload();
            }.bind(this))
        };

        RootController.prototype.filterTweets = function (searchTerm) {
            this.TweetResource.query({id:this.user.userId,search:searchTerm}).$promise.then(function(data){
                this.tweets = data;
            }.bind(this))
        };

        return RootController;
    });
})();