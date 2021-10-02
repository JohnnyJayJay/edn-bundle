package com.github.johnnyjayjay.ednbundle;

import clojure.java.api.Clojure;
import clojure.lang.ISeq;
import clojure.lang.RT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

public class EdnResourceBundle extends ResourceBundle {

    private final Object map;

    public EdnResourceBundle(Object map) {
        ISeq keys = RT.keys(map);
        Object acc = RT.map();
        while (keys.next() != null) {
            Object key = keys.first();
            acc = RT.assoc(acc, key.toString(), RT.get(map, key));
        }
        this.map = acc;
    }

    @Override
    protected Object handleGetObject(String key) {
        return RT.get(map, key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(keySet());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Set<String> keySet() {
        List keys = Arrays.asList(RT.seqToArray(RT.keys(map)));
        return new HashSet<>((List<String>) keys);
    }

    public static class Control extends ResourceBundle.Control {

        @Override
        public List<String> getFormats(String baseName) {
            Objects.requireNonNull(baseName);
            return Collections.singletonList("edn");
        }

        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
            Arrays.asList(baseName, locale, format, loader).forEach(Objects::requireNonNull);
            ResourceBundle result = null;
            if (format.equals("edn")) {
                String bundleName = toBundleName(baseName, locale);
                String resourceName = toResourceName(bundleName, format);
                InputStream stream = null;
                if (reload) {
                    URL url = loader.getResource(resourceName);
                    if (url != null) {
                        URLConnection connection = url.openConnection();
                        if (connection != null) {
                            connection.setUseCaches(false);
                            stream = connection.getInputStream();
                        }
                    }
                } else {
                    stream = loader.getResourceAsStream(resourceName);
                }

                if (stream != null) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                        String content = reader.lines().collect(Collectors.joining("\n"));
                        Object map = Clojure.read(content);
                        result = new EdnResourceBundle(map);
                    }
                }
            }

            return result;
        }
    }
}
