package com.tamborine.experiments.breadnbutter.permutate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.ListUtils;

/**
 * Class will permutate a list using the Steinhaus–Johnson–Trotter algorithum
 * **STEPS**
 * - Check the length of the list, if == 1 return as the list is in all possible permutations
 * - Strip the head element from the list, and permutate buy placing the stripped element with the result of calling the method again.
 * - The permutate involves create n new lists by placing the stripped element in each position of the returned list(s)
 * @author stuart
 *
 */
public class Permutate {

	public List<List<Object>> permutate(List<Object> toPermutate) {
		if(toPermutate==null || toPermutate.size()<2) {
			List<List<Object>> nl = new ArrayList<List<Object>>();
			nl.add(toPermutate);
			return nl;
		}
		Object strippedValue = toPermutate.get(0);
		
		return newPermutations(strippedValue, permutate(new ArrayList<Object>(toPermutate.subList(1, toPermutate.size()))));
	}

	/**
	 * Apply the passed in value to each of the passed in lists to create a new list
	 * @param strippedValue
	 * @param permutate
	 * @return
	 */
	private List<List<Object>> newPermutations(Object strippedValue, List<List<Object>> permutate) {
		List<List<Object>> toReturn = new ArrayList<List<Object>>();
		for(List<Object> p : permutate) {
			toReturn.addAll(apply(strippedValue, p));
		}
		return toReturn;
	}

	private List<? extends List<Object>> apply(Object strippedValue,
			List<Object> p) {
		List<List<Object>> toReturn = new ArrayList<List<Object>>();
		for(int i=0; i<(p.size()+1);i++){
			List<Object> newP = new ArrayList<Object>(p.size()+1);
			newP.addAll(p);
			newP.add(i,strippedValue);
			toReturn.add(newP);
		}
		return toReturn;
	}
	
	
	public int calculateNCk(int n, int k){
		return factor(n)/(factor(k)*factor(n-k));
	}
	
	private int factor(int val) {
		if(val==1) return 1;
		return val*factor(val-1);
	}
	
	public List<List<Object>> nCk(List<Object> src, int k) {
		List<List<Object>> toReturn = new ArrayList<List<Object>>();
		int n = src.size();
			List<Object> newPermutation = new ArrayList<Object>();
			toReturn.addAll(rNCk(src.subList(0, n),k,newPermutation));
		return toReturn;
	}

	private List<? extends List<Object>> rNCk(List<Object> subList,
			int k, List<Object> newPermutation) {
		List<List<Object>> toReturn = new ArrayList<List<Object>>();
		if(newPermutation.size()==k) {
			toReturn.add(newPermutation);
			return toReturn;
		}
		
		for(int i=0;i<subList.size();i++) {
			List<Object> currentState = new ArrayList<Object>(newPermutation);
			currentState.add(subList.get(i));
			toReturn.addAll(rNCk(subList.subList(i+1, subList.size()), k, currentState));
		}
		return toReturn;
	}
}