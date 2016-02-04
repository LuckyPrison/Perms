package com.ulfric.perms.node;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.ulfric.lib.api.hook.CachingEngine;

public class NodeColl {

	public static Node of(String permission)
	{
		return NodeColl.cache.getNode(permission);
	}

	protected static Node create(String permission)
	{
		boolean value = !permission.startsWith("-");

		String setPermission = value ? permission : permission.substring(1);

		if (setPermission.isEmpty()) return null;

		return new Node(permission, setPermission, value);
	}

	public static Map<String, Collection<Node>> fromSection(ConfigurationSection section)
	{
		return NodeColl.cache.fromSection(section);
	}

	public static Map<String, Collection<Node>> fromSection(Map<String, Object> map)
	{
		return NodeColl.cache.fromSection(map);
	}

	protected static Cache cache;

	protected static class Cache extends CachingEngine<String, Node>
	{
		protected Cache(boolean caching)
		{
			super(caching);
		}

		public Node getNode(String permission)
		{
			permission = permission.trim().toLowerCase();

			if (!this.isCaching())
			{
				return NodeColl.create(permission);
			}

			Node node = this.getCached(permission);

			if (node != null) return node;

			node = NodeColl.create(permission);

			if (node == null) return null;

			this.cache(permission, node);

			return node;
		}

		private Map<String, Collection<Node>> fromSection(ConfigurationSection section)
		{
			if (section == null) return Maps.newHashMapWithExpectedSize(0);

			Map<String, Object> objects = Maps.newHashMap();

			for (String key : section.getKeys(true))
			{
				Object object = section.get(key);

				key = key.toLowerCase();

				if (object instanceof MemoryConfiguration) continue;

				objects.put(key.equals("all") ? null : key, object);
			}

			return this.fromSection(objects);
		}

		@SuppressWarnings("unchecked")
		private Map<String, Collection<Node>> fromSection(Map<String, Object> section)
		{
			Map<String, Collection<Node>> permissions;

			if (section == null)
			{
				permissions = Maps.newHashMapWithExpectedSize(0);
			}
			else
			{
				Stream<Entry<String, Object>> stream = section.entrySet().stream().filter(entry -> entry.getKey().startsWith("worlds"));

				permissions = Maps.newHashMapWithExpectedSize((int) (stream.count() + 1));

				permissions.put(null, Optional.ofNullable((List<String>) section.get("all")).orElseGet(ImmutableList::of).stream().map(this::getNode).collect(Collectors.toSet()));

				stream.forEach(entry -> permissions.put(entry.getKey().toLowerCase(), ((List<String>) entry.getValue()).stream().map(this::getNode).collect(Collectors.toSet())));
			}

			return permissions;
		}
	}

}