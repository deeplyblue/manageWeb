/**
 * Created by lupf on 2016/5/3.
 */

/*
 初始化应用,
 加载默认配置及服务*/
angular.module('myApp', ['ui.bootstrap', 'ngAnimate', 'angularFileUpload', 'angular-growl', 'w5c.validator', 'angular.filter'])

/*    .constant('datepicker.dateOptions', {
 formatYear: 'yy',
 startingDay: 1,
 formatDayTitle: 'yyyy MMMM'
 })*/

// .constant('inputClass', 'form-control input-sm')

    .factory('myHttpInterceptor', ['$q', 'growl', '$log', 'CheckboxService', function ($q, growl, $log, CheckboxService) {
        return {
            'request': function (config) {
                // do something on success
                if (config.url.contains('search')) {
                    growl.addServerMessages(
                        [{
                            msg: '请求加载中---',
                            'severity-level': 'info'
                        }]);

                    // $log.debug(config);
                } else {
                }

                CheckboxService.clearCheckedNew(config);
                return config;
            },
            'requestError': function (rejection) {
                // do something on error

                growl.addServerMessages(
                    [{
                        msg: '请求未能成功发出---',
                        'severity-level': 'error'
                    }]);

                // $log.debug(rejection);
                return $q.reject(rejection);
            },
            'response': function (response) {
                // do something on success
                if (response.config.url.contains('search')) {
                    growl.addServerMessages(
                        [{
                            msg: '请求响应成功---',
                            'severity-level': 'success'
                        }]);

                    // $log.debug(response);
                } else {
                }
                return response;
            },
            'responseError': function (rejection) {
                // do something on error

                growl.addServerMessages(
                    [{
                        msg: '请求响应失败---',
                        'severity-level': 'warn'
                    }]);

                // $log.debug(rejection);
                return $q.reject(rejection);
            }
        }
    }])

    .constant('myConstant', {
        'datepicker': {
            dateOptions: {
                formatYear: 'yy',
                startingDay: 1,
                formatDayTitle: 'yyyy MMMM',
                closeText: 'close'
            }
        },
        'inputClass': 'form-control input-sm form-control-inline'
    })

    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

        /*$httpProvider.defaults.transformResponse.push(function (responseData) {
         console.log(responseData);
         if (responseData.success) {
         return responseData;
         } else {
         throw responseData;
         }
         });

         $httpProvider.interceptors.push(function () {
         return {
         responseError: function (response) {
         if (response) {
         if (response.hasOwnProperty("code")) {
         if (response.code > 0) {
         alert(response.code);
         } else {
         alert("系统维护中，请稍候重试");
         }

         } else {
         if (response.status == 404) {
         alert("抱歉，后台服务出错,找不到对应的接口");
         } else {
         alert("抱歉，后台服务出错");
         }
         }
         }
         }
         }
         });*/
    }])

    .config(["growlProvider", "$httpProvider", function (growlProvider, $httpProvider) {
        growlProvider.globalTimeToLive(3000);
        growlProvider.messagesKey("success");
        growlProvider.messageTextKey("msg");
        growlProvider.messageSeverityKey("severity-level");
        growlProvider.onlyUniqueMessages(true);
        $httpProvider.interceptors.push('myHttpInterceptor');
    }])

    .config(["w5cValidatorProvider", function (w5cValidatorProvider) {

        // 全局配置
        w5cValidatorProvider.config({
            blurTrig: true,
            showError: function (element, errorMsg) {

                if (angular.element('.modal-body')) {
                    if (!angular.element(element).next().hasClass('errorInfo')) {
                        var newTag = document.createElement('span');
                        angular.element(newTag).attr('style', 'color:red;').addClass('errorInfo')
                            .html(' <i class="glyphicon glyphicon-minus-sign"></i>' + ' ' + errorMsg
                            );
                        angular.element(element).after(newTag);
                    }
                    return false;
                }

                $(element).tooltip({
                    title: errorMsg,
                    animation: false,
                    placement: 'right'
                });

                $(element).tooltip('show');
            },
            removeError: function (element) {
                if (angular.element('.modal-body')) {
                    var nextTag = angular.element(element).next();
                    if (nextTag.hasClass('errorInfo')) {
                        nextTag.remove();
                    }

                    return false;
                }

                $(element).tooltip('destroy');
            }

        });

        w5cValidatorProvider.setRules({
            /*input的name : {
             验证属性 : 提示内容
             }*/
            email: {
                required: "输入的邮箱地址不能为空",
                email: "输入邮箱地址格式不正确"
            },
            userName: {
                required: "输入的用户名不能为空",
                pattern: "用户名必须输入字母、数字、下划线,以字母开头",
                w5cuniquecheck: "输入用户名已经存在，请重新输入"
            },
            userPwd: {
                required: "密码不能为空",
                pattern: "密码长度8-16位,且必须包含大写字母、小写字母、数字、特殊字符中至少三种",
                minlength: "密码长度不能小于{minlength}",
                maxlength: "密码长度不能大于{maxlength}"
            },
            repeatPassword: {
                required: "重复密码不能为空",
                repeat: "两次密码输入不一致"
            },
            userMobile: {
                pattern: "手机号必须为11位数字"
            },
            number: {
                required: "数字不能为空",
                pattern: "数字格式错误"
            },
            customizer: {
                customizer: "自定义验证数字必须大于上面的数字"
            },
            dynamicName: {
                required: "动态Name不能为空"
            },
            dynamic: {
                required: "动态元素不能为空"
            }
        });
    }])
    /**
     * 给必填字段加上红色*
     */
    .config(['$provide', function ($provide) {
        $provide.decorator('requiredDirective', ['$delegate', function ($delegate) {
            var directive = $delegate[0];
            var link = directive.link;

            directive.compile = function () {
                return function (scope, element, attrs) {
                    link.apply(this, arguments);
                    element.after('<span style="color: red;font-weight: bold"> *</span>');
                }
            }

            return $delegate;
        }])
    }])
    /**
     * 日期控件配置修改
     * 置为只读
     * 点击展示
     */
    .config(['$provide', function ($provide) {
        $provide.decorator('uibDatepickerPopupDirective', ['$delegate', 'myConstant', function ($delegate, myConstant) {
            var directive = $delegate[0];
            var link = directive.link;
            directive.compile = function () {
                return function (scope, element, attrs) {
                    scope.datepickerOptions = myConstant.datepicker.dateOptions;
                    element.attr('readonly', true);
                    link.apply(this, arguments);
                    $(element).on('click', function () {
                        scope.isOpen = !scope.isOpen;
                        scope.$apply();
                    })
                }
            }

            return $delegate;
        }])
    }])
    //分页信息汉化
    .config(['$provide', function ($provide) {
        $provide.decorator('uibPaginationDirective', ['$delegate', function ($delegate) {
            var directive = $delegate[0];
            var link = directive.link;
            directive.compile = function () {
                return function (scope, element, attrs) {
                    attrs.firstText = '首页';
                    attrs.lastText = '尾页';
                    attrs.previousText = '前一页';
                    attrs.nextText = '后一页';
                    link.apply(this, arguments);
                }
            }

            return $delegate;
        }])
    }])
    .factory('OpenService', ['$http', '$uibModal', '$log', function ($http, $uibModal, $log) {
        /*options = {
         // 打开页面的标题
         modalTitle: '新增用户',
         // 请求的地址，请求与当前页面同父目录，使用相对地址
         modalBody: 'toAdd',
         // 打开后页面提交地址【save】
         url: 'add',
         // 向打开后页面传递的model
         item: {}
         }*/
        return function (options, callback, ctrl, template) {
            if (options.modalTitle && options.modalBody && options.url) {
                if (!ctrl) {
                    ctrl = 'ModalInstanceCtrl';
                }
                if (!template) {
                    template = 'myModalContent.html';
                }
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: template,
                    controller: ctrl,
                    size: 'lg',
                    resolve: {
                        modalItem: function () {
                            return options;
                        }
                    }
                });
                modalInstance.result.then(callback, function () {
                    if (angular.isFunction(callback)) {
                        callback();
                    }
                    //$log.debug('cancel');
                })
            }
        };
    }])
    .factory('BoxService', ['$uibModal', '$log', function ($uibModal, $log) {
        /*options = {
         // 打开页面的标题
         modalTitle: '新增用户',
         // 请求的地址，请求与当前页面同父目录，使用相对地址
         modalBody: 'toAdd',
         // 打开后页面提交地址【save】
         url: 'add',
         // 向打开后页面传递的model
         item: {}
         }*/
        var open = function (template, msg) {
            return $uibModal.open({
                animation: true,
                templateUrl: template,
                controller: 'BoxServiceModalInstanceCtrl',
                size: 'sm',
                resolve: {
                    modalItem: function () {
                        return msg;
                    }
                }
            })
        }
        var BoxService = {
            alert: function (msg, callback) {
                var modalInstance = open('alertModalContent.html', msg);
                modalInstance.result.then(function (result) {
                    if (angular.isFunction(callback)) {
                        callback(result);
                    }
                }, function () {
                    $log.debug('dismiss');
                })

            },
            confirm: function (msg, callback) {
                var modalInstance = open('confirmModalContent.html', msg);
                modalInstance.result.then(function () {
                    if (angular.isFunction(callback)) {
                        callback(true);
                    }
                }, function () {
                    if (angular.isFunction(callback)) {
                        callback(false);
                    }
                })
            },
            prompt: function (msg, callback) {
                var modalInstance = open('promptModalContent.html', msg);
                modalInstance.result.then(function (result) {
                    if (angular.isFunction(callback)) {
                        callback(result);
                    }
                }, function () {
                    $log.debug('dismiss');
                })
            }
        }
        return BoxService;
    }])
    .factory('CheckboxService', function () {
        var checked = [];

        function clear(json) {
            if (angular.isArray(json)) {
                for (var property in json) {
                    //console.debug(property);
                    clear(json[property]);
                }
            } else if (angular.isObject(json)) {
                //console.debug(json);
                if (json.hasOwnProperty('_checked')) {
                    delete json['_checked'];
                }

                if (!isOwnEmpty(json)) {
                    for (var property in json) {
                        if (angular.isArray(json[property])) {
                            for (var property2 in json[property]) {
                                //console.debug('1111',property2);
                                clear(json[property][property2]);
                            }
                        } else if (angular.isObject(json[property])) {
                            if (json[property].hasOwnProperty('_checked')) {
                                delete json[property]['_checked'];
                            }
                        }

                    }
                }
            }
        }

        function isOwnEmpty(obj) {
            for (var name in obj) {
                if (obj.hasOwnProperty(name)) {
                    return false;
                }
            }
            return true;
        };

        var CheckboxService = {
            updateChecked: function (event, id) {
                var checkbox = event.target;
                if (checkbox.checked && checked.indexOf(id) == -1) {
                    checked.push(id);
                } else if (!checkbox.checked && checked.indexOf(id) != -1) {
                    checked.splice(checked.indexOf(id), 1);
                }
            },
            getChecked: function () {
                return checked;
            },
            clearChecked: function () {
                checked = [];
            },
            getCheckedNew: function (list) {
                if (list instanceof Array) {
                    return list.filter(function (item) {
                        if (item._checked) {
                            return true;
                        }
                    })
                }
                return [];
            },
            // 删除对象中_checked属性
            clearCheckedNew: function (config) {
                if (config.headers['Content-Type']) {
                    if (config.headers['Content-Type'].contains('json')) {
                        clear(config.data);
                    } else if (config.headers['Content-Type'].contains('x-www-form-urlencoded') && !isOwnEmpty(config.data)) {
                        // console.log(config.data);
                        config.data = config.data.replace(/\b_checked\b=true/g, '');
                        config.data = config.data.replace(/\b_checked\b=false/g, '');
                        // delete config.data._checked;
                    }
                }
            }
        }

        return CheckboxService;
    })
    .factory('CacheService', ['$http', '$interval', '$log', '$httpParamSerializerJQLike', function ($http, $interval, $log, $httpParamSerializerJQLike) {
        var cached = new Object();

        var CacheService = {
            initCache: function (cacheKey, callback) {

                //批量获取缓存
                if (!callback && typeof cacheKey == 'object') {
                    var keys = [];
                    for (var key in cacheKey) {
                        keys.push(key);
                    }
                    $log.debug('批量查询缓存');
                    $http.post(config.ctx + '/base/cache/getMore', keys, config.jsonHeader)
                        .then(function (response) {
                            var tempMap = response.data.object;
                            for (var key in cacheKey) {
                                cacheKey[key] = tempMap[key];
                                cached[key] = tempMap[key];

                            }
                        }, function (response) {
                            $log.error('获取数据%s失败', keys);
                        })

                    return;
                }

                //单独获取缓存
                if (cached.hasOwnProperty(cacheKey)) {
                    return cached[cacheKey];
                } else {
                    cached[cacheKey] = [];
                    if (cached[cacheKey].length == 0) {
                        $log.debug('查询缓存');
                        $http.post(config.ctx + '/base/cache/get',
                            $httpParamSerializerJQLike({cacheKey: cacheKey}))
                            .then(function (response) {
                                cached[cacheKey] = response.data.object;
                                callback(cacheKey, cached[cacheKey]);
                            }, function (response) {
                                $log.error('获取数据%s失败', cacheKey);
                            })
                    }
                }
                /*}*/
            },
            initCaches: function (cacheKey, callback) {

                //批量获取缓存
                var keys = [];
                for (var key in cacheKey) {
                    keys.push(key);
                }
                $log.debug('批量查询缓存');
                $http.post(config.ctx + '/base/cache/getMore', keys, config.jsonHeader)
                    .then(function (response) {
                        var tempMap = response.data.object;
                        for (var key in cacheKey) {
                            if (!angular.isUndefined(tempMap[key])) {
                                cacheKey[key] = tempMap[key];
                                cached[key] = tempMap[key];
                            }
                        }
                        if (callback) {
                            callback();
                        }
                    }, function (response) {
                        $log.error('获取数据%s失败', keys);
                    })

                return;
            },
            getCache: function (key) {
                var arr = [];
                for (var temp in cached[key]) {
                    arr.push({'key': temp, 'value': cached[key][temp]});
                }
                return arr;
            },
            cast2Arr: function (obj) {
                if (obj instanceof Object) {
                    var arr = [];
                    for (var temp in obj) {
                        arr.push({'key': temp, 'value': obj[temp]});
                    }
                    return arr;
                } else {
                    return [];
                }
            },
            getObj: function (key, callback) {
                return $http.post(config.ctx + '/base/cache/getAll',
                    $httpParamSerializerJQLike({cacheKey: key}))
                    .then(function (response) {
                        $log.debug(response.data.object);
                        if (callback) {
                            callback(key, response.data.object)
                        }
                        return response.data.object;
                    }, function (response) {
                        $log.error('获取数据%s失败', cacheKey);
                    })
            }
        }
        return CacheService;
    }])
    .factory('DateCalculateService', ['$log', function ($log) {
        var DateCalculateService = {
            getToday: function () {
                var now = new Date();
                return new Date(now.getFullYear(), now.getMonth(), now.getDate());
            },
            getYesterday: function () {
                var now = this.getToday();
                var yes = now.setTime(now.getTime() - this.getTimes(1));
                return yes;
            },
            getTomorrow: function () {
                var now = this.getToday();
                var tom = now.setTime(now.getTime() + this.getTimes(1));
                return tom;
            },
            dayBefore: function (days) {
                var now = this.getToday();
                var temp = now.setTime(now.getTime() - this.getTimes(days));
                return temp;
            },
            dayAfter: function (days) {
                var now = this.getToday();
                var temp = now.setTime(now.getTime() + this.getTimes(days));
                return temp;
            },
            getTimes: function (days) {
                if (angular.isNumber(days)) {
                    return days * 24 * 60 * 60 * 1000;
                } else {
                    $log.error('date calculate failed:', days);
                }
            }
        }
        return DateCalculateService;
    }])
