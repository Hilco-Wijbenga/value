package org.cavebeetle.valueclass.internal.impl;

import static com.google.common.collect.ImmutableMap.copyOf;
import static com.google.common.collect.Maps.newConcurrentMap;
import static javax.lang.model.element.ElementKind.INTERFACE;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import org.cavebeetle.valueclass.ValueModel;
import com.squareup.javapoet.ClassName;

public final class TypeMirrorToBasicEntityMap
        implements
            Iterable<BasicEntity>
{
    private final Map<TypeMirror, BasicEntity> typeMirrorToBasicEntityMap;

    public TypeMirrorToBasicEntityMap(final Elements elementUtils, final Set<? extends Element> entityElements)
    {
        final Map<TypeMirror, BasicEntity> typeMirrorToBasicEntityMap = newConcurrentMap();
        for (final Element entityElement : entityElements)
        {
            if (entityElement.getKind() == INTERFACE)
            {
                final PackageElement targetPackage = elementUtils.getPackageOf(entityElement);
                final ValueModel valueModel = entityElement.getAnnotation(ValueModel.class);
                final Name name = entityElement.getSimpleName();
                final String qualifiedPackageName = targetPackage.getQualifiedName().toString();
                final ClassName type = ClassName.get(qualifiedPackageName, name.toString());
                final BasicEntity basicEntity = new BasicEntity(entityElement, valueModel, name, type);
                typeMirrorToBasicEntityMap.put(entityElement.asType(), basicEntity);
            }
        }
        this.typeMirrorToBasicEntityMap = copyOf(typeMirrorToBasicEntityMap);
    }

    public boolean contains(final TypeMirror key)
    {
        return typeMirrorToBasicEntityMap.containsKey(key);
    }

    public BasicEntity getBasicEntity(final TypeMirror key)
    {
        return typeMirrorToBasicEntityMap.get(key);
    }

    @Override
    public Iterator<BasicEntity> iterator()
    {
        return typeMirrorToBasicEntityMap.values().iterator();
    }
}
