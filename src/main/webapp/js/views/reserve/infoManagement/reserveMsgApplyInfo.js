/**
 * Created by hz on 2016/12/20.
 */
angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService', 'CacheService', 'myConstant', 'dateFilter',
        function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService, CacheService, myConstant, dateFilter) {
            //视图层变量viewModel
            var vm = $scope.vm = {};
            vm.constant = myConstant;
            //变量初始化
            //分页数据
            vm.pagination = {
                pageSize: 10,
                pageNum: 1
            };
            vm.pagination1 = {
                pageSize: 10,
                pageNum: 1
            };
            //查询条 件
            vm.queryBean = {};

            vm.queryDetail = queryDetail;
            vm.resetForm = resetForm;
            vm.queryCleearCfgDetail=queryCleearCfgDetail;
            vm.applyForm = applyForm;
            /*------------------以上配置通用---------------------*/



            /*------------------以上方法名可选择性通用---------------------*/
            //缓存数据初始化(需要缓存的key请自定义)
            vm.cached = {
                MESSAGE_APPLY_TYPE_FOR_RESERVE: {},
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
                $log.debug("postData",postData);
                $http.post(vm.url, $httpParamSerializerJQLike(postData))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            vm.pagination = data.object;
                            $log.debug("pagination",vm.pagination);
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


            function queryCleearCfgDetail(bean) {
                var item = angular.copy(bean);

                OpenService({
                    modalTitle: '查看明细',
                    modalBody: 'toDetail',
                    url: '#',
                    item: item,
                    cached: {
                        MESSAGE_APPLY_TYPE_FOR_RESERVE: vm.cached.MESSAGE_APPLY_TYPE_FOR_RESERVE
                    }
                }, function (item) {
                }, '', 'myModalNoSave.html');
            }
            
            function applyForm() {
                var applyMsgType = vm.queryBean.applyMsgType;
                if(applyMsgType =="" || applyMsgType ==null){
                    alert("请选择申请报文类型");
                    return;
                }
                //组织参数：查询条件 + 分页数据
                var postData = vm.queryBean;
                postData.beginTime = null;
                postData.endTime = null;
                $http.post('apply', $httpParamSerializerJQLike(postData))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            alert(data.msg);
                        } else {
                            alert(data.msg);
                        }
                    });
            }
        }]);
