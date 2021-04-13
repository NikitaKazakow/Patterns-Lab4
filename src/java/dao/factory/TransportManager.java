package dao.factory;

import dao.factory.impl.CarFactory;

public class TransportManager {

    private static TransportFactory factory = new CarFactory();

    public static void setFactory(TransportFactory factory) {
        TransportManager.factory = factory;
    }

    public static double getAveragePriceOfModels(Transport transport) {
        double sum = 0;
        for (double price : transport.getPrices()) {
            sum += price;
        }
        return sum / transport.getModelsCount();
    }

    public static void printModels(Transport transport) {
        System.out.println("Модели:");
        for (String modelName : transport.getModelsArray()) {
            System.out.println(modelName);
        }
    }

    public static void printPrices(Transport transport) {
        System.out.println("Цена на модели:");
        for (double price : transport.getPrices()) {
            System.out.println(price);
        }
    }

    public static Transport createInstance(String name, int size) {
        return TransportManager.factory.createInstance(name, size);
    }
}
