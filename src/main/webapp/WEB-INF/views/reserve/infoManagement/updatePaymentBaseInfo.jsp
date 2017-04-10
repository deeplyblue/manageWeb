<%--
  User: JinXin
  Date: 2016/12/21
  更新支付机基本信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<div common-modal-form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                    机构编号：
                </td>
                <td>
                    <input type="text" name="orgNo" ng-model="vm.item.orgNo" class="{{vm.constant.inputClass}} " readonly="readonly" required  ng-pattern="/^[\w]*$/"  maxlength="14"/>
                </td>
                <td class="text-right">
                    机构名称：
                </td>
                <td>
                    <input type="text" name="orgName" ng-model="vm.item.orgName" class="{{vm.constant.inputClass}} "  required   maxlength="90"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构类型：
                </td>
                <td>
                    <select  name="orgType" ng-model="vm.item.orgType" class="{{vm.constant.inputClass}} "  name="orgType"
                            ng-options="key as value for (key,value) in {'01':'支付机构','02':'分公司'}" required>
                        <option value="">请选择类型</option>
                    </select>
                </td>
                <td class="text-right">
                    组织机构代码：
                </td>
                <td>
                    <input type="text" name="orgCode" ng-model="vm.item.orgCode" class="{{vm.constant.inputClass}} " required  maxlength="35"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    许可证有效期：
                </td>
                <td>
                    <input  type="text" name="licenseDate" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                            ng-model="vm.item.licenseDate" required />
                </td>
                <td class="text-right">
                    许可证编号：
                </td>
                <td>
                    <input type="text" name="licenseNO" ng-model="vm.item.licenseNO" class="{{vm.constant.inputClass}} "  required  ng-pattern="/^[\w]*$/" maxlength="40"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    许可证发放日期：
                </td>
                <td>
                    <input  type="text" name="licenseGrantDate" class="{{vm.constant.inputClass}}"  uib-datepicker-popup="yyyy-MM-dd"
                            ng-model="vm.item.licenseGrantDate" required />
                </td>
                <td class="text-right">
                    营业执照编号：
                </td>
                <td>
                    <input type="text" name="businessCode" ng-model="vm.item.businessCode" class="{{vm.constant.inputClass}} "  required ng-pattern="/^[\w]*$/" maxlength="35"/>
                </td>
            </tr>
            <tr>

                <td class="text-right">
                    住所：
                </td>
                <td>
                    省:<select ng-model="vm.pro" ng-options="item as item.areaName for item  in vm.cached.ALL_CITY" class="{{vm.constant.inputClass}} "
                              ng-change="vm.item.addrProvince = vm.pro.areaName"  >
                </select>
                    </td>
                <td>
                        市:<select  ng-model="vm.city" ng-options="item as item.cityName for item in vm.pro.citys" class="{{vm.constant.inputClass}} "
                                  ng-change="vm.item.addrCity = vm.city.cityName" >
                    </select>
                        <input type="hidden"  ng-model="vm.item.addrProvince" class="{{vm.constant.inputClass}} "  />
                        <input type="hidden" name="addrCity" ng-model="vm.item.addrCity" class="{{vm.constant.inputClass}} "  />
                </td>
                <td>详细：
                    <input type="text" name="addr" ng-model="vm.item.addr" class="{{vm.constant.inputClass}} " required   maxlength="100"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    实际经营地：
                </td>
                <td>
                    省:<select ng-model="vm.realpro" ng-options="item as item.areaName for item  in vm.cached.ALL_CITY" class="{{vm.constant.inputClass}} "
                              ng-change="vm.item.realProvince = vm.realpro.areaName"  >
                </select>
                </td><td>
                    市:<select  ng-model="vm.realcity" ng-options="item as item.cityName for item in vm.realpro.citys" class="{{vm.constant.inputClass}} "
                               ng-change="vm.item.realCity = vm.realcity.cityName" >
                </select>
                   <input type="hidden" name="realProvince" ng-model="vm.item.realProvince" class="{{vm.constant.inputClass}} "  />
                    <input type="hidden" name="realCity" ng-model="vm.item.realCity" class="{{vm.constant.inputClass}} "  />
            </td>
                <td>详细：
                    <input type="text" name="realAddr" ng-model="vm.item.realAddr" class="{{vm.constant.inputClass}} " required  maxlength="100"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    邮编：
                </td>
                <td>
                    <input type="text" name="zipCode" ng-model="vm.item.zipCode" class="{{vm.constant.inputClass}} " required ng-pattern="/^[0-9]*$/" maxlength="10"/>
                </td>
                <td class="text-right">
                    注册资本：
                </td>
                <td>
                    <input type="text" name="registeredCapital" ng-model="vm.item.registeredCapital" class="{{vm.constant.inputClass}} " required ng-pattern="/^(0|[1-9][0-9]{0,15})(\.[0-9]{2})$/" maxlength="20" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    组织形式：
                </td>
                <td>
                    <input type="text" name="orgForm" ng-model="vm.item.orgForm" class="{{vm.constant.inputClass}} "  required maxlength="100"/>
                </td>
                <td class="text-right">
                    实缴货币资本：
                </td>
                <td>
                    <input type="text" name="moneyCapital" ng-model="vm.item.moneyCapital" class="{{vm.constant.inputClass}} " required  ng-pattern="/^(0|[1-9][0-9]{0,15})(\.[0-9]{2})$/" maxlength="20" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    法定代表人：
                </td>
                <td>
                    <input type="text" name="legalPerson" ng-model="vm.item.legalPerson" class="{{vm.constant.inputClass}} " required  maxlength="90"/>
                </td>
                <td class="text-right">
                    投诉电话：
                </td>
                <td>
                    <input type="text" name="complainTel" ng-model="vm.item.complainTel" class="{{vm.constant.inputClass}} " required ng-pattern="/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/" style="ime-mode:disabled" maxlength="30"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    传真：
                </td>
                <td>
                    <input type="text" name="fax" ng-model="vm.item.fax" class="{{vm.constant.inputClass}} " required
                           ng-pattern="/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/"
                           style="ime-mode:disabled" maxlength="30"/>

                </td>
                <td class="text-right">
                    审计机构名称：
                </td>
                <td>
                    <input type="text" name="auditName" ng-model="vm.item.auditName" class="{{vm.constant.inputClass}} " required maxlength="50" />
                </td>
            </tr>

            <%--股东名称,持股比例,身份证号--%>
            <tr>
                <td colspan="4" style="text-align: center">出资人及持股比例
                    <input type="hidden" name="sponsor"/>
                </td>

            </tr>
            <tr>
                <td>股东名称</td>
                <td>持股比例(%)</td>
                <td>身份证号</td>
                <td>操作</td>
            </tr>
            <tr ng-repeat="bean in vm.item.sponsorList track by $index">
                <td>
                    <input type="text" name="shareholderName" class="{{vm.constant.inputClass}}" ng-model="bean.shareholderName"/>
                </td>
                <td>
                    <input type="text" name="shareholding" class="{{vm.constant.inputClass}}" ng-model="bean.shareholding" />
                </td>
                <td>
                    <input type="text" name="idNo" class="{{vm.constant.inputClass}}" ng-model="bean.idNo" />
                </td>
                <td>
                    <button ng-click="vm.addSponsor($index)" title="增加">增加</button>
                    <button ng-click="vm.deleteSponsor($index)" title="删除">删除</button>
                </td>
            </tr>
            <tr>
                <td colspan="4" style="text-align: center">主要出资人及持股比例
                    <input type="hidden" name="mastereSponsor"  />
                </td>
            </tr>
            <tr>
                <td>股东名称</td>
                <td>持股比例(%)</td>
                <td>身份证号</td>
                <td>操作</td>
            </tr>
            <tr ng-repeat="bean in vm.item.mastereSponsorList track by $index">
                <td>
                    <input type="text" name="shareholderName" class="{{vm.constant.inputClass}}" ng-model="bean.shareholderName"/>
                </td>
                <td>
                    <input type="text" name="shareholding" class="{{vm.constant.inputClass}}" ng-model="bean.shareholding" />
                </td>
                <td>
                    <input type="text" name="idNo" class="{{vm.constant.inputClass}}" ng-model="bean.idNo" />
                </td>
                <td>
                    <button ng-click="vm.addMastereSponsor($index)" title="增加">增加</button>
                    <button ng-click="vm.deleteMastereSponsor($index)" title="删除">删除</button>
                </td>
            </tr>


            <tr>
                <td colspan="4" style="text-align: center">业务信息
                </td>
            </tr>

            <td >业务种类</td>
            <td colspan="2">业务范围</td>
            <td >操作</td>

        <tr ng-repeat="bean in vm.item.serviceList track by $index">
                <td>
                    <select name="serviceType" ng-model="bean.serviceType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.SERVICE_TYPE_FOR_RESERVE" >
                        <option value="">请选择</option>
                    </select>
                </td>
                <td colspan="2" >
                    <span ng-repeat="beanRange in bean.serviceRange track by $index">
                        <select name="range" ng-model="beanRange.range" class="{{vm.constant.inputClass}}"
                                ng-options="key as value for (key,value) in vm.cached.SERVICE_RANGE_FOR_RESERVE" >
                        <option value="">请选择</option>
                    </select>
                        <button ng-click="vm.addServiceRange($index,bean)">增加业务范围</button>
                        <button ng-click="vm.deleteServiceRange($index,bean)">删除业务范围</button>
                    </span>
                </td>
                <td>
                    <button ng-click="vm.addServiceType($index)" title="增加">增加业务种类</button>
                    <button ng-click="vm.deleteServiceType($index)" title="删除">删除业务种类</button>
                </td>
            </tr>
            <tr>
                <td class="text-right" >
                    备注：
                </td>
                <td colspan="3" class="col-xs-3">
                    <textarea  name="remarks" class="{{vm.constant.inputClass}}" ng-model="vm.item.remarks" required maxlength="500"></textarea>
                </td>

            </tr>
        </table>
    </div>
</body>
</html>