/*.filter("cacheFilter", function (CacheService) {
 var filterFun = function (input, cache) {
 CacheService.getCache(cache)[input];
 }
 return filterFun;
 })*/


/*
 加载插件
 打开新窗口,
 覆盖原有窗口*/
angular.module('myApp').controller('ModalInstanceCtrl', ['$scope', '$uibModalInstance', 'modalItem', '$http',
    '$httpParamSerializerJQLike', '$log', 'myConstant',
    function ($scope, $uibModalInstance, modalItem, $http, $httpParamSerializerJQLike, $log, myConstant) {

        var vm = $scope.vm = {
            modalTitle: 'please change the title',
            modalBody: '#',
            url: '',
            item: {}
        }

        vm = $scope.vm = modalItem;

        vm.constant = myConstant;

        $scope.save = function () {
            $http.post(vm.url, $httpParamSerializerJQLike(vm.item))
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

//弹框使用的通用controller
angular.module('myApp').controller('BoxServiceModalInstanceCtrl', ['$scope', '$uibModalInstance', 'modalItem', 'myConstant', function ($scope, $uibModalInstance, modalItem, myConstant) {

    var vm = $scope.vm = {
        msg: 'please change the title',
        result: ''
    }

    vm.msg = modalItem;

    vm.constant = myConstant;

    //alert
    $scope.ok = function () {
        $uibModalInstance.dismiss('cancel');
    }

    //prompt
    $scope.save = function () {
        $uibModalInstance.close(vm.result);
    };

    //confirm
    $scope.confirm = function () {
        $uibModalInstance.close(vm.result);
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);