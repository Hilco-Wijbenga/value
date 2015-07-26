package org.cavebeetle.valueclass.internal.impl;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static org.cavebeetle.valueclass.internal.impl.Misc.toComparable;
import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import com.google.common.collect.ComparisonChain;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public final class VisitorImplementComparable
        implements
            ClassBuilderVisitor
{
    @Override
    public void visit(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addSuperinterface(toComparable(entity.type()));
        builder.addMethod(makeCompareToMethod(entity));
    }

    public static final MethodSpec makeCompareToMethod(final Entity entity)
    {
        return MethodSpec.methodBuilder("compareTo")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .returns(int.class)
                .addParameter(entity.type(), "other", FINAL)
                .addCode(makeCompareToCodeBlock(entity))
                .build();
    }

    public static final CodeBlock makeCompareToCodeBlock(final Entity entity)
    {
        final CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        codeBlockBuilder.add("$[return $T\n.start()", ClassName.get(ComparisonChain.class));
        for (final FieldSpec field : entity.components().asFields())
        {
            codeBlockBuilder.add("\n.compare($N, other.$N)", field.name, field.name);
        }
        codeBlockBuilder.add("\n.result();\n$]");
        return codeBlockBuilder.build();
    }
}
