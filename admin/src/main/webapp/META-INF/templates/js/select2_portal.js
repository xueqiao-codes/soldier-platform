function open_service_admin_select2(selector) {
	$(selector).html("").select2({
        placeholder: "输入OA用户名",
        allowClear: true,
        multiple: true,
        quietMillis: 100,
        minimumInputLength: 2,
        id: function (obj) {
            return obj.userName;
        },
        ajax: {
            url: "${base_url}/OaUserManage/select2UserList",
            dataType: 'json',
            data: function (term, page) {
                return {
                    query: term,
                    limit: 10
                };
            },
            results: function (json, page) {
                return {results: json.data};
            }
        },

        initSelection: function (element, callback) {
            var data = [];
            $($(element).val().split(",")).each(function () {
            	value=$.trim(this);
            	if (value != "") {
            		data.push({id: value, userName: value});
            	}
            });
            callback(data);
        },

        formatResult: function (obj) {
            return obj.userName
        },
        formatSelection: function (obj) {
            return obj.userName
        }
    });
}

function open_hosts_select2(selector) {
	$(selector).html("").select2({
        placeholder: "输入机器名称",
        allowClear: true,
        multiple: true,
        quietMillis: 100,
        minimumInputLength: 2,
        id: function (obj) {
            return obj.hostName;
        },
        ajax: {
            url: "${base_url}/MachineManage/select2MachineList",
            dataType: 'json',
            data: function (term, page) {
                return {
                    query: term,
                    limit: 10
                };
            },
            results: function (json, page) {
                return {results: json.data};
            }
        },

        initSelection: function (element, callback) {
            var data = [];
            $($(element).val().split(",")).each(function () {
            	value=$.trim(this);
            	if (value != "") {
            		data.push({id: value, hostName: value});
            	}
            });
            callback(data);
        },

        formatResult: function (obj) {
            return obj.hostName
        },
        formatSelection: function (obj) {
            return obj.hostName
        }
    });
	
	
}