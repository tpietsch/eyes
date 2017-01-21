require(['angular','growlMessage','env','static/app/main-app'], function (angular,growlMessage,env) {
    angular.element(document).ready(function () {
        angular.bootstrap(document.getElementsByTagName('body')[0], ['app']);
    });
});