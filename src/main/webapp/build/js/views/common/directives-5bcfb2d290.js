angular.module("myApp").directive("commonModalForm",function(){return{replace:!0,transclude:!0,template:'<form name="commonModalForm" novalidate w5c-form-validate autocomplete="off"><div ng-transclude=""></div><div class="modal-footer" style="height: 40px"><button class="btn btn-primary" type="button" w5c-form-submit="save()">save</button><button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button></div></form>'}}).directive("myColor",function(){return{restrict:"A",scope:{myColor:"=",myColorTrue:"@",myColorCode:"@",myColorType:"@"},link:function(e,t,a){var n=$(t);e.$watch("myColor",function(t,a){t+""==e.myColorTrue?e.myColorType&&"bg"!=e.myColorType?"font"==e.myColorType?n.css("color",e.myColorCode):console.error("myColorType:",e.myColorType," didnt support!"):n.css("background-color",e.myColorCode):n.css("background-color","white")})}}}).directive("tableDetail",function(){return{replace:!0,transclude:!0,template:'<table class="table table-bordered table-striped table-condensed myTable" style = "word-wrap:break-word;word-break:break-all;" ng-transclude></table> '}}).directive("tableForm",function(){return{replace:!0,transclude:!0,template:'<table class="table table-bordered table-condensed myTable" ng-transclude></table> '}}).directive("checkboxAll",function(){return{restrict:"A",scope:{model:"=ngModel",checkboxAll:"="},link:function(e,t,a){e.$watch("model",function(t,a,n){angular.isArray(e.checkboxAll)&&e.checkboxAll.forEach(function(e){e._checked=t})})}}}).directive("downFile",["$http","$httpParamSerializerJQLike","dateFilter",function(e,t,a){return{restrict:"A",scope:{params:"=",downFileType:"@",downFile:"@",downCfg:"="},link:function(n,r,o){var l=$(r);l.on("click",function(r){l.prop("disabled",!0),r.preventDefault();var i=angular.copy(n.params);if(n.downCfg){if("jqLike"==n.downCfg.contentType){var s=n.downCfg.date;for(var d in s)i[d]=a(i[d],s[d])}}else n.downCfg={contentType:"json"};var c="json"==n.downCfg.contentType?config.downHeaderJson:config.downHeaderJqLike,i="json"==n.downCfg.contentType?i:t(i);e.post(o.downFile,i,c).success(function(e,t,a){l.prop("disabled",!1);var n;switch(o.downFileType){case"xlsx":n="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";break;case"xls":n="application/vnd.ms-excel;charset=utf-8";break;default:n="application/vnd.ms-excel;charset=utf-8"}if(!n)throw"无效类型";saveAs(new Blob([e],{type:n}),decodeURI(a()["x-filename"]))}).error(function(e,t){alert(e),l.prop("disabled",!1)})})}}}]).directive("dateTime",function(){return{restrict:"A",scope:{model:"=ngModel"},link:function(e,t,a,n){$(t).datetimepicker({format:"yyyymmdd-hh:ii",autoclose:!0,todayBtn:!0,minuteStep:5,language:"zh-CN"}).on("changeDate",function(t){e.model=t.date,e.$apply()})}}}).directive("stTable",function(e){return{restrict:"A",scope:{stTable:"=",stTableOptions:"="},link:function(t,a,n){t.$watch("stTable",function(r,o){if(a.html(""),e.debug("run in again"),!(r&&r.length>0))return void e.info("aaaaaaaaaa");e.info(n.stTable);for(var l=document.createElement("thead"),i=document.createElement("tr"),s=0;s<t.stTableOptions.length;s++){var d=document.createElement("td");d.innerText=t.stTableOptions[s].headName,i.appendChild(d)}l.appendChild(i),a.append(l);var c=[];for(var s in t.stTableOptions){var p=t.stTableOptions[s].name;e.debug(p);for(var m in t.stTable){var u,v=t.stTable[m];if(c[m]?u=c[m]:(u=[],c.push(u)),0==m){var b=t.stTableOptions[s].mergeAsCol,g={value:v[p],rowSpan:u[b]?u[b].rowSpan:1,mergedBy:0};u[s]=g}else{var f=c[m-1];if(void 0!=t.stTableOptions[s].mergeAsCol){var b=t.stTableOptions[s].mergeAsCol;if(u[b].rowSpan){var g={value:v[p],rowSpan:u[b].rowSpan,mergedBy:u[b].mergedBy};u[s]=g}else{var g={value:v[p],mergedBy:f[s].mergedBy};u[s]=g}}else if(t.stTableOptions[s].notMerge||f[s].value!=v[p]||0!=s&&f[s-1].mergedBy!=u[s-1].mergedBy){var g={value:v[p],rowSpan:1,mergedBy:m};u[s]=g}else{var g={value:v[p],mergedBy:f[s].mergedBy};u[s]=g,c[g.mergedBy][s].rowSpan+=1}}}}var h=document.createElement("tbody");for(s in c){var i=document.createElement("tr");for(m in c[s]){var y=c[s][m];if(y.rowSpan){var d=document.createElement("td");if(d.setAttribute("rowSpan",y.rowSpan),e.debug(t.stTableOptions[m].mappingCache),t.stTableOptions[m].mappingCache){var T=t.stTableOptions[m].mappingCache[y.value];T?d.innerText=T:d.innerText=""}else d.innerText=y.value;if(t.stTableOptions[m].highlightNE){var C=t.stTableOptions[m].highlightNE,w=c[s][C[0]].value,O=c[s][C[1]].value;w!=O&&(d.style.color=C[2])}i.appendChild(d)}}h.appendChild(i)}a.append(h)})}}}).directive("stTableData",function(e,t){return{restrict:"E",scope:{stData:"=",stCaches:"="},controller:["$scope",function(e){var t=e.stTableOptions=[];this.addColumn=function(e){var a={name:e.stField,headName:e.stHead,mappingCache:e.stMapping,notMerge:e.stNoMerge,mergeAsCol:e.stMergeAs,highlightNE:e.stHighlightNe,fieldType:e.stFieldType};t.unshift(a)},this.addHead=function(t){e.stHead=t}}],link:function(a,n,r){a.$watch("stData",function(o,l){n.html("");var i=document.createElement("table");if(i.setAttribute("class","table table-bordered table-striped table-condensed myTable"),n.append(i),i=angular.element(i),e.debug("run in again"),!(o&&o.length>0))return void e.info("aaaaaaaaaa");if(e.info(r.stData),a.stHead)i.append(a.stHead);else{for(var s=document.createElement("thead"),d=document.createElement("tr"),c=0;c<a.stTableOptions.length;c++){var p=document.createElement("td");p.innerText=a.stTableOptions[c].headName,d.appendChild(p)}s.appendChild(d),i.append(s)}var m=[];for(var c in a.stTableOptions){a.stTableOptions[c].mappingCache=a.stCaches[a.stTableOptions[c].mappingCache];var u=a.stTableOptions[c].name;e.debug(u);for(var v in a.stData){var b=a.stData[v],g=a.stTableOptions[c].fieldType;g&&(b[u]=t(g)(b[u],"￥"));var f;if(m[v]?f=m[v]:(f=[],m.push(f)),0==v){var h=a.stTableOptions[c].mergeAsCol,y={value:b[u],rowSpan:f[h]?f[h].rowSpan:1,mergedBy:0};f[c]=y}else{var T=m[v-1];if(void 0!=a.stTableOptions[c].mergeAsCol){var h=a.stTableOptions[c].mergeAsCol;if(f[h].rowSpan){var y={value:b[u],rowSpan:f[h].rowSpan,mergedBy:f[h].mergedBy};f[c]=y}else{var y={value:b[u],mergedBy:T[c].mergedBy};f[c]=y}}else if(a.stTableOptions[c].notMerge||T[c].value!=b[u]||0!=c&&T[c-1].mergedBy!=f[c-1].mergedBy){var y={value:b[u],rowSpan:1,mergedBy:v};f[c]=y}else{var y={value:b[u],mergedBy:T[c].mergedBy};f[c]=y,m[y.mergedBy][c].rowSpan+=1}}}}for(c in m){var C=m[c][0],w=m[c][1];"合计"==C.value&&"小计"==w.value?(C.colSpan=2,w.rowSpan=void 0):"小计"==w.value&&(C.rowSpan=void 0,w.colSpan=2)}e.debug(m);var O=document.createElement("tbody");for(c in m){var d=document.createElement("tr");for(v in m[c]){var S=m[c][v];if(S.rowSpan){var p=document.createElement("td");if(p.setAttribute("rowSpan",S.rowSpan),S.colSpan&&p.setAttribute("colSpan",S.colSpan),a.stTableOptions[v].mappingCache){var k=a.stTableOptions[v].mappingCache[S.value];k?p.innerText=k:p.innerText=S.value}else p.innerText=S.value;if(a.stTableOptions[v].highlightNE){var A=a.stTableOptions[v].highlightNE,B=m[c][A[0]].value,E=m[c][A[1]].value;B!=E&&(p.style.color=A[2])}d.appendChild(p)}}O.appendChild(d)}i.append(O)})}}}).directive("stTableHead",function(){return{restrict:"E",require:"^^stTableData",scope:{},link:function(e,t,a,n){n.addHead(t.children().html())}}}).directive("stTableColumn",function(){return{restrict:"E",require:"^^stTableData",scope:{stHead:"@",stField:"@",stFieldType:"@",stMapping:"@",stNoMerge:"@",stMergeAs:"@",stHighlightNe:"@"},link:function(e,t,a,n){n.addColumn(e)}}});