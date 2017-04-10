angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","limitToFilter","filterFilter","dateFilter","myConstant",function(e,t,a,n,o,i,r,l,c,d,u){function s(){var e=document.getElementById("queryForm");v.url=angular.element(e).prop("action");var n=angular.copy(v.queryBean);n.pageSize=v.pagination.pageSize,n.pageNum=v.pagination.pageNum,t.post(v.url,a(n)).then(function(e){var t=e.data;t.success?(v.pagination=t.object,i.clearChecked()):alert(t.msg)})}function m(){v.queryBean={}}function f(e,t){i.updateChecked(e,t),n.debug(t),n.debug(i.getChecked())}function p(){var e=new Date;e.setFullYear((new Date).getFullYear()+1),o({modalTitle:"增加商户密钥配置 ",modalBody:"toAdd",url:"add",item:{dataKeyValidDate:e,rsaKeyValidDate:e},cached:{MERCHANT_CODE:r.getCache("MERCHANT_CODE"),ENCTYPE:r.getCache("ENCTYPE")}},function(){s()},"privateMerchantKeyInfoCtrl","myModalNoFooter.html")}function g(e){var n;t.post(config.ctx+"/merchant/itCfg/key/searchDataKey",a({id:e})).then(function(e){var t=e.data;t.success?(n=t.object,o({modalTitle:"数据加密KEY查看",modalBody:"toDataKey",url:"#",item:n,cached:v.cached},function(){},"","myModalNoSave.html")):alert(t.msg)})}function h(e){var n;t.post(config.ctx+"/merchant/itCfg/key/searchRsaKey",a({id:e})).then(function(e){var t=e.data;t.success?(n=t.object,o({modalTitle:"RSA公钥查看",modalBody:"toRsaKey",url:"#",item:n,cached:v.cached},function(){},"","myModalNoSave.html")):alert(t.msg)})}function y(){if(1!=i.getCheckedNew(v.pagination.list).length)return void alert("必须勾选一条记录！");var e=i.getCheckedNew(v.pagination.list)[0];o({modalTitle:"修改商户密钥配置 ",modalBody:"toUpdate",url:"update",item:e,cached:{MERCHANT_CODE:v.cached.MERCHANT_CODE,ENCTYPE:r.getCache("ENCTYPE")}},function(){s()},"privateMerchantKeyInfoCtrl","myModalNoFooter.html")}function C(){return 1!=i.getChecked().length?void alert("必须勾选一条记录！"):void t.post("delete",a({userId:i.getChecked()[0]})).then(function(e){var t=e.data;if(t.success){var a=v.pagination.list.filter(function(e,t,a){return e.userId==i.getChecked()[0]})[0];v.pagination.list.splice(v.pagination.list.indexOf(a),1),alert("操作成功")}else alert("操作失败")},function(e){alert(e.statusText),n.debug("error")})}var v=e.vm={};v.constant=u,v.pagination={pageSize:10,pageNum:1},v.queryBean={},v.cached={MERCHANT_CODE:{},ENCTYPE:{}},r.initCache(v.cached),v.getCache=function(e){return r.getCache(e)},v.getObj=function(e,o){return t.post(config.ctx+"/base/cache/getAll",a({cacheKey:e})).then(function(e){return l(c(e.data.object,o),6)},function(e){n.error("获取数据%s失败",cacheKey)})},v.queryDetail=s,v.resetForm=m,v.updateChecked=f,v.addItem=p,v.updateItem=y,v.deleteItem=C,v.queryDataKey=g,v.queryRSAKey=h}]),angular.module("myApp").controller("privateMerchantKeyInfoCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike","FileUploader","$log","$timeout","dateFilter","myConstant",function(e,t,a,n,o,i,r,l,c,d){var u=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};u=e.vm=a,u.constant=d,u.constant=d,u.constant=d,u.fileItems=[{field:"rsaRemoteAddr"}],u.fileUploader=new i({url:config.ctx+"/system/fileUploader/upload",formData:[{busiType:"PK",remark:"公钥文件"}],filters:[{field:"rsaRemoteAddr",fn:function(e){var t=e.name.substring(e.name.lastIndexOf(".")+1);return"cer"==t||void alert("请上传后缀名为cer的文件!")}}],removeAfterUpload:!1}),u.fileUploader.onAfterAddingFile=function(e,t){u.fileUploader.queue.forEach(function(t){t.field==e.field&&t!=e&&u.fileUploader.removeFromQueue(t)})},u.fileUploader.onSuccessItem=function(e,t,a,n){e.originFileName=e.file.name,e.dfsFullFilename=t.object.dfsFullFilename,e.dfsGroupName=t.object.dfsGroupName},e.save=function(){var e=angular.copy(u.item);e.dataKeyValidDate=c(e.dataKeyValidDate,"yyyy-MM-dd"),e.rsaKeyValidDate=c(e.rsaKeyValidDate,"yyyy-MM-dd"),u.fileUploader.queue.forEach(function(t){r.debug(t),t.isSuccess&&(e[t.field]=t.dfsFullFilename)}),"*******"==e.dataKey&&(e.dataKey=null),r.debug(e),n.post(u.url,o(e)).then(function(e){e.data.success?(alert("操作成功"),t.close(u.item)):alert(e.data.msg)},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")}}]);