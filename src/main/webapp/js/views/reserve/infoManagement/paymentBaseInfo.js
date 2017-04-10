/**
 * Created by jinxin on 2016/12/20.
 * 支付机构基本信息管理
 */
angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter', 'myConstant', 'dateFilter', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, limitToFilter, filterFilter, myConstant, dateFilter) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;
        //变量初始化
        //分页数据
        vm.pagination = {
            pageSize: 10,
            pageNum: 1
        };
        vm.test1={
            id: 12
        }
        //查询条件
        vm.queryBean = {};

        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
            //缓存数据初始化(需要缓存的key请自定义)
            /*数据格式{
             key1 :value1,
             key2:value2
             }*/
            vm.cached = {
            SERVICE_TYPE_FOR_RESERVE:{},
            SERVICE_RANGE_FOR_RESERVE:{},
            HANDLE_STATUS_FOR_RESERVE:{}
        };
        (function initCache() {
            CacheService.getObj('ALL_CITY', function (key, cacheObj) {
                vm.cached['ALL_CITY'] = cacheObj;
            });
            CacheService.initCaches(vm.cached);
        }());

        // /*将前面缓存的数据格式化
        //  [{key:value},{key:value}]*/
        vm.getCache = function (key) {
            return CacheService.getCache(key);
        };

        //获取对象数据[user,user,user]
        vm.getObj = function (key, input) {
            return $http.post(config.ctx + '/base/cache/getAll',
                $httpParamSerializerJQLike({cacheKey: key}))
                .then(function (response) {
                    return limitToFilter(filterFilter(response.data.object, input), 6);
                }, function (response) {
                    $log.error('获取数据%s失败', cacheKey);
                })
        };

        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;

        /*------------------以上配置通用---------------------*/
        vm.queryBaseInfoDetail=queryBaseInfoDetail;
        vm.updateChecked = updateChecked;
        vm.updateItem = updateItem;
        vm.deleteItem = deleteItem;


        /*------------------以上方法名可选择性通用---------------------*/

        /*CacheService.initCache(vm.cached, function (cacheKey, cacheObj) {
         $log.debug(cacheKey, cacheObj);
         vm.cached[cacheKey] = cacheObj;
         });*/

        /*vm.getCache = function (key) {
         CacheService.getCache(key)
         }*/
