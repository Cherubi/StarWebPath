/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.searchpath;

/**
 *
 * @author Cherubi
 */

public class MinimumHeap {
	private Vortex[] heap;
	private int length;
	
	public MinimumHeap() {
		heap = new Vortex[50];
		length = 0;
	}
	
	/**
	 * Adds a new Vortex to the minimum heap.
	 * 
	 * @param newVortex Vortex to be added.
	 */
	public void add(Vortex newVortex) {
		if (length==heap.length) {
			doubleLength();
		}
		
		length++; //indeksiä nolla ei käytetä
		heap[length] = newVortex;
		heapify(length/2);
	}
	
	private void doubleLength() {
		Vortex[] newHeap = new Vortex[heap.length*2];
		for (int i = 0; i<heap.length; i++) {
			newHeap[i] = heap[i];
		}
		this.heap = newHeap;
	}
	
	/**
	 * Remove the smallest vortex from the heap.
	 * 
	 * @return Smallest vortex
	 */
	public Vortex remove() {
		if (length == 0) {
			return null;
		}
		
		Vortex minVortex = heap[1];
		heap[1] = heap[length];
		length--;
		
		heapifyDown(1);
		
		return minVortex;
	}
	
	private void heapify(int i) {
		if (i <= 0 && i >= length) {
			return;
		}
		
		int left = 2*i;
		int right = 2*i + 1;
		
		if (right < length) {
			//left is smaller
			if (heap[left].compareTo(heap[right]) < 0) {
				if (heap[i].compareTo(heap[left]) > 0) {
					switchTwo(i, left);
					heapify(left);
				}
			}
			//right is smaller
			else {
				if (heap[i].compareTo(heap[right]) > 0) {
					switchTwo(i, right);
					heapify(right);
				}
			}
		}
		else if (left < length) {
			if (heap[i].compareTo(heap[left]) > 0) {
				switchTwo(i, left);
				heapify(i/2);
			}
		}
	}
	
	private void heapifyDown(int parentId) {
		if (parentId <= 0 && parentId >= length) {
			return;
		}
		
		int left = 2*parentId;
		int right = 2*parentId + 1;
		
		if (right < length) {
			//left is smaller
			if (heap[left].compareTo(heap[right]) < 0) {
				if (heap[parentId].compareTo(heap[left]) > 0) {
					switchTwo(parentId, left);
					heapifyDown(left);
				}
			}
			//right is smaller
			else {
				if (heap[parentId].compareTo(heap[right]) > 0) {
					switchTwo(parentId, right);
					heapifyDown(right);
				}
			}
		}
		else if (left < length) {
			if (heap[parentId].compareTo(heap[left]) > 0) {
				switchTwo(parentId, left);
				heapifyDown(parentId/2);
			}
		}
	}
	
	private void switchTwo(int parent, int child) {
		Vortex newParent = heap[child];
		heap[child] = heap[parent];
		heap[parent] = newParent;
	}
	
	/**
	 * Decreases the value of one vortex. Will not increase the value.
	 * 
	 * @param vortex Vortex to be changed
	 * @param dayDistance New daydistance to the vortex
	 * @param dayTravelDistance New daytraveldistance to the vortex
	 */
	public void heapDecKey(Vortex vortex, int dayDistance, double dayTravelDistance) {
		if (vortex.getDayDistance() < dayDistance) {
			return;
		}
		if (vortex.getDayDistance() == dayDistance && vortex.getDayTravelDistance() < dayTravelDistance) {
			return;
		}
		
		int i = 1;
		for (i=1; i<=length; i++) { //right upper limit?
			if (vortex.equals(heap[i])) {
				break;
			}
		}
		
		vortex.setDistance(dayDistance, dayTravelDistance);
		heapify(i);
	}
	
	/**
	 * Tells if the heap is empty.
	 * 
	 * @return Boolean of the emptiness
	 */
	public boolean isEmpty() {
		return length == 0;
	}
}
