(function () {
   'use strict';
   define([], function () {

      var _context = document.querySelector("[data-context]").getAttribute("data-context");

      if (_context !== '/')
         _context += '/';
      var _env = document.querySelector("script[data-env]");
      if (typeof _env !== 'undefined') {
         _env = _env.getAttribute("data-env");
      }

      return {
         env: _env,
         context: _context,
         getHost: function () {
            var host = document.querySelector("[data-rest]");
            if(host){
               return document.querySelector("[data-rest]").getAttribute("data-rest");
            }else{
               return "";
            }
         }
      }
   });
})();