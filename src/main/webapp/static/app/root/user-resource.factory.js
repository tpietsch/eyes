(function () {
    'use strict';

    define(['env'], function (env) {
        UserResource.prototype.$inject = ['$resource'];
        function UserResource($resource) {
            return $resource(env.getHost() + '/rest/v1/user/:id', {},
                {
                });
        }

        return UserResource;
    });
})();
