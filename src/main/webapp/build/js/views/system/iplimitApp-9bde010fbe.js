angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","limitToFilter","filterFilter","myConstant","growl",function(e,t,a,n,i,o,r,c,l,s,u){function p(){h.url=config.ctx+"/system/userIp/search",h.postData=h.queryBean,h.postData.pageSize=h.pagination.pageSize,h.postData.pageNum=h.pagination.pageNum,t.post(h.url,a(h.postData)).then(function(e){var t=e.data;t.success?(h.pagination=t.object,o.clearChecked()):alert(t.msg)})}function d(){h.queryBean={}}function g(){return 1!=o.getCheckedNew(h.pagination.list).length?void alert("必须勾选一条记录！"):void t.post("delete",a({id:o.getCheckedNew(h.pagination.list)[0].id})).then(function(e){var t=e.data;t.success?(h.pagination.list.splice(h.pagination.list.indexOf(o.getCheckedNew(h.pagination.list)[0]),1),alert("操作成功")):alert("操作失败")},function(e){alert(e.statusText),n.debug("error")})}function m(){i({modalTitle:"新增用户",modalBody:"toAdd",url:"add",item:{},cached:{IP_TYPE:r.getCache("IP_TYPE"),ENABLE_LIMITFLAG:r.getCache("ENABLE_LIMITFLAG")}},function(e){p()},"updateOrganizeInstanceCtrl","myModalNoFooter.html")}var h=e.vm={};h.constant=s,h.pagination={pageSize:10,pageNum:1},h.queryBean={},h.validateOptions={blurTrig:!0,showError:function(e,t){u.addErrorMessage(t)},removeError:!0},h.cached={TYPE_OPERATION:{},USER_STATUS:{},OPERATION_RESULT:{},MERCHANT_CODE:{},ENABLE_LIMITFLAG:{},IP_TYPE:{}},r.initCache(h.cached),h.getCache=function(e){return r.getCache(e)},h.getObj=function(e,i){return t.post(config.ctx+"/base/cache/getAll",a({cacheKey:e})).then(function(e){return c(l(e.data.object,i),6)},function(e){n.error("获取数据%s失败",cacheKey)})},h.queryDetail=p,h.resetForm=d,h.deleteItem=g,h.addItem=m}]),angular.module("myApp").controller("updateOrganizeInstanceCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike","$log","dateFilter","myConstant",function(e,t,a,n,i,o,r,c){var l=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};l=e.vm=a,l.constant=c,e.save=function(){l.postData=angular.copy(l.item),l.postData.ipBeginTime=r(l.postData.ipBeginTime,"yyyyMMdd"),l.postData.ipEndTime=r(l.postData.ipEndTime,"yyyyMMdd"),o.debug(l.postData),n.post(l.url,i(l.postData)).then(function(e){e.data.success?(alert("操作成功"),t.close(l.item)):alert(e.data.msg)},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")}}]);