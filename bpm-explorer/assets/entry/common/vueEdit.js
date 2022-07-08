
/**
 *  @基础服务： http 请求封装等
 *  @tools ：常用工具类
 *	@表单服务：表单相关的组件，表单校验服务指令，表单权限服务指令等
 */

import 'babel-polyfill'
import Vue from 'vue'
// 引入 ctx 配置
import appConfig from '../../app-conf.js'

import BaseService from '../../vue/common/baseService'
import Tools from '../../vue/common/tools'
import App from '../../vue/custVue/APP.vue'
import FormService from '../../vue/service/form/formService'

Vue.use(Tools)
Vue.use(BaseService)
Vue.use(FormService)
// 引入一个 公共组件的案例
Vue.component('abApp', App);
window.Vue = Vue;