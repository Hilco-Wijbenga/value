package org.cavebeetle.valueclass.internal.impl;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;

public final class Entities
        implements
            Iterable<Entity>
{
    public static final Entities EMPTY = new Entities();
    private final List<Entity> entities;

    private Entities()
    {
        entities = emptyList();
    }

    public Entities(final Elements elementUtils, final Set<? extends Element> entityElements)
    {
        final TypeMirrorToBasicEntityMap typeMirrorToBasicEntityMap = new TypeMirrorToBasicEntityMap(elementUtils, entityElements);
        final List<Entity> entities = newArrayList();
        final Entity.Builder entityBuilder = Entity.Builder.make(typeMirrorToBasicEntityMap);
        for (final BasicEntity basicEntity : typeMirrorToBasicEntityMap)
        {
            entities.add(entityBuilder.builder(basicEntity).build());
        }
        this.entities = copyOf(entities);
    }

    @Override
    public Iterator<Entity> iterator()
    {
        return entities.iterator();
    }
}
