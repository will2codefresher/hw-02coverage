package com.coverage


import spock.lang.Specification

class LocaleUtilsTest extends Specification {
    def localeUtils = new LocaleUtils()

    def "should return null when str is null"() {
        given:
        def str = null
        def expectedResult = null

        when:
        def result = localeUtils.toLocale(str)

        then:
        result == expectedResult

    }

    def "should get local of empty when str is empty"() {
        given:
        def str = ""
        def lang = ""
        def country = ""

        when:
        Locale result = localeUtils.toLocale(str)

        then:
        result.getLanguage() == lang
        result.getCountry() == country
    }

    def "should throw IllegalArgumentException when format is invalid"() {
        when:
        localeUtils.toLocale(str)

        then:
        def ex = thrown(Exception)
        ex.class.name == exceptionName
        ex.getMessage() == exceptionMsg

        where:
        str          | exceptionName                        | exceptionMsg
        "hello#aa"   | "java.lang.IllegalArgumentException" | "Invalid locale format: hello#aa"
        "h"          | "java.lang.IllegalArgumentException" | "Invalid locale format: h"
        "_h"         | "java.lang.IllegalArgumentException" | "Invalid locale format: _h"
        "_hW"        | "java.lang.IllegalArgumentException" | "Invalid locale format: _hW"
        "_Hw"        | "java.lang.IllegalArgumentException" | "Invalid locale format: _Hw"

        "_HWa"       | "java.lang.IllegalArgumentException" | "Invalid locale format: _HWa"
        "_HWaa"      | "java.lang.IllegalArgumentException" | "Invalid locale format: _HWaa"
        "C"          | "java.lang.IllegalArgumentException" | "Invalid locale format: C"
        "Cn"         | "java.lang.IllegalArgumentException" | "Invalid locale format: Cn"
        "Chn"        | "java.lang.IllegalArgumentException" | "Invalid locale format: Chn"
        "chnn"       | "java.lang.IllegalArgumentException" | "Invalid locale format: chnn"
        "cn_hj"      | "java.lang.IllegalArgumentException" | "Invalid locale format: cn_hj"
        "cn_HJW"     | "java.lang.IllegalArgumentException" | "Invalid locale format: cn_HJW"
        "cn_12"      | "java.lang.IllegalArgumentException" | "Invalid locale format: cn_12"
        "cn_"        | "java.lang.IllegalArgumentException" | "Invalid locale format: cn_"
        "cn_ "       | "java.lang.IllegalArgumentException" | "Invalid locale format: cn_ "
        "c_HJ"       | "java.lang.IllegalArgumentException" | "Invalid locale format: c_HJ"
        "c_HJ_1234"  | "java.lang.IllegalArgumentException" | "Invalid locale format: c_HJ_1234"
        "c_HJ_"      | "java.lang.IllegalArgumentException" | "Invalid locale format: c_HJ_"
        " _HJ_12"    | "java.lang.IllegalArgumentException" | "Invalid locale format:  _HJ_12"
        "c_HJKK_123" | "java.lang.IllegalArgumentException" | "Invalid locale format: c_HJKK_123"
        "cn_hj_123"  | "java.lang.IllegalArgumentException" | "Invalid locale format: cn_hj_123"
        "cn_ZH_"     | "java.lang.IllegalArgumentException" | "Invalid locale format: cn_ZH_"
    }

    def "should get empty lan when the length of str is 3"() {
        given:
        def str = "_HW"

        when:
        def locale = localeUtils.toLocale(str)

        then:
        locale.getLanguage() == ""
        locale.getCountry() == "HW"
    }

    def "should get locale with emtpy lang and country and aa when str is started with _ and the length is more than 5"() {
        given:
        def str = "_CN_C"

        when:
        def locale = localeUtils.toLocale(str)

        then:
        locale.getLanguage() == ""
        locale.getCountry() == "CN"
        locale.getVariant() == "C"

    }

    def "should get locale when str is of format ISO639"() {
        when:
        def locale = localeUtils.toLocale(str)

        then:
        locale.getLanguage() == lang

        where:
        str   | lang
        "cn"  | "cn"
        "chn" | "chn"

    }

    def "should get locale when str match ISO639 ,ISO3166 or is numberic"() {
        when:
        Locale result = localeUtils.toLocale(str)

        then:
        result.getLanguage() == lang
        result.getCountry() == country

        where:
        str      | lang | country
        "cn_TW"  | "cn" | "TW"
        "cn_123" | "cn" | "123"

    }

    def "should get locale when str contains 2 _"() {
        when:
        Locale result = localeUtils.toLocale(str)

        then:
        result.getLanguage() == lang
        result.getCountry() == country
        result.getVariant() == variant

        where:
        str          | lang | country | variant
        "zh_CN_BIG"  | "zh" | "CN"    | "BIG"
        "zh__BIG"    | "zh" | ""      | "BIG"
        "zh_123_BIG" | "zh" | "123"   | "BIG"

    }

}
