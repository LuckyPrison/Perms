package com.ulfric.perms.permissible;

import java.util.Collection;
import java.util.Map;

import com.ulfric.lib.api.java.Named;
import com.ulfric.perms.node.Node;

public interface IPermissible extends Named {

	Map<String, Collection<Node>> getAllPermissions();

	Collection<Node> getPermissions();

	Collection<Node> getPermissions(String worldName);

	void supplyPermissions(Map<String, Collection<Node>> permissions);

}