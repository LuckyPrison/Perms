package com.ulfric.perms.group;

import java.util.Collection;

public class GroupColl {

	protected interface IGroupManager
	{
		IGroupManager EMPTY = new IGroupManager() { };

		default Group getGroup(String name) { return null; }

		default Group getGroup(String name, boolean create) { return null; }

		default Collection<Group> getDefaultGroups() { return null; }
	}

	protected static IGroupManager impl = IGroupManager.EMPTY;

	public static Group getGroup(String name)
	{
		return GroupColl.impl.getGroup(name);
	}

	public static Group getGroup(String name, boolean create)
	{
		return GroupColl.impl.getGroup(name, create);
	}

	public static Collection<Group> getDefaultGroups()
	{
		return GroupColl.impl.getDefaultGroups();
	}

}