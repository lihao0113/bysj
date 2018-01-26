function ajax(url, params, success, timeout, error, context) {
	var options = {
		type: 'POST',
		url: url,
		data: params,
		dataType: 'json',
		cache: false,
		success: success
	};
	if(error)
		options.error = error;
	if(timeout)
		options.timeout = timeout;
	if(context)
		options.context = context;
	jQuery.ajax(options);
}