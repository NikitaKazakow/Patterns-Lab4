package dao.factory.impl;

import dao.factory.Transport;
import dao.factory.exception.DuplicateModelNameException;
import dao.factory.exception.ModelPriceOutOfBoundsException;
import dao.factory.exception.NoSuchModelNameException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

public class Motorcycle implements Transport, Cloneable {

    //region Fields
    @Getter
    @Setter
    private String brand;

    private Model head = new Model();

    private int size = 0;
    //endregion

    //region Init block
    {
        head.prev = head;
        head.next = head;
    }
    //endregion

    //region Constructors
    public Motorcycle(String brand, int modelsCount) {
        this.brand = brand;
        for (int i = 0; i < modelsCount; i++) {

            Model model = new Model();
            model.modelName = "Модель " + (i + 1);
            model.price = (i + 1) * 100;

            Model temp = head.prev;

            temp.next = model;
            model.prev = temp;

            model.next = head;
            head.prev = model;

            size++;
        }
    }
    //endregion

    //region Methods
    @Override
    public void setModelName(String modelName, String newModelName) throws NoSuchModelNameException, DuplicateModelNameException {
        boolean isExists = false;
        Model temp = head.next;
        while (temp != head) {
            if (temp.modelName.equals(newModelName)) {
                throw new DuplicateModelNameException("Модель с таким именем уже есть в списке");
            }
            temp = temp.next;
        }
        temp = head.next;
        while (temp != head) {
            if (temp.modelName.equals(modelName)) {
                temp.modelName = newModelName;
                isExists = true;
                break;
            } else {
                temp = temp.next;
            }
        }
        if (!isExists) {
            throw new NoSuchModelNameException("Модели \"" + modelName + "\" нет в списке!");
        }
    }

    @Override
    public String[] getModelsArray() {
        String[] result = new String[size];
        Model temp = head.next;
        for (int i = 0; i < size; i++) {
            result[i] = temp.modelName;
            temp = temp.next;
        }
        return result;
    }

    @Override
    public double getPriceByModelName(String modelName) throws NoSuchModelNameException {
        double result = 0;
        boolean isExists = false;
        Model temp = head.next;
        while (temp != head) {
            if (temp.modelName.equals(modelName)) {
                result = temp.price;
                isExists = true;
                break;
            } else {
                temp = temp.next;
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
        Model temp = head.next;
        while (temp != head) {
            if (temp.modelName.equals(modelName)) {
                temp.price = price;
                isExists = true;
                break;
            } else {
                temp = temp.next;
            }
        }
        if (!isExists) {
            throw new NoSuchModelNameException("Модели \"" + modelName + "\" нет в списке!");
        }
    }

    @Override
    public double[] getPrices() {
        Model temp = head.next;
        double[] prices = new double[size];
        for (int i = 0; i < size; i++) {
            prices[i] = temp.price;
            temp = temp.next;
        }
        return prices;
    }

    @Override
    public void addModelNameAndPrice(String modelName, double price) throws DuplicateModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException("Недопустимое значение цены!");
        }
        Model model = new Model(modelName, price, null, null);
        Model temp = head.next;
        while (temp != head) {
            if (temp.modelName.equals(model.modelName)) {
                throw new DuplicateModelNameException("Модель с названием \"" + model.modelName + "\" уже есть в списке!");
            }
            temp = temp.next;
        }

        model.next = temp;
        model.prev = temp.prev;

        temp.prev.next = model;
        temp.prev = model;

        size++;
    }

    @Override
    public void deleteModel(String modelName) throws NoSuchModelNameException {
        boolean isExists = false;
        Model temp = head.next;
        while (temp != head) {
            if (temp.modelName.equals(modelName)) {

                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                size--;

                isExists = true;

                break;
            } else {
                temp = temp.next;
            }
        }
        if (!isExists) {
            throw new NoSuchModelNameException("Модели \"" + modelName + "\" нет в списке!");
        }
    }

    @Override
    public int getModelsCount() {
        return this.size;
    }

    @Override
    public Motorcycle clone() throws CloneNotSupportedException {

        Motorcycle clone = (Motorcycle) super.clone();

        clone.head = new Model();
        clone.head.next = clone.head.prev = clone.head;

        Model temp = head.next;

        Model cloneNext = clone.head;
        Model clonePrev = clone.head;

        while (temp != head) {

            Model cloneModel = temp.clone();
            cloneModel.next = cloneNext;
            cloneModel.prev = clonePrev;

            clonePrev.next = cloneModel;
            cloneNext.prev = cloneModel;

            clonePrev = cloneModel;
            temp = temp.next;
        }

        return clone;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(brand);
        Model temp = head.next;
        while (temp != head) {
            stringBuilder.append("\n").append(temp.toString());
            temp = temp.next;
        }
        return stringBuilder.toString();
    }

    //endregion

    //region Child class
    @NoArgsConstructor
    @AllArgsConstructor
    protected class Model implements Cloneable, Serializable {

        String modelName = null;

        double price = Double.NaN;

        Model prev = null;
        Model next = null;

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
