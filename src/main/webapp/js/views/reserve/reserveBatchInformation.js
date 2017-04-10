angular.module('myApp')

    .controller('queryCtrl', ['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'myConstant', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                                CacheService, myConstant) {
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

            vm.queryDetail = queryDetail;
            vm.resetForm = resetForm;

            /*------------------以上配置通用---------------------*/


            vm.queryInformationDetail = queryInformationDetail;
            vm.audit = audit;
            vm.reGenerate = reGenerate;
            vm.detailType = detailType;

            vm.getCache = function (key) {
                return CacheService.getCache(key);
            };
            /*------------------以上方法名可选择性通用---------------------*/


            //缓存数据初始化(需要缓存的key请自定义)
            vm.cached = {
                ACCOUNT_TYPE: {},
                MESSAGE_TYPE_FOR_RESERVE: {},
                MESSAGE_APPLY_TYPE_FOR_RESERVE: {},
                APPLY_STATUS_FOR_RESERVE:{},
                COMPLAINT_TYPE_FOR_RESERVE:{},
                MAIN_CONN_FOR_RESERVE:{},
                OFF_STATUS_FOR_RESERVE:{},
                ACCT_TYPE:{},
                IS_CROSS_TRANS:{},
                BANK_TYPE:{},
                SERVICE_TYPE_FOR_RESERVE:{},
                SERVICE_RANGE_FOR_RESERVE:{},
                NON_SETTLE_FOR_RESERVE:{},
                HANDLE_STATUS_FOR_RESERVE:{},
                BUSINESS_UP_FOR_RESERVE:{},
                BASE_DATA_FOR_RESERVE:{},
                BASE_INFO_FOR_RESERVE:{},
                REPORT_MONTH_FOR_RESERVE:{},
                REPORT_QUARTER_FOR_RESERVE:{},
                REPORT_YEAR_FOR_RESERVE:{},
                SYSTEM_NOTICE_FOR_RESERVE:{},
                SYSTEM_CONTROL_FOR_RESERVE:{},
                TYPE_FOR_RESERVE:{}
            };

            CacheService.initCaches(vm.cached);


            function queryDetail() {
                var queryForm = document.getElementById('queryForm');
                vm.url = angular.element(queryForm).prop('action');

                //组织参数：查询条件 + 分页数据
                var postData = angular.copy(vm.queryBean);
                postData.pageSize = vm.pagination.pageSize;
                postData.pageNum = vm.pagination.pageNum;
                vm.pagination.queryBean = postData;
                $http.post(vm.url, vm.pagination, config.jsonHeader)
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


            function detailType(){
                vm.temp.DETAIL_TYPE=vm.cached[vm.temp.messageType];
            }

            function queryInformationDetail(bean) {

                $http.post(config.ctx + "/reserve/batch/query/detail", bean, config.jsonHeader)
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            var item = data.object;

                            $log.debug("响应信息:{}", item);
                            OpenService({
                                modalTitle: '查看明细',
                                modalBody: 'toDetail?messageType=' + bean.messageType,
                                p: '2',
                                url: '#',
                                item: item,
                                cached: {
                                    ALL_CITY: vm.cached.ALL_CITY,
                                    HANDLE_STATUS_FOR_RESERVE:vm.cached.HANDLE_STATUS_FOR_RESERVE,
                                    SERVICE_TYPE_FOR_RESERVE: vm.cached.SERVICE_TYPE_FOR_RESERVE,
                                    SERVICE_RANGE_FOR_RESERVE: vm.cached.SERVICE_RANGE_FOR_RESERVE,
                                    MESSAGE_APPLY_TYPE_FOR_RESERVE:vm.cached.MESSAGE_APPLY_TYPE_FOR_RESERVE,
                                    APPLY_STATUS_FOR_RESERVE:vm.cached.APPLY_STATUS_FOR_RESERVE,
                                    MAIN_CONN_FOR_RESERVE:vm.cached.MAIN_CONN_FOR_RESERVE,
                                    OFF_STATUS_FOR_RESERVE:vm.cached.OFF_STATUS_FOR_RESERVE,
                                    COMPLAINT_TYPE_FOR_RESERVE:vm.cached.COMPLAINT_TYPE_FOR_RESERVE,
                                    ACCT_TYPE:vm.cached.ACCT_TYPE,
                                    IS_CROSS_TRANS:vm.cached.IS_CROSS_TRANS,
                                    NON_SETTLE_FOR_RESERVE:vm.cached.NON_SETTLE_FOR_RESERVE,
                                    BANK_TYPE:vm.cached.BANK_TYPE
                                }
                            }, function (item) {
                            }, '', 'myModalNoSave.html');
                            $log.debug("响应信息:{}", item);
                        } else {
                            alert(data.msg);
                        }
                    });

            };

            function audit(status) {
                if (CheckboxService.getCheckedNew(vm.pagination.list).length != 1) {
                    alert("请选择一条记录！");
                    return;
                }
                var temp = CheckboxService.getCheckedNew(vm.pagination.list)[0];
                temp.messageStatus = status;
                $http.post(config.ctx + "/reserve/batch/audit/detail", temp, config.jsonHeader)
                    .then(function (response) {
                        $log.info("审核响应信息:{}", response);
                        var data = response.data;
                        $log.info("审核响应信息1:{}", data);
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert(data.msg);
                        }
                    });

            }

            function reGenerate(item){
                $http.post(config.ctx + "/reserve/batch/regenerate", item, config.jsonHeader)
                    .then(function (response) {
                        $log.info("重新生成响应信息:{}", response);
                        var data = response.data;
                        $log.info("重新生成响应信息1:{}", data);
                        if (data.success) {
                            alert("操作成功");
                        } else {
                            alert(data.msg);
                        }
                    });
            }


            function resetForm() {
                vm.queryBean = {};
            }

        }]);
