package org.cavebeetle.valueclass.internal.impl;

import static java.lang.String.format;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import java.util.Iterator;
import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public final class VisitorEqualsMethod
        implements
            ClassBuilderVisitor
{
    @Override
    public void visit(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addMethod(makeEqualsMethod(entity));
    }

    public static final MethodSpec makeEqualsMethod(final Entity entity)
    {
        return MethodSpec.methodBuilder("equals")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .returns(boolean.class)
                .addParameter(Object.class, "object", FINAL)
                .addCode(makeEqualsCodeBlock(entity))
                .build();
    }

    public static final CodeBlock makeEqualsCodeBlock(final Entity entity)
    {
        final TypeName valueType = entity.type();
        return CodeBlock.builder()
                .beginControlFlow("if (this == object)")
                .addStatement("return true")
                .endControlFlow()
                .beginControlFlow("if (object == null || getClass() != object.getClass())")
                .addStatement("return false")
                .endControlFlow()
                .addStatement("final $T other = ($T) object", valueType, valueType)
                .addStatement(writeComponentDependentEqualsCode(entity.components()))
                .build();
    }

    public static final String writeComponentDependentEqualsCode(final Components components)
    {
        final StringBuilder equalsCode = new StringBuilder();
        final Iterator<Component> componentIt = components.iterator();
        writeFirstComponent(equalsCode, componentIt.next());
        writeSubsequentComponents(equalsCode, componentIt);
        return equalsCode.toString();
    }

    public static final void writeFirstComponent(final StringBuilder equalsCode, final Component firstComponent)
    {
        final FieldSpec firstField = firstComponent.asPrivateField();
        equalsCode.append(format(
                firstComponent.primitive()
                        ? "return %s == other.%s"
                        : "return %s.equals(other.%s)",
                firstField.name,
                firstField.name));
    }

    public static final void writeSubsequentComponents(
            final StringBuilder equalsCode,
            final Iterator<Component> componentIt)
    {
        while (componentIt.hasNext())
        {
            final Component component = componentIt.next();
            final FieldSpec field = component.asPrivateField();
            equalsCode.append(format(
                    component.primitive()
                            ? "\n&& %s == other.%s"
                            : "\n&& %s.equals(other.%s)",
                    field.name,
                    field.name));
        }
    }
}
