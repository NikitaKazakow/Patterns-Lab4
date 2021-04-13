package dao;

import dao.factory.Transport;
import dao.factory.TransportManager;
import dao.factory.exception.DuplicateModelNameException;
import dao.factory.exception.NoSuchModelNameException;
import dao.factory.impl.MotorcycleFactory;
import dao.impl.SerializeDao;
import dao.impl.TextDao;

public class Main {
    public static void main(String[] args) {

        try {

            SerializeDao<Transport> serializeDao = new SerializeDao<>();
            TextDao<Transport> textDao = new TextDao<>();

            Transport car = TransportManager.createInstance("Mitsubishi", 3);

            car.setModelName("Модель 1", "Lancer Evo X");
            car.setModelName("Модель 2", "L200");
            car.setModelName("Модель 3", "Galant");

            serializeDao.write(car);
            textDao.write(car);

            Transport newCar = serializeDao.read();
            System.out.println(newCar + "\n");

            newCar = textDao.read();
            System.out.println(newCar + "\n");

            TransportManager.setFactory(new MotorcycleFactory());

            Transport motorcycle = TransportManager.createInstance("Honda", 3);

            motorcycle.setModelName("Модель 1", "Super sport");
            motorcycle.setModelName("Модель 2", "Adventure");
            motorcycle.setModelName("Модель 3", "Street");

            serializeDao.write(motorcycle);
            textDao.write(motorcycle);

            Transport newMotorcycle = serializeDao.read();
            System.out.println(newMotorcycle + "\n");

            newMotorcycle = textDao.read();
            System.out.println(newMotorcycle);


        } catch (DuplicateModelNameException | NoSuchModelNameException e) {
            e.printStackTrace();
        }
    }
}
