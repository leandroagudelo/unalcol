package unalcol.search.population.variation;

import unalcol.types.collection.vector.*;

/**
 * <p>Title:ArityTwo</p>
 * <p>Description: A binary operator </p>
 * <p>Copyright:    Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public abstract class ArityTwo<T> extends Operator<T> {

  /**
   * Apply the unary genetic operator over the individual
   * @param obj Individual to be modified by the genetic operator
   * @return extra information of the genetic operator
   */
  public abstract Vector<T> apply(T one, T two);

  /**
   * Apply the genetic operator to the first and second individuals in the population of parents
   * This method is parent compatible
   * @param parents Collection of parents used by the genetic operator (selects just the first and second
   * individuals in the collection
   * @return A collection of individuals generated by the gentic operator
   */
  @SuppressWarnings("unchecked")
  @Override
  public Vector<T> apply( T... parents ){
	  Vector<T> v = new Vector<T>();
      for( int i=0; i<parents.length;i+=2){
    	  Vector<T> p = apply( parents[i], parents[(i+1)%parents.length] );
    	  for( T x : p ){
    		  v.add(x);
    	  }  
      }  
      return v;
  }

  /**
   * Return the genetic operator arity
   * @return the genetic operator arity
   */
  @Override
  public int arity() { return 2; }
}
