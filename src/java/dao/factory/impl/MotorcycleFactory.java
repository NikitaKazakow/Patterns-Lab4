package dao.factory.impl;

import dao.factory.Transport;
import dao.factory.TransportFactory;

public class MotorcycleFactory implements TransportFactory {
    @Override
    public Transport createInstance(String modelName, int modelsCount) {
        return new Motorcycle(modelName, modelsCount);
    }
}
