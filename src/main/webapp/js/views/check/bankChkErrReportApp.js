/**
 * Created by 蒯越 on 2016/7/7.
 */
angular.module('myApp')

    .controller('queryCtrl',['$scope', '$http', '$httpParamSerializerJQLike', '$log', 'OpenService', 'CheckboxService',
        'CacheService', 'dateFilter', 'myConstant', 'DateCalculateService', function ($scope, $http, $httpParamSerializerJQLike, $log, OpenService, CheckboxService,
                                       CacheService, dateFilter, myConstant, DateCalculateService) {
        //视图层变量viewModel
        var vm = $scope.vm = {};
        vm.constant = myConstant;
        vm.downCfg = {
            contentType: 'jqLike',
            date : {
                'oDate' : 'yyyyMMdd'
            }
        }
        //变量初始化
        //分页数据
        vm.result = {
        };
        //查询条件
        vm.queryBean = {
            oDate: DateCalculateService.getYesterday(),
        };

        vm.validateOptions = {
            blurTrig: true,
            showError: function (elem, errorMessages) {
                // growl.addErrorMessage(errorMessages);
                // angular.element(elem).after('<span>' + errorMessages + '</span>');
            },
            removeError: true
        };

        vm.queryDetail = queryDetail;
        vm.resetForm = resetForm;

        vm.cached = {
            MCHNT_BUSI_TYPE: {}
        };

        CacheService.initCache(vm.cached);

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

        function queryDetail() {
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            vm.postData = vm.queryBean;
            vm.postData.sSettleDate = dateFilter(vm.queryBean.oDate, 'yyyyMMdd');
            $http.post(vm.url, $httpParamSerializerJQLike(vm.postData))
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.result = data.object;
                        vm.sumObject = data.sumObject;
                        //清空选中记录
                        CheckboxService.clearChecked();
                    } else {
                        alert(data.msg);
                    }
                });
        }

        function resetForm() {
            vm.queryBean = {
                oDate: DateCalculateService.getYesterday()
            };
        }
    }]);
