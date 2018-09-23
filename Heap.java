public class Heap
{
   private static final int CAPACITY = 2;

   private int size;            // Number of elements in heap
   private int[] ex_time;     // The executed_time array
   private int[] id;         // the id array

 // Constructor of a new heap
   public Heap()
   {
      size = 0;
      ex_time =  new int [CAPACITY];
      id = new int[CAPACITY];
   }

   // Whether the heap is empty or not
   public boolean isEmpty(){
	   return size == 0;
   }
   
   // each time after deletion, we need to adjust the heap structure in order to mantain heap property;
   private void percolatingDown(int k)
   {
      int tmp = ex_time[k];
      int id_tmp = id[k];
      int child;

      for(; 2*k <= size; k = child)
      {
         child = 2*k;

         if(child != size && ex_time[child] > ex_time[child + 1]) child++;

         if(tmp > ex_time[child])  {ex_time[k] = ex_time[child]; id[k] = id[child];}
         else
                break;
      }
      ex_time[k] = tmp;
      id[k] = id_tmp;
   }

   // return the pairs with minimum executed_time and corresponding id;

   public int[] deleteMin() throws RuntimeException
   {
      if (size == 0) throw new RuntimeException();
      int min[] ={ id[1],ex_time[1]};
       
      id[1] = id[size];
      ex_time[1] = ex_time[size--];
      
      percolatingDown(1);
      return min;
	}

 // Insert a pairs;
   public void insert(int id, int e_time)
   {
      if(size == ex_time.length - 1) doubleSize();

      //Insert a new item to the end of the array
      int pos = ++size;

      //Percolate up in order to maintain priority queue property
      for(; pos > 1 && e_time < ex_time[pos/2]; pos = pos/2 )
         {ex_time[pos] = ex_time[pos/2];
          this.id[pos] = this.id[pos/2];
         }
      
      ex_time[pos] = e_time;
      this.id[pos] = id;
   }
   
   // double the executed_time array size and id size when they are full
   private void doubleSize()
   {  int [] oldid = id;
      int [] old = ex_time;
      ex_time =  new int[ex_time.length * 2];
      id = new int[id.length * 2];
      System.arraycopy(old, 1, ex_time, 1, size);
      System.arraycopy(oldid, 1, id, 1, size);
   }
