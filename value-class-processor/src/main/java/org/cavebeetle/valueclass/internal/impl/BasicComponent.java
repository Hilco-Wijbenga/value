package org.cavebeetle.valueclass.internal.impl;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import org.cavebeetle.valueclass.internal.ComponentType;

public final class BasicComponent
{
    public static final class Builder
    {
        public static final Builder make(final Entity.Builder entityBuilder, final BasicEntity basicEntity)
        {
            return new Builder(entityBuilder, basicEntity);
        }

        private final Entity.Builder entityBuilder;
        private final BasicEntity basicEntity;

        private Builder(final Entity.Builder entityBuilder, final BasicEntity basicEntity)
        {
            this.entityBuilder = entityBuilder;
            this.basicEntity = basicEntity;
        }

        public TypeMirrorToBasicEntityMap typeMirrorToBasicEntityMap()
        {
            return entityBuilder.typeMirrorToBasicEntityMap();
        }

        public BasicEntity basicEntity()
        {
            return basicEntity;
        }

        public BasicComponentBuilder builder(final Element enclosedElement)
        {
            return BasicComponentBuilder.make(this, enclosedElement);
        }
    }

    private final BasicEntity parent;
    private final Name name;
    private final ComponentType type;

    public BasicComponent(final BasicEntity parent, final Name name, final ComponentType type)
    {
        this.parent = parent;
        this.name = name;
        this.type = type;
    }

    public BasicEntity parent()
    {
        return parent;
    }

    public Name name()
    {
        return name;
    }

    public ComponentType type()
    {
        return type;
    }
}
