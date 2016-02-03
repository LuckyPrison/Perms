package com.ulfric.perms;

import com.ulfric.lib.api.module.Plugin;
import com.ulfric.perms.command.CommandPerms;
import com.ulfric.perms.group.GroupCollModule;
import com.ulfric.perms.group.GroupProxyModule;
import com.ulfric.perms.ladder.LadderCollModule;
import com.ulfric.perms.node.NodeCollModule;
import com.ulfric.perms.user.UserCollModule;

public class Perms extends Plugin {

	private static Perms i;
	public static Perms get() { return Perms.i; }

	@Override
	public void load()
	{
		Perms.i = this;

		this.withSubModule(new NodeCollModule());
		this.withSubModule(new GroupProxyModule());
		this.withSubModule(new LadderCollModule());
		this.withSubModule(new GroupCollModule());
		this.withSubModule(new UserCollModule());

		this.addCommand("perms", new CommandPerms());
	}

	@Override
	public void disable()
	{
		Perms.i = null;
	}

}