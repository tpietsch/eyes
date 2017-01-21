(function () {
    'use strict';
    define(["jquery", 'bootstrap-growl'], function ($) {
        return function notify(message, type) {
            $.growl({
                icon: '',
                title: '',
                message: message,
                url: ''
            }, {
                element: 'body',
                type: type,
                allow_dismiss: true,
                placement: {
                    from: 'bottom',
                    align: 'right'
                },
                offset: {
                    x: 30,
                    y: 30
                },
                spacing: 1,
                stackup_spacing: 1,
                z_index: 99999999,
                delay: 2500,
                timer: 1000,
                url_target: '_blank',
                mouse_over: false,
                animate: {
                    enter: 'animated fadeInRight',
                    exit: 'animated fadeOutRight'
                },
                icon_type: 'class',
                template: '<div data-growl="container" class="alert" role="alert">' +
                '<button type="button" class="close" data-growl="dismiss">' +
                '<span aria-hidden="true">&times;</span>' +
                '<span class="sr-only">Close</span>' +
                '</button>' +
                '<span data-growl="icon"></span>' +
                '<span data-growl="title"></span>' +
                '<span data-growl="message"></span>' +
                '<a href="#" data-growl="url"></a>' +
                '</div>'
            });
        };
    });
})();