import jquery from 'expose-loader?$!jquery';
// 配置
require('../../app-conf.js');

// css
require('../../css/bootstrap.min.css');
require('../../css/font-awesome.min.css');
require('../../css/animate.css');
require('../../js/plugins/jquery-qtip/jquery.qtip.css');
//平台css
require('../../css/style.css');
// 平台util
require('../../js/common/tools/util.js');

require('../../js/bootstrap.min.js');
require('../../js/plugins/peity/jquery.peity.min.js');

require('../../js/plugins/jquery-qtip/jquery.qtip.js');

// 弹框
require('../../js/common/tools/dialogCreator.js');
require('../../js/plugins/toastr/toastr.min.css');
require('expose-loader?toastr!../../js/plugins/toastr/toastr.min.js');
require('../../js/plugins/layer/layer.min.js');
require('../../js/plugins/layer/skin/layer.css');

// 日期选择
require('../../js/plugins/layer/laydate/laydate.js');
require('../../js/plugins/layer/laydate/theme/default/laydate.css');

// ztree 
require('../../js/plugins/ztree/css/zTreeStyle.css');
require('../../js/plugins/ztree/jquery.ztree.all.min.js');
require('../../js/plugins/ztree/ztreeCreator.js');

require('../../js/common/tools/toolsControllerUtil.js');

//两个比较小的列表页 的css这里放 base css中，减少列表页面 特殊css 引入了
require('../../css/plugins/bootstrap-table/bootstrap-table.min.css');
require('../../css/extends/list.css');

require('../../js/plugins/fastjson/FastJson-1.0.min.js');
require('../../js/common/tools/toolsControllerUtil.js');
















