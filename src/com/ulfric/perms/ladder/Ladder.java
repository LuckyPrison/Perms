package com.ulfric.perms.ladder;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.ulfric.lib.api.java.Assert;
import com.ulfric.lib.api.java.Named;
import com.ulfric.perms.group.Group;

public class Ladder implements Named, Iterable<Group> {

	private List<Group> rankings = Lists.newArrayList();

	protected Ladder(String name)
	{
		this.name = name;
	}

	private final String name;
	@Override
	public String getName()
	{
		return this.name;
	}

	public boolean insert(Group group, int place)
	{
		Assert.notNull(group);

		if (this.rankings.isEmpty())
		{
			this.rankings.add(group);

			return false;
		}

		boolean removed = this.rankings.remove(group);

		this.rankings.add(place, group);

		return removed;
	}

	@Override
	public Iterator<Group> iterator()
	{
		return this.rankings.iterator();
	}

}