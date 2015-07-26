package org.cavebeetle.valueclass.internal.impl;

import org.cavebeetle.valueclass.ValueModel;
import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import com.squareup.javapoet.TypeSpec;

public final class VisitorAccessors
        implements
            ClassBuilderVisitor
{
    @Override
    public void visit(final TypeSpec.Builder builder, final Entity entity)
    {
        if (entity.accessorType() == ValueModel.AccessorType.USE_PUBLIC_FINAL_FIELDS)
        {
            builder.addFields(entity.components().asPublicFinalFields());
        }
        else
        {
            builder.addFields(entity.components().asPrivateFinalFields());
            builder.addMethods(entity.components().asGetters());
        }
    }
}
