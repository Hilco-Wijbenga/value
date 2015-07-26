package org.cavebeetle.valueclass.internal.impl;

import javax.lang.model.element.Name;
import com.google.common.base.Preconditions;

public final class StringName
        implements
            Name
{
    public static final Name SENTINEL = new StringName("");

    public static final Name newName(final String name)
    {
        Preconditions.checkNotNull(name, "Missing 'name'.");
        Preconditions.checkArgument(!name.isEmpty(), "Invalid 'name': empty.");
        return new StringName(name);
    }

    private final String name;

    private StringName(final String name)
    {
        this.name = name;
    }

    @Override
    public int length()
    {
        return name.length();
    }

    @Override
    public char charAt(final int index)
    {
        return name.charAt(index);
    }

    @Override
    public CharSequence subSequence(final int beginIndex, final int endIndex)
    {
        return name.subSequence(beginIndex, endIndex);
    }

    @Override
    public boolean contentEquals(final CharSequence charSequence)
    {
        return name.contentEquals(charSequence);
    }

    @Override
    public int hashCode()
    {
        return 157 + name.hashCode();
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
        final StringName other = (StringName) object;
        return name.equals(other.name);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
