angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","dateFilter","myConstant","DateCalculateService",function(e,t,a,n,r,o,c,l,i,y){function d(){var e=document.getElementById("queryForm");m.url=angular.element(e).prop("action"),m.postData=m.queryBean,m.postData.pageSize=m.pagination.pageSize,m.postData.pageNum=m.pagination.pageNum,m.postData.sDateStart=l(m.queryBean.oDateStart,"yyyyMMdd"),m.postData.sDateEnd=l(m.queryBean.oDateEnd,"yyyyMMdd"),m.postData.sSettleDateStart=l(m.queryBean.oSettleDateStart,"yyyyMMdd"),m.postData.sSettleDateEnd=l(m.queryBean.oSettleDateEnd,"yyyyMMdd"),t.post(m.url,a(m.postData)).then(function(e){var t=e.data;t.success?(m.pagination=t.object,m.sumObject=t.sumObject,o.clearChecked()):alert(t.msg)})}function s(){m.queryBean={oDateStart:y.getYesterday(),oDateEnd:y.getToday()}}function u(e,n){var o;t.post(config.ctx+"/settlementFront/transDetailOne/searchDetail",a({platformCode:e,innerPayTransNo:n})).then(function(t){var a=t.data;a.success?(o=a.object,r({modalTitle:"交易明细查询",modalBody:config.ctx+"/settlementFront/transDetailOne/toSearchDetail",url:"searchDetail",item:{results:o,platformCode:e,innerPayTransNo:n},cached:m.cached},function(e){},"QueryDetailModalInstanceCtrl","myModalNoSave.html")):alert(a.msg)})}var m=e.vm={};m.constant=i,m.downCfg={contentType:"jqLike",date:{oDateStart:"yyyyMMdd",oDateEnd:"yyyyMMdd",oSettleDateStart:"yyyyMMdd",oSettleDateEnd:"yyyyMMdd"}},m.pagination={pageSize:10,pageNum:1},m.queryBean={oDateStart:y.getYesterday(),oDateEnd:y.getToday()},m.validateOptions={blurTrig:!0,showError:function(e,t){},removeError:!0},m.queryDetail=d,m.resetForm=s,m.queryTransDetail=u,m.cached={BANK_INFO:{},COMANY_CODE:{},MERCHANT_CODE:{},SEETLE_FLAG:{}},c.initCache(m.cached),m.getCache=function(e){return c.getCache(e)},m.getObj=function(e,r){return t.post(config.ctx+"/base/cache/getAll",a({cacheKey:e})).then(function(e){return limitToFilter(filterFilter(e.data.object,r),6)},function(e){n.error("获取数据%s失败",cacheKey)})}}]),angular.module("myApp").controller("QueryDetailModalInstanceCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike",function(e,t,a,n,r){var o=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};o=e.vm=a,e.cancel=function(){t.dismiss("cancel")}}]);