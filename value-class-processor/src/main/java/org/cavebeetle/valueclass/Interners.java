package org.cavebeetle.valueclass;

import static com.google.common.collect.Interners.newWeakInterner;
import static com.google.common.collect.Maps.newConcurrentMap;
import java.util.concurrent.ConcurrentMap;
import com.google.common.collect.Interner;

public final class Interners
{
    public static interface Lazy
    {
        Interners INTERNERS = new Interners();
    }

    private final ConcurrentMap<Class<?>, Interner<?>> map = newConcurrentMap();

    public <T> T intern(final T instance)
    {
        @SuppressWarnings("unchecked")
        final Class<T> classT = (Class<T>) instance.getClass();
        if (!map.containsKey(classT))
        {
            final Interner<T> interner = newWeakInterner();
            map.putIfAbsent(classT, interner);
        }
        @SuppressWarnings("unchecked")
        final Interner<T> interner = (Interner<T>) map.get(classT);
        return interner.intern(instance);
    }
}
