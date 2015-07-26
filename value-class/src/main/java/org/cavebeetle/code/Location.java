package org.cavebeetle.code;

import java.math.BigInteger;
import org.cavebeetle.valueclass.internal.impl.DefaultIntGenerator;
import org.cavebeetle.valueclass.internal.impl.DefaultPrimeGenerator;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public final class Location
        implements
            Comparable<Location>
{
    public static final class Copier
    {
        private final Location original;
        private BigInteger x_;
        private int y_;

        public Copier(final Location original)
        {
            this.original = original;
            x_ = original.x;
            y_ = original.y;
        }

        public Copier withX(final BigInteger x)
        {
            x_ = x;
            return this;
        }

        public Copier withY(final int y)
        {
            y_ = y;
            return this;
        }

        public Location toLocation()
        {
            if (x_ == original.x && y_ == original.y)
            {
                return original;
            }
            else
            {
                return newLocation(x_, y_);
            }
        }
    }

    public static final Location ABSENT = new Location();
    private static final Interner<Location> INTERNER = Interners.newWeakInterner();
    private static final int PRIME = new DefaultPrimeGenerator(new DefaultIntGenerator.DefaultBuilder()).primeAtIndex(32);

    public static final Location newLocation(final BigInteger x, final int y)
    {
        return INTERNER.intern(new Location(x, y));
    }

    private final int hashCode;
    public final BigInteger x;
    public final int y;

    private Location()
    {
        x = BigInteger.ZERO;
        y = 0;
        hashCode = 0;
    }

    private Location(final BigInteger x, final int y)
    {
        this.x = x;
        this.y = y;
        hashCode = generateHashCode();
    }

    public Copier copy()
    {
        return new Copier(this);
    }

    @Override
    public int compareTo(final Location other)
    {
        return ComparisonChain
                .start()
                .compare(x, other.x)
                .compare(y, other.y)
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
        final Location other = (Location) object;
        return x.equals(other.x)
                && y == other.y;
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
            return "(Location ABSENT)";
        }
        else
        {
            return "(Location '" + x + "' '" + y + "')";
        }
    }

    private int generateHashCode()
    {
        int result = 1;
        result = PRIME * result + x.hashCode();
        result = PRIME * result + y;
        return result;
    }
}
