angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","myConstant",function(e,t,a,n,o,c,i,l){function r(){var e=document.getElementById("queryForm");C.url=angular.element(e).prop("action"),C.postData=C.queryBean,C.postData.pageSize=C.pagination.pageSize,C.postData.pageNum=C.pagination.pageNum,t.post(C.url,a(C.postData)).then(function(e){var t=e.data;t.success?(C.pagination=t.object,c.clearChecked()):alert(t.msg)})}function d(){C.queryBean={}}function u(e,t){c.updateChecked(e,t),n.debug(t),n.debug(c.getChecked())}function s(){}function p(){C.orgBankType=new Array,C.url=config.ctx+"/merchant/bankTypeRelation/getAllBankCode",t.post(C.url,a(C.postData)).then(function(e){var t=e.data;t.success?(C.orgBankType=t.object,o({modalTitle:"新增",modalBody:"toAdd",url:"add",item:{bankCodes:C.orgBankType},cached:{COMANY_CODE:i.getCache("COMANY_CODE"),TRANS_CODE:i.getCache("TRANS_CODE"),BANK_TYPE:i.getCache("BANK_TYPE")}},function(e){r()},"BankTypeModalInstanceCtrl","myModalNoFooter.html")):alert(t.msg)})}function g(){if(C.orgBankType=new Array,C.url=config.ctx+"/merchant/bankTypeRelation/getAllBankCode",n.debug(c.getCheckedNew(C.pagination.list)),1!=c.getCheckedNew(C.pagination.list).length)return void alert("必须勾选一条记录！");var e=c.getCheckedNew(C.pagination.list)[0].bankTypeCode,l=C.pagination.list.filter(function(t){if(t.bankTypeCode==e)return!0});n.info(l);var r=l.map(function(e){return e.bankCode});t.post(C.url,a(C.postData)).then(function(t){var a=t.data;a.success?(C.orgBankType=a.object.map(function(e){return n.debug(e.bankCode),r.indexOf(e.bankCode)!=-1&&(e.checked=!0),e}),o({modalTitle:"修改",modalBody:"toUpdate",url:"update",item:{bankTypeCode:e,bankCodes:C.orgBankType,BANK_TYPE:C.cached.BANK_TYPE},cached:{COMANY_CODE:i.getCache("COMANY_CODE"),TRANS_CODE:i.getCache("TRANS_CODE"),BANK_TYPE:i.getCache("BANK_TYPE")}},function(e){C.queryDetail()},"updateBankTypeModalInstanceCtrl")):alert(a.msg)})}function m(){return 1!=c.getCheckedNew(C.pagination.list).length?void alert("必须勾选一条记录！"):void t.post("delete",a({bankTypeCode:c.getCheckedNew(C.pagination.list)[0].bankTypeCode})).then(function(e){var t=e.data;t.success?(C.pagination.list.splice(C.pagination.list.indexOf(c.getCheckedNew(C.pagination.list)[0]),1),C.queryDetail(),alert("操作成功")):alert("操作失败")},function(e){alert(e.statusText),n.debug("error")})}var C=e.vm={};C.constant=l,C.pagination={pageSize:10,pageNum:1},C.queryBean={},C.queryDetail=r,C.resetForm=d,C.updateChecked=u,C.addItem=p,C.updateItem=g,C.deleteItem=m,C.cached={COMANY_CODE:{},TRANS_CODE:{},BANK_TYPE:{}},i.initCache(C.cached),C.getAllBankCode=s}]),angular.module("myApp").controller("updateBankTypeModalInstanceCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike","myConstant",function(e,t,a,n,o,c){var i=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};i=e.vm=a,i.constant=c,console.log(i.item),e.save=function(){var e=angular.copy(i.item.bankCodes).filter(function(e){return e.bankTypeCode=i.item.bankTypeCode,e.checked?(delete e.checked,!0):void delete e.checked});n.post(i.url,e,config.jsonHeader).then(function(e){e.data.success?(alert("操作成功"),t.close(i.item)):alert(e.data.msg)},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")}}]),angular.module("myApp").controller("BankTypeModalInstanceCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike","myConstant",function(e,t,a,n,o,c){var i=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};i=e.vm=a,i.constant=c,e.save=function(){var e=angular.copy(i.item.bankCodes).filter(function(e){return e.bankTypeCode=i.item.bankTypeCode,e.checked?(delete e.checked,!0):void delete e.checked});console.log(e),n.post(i.url,e,config.jsonHeader).then(function(e){e.data.success?(alert("操作成功"),t.close(i.item)):alert(e.data.msg)},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")}}]);