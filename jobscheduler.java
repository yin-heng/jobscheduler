import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.RandomAccessFile;  
import java.io.Reader;
import java.util.regex.Pattern;  
// In this class, I create a class jobscheduler 
public class jobscheduler{
      RBT rbt = new RBT();
	 Heap  pq = new Heap();
	 boolean job = false; // this boolean varaible is used to show whether their is a running job
     int job_id; //this varaible is used to store the id of running job
     int executedtime, totaltime;// first is used to store the executed time of the running job since last dispatch
                                 // second is used to store the time that will be executed from last dispatch() to next dispatch
     int finishtime;
 writetxt a = new writetxt();	
 // dispatch a new job if no running job, set varaibles above
	public void Dispatch(){
		if(job == true || pq.isEmpty() == true) return;
		if(job == false && pq.isEmpty() == false){
			int pq_res[] = pq.deleteMin();
			
			job_id = pq_res[0];
			int rbt_res[] = rbt.get(pq_res[0]);
			if(rbt_res[1] - rbt_res[2] <= 5){
				totaltime = rbt_res[1] - rbt_res[2];
				executedtime = 0;
				job = true;
				
			}
			else if(rbt_res[1] - rbt_res[2] > 5){
				totaltime = 5;
				executedtime = 0;
				job = true;
				
			}

		}
		
	}
	// run a job
	
	public  void running(){
		if(job == false) return;
		else {
			int rbt_res[] = rbt.get(job_id);  
			if(totaltime > executedtime + 1){
				executedtime++;
				rbt.put(rbt_res[0], rbt_res[1], rbt_res[2] + 1);
			}
			else if(totaltime == executedtime + 1){
				job  = false;
				if(rbt_res[1] > rbt_res[2] + 1){
					pq.insert(job_id, rbt_res[2] + 1);
					
					rbt.put(job_id, rbt_res[1], rbt_res[2] + 1);
				}
				else if(rbt_res[1] == rbt_res[2] + 1){
					rbt.delete(job_id);
				}
				
				
			}

		}
		
	}
	
	public   void Insert(int id, int total_time){
           pq.insert(id, 0);
           rbt.put(id, total_time, 0);
	}
	public  void Insert(int id, int total_time, int executed_time){
		pq.insert(id, executed_time);
		rbt.put(id, total_time, executed_time);
	}
    
	public  void Print(int id){
		int res[] = rbt.get(id);
		
		a.method("output_file.txt", "("+res[0]+","+res[2]+","+res[1]+")" + "\n");
	}
   
    public  void Print(int id1,int id2){
    	int res[] = rbt.Range(id1,id2);
    	for(int i = 0; i < res.length; i = i + 3){
    		if( i != res.length - 3) {
    			    			a.method("output_file.txt", "("+res[i]+","+res[i + 1]+","+res[ i + 2]+")"+",");
    		}
    		if( i == res.length -3) {    		a.method("output_file.txt", "("+res[i]+","+res[i + 1]+","+res[ i + 2]+")"+"\n");}
    	}
    	rbt.res = new int[3];
    	rbt.N = 0;
    	
    	
    }
    public void PreviousJob(int id){
    	int res[] = rbt.Previous(id);
    	    	a.method("output_file.txt", "("+res[0]+","+res[2]+","+res[1]+")" + "\n");
    }
    public  void NextJob(int id){
    	int res[] = rbt.Next(id);
    	    	a.method("output_file.txt", "("+res[0]+","+res[2]+","+res[1]+")" + "\n");
    }
    // this is to get the time of a command
    public static int getTimestamp(String input){
    	String s[] = input.split(":");
    	int k = Integer.parseInt(s[0]);
    	return k;
    	}
    // this is to convert the input string to our command 
    public  void command(String input){
    	
    	input = input.replace(",",":");
    	
    	input = input.replaceAll("\\(", ":");
    	input = input.replaceAll("\\)", ":");
    	String k[] =input.split(":");
    	switch(k[1]){
    	case " Insert":
    		 int x = Integer.parseInt(k[2]);
    		 int y = Integer.parseInt(k[3]);
    		 Insert(x,y);
    		
    		 break;
    	case " PreviousJob":
    		  x = Integer.parseInt(k[2]);
    		PreviousJob(x);
    		  
    		  break;
    	case " NextJob":
    		  x = Integer.parseInt(k[2]);
    		  NextJob(x);
    		  
    		  break;
    	case " PrintJob":
    		  if( k.length == 3){
    			x = Integer.parseInt(k[2]);
    			Print(x);
    			
    		  }
    		  else if (k.length == 4){
    			  x = Integer.parseInt(k[2]);
    	    	  y = Integer.parseInt(k[3]);
    	    	 Print(x,y);
    	    	  
    			  }
    		  break;
    		}
    	
    	}
    
    
    
	
    	
  
    	
    
    public static void main(String[] args) throws IOException{
    	jobscheduler js = new jobscheduler();
    	String fileName = "./" + args[0];
    	File file = new File(fileName);  
        BufferedReader reader = null;  
 	    reader = new BufferedReader(new FileReader(file));  
 	    String input = null; 
 	    input = reader.readLine();
 	    int counter = 0;
 	    while(true && input != null){
 	    	 js.running();
 	    	if(getTimestamp(input) == counter){
 	    		js.command(input);
 	    		input = reader.readLine();
 	    	}
 	    	js.Dispatch();
 	    	counter++;
 	     }
 	    while(js.pq.isEmpty() == false){
 	    	
 	    	js.running();
 	    	js.Dispatch();
 	    	counter++;
 	    }
 	    	
 	    	
 	    }
  		
  	}
  	


