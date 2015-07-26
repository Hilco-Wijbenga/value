package org.cavebeetle.code;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import org.cavebeetle.valueclass.internal.impl.DefaultIntGenerator;
import org.cavebeetle.valueclass.internal.impl.DefaultPrimeGenerator;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public final class LastName
        implements
            Comparable<LastName>
{
    public static final class Copier
    {
        private final LastName original;
        private String lastName_;

        public Copier(final LastName original)
        {
            this.original = original;
        }

        public Copier withLastName(final String lastName)
        {
            checkNotNull(lastName, "Missing 'lastName'.");
            lastName_ = lastName.intern();
            return this;
        }

        public LastName toLastName()
        {
            if (lastName_ == null || lastName_ == original.lastName)
            {
                return original;
            }
            else
            {
                return newLastName(lastName_);
            }
        }
    }

    public static final LastName ABSENT = new LastName();
    private static final Interner<LastName> INTERNER = Interners.newWeakInterner();
    private static final int PRIME = new DefaultPrimeGenerator(new DefaultIntGenerator.DefaultBuilder()).primeAtIndex(31);

    public static final LastName newLastName(final String lastName)
    {
        return INTERNER.intern(new LastName(lastName));
    }

    private final int hashCode;
    public final String lastName;

    private LastName()
    {
        lastName = "";
        hashCode = 0;
    }

    private LastName(final String lastName)
    {
        checkNotNull(lastName, "Missing 'lastName'.");
        this.lastName = lastName.intern();
        hashCode = generateHashCode();
    }

    public Copier copy()
    {
        return new Copier(this);
    }

    @Override
    public int compareTo(final LastName other)
    {
        return lastName.compareTo(other.lastName);
    }

    @Override
    public int hashCode()
    {
        return hashCode;
    }

    @Override
    public boolean equals(final Object object)
    {
        return this == object;
    }

    @Override
    public String toString()
    {
        if (this == ABSENT)
        {
            return "(LastName ABSENT)";
        }
        else
        {
            return format("(LastName '%s')", lastName);
        }
    }

    private int generateHashCode()
    {
        int result = 1;
        result = PRIME * result + lastName.hashCode();
        return result;
    }
}
