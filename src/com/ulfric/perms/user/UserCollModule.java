package com.ulfric.perms.user;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ulfric.lib.api.module.SimpleModule;

public class UserCollModule extends SimpleModule {

	public UserCollModule()
	{
		super("user-coll", "User collection module", "Packet", "1.0.0-REL");

		this.withConf();
	}

	@Override
	public void onFirstEnable()
	{
		this.addListener(new Listener()
		{
			@EventHandler(priority = EventPriority.LOW)
			public void onJoin(PlayerJoinEvent event)
			{
				UserColl.getUserUnchecked(event.getPlayer()).recalculateOnline();
			}
		});
	}

	@Override
	public void postEnable()
	{
		UserColl.cache = new UserColl.Cache(this.getConf().getValue("cache", true));
	}

}