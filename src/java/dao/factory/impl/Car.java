package dao.factory.impl;

import dao.factory.Transport;
import dao.factory.exception.DuplicateModelNameException;
import dao.factory.exception.ModelPriceOutOfBoundsException;
import dao.factory.exception.NoSuchModelNameException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;

public class Car implements Transport, Cloneable {

    //region Fields
    @Getter
    @Setter
    private String brand;

    private Model[] models;
    //endregion

    //region Constructor
    public Car(String brand, int modelsCount) {
        this.brand = brand;
        this.models = new Model[modelsCount];

        for (int i = 0; i < modelsCount; i++) {
            this.models[i] = new Model("Модель " + (i + 1), (i + 1) * 100);
        }
    }
    //endregion

    //region Methods
    @Override
    public void setModelName(String modelName, String newModelName) throws NoSuchModelNameException, DuplicateModelNameException {
        for (Model model : models) {
            if (model.modelName.equals(newModelName)) {
                throw new DuplicateModelNameException("Модель с таким именем уже есть в списке");
            }
        }
        boolean isExists = false;
        for (Model model : models) {
            if (model.modelName.equals(modelName)) {
                model.modelName = newModelName;
                isExists = true;
                break;
            }
        }
        if (!isExists) {
            throw new NoSuchModelNameException("Модели \"" + modelName + "\" нет в массиве!");
        }
    }

    @Override
    public String[] getModelsArray() {
        int size = models.length;
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = models[i].modelName;
        }
        return result;
    }

    @Override
    public double getPriceByModelName(String modelName) throws NoSuchModelNameException {
        double result = 0;
        boolean isExists = false;
        for (Model model : models) {
            if (model.modelName.equals(modelName)) {
                result = model.price;
                isExists = true;
                break;
            }
        }
        if (!isExists) {
            throw new NoSuchModelNameException("Модели \"" + modelName + "\" нет в списке!");
        }
        return result;
    }

    @Override
    public void setPriceByModelName(double price, String modelName) throws NoSuchModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException("Недопустимое значение цены!");
        }
        boolean isExists = false;
        for (Model model : models) {
            if (model.modelName.equals(modelName)) {
                model.price = price;
                isExists = true;
                break;
            }
        }
        if (!isExists) {
            throw new NoSuchModelNameException("Модели \"" + modelName + "\" нет в списке!");
        }
    }

    @Override
    public double[] getPrices() {
        int size = models.length;
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            result[i] = models[i].price;
        }
        return result;
    }

    @Override
    public void addModelNameAndPrice(String modelName, double price) throws DuplicateModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException("Недопустимое значение цены!");
        }
        for (Model value : models) {
            if (value != null && value.modelName.equals(modelName)) {
                throw new DuplicateModelNameException("Модель с названием \"" + modelName
                        + "\" уже есть в списке!");
            }
        }
        int size = models.length;
        Model model = new Model(modelName, price);
        models = Arrays.copyOf(models, size + 1);
        models[size] = model;
    }

    @Override
    public void deleteModel(String modelName) throws NoSuchModelNameException {
        boolean isExists = false;
        int size = models.length;
        Model[] result = new Model[models.length - 1];
        for (int i = 0; i < size; i++) {
            if (models[i].modelName.equals(modelName)) {
                System.arraycopy(models, 0, result, 0, i);
                System.arraycopy(models, i + 1, result, i, models.length - i - 1);
                isExists = true;
                models = result;
                break;
            }
        }
        if (!isExists) {
            throw new NoSuchModelNameException("Модели \"" + modelName + "\" нет в списке!");
        }
    }

    @Override
    public int getModelsCount() {
        return models.length;
    }

    @Override
    public Car clone() throws CloneNotSupportedException {

        Car clone = (Car)super.clone();

        int size = this.getModelsCount();

        clone.models = new Model[size];
        for (int i = 0; i < size; i++) {
            clone.models[i] = models[i].clone();
        }
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(brand);
        for (Model model : models) {
            stringBuilder.append("\n").append(model.toString());
        }
        return stringBuilder.toString();
    }

    //endregion

    //region Child class
    @AllArgsConstructor
    protected class Model implements Cloneable, Serializable {
        //Название модели
        private String modelName;

        //Цена модели
        private double price;

        @Override
        protected Model clone() throws CloneNotSupportedException {
            return (Model) super.clone();
        }

        @Override
        public String toString() {
            return modelName + " : " + price;
        }
    }
    //endregion
}
