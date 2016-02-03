package com.ulfric.perms.command;

import java.util.List;

import com.ulfric.lib.api.command.Command;
import com.ulfric.lib.api.command.SimpleSubCommand;
import com.ulfric.lib.api.locale.Locale;
import com.ulfric.perms.command.argument.UserArg;
import com.ulfric.perms.group.IGroup;
import com.ulfric.perms.user.User;

public class SubCommandCleargroups extends SimpleSubCommand {

	public SubCommandCleargroups(Command command)
	{
		super(command, "cleargroups", "cg");

		this.withArgument("user", UserArg.INSTANCE, "perms.user_required");
	}

	@Override
	public void run()
	{
		User user = (User) this.getObject("user");

		List<IGroup> groups = user.getGroups();

		int count = groups.size();

		user.clearGroups();

		Locale.sendSuccess(this.getSender(), "perms.user_group_set", user.getName(), count - groups.size());
	}

}