package org.caro.caroserver;

import controller.ServerThread;
import controller.ServerThreadBus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.AdminController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Server.class.getResource("admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static volatile ServerThreadBus serverThreadBus;
    public static Socket socketOfServer;
    public static int ROOM_ID;
    public static volatile AdminController admin;

    public static void main(String[] args) {
        launch();
        ServerSocket listener = null;
        serverThreadBus = new ServerThreadBus();
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;
        ROOM_ID = 100;

        try {
            listener = new ServerSocket(7777);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                100,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8)
        );
//        admin = new Admin();
//        admin.run();
        try {
            while (true) {
                socketOfServer = listener.accept();
                System.out.println(socketOfServer.getInetAddress().getHostAddress());
                ServerThread serverThread = new ServerThread(socketOfServer, clientNumber++);
                serverThreadBus.add(serverThread);
                System.out.println("Số thread đang chạy là: " + serverThreadBus.getLength());
                executor.execute(serverThread);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                listener.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}