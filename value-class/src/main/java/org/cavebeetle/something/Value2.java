package org.cavebeetle.something;

import org.cavebeetle.valueclass.ValueModel;
import org.cavebeetle.valueclass.ValueModel.AccessorType;

public interface Value2
{
    @ValueModel(accessorType = AccessorType.DEFAULT_USE_FIELD_METHODS, missingValueFieldName = "NULL")
    public interface FirstName2
    {
        String firstName();
    }

    @ValueModel(accessorType = AccessorType.USE_JAVA_BEAN_GETTERS, missingValueFieldName = "NULL_%s")
    public interface Count2
    {
        int count();

        boolean empty();
    }

    @ValueModel
    public interface LastName2
    {
        String lastName();
    }

    @ValueModel(accessorType = AccessorType.USE_JAVA_BEAN_GETTERS, missingValueFieldName = "%s_NULL")
    public interface Location2
    {
        int x();

        int y();

        boolean colour();
    }

    @ValueModel(accessorType = AccessorType.USE_JAVA_BEAN_GETTERS, missingValueFieldName = "MISSING")
    public interface Name2
    {
        LastName2 lastName();

        FirstName2 firstName();
    }
}
