package com.tamborine.experiments.breadnbutter;

import java.util.Arrays;
import java.util.List;

import com.tamborine.experiments.breadnbutter.graph.tree.Tree;
import com.tamborine.experiments.breadnbutter.graph.tree.TreeNode;
import com.tamborine.experiments.breadnbutter.permutate.Permutate;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	runTreeBuild();
    }
    
    private static void runPermute(){
        Permutate p = new Permutate();
        Object[] l = {1,2,3,4,5};
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
