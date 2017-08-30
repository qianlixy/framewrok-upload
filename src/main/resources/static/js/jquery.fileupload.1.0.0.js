;(function($) {

    var DEFAULT = {
        action:"/upload",
        progress:function(progress) {},
        complete:function(data, error) {},
        success:function(data) {},
        error:function(error) {}
    };
    
    var FlashUpload = function(settings) {
    	var opts = settings.opts;
    	settings.ele.uploadify({
			'swf'      : 'http://localhost:8082/static/js/uploadify/uploadify.swf',
			'uploader' : opts.action,
			'auto': true,
			onAllComplete:function(event, data) {
				alert(data);
			}
		});
    }
    
    var Html5Upload = function(settings) {
    	var opts = settings.opts, $this = settings.ele;
    	var uploadProgress = function(e) {
            opts.progress.call($this, {loaded:e.loaded,total:e.total});
        };
        var uploadComplete = function(e) {
        	var data = $.parseJSON(e.target.responseText);
        	opts.complete.call($this, data);
        	opts.success.call($this, data);
        };
        var uploadFailed = function(e) {
        	var error = {message:"Unknown reason"};
        	opts.complete.call($this, null, error);
        	opts.error.call($this, error);
        };
        var uploadCanceled = function(e) {};
        $this.bind("change", function() {
    		var fd = new FormData();
    		$.each($this[0].files, function(i, n) {
    			fd.append(($this.attr("name") || 'file') + i, n);
    		})
    		var xhr = new XMLHttpRequest();
    		xhr.upload.addEventListener("progress", uploadProgress, false);
    		xhr.addEventListener("load", uploadComplete, false);
    		xhr.addEventListener("error", uploadFailed, false);
    		xhr.addEventListener("abort", uploadCanceled, false);
    		xhr.open("POST", opts.action);
    		xhr.send(fd);
        });
    } 

    $.fn.fileupload = function(settings) {
        var opts = $.extend({}, DEFAULT, settings);
        return this.each(function() {
            var $this = $(this);
            var uploadSettings = {
            	ele:$this,
            	opts:opts
            }
            if(typeof(FormData) === 'undefined') {
            	FlashUpload(uploadSettings);
            } else {
            	Html5Upload(uploadSettings);
            }
            
            
        });
    };

    $.fileupload = function() {

    };
})(jQuery);
