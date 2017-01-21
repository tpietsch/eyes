(function () {
    'use strict';
    define(['growlMessage'],
        function (growlMessage) {
            HttpErrorInterceptor.prototype.$inject = ['$q'];
            function HttpErrorInterceptor($q) {
                return {
                    request: function (config) {
                        return config || $q.when(config);
                    },
                    requestError: function (request) {
                        growlMessage("request error " + request.status, 'danger')
                        return $q.reject(request);
                    },
                    response: function (response) {
                        if (response.status != 200) {
                            growlMessage("request error " + response.status, 'danger')
                        }
                        return response || $q.when(response);
                    },
                    responseError: function (response) {
                        growlMessage("request error " + response.status + ". " + response.data.message, 'danger')
                        return $q.reject(response);
                    }
                }
            }

            return HttpErrorInterceptor;
        });
})();