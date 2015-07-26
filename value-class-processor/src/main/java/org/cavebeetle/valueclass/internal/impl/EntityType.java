package org.cavebeetle.valueclass.internal.impl;

import static org.cavebeetle.valueclass.internal.DefaultValues.entityDefaultValue;
import org.cavebeetle.valueclass.internal.ComponentType;
import com.squareup.javapoet.TypeName;

public final class EntityType
        implements
            ComponentType.Entity
{
    public static final class DefaultBuilder
            implements
                Builder
    {
        @Override
        public ComponentType.Entity make(final BasicEntity type)
        {
            return new EntityType(type);
        }
    }

    private final BasicEntity type;

    public EntityType(final BasicEntity type)
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
        return false;
    }

    @Override
    public TypeName typeName()
    {
        return type.type();
    }

    @Override
    public String defaultValue()
    {
        return entityDefaultValue(type);
    }

    @Override
    public String toString()
    {
        return type.type().packageName() + "." + type.type().simpleName();
    }
}
