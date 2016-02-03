package com.ulfric.perms.ladder;

import java.util.Iterator;
import java.util.List;

import com.ulfric.lib.api.java.Assert;
import com.ulfric.lib.api.java.Named;
import com.ulfric.perms.group.Group;

public class Ladder implements Named, Iterable<Group> {

	private List<Group> rankings;

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