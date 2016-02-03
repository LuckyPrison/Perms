package com.ulfric.perms.permissible;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableSet;
import com.ulfric.perms.node.Node;

public abstract class Permissible implements IPermissible {

	public Permissible() { }

	public Permissible(Map<String, Collection<Node>> permissions)
	{
		this.permissions = permissions;
	}

	@Override
	public void supplyPermissions(Map<String, Collection<Node>> permissions)
	{
		this.permissions = permissions;
	}

	/**
	 * Map of world names and permissions.
	 */
	private Map<String, Collection<Node>> permissions;

	/**
	 * Gets all worlds with all permissions
	 *
	 * @return Returns all permissions
	 */
	@Override
	public Map<String, Collection<Node>> getAllPermissions()
	{
		return this.permissions;
	}

	@Override
	public Collection<Node> getPermissions()
	{
		return this.getPermissions(null);
	}

	@Override
	public Collection<Node> getPermissions(String worldName)
	{
		Map<String, Collection<Node>> nodes = this.getAllPermissions();

		if (nodes == null) return ImmutableSet.of();

		return Optional.ofNullable(nodes.get(worldName)).orElseGet(ImmutableSet::of);
	}

}