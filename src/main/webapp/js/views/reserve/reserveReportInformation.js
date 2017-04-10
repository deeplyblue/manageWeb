

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


        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
            BUSINESS_STATUS_FOR_RESERVE:{},
            BUSINESS_TYPE_FOR_RESERVE:{},
            BUSINESS_AGENT_SETTLEMENT_RESERVE:{},
            DELETE_STATUS:{},
            COMANY_CODE:{}
        };
            CacheService.initCache(vm.cached);

        /*将前面缓存的数据格式化
         [{key:value},{key:value}]*/
        vm.getCache = function (key) {
            return CacheService.getCache(key);
        };

            vm.queryBean = {};
        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;

        /*------------------以上配置通用---------------------*/


        /*------------------以上方法名可选择性通用---------------------*/

        vm.queryInformtionDetail = queryInformtionDetail;

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
                vm.queryBean.pageSize = vm.pagination.pageSize;
                vm.queryBean.pageNum = vm.pagination.pageNum;
                vm.queryBean.beginTime = dateFilter(vm.queryBean.startDate, 'yyyy-MM-dd');
                vm.queryBean.endTime = dateFilter(vm.queryBean.endDate, 'yyyy-MM-dd');

                vm.pagination = vm.queryBean;
                $log.debug("pagination",vm.pagination);
                $http.post(vm.url, $httpParamSerializerJQLike(vm.pagination))
                    .then(function (response) {
                        var data = response.data;
                        if (data.success) {
                            vm.pagination = data.object;
                            vm.sumObject = data.sumObject;
                        } else {
                            alert(data.msg);
                        }
                    });
            };


            function queryInformtionDetail(bean) {
                var item=bean;
                OpenService({
                    modalTitle: '查看明细',
                    modalBody: 'toDetail',
                    url: '#',
                    item: item,
                    cached: {
                        BUSINESS_STATUS_FOR_RESERVE: vm.cached.BUSINESS_STATUS_FOR_RESERVE,
                        BUSINESS_AGENT_SETTLEMENT_RESERVE:vm.cached.BUSINESS_AGENT_SETTLEMENT_RESERVE,
                        DELETE_STATUS:vm.cached.DELETE_STATUS,
                        BUSINESS_TYPE_FOR_RESERVE: vm.cached.BUSINESS_TYPE_FOR_RESERVE
                    }
                }, function (item) {
                }, '', 'myModalNoSave.html');
            }

        function resetForm() {
            vm.queryBean = {};
        }


    }]);