angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","dateFilter",function(e,t,a,i,n,r,d,o){function c(){var e=document.getElementById("queryForm");m.url=angular.element(e).prop("action"),m.postData=m.queryBean,m.postData.pageSize=m.pagination.pageSize,m.postData.pageNum=m.pagination.pageNum,t.post(m.url,a(m.postData)).then(function(e){var t=e.data;t.success?(m.pagination=t.object,r.clearChecked()):alert(t.msg)})}function u(){m.queryBean={}}function l(e,t){r.updateChecked(e,t),i.debug(t),i.debug(r.getChecked())}function p(){n({modalTitle:"新增",modalBody:"toAdd",url:"add",item:{},cached:{TRANS_CODE_ALL:d.getCache("TRANS_CODE_ALL")}},function(e){m.pagination.list.push(e)})}function g(){if(1!=r.getChecked().length)return void alert("必须勾选一条记录！");var e=m.pagination.list.filter(function(e,t,a){return e.id==r.getChecked()[0]})[0];e.createTime=o(e.createTime,"yyyyMMdd"),e.lastUpdTime=o(e.lastUpdTime,"yyyyMMdd"),n({modalTitle:"修改",modalBody:"toUpdate",url:"update",item:e,cached:{TRANS_CODE_ALL:d.getCache("TRANS_CODE_ALL")}})}function s(){return 1!=r.getChecked().length?void alert("必须勾选一条记录！"):void t.post("delete",a({id:r.getChecked()[0]})).then(function(e){var t=e.data;if(t.success){var a=m.pagination.list.filter(function(e,t,a){return e.userId==r.getChecked()[0]})[0];m.pagination.list.splice(m.pagination.list.indexOf(a),1),alert("操作成功")}else alert("操作失败")},function(e){alert(e.statusText),i.debug("error")})}var m=e.vm={};m.pagination={pageSize:10,pageNum:1},m.queryBean={},m.queryDetail=c,m.resetForm=u,m.updateChecked=l,m.addItem=p,m.updateItem=g,m.deleteItem=s,m.cached={TRANS_CODE_ALL:{}},d.initCache(m.cached)}]);