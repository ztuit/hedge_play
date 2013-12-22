package com.tamborine.experiments.breadnbutter.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MergeSort<T extends Comparable<T>> implements Sorter<T>{

	
	private List<T> recurseSort(List<T> toSort) {
		if(toSort.size()==0) return new ArrayList<T>();
		if(toSort.size()<3) {
			List<T> toReturn = new ArrayList<T>(toSort);
			if(toReturn.size()==1) {
				toReturn = new ArrayList<T>();
				toReturn.add(toSort.get(0));
				return toReturn;
			}
			T val1 = toReturn.get(0);
			T val2 = toReturn.get(1);
			if(val1.compareTo((T) val2)==1) {
				toReturn.set(0, val2);
				toReturn.set(1, val1);
			}
			return toReturn;
		}
		int inputSize = (toSort.size()/2);
		
		return merge(recurseSort(toSort.subList(0, inputSize)),recurseSort(toSort.subList(inputSize,toSort.size())));
	}

	/**
	 * Each sublist is sorted to the sort
	 * lists need to be merged into a new one
	 * @param recurseSort
	 * @param recurseSort2
	 * @return
	 */
	private List<T> merge(List<T> li,
			List<T> lj) {
		System.out.println("Merging: " + li + " and " + lj);
		List<T> toReturn = new ArrayList<T>(li.size()*2);
		int i=0,j=0;
		while(true) {
			T val1 = li.get(i);
			T val2 = lj.get(j);
			if(val1.compareTo((T) val2)<0) {
				toReturn.add(val1);
				i++;
			} else if(val1.compareTo((T) val2)>0) {
				toReturn.add(val2);
				j++;
			} else {
				toReturn.add(val1);
				toReturn.add(val2);
				i++;j++;
			}
			//If at end of either, then add all of others and return
			if(i==li.size()) {
				toReturn.addAll(lj.subList(j, lj.size()));
				return toReturn;
			}
			if(j==lj.size()) {
				toReturn.addAll(li.subList(i, li.size()));
				return toReturn;
			}
		}
	}

	public List<T> sort(List<T> list) {
		// TODO Auto-generated method stub
		List<T> lToSort = new ArrayList<T>(list);
		return this.recurseSort(lToSort);
	}

}
