angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","myConstant",function(e,t,a,n,r,c,u,o){function i(){var e=document.getElementById("queryForm"),n=e.getAttribute("action");m.postData=m.queryBean,m.postData.pageSize=m.pagination.pageSize,m.postData.pageNum=m.pagination.pageNum,t.post(n,a(m.postData)).then(function(e){var t=e.data;t.success?m.pagination=t.object:alert(t.msg)})}function l(){m.queryBean={}}function s(e,t){c.updateChecked(e,t),n.debug(t),n.debug(c.getChecked())}function T(e){var t=e;r({modalTitle:"查看明细",modalBody:"toDetail",url:"#",item:t,cached:{ACCOUNT_TYPE:m.cached.ACCOUNT_TYPE,TGB_CUSTOMER_STATUS:m.cached.TGB_CUSTOMER_STATUS,OPCIF_CERTIFICATE_TYPE:m.cached.OPCIF_CERTIFICATE_TYPE,REALNAME_LV:m.cached.REALNAME_LV,CUSTOMER_CRADE:m.cached.CUSTOMER_CRADE}},function(e){},"","myModalNoSave.html")}function f(e){if(confirm("是否确认此操作?")){var r=angular.copy(e);r.authTime=null,r.birthDatetime=null,r.certStartDate=null,r.certExpiryDate=null,t.post(config.ctx+"/cif/customerInfo/freeze",a(r)).then(function(e){var t=e.data;t.success?(alert("操作成功"),i()):alert(t.msg)},function(e){alert(e.statusText),n.debug("error")})}}function C(e){if(confirm("是否确认此操作?")){var r=angular.copy(e);r.authTime=null,r.birthDatetime=null,r.certStartDate=null,r.certExpiryDate=null,t.post(config.ctx+"/cif/customerInfo/defrosting",a(r)).then(function(e){var t=e.data;t.success?(alert("操作成功"),i()):alert(t.msg)},function(e){alert(e.statusText),n.debug("error")})}}function g(e){if(confirm("是否确认此操作?")){var r=angular.copy(e);r.authTime=null,r.birthDatetime=null,r.certStartDate=null,r.certExpiryDate=null,t.post(config.ctx+"/cif/customerInfo/unFreeze",a(r)).then(function(e){var t=e.data;t.success?(alert("操作成功"),m.queryDetail()):alert(t.msg)},function(e){alert(e.statusText),n.debug("error")})}}var m=e.vm={};m.constant=o,m.pagination={pageSize:10,pageNum:1},m.queryBean={},m.queryDetail=i,m.resetForm=l,m.updateChecked=s,m.queryCustomerDetail=T,m.freezeOperator=f,m.unFreezeOperator=g,m.defrosting=C,m.getCache=function(e){return u.getCache(e)},m.cached={ACCOUNT_TYPE:{},TGB_CUSTOMER_STATUS:{},OPCIF_CERTIFICATE_TYPE:{},REALNAME_LV:{},CUSTOMER_CRADE:{}},u.initCache(m.cached)}]);