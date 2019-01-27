import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * This class represents a heap structure.
 */
public class RepSelHeap {
    private int amountOfNumbers;
    private int heapSize;
    private ArrayList<Integer> data;


    public RepSelHeap(int amountOfNumbers, int heapSize) {
        data = new ArrayList<>();
        this.amountOfNumbers = amountOfNumbers;
        this.heapSize = heapSize;
    }


    /**
     * This method sorts the heap, so that we can use it.
     */
    public void buildHeap() {
        ArrayList<Integer> result = new ArrayList<>();

        //as inside the while loop the data gets removed to the dead space, when the data list is empty the sorting is
        // done.
        while (data.size() > 0) {
            int lowest = 0;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(lowest) > data.get(i)) {
                    lowest = i;
                }
            }
            result.add(data.get(lowest));
            data.remove(lowest);
        }
        data = result;
    }

    /**
     * This method prints the heap, so that we have a visual representation of the heap.
     */
    public void print() {

        int placeCounter = 0;
        int counter = 1;
        int formula = (data.size() - 1) / 2;
        int newCounter = 0;

        //here we make known how many rows to print.
        while (formula > 1) {
            formula = (formula - 1) / 2;
            newCounter++;
        }

        //here we print the heap.
        for (int i = 0; i < newCounter + 2; i++) {
            int tempCounter = counter;
            for (int j = 0; j < tempCounter; j++) {

                if (placeCounter < data.size()) {
                    System.out.print("|" + data.get(placeCounter));
                    placeCounter++;
                }
            }
            counter = counter * 2;
            System.out.print("|");
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * here we make sure the smallest number is on top of the heap and then delete it.
     */
    public void deleteMin() {
        buildHeap();
        System.out.println("Deleting lowest value in heap: "+ data.get(0));
        data.remove(data.get(0));
        buildHeap();
    }


    /**
     * here we add a number to the heap, but we do not rebuild the heap, so the number will be at the bottom of the heap.
     *
     * @param num is the number that has to be added.
     */
    public void insert(int num) {

        System.out.println("Inserting into heap num = [" + num + "]");
        data.add(num);
        buildHeap();
    }


    /**
     * here we find the lowest number in the heap.
     *
     * @return the lowest number in the heap.
     */
    public Integer findMin() {
        buildHeap();
        return data.get(0);
    }
    /**
     * This method checks if the heap is empty, or in this case, has only -1's.
     *
     * @param heap is the heap
     * @return if the heap is "empty" or not.
     */
    public boolean empty(int[] heap) {
        for (Integer i : heap) {
            if (i != -1) {
                return false;
            }
        }
        return true;
    }

    public void sortHeap(){
        /**Here we create random data data for the repSelHeap. if you want to write your own input, comment this for loop out and
         * put your own data in the "Input.txt" file, just make sure to change the global "amountOfNumbers" variable accordingly to match the size
         **/
        for (int j = 0; j < amountOfNumbers; j++) {
            data.add((int) (1 + Math.random() * amountOfNumbers));
        }



        //here we write the data to the input.txt file. This is should also be commented out if you want to input your own data
        try {
            String input = "";
            PrintWriter writer = new PrintWriter(new File("Input.txt"));
            for (int i = 0; i < data.size(); i++) {
                if (i != data.size() - 1) {
                    input += data.get(i) + ",";
                } else {
                    input += data.get(i);
                }
            }
            writer.write(input);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //here we declare the heapsize and get back the data from the input file.
        int[] heap = new int[heapSize];
        int counter = 0;
        String[] tempData = new String[amountOfNumbers];
        try {
            Scanner sc = new Scanner(new File("Input.txt"));

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                tempData = line.split(",");
            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < heapSize; i++) {
            heap[i] = Integer.parseInt(tempData[counter]);
            counter++;
        }


        //here we set a temporary heap size which will be able to change in comparison to the heapsize.
        int tempHeapSize = heapSize;

        //here we create the filewriter so we don't have to store the numbers in the RAM.
        File file = new File("Output.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);


        //here we instantiate a couple of counters to know how many runs happened and how long they continued.
        int counterForAverage = 0;
        int runlengthCounter = 0;
        int runCounter = 0;

        //here we have the worked out code of the slides.
        while (counter != amountOfNumbers) {

            int smallest = 0;
            for (int i = 0; i < tempHeapSize; i++) {
                if (i == tempHeapSize - 1) {

                } else if (heap[smallest] > heap[i + 1]) {
                    smallest = i + 1;
                }
            }

            //here we send the smallest to the output.txt file.
            try {
                String i = heap[smallest] + ",";
                bw.write(i);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //here we make sure the number doesn't get used again.
            int tempnum = heap[smallest];
            heap[smallest] = heap[tempHeapSize - 1];
            heap[tempHeapSize - 1] = Integer.parseInt(tempData[counter]);
            counter++;
            runlengthCounter++;


            //if the new number is smaller than the last number this number will be put into the dead space.
            if (!(heap[tempHeapSize - 1] >= tempnum)) {
                tempHeapSize--;

            }

            //if the dead space has consumed all of the heap, the dead space will form a new heap.
            if (tempHeapSize == 0) {

                tempHeapSize = heapSize;
                runCounter++;
                System.out.println("run " + runCounter + ": " + runlengthCounter);
                counterForAverage += runlengthCounter;
                runlengthCounter = 0;


            }
        }

        //here we clean out the rest of the heap.
        //as we can't make the numbers disappear we replace them with -1 and then ignore -1 in the code.
        while (!empty(heap)) {
            int smallest = 0;
            for (int i = 0; i < tempHeapSize; i++) {
                if (i == tempHeapSize - 1) {

                } else if (heap[smallest] > heap[i + 1] && heap[i + 1] != -1) {
                    smallest = i + 1;
                }
            }
            try {
                String i = heap[smallest] + ",";
                if(!i.equals("-1,")){
                    bw.write(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            heap[smallest] = heap[tempHeapSize - 1];
            heap[tempHeapSize - 1] = -1;
            runlengthCounter++;

            tempHeapSize--;
            if (tempHeapSize == 0) {

                tempHeapSize = heapSize;
                runCounter++;
                System.out.println("run " + runCounter + ": " + runlengthCounter);
                counterForAverage += runlengthCounter;
                runlengthCounter = 0;


            }
        }

        //here we print the final statistics of the runs.
        runCounter++;
        System.out.println("run " + runCounter + ": " + runlengthCounter);
        if (runlengthCounter == 0) {
            runCounter--;
        }
        counterForAverage += runlengthCounter;

        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(runCounter + " Runs in total");
        System.out.println("");
        System.out.println("Average run length is: " + (counterForAverage / runCounter));
        System.out.println("");

        try {
            Scanner sc = new Scanner(new File("Output.txt"));
            String[] temporaryData = new String[]{};
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                 temporaryData= line.split(",");
            }
            sc.close();
            for (int i = 0;i<temporaryData.length;i++){
                data.add(Integer.parseInt(temporaryData[i]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}