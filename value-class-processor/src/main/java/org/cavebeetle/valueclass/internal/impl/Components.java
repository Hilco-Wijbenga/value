package org.cavebeetle.valueclass.internal.impl;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static org.cavebeetle.valueclass.internal.impl.Component.newComponent;
import static org.cavebeetle.valueclass.internal.impl.Iterables.iterable;
import java.util.Iterator;
import java.util.List;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

public final class Components
        implements
            Iterable<Component>
{
    public static final Components newComponents(final Entity entity, final List<BasicComponent> basicComponents)
    {
        return new Components(entity, basicComponents);
    }

    private final List<Component> components;

    private Components(final Entity entity, final List<BasicComponent> basicComponents)
    {
        final List<Component> components = newArrayList();
        for (final BasicComponent basicComponent : basicComponents)
        {
            components.add(newComponent(basicComponent));
        }
        this.components = copyOf(components);
    }

    public int getFieldCount()
    {
        return components.size();
    }

    public Iterable<FieldSpec> asFields()
    {
        return iterable(components, component -> component.asField());
    }

    public Iterable<FieldSpec> asPublicFinalFields()
    {
        return iterable(components, component -> component.asPublicFinalField());
    }

    public Iterable<FieldSpec> asPrivateFinalFields()
    {
        return iterable(components, component -> component.asPrivateFinalField());
    }

    public Iterable<FieldSpec> asPrivateFields()
    {
        return iterable(components, component -> component.asPrivateField());
    }

    public Iterable<ParameterSpec> asFinalParameters()
    {
        return iterable(components, component -> component.asFinalParameter());
    }

    public Iterable<MethodSpec> asGetters()
    {
        return iterable(components, component -> component.getter());
    }

    @Override
    public Iterator<Component> iterator()
    {
        return components.iterator();
    }
}
