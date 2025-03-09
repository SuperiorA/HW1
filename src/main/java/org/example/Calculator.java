package org.example;

//Написать класс Калькулятор (необобщенный), который содержит обобщенные статические методы: sum(), multiply(), divide(), subtract().
//Параметры этих методов – два числа разного типа, над которыми должна быть произведена операция.
//Методы должны возвращать результат своей работы.

public class Calculator {

    public static <T extends Number, U extends Number> double sum(T num1, U num2) {
        return num1.doubleValue() + num2.doubleValue();
    }

    public static <T extends Number, U extends Number> double subtract(T num1, U num2) {
        return num1.doubleValue() - num2.doubleValue();
    }

    public static <T extends Number, U extends Number> double multiply(T num1, U num2) {
        return num1.doubleValue() * num2.doubleValue();
    }

    public static <T extends Number, U extends Number> double divide(T num1, U num2) {
        if (num2.doubleValue() == 0) {
            throw new ArithmeticException("Деление на ноль невозможно!");
        }
        return num1.doubleValue() / num2.doubleValue();
    }
}
