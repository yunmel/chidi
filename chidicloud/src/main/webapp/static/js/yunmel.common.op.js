;(function($){
	var cuslayer = function(params){
		var defaults = {
			mode:'normal',
			mainContainerSelector:'#content-wrapper',
			type:1, //0：信息框（默认），1：页面层，2：iframe层，3：加载层，4：tppm层。
			title:false,
			shade: [0.5, '#000'], //[遮罩透明度, 遮罩颜色]
			border:[3, 0.5, '#666'],
			closeBtn:[0, true],
			url:undefined, //请求回来弹窗的url
			data:{}, //请求弹窗携带的参数
			maxmin:true, //是否输出窗口最小化/全屏/还原按钮。 
			width:'0',
			height:'0',
			btns:2,
			btn:['确 定','取 消'],
			msg:'',
			reloadurl:false //是否url刷新,默认false当前右侧刷新
		};
		
		params = $.extend(defaults, params);
		
		var mode = params.mode;
		if(undefined != params.closebtn){
			params.closeBtn = params.closebtn;
		}
		
		if (mode == 'del' || mode == 'delete' || mode == 'page'
			|| mode == 'delSelect' || mode == 'detail' || mode=="batchOp") {
			if(undefined == params.url) {
				alert("请求url未填写");
				return false;
			}
		}
		
		if(mode =="batchOp"){
			var rows = $('#' + params.table).bootstrapTable("getAllSelections");
			if (rows.length <= 0) {
				msg.error('请选择需要%s的数据',params.op);
				return;
			} else {
				var ids = [];
				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				if(params.data == undefined || params.data == null){
					params.data = [];
				}
				params.data['ids'] = ids;
			}
		}
		
		if(mode == 'detail'){
			$.ajax({
				url:params.url,
				data:params.data,
				type:'post',
				dataType:'html'
			}).done(function(data){
//				$(".crud-detail .detail-head .title").html(params.title || '详细信息');
//				$(".crud-detail .detail-inner").removeClass("detail-hide").addClass("detail-show")
//				.find(".detail-body").html(data);
//				
//				var h = yh.getCenterHeight() - $(".crud-detail .detail-detail-head").height();
//				var detail_scroll = $(".crud-detail .detail-body .wrapper-content").yunmel_scroll({
//					size:h - 50
//				})
//				window.onresize = function(){
//					detail_scroll.data("yunmel_scroll").update({
//						size: yh.getCenterHeight() - $(".crud-detail .detail-detail-head").height()- 50
//					})
//				}
				//自定页
				layer.open({
				  type: 1,
				  skin: 'layui-layer-demo', //样式类名
				  closeBtn: 0, //不显示关闭按钮
				  shift: 2,
				  shadeClose: true, //开启遮罩关闭
				  content: data
				});
			});
		}else if(mode == "batchOp" || mode == 'sop'){
			var html = [];
			html.push('<div class="row"><div class="col-sm-1">',
							'<i class="fa fa-info-circle bigger-300 orange"></i>',
					  '</div>',
					  '<div class="col-sm-11">',
							'<p style="height:35;line-height:35px;">',
							mode == "batchOp" ?
							yh.sprintf("您确定要%s选中的%s条%s吗？",params.op,params.data['ids'].length,params.name):
							yh.sprintf("您确定要%s选中的%s吗？",params.op,params.name),
							'</p>',
					  '</div></div>');
			BootstrapDialog.confirm({
				message:html.join(" "),
				title:yh.sprintf("%s站内信",params.op),
				callback:function(result){
					if(result){
						$.ajax({
							url:params.url,
							data:params.data,
							type:'post'
						}).done(function(data){
		        			if(data>0) {
		        				if(params.success == undefined){
		        					//msg.success("%s成功!",params.op);
	            					if(params.reloadurl){
	            						location.reload();
	            					}else{
	            						if(params.callback != undefined) {
	            							if(typeof params.callback === "string"){
	            								eval(params.callback)
	            							}else{
	            								params.callback();
	            							}
	            						}else{
	            							$curmenu.trigger('click');
	            						}
	            					}
		        				}else{
		        					params.success();
		        				}
		        			}else if(data == 0){
		        				msg.error(yh.sprintf('%s失败！',params.op));
		        			}else if(data <= -1){
		        				msg.warning(yh.sprintf('%s失败，数据正在使用中...',params.op));
		        			}
		        		}).fail(function(error){
		        			msg.error(yh.sprintf('%s失败,网络错误！',params.op));
		        		});
					}
				}
			});
		}else if(mode == 'list-mng'){//列表方式管理
			$(params.mainContainerSelector).html(yh.loadHtml(params.url));
		}else if(mode == 'add' || mode == 'edit'){
			BootstrapDialog.show({
			 	title:params.title,
			 	size:params.size || 'size-normal',
	        	message: $('<div></div>').load(params.url,params.data),
	        	 buttons: [{
	 		        id: 'btn-save',   
	 		        label: '保存',
	 		        cssClass: 'btn-primary', 
	 		        autospin: false,
	 		        action: function(dialogRef){    
	 		        	saveBtnClick();
	 		        }
	 		    },{
	 		        id: 'btn-cancel',   
	 		        label: '取消',
	 		        cssClass: 'btn-default', 
	 		        autospin: false,
	 		        action: function(dialogRef){    
	 		            dialogRef.close();
	 		        }
	 		    }]
			});
		}else if(mode == 'normal'){//弹出一个框，仅有关闭按钮
			BootstrapDialog.show({
			 	title:params.title,
			 	size:params.size || 'size-normal',
	        	message: $('<div></div>').load(params.url,params.data),
	        	 buttons: [{
	 		        id: 'btn-cancel',   
	 		        label: '关闭',
	 		        cssClass: 'btn-default', 
	 		        autospin: false,
	 		        action: function(dialogRef){    
	 		            dialogRef.close();
	 		        }
	 		    }]
			});
		}
	};
	$.cuslayer = cuslayer;
})(jQuery);