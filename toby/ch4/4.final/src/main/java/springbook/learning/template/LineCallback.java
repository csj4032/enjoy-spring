package springbook.learning.template;

public interface LineCallback<T> {

	T doSomethingWithLine(String line, T res);
}
