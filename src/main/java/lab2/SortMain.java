package lab2;

import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <h3>JavaSwing</h3>
 * <p></p>
 * @author : hit-lsy
 * @date : 2023/5/1 20:41
 **/
public class SortMain {
    // 总元组数
    static final int TOTAL_NUM = 10000000;

    // 内存能装下的元组数
    static final int LOAD_NUM = 62500;

    // 数据分成几个子集合,以及待排序的元组的内存大小
    static final int WAY_NUM = 16;

    // 第二趟扫描时每个子集合的输入缓冲区(等待被排序)的元组数量
    static final int SORT_NUM = 2500;

    // 第二趟扫描时输出缓冲区(等待被输出)的元组数量
    static final int OUTPUT_NUM = 20000;


    static final String dataPath = System.getProperty("user.dir")+"/src/main/java/lab2/data";

    static final File inputFile = new File(dataPath+"/in.txt");

    static final File outputFile = new File(dataPath+"/out.txt");
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(10,100,1, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
    public static void createData(int n){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))){
            CountDownLatch downLatch = new CountDownLatch(n);
            for (int i = 0; i < n; i+=1) {
                executor.execute(new createThread(writer,downLatch));
            }
            downLatch.await();
            writer.flush();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    static class createThread implements Runnable{
        private BufferedWriter writer;
        private CountDownLatch downLatch;
        public createThread(BufferedWriter writer,CountDownLatch downLatch) {
            this.writer = writer;
            this.downLatch =downLatch;
        }

        @Override
        public void run() {
            try {
                writer.write(new pojo().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            downLatch.countDown();
        }
    }

    public static void writeData(List<pojo> pojos,BufferedWriter writer){
        try{
            for (pojo pojo : pojos) {
                writer.write(pojo.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void firstSort(){
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));){
            List<pojo> cur = null;
             CountDownLatch downLatch= new CountDownLatch(WAY_NUM);
            for (int i = 0; i < WAY_NUM; i++) {
                cur = readData(TOTAL_NUM / WAY_NUM, reader);
                executor.execute(new writeThread(new File(dataPath+"/"+i),cur,downLatch));
            }
            downLatch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class writeThread implements Runnable{
        private File file;
        private List<pojo> cur;
        private CountDownLatch downLatch;
        public writeThread(File file, List<pojo> cur,CountDownLatch downLatch) {
            this.file = file;
            this.cur = cur;
            this.downLatch = downLatch;
        }

        @Override
        public void run() {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {
                Collections.sort(cur);
                writeData(cur, writer);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            downLatch.countDown();
        }
    }
    public static void secondSort(){
        List<BufferedReader> readers = new ArrayList<>(WAY_NUM);
        List<Queue<pojo>> lqp = new ArrayList<>(WAY_NUM);
        for (int i = 0; i < WAY_NUM; i++) {
            try{
                BufferedReader reader = new BufferedReader(new FileReader(dataPath+"/"+i));
                readers.add(reader);
                lqp.add(readData(SORT_NUM,reader));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<pojo> outputList = new ArrayList<>();
        Map<pojo,Integer> qMap = new HashMap<>(WAY_NUM+1);
        PriorityQueue<pojo> sorting = new PriorityQueue<>(WAY_NUM);
        for (Queue<pojo> pojos : lqp) {
            qMap.put(pojos.peek(),qMap.size());
            sorting.add(pojos.peek());
        }
        pojo outputPojo = null;
        Queue<pojo> newPojos = null;
        int i = 0;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))){
            while (!sorting.isEmpty()) {
                outputPojo = sorting.poll();
                lqp.get(qMap.get(outputPojo)).poll();
                if (lqp.get(qMap.get(outputPojo)).isEmpty()) {
                    newPojos = readData(SORT_NUM, readers.get(qMap.get(outputPojo)));
                    if (!newPojos.isEmpty()) {
                        lqp.set(qMap.get(outputPojo), newPojos);
                        sorting.add(newPojos.peek());
                        qMap.put(newPojos.peek(), qMap.get(outputPojo));
                    }
                } else {
                    sorting.add(lqp.get(qMap.get(outputPojo)).peek());
                    qMap.put(lqp.get(qMap.get(outputPojo)).peek(), qMap.get(outputPojo));
                }
                qMap.remove(outputPojo);
                outputList.add(outputPojo);
                if (outputList.size() == OUTPUT_NUM) {
                    writeData(outputList, writer);
                    outputList.clear();
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static LinkedList<pojo> readData(int n,BufferedReader reader){
        LinkedList<pojo> pojos = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        String s = null;
        for (int i = 0; i < n; i++) {
            try {
                sb.setLength(0);
                int j;
                int flag = 0;
                while(flag!=2&&(j=reader.read())!=-1){
                    sb.append((char)j);
                    if(j==' '){
                        flag++;
                    }
                }
                s = sb.substring(0,sb.length());
                if(s.length()!=0){
                    pojos.add(new pojo(Integer.parseInt(s.substring(0,s.indexOf(" "))),s.substring(s.indexOf(" ")+1,s.length()-1)));
                    s = null;
                }else{
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pojos;
    }
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        System.out.println(Calendar.getInstance().getTimeInMillis());
        createData(TOTAL_NUM);
        System.out.println(Calendar.getInstance().getTimeInMillis());
        firstSort();
        System.out.println(Calendar.getInstance().getTimeInMillis());
        secondSort();
        System.out.println(Calendar.getInstance().getTimeInMillis());
    }
}
