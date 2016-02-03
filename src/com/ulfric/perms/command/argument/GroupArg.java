package com.ulfric.perms.command.argument;

import com.ulfric.lib.api.command.arg.ArgStrategy;
import com.ulfric.perms.group.Group;
import com.ulfric.perms.group.GroupColl;

public enum GroupArg implements ArgStrategy<Group> {

	INSTANCE;

	@Override
	public Group match(String string)
	{
		return GroupColl.getGroup(string);
	}

}