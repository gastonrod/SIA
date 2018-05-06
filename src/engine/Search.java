package engine;

import ar.com.itba.sia.*;
import java.util.*;

class Search<E> {

	private Frontier<Node<E>> frontier;
	private Map<Node<E>, Integer> explored;
	private int expanded;
	private int generated;

	private InputManager<E> iM;
	private static int stepsCounter = 1;

	Search() {
		reset();
	}

	void reset() {
		frontier = null;
		explored = new HashMap<>();
		expanded = 0;
		generated = 0;
	}

	Node<E> solve(Problem<E> problem, Heuristic<E> heuristic, boolean iterativeDeepening, Method method) {
		if (Solver.debug){
			iM = new InputManager<>();
		}
		if (iterativeDeepening) {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				Node<E> result = solve(problem, heuristic, i, method);
				if (result != null) {
					return result;
				}
				reset();
			}
		} else {
			return solve(problem, heuristic, -1, method);
		}
		return null;
	}

	private Node<E> solve(Problem<E> problem, Heuristic<E> heuristic, int depthLimit, Method method) {

		frontier = getNewFrontier(method);
		E initialState = problem.getInitialState();
		Node<E> initialNode = new Node<>(
				initialState,
				0,	// depth
				null,	// parent
				0,	// cost
				heuristic.getValue(initialState),	// estimated cost
				null	// rule
		);
		generated++;
		if (problem.isResolved(initialNode.state)) {
			return initialNode;
		}
		frontier.add(initialNode);
		while(!frontier.isEmpty()) {
			Node<E> currentNode = frontier.remove();
			if(Solver.debug && --stepsCounter <= 0){
				stepsCounter = iM.manageInput(currentNode, problem);
				if (stepsCounter == -1) {
					Solver.debug = false;
					iM.closeScanner();
				}
			}
			explored.put(currentNode, currentNode.depth);
			List<Rule<E>> rules = problem.getRules(currentNode.state);
			List<Node<E>> nextNodes = new LinkedList<>();
			for (Rule<E> rule: rules) {
				E state = rule.applyToState(currentNode.state);
				nextNodes.add(new Node<>(	state,
										currentNode.depth + 1,
										currentNode,
										currentNode.accumulatedCost + rule.getCost(),
										heuristic.getValue(state),
										rule));
			}
			expanded++;
			generated += nextNodes.size();
			for (Node<E> next: nextNodes) {
				Integer exploredDepth = explored.get(next);
				if (exploredDepth == null || (depthLimit >= 0 && exploredDepth > next.depth)) {
					if (problem.isResolved(next.state)) {
						frontier.add(next);
						if(Solver.debug)
							iM.closeScanner();
						return next;
					}
					if (depthLimit < 0 || next.depth < depthLimit) {
						frontier.add(next);
					}
				}
			}
		}
		if(Solver.debug)
			iM.closeScanner();
		return null;
	}



	int frontierSize() {
		return (frontier == null ? 0 : frontier.size());
	}

	int expandedSize() {
		return expanded;
	}

	int generatedSize() {
		return generated;
	}

	private Frontier<Node<E>> getNewFrontier(Method method) {

		switch(method) {
			case BFS:
				return new MyQueue<>();
			case DFS:
				return new MyStack<>();
			case GREEDY:
				return new MyPriorityQueue<>(Comparator.comparingDouble(n -> n.estimatedCost));
			case A_STAR:
				return new MyPriorityQueue<>(Comparator.comparingDouble(n -> n.accumulatedCost + n.estimatedCost));
			default:
				throw new IllegalArgumentException("Method not supported: " + method);
		}
	}

}