package org.cavebeetle.code;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import org.cavebeetle.valueclass.internal.impl.DefaultIntGenerator;
import org.cavebeetle.valueclass.internal.impl.DefaultPrimeGenerator;
import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public final class Name
        implements
            Comparable<Name>
{
    public static final class Copier
    {
        private final Name original;
        private LastName lastName;
        private FirstName firstName;

        public Copier()
        {
            this(ABSENT);
        }

        public Copier(final Name original)
        {
            Preconditions.checkNotNull(original, "Missing 'original'.");
            this.original = original;
            lastName = original.lastName;
            firstName = original.firstName;
        }

        public Copier setLastName(final LastName lastName)
        {
            Preconditions.checkNotNull(lastName, "Missing 'lastName'.");
            this.lastName = lastName == original.lastName ? original.lastName : lastName;
            return this;
        }

        public Copier setFirstName(final FirstName firstName)
        {
            Preconditions.checkNotNull(firstName, "Missing 'firstName'.");
            this.firstName = firstName;
            return this;
        }

        public Name toName()
        {
            if (lastName.equals(original.lastName)
                    && firstName.equals(original.firstName))
            {
                return original;
            }
            else
            {
                return newName(
                        lastName == null ? original.lastName : lastName,
                        firstName == null ? original.firstName : firstName);
            }
        }
    }

    public static final Name ABSENT = new Name();
    private static final Interner<Name> INTERNER = Interners.newWeakInterner();
    private static final int PRIME = new DefaultPrimeGenerator(new DefaultIntGenerator.DefaultBuilder()).primeAtIndex(33);

    public static final Name newName(final LastName lastName, final FirstName firstName)
    {
        return INTERNER.intern(new Name(lastName, firstName));
    }

    private final int hashCode;
    public final LastName lastName;
    public final FirstName firstName;

    private Name()
    {
        lastName = LastName.ABSENT;
        firstName = FirstName.ABSENT;
        hashCode = 0;
    }

    private Name(final LastName lastName, final FirstName firstName)
    {
        checkNotNull(lastName, "Missing 'lastName'.");
        checkNotNull(firstName, "Missing 'firstName'.");
        this.lastName = lastName;
        this.firstName = firstName;
        hashCode = generateHashCode();
    }

    public Copier copy()
    {
        return new Copier(this);
    }

    @Override
    public int compareTo(final Name other)
    {
        return ComparisonChain
                .start()
                .compare(lastName, other.lastName)
                .compare(firstName, other.firstName)
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
        final Name other = (Name) object;
        return firstName.equals(other.firstName)
                && lastName.equals(other.lastName);
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
            return "(Name ABSENT)";
        }
        else
        {
            return format("(Name %s %s)", firstName, lastName);
        }
    }

    private int generateHashCode()
    {
        int result = PRIME + firstName.hashCode();
        result = PRIME * result + lastName.hashCode();
        return result;
    }
}
