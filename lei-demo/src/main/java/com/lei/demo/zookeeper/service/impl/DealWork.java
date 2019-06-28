package com.lei.demo.zookeeper.service.impl;


import com.lei.demo.zookeeper.service.SRLockDealCallback;

import java.util.concurrent.TimeUnit;

public class DealWork implements SRLockDealCallback<String> {

	@Override
	public String deal() {
	 
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return "abc";
	}

}
