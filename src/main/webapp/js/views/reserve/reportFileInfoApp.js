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


        //缓存数据初始化(需要缓存的key请自定义)
        /*数据格式{
         key1 :value1,
         key2:value2
         }*/
        vm.cached = {
        };

        (function initCache() {
            vm.queryBean = {};
            var temp = new Date();
            temp.setDate(temp.getDate()-1);
            var now= new Date(temp.getFullYear(),temp.getMonth(),temp.getDate());
            vm.queryBean.beginDate =now;
            vm.queryBean.endDate =now;
            $log.debug("当前日期"+vm.queryBean.beginDate);
            for (var cacheKey in vm.cached) {
                CacheService.initCache(cacheKey, function (cacheKey, cacheObj) {
                    $log.debug(cacheKey, cacheObj);
                    vm.cached[cacheKey] = cacheObj;
                })
            }
        }());

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


        vm.updateChecked = updateChecked;
        vm.addItem = addItem;
        vm.updateItem = updateItem;
        vm.deleteItem = deleteItem;
        vm.downFile= downFile;
        vm.resetGenerateReport= resetGenerateReport;

        /*------------------以上方法名可选择性通用---------------------*/

        /*CacheService.initCache(vm.cached, function (cacheKey, cacheObj) {
         $log.debug(cacheKey, cacheObj);
         vm.cached[cacheKey] = cacheObj;
         });*/

        /*vm.getCache = function (key) {
         CacheService.getCache(key)
         }*/

        function downFile(item){
            location.href = config.ctx + '/report/file/downloadReportFile?groupName='+item['groupName']+'&dfsFilePath='+item['dfsFilePath']+'&orgFileName='+item['orgFileName'];
        }

        function resetGenerateReport(item){
            var headersConfig = {headers:{
                'Content-Type': 'application/json'
            }};
            //var bean = item;
            $http.post(config.ctx+"/report/file/reset", item,headersConfig)
                .then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        vm.pagination.list.splice(vm.pagination.list.indexOf(item), 1);
                    } else {
                        alert(data.msg);
                    }
                });
        }

        function queryDetail() {
            $log.debug("当前日期"+vm.queryBean.beginDate);
            var queryForm = document.getElementById('queryForm');
            vm.url = angular.element(queryForm).prop('action');

            //组织参数：查询条件 + 分页数据
            vm.postData = vm.queryBean;
            //vm.postData.pageSize = vm.pagination.pageSize;
            //vm.postData.pageNum = vm.pagination.pageNum;
            //vm.postData.beginTime = dateFilter(vm.postData.beginTime,'yyyy-MM-dd');
            //vm.postData.endTime = dateFilter(vm.postData.endTime,'yyyy-MM-dd');
            vm.queryBean.pageSize = vm.pagination.pageSize;
            vm.queryBean.pageNum = vm.pagination.pageNum;
            vm.pagination.queryBean = vm.queryBean;
            var headersConfig = {headers:{
                'Content-Type': 'application/json'
            }};
            $log.debug("当前日期"+vm.queryBean.beginDate);
            $log.debug("当前日期"+JSON.stringify(vm.pagination));
            $log.debug("当前日期"+JSON.stringify(vm.queryBean));
            $http.post(vm.url, vm.pagination,headersConfig)
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

        function updateChecked($event, id) {
            CheckboxService.updateChecked($event, id);
            $log.debug(id);
            $log.debug(CheckboxService.getChecked());
        }

        function addItem() {
            OpenService({
                modalTitle: '新增备付金信息',
                modalBody: 'toAdd',
                url: 'add',
                item: {},
                cached: {

                }
            }, function (item) {
                vm.pagination.list.push(item);
            });
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
