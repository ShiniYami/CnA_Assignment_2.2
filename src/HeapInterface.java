
public class HeapInterface {
    public static void main(String[] args) {
        new HeapInterface().run();

    }

    //this is the heapsize.
    private static final int amountOfNumbers = 1000;
    private static final int heapSize = 50;


    public void run() {


        //here we create the heap, so we can call the methods.
        RepSelHeap repSelHeap = new RepSelHeap(amountOfNumbers,heapSize);
        repSelHeap.sortHeap();
        //here we print the still unsorted repSelHeap.
        System.out.println("Unsorted Heap: ");
        repSelHeap.print();

        //here we build the repSelHeap.
        repSelHeap.buildHeap();

        //here we print the repSelHeap again to show it's sorted.
        System.out.println("Sorted list: ");
        repSelHeap.print();

        //here we delete the lowest number so we prove it works.
        repSelHeap.deleteMin();

        //here is the visual representation of the deleteMin.
        repSelHeap.print();

        //here we insert the number 5, this can be any number. but we chose for 5.
        repSelHeap.insert(5);

        //here you can see that the number 5 has been added at the bottom of the list.
        repSelHeap.print();

        //here we print out the lowest number in the repSelHeap.
        System.out.println(repSelHeap.findMin());


    }




}





