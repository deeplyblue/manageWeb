angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService",function(e,t,a,i,n,c,o){function r(){var e=document.getElementById("queryForm");h.url=angular.element(e).prop("action"),h.postData=h.queryBean,h.postData.pageSize=h.pagination.pageSize,h.postData.pageNum=h.pagination.pageNum,t.post(h.url,a(h.postData)).then(function(e){var t=e.data;t.success?(h.pagination=t.object,c.clearChecked()):alert(t.msg)})}function d(){h.queryBean={}}function u(e,t){c.updateChecked(e,t),i.debug(t),i.debug(c.getChecked())}function l(){n({modalTitle:"新增",modalBody:"toAdd",url:"add",item:{},cached:{BANK_CARD_TYPE:o.getCache("BANK_CARD_TYPE"),ENABLE_FLAG:o.getCache("ENABLE_FLAG")}},function(e){h.pagination.list.push(e)})}function p(){if(1!=c.getChecked().length)return void alert("必须勾选一条记录！");var e=h.pagination.list.filter(function(e,t,a){return e.id==c.getChecked()[0]})[0];e.createTime=null,e.lastUpdTime=null,e.auditDate=null,n({modalTitle:"修改",modalBody:"toUpdate",url:"update",item:e,cached:{BANK_CARD_TYPE:o.getCache("BANK_CARD_TYPE"),ENABLE_FLAG:o.getCache("ENABLE_FLAG")}})}function g(){return 1!=c.getChecked().length?void alert("必须勾选一条记录！"):void t.post("delete",a({id:c.getChecked()[0]})).then(function(e){var t=e.data;if(t.success){var a=h.pagination.list.filter(function(e,t,a){return e.id==c.getChecked()[0]})[0];h.pagination.list.splice(h.pagination.list.indexOf(a),1),alert("操作成功")}else alert("操作失败")},function(e){alert(e.statusText),i.debug("error")})}var h=e.vm={};h.pagination={pageSize:10,pageNum:1},h.queryBean={},h.queryDetail=r,h.resetForm=d,h.updateChecked=u,h.addItem=l,h.updateItem=p,h.deleteItem=g,h.cached={BANK_CARD_TYPE:{},ENABLE_FLAG:{},ENABLE_FLAG:{}},o.initCache(h.cached)}]);