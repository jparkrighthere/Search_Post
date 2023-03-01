import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//A placeholder class with some code implementation from last week
public class HashtableWithDuplicateKeysBD <String,PostInterface> implements HashtableWithDuplicateKeysInterface<String,PostInterface> {

  private int capacity;
  private Pairs<String,PostInterface>[] hashTable;
  private double loadFactor;

  public HashtableWithDuplicateKeysBD() {
    capacity=8;
    hashTable=new Pairs[capacity];
    loadFactor = (double)getSize() / (double)getCapacity();
  }

  public HashtableWithDuplicateKeysBD(int capacity) {
    this.capacity=capacity;
    hashTable=new Pairs[capacity];
    loadFactor = (double)getSize() / (double)getCapacity();
  }

  @SuppressWarnings("hiding")
  private class Pairs <KeyType, ValueType> {
    private KeyType key;
    private List<ValueType> value;

    //Constructor
    private Pairs (KeyType key, List<ValueType> value) {
      this.key=key;
      this.value=value;
    }

    //key getter
    private KeyType getKey() {
      return key;
    }

    //value setter
    private List<ValueType> getValue() {
      return value;
    }

  }

  /**
   * This is a private helper methods that stores and return an index value depending on hashCode
   * @param key
   * @return index value that will be used in Hashtable
   */
  private int hashIndex(String key) {
    int hashCode = key.hashCode();
    int index;

    index = Math.abs(hashCode) % getCapacity();

    return index;
  }

  /**
   * This is a private helper method that is used when rehashing
   */
  @SuppressWarnings("unchecked")
  private void rehash() {
    Pairs<String,PostInterface>[] tempTable = hashTable.clone();//copy the original hashTable
    capacity *=2;
    this.hashTable = new Pairs[capacity];//Double the capacity and create a new hashTable 
    loadFactor = (double)getSize() / (double)getCapacity();//update loadFactor


    for (int i=0; i<tempTable.length; i++) {
      if (tempTable[i]!=null) {
        put((String)tempTable[i].getKey(),(List<PostInterface>)tempTable[i].getValue());//put all values in original hashTable to new created hashTable
      }
    }
  }

  @Override
  public void put(String key, List<PostInterface> value) throws IllegalArgumentException {

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
    hashTable[index] = new Pairs(key,(List) value);//create a new object and store key, value in the hashTable

    loadFactor = (double)getSize() / (double)getCapacity();//update loadFactor
    if (loadFactor >= 0.7) {//check if it needs rehashing
      rehash();
    }
  }

  @Override
  public boolean containsKey(String key) {
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

  @Override
  public List<PostInterface> get(String key) throws NoSuchElementException {

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
        return (List<PostInterface>) hashTable[index].getValue();
      }
    }
    throw new NoSuchElementException("No Key Found");
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<PostInterface> remove(String key) throws NoSuchElementException {

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
        List<PostInterface> value = hashTable[index].getValue();//store value 
        hashTable[index]=new Pairs ("",null);//Overwrite the index with empty object
        loadFactor = (double)getSize() / (double)getCapacity();//update loadFactor

        return value;
      }
    }
    throw new NoSuchElementException("No Key Found");
  }

  @Override
  public void clear() {

    for (int i=0; i<getCapacity(); i++) {
      hashTable[i]=null;
    }
  }

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

  @Override
  public int getCapacity() {
    return this.capacity;
  }

  @Override
  public void putOne(String key, PostInterface value) {
    // TODO Auto-generated method stub
    if (key==null) {//if key is not valid, throw exception
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

    if (containsKey(key)) {
      hashTable[index].getValue().add(value);//create a new object and store key, value in the hashTable
    }
    else {
      List<PostInterface> valueList = new ArrayList<PostInterface>();
      valueList.add(value);
      hashTable[index] = new Pairs(key,valueList);
    }

    loadFactor = (double)getSize() / (double)getCapacity();//update loadFactor
    if (loadFactor >= 0.7) {//check if it needs rehashing
      rehash();
    }
  }

  @Override
  public void removeOne(String key, PostInterface value) {
    // TODO Auto-generated method stub
    remove(key);
  }

  @Override
  public int getNumberOfValues() {
    // TODO Auto-generated method stub
    return getSize();
  }

}