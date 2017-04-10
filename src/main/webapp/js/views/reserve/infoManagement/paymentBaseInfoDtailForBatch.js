/**
 * Created by jinxin on 2016/12/20.
 * 支付机构基本信息管理详情
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




        /*------------------以上方法名可选择性通用---------------------*/

        /*CacheService.initCache(vm.cached, function (cacheKey, cacheObj) {
         $log.debug(cacheKey, cacheObj);
         vm.cached[cacheKey] = cacheObj;
         });*/

        /*vm.getCache = function (key) {
         CacheService.getCache(key)
         }*/
// 点击查询生效
            var pro;
            var proName;
            var city;
            var cityName;
            var realpro;
            var realproName;
            var realcity;
            var realcityName;
            if(item.addrProvince!=null&&item.addrProvince!=""){
                console  .info(item.addrProvince);
                pro = vm.cached.ALL_CITY.filter(function (it) {
                    console.info(item.addrProvince);
                    if (item.addrProvince && it.areaCode == item.addrProvince){
                        proName=it.areaName;
                        return true;
                    }
                })[0];
                city = pro.citys.filter(function (it) {
                    if (item.addrCity && it.cityCode == item.addrCity){
                        cityName=it.cityName;
                        return true;
                    }
                })[0];
            }
            if(item.realProvince!=null&&item.realProvince!="") {
                realpro = vm.cached.ALL_CITY.filter(function (it) {
                    if (item.realProvince && it.areaCode == item.realProvince) {
                        realproName=it.areaName;
                        return true;
                    }
                })[0];
                //回显
                realcity = realpro.citys.filter(function (it) {
                    if (item.realCity && it.cityCode == item.realCity) {
                        realcityName=it.cityName;
                        return true;
                    }
                })[0];
            }

    }]);







