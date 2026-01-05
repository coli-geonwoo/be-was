package application.dto.convertor;

public interface DtoConvertor <T> {

    T convert(String rawValue);
}
