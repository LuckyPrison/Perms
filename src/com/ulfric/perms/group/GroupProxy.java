package com.ulfric.perms.group;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.ulfric.lib.api.hook.CachingEngine;
import com.ulfric.lib.api.java.Proxy;
import com.ulfric.perms.node.Node;

public class GroupProxy implements IGroup, Proxy<Group> {

	protected static Cache cache;

	public static GroupProxy of(String name)
	{
		return GroupProxy.cache.getProxy(name);
	}

	protected static class Cache extends CachingEngine<String, GroupProxy>
	{
		public Cache(boolean caching)
		{
			super(caching);
		}

		public GroupProxy getProxy(String name)
		{
			name = name.trim().toLowerCase();

			if (!this.isCaching())
			{
				return new GroupProxy(name);
			}

			GroupProxy group = this.getCached(name);

			if (group != null) return group;

			group = new GroupProxy(name);

			this.cache(name, group);

			return group;
		}
	}

	protected GroupProxy(String name)
	{
		this.name = name;
	}

	private final String name;

	private WeakReference<Group> group;

	@Override
	public Group get()
	{
		Group group;
		if (this.group != null && (group = this.group.get()) != null)
		{
			return group;
		}

		group = GroupColl.getGroup(this.name);

		if (group == null) return null;

		this.group = new WeakReference<>(group);

		return group;
	}

	private <R> R func(Function<Group, R> function)
	{
		Group group = this.get();

		if (group == null) return null;

		return function.apply(group);
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public Map<String, Collection<Node>> getAllPermissions()
	{
		return this.func(Group::getAllPermissions);
	}

	@Override
	public Collection<Node> getPermissions()
	{
		return this.func(Group::getPermissions);
	}

	@Override
	public Collection<Node> getPermissions(String worldName)
	{
		return this.func(group -> group.getPermissions(worldName));
	}

	@Override
	public Collection<IGroup> getParents()
	{
		return this.func(Group::getParents);
	}

	@Override
	public boolean isDefault()
	{
		return Optional.ofNullable(this.func(Group::isDefault)).orElse(Boolean.FALSE);
	}

	@Override
	public String getTitle()
	{
		return this.func(Group::getTitle);
	}

	@Override
	public Group getNext(String ladder)
	{
		return this.func(group -> group.getNext(ladder));
	}

	@Override
	public void supplyPermissions(Map<String, Collection<Node>> permissions)
	{
		this.consume(group -> group.supplyPermissions(permissions));
	}

}
