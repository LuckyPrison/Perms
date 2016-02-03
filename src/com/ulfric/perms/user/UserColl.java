package com.ulfric.perms.user;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.ulfric.lib.api.hook.CachingEngine;
import com.ulfric.lib.api.hook.Hooks;

public class UserColl {

	protected static Cache cache;

	protected static class Cache extends CachingEngine<UUID, User>
	{
		public Cache(boolean caching)
		{
			super(caching);
		}

		public User getUser(Player player)
		{
			return this.getUser(player.getUniqueId(), true);
		}

		public User getUser(UUID uuid)
		{
			return this.getUser(uuid, false);
		}

		private User getUser(UUID uuid, boolean safe)
		{
			if (!this.isCaching())
			{
				return this.newUserObject(uuid, safe);
			}

			User user = this.getCached(uuid);

			if (user != null) return user;

			user = this.newUserObject(uuid, safe);

			this.cache(uuid, user);

			return user;
		}

		private User newUserObject(UUID uuid, boolean safe)
		{
			if (!safe && Hooks.DATA.getPlayerData(uuid) == null) return null;

			return new User(uuid);
		}
	}

	public static User getUser(UUID uuid)
	{
		return UserColl.cache.getUser(uuid);
	}

	public static User getUserUnchecked(Player player)
	{
		return UserColl.cache.getUser(player);
	}

}