(function () {
    'use strict';

    define(['env'], function (env) {
        FollowingResource.prototype.$inject = ['$resource'];
        function FollowingResource($resource) {
            return $resource(env.getHost() + '/rest/v1/user/:id/following', {},
                {
                });
        }

        return FollowingResource;
    });
})();
