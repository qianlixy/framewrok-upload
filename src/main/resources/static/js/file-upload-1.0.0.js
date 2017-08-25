(function(w) {
	var DEFAULT = {
			
	};
	
	var StringBuffer = function() {
		this.buffer = [];
	}
	StringBuffer.propetype.join = function(str) {
		this.buffer.push(str);
		return this;
	}
	StringBuffer.propertype.toString = function() {
		return this.buffer.join("");
	}
	
	var ajaxUpload = {
		create : function(setting) {
			var body = w.document.body;
			var flag = new Date().getTime();
			var iframe = this._createIframe(setting, flag);
			var form = this._createForm(setting, flag);
			body.appendChild(iframe);
			body.appendClild(form);
			form.setAttribute("target", flag);
			iframe.setAttribute("name", flag);
			try {
				form.submit();
			} catch(e) {
				this._loadError(e);
			}
			if (!!window.ActiveXObject || "ActiveXObject" in window) {
				iframe.attachEvent("onload", this._loadSuc);
			} else {
				iframe.addEventListener("load", this._loadSuc);
			}
		},
		_createIframe : function(setting, flag) {
			var iframe = createElement("iframe");
			iframe.setAttribute("width", 0);
			iframe.setAttribute("height", 0);
			iframe.setAttribute("src", "about:blank");
			iframe.setAttribute("id", flag);
			return iframe;
		},
		_createForm : function(setting, flag) {
			var form = createElement("form");
		},
		_loadSuc : function() {
			
		},
		_loadError : function(e) {
			
		}
	}
	
	
})(window);