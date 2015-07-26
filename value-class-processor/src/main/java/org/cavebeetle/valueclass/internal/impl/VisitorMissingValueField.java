package org.cavebeetle.valueclass.internal.impl;

import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeSpec;

public final class VisitorMissingValueField
        implements
            ClassBuilderVisitor
{
    @Override
    public void visit(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addField(entity.missingValueField().toFieldSpec());
        builder.addStaticBlock(makeStaticCodeBlock(entity));
    }

    public static final CodeBlock makeStaticCodeBlock(final Entity entity)
    {
        return CodeBlock.builder()
                .addStatement("$L = new $T()", entity.missingValueField().name(), entity.type())
                .build();
    }
}
