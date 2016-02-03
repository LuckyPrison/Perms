package com.ulfric.perms.node;

import com.ulfric.lib.api.module.SimpleModule;

public class NodeCollModule extends SimpleModule {

	public NodeCollModule()
	{
		super("node-coll", "Node collection module", "Packet", "1.0.0-REL");

		this.withConf();
	}

	@Override
	public void postEnable()
	{
		Node.cache = new Node.Cache(this.getConf().getValue("cache", true));
	}

}