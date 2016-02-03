package com.ulfric.perms.user;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ulfric.lib.api.collect.CollectUtils;
import com.ulfric.lib.api.hook.DataHook.IPlayerData;
import com.ulfric.lib.api.hook.Hooks;
import com.ulfric.lib.api.java.Assert;
import com.ulfric.lib.api.java.Unique;
import com.ulfric.lib.api.player.PlayerUtils;
import com.ulfric.perms.group.Group;
import com.ulfric.perms.group.GroupColl;
import com.ulfric.perms.group.GroupProxy;
import com.ulfric.perms.group.IGroup;
import com.ulfric.perms.node.Node;
import com.ulfric.perms.permissible.Permissible;

public class User extends Permissible implements Unique {

	protected User(UUID uuid)
	{
		this.uuid = Assert.notNull(uuid);

		this.player = PlayerUtils.proxy(uuid);
	}

	private final UUID uuid;
	private final Player player;
	private String name;
	private boolean hasPlayed;

	public boolean hasPlayedBefore()
	{
		return this.hasPlayed = this.hasPlayedBeforeInternal();
	}
	private boolean hasPlayedBeforeInternal()
	{
		if (this.hasPlayed) return true;

		if (Bukkit.getPlayer(this.uuid) != null) return true;

		return Bukkit.getOfflinePlayer(this.uuid).hasPlayedBefore();
	}

	@Override
	public String getName()
	{
		if (this.name != null) return this.name;

		if (!this.hasPlayedBefore()) return null;

		return this.name = Hooks.DATA.getPlayerDataAsString(this.uuid, "data.name");
	}

	@Override
	public UUID getUniqueId()
	{
		return this.uuid;
	}

	private List<IGroup> groups;
	public List<IGroup> getGroups()
	{
		if (this.groups == null)
		{
			this.groups = Hooks.DATA.getPlayerDataAsStringList(this.uuid, "perms.groups").stream().map(GroupProxy::of).collect(Collectors.toList());
		}

		return this.groups;
	}

	public void setGroup(Group group)
	{
		this.clearGroupsInternal();
		this.recalculate();
	}

	public void addGroup(Group group)
	{
		this.addGroupInternal(group);
		this.recalculate();
	}
	private void addGroupInternal(Group group)
	{
		List<IGroup> groups = this.getGroups();

		if (groups.isEmpty())
		{
			groups.add(group);

			return;
		}

		groups.remove(group);

		groups.add(0, group);
	}

	public void clearGroups()
	{
		this.clearGroupsInternal();
		this.recalculate();
	}
	private void clearGroupsInternal()
	{
		this.groups.clear();
		this.groups.addAll(GroupColl.getDefaultGroups());
	}

	public boolean removeGroup(Group group)
	{
		if (!this.removeGroupInternal(group)) return false;

		this.recalculate();

		return true;
	}
	private boolean removeGroupInternal(Group group)
	{
		return this.getGroups().remove(group);
	}

	@Override
	public Map<String, Collection<Node>> getAllPermissions()
	{
		Map<String, Collection<Node>> permissions = super.getAllPermissions();

		if (permissions != null) return permissions;

		if (!this.hasPlayedBefore()) return null;

		this.supplyPermissions(Node.fromSection(Hooks.DATA.getPlayerData(this.uuid).getRecur("perms.nodes")));

		return super.getAllPermissions();
	}

	public boolean recalculate()
	{
		if (!this.writeInternal()) return false;

		this.recalculateOnline();

		return true;
	}

	private boolean writeInternal()
	{
		if (!this.hasPlayedBefore()) return false;

		IPlayerData data = Hooks.DATA.getPlayerData(this.uuid);

		data.set("perms.groups", this.getGroups());
		data.set("perms.nodes", this.getAllPermissions());

		return true;
	}
	public void recalculateOnline()
	{
		Player player = this.player; 

		if (!player.isOnline()) return;

		boolean flag = false;

		flag = this.recalculateGroups(player);

		if (!this.recalculateNodes(player) && !flag) return;

		player.recalculatePermissions();
	}

	private boolean recalculateGroups(Player player)
	{
		Collection<? extends IGroup> groups = this.getGroups();

		if (groups.isEmpty() && (groups = GroupColl.getDefaultGroups()).isEmpty()) return false;

		boolean flag = false;

		for (IGroup group : groups)
		{
			flag = true;

			this.applyNodes(group.getPermissions(), player);
			this.applyNodes(group.getPermissions(player.getWorld().getName()), player);
			this.getAllPermissions().values().forEach(nodes -> this.applyNodes(nodes, player));
		}

		return flag;
	}

	private boolean recalculateNodes(Player player)
	{
		boolean flag = false;

		flag = this.applyNodes(this.getPermissions(), player);

		return this.applyNodes(this.getPermissions(player.getWorld().getName()), player) || flag;
	}

	private boolean applyNodes(Collection<Node> nodes, Player player)
	{
		if (CollectUtils.isEmpty(nodes)) return false;

		for (Node node : nodes)
		{
			node.apply(player);
		}

		return true;
	}

}