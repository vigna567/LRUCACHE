import java.util.*;

class CDLLNode {
    int key;
    int val;

    CDLLNode prev;
    CDLLNode next;

    public CDLLNode(int k, int v){
        this.key = k; this.val = v;
        this.prev = this.next = null;
    }
}

class CDLL {
    CDLLNode head = null;

    CDLLNode insAtBegin(int k, int v){
        CDLLNode nn = new CDLLNode(k, v);
        nn.next = nn.prev = nn;
        if(head == null) {
            head = nn;
            return nn;
        } else {
            CDLLNode last = head.prev;
            nn.next = head; head.prev = nn;
            last.next = nn; nn.prev = last;
            head = nn;
            return nn;
        }
    }
    
    void print(){
        CDLLNode temp=head;
        if(temp==null){System.out.println();return;}
        while(temp.next!=head){
            System.out.print("("+temp.key+","+temp.val+") ->");
            temp=temp.next;
        }
        System.out.println("("+temp.key+","+temp.val+")");
    }
    
    int delLast(){
        if(head == null){
            return -1;
        }
        CDLLNode last = head.prev;
        if(head == last){
            head = null;
            return last.key;
        }
        last.prev.next = head;
        head.prev = last.prev;
        return last.key;
    }

    void moveAtFront(CDLLNode nodeToMove){
        if(nodeToMove == head){ // already at front - nothing to do
            return;
        }
        nodeToMove.prev.next = nodeToMove.next;
        nodeToMove.next.prev = nodeToMove.prev;

        CDLLNode last = head.prev;
        nodeToMove.next = head; head.prev = nodeToMove;
        last.next = nodeToMove; nodeToMove.prev = last;
        head = nodeToMove;
    }

}

class LRUCache {
    int capacity;
    int size;
    CDLL MRUOrderList;
    Map<Integer, CDLLNode> mp;

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.size = 0;
        MRUOrderList = new CDLL();
        mp = new HashMap<>();
    }

    int get(int key){ // O(1)
        if(mp.containsKey(key)){
            CDLLNode node = mp.get(key);
            MRUOrderList.moveAtFront(node);
            return node.val;
        } else {
            return -1;
        }
    }

    void put(int k, int v){
        if(mp.containsKey(k)){ // update existing entry in cache
            CDLLNode node = mp.get(k);
            node.val = v; // update value part
            MRUOrderList.moveAtFront(node);
        } else { // ins new entry
            if(size < capacity){
                CDLLNode nn = MRUOrderList.insAtBegin(k, v);
                mp.put(k, nn);
                size++;
            } else {
                int delKey = MRUOrderList.delLast();
                mp.remove(delKey);
                CDLLNode nn = MRUOrderList.insAtBegin(k, v);
                mp.put(k, nn);
            }
        }
    }
    
    
}

class Main {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(3);
        System.out.println(cache.get(7));
        cache.put(1, 100);
        cache.put(0, 0);
        cache.put(7, 70);
        System.out.println(cache.get(0));
        cache.put(99, 99);
        System.out.println(cache.get(1));
        System.out.println(cache.get(7));
    }
}