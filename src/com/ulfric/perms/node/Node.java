package com.ulfric.perms.node;

import org.bukkit.permissions.Permissible;

import com.ulfric.perms.Perms;

public class Node {

	protected Node(String original, String permission, boolean value)
	{
		this.original = original;

		this.permission = permission;

		this.value = value;
	}

	private final String original;
	public String getOriginal()
	{
		return this.original;
	}

	private final String permission;
	public String getPermission()
	{
		return this.permission;
	}

	private final boolean value;
	public boolean isPositive()
	{
		return this.value;
	}

	public void apply(Permissible permissible)
	{
		permissible.addAttachment(Perms.get(), this.permission, this.value);
	}

	@Override
	public String toString()
	{
		return this.original;
	}

}