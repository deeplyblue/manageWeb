angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","limitToFilter","filterFilter","myConstant","growl",function(e,t,a,n,o,l,i,r,c,s,u){function d(){T.url=config.ctx+"/system/user/search",T.postData=T.queryBean,T.postData.pageSize=T.pagination.pageSize,T.postData.pageNum=T.pagination.pageNum,t.post(T.url,a(T.postData)).then(function(e){var t=e.data;t.success?(T.pagination=t.object,l.clearChecked()):alert(t.msg)})}function g(){T.queryBean={}}function m(){o({modalTitle:"新增用户",modalBody:"toAdd",url:"add",item:{},cached:{USER_TYPE:i.getCache("USER_TYPE"),DPET_NAME:i.getCache("DPET_NAME"),MERCHANT_CODE:i.getCache("MERCHANT_CODE")}},function(e){T.pagination&&T.pagination.list&&T.pagination.list.push(e)},"","myModalNoFooter.html")}function p(){if(1!=l.getCheckedNew(T.pagination.list).length)return void alert("必须勾选一条记录！");var e=l.getCheckedNew(T.pagination.list)[0];e.userRoles=null,e.auditDate=null,e.createTime=null,e.lastLoginTime=null,e.lastUpdTime=null,e.pwdLastUpd=null,e.statusLastUpd=null,o({modalTitle:"修改用户",modalBody:"toUpdate",url:"update",item:e,cached:{USER_TYPE:i.getCache("USER_TYPE"),DPET_NAME:i.getCache("DPET_NAME"),MERCHANT_CODE:i.getCache("MERCHANT_CODE")}})}function h(){return 1!=l.getCheckedNew(T.pagination.list).length?void alert("必须勾选一条记录！"):void t.post("delete",a({userId:l.getCheckedNew(T.pagination.list)[0].userId})).then(function(e){var t=e.data;t.success?(T.pagination.list.splice(T.pagination.list.indexOf(l.getCheckedNew(T.pagination.list)[0]),1),alert("操作成功")):alert("操作失败")},function(e){alert(e.statusText),n.debug("error")})}function f(e){return 1!=l.getCheckedNew(T.pagination.list).length?void alert("必须勾选一条记录！"):void t.post("audit",a({userId:l.getCheckedNew(T.pagination.list)[0].userId,userStatus:e})).then(function(e){var t=e.data;t.success?(alert("操作成功"),d()):alert("操作失败")},function(e){alert(e.statusText),n.debug("error")})}function C(e){o({modalTitle:"角色分配",modalBody:"toAllocate",url:"allocate",item:{user:e,roles:e.userRoles.map(function(e){return e.roleId})},cached:{ROLE_INFO:i.getCache("ROLE_INFO")}},function(e){T.queryDetail()},"RoleAllocateModalInstanceCtrl")}function E(e){confirm("是否确认此操作?")&&t.post(config.ctx+"/system/user/resetPwd",{userId:e.userId},config.jsonHeader).then(function(e){var t=e.data;t.success?alert("操作成功"):alert("操作失败")},function(e){alert(e.statusText),n.debug("error")})}var T=e.vm={};T.constant=s,T.pagination={pageSize:10,pageNum:1},T.queryBean={},T.validateOptions={blurTrig:!0,showError:function(e,t){u.addErrorMessage(t)},removeError:!0},T.cached={USER_TYPE:{},ROLE_INFO:{},USER_STATUS:{},DPET_NAME:{},MERCHANT_CODE:{}},i.initCache(T.cached),T.getCache=function(e){return i.getCache(e)},T.getObj=function(e,o){return t.post(config.ctx+"/base/cache/getAll",a({cacheKey:e})).then(function(e){return r(c(e.data.object,o),6)},function(e){n.error("获取数据%s失败",cacheKey)})},T.queryDetail=d,T.resetForm=g,T.addItem=m,T.updateItem=p,T.deleteItem=h,T.auditItem=f,T.roleAllocate=C,T.resetPwd=E}]),angular.module("myApp").controller("RoleAllocateModalInstanceCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike",function(e,t,a,n,o){var l=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};l.item.roles=[],l=e.vm=a,l.toggleCheck=function(e,t){e.target.checked?l.item.roles.push(t):l.item.roles.splice(l.item.roles.indexOf(t),1)},e.save=function(){n.post(l.url,o({userId:l.item.user.userId,roles:l.item.roles})).then(function(t){t.data.success?(alert("操作成功"),e.cancel()):alert("操作失败")},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")}}]),angular.module("myApp").controller("ResetPwdModalInstanceCtrl",["$scope","$uibModalInstance","modalItem","$http","myConstant",function(e,t,a,n,o){var l=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};l=e.vm=a,l.constant=o,e.save=function(){n.post(l.url,{userId:l.item.userId,userPwd:l.item.userPwd},config.jsonHeader).then(function(t){t.data.success?(alert("操作成功"),e.cancel()):alert("操作失败")},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")}}]);