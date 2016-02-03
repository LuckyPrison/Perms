package com.ulfric.perms.command;

import com.ulfric.lib.api.command.Command;
import com.ulfric.lib.api.command.SimpleSubCommand;
import com.ulfric.lib.api.locale.Locale;
import com.ulfric.perms.command.argument.GroupArg;
import com.ulfric.perms.command.argument.UserArg;
import com.ulfric.perms.group.Group;
import com.ulfric.perms.user.User;

public class SubCommandRemovegroup extends SimpleSubCommand {

	public SubCommandRemovegroup(Command command)
	{
		super(command, "removegroup", "rg");

		this.withArgument("user", UserArg.INSTANCE, "perms.user_required");
		this.withArgument("group", GroupArg.INSTANCE, "perms.group_required");
	}

	@Override
	public void run()
	{
		User user = (User) this.getObject("user");

		Group group = (Group) this.getObject("group");

		if (user.removeGroup(group))
		{
			Locale.sendSuccess(this.getSender(), "perms.user_group_removed", user.getName(), group.getName());

			return;
		}

		Locale.sendSuccess(this.getSender(), "perms.user_group_not_present", user.getName(), group.getName());
	}

}