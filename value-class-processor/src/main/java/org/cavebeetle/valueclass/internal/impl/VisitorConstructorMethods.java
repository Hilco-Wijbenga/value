package org.cavebeetle.valueclass.internal.impl;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

public final class VisitorConstructorMethods
        implements
            ClassBuilderVisitor
{
    @Override
    public void visit(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addMethod(makeDefaultConstructorMethod(entity));
        builder.addMethod(makeConstructorMethod(entity));
    }

    public static final MethodSpec makeDefaultConstructorMethod(final Entity entity)
    {
        return MethodSpec.constructorBuilder()
                .addModifiers(PRIVATE)
                .addCode(makeDefaultConstructorCodeBlock(entity))
                .build();
    }

    public static final CodeBlock makeDefaultConstructorCodeBlock(final Entity entity)
    {
        final CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        for (final Component component : entity.components())
        {
            final FieldSpec field = component.asField();
            codeBlockBuilder.addStatement("this.$N = $L", field.name, component.defaultValue());
        }
        return codeBlockBuilder.build();
    }

    public static final MethodSpec makeConstructorMethod(final Entity entity)
    {
        return MethodSpec.constructorBuilder()
                .addModifiers(PRIVATE)
                .addParameter(ParameterSpec
                        .builder(entity.builderType(), "builder", FINAL)
                        .build())
                .addCode(makeConstructorCodeBlock(entity))
                .build();
    }

    public static final CodeBlock makeConstructorCodeBlock(final Entity entity)
    {
        final CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        for (final FieldSpec field : entity.components().asFields())
        {
            codeBlockBuilder.addStatement("$N = builder.$N", field.name, field.name);
        }
        return codeBlockBuilder.build();
    }
}