// 点击查询生效


        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            var postData = angular.copy(vm.queryBean);
            postData.pageSize = vm.pagination.pageSize;
            postData.pageNum = vm.pagination.pageNum;
            postData.beginTime = dateFilter(postData.beginTime, 'yyyy-MM-dd');
            postData.endTime = dateFilter(postData.endTime, 'yyyy-MM-dd');
            console.info(postData.endTime);
            $http.post(vm.url, $httpParamSerializerJQLike(postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination = data.object;
                        //清空选中记录
                        CheckboxService.clearChecked();
                    } else {
                        alert(data.msg);
                    }
                });

        }


        function resetForm() {
            vm.queryBean = {};
        }

        function updateChecked($event, id) {
            CheckboxService.updateChecked($event,id);

            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }


        function updateItem() {

            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            var pro;
            var city;
            var realpro;
            var realcity;

            var temp = vm.pagination.list.filter(function (item, index, array) {
                if (item.msgId == CheckboxService.getChecked()[0]) {
                    if(item.addrProvince!=null&&item.addrProvince!=""){
                        console     .info(item.addrProvince);
                        pro = vm.cached.ALL_CITY.filter(function (it) {
                            console.info(item.addrProvince);
                            if (item.addrProvince && it.areaName == item.addrProvince){
                                console.info(it.areaName);
                                return true;
                            }
                        })[0];
                        city = pro.citys.filter(function (it) {
                            if (item.addrCity && it.cityName == item.addrCity){
                                return true;
                            }
                        })[0];
                    }
                    if(item.realProvince!=null&&item.realProvince!="") {
                        realpro = vm.cached.ALL_CITY.filter(function (it) {
                            if (item.realProvince && it.areaName == item.realProvince) {
                                return true;
                            }
                        })[0];
                        //回显
                        realcity = realpro.citys.filter(function (it) {
                            if (item.realCity && it.cityName == item.realCity) {
                                return true;
                            }
                        })[0];
                    }
                    return true;
                }
            })[0];

            OpenService({
                modalTitle: '修改支付机构基本信息',
                modalBody: 'toUpdate',
                url: 'update',
                item: temp,
                pro:pro,
                city:city,
                realpro:realpro,
                realcity:realcity,
                cached: {
                    ALL_CITY: vm.cached.ALL_CITY,
                    SERVICE_TYPE_FOR_RESERVE: vm.cached.SERVICE_TYPE_FOR_RESERVE,
                    SERVICE_RANGE_FOR_RESERVE: vm.cached.SERVICE_RANGE_FOR_RESERVE
                }
            },function(){
                vm.queryDetail();
            },"updatePaymentBaseInfoModalInstanceCtrl",'myModalNoFooter.html');
        }

        function deleteItem() {
            if (CheckboxService.getChecked().length != 1) {
                alert("必须勾选一条记录！");
                return;
            }
            if(!confirm("确定删除吗？")){
                return ;
            }
            var paymentBaseInfoPojoIds= CheckboxService.getChecked().join(",");
            console.info(paymentBaseInfoPojoIds);
            // alert(1);CheckboxService.getChecked()[0]
            $http.post('delete', $httpParamSerializerJQLike({'msgId': paymentBaseInfoPojoIds}))
        .then(function (response) {
            // alert(1);
            var data = response.data;
            if (data.success) {
                var item = vm.pagination.list.filter(function (item, index, array) {
                    return item.msgId == CheckboxService.getChecked()[0];
                })[0];
                vm.pagination.list.splice(vm.pagination.list.indexOf(item), 1);
                alert("操作成功");
            } else {
                alert(response.data.msg);
            }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        }
            function queryBaseInfoDetail(bean) {
                var item = angular.copy(bean);
                // var addrPro=item.addrProvince;
                // var pro;
                // var proName;
                // var city;
                // var cityName;
                // var realpro;
                // var realproName;
                // var realcity;
                // var realcityName;
                // if(item.addrProvince!=null&&item.addrProvince!=""){
                //     console  .info(item.addrProvince);
                //     pro = vm.cached.ALL_CITY.filter(function (it) {
                //         console.info(item.addrProvince);
                //         if (item.addrProvince && it.areaCode == item.addrProvince){
                //             proName=it.areaName;
                //             return true;
                //         }
                //     })[0];
                //     city = pro.citys.filter(function (it) {
                //         if (item.addrCity && it.cityCode == item.addrCity){
                //             cityName=it.cityName;
                //             return true;
                //         }
                //     })[0];
                // }
                // if(item.realProvince!=null&&item.realProvince!="") {
                //     realpro = vm.cached.ALL_CITY.filter(function (it) {
                //         if (item.realProvince && it.areaCode == item.realProvince) {
                //             realproName=it.areaName;
                //             return true;
                //         }
                //     })[0];
                //     //回显
                //     realcity = realpro.citys.filter(function (it) {
                //         if (item.realCity && it.cityCode == item.realCity) {
                //             realcityName=it.cityName;
                //             return true;
                //         }
                //     })[0];
                // }

               // item.push("","");

                OpenService({
                    modalTitle: '查看明细',
                    modalBody: 'toDetail',
                    url: '#',
                    item: item,
                    // cityName:cityName,
                    // proName:proName,
                    // realcityName:realcityName,
                    // realproName:realproName,
                    cached: {
                        ALL_CITY: vm.cached.ALL_CITY,
                        SERVICE_TYPE_FOR_RESERVE: vm.cached.SERVICE_TYPE_FOR_RESERVE,
                        SERVICE_RANGE_FOR_RESERVE: vm.cached.SERVICE_RANGE_FOR_RESERVE,
                        HANDLE_STATUS_FOR_RESERVE:vm.cached.HANDLE_STATUS_FOR_RESERVE
                    }

                }, function (item) {
                }, '', 'myModalNoSave.html');
            }


    }]);


