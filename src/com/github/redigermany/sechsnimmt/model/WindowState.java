package com.github.redigermany.sechsnimmt.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WindowState {
    private final String fileName;
    private double windowX = 0;
    private double windowY = 0;

    private Thread moveThread;
    private void startThread(){
        if(moveThread==null || !moveThread.getName().equals("moveThread")){
            moveThread = new Thread(()->{
                try {
                    Thread.sleep(1000);
                    try(FileWriter fw = new FileWriter(fileName)) {
                        fw.write("x=" + windowX+"\ny=" + windowY);
                    }catch(IOException ignored){
                    }
                    moveThread.interrupt();
                    moveThread = null;
                } catch (InterruptedException ignored) {
                }
            },"moveThread");
            moveThread.start();
        }
    }

    public double getX() {
        return windowX;
    }
    public double getY() {
        return windowY;
    }
    public void setX(double n) {
        windowX = n;
        startThread();
    }
    public void setY(double n) {
        windowY = n;
        startThread();
    }

    private void parseLine(String line){
        String[] ln = line.split("=");
        if(ln[0].equals("x")) windowX = Double.parseDouble(ln[1]);
        if(ln[0].equals("y")) windowY = Double.parseDouble(ln[1]);
    }
    private void readConfig(){
        try(BufferedReader fr = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line=fr.readLine())!=null){
                parseLine(line);
            }
        }catch(IOException ignored) {
        }
    }
    public WindowState(String fileName){
        this.fileName = fileName;
        readConfig();
    }
}
