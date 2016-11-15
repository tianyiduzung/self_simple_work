package linkedlist;
//双向链表

public class linked1 {  
    private class Data{  
        private Object obj;  
        private Data next = null;  
          
        Data(Object obj){  
            this.obj = obj;  
        }  
    }  
      
    private Data first = null;  
    private Data last = null;  
      
    public void insertFirst(Object obj){  
        Data data = new Data(obj);  
        if(first == null)  
            last = data; //注意，第一个插入的数据就是最后一个，因为这是前向插入
        data.next = first;  
        first = data;  
    }  
      
    public void insertLast(Object obj){  
        Data data = new Data(obj);  
        if(first == null){  
            first = data;  
        }else{  
            last.next = data;  
  
        }  
        last = data;  
    }  
      
    public Object deleteFirst() throws Exception{  
          if(first == null)  
             throw new Exception("empty");  
          Data temp = first;  
          if(first.next == null)  
             last = null;  
          first = first.next;  
          return temp.obj;  
   }     
      
    public void deleteLast() throws Exception{  
        if(first == null)  
            throw new Exception("empty");  
        if(first.next == null){  
            first = null;  
            last = null;  
        }else{  
            Data temp = first;  
            while(temp.next != null){  
                if(temp.next == last){  
                    last = temp;  
                    last.next = null;  
                    break;  
                }  
                temp = temp.next;  
            }  
        }  
    }  
      
    public void display(){  
        if(first == null)  
            System.out.println("empty");  
        Data cur = first;  
        while(cur != null){  
            System.out.print(cur.obj.toString() + " -> ");  
            cur = cur.next;  
        }  
        System.out.print("\n");  
    }  
      
    public static void main(String[] args) throws Exception {  
        linked1 fll = new linked1();  
        fll.insertFirst(2);  
        fll.insertFirst(1);  
        fll.display();  
        fll.insertLast(3);  
        fll.display();  
        fll.deleteFirst();  
        fll.display();  
        fll.deleteLast();  
        fll.display();  
    }  
}  
