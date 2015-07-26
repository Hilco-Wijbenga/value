package org.cavebeetle.valueclass.internal;

import org.cavebeetle.valueclass.internal.impl.Entity;
import com.squareup.javapoet.TypeSpec;

public interface ClassBuilderVisitor
{
    void visit(TypeSpec.Builder builder, Entity entity);
}
