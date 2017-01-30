package com.qiuch.springhibernate.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @author qiuch
 * 
 */
@Service("baseService")
public class BaseService {

	@Resource
	DataDao dataDao;
	
	
}
