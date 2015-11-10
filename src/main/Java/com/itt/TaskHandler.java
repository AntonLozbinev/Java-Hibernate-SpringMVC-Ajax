package com.itt;

import com.itt.entity.Task;
import com.itt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class TaskHandler {

    private ReentrantLock readLock = new ReentrantLock();
    private ReentrantLock writeLock = new ReentrantLock();
    private final String DIRECTORY_PATH = "C:\\test\\";

    @Autowired
    private TaskService taskService;

    public String forcedTaskStart(Integer id) {
        Task task = taskService.getTaskById(id);
        String result = doTask(task);
        taskService.updateStatus(task.getId(), result);
        return result;
    }

    @Scheduled(fixedDelay = 3000)
    private void doTaskOnSchedule() {
        List<Task> tasks = taskService.getTasksWithMaxPriorityForExecutor();
        for (Task task : tasks) {
            taskService.updateStatus(task.getId(), "running");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    taskService.updateStatus(task.getId(), doTask(task));
                }
            }).start();
        }
    }

    private String doTask(Task task) {
        if (task.getType().equals("write")) {
            writeLock.lock();
            Path path = Paths.get(DIRECTORY_PATH + ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE) + ".txt");
            try {
                Files.deleteIfExists(path);
                Files.createFile(path);
                try(BufferedWriter writer = Files.newBufferedWriter(path)){
                    writer.write("Hello");
                }
            } catch (IOException e) {
                return "blocked";
            } finally {
                writeLock.unlock();
            }
        }
        if (task.getType().equals("read")) {
            readLock.lock();
            try {
                File file = new File(DIRECTORY_PATH);
                if (file.listFiles().length == 0) {
                    return "blocked";
                }
                Path path = Paths.get(file.listFiles()[0].getAbsolutePath());
                List<String> lines = Files.readAllLines(path);
                Files.delete(path);
            } catch (IOException e) {
                return "blocked";
            } finally {
                readLock.unlock();
            }
        }
        return "complete";
    }
}
