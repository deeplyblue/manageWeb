angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","limitToFilter","filterFilter","myConstant",function(e,t,a,i,n,l,o,c,r,d){function s(){var e=document.getElementById("queryForm");g.url=angular.element(e).prop("action"),g.postData=g.queryBean,g.postData.pageSize=g.pagination.pageSize,g.postData.pageNum=g.pagination.pageNum,t.post(g.url,a(g.postData)).then(function(e){var t=e.data;t.success?(g.pagination=t.object,l.clearChecked()):alert(t.msg)})}function u(){g.queryBean={}}function h(e,t){l.updateChecked(e,t),i.debug(t),i.debug(l.getChecked())}function m(){n({modalTitle:"新增对账文件模板",modalBody:"toAdd",url:"add",item:{},cached:{}},function(e){g.pagination.list.push(e)},"chkFileDataCfgModalInstanceCtrl","myModalNoFooter.html")}function p(){return 1!=l.getCheckedNew(g.pagination.list).length?void alert("必须勾选一条记录！"):void n({modalTitle:"修改对账文件模板",modalBody:"toUpdate",url:"update",item:l.getCheckedNew(g.pagination.list)[0],cached:{}},function(){},"updateChkFileDataCfgModalInstanceCtrl")}var g=e.vm={};g.constant=d,g.pagination={pageSize:10,pageNum:1},g.queryBean={},g.cached={FILE_TEMPLATE_NO:{},MERCHANT_CODE:{}},o.initCache(g.cached),g.getCache=function(e){return o.getCache(e)},g.getObj=function(e,n){return t.post(config.ctx+"/base/cache/getAll",a({cacheKey:e})).then(function(e){return c(r(e.data.object,n),6)},function(e){i.error("获取数据%s失败",cacheKey)})},g.queryDetail=s,g.resetForm=u,g.updateChecked=h,g.addItem=m,g.updateItem=p}]),angular.module("myApp").controller("chkFileDataCfgModalInstanceCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike","$log","myConstant",function(e,t,a,i,n,l,o){var c=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};c=e.vm=a,c.constant=o,c.chkFileDataField=[],c.chkFileDataField[0]={field:"",seqNo:""},c.chkFileDataField[1]={field:"",seqNo:""},c.addChkFileDataField=function(e){c.chkFileDataField.push({field:"",seqNo:""})},c.deleteChkFileDataField=function(e){0==e?alert("第一条信息不能删除!"):c.chkFileDataField.remove(e)},Array.prototype.remove=function(e){if(isNaN(e)||e>this.length)return!1;for(var t=0,a=0;t<this.length;t++)this[t]!=this[e]&&(this[a++]=this[t]);this.length-=1},e.save=function(){var e=angular.copy(c.item);e.fieldDTOList=c.chkFileDataField,i.post(c.url,e,config.jsonHeader).then(function(e){e.data.success?(alert("操作成功"),t.close(c.item)):alert(e.data.msg)},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")}}]),angular.module("myApp").controller("updateChkFileDataCfgModalInstanceCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike","$log","myConstant",function(e,t,a,i,n,l,o){var c=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};c=e.vm=a,c.constant=o,c.addChkFileDataField=function(e){c.item.fieldDTOList.push({field:"",seqNo:""})},c.deleteChkFileDataField=function(e){0==e?alert("第一条信息不能删除!"):c.item.fieldDTOList.remove(e)},Array.prototype.remove=function(e){if(isNaN(e)||e>this.length)return!1;for(var t=0,a=0;t<this.length;t++)this[t]!=this[e]&&(this[a++]=this[t]);this.length-=1},e.save=function(){var e=angular.copy(c.item);i.post(c.url,e,config.jsonHeader).then(function(e){e.data.success?(alert("操作成功"),t.close(c.item)):alert(e.data.msg)},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")}}]);