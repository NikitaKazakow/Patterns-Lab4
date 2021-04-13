package dao.impl;

import dao.Dao;
import dao.factory.Transport;
import dao.factory.TransportManager;
import dao.factory.exception.DuplicateModelNameException;
import dao.factory.exception.NoSuchModelNameException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TextDao<T extends Transport> implements Dao<T> {

    @Override
    public T read() {
        T transport = (T) TransportManager.createInstance("1", 1);
        try {
            List<String> objStr = Files.readAllLines(Path.of("src/resources/dao/" + transport.getClass().getSimpleName() + ".txt"));
            String brand = objStr.get(0);
            objStr.remove(0);
            transport.setBrand(brand);
            for (String model : objStr) {
                String modelName = model.split(" : ")[0];
                double price = Double.parseDouble(model.split(" : ")[1]);
                transport.addModelNameAndPrice(modelName, price);
            }
            transport.deleteModel("Модель 1");
        } catch (IOException | DuplicateModelNameException | NoSuchModelNameException e) {
            e.printStackTrace();
        }
        return transport;
    }

    @Override
    public void write(T object) {
        try {
            Files.writeString(Path.of("src/resources/dao/" + object.getClass().getSimpleName() + ".txt"), object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
