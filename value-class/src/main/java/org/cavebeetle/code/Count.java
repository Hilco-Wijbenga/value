package org.cavebeetle.code;

import org.cavebeetle.valueclass.internal.impl.DefaultIntGenerator;
import org.cavebeetle.valueclass.internal.impl.DefaultPrimeGenerator;
import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

/**
 * <p>
 * The {@code Count} value class. Each value class is guaranteed to act like a "value", i.e. it is always safe to
 * compare two value classes using {@code ==}. In other words, {@code Count#equals} and {@code ==} are guaranteed to
 * always return the same result.
 * </p>
 * <p>
 * {@code Count} is thread safe.
 * </p>
 */
public final class Count
        implements
            Comparable<Count>
{
    /**
     * A {@code Builder} facilitates creating new instances by copying data from an existing instance.
     */
    public static final class Builder
    {
        private final Count original;
        private int count;

        public Builder()
        {
            this(ABSENT);
        }

        /**
         * Creates a new {@code Builder}.
         *
         * @param original
         *            the original {@code Count} instance.
         */
        public Builder(final Count original)
        {
            this.original = original;
            count = original.count;
        }

        /**
         * Updates this {@code Builder} instance to store the given {@code count}.
         *
         * @param count
         *            the new value for the {@code count} property.
         * @return this {@code Builder} instance.
         */
        public Builder setCount(final int count)
        {
            this.count = count;
            return this;
        }

        public int count()
        {
            return count;
        }

        /**
         * Returns the {@code Count} instance created by this {@code Builder}.
         *
         * @return the {@code Count} instance created by this {@code Builder}.
         */
        public Count build()
        {
            if (count == original.count)
            {
                return original;
            }
            else
            {
                return org.cavebeetle.valueclass.Interners.Lazy.INTERNERS.intern(new Count(this));
            }
        }
    }

    /**
     * Represents the absence of a {@code Count} instance. Essentially, a typed {@code null}.
     */
    public static final Count ABSENT = new Count();
    private static final Interner<Count> INTERNER = Interners.newWeakInterner();
    private static final int PRIME = new DefaultPrimeGenerator(new DefaultIntGenerator.DefaultBuilder()).primeAtIndex(29);

    /**
     * Returns an instance with the given content. If an instance with the given content already exists then that
     * instance is returned. Otherwise, a new instance is created.
     *
     * @param count
     *            the value for the {@code Count} instance's {@code count} property.
     * @return the requested {@code Count} instance.
     */
    public static final Count newCount(final int count)
    {
        return INTERNER.intern(new Builder().setCount(count).build());
    }

    private final int hashCode;
    /**
     * The {@code count} property.
     */
    public final int count;

    private Count()
    {
        count = 0;
        hashCode = 0;
    }

    private Count(final Builder builder)
    {
        count = builder.count();
        hashCode = generateHashCode();
    }

    /**
     * Returns a {@code Builder} for this {@code Count} instance.
     *
     * @return a {@code Builder} for this {@code Count} instance.
     */
    public Builder copy()
    {
        return new Builder(this);
    }

    @Override
    public int compareTo(final Count other)
    {
        Preconditions.checkNotNull(other, "Missing 'other'.");
        return ComparisonChain
                .start()
                .compare(count, other.count)
                .result();
    }

    @Override
    public boolean equals(final Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object == null || getClass() != object.getClass())
        {
            return false;
        }
        final Count other = (Count) object;
        return count == other.count;
    }

    @Override
    public int hashCode()
    {
        return hashCode;
    }

    @Override
    public String toString()
    {
        if (this == ABSENT)
        {
            return "(Counter ABSENT)";
        }
        else
        {
            return "(Counter '" + count + "')";
        }
    }

    private int generateHashCode()
    {
        int result = 1;
        result = PRIME * result + count;
        return result;
    }
}
