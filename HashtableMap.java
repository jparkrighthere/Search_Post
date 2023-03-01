import java.util.NoSuchElementException;

// --== CS400 Project One File Header ==--
// Name: Jeonghyeon Park
// CSL Username: jeonghyeon
// Email: jpark634@wisc.edu
// Lecture #: COMPSCI400: Programming III (001) SP23, TH @ 1:00PM
// Notes to Grader: none

/**
 * This is HashtableMap class
 * @author park
 *
 * @param <KeyType>
 * @param <ValueType>
 */
public class HashtableMap <KeyType, ValueType> implements MapADT<KeyType, ValueType> {
  //instance variables
  private int capacity;
  protected Pairs<KeyType,ValueType>[] hashTable;
  private double loadFactor;
  
  /**
   * This is a private class that holds key and value at once in array.
   * @param <KeyType>
   * @param <ValueType>
   */
  @SuppressWarnings("hiding")
  private class Pairs <KeyType, ValueType> {
    private KeyType key;
    private ValueType value;
    
    //Constructor
    private Pairs (KeyType key, ValueType value) {
      this.key=key;
      this.value=value;
    }
    
    //key getter
    private KeyType getKey() {
      return key;
    }
    
    //value setter
    private ValueType getValue() {
      return value;
    }

  }
  
  //default constructor
  @SuppressWarnings("unchecked")
  public HashtableMap() {
    capacity=8;
    hashTable=new Pairs[capacity];
    loadFactor = (double)getSize() / (double)getCapacity();
  }
  
  //Overloading constructor
  @SuppressWarnings("unchecked")
  public HashtableMap(int capacity) {
    this.capacity=capacity;
    hashTable=new Pairs[capacity];
    loadFactor = (double)getSize() / (double)getCapacity();
  }
  
  /**
   * This is a private helper methods that stores and return an index value depending on hashCode
   * @param key
   * @return index value that will be used in Hashtable
   */
  private int hashIndex(KeyType key) {
    int hashCode = key.hashCode();
    int index;

    index = Math.abs(hashCode) % getCapacity();

    return index;
  }

  /**
   * add a new key-value pair/mapping to this collection
   * throws exception when key is null or duplicate of one already stored
   * @param key and value
   */
  @Override
  @SuppressWarnings("unchecked")
  public void put(KeyType key, ValueType value) throws IllegalArgumentException{
    if (key==null || containsKey(key)) {//if key is not valid, throw exception
      throw new IllegalArgumentException("Not valid Key");
    }

    int index = hashIndex(key);

    for (int i=0;i<getCapacity();i++) {
      index = index +i;

      if (index>=getCapacity()) {//if the index gets same or bigger than capacity by linear-probing, subtract.
        index=index-getCapacity();
      }

      if (hashTable[index]==null || hashTable[index].getKey()!="") {//if hashTable is empty at the index when i=0, break the loop and stores the value there,
        break;                     //Otherwise, start linear-probe by increasing i.
      }
    }
    hashTable[index] = new Pairs(key,value);//create a new object and store key, value in the hashTable

    loadFactor = (double)getSize() / (double)getCapacity();//update loadFactor
    if (loadFactor >= 0.7) {//check if it needs rehashing
      rehash();
    }
  }
  
  /**
   * This is a private helper method that is used when rehashing
   */
  @SuppressWarnings("unchecked")
  private void rehash() {
    Pairs<KeyType,ValueType>[] tempTable = hashTable.clone();//copy the original hashTable
    capacity *=2;
    this.hashTable = new Pairs[capacity];//Double the capacity and create a new hashTable 
    loadFactor = (double)getSize() / (double)getCapacity();//update loadFactor


    for (int i=0; i<tempTable.length; i++) {
      if (tempTable[i]!=null) {
        put((KeyType)tempTable[i].getKey(),(ValueType)tempTable[i].getValue());//put all values in original hashTable to new created hashTable
      }
    }
  }

  /**
   * check whether a key maps to a value within this collection
   * @param key
   */
  @Override
  public boolean containsKey(KeyType key) {
    int index = hashIndex(key);
    if (hashTable[index]==null) {//if it is empty at the index
      return false;
    }
    for (int i=0; i<getCapacity(); i++) {//find key,value that are stored in a different index due to linear-probe
      if (hashTable[i]==null) {
        continue;
      }
      else if (hashTable[i].getKey().equals(key)) {
        return true;
      }
    }
    return false;
  }

  /**
   * retrieve the specific value that a key maps to
   * throws exception when key is not stored in this collection
   * @param key
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException{
    int index = hashIndex(key);

    for (int i=0; i<getCapacity(); i++) { 
      index = index+i;

      if (index >= getCapacity()) {//if the index gets same or bigger than capacity by linear-probing, subtract.
        index= index-getCapacity();
      }
      if (hashTable[index] == null) {//throw exception if it is empty
        throw new NoSuchElementException();
      }

      if (hashTable[index].getKey().equals(key)) {
        return (ValueType) hashTable[index].getValue();
      }
    }
    throw new NoSuchElementException("No Key Found");
  }

  /**
   * remove the mapping for a given key from this collection
   * throws exception when key is not stored in this collection
   * @param key
   */
  @SuppressWarnings("unchecked")
  @Override
  public ValueType remove(KeyType key) throws NoSuchElementException{
    int index = hashIndex(key);

    if (key == null || hashTable[index] == null) {//if key is null or hashTable is empty at the index, throw exception
      throw new NoSuchElementException("No Key Found");
    }

    for (int i=0; i<getCapacity(); i++) {
      index = index+ i;

      if (index >= getCapacity()) {//if the index gets same or bigger than capacity by linear-probing, subtract.
        index= index-getCapacity();
      }

      if (hashTable[index].getKey().equals(key)) {
        ValueType value = hashTable[index].getValue();//store value 
        hashTable[index]=new Pairs ("","");//Overwrite the index with empty object
        loadFactor = (double)getSize() / (double)getCapacity();//update loadFactor

        return value;
      }
    }
    throw new NoSuchElementException("No Key Found");
  }

  /**
   * remove all key-value pairs from this collection
   */
  @Override
  public void clear() {
    for (int i=0; i<getCapacity(); i++) {
      hashTable[i]=null;
    }
  }

  /**
   * retrieve the number of keys stored within this collection
   */
  @Override
  public int getSize() {
    int size = 0;
    for (int i=0; i<getCapacity(); i++) {
      if (hashTable[i]!=null && hashTable[i].getKey()!="") {
        size+=1;
      }
    }
    return size;
  }

  /**
   * retrieve this collection's capacity (size of its underlying array)
   */
  @Override
  public int getCapacity() {
    return this.capacity;
  }

}