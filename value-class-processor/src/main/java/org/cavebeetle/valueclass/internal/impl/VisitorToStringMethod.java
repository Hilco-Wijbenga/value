package org.cavebeetle.valueclass.internal.impl;

import static javax.lang.model.element.Modifier.PUBLIC;
import java.util.Iterator;
import javax.lang.model.element.Name;
import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public final class VisitorToStringMethod
        implements
            ClassBuilderVisitor
{
    @Override
    public void visit(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addMethod(makeToStringMethod(entity));
    }

    public static final MethodSpec makeToStringMethod(final Entity entity)
    {
        final Name valueName = entity.name();
        final String missingValueName = entity.missingValueField().name();
        final MethodSpec toString = MethodSpec.methodBuilder("toString")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .returns(ClassName.get(String.class))
                .addCode(makeToStringCodeBlock(entity, valueName, missingValueName))
                .build();
        return toString;
    }

    public static final CodeBlock makeToStringCodeBlock(
            final Entity entity,
            final Name valueName,
            final String missingValueName)
    {
        return CodeBlock.builder()
                .addStatement(
                        "return this == " + missingValueName
                                + "\n? \"(" + valueName + " " + missingValueName + ")\""
                                + "\n: String.format(" + pattern(valueName, entity.components()) + ")")
                .build();
    }

    public static final String pattern(final Name name, final Components components)
    {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('"').append('(');
        stringBuilder.append(name);
        for (final Component component : components)
        {
            stringBuilder.append(' ').append(component.string() ? "\\\"%s\\\"" : "%s");
        }
        stringBuilder.append(')').append('"');
        stringBuilder.append(", ").append(listFields(components));
        return stringBuilder.toString();
    }

    public static final String listFields(final Components components)
    {
        final StringBuilder stringBuilder = new StringBuilder();
        final Iterator<FieldSpec> fieldIt = components.asFields().iterator();
        if (fieldIt.hasNext())
        {
            stringBuilder.append(fieldIt.next().name);
        }
        while (fieldIt.hasNext())
        {
            stringBuilder.append(", ").append(fieldIt.next().name);
        }
        return stringBuilder.toString();
    }
}
