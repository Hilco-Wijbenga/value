package org.cavebeetle.valueclass.internal;

import javax.lang.model.type.TypeMirror;
import org.cavebeetle.valueclass.internal.impl.BasicEntity;
import com.squareup.javapoet.TypeName;

public interface ComponentType
{
    public interface Entity
            extends
                ComponentType
    {
        public interface Builder
        {
            Entity make(final BasicEntity type);
        }
    }

    public interface Primitive
            extends
                ComponentType
    {
        public interface Builder
        {
            Primitive make(final TypeMirror type);
        }
    }

    public interface Foreign
            extends
                ComponentType
    {
        public interface Builder
        {
            Foreign make(final TypeMirror type);
        }
    }

    boolean primitive();

    boolean string();

    TypeName typeName();

    String defaultValue();
}
