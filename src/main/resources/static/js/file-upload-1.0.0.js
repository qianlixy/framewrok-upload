(function(w, $) {

	$(document).ready(function() {
		$("head").append("<link rel=\"stylesheet\" href=\"../css/file-upload.css\">");
	});

	var DEFAULT = {
		fileId:'file',
		action:'/upload',
		type:'post',
		dataType:'json',
		extra:{},
		style:'basic',
		complete:function() {},
		success:function() {},
		error:function() {}
	};

	var AjaxFileUpload = {
		create : function(settings) {
			var _this = this;
			_this.settings = settings;
			$("#"+settings.fileId).bind("change", function() {
				var flag = "upload_iframe_" + new Date().getTime();
				var iframe = _this._createIframe(flag);
				var form = _this._createForm(flag);
				iframe.bind("load", function() {_this._loadSuc.call(_this, iframe[0], form[0])});
				form.submit();
			});
		},
		_createIframe : function(flag) {
			$("<iframe width='0' height='0' border='0' src='' id='"+flag+"' name='"+flag+"'></iframe>")
				.css({position:'absolute',left:9999,top:9999})
				.prependTo($("body"));
			return $("#" + flag);
		},
		_createForm : function(flag) {
			var form = $("<form />", {
				"target" : flag,
				"action" : this.settings.action,
				"method" : this.settings.type,
				"enctype" : "multipart/form-data"
			}).hide().prependTo($("body"));
			var oldFileElement = $("#"+this.settings.fileId);
			var newFileElement = oldFileElement.clone();
			oldFileElement.before(newFileElement);
			form.append(oldFileElement);
			if(this.settings.extra) {
				$.each(this.settings.extra, function(name, vlaue) {
					form.append("<input type='hidden' name='"+name+"' value='"+value+"'/>");
				});
			}
			return form;
		},
		_loadSuc : function(iframe, form) {
			try {
				var responseText;
				if(iframe.contentWindow) {
					responseText = iframe.contentWindow.document.body.innerHTML;
				} else if(iframe.contentDocument) {
					responseText = iframe.contentDocument.document.body.innerHTML;
				}
				if(!responseText) {
					this.settings.complete({});
					this.settings.success({});
					return;
				}
				responseText = responseText.replace(/<[/]{0,1}[\w]+>/g, "");
				var data;
				switch(this.settings.dataType) {
					case 'json' :
						data = $.parseJSON(responseText);
						break;
					default : data = responseText;
				}
				this.settings.complete(data);
				this.settings.success(data);
			} catch(e) {
				this._loadError(e);
			} finally {
				$(iframe).remove();
				$(form).remove();
			}
		},
		_loadError : function(e) {
			this.settings.complete(null, e);
			this.settings.error(e);
		}
	};

	var createInputFile = function(settings) {
		var $ele = $("#"+settings.fileId).addClass("ajax-file-upload " + settings.style),
			fileId = "ajax-file-upload_"+$ele.attr("id");
		$("<input type='file' class='input-file' id='"+fileId+"' name='"+$ele.data('name')+"'/>").appendTo($ele);
		settings.fileId = fileId;
		AjaxFileUpload.create(settings);
	};

	$.fileupload = function(settings) {
		createInputFile($.extend({}, DEFAULT, settings));
		//AjaxFileUpload.create($.extend({}, DEFAULT, settings));
	};

})(window, jQuery);
