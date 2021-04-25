// jQuery File Tree Plugin
//
// Version 1.01 http://labs.abeautifulsite.net/archived/jquery-fileTree/demo/
//
// Usage: $('.fileTreeDemo').fileTree( options, callback )
//
// Options:
//           rootFolderId   - root folder Id
//           keyword        - file name key word
//           folderEvent    - event to trigger expand/collapse; default = click
//           expandSpeed    - default = 500 (ms); use -1 for no animation
//           collapseSpeed  - default = 500 (ms); use -1 for no animation
//           expandEasing   - easing function to use on expand (optional)
//           collapseEasing - easing function to use on collapse (optional)
//           multiFolder    - whether or not to limit the browser to one subfolder at a time
//           loadMessage    - Message to display while initial tree loads (can be HTML)
//

if(jQuery) (function($){

	var HOST_PORT = window.location.href;

	$.extend($.fn, {
		fileTree: function(o, h) {
			// Defaults
			if( !o ) var o = {};
			if( o.rootFolderId == undefined) o.rootFolderId = '';
			if( o.keyword == undefined ) o.keyword = '';
			if( o.folderEvent == undefined ) o.folderEvent = 'click';
			if( o.expandSpeed == undefined ) o.expandSpeed= 500;
			if( o.collapseSpeed == undefined ) o.collapseSpeed= 500;
			if( o.expandEasing == undefined ) o.expandEasing = null;
			if( o.collapseEasing == undefined ) o.collapseEasing = null;
			if( o.multiFolder == undefined ) o.multiFolder = true;
			if( o.loadMessage == undefined ) o.loadMessage = 'Loading...';
			
			$(this).each( function() {

				function showTree(c, folderId) {
					$(c).addClass('wait');
					$(".jqueryFileTree.start").remove();

					var url = '';
					if (o.keyword === '') {
						url = HOST_PORT + "/getAllFiles/" + folderId;
					} else {
						url = HOST_PORT + "/searchFiles/" + o.keyword;
					}

					$.getJSON(url, function(json) {
						if (json.retCode === 1 && json.data !== null && json.data.length > 0) {
							var thriftDatas = json.data;
							var r = '<ul class="jqueryFileTree" style="display: none;">';
							thriftDatas.forEach(function(data){
								if (data.directory) {
									r += '<li class="directory collapsed"><a href="#" rel=' + data.id  + '>' + data.name + '</a></li>';
								} else {
									var e = data.name.split('.')[1];
									r += '<li class="file ext_' + e + '"><a href="#" rel='+ JSON.stringify(data) + '>' + data.name + '</a></li>';
								}
							});
							r += '</ul>';

							$(c).find('.start').html('');
							$(c).removeClass('wait').append(r);
							if( o.rootFolderId == '' ) $(c).find('UL:hidden').show(); else $(c).find('UL:hidden').slideDown({ duration: o.expandSpeed, easing: o.expandEasing });
							bindTree(c);
						} else {
							$(c).removeClass('wait');
							$(c).removeClass('expanded').addClass('collapsed');
						}
					});
				}
				
				function bindTree(t) {
					$(t).find('LI A').bind(o.folderEvent, function() {
						if( $(this).parent().hasClass('directory') ) {
							if( $(this).parent().hasClass('collapsed') ) {
								// Expand
								if( !o.multiFolder ) {
									$(this).parent().parent().find('UL').slideUp({ duration: o.collapseSpeed, easing: o.collapseEasing });
									$(this).parent().parent().find('LI.directory').removeClass('expanded').addClass('collapsed');
								}
								$(this).parent().find('UL').remove(); // cleanup
								showTree( $(this).parent(), $(this).attr('rel'));
								$(this).parent().removeClass('collapsed').addClass('expanded');
							} else {
								// Collapse
								$(this).parent().find('UL').slideUp({ duration: o.collapseSpeed, easing: o.collapseEasing });
								$(this).parent().removeClass('expanded').addClass('collapsed');
							}
						} else {
							h(JSON.parse($(this).attr('rel')));
						}
						return false;
					});
					// Prevent A from triggering the # on non-click events
					if( o.folderEvent.toLowerCase != 'click' ) $(t).find('LI A').bind('click', function() { return false; });
				}
				// Loading message
				$(this).html('<ul class="jqueryFileTree start"><li class="wait">' + o.loadMessage + '<li></ul>');
				// Get the initial file list
				showTree( $(this), '' );
			});
		}
	});
	
})(jQuery);