package org.cavebeetle.valueclass.internal.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toUpperCase;
import com.google.common.base.Preconditions;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;

public final class Misc
{
    public static final ClassName PRECONDITIONS = ClassName.get(Preconditions.class);

    public static final ParameterizedTypeName toComparable(final ClassName type)
    {
        return ParameterizedTypeName.get(ClassName.get(Comparable.class), type);
    }

    public static final String makeLeadingUpperCase(final String text)
    {
        checkNotNull(text, "Missing 'text'.");
        if (text.isEmpty())
        {
            return text;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        char previousChar = text.charAt(0);
        stringBuilder.append(toUpperCase(previousChar));
        for (int i = 1; i < text.length(); i++)
        {
            final char ch = text.charAt(i);
            if (isLowerCase(previousChar) && isUpperCase(ch))
            {
                stringBuilder.append('_');
            }
            stringBuilder.append(toUpperCase(ch));
            previousChar = ch;
        }
        return stringBuilder.toString();
    }
}
