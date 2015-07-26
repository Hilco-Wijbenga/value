package org.cavebeetle.code;

import org.cavebeetle.something.Count2;
import org.cavebeetle.something.FirstName2;
import org.cavebeetle.something.LastName2;
import org.cavebeetle.something.Location2;
import org.cavebeetle.something.Name2;

public final class Main
{
    public static void main(final String[] args)
    {
        final Name2 nameA = Name2.builder()
                .setFirstName(
                        FirstName2.builder()
                                .setFirstName("First")
                                .build())
                .setLastName(
                        LastName2.builder()
                                .setLastName("Last")
                                .build())
                .build();
        System.out.println(nameA);
        final Name2 nameB = nameA.copy()
                .setLastName(LastName2.SENTINEL)
                .build();
        System.out.println(nameB);
        final Count2 countA = Count2.builder()
                .setCount(100)
                .build();
        System.out.println(countA);
        final Count2 countB = countA.copy()
                .setCount(5)
                .build();
        System.out.println(countB);
        final Location2 location = Location2.builder()
                .setX(1000)
                .setY(2000)
                .build();
        System.out.println(location);
    }
}
