package com.lhpc.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.model.Image;
import com.lhpc.service.impl.ImageServiceImpl;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ResponseCodeUtil;

/**
 * 轮播图controller
 * @author YangGuang
 */
@Controller
public class ImageController {

	@Autowired
	private ImageServiceImpl imageService;//注入service
	
	private static Logger log = Logger.getLogger(ImageController.class);
	
	@ResponseBody
	@RequestMapping(value="image/list")
	public ResponseEntity<String> noFinished() {
		try {
			List<Image> imageList = imageService.selectImageList();
			if (imageList.size() == 0) 
				return GsonUtil.getJson(ResponseCodeUtil.NO_DATA, "暂无数据");
			else 
				return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "请求成功", imageList);
		} catch (Exception e) {
			log.error(e.getMessage());
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "服务器繁忙,请重试!");
		}
		
	}
}
