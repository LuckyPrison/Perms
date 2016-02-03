package com.ulfric.perms.group;

import com.ulfric.lib.api.module.SimpleModule;

public class GroupProxyModule extends SimpleModule {

	public GroupProxyModule()
	{
		super("group-proxy", "Group proxying module", "Packet", "1.0.0-REL");

		this.withConf();
	}

	@Override
	public void postEnable()
	{
		GroupProxy.cache = new GroupProxy.Cache(this.getConf().getValue("cache", true));
	}

}