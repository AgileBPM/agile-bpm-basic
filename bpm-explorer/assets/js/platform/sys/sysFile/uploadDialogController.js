var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	$scope.ArrayTool = ArrayToolService;
	var filter = $filter('filter');
	var uploader;// 上传器
	$scope.init = function() {
		$scope.fileList = [];// 文件列表，是业务需要的数据[{id:xxx,name:xxx}]

		if (window.passData) {
			$scope.fileList = window.passData.fileList;
			angular.forEach($scope.fileList, function(file) {
				file.external = true;
				file.statusText = "已上传";
				file.href = __ctx + "/sys/sysFile/download?fileId=" + file.id;
				if (file.name.isPicture()) {
					file.src = file.href;
				}
			});
		}

		// 初始化Web Uploader
		$scope.uploader = uploader = WebUploader.create({
			// 自动上传。
			auto : true,
			// swf文件路径
			swf : '../../assets/js/plugins/webuploader/Uploader.swf',
			// 文件接收服务端。
			server : __ctx + '/sys/sysFile/upload',
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : '#picker',
			// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			resize : false
		});

		/**
		 * 当有文件被添加进队列的时候
		 */
		var ratio = window.devicePixelRatio || 1;
		uploader.on('fileQueued', function(file) {
			$scope.$apply(function() {
				$scope.fileList.push(file);
				if (file.name.isPicture()) {
					// 创建缩略图
					uploader.makeThumb(file, function(error, src) {
						if (error) {
							return;
						}
						file.src = src;
					}, 100 * ratio, 100 * ratio);
				}
			});
		});

		/**
		 * 文件上传过程中创建进度条实时显示。
		 */
		uploader.on('uploadProgress', function(file, percentage) {
			$scope.$apply(function() {
				file.statusText = "上传中..." + percentage + "%";
			});
		});

		/**
		 * 文件上传成功时调用
		 */
		uploader.on('uploadSuccess', function(file, result) {
			$scope.$apply(function() {
				if (result.isOk) {
					file.id = result.data;
					file.href = __ctx + "/sys/sysFile/download?fileId=" + file.id;
					toastr.success(result.msg);
					file.statusText = "上传成功";
				} else {
					toastr.error(result.msg);
					uploader.removeFile(file, true);
				}
			});
		});

		/**
		 * 上传失败时，服务器拒绝访问时
		 */
		uploader.on('uploadError', function(file, reason) {
			$scope.$apply(function() {
				file.statusText = "上传出错-" + reason;
			});
		});
	};

	/**
	 * 删除附件
	 */
	$scope.del = function(file) {
		var url = __ctx + "/sys/sysFile/del";
		baseService.postForm(url, {
			fileId : file.id,
		}).then(function(result) {
			if (result.isOk) {
				toastr.success(result.msg);
				if(!file.external){
					uploader.removeFile(file, true);
				}
			} else {
				toastr.error(result.msg);
			}
			ArrayToolService.remove(file, $scope.fileList);
		});
	};

	/**
	 * 打包zip
	 */
	$scope.zip = function() {
		var fileIds = [];
		angular.forEach($scope.fileList, function(file) {
			fileIds.push(file.id);
		});
		var url = __ctx + "/sys/sysFile/zip";
		var form = $('<form method="GET"></form>');
		form.attr('action', url);

		var fileIdsEl = $("<input name='fileIds'></input>");
		fileIdsEl.val(fileIds.join(","));
		form.append(fileIdsEl);

		form.appendTo($('body'));
		form.submit();
		form.remove();
	};

	/**
	 * <pre>
	 * 打开文件
	 * 目前暂时只支持打开图片
	 * </pre>
	 */
	$scope.open = function(file) {
		if (file.name.isPicture()) {
			window.open("imagePreview.html?url=" + escape(file.href));
		} else {
			toastr.error("目前只支持打开图片，请下载后查看");
		}
	};

	/**
	 * 获取非删除状态的数组
	 */
	$scope.getData = function() {
		var list = [];
		angular.forEach($scope.fileList, function(file) {
			if (file.external||!file.getStatus() || file.getStatus() == "complete") {
				list.push({
					id : file.id,
					name : file.name
				});
			}
		});
		return list;
	};

} ]);
