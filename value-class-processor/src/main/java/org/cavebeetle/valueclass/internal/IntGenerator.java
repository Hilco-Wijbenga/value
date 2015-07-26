package org.cavebeetle.valueclass.internal;

public interface IntGenerator
        extends
            Comparable<IntGenerator>
{
    public interface Builder
    {
        IntGenerator make(int first, int step);
    }

    int step();

    int current();

    void next();
}
