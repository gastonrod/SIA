package ar.com.itba.sia;

import java.util.List;

public interface Problem<E> {
 	E getInitialState();
	List<Rule<E>> getRules(E paramE);
	boolean isResolved(E paramE);
}
