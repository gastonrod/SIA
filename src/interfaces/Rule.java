package interfaces;

public interface Rule<E> {
	double getCost();
	void setCost(double paramDouble);
	E applyToState(E paramE);
}
