package com.lei.demo.zookeeper;

import com.lei.demo.config.ZkClient;
import com.lei.demo.utils.R;
import com.lei.demo.zookeeper.service.impl.DealWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoo")
@ConditionalOnProperty(  //配置文件属性是否为true
		value = {"zookeeper.enabled"},
		matchIfMissing = false
)
public class ZkController {
	
	@Autowired
	private ZkClient zkClient;
	
	@RequestMapping(value = "register/{path}", method = RequestMethod.GET)
	@ResponseBody
	public R register(@PathVariable("path") String path) {
		//zkClient.createNode(CreateMode.PERSISTENT, "/"+path, "1");
		//System.out.println(JSON.toJSONString(zkClient.getChildren("/")));
		System.out.println(Thread.currentThread().getName());
		System.out.println(zkClient.getSRLock("/8521", 2, new DealWork()));
		return R.ok().put("data", zkClient.getRandomData("/test1"));
	}

}
