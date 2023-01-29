package com.example.restdocssample.utils;

import lombok.Getter;

public interface DocumentLinkGenerator {

    static String generateLinkCode(DocUrl docUrl) {
        return String.format("link:codes/%s.html[%s %s,role=\"popup\"]", docUrl.pageId, docUrl.text, "코드");
    }

    static String generateText(DocUrl docUrl) {
        return String.format("%s %s", docUrl.text, "코드명");
    }

    enum DocUrl {
        GENDER("gender", "성별"),
        ADDRESS("address", "주소")
        ;

        private final String pageId;
        @Getter
        private final String text;

        DocUrl(String pageId, String text) {
            this.pageId = pageId;
            this.text = text;
        }
    }
}
