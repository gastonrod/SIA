package engine;

import ar.com.itba.sia.*;

public class Node<E> {

	final E state;
	final int depth;
	final Node parent;
	final double accumulatedCost;
	final double estimatedCost;
	final Rule<E> rule;

	private boolean hashCalculated;
	private int hash;

	Node(E state, int depth, Node parent, double accumulatedCost, double estimatedCost, Rule<E> rule) {
		this.state = state;
		this.depth = depth;
		this.parent = parent;
		this.accumulatedCost = accumulatedCost;
		this.estimatedCost = estimatedCost;
		this.hashCalculated = false;
		this.rule = rule;
	}

	@Override
	public boolean equals(Object o) {
		return (this == o
				||
				(o instanceof Node
						&&
				 state.equals(((Node)o).state)
				));
	}

	@Override
	public int hashCode() {
		if (!hashCalculated) {
			hash = state.hashCode();
			hashCalculated = true;
		}
		return hash;
	}
}