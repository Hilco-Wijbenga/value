package org.cavebeetle.valueclass.internal.impl;

import static org.cavebeetle.valueclass.internal.DefaultValues.primitiveDefaultValue;
import javax.lang.model.type.TypeMirror;
import org.cavebeetle.valueclass.internal.ComponentType;
import com.squareup.javapoet.TypeName;

public final class PrimitiveType
        implements
            ComponentType.Primitive
{
    public static final class DefaultBuilder
            implements
                Builder
    {
        @Override
        public ComponentType.Primitive make(final TypeMirror type)
        {
            return new PrimitiveType(type);
        }
    }

    private final TypeMirror type;

    public PrimitiveType(final TypeMirror type)
    {
        this.type = type;
    }

    @Override
    public boolean primitive()
    {
        return true;
    }

    @Override
    public boolean string()
    {
        return false;
    }

    @Override
    public TypeName typeName()
    {
        return TypeName.get(type);
    }

    @Override
    public String defaultValue()
    {
        return primitiveDefaultValue(typeName());
    }

    @Override
    public String toString()
    {
        return type.toString();
    }
}
