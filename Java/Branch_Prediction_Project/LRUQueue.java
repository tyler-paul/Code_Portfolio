/*
 * Course: CS 59000-06 Computer Architecture
 * Tyler Paul
 * Branch Prediction Project
 *
 * The LRUQueue class implements a queue which keeps track of the relative order
 * of the entries last used in a given underlying set in the set-associative BHT.
 * Initially an index for each entries in the set (i.e. for a total number of entries
 * equal to the associativity of the cache) should enqueued.
 * If a cache hit occurs for a given entry in the BHT, then the index for that entry should be
 * updated (i.e. remove the index from the queue and then enqueue it again to move it
 * to the front of the queue to indicate it was recently used).
 * Lastly, upon a cache miss, dequeue can be called to determine the LRU entry in the
 * set. Then the corresponding entry can be replaced in the BHT.
 *
 * Note: In the below code "offset" refers a value from 0 to associativity that is
 * the index of an entry in a set of the BHT.
 */

import java.util.LinkedList;

public class LRUQueue extends LinkedList<Integer>{

   public LRUQueue(int associativity) {
      //initially enqueue all offsets
      for (int i = 0; i < associativity; i++)
         addFirst(i);
   }

   /* update() updates the LRU Queue, such that if an offset is already
    * in the queue, then it is removed and then added to front of the queue
    * (since it has been recently used)
    */
   public void update(int offset) {
      remove(new Integer(offset));
      addFirst(offset);
   }

   /* dequeue removes the last node in the queue,
    * i.e the least recently used node
    */
   public int dequeue() {
      return removeLast();
   }
   
   //testing
   public static void main(String[] args) {
      LRUQueue lrq = new LRUQueue(4);
      System.out.println(lrq);
      lrq.update(2);
      lrq.dequeue();
      System.out.println(lrq);
   }
}
  