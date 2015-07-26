package org.cavebeetle.valueclass.internal.impl;

import static com.google.common.collect.Lists.newArrayList;
import static javax.lang.model.element.ElementKind.METHOD;
import java.util.List;
import javax.lang.model.element.Element;

public final class EntityBuilder
{
    public static final EntityBuilder make(final Entity.Builder basicBuilder, final BasicEntity basicEntity)
    {
        return new EntityBuilder(basicBuilder, basicEntity);
    }

    private final Entity.Builder basicBuilder;
    private final BasicEntity basicEntity;

    private EntityBuilder(final Entity.Builder basicBuilder, final BasicEntity basicEntity)
    {
        this.basicBuilder = basicBuilder;
        this.basicEntity = basicEntity;
    }

    public Entity build()
    {
        final BasicComponent.Builder componentBuilder = BasicComponent.Builder.make(basicBuilder, basicEntity);
        final List<BasicComponent> basicComponents = newArrayList();
        for (final Element enclosedElement : basicEntity.entityElement().getEnclosedElements())
        {
            if (enclosedElement.getKind() == METHOD)
            {
                basicComponents.add(componentBuilder.builder(enclosedElement).build());
            }
        }
        return new Entity(basicEntity, basicComponents);
    }
}
