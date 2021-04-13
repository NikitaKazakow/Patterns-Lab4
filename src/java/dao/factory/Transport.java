package dao.factory;

import dao.factory.exception.DuplicateModelNameException;
import dao.factory.exception.NoSuchModelNameException;

import java.io.Serializable;

public interface Transport extends Serializable {

    String getBrand();

    void setBrand(String brand);

    void setModelName(String modelName, String newModelName) throws NoSuchModelNameException, DuplicateModelNameException;

    String [] getModelsArray();

    double getPriceByModelName(String modelName) throws NoSuchModelNameException;

    void setPriceByModelName(double price, String modelName) throws NoSuchModelNameException;

    double[] getPrices();

    void addModelNameAndPrice(String modelName, double price)throws DuplicateModelNameException;

    void deleteModel(String modelName) throws NoSuchModelNameException;

    int getModelsCount();

    Transport clone() throws CloneNotSupportedException;
}
