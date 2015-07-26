package org.cavebeetle.valueclass.internal.impl;

import static java.lang.String.format;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import org.cavebeetle.valueclass.ValueModel;
import org.cavebeetle.valueclass.internal.DaggerAnnotationProcessor;
import org.cavebeetle.valueclass.internal.PrimeGenerator;
import com.squareup.javapoet.ClassName;

public final class BasicEntity
{
    private static final PrimeGenerator PRIME_GENERATOR = DaggerAnnotationProcessor.create().primeGenerator();

    public static final int calculatePrime(final Name name, final ClassName type)
    {
        final String fullyQualifiedName = format("%s.%s", type.packageName(), name);
        final int hashCode = fullyQualifiedName.hashCode();
        final int primeIndex = (hashCode & 0xFF000000) >>> 24
                ^ (hashCode & 0x00FF0000) >>> 16
                ^ (hashCode & 0x0000FF00) >>> 8
                ^ hashCode & 0x000000FF;
        return PRIME_GENERATOR.primeAtIndex(primeIndex);
    }

    private final Element entityElement;
    private final ValueModel valueModel;
    private final String fullyQualifiedName;
    private final Name name;
    private final ClassName type;
    private final int prime;

    public BasicEntity(final Element entityElement, final ValueModel valueModel, final Name name, final ClassName type)
    {
        this.entityElement = entityElement;
        this.valueModel = valueModel;
        this.name = name;
        this.type = type;
        fullyQualifiedName = type.packageName() + "." + name;
        prime = calculatePrime(name, type);
    }

    public Element entityElement()
    {
        return entityElement;
    }

    public ValueModel valueModel()
    {
        return valueModel;
    }

    public String fullyQualifiedName()
    {
        return fullyQualifiedName;
    }

    public Name name()
    {
        return name;
    }

    public ClassName type()
    {
        return type;
    }

    public int prime()
    {
        return prime;
    }
}
