package org.cavebeetle.valueclass.internal.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public final class Iterables
{
    public interface Mapper<A, B>
    {
        B map(A a);
    }

    public static final <T> Iterable<T> none()
    {
        return new Iterable<T>()
        {
            @Override
            public Iterator<T> iterator()
            {
                return Collections.<T> emptyList().iterator();
            }
        };
    }

    public static final <X, Y> Iterable<Y> iterable(final Collection<X> xs, final Mapper<X, Y> mapper)
    {
        return new Iterable<Y>()
        {
            @Override
            public Iterator<Y> iterator()
            {
                return new Iterator<Y>()
                {
                    private final Iterator<X> iterator = xs.iterator();

                    @Override
                    public boolean hasNext()
                    {
                        return iterator.hasNext();
                    }

                    @Override
                    public Y next()
                    {
                        return mapper.map(iterator.next());
                    }
                };
            }
        };
    }
}
