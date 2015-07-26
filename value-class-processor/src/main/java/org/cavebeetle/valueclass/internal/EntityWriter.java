package org.cavebeetle.valueclass.internal;

import javax.annotation.processing.Filer;
import org.cavebeetle.valueclass.internal.impl.Entity;

public interface EntityWriter
{
    public interface Builder
    {
        EntityWriter make(Filer filer);
    }

    void create(Entity entity);
}
