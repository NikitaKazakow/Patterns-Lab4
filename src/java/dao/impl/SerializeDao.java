package dao.impl;

import dao.Dao;
import dao.factory.Transport;
import dao.factory.TransportManager;

import java.io.*;

public class SerializeDao<T extends Transport> implements Dao<T> {

    @Override
    public T read() {
        T transport = (T) TransportManager.createInstance("1", 1);
        try (FileInputStream fileInputStream = new FileInputStream("src/resources/dao/" + transport.getClass().getSimpleName() + ".ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            transport = (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return transport;
    }

    @Override
    public void write(T transport) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("src/resources/dao/" + transport.getClass().getSimpleName() + ".ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(transport);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
