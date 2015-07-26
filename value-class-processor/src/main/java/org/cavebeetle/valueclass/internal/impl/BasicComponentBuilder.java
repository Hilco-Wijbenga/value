package org.cavebeetle.valueclass.internal.impl;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import org.cavebeetle.valueclass.internal.ComponentType;

public final class BasicComponentBuilder
{
    public static final BasicComponentBuilder make(
            final BasicComponent.Builder basicBuilder,
            final Element enclosedElement)
    {
        return new BasicComponentBuilder(basicBuilder, enclosedElement);
    }

    private final BasicComponent.Builder basicBuilder;
    private final Element enclosedElement;

    private BasicComponentBuilder(final BasicComponent.Builder basicBuilder, final Element enclosedElement)
    {
        this.basicBuilder = basicBuilder;
        this.enclosedElement = enclosedElement;
    }

    public BasicComponent build()
    {
        final ExecutableElement method = ExecutableElement.class.cast(enclosedElement);
        final Name componentName = method.getSimpleName();
        final TypeMirror returnTypeMirror = method.getReturnType();
        if (basicBuilder.typeMirrorToBasicEntityMap().contains(returnTypeMirror))
        {
            final BasicEntity returnType = basicBuilder.typeMirrorToBasicEntityMap().getBasicEntity(returnTypeMirror);
            return createEntityTypeComponent(componentName, returnType);
        }
        else
        {
            return createPrimitiveOrForeignTypeComponent(componentName, returnTypeMirror);
        }
    }

    public BasicComponent createEntityTypeComponent(final Name componentName, final BasicEntity returnType)
    {
        final ComponentType componentType = new EntityType(returnType);
        return new BasicComponent(basicBuilder.basicEntity(), componentName, componentType);
    }

    public BasicComponent createPrimitiveOrForeignTypeComponent(
            final Name componentName,
            final TypeMirror returnTypeMirror)
    {
        if (returnTypeMirror.getKind().isPrimitive())
        {
            final ComponentType componentType = new PrimitiveType(returnTypeMirror);
            return new BasicComponent(basicBuilder.basicEntity(), componentName, componentType);
        }
        else
        {
            final ComponentType componentType = new ForeignType(returnTypeMirror);
            return new BasicComponent(basicBuilder.basicEntity(), componentName, componentType);
        }
    }
}
