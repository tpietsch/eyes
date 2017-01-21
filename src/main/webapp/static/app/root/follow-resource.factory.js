(function () {
    'use strict';

    define(['env'], function (env) {
        FollowResource.prototype.$inject = ['$resource'];
        function FollowResource($resource) {
            return $resource(env.getHost() + '/rest/v1/user/:id/follow/:followId', {},
                {
                });
        }

        return FollowResource;
    });
})();
