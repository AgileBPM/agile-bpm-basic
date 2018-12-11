package com.dstz.sys.rest.controller;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.FileUtil;
import com.dstz.base.core.util.ZipUtil;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.rest.GenericController;
import com.dstz.sys.core.manager.SysFileManager;
import com.dstz.sys.core.model.SysFile;
import com.github.pagehelper.Page;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import net.lingala.zip4j.core.ZipFile;

/**
 * <pre>
 * 描述：上传附件的controller
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年6月4日
 * 版权:summer
 * </pre>
 */
@RestController
@RequestMapping("/sys/sysFile/")
public class SysFileController extends GenericController {
	@Autowired
	SysFileManager sysFileManager;

	/**
	 * <pre>
	 * </pre>
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@CatchErr(value = "上传失败")
	public ResultMsg<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
		SysFile sysFile = sysFileManager.upload(file.getInputStream(), file.getOriginalFilename());
		return getSuccessResult(sysFile.getId(), "上传成功");
	}

	/**
	 * <pre>
	 * </pre>
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "download", method = RequestMethod.GET)
	@CatchErr(value = "下载失败")
	public ResponseEntity<byte[]> download(@RequestParam("fileId") String fileId) throws Exception {
		SysFile sysFile = sysFileManager.get(fileId);

		HttpHeaders headers = new HttpHeaders();
		String downloadFileName = new String(sysFile.getName().getBytes("UTF-8"), "iso-8859-1");
		headers.setContentDispositionFormData("attachment", downloadFileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<>(IOUtils.toByteArray(sysFileManager.download(fileId)), headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "zip", method = RequestMethod.GET)
	@CatchErr(value = "打包失败")
	public ResponseEntity<byte[]> zip(@RequestParam("fileIds") String fileIds) throws Exception {
		ArrayList<File> sourceFileList = new ArrayList<>();
		for (String id : fileIds.split(",")) {
			SysFile sysFile = sysFileManager.get(id);
			sourceFileList.add(FileUtil.inputstream2file(sysFileManager.download(id),new File(sysFile.getName())));
		}

		String zipName = DateUtil.format(new DateTime(),"yyyyMMddHHmmss")  + ".zip";
		File file = ZipUtil.zip(sourceFileList, new ZipFile(new File(zipName)));
		HttpHeaders headers = new HttpHeaders();
		String downloadFileName = new String(zipName.getBytes("UTF-8"), "iso-8859-1");
		headers.setContentDispositionFormData("attachment", downloadFileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		byte[] bs = FileUtils.readFileToByteArray(file);
		file.delete();// 删除临时zip文件
		return new ResponseEntity<>(bs, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "del")
	@CatchErr(value = "删除失败")
	public ResultMsg<String> del(@RequestParam("fileId") String fileId) throws Exception {
		sysFileManager.delete(fileId);
		return getSuccessResult("删除成功");
	}
	
    @RequestMapping("listJson")
    public PageResult listJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter queryFilter = getQueryFilter(request);
        Page<SysFile> pageList = (Page<SysFile>) sysFileManager.query(queryFilter);
        return new PageResult(pageList);
    }
}
