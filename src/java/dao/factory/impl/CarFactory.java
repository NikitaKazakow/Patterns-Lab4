package dao.factory.impl;

import dao.factory.Transport;
import dao.factory.TransportFactory;

public class CarFactory implements TransportFactory {
    @Override
    public Transport createInstance(String modelName, int modelsCount) {
        return new Car(modelName, modelsCount);
    }
}
