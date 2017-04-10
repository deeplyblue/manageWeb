/**
 * Created by hz on 2016/12/22.
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
            //查询条 件
            vm.queryBean = {};

            vm.queryDetail = queryDetail;

            vm.resetForm = resetForm;
            vm.handle = handle;
            vm.detailShow = detailShow;

            /*------------------以上配置通用---------------------*/


            /*------------------以上方法名可选择性通用---------------------*/
            //缓存数据初始化(需要缓存的key请自定义)
            vm.cached = {
                MESSAGE_TYPE_FOR_RESERVE: {},
                COMPLAINT_TYPE_FOR_RESERVE:{},
                HANDLE_STATUS_FOR_RESERVE:{}
            };
            CacheService.initCache(vm.cached);

            vm.getCache = function (key) {
                return CacheService.getCache(key);
            };

            function queryDetail() {
                var queryForm = document.getElementById('queryForm');
                vm.url = angular.element(queryForm).prop('action');

                //组织参数：查询条件 + 分页数据
                var postData = angular.copy(vm.queryBean);
                postData.pageSize = vm.pagination.pageSize;
                postData.pageNum = vm.pagination.pageNum;
                postData.beginTime = dateFilter(postData.beginTime, 'yyyy-MM-dd');
                postData.endTime = dateFilter(postData.endTime, 'yyyy-MM-dd');
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

            };

            function resetForm() {
                vm.queryBean = {};
            }


            function handle(bean) {
                var temp = angular.copy(bean)
                OpenService({
                    modalTitle: '处理信息',
                    modalBody: 'toHandle',
                    url: 'handle',
                    item: temp,
                    cached: {
                        COMPLAINT_TYPE_FOR_RESERVE:vm.cached.COMPLAINT_TYPE_FOR_RESERVE
                    }
                },function(){},"handleComplaitInfo",'');
            }



            function detailShow(bean) {
                var temp = angular.copy(bean)
                OpenService({
                    modalTitle: '投诉信息处理详情',
                    modalBody: 'toDetail',
                    url: 'detail',
                    item: temp,
                    cached: {
                        COMPLAINT_TYPE_FOR_RESERVE:vm.cached.COMPLAINT_TYPE_FOR_RESERVE
                    }
                },function(){},'','myModalNoSave.html');
            }
        }]);

angular.module('myApp').controller('handleComplaitInfo',['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', 'dateFilter', 'myConstant', function ($scope, $uibModalInstance, modalItem, $http,
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

            vm.postData = angular.copy(vm.item);
            vm.postData.accuseDate = dateFilter(vm.postData.accuseDate, 'yyyy-MM-dd');
            vm.postData.handleTime = dateFilter(vm.postData.handleTime, 'yyyy-MM-dd');
            $log.debug("postData",vm.postData);
            $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
                .then(function (response) {
                    if (response.data.success) {
                        alert("操作成功");
                        $uibModalInstance.close(vm.item);
                    } else {
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
    }]);