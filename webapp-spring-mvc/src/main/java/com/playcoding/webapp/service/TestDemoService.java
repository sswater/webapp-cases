package com.playcoding.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playcoding.webapp.dao.TestDemoDao;

@Service
public class TestDemoService {
	@Autowired
	TestDemoDao demoDao;

	/**
	 * business using dao
	 */
	public String getString() {
		return demoDao.load();
	}
}
