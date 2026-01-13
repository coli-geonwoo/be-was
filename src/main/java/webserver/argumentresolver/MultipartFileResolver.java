package webserver.argumentresolver;

import http.ContentType;
import http.request.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import webserver.exception.RequestProcessingException;

public class MultipartFileResolver extends RequestBodyArgumentResolver {

    private static final String MULTIPART_BOUNDARY_MARKER_PREFIX = "--";
    private static final String MULTIPART_BOUNDARY_PREFIX = "boundary".toLowerCase();
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private static final String BOUNDARY_TOKEN_DELIMITER = "=".toLowerCase();

    private static final Pattern NAME_PATTERN = Pattern.compile("name=\"([^\"]+)\"");
    private static final Pattern FILENAME_PATTERN = Pattern.compile("filename=\"([^\"]+)\"");
    private static final Pattern CONTENT_TYPE_PATTERN = Pattern.compile("Content-Type:\\s*(.+)");


    @Override
    protected boolean resolvableType(String contentType) {
        String lowerCaseContentType = contentType.toLowerCase();
        return lowerCaseContentType.startsWith(ContentType.MULTIPART_FORM_DATA.getResponseContentType());
    }

    @Override
    public Object resolve(HttpRequest request, Class<?> clazz) {
        String rawContentType = request.getRequestHeader().getHeaderContent(ContentType.CONTENT_TYPE_HEADER_KEY);
        String boundary = extractBoundary(rawContentType);
        byte[] data = request.getRequestBody().getValue();
        Map<String, List<MultipartFile>> parsedMultiPartFiles = parse(boundary, data);
        return new MultipartFiles(parsedMultiPartFiles);
    }

    private String extractBoundary(String contentType) {
        String[] parts = contentType.split(MULTIPART_BOUNDARY_PREFIX + BOUNDARY_TOKEN_DELIMITER);
        if(parts.length < 2) {
            throw new RequestProcessingException("Invalid multipart boundary");
        }
        return parts[1].trim();
    }

    public Map<String, List<MultipartFile>> parse(String boundary, byte[] data) {
        Map<String, List<MultipartFile>> result = new HashMap<>();

        String boundaryMarker = MULTIPART_BOUNDARY_MARKER_PREFIX + boundary;
        byte[] boundaryBytes = boundaryMarker.getBytes(StandardCharsets.UTF_8);

        List<Integer> boundaryIndices = findBoundaryIndices(data, boundaryBytes);

        for (int i = 0; i < boundaryIndices.size() - 1; i++) {
            int start = boundaryIndices.get(i) + boundaryBytes.length;
            int end = boundaryIndices.get(i + 1);
            byte[] partData = extractPart(data, start, end);
            if (partData.length == 0) continue;
            MultipartFile file = parsePart(partData);
            result.putIfAbsent(file.getName(), new ArrayList<>());
            result.get(file.getName()).add(file);
        }

        return result;
    }

    private List<Integer> findBoundaryIndices(byte[] data, byte[] boundary) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i <= data.length - boundary.length; i++) {
            //두 구간이 같은지 검증(Arrays.mismatch는 서로 다른 값이 없으면 -1을 반환)
            if (Arrays.mismatch(data, i, i + boundary.length, boundary, 0, boundary.length) == -1) {
                indices.add(i);
            }
        }
        return indices;
    }

    private byte[] extractPart(byte[] data, int start, int end) {
        while (start < end && (data[start] == '\r' || data[start] == '\n')) {
            start++;
        }
        while (end > start && (data[end - 1] == '\r' || data[end - 1] == '\n')) {
            end--;
        }
        if (start >= end) return new byte[0];
        byte[] part = new byte[end - start];
        System.arraycopy(data, start, part, 0, end - start);
        return part;
    }

    private MultipartFile parsePart(byte[] partData) {
        int headerEnd = findHeaderEnd(partData);
        String header = new String(partData, 0, headerEnd, StandardCharsets.UTF_8);
        String name = extractName(header);
        String filename = extractFilename(header);
        String contentType = extractContentType(header);

        int dataStart = headerEnd + "\r\n\r\n".length();
        byte[] content = new byte[partData.length - dataStart];
        System.arraycopy(partData, dataStart, content, 0, content.length);

        if (filename == null) {
            String value = new String(content, StandardCharsets.UTF_8);
            return new MultipartFile(name, value);
        }
        return new MultipartFile(name, filename, contentType, content);
    }

    private int findHeaderEnd(byte[] data) {
        for (int i = 0; i < data.length - 3; i++) {
            if (data[i] == '\r' && data[i+1] == '\n'
                    && data[i+2] == '\r' && data[i+3] == '\n') {
                return i;
            }
        }
        return -1;
    }

    private String extractName(String header) {
        Matcher matcher = NAME_PATTERN.matcher(header);
        return matcher.find() ? matcher.group(1) : null;
    }

    private String extractFilename(String header) {
        Matcher matcher = FILENAME_PATTERN.matcher(header);
        return matcher.find() ? matcher.group(1) : null;
    }

    private String extractContentType(String header) {
        Matcher matcher = CONTENT_TYPE_PATTERN.matcher(header);
        return matcher.find() ? matcher.group(1).trim() : DEFAULT_CONTENT_TYPE;
    }
}
