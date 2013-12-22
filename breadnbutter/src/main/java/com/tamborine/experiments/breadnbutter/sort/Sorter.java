package com.tamborine.experiments.breadnbutter.sort;

import java.util.Collection;
import java.util.List;

public interface Sorter<T extends Comparable<T>> {

	List<T> sort(List<T> toSort);
}
