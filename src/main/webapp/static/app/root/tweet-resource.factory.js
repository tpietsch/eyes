(function () {
    'use strict';

    define(['env'], function (env) {
        TweetResource.prototype.$inject = ['$resource'];
        function TweetResource($resource) {
            return $resource(env.getHost() + '/rest/v1/user/:id/tweet', {},
                {
                });
        }

        return TweetResource;
    });
})();
