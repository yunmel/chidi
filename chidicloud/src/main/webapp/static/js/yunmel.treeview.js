/**
 <b>Treeview</b>. A wrapper for FuelUX treeview element.
 It's just a wrapper so you still need to include FuelUX treeview script first.
*/
(function($ , undefined) {

	$.fn.yunmelTree = $.fn.yunmel_tree = function(options) {
		var $options = {
			'multiSelect': false,
			'cacheItems': true,
			'selectable': false,
			'folderSelect':true,
			'open-icon': 'yunmel-icon tree-minus',
			'close-icon': 'yunmel-icon tree-plus',
			'selected-icon': 'yunmel-icon fa fa-check',
			'unselected-icon': 'yunmel-icon fa fa-times',
			'data':[],
			'dataSource': undefined,
			'loadingHTML' : '<div class="tree-loading"><i class="yunmel-icon fa fa-refresh fa-spin blue"></i></div>'
		}
		
		$options = $.extend({}, $options, options)
		if($options.dataSource == undefined){
			$options.dataSource = function(options ,callback){
				var $data = null;
				console.log(options);
				if("children" in options){
		             $data = options.children;
		         }
		         else {
		        	 $data = $options.data;//the root tree
		             callback({ data: $data });
		             return;
		         }
		        if($data != null)
		            setTimeout(function(){callback({ data: $data });} , parseInt(Math.random() * 200) + 100);
			}
		}
		
		
		this.each(function() {
			var $this = $(this);
			$this.addClass('tree').attr('role', 'tree');
			if($.trim($this.html()).length == 0){
				$this.html(
				'<li class="tree-branch hide" data-template="treebranch" role="treeitem" aria-expanded="false">\
					<div class="tree-branch-header">\
						<span class="tree-branch-name">\
							<i class="icon-folder '+$options['close-icon']+'"></i>\
							<span class="tree-label"></span>\
						</span>\
					</div>\
					<ul class="tree-branch-children" role="group"></ul>\
					<div class="tree-loader" role="alert">'+$options['loadingHTML']+'</div>\
				</li>\
				<li class="tree-item hide" data-template="treeitem" role="treeitem">\
					<span class="tree-item-name">\
					  <i class="icon-item fa fa-file"></i>\
					  <span class="tree-label"></span>\
					</span>\
				</li>');
			}
			
			
			$this.addClass($options['selectable'] == true ? 'tree-selectable' : 'tree-unselectable');
			
			$this.tree($options);
		});
		
		return this;
	}
	
	

})(window.jQuery);
