/**
 * Created by lupf on 2016/4/28.
 */

angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'limitToFilter', 'filterFilter','dateFilter','myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                                                                             CacheService, limitToFilter, filterFilter,dateFilter,myConstant) {
            //视图层变量viewModel
            var vm = $scope.vm = {};
            vm.constant = myConstant;
            //变量初始化
            //分页数据
            vm.pagination = {
                pageSize: 10,
                pageNum: 1
            };
            //查询条件
            vm.queryBean = {};

            //缓存数据初始化(需要缓存的key请自定义)
            /*数据格式{
             key1 :value1,
             key2:value2
             }*/
            vm.cached = {
                RESERVE_LIST:{},
                RESERVE_INFO:{}
            };

            /*        (function init() {
             vm.queryBean = {};
             $http.post(config.ctx + '/reserve/info/init/reserve',{},config.jsonHeader)
             .then(function (response) {
             if (response.data.success){
             vm.cached.RESERVE_INFO=response.data.object;
             format();
             }else {
             $log.error('获取数据%s失败',response.data.msg);
             }
             }, function (response) {
             $log.error('获取数据%s失败', cacheKey);
             })
             }());*/

            function format(){
                vm.cached.RESERVE_LIST = [];
                for (var index in vm.cached.RESERVE_INFO){
                    vm.cached.RESERVE_LIST.push({'accountNo':index,'desc':vm.cached.RESERVE_INFO[index]['description']});
                }
                //vm.queryBean.accountNo=vm.cached.RESERVE_LIST[0]['accountNo'];
                //vm.queryBean.date =  new Date();
            }


            /*将前面缓存的数据格式化
             [{key:value},{key:value}]*/
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

            vm.queryDifferDetail = queryDifferDetail;
            vm.updateChecked = updateChecked;
            vm.addItem = addItem;
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

            function queryDetail() {
                var queryForm = document.getElementById('queryForm');
                vm.url = angular.element(queryForm).prop('action');

                //组织参数：查询条件 + 分页数据
                var postData = angular.copy(vm.queryBean);
                postData.pageSize = vm.pagination.pageSize;
                postData.pageNum = vm.pagination.pageNum;
                postData.startDate = dateFilter(postData.startDate, 'yyyy-MM-dd');
                postData.endDate = dateFilter(postData.endDate, 'yyyy-MM-dd');
                $log.debug("postData",postData);
                $http.post(vm.url, $httpParamSerializerJQLike(postData))
                    .then(function (response) {
                        var data = response.data;
                        $log.debug("data",data);
                        if (data.success) {
                            vm.pagination = data.object;
                            vm.pagination.queryBean = vm.queryBean;
                            //清空选中记录
                            CheckboxService.clearChecked();
                        } else {
                            alert(data.msg);
                        }
                    });

            };

            function resetForm() {
                vm.queryBean = {};
            }

            function queryDifferDetail(bean) {
                var item=bean;
                OpenService({
                    modalTitle: '查看明细',
                    modalBody: 'toDifferDetail',
                    url: 'differSubmit',
                    item: item,
                    cached: {
                    }
                }, function (item) {

                }, 'queryDiffDetailController', 'myModalNoFooter.html');
            }

            function updateChecked($event, id) {
                CheckboxService.updateChecked($event, id);
                $log.debug(id);
                $log.debug(CheckboxService.getChecked());
            }

            function addItem(item) {
                alert("***");
                alert(item);
                /*           OpenService({
                 modalTitle: '差异数据说明提交',
                 modalBody: 'toDetail',
                 url: 'differSubmit',
                 //item: {},
                 item: item,
                 cached: {
                 MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE'),
                 ALL_CITY: vm.cached.ALL_CITY
                 }
                 }, function (item) {
                 vm.pagination.list.push(item);
                 });*/
            }

            function updateItem() {
                if (CheckboxService.getChecked().length != 1) {
                    alert("必须勾选一条记录！");
                    return;
                }

                var temp = vm.pagination.list.filter(function (item, index, array) {
                    if (item.id == CheckboxService.getChecked()[0]) {
                        return true;
                    }
                })[0];
                OpenService({
                    modalTitle: '修改备付金信息',
                    modalBody: 'toUpdate',
                    url: 'update',
                    item:temp,
                    cached: {
                        MERCHANT_CODE: CacheService.getCache('MERCHANT_CODE'),
                        ALL_CITY: vm.cached.ALL_CITY
                    }
                },function(item){
                    $log.debug(item);
                });
            }

            function deleteItem() {
                if (CheckboxService.getChecked().length != 1) {
                    alert("必须勾选一条记录！");
                    return;
                }
                $http.post('delete', $httpParamSerializerJQLike({'id': CheckboxService.getChecked()[0]}))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            var item = vm.pagination.list.filter(function (item, index, array) {
                                return item.id == CheckboxService.getChecked()[0];
                            })[0];
                            vm.pagination.list.splice(vm.pagination.list.indexOf(item), 1);
                            alert("操作成功");
                        } else {
                            alert("操作失败");
                        }
                    }, function (response) {
                        alert(response.statusText);
                        $log.debug("error");
                    });
            }
        }]);


angular.module('myApp').controller('queryDiffDetailController', ['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', 'FileUploader', 'dateFilter', '$log', 'CheckboxService', 'myConstant', 'DateCalculateService', function ($scope, $uibModalInstance, modalItem, $http,
                                                                                                                                           $httpParamSerializerJQLike, FileUploader, dateFilter, $log, CheckboxService, myConstant, DateCalculateService) {

        var vm = $scope.vm = {
            modalTitle: 'please change the title',
            modalBody: '#',
            url: '',
            item: {}
        };

        vm = $scope.vm = modalItem;
        vm.constant = myConstant;

        vm.queryBean = {};

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.save = function () {
            var postData = {};
            postData.content = vm.item.content;
            postData.oriMsgType ='PSIS503';
            postData.oriMsgId = vm.item.batchMsgId;
            $http.post(config.ctx+'/reserve/data/differ/differSubmit', postData ,config.jsonHeader)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert("操作成功");
                        $uibModalInstance.close(vm.item);
                    } else {
                        alert("操作失败");
                    }
                }, function (response) {
                    alert(response.statusText);
                    $log.debug("error");
                });
        };


    }]);