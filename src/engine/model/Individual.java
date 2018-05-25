package engine.model;

import engine.engineException.InvalidLocusException;

public interface Individual {

    Individual replicate();

    int getLocusAmount();

    void exchangeAt(Individual otherIndividual, int locus) throws InvalidLocusException;

    void mutateAt(int locus) throws InvalidLocusException;
}
