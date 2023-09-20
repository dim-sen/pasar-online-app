package com.online.pasaronlineapp.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

@Slf4j
public class EmailUtil {

    private String template;
    private Map<String, String> replacementParams;

    public EmailUtil(String custom) {
        try {
            this.template = loadTemplate(custom);
        } catch (Exception e) {
            this.template = "Empty";
        }
    }

    private String loadTemplate(String custom) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(custom).getFile());

        String content = "Empty content";
        try {
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            log.error("Can't read template: " + custom);
            throw new RuntimeException(e);
        }
        return content;
    }

    public String getTemplate(Map<String, String> replacementParams) {
        String nTemplate = this.template;
        for (Map.Entry<String, String> entry : replacementParams.entrySet()) {
            nTemplate = nTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        return nTemplate;
    }
}
