angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","limitToFilter","filterFilter","dateFilter","myConstant","growl",function(e,t,r,n,a,c,o,i,u,l,s,g){function m(){var e=document.getElementById("queryForm");f.url=angular.element(e).prop("action");var r=angular.copy(f.pagination);r.list=null,r.queryBean=f.queryBean,t.post(f.url,r,config.jsonHeader).then(function(e){var t=e.data;t.success?(f.pagination=t.object,f.sumObject=t.sumObject):alert(t.msg)},function(e){alert("操作失败")})}function p(){f.queryBean={}}function h(){}var f=e.vm={};f.constant=s,f.pagination={pageSize:10,pageNum:1},f.sumObject={},f.queryBean={},f.validateOptions={blurTrig:!0,showError:function(e,t){g.addErrorMessage(t)},removeError:!0},f.cached={MERCHANT_CODE:{},COMANY_CODE:{},CASH_TRANS_FLAG:{}},o.initCache(f.cached),f.getCache=function(e){return o.getCache(e)},f.getObj=function(e,a){return t.post(config.ctx+"/base/cache/getAll",r({cacheKey:e})).then(function(e){return i(u(e.data.object,a),6)},function(e){n.error("获取数据%s失败",cacheKey)})},f.queryDetail=m,f.resetForm=p,f.tableFilter=h}]);