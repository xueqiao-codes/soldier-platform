var HOST_PORT = window.location.href;

var supportLang = [
	                {name: "java", selected: false},
					{name: "csharp", selected: false},
	                {name: "java-with-py-test", selected: false},
                    {name: "py", selected: false},
	                {name: "cpp", selected: false},
	                {name: "js", selected: false}
                   ];

var selectedFileIds = [];

window.onload = function() {
	init();
};

function init() {
	var startBtn = document.getElementById("start-btn");
	var keyword = document.getElementById("keyword");
	var search = document.getElementById("search");
	var generateResult = document.getElementById("generate-result");

	getAllFiles('');
	setLang();

	setupSelectedFileClick();
	setupFileMouseOver('#selected-file-list li a');
	setupFileMouseOver('#server-file-list li a');

	search.onclick = function() {
		getAllFiles(keyword.value);
	};

	startBtn.onclick = function() {
		var ids = '';
		selectedFileIds.forEach(function(id) {
			ids += id;
		});

		var error = '';
		if (selectedFileIds !== '') {
			var selectedLang = [];
			supportLang.forEach(function(langInfo) {
				if (langInfo.selected) {
					selectedLang.push(langInfo.name);
				}
			});

			if (selectedLang.length > 0) {
				selectedLang.forEach(function(langName) {
					var url = HOST_PORT + "/generate/" + langName + "/" + selectedFileIds;
					$.getJSON(url, function(json) {
						if (json.retCode === 1) {
							generateResult.style.display = 'none';
							download_file(HOST_PORT + "/downloadResultZip/" + json.data);
						} else {
							generateResult.style.display = 'block';
							generateResult.style.color = 'red';
							generateResult.innerHTML = "生成失败: " + json.data ;
						}
					});

				});
			} else {
				error = "未选中任何生成代码类型";
			}
		} else {
			error = "未选中任何文件";
		}

		if (error === '') {
			generateResult.style.display = 'none';
		} else {
			generateResult.style.display = 'block';
			generateResult.style.color = 'red';
			generateResult.innerHTML = "生成失败: " + error ;
		}
	};
}

function getAllFiles(keyword) {
	$("#server-file-list").children().remove();
	$("#server-file-list").fileTree({ keyword: keyword }, function(file) {
		addSelectedFile(file);
	});
}

function setupSelectedFileClick() {
	$("#selected-file-list").find('li a').bind('click', function() { alert('click');return false; });
	$(document).on('click','#selected-file-list li a',function(){
		var fileInfo = JSON.parse($(this).attr('rel'));
		removeSelectedFile(fileInfo);
		$(this).remove();
	});
}

function setupFileMouseOver(selector) {
	$(document).on('mouseover',selector,function(){
		var listNode = $(this);
		var attrRel = listNode.attr('rel');
		if (attrRel) {
			var fileInfo = JSON.parse(attrRel);
			if (!fileInfo.content) {
				$.getJSON(HOST_PORT + "getFileContent/" + fileInfo.id, function(json) {
					fileInfo.content = json.data;
					listNode.attr("title",json.data);
					// listNode.attr("rel",JSON.stringify(fileInfo));
				});
			} else {
				listNode.title = fileInfo.content;
			}
		}
	});
}

function addSelectedFile(file) {
	if (selectedFileIds.indexOf(file.id) === -1) {
		$('#selected-file-list').append('<li class="file ext_thrift"><a rel='+ JSON.stringify(file) + '>' + $.base64.decode(file.id) + '</a></li>');
		selectedFileIds.push(file.id);
	}
}

function removeSelectedFile(file) {
	if (selectedFileIds.indexOf(file.id) !== -1) {
		selectedFileIds.splice($.inArray(file.id,selectedFileIds),1);
	}
}

function setLang() {
	supportLang.forEach(function(lang) {
		addLangNode(lang);
	});
}

function addLangNode(lang) {
	var langList = document.getElementById("lang-list");
	var li = document.createElement('li');
	var input = document.createElement('input');
	input.type = 'checkbox';
	input.checked = lang.selected;

	input.onclick = function() {
		this.checked = !!this.checked;
		lang.selected = this.checked;
	};

	var label = document.createElement('label');
	var text = document.createTextNode(lang.name);

	label.appendChild(text);
	li.appendChild(input);
	li.appendChild(label);
	langList.appendChild(li);
}

function download_file(url) {
    var iframe = document.createElement("iframe");
    download_file.iframe = iframe;
    document.body.appendChild(download_file.iframe);
    download_file.iframe.src = url;
    download_file.iframe.style.display = "none";
}