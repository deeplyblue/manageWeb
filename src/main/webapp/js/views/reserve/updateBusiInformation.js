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
            BUSINESS_STATUS_FOR_RESERVE:{}
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
                var  merchantNO=vm.queryBean.merchantNO;
                var  merchantType=vm.queryBean.operateType;
                if ((merchantNO == null || merchantNO == '' || merchantNO == undefined)) {
                   if(merchantType == null || merchantType== undefined || merchantType == ''){
                       if(!confirm("未填写商户编号时，查询更新全量数据，是否确认操作？")){
                           return;
                       }
                       vm.queryBean.pageSize = vm.pagination.pageSize;
                       vm.queryBean.pageNum = vm.pagination.pageNum;
                       vm.pagination = vm.queryBean;
                       $log.debug("pagination",vm.pagination);
                       $http.post(vm.url, $httpParamSerializerJQLike(vm.pagination))
                           .then(function (response) {
                               var data = response.data;
                               alert(data.msg);
                           });
                   }else{
                       alert("填写完整的更新条件！");
                       return;
                   }

                }else {
                    if (merchantType == null || merchantType == undefined || merchantType == '') {
                        alert("填写完整的更新条件！");
                        return;
                    } else {
                        vm.queryBean.pageSize = vm.pagination.pageSize;
                        vm.queryBean.pageNum = vm.pagination.pageNum;
                        vm.pagination = vm.queryBean;
                        $log.debug("pagination",vm.pagination);
                        $http.post(vm.url, $httpParamSerializerJQLike(vm.pagination))
                            .then(function (response) {
                                var data = response.data;
                                alert(data.msg);
                            });
                    }
                }
            };

        function resetForm() {
            vm.queryBean = {};
        }


    }]);