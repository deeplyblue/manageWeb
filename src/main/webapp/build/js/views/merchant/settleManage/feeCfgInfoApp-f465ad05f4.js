angular.module("myApp").controller("queryCtrl",["$scope","$http","$httpParamSerializerJQLike","$log","OpenService","CheckboxService","CacheService","limitToFilter","filterFilter","myConstant",function(e,t,a,o,i,s,l,m,n,p){function f(){t.post(config.ctx+"/merchant/feeCfg/initTemp",a({companyType:"03"})).then(function(e){e.data.success?(C.cached.TEMP_lIST=e.data.object,r()):o.error("获取手续费模版数据失败,%s",JSON.stringify(e))},function(e){o.error("获取手续费模版数据失败")})}function r(){C.cached.TEMP_CACHE=[];for(var e in C.cached.TEMP_lIST){var t=C.cached.TEMP_lIST[e],a={id:t.id,templateName:t.templateName};C.cached.TEMP_CACHE.push(a)}}function d(){var e=document.getElementById("queryForm");C.url=angular.element(e).prop("action");var o=C.queryBean;o.pageSize=C.pagination.pageSize,o.pageNum=C.pagination.pageNum,t.post(C.url,a(o)).then(function(e){var t=e.data;t.success?(C.pagination=t.object,s.clearChecked()):alert(t.msg)})}function g(e,a){var o=angular.copy(e);delete o.feeCfgKey,o.enableFlag=a,t.post(config.ctx+"/merchant/feeCfg/updateItemEnableFlag",o,config.jsonHeader).then(function(t){var o=t.data;o.success?(e.enableFlag=a,alert("操作成功！")):alert(o.msg)})}function L(){C.queryBean={},C.queryBean.companyType="03"}function c(e,t){s.updateChecked(e,t),o.debug(t),o.debug(s.getChecked())}function u(){i({modalTitle:"增加手续费信息",modalBody:"toAdd",url:"addFeeCfg",item:{},tempSize:new Array(1),cached:{BANK_INFO:C.getCache("BANK_INFO"),BANK_CARD_TYPE:C.getCache("BANK_CARD_TYPE"),MCHNT_TRANS_CODE:C.getCache("MCHNT_TRANS_CODE"),MERCHANT_CODE:C.getCache("MERCHANT_CODE"),CONN_CHANNEL:C.getCache("CONN_CHANNEL"),TEMP_lIST:C.cached.TEMP_lIST,TEMP_CACHE:C.cached.TEMP_CACHE}},function(e){C.queryDetail()},"privateAddMerchantFeeCfgInfoCtrl","myModalNoFooter.html")}function S(){if(1!=s.getCheckedNew(C.pagination.list).length)return void alert("必须勾选一条记录！");var e=s.getCheckedNew(C.pagination.list)[0];i({modalTitle:"修改手续费模板信息",modalBody:"toUpdate",url:"updateFeeCfg",item:e,tempSize:new Array(1),cached:{BANK_INFO:C.getCache("BANK_INFO"),BANK_CARD_TYPE:C.getCache("BANK_CARD_TYPE"),MCHNT_TRANS_CODE:C.getCache("MCHNT_TRANS_CODE"),MERCHANT_CODE:C.cached.MERCHANT_CODE,CONN_CHANNEL:C.getCache("CONN_CHANNEL"),TEMP_lIST:C.cached.TEMP_lIST,TEMP_CACHE:C.cached.TEMP_CACHE}},function(e){C.queryDetail()},"privateUpdateMerchantFeeCfgInfoCtrl","myModalNoFooter.html")}function M(){return 1!=s.getChecked().length?void alert("必须勾选一条记录！"):void t.post("delete",a({userId:s.getChecked()[0]})).then(function(e){var t=e.data;if(t.success){var a=C.pagination.list.filter(function(e,t,a){return e.feeCfgNo==s.getChecked()[0]})[0];C.pagination.list.splice(C.pagination.list.indexOf(a),1),alert("操作成功")}else alert("操作失败")},function(e){alert(e.statusText),o.debug("error")})}var C=e.vm={};C.constant=p,C.pagination={pageSize:10,pageNum:1},C.queryBean={},C.cached={BANK_INFO:{},BANK_CARD_TYPE:{},MCHNT_TRANS_CODE:{},MERCHANT_CODE:{},CONN_CHANNEL:{}},function(){l.initCache(C.cached),f()}(),C.getCache=function(e){return l.getCache(e)},C.getObj=function(e,i){return t.post(config.ctx+"/base/cache/getAll",a({cacheKey:e})).then(function(e){return m(n(e.data.object,i),6)},function(e){o.error("获取数据%s失败",cacheKey)})},C.queryDetail=d,C.resetForm=L,C.updateChecked=c,C.addItem=u,C.updateItem=S,C.deleteItem=M,C.updateItemEnableFlag=g}]),angular.module("myApp").controller("privateAddMerchantFeeCfgInfoCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike","$log","$timeout","dateFilter","myConstant",function(e,t,a,o,i,s,l,m,n){var p=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};p=e.vm=a,p.constant=n,p.tempParams={},p.validateOptions={blurTrig:!0,showError:function(e,t){},removeError:!0},function(){p.item.feeSegRateModelList=[];for(var e=0;e<1;e++)p.item.feeSegRateModelList.push({segLowLmt:"",orderSeq:e+1});p.item.feeSegRateModelList[0].segLowLmt="0",p.item.feeSegRateModelList[0].segUppLmt="9999999999999"}(),p.appendSizeFun=function(){if(p.tempParams.feeSegCount){p.item.feeSegRateModelList=[];for(var e=0;e<p.tempParams.feeSegCount;e++)p.item.feeSegRateModelList.push({segLowLmt:"",orderSeq:e+1});p.item.feeSegRateModelList[0].segLowLmt="0",p.item.feeSegRateModelList[Number(p.tempParams.feeSegCount)-1].segUppLmt="99999999999999999"}},e.save=function(){p.postData=angular.copy(p.item);var e=p.postData.reFeeFlag,a=p.cached.TEMP_lIST[p.tempParams.tempId];for(var i in a)"id"!=i&&"templateName"!=i&&"creator"!=i&&"modifief"!=i&&"createTime"!=i&&"lastUpdTime"!=i&&(p.postData[i]=a[i]);for(var l in p.postData.feeSegRateModelList)"01"==p.postData.feeUnit&&(p.postData.feeSegRateModelList[l].segLowLmt=100*p.postData.feeSegRateModelList[l].segLowLmt,p.postData.feeSegRateModelList[l].segUppLmt=100*p.postData.feeSegRateModelList[l].segUppLmt),"01"==p.postData.feeSegRateModelList[l].feeSegMethod?(p.postData.feeSegRateModelList[l].feeLowLmt="",p.postData.feeSegRateModelList[l].feeUppLmt=""):"02"==p.postData.feeSegRateModelList[l].feeSegMethod&&(p.postData.feeSegRateModelList[l].feeLowLmt=100*p.postData.feeSegRateModelList[l].feeLowLmt,p.postData.feeSegRateModelList[l].feeUppLmt=100*p.postData.feeSegRateModelList[l].feeUppLmt);s.info("------------"+JSON.stringify(p.postData));var m={headers:{"Content-Type":"application/json"}};angular.isUndefined(e)||(p.postData.reFeeFlag=e),o.post(p.url,p.postData,m).then(function(e){e.data.success?(alert("操作成功"),t.close(p.item)):alert(e.data.msg)},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")},p.checkFeeUppLmt=function(e){p.item.feeSegRateModelList[e].feeUppLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)||(p.item.feeSegRateModelList[e].feeUppLmt=null),p.item.feeSegRateModelList[e].feeLowLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)||(p.item.feeSegRateModelList[e].feeLowLmt=null);var t=parseFloat(p.item.feeSegRateModelList[e].feeUppLmt),a=parseFloat(p.item.feeSegRateModelList[e].feeLowLmt);t>a||(p.item.feeSegRateModelList[e].feeUppLmt=null)},p.checkFeeLowLmt=function(e){p.item.feeSegRateModelList[e].feeLowLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)||(p.item.feeSegRateModelList[e].feeLowLmt=null);var t=parseFloat(p.item.feeSegRateModelList[e].feeUppLmt),a=parseFloat(p.item.feeSegRateModelList[e].feeLowLmt);t>a||(p.item.feeSegRateModelList[e].feeUppLmt=null)},p.checkUppLmtAmt=function(e){p.item.feeSegRateModelList[e].segUppLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)||(p.item.feeSegRateModelList[e].segUppLmt=null);var t=parseFloat(p.item.feeSegRateModelList[e].segUppLmt),a=parseFloat(p.item.feeSegRateModelList[e].segLowLmt);t>a?p.item.feeSegRateModelList[e+1].segLowLmt=p.item.feeSegRateModelList[e].segUppLmt:p.item.feeSegRateModelList[e].segUppLmt=null}}]),angular.module("myApp").controller("privateUpdateMerchantFeeCfgInfoCtrl",["$scope","$uibModalInstance","modalItem","$http","$httpParamSerializerJQLike","$log","$timeout","dateFilter","myConstant",function(e,t,a,o,i,s,l,m,n){var p=e.vm={modalTitle:"please change the title",modalBody:"#",url:"",item:{}};p=e.vm=a,p.constant=n,p.tempParams={},p.validateOptions={blurTrig:!0,showError:function(e,t){},removeError:!0},function(){var e=p.item.feeSegRateModelList.length;p.tempParams.feeSegCount=e}(),p.appendSizeFun=function(){if(p.tempParams.feeSegCount){p.item.feeSegRateModelList=[];for(var e=0;e<p.tempParams.feeSegCount;e++)p.item.feeSegRateModelList.push({segLowLmt:"",orderSeq:e+1});p.item.feeSegRateModelList[0].segLowLmt="0",p.item.feeSegRateModelList[Number(p.tempParams.feeSegCount)-1].segUppLmt="99999999999999999"}},e.save=function(){p.postData=angular.copy(p.item),delete p.postData.feeCfgKey,s.info("------------"+JSON.stringify(p.postData));for(var e in p.postData.feeSegRateModelList)"01"==p.postData.feeUnit&&(p.postData.feeSegRateModelList[e].segLowLmt=100*p.postData.feeSegRateModelList[e].segLowLmt,p.postData.feeSegRateModelList[e].segUppLmt=100*p.postData.feeSegRateModelList[e].segUppLmt),"01"==p.postData.feeSegRateModelList[e].feeSegMethod?(p.postData.feeSegRateModelList[e].feeLowLmt="",p.postData.feeSegRateModelList[e].feeUppLmt=""):"02"==p.postData.feeSegRateModelList[e].feeSegMethod&&(p.postData.feeSegRateModelList[e].feeLowLmt=100*p.postData.feeSegRateModelList[e].feeLowLmt,p.postData.feeSegRateModelList[e].feeUppLmt=100*p.postData.feeSegRateModelList[e].feeUppLmt);var a={headers:{"Content-Type":"application/json"}};o.post(p.url,p.postData,a).then(function(e){e.data.success?(alert("操作成功"),t.close(p.item)):alert(e.data.msg)},function(e){alert("操作失败"),alert(e.status)})},e.cancel=function(){t.dismiss("cancel")},p.checkFeeUppLmt=function(e){p.item.feeSegRateModelList[e].feeUppLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)||(p.item.feeSegRateModelList[e].feeUppLmt=null),p.item.feeSegRateModelList[e].feeLowLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)||(p.item.feeSegRateModelList[e].feeLowLmt=null);var t=parseFloat(p.item.feeSegRateModelList[e].feeUppLmt),a=parseFloat(p.item.feeSegRateModelList[e].feeLowLmt);t>a||(p.item.feeSegRateModelList[e].feeUppLmt=null)},p.checkFeeLowLmt=function(e){p.item.feeSegRateModelList[e].feeLowLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)||(p.item.feeSegRateModelList[e].feeLowLmt=null);var t=parseFloat(p.item.feeSegRateModelList[e].feeUppLmt),a=parseFloat(p.item.feeSegRateModelList[e].feeLowLmt);t>a||(p.item.feeSegRateModelList[e].feeUppLmt=null)},p.checkUppLmtAmt=function(e){p.item.feeSegRateModelList[e].segUppLmt.match(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/)||(p.item.feeSegRateModelList[e].segUppLmt=null);var t=parseFloat(p.item.feeSegRateModelList[e].segUppLmt),a=parseFloat(p.item.feeSegRateModelList[e].segLowLmt);t>a?p.item.feeSegRateModelList[e+1].segLowLmt=p.item.feeSegRateModelList[e].segUppLmt:p.item.feeSegRateModelList[e].segUppLmt=null};for(var f in p.item.feeSegRateModelList)"01"==p.item.feeUnit&&(p.item.feeSegRateModelList[f].segLowLmt=p.item.feeSegRateModelList[f].segLowLmt/100,p.item.feeSegRateModelList[f].segUppLmt=p.item.feeSegRateModelList[f].segUppLmt/100)}]);