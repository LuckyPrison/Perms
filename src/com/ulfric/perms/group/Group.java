package com.ulfric.perms.group;

import java.util.Collection;
import java.util.Map;

import com.ulfric.perms.ladder.Ladder;
import com.ulfric.perms.ladder.LadderColl;
import com.ulfric.perms.node.Node;
import com.ulfric.perms.permissible.Permissible;

public class Group extends Permissible implements IGroup {

	public Group(String name, boolean isDefault, String title, Map<String, Collection<Node>> permissions, Collection<IGroup> parents)
	{
		super(permissions);

		this.name = name;

		this.isDefault = isDefault;

		this.title = title;

		this.parents = parents;
	}

	private final String name;

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	private final Collection<IGroup> parents;
	@Override
	public Collection<IGroup> getParents()
	{
		return this.parents;
	}

	private final boolean isDefault;
	@Override
	public boolean isDefault()
	{
		return this.isDefault;
	}

	private final String title;
	@Override
	public String getTitle()
	{
		return this.title;
	}

	@Override
	public Group getNext(String ladderName)
	{
		Ladder ladder = LadderColl.ofOrNull(ladderName);

		if (ladder == null) return null;

		boolean found = false;
		Group next = null;

		for (Group group : ladder)
		{
			if (!found)
			{
				if (!group.equals(this)) continue;

				found = true;

				continue;
			}

			next = group;

			break;
		}

		return next;
	}

}