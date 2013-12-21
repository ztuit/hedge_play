package com.tamborine.experiments.breadnbutter;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Permutate p = new Permutate();
        Object[] l = {1,2,3,4,5};
        List<List<Object>> r = p.permutate(Arrays.asList(l));
        for(Object o : r) {
        	System.out.println(o);
        }
    }
}
