angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","limitToFilter","filterFilter","myConstant","dateFilter",function(e,t,a,n,c,o,r,i,l,u,d){function s(){var e=document.getElementById("queryForm");T.url=angular.element(e).prop("action");var n=angular.copy(T.queryBean);n.pageSize=T.pagination.pageSize,n.pageNum=T.pagination.pageNum,t.post(T.url,a(n)).then(function(e){var t=e.data;t.success?(T.pagination=t.object,o.clearChecked()):alert(t.msg)})}function g(e,a){var c=angular.copy(e);c.cttStatus=T.cached.STATUS[a],t.post(config.ctx+"/merchant/contacts/updateItemEnableFlag",c,config.jsonHeader).then(function(t){var a=t.data;a.success?(e.cttStatus=c.cttStatus,alert("操作成功")):alert("操作失败")},function(e){alert(e.statusText),n.debug("error")})}function p(){T.queryBean={}}function m(e,t){o.updateChecked(e,t),n.debug(t),n.debug(o.getChecked())}function h(){c({modalTitle:"新增商户联系人",modalBody:"toAdd",url:"add",item:{},cached:{MERCHANT_CODE:r.getCache("MERCHANT_CODE"),CTT_TYPE:r.getCache("CTT_TYPE")}},function(){},"","myModalNoFooter.html")}function C(){if(1!=o.getChecked().length)return void alert("必须勾选一条记录！");var e=T.pagination.list.filter(function(e,t,a){if(e.id==o.getChecked()[0])return!0})[0];c({modalTitle:"修改商户联系人",modalBody:"toUpdate",url:"update",item:e,cached:{MERCHANT_CODE:r.getCache("MERCHANT_CODE"),CTT_TYPE:r.getCache("CTT_TYPE")}},function(){},"updateMerchantContactsModalInstanceCtrl","myModalNoFooter.html")}function f(){return 1!=o.getChecked().length?void alert("必须勾选一条记录！"):void t.post("delete",a({userId:o.getChecked()[0]})).then(function(e){var t=e.data;if(t.success){var a=T.pagination.list.filter(function(e,t,a){return e.userId==o.getChecked()[0]})[0];T.pagination.list.splice(T.pagination.list.indexOf(a),1),alert("操作成功")}else alert("操作失败")},function(e){alert(e.statusText),n.debug("error")})}var T=e.vm={};T.constant=u,T.pagination={pageSize:10,pageNum:1},T.queryBean={},T.cached={MERCHANT_CODE:{},CTT_TYPE:{}},function(){r.initCaches(T.cached,function(){T.cached.STATUS={0:"1",1:"0"}})}(),T.getCache=function(e){return r.getCache(e)},T.getObj=function(e,c){return t.post(config.ctx+"/base/cache/getAll",a({cacheKey:e})).then(function(e){return i(l(e.data.object,c),6)},function(e){n.error("获取数据%s失败",cacheKey)})},T.queryDetail=s,T.resetForm=p,T.updateChecked=m,T.addItem=h,T.updateItem=C,T.deleteItem=f,T.updateItemEnableFlag=g}]),angular.module("myApp").controller("updateMerchantContactsModalInstanceCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike","$log","dateFilter","myConstant",function(e,t,a,n,c,o,r,i){var l=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};l=e.vm=a,l.constant=i,e.save=function(){l.postData=angular.copy(l.item),l.postData.createTime=null,l.postData.lastUpdTime=null,o.debug(l.postData),n.post(l.url,c(l.postData)).then(function(e){e.data.success?(alert("操作成功"),t.close(l.item)):alert(e.data.msg)},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")}}]);