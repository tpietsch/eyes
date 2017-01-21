(function (){
    requirejs.config({
        baseUrl: "",
        waitSeconds: 0,
        //enforceDefine: true,
        paths: {
            /* LIBS */
            'angular' : 'static/node_modules/angular/angular.min',
            'angular-animate' : 'static/node_modules/angular-animate/angular-animate.min',
            'angular-resource' : 'static/node_modules/angular-resource/angular-resource.min',
            'ui-router' : 'https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.4.0/angular-ui-router.min',
            //'ui-router' : 'static/node_modules/angular-ui-router/release/angular-ui-router.min.js',
            'angular-ui-bootstrap' : 'static/node_modules/angular-ui-bootstrap/dist/ui-bootstrap',
            'angular-ui-bootstrap-tpls' : 'static/node_modules/angular-ui-bootstrap/dist/ui-bootstrap-tpls',
            'angular-file-upload' : 'static/node_modules/ng-file-upload/dist/ng-file-upload-all.min',
            'angular-autocomplete' : 'static/node_modules/angucomplete-alt/dist/angucomplete-alt.min',
            'angular-ui-tree' : 'static/node_modules/angular-ui-tree/dist/angular-ui-tree.min',
            'angular-ngIdle' : 'static/node_modules/ng-idle/angular-idle.min',
            'angular-storage' : 'static/node_modules/angular-storage/dist/angular-storage.min',
            'bootstrap' : 'static/node_modules/bootstrap/dist/js/bootstrap.min',
            'bootstrap-notify' : 'static/node_modules/bootstrap-growl/bootstrap-notify.min',
            'sweetAlert' : 'static/node_modules/sweetalert/dist/sweetalert.min',
            'moment' : 'static/node_modules/moment/min/moment.min',
            'metisMenu' : 'static/node_modules/metismenu/dist/metisMenu',
            'inspinia' : 'js/custom/inspinia',
            'stompjs' : 'static/node_modules/stompjs/lib/stomp',
            //'SockJS' : '//cdn.jsdelivr.net/sockjs/1/sockjs.min',
            'SockJS' : 'static/modules/sockjs.min',
            'wowJs' : 'static/js/custom/wow.min',
            'paceJs' : 'static/node_modules/pace-progress/pace',
            'jquery.slimScroll' : 'static/node_modules/jquery-slimscroll/jquery.slimscroll',
            'jquery' : 'static/node_modules/jquery/dist/jquery.min',
            'jquery.mCustomScrollbar.concat' : 'static/node_modules/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar',
            'fullcalendar' : 'static/node_modules/fullcalendar/dist/fullcalendar',
            'salvattore' : 'static/node_modules/salvattore/dist/salvattore.min',
            'jquery.flot' : 'static/node_modules/flot-charts/jquery.flot',
            'jquery.flot.resize' : 'static/node_modules/flot-charts/jquery.flot.resize',
            'jquery.sparkline' : 'static/node_modules/sparklines/source/sparkline',
            'jquery.placeholder' : 'static/node_modules/jquery-placeholder/jquery.placeholder',
            'jquery-datetimepicker' : 'static/node_modules/jquery-datetimepicker/build/jquery.datetimepicker.full.min',
            'bootstrap-datetimepicker' : 'static/node_modules/eonasdan-bootstrap-datetimepicker/src/js/bootstrap-datetimepicker',
            'jquery-mousewheel' : 'static/node_modules/jquery-mousewheel/jquery.mousewheel',
            'xeditable' : 'static/node_modules/angular-xeditable/dist/js/xeditable.min',
            'jsTree' : 'static/node_modules/jstree/dist/jstree.min',
            'angucomplete':'static/node_modules/angucomplete-alt/dist/angucomplete-alt.min',
            'uuid':'static/node_modules/uuid/uuid',
            'guid':'static/node_modules/guid/guid',
            'pdfjs-dist/build/pdf' : 'modules/pdfjs/pdf',
            'pdf.worker' : 'modules/pdfjs/pdf.worker',
            'signiture.panel' : 'static/modules/signature_pad.min',
            'angular-summernote' : 'static/node_modules/angular-summernote/dist/angular-summernote.min',
            'summernote' : 'static/node_modules/summernote/dist/summernote',
            'lib/stripe' : 'https://js.stripe.com/v2/?',

            /* TEMPLATEJS & MODULES */
            'growlMessage': "static/modules/growlMessage",
            'env': "static/modules/env",
            'bootstrap-growl' : 'static/modules/bootstrap-growl',
            'waves' : 'static/modules/waves',


            /* APP UI ROUTER STATES */
            'rootStates': "app/root/rootStates",
            'teamStates': "app/team/teamStates",
            'profileStates': "app/root/profileStates",
            'playerFeeStates': "app/team/playerFeeStates",

            /* APP SERVICES */
            'chat-service' : 'app/ChatService',

            /* APP ANGULAR RESOURCES */
            'TeamResource': "app/root/TeamResource",
            'TeamAdminResource': "app/root/TeamAdminResource",
            'ExpenseResource': "app/root/ExpenseResource",
            'PlayerResource': "app/root/PlayerResource",
            'ReceiptResource': "app/root/ReceiptResource",
            'AuthResource': "app/root/AuthResource",
            'UserResource': "app/root/UserResource",
            'TeamMessageResource': "app/root/TeamMessageResource",
            'ActualUserResource': "app/root/ActualUserResource",
            'InstantMessageResource': "app/root/InstantMessageResource",
            'FeeResource': "app/root/FeeResource",
            'PlayerFeeResource': "app/root/PlayerFeeResource",
        },
        shim: {
            'angular' : {
                deps : ['jquery'],
                exports : "angular"
            },
            'angular-ui-bootstrap-tpls' : ['bootstrap','angular'],
            'jquery.flot' : ['jquery'],
            'jquery.flot.resize' : ['jquery','jquery.flot'],
            'angular-ui-bootstrap' : ['bootstrap','angular','angular-ui-bootstrap-tpls'],
            'angular-resource': ['angular'],
            'sweetAlert':{
                deps : [],
                exports : "swal"
            },
            'waves':{
                deps : [],
                exports : "waves"
            },
            'wowJs' :{
                exports : "WOW"
            },
            'SockJS' :{
                exports : "SockJS",
            },
            'stompjs' :{
                exports : "Stomp",
            },
            'angular-animate': ['angular'],
            'bootstrap-notify': ['jquery','bootstrap'],
            'bootstrap-datetimepicker': ['jquery','bootstrap'],
            'ui-router': ['angular'],
            'xeditable': ['angular'],
            'angular-ngIdle': ['angular'],
            'angucomplete': ['angular'],
            'angular-summernote': ['angular','summernote'],
            'angular-ui-tree': ['angular'],
            'bootstrap': ['jquery'],
            'jquery.slimScroll': ['jquery'],
            'angular-file-upload': ['angular'],
            'angular-storage': ['angular'],
            'lib/stripe': {
                exports: 'Stripe',
                init: function() {
                    this.Stripe.setPublishableKey('pk_test_4WRpgPkq5tDSjCip1A1ODFv2');
                    return this.Stripe
                }
            }

        }
    });
})();

