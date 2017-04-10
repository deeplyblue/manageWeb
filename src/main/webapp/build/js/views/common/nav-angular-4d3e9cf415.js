angular.module("myApp",["ui.bootstrap","ngAnimate","angularFileUpload","angular-growl","w5c.validator","angular.filter"]).factory("myHttpInterceptor",["$q","growl","$log","CheckboxService",function(e,t,n,r){return{request:function(e){return e.url.contains("search")&&t.addServerMessages([{msg:"请求加载中---","severity-level":"info"}]),r.clearCheckedNew(e),e},requestError:function(n){return t.addServerMessages([{msg:"请求未能成功发出---","severity-level":"error"}]),e.reject(n)},response:function(e){return e.config.url.contains("search")&&t.addServerMessages([{msg:"请求响应成功---","severity-level":"success"}]),e},responseError:function(n){return t.addServerMessages([{msg:"请求响应失败---","severity-level":"warn"}]),e.reject(n)}}}]).constant("myConstant",{datepicker:{dateOptions:{formatYear:"yy",startingDay:1,formatDayTitle:"yyyy MMMM",closeText:"close"}},inputClass:"form-control input-sm form-control-inline"}).config(["$httpProvider",function(e){e.defaults.headers.post["Content-Type"]="application/x-www-form-urlencoded"}]).config(["growlProvider","$httpProvider",function(e,t){e.globalTimeToLive(3e3),e.messagesKey("success"),e.messageTextKey("msg"),e.messageSeverityKey("severity-level"),e.onlyUniqueMessages(!0),t.interceptors.push("myHttpInterceptor")}]).config(["w5cValidatorProvider",function(e){e.config({blurTrig:!0,showError:function(e,t){if(angular.element(".modal-body")){if(!angular.element(e).next().hasClass("errorInfo")){var n=document.createElement("span");angular.element(n).attr("style","color:red;").addClass("errorInfo").html(' <i class="glyphicon glyphicon-minus-sign"></i> '+t),angular.element(e).after(n)}return!1}$(e).tooltip({title:t,animation:!1,placement:"right"}),$(e).tooltip("show")},removeError:function(e){if(angular.element(".modal-body")){var t=angular.element(e).next();return t.hasClass("errorInfo")&&t.remove(),!1}$(e).tooltip("destroy")}}),e.setRules({email:{required:"输入的邮箱地址不能为空",email:"输入邮箱地址格式不正确"},userName:{required:"输入的用户名不能为空",pattern:"用户名必须输入字母、数字、下划线,以字母开头",w5cuniquecheck:"输入用户名已经存在，请重新输入"},userPwd:{required:"密码不能为空",pattern:"密码长度8-16位,且必须包含大写字母、小写字母、数字、特殊字符中至少三种",minlength:"密码长度不能小于{minlength}",maxlength:"密码长度不能大于{maxlength}"},repeatPassword:{required:"重复密码不能为空",repeat:"两次密码输入不一致"},userMobile:{pattern:"手机号必须为11位数字"},number:{required:"数字不能为空",pattern:"数字格式错误"},customizer:{customizer:"自定义验证数字必须大于上面的数字"},dynamicName:{required:"动态Name不能为空"},dynamic:{required:"动态元素不能为空"}})}]).config(["$provide",function(e){e.decorator("requiredDirective",["$delegate",function(e){var t=e[0],n=t.link;return t.compile=function(){return function(e,t,r){n.apply(this,arguments),t.after('<span style="color: red;font-weight: bold"> *</span>')}},e}])}]).config(["$provide",function(e){e.decorator("uibDatepickerPopupDirective",["$delegate","myConstant",function(e,t){var n=e[0],r=n.link;return n.compile=function(){return function(e,n,a){e.datepickerOptions=t.datepicker.dateOptions,n.attr("readonly",!0),r.apply(this,arguments),$(n).on("click",function(){e.isOpen=!e.isOpen,e.$apply()})}},e}])}]).config(["$provide",function(e){e.decorator("uibPaginationDirective",["$delegate",function(e){var t=e[0],n=t.link;return t.compile=function(){return function(e,t,r){r.firstText="首页",r.lastText="尾页",r.previousText="前一页",r.nextText="后一页",n.apply(this,arguments)}},e}])}]).factory("OpenService",["$http","$uibModal","$log",function(e,t,n){return function(e,n,r,a){if(e.modalTitle&&e.modalBody&&e.url){r||(r="ModalInstanceCtrl"),a||(a="myModalContent.html");var o=t.open({animation:!0,templateUrl:a,controller:r,size:"lg",resolve:{modalItem:function(){return e}}});o.result.then(n,function(){angular.isFunction(n)&&n()})}}}]).factory("BoxService",["$uibModal","$log",function(e,t){var n=function(t,n){return e.open({animation:!0,templateUrl:t,controller:"BoxServiceModalInstanceCtrl",size:"sm",resolve:{modalItem:function(){return n}}})},r={alert:function(e,r){var a=n("alertModalContent.html",e);a.result.then(function(e){angular.isFunction(r)&&r(e)},function(){t.debug("dismiss")})},confirm:function(e,t){var r=n("confirmModalContent.html",e);r.result.then(function(){angular.isFunction(t)&&t(!0)},function(){angular.isFunction(t)&&t(!1)})},prompt:function(e,r){var a=n("promptModalContent.html",e);a.result.then(function(e){angular.isFunction(r)&&r(e)},function(){t.debug("dismiss")})}};return r}]).factory("CheckboxService",function(){function e(n){if(angular.isArray(n))for(var r in n)e(n[r]);else if(angular.isObject(n)&&(n.hasOwnProperty("_checked")&&delete n._checked,!t(n)))for(var r in n)if(angular.isArray(n[r]))for(var a in n[r])e(n[r][a]);else angular.isObject(n[r])&&n[r].hasOwnProperty("_checked")&&delete n[r]._checked}function t(e){for(var t in e)if(e.hasOwnProperty(t))return!1;return!0}var n=[],r={updateChecked:function(e,t){var r=e.target;r.checked&&n.indexOf(t)==-1?n.push(t):r.checked||n.indexOf(t)==-1||n.splice(n.indexOf(t),1)},getChecked:function(){return n},clearChecked:function(){n=[]},getCheckedNew:function(e){return e instanceof Array?e.filter(function(e){if(e._checked)return!0}):[]},clearCheckedNew:function(n){n.headers["Content-Type"]&&(n.headers["Content-Type"].contains("json")?e(n.data):n.headers["Content-Type"].contains("x-www-form-urlencoded")&&!t(n.data)&&(n.data=n.data.replace(/\b_checked\b=true/g,""),n.data=n.data.replace(/\b_checked\b=false/g,"")))}};return r}).factory("CacheService",["$http","$interval","$log","$httpParamSerializerJQLike",function(e,t,n,r){var a=new Object,o={initCache:function(t,o){if(!o&&"object"==typeof t){var i=[];for(var c in t)i.push(c);return n.debug("批量查询缓存"),void e.post(config.ctx+"/base/cache/getMore",i,config.jsonHeader).then(function(e){var n=e.data.object;for(var r in t)t[r]=n[r],a[r]=n[r]},function(e){n.error("获取数据%s失败",i)})}return a.hasOwnProperty(t)?a[t]:(a[t]=[],void(0==a[t].length&&(n.debug("查询缓存"),e.post(config.ctx+"/base/cache/get",r({cacheKey:t})).then(function(e){a[t]=e.data.object,o(t,a[t])},function(e){n.error("获取数据%s失败",t)}))))},initCaches:function(t,r){var o=[];for(var i in t)o.push(i);n.debug("批量查询缓存"),e.post(config.ctx+"/base/cache/getMore",o,config.jsonHeader).then(function(e){var n=e.data.object;for(var o in t)angular.isUndefined(n[o])||(t[o]=n[o],a[o]=n[o]);r&&r()},function(e){n.error("获取数据%s失败",o)})},getCache:function(e){var t=[];for(var n in a[e])t.push({key:n,value:a[e][n]});return t},cast2Arr:function(e){if(e instanceof Object){var t=[];for(var n in e)t.push({key:n,value:e[n]});return t}return[]},getObj:function(t,a){return e.post(config.ctx+"/base/cache/getAll",r({cacheKey:t})).then(function(e){return n.debug(e.data.object),a&&a(t,e.data.object),e.data.object},function(e){n.error("获取数据%s失败",cacheKey)})}};return o}]).factory("DateCalculateService",["$log",function(e){var t={getToday:function(){var e=new Date;return new Date(e.getFullYear(),e.getMonth(),e.getDate())},getYesterday:function(){var e=this.getToday(),t=e.setTime(e.getTime()-this.getTimes(1));return t},getTomorrow:function(){var e=this.getToday(),t=e.setTime(e.getTime()+this.getTimes(1));return t},dayBefore:function(e){var t=this.getToday(),n=t.setTime(t.getTime()-this.getTimes(e));return n},dayAfter:function(e){var t=this.getToday(),n=t.setTime(t.getTime()+this.getTimes(e));return n},getTimes:function(t){return angular.isNumber(t)?24*t*60*60*1e3:void e.error("date calculate failed:",t)}};return t}]),angular.module("myApp").controller("ModalInstanceCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike","$log","myConstant",function(e,t,n,r,a,o,i){var c=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};c=e.vm=n,c.constant=i,e.save=function(){r.post(c.url,a(c.item)).then(function(e){e.data.success?(alert("操作成功"),t.close(c.item)):alert(e.data.msg)},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")}}]),angular.module("myApp").controller("BoxServiceModalInstanceCtrl",["$scope","$uibModalInstance","modalItem","myConstant",function(e,t,n,r){var a=e.vm={msg:"please change the title",result:""};a.msg=n,a.constant=r,e.ok=function(){t.dismiss("cancel")},e.save=function(){t.close(a.result)},e.confirm=function(){t.close(a.result)},e.cancel=function(){t.dismiss("cancel")}}]);