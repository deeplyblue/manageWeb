angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","dateFilter","myConstant","DateCalculateService",function(e,t,a,n,r,o,c,i,y,u){function d(){var e=document.getElementById("queryForm");g.url=angular.element(e).prop("action"),g.postData=g.queryBean,g.postData.pageSize=g.pagination.pageSize,g.postData.pageNum=g.pagination.pageNum,g.postData.sDateStart=i(g.queryBean.oDateStart,"yyyyMMdd"),g.postData.sDateEnd=i(g.queryBean.oDateEnd,"yyyyMMdd"),t.post(g.url,a(g.postData)).then(function(e){var t=e.data;t.success?(g.pagination=t.object,g.sumObject=t.sumObject,o.clearChecked()):alert(t.msg)})}function p(){g.queryBean={oDateStart:u.getYesterday(),oDateEnd:u.getToday()}}var g=e.vm={};g.constant=y,g.downCfg={contentType:"jqLike",date:{oDateStart:"yyyyMMdd",oDateEnd:"yyyyMMdd"}},g.pagination={pageSize:10,pageNum:1},g.sumObject={},g.queryBean={oDateStart:u.getYesterday(),oDateEnd:u.getToday()},g.queryDetail=d,g.resetForm=p,g.cached={MERCHANT_CODE:{}},c.initCache(g.cached),g.getCache=function(e){return c.getCache(e)},g.getObj=function(e,r){return t.post(config.ctx+"/base/cache/getAll",a({cacheKey:e})).then(function(e){return limitToFilter(filterFilter(e.data.object,r),6)},function(e){n.error("获取数据%s失败",cacheKey)})}}]);