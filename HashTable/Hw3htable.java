import java.util.Random;
  
  public class Hw3htable {
	  Point [] p = new Point[10000];
	  Integer [] iarray = new Integer[1000];
	  
	  void initializeTable() {
	    Random r=new Random();
	    for (int i=0;i<p.length;i++) {
	      int x=r.nextInt(60);
	      int y=r.nextInt(60);
	      p[i] = new Point(x, y);
	    }
	    for (int i=0;i<iarray.length;i++) {
	      iarray[i]=new Integer(r.nextInt(10000));
	    }
	  }
	    
	    
	  boolean putOrGet(Random r) {
	    //10% puts   90% gets
	    return (r.nextInt(1000) > 900);
	  }
	  
	  Point getRandomPoint(Random r) {
	    return p[r.nextInt(p.length)];
	  }

	  Integer getRandomInt(Random r) {
	    return iarray[r.nextInt(iarray.length)];
	  }
	  
	  void action(HashTable table) {
	    Random r=new Random();
	    for(int k = 0; k < 5000000 ; k++) { //5000000
	      if(putOrGet(r)){
	        Point point = getRandomPoint(r);
	        Integer i = getRandomInt(r);
	        table.put(point, i);
	      } else {
	        Point point = getRandomPoint(r);
	        table.get(point);
	      }
	    }
	  }

/*
	    public static void main(String[] args)
	    {
	    	Point point1=new Point(10,12);
	    	Point point2=new Point(21,22);
	    	Point point3=new Point(10,12);
	    	Point point4=new Point(13,12);
	    	Point point5=new Point(10,17);
	    	Point point6=new Point(18,23);
	    	Point point7=new Point(12,1009);
	    	Point point8=new Point(2,109);
	    	
	        HashTable t = new HashTable();
	        t.put(point1, 361);
	        t.put(point2, 221);
	        t.put(point3, 881); //this key is the same as point1
	        t.put(point4, 41);
	        t.put(point5, 191);
	        t.put(point6, 11);
	        t.put(point7, 108);//this key' hashcode is the same as point4
	        System.out.println(t.get(point1)); //to check whether point1's value is replaced by point3's value
	        System.out.println(t.get(point4));
	        System.out.println(t.get(point7));
	        System.out.println(t.get(point2));
	        System.out.println(t.get(point8)); //this key doesn't exist in the hashtable
	        System.out.println("table_size:"+t.size);
	    } 
*/

	  
	  
  static class thread extends Thread{
	  Hw3htable t;
	  HashTable table;
	    
	  public thread(HashTable table, Hw3htable t){
		  this.t=t;
		  this.table=table;
	  }
	  
	  public void run(){
		  try {
              t.action(table);
            } catch(Exception e){
              e.printStackTrace();
            }
	  }
	  
  }
 
  public static void main(String...args) {
	    Hw3htable t=new Hw3htable();
	    HashTable table = new HashTable();
	    t.initializeTable();
	    Thread[] threads=new Thread[4]; // Use 4 Threads
	    long starttime=System.currentTimeMillis();
	    
	    for(int i=0;i<threads.length;i++) {
	    	threads[i]=new thread(table,t);
	    	threads[i].start();
	    }
	    
	    for(int i=0;i<threads.length;i++) {
	      try {
	        threads[i].join();
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }

	    long endtime=System.currentTimeMillis();
	    System.out.println("Total time in mS: " + (endtime-starttime));
	    System.out.println("Total size is: " + table.size());
	  }
	  
}