angular.module('myApp').controller('updatePaymentBaseInfoModalInstanceCtrl',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', 'dateFilter', 'myConstant','OpenService','CacheService', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                        $httpParamSerializerJQLike, $log, dateFilter, myConstant) {

    var vm = $scope.vm = {
        modalTitle: 'please change the title',
        modalBody: '#',
        url: '',
        item: {}
    }

    vm = $scope.vm = modalItem;
    vm.constant = myConstant;
    $scope.save = function () {
        // var postData = angular.copy(vm.item);
        var postData =angular.copy( vm.item);
        // postData.mastereSponsorList=null;
        // postData.sponsorList=null;
        // postData.serviceList=null;
        postData.licenseDate = dateFilter(postData.licenseDate, 'yyyy-MM-dd');
        postData.licenseGrantDate = dateFilter(postData.licenseGrantDate, 'yyyy-MM-dd');
        postData.operateTime = dateFilter(postData.operateTime, 'yyyy-MM-dd');
        delete postData.beginTime;
        delete postData.endTime;
        delete postData.pageNum;
        delete postData.pageSize;
        delete postData.rowCount;
        delete postData.updatedAt;
        delete postData.startRow;
        delete postData.updateBy;
        delete postData.remarkDesc;
        delete postData.clearKey;
        delete postData.$$hashKey;
        // delete postData.mastereSponsorList;
        // delete postData.serviceList;
        // delete postData.sponsorList;
        $log.debug("---------postData",postData);
        $http.post(vm.url,postData ,config.jsonHeader)
            .then(function (response) {
                if (response.data.success) {
                    alert("操作成功");
                    $uibModalInstance.close(vm.item);
                } else {
                    // alert(111);
                    alert(response.data.msg);
                }
            }, function (response) {
                alert("操作失败");
                alert(response.status);
            })
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
    // 如何拿数据
        //出资人及持股比例
        if( vm.item.sponsorList==null||vm.item.sponsorList==""){
            console.info(vm.item.sponsorList);
            vm.item.sponsorList=[];
            vm.item.sponsorList[0]={"shareholderName": "", "shareholding": "", "idNo": ""};
        }
        vm.addSponsor = function addSponsor(obj) {
            console.log("addSponsor");
            vm.item.sponsorList.push({"shareholderName": "", "shareholding": "", "idNo": ""});
        };

        vm.deleteSponsor = function deleteSponsor(index) {
            if (index == 0) {
                alert("第一条信息不能删除!");
            } else {
                vm.item.sponsorList.remove(index);
            }
        };
        //出资人及持股比例

        if( vm.item.mastereSponsorList==null||vm.item.mastereSponsorList==""){
            vm.item.mastereSponsorList=[];
            vm.item.mastereSponsorList[0]={"shareholderName": "", "shareholding": "", "idNo": ""};
        }
        vm.addMastereSponsor = function addMastereSponsor(obj) {
            console.log("mastereSponsorList");
            vm.item.mastereSponsorList.push({"shareholderName": "", "shareholding": "", "idNo": ""});
        };

        vm.deleteMastereSponsor = function deleteMastereSponsor(index) {
            if (index == 0) {
                alert("第一条信息不能删除!");
            } else {
                vm.item.mastereSponsorList.remove(index);
            }

        };
        // 业务种类及范围明细
        if( vm.item.serviceList==null||vm.item.serviceList==""){
            vm.item.serviceList=[];
            vm.item.serviceList[0]={"serviceType": "", "serviceRange": ""};

        }

        // for(var i=0;i<vm.item.serviceList.length;i++){
        //     if( vm.item.serviceList[i].serviceRange==null){
        //         vm.item.serviceList[i].serviceRange.push({"serviceRange": ""});
        //     }
        // }
        vm.addServiceType = function addServiceType(obj,bean) {
            console.log("serviceList");
            // for(){
            //  vm.companyProfit = [];
            // vm.companyProfit[0] = {"companyCode": "", "propNum": ""};
            // }
            console.info(vm.item.serviceList);
            vm.item.serviceList.push({"serviceType": "", "serviceRange":"" });
            var length=vm.item.serviceList.length;
            vm.item.serviceList[length-1].serviceRange=[];
            vm.item.serviceList[length-1].serviceRange[0]={ "range": ""};
            // vm.item.serviceList[obj+1].serviceRange.push({ "range": ""});
        };

        vm.deleteServiceType = function deleteServiceType(index) {
            if (index == 0) {
                alert("第一条信息不能删除!");
            } else {
                vm.item.serviceList.remove(index);
            }
        };
        vm.addServiceRange = function addServiceRange(index,obj) {
            console.log("serviceType");

            obj.serviceRange.push({ "range": ""});
        };

        vm.deleteServiceRange = function deleteServiceRange(index,bean) {
            if (index == 0) {
                alert("第一条信息不能删除!");
            } else {
                bean.serviceRange.remove(index);
            }
        };
        // if( vm.item.serviceList==null||vm.item.serviceList==""){
        //     vm.item.serviceList.push({"serviceName": "", "serviceRange": ""});
        // }
        // vm.addServiceRange = function addServiceRange(obj,bean) {
        //     console.log("serviceList");
        //     bean.serviceRange.push({"serviceName": "", "serviceRange": ""});
        // };
        //
        // vm.deleteServiceRange = function deleteServiceRange(index,bean) {
        //     if (index == 0) {
        //         alert("第一条信息不能删除!");
        //     } else {
        //         bean.serviceRange.remove(index);
        //     }
        // };
// 添加业务范围
        //vm.addServiceRange = function addServiceRange() {


        Array.prototype.remove = function (dx) {
            if (isNaN(dx) || dx > this.length) {
                return false;
            }
            for (var i = 0, n = 0; i < this.length; i++) {
                if (this[i] != this[dx]) {
                    this[n++] = this[i]
                }
            }
            this.length -= 1
        };


}]);






