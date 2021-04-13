package dao.factory;

public interface TransportFactory {
    Transport createInstance(String modelName, int modelsCount);
}
