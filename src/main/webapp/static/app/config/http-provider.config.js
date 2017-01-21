(function () {
    'use strict';
    define([],
        function () {
            HttpProvider.prototype.$inject = ['$httpProvider'];
            function HttpProvider($httpProvider) {
                $httpProvider.interceptors.push('errorInterceptor');
                $httpProvider.defaults.transformResponse = function (data, headers) {
                    var x = headers()['content-type'];
                    if (typeof x !== 'undefined')
                        x = x.toLowerCase();
                    if (x === "application/json;charset=utf-8" || x === "application/json") {
                        return JSON.parse(data);
                    }
                    return data;

                };
                $httpProvider.defaults.withCredentials = true;
                $httpProvider.defaults.transformRequest = function (data, headers) {
                    var x = headers()['content-type'];
                    if (typeof x !== 'undefined')
                        x = x.toLowerCase();
                    if (x === "application/json;charset=utf-8" || x === "application/json") {
                        return JSON.stringify(data);
                    }
                    return data;
                }
            }

            return HttpProvider;
        });
})();