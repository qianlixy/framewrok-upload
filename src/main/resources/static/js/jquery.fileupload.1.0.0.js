;(function($) {

    var DEFAULT = {
        action:"/upload",
        progress:function(progress) {},
        complete:function(data, error) {},
        success:function(data) {},
        error:function(error) {}
    };

    $.fn.fileupload = function(settings) {
        var opts = $.extend({}, DEFAULT, settings);
        return this.each(function() {
            var $this = $(this);
            var uploadProgress = function(e) {
                opts.progress.call($this, {loaded:e.loaded,total:e.total});
            };
            var uploadComplete = function(e) {
            	opts.complete.call($this, $.parseJSON(e.target.responseText));
            	opts.success.call($this, $.parseJSON(e.target.responseText));
            };
            var uploadFailed = function(e) {
            	var error = {message:"Unknown reason"};
            	opts.complete.call($this, null, error);
            	opts.error.call($this, error);
            };
            var uploadCanceled = function(e) {};
            $this.bind("change", function() {
                if(typeof(FormData) === 'undefined') {
                    console.log("不支持HTML5");
                    return;
                }
        		var fd = new FormData();
        		fd.append($this.attr("name"), $this[0].files[0]);
        		var xhr = new XMLHttpRequest();
        		xhr.upload.addEventListener("progress", uploadProgress, false);
        		xhr.addEventListener("load", uploadComplete, false);
        		xhr.addEventListener("error", uploadFailed, false);
        		xhr.addEventListener("abort", uploadCanceled, false);
        		xhr.open("POST", opts.action);
        		xhr.send(fd);
            });
        });
    };

    $.fileupload = function() {

    };
})(jQuery);
