package webserver.resolver;

interface HttpResponseResolver<T> {

    String resolve(T object);
}
