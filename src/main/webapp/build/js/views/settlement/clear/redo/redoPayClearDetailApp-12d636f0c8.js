angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","limitToFilter","filterFilter","dateFilter","myConstant","DateCalculateService",function(e,t,a,r,n,c,o,i,l,u,s,p){function y(){var e=document.getElementById("queryForm");if(d.url=angular.element(e).prop("action"),confirm("是否确认操作？"))return d.postData=angular.copy(d.queryBean),d.postData.settleDate=u(d.postData.settleDate,"yyyyMMdd"),null==d.postData.companyCode||""==d.postData.companyCode?void alert("机构不能为空！"):void t.post(d.url,a(d.postData)).then(function(e){var t=e.data;t.success?(d.pagination=t.object,alert(t.msg)):alert(t.msg)})}function m(){d.queryBean={}}var d=e.vm={};d.constant=s,d.queryBean={},d.queryBean.settleDate=p.getYesterday(),d.cached={COMANY_CODE:{}},o.initCache(d.cached),d.getCache=function(e){return o.getCache(e)},d.getObj=function(e,n){return t.post(config.ctx+"/base/cache/getAll",a({cacheKey:e})).then(function(e){return i(l(e.data.object,n),6)},function(e){r.error("获取数据%s失败",cacheKey)})},d.queryDetail=y,d.resetForm=m}]);