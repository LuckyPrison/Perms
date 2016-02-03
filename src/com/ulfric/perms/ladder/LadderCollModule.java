package com.ulfric.perms.ladder;

import java.util.Map;

import com.google.common.collect.Maps;
import com.ulfric.lib.api.module.SimpleModule;
import com.ulfric.perms.ladder.LadderColl.ILadderColl;

public class LadderCollModule extends SimpleModule {

	public LadderCollModule()
	{
		super("ladder-coll", "Ladder collection module", "Packet", "1.0.0-REL");
	}

	@Override
	public void postEnable()
	{
		LadderColl.impl = new ILadderColl()
		{
			private Map<String, Ladder> ladders = Maps.newHashMap();

			@Override
			public Ladder of(String name)
			{
				name = name.trim().toLowerCase();

				Ladder ladder = this.internalOfOrNull(name);

				if (ladder != null) return ladder;

				ladder = new Ladder(name);

				return ladder;
			}

			@Override
			public Ladder ofOrNull(String name)
			{
				return this.internalOfOrNull(name.trim().toLowerCase());
			}

			private Ladder internalOfOrNull(String name)
			{
				return this.ladders.get(name);
			}
		};
	}

}