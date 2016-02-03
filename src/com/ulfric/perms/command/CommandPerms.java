package com.ulfric.perms.command;

import com.ulfric.lib.api.command.SubCommandParent;

public class CommandPerms extends SubCommandParent {

	public CommandPerms()
	{
		this.withSubcommand(new SubCommandSetgroup(this));
		this.withSubcommand(new SubCommandCleargroups(this));
	}

}