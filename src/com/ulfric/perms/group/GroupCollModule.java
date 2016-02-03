package com.ulfric.perms.group;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ulfric.lib.api.collect.Sets;
import com.ulfric.lib.api.java.Assert;
import com.ulfric.lib.api.module.SimpleModule;
import com.ulfric.lib.api.persist.ConfigFile;
import com.ulfric.perms.group.GroupColl.IGroupManager;
import com.ulfric.perms.node.Node;

public class GroupCollModule extends SimpleModule {

	public GroupCollModule()
	{
		super("group-coll", "Rankings module for Perms", "Packet", "1.0.0-REL");

		this.withConf();
	}

	@Override
	public void postEnable()
	{
		GroupColl.impl = new IGroupManager()
		{
			private List<Group> defaultGroups = Lists.newArrayList();

			private Map<String, Group> groups;

			{
				ConfigFile conf = GroupCollModule.this.getConf();
				Set<String> groups = conf.getKeys(false);

				this.groups = Maps.newHashMapWithExpectedSize(groups.size());

				for (String groupName : groups)
				{
					ConfigurationSection section = Assert.notNull(conf.getSection(groupName));

					groupName = groupName.toLowerCase();

					ConfigurationSection permSection = section.getConfigurationSection("permissions");

					Map<String, Collection<Node>> permissions = Node.fromSection(permSection);
					Collection<IGroup> parents = permSection == null ? ImmutableList.of() : permSection.getStringList("parents").stream().map(GroupProxy::of).collect(Collectors.toList());

					ConfigurationSection optionsSection = section.getConfigurationSection("options");

					boolean isDefault = false;
					String title = null;

					if (optionsSection != null)
					{
						isDefault = optionsSection.getBoolean("default");
						title = optionsSection.getString("title");
					}

					Group group = new Group(groupName, isDefault, title, permissions, parents);

					this.groups.put(groupName, group);

					if (!isDefault) continue;

					this.defaultGroups.add(group);
				}
			}

			@Override
			public Collection<Group> getDefaultGroups()
			{
				return this.defaultGroups;
			}

			@Override
			public Group getGroup(String name)
			{
				return this.getGroup(name, false);
			}

			@Override
			public Group getGroup(String name, boolean create)
			{
				name = Assert.notNull(name, "The group name must not be null").trim().toLowerCase();

				Group group = this.groups.get(name);

				if (group != null || !create) return group;

				Map<String, Collection<Node>> permissions = Maps.newHashMap();

				permissions.put(null, Sets.newHashSet());

				group = new Group(name, false, null, permissions, null);

				this.groups.put(null, group);

				return group;
			}
		};
	}

}