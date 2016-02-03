package com.ulfric.perms.group;

import java.util.Collection;

import com.ulfric.perms.permissible.IPermissible;

public interface IGroup extends IPermissible {

	Collection<IGroup> getParents();

	boolean isDefault();

	String getTitle();

	Group getNext(String ladder);

}