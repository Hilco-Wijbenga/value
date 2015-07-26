package org.cavebeetle.valueclass.internal.impl;

import static org.cavebeetle.valueclass.internal.DefaultValues.stringDefaultValue;
import javax.lang.model.type.TypeMirror;
import org.cavebeetle.valueclass.internal.ComponentType;
import com.squareup.javapoet.TypeName;

public final class ForeignType
        implements
            ComponentType.Foreign
{
    public static final class DefaultBuilder
            implements
                Builder
    {
        @Override
        public ComponentType.Foreign make(final TypeMirror type)
        {
            return new ForeignType(type);
        }
    }

    private final TypeMirror type;

    public ForeignType(final TypeMirror type)
    {
        this.type = type;
    }

    @Override
    public boolean primitive()
    {
        return false;
    }

    @Override
    public boolean string()
    {
        return type.toString().equals("java.lang.String");
    }

    @Override
    public TypeName typeName()
    {
        return TypeName.get(type);
    }

    @Override
    public String defaultValue()
    {
        return stringDefaultValue(typeName());
    }

    @Override
    public String toString()
    {
        return type.toString();
    }
}
