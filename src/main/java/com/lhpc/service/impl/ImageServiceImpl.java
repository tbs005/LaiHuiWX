package com.lhpc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhpc.dao.ImageMapper;
import com.lhpc.model.Image;
import com.lhpc.service.IImageService;

/**
 * 轮播图service
 * @author YangGuang
 *
 */
@Service
public class ImageServiceImpl implements IImageService {

	@Autowired
	private ImageMapper imageDao;
	@Override
	public List<Image> selectImageList() {
		return imageDao.selectImageList();
	}


}
