package engine;

import interfaces.*;
import java.util.*;

public class Search<E> {

	private Frontier<Node<E>> frontier;
	private Set<Node<E>> explored;
	private int expanded;
	private int generated;

	public Search() {
		reset();
	}

	public void reset() {
		frontier = null;
		explored = new HashSet<>();
		expanded = 0;
		generated = 0;
	}

	public Node<E> solve(Problem<E> problem, Heuristic<E> heuristic, boolean iterativeDeepening, Method method) {
		if (iterativeDeepening) {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				Node<E> result = solve(problem, heuristic, i);
				if (result != null) {
					return result;
				}
				reset();
			}
		} else {
			return solve(problem, heuristic, -1, method);
		}
	}

	private Node<E> solve(Problem<E> problem, Heuristic<E> heuristic, int depthLimit, Method method) {

		frontier = getNewFrontier(method);
		Node<E> initialNode = new Node<>(
				problem.getInitialState(),
				0,	// depth
				null,	// parent
				0,	// cost
				heuristic.getValue(initialState),
				null	// rule
		);
		generated++;
		if (problem.isResolved(initialNode.state)) {
			return initialNode;
		}
		frontier.add(initialNode);
		while(!frontier.isEmpty()) {
			Node<E> currentNode = frontier.remove();
			explored.add(currentNode);
			List<Rule<E>> rules = problem.getRules(currentNode.state);
			List<Node<E>> nextNodes;
			for (Rule<E> rule: rules) {
				E state = rule.applyToState(currentNode.state);
				nextNodes.add(new Node(	state,
										currentNode.depth + 1,
										currentNode,
										currentNode.accumulatedCost + rule.getCost(),
										heuristic.getValue(state),
										rule));
			}
			expanded++;
			generated += nextNodes.size();
			for (Node<E> next: nextNodes) {
				if (!explored.contains(next)) {
					if (problem.isResolved(next.state)) {
						return next;
					}
					if (depthLimit >= 0 && next.depth < depthLimit) {
						frontier.add(next);
					}
				}
			}
		}
		return null;
	}

	public int frontierSize() {
		return (frontier == null ? 0 : frontier.size());
	}

	public int expandedSize() {
		return expanded;
	}

	public int generatedSize() {
		return generated;
	}

	private Frontier<Node<E>> getNewFrontier(Method method) {

		switch(method) {
			case Method.BFS:
				return new MyQueue();
			case Method.DFS:
				return new MyStack();
			case Method.GREEDY:
				return new MyPriorityQueue(Comparator.comparingDouble(n -> n.estimatedCost));
			case Method.A_STAR:
				return new MyPriorityQueue(Comparator.comparingDouble(n -> n.accumulatedCost + n.estimatedCost));
			default throw new IllegalArgumentException("Method not supported: " + method);
		}
	}

	private static abstract Frontier<Node<E>> {

		protected Deque<Node<E>> deque;

		int size() {
			return deque.size();
		}

		abstract void add(E elem);
		abstract E remove();
		
		E peek() {
			return deque.peek();
		}
	}

	private static MyStack<Node<E>> extends Frontier<Node<E>> {

		Stack() {
			deque = new LinkedList<Node<E>>();
		}

		@Override
		void add(E elem) {
			deque.push(elem);
		}

		@Override
		E remove() {
			return deque.pop();
		}
	}

	private static MyQueue<Node<E>> extends Frontier<Node<E>> {

		MyQueue() {
			deque = new LinkedList<Node<E>>();
		}

		@Override
		void add(E elem) {
			deque.add(elem);
		}

		@Override E remove() {
			return deque.poll();
		}
	}

	private static MyPriorityQueue<Node<E>> extends Frontier<Node<E>> {

		MyPriorityQueue(Comparator<? super Node<E>> c) {
			deque = new PriorityQueue<Node<E>>(c);
		}

		@Override
		void add(E elem) {
			deque.add(elem);
		}

		@Override E remove() {
			return deque.poll();
		}
	}
}