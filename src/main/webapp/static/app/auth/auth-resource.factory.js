(function () {
    'use strict';

    define(['env'], function (env) {
        AuthResource.prototype.$inject = ['$resource'];
        function AuthResource($resource) {
            return $resource(env.getHost() + '/rest/v1/auth', {},
                {
                    'login': {
                        method: 'POST',
                    },
                    'logout': {
                        method: 'DELETE'
                    }
                });
        }

        return AuthResource;
    });
})();
