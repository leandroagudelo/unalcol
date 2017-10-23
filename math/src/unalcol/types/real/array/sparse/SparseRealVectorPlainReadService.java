/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.types.real.array.sparse;

import java.io.IOException;

import unalcol.io.Read;
import unalcol.io.ReadWrapper;
import unalcol.services.AbstractMicroService;
import unalcol.services.MicroService;
import unalcol.types.collection.UnalcolIterator;

/**
 *
 * @author jgomez
 */
public class SparseRealVectorPlainReadService extends MicroService<SparseRealVector> implements Read<SparseRealVector>{
	public static final String integer = "Read.integer";
	public static final String real = "Read.double";
    /**
     * Character used for separating the values in the array
     */
    protected char separator = ' ';

    protected boolean read_dimension = true;
    protected int n = -1;
	protected Read<Integer> ri=null;
	protected Read<Double> rr=null;

    /**
     * Creates an integer array persistent method that uses an space for separatng the array values
     */
    public SparseRealVectorPlainReadService() {}

    /**
     * Creates a double array persistent method that uses the give charater for separating the array values
     * @param separator Character used for separating the array values
     */
    public SparseRealVectorPlainReadService(char separator) {
        this.separator = separator;
    }

    public SparseRealVectorPlainReadService(int n) {
        this.n = n;
        read_dimension = n<=0;
    }

    public SparseRealVectorPlainReadService(int n, char separator) {
        this.n = n;
        read_dimension = n<=0;
        this.separator = separator;
    }

	public AbstractMicroService<?> wrap(String id){
		if( id.equals(integer) ) return new ReadWrapper<Integer>();
		if( id.equals(real) ) return new ReadWrapper<Double>();
		return null;
	}
	
    public void setIntReader( Read<Integer> ri ){ this.ri = ri; }
	
	public void setDoubleReader( Read<Double> rr ){ this.rr = rr; }
	
	public SparseRealVector read( UnalcolIterator<?, Integer> reader ) throws IOException{
    	@SuppressWarnings("unchecked")
		Read<Integer> ri = (Read<Integer>)getMicroService(integer);
    	ri.setCaller(n);
        if( read_dimension ){
        	n = ri.read(reader);
            Read.readSeparator(reader, separator);        	
        }
    	@SuppressWarnings("unchecked")
		Read<Double> rr = (Read<Double>)getMicroService(real);
    	rr.setCaller(0.0);
        SparseRealVector d = new SparseRealVector(n);
        int k;
        double v;
        while( reader.hasNext() ){
            k = ri.read(reader);
            Read.readSeparator(reader, separator);
            v = rr.read(reader);
            Read.readSeparator(reader, separator);
            d.set(k,(Double)v);
        }
        return d;
    }
}