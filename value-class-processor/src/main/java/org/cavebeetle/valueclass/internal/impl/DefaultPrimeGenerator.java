package org.cavebeetle.valueclass.internal.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.sort;
import java.util.List;
import javax.inject.Singleton;
import org.cavebeetle.valueclass.internal.IntGenerator;
import org.cavebeetle.valueclass.internal.PrimeGenerator;

@Singleton
public final class DefaultPrimeGenerator
        implements
            PrimeGenerator
{
    private final int[] primes;

    public DefaultPrimeGenerator(final IntGenerator.Builder intGeneratorBuilder)
    {
        final List<IntGenerator> generators = newArrayList();
        primes = new int[256];
        primes[0] = 2;
        for (int i = 1; i < 256; i++)
        {
            generators.add(intGeneratorBuilder.make(primes[i - 1] + primes[i - 1], primes[i - 1]));
            primes[i] = initNextPrime(generators, primes[i - 1] + 1);
        }
    }

    @Override
    public int primeAtIndex(final int index)
    {
        checkArgument(0 <= index && index <= 0xFF, "Index out of bounds.");
        return primes[index];
    }

    private int initNextPrime(final List<IntGenerator> generators, final int nextPrimeGuess)
    {
        if (nextPrimeGuess != generators.get(0).current())
        {
            return nextPrimeGuess;
        }
        else
        {
            int nextPrime = nextPrimeGuess;
            while (nextPrime == generators.get(0).current())
            {
                while (true)
                {
                    final IntGenerator generator = generators.get(0);
                    if (nextPrime != generator.current())
                    {
                        break;
                    }
                    generator.next();
                    sort(generators);
                }
                nextPrime++;
            }
            return nextPrime;
        }
    }
}
