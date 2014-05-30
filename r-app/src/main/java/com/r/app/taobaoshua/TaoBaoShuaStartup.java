package com.r.app.taobaoshua;

public interface TaoBaoShuaStartup {

	/** 初始化 */
	void init();

	/** 启动组件 */
	void startup();
	
	/**是否运行中*/
	boolean isRunning();

}