import functions.Functions;
import functions.InappropriateFunctionPointException;
import functions.basic.Log;
import threads.*;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) throws InappropriateFunctionPointException {

        try {
            complicatedThreads();
            simpleThreads();
            nonThread();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void complicatedThreads() throws InterruptedException {

        final int TASKS_COUNT = 200;
        BlockingQueue<Task> queue = new LinkedBlockingQueue<>(1);

        Thread generator = new Generator(queue, TASKS_COUNT);
        Thread integrator = new Integrator(queue, TASKS_COUNT);

        generator.start();
        integrator.start();

        Thread.sleep(50);
        generator.interrupt();
        integrator.interrupt();

    }

    public static void simpleThreads() {

        SimpleTask task = new SimpleTask();
        task.setTasksCount(100);

        Thread generator = new Thread(new SimpleGenerator(task));
        Thread integrator = new Thread(new SimpleIntegrator(task));

        generator.start();
        integrator.start();

    }

    public static void nonThread() throws InterruptedException {

        SimpleTask task = new SimpleTask();
        task.setTasksCount(100);

        for (int i = 0; i < task.getTasksCount(); ++i) {

            task.setFunction(new Log(new Random().nextDouble(9.0) + 1.0));
            task.setLeftX(new Random().nextDouble(100.0));
            task.setRightX(new Random().nextDouble(100.0) + 100.0);
            task.setStep(new Random().nextDouble(1.0));
            System.out.printf("Source %f %f %f%n", task.getLeftX(), task.getRightX(), task.getStep());

            double result = Functions.integral(task.getFunction(),  task.getLeftX(), task.getRightX(), task.getStep());
            System.out.printf("Result %f %f %f %f%n", task.getLeftX(), task.getRightX(), task.getStep(), result);

        }

    }


}
