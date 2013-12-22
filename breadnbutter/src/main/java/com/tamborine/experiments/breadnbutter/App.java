package com.tamborine.experiments.breadnbutter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tamborine.experiments.breadnbutter.graph.tree.Tree;
import com.tamborine.experiments.breadnbutter.graph.tree.TreeNode;
import com.tamborine.experiments.breadnbutter.permutate.Permutate;
import com.tamborine.experiments.breadnbutter.sort.MergeSort;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	runMergeSort();
    }
    
    private static void runMergeSort() {
    	Comparable c;
   	 MergeSort<Integer> p = new MergeSort<Integer>();
     Integer[] l = {1,2,3,4,5,6,7,8,9,10,11,12};
     List<Integer> toSort = new ArrayList<Integer>(Arrays.asList(l));
     List<Integer> r = p.sort(toSort);
     
     for(Object o : r) {
     	System.out.println(o);
     }
    // System.out.println("Found " + r.size() + " permutations, should have " + p.calculateNCk(12, 3));
}
    
    private static void runNCk() {
    	 Permutate p = new Permutate();
         Object[] l = {1,2,3,4,5,6,7,8,9,10,11,12};
         List<List<Object>> r = p.nCk(Arrays.asList(l),3);
         
         for(Object o : r) {
         	System.out.println(o);
         }
         System.out.println("Found " + r.size() + " permutations, should have " + p.calculateNCk(12, 3));
    }
    
    private static void runPermute(){
        Permutate p = new Permutate();
        Object[] l = {1,2,3,4};
        List<List<Object>> r = p.permutate(Arrays.asList(l));
        for(Object o : r) {
        	System.out.println(o);
        }
    }
    
    private static void runTreeBuild(){
    	Integer[] l = {1,2,3,4,5,6,7,8,9,10};
    	TreeNode<Integer> n = Tree.buildTree(Arrays.asList(l));
    	System.out.println(Tree.findNode(n, 4));
    	
    }

    
    
    
}
