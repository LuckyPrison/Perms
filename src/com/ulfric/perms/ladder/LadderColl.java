package com.ulfric.perms.ladder;

public class LadderColl {

	protected static ILadderColl impl = ILadderColl.EMPTY;

	protected interface ILadderColl
	{
		ILadderColl EMPTY = new ILadderColl() { };

		default Ladder of(String name) { return null; }

		default Ladder ofOrNull(String name) { return null; }
	}

	public static Ladder of(String name)
	{
		return LadderColl.impl.of(name);
	}

	public static Ladder ofOrNull(String name)
	{
		return LadderColl.impl.ofOrNull(name);
	}

}