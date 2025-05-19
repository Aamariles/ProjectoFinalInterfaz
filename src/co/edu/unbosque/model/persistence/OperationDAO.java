package co.edu.unbosque.model.persistence;

import java.util.ArrayList;

public interface OperationDAO <D,E>{
    public String showAll(); 
	
	public ArrayList<D> getAll(); 
	
	public boolean add(D newData); 
	
	public boolean delete(D toDelete); 
	
	int delete(int index);
	
	public E find(E toFind); 
	
	public boolean update(D previous, D newData); 
	

}
