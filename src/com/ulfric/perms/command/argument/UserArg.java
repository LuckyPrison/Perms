package com.ulfric.perms.command.argument;

import org.bukkit.OfflinePlayer;

import com.ulfric.lib.api.command.arg.ArgStrategy;
import com.ulfric.lib.api.player.PlayerUtils;
import com.ulfric.perms.user.User;
import com.ulfric.perms.user.UserColl;

public enum UserArg implements ArgStrategy<User> {

	INSTANCE;

	@Override
	public User match(String string)
	{
		OfflinePlayer player = PlayerUtils.getOffline(string);

		if (player == null) return null;

		return UserColl.getUser(player.getUniqueId());
	}

}