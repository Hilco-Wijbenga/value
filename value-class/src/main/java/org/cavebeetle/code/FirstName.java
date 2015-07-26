package org.cavebeetle.code;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import org.cavebeetle.valueclass.internal.impl.DefaultIntGenerator;
import org.cavebeetle.valueclass.internal.impl.DefaultPrimeGenerator;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public final class FirstName
        implements
            Comparable<FirstName>
{
    public static final class Copier
    {
        private final FirstName original;
        private String firstName_;

        public Copier(final FirstName original)
        {
            this.original = original;
        }

        public Copier withFirstName(final String firstName)
        {
            checkNotNull(firstName, "Missing 'firstName'.");
            firstName_ = firstName.intern();
            return this;
        }

        public FirstName toFirstName()
        {
            if (firstName_ == null || firstName_ == original.firstName)
            {
                return original;
            }
            else
            {
                return newFirstName(firstName_);
            }
        }
    }

    public static final FirstName ABSENT = new FirstName();
    private static final Interner<FirstName> INTERNER = Interners.newWeakInterner();
    private static final int PRIME = new DefaultPrimeGenerator(new DefaultIntGenerator.DefaultBuilder()).primeAtIndex(30);

    public static final FirstName newFirstName(final String firstName)
    {
        return INTERNER.intern(new FirstName(firstName));
    }

    private final int hashCode;
    public final String firstName;

    private FirstName()
    {
        firstName = "";
        hashCode = 0;
    }

    private FirstName(final String firstName)
    {
        checkNotNull(firstName, "Missing 'firstName'.");
        this.firstName = firstName.intern();
        hashCode = generateHashCode();
    }

    public Copier copy()
    {
        return new Copier(this);
    }

    @Override
    public int compareTo(final FirstName other)
    {
        return firstName.compareTo(other.firstName);
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
            return "(FirstName ABSENT)";
        }
        else
        {
            return format("(FirstName '%s')", firstName);
        }
    }

    private int generateHashCode()
    {
        int result = 1;
        result = PRIME * result + firstName.hashCode();
        return result;
    }
}
