package com.ulfric.perms.command;

import com.ulfric.lib.api.command.Command;
import com.ulfric.lib.api.command.SimpleSubCommand;
import com.ulfric.lib.api.locale.Locale;
import com.ulfric.perms.command.argument.GroupArg;
import com.ulfric.perms.command.argument.UserArg;
import com.ulfric.perms.group.Group;
import com.ulfric.perms.user.User;

public class SubCommandAddgroup extends SimpleSubCommand {

	public SubCommandAddgroup(Command command)
	{
		super(command, "addgroup", "ag");

		this.withArgument("user", UserArg.INSTANCE, "perms.user_required");
		this.withArgument("group", GroupArg.INSTANCE, "perms.group_required");
	}

	@Override
	public void run()
	{
		User user = (User) this.getObject("user");

		Group group = (Group) this.getObject("group");

		user.addGroup(group);

		Locale.sendSuccess(this.getSender(), "perms.user_group_added", user.getName(), group.getName());
	}

}
