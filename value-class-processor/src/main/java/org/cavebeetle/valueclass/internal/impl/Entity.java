package org.cavebeetle.valueclass.internal.impl;

import java.util.List;
import javax.lang.model.element.Name;
import org.cavebeetle.valueclass.ValueModel;
import com.squareup.javapoet.ClassName;

public final class Entity
{
    public static final class Builder
    {
        public static final Builder make(final TypeMirrorToBasicEntityMap typeMirrorToBasicEntityMap)
        {
            return new Builder(typeMirrorToBasicEntityMap);
        }

        private final TypeMirrorToBasicEntityMap typeMirrorToBasicEntityMap;

        private Builder(final TypeMirrorToBasicEntityMap typeMirrorToBasicEntityMap)
        {
            this.typeMirrorToBasicEntityMap = typeMirrorToBasicEntityMap;
        }

        public TypeMirrorToBasicEntityMap typeMirrorToBasicEntityMap()
        {
            return typeMirrorToBasicEntityMap;
        }

        public EntityBuilder builder(final BasicEntity basicEntity)
        {
            return EntityBuilder.make(this, basicEntity);
        }
    }

    private final BasicEntity basicEntity;
    private final ClassName builderType;
    private final MissingValueField missingValueField;
    private final List<BasicComponent> basicComponents;
    private final Components components;

    public Entity(final BasicEntity basicEntity, final List<BasicComponent> basicComponents)
    {
        this.basicEntity = basicEntity;
        builderType = ClassName.get(basicEntity.type().packageName(), basicEntity.name().toString(), "Builder");
        final String missingValueFieldName = basicEntity.valueModel().missingValueFieldName();
        missingValueField = MissingValueField.newMissingValueField(missingValueFieldName, basicEntity.type());
        this.basicComponents = basicComponents;
        components = Components.newComponents(this, basicComponents);
    }

    public ClassName type()
    {
        return basicEntity.type();
    }

    public Name name()
    {
        return basicEntity.name();
    }

    public String fullyQualifiedName()
    {
        return basicEntity.fullyQualifiedName();
    }

    public int prime()
    {
        return basicEntity.prime();
    }

    public ClassName builderType()
    {
        return builderType;
    }

    public MissingValueField missingValueField()
    {
        return missingValueField;
    }

    public ValueModel.AccessorType accessorType()
    {
        return basicEntity.valueModel().accessorType();
    }

    public Components components()
    {
        return components;
    }

    @Override
    public String toString()
    {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(').append(basicEntity.type()).append('\n');
        for (final BasicComponent basicComponent : basicComponents)
        {
            stringBuilder.append("    ").append(basicComponent).append('\n');
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}
