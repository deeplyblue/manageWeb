angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","limitToFilter","filterFilter","dateFilter","myConstant","DateCalculateService",function(e,t,a,n,c,r,i,o,u,l,g,d){function s(){var e=document.getElementById("queryForm");m.url=angular.element(e).prop("action"),m.pagination.queryBean=m.queryBean;var a=m.pagination;t.post(m.url,a,config.jsonHeader).then(function(e){var t=e.data;t.success?(m.pagination=t.object,m.sumObject=t.sumObject,r.clearChecked()):alert(t.msg)})}function f(){m.queryBean={}}function h(e){t.post(config.ctx+"/mchntplatform/mchntRefund/audit",{id:e},config.jsonHeader).then(function(e){var t=e.data;t.success&&(alert("审核成功"),m.queryDetail())},function(e){alert("审核失败成功"),m.queryDetail()})}var m=e.vm={};m.constant=g,m.pagination={pageSize:10,pageNum:1},m.cached={MERCHANT_CODE:{},MCHNT_TRANS_CODE:{},REFUND_STATUS:{}},i.initCache(m.cached),m.getCache=function(e){return i.getCache(e)},m.getObj=function(e,c){return t.post(config.ctx+"/base/cache/getAll",a({cacheKey:e})).then(function(e){return o(u(e.data.object,c),6)},function(e){n.error("获取数据%s失败",cacheKey)})},m.queryDetail=s,m.resetForm=f;var p={beginDate:d.getToday(),endDate:d.getToday()};m.queryBean=p,m.doAudit=h}]);