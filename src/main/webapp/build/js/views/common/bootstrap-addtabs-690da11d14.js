$.fn.addtabs=function(t){obj=$(this),Addtabs.options=$.extend({content:"",close:!0,monitor:"body",iframeUse:!0,iframeHeight:$(document).height()-50,method:"init",callback:function(){}},t||{}),$(Addtabs.options.monitor).on("click","[data-addtab]",function(){Addtabs.add({id:$(this).attr("data-addtab"),title:$(this).attr("title")?$(this).attr("title"):$(this).html(),content:Addtabs.options.content?Addtabs.options.content:$(this).attr("content"),url:$(this).attr("url"),ajax:!!$(this).attr("ajax")})}),obj.on("click",".close-tab",function(){var t=$(this).prev("a").attr("aria-controls");Addtabs.close(t)}),obj.on("contextmenu","li[role=presentation]",function(){var t=$(this).children("a").attr("aria-controls");return Addtabs.pop(t,$(this)),!1}),obj.on("click","ul.rightMenu a[data-right=refresh]",function(){var t=$(this).parent("ul").attr("aria-controls").substring(4),a=$(this).parent("ul").attr("aria-url");Addtabs.add({id:t,url:a}),$("#popMenu").fadeOut()}),obj.on("click","ul.rightMenu a[data-right=remove]",function(){var t=$(this).parent("ul").attr("aria-controls");Addtabs.close(t),Addtabs.drop(),$("#popMenu").fadeOut()}),obj.on("click","ul.rightMenu a[data-right=remove-circle]",function(){var t=$(this).parent("ul").attr("aria-controls");obj.children("ul.nav").find("li").each(function(){var a=$(this).attr("id");a&&a!="tab_"+t&&Addtabs.close($(this).children("a").attr("aria-controls"))}),Addtabs.drop(),$("#popMenu").fadeOut()}),obj.on("click","ul.rightMenu a[data-right=remove-left]",function(){var t=$(this).parent("ul").attr("aria-controls");$("#tab_"+t).prevUntil().each(function(){var a=$(this).attr("id");a&&a!="tab_"+t&&Addtabs.close($(this).children("a").attr("aria-controls"))}),Addtabs.drop(),$("#popMenu").fadeOut()}),obj.on("click","ul.rightMenu a[data-right=remove-right]",function(){var t=$(this).parent("ul").attr("aria-controls");$("#tab_"+t).nextUntil().each(function(){var a=$(this).attr("id");a&&a!="tab_"+t&&Addtabs.close($(this).children("a").attr("aria-controls"))}),Addtabs.drop(),$("#popMenu").fadeOut()}),obj.on("mouseover",'li[role = "presentation"]',function(){$(this).find(".close-tab").show()}),obj.on("mouseleave",'li[role = "presentation"]',function(){$(this).find(".close-tab").hide()}),$(window).resize(function(){obj.find("iframe").attr("height",Addtabs.options.iframeHeight),Addtabs.drop()})},window.Addtabs={options:{},add:function(t){var a="tab_"+t.id;if($(window.parent.document).find('li[role = "presentation"].active').removeClass("active"),$(window.parent.document).find('div[role = "tabpanel"].active').removeClass("active"),$(window.parent.document).find("#"+a)[0]){var i=$("#"+a);i.html("")}else{var e=$("<li>",{role:"presentation",id:"tab_"+a,"aria-url":t.url}).append($("<a>",{href:"#"+a,"aria-controls":a,role:"tab","data-toggle":"tab"}).html(t.title));Addtabs.options.close&&e.append($("<i>",{class:"close-tab glyphicon glyphicon-remove"}));var i=$("<div>",{class:"tab-pane",id:a,role:"tabpanel"});obj.children(".nav-tabs").append(e),obj.children(".tab-content").append(i)}t.content?i.append(t.content):Addtabs.options.iframeUse&&!t.ajax?i.append($("<iframe>",{class:"iframeClass",height:Addtabs.options.iframeHeight,frameborder:"no",border:"0",src:t.url})):$.get(t.url,function(t){i.append(t)}),$(window.parent.document).find("#tab_"+a).addClass("active"),$(window.parent.document).find("#"+a).addClass("active"),Addtabs.drop()},pop:function(t,a){$(window.parent.document).find("body").find("#popMenu").remove();var i=$("<ul>",{"aria-controls":t,class:"rightMenu list-group",id:"popMenu","aria-url":a.attr("url")}).append('<a href="javascript:void(0);" class="list-group-item" data-right="refresh"><i class="glyphicon glyphicon-refresh"></i> 刷新此标签</a><a href="javascript:void(0);" class="list-group-item" data-right="remove"><i class="glyphicon glyphicon-remove"></i> 关闭此标签</a><a href="javascript:void(0);" class="list-group-item" data-right="remove-circle"><i class="glyphicon glyphicon-remove-circle"></i> 关闭其他标签</a><a href="javascript:void(0);" class="list-group-item" data-right="remove-left"><i class="glyphicon glyphicon-chevron-left"></i> 关闭左侧标签</a><a href="javascript:void(0);" class="list-group-item" data-right="remove-right"><i class="glyphicon glyphicon-chevron-right"></i> 关闭右侧标签</a>');i.css({top:a.context.offsetHeight-10+"px",left:a.context.offsetLeft+20+"px"}),i.appendTo(obj).fadeIn("slow"),i.mouseleave(function(){$(this).fadeOut("slow")})},close:function(t){obj.find("li.active").attr("id")==="tab_"+t&&($(window.parent.document).find("#tab_"+t).prev().addClass("active"),$(window.parent.document).find("#"+t).prev().addClass("active")),$(window.parent.document).find("#tab_"+t).remove(),$(window.parent.document).find("#"+t).remove(),Addtabs.drop(),Addtabs.options.callback()},closeAll:function(){$.each(obj.find("li[id]"),function(){var t=$(this).children("a").attr("aria-controls");$("#tab_"+t).remove(),$("#"+t).remove()}),obj.find('li[role = "presentation"]').first().addClass("active");var t=obj.find('li[role = "presentation"]').first().children("a").attr("aria-controls");$(window.parent.document).find("#"+t).addClass("active"),Addtabs.drop()},drop:function(){element=obj.find(".nav-tabs");var t=$("<li>",{class:"dropdown pull-right hide tabdrop tab-drop"}).append($("<a>",{class:"dropdown-toggle","data-toggle":"dropdown",href:"#"}).append($("<i>",{class:"glyphicon glyphicon-align-justify"})).append($("<b>",{class:"caret"}))).append($("<ul>",{class:"dropdown-menu"}));$(window.parent.document).find(".tabdrop").html()?t=element.find(".tabdrop"):t.prependTo(element),element.parent().is(".tabs-below")&&t.addClass("dropup");var a=0;element.append(t.find("li")).find(">li").not(".tabdrop").each(function(){(this.offsetTop>0||element.width()-$(this).position().left-$(this).width()<83)&&(t.find("ul").append($(this)),a++)}),a>0?(t.removeClass("hide"),1==t.find(".active").length?t.addClass("active"):t.removeClass("active")):t.addClass("hide")}};