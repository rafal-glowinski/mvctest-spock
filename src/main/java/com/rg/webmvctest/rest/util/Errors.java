package com.rg.webmvctest.rest.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Errors implements Iterable<Error> {

    private final List<Error> errors = new ArrayList<>();

    public Errors() {
    }

    public Errors(Collection<Error> errors) {
        this.errors.addAll(errors);
    }

    public Errors(Error... errors) {
        this.errors.addAll(Arrays.asList(errors));
    }

    public static Errors empty() {
        return new Errors();
    }

    public boolean noErrors() {
        return errors.isEmpty();
    }

    public Error singleError() {
        if (errors.isEmpty()) {
            throw new IllegalStateException("Tried to get single error, but there are no errors in this container!");
        }
        return errors.get(0);
    }

    public Errors addError(Error error) {
        this.errors.add(error);
        return this;
    }

    public Errors combine(Errors errors) {
        this.errors.addAll(errors.errors);
        return this;
    }

    public Errors cloneWithPrefixedPaths(String prefix) {
        return this.errors.stream()
                .map(e -> e.cloneWithPrefixedPath(prefix))
                .collect(collector());
    }

    public int size() {
        return errors.size();
    }

    public Stream<Error> stream() {
        return this.errors.stream();
    }

    public List<Error> list() {
        return Collections.unmodifiableList(errors);
    }

    @Override
    public Iterator<Error> iterator() {
        return Collections.unmodifiableCollection(errors).iterator();
    }

    @Override
    public String toString() {
        return "Errors{errors=" + errors + '}';
    }

    public static Collector<Error, Errors, Errors> collector() {
        return Collector.of(Errors::new, Errors::addError, Errors::combine);
    }
}