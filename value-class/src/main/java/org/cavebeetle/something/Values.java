package org.cavebeetle.something;

import org.cavebeetle.valueclass.ValueModel;

public interface Values
{
    @ValueModel
    public interface Count
    {
        int count();
    }

    @ValueModel
    public interface Location
    {
        int x();

        int y();
    }

    @ValueModel
    public interface FirstName
    {
        String firstName();
    }

    @ValueModel
    public interface LastName
    {
        String lastName();
    }

    @ValueModel
    public interface Name
    {
        FirstName firstName();

        LastName lastName();
    }
}
